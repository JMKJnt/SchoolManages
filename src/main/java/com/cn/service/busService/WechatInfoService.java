package com.cn.service.busService;

import org.json.JSONObject;

public interface WechatInfoService {
    /**
     * 新增token
     * @param jsonstr
     * @return
     */
    public JSONObject insertToken(String jsonstr);

    /*
    功能：获取token
     */
    public JSONObject getToken(String jsonstr);

}
