package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ACTIVITY_TEACHER_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActivityTeacherInfo implements Serializable {

    /**
     * 主键  活动导师id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "A_TEACHER_ID", length = 32)
    private String aTeacherId;

    /**
     * 活动id
     */
    @Column(name = "ACTIVITY_ID", length = 32)
    private String activityId;

    /**
     * 导师id
     */
    @Column(name = "TEACHER_ID", length = 32)
    private String teacherId;


    /**
     * 创建时间
     */
    @Column(name = "A_TEACHER_CREATETIME")
    private Date aTeacherCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "A_TEACHER_UPDATETIME")
    private Date aTeacherUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "A_TEACHER_CREATENAME", length = 100)
    private String aTeacherCreateName;

    /**
     * 修改人
     */
    @Column(name = "A_TEACHER_UPDATENAME", length = 100)
    private String aTeacherUpdateName;


    public String getaTeacherId() {
        return aTeacherId;
    }

    public void setaTeacherId(String aTeacherId) {
        this.aTeacherId = aTeacherId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Date getaTeacherCreateTime() {
        return aTeacherCreateTime;
    }

    public void setaTeacherCreateTime(Date aTeacherCreateTime) {
        this.aTeacherCreateTime = aTeacherCreateTime;
    }

    public Date getaTeacherUpdateTime() {
        return aTeacherUpdateTime;
    }

    public void setaTeacherUpdateTime(Date aTeacherUpdateTime) {
        this.aTeacherUpdateTime = aTeacherUpdateTime;
    }

    public String getaTeacherCreateName() {
        return aTeacherCreateName;
    }

    public void setaTeacherCreateName(String aTeacherCreateName) {
        this.aTeacherCreateName = aTeacherCreateName;
    }

    public String getaTeacherUpdateName() {
        return aTeacherUpdateName;
    }

    public void setaTeacherUpdateName(String aTeacherUpdateName) {
        this.aTeacherUpdateName = aTeacherUpdateName;
    }
}
