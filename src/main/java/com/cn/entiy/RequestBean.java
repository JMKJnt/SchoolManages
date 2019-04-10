package com.cn.entiy;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonProperty;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sun.yayi on 2016/11/21.
 */
public class RequestBean implements Serializable {
    private Logger logger = Logger.getLogger(RequestBean.class);

    @JsonProperty(value = "code")
    private String code;
    @JsonProperty(value = "version")
    private String version;
    @JsonProperty(value = "jsonStr")
    private JSONObject jsonStr;
    private String rspCode;
    private String rspdesc;

    public RequestBean() {
        super();
    }

    public RequestBean(String code, String version, JSONObject jsonStr) {
        this.code = code;
        this.version = version;
        this.jsonStr = jsonStr;
    }

    @Override
    public String toString() {
        return "jsonStr=" + jsonStr +
                ", code='" + code + '\'' +
                ", version='" + version + '\'';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public JSONObject getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(JSONObject jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspdesc() {
        return rspdesc;
    }

    public void setRspdesc(String rspdesc) {
        this.rspdesc = rspdesc;
    }
}
