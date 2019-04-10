package com.cn.service.busService;

import org.json.JSONObject;

public interface ActiveInfoService {
    /**
     * 新增活动
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertActive(String jsonstr);

    /**
     * 修改活动
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateActive(String jsonstr);

    /**
     * 获取活动信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getActiveInfoById(String jsonstr);

    /**
     * 获取活动列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getActiveInfoList(String jsonstr);

    /**
     * 获取client活动列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getActiveClientList(String jsonstr);

    /**
     * 功能：修改活动状态
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateActiveStatus(String jsonstr);

    /**
     * 功能：修改报名活动审核状态
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateRegisterStatus(String jsonstr);

    /*
    功能：修改报名参与状态
     */
    public JSONObject updateRegisterProgressStatus(String jsonstr);

    /**
     * 新增报名活动信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertRegisterActive(String jsonstr);

    /**
     * 修改报名活动信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateRegisterActive(String jsonstr);

    /**
     * 获取报名活动信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getRegisterInfoById(String jsonstr);

    /**
     * 获取活动报名信息列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getActiveRegisterList(String jsonstr);

    /*
    功能：新增问卷调查
     */
    public JSONObject insertQuestionInfo(String jsonstr);

    /*
    功能：获取问卷列表
     */
    public JSONObject getQuesrionList(String jsonstr);


    /**
     * 新增明细
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertDetail(String jsonstr);

    /**
     * 修改明细
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateDetail(String jsonstr);

    /**
     * 获取明细信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getDetailInfoById(String jsonstr);

    /**
     * 获取明细列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getDetailInfoList(String jsonstr);

    /**
     * 获取活动 明细列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getDetailInfoListByActiveId(String jsonstr);

    /**
     * 获取用户报名活动 明细列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getDetailInfoListByUserId(String jsonstr);


}
