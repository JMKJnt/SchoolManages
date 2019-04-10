package com.cn.service.busService;

import org.json.JSONObject;

public interface ClassInfoService {
    /**
     * 新增学校
     * @param jsonstr
     * @return
     */
    public JSONObject insertSchool(String jsonstr);

    /**
     * 修改学校
     * @param jsonstr
     * @return
     */
    public JSONObject updateSchool(String jsonstr);

    /**
     * 获取学校信息
     * @param jsonstr
     * @return
     */
    public JSONObject getSchoolInfo(String jsonstr);

    /**
     * 获取学校列表
     * @param jsonstr
     * @return
     */
    public JSONObject getSchoolList(String jsonstr);
    /**
     * 新增班级
     * @param jsonstr
     * @return
     */
    public JSONObject insertClass(String jsonstr);

    /**
     * 修改班级
     * @param jsonstr
     * @return
     */
    public JSONObject updateClass(String jsonstr);

    /**
     * 获取班级信息
     * @param jsonstr
     * @return
     */
    public JSONObject getClassInfo(String jsonstr);

    /**
     * 获取班级列表
     * @param jsonstr
     * @return
     */
    public JSONObject getClassInfoList(String jsonstr);
    /*
    功能：删除
     */
    public JSONObject deleteStudentById(String jsonstr);

    public JSONObject updateClassTeacher(String jsonstr);
    //新增学生
    public JSONObject insertStudent(String jsonstr);

    /**
     * 新增导师信息
     * @param jsonstr
     * @return
     */
    public JSONObject insertFanousTea(String jsonstr);

    /**
     * 修改导师信息
     * @param jsonstr
     * @return
     */
    public JSONObject updateFanousTea(String jsonstr);

    /**
     * 获取导师信息
     * @param jsonstr
     * @return
     */
    public JSONObject getFanousTea(String jsonstr);

    /**
     * 获取导师列表
     * @param jsonstr
     * @return
     */
    public JSONObject getFanousTeaList(String jsonstr);

}
