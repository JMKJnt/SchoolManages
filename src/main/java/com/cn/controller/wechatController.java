package com.cn.controller;

import com.cn.common.LogHelper;
import com.cn.common.MsgAndCode;
import com.cn.common.Utils;
import com.cn.common.WebUtils;
import com.cn.entiy.RequestBean;
import com.cn.service.busService.WechatInfoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@RequestMapping(value = "/wechatInfo")
public class wechatController {

    private String serverUrl = "https://api.weixin.qq.com";

    @Autowired
    private WechatInfoService wechatInfoService;

    @Value("${baAppId}")
    String baAppId;

    @Value("${baSecret}")
    String baSecret;

    private SimpleDateFormat tradeDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @ResponseBody
    @RequestMapping(value = "/getToken", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getToken() {
        LogHelper.info("新增活动 请求参数：");
        JSONObject result = new JSONObject();
        try {
            JSONObject tokenObj = new JSONObject();
            tokenObj.put("appid", baAppId);
            tokenObj.put("secret", baSecret);
            result = wechatInfoService.getToken(tokenObj.toString());
            LogHelper.info("新增活动 请求参数：result=" + result.toString());
            if (result.getString("rspCode").equals("000")) {
                //成功，获取token time，判断是否过期
                String dateNow = tradeDate.format(new Date());
                String timeOld = tradeDate.format(result.get("time"));
                LogHelper.info("新增活动 当前时间：" + dateNow);
                if (dateNow.compareTo(timeOld) >= 0) {
                    //重新获取token
                    result = getTokens();
                }
            } else {
                result = getTokens();
            }
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /*
    功能：获取token
     */
    protected JSONObject getTokens() {
        JSONObject result = new JSONObject();
        JSONObject obj = new JSONObject();
        obj.put("appid", baAppId);
        obj.put("secret", baSecret);
        obj.put("grant_type", "client_credential");
        String url = serverUrl + "/cgi-bin/token";
        JSONObject rejo = WebUtils.sendHttpGet(obj, url); //quanxianService.sendHttpPostFrom(beans, url, "请求数据查询订单状态信息接口嵩高");
        LogHelper.info("获取token 请求参数：" + rejo.toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 2);//添加2个小时
        Date time = calendar.getTime();
        //保存到库中
        if (!Utils.isEmpty(rejo.get("access_token").toString())) {
            obj.put("token", rejo.getString("access_token"));
            obj.put("time", tradeDate.format(time));
            result = wechatInfoService.insertToken(obj.toString());
            result.put("token", rejo.getString("access_token"));
        } else {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_015);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_015_MSG);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getOpenId", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getOpenId(@RequestBody RequestBean bean) {
        LogHelper.info("获取openID 请求参数：" + bean.getJsonStr());
        JSONObject result = new JSONObject();
        try {
            JSONObject obj = bean.getJsonStr();
            obj.put("appid", baAppId);
            obj.put("secret", baSecret);
            obj.put("grant_type", "authorization_code");
            LogHelper.info("获取openID 请求参数：" + obj.toString());
            String url = serverUrl + "/sns/jscode2session";
            JSONObject rejo = WebUtils.sendHttpGet(obj, url); //quanxianService.sendHttpPostFrom(beans, url, "请求数据查询订单状态信息接口嵩高");
            //保存到库中
            if (!Utils.isEmpty(rejo.get("openid").toString())) {
                result.put("openid", rejo.get("openid").toString());
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
            } else {
                result.put("openid", "");
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_016);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_016_MSG);
            }

        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/sendMsgWechat", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String sendMsgWechat(@RequestBody RequestBean bean) {
        LogHelper.info("发送模板信息 请求参数：" + bean.getJsonStr());
        JSONObject result = new JSONObject();
        try {
            JSONObject obj = bean.getJsonStr();
            //先根据appid 和secret 获取token信息
            JSONObject tokenObj = new JSONObject();
            tokenObj.put("appid", baAppId);
            tokenObj.put("secret", baSecret);
            result = wechatInfoService.getToken(tokenObj.toString());
            String token = "";
            if (result.getString("rspCode").equals("000")) {
                //成功，获取token time，判断是否过期
                String dateNow = tradeDate.format(new Date());
                String timeOld = tradeDate.format(result.get("time"));
                LogHelper.info("获取token 当前时间：" + dateNow);
                if (dateNow.compareTo(timeOld) >= 0) {
                    //重新获取token
                    result = getTokens();
                }
                token = result.getString("token");
            }

            String url = serverUrl + "/cgi-bin/message/wxopen/template/send?access_token=" + token;
            JSONObject rejo = WebUtils.sendHttpPost(obj, url); //quanxianService.sendHttpPostFrom(beans, url, "请求数据查询订单状态信息接口嵩高");
            LogHelper.info("发送模板消息返回参数：" + rejo.toString());
            //保存到库中
            if (rejo.get("errcode").toString().equals("0")) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
            } else {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_016);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_016_MSG);
            }

        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }
}
