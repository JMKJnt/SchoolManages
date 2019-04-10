package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "QUESTION_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionInfo implements Serializable {

    /**
     * 主键  活动id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "QUESTION_ID", length = 32)
    private String questionId;

    /**
     * 活动类型
     */
    @Column(name = "QUESTION_ISACTIVE", length = 10)
    private String questionIsActive;

    /**
     * 活动banner图
     */
    @Column(name = "QUESTION_ACTIVENUM", length = 10)
    private String questionActiveNum;

    /**
     * 活动banner图
     */
    @Column(name = "QUESTION_USER_ID", length = 32)
    private String questionUserId;

    /**
     * 活动banner图
     */
    @Column(name = "QUESTION_ACTIVEID", length = 32)
    private String questionActiveId;

    /**
     * 创建时间
     */
    @Column(name = "QUESTION_CREATETIME")
    private Date questionCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "QUESTION_UPDATETIME")
    private Date questionUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "QUESTION_CREATENAME", length = 100)
    private String questionCreateName;
    /**
     * 修改人
     */
    @Column(name = "QUESTION_UPDATENAME", length = 100)
    private String questionUpdateName;

    @Column(name = "STATUS",length = 10)
    private String status;


    public String getQuestionActiveId() {
        return questionActiveId;
    }

    public void setQuestionActiveId(String questionActiveId) {
        this.questionActiveId = questionActiveId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionIsActive() {
        return questionIsActive;
    }

    public void setQuestionIsActive(String questionIsActive) {
        this.questionIsActive = questionIsActive;
    }

    public String getQuestionActiveNum() {
        return questionActiveNum;
    }

    public void setQuestionActiveNum(String questionActiveNum) {
        this.questionActiveNum = questionActiveNum;
    }

    public String getQuestionUserId() {
        return questionUserId;
    }

    public void setQuestionUserId(String questionUserId) {
        this.questionUserId = questionUserId;
    }

    public Date getQuestionCreateTime() {
        return questionCreateTime;
    }

    public void setQuestionCreateTime(Date questionCreateTime) {
        this.questionCreateTime = questionCreateTime;
    }

    public Date getQuestionUpdateTime() {
        return questionUpdateTime;
    }

    public void setQuestionUpdateTime(Date questionUpdateTime) {
        this.questionUpdateTime = questionUpdateTime;
    }

    public String getQuestionCreateName() {
        return questionCreateName;
    }

    public void setQuestionCreateName(String questionCreateName) {
        this.questionCreateName = questionCreateName;
    }

    public String getQuestionUpdateName() {
        return questionUpdateName;
    }

    public void setQuestionUpdateName(String questionUpdateName) {
        this.questionUpdateName = questionUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
