package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CLASS_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClassInfo implements Serializable {

    /**
     * 主键  班级id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "CLASS_ID", length = 32)
    private String classId;

    /**
     * 班级名称
     */
    @Column(name = "CLASS_NAME", length = 200)
    private String className;

    /**
     * 学校id
     */
    @Column(name = "CLASS_SCHOOL", length = 32)
    private String classSchool;

    /**
     * 班主任id  老师
     */
    @Column(name = "CLASS_TEACHER", length = 32)
    private String clssTeacher;

    /**
     * 班级描述
     */
    @Column(name = "CLASS_DESC", length = 1000)
    private String classDesc;

    /**
     * 学校地址
     */
    @Column(name = "CLASS_SCHOOL_ADDRESS", length = 500)
    private String classSchoolAddress;

    /**
     * 创建时间
     */
    @Column(name = "CLASS_CREATETIME")
    private Date classCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "CLASS_UPDATETIME")
    private Date classUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "CLASS_CREATENAME", length = 100)
    private String classCreateName;

    /**
     * 修改人
     */
    @Column(name = "CLASS_UPDATENAME", length = 100)
    private String classUpdateName;

    @Column(name = "CLASS_LEVEL",length = 10)
    private String classLevel;

    @Column(name = "CLASS_DIVISION",length = 10)
    private  String classDivision;

    @Column(name = "CLASS_NUM",length = 50)
    private String classNum;

    @Column(name = "STATUS",length = 10)
    private String status;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassSchool() {
        return classSchool;
    }

    public void setClassSchool(String classSchool) {
        this.classSchool = classSchool;
    }

    public String getClssTeacher() {
        return clssTeacher;
    }

    public void setClssTeacher(String clssTeacher) {
        this.clssTeacher = clssTeacher;
    }

    public String getClassDesc() {
        return classDesc;
    }

    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

    public String getClassSchoolAddress() {
        return classSchoolAddress;
    }

    public void setClassSchoolAddress(String classSchoolAddress) {
        this.classSchoolAddress = classSchoolAddress;
    }

    public Date getClassCreateTime() {
        return classCreateTime;
    }

    public void setClassCreateTime(Date classCreateTime) {
        this.classCreateTime = classCreateTime;
    }

    public Date getClassUpdateTime() {
        return classUpdateTime;
    }

    public void setClassUpdateTime(Date classUpdateTime) {
        this.classUpdateTime = classUpdateTime;
    }

    public String getClassCreateName() {
        return classCreateName;
    }

    public void setClassCreateName(String classCreateName) {
        this.classCreateName = classCreateName;
    }

    public String getClassUpdateName() {
        return classUpdateName;
    }

    public void setClassUpdateName(String classUpdateName) {
        this.classUpdateName = classUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    public String getClassDivision() {
        return classDivision;
    }

    public void setClassDivision(String classDivision) {
        this.classDivision = classDivision;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
}
