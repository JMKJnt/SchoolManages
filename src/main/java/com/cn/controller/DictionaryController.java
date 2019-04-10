package com.cn.controller;

import com.cn.common.LogHelper;
import com.cn.common.MsgAndCode;
import com.cn.service.busService.DictionaryService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequestMapping("/dicDataInfo")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;


    /**
     * 根据dataDictionary获取码表字表信息列表
     * 不加@RequestBody获取不到参数
     */
    @ResponseBody
    @RequestMapping(value = "/getDictionaryDataInfoList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getBugsinessList(@RequestBody String jsonStr) {
        LogHelper.info("根据dataDictionary获取码表字表信息列表 请求参数：" + jsonStr);
        String result = "";
        JSONObject resultJo = new JSONObject();
        try {
            JSONObject jo=new JSONObject(jsonStr);
            result = dictionaryService.GetDictionaryDataInfoList(jo.getString("jsonStr"));
        } catch (Exception e) {
            resultJo.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            resultJo.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            result = resultJo.toString();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 新增码表子表信息
     */
    @ResponseBody
    @RequestMapping(value = "/insertDictionaryDataInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String InsertDictionaryDataInfo(@RequestBody String jsonStr) {
        LogHelper.info("根据dataDictionary获取码表字表信息列表 请求参数：" + jsonStr);
        String result = "";
        JSONObject resultJo = new JSONObject();
        try {
            JSONObject jo=new JSONObject(jsonStr);
            result = dictionaryService.InsertDictionaryDataInfo(jo.getString("jsonStr"));
        } catch (Exception e) {
            resultJo.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            resultJo.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            result = resultJo.toString();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改码表子表信息
     */
    @ResponseBody
    @RequestMapping(value = "/updateDictionaryDataInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String UpdateDictionaryDataInfo(@RequestBody String jsonStr) {
        LogHelper.info("根据dataDictionary获取码表字表信息列表 请求参数：" + jsonStr);
        String result = "";
        JSONObject resultJo = new JSONObject();
        try {
            JSONObject jo=new JSONObject(jsonStr);
            result = dictionaryService.UpdateDictionaryDataInfo(jo.getString("jsonStr"));
        } catch (Exception e) {
            resultJo.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            resultJo.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            result = resultJo.toString();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取码表主表信息
     */
    @ResponseBody
    @RequestMapping(value = "/getDictionaryInfoList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String GetDictionaryInfoList(@RequestBody String jsonStr) {
        LogHelper.info("根据dataDictionary获取码表字表信息列表 请求参数：" + jsonStr);
        String result = "";
        JSONObject resultJo = new JSONObject();
        try {
            JSONObject jo=new JSONObject(jsonStr);
            result = dictionaryService.GetDictionaryInfoList(jo.getString("jsonStr"));
        } catch (Exception e) {
            resultJo.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            resultJo.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            result = resultJo.toString();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 新增码表主表信息
     */
    @ResponseBody
    @RequestMapping(value = "/insertDictionaryInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String InsertDictionaryInfo(@RequestBody String jsonStr) {
        LogHelper.info("根据dataDictionary获取码表字表信息列表 请求参数：" + jsonStr);
        String result = "";
        JSONObject resultJo = new JSONObject();
        try {
            JSONObject jo=new JSONObject(jsonStr);
            result = dictionaryService.InsertDictionaryInfo(jo.getString("jsonStr"));
        } catch (Exception e) {
            resultJo.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            resultJo.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            result = resultJo.toString();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改码表主表信息
     */
    @ResponseBody
    @RequestMapping(value = "/updateDictionaryInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String UpdateDictionaryInfo(@RequestBody String jsonStr) {
        LogHelper.info("根据dataDictionary获取码表字表信息列表 请求参数：" + jsonStr);
        String result = "";
        JSONObject resultJo = new JSONObject();
        try {
            JSONObject jo=new JSONObject(jsonStr);
            result = dictionaryService.UpdateDictionaryInfo(jo.getString("jsonStr"));
        } catch (Exception e) {
            resultJo.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            resultJo.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            result = resultJo.toString();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据码表code获取码表详细信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/getDictionaryDataList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String GetDictionaryDataList(@RequestBody String jsonStr) {
        LogHelper.info("根据dataDictionary获取码表字表信息列表 请求参数：" + jsonStr);
        String result = "";
        JSONObject resultJo = new JSONObject();
        try {
            JSONObject jo=new JSONObject(jsonStr);
            result = dictionaryService.GetDictionaryDataList(jo.getString("jsonStr"));
        } catch (Exception e) {
            resultJo.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            resultJo.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            result = resultJo.toString();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据id获取码表主表信息
     */
    @ResponseBody
    @RequestMapping(value = "/getDictionaryInfoByid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String GetDictionaryInfoByid(@RequestBody String jsonStr) {
        LogHelper.info("根据dataDictionary获取码表字表信息列表 请求参数：" + jsonStr);
        String result = "";
        JSONObject resultJo = new JSONObject();
        try {
            JSONObject jo=new JSONObject(jsonStr);
            result = dictionaryService.GetDictionaryInfoByid(jo.getString("jsonStr"));
        } catch (Exception e) {
            resultJo.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            resultJo.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            result = resultJo.toString();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据id获取码表子表信息
     */
    @ResponseBody
    @RequestMapping(value = "/getDictionaryDataInfoByid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String GetDictionaryDataInfoByid(@RequestBody String jsonStr) {
        LogHelper.info("根据dataDictionary获取码表字表信息列表 请求参数：" + jsonStr);
        String result = "";
        JSONObject resultJo = new JSONObject();
        try {
            JSONObject jo=new JSONObject(jsonStr);
            result = dictionaryService.GetDictionaryDataInfoByid(jo.getString("jsonStr"));
        } catch (Exception e) {
            resultJo.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            resultJo.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            result = resultJo.toString();
            e.printStackTrace();
        }
        return result;
    }

}
