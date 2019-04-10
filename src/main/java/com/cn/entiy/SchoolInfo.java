package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SCHOOL_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchoolInfo implements Serializable {

    /**
     * 主键  学校id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "SCHOOL_ID", length = 32)
    private String schoolId;

    /**
     * 学校名称
     */
    @Column(name = "SCHOOL_NAME", length = 200)
    private String schoolName;

    /*
    学校简称
     */
    @Column(name = "SCHOOL_SHORTNAME",length = 100)
    private String schoolShortName;

    /**
     * 省id
     */
    @Column(name = "SCHOOL_PROVINCE", length = 50)
    private String schoolProvince;

    /**
     * 市id
     */
    @Column(name = "SCHOOL_CITY", length = 50)
    private String schoolCity;

    /**
     * 县id
     */
    @Column(name = "SCHOOL_COUNTY", length = 50)
    private String schoolCounty;

    /**
     * 学校地址
     */
    @Column(name = "SCHOOL_ADDRESS", length = 500)
    private String schoolAddress;

    /**
     * 班级描述
     */
    @Column(name = "SCHOOL_DESC", length = 2000)
    private String schoolDesc;

    /**
     * 创建时间
     */
    @Column(name = "SCHOOL_CREATETIME")
    private Date schoolCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "SCHOOL_UPDATETIME")
    private Date schoolUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "SCHOOL_CREATENAME", length = 100)
    private String schoolCreateName;

    /**
     * 修改人
     */
    @Column(name = "SCHOOL_UPDATENAME", length = 100)
    private String schoolUpdateName;

    @Column(name = "STATUS",length = 10)
    private String status;

    /*
    学校类型 1 城市 2 农村
     */
    @Column(name = "SCHOOL_TYPE",length = 10)
    private String schoolType;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolProvince() {
        return schoolProvince;
    }

    public void setSchoolProvince(String schoolProvince) {
        this.schoolProvince = schoolProvince;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }

    public String getSchoolCounty() {
        return schoolCounty;
    }

    public void setSchoolCounty(String schoolCounty) {
        this.schoolCounty = schoolCounty;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolDesc() {
        return schoolDesc;
    }

    public void setSchoolDesc(String schoolDesc) {
        this.schoolDesc = schoolDesc;
    }

    public Date getSchoolCreateTime() {
        return schoolCreateTime;
    }

    public void setSchoolCreateTime(Date schoolCreateTime) {
        this.schoolCreateTime = schoolCreateTime;
    }

    public Date getSchoolUpdateTime() {
        return schoolUpdateTime;
    }

    public void setSchoolUpdateTime(Date schoolUpdateTime) {
        this.schoolUpdateTime = schoolUpdateTime;
    }

    public String getSchoolCreateName() {
        return schoolCreateName;
    }

    public void setSchoolCreateName(String schoolCreateName) {
        this.schoolCreateName = schoolCreateName;
    }

    public String getSchoolUpdateName() {
        return schoolUpdateName;
    }

    public void setSchoolUpdateName(String schoolUpdateName) {
        this.schoolUpdateName = schoolUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSchoolShortName() {
        return schoolShortName;
    }

    public void setSchoolShortName(String schoolShortName) {
        this.schoolShortName = schoolShortName;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }
}
