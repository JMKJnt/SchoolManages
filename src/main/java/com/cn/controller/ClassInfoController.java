package com.cn.controller;

import com.cn.common.LogHelper;
import com.cn.common.MsgAndCode;
import com.cn.entiy.RequestBean;
import com.cn.service.busService.ClassInfoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequestMapping(value = "/classInfo")
public class ClassInfoController {

    @Autowired
    private ClassInfoService classInfoService;

    /**
     * 新增学校
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertSchool", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertSchool(@RequestBody RequestBean bean) {
        LogHelper.info("新增学校 请求参数：" + bean);
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.insertSchool(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 修改学校
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateSchool", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateSchool(@RequestBody RequestBean bean) {
        LogHelper.info("修改学校 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.updateSchool(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取学校信息
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSchoolInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getSchoolInfo(@RequestBody RequestBean bean) {
        LogHelper.info("获取学校信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.getSchoolInfo(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取学校列表
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSchoolList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getSchoolList(@RequestBody RequestBean bean) {
        LogHelper.info("获取学校列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.getSchoolList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }


    /**
     * 新增班级
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertClassInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertClass(@RequestBody RequestBean bean) {
        LogHelper.info("新增班级 请求参数：" + bean);
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.insertClass(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 修改班级
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateClassInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateClass(@RequestBody RequestBean bean) {
        LogHelper.info("修改班级 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.updateClass(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取班级信息
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteStudentById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String deleteStudentById(@RequestBody RequestBean bean) {
        LogHelper.info("删除学生信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.deleteStudentById(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }
    /**
     * 新增学生
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertStudent", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertStudent(@RequestBody RequestBean bean) {
        LogHelper.info("新增学生 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.insertStudent(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }
    /**
     * 获取班级信息
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateClassTeacher", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateClassTeacher(@RequestBody RequestBean bean) {
        LogHelper.info("更改班主任信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.updateClassTeacher(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取班级信息
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClassInfoById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getClassInfo(@RequestBody RequestBean bean) {
        LogHelper.info("获取班级信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.getClassInfo(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取班级列表
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClassInfoList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getClassInfoList(@RequestBody RequestBean bean) {
        LogHelper.info("获取班级列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.getClassInfoList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 新增管导师
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertFanousTea", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertFanousTea(@RequestBody RequestBean bean) {
        LogHelper.info("新增导师 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.insertFanousTea(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 修改导师
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateFanousTea", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String updateFanousTea(@RequestBody RequestBean bean) {
        LogHelper.info("修改导师请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.updateFanousTea(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取导师信息
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getFanousTeaInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getFanousTea(@RequestBody RequestBean bean) {
        LogHelper.info("获取导师信息 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.getFanousTea(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取导师列表
     *
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getFanousTeaList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getFanousTeaList(@RequestBody RequestBean bean) {
        LogHelper.info("获取导师列表 请求参数：" + bean.toString());
        JSONObject result = new JSONObject();
        try {
            result = classInfoService.getFanousTeaList(bean.getJsonStr().toString());
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result.toString();
    }


}
