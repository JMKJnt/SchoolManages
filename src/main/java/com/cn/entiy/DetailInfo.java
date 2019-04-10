package com.cn.entiy;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DETAIL_INFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetailInfo implements Serializable {

    /**
     * 主键  活动id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "DETAIL_ID", length = 32)
    private String detailId;

    /**
     * 细目类别  1策略 2共读 3 思想 4其他
     */
    @Column(name = "DETAIL_CATE", length = 10)
    private String detailCate;

    /*
     * 导师id
     */
    @Column(name = "DETAIL_TEACHERID", length = 32)
    private String detailTeacherId;

    /**
     * 活动banner图
     */
    @Column(name = "DETAIL_BANNER", length = 500)
    private String detailBanner;

    /**
     * 活动主题
     */
    @Column(name = "DETAIL_THEME", length = 1000)
    private String detailTheme;

    /**
     * 活动详情
     */
    @Column(name = "DETAIL_DESC", length = 4000)
    private String detailDesc;


    /**
     * 活动开始时间
     */
    @Column(name = "DETAIL_STARTTIME")
    private Date detailStartTime;

    /**
     * 活动结束时间
     */
    @Column(name = "DETAIL_ENDTIME")
    private Date detailEndTime;


    /**
     * 活动是否公开  1 公开  2 不公开
     */
    @Column(name = "DETAIL_ISPUBLIC", length = 1)
    private String detailIsPublic;

    /**
     * 直播码  url
     */
    @Column(name = "DETAIL_ZBCODE", length = 100)
    private String detailZbcode;

    /**
     * 创建时间
     */
    @Column(name = "DETAIL_CREATETIME")
    private Date detailCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "DETAIL_UPDATETIME")
    private Date detailUpdateTime;

    /**
     * 创建人
     */
    @Column(name = "DETAIL_CREATENAME", length = 100)
    private String detailCreateName;

    /**
     * 修改人
     */
    @Column(name = "DETAIL_UPDATENAME", length = 100)
    private String detailUpdateName;

    @Column(name = "STATUS",length = 10)
    private String status;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getDetailCate() {
        return detailCate;
    }

    public void setDetailCate(String detailCate) {
        this.detailCate = detailCate;
    }

    public String getDetailTeacherId() {
        return detailTeacherId;
    }

    public void setDetailTeacherId(String detailTeacherId) {
        this.detailTeacherId = detailTeacherId;
    }

    public String getDetailBanner() {
        return detailBanner;
    }

    public void setDetailBanner(String detailBanner) {
        this.detailBanner = detailBanner;
    }

    public String getDetailTheme() {
        return detailTheme;
    }

    public void setDetailTheme(String detailTheme) {
        this.detailTheme = detailTheme;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public Date getDetailStartTime() {
        return detailStartTime;
    }

    public void setDetailStartTime(Date detailStartTime) {
        this.detailStartTime = detailStartTime;
    }

    public Date getDetailEndTime() {
        return detailEndTime;
    }

    public void setDetailEndTime(Date detailEndTime) {
        this.detailEndTime = detailEndTime;
    }

    public String getDetailIsPublic() {
        return detailIsPublic;
    }

    public void setDetailIsPublic(String detailIsPublic) {
        this.detailIsPublic = detailIsPublic;
    }

    public String getDetailZbcode() {
        return detailZbcode;
    }

    public void setDetailZbcode(String detailZbcode) {
        this.detailZbcode = detailZbcode;
    }

    public Date getDetailCreateTime() {
        return detailCreateTime;
    }

    public void setDetailCreateTime(Date detailCreateTime) {
        this.detailCreateTime = detailCreateTime;
    }

    public Date getDetailUpdateTime() {
        return detailUpdateTime;
    }

    public void setDetailUpdateTime(Date detailUpdateTime) {
        this.detailUpdateTime = detailUpdateTime;
    }

    public String getDetailCreateName() {
        return detailCreateName;
    }

    public void setDetailCreateName(String detailCreateName) {
        this.detailCreateName = detailCreateName;
    }

    public String getDetailUpdateName() {
        return detailUpdateName;
    }

    public void setDetailUpdateName(String detailUpdateName) {
        this.detailUpdateName = detailUpdateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
