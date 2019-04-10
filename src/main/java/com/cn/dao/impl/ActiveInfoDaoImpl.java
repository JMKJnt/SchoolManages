package com.cn.dao.impl;

import com.cn.dao.interfaceDao.ActiveInfoDao;
import com.cn.dao.util.HibernateDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Repository("/activeInfoDaoImpl")
public class ActiveInfoDaoImpl implements ActiveInfoDao {
    @Autowired
    private HibernateDAO hibernateDAO;

    /**
     * 描述 删除活动导师信息，根据活动id
     * @param <T>
     * @return
     */
    public <T> String  deleteInfoByAId(String entity,String filedName,String filedId){
        String result="success";
        String hql="delete from "+entity+" entity where entity."+filedName+"='"+filedId+"'";
        Session session=hibernateDAO.getSessionFactory().getCurrentSession();
        Query queryupdate=session.createQuery(hql);
        int ret=queryupdate.executeUpdate();
        return result;
    }

}
