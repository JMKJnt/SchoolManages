package com.cn.service.busImpl;

import com.cn.common.CommonHelper;
import com.cn.common.MsgAndCode;
import com.cn.common.Utils;
import com.cn.dao.interfaceDao.BaseEntityDao;
import com.cn.entiy.*;
import com.cn.service.busService.UserInfoService;
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
public class UserInfoServiceImpl implements UserInfoService {

    Logger logger = Logger.getLogger(UserInfoServiceImpl.class);

    /*
    变量：码表dao实现
   */
    @Autowired
    private BaseEntityDao baseEntityDao;
    @Autowired
    private DictionaryServiceImpl dictionaryServiceImpl;

    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd HHmmss");

    /**
     * 新增用户
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertUser(String jsonstr) {
        logger.info("新增用户接口信息insertUser--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String userName = jo.getString("userName");//用户名
        String passWord = jo.getString("passWord");//base64加密处理
        String nickName = jo.has("nickName") ? jo.getString("nickName") : "";//用户昵称
        String userPhone = jo.getString("userPhone");//用户电话
        String userRole = jo.getString("userRole");//用户角色
        String userThird = jo.has("userThird") ? jo.getString("userThird") : "";//三方id
        String userWechatUnicon = jo.has("userWechatUnicon") ? jo.getString("userWechatUnicon") : "";//微信unionID
        String userCreate = jo.getString("userCreate");//创建人
        String status = jo.getString("status");
        String classId = jo.has("classId") ? jo.getString("classId") : "";//班级ID
        String userEmail = jo.has("userEmail") ? jo.getString("userEmail") : "";
        String userAddress = jo.has("userAddress") ? jo.getString("userAddress") : "";
        if (Utils.isEmpty(userName) || Utils.isEmpty(passWord) || Utils.isEmpty(userPhone) ||
                Utils.isEmpty(userRole) || Utils.isEmpty(userCreate) || Utils.isEmpty(status)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        }//1为导师，2为老师，3为学生，4为家长
        //判断是否角色同时为学生和导师，或者学生和老师，或者学生和家长，是则报错
        else if (userRole.contains("3") && userRole.contains("4")) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_013);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_013_MSG);
            return result;
        } else if (userRole.contains("3") && userRole.contains("1")) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_012);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_012_MSG);
            return result;
        } else if (userRole.contains("3") && userRole.contains("2")) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_014);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_014_MSG);
            return result;
        } else {
            //手机号唯一验证
            if (isPhoneSingle(userPhone, "")) {
                try {
                    int countClassS = 0;
                    //判断如果用户角色包含学生及班级，则判断班级人数是否超过200，超过200返回错误
                    if (userRole.contains("3") && !Utils.isEmpty(classId)) {
                        Conjunction classCn = Restrictions.conjunction();
                        classCn.add(Restrictions.eq("classId", classId));
                        countClassS = baseEntityDao.countByCriteria(ClassStudentInfo.class, classCn, false);
                    }
                    if (countClassS < 200) {
                        UserInfo userInfo = new UserInfo();
                        //循环用户角色
                        userInfo.setUserName(userName);
                        //密码base64加密处理
                        String passBase64 = CommonHelper.encryptBASE(passWord.getBytes());
                        userInfo.setUserPassword(passBase64);
                        userInfo.setUserNickName(nickName);
                        userInfo.setUserPhone(userPhone);
                        userInfo.setUserRoleType(userRole);
                        userInfo.setUserThirdId(userThird);
                        userInfo.setUserWechatUnionId(userWechatUnicon);
                        userInfo.setUserEmail(userEmail);
                        userInfo.setUserAddress(userAddress);
                        userInfo.setUserCreateName(userCreate);
                        userInfo.setUserCreateTime(new Date());
                        userInfo.setUserUpdateName(userCreate);
                        userInfo.setUserUpdateTime(new Date());
                        userInfo.setStatus(status);
                        if (userRole.contains("3") && !Utils.isEmpty(classId)) {
                            userInfo.setUserClass(classId);
                        }
                        String resultStr = baseEntityDao.saveOrUpdate(userInfo);
                        if (resultStr.equals("success")) {
                            if (!Utils.isEmpty(classId)) {
                                //根据班级ID 插入学生信息
                                ClassStudentInfo classStudentInfo = new ClassStudentInfo();
                                classStudentInfo.setClassId(classId);
                                classStudentInfo.setClassStudent(userInfo.getUserId());
                                classStudentInfo.setClassCreateName(userCreate);
                                classStudentInfo.setClassCreateTime(new Date());
                                classStudentInfo.setClassUpdateName(userCreate);
                                classStudentInfo.setClassCreateTime(new Date());
                                classStudentInfo.setStatus(status);
                                resultStr = baseEntityDao.saveOrUpdate(classStudentInfo);
                            }
                            //判断用户角色是否包含导师则判断是否存在导师手机号，不存在添加导师数据
                            if (userRole.contains("1")) {
                                Conjunction cc = Restrictions.conjunction();
                                cc.add(Restrictions.eq("teacherPhone", userPhone));
                                List<FamousTeacherInfo> famousTeacherInfoList = baseEntityDao.listByCriteria(FamousTeacherInfo.class, cc, false);
                                if (famousTeacherInfoList == null || famousTeacherInfoList.size() <= 0) {
                                    FamousTeacherInfo famousTeacherInfo = new FamousTeacherInfo();
                                    famousTeacherInfo.setTeacherName(nickName);
                                    famousTeacherInfo.setTeacherSex("1");
                                    famousTeacherInfo.setTeacherPhone(userPhone);
                                    famousTeacherInfo.setTeacherCreateName(userCreate);
                                    famousTeacherInfo.setTeacherCreateTime(new Date());
                                    famousTeacherInfo.setTeacherUpdateName(userCreate);
                                    famousTeacherInfo.setTeacherUpdateTime(new Date());
                                    famousTeacherInfo.setStatus(status);
                                    resultStr = baseEntityDao.saveOrUpdate(famousTeacherInfo);
                                }
                            }
                        }
                        if (resultStr.equals("success")) {
                            result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                            result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                            result.put("userId", userInfo.getUserId());
                        } else {
                            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                        }
                    } else {
                        result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_011);
                        result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_011_MSG);
                    }

                } catch (Exception e) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                    e.printStackTrace();
                }
            } else {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_006);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_006_MSG);
            }
        }
        return result;
    }

    /**
     * 修改用户
     *
     * @param jsonstr
     * @return
     */

