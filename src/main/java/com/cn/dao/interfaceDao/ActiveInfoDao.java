package com.cn.dao.interfaceDao;

public interface ActiveInfoDao {

    /**
     * 描述 删除活动导师信息，根据活动id
     * @param <T>
     * @return
     */
    public <T> String  deleteInfoByAId(String entity,String filedName,String filedId);
}
