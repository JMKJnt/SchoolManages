package com.cn.controller;

import com.cn.common.LogHelper;
import com.cn.common.MsgAndCode;
import com.cn.entiy.RequestBean;
import com.cn.service.busService.UserInfoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 新增用户
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertUserInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertUser(@RequestBody RequestBean bean) {
        LogHelper.info("新增用户 请求参数：" + bean);
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.insertUser(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 修改用户
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateUser(@RequestBody RequestBean bean) {
        LogHelper.info("修改用户 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.updateUser(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取用户信息
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserInfoById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getUserInfo(@RequestBody RequestBean bean) {
        LogHelper.info("获取用户信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.getUserInfo(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取用户列表
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserInfoList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getUserInfoList(@RequestBody RequestBean bean) {
        LogHelper.info("获取用户列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.getUserInfoList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 用户登录（web，小程序）
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String login(@RequestBody RequestBean bean) {
        LogHelper.info("用户登录（web，小程序） 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.login(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 新增管理员
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertUserManage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertUserManage(@RequestBody RequestBean bean) {
        LogHelper.info("新增管理员 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.insertUserManage(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 修改管理员
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserManage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateUserManage(@RequestBody RequestBean bean) {
        LogHelper.info("修改管理员请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.updateUserManage(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取管理员信息
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getManageInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getManage(@RequestBody RequestBean bean) {
        LogHelper.info("获取管理员信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.getManage(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取管理员列表
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getManageList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getManageList(@RequestBody RequestBean bean) {
        LogHelper.info("获取管理员列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.getManageList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 管理员登录后台
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loginManager", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String loginManager(@RequestBody RequestBean bean) {
        LogHelper.info("管理员登录后台 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.loginManager(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 修改密码
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updatePassword(@RequestBody RequestBean bean) {
        LogHelper.info("修改管理员密码 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.updatePassword(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /*
       功能：新增家庭成员
        */
    @ResponseBody
    @RequestMapping(value = "/insertFamily", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertFamily(@RequestBody RequestBean bean){
        LogHelper.info("新增家庭成员 insertFamily请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.insertFamily(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /*
    功能：新增家庭成员
     */
    @ResponseBody
    @RequestMapping(value = "/updateFamily", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateFamily(@RequestBody RequestBean bean){
        LogHelper.info("修改家庭成员 updateFamily请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.updateFamily(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /*
  功能：获取家庭成员
   */
    @ResponseBody
    @RequestMapping(value = "/getFamilyInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getFamilyInfo(@RequestBody RequestBean bean){
        LogHelper.info("获取家庭成员 getFamilyInfo请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.getFamilyInfo(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }


    /*
   功能：获取家庭成员
    */
    @ResponseBody
    @RequestMapping(value = "/getFamilyList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getFamilyList(@RequestBody RequestBean bean){
        LogHelper.info("获取家庭成员 getFamilyList请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = userInfoService.getFamilyList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

}