    public JSONObject updateUser(String jsonstr) {
        logger.info("修改用户接口信息updateUser--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String userId = jo.getString("userId");//用户id
        String userName = jo.getString("userName");//用户名
        String passWord = jo.has("passWord") ? jo.getString("passWord") : "";//base64加密处理
        String nickName = jo.has("nickName") ? jo.getString("nickName") : "";//用户昵称
        String userPhone = jo.getString("userPhone");//用户电话
        String userRole = jo.getString("userRole");//用户角色
        String userThird = jo.has("userThird") ? jo.getString("userThird") : "";//三方id
        String userWechatUnicon = jo.has("userWechatUnicon") ? jo.getString("userWechatUnicon") : "";//微信unionID
        String userUpdate = jo.getString("userUpdate");//修改人
        String status = jo.getString("status");
        String userEmail = jo.has("userEmail") ? jo.getString("userEmail") : "";
        String userAddress = jo.has("userAddress") ? jo.getString("userAddress") : "";
        if (Utils.isEmpty(userId) || Utils.isEmpty(userName) ||
                Utils.isEmpty(userPhone) || Utils.isEmpty(userRole) || Utils.isEmpty(userUpdate)
                || Utils.isEmpty(status)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } //1为导师，2为老师，3为学生，4为家长
        //判断是否角色同时为学生和导师，或者学生和老师，或者学生和家长，是则报错
        else if (userRole.contains("3") && userRole.contains("4")) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_013);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_013_MSG);
            return result;
        } else if (userRole.contains("3") && userRole.contains("1")) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_012);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_012_MSG);
            return result;
        } else if (userRole.contains("3") && userRole.contains("2")) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_014);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_014_MSG);
            return result;
        } else {
            //手机号唯一验证
            if (isPhoneSingle(userPhone, userId)) {
                try {
                    UserInfo userInfo = baseEntityDao.listById(UserInfo.class, userId, false);
                    userInfo = userInfo == null ? new UserInfo() : userInfo;
                    userInfo.setUserId(userId);
                    userInfo.setUserName(userName);
                    //密码base64加密处理
                    if (!passWord.equals("")) {
                        String passBase64 = CommonHelper.encryptBASE(passWord.getBytes());
                        userInfo.setUserPassword(passBase64);
                    }

                    userInfo.setUserNickName(nickName);
                    userInfo.setUserPhone(userPhone);
                    userInfo.setUserRoleType(userRole);
                    userInfo.setUserThirdId(userThird);
                    userInfo.setUserWechatUnionId(userWechatUnicon);
                    userInfo.setUserUpdateName(userUpdate);
                    userInfo.setUserUpdateTime(new Date());
                    userInfo.setStatus(status);
                    userInfo.setUserEmail(userEmail);
                    userInfo.setUserAddress(userAddress);
                    String resultStr = baseEntityDao.saveOrUpdate(userInfo);
                    if (resultStr.equals("success")) {
                        //判断用户角色是否包含导师则添加 判断手机号是否已存在导师数据，不存在则新增导师数据
                        if (userRole.contains("1")) {
                            Conjunction cc = Restrictions.conjunction();
                            cc.add(Restrictions.eq("teacherPhone", userPhone));
                            List<FamousTeacherInfo> famousTeacherInfoList = baseEntityDao.listByCriteria(FamousTeacherInfo.class, cc, false);
                            if (famousTeacherInfoList == null || famousTeacherInfoList.size() <= 0) {
                                FamousTeacherInfo famousTeacherInfo = new FamousTeacherInfo();
                                famousTeacherInfo.setTeacherName(nickName);
                                famousTeacherInfo.setTeacherSex("1");
                                famousTeacherInfo.setTeacherPhone(userPhone);
                                famousTeacherInfo.setTeacherCreateName(userUpdate);
                                famousTeacherInfo.setTeacherCreateTime(new Date());
                                famousTeacherInfo.setTeacherUpdateName(userUpdate);
                                famousTeacherInfo.setTeacherUpdateTime(new Date());
                                famousTeacherInfo.setStatus(status);
                                resultStr = baseEntityDao.saveOrUpdate(famousTeacherInfo);
                            }
                        }
                    }
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
            } else {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_006);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_006_MSG);
            }
        }
        return result;
    }

    /**
     * 获取用户信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getUserInfo(String jsonstr) {
        logger.info("获取用户信息接口信息getUserInfo--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String userId = jo.getString("userId");//用户id
        String activeId = jo.has("activeId") ? jo.getString("activeId") : "";
        if (Utils.isEmpty(userId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                UserInfo userInfo = baseEntityDao.listById(UserInfo.class, userId, false);
                if (userInfo != null) {
                    JSONArray classArry = new JSONArray();
                    //根据userid获取班级信息，如果role为老师，则获取班级表，如果为学生则sql
                    if (userInfo.getUserRoleType().contains("2")) {
                        //老师
                        StringBuilder strSql = new StringBuilder("SELECT C.CLASS_ID,C.CLASS_NAME,C.CLASS_NUM,C.CLASS_SCHOOL,S.SCHOOL_NAME,C.CLASS_TEACHER");
                        strSql.append(" FROM CLASS_INFO C LEFT JOIN SCHOOL_INFO S ON S.SCHOOL_ID=C.CLASS_SCHOOL ");
                        strSql.append("WHERE C.CLASS_TEACHER='" + userInfo.getUserId() + "' AND C.STATUS='1' ");
                        List<Map<String, Object>> classList = baseEntityDao.listBySQL(strSql.toString(), false);
                        if (classList != null && classList.size() > 0) {
                            JSONObject job = null;
                            for (int i = 0; i < classList.size(); i++) {
                                job = new JSONObject();
                                Map<String, Object> map = classList.get(i);
                                job.put("classId", map.get("CLASS_ID").toString());
                                job.put("className", map.get("CLASS_NAME").toString());
                                job.put("schoolId", map.get("CLASS_SCHOOL").toString());
                                job.put("schoolName", map.get("SCHOOL_NAME").toString());
                                job.put("classTeacher", userInfo.getUserId());
                                job.put("classTeacherName", userInfo.getUserName());
                                job.put("classTeacherPhone", userInfo.getUserPhone());
                                job.put("classNum", map.get("CLASS_NUM") == null ? "" : map.get("CLASS_NUM").toString());
                                classArry.put(job);
                            }
                        }
                    } else if (userInfo.getUserRoleType().contains("3")) {
                        //如果是学生，则根据学生表查询
                        StringBuilder strSql = new StringBuilder("SELECT C.CLASS_ID,C.CLASS_NAME,C.CLASS_NUM,C.CLASS_SCHOOL,S.SCHOOL_NAME,C.CLASS_TEACHER ");
                        strSql.append(" ,UT.USER_NAME,UT.USER_PHONE  FROM CLASS_INFO C LEFT JOIN CLASS_STUDENT_INFO CS ON CS.CLASS_ID=C.CLASS_ID");
                        strSql.append(" LEFT JOIN SCHOOL_INFO S ON S.SCHOOL_ID=C.CLASS_SCHOOL ");
                        strSql.append(" LEFT JOIN USER_INFO U ON U.USER_ID=CS.CLASS_STUDENT AND U.USER_ROLETYPE LIKE '%3%'");
                        strSql.append(" LEFT JOIN USER_INFO UT ON UT.USER_ID=C.CLASS_TEACHER AND UT.USER_ROLETYPE LIKE '%2%'");
                        strSql.append(" WHERE U.USER_ID='" + userInfo.getUserId() + "'");
                        List<Map<String, Object>> classList = baseEntityDao.listBySQL(strSql.toString(), false);
                        if (classList != null && classList.size() > 0) {
                            JSONObject job = null;
                            for (int i = 0; i < classList.size(); i++) {
                                job = new JSONObject();
                                Map<String, Object> map = classList.get(i);
                                job.put("classId", map.get("CLASS_ID").toString());
                                job.put("className", map.get("CLASS_NAME").toString());
                                job.put("schoolId", map.get("CLASS_SCHOOL") == null ? "" : map.get("CLASS_SCHOOL").toString());
                                job.put("schoolName", map.get("SCHOOL_NAME") == null ? "" : map.get("SCHOOL_NAME").toString());
                                job.put("classTeacher", map.get("CLASS_TEACHER") == null ? "" : map.get("CLASS_TEACHER").toString());
                                job.put("classTeacherName", map.get("USER_NAME") == null ? "" : map.get("USER_NAME").toString());
                                job.put("classTeacherPhone", map.get("USER_PHONE") == null ? "" : map.get("USER_PHONE").toString());
                                job.put("classNum", map.get("CLASS_NUM") == null ? "" : map.get("CLASS_NUM").toString());
                                classArry.put(job);
                            }
                        }
                    }
                    //判断是否活动ID不为空，不为空则根据activeID及用户ID查询活动报名表，反数据大于0则为已参加，没有则未参加
                    if (!Utils.isEmpty(activeId)) {
                        Conjunction ccA = Restrictions.conjunction();
                        ccA.add(Restrictions.eq("registerActivityId", activeId));
                        ccA.add(Restrictions.eq("registerUserId", userId));
                        List<RegisterInfo> registerInfos = baseEntityDao.listByCriteria(RegisterInfo.class, ccA, false);
                        if (registerInfos != null && registerInfos.size() > 0) {
                            result.put("isRegisterActive", true);
                        } else {
                            result.put("isRegisterActive", false);
                        }
                    } else {
                        result.put("isRegisterActive", false);
                    }

                    result.put("classList", classArry);
                    result.put("userId", userInfo.getUserId());
                    result.put("userName", userInfo.getUserName());
                    result.put("nickName", userInfo.getUserNickName());
                    result.put("userPhone", userInfo.getUserPhone());
                    result.put("userRole", userInfo.getUserRoleType());
                    //获取码表对应name
                    result.put("userRoleName", dictionaryServiceImpl.getNameByCodeAndValue("roleType", userInfo.getUserRoleType()));
                    result.put("userThird", userInfo.getUserThirdId());
                    result.put("userWechatUnicon", userInfo.getUserWechatUnionId());
                    result.put("userUpdate", userInfo.getUserUpdateName());
                    result.put("status", userInfo.getStatus());
                    result.put("userEmail", userInfo.getUserEmail());
                    result.put("userAddress", userInfo.getUserAddress());

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
        return result;
    }

    /**
     * 功能：手机号唯一验证
     * 创建人：Liql
     * 创建时间：2018-9-11
     *
     * @param phone
     * @return
     */
    private boolean isPhoneSingle(String phone, String userId) {
        boolean isPass = true;
        if (!Utils.isEmpty(phone)) {
            Conjunction cn = Restrictions.conjunction();
            cn.add(Restrictions.eq("userPhone", phone));
            if (!Utils.isEmpty(userId)) {
                cn.add(Restrictions.ne("userId", userId));
            }
            int count = baseEntityDao.countByCriteria(UserInfo.class, cn, false);
            if (count > 0) {
                isPass = false;
            }
        }
        return isPass;
    }

    /**
     * 获取用户列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getUserInfoList(String jsonstr) {
        logger.info("获取用户列表接口信息getUserInfoList--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String userName = jo.has("userName") ? jo.getString("userName") : "";//用户名称
        String userPhone = jo.has("userPhone") ? jo.getString("userPhone") : "";//用户电话
        String userRole = jo.has("userRole") ? jo.getString("userRole") : "";//用户角色
        String isClass = jo.has("isClass") ? jo.getString("isClass") : "";//是否班级查询
        String classId = jo.has("classId") ? jo.getString("classId") : "";//班级ID
        String curragePage = jo.getString("curragePage");//当前页
        String pageSize = jo.getString("pageSize");//每页记录数
        if (Utils.isEmpty(curragePage) || Utils.isEmpty(pageSize)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                StringBuffer sqlCount = new StringBuffer("SELECT COUNT(*) SUM FROM USER_INFO U WHERE 1=1 ");
                if (!Utils.isEmpty(userPhone)) {
                    sqlCount.append(" AND U.USER_PHONE='" + userPhone + "'");
                }
                if (!Utils.isEmpty(userName)) {
                    sqlCount.append(" AND U.USER_NAME LIKE '%" + userName + "%'");
                }
                if (!Utils.isEmpty(userRole)) {
                    sqlCount.append(" AND U.USER_ROLETYPE LIKE '%" + userRole + "%'");
                }
                if (!Utils.isEmpty(isClass) && isClass.equals("1")) {
                    sqlCount.append(" AND NOT EXISTS (SELECT 'X' FROM CLASS_STUDENT_INFO C WHERE C.CLASS_STUDENT =U.USER_ID ");
                    if (!Utils.isEmpty(classId)) {
                        sqlCount.append(" AND C.CLASS_ID <> '" + classId + "' ");
                    }
                    sqlCount.append(" )");
                }
                int total = baseEntityDao.CountBySQL(sqlCount.toString());

                StringBuffer strsql = new StringBuffer("SELECT U.USER_ID,U.USER_NAME,U.USER_NICKNAME,U.USER_PHONE,U.USER_ROLETYPE,");
                strsql.append("U.USER_THIRDID,U.USER_WECHAT_UNIONID,U.USER_UPDATENAME,U.STATUS,U.COUNTACTIVE FROM USER_INFO U WHERE 1=1 ");

                if (!Utils.isEmpty(userPhone)) {
                    strsql.append(" AND U.USER_PHONE='" + userPhone + "'");
                }
                if (!Utils.isEmpty(userName)) {
                    strsql.append(" AND U.USER_NAME LIKE '%" + userName + "%'");
                }
                if (!Utils.isEmpty(userRole)) {
                    strsql.append(" AND U.USER_ROLETYPE LIKE '%" + userRole + "%'");
                }
                if (!Utils.isEmpty(isClass) && isClass.equals("1")) {
                    strsql.append(" AND NOT EXISTS (SELECT 'X' FROM CLASS_STUDENT_INFO C WHERE C.CLASS_STUDENT =U.USER_ID ");
                    if (!Utils.isEmpty(classId)) {
                        strsql.append(" AND C.CLASS_ID <> '" + classId + "' ");
                    }
                    strsql.append(" )");
                }
                List<Map<String, Object>> list = baseEntityDao.listByPageBySQL(strsql.toString(), Integer.parseInt(pageSize),
                        Integer.parseInt(curragePage) - 1, false);
                JSONArray array = new JSONArray();
                if (list != null && list.size() > 0) {
                    //循环添加数据到lists
                    JSONObject job = null;
                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> map = list.get(i);
                        job = new JSONObject();
                        job.put("userId", map.get("USER_ID").toString());
                        job.put("userName", map.get("USER_NAME").toString());
                        job.put("nickName", map.get("USER_NICKNAME").toString());
                        job.put("userPhone", map.get("USER_PHONE").toString());
                        job.put("userRole", map.get("USER_ROLETYPE").toString());
//                        job.put("userRoleName", map.get("ROLENAME").toString());
                        job.put("userThird", map.get("USER_THIRDID").toString());
                        job.put("userWechatUnicon", map.get("USER_WECHAT_UNIONID").toString());
                        job.put("userUpdate", map.get("USER_UPDATENAME").toString());
                        job.put("status", map.get("STATUS").toString());
                        job.put("countActive", map.get("COUNTACTIVE") == null ? "0" : map.get("COUNTACTIVE").toString());
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
     * 用户登录（web，小程序）
     *
     * @param jsonstr
     * @return
     */
    public JSONObject login(String jsonstr) {
        logger.info("用户登录（web，小程序）接口信息login--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String userPhone = jo.has("userPhone") ? jo.getString("userPhone") : "";//用户手机号
        String userThird = jo.has("userThird") ? jo.getString("userThird") : "";//三方id

        String passWord = jo.has("passWord") ? jo.getString("passWord") : "";//密码
        String userWechatUnicon = jo.has("userWechatUnicon") ? jo.getString("userWechatUnicon") : "";//微信unionID
        if ((Utils.isEmpty(userPhone) && Utils.isEmpty(userThird)) || Utils.isEmpty(passWord)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                Conjunction cn = Restrictions.conjunction();
                cn.add(Restrictions.eq("userPhone", userPhone));
                if (!userThird.equals("")) {
                    cn.add(Restrictions.eq("userThirdId", userThird));
                }
                if (!userWechatUnicon.equals("")) {
                    cn.add(Restrictions.eq("userWechatUnionId", userWechatUnicon));
                }
                //密码需判断是否加密 未加密则需要加密
                if (!CommonHelper.isBase64(passWord)) {
                    passWord = CommonHelper.encryptBASE(passWord.getBytes()).toString();
                    logger.info("passEn=" + passWord);
                } else {
                    logger.info("passEn=" + passWord);
                }
                cn.add(Restrictions.eq("userPassword", passWord));//密码
                cn.add(Restrictions.eq("status", "1"));//默认启用
                List<UserInfo> userInfoList = baseEntityDao.listByCriteria(UserInfo.class, cn, false);
                ;
                if (userInfoList.size() > 0) {
                    UserInfo userInfo = userInfoList.get(0);
                    result.put("userId", userInfo.getUserId());
                    result.put("userName", userInfo.getUserName());
                    result.put("nickName", userInfo.getUserNickName());
                    result.put("userPhone", userInfo.getUserPhone());
                    result.put("userRole", userInfo.getUserRoleType());
                    //获取码表对应name
                    result.put("userRoleName", dictionaryServiceImpl.getNameByCodeAndValue("roleType", userInfo.getUserRoleType()));
                    result.put("userThird", userInfo.getUserThirdId());
                    result.put("userWechatUnicon", userInfo.getUserWechatUnionId());
                    result.put("userUpdate", userInfo.getUserUpdateName());
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
        return result;
    }

    /**
     * 功能：手机号唯一验证
     * 创建人：Liql
     * 创建时间：2018-9-11
     *
     * @param phone
     * @return
     */
    private boolean isManagePhoneSingle(String phone, String manageId) {
        boolean isPass = true;
        if (!Utils.isEmpty(phone)) {
            Conjunction cn = Restrictions.conjunction();
            cn.add(Restrictions.eq("managePhone", phone));
            if (!Utils.isEmpty(manageId)) {
                cn.add(Restrictions.ne("manageId", manageId));
            }
            int count = baseEntityDao.countByCriteria(UserManageInfo.class, cn, false);
            if (count > 0) {
                isPass = false;
            }
        }
        return isPass;
    }

    /**
     * 新增管理员
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertUserManage(String jsonstr) {
        logger.info("新增管理员接口信息insertUserManage--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String manageUserName = jo.getString("manageUserName");//管理员用户名
        String manageName = jo.getString("manageName");//管理员姓名
        String password = jo.getString("password");//管理员密码
        String managePhone = jo.getString("managePhone");//用户电话
        String managePurview = jo.has("managePurview") ? jo.getString("managePurview") : "";//权限id
        String manageCreate = jo.getString("manageCreate");//创建人
        String status = jo.getString("status");
        if (Utils.isEmpty(manageUserName) || Utils.isEmpty(manageName) || Utils.isEmpty(password) ||
                Utils.isEmpty(managePhone) || Utils.isEmpty(manageCreate) || Utils.isEmpty(status)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            if (isManagePhoneSingle(managePhone, "")) {
                try {
                    UserManageInfo userInfo = new UserManageInfo();
                    userInfo.setManageUserName(manageUserName);
                    //密码base64加密处理
                    String passBase64 = CommonHelper.encryptBASE(password.getBytes());
                    userInfo.setManagePassword(passBase64);
                    userInfo.setManageName(manageName);
                    userInfo.setManagePhone(managePhone);
                    userInfo.setManagePurviewId(managePurview);
                    userInfo.setManageCreateName(manageCreate);
                    userInfo.setManageCreateTime(new Date());
                    userInfo.setManageUpdateName(manageCreate);
                    userInfo.setManageUpdateTime(new Date());
                    userInfo.setStatus(status);
                    String resultStr = baseEntityDao.saveOrUpdate(userInfo);
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
            } else {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_006);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_006_MSG);
            }
        }
        return result;
    }

    /**
     * 修改管理员
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updateUserManage(String jsonstr) {
        logger.info("修改管理员接口信息updateUserManage--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String manageId = jo.getString("manageId");//管理员id
        String manageUserName = jo.getString("manageUserName");//管理员用户名
        String manageName = jo.getString("manageName");//管理员姓名
        String password = jo.getString("password");//管理员密码
        String managePhone = jo.getString("managePhone");//用户电话
        String managePurview = jo.has("managePurview") ? jo.getString("managePurview") : "";//权限id
        String manageCreate = jo.getString("manageCreate");//创建人
        String status = jo.getString("status");//状态
        if (Utils.isEmpty(manageUserName) || Utils.isEmpty(manageName) || Utils.isEmpty(password) ||
                Utils.isEmpty(manageId) || Utils.isEmpty(managePhone) || Utils.isEmpty(manageCreate) ||
                Utils.isEmpty(status)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            if (isManagePhoneSingle(managePhone, manageId)) {
                try {
                    UserManageInfo userInfo = baseEntityDao.listById(UserManageInfo.class, manageId, false);
                    userInfo = userInfo == null ? new UserManageInfo() : userInfo;
                    userInfo.setManageId(manageId);
                    userInfo.setManageUserName(manageUserName);
                    //密码base64加密处理
                    String passBase64 = CommonHelper.encryptBASE(password.getBytes());
                    userInfo.setManagePassword(passBase64);
                    userInfo.setManageName(manageName);
                    userInfo.setManagePhone(managePhone);
                    userInfo.setManagePurviewId(managePurview);
                    userInfo.setManageUpdateName(manageCreate);
                    userInfo.setManageUpdateTime(new Date());
                    userInfo.setStatus(status);
                    String resultStr = baseEntityDao.saveOrUpdate(userInfo);
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
            } else {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_006);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_006_MSG);
            }
        }
        return result;
    }

    /**
     * 获取管理员信息
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getManage(String jsonstr) {
        logger.info("获取管理员信息接口信息getManage--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String manageId = jo.getString("manageId");//管理员id
        if (Utils.isEmpty(manageId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                UserManageInfo userInfo = baseEntityDao.listById(UserManageInfo.class, manageId, false);
                if (userInfo == null) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_004_MSG);
                } else {
                    result.put("manageId", userInfo.getManageId());
                    result.put("manageUserName", userInfo.getManageUserName());
                    result.put("manageName", userInfo.getManageName());
                    result.put("managePhone", userInfo.getManagePhone());
                    result.put("managePurview", userInfo.getManagePurviewId());
                    result.put("manageUpdate", userInfo.getManageUpdateName());
                    result.put("manageCreate", userInfo.getManageCreateName());
                    result.put("status", userInfo.getStatus());
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
     * 获取管理员列表
     *
     * @param jsonstr
     * @return
     */
    public JSONObject getManageList(String jsonstr) {
        logger.info("获取管理员列表接口信息getManageList--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String manageUserName = jo.has("manageUserName") ? jo.getString("manageUserName") : "";//管理员用户名
        String manageName = jo.has("manageName") ? jo.getString("manageName") : "";//管理员姓名
        String managePhone = jo.has("managePhone") ? jo.getString("managePhone") : "";//手机号
        String curragePage = jo.getString("curragePage");//当前页
        String pageSize = jo.getString("pageSize");//每页记录数
        if (Utils.isEmpty(curragePage) || Utils.isEmpty(pageSize)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                Conjunction cn = Restrictions.conjunction();
                if (!manageName.equals("")) {
                    cn.add(Restrictions.like("manageName", "%" + manageName + "%"));
                }
                if (!managePhone.equals("")) {
                    cn.add(Restrictions.eq("managePhone", managePhone));
                }
                if (!manageUserName.equals("")) {
                    cn.add(Restrictions.like("manageUserName", "%" + manageUserName + "%"));
                }
                int total = baseEntityDao.countByCriteria(UserManageInfo.class, cn, false);
//
                List<UserManageInfo> userManageInfoList = baseEntityDao.listByPageCriteria(UserManageInfo.class, cn,
                        Integer.parseInt(pageSize), Integer.parseInt(curragePage) - 1, false);
                JSONArray array = new JSONArray();
                if (userManageInfoList.size() > 0) {
                    JSONObject job = null;
                    for (UserManageInfo userInfo : userManageInfoList) {
                        job = new JSONObject();
                        job.put("manageId", userInfo.getManageId());
                        job.put("manageUserName", userInfo.getManageUserName());
                        job.put("manageName", userInfo.getManageName());
                        job.put("managePhone", userInfo.getManagePhone());
                        job.put("managePurview", userInfo.getManagePurviewId());
                        job.put("manageUpdate", userInfo.getManageUpdateName());
                        job.put("manageCreate", userInfo.getManageCreateName());
                        job.put("status", userInfo.getStatus());
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
     * 管理员登录后台
     *
     * @param jsonstr
     * @return
     */
    public JSONObject loginManager(String jsonstr) {
        logger.info("管理员登录后台接口信息loginManager--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String managePhone = jo.getString("managePhone");//管理员用户名
        String password = jo.getString("password");//密码
        if (Utils.isEmpty(managePhone) || Utils.isEmpty(password)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                Conjunction cn = Restrictions.conjunction();
                cn.add(Restrictions.eq("managePhone", managePhone));
                //密码需判断是否加密 未加密则需要加密
                if (!CommonHelper.isBase64(password)) {
                    password = CommonHelper.encryptBASE(password.getBytes()).toString();
                }
                cn.add(Restrictions.eq("managePassword", password));
                cn.add(Restrictions.eq("status", "1"));//默认启用状态
                List<UserManageInfo> userInfoList = baseEntityDao.listByCriteria(UserManageInfo.class, cn, false);
                if (userInfoList.size() <= 0) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_005_MSG);
                } else {
                    UserManageInfo userInfo = userInfoList.get(0);
                    result.put("manageId", userInfo.getManageId());
                    result.put("manageUserName", userInfo.getManageUserName());
//                    result.put("manageName", userInfo.getManageName());
                    result.put("managePhone", userInfo.getManagePhone());
                    result.put("managePassword", userInfo.getManagePassword());
//                    result.put("managePurview", userInfo.getManagePurviewId());
                    result.put("manageUpdate", userInfo.getManageUpdateName());
                    result.put("manageCreate", userInfo.getManageCreateName());
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
     * 管理员修改密码
     *
     * @param jsonstr
     * @return
     */
    public JSONObject updatePassword(String jsonstr) {
        logger.info("管理员修改密码接口信息updatePassword--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String managePhone = jo.getString("managePhone");//管理员用户名
        String oldPassword = jo.getString("oldPassword");//密码
        String newPassword = jo.getString("newPassword");//新密码
        String manageUpdate = jo.getString("manageUpdate");
        if (Utils.isEmpty(managePhone) || Utils.isEmpty(oldPassword) || Utils.isEmpty(newPassword)
                || Utils.isEmpty(manageUpdate)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //根据旧密码和手机号查询 原有数据，通过验证则修改新密码，不通过再返回原始密码不正确
                Conjunction cn = Restrictions.conjunction();
                cn.add(Restrictions.eq("managePhone", managePhone));
                //密码需判断是否加密 未加密则需要加密
                if (!CommonHelper.isBase64(oldPassword)) {
                    oldPassword = CommonHelper.encryptBASE(oldPassword.getBytes()).toString();
                }
                cn.add(Restrictions.eq("managePassword", oldPassword));
                List<UserManageInfo> userInfoList = baseEntityDao.listByCriteria(UserManageInfo.class, cn, false);
                if (userInfoList.size() <= 0) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_007);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_007_MSG);
                } else {
                    UserManageInfo userInfo = userInfoList.get(0);
                    userInfo.setManagePassword(CommonHelper.encryptBASE(newPassword.getBytes()));
                    userInfo.setManageUpdateName(manageUpdate);
                    userInfo.setManageUpdateTime(new Date());
                    String resultStr = baseEntityDao.saveOrUpdate(userInfo);
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

    /*
    功能：新增家庭成员
     */
    public JSONObject insertFamily(String jsonstr) {
        logger.info("新增家庭信息接口信息insertFamily--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String familyName = jo.getString("familyName");//
        String familyPhone = jo.getString("familyPhone");//
        String familyCate = jo.getString("familyCate");//用户电话
        String familyUserId = jo.getString("familyUserId");//用户角色
        String familyEmail = jo.has("familyEmail") ? jo.getString("familyEmail") : "";//三方id
        String familyAddress = jo.has("familyAddress") ? jo.getString("familyAddress") : "";//微信unionID
        String userCreate = jo.getString("userCreate");//创建人
        String status = jo.getString("status");
        if (Utils.isEmpty(familyName) || Utils.isEmpty(familyPhone) || Utils.isEmpty(familyCate) ||
                Utils.isEmpty(familyUserId) || Utils.isEmpty(userCreate) || Utils.isEmpty(status)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        }
        try {
            FamilyInfo familyInfo = new FamilyInfo();
            familyInfo.setFamilyName(familyName);
            familyInfo.setFamilyPhone(familyPhone);
            familyInfo.setFamilyCate(familyCate);
            familyInfo.setFamilyUserId(familyUserId);
            familyInfo.setFamilyEmail(familyEmail);
            familyInfo.setFamilyAddress(familyAddress);
            familyInfo.setFamilyCreateName(userCreate);
            familyInfo.setFamilyUpdateName(userCreate);
            familyInfo.setFamilyCreateTime(new Date());
            familyInfo.setFamilyUpdateTime(new Date());
            familyInfo.setStatus(status);
            String resultStr = baseEntityDao.saveOrUpdate(familyInfo);
            if (resultStr.equals("success")) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                result.put("userId", familyInfo.getFamilyId());
            } else {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
            }
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result;
    }

    /*
   功能：修改家庭成员
    */
    public JSONObject updateFamily(String jsonstr) {
        logger.info("修改家庭信息接口信息updateFamily--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String familyId = jo.getString("familyId");
        String familyName = jo.getString("familyName");//
        String familyPhone = jo.getString("familyPhone");//
        String familyCate = jo.getString("familyCate");//用户电话
        String familyUserId = jo.getString("familyUserId");//用户角色
        String familyEmail = jo.has("familyEmail") ? jo.getString("familyEmail") : "";//三方id
        String familyAddress = jo.has("familyAddress") ? jo.getString("familyAddress") : "";//微信unionID
        String userCreate = jo.getString("userCreate");//创建人
        String status = jo.getString("status");
        if (Utils.isEmpty(familyName) || Utils.isEmpty(familyPhone) || Utils.isEmpty(familyCate) ||
                Utils.isEmpty(familyUserId) || Utils.isEmpty(userCreate) || Utils.isEmpty(status) || Utils.isEmpty(familyId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        }
        try {
            FamilyInfo familyInfo = baseEntityDao.listById(FamilyInfo.class, familyId, false);
            if (familyInfo == null) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_004_MSG);
            } else {
                familyInfo.setFamilyName(familyName);
                familyInfo.setFamilyPhone(familyPhone);
                familyInfo.setFamilyCate(familyCate);
                familyInfo.setFamilyUserId(familyUserId);
                familyInfo.setFamilyEmail(familyEmail);
                familyInfo.setFamilyAddress(familyAddress);
                familyInfo.setFamilyUpdateName(userCreate);
                familyInfo.setFamilyUpdateTime(new Date());
                familyInfo.setStatus(status);
                String resultStr = baseEntityDao.saveOrUpdate(familyInfo);
                if (resultStr.equals("success")) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                    result.put("userId", familyInfo.getFamilyId());
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
        return result;
    }

    /*
  功能：获取家庭成员
   */
    public JSONObject getFamilyInfo(String jsonstr) {
        logger.info("获取家庭信息接口信息getFamilyInfo--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String familyId = jo.getString("familyId");
        if (Utils.isEmpty(familyId)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        }
        try {
            FamilyInfo familyInfo = baseEntityDao.listById(FamilyInfo.class, familyId, false);
            if (familyInfo == null) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_004_MSG);
            } else {
                UserInfo userInfo = baseEntityDao.listById(UserInfo.class, familyInfo.getFamilyUserId(), false);
                result.put("familyId", familyInfo.getFamilyId());
                result.put("familyName", familyInfo.getFamilyName());
                result.put("familyPhone", familyInfo.getFamilyPhone());
                result.put("familyCate", familyInfo.getFamilyCate());
                result.put("familyUserId", familyInfo.getFamilyUserId());
                result.put("familyUserName", userInfo == null ? "" : userInfo.getUserName());
                result.put("familyEmail", familyInfo.getFamilyEmail());
                result.put("familyAddress", familyInfo.getFamilyAddress());
                result.put("status", familyInfo.getStatus());
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
            }
        } catch (Exception e) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
            e.printStackTrace();
        }
        return result;
    }

    /*
   功能：获取家庭成员
    */
    public JSONObject getFamilyList(String jsonstr) {
        logger.info("获取家庭信息列表接口信息getFamilyList--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String userId = jo.has("familyUserId") ? jo.getString("familyUserId") : "";
        String familyName=jo.has("familyName")?jo.getString("familyName"):"";
        String familyPhone=jo.has("familyPhone")?jo.getString("familyPhone"):"";
        String curragePage = jo.getString("curragePage");//当前页
        String pageSize = jo.getString("pageSize");//每页记录数
        if (Utils.isEmpty(curragePage) || Utils.isEmpty(pageSize)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                StringBuffer strSQL = new StringBuffer("SELECT F.FAMILY_CATE,F.FAMILY_ADDRESS,F.FAMILY_ID,F.FAMILY_EMAIL,F.FAMILY_NAME,");
                strSQL.append("F.FAMILY_PHONE,F.FAMILY_USERID,F.STATUS,U.USER_NAME FROM FAMILY_INFO F LEFT JOIN ");
                strSQL.append("USER_INFO U ON U.USER_ID=F.FAMILY_USERID  WHERE 1=1 ");
                StringBuffer sqlComm =new StringBuffer();
                if (!Utils.isEmpty(userId)) {
                    sqlComm.append(" AND F.FAMILY_USERID='" + userId + "'") ;
                }
                if (!Utils.isEmpty(familyName)) {
                    sqlComm.append(" AND F.FAMILY_NAME LIKE '%" + familyName + "%'") ;
                }
                if (!Utils.isEmpty(familyPhone)) {
                    sqlComm.append(" AND F.FAMILY_PHONE='" + familyPhone + "'") ;
                }
                strSQL.append(sqlComm);
                List<Map<String, Object>> list = baseEntityDao.listByPageBySQL(strSQL.toString(), Integer.parseInt(pageSize),
                        Integer.parseInt(curragePage) - 1, false);
                StringBuffer countSql = new StringBuffer("SELECT COUNT(1) SUM FROM FAMILY_INFO F LEFT JOIN USER_INFO U ON U.USER_ID=F.FAMILY_USERID  WHERE 1=1 ");
                countSql.append(sqlComm);
                int count = baseEntityDao.CountBySQL(countSql.toString());
                JSONArray lists = new JSONArray();
                JSONObject obj = null;
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> map = list.get(i);
                        obj = new JSONObject();
                        obj.put("familyId", map.get("FAMILY_ID").toString());
                        obj.put("familyPhone", map.get("FAMILY_PHONE").toString());
                        obj.put("familyName", map.get("FAMILY_NAME").toString());
                        obj.put("familyCate", map.get("FAMILY_CATE").toString());
                        obj.put("familyUserId", map.get("FAMILY_USERID").toString());
                        obj.put("familyUserName", map.get("USER_NAME").toString());
                        obj.put("familyEmail", map.get("FAMILY_EMAIL").toString());
                        obj.put("familyAddress", map.get("FAMILY_ADDRESS").toString());
                        obj.put("status", map.get("STATUS").toString());
                    }
                    lists.put(obj);
                }
                result.put("total", count);
                result.put("lists", lists);
                result.put("curragePage", curragePage);
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
