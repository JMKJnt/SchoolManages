package com.cn.service.busImpl;

import com.cn.common.CommonHelper;
import com.cn.common.MsgAndCode;
import com.cn.common.Utils;
import com.cn.dao.interfaceDao.ActiveInfoDao;
import com.cn.dao.interfaceDao.BaseEntityDao;
import com.cn.entiy.*;
import com.cn.service.busService.ActiveInfoService;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class ActiveInfoServiceImpl implements ActiveInfoService {
    Logger logger = Logger.getLogger(ActiveInfoServiceImpl.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private BaseEntityDao baseEntityDao;

    @Autowired
    private ActiveInfoDao activeInfoDao;

    /**
     * 新增活动
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertActive(String jsonstr) {
        logger.info("新增活动接口信息insertActive--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
//        String activeType = jo.getString("activeType");//活动类型 （1 线上 2 线下）
        String activeName = jo.getString("activeName");//活动名称
        String activeBanner = jo.has("activeBanner") ? jo.getString("activeBanner") : "";//活动banner图
        String activeTheme = jo.getString("activeTheme");//活动主题
        String activeDetail = jo.getString("activeDetail");//活动详情
//        String activeApplicableRole = jo.getString("activeApplicableRole");//活动适用角色 （1,2,3）
        String activeClassNum = jo.getString("activeClassNum");//年级
        String signStartTime = jo.getString("signStartTime");//报名开始
        String signEndTime = jo.getString("signEndTime");//报名结束
        String activeCate = jo.getString("activeCate");//类别  单选
//        String activeStartTime = jo.getString("activeStartTime");//活动开始
//        String activeEndTime = jo.getString("activeEndTime");//活动结束
        String activeStatus = "1";//活动状态 默认已创建 1 已创建 2 已发布 3 终止
        String activeIsApprove = jo.getString("activeIsApprove");//是否需要审核 1 是 2 否
        String activeIsQuestion = jo.getString("activeIsQuestion");//是否需要填写问卷 1是 2 否
        String activeQuestion = jo.has("activeQuestion") ? jo.getString("activeQuestion") : "";//问卷ID
        String activeCreate = jo.getString("activeCreate");//创建人
        String activeSeq = jo.getString("activeSeq");//顺序
        String activeNumCy = jo.getString("activeNumCy");//默认人气
//        JSONArray teacherList = new JSONArray(jo.get("activeTeaList").toString());//活动导师id
        String status = jo.getString("status");
//        String zb_code = jo.has("Zb_code") ? jo.getString("Zb_code") : "";
        String activityTemplate = jo.has("activityTemplate") ? jo.getString("activityTemplate") : "";
        String activeHeadStr = jo.has("activeHeadStr") ? jo.getString("activeHeadStr") : "";
        JSONArray detailList = jo.getJSONArray("detailList");//活动细目
        //新增活动信息，再新增活动参与导师信息
        if (Utils.isEmpty(activeCate) || Utils.isEmpty(activeName) || Utils.isEmpty(activeTheme) ||
                Utils.isEmpty(activeDetail) || Utils.isEmpty(activeClassNum) || Utils.isEmpty(signStartTime)
                || Utils.isEmpty(signEndTime) || Utils.isEmpty(activeIsApprove) || Utils.isEmpty(activeCreate)
                || Utils.isEmpty(status) || Utils.isEmpty(activeIsQuestion) || Utils.isEmpty(activeSeq)
                || Utils.isEmpty(activeNumCy) || detailList == null || detailList.length() <= 0) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存活动信息
                ActivityInfo activityInfo = new ActivityInfo();
                activityInfo.setActivityName(activeName);
                activityInfo.setActivityBanner(activeBanner);
                activityInfo.setActivityCate(activeCate);
                activityInfo.setActivityTheme(activeTheme);
                activityInfo.setActivityDetail(activeDetail);
                activityInfo.setActivityClassNum(activeClassNum);

                activityInfo.setActivitySginStartTime(sdf.parse(signStartTime));
                activityInfo.setActivitySginEndTime(sdf.parse(signEndTime));
                //如果需要审核则默认已创建 否则默认已发布
                activityInfo.setActivityStatus(activeIsApprove.equals("1") ? "1" : "2");//默认状态为已创建
                activityInfo.setActivityIsApprove(activeIsApprove);
                activityInfo.setActivityIsQuestion(activeIsQuestion);
                activityInfo.setActivityQuestion(activeQuestion);
                activityInfo.setActivityCreateName(activeCreate);
                activityInfo.setActivityCreateTime(new Date());
                activityInfo.setActivityUpdateName(activeCreate);
                activityInfo.setActivityUpdateTime(new Date());
                activityInfo.setActivityTemplate(activityTemplate);
//                activityInfo.setActivityZbcode(zb_code);
                activityInfo.setActiveSeq(activeSeq);
                activityInfo.setActivityNumCy(activeNumCy);//默认人气
                //获取活动编号
                activityInfo.setActivityCode(CommonHelper.getRandomStr(Utils.isEmpty(activeHeadStr) ? "HD" : activeHeadStr, 6));
                activityInfo.setStatus(status);
                String resultStr = baseEntityDao.saveOrUpdate(activityInfo);
                if (resultStr.equals("success")) {
                    //保存成功，则保存活动细目
                    if (detailList != null && detailList.length() > 0) {
                        ActivityDetailInfo activityDetailInfo = null;
                        JSONObject obj = null;
                        for (int i = 0; i < detailList.length(); i++) {
                            obj = new JSONObject(detailList.get(i).toString());
                            activityDetailInfo = new ActivityDetailInfo();
                            activityDetailInfo.setDetailId(obj.get("detailId").toString());
                            activityDetailInfo.setActivityId(activityInfo.getActivityId());
                            baseEntityDao.saveOrUpdate(activityDetailInfo);
                        }
                    }
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                } else {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                }

            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 修改活动
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateActive(String jsonstr) {
        logger.info("修改活动接口信息updateActive--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String activeId = jo.getString("activeId");
//        String activeType = jo.getString("activeType");//活动类型 （1 线上 2 线下）
        String activeCate = jo.getString("activeCate");//类别  单选
        String activeName = jo.getString("activeName");//活动名称
        String activeBanner = jo.has("activeBanner") ? jo.getString("activeBanner") : "";//活动banner图
        String activeTheme = jo.getString("activeTheme");//活动主题
        String activeDetail = jo.getString("activeDetail");//活动详情
//        String activeApplicableRole = jo.getString("activeApplicableRole");//活动适用角色 （1,2,3）
        String signStartTime = jo.getString("signStartTime");//报名开始
        String signEndTime = jo.getString("signEndTime");//报名结束
        String activeClassNum = jo.getString("activeClassNum");//年级
//        String activeStartTime = jo.getString("activeStartTime");//活动开始
//        String activeEndTime = jo.getString("activeEndTime");//活动结束
//        String activeStatus = jo.getString("activeStatus");//活动状态 默认已创建 1 已创建 2 已发布 3 终止
        String activeIsApprove = jo.getString("activeIsApprove");//是否需要审核 1 是 2 否
        String activeIsQuestion = jo.getString("activeIsQuestion");//是否需要填写问卷 1是 2 否
        String activeQuestion = jo.has("activeQuestion") ? jo.getString("activeQuestion") : "";//问卷ID
        String activeUpdate = jo.getString("activeUpdate");//创建人
//        JSONArray teacherList = new JSONArray(jo.get("activeTeaList").toString());//活动导师id
//        String zb_code = jo.has("Zb_code") ? jo.getString("Zb_code") : "";
        String activeSeq = jo.getString("activeSeq");//顺序
        String activeNumCy = jo.getString("activeNumCy");//默认人气
        String status = jo.getString("status");
        JSONArray detailList = jo.getJSONArray("detailList");//活动细目
        String activityTemplate = jo.has("activityTemplate") ? jo.getString("activityTemplate") : "";
        //新增活动信息，再新增活动参与导师信息
        if (Utils.isEmpty(activeCate) || Utils.isEmpty(activeName) || Utils.isEmpty(activeTheme) ||
                Utils.isEmpty(activeDetail) || Utils.isEmpty(signStartTime) || Utils.isEmpty(signEndTime)
                || Utils.isEmpty(activeIsApprove) || Utils.isEmpty(activeUpdate) || Utils.isEmpty(activeId)
                || Utils.isEmpty(status) || Utils.isEmpty(activeIsQuestion) || Utils.isEmpty(activeNumCy)
                || detailList == null || detailList.length() <= 0 || Utils.isEmpty(activeClassNum)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {

                //保存活动信息
                ActivityInfo activityInfo = baseEntityDao.listById(ActivityInfo.class, activeId, false);
                activityInfo = activityInfo == null ? new ActivityInfo() : activityInfo;
                activityInfo.setActivityName(activeName);
                activityInfo.setActivityBanner(activeBanner);
                activityInfo.setActivityCate(activeCate);
                activityInfo.setActivityTheme(activeTheme);
                activityInfo.setActivityDetail(activeDetail);
                activityInfo.setActivityClassNum(activeClassNum);
//                activityInfo.setActivityStartTime(sdf.parse(activeStartTime));
//                activityInfo.setActivityEndTime(sdf.parse(activeEndTime));
                activityInfo.setActivitySginStartTime(sdf.parse(signStartTime));
                activityInfo.setActivitySginEndTime(sdf.parse(signEndTime));
//                    //如果需要审核则默认已创建 否则默认已发布
                activityInfo.setActivityStatus(activeIsApprove.equals("1") ? "1" : "2");//默认状态为已创建
                activityInfo.setActivityIsApprove(activeIsApprove);
                activityInfo.setActivityIsQuestion(activeIsQuestion);
                activityInfo.setActivityQuestion(activeQuestion);
                activityInfo.setActivityUpdateName(activeUpdate);
                activityInfo.setActivityUpdateTime(new Date());
//                activityInfo.setActivityZbcode(zb_code);
                activityInfo.setActivityNumCy(activeNumCy);//默认人气
                activityInfo.setActiveSeq(activeSeq);
                activityInfo.setStatus(status);
                activityInfo.setActivityTemplate(activityTemplate);
                String resultStr = baseEntityDao.saveOrUpdate(activityInfo);
                if (resultStr.equals("success")) {
                    //阐述原有参与活动细目
                    activeInfoDao.deleteInfoByAId("ActivityDetailInfo",
                            "activityId", activityInfo.getActivityId());
                    //保存成功，则保存参与活动细目
                    if (detailList != null && detailList.length() > 0) {
                        ActivityDetailInfo activityDetailInfo = null;
                        JSONObject obj = null;
                        for (int i = 0; i < detailList.length(); i++) {
                            obj = new JSONObject(detailList.get(i).toString());
                            activityDetailInfo = new ActivityDetailInfo();
                            activityDetailInfo.setDetailId(obj.get("detailId").toString());
                            activityDetailInfo.setActivityId(activityInfo.getActivityId());
                            baseEntityDao.saveOrUpdate(activityDetailInfo);
                        }
                    }
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                } else {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                }

            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取活动信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getActiveInfoById(String jsonstr) {
        logger.info("获取活动信息接口信息getActiveInfoById--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String activeId = jo.getString("activeId");
        //
        if (Utils.isEmpty(activeId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存活动信息
                ActivityInfo activityInfo = baseEntityDao.listById(ActivityInfo.class, activeId, false);
                if (activityInfo == null) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_004_MSG);
                } else {
                    result.put("activeId", activityInfo.getActivityId());
                    result.put("activeNum", activityInfo.getActivityCode());
                    result.put("activeName", activityInfo.getActivityName());
                    result.put("activeBanner", activityInfo.getActivityBanner());
//                    result.put("activeType", activityInfo.getActivityType());
                    result.put("activeCate", activityInfo.getActivityCate());
                    result.put("activeTheme", activityInfo.getActivityTheme());
                    result.put("activeDetail", activityInfo.getActivityDetail());
                    result.put("activeClassNum", activityInfo.getActivityClassNum());
//                    result.put("activeApplicableRole", activityInfo.getActivityApplicableRole());
//                    result.put("activeStartTime", sdf.format(activityInfo.getActivityStartTime()));
//                    result.put("activeEndTime", sdf.format(activityInfo.getActivityEndTime()));
                    result.put("signStartTime", sdf.format(activityInfo.getActivitySginStartTime()));
                    result.put("signEndTime", sdf.format(activityInfo.getActivitySginEndTime()));
                    result.put("activeStatus", activityInfo.getActivityStatus());
                    result.put("activeIsApprove", activityInfo.getActivityIsApprove());
                    result.put("activeIsQuestion", activityInfo.getActivityIsQuestion());
                    result.put("activeQuestion", activityInfo.getActivityQuestion());
                    result.put("activeCreate", activityInfo.getActivityCreateName());
                    result.put("activeUpdate", activityInfo.getActivityUpdateName());
                    result.put("status", activityInfo.getStatus());
//                    result.put("Zb_code", activityInfo.getActivityZbcode());
                    result.put("activeSeq", activityInfo.getActiveSeq());
                    result.put("activeNumCy", activityInfo.getActivityNumCy());//默认人气
                    //根据活动id查询活动细目信息
                    Conjunction cn = Restrictions.conjunction();
                    cn.add(Restrictions.eq("activityId", activityInfo.getActivityId()));
                    List<ActivityDetailInfo> activityDetailInfoList = baseEntityDao.listByCriteria(ActivityDetailInfo.class, cn, false);
                    JSONArray array = new JSONArray();
                    if (activityDetailInfoList != null && activityDetailInfoList.size() > 0) {
                        JSONObject jobs = null;
                        for (ActivityDetailInfo activityDetailInfo : activityDetailInfoList) {
                            jobs = new JSONObject();
                            jobs.put("detailId", activityDetailInfo.getDetailId());

                            array.put(jobs);
                        }
                    }

//                    StringBuffer sql = new StringBuffer("SELECT A.TEACHER_ID,F.TEACHER_NAME,F.TEACHER_IMG,F.TEACHER_QUALIFICATION  FROM  ACTIVITY_TEACHER_INFO A LEFT JOIN  FAMOUS_TEACHER_INFO F ");
//                    sql.append("ON F.TEACHER_ID=A.TEACHER_ID WHERE A.ACTIVITY_ID ='" + activityInfo.getActivityId() + "' ");
//                    List<Map<String, Object>> list = baseEntityDao.listBySQL(sql.toString(), false);
//                    JSONArray array = new JSONArray();
//                    if (list != null && list.size() > 0) {
//                        JSONObject obj = null;
//                        for (int i = 0; i < list.size(); i++) {
//                            obj = new JSONObject();
//                            Map<String, Object> map = list.get(i);
//                            obj.put("activeTeaId", map.get("TEACHER_ID").toString());
//                            obj.put("activeTeaName", map.get("TEACHER_NAME").toString());
//                            obj.put("activeTeaImg", map.get("TEACHER_IMG") == null ? "" : map.get("TEACHER_IMG").toString());
//                            obj.put("activeTeaDesc", map.get("TEACHER_QUALIFICATION") == null ? "" : map.get("TEACHER_QUALIFICATION").toString());
//                            array.put(obj);
//                        }
//                    }
//                    result.put("activeTeaList", array);
                    result.put("detailList", array);
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                }

            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取活动列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getActiveInfoList(String jsonstr) {
        logger.info("获取活动列表接口信息getActiveInfoList--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
//        String activeType = jo.has("activeType") ? jo.getString("activeType") : "";//活动类型 （"" 全部 1 线上 2 线下）
        String activeCate = jo.has("activeCate") ? jo.getString("activeCate") : "";//活动类型 （"" 全部 1 线上 2 线下）
        String activeName = jo.has("activeName") ? jo.getString("activeName") : "";//活动名称
        String activeTheme = jo.has("activeTheme") ? jo.getString("activeTheme") : "";//活动主题
//        String activeApplicableRole = jo.has("activeApplicableRole") ? jo.getString("activeApplicableRole") : "";//活动适用角色 （1,2,3）
        String activeClassNum = jo.has("activeClassNum") ? jo.getString("activeClassNum") : "";//活动适用年级 （1,2,3）
        String signStartTime = jo.has("signStartTime") ? jo.getString("signStartTime") : "";//报名开始
        String signEndTime = jo.has("signEndTime") ? jo.getString("signEndTime") : "";//报名结束
//        String activeStartTime = jo.has("activeStartTime") ? jo.getString("activeStartTime") : "";//活动开始
//        String activeEndTime = jo.has("activeEndTime") ? jo.getString("activeEndTime") : "";//活动结束
        String activeStatus = jo.has("activeStatus") ? jo.getString("activeStatus") : "";//活动状态 默认已创建 1 已创建 2 已发布 3 终止
        String isNumCy = jo.has("isNumCy") ? jo.getString("isNumCy") : "";//人气类型  2 人气升序 1 默认 顺序 3 报名时间降序
        String registerStatus = jo.has("registerStatus") ? jo.getString("registerStatus") : "";//注册时间状态 1未开始，2 进行中，3已截止
        String curragePage = jo.getString("curragePage");//当前页
        String pageSize = jo.getString("pageSize");//每页记录数
        if (Utils.isEmpty(curragePage) || Utils.isEmpty(pageSize)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //根据条件查询列表
                StringBuffer strCount = new StringBuffer("SELECT COUNT(1) SUM FROM ACTIVITY_INFO A WHERE 1=1");
                StringBuffer strCommon = new StringBuffer();
//                if (!activeType.equals("")) {
//                    strCommon.append(" AND A.ACTIVITY_TYPE='" + activeType + "'");
//                }
                if (!activeCate.equals("")) {
                    strCommon.append(" AND A.ACTIVITY_CATE='" + activeCate + "'");
                }
                if (!activeStatus.equals("")) {
                    strCommon.append(" AND A.ACTIVITY_STATUS='" + activeStatus + "'");
                }
                if (!activeName.equals("")) {
                    strCommon.append(" AND A.ACTIVITY_NAME LIKE '%" + activeName + "%'");
                }
                if (!activeTheme.equals("")) {
                    strCommon.append(" AND A.ACTIVITY_THEME LIKE '%" + activeTheme + "%'");
                }
//                if (!activeApplicableRole.equals("") && !activeApplicableRole.equals("0")) {
//                    strCommon.append(" AND A.ACTIVITY_APPLICABLE_ROLE LIKE '%" + activeApplicableRole + "%'");
//                }
                if (!activeClassNum.equals("") && !activeClassNum.equals("0")) {
                    strCommon.append(" AND A.ACTIVITY_CLASS_NUM LIKE '%" + activeClassNum + "%'");
                }

                if (!signStartTime.equals("")) {
                    strCommon.append(" AND date_format(A.ACTIVITY_SGIN_STARTTIME,'%Y-%m-%d') <= '" + signStartTime + "'");
                }
                if (!signEndTime.equals("")) {
                    strCommon.append(" AND date_format(A.ACTIVITY_SGIN_ENDTIME,'%Y-%m-%d') <= '" + signEndTime + "'");
                }
//                if (!activeStartTime.equals("")) {
//                    strCommon.append(" AND date_format(A.ACTIVITY_STARTTIME,'%Y-%m-%d') <= '" + activeStartTime + "'");
//                }
//                if (!activeEndTime.equals("")) {
//                    strCommon.append(" AND date_format(A.ACTIVITY_ENDTIME,'%Y-%m-%d') <= '" + activeEndTime + "'");
//                }
                if (registerStatus.equals("1")) {
                    //未开始，报名开始日期大于今天
                    strCommon.append(" AND date_format(A.ACTIVITY_SGIN_STARTTIME,'%Y-%m-%d') > '" + sdfDate.format(new Date()) + "'");
                } else if (registerStatus.equals("2")) {
                    //进行中
                    strCommon.append(" AND date_format(A.ACTIVITY_SGIN_STARTTIME,'%Y-%m-%d') <= '" + sdfDate.format(new Date()) + "'");
                    strCommon.append(" AND date_format(A.ACTIVITY_SGIN_ENDTIME,'%Y-%m-%d') >= '" + sdfDate.format(new Date()) + "'");
                } else if (registerStatus.equals("3")) {
                    //已截止
                    strCommon.append(" AND date_format(A.ACTIVITY_SGIN_ENDTIME,'%Y-%m-%d') < '" + sdfDate.format(new Date()) + "'");
                }

                strCount.append(strCommon.toString());
                int total = baseEntityDao.CountBySQL(strCount.toString());

                StringBuffer sql = new StringBuffer("SELECT A.ACTIVITY_ID,A.ACTIVITY_NAME,A.ACTIVITY_BANNER,A.ACTIVITY_CATE,A.ACTIVITY_THEME,");
                sql.append(" A.ACTIVITY_DETAIL,A.ACTIVITY_CODE ,A.ACTIVITY_CLASS_NUM,A.ACTIVITY_STARTTIME,A.ACTIVITY_ENDTIME,A.ACTIVITY_SGIN_STARTTIME,");
                sql.append(" A.ACTIVITY_SGIN_ENDTIME,A.ACTIVITY_STATUS,A.ACTIVITY_ISAPPROVE,A.ACTIVITY_ISQUESTION,A.ACTIVITY_CREATENAME,");
                sql.append(" A.ACTIVITY_UPDATENAME,A.STATUS,A.ACTIVITY_NUMCY,A.ACTIVITY_SEQ FROM ACTIVITY_INFO A WHERE 1=1");
                sql.append(strCommon.toString());
                //是否人气排序 2 人气倒序 1 默认 顺序 3 报名时间降序
                if (isNumCy.equals("1")) {
                    sql.append(" ORDER BY A.ACTIVITY_SEQ  ");
                } else if (isNumCy.equals("2")) {
                    //倒序
                    sql.append(" ORDER BY A.ACTIVITY_NUMCY DESC");
                } else if (isNumCy.equals("3")) {
                    sql.append(" ORDER BY A.ACTIVITY_SGIN_STARTTIME DESC ");
                }
                logger.info("sql=" + sql.toString());
                List<Map<String, Object>> activityInfoList = baseEntityDao.listByPageBySQL(sql.toString(), Integer.parseInt(pageSize), Integer.parseInt(curragePage) - 1, false);
                JSONArray array = new JSONArray();
                if (activityInfoList != null && activityInfoList.size() > 0) {
                    JSONObject job = null;
                    for (int i = 0; i < activityInfoList.size(); i++) {
                        job = new JSONObject();
                        Map<String, Object> map = activityInfoList.get(i);
                        job.put("activeId", map.get("ACTIVITY_ID").toString());
                        job.put("activeNum", map.get("ACTIVITY_CODE") == null ? "" : map.get("ACTIVITY_CODE").toString());
                        job.put("activeName", map.get("ACTIVITY_NAME").toString());
                        job.put("activeBanner", map.get("ACTIVITY_BANNER").toString());
                        job.put("activeCate", map.get("ACTIVITY_CATE").toString());
                        job.put("activeTheme", map.get("ACTIVITY_THEME").toString());
                        job.put("activeDetail", map.get("ACTIVITY_DETAIL").toString());
                        job.put("activeClassNum", map.get("ACTIVITY_CLASS_NUM").toString());
//                        job.put("activeStartTime", map.get("ACTIVITY_STARTTIME").toString());
//                        job.put("activeEndTime", map.get("ACTIVITY_ENDTIME").toString());
                        job.put("signStartTime", map.get("ACTIVITY_SGIN_STARTTIME").toString());
                        job.put("signEndTime", map.get("ACTIVITY_SGIN_ENDTIME").toString());
                        job.put("activeStatus", map.get("ACTIVITY_STATUS").toString());
                        job.put("activeIsApprove", map.get("ACTIVITY_ISAPPROVE").toString());
                        job.put("activeCreate", map.get("ACTIVITY_CREATENAME").toString());
                        job.put("activeUpdate", map.get("ACTIVITY_UPDATENAME").toString());
                        job.put("status", map.get("STATUS").toString());

//                        //根据活动id查询活动导师信息 ，导师id及导师名称
//                        StringBuffer sql1 = new StringBuffer("SELECT A.TEACHER_ID,F.TEACHER_NAME,F.TEACHER_IMG, F.TEACHER_QUALIFICATION,F.TEACHER_PHONE FROM  ACTIVITY_TEACHER_INFO A  LEFT ");
//                        sql1.append(" JOIN  FAMOUS_TEACHER_INFO F  ON F.TEACHER_ID=A.TEACHER_ID WHERE A.ACTIVITY_ID ='" + map.get("ACTIVITY_ID").toString() + "' ");
//                        List<Map<String, Object>> list = baseEntityDao.listBySQL(sql1.toString(), false);
//                        JSONArray arrayT = new JSONArray();
//                        if (list != null && list.size() > 0) {
//                            JSONObject obj = null;
//                            for (int ii = 0; ii < list.size(); ii++) {
//                                obj = new JSONObject();
//                                Map<String, Object> map1 = list.get(ii);
//                                obj.put("activeTeaId", map1.get("TEACHER_ID").toString());
//                                obj.put("activeTeaName", map1.get("TEACHER_NAME").toString());
//                                obj.put("teacherImg", map1.get("TEACHER_IMG") == null ? "" : map1.get("TEACHER_IMG").toString());
//                                obj.put("teacherQualification", map1.get("TEACHER_QUALIFICATION") == null ? "" : map1.get("TEACHER_QUALIFICATION").toString());
//                                obj.put("teacherPhone", map1.get("TEACHER_PHONE").toString());
//                                arrayT.put(obj);
//                            }
//                        }
//                        job.put("activeTeaList", arrayT);
                        array.put(job);

                    }
                }
                result.put("lists", array);
                result.put("curragePage", curragePage);
                result.put("pageSize", pageSize);
                result.put("total", total);
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);

            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取活动列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getActiveClientList(String jsonstr) {
        logger.info("获取活动列表接口信息getActiveInfoList--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String activeCate = jo.has("activeCate") ? jo.getString("activeCate") : "";//活动类型 （"" 全部 1 线上 2 线下）
        String activeClassNum = jo.has("activeClassNum") ? jo.getString("activeClassNum") : "";//活动适用年级 （1,2,3）
        String activeStatus = jo.has("activeStatus") ? jo.getString("activeStatus") : "";//活动状态 默认已创建 1 已创建 2 已发布 3 终止
        String teacherId = jo.has("teacherId") ? jo.getString("teacherId") : "";//参与导师
        String curragePage = jo.getString("curragePage");//当前页
        String pageSize = jo.getString("pageSize");//每页记录数
        if (Utils.isEmpty(curragePage) || Utils.isEmpty(pageSize)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //根据条件查询列表
                StringBuffer strCount = new StringBuffer("SELECT COUNT(1) SUM FROM ACTIVITY_INFO A  WHERE 1=1");
                StringBuffer strCommon = new StringBuffer();

                if (!activeCate.equals("")) {
                    strCommon.append(" AND A.ACTIVITY_CATE='" + activeCate + "'");
                }
                if (!activeStatus.equals("")) {
                    strCommon.append(" AND A.ACTIVITY_STATUS='" + activeStatus + "'");
                }
                if (!activeClassNum.equals("") && !activeClassNum.equals("0")) {
                    strCommon.append(" AND A.ACTIVITY_CLASS_NUM LIKE '%" + activeClassNum + "%'");
                }
                if (!teacherId.equals("")) {
                    strCommon.append(" AND EXISTS (SELECT 'X' FROM ACTIVITY_DETAIL_INFO AD LEFT JOIN DETAIL_INFO D ON D.DETAIL_ID = AD.DETAIL_ID ");
                    strCommon.append(" WHERE AD.ACTIVITY_ID = A.ACTIVITY_ID AND D.DETAIL_TEACHERID ='" + teacherId + "') ");
                }

                strCount.append(strCommon.toString());
                int total = baseEntityDao.CountBySQL(strCount.toString());

                StringBuffer sql = new StringBuffer("SELECT A.ACTIVITY_ID,A.ACTIVITY_NAME,A.ACTIVITY_BANNER,A.ACTIVITY_CATE,A.ACTIVITY_THEME,");
                sql.append("A.ACTIVITY_CLASS_NUM,A.ACTIVITY_SGIN_STARTTIME, A.ACTIVITY_SGIN_ENDTIME,A.ACTIVITY_STATUS,");
                sql.append("A.STATUS FROM ACTIVITY_INFO A  WHERE 1=1 ");
                sql.append(strCommon.toString());

                logger.info("sql=" + sql.toString());
                List<Map<String, Object>> activityInfoList = baseEntityDao.listByPageBySQL(sql.toString(), Integer.parseInt(pageSize), Integer.parseInt(curragePage) - 1, false);
                JSONArray array = new JSONArray();
                if (activityInfoList != null && activityInfoList.size() > 0) {
                    JSONObject job = null;
                    for (int i = 0; i < activityInfoList.size(); i++) {
                        job = new JSONObject();
                        Map<String, Object> map = activityInfoList.get(i);
                        job.put("activeId", map.get("ACTIVITY_ID").toString());
                        job.put("activeName", map.get("ACTIVITY_NAME").toString());
                        job.put("activeBanner", map.get("ACTIVITY_BANNER").toString());
                        job.put("activeCate", map.get("ACTIVITY_CATE").toString());
                        job.put("activeClassNum", map.get("ACTIVITY_CLASS_NUM").toString());
                        job.put("activeTheme", map.get("ACTIVITY_THEME").toString());
                        job.put("signStartTime", map.get("ACTIVITY_SGIN_STARTTIME").toString());
                        job.put("signEndTime", map.get("ACTIVITY_SGIN_ENDTIME").toString());
                        job.put("activeStatus", map.get("ACTIVITY_STATUS").toString());
                        job.put("status", map.get("STATUS").toString());
                        array.put(job);

                    }
                }
                result.put("lists", array);
                result.put("curragePage", curragePage);
                result.put("pageSize", pageSize);
                result.put("total", total);
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);

            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取活动报名信息列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getActiveRegisterList(String jsonstr) {
        logger.info("获取活动报名信息列表接口信息getActiveRegisterList--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String activeId = jo.has("activeId") ? jo.getString("activeId") : "";//活动id
        String activeName = jo.has("activeName") ? jo.getString("activeName") : "";//活动名称
        String activeTheme = jo.has("activeTheme") ? jo.getString("activeTheme") : "";//活动主题
        String activeApplicableRole = jo.has("activeApplicableRole") ? jo.getString("activeApplicableRole") : "";//活动适用角色 （1,2,3）
        String signStartTime = jo.has("signStartTime") ? jo.getString("signStartTime") : "";//报名开始
        String signEndTime = jo.has("signEndTime") ? jo.getString("signEndTime") : "";//报名结束
        String registerApproveStatus = jo.has("registerApproveStatus") ? jo.getString("registerApproveStatus") : "";//报名审核状态 0 全部 1 驳回 2 通过 3 待审核
        String registerSource = jo.has("registerSource") ? jo.getString("registerSource") : "";//报名来源（1 web 2 微信 0 全部）
        String userId = jo.has("userId") ? jo.getString("userId") : "";//用户id
        String curragePage = jo.getString("curragePage");//当前页
        String pageSize = jo.getString("pageSize");//每页记录数
        if (Utils.isEmpty(curragePage) || Utils.isEmpty(pageSize)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //根据条件查询列表
                StringBuffer sqlC = new StringBuffer("SELECT COUNT(*) SUM FROM ACTIVITY_REGISTER_INFO AR LEFT JOIN ACTIVITY_INFO AI ");
                sqlC.append(" ON AR.REGISTER_ACTIVITY_ID = AI.ACTIVITY_ID LEFT JOIN USER_INFO U ON AR.REGISTER_USER_ID = U.USER_ID WHERE 1=1 ");
                if (!registerApproveStatus.equals("")) {
                    sqlC.append(" AND AR.REGISTER_APPROVE_STATUS = '" + registerApproveStatus + "'");
                }
                if (!registerSource.equals("")) {
                    sqlC.append(" AND AR.REGISTER_SOURCE = '" + registerSource + "'");
                }
                if (!signStartTime.equals("")) {
                    sqlC.append(" AND date_format(AR.REGISTER_TIME,'%Y-%m-%d') >= '" + signStartTime + "'");
                }
                if (!signEndTime.equals("")) {
                    sqlC.append(" AND date_format(AR.REGISTER_TIME,'%Y-%m-%d') <= '" + signEndTime + "'");
                }
                if (!activeId.equals("")) {
                    sqlC.append(" AND AI.ACTIVITY_ID = '" + activeId + "'");
                }
                if (!activeName.equals("")) {
                    sqlC.append(" AND AI.ACTIVITY_NAME LIKE '%" + activeName + "%'");
                }
                if (!activeTheme.equals("")) {
                    sqlC.append(" AND AI.ACTIVITY_THEME LIKE '%" + activeTheme + "%'");
                }
                if (!activeApplicableRole.equals("")) {
                    sqlC.append(" AND AI.ACTIVITY_APPLICABLE_ROLE LIKE '%" + activeApplicableRole + "%'");
                }
                if (!userId.equals("")) {
                    sqlC.append(" AND U.USER_ID = '" + userId + "'");
                }
                logger.info("sql=" + sqlC.toString());

                int total = baseEntityDao.CountBySQL(sqlC.toString());

                StringBuffer sqlL = new StringBuffer("SELECT AI.ACTIVITY_ID,AI.ACTIVITY_ZBCODE,AR.REGISTER_ID,AI.ACTIVITY_TYPE,AI.ACTIVITY_NAME,AI.ACTIVITY_THEME,AI.ACTIVITY_BANNER,AI.ACTIVITY_APPLICABLE_ROLE,");
                sqlL.append("AR.REGISTER_TIME,AR.REGISTER_PROGRESS_STATUS,AI.ACTIVITY_STARTTIME,AI.ACTIVITY_ENDTIME,AI.ACTIVITY_SGIN_STARTTIME,AI.ACTIVITY_SGIN_ENDTIME,AI.ACTIVITY_STATUS,(SELECT D.DATA_NAME FROM DATA_VALUE D WHERE ");
                sqlL.append(" D.DATA_CODE = AI.ACTIVITY_STATUS AND D.DATA_DICTIONARY = ( SELECT DT.DICTIONARY_ID FROM DATA_DICTIONARY DT WHERE ");
                sqlL.append(" DT.DICTIONARY_CODE = 'activeStatus')) AS ACTIVITY_STATUS_NAME,AI.ACTIVITY_CREATENAME,AI.ACTIVITY_UPDATENAME,U.USER_ID,");
                sqlL.append(" U.USER_NAME,U.USER_PHONE,U.USER_ROLETYPE,AR.REGISTER_APPROVE_STATUS,AR.REGISTER_SOURCE,(SELECT D.DATA_NAME FROM DATA_VALUE D");
                sqlL.append(" WHERE D.DATA_CODE = AR.REGISTER_SOURCE AND D.DATA_DICTIONARY = (SELECT DT.DICTIONARY_ID FROM DATA_DICTIONARY DT WHERE");
                sqlL.append(" DT.DICTIONARY_CODE = 'source' )) AS REGISTER_SOURCE_NAME FROM ACTIVITY_REGISTER_INFO AR LEFT JOIN ACTIVITY_INFO AI ");
                sqlL.append(" ON AR.REGISTER_ACTIVITY_ID = AI.ACTIVITY_ID LEFT JOIN USER_INFO U ON AR.REGISTER_USER_ID = U.USER_ID WHERE 1=1 ");
                if (!registerApproveStatus.equals("")) {
                    sqlL.append(" AND AR.REGISTER_APPROVE_STATUS = '" + registerApproveStatus + "'");
                }
                if (!registerSource.equals("")) {
                    sqlL.append(" AND AR.REGISTER_SOURCE = '" + registerSource + "'");
                }
                if (!signStartTime.equals("")) {
                    sqlL.append(" AND date_format(AR.REGISTER_TIME,'%Y-%m-%d') >= '" + signStartTime + "'");
                }
                if (!signEndTime.equals("")) {
                    sqlL.append(" AND date_format(AR.REGISTER_TIME,'%Y-%m-%d') <= '" + signEndTime + "'");
                }
                if (!activeId.equals("")) {
                    sqlL.append(" AND AI.ACTIVITY_ID = '" + activeId + "'");
                }
                if (!activeName.equals("")) {
                    sqlL.append(" AND AI.ACTIVITY_NAME LIKE '%" + activeName + "%'");
                }
                if (!activeTheme.equals("")) {
                    sqlL.append(" AND AI.ACTIVITY_THEME LIKE '%" + activeTheme + "%'");
                }
                if (!activeApplicableRole.equals("")) {
                    sqlL.append(" AND AI.ACTIVITY_APPLICABLE_ROLE LIKE '%" + activeApplicableRole + "%'");
                }
                if (!userId.equals("")) {
                    sqlL.append(" AND U.USER_ID = '" + userId + "'");
                }
                logger.info("sql=" + sqlL.toString());
                List<Map<String, Object>> activeRegisterList = baseEntityDao.listByPageBySQL(sqlL.toString(), Integer.parseInt(pageSize), Integer.parseInt(curragePage) - 1, false);
                JSONArray array = new JSONArray();
                if (activeRegisterList != null && activeRegisterList.size() > 0) {
                    JSONObject job = null;
                    for (int i = 0; i < activeRegisterList.size(); i++) {
                        job = new JSONObject();
                        Map<String, Object> map = activeRegisterList.get(i);
                        job.put("activeId", map.get("ACTIVITY_ID").toString());
                        job.put("registerId", map.get("REGISTER_ID").toString());
//                        job.put("activeType", map.get("ACTIVITY_TYPE").toString());
                        job.put("activeName", map.get("ACTIVITY_NAME").toString());
                        job.put("activeTheme", map.get("ACTIVITY_THEME").toString());
                        job.put("activeBanner", map.get("ACTIVITY_BANNER").toString());
                        job.put("registerProgressStatus", map.get("REGISTER_PROGRESS_STATUS").toString());
//                        job.put("activeApplicableRole", map.get("ACTIVITY_APPLICABLE_ROLE").toString());
                        job.put("registerTime", map.get("REGISTER_TIME").toString());
//                        job.put("activeStartTime", map.get("ACTIVITY_STARTTIME").toString());
//                        job.put("activeEndTime", map.get("ACTIVITY_ENDTIME").toString());
                        job.put("signStartTime", map.get("ACTIVITY_SGIN_STARTTIME").toString());
                        job.put("signEndTime", map.get("ACTIVITY_SGIN_ENDTIME").toString());
                        job.put("activeStatus", map.get("ACTIVITY_STATUS").toString());
                        job.put("activeStatusName", map.get("ACTIVITY_STATUS_NAME").toString());
                        job.put("activeCreate", map.get("ACTIVITY_CREATENAME").toString());
                        job.put("activeUpdate", map.get("ACTIVITY_UPDATENAME").toString());
                        job.put("userId", map.get("USER_ID").toString());
                        job.put("userName", map.get("USER_NAME").toString());
                        job.put("userPhone", map.get("USER_PHONE").toString());
                        job.put("userRole", map.get("USER_ROLETYPE").toString());
                        job.put("approveStatus", map.get("REGISTER_APPROVE_STATUS").toString());
                        job.put("source", map.get("REGISTER_SOURCE").toString());
                        job.put("sourceName", map.get("REGISTER_SOURCE_NAME").toString());
//                        job.put("Zb_code", map.get("ACTIVITY_ZBCODE").toString());
                        array.put(job);
                    }
                }
                result.put("lists", array);
                result.put("curragePage", curragePage);
                result.put("pageSize", pageSize);
                result.put("total", total);
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);

            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 新增报名活动信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertRegisterActive(String jsonstr) {
        logger.info("新增报名活动信息接口信息insertRegisterActive--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String activeId = jo.getString("activeId");//活动id
        String userId = jo.has("userId") ? jo.getString("userId") : "";//用户id
        String userRole = jo.getString("userRole");//用户角色
        String registerSource = jo.getString("registerSource");//报名来源（1 web 2 微信 0 全部）
        String userPhone = jo.getString("userPhone");//用户电话
        String userName = jo.getString("userName");//用户名称
        String classId = jo.has("classId") ? jo.getString("classId") : "";//班级id
        String schoolId=jo.has("schoolId")?jo.getString("schoolId"):"";//学校id
        String openId=jo.has("openId")?jo.getString("openId"):"";//微信openid
        String email=jo.has("email")?jo.getString("email"):"";
        if (Utils.isEmpty(activeId) || Utils.isEmpty(userRole) || Utils.isEmpty(userId) ||
                Utils.isEmpty(registerSource) || Utils.isEmpty(userPhone) || Utils.isEmpty(userName) ||
                Utils.isEmpty(classId) ) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                String resultStr = "";
                //先判断如果用户角色为老师则查询班级学生数是否小于10人，小于10人返回错误
                int countClassS = 0;
                if (userRole.contains("2")) {
                    Conjunction classCn = Restrictions.conjunction();
                    classCn.add(Restrictions.eq("classId", classId));
                    countClassS = baseEntityDao.countByCriteria(ClassStudentInfo.class, classCn, false);
                }
                if (userRole.contains("2") && countClassS <= 10) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_010);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_010_MSG);
                    return result;
                } else {
                    //取信息保存
                    //更新活动人气，每报名一个增加1个
                    ActivityInfo activityInfo = baseEntityDao.listById(ActivityInfo.class, activeId, false);
                    int count = Utils.isEmpty(activityInfo.getActivityNumCy()) ? 0 : Integer.parseInt(activityInfo.getActivityNumCy());
                    //判断如果身份是老师，则默认给所传班级底下未报名所有学生报名
                    if (userRole.contains("2")) {
                        //根据会员id查询班级信息
                        StringBuffer sqlb = new StringBuffer("SELECT U.USER_ID,U.USER_NAME,U.USER_PHONE,U.USER_ROLETYPE FROM USER_INFO U ");
                        sqlb.append(" LEFT JOIN  CLASS_STUDENT_INFO CS ON CS.CLASS_STUDENT=U.USER_ID");
                        sqlb.append(" LEFT JOIN CLASS_INFO C ON C.CLASS_ID=CS.CLASS_ID ");
                        sqlb.append(" WHERE  NOT EXISTS (SELECT 'X' FROM ACTIVITY_REGISTER_INFO AR WHERE AR.REGISTER_USER_ID =CS.CLASS_STUDENT )");
                        sqlb.append(" AND C.CLASS_TEACHER='" + userId + "' AND CS.CLASS_ID='" + classId + "' ");
                        List<Map<String, Object>> list = baseEntityDao.listBySQL(sqlb.toString(), false);
                        JSONArray arrayT = new JSONArray();
                        if (list != null && list.size() > 0) {
                            JSONObject obj = null;
                            for (int i = 0; i < list.size(); i++) {
                                Map<String, Object> map = list.get(i);
                                RegisterInfo registerInfoT = new RegisterInfo();
                                registerInfoT.setRegisterActivityId(activeId);
                                registerInfoT.setRegisterUserId(map.get("USER_ID").toString());
                                registerInfoT.setRegisterSource(registerSource);
                                registerInfoT.setRegisterUserPhone(map.get("USER_PHONE").toString());
                                registerInfoT.setRegisterUserName(map.get("USER_NAME").toString());
                                registerInfoT.setStatus("1");
                                registerInfoT.setRegisterProgressStatus("3");//未参加 1 已参加，2 进行中，3未参加，4已过期
                                registerInfoT.setRegisterApproveStatus("1");//待审核  1待审核 2 驳回 3 通过
                                registerInfoT.setRegisterCreateName(userName);
                                registerInfoT.setRegisterCreateTime(new Date());
                                registerInfoT.setRegisterUpdateName(userName);
                                registerInfoT.setRegisterUpdateTime(new Date());
                                registerInfoT.setRegisterTime(new Date());//报名时间
                                resultStr = baseEntityDao.saveOrUpdate(registerInfoT);
                                //更新用户活动次数
                                UserInfo userInfo = baseEntityDao.listById(UserInfo.class, map.get("USER_ID").toString(), false);
                                if (userInfo != null) {
                                    int countActive = Integer.parseInt(userInfo.getCountActive());
                                    userInfo.setCountActive(String.valueOf(countActive++));
                                    resultStr = baseEntityDao.saveOrUpdate(userInfo);
                                }
                                if (resultStr.equals("success")) {
                                    count++;
                                }

                            }
                        }
                    }
                    //先查询是否已报过名
                    Conjunction cn = Restrictions.conjunction();
                    cn.add(Restrictions.eq("registerUserId", userId));
                    cn.add(Restrictions.eq("registerActivityId", activeId));
                    List<RegisterInfo> registerInfoList = baseEntityDao.listByCriteria(RegisterInfo.class, cn, false);
                    if (registerInfoList != null && registerInfoList.size() <= 0) {
                        RegisterInfo registerInfo = new RegisterInfo();
                        registerInfo.setRegisterActivityId(activeId);
                        registerInfo.setRegisterUserId(userId);
                        registerInfo.setRegisterSource(registerSource);
                        registerInfo.setRegisterUserPhone(userPhone);
                        registerInfo.setRegisterUserName(userName);
                        registerInfo.setStatus("1");
                        registerInfo.setRegisterProgressStatus("3");//未参加 1 已参加，2 进行中，3未参加
                        registerInfo.setRegisterApproveStatus("1");//待审核  1待审核 2 驳回 3 通过
                        registerInfo.setRegisterCreateName(userName);
                        registerInfo.setRegisterCreateTime(new Date());
                        registerInfo.setRegisterUpdateName(userName);
                        registerInfo.setRegisterUpdateTime(new Date());
                        registerInfo.setRegisterTime(new Date());//报名时间
                        resultStr = baseEntityDao.saveOrUpdate(registerInfo);
                        count++;
                        //更新活动表信息
//                        activityInfo = baseEntityDao.listById(ActivityInfo.class, activeId, false);
                        activityInfo.setActivityNumCy(String.valueOf(count));
                        logger.info("更新活动表人气值--------" + count);
                        resultStr = baseEntityDao.saveOrUpdate(activityInfo);
                        //更新用户活动次数
                        UserInfo userInfo = baseEntityDao.listById(UserInfo.class, userId, false);
                        if (userInfo != null) {
                            int countActive = userInfo.getCountActive() == null ? 0 : Integer.parseInt(userInfo.getCountActive());
                            countActive++;
                            userInfo.setCountActive(String.valueOf(countActive));
                            resultStr = baseEntityDao.saveOrUpdate(userInfo);
                        }
                        if (resultStr.equals("success")) {
                            //保存成功 返回
                            result.put("activeNum", activityInfo.getActivityCode());//返回活动编码
                            result.put("regiterTime", new Date());//返回活动编码
                            result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                            result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                        } else {
                            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                        }
                    } else {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_009);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_009_MSG);
                    }
                }

            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        logger.info("新增报名活动信息接口信息insertRegisterActive 返回参数--------" + result.toString());
        return result;
    }

    /**
     * 修改报名活动信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateRegisterActive(String jsonstr) {
        logger.info("修改报名活动信息接口信息updateRegisterActive--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String registerId = jo.getString("registerId");//报名活动id
        String activeId = jo.getString("activeId");//活动id
        String userId = jo.getString("userId");//用户id
        String userRole = jo.getString("userRole");//用户角色
        String activeCreate = jo.getString("activeCreate");//创建人
        String status = jo.getString("status");//状态
        String registerSource = jo.getString("registerSource");//报名来源（1 web 2 微信 0 全部）
        String userPhone = jo.getString("userPhone");//用户电话
        String userName = jo.getString("userName");//用户名称
        if (Utils.isEmpty(registerId) || Utils.isEmpty(activeId) || Utils.isEmpty(userId) || Utils.isEmpty(userRole) ||
                Utils.isEmpty(activeCreate) || Utils.isEmpty(status) || Utils.isEmpty(status) ||
                Utils.isEmpty(registerSource) || Utils.isEmpty(userPhone) || Utils.isEmpty(userName)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //取信息保存
                RegisterInfo registerInfo = baseEntityDao.listById(RegisterInfo.class, registerId, false);
                registerInfo = registerInfo == null ? new RegisterInfo() : registerInfo;
                registerInfo.setRegisterActivityId(activeId);
                registerInfo.setRegisterUserId(userId);
                registerInfo.setRegisterSource(registerSource);
                registerInfo.setRegisterUserPhone(userPhone);
                registerInfo.setRegisterUserName(userName);
                registerInfo.setStatus(status);
                registerInfo.setRegisterProgressStatus("3");//未参加 1 已参加，2 进行中，3未参加，4已过期
                registerInfo.setRegisterApproveStatus("1");//待审核  1待审核 2 驳回 3 通过
                registerInfo.setRegisterCreateName(activeCreate);
                registerInfo.setRegisterCreateTime(new Date());
                registerInfo.setRegisterUpdateName(activeCreate);
                registerInfo.setRegisterUpdateTime(new Date());
                registerInfo.setRegisterTime(new Date());//报名时间
                String resultStr = baseEntityDao.saveOrUpdate(registerInfo);
                if (resultStr.equals("success")) {
                    //保存成功 返回
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                } else {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                }

            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        logger.info("修改报名活动信息接口信息updateRegisterActive 返回参数--------" + result.toString());
        return result;
    }

    /**
     * 获取报名活动信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getRegisterInfoById(String jsonstr) {
        logger.info("获取报名活动信息接口信息getRegisterInfoById--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String registerId = jo.getString("registerId");//报名活动id
        if (Utils.isEmpty(registerId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //取信息保存
                StringBuffer sql = new StringBuffer("SELECT AR.REGISTER_ID,AI.ACTIVITY_ID,AI.ACTIVITY_TYPE, AI.ACTIVITY_BANNER,");
                sql.append(" AI.ACTIVITY_NAME,AI.ACTIVITY_DETAIL,AI.ACTIVITY_THEME,U.USER_ID,U.USER_PHONE,AR.REGISTER_SOURCE,");
                sql.append(" U.USER_NAME,U.USER_ROLE,AR.REGISTER_TIME,AR.REGISTER_APPROVE_STATUS,AR.REGISTER_PROGRESS_STATUS,");
                sql.append(" AR.STATUS, AI.ACTIVITY_STARTTIME,AI.ACTIVITY_ENDTIME,AR.REGISTER_USERPHONE,AR.REGISTER_USERNAME FROM ACTIVITY_REGISTER_INFO AR");
                sql.append("  LEFT JOIN ACTIVITY_INFO  AI ON AR.REGISTER_ACTIVITY_ID=AI.ACTIVITY_ID LEFT JOIN");
                sql.append("  USER_INFO U ON AR.REGISTER_USER_ID=U.USER_ID WHERE AR.REGISTER_ID='" + registerId + "'");
                List<Map<String, Object>> list = baseEntityDao.listBySQL(sql.toString(), false);
                if (list != null && list.size() > 0) {
                    Map<String, Object> map = list.get(0);
                    result.put("registerId", map.get("REGISTER_ID").toString());
                    result.put("activeId", map.get("ACTIVITY_ID").toString());
                    result.put("activeType", map.get("ACTIVITY_TYPE").toString());
                    result.put("activeBanner", map.get("ACTIVITY_BANNER").toString());
                    result.put("activeName", map.get("ACTIVITY_NAME").toString());
                    result.put("activeTheme", map.get("ACTIVITY_THEME").toString());
                    result.put("activeDetail", map.get("ACTIVITY_DETAIL").toString());
                    result.put("userId", map.get("USER_ID").toString());
                    result.put("userName", map.get("USER_NAME").toString());
                    result.put("userPhone", map.get("USER_PHONE").toString());
                    result.put("registerTime", map.get("REGISTER_TIME").toString());
                    result.put("registerApproveStatus", map.get("REGISTER_APPROVE_STATUS").toString());
                    result.put("registerProgressStatus", map.get("REGISTER_PROGRESS_STATUS").toString());
                    result.put("activeEndTime", map.get("ACTIVITY_ENDTIME").toString());
                    result.put("activeStartTime", map.get("ACTIVITY_STARTTIME").toString());
                    result.put("source", map.get("REGISTER_SOURCE").toString());
                    result.put("userRole", map.get("USER_ROLE").toString());
                    result.put("registerUserPhone", map.get("REGISTER_USERPHONE").toString());
                    result.put("registerUserName", map.get("REGISTER_USERNAME").toString());
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                } else {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_004_MSG);
                }

            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        logger.info("获取报名活动信息接口信息getRegisterInfoById 返回参数--------" + result.toString());
        return result;
    }

    /**
     * 功能：修改活动状态
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateActiveStatus(String jsonstr) {
        logger.info("修改活动状态接口信息updateActiveStatus--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String activeId = jo.getString("activeId");
        String activeStatus = jo.getString("activeStatus");
        if (Utils.isEmpty(activeId) || Utils.isEmpty(activeStatus)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                ActivityInfo activityInfo = baseEntityDao.listById(ActivityInfo.class, activeId, false);
                if (activityInfo != null) {
                    activityInfo.setActivityStatus(activeStatus);
                    String resultStr = baseEntityDao.saveOrUpdate(activityInfo);
                    if (resultStr.equals("success")) {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                    } else {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                    }

                } else {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_008);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_008_MSG);
                }
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 功能：修改报名活动审核状态
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateRegisterStatus(String jsonstr) {
        logger.info("修改报名活动审核状态息updateRegisterStatus--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String registerId = jo.getString("registerId");
        String registerApproveStatus = jo.getString("registerApproveStatus");
        if (Utils.isEmpty(registerId) || Utils.isEmpty(registerApproveStatus)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                RegisterInfo registerInfo = baseEntityDao.listById(RegisterInfo.class, registerId, false);
                if (registerInfo != null) {
                    registerInfo.setRegisterApproveStatus(registerApproveStatus);
                    String resultStr = baseEntityDao.saveOrUpdate(registerInfo);
                    if (resultStr.equals("success")) {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                    } else {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                    }
                } else {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_008);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_008_MSG);
                }
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }

        return result;
    }

    /*
   功能：修改报名参与状态
    */
    public JSONObject updateRegisterProgressStatus(String jsonstr) {
        logger.info("修改报名活动参与状态息updateRegisterProgressStatus--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String activeId = jo.getString("activeId");
        String progressStatus = jo.getString("progressStatus");
        String userId = jo.getString("userId");
        if (Utils.isEmpty(userId) || Utils.isEmpty(activeId) || Utils.isEmpty(progressStatus)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                Conjunction cn = Restrictions.conjunction();
                cn.add(Restrictions.eq("registerUserId", userId));
                cn.add(Restrictions.eq("registerActivityId", activeId));
//                cn.add(Restrictions.eq("registerProgressStatus",progressStatus));
                List<RegisterInfo> registerInfoList = baseEntityDao.listByCriteria(RegisterInfo.class, cn, false);
                if (registerInfoList != null && registerInfoList.size() > 0) {
                    String resultStr = "";
                    for (RegisterInfo registerInfo : registerInfoList) {
//                        RegisterInfo registerInfo=registerInfoList.get(0);
                        registerInfo.setRegisterProgressStatus(progressStatus);
                        registerInfo.setRegisterUpdateTime(new Date());
                        registerInfo.setRegisterUserName("admin");
                        resultStr = baseEntityDao.saveOrUpdate(registerInfo);
                    }
                    if (resultStr.equals("success")) {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                    } else {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                    }
                } else {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_008);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_008_MSG);
                }
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }

        return result;
    }

    /*
    功能：新增问卷调查
     */
    public JSONObject insertQuestionInfo(String jsonstr) {
        logger.info("新增问卷调查insertQuestionInfo--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject oj = new JSONObject(jsonstr);
        String activeId = oj.getString("activeId");
        String questionIsActive = oj.getString("questionIsActive");
        String questionActiveNum = oj.getString("questionActiveNum");
        String questionCreate = oj.getString("questionCreate");
        String quesrionUserId = oj.getString("questionUserId");
        String status = oj.getString("status");
        if (Utils.isEmpty(questionCreate) || Utils.isEmpty(activeId) || Utils.isEmpty(questionActiveNum)
                || Utils.isEmpty(status) || Utils.isEmpty(questionIsActive)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            QuestionInfo questionInfo = new QuestionInfo();
            questionInfo.setQuestionActiveNum(questionActiveNum);
            questionInfo.setQuestionUserId(quesrionUserId);
            questionInfo.setQuestionActiveId(activeId);
            questionInfo.setQuestionIsActive(questionIsActive);
            questionInfo.setQuestionCreateName(questionCreate);
            questionInfo.setQuestionCreateTime(new Date());
            questionInfo.setQuestionUpdateName(questionCreate);
            questionInfo.setQuestionUpdateTime(new Date());
            questionInfo.setStatus(status);
            String resultStr = baseEntityDao.saveOrUpdate(questionInfo);
            if (resultStr.equals("success")) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
            } else {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
            }
        }
        return result;
    }

    /*
    功能：获取问卷列表
     */
    public JSONObject getQuesrionList(String jsonstr) {
        logger.info("获取问卷调查getQuesrionList--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject oj = new JSONObject(jsonstr);
        String activeId = oj.has("activeId") ? oj.getString("activeId") : "";
        Conjunction cn = Restrictions.conjunction();
        if (Utils.isEmpty(activeId)) {
            cn.add(Restrictions.eq("questionActiveId", activeId));
        }
        List<QuestionInfo> questionInfos = baseEntityDao.listByCriteria(QuestionInfo.class, cn, false);
        JSONArray lists = new JSONArray();
        if (questionInfos != null && questionInfos.size() > 0) {
            JSONObject jsonobj = null;
            for (QuestionInfo questionInfo : questionInfos) {
                jsonobj = new JSONObject();
                jsonobj.put("questionId", questionInfo.getQuestionId());
                jsonobj.put("activeId", questionInfo.getQuestionActiveId());
                jsonobj.put("questionIsActive", questionInfo.getQuestionIsActive());
                jsonobj.put("questionActiveNum", questionInfo.getQuestionActiveNum());
                jsonobj.put("status", questionInfo.getStatus());
                jsonobj.put("questionUserId", questionInfo.getQuestionUserId());
                lists.put(jsonobj);
            }
        }
        result.put("lists", lists);
        result.put("total", lists.length());
        result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
        result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        return result;
    }


    /**
     * 新增明细
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertDetail(String jsonstr) {
        logger.info("新增明细insertDetail--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject oj = new JSONObject(jsonstr);
        String detailCate = oj.getString("detailCate");
        String detailTeacherId = oj.getString("detailTeacherId");
        String detailBanner = oj.getString("detailBanner");
        String detailTheme = oj.getString("detailTheme");
        String detailDesc = oj.getString("detailDesc");
        String detailStartTime = oj.getString("detailStartTime");//sdf.parse
        String detailEndTime = oj.getString("detailEndTime");//sdf.parse
        String detailIsPublic = oj.getString("detailIsPublic");//
        String detailZbcode = oj.getString("detailZbcode");
        String createName = oj.getString("createName");
        String status = oj.getString("status");
        if (Utils.isEmpty(detailCate) || Utils.isEmpty(detailTeacherId) || Utils.isEmpty(detailBanner)
                || Utils.isEmpty(status) || Utils.isEmpty(detailTheme) || Utils.isEmpty(detailDesc)
                || Utils.isEmpty(detailStartTime) || Utils.isEmpty(detailEndTime) || Utils.isEmpty(detailIsPublic)
                || Utils.isEmpty(createName) || Utils.isEmpty(detailZbcode)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                DetailInfo detailInfo = new DetailInfo();
                detailInfo.setDetailCate(detailCate);
                detailInfo.setDetailBanner(detailBanner);
                detailInfo.setDetailDesc(detailDesc);
                detailInfo.setDetailIsPublic(detailIsPublic);
                detailInfo.setDetailTheme(detailTheme);
                detailInfo.setDetailTeacherId(detailTeacherId);
                detailInfo.setDetailStartTime(sdf.parse(detailStartTime));
                detailInfo.setDetailEndTime(sdf.parse(detailEndTime));
                detailInfo.setDetailZbcode(detailZbcode);
                detailInfo.setDetailCreateName(createName);
                detailInfo.setDetailCreateTime(new Date());
                detailInfo.setDetailUpdateName(createName);
                detailInfo.setDetailUpdateTime(new Date());
                detailInfo.setStatus(status);
                String resultStr = baseEntityDao.saveOrUpdate(detailInfo);
                if (resultStr.equals("success")) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                } else {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                }
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 修改明细
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateDetail(String jsonstr) {
        logger.info("修改明细updateDetail--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject oj = new JSONObject(jsonstr);
        String detailId = oj.getString("detailId");
        String detailCate = oj.getString("detailCate");
        String detailTeacherId = oj.getString("detailTeacherId");
        String detailBanner = oj.getString("detailBanner");
        String detailTheme = oj.getString("detailTheme");
        String detailDesc = oj.getString("detailDesc");
        String detailStartTime = oj.getString("detailStartTime");//sdf.parse
        String detailEndTime = oj.getString("detailEndTime");//sdf.parse
        String detailIsPublic = oj.getString("detailIsPublic");//
        String detailZbcode = oj.getString("detailZbcode");
        String updateName = oj.getString("updateName");
        String status = oj.getString("status");
        if (Utils.isEmpty(detailCate) || Utils.isEmpty(detailTeacherId) || Utils.isEmpty(detailBanner)
                || Utils.isEmpty(status) || Utils.isEmpty(detailTheme) || Utils.isEmpty(detailDesc)
                || Utils.isEmpty(detailStartTime) || Utils.isEmpty(detailEndTime) || Utils.isEmpty(detailIsPublic)
                || Utils.isEmpty(updateName) || Utils.isEmpty(detailId) || Utils.isEmpty(detailZbcode)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                DetailInfo detailInfo = baseEntityDao.listById(DetailInfo.class, detailId, false);
                if (detailInfo == null) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_008);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_008_MSG);
                } else {
                    detailInfo.setDetailCate(detailCate);
                    detailInfo.setDetailBanner(detailBanner);
                    detailInfo.setDetailDesc(detailDesc);
                    detailInfo.setDetailIsPublic(detailIsPublic);
                    detailInfo.setDetailTheme(detailTheme);
                    detailInfo.setDetailTeacherId(detailTeacherId);
                    detailInfo.setDetailStartTime(sdf.parse(detailStartTime));
                    detailInfo.setDetailEndTime(sdf.parse(detailEndTime));
                    detailInfo.setDetailZbcode(detailZbcode);
                    detailInfo.setDetailUpdateName(updateName);
                    detailInfo.setDetailUpdateTime(new Date());
                    detailInfo.setStatus(status);
                    String resultStr = baseEntityDao.saveOrUpdate(detailInfo);
                    if (resultStr.equals("success")) {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                    } else {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                    }
                }
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取明细信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getDetailInfoById(String jsonstr) {
        logger.info("获取明细信息getDetailInfoById--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject oj = new JSONObject(jsonstr);
        String detailId = oj.getString("detailId");
        if (Utils.isEmpty(detailId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                DetailInfo detailInfo = baseEntityDao.listById(DetailInfo.class, detailId, false);
                if (detailInfo == null) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_008);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_008_MSG);
                } else {
                    result.put("detailId", detailInfo.getDetailId());
                    result.put("detailCate", detailInfo.getDetailCate());
                    result.put("detailBanner", detailInfo.getDetailBanner());
                    result.put("detailDesc", detailInfo.getDetailDesc());
                    result.put("detailIsPublic", detailInfo.getDetailIsPublic());
                    result.put("detailTheme", detailInfo.getDetailTheme());
                    result.put("detailTeacherId", detailInfo.getDetailTeacherId());
                    result.put("detailStartTime", detailInfo.getDetailStartTime());
                    result.put("detailEndTime", detailInfo.getDetailEndTime());
                    result.put("detailZbcode", detailInfo.getDetailZbcode());
                    result.put("status", detailInfo.getStatus());
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                }
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取明细列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getDetailInfoList(String jsonstr) {
        logger.info("获取明细列表getDetailInfoList--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject oj = new JSONObject(jsonstr);
        String detailTheme = oj.has("detailTheme") ? oj.getString("detailTheme") : "";
        String detailCate = oj.has("detailCate") ? oj.getString("detailCate") : "";
        String detailTeacherId = oj.has("detailTeacherId") ? oj.getString("detailTeacherId") : "";
        String detailStartTime = oj.has("detailStartTime") ? oj.getString("detailStartTime") : "";//sdf.parse
        String detailEndTime = oj.has("detailEndTime") ? oj.getString("detailEndTime") : "";//sdf.parse
        String detailIsPublic = oj.has("detailIsPublic") ? oj.getString("detailIsPublic") : "";//
        String activeId = oj.has("activeId") ? oj.getString("activeId") : "";
        String curragePage = oj.getString("curragePage");//当前页
        String pageSize = oj.getString("pageSize");//每页记录数
        if (Utils.isEmpty(curragePage) || Utils.isEmpty(pageSize)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                Conjunction cn = Restrictions.conjunction();
                if (!Utils.isEmpty(detailTheme)) {
                    cn.add(Restrictions.like("detailTheme", "%" + detailTheme + "%"));
                }
                if (!Utils.isEmpty(detailCate)) {
                    cn.add(Restrictions.eq("detailCate", detailCate));
                }
                if (!Utils.isEmpty(detailTeacherId)) {
                    cn.add(Restrictions.eq("detailTeacherId", detailTeacherId));
                }
                if (!Utils.isEmpty(detailStartTime)) {
                    cn.add(Restrictions.ne("detailStartTime", detailStartTime));
                }
                if (!Utils.isEmpty(detailEndTime)) {
                    cn.add(Restrictions.ge("detailEndTime", detailEndTime));
                }
                if (!Utils.isEmpty(detailIsPublic)) {
                    cn.add(Restrictions.eq("detailIsPublic", detailIsPublic));
                }
                int counts = baseEntityDao.countByCriteria(DetailInfo.class, cn, false);
                List<DetailInfo> detailInfoList = baseEntityDao.listByPageCriteria(DetailInfo.class, cn, Integer.parseInt(pageSize),
                        Integer.parseInt(curragePage) - 1, false);
                JSONArray lists = new JSONArray();
                if (detailInfoList != null && detailInfoList.size() > 0) {
                    JSONObject obj = null;
                    for (DetailInfo detailInfo : detailInfoList) {
                        obj = new JSONObject();
                        obj.put("detailId", detailInfo.getDetailId());
                        obj.put("detailCate", detailInfo.getDetailCate());
                        obj.put("detailBanner", detailInfo.getDetailBanner());
                        obj.put("detailDesc", detailInfo.getDetailDesc());
                        obj.put("detailIsPublic", detailInfo.getDetailIsPublic());
                        obj.put("detailTheme", detailInfo.getDetailTheme());
                        obj.put("detailTeacherId", detailInfo.getDetailTeacherId());
                        obj.put("detailStartTime", detailInfo.getDetailStartTime());
                        obj.put("detailEndTime", detailInfo.getDetailEndTime());
                        obj.put("status", detailInfo.getStatus());
                        lists.put(obj);
                    }
                }
                result.put("lists", lists);
                result.put("curragePage", curragePage);
                result.put("pageSize", pageSize);
                result.put("total", counts);
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取活动 明细列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getDetailInfoListByActiveId(String jsonstr) {
        logger.info("获取活动明细列表getDetailInfoListByActiveId--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject oj = new JSONObject(jsonstr);
        String activeId = oj.has("activeId") ? oj.getString("activeId") : "";
        if (Utils.isEmpty(activeId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                StringBuffer sql = new StringBuffer("SELECT D.DETAIL_ID,D.DETAIL_BANNER,D.DETAIL_CATE,D.DETAIL_DESC,D.DETAIL_ENDTIME,D.DETAIL_ISPUBLIC");
                sql.append(",D.DETAIL_STARTTIME,D.DETAIL_ZBCODE ,D.DETAIL_THEME,D.DETAIL_TEACHERID FROM DETAIL_INFO D LEFT JOIN ACTIVITY_DETAIL_INFO AD ON D.DETAIL_ID=AD.DETAIL_ID ");
                sql.append("  WHERE D.STATUS='1' AND AD.ACTIVITY_ID='" + activeId + "'");
                List<Map<String, Object>> detailInfoList = baseEntityDao.listBySQL(sql.toString(), false);
                JSONArray lists = new JSONArray();
                if (detailInfoList != null && detailInfoList.size() > 0) {
                    JSONObject obj = null;
                    for (int i = 0; i < detailInfoList.size(); i++) {
                        obj = new JSONObject();
                        Map<String,Object> detailInfo=detailInfoList.get(i);
                        obj.put("detailId", detailInfo.get("DETAIL_ID").toString());
                        obj.put("detailCate", detailInfo.get("DETAIL_CATE").toString());
                        obj.put("detailBanner", detailInfo.get("DETAIL_BANNER").toString());
                        obj.put("detailDesc", detailInfo.get("DETAIL_DESC").toString());
                        obj.put("detailIsPublic", detailInfo.get("DETAIL_ISPUBLIC").toString());
                        obj.put("detailTheme", detailInfo.get("DETAIL_THEME").toString());
                        obj.put("detailTeacherId", detailInfo.get("DETAIL_TEACHERID").toString());
                        obj.put("detailStartTime", detailInfo.get("DETAIL_STARTTIME").toString());
                        obj.put("detailEndTime", detailInfo.get("DETAIL_ENDTIME").toString());
                        lists.put(obj);
                    }
                }
                result.put("lists", lists);
                result.put("total", detailInfoList.size());
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 获取获取会员报名活动明细列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getDetailInfoListByUserId(String jsonstr) {
        logger.info("获取会员报名活动明细列表getDetailInfoListByUserId--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject oj = new JSONObject(jsonstr);
        String userId = oj.has("userId") ? oj.getString("userId") : "";
        if (Utils.isEmpty(userId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                StringBuffer sql = new StringBuffer("SELECT D.DETAIL_ID,D.DETAIL_BANNER,D.DETAIL_CATE,D.DETAIL_DESC,D.DETAIL_ENDTIME,D.DETAIL_ISPUBLIC");
                sql.append(",D.DETAIL_STARTTIME,D.DETAIL_ZBCODE ,D.DETAIL_THEME,D.DETAIL_TEACHERID,R.REGISTER_USER_ID,A.ACTIVITY_ID ,A.ACTIVITY_NAME FROM DETAIL_INFO D ");
                sql.append(" LEFT JOIN ACTIVITY_DETAIL_INFO AD ON D.DETAIL_ID=AD.DETAIL_ID ");
                sql.append(" LEFT JOIN ACTIVITY_INFO A ON A.ACTIVITY_ID =AD.ACTIVITY_ID ");
                sql.append(" LEFT JOIN ACTIVITY_REGISTER_INFO R ON R.REGISTER_ACTIVITY_ID=A.ACTIVITY_ID AND R.REGISTER_APPROVE_STATUS='2' ");
                sql.append("  WHERE 1=1 AND R.REGISTER_USER_ID='" + userId + "'");
                List<Map<String, Object>> detailInfoList = baseEntityDao.listBySQL(sql.toString(), false);
                JSONArray lists = new JSONArray();
                if (detailInfoList != null && detailInfoList.size() > 0) {
                    JSONObject obj = null;
                    for (int i = 0; i < detailInfoList.size(); i++) {
                        obj = new JSONObject();
                        Map<String,Object> detailInfo=detailInfoList.get(i);
                        obj.put("detailId", detailInfo.get("DETAIL_ID").toString());
                        obj.put("detailCate", detailInfo.get("DETAIL_CATE").toString());
                        obj.put("detailBanner", detailInfo.get("DETAIL_BANNER").toString());
                        obj.put("detailDesc", detailInfo.get("DETAIL_DESC").toString());
                        obj.put("detailIsPublic", detailInfo.get("DETAIL_ISPUBLIC").toString());
                        obj.put("detailTheme", detailInfo.get("DETAIL_THEME").toString());
                        obj.put("detailTeacherId", detailInfo.get("DETAIL_TEACHERID").toString());
                        obj.put("detailStartTime", detailInfo.get("DETAIL_STARTTIME").toString());
                        obj.put("detailEndTime", detailInfo.get("DETAIL_ENDTIME").toString());
                        obj.put("activeId", detailInfo.get("ACTIVITY_ID").toString());
                        obj.put("activeName", detailInfo.get("ACTIVITY_NAME").toString());
                        obj.put("userId", detailInfo.get("REGISTER_USER_ID").toString());
                        lists.put(obj);
                    }
                }
                result.put("lists", lists);
                result.put("total", detailInfoList.size());
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }
}
