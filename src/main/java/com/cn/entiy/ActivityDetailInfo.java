package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ACTIVITY_DETAIL_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActivityDetailInfo implements Serializable {

    /**
     * 主键  活动明细关联id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "A_DETAIL_ID", length = 32)
    private String aDetailId;

    /**
     * 活动ID
     */
    @Column(name = "ACTIVITY_ID", length = 32)
    private String activityId;

    /**
     * 活动banner图
     */
    @Column(name = "DETAIL_ID", length = 32)
    private String detailId;

    /**
     * 创建时间
     */
    @Column(name = "A_DETAIL_CREATETIME")
    private Date aDetailCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "A_DETAIL_UPDATETIME")
    private Date aDetailUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "A_DETAIL_CREATENAME", length = 100)
    private String aDetailCreateName;

    /**
     * 修改人
     */
    @Column(name = "A_DETAIL_UPDATENAME", length = 100)
    private String aDetailUpdateName;

    @Column(name = "STATUS",length = 10)
    private String status;

    public String getaDetailId() {
        return aDetailId;
    }

    public void setaDetailId(String aDetailId) {
        this.aDetailId = aDetailId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public Date getaDetailCreateTime() {
        return aDetailCreateTime;
    }

    public void setaDetailCreateTime(Date aDetailCreateTime) {
        this.aDetailCreateTime = aDetailCreateTime;
    }

    public Date getaDetailUpdateTime() {
        return aDetailUpdateTime;
    }

    public void setaDetailUpdateTime(Date aDetailUpdateTime) {
        this.aDetailUpdateTime = aDetailUpdateTime;
    }

    public String getaDetailCreateName() {
        return aDetailCreateName;
    }

    public void setaDetailCreateName(String aDetailCreateName) {
        this.aDetailCreateName = aDetailCreateName;
    }

    public String getaDetailUpdateName() {
        return aDetailUpdateName;
    }

    public void setaDetailUpdateName(String aDetailUpdateName) {
        this.aDetailUpdateName = aDetailUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
