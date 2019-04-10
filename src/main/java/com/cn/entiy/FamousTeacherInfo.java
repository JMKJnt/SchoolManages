package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FAMOUS_TEACHER_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FamousTeacherInfo implements Serializable {

    /**
     * 主键  导师id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "TEACHER_ID", length = 32)
    private String teacherId;

    /**
     * 导师名称
     */
    @Column(name = "TEACHER_NAME",length = 50)
    private String teacherName;

    /**
     * 导师照片
     */
    @Column(name = "TEACHER_IMG",length = 500)
    private String teacherImg;

    /**
     * 导师性别
     */
    @Column(name = "TEACHER_SEX",length = 1)
    private String teacherSex;

    /**
     * 导师出生日期
     */
    @Column(name = "TEACHER_BIRTHDAY",length = 50)
    private String teacherBirthday;

    /**
     * 导师电话
     */
    @Column(name = "TEACHER_PHONE",length = 50)
    private String teacherPhone;

    /**
     * 资质说明
     */
    @Column(name = "TEACHER_QUALIFICATION",length = 4000)
    private String teacherQualification;

    /**
     * 搜索关键词
     */
    @Column(name = "TEACHER_SEARCH_KEYWORD",length = 2000)
    private String teacherSearchKeyword;

    /**
     * 创建时间
     */
    @Column(name = "TEACHER_CREATETIME")
    private Date teacherCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "TEACHER_UPDATETIME")
    private Date teacherUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "TEACHER_CREATENAME",length = 100)
    private String teacherCreateName;

    /**
     * 修改人
     */
    @Column(name = "TEACHER_UPDATENAME",length = 100)
    private String teacherUpdateName;

    @Column(name = "STATUS",length = 10)
    private String status;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherImg() {
        return teacherImg;
    }

    public void setTeacherImg(String teacherImg) {
        this.teacherImg = teacherImg;
    }

    public String getTeacherSex() {
        return teacherSex;
    }

    public void setTeacherSex(String teacherSex) {
        this.teacherSex = teacherSex;
    }

    public String getTeacherBirthday() {
        return teacherBirthday;
    }

    public void setTeacherBirthday(String teacherBirthday) {
        this.teacherBirthday = teacherBirthday;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getTeacherQualification() {
        return teacherQualification;
    }

    public void setTeacherQualification(String teacherQualification) {
        this.teacherQualification = teacherQualification;
    }

    public String getTeacherSearchKeyword() {
        return teacherSearchKeyword;
    }

    public void setTeacherSearchKeyword(String teacherSearchKeyword) {
        this.teacherSearchKeyword = teacherSearchKeyword;
    }

    public Date getTeacherCreateTime() {
        return teacherCreateTime;
    }

    public void setTeacherCreateTime(Date teacherCreateTime) {
        this.teacherCreateTime = teacherCreateTime;
    }

    public Date getTeacherUpdateTime() {
        return teacherUpdateTime;
    }

    public void setTeacherUpdateTime(Date teacherUpdateTime) {
        this.teacherUpdateTime = teacherUpdateTime;
    }

    public String getTeacherCreateName() {
        return teacherCreateName;
    }

    public void setTeacherCreateName(String teacherCreateName) {
        this.teacherCreateName = teacherCreateName;
    }

    public String getTeacherUpdateName() {
        return teacherUpdateName;
    }

    public void setTeacherUpdateName(String teacherUpdateName) {
        this.teacherUpdateName = teacherUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
