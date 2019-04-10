package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ACTIVITY_REGISTER_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegisterInfo implements Serializable {

    /**
     * 主键  活动报名id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "REGISTER_ID", length = 32)
    private String registerId;

    /**
     * 活动id
     */
    @Column(name = "REGISTER_ACTIVITY_ID", length = 32)
    private String registerActivityId;

    /**
     * 用户id
     */
    @Column(name = "REGISTER_USER_ID", length = 32)
    private String registerUserId;

    /**
     * 报名来源 1为web,2为微信
     */
    @Column(name = "REGISTER_SOURCE", length = 10)
    private String registerSource;
    /**
     * 报名电话
     */
    @Column(name = "REGISTER_USERPHONE", length = 50)
    private String registerUserPhone;
    /**
     * 报名姓名
     */
    @Column(name = "REGISTER_USERNAME", length = 100)
    private String registerUserName;

    /**
     * 报名时间
     */
    @Column(name = "REGISTER_TIME")
    private Date registerTime;

    /**
     * 活动参与状态 1 已参加，2 进行中，3未参加，4已过期
     */
    @Column(name = "REGISTER_PROGRESS_STATUS", length = 10)
    private String registerProgressStatus;

    /**
     * 审核状态 1 驳回 2 通过 3 待审核
     */
    @Column(name = "REGISTER_APPROVE_STATUS", length = 10)
    private String registerApproveStatus;

    /**
     * 审核人
     */
    @Column(name = "REGISTER_APPROVE_NAME", length = 100)
    private String registerApproveName;

    /**
     * 备注
     */
    @Column(name = "REGISTER_REMARK", length = 1000)
    private String registerRemark;

    /**
     * 创建时间
     */
    @Column(name = "REGISTER_CREATETIME")
    private Date registerCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "REGISTER_UPDATETIME")
    private Date registerUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "REGISTER_CREATENAME", length = 100)
    private String registerCreateName;

    /**
     * 修改人
     */
    @Column(name = "REGISTER_UPDATENAME", length = 100)
    private String registerUpdateName;

    @Column(name = "STATUS",length = 10)
    private String status;

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getRegisterActivityId() {
        return registerActivityId;
    }

    public void setRegisterActivityId(String registerActivityId) {
        this.registerActivityId = registerActivityId;
    }

    public String getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(String registerUserId) {
        this.registerUserId = registerUserId;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterApproveStatus() {
        return registerApproveStatus;
    }

    public void setRegisterApproveStatus(String registerApproveStatus) {
        this.registerApproveStatus = registerApproveStatus;
    }

    public String getRegisterApproveName() {
        return registerApproveName;
    }

    public void setRegisterApproveName(String registerApproveName) {
        this.registerApproveName = registerApproveName;
    }

    public String getRegisterRemark() {
        return registerRemark;
    }

    public void setRegisterRemark(String registerRemark) {
        this.registerRemark = registerRemark;
    }

    public Date getRegisterCreateTime() {
        return registerCreateTime;
    }

    public void setRegisterCreateTime(Date registerCreateTime) {
        this.registerCreateTime = registerCreateTime;
    }

    public Date getRegisterUpdateTime() {
        return registerUpdateTime;
    }

    public void setRegisterUpdateTime(Date registerUpdateTime) {
        this.registerUpdateTime = registerUpdateTime;
    }

    public String getRegisterCreateName() {
        return registerCreateName;
    }

    public void setRegisterCreateName(String registerCreateName) {
        this.registerCreateName = registerCreateName;
    }

    public String getRegisterUpdateName() {
        return registerUpdateName;
    }

    public void setRegisterUpdateName(String registerUpdateName) {
        this.registerUpdateName = registerUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegisterProgressStatus() {
        return registerProgressStatus;
    }

    public void setRegisterProgressStatus(String registerProgressStatus) {
        this.registerProgressStatus = registerProgressStatus;
    }

    public String getRegisterUserPhone() {
        return registerUserPhone;
    }

    public void setRegisterUserPhone(String registerUserPhone) {
        this.registerUserPhone = registerUserPhone;
    }

    public String getRegisterUserName() {
        return registerUserName;
    }

    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
    }
}
