package com.cn.service.busService;


import org.json.JSONObject;

public interface UserInfoService {
    /**
     * 新增用户
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertUser(String jsonstr);

    /**
     * 修改用户
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateUser(String jsonstr);

    /**
     * 获取用户信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getUserInfo(String jsonstr);

    /**
     * 获取用户列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getUserInfoList(String jsonstr);

    /**
     * 用户登录（web，小程序）
     *
     * @param jsonstr
     * @return
     */
    public JSONObject login(String jsonstr);

    /**
     * 新增管理员
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertUserManage(String jsonstr);

    /**
     * 修改管理员
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateUserManage(String jsonstr);

    /**
     * 获取管理员信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getManage(String jsonstr);

    /**
     * 获取管理员列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getManageList(String jsonstr);

    /**
     * 管理员登录后台
     *
     * @param jsonstr
     * @return
     */
    public JSONObject loginManager(String jsonstr);

    /**
     * 管理员修改密码
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updatePassword(String jsonstr);

    /*
       功能：新增家庭成员
        */
    public JSONObject insertFamily(String jsonstr);

    /*
   功能：修改家庭成员
    */
    public JSONObject updateFamily(String jsonstr);

    /*
  功能：获取家庭成员
   */
    public JSONObject getFamilyInfo(String jsonstr);

    /*
   功能：获取家庭成员
    */
    public JSONObject getFamilyList(String jsonstr);

}
