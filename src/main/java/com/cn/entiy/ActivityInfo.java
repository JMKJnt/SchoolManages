package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ACTIVITY_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActivityInfo implements Serializable {

    /**
     * 主键  活动id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ACTIVITY_ID", length = 32)
    private String activityId;

    /**
     * 活动类型
     */
    @Column(name = "ACTIVITY_TYPE", length = 10)
    private String activityType;
    /**
     * 活动类别
     */
    @Column(name = "ACTIVITY_CATE", length = 10)
    private String activityCate;

    /**
     * 活动banner图
     */
    @Column(name = "ACTIVITY_BANNER", length = 500)
    private String activityBanner;

    /**
     * 活动名称
     */
    @Column(name = "ACTIVITY_NAME", length = 500)
    private String activityName;

    /**
     * 活动主题
     */
    @Column(name = "ACTIVITY_THEME", length = 1000)
    private String activityTheme;

    /**
     * 活动详情
     */
    @Column(name = "ACTIVITY_DETAIL", length = 4000)
    private String activityDetail;

    /**
     * 活动适用角色
     */
    @Column(name = "ACTIVITY_APPLICABLE_ROLE", length = 10)
    private String activityApplicableRole;

    /**
     * 适用年级 多选 1,2
     */
    @Column(name = "ACTIVITY_CLASS_NUM", length = 10)
    private String activityClassNum;

    /**
     * 报名开始时间
     */
    @Column(name = "ACTIVITY_SGIN_STARTTIME")
    private Date activitySginStartTime;

    /**
     * 报名结束时间
     */
    @Column(name = "ACTIVITY_SGIN_ENDTIME")
    private Date activitySginEndTime;

    /**
     * 活动开始时间
     */
    @Column(name = "ACTIVITY_STARTTIME")
    private Date activityStartTime;

    /**
     * 活动结束时间
     */
    @Column(name = "ACTIVITY_ENDTIME")
    private Date activityEndTime;

    /**
     * 活动状态 1 已创建 2 已发布 3 终止
     */
    @Column(name = "ACTIVITY_STATUS", length = 10)
    private String activityStatus;


    /**
     * 活动是否需要审核
     */
    @Column(name = "ACTIVITY_ISAPPROVE", length = 1)
    private String activityIsApprove;


    /**
     * 活动是否需要填写问卷
     */
    @Column(name = "ACTIVITY_ISQUESTION", length = 1)
    private String activityIsQuestion;

    /**
     * 问卷ID
     */
    @Column(name = "ACTIVITY_QUESTION", length = 1)
    private String activityQuestion;

    /**
     * 直播码
     */
    @Column(name = "ACTIVITY_ZBCODE", length = 100)
    private String activityZbcode;
    /**
     * 成功模板
     */
    @Column(name = "ACTIVITY_TEMPLATE", length = 4000)
    private String activityTemplate;

    /**
     * 创建时间
     */
    @Column(name = "ACTIVITY_CREATETIME")
    private Date activityCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "ACTIVITY_UPDATETIME")
    private Date activityUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "ACTIVITY_CREATENAME", length = 100)
    private String activityCreateName;

    @Column(name = "ACTIVITY_NUMCY",length = 100)
    private String activityNumCy;

    @Column(name = "ACTIVITY_CODE",length = 50)
    private String activityCode;
    /**
     * 修改人
     */
    @Column(name = "ACTIVITY_UPDATENAME", length = 100)
    private String activityUpdateName;

    @Column(name = "STATUS",length = 10)
    private String status;
    @Column(name = "ACTIVITY_SEQ",length = 10)
    private String activeSeq;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityBanner() {
        return activityBanner;
    }

    public void setActivityBanner(String activityBanner) {
        this.activityBanner = activityBanner;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityTheme() {
        return activityTheme;
    }

    public void setActivityTheme(String activityTheme) {
        this.activityTheme = activityTheme;
    }

    public String getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }

    public String getActivityApplicableRole() {
        return activityApplicableRole;
    }

    public void setActivityApplicableRole(String activityApplicableRole) {
        this.activityApplicableRole = activityApplicableRole;
    }

    public Date getActivitySginStartTime() {
        return activitySginStartTime;
    }

    public void setActivitySginStartTime(Date activitySginStartTime) {
        this.activitySginStartTime = activitySginStartTime;
    }

    public Date getActivitySginEndTime() {
        return activitySginEndTime;
    }

    public void setActivitySginEndTime(Date activitySginEndTime) {
        this.activitySginEndTime = activitySginEndTime;
    }

    public Date getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Date activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public Date getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Date activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getActivityIsApprove() {
        return activityIsApprove;
    }

    public void setActivityIsApprove(String activityIsApprove) {
        this.activityIsApprove = activityIsApprove;
    }

    public Date getActivityCreateTime() {
        return activityCreateTime;
    }

    public void setActivityCreateTime(Date activityCreateTime) {
        this.activityCreateTime = activityCreateTime;
    }

    public Date getActivityUpdateTime() {
        return activityUpdateTime;
    }

    public void setActivityUpdateTime(Date activityUpdateTime) {
        this.activityUpdateTime = activityUpdateTime;
    }

    public String getActivityCreateName() {
        return activityCreateName;
    }

    public void setActivityCreateName(String activityCreateName) {
        this.activityCreateName = activityCreateName;
    }

    public String getActivityUpdateName() {
        return activityUpdateName;
    }

    public void setActivityUpdateName(String activityUpdateName) {
        this.activityUpdateName = activityUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivityIsQuestion() {
        return activityIsQuestion;
    }

    public void setActivityIsQuestion(String activityIsQuestion) {
        this.activityIsQuestion = activityIsQuestion;
    }

    public String getActivityQuestion() {
        return activityQuestion;
    }

    public void setActivityQuestion(String activityQuestion) {
        this.activityQuestion = activityQuestion;
    }

    public String getActivityZbcode() {
        return activityZbcode;
    }

    public void setActivityZbcode(String activityZbcode) {
        this.activityZbcode = activityZbcode;
    }

    public String getActiveSeq() {
        return activeSeq;
    }

    public void setActiveSeq(String activeSeq) {
        this.activeSeq = activeSeq;
    }

    public String getActivityNumCy() {
        return activityNumCy;
    }

    public void setActivityNumCy(String activityNumCy) {
        this.activityNumCy = activityNumCy;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityCate() {
        return activityCate;
    }

    public void setActivityCate(String activityCate) {
        this.activityCate = activityCate;
    }

    public String getActivityClassNum() {
        return activityClassNum;
    }

    public void setActivityClassNum(String activityClassNum) {
        this.activityClassNum = activityClassNum;
    }

    public String getActivityTemplate() {
        return activityTemplate;
    }

    public void setActivityTemplate(String activityTemplate) {
        this.activityTemplate = activityTemplate;
    }

}
