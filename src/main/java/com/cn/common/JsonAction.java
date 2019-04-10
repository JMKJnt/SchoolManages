package com.cn.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by cuixiaowei on 2015/12/1.
 */
public class JsonAction {


    /**
     * 处理错误返回的json格式化返回串
     * @param code 错误编码
     * @param message 错误信息
     * @return
     */
    public static JSONObject errorJson(String code, String message)
    {
        JSONObject result = new JSONObject();
        result.put("rspCode", code);
        result.put("rspDesc", message);
        return result;
    }


    /**
     * 若返回json为null，则将其转换成字符串
     * @param jsonObject
     * @return
     */
    public static String toString(JSONObject jsonObject)
    {

        SerializerFeature[] features = new SerializerFeature[]{
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty};
        String jsonString = JSON.toJSONString(jsonObject, features);
        return jsonString;
    }

}
