package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USER_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserInfo implements Serializable {

    /**
     * 主键  用户id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "USER_ID", length = 32)
    private String userId;

    /**
     * 用户名称
     */
    @Column(name = "USER_NAME", length = 100)
    private String userName;

    /**
     * 用户密码
     */
    @Column(name = "USER_PASSWORD", length = 500)
    private String userPassword;

    /**
     * 用户昵称，微信昵称
     */
    @Column(name = "USER_NICKNAME", length = 200)
    private String userNickName;

    /**
     * 用户电话
     */
    @Column(name = "USER_PHONE", length = 50)
    private String userPhone;

    /**
     * 用户角色  码表
     */
    @Column(name = "USER_ROLETYPE", length = 10)
    private String userRoleType;

    /**
     * 三方id  openid
     */
    @Column(name = "USER_THIRDID", length = 100)
    private String userThirdId;
    /**
     * 班级id
     */
    @Column(name = "USER_CLASS", length = 32)
    private String userClass;

    /**
     * 微信 unionid
     */
    @Column(name = "USER_WECHAT_UNIONID", length = 100)
    private String userWechatUnionId;

    /**
     * 登陆时间
     */
    @Column(name = "USER_LOGINTIME")
    private Date userLoginTime;

    /*
    活动次数
     */
    @Column(name="COUNTACTIVE",length = 10)
    private String countActive;

    /*
    邮箱
     */
    @Column(name="USER_EMAIL",length = 100)
    private String userEmail;

    @Column(name = "USER_ADDRESS",length = 100)
    private String userAddress;

    /**
     * 创建时间
     */
    @Column(name = "USER_CREATETIME")
    private Date userCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "USER_UPDATETIME")
    private Date userUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "USER_CREATENAME", length = 100)
    private String userCreateName;

    /**
     * 修改人
     */
    @Column(name = "USER_UPDATENAME", length = 100)
    private String userUpdateName;

    @Column(name = "STATUS", length = 10)
    private String status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(String userRoleType) {
        this.userRoleType = userRoleType;
    }

    public String getUserThirdId() {
        return userThirdId;
    }

    public void setUserThirdId(String userThirdId) {
        this.userThirdId = userThirdId;
    }

    public String getUserWechatUnionId() {
        return userWechatUnionId;
    }

    public void setUserWechatUnionId(String userWechatUnionId) {
        this.userWechatUnionId = userWechatUnionId;
    }

    public Date getUserLoginTime() {
        return userLoginTime;
    }

    public void setUserLoginTime(Date userLoginTime) {
        this.userLoginTime = userLoginTime;
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public Date getUserUpdateTime() {
        return userUpdateTime;
    }

    public void setUserUpdateTime(Date userUpdateTime) {
        this.userUpdateTime = userUpdateTime;
    }

    public String getUserCreateName() {
        return userCreateName;
    }

    public void setUserCreateName(String userCreateName) {
        this.userCreateName = userCreateName;
    }

    public String getUserUpdateName() {
        return userUpdateName;
    }

    public void setUserUpdateName(String userUpdateName) {
        this.userUpdateName = userUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getCountActive() {
        return countActive;
    }

    public void setCountActive(String countActive) {
        this.countActive = countActive;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
