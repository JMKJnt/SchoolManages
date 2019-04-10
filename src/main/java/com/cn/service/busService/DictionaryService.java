package com.cn.service.busService;

public interface DictionaryService {
    //根据dataDictionary获取码表字表信息列表
    public String GetDictionaryDataInfoList(String jsonStr);
    //新增码表子表信息
    public String InsertDictionaryDataInfo(String jsonStr);
    //修改码表子表信息
    public String UpdateDictionaryDataInfo(String jsonStr);
    //获取码表主表信息
    public String GetDictionaryInfoList(String jsonStr);
    //新增码表主表信息
    public String InsertDictionaryInfo(String jsonStr);
    //修改码表主表信息
    public String UpdateDictionaryInfo(String jsonStr);
    //根据码表code获取码表详细信息列表
    public String GetDictionaryDataList(String jsonStr);
    //根据id获取码表主表信息
    public String GetDictionaryInfoByid(String jsonStr);
    //根据id获取码表子表信息
    public String GetDictionaryDataInfoByid(String jsonStr);

}
