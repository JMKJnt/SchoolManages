package com.cn.common;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 网络工具类。
 *
 * @author carver.gu
 * @since 1.0, Sep 12, 2009
 */
@SuppressWarnings("unused")
public abstract class WebUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    private static class DefaultTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private WebUtils() {
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, Object> params, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, Object> params, String charset, int connectTimeout, int readTimeout)
            throws IOException {
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return doPost(url, ctype, content, connectTimeout, readTimeout);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url     请求地址
     * @param ctype   请求类型
     * @param content 请求字节数组
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String responseData = null;
        try {
            try {
                conn = getConnection(new URL(url), METHOD_POST, ctype);
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            } catch (IOException e) {
                Map<String, String> map = getParamsFromUrl(url);
                throw e;
            }
            try {
                out = conn.getOutputStream();
                out.write(content);
                responseData = getResponseAsString(conn);
            } catch (IOException e) {
                Map<String, String> map = getParamsFromUrl(url);
                throw e;
            }

        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return responseData;
    }

    /**
     * @param url
     * @param method
     * @param ctype
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getConnection(URL url, String method, String ctype) throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;// 默认都认证通过
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "*");
        conn.setRequestProperty("User-Agent", "top-sdk-java");
        conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    /**
     * @param url
     * @return
     */
    private static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> map = null;
        if (url != null && url.indexOf('?') != -1) {
            map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
        }
        if (map == null) {
            map = new HashMap<String, String>();
        }
        return map;
    }

    /**
     * @param conn
     * @return
     * @throws IOException
     */
    private static String getResponseAsString(HttpURLConnection conn) throws IOException {

        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (Utils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    /**
     * @param stream
     * @param charset
     * @return
     * @throws IOException
     */
    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();
            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }
            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    /**
     * @param ctype
     * @return
     */
    private static String getResponseCharset(String ctype) {

        String charset = DEFAULT_CHARSET;
        if (!Utils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!Utils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }
        return charset;
    }

    /**
     * @param query
     * @return
     */
    private static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<String, String>();
        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }
        return result;
    }

    /**
     * @param params
     * @param charset
     * @return
     * @throws IOException
     */
    private static String buildQuery(Map<String, Object> params, String charset) throws IOException {

        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Entry<String, Object>> entries = params.entrySet();
        boolean hasParam = false;
        for (Entry<String, Object> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue().toString();
            // 忽略参数名或参数值为空的参数
            if (Utils.areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }
        return query.toString();
    }


    /**
     * httpclient post请求方式
     *
     * @param url
     * @param marked
     * @param jsonStr
     * @return
     */
    public static JSONObject httpPostMethod(String url, String marked, String jsonStr) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        JSONObject resultJson = new JSONObject();
        try {
            if (!CommonHelper.isNullOrEmpty(marked) && !CommonHelper.isNullOrEmpty(jsonStr)) {
                String param = "marked=" + marked + "&&code=10002&&version=1.0&&jsonStr=" + jsonStr;
                //RequestEntity requestEntity = new StringRequestEntity(param,"application/json","utf-8");

                URL realUrl = new URL(url);
                // 打开和URL之间的连接
                URLConnection conn = realUrl.openConnection();
                // 设置通用的请求属性
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // 发送POST请求必须设置如下两行
                conn.setDoOutput(true);
                conn.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } else {
                resultJson.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                resultJson.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                return resultJson;
            }

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            resultJson = new JSONObject(result);
        } catch (JSONException e) {
            resultJson.put("code", -404);
            resultJson.put("message", "返回数据无法转JSON");
            e.printStackTrace();
        }
        return resultJson;
//		return result;

	/*
        String responseMsg = "";
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("utf-8");
		PostMethod postMethod = new PostMethod(url);
		try {
			//传递json参数
			if(StringUtils.isNotBlank(marked) && StringUtils.isNotBlank(jsonStr)) {
				String param="marked="+marked+"&&code=10002&&version=1.0&&jsonStr="+jsonStr;
				RequestEntity requestEntity = new StringRequestEntity(param,"application/json","utf-8");
				postMethod.setRequestEntity(requestEntity);
			}
//		requestEntity	TreeMap<String,String> treeMap = toTreeMap(JSON.parseObject(paramter));
//			if(null != treeMap){
//				for (Map.Entry<String, String> entry : treeMap.entrySet()) {
//					postMethod.addParameter(entry.getKey(),  entry.getValue());
//				}
//			}
			// 执行postMethod,调用http接口
			httpClient.executeMethod(postMethod);// 200
			// 读取内容
			responseMsg = postMethod.getResponseBodyAsString().trim();
		}catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			// 7.释放连接
			postMethod.releaseConnection();
		}
		return responseMsg;*/
    }

    /**
     * post访问方法
     *
     * @param paramJSON
     * @param url
     * @return
     */
    public static JSONObject sendHttpPost(JSONObject paramJSON, String url) throws JSONException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(50000).setConnectTimeout(50000).build();// 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;
        String result = "";
    /*	List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        Iterator it = paramJSON.keys();
		while (it.hasNext()) {
			try {
				String key = (String) it.next();
				String value = paramJSON.get(key).toString();
				parameters.add(new BasicNameValuePair(key, value));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}*/
        try {
            //httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            StringEntity se = new StringEntity(paramJSON.toString(), "UTF-8");
            se.setContentType("application/json");
            httpPost.setEntity(se);
            httpResponse = httpClient.execute(httpPost);

            int sc = httpResponse.getStatusLine().getStatusCode();
            switch (sc) {
                case 200:
                    // 第三步，使用getEntity方法活得返回结果
                    result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            result = "{\"code\":-404,\"message\":\"网络访问异常:" + e.getMessage() + "\",\"data\":{},\"rspCode\":\"-404\",\"rspDesc\":\"网络访问异常:" + e.getMessage() + "\"}";
            e.printStackTrace();
        }

        System.out.println("返回值:" + result.toString());
        JSONObject resultJson = new JSONObject();
        try {
            resultJson = new JSONObject(result);
        } catch (JSONException e) {
            resultJson.put("code", -404);
            resultJson.put("message", "返回数据无法转JSON");
            resultJson.put("rspCode", "-404");
            resultJson.put("rspDesc", "返回数据无法转JSON");
            e.printStackTrace();
        }
        return resultJson;
    }

    public static JSONObject sendHttpPostForm(JSONObject paramJSON, String url) throws JSONException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(50000).setConnectTimeout(50000).build();// 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;
        String result = "";
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        Iterator it = paramJSON.keys();
        while (it.hasNext()) {
            try {
                String key = (String) it.next();
                String value = paramJSON.get(key).toString();
                parameters.add(new BasicNameValuePair(key, value));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            //StringEntity se = new StringEntity(paramJSON.toString(), "UTF-8");
//			se.setContentType("application/json");
            //httpPost.setEntity(se);

            httpResponse = httpClient.execute(httpPost);

            int sc = httpResponse.getStatusLine().getStatusCode();
            switch (sc) {
                case 200:
                    // 第三步，使用getEntity方法活得返回结果
                    result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                    break;
                default:
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            result = "{\"code\":-404,\"message\":\"网络访问异常:" + e.getMessage() + "\",\"data\":{}}";
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            result = "{\"code\":-404,\"message\":\"网络访问异常:" + e.getMessage() + "\",\"data\":{}}";
            e.printStackTrace();
        } catch (IOException e) {
            result = "{\"code\":-404,\"message\":\"网络访问异常:" + e.getMessage() + "\",\"data\":{}}";
            e.printStackTrace();
        }
        System.out.println("返回:" + result);
        JSONObject resultJson = new JSONObject();
        try {
            resultJson = new JSONObject(result);
        } catch (JSONException e) {
            resultJson.put("code", -404);
            resultJson.put("message", "返回数据无法转JSON");
            e.printStackTrace();
        }
        return resultJson;
    }

    public static void getDownloadFile(String excel_url, HttpServletResponse response, String fileName, String param) {
        HttpURLConnection meidaConn = null;
        try {
            if (!CommonHelper.isNullOrEmpty(excel_url)) {
                URL mediaUrl = new URL(excel_url);
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%即将访问的路径%%%%%%%%%%%%%%%%%%%%%" + mediaUrl);
                meidaConn = (HttpURLConnection) mediaUrl.openConnection();
                meidaConn.setRequestMethod("POST");
                meidaConn.setDoOutput(true);

                meidaConn.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                OutputStream writerT = meidaConn.getOutputStream();
                // 发送请求参数
                writerT.write(param.getBytes("UTF-8"));
                // flush输出流的缓冲
                writerT.flush();

                // meidaConn.connect();
                InputStream inStream = meidaConn.getInputStream();

                String name = fileName + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".xls";

                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Type", "text/html;charset=UTF-8");
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));

                OutputStream out = response.getOutputStream();
                byte[] buff = new byte[8096];
                int rc = 0;
                while ((rc = inStream.read(buff, 0, 100)) > 0) {
                    out.write(buff, 0, rc);
                }
                out.close();
                meidaConn.disconnect();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * httpPost访问请求
     *
     * @param marked
     * @param jsonStr
     * @return
     */
    public static String postHttp(String marked, String jsonStr, String url) {
        String responseMsg = "";
        // 1.构造HttpClient的实例
        HttpClient httpClient = new HttpClient();

        httpClient.getParams().setContentCharset("utf-8");
        // 接口地址
//        String urlServer = "http://192.168.15.138:8080/changeFunction/interfaceForApay?";
        String urlServer = url;
        // 2.构造PostMethod的实例

        PostMethod postMethod = new PostMethod(urlServer);
        // 3.把参数值放入到PostMethod对象中
        // 方式1：
        /*
         * NameValuePair[] data = { new NameValuePair("param1", param1), new
		 * NameValuePair("param2", param2) }; postMethod.setRequestBody(data);
		 */
        // 方式2：
        /*if("balance_weichat_callback".equals(marked))
        {
            postMethod.addParameter("code", "10002");
            postMethod.addParameter("version", "1.0");
        }*/

        postMethod.addParameter("jsonStr", jsonStr);
        try {
            // 4.执行postMethod,调用http接口
            httpClient.executeMethod(postMethod);// 200
            // 5.读取内容
            responseMsg = postMethod.getResponseBodyAsString().trim();
            // responseMsg = new
            // String(responseMsg.getBytes("iso-8859-1"),"UTF-8");
            // 6.处理返回的内容
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 7.释放连接
            postMethod.releaseConnection();
        }
        return responseMsg;
    }

    public static String sendHttpPostFormString(JSONObject paramJSON, String url) throws JSONException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(50000).setConnectTimeout(50000).build();// 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;
        String result = "";
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        Iterator it = paramJSON.keys();
        while (it.hasNext()) {
            try {
                String key = (String) it.next();
                String value = paramJSON.get(key).toString();
                parameters.add(new BasicNameValuePair(key, value));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            //StringEntity se = new StringEntity(paramJSON.toString(), "UTF-8");
//			se.setContentType("application/json");
            //httpPost.setEntity(se);

            httpResponse = httpClient.execute(httpPost);

            int sc = httpResponse.getStatusLine().getStatusCode();
            switch (sc) {
                case 200:
                    // 第三步，使用getEntity方法活得返回结果
                    result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                    break;
                default:
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            result = "{\"code\":-404,\"message\":\"网络访问异常:" + e.getMessage() + "\",\"data\":{}}";
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            result = "{\"code\":-404,\"message\":\"网络访问异常:" + e.getMessage() + "\",\"data\":{}}";
            e.printStackTrace();
        } catch (IOException e) {
            result = "{\"code\":-404,\"message\":\"网络访问异常:" + e.getMessage() + "\",\"data\":{}}";
            e.printStackTrace();
        }
        System.out.println("返回:" + result);
        return result;
    }

    /**
     * 描述 封装参数
     *
     * @param param
     * @return
     */
    private static String StringEntiyApend(JSONObject param) {
        String result = "";
        StringBuilder query = new StringBuilder();
        if (param == null) {
            return result;
        } else {
            Iterator<String> iterator = param.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = param.getString(key);
                if (!Utils.isEmpty(value.toString())) {
                    query = query.length() > 0 ? query.append("&").append(key) : query.append(key);
                    query.append("=").append(value);
                    System.out.println("query=" + query);
                }
            }
            result = query.toString();
        }
        return result;
    }

    /**
     * 描述 get请求
     *
     * @param paramJSON
     * @param url
     * @return
     * @throws JSONException
     */
    public static JSONObject sendHttpGet(JSONObject paramJSON, String url) throws JSONException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //处理参数
        String jsonstr = StringEntiyApend(paramJSON);
        url = jsonstr.equals("") ? url : (url + "?" + jsonstr);
        System.out.println("地址url=" + url);
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(50000).setConnectTimeout(50000).build();// 设置请求和传输超时时间
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;
        String result = "";
        try {
            httpResponse = httpClient.execute(httpGet);
            int sc = httpResponse.getStatusLine().getStatusCode();
            switch (sc) {
                case 200:
                    // 第三步，使用getEntity方法活得返回结果
                    result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            result = "{\"code\":-404,\"message\":\"网络访问异常:" + e.getMessage() + "\",\"data\":{},\"rspCode\":\"-404\",\"rspDesc\":\"网络访问异常:" + e.getMessage() + "\"}";
            e.printStackTrace();
        }

        System.out.println("返回值:" + result.toString());
        JSONObject resultJson = new JSONObject();
        try {
            resultJson = new JSONObject(result);
        } catch (JSONException e) {
            resultJson.put("code", -404);
            resultJson.put("message", "返回数据无法转JSON");
            resultJson.put("rspCode", "-404");
            resultJson.put("rspDesc", "返回数据无法转JSON");
            e.printStackTrace();
        }
        return resultJson;
    }
}
