package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FAMILY_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FamilyInfo implements Serializable {

    /**
     * 主键  用户id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "FAMILY_ID", length = 32)
    private String familyId;

    /**
     * 用户名称
     */
    @Column(name = "FAMILY_NAME", length = 100)
    private String familyName;

    /**
     * 用户电话
     */
    @Column(name = "FAMILY_PHONE", length = 50)
    private String  familyPhone;

    /**
     * 家庭分类  1父母，2外/祖父母，3其他  码表
     */
    @Column(name = "FAMILY_CATE", length = 10)
    private String familyCate;

    /**
     * 班级id
     */
    @Column(name = "FAMILY_USERID", length = 32)
    private String familyUserId;
    /*
    邮箱
     */
    @Column(name = "FAMILY_EMAIL", length = 100)
    private String familyEmail;

    @Column(name = "FAMILY_ADDRESS", length = 100)
    private String familyAddress;

    /**
     * 创建时间
     */
    @Column(name = "FAMILY_CREATETIME")
    private Date familyCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "FAMILY_UPDATETIME")
    private Date familyUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "FAMILY_CREATENAME", length = 100)
    private String familyCreateName;

    /**
     * 修改人
     */
    @Column(name = "FAMILY_UPDATENAME", length = 100)
    private String familyUpdateName;

    @Column(name = "STATUS", length = 10)
    private String status;

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public String getFamilyCate() {
        return familyCate;
    }

    public void setFamilyCate(String familyCate) {
        this.familyCate = familyCate;
    }

    public String getFamilyUserId() {
        return familyUserId;
    }

    public void setFamilyUserId(String familyUserId) {
        this.familyUserId = familyUserId;
    }

    public String getFamilyEmail() {
        return familyEmail;
    }

    public void setFamilyEmail(String familyEmail) {
        this.familyEmail = familyEmail;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public Date getFamilyCreateTime() {
        return familyCreateTime;
    }

    public void setFamilyCreateTime(Date familyCreateTime) {
        this.familyCreateTime = familyCreateTime;
    }

    public Date getFamilyUpdateTime() {
        return familyUpdateTime;
    }

    public void setFamilyUpdateTime(Date familyUpdateTime) {
        this.familyUpdateTime = familyUpdateTime;
    }

    public String getFamilyCreateName() {
        return familyCreateName;
    }

    public void setFamilyCreateName(String familyCreateName) {
        this.familyCreateName = familyCreateName;
    }

    public String getFamilyUpdateName() {
        return familyUpdateName;
    }

    public void setFamilyUpdateName(String familyUpdateName) {
        this.familyUpdateName = familyUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
