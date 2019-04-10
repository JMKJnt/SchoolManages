package com.cn.service.busImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.dao.interfaceDao.BaseEntityDao;
import com.cn.entiy.DataDictionary;
import com.cn.entiy.DataValue;
import com.cn.service.busService.DictionaryService;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class DictionaryServiceImpl implements DictionaryService {
    Logger logger = Logger.getLogger(DictionaryServiceImpl.class);
    /*
   变量：码表dao实现
    */
    @Autowired
    private BaseEntityDao dictionaryDao;


    /*
    功能：根据dataDictionary获取码表字表信息列表
    创建人：liql
    创建时间：2015-12-03
     */
    public String GetDictionaryDataInfoList(String jsonstr) {
        String result = null;
        logger.debug("获取码表接口信息GetDictionaryDataInfoList--------" + jsonstr);
        JSONObject jsonObject = GetJsonOject(jsonstr);
        String dataDictionary = jsonObject.getString("data_dictionary");//获取码表主表id
        if (dataDictionary == null || "".equals(dataDictionary.trim())) {
            result = GetCommonObject("320000", "data_dictionary参数值错误！").toString();
        } else {
            //获取码表集合
            Conjunction conj = Restrictions.conjunction();
            conj.add(Restrictions.eq("data_Dictionary", dataDictionary));
            List<DataValue> dataLists = dictionaryDao.listByCriteria(DataValue.class, conj, false);
            JSONObject object = GetCommonObject("000", "成功");
            JSONArray arry = new JSONArray();
            if (dataLists.size() > 0) {
                Collections.sort(dataLists, new Comparator<DataValue>() {
                    @Override
                    public int compare(DataValue o1, DataValue o2) {
                        return o1.getData_Code().compareTo(o2.getData_Code());
                    }
                });

                for (DataValue data : dataLists) {
                    JSONObject jo = new JSONObject();
                    jo.put("data_id", data.getData_Id());
                    jo.put("data_code", data.getData_Code());
                    jo.put("data_name", data.getData_Name());
                    jo.put("data_parent_id", data.getData_Parent_Id() == null ? "" : data.getData_Parent_Id());
                    jo.put("data_dictionary", data.getData_Dictionary() == null ? "" : data.getData_Dictionary());
                    jo.put("data_string1", data.getData_String1() == null ? "" : data.getData_String1());
                    jo.put("data_string2", data.getData_String2() == null ? "" : data.getData_String2());
                    jo.put("data_string3", data.getData_String3() == null ? "" : data.getData_String3());
                    jo.put("data_string4", data.getData_String4() == null ? "" : data.getData_String4());
                    jo.put("data_string5", data.getData_String5() == null ? "" : data.getData_String5());
                    arry.add(jo);
                }
            }
            object.put("lists", arry);
            result = object.toString();
        }
        return result;
    }

    /*
    功能：新增码表子表信息
    创建人：liql
    创建时间：2015-12-07
     */
    public String InsertDictionaryDataInfo(String jsonStr) {
        String result = null;
        logger.debug("新增码表子表信息InsertDictionaryDataInfo-------" + jsonStr);
        JSONObject jsonObject = GetJsonOject(jsonStr);
        String dataDictionary = jsonObject.getString("data_dictionary");
        if (dataDictionary == null || "".equals(dataDictionary.trim())) {
            return GetCommonObject("320000", "data_dictionary参数值错误！").toString();
        } else {
            if (DictionaryDataValidate(jsonObject.getString("data_code"), null, dataDictionary)) {
                DataValue dataValue = new DataValue();
                dataValue.setData_Code(jsonObject.getString("data_code"));//code
                dataValue.setData_Name(jsonObject.getString("data_name"));//名称
                dataValue.setData_Parent_Id(jsonObject.getString("data_parent_id"));//父级
                dataValue.setData_Dictionary(jsonObject.getString("data_dictionary"));//主表
                dataValue.setData_String1(jsonObject.getString("data_string1"));
                dataValue.setData_String2(jsonObject.getString("data_string2"));
                dataValue.setData_String3(jsonObject.getString("data_string3"));
                dataValue.setData_String4(jsonObject.getString("data_string4"));
                dataValue.setData_String5(jsonObject.getString("data_string5"));
                dataValue.setData_Seqno(jsonObject.getIntValue("data_seqno"));
                dataValue.setData_Isvalid(jsonObject.getIntValue("data_isvalid"));//是否有效
                dataValue.setData_Remark(jsonObject.getString("data_remark"));//备注
                dataValue.setData_Updated(new Date());
                dataValue.setData_Created(new Date());
                result = dictionaryDao.saveOrUpdate(dataValue);
                if (result.equals("success")) {
                    result = GetCommonObject("000", "成功").toString();
                } else {
                    result = GetCommonObject("320008", "新增码表子表信息失败!").toString();
                }
            } else {
                return GetCommonObject("320000", "编码已存在！").toString();
            }
        }
        return result;
    }

    /*
    功能;修改码表子表信息
    创建人：liql
    创建时间：2015-12-07
     */
    public String UpdateDictionaryDataInfo(String jsonStr) {
        String result = null;
        logger.debug("修改码表子表信息UpdateDictionaryDataInfo-------" + jsonStr);
        JSONObject jsonObject = GetJsonOject(jsonStr);
        String dataId = jsonObject.getString("data_id");
        String dataDictionary = jsonObject.getString("data_dictionary");
        if (dataId == null || "".equals(dataId.trim())) {
            return GetCommonObject("320000", "data_id参数值错误！").toString();
        } else if (dataDictionary == null || "".equals(dataDictionary.trim())) {
            return GetCommonObject("320000", "data_dictionary参数值错误！").toString();
        } else {
            if (DictionaryDataValidate(jsonObject.getString("data_code"), jsonObject.getString("data_id"), dataDictionary)) {
                DataValue dataValue = new DataValue();
                dataValue.setData_Id(dataId);//id主键
                dataValue.setData_Code(jsonObject.getString("data_code"));//code
                dataValue.setData_Name(jsonObject.getString("data_name"));//名称
                dataValue.setData_Parent_Id(jsonObject.getString("data_parent_id"));//父级
                dataValue.setData_Dictionary(jsonObject.getString("data_dictionary"));//主表
                dataValue.setData_String1(jsonObject.getString("data_string1"));
                dataValue.setData_String2(jsonObject.getString("data_string2"));
                dataValue.setData_String3(jsonObject.getString("data_string3"));
                dataValue.setData_String4(jsonObject.getString("data_string4"));
                dataValue.setData_String5(jsonObject.getString("data_string5"));
                dataValue.setData_Seqno(Integer.parseInt(jsonObject.getString("data_seqno")));
                dataValue.setData_Isvalid(jsonObject.getIntValue("data_isvalid"));//是否有效
                dataValue.setData_Remark(jsonObject.getString("data_remark"));//备注
                dataValue.setData_Updated(new Date());
                result = dictionaryDao.saveOrUpdate(dataValue);
                if (result.equals("success")) {
                    result = GetCommonObject("000", "成功").toString();
                } else {
                    result = GetCommonObject("320008", "新增码表子表信息失败!").toString();
                }
            } else {
                return GetCommonObject("320000", "编码已存在！").toString();
            }

        }
        return result;
    }

    /*
    功能：获取码表主表信息
    创建人：liql
    创建时间：2015-12-07
     */

    public String GetDictionaryInfoList(String jsonStr) {
        String result = null;
        logger.debug("查询码表主表信息GetDictionaryInfoList-------" + jsonStr);
        JSONObject jsonObject = GetJsonOject(jsonStr);
        String dictionaryName = jsonObject.getString("dictionary_name");
        logger.info("pagesize=" + jsonObject.getIntValue("pageSize"));
        if (jsonObject.getIntValue("pageSize") < 1) {
            return GetCommonObject("320000", "pageSize参数值错误！").toString();
        } else if (jsonObject.getIntValue("curragePage") < 0) {
            return GetCommonObject("320000", "curragePage参数值错误！").toString();
        } else {
            Conjunction conj = Restrictions.conjunction();
            if (dictionaryName != null && !"".equals(dictionaryName.trim())) {
                logger.debug(dictionaryName.trim());
                conj.add(Restrictions.like("dictionary_Name", dictionaryName, MatchMode.ANYWHERE));
            }
            int total = dictionaryDao.countByCriteria(DataDictionary.class, conj, false);
            List<DataDictionary> dictLists = dictionaryDao.listByPageCriteria(DataDictionary.class, conj,
                    jsonObject.getIntValue("pageSize"), jsonObject.getIntValue("curragePage") - 1, false);
            JSONObject object = GetCommonObjectPages("000", "成功", jsonObject.getInteger("curragePage"),
                    jsonObject.getInteger("pageSize"), total);
            JSONArray arry = new JSONArray();
            logger.debug(dictLists);
            if (dictLists != null && dictLists.size() > 0) {
                //排序
                Collections.sort(dictLists, new Comparator<DataDictionary>() {
                    @Override
                    public int compare(DataDictionary o1, DataDictionary o2) {
                        return o1.getDictionary_Code().compareTo(o2.getDictionary_Code());
                    }
                });

                for (DataDictionary data : dictLists) {
                    JSONObject jo = new JSONObject();
                    jo.put("dictionary_id", data.getDictionary_Id());
                    jo.put("dictionary_code", data.getDictionary_Code());
                    jo.put("dictionary_name", data.getDictionary_Name());
                    jo.put("dictionary_string1", data.getDictionary_String1() == null ? "" : data.getDictionary_String1());
                    jo.put("dictionary_string2", data.getDictionary_String2() == null ? "" : data.getDictionary_String2());
                    jo.put("dictionary_string3", data.getDictionary_String3() == null ? "" : data.getDictionary_String3());
                    jo.put("dictionary_string4", data.getDictionary_String4() == null ? "" : data.getDictionary_String4());
                    jo.put("dictionary_string5", data.getDictionary_String5() == null ? "" : data.getDictionary_String5());
                    arry.add(jo);
                }
            }
            object.put("lists", arry);
            result = object.toString();
        }
        return result;
    }

    /*
    功能：新增码表主表信息
    创建人：liql
    创建时间：2015-12-07
     */
    public String InsertDictionaryInfo(String jsonStr) {
        String result = null;
        logger.debug("新增码表主表信息InsertDictionaryInfo-------" + jsonStr);
        JSONObject jsonObject = GetJsonOject(jsonStr);
        if (jsonObject.getString("dictionary_code") == null || jsonObject.getString("dictionary_code").trim() == "") {
            return GetCommonObject("320000", "dictionary_code参数值不可为空！").toString();
        } else if (jsonObject.getString("dictionary_name") == null || jsonObject.getString("dictionary_name").trim() == "") {
            return GetCommonObject("320000", "dictionary_name参数值不可为空！").toString();
        } else {
            if (DictionaryValidate(jsonObject.getString("dictionary_code"), null)) {
                DataDictionary dataDictionary = new DataDictionary();
                dataDictionary.setDictionary_Code(jsonObject.getString("dictionary_code"));//code
                dataDictionary.setDictionary_Name(jsonObject.getString("dictionary_name"));//名称
                dataDictionary.setDictionary_String1(jsonObject.getString("dictionary_string1"));
                dataDictionary.setDictionary_String2(jsonObject.getString("dictionary_string2"));
                dataDictionary.setDictionary_String3(jsonObject.getString("dictionary_string3"));
                dataDictionary.setDictionary_String4(jsonObject.getString("dictionary_string4"));
                dataDictionary.setDictionary_String5(jsonObject.getString("dictionary_string5"));
                dataDictionary.setDictionary_Seqno(jsonObject.getString("dictionary_seqno"));
                dataDictionary.setDictionary_Isvalid(jsonObject.getString("dictionary_isvalid"));//是否有效
                dataDictionary.setDictionary_Remark(jsonObject.getString("dictionary_remark"));//备注
                dataDictionary.setDictionary_Updated(new Date());
                dataDictionary.setDictionary_Created(new Date());
                result = dictionaryDao.saveOrUpdate(dataDictionary);
                if (result.equals("success")) {
                    result = GetCommonObject("000", "成功").toString();
                } else {
                    result = GetCommonObject("320008", "新增码表子表信息失败!").toString();
                }
            } else {
                return GetCommonObject("320000", "编码已存在！").toString();
            }
        }
        return result;
    }

    /*
    功能：修改码表主表信息
    创建人：liql
    创建时间：2015-12-07
     */
    public String UpdateDictionaryInfo(String jsonStr) {
        String result = null;
        logger.debug("修改码表主表信息UpdateDictionaryInfo-------" + jsonStr);
        JSONObject jsonObject = GetJsonOject(jsonStr);
        if (jsonObject.getString("dictionary_id") == null || "".equals(jsonObject.getString("dictionary_id").trim())) {
            return GetCommonObject("320000", "dictionary_id参数值错误！").toString();
        } else if (jsonObject.getString("dictionary_code") == null || "".equals(jsonObject.getString("dictionary_code").trim())) {
            return GetCommonObject("320000", "dictionary_code参数值不可为空！").toString();
        } else if (jsonObject.getString("dictionary_name") == null || "".equals(jsonObject.getString("dictionary_name").trim())) {
            return GetCommonObject("320000", "dictionary_name参数值不可为空！").toString();
        } else {
            if (DictionaryValidate(jsonObject.getString("dictionary_code"), jsonObject.getString("dictionary_id"))) {
                DataDictionary dataDictionary = new DataDictionary();
                dataDictionary.setDictionary_Id(jsonObject.getString("dictionary_id"));//id
                dataDictionary.setDictionary_Code(jsonObject.getString("dictionary_code"));//code
                dataDictionary.setDictionary_Name(jsonObject.getString("dictionary_name"));//名称
                dataDictionary.setDictionary_String1(jsonObject.getString("dictionary_string1"));
                dataDictionary.setDictionary_String2(jsonObject.getString("dictionary_string2"));
                dataDictionary.setDictionary_String3(jsonObject.getString("dictionary_string3"));
                dataDictionary.setDictionary_String4(jsonObject.getString("dictionary_string4"));
                dataDictionary.setDictionary_String5(jsonObject.getString("dictionary_string5"));
                dataDictionary.setDictionary_Seqno(jsonObject.getString("dictionary_seqno"));
                dataDictionary.setDictionary_Isvalid(jsonObject.getString("dictionary_isvalid"));//是否有效
                dataDictionary.setDictionary_Remark(jsonObject.getString("dictionary_remark"));//备注
                dataDictionary.setDictionary_Updated(new Date());
                result = dictionaryDao.saveOrUpdate(dataDictionary);
                if (result == "success") {
                    result = GetCommonObject("000", "成功").toString();
                } else {
                    result = GetCommonObject("320008", "新增码表子表信息失败!").toString();
                }
            } else {
                return GetCommonObject("320000", "编码已存在！").toString();
            }
        }
        return result;
    }

    /*
    功能：根据码表code获取码表详细信息列表
    创建人：liql
    创建时间：2015-12-10
     */
    public String GetDictionaryDataList(String jsonStr) {
        String result = null;
        logger.debug("根据码表code获取码表详细信息列表getDictionaryDataList-------" + jsonStr);
        JSONObject jsonObject = GetJsonOject(jsonStr);
        String code = jsonObject.getString("Dictionary_code");
        if (code == null || "".equals(code.trim())) {
            return GetCommonObject("320000", "Dictionary_code参数值错误！").toString();
        } else {
            //获取码表集合
            Conjunction conj = Restrictions.conjunction();
            conj.add(Restrictions.eq("dictionary_Code", code));
            List<DataDictionary> dictList = dictionaryDao.listByCriteria(DataDictionary.class, conj, false);
            String dictId = null;
            if (dictList.size() > 0) {
                for (DataDictionary data : dictList) {
                    dictId = data.getDictionary_Id();
                }
            }
            Conjunction conj1 = Restrictions.conjunction();
            conj1.add(Restrictions.eq("data_Dictionary", dictId));
            conj1.add(Restrictions.eq("data_Isvalid", 1));
            List<DataValue> dataLists = dictionaryDao.listByCriteria(DataValue.class, conj1, false);
            JSONObject object = GetCommonObject("000", "成功");
            JSONArray arry = new JSONArray();
            if (dataLists.size() > 0) {
                //排序
                Collections.sort(dataLists, new Comparator<DataValue>() {
                    @Override
                    public int compare(DataValue o1, DataValue o2) {
                        return o1.getData_Code().compareTo(o2.getData_Code());
                    }
                });
                for (DataValue data : dataLists) {
                    JSONObject jo = new JSONObject();
                    jo.put("data_id", data.getData_Id());
                    jo.put("data_code", data.getData_Code());
                    jo.put("data_name", data.getData_Name());
                    jo.put("data_parent_id", data.getData_Parent_Id() == null ? "" : data.getData_Parent_Id());
                    jo.put("data_dictionary", data.getData_Dictionary() == null ? "" : data.getData_Dictionary());
                    jo.put("data_string1", data.getData_String1() == null ? "" : data.getData_String1());
                    jo.put("data_string2", data.getData_String2() == null ? "" : data.getData_String2());
                    jo.put("data_string3", data.getData_String3() == null ? "" : data.getData_String3());
                    jo.put("data_string4", data.getData_String4() == null ? "" : data.getData_String4());
                    jo.put("data_string5", data.getData_String5() == null ? "" : data.getData_String5());
                    arry.add(jo);
                }
            }
            object.put("lists", arry);
            result = object.toString();
        }
        return result;
    }

    /*
    功能：根据id获取码表主表信息
    创建人：liql
    创建时间：2015-12-10
     */
    public String GetDictionaryInfoByid(String jsonStr) {
        String result = null;
        logger.debug("根据id获取码表主表信息getDictionaryInfoByid-------" + jsonStr);
        JSONObject jsonObject = GetJsonOject(jsonStr);
        String code = jsonObject.getString("dictionary_id");
        if (code == null || "".equals(code.trim())) {
            return GetCommonObject("320000", "dictionary_id参数值错误！").toString();
        } else {
            DataDictionary dict = dictionaryDao.listById(DataDictionary.class, code, false);
            JSONObject jo = GetCommonObject("000", "成功");
            if (dict != null) {
                jo.put("dictionary_id", dict.getDictionary_Id());
                jo.put("dictionary_code", dict.getDictionary_Code());
                jo.put("dictionary_name", dict.getDictionary_Name());
                jo.put("dictionary_string1", dict.getDictionary_String1() == null ? "" : dict.getDictionary_String1());
                jo.put("dictionary_string2", dict.getDictionary_String2() == null ? "" : dict.getDictionary_String2());
                jo.put("dictionary_string3", dict.getDictionary_String3() == null ? "" : dict.getDictionary_String3());
                jo.put("dictionary_string4", dict.getDictionary_String4() == null ? "" : dict.getDictionary_String4());
                jo.put("dictionary_string5", dict.getDictionary_String5() == null ? "" : dict.getDictionary_String5());
                jo.put("dictionary_seqno", dict.getDictionary_Seqno() == null ? "" : dict.getDictionary_Seqno());
                jo.put("dictionary_isvalid", dict.getDictionary_Isvalid() == null ? "" : dict.getDictionary_Isvalid());
                jo.put("dictionary_remark", dict.getDictionary_Remark() == null ? "" : dict.getDictionary_Remark());
            }
            result = jo.toString();
        }
        return result;
    }

    /*
    功能：根据id获取码表子表信息
    创建人：liql
    创建时间：2015-12-10
     */
    public String GetDictionaryDataInfoByid(String jsonStr) {
        String result = null;
        logger.debug("根据id获取码表主表信息getDictionaryDataInfoByid-------" + jsonStr);
        JSONObject jsonObject = GetJsonOject(jsonStr);
        String code = jsonObject.getString("data_id");
        if (code == null || "".equals(code.trim())) {
            return GetCommonObject("320000", "data_id参数值错误！").toString();
        } else {
            DataValue data = dictionaryDao.listById(DataValue.class, code, false);
            JSONObject jo = GetCommonObject("000", "成功");
            if (data != null) {
                jo.put("data_id", data.getData_Id());
                jo.put("data_code", data.getData_Code());
                jo.put("data_name", data.getData_Name());
                jo.put("data_parent_id", data.getData_Parent_Id() == null ? "" : data.getData_Parent_Id());
                jo.put("data_dictionary", data.getData_Dictionary() == null ? "" : data.getData_Dictionary());
                jo.put("data_string1", data.getData_String1() == null ? "" : data.getData_String1());
                jo.put("data_string2", data.getData_String2() == null ? "" : data.getData_String2());
                jo.put("data_string3", data.getData_String3() == null ? "" : data.getData_String3());
                jo.put("data_string4", data.getData_String4() == null ? "" : data.getData_String4());
                jo.put("data_string5", data.getData_String5() == null ? "" : data.getData_String5());
                jo.put("data_seqno", data.getData_Seqno() == null ? "" : data.getData_Seqno());
                jo.put("data_isvalid", data.getData_Isvalid());
                jo.put("data_remark", data.getData_Remark() == null ? "" : data.getData_Remark());
            }
            result = jo.toString();
        }
        return result;
    }

    private List getCodeAndValue(String code, String value) {
        String sql1 = "select d.dictionary_id\n" +
                "          from DATA_DICTIONARY d\n" +
                "         where d.dictionary_code = '" + code + "'";

        // List aa1=new ArrayList<>()
        List codeList = dictionaryDao.listByPageBySQL(sql1, 0, 10, false);
        Object ob = codeList.get(0);
        System.out.println(ob.toString());
        Map exmap = (Map) ob;
        String aa = exmap.get("dictionary_id").toString();
        System.out.println("aa=" + aa);
        String sql = "select t.DATA_NAME " +
                "  from DATA_VALUE t " +
                " where t.DATA_DICTIONARY ='" + aa + "'" +
                "   and t.DATA_CODE = '" + value + "'";
        List codeandvalueList = dictionaryDao.listByPageBySQL(sql, 0, 1000, false);
        return codeandvalueList;
    }

    /**
     * 功能：根据code和名称获取码表对应name
     *
     * @param codestr
     * @param value
     * @return
     */
    public String getNameByCodeAndValue(String codestr, String value) {
        String name = "";
        String[] codeLists = new String[]{};
        if (value.contains(",")) {
            codeLists = value.split(",");
        } else {
            codeLists = new String[]{value};
        }
        for (String code : codeLists) {
            List datalist = getCodeAndValue(codestr, code);
            if (datalist != null && datalist.size() > 0) {
                for (Object ob : datalist) {
                    System.out.println(ob.toString());
                    Map exmap = (Map) ob;
                    name += exmap.get("DATA_NAME").toString() + ",";
                }
            }
        }
        return name.substring(0, name.length() - 1);
    }

    /*
        功能：码表主表唯一性验证
        创建人：liql
        创建时间：2015-12-11
         */
    public Boolean DictionaryValidate(String code, String id) {
        Boolean isValidate = true;
        Conjunction con = new Conjunction();
        con.add(Restrictions.eq("dictionary_Code", code));
        if (id != null && !"".equals(id)) {
            con.add(Restrictions.ne("dictionary_Id", id));
        }
        List<DataDictionary> lists = dictionaryDao.listByCriteria(DataDictionary.class, con, false);
        if (lists.size() > 0) {
            isValidate = false;
        }
        return isValidate;
    }

    /*
    功能：码表子表唯一性验证
    创建人：liql
    创建时间：2015-12-11
     */
    public Boolean DictionaryDataValidate(String code, String id, String dictId) {
        Boolean isValidate = true;
        Conjunction con = new Conjunction();
        con.add(Restrictions.eq("data_Code", code));
        con.add(Restrictions.eq("data_Dictionary", dictId));
        if (id != null && !"".equals(id)) {
            con.add(Restrictions.ne("data_Id", id));
        }
        List<DataValue> lists = dictionaryDao.listByCriteria(DataValue.class, con, false);
        if (lists.size() > 0) {
            isValidate = false;
        }
        return isValidate;
    }

    /*
    功能：字符串转为jsonobject
    创建人：liql
    创建时间：2015-12-03
     */
    public JSONObject GetJsonOject(String str) {
        JSONObject jsonObject = new JSONObject();
        return jsonObject.parseObject(str);
    }

    /*
    功能：返回分页共同参数
    创建人：liql
    创建时间：2015-12-03
     */
    public JSONObject GetCommonObjectPages(String errorCode, String errorText, int curragePage, int pageSize, int total) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rspCode", errorCode);
        jsonObject.put("rspDesc", errorText);
        jsonObject.put("curragePage", curragePage);
        jsonObject.put("pageSize", pageSize);
        jsonObject.put("total", total);
        return jsonObject;
    }

    /*
    功能：返回共同参数
    创建人：liql
    创建时间：2015-12-03
     */
    public JSONObject GetCommonObject(String errorCode, String errorText) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rspCode", errorCode);
        jsonObject.put("rspDesc", errorText);
        return jsonObject;
    }
}
