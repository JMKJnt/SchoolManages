package com.cn.controller;

import com.cn.common.LogHelper;
import com.cn.common.MsgAndCode;
import com.cn.entiy.RequestBean;
import com.cn.service.busService.ActiveInfoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequestMapping(value = "/activeInfo")
public class ActiveInfoInfoController {

    @Autowired
    private ActiveInfoService activeInfoService;


    /**
     * 新增活动
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertActive", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertActive(@RequestBody RequestBean bean) {
        LogHelper.info("新增活动 请求参数：" + bean);
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.insertActive(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 修改活动
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateActive", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateActive(@RequestBody RequestBean bean) {
        LogHelper.info("修改活动 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.updateActive(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 修改活动状态
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateActiveStatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateActiveStatus(@RequestBody RequestBean bean) {
        LogHelper.info("修改活动状态 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.updateActiveStatus(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 功能：修改活动参与状态
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateProgressStatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateProgressStatus(@RequestBody RequestBean bean) {
        LogHelper.info("修改活动参与状态 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.updateRegisterProgressStatus(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取活动信息
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getActiveInfoById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getActiveInfoById(@RequestBody RequestBean bean) {
        LogHelper.info("获取活动信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.getActiveInfoById(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取活动列表
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getActiveInfoList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getActiveInfoList(@RequestBody RequestBean bean) {
        LogHelper.info("获取活动列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.getActiveInfoList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }


    /**
     * 获取client活动列表
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getActiveClientList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getActiveClientList(@RequestBody RequestBean bean) {
        LogHelper.info("获取client活动列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.getActiveClientList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 新增报名活动信息
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertRegisterActive", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertRegisterActive(@RequestBody RequestBean bean) {
        LogHelper.info("新增活动报名信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.insertRegisterActive(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 修改报名活动信息
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateRegisterActive", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateRegisterActive(@RequestBody RequestBean bean) {
        LogHelper.info("修改活动报名信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.updateRegisterActive(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取报名活动信息详情
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRegisterInfoById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getRegisterInfoById(@RequestBody RequestBean bean) {
        LogHelper.info("获取活动报名信息详情 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.getRegisterInfoById(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 修改报名活动审核状态
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateRegisterStatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateRegisterStatus(@RequestBody RequestBean bean) {
        LogHelper.info("修改报名活动审核状态 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.updateRegisterStatus(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取活动报名信息列表
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getActiveRegisterList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getActiveRegisterList(@RequestBody RequestBean bean) {
        LogHelper.info("获取活动报名信息列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.getActiveRegisterList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /*
   功能：新增问卷调查
    */
    @ResponseBody
    @RequestMapping(value = "/insertQuestionInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertQuestionInfo(@RequestBody RequestBean bean) {
        LogHelper.info("新增问卷调查信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.insertQuestionInfo(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /*
    功能：获取问卷列表
     */
    @ResponseBody
    @RequestMapping(value = "/getQuesrionList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getQuesrionList(@RequestBody RequestBean bean) {
        LogHelper.info("获取问卷列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.getQuesrionList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 新增明细
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertDetail", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertDetail(@RequestBody RequestBean bean) {
        LogHelper.info("新增明细 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.insertDetail(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }


    /**
     * 修改明细
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDetail", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateDetail(@RequestBody RequestBean bean) {
        LogHelper.info("修改明细 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.updateDetail(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取明细信息
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDetailInfoById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getDetailInfoById(@RequestBody RequestBean bean) {
        LogHelper.info("获取明细列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.getDetailInfoById(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取明细列表
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDetailInfoList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getDetailInfoList(@RequestBody RequestBean bean) {
        LogHelper.info("获取明细列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.getDetailInfoList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取活动 明细列表
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDetailInfoListByActiveId", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getDetailInfoListByActiveId(@RequestBody RequestBean bean) {
        LogHelper.info("获取活动明细列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.getDetailInfoListByActiveId(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }
    /**
     * 获取会员报名活动 明细列表
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDetailInfoListByUserId", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getDetailInfoListByuUserId(@RequestBody RequestBean bean) {
        LogHelper.info("获取会员报名活动明细列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = activeInfoService.getDetailInfoListByUserId(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }
}
