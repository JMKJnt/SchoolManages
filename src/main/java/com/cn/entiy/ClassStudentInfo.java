package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CLASS_STUDENT_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClassStudentInfo implements Serializable {

    /**
     * 主键   班级学生id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "CLASS_S_ID", length = 32)
    private String classSId;

    /**
     * 班级ID
     */
    @Column(name = "CLASS_ID", length = 200)
    private String classId;

    /**
     * 学生ID
     */
    @Column(name = "CLASS_STUDENT", length = 500)
    private String classStudent;


    /**
     * 创建时间
     */
    @Column(name = "CLASS_S_CREATETIME")
    private Date classCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "CLASS_S_UPDATETIME")
    private Date classUpdateTime;

    /**

     * 创建人
     */
    @Column(name = "CLASS_S_CREATENAME", length = 100)
    private String classCreateName;

    /**
     * 修改人
     */
    @Column(name = "CLASS_S_UPDATENAME", length = 100)
    private String classUpdateName;

    @Column(name = "APPROVE_STATUS",length = 10)
    private String approveStatus;

    @Column(name = "STATUS",length = 10)
    private String status;

    public String getClassSId() {
        return classSId;
    }

    public void setClassSId(String classSId) {
        this.classSId = classSId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassStudent() {
        return classStudent;
    }

    public void setClassStudent(String classStudent) {
        this.classStudent = classStudent;
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

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }
}
