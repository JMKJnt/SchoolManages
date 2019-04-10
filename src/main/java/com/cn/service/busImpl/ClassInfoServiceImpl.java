package com.cn.service.busImpl;

import com.cn.common.CommonHelper;
import com.cn.common.MsgAndCode;
import com.cn.common.Utils;
import com.cn.dao.interfaceDao.BaseEntityDao;
import com.cn.entiy.*;
import com.cn.service.busService.ClassInfoService;
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
public class ClassInfoServiceImpl implements ClassInfoService {
    Logger logger = Logger.getLogger(ClassInfoService.class);
    /*
       变量：码表dao实现
      */
    @Autowired
    private BaseEntityDao baseEntityDao;
    @Autowired
    private DictionaryServiceImpl dictionaryServiceImpl;

    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd HHmmss");
    private int total;

    /**
     * 新增学校
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertSchool(String jsonstr) {
        logger.info("新增学校接口信息insertSchool--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String schoolName = jo.getString("schoolName");//学校名称
        String schoolProvince = jo.getString("schoolProvince");//省名称
        String schoolCity = jo.getString("schoolCity");//市id
        String schoolCounty = jo.getString("schoolCounty");//县
        String schoolDesc = jo.getString("schoolDesc");//学校描述
        String schoolAddress = jo.getString("schoolAddress");//学校地址
        String schoolCreate = jo.getString("schoolCreate");//创建人
        String schoolType = jo.getString("schoolType");//学校属性 1 城市 2 农村
        String schoolShortName = jo.getString("schoolShortName");//学校简称
        String status = jo.getString("status");
        if (Utils.isEmpty(schoolName) || Utils.isEmpty(schoolProvince) || Utils.isEmpty(schoolCity) ||
                Utils.isEmpty(schoolCounty) || Utils.isEmpty(schoolDesc) || Utils.isEmpty(schoolShortName) || Utils.isEmpty(schoolType)
                || Utils.isEmpty(status) || Utils.isEmpty(schoolAddress) || Utils.isEmpty(schoolCreate)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存
                SchoolInfo schoolInfo = new SchoolInfo();
                schoolInfo.setSchoolName(schoolName);
                schoolInfo.setSchoolProvince(schoolProvince);
                schoolInfo.setSchoolCity(schoolCity);
                schoolInfo.setSchoolCounty(schoolCounty);
                schoolInfo.setSchoolDesc(schoolDesc);
                schoolInfo.setSchoolAddress(schoolAddress);
                schoolInfo.setSchoolCreateName(schoolCreate);
                schoolInfo.setSchoolCreateTime(new Date());
                schoolInfo.setSchoolUpdateName(schoolCreate);
                schoolInfo.setSchoolCreateTime(new Date());
                schoolInfo.setSchoolType(schoolType);
                schoolInfo.setSchoolShortName(schoolShortName);
                schoolInfo.setStatus(status);
                String resultStr = baseEntityDao.saveOrUpdate(schoolInfo);
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
     * 修改学校
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateSchool(String jsonstr) {
        logger.info("修改学校接口信息updateSchool--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String schoolId = jo.getString("schoolId");//学校id
        String schoolName = jo.getString("schoolName");//学校名称
        String schoolProvince = jo.getString("schoolProvince");//省名称
        String schoolCity = jo.getString("schoolCity");//市id
        String schoolCounty = jo.getString("schoolCounty");//县
        String schoolDesc = jo.getString("schoolDesc");//学校描述
        String schoolAddress = jo.getString("schoolAddress");//学校地址
        String schoolUpdate = jo.getString("schoolUpdate");//创建人
        String schoolType = jo.getString("schoolType");//学校属性 1 城市 2 农村
        String schoolShortName = jo.getString("schoolShortName");//学校简称
        String status = jo.getString("status");
        if (Utils.isEmpty(schoolName) || Utils.isEmpty(schoolProvince) || Utils.isEmpty(schoolCity) ||
                Utils.isEmpty(schoolCounty) || Utils.isEmpty(schoolDesc) || Utils.isEmpty(schoolId)
                || Utils.isEmpty(status) || Utils.isEmpty(schoolAddress) || Utils.isEmpty(schoolUpdate)
                || Utils.isEmpty(schoolShortName) || Utils.isEmpty(schoolType)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存
                SchoolInfo schoolInfo = baseEntityDao.listById(SchoolInfo.class, schoolId, false);
                schoolInfo = schoolInfo == null ? new SchoolInfo() : schoolInfo;
                schoolInfo.setSchoolName(schoolName);
                schoolInfo.setSchoolProvince(schoolProvince);
                schoolInfo.setSchoolCity(schoolCity);
                schoolInfo.setSchoolCounty(schoolCounty);
                schoolInfo.setSchoolDesc(schoolDesc);
                schoolInfo.setSchoolAddress(schoolAddress);
                schoolInfo.setSchoolType(schoolType);
                schoolInfo.setSchoolShortName(schoolShortName);
                schoolInfo.setSchoolUpdateName(schoolUpdate);
                schoolInfo.setSchoolCreateTime(new Date());
                schoolInfo.setStatus(status);
                String resultStr = baseEntityDao.saveOrUpdate(schoolInfo);
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
     * 获取学校信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getSchoolInfo(String jsonstr) {
        logger.info("获取学校接口信息getSchoolInfo--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String schoolId = jo.getString("schoolId");//学校id
        if (Utils.isEmpty(schoolId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存
                SchoolInfo schoolInfo = baseEntityDao.listById(SchoolInfo.class, schoolId, false);
                if (schoolInfo == null) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_004_MSG);
                } else {
                    result.put("schoolName", schoolInfo.getSchoolName());
                    result.put("schoolProvince", schoolInfo.getSchoolProvince());
                    result.put("schoolCity", schoolInfo.getSchoolCity());
                    result.put("schoolCounty", schoolInfo.getSchoolCounty());
                    result.put("schoolDesc", schoolInfo.getSchoolDesc());
                    result.put("schoolAddress", schoolInfo.getSchoolAddress());
                    result.put("schoolType", schoolInfo.getSchoolType());
                    result.put("schoolShortName", schoolInfo.getSchoolShortName());
                    result.put("status", schoolInfo.getStatus());
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
     * 获取学校列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getSchoolList(String jsonstr) {
        logger.info("获取学校接口信息getSchoolInfo--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String schoolName = jo.has("schoolName") ? jo.getString("schoolName") : "";//学校名称
        String schoolProvince = jo.has("schoolProvince") ? jo.getString("schoolProvince") : "";//学校名称
        String schoolCity = jo.has("schoolCity") ? jo.getString("schoolCity") : "";//学校名称
        String schoolCounty = jo.has("schoolCounty") ? jo.getString("schoolCounty") : "";//学校名称
        String status = jo.has("status") ? jo.getString("status") : "";//学校名称
        String curragePage = jo.getString("curragePage");//当前页
        String pageSize = jo.getString("pageSize");//每页记录数
        try {
            //保存
            Conjunction cn = Restrictions.conjunction();
            if (!Utils.isEmpty(schoolName)) {
                cn.add(Restrictions.like("schoolName", "%" + schoolName + "%"));
            }
            if (!Utils.isEmpty(schoolProvince)) {
                cn.add(Restrictions.eq("schoolProvince", schoolProvince));
            }
            if (!Utils.isEmpty(schoolCity)) {
                cn.add(Restrictions.eq("schoolCity", schoolCity));
            }
            if (!Utils.isEmpty(schoolCounty)) {
                cn.add(Restrictions.eq("schoolCounty", schoolCounty));
            }
            if (!Utils.isEmpty(status)) {
                cn.add(Restrictions.eq("status", status));
            }
            int counts = baseEntityDao.countByCriteria(SchoolInfo.class, cn, false);
            List<SchoolInfo> schoolInfoList = baseEntityDao.listByPageCriteria(SchoolInfo.class, cn, Integer.parseInt(pageSize),
                    Integer.parseInt(curragePage) - 1, false);
            JSONArray lists = new JSONArray();
            if (schoolInfoList != null && schoolInfoList.size() > 0) {
                JSONObject obj = null;
                for (SchoolInfo schoolInfo : schoolInfoList) {
                    obj = new JSONObject();
                    obj.put("schoolId", schoolInfo.getSchoolId());
                    obj.put("schoolName", schoolInfo.getSchoolName());
                    obj.put("schoolProvince", schoolInfo.getSchoolProvince());
                    obj.put("schoolCity", schoolInfo.getSchoolCity());
                    obj.put("schoolCounty", schoolInfo.getSchoolCounty());
                    obj.put("schoolDesc", schoolInfo.getSchoolDesc());
                    obj.put("schoolAddress", schoolInfo.getSchoolAddress());
                    obj.put("schoolType", schoolInfo.getSchoolType());
                    obj.put("schoolShortName", schoolInfo.getSchoolShortName());
                    obj.put("status", schoolInfo.getStatus());
                    lists.put(obj);
                }
            }
            result.put("total", counts);
            result.put("curragePage", curragePage);
            result.put("pageSize", pageSize);
            result.put("lists", lists);
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 新增班级
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertClass(String jsonstr) {
        logger.info("新增班级接口信息insertClass--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String className = jo.getString("className");//班级名称
        String classSchool = jo.getString("classSchool");//学校名称
        String classTeacher = jo.getString("classTeacher");//班主任id
        String classDesc = jo.getString("classDesc");//班级描述
        String classSchoolAddress = jo.getString("classSchoolAddress");//学校地址
        String classCreate = jo.getString("classCreate");//创建人
        String status = jo.getString("status");
        String classLevel = jo.getString("classLevel");//班级级别
        String classDivision = jo.getString("classDivision");//班级划分
        JSONArray paramJO = jo.getJSONArray("studentList");
        if (Utils.isEmpty(className) || Utils.isEmpty(classSchool) || Utils.isEmpty(classTeacher) ||
                Utils.isEmpty(classDesc) || Utils.isEmpty(classSchoolAddress) || Utils.isEmpty(classCreate)
                || Utils.isEmpty(status) || Utils.isEmpty(classLevel) || Utils.isEmpty(classDivision)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存
                ClassInfo classInfo = new ClassInfo();
                classInfo.setClassName(className);
                classInfo.setClassSchool(classSchool);
                classInfo.setClssTeacher(classTeacher);
                classInfo.setClassSchoolAddress(classSchoolAddress);
                classInfo.setClassDesc(classDesc);
                classInfo.setClassCreateName(classCreate);
                classInfo.setClassLevel(classLevel);
                classInfo.setClassDivision(classDivision);
                //获取班级编号,根据选择的班级划分来显示首字母，如果
                String headStr = classDivision.equals("1") ? "A" : classDivision.equals("2") ? "B" : classDivision.equals("3") ? "C" :
                        classDivision.equals("4") ? "D" : classDivision.equals("5") ? "E" : classDivision.equals("6") ? "F" : "";
                classInfo.setClassNum(CommonHelper.getRandomStr(headStr, 4));
                classInfo.setClassCreateTime(new Date());
                classInfo.setClassUpdateName(classCreate);
                classInfo.setClassCreateTime(new Date());
                classInfo.setStatus(status);
                String resultStr = baseEntityDao.saveOrUpdate(classInfo);
                if (resultStr.equals("success")) {
                    //根据班级ID 插入学生信息
                    ClassStudentInfo classStudentInfo = null;
                    for (int i = 0; i < paramJO.length(); i++) {
                        JSONObject obj = paramJO.getJSONObject(i);
                        classStudentInfo = new ClassStudentInfo();
                        classStudentInfo.setClassId(classInfo.getClassId());
                        classStudentInfo.setClassStudent(obj.getString("studentId"));
                        classStudentInfo.setClassCreateName(classCreate);
                        classStudentInfo.setClassCreateTime(new Date());
                        classStudentInfo.setClassUpdateName(classCreate);
                        classStudentInfo.setClassCreateTime(new Date());
                        classStudentInfo.setStatus(status);
                        baseEntityDao.saveOrUpdate(classStudentInfo);
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
     * 新增学生
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertStudent(String jsonstr) {
        logger.info("新增学生接口信息insertStudent--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String classId = jo.getString("classId");//班级名称
        String studentId = jo.getString("studentId");//学校名称
        String createName = jo.getString("create");
        if (Utils.isEmpty(studentId) || Utils.isEmpty(classId) || Utils.isEmpty(createName)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存
                ClassInfo classInfo = baseEntityDao.listById(ClassInfo.class, classId, false);
                if (classInfo != null) {
                    //根据班级ID 插入学生信息
                    ClassStudentInfo classStudentInfo = new ClassStudentInfo();
                    classStudentInfo.setClassId(classInfo.getClassId());
                    classStudentInfo.setClassStudent(studentId);
                    classStudentInfo.setClassCreateName(createName);
                    classStudentInfo.setClassCreateTime(new Date());
                    classStudentInfo.setClassUpdateName(createName);
                    classStudentInfo.setClassCreateTime(new Date());
                    classStudentInfo.setStatus("1");
                    String resultStr = baseEntityDao.saveOrUpdate(classStudentInfo);
                    if (resultStr.equals("success")) {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                    }
                    else {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                    }
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
        return result;
    }

    /**
     * 修改班级
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateClass(String jsonstr) {
        logger.info("修改班级接口信息updateClass--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String classId = jo.getString("classId");//班级id
        String className = jo.getString("className");//班级名称
        String classSchool = jo.getString("classSchool");//学校名称
        String classTeacher = jo.getString("classTeacher");//班主任id
        String classDesc = jo.getString("classDesc");//班级描述
        String classSchoolAddress = jo.getString("classSchoolAddress");//学校地址
        String classUpdate = jo.getString("classUpdate");//修改人
        String classLevel = jo.getString("classLevel");//班级级别
        String classDivision = jo.getString("classDivision");//班级划分
        String status = jo.getString("status");
        JSONArray paramJO = jo.getJSONArray("studentList");
        if (Utils.isEmpty(classId) || Utils.isEmpty(className) || Utils.isEmpty(classSchool) || Utils.isEmpty(classTeacher) ||
                Utils.isEmpty(classDesc) || Utils.isEmpty(classSchoolAddress) || Utils.isEmpty(classUpdate) ||
                Utils.isEmpty(status) || paramJO.length() <= 0 || Utils.isEmpty(classLevel) || Utils.isEmpty(classDivision)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存
                ClassInfo classInfo = baseEntityDao.listById(ClassInfo.class, classId, false);
                classInfo = classInfo == null ? new ClassInfo() : classInfo;
                classInfo.setClassName(className);
                classInfo.setClassSchool(classSchool);
                classInfo.setClssTeacher(classTeacher);
                classInfo.setClassSchoolAddress(classSchoolAddress);
                classInfo.setClassDesc(classDesc);
                classInfo.setClassLevel(classLevel);
                classInfo.setClassDivision(classDivision);
                classInfo.setClassUpdateName(classUpdate);
                classInfo.setClassUpdateTime(new Date());
                classInfo.setStatus(status);
                String resultStr = baseEntityDao.saveOrUpdate(classInfo);
                if (resultStr.equals("success")) {
                    //根据班级ID删除学生信息
                    baseEntityDao.deleteEntityById("ClassStudentInfo", "classId", classId);
                    //根据班级ID 插入学生信息
                    ClassStudentInfo classStudentInfo = null;
                    for (int i = 0; i < paramJO.length(); i++) {
                        JSONObject obj = paramJO.getJSONObject(i);
                        classStudentInfo = new ClassStudentInfo();
                        classStudentInfo.setClassId(classId);
                        classStudentInfo.setClassStudent(obj.getString("studentId"));
                        classStudentInfo.setClassUpdateName(classUpdate);
                        classStudentInfo.setClassCreateTime(new Date());
                        classStudentInfo.setStatus(status);
                        baseEntityDao.saveOrUpdate(classStudentInfo);
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

    public JSONObject updateClassTeacher(String jsonstr) {
        logger.info("修改班级接口信息updateClassTeacher--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String classId = jo.getString("classId");//班级id
        String classTeacher = jo.getString("classTeacher");//班主任id
        String classUpdate = jo.getString("classUpdate");//修改人

        if (Utils.isEmpty(classId) || Utils.isEmpty(classUpdate) || Utils.isEmpty(classTeacher)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存
                ClassInfo classInfo = baseEntityDao.listById(ClassInfo.class, classId, false);
                if (classInfo == null) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_004_MSG);
                } else {
                    classInfo.setClssTeacher(classTeacher);
                    classInfo.setClassUpdateName(classUpdate);
                    classInfo.setClassUpdateTime(new Date());
                    String resultStr = baseEntityDao.saveOrUpdate(classInfo);
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
     * 获取班级信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getClassInfo(String jsonstr) {
        logger.info("获取班级信息接口信息getClassInfo--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String classId = jo.getString("classId");//班级id
        if (Utils.isEmpty(classId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //根据ID查询班级信息，关联教师名称
                ClassInfo classInfo = baseEntityDao.listById(ClassInfo.class, classId, false);
                if (classInfo == null) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_004_MSG);
                } else {
                    //根据班主任ID获取班主任信息 用户表，角色=老师
                    Conjunction cn = Restrictions.conjunction();
                    cn.add(Restrictions.eq("userId", classInfo.getClssTeacher()));
                    cn.add(Restrictions.eq("userRoleType", "2"));
                    List<UserInfo> userInfoList = baseEntityDao.listByCriteria(UserInfo.class, cn, false);
                    if (userInfoList != null && userInfoList.size() > 0) {
                        result.put("classTeacherName", userInfoList.get(0).getUserName());
                    }
                    result.put("classId", classInfo.getClassId());
                    result.put("className", classInfo.getClassName());
                    result.put("classSchool", classInfo.getClassSchool());
                    result.put("classTeacher", classInfo.getClssTeacher());
                    result.put("classSchoolAddress", classInfo.getClassSchoolAddress());
                    result.put("classDesc", classInfo.getClassDesc());
                    result.put("classUpdate", classInfo.getClassUpdateName());
                    result.put("classCreate", classInfo.getClassCreateName());
                    result.put("classLevel", classInfo.getClassLevel());
                    result.put("classDivision", classInfo.getClassDivision());
                    result.put("status", classInfo.getStatus());
                    //根据班级ID查询班级学生信息列表
                    StringBuilder strsql = new StringBuilder("SELECT CS.CLASS_STUDENT,U.USER_NAME,U.USER_PHONE,CS.CLASS_S_ID FROM CLASS_STUDENT_INFO CS");
                    strsql.append(" LEFT JOIN USER_INFO U ON U.USER_ID=CS.CLASS_STUDENT WHERE CS.CLASS_ID='" + classInfo.getClassId() + "'");
                    List<Map<String, Object>> classList = baseEntityDao.listBySQL(strsql.toString(), false);
                    JSONArray Jarry = new JSONArray();
                    if (classList != null && classList.size() > 0) {
                        JSONObject job = null;
                        for (int i = 0; i < classList.size(); i++) {
                            job = new JSONObject();
                            Map<String, Object> map = classList.get(i);
                            job.put("studentId", map.get("CLASS_STUDENT").toString());
                            job.put("studentName", map.get("USER_NAME").toString());
                            job.put("studentPhone", map.get("USER_PHONE").toString());
                            job.put("classStudentId", map.get("CLASS_S_ID").toString());
                            Jarry.put(job);
                        }
                    }
                    result.put("studentList", Jarry);
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
     * 获取班级列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getClassInfoList(String jsonstr) {
        logger.info("获取班级列表接口信息getClassInfoList--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String className = jo.has("className") ? jo.getString("className") : "";//班级名称
        String classTeacher = jo.has("classTeacher") ? jo.getString("classTeacher") : "";//班主任ID
        String classSchool = jo.has("classSchool") ? jo.getString("classSchool") : "";//学校
        String studentId = jo.has("studentId") ? jo.getString("studentId") : "";
        String curragePage = jo.getString("curragePage");//当前页
        String pageSize = jo.getString("pageSize");//每页记录数
        if (Utils.isEmpty(curragePage) || Utils.isEmpty(pageSize)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //根据条件查询班级列表
                StringBuffer sqlCount = new StringBuffer("SELECT COUNT(*) SUM FROM  CLASS_INFO C ");
                sqlCount.append(" LEFT JOIN SCHOOL_INFO S ON S.SCHOOL_ID=C.CLASS_SCHOOL ");
                sqlCount.append("LEFT JOIN USER_INFO U ON U.USER_ID=C.CLASS_TEACHER  WHERE  U.USER_ROLETYPE LIKE '%2%' ");
                if (!Utils.isEmpty(className)) {
                    sqlCount.append(" AND C.CLASS_NAME LIKE '%" + className + "%'");
                }
                if (!Utils.isEmpty(classTeacher)) {
                    sqlCount.append(" AND C.CLASS_TEACHER='" + classTeacher + "'");
                }
                if (!Utils.isEmpty(classSchool)) {
                    sqlCount.append(" AND C.CLASS_SCHOOL='" + classSchool + "'");
                }
                if (!Utils.isEmpty(studentId)) {
                    sqlCount.append(" AND C.CLASS_ID IN (SELECT CS.CLASS_ID  FROM CLASS_STUDENT_INFO CS WHERE CS.CLASS_ID =C.CLASS_ID ");
                    sqlCount.append("  AND CS.CLASS_STUDENT ='" + studentId + "')");
                }
                int total = baseEntityDao.CountBySQL(sqlCount.toString());

                //写sql查询数据
                StringBuffer sql = new StringBuffer("SELECT C.CLASS_ID,C.CLASS_NAME,C.CLASS_SCHOOL,S.SCHOOL_NAME,C.CLASS_TEACHER, C.CLASS_DESC, C.STATUS,  ");
                sql.append(" C.CLASS_SCHOOL_ADDRESS,C.CLASS_CREATENAME,C.CLASS_UPDATENAME,C.CLASS_NUM ,C.CLASS_LEVEL,C.CLASS_DIVISION,U.USER_NAME CLASS_TEACHER_NAME FROM CLASS_INFO C  ");
                sql.append(" LEFT JOIN SCHOOL_INFO S ON S.SCHOOL_ID=C.CLASS_SCHOOL ");
                sql.append(" LEFT JOIN USER_INFO U ON U.USER_ID=C.CLASS_TEACHER  WHERE U.USER_ROLETYPE LIKE '%2%'  ");
                if (!Utils.isEmpty(className)) {
                    sql.append(" AND C.CLASS_NAME LIKE '%" + className + "%'");
                }
                if (!Utils.isEmpty(classTeacher)) {
                    sql.append(" AND C.CLASS_TEACHER='" + classTeacher + "'");
                }
                if (!Utils.isEmpty(classSchool)) {
                    sql.append(" AND C.CLASS_SCHOOL='" + classSchool + "'");
                }
                if (!Utils.isEmpty(studentId)) {
                    sqlCount.append(" AND C.CLASS_ID IN (SELECT CS.CLASS_ID  FROM CLASS_STUDENT_INFO CS WHERE CS.CLASS_ID =C.CLASS_ID ");
                    sqlCount.append("  AND CS.CLASS_STUDENT ='" + studentId + "')");
                }

                List<Map<String, Object>> list = baseEntityDao.listByPageBySQL(sql.toString(), Integer.parseInt(pageSize),
                        Integer.parseInt(curragePage) - 1, false);
                JSONArray array = new JSONArray();
                if (list != null && list.size() > 0) {
                    //循环添加数据到lists
                    JSONObject ojT = null;
                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> map = list.get(i);
                        ojT = new JSONObject();
                        ojT.put("classId", map.get("CLASS_ID").toString());
                        ojT.put("classTeacherName", map.get("CLASS_TEACHER_NAME").toString());
                        ojT.put("className", map.get("CLASS_NAME").toString());
                        ojT.put("classSchool", map.get("CLASS_SCHOOL").toString());
                        ojT.put("schoolName", map.get("SCHOOL_NAME").toString());
                        ojT.put("classTeacher", map.get("CLASS_TEACHER").toString());
                        ojT.put("classSchoolAddress", map.get("CLASS_SCHOOL_ADDRESS").toString());
                        ojT.put("classDesc", map.get("CLASS_DESC").toString());
                        ojT.put("classLevel", map.get("CLASS_LEVEL") == null ? "" : map.get("CLASS_LEVEL").toString());
                        ojT.put("classNum", map.get("CLASS_NUM") == null ? "" : map.get("CLASS_NUM").toString());
                        ojT.put("classDivision", map.get("CLASS_DIVISION") == null ? "" : map.get("CLASS_DIVISION").toString());
                        ojT.put("classUpdate", map.get("CLASS_UPDATENAME").toString());
                        ojT.put("classCreate", map.get("CLASS_CREATENAME").toString());
                        ojT.put("status", map.get("STATUS").toString());
                        array.put(ojT);
                    }
                }
                result.put("total", total);
                result.put("curragePage", curragePage);
                result.put("pageSize", pageSize);
                result.put("lists", array);
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

    /*
    功能：删除
     */
    public JSONObject deleteStudentById(String jsonstr) {
        logger.info("删除信息接口信息deleteStudentById--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String classStudentId = jo.getString("classStudentId");
        if (Utils.isEmpty(classStudentId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                Boolean resultStr = baseEntityDao.deleteById(ClassStudentInfo.class, classStudentId);
                if (resultStr) {
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
     * 新增导师信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertFanousTea(String jsonstr) {
        logger.info("新增导师信息接口信息insertFanousTea--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String teacherName = jo.getString("teacherName");//导师名称
        String teacherImg = jo.has("teacherImg") ? jo.getString("teacherImg") : "";//导师照片
        String teacherSex = jo.getString("teacherSex");//性别
        String teacherBirthday = jo.has("teacherBirthday") ? jo.getString("teacherBirthday") : "";//班级描述
        String teacherPhone = jo.getString("teacherPhone");//导师电话
        String teacherQualification = jo.getString("teacherQualification");//资质说明
        String teacherKeyword = jo.getString("teacherKeyword");//关键字
        String teacherCreate = jo.getString("teacherCreate");//创建人
        String status = jo.getString("status");
        if (Utils.isEmpty(teacherName) || Utils.isEmpty(teacherSex) || Utils.isEmpty(teacherPhone) ||
                Utils.isEmpty(teacherQualification) || Utils.isEmpty(teacherKeyword) || Utils.isEmpty(teacherCreate) ||
                Utils.isEmpty(status)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存
                FamousTeacherInfo famousTeacherInfo = new FamousTeacherInfo();
                famousTeacherInfo.setTeacherName(teacherName);
                famousTeacherInfo.setTeacherImg(teacherImg);
                famousTeacherInfo.setTeacherSex(teacherSex);
                famousTeacherInfo.setTeacherBirthday(teacherBirthday);
                famousTeacherInfo.setTeacherPhone(teacherPhone);
                famousTeacherInfo.setTeacherQualification(teacherQualification);
                famousTeacherInfo.setTeacherSearchKeyword(teacherKeyword);
                famousTeacherInfo.setTeacherCreateName(teacherCreate);
                famousTeacherInfo.setTeacherCreateTime(new Date());
                famousTeacherInfo.setTeacherUpdateName(teacherCreate);
                famousTeacherInfo.setTeacherUpdateTime(new Date());
                famousTeacherInfo.setStatus(status);
                String resultStr = baseEntityDao.saveOrUpdate(famousTeacherInfo);
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
     * 修改导师信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateFanousTea(String jsonstr) {
        logger.info("修改导师信息接口信息updateFanousTea--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String teacherId = jo.getString("teacherId");
        String teacherName = jo.getString("teacherName");//导师名称
        String teacherImg = jo.has("teacherImg") ? jo.getString("teacherImg") : "";//导师照片
        String teacherSex = jo.getString("teacherSex");//性别
        String teacherBirthday = jo.has("teacherBirthday") ? jo.getString("teacherBirthday") : "";//班级描述
        String teacherPhone = jo.getString("teacherPhone");//导师电话
        String teacherQualification = jo.getString("teacherQualification");//资质说明
        String teacherKeyword = jo.getString("teacherKeyword");//关键字
        String teacherUpdate = jo.getString("teacherUpdate");//修改人
        String status = jo.getString("status");
        if (Utils.isEmpty(teacherId) || Utils.isEmpty(teacherName) || Utils.isEmpty(teacherSex) || Utils.isEmpty(teacherPhone) ||
                Utils.isEmpty(teacherQualification) || Utils.isEmpty(teacherKeyword) || Utils.isEmpty(teacherUpdate) ||
                Utils.isEmpty(status)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                FamousTeacherInfo famousTeacherInfo = baseEntityDao.listById(FamousTeacherInfo.class, teacherId, false);
                //保存
                famousTeacherInfo = famousTeacherInfo == null ? new FamousTeacherInfo() : famousTeacherInfo;
                famousTeacherInfo.setTeacherName(teacherName);
                famousTeacherInfo.setTeacherImg(teacherImg);
                famousTeacherInfo.setTeacherSex(teacherSex);
                famousTeacherInfo.setTeacherBirthday(teacherBirthday);
                famousTeacherInfo.setTeacherPhone(teacherPhone);
                famousTeacherInfo.setTeacherQualification(teacherQualification);
                famousTeacherInfo.setTeacherSearchKeyword(teacherKeyword);
                famousTeacherInfo.setTeacherUpdateName(teacherUpdate);
                famousTeacherInfo.setTeacherUpdateTime(new Date());
                famousTeacherInfo.setStatus(status);
                String resultStr = baseEntityDao.saveOrUpdate(famousTeacherInfo);
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
     * 获取导师信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getFanousTea(String jsonstr) {
        logger.info("获取导师信息接口信息getFanousTea--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String teacherId = jo.getString("teacherId");
        if (Utils.isEmpty(teacherId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                FamousTeacherInfo famousTeacherInfo = baseEntityDao.listById(FamousTeacherInfo.class, teacherId, false);
                if (famousTeacherInfo == null) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_004_MSG);
                } else {
                    //保存
                    result.put("teacherId", famousTeacherInfo.getTeacherId());
                    result.put("teacherName", famousTeacherInfo.getTeacherName());
                    result.put("teacherImg", famousTeacherInfo.getTeacherImg());
                    result.put("teacherSex", famousTeacherInfo.getTeacherSex());
                    result.put("teacherBirthday", famousTeacherInfo.getTeacherBirthday());
                    result.put("teacherPhone", famousTeacherInfo.getTeacherPhone());
                    result.put("teacherQualification", famousTeacherInfo.getTeacherQualification());
                    result.put("teacherKeyword", famousTeacherInfo.getTeacherSearchKeyword());
                    result.put("teacherUpdate", famousTeacherInfo.getTeacherUpdateName());
                    result.put("status", famousTeacherInfo.getStatus());
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
     * 获取导师列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getFanousTeaList(String jsonstr) {
        logger.info("获取导师列表接口信息getFanousTeaList--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String teacherName = jo.has("teacherName") ? jo.getString("teacherName") : "";//导师名称
        String teacherKeyword = jo.has("teacherKeyword") ? jo.getString("teacherKeyword") : "";//关键字
        String teacherPhone = jo.has("teacherPhone") ? jo.getString("teacherPhone") : "";//手机号
        String teacherQualification = jo.has("teacherQualification") ? jo.getString("teacherQualification") : "";//资质说明
        String status = jo.has("status") ? jo.getString("status") : "";//状态  启用禁用
        String curragePage = jo.getString("curragePage");//当前页
        String pageSize = jo.getString("pageSize");//每页记录数
        if (Utils.isEmpty(curragePage) || Utils.isEmpty(pageSize)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                Conjunction cn = Restrictions.conjunction();
                if (!teacherName.equals("")) {
                    cn.add(Restrictions.like("teacherName", "%" + teacherName + "%"));
                }
                if (!teacherKeyword.equals("")) {
                    cn.add(Restrictions.like("teacherSearchKeyword", "%" + teacherKeyword + "%"));
                }
                if (!teacherQualification.equals("")) {
                    cn.add(Restrictions.like("teacherQualification", "%" + teacherQualification + "%"));
                }
                if (!teacherPhone.equals("")) {
                    cn.add(Restrictions.eq("teacherPhone", teacherPhone));
                }
                if (!status.equals("")) {
                    cn.add(Restrictions.eq("status", status));
                }
                int total = baseEntityDao.countByCriteria(FamousTeacherInfo.class, cn, false);
                List<FamousTeacherInfo> famousTeacherInfoList = baseEntityDao.listByPageCriteria(FamousTeacherInfo.class, cn,
                        Integer.parseInt(pageSize), Integer.parseInt(curragePage) - 1, false);
                JSONArray array = new JSONArray();
                if (famousTeacherInfoList != null && famousTeacherInfoList.size() > 0) {
                    JSONObject ojT = null;
                    for (FamousTeacherInfo famousTeacherInfo : famousTeacherInfoList) {
                        ojT = new JSONObject();
                        ojT.put("teacherId", famousTeacherInfo.getTeacherId());
                        ojT.put("teacherName", famousTeacherInfo.getTeacherName());
                        ojT.put("teacherImg", famousTeacherInfo.getTeacherImg());
                        ojT.put("teacherSex", famousTeacherInfo.getTeacherSex());
                        ojT.put("teacherBirthday", famousTeacherInfo.getTeacherBirthday());
                        ojT.put("teacherPhone", famousTeacherInfo.getTeacherPhone());
                        ojT.put("teacherQualification", famousTeacherInfo.getTeacherQualification());
                        ojT.put("teacherKeyword", famousTeacherInfo.getTeacherSearchKeyword());
                        ojT.put("teacherUpdate", famousTeacherInfo.getTeacherUpdateName());
                        ojT.put("status", famousTeacherInfo.getStatus());
                        array.put(ojT);
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
}
