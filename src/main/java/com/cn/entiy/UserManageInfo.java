package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USER_MANAGE_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserManageInfo implements Serializable {

    /**
     * 主键  管理id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "MANAGE_ID", length = 32)
    private String manageId;

    /**
     * 管理员姓名
     */
    @Column(name = "MANAGE_NAME", length = 50)
    private String manageName;

    /**
     * 管理员密码
     */
    @Column(name = "MANAGE_PASSWORD", length = 500)
    private String managePassword;

    /**
     * 管理员名称
     */
    @Column(name = "MANAGE_USERNAME", length = 100)
    private String manageUserName;

    /**
     * 管理员电话
     */
    @Column(name = "MANAGE_PHONE", length = 50)
    private String managePhone;

    /**
     * 权限id
     */
    @Column(name = "MANAGE_PURVIEWID", length = 32)
    private String managePurviewId;

    /**
     * 创建时间
     */
    @Column(name = "MANAGE_CREATETIME")
    private Date manageCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "MANAGE_UPDATETIME")
    private Date manageUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "MANAGE_CREATENAME", length = 100)
    private String manageCreateName;

    /**
     * 修改人
     */
    @Column(name = "MANAGE_UPDATENAME", length = 100)
    private String manageUpdateName;

    @Column(name = "STATUS",length = 10)
    private String status;

    public String getManageId() {
        return manageId;
    }

    public void setManageId(String manageId) {
        this.manageId = manageId;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }

    public String getManagePassword() {
        return managePassword;
    }

    public void setManagePassword(String managePassword) {
        this.managePassword = managePassword;
    }

    public String getManageUserName() {
        return manageUserName;
    }

    public void setManageUserName(String manageUserName) {
        this.manageUserName = manageUserName;
    }

    public String getManagePhone() {
        return managePhone;
    }

    public void setManagePhone(String managePhone) {
        this.managePhone = managePhone;
    }

    public String getManagePurviewId() {
        return managePurviewId;
    }

    public void setManagePurviewId(String managePurviewId) {
        this.managePurviewId = managePurviewId;
    }

    public Date getManageCreateTime() {
        return manageCreateTime;
    }

    public void setManageCreateTime(Date manageCreateTime) {
        this.manageCreateTime = manageCreateTime;
    }

    public Date getManageUpdateTime() {
        return manageUpdateTime;
    }

    public void setManageUpdateTime(Date manageUpdateTime) {
        this.manageUpdateTime = manageUpdateTime;
    }

    public String getManageCreateName() {
        return manageCreateName;
    }

    public void setManageCreateName(String manageCreateName) {
        this.manageCreateName = manageCreateName;
    }

    public String getManageUpdateName() {
        return manageUpdateName;
    }

    public void setManageUpdateName(String manageUpdateName) {
        this.manageUpdateName = manageUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
