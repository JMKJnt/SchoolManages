package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "WECHAT_TOKEN_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class wechatTokenInfo implements Serializable {

    /**
     * 主键  tokenid
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "TOKEN_ID", length = 32)
    private String tokenId;

    /**
     * 班级名称
     */
    @Column(name = "WECHAT_SECRET", length = 200)
    private String wechatSecret;

    /**
     * 班级名称
     */
    @Column(name = "WECHAT_APPID", length = 200)
    private String wechatAppid;

    /**
     * 班级名称
     */
    @Column(name = "WECHAT_TOKEN", length = 500)
    private String wechatToken;

    /**
     * 创建时间
     */
    @Column(name = "TOKEN_CREATETIME")
    private Date tokenCreateTime;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Date getTokenCreateTime() {
        return tokenCreateTime;
    }

    public void setTokenCreateTime(Date tokenCreateTime) {
        this.tokenCreateTime = tokenCreateTime;
    }

    public String getWechatSecret() {
        return wechatSecret;
    }

    public void setWechatSecret(String wechatSecret) {
        this.wechatSecret = wechatSecret;
    }

    public String getWechatAppid() {
        return wechatAppid;
    }

    public void setWechatAppid(String wechatAppid) {
        this.wechatAppid = wechatAppid;
    }

    public String getWechatToken() {
        return wechatToken;
    }

    public void setWechatToken(String wechatToken) {
        this.wechatToken = wechatToken;
    }
}
