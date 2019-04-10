package com.cn.dao.impl;

import com.cn.dao.interfaceDao.BaseEntityDao;
import com.cn.dao.util.HibernateDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Repository("/baseEntityDaoImpl")
public class BaseEntityDaoImpl implements BaseEntityDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    //新增保存
    @Override
    public <T> String saveOrUpdate(T entity) {
        return hibernateDAO.saveOrUpdate(entity);
    }

    //根据条件查询
    @Override
    public <T> List<T> listByCriteria(Class<T> clazz, Criterion criterion, boolean isCached) {
        return hibernateDAO.listByCriteria(clazz, criterion, isCached);
    }

    //根据条件查询返回第一条
    @Override
    public <T> List<T> listFirstByCriteria(Class<T> clazz, Criterion criterion, boolean isCached) {
        return hibernateDAO.listFirstByCriteria(clazz, criterion, isCached);
    }

    //根据id查询数据list
    @Override
    public <T> T listById(Class<T> clazz, String id, boolean isCached) {
        return hibernateDAO.listById(clazz, id, isCached);
    }

    //根据条件查询数据count
    @Override
    public <T> int countByCriteria(Class<T> clazz, Criterion criterion, boolean isCached) {
        return hibernateDAO.countByCriteria(clazz, criterion, isCached);
    }

    //查询所有列表数据
    @Override
    public <T> List<T> listByAll(Class<T> clazz, boolean isCached) {
        return hibernateDAO.listByAll(clazz, isCached);
    }

    //查询列表 排序
    @Override
    public <T> List<T> listByCriteria(Class<T> clazz, Criterion criterion, Order order, boolean isCached) {
        return hibernateDAO.listByCriteria(clazz, criterion, order, isCached);
    }


    //根据sql分页查询
    @Override
    public <T> List listByPageBySQL(String listSqlString, int pageSize, int pageCurrent, boolean isCached) {
        return hibernateDAO.listByPageBySQL(listSqlString, pageSize, pageCurrent, isCached);
    }

    //根据sql不分页查询
    @Override
    public <T> List listBySQL(String listSqlString, boolean isCached) {
        return hibernateDAO.listBySQL(listSqlString, isCached);
    }

    //根据id删除
    @Override
    public <T> boolean deleteById(Class<T> clazz, String id) {
        String result = hibernateDAO.removeById(clazz, id);
        if (result.equals("success")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据卡劵id删除不同的实体
     *
     * @param entity   实体名称
     * @param filed    实体中关联到的Id字段
     * @param Id 卡劵id
     * @return
     */
    public String deleteEntityById(String entity, String filed, String Id) {
        String result = "success";
        String hql = "delete from " + entity + " entity where entity." + filed + "='" + Id + "'";
        Session session = hibernateDAO.getSessionFactory().getCurrentSession();
        Query queryupdate = session.createQuery(hql);
        int ret = queryupdate.executeUpdate();
        return result;
    }

    @Override
    public <T>List<T>  listByPageCriteria(Class<T> clazz, Criterion criterion, int page, int currentpage, boolean isCached){
        return hibernateDAO.listByPageByCriteria(clazz, criterion, page, currentpage, isCached);
    }
    @Override
    public <T> List<T> listByPageByCriteriaOrderBy(Class<T> clazz,
                                                   Criterion criterion, int page, int currentpage,
                                                   boolean isCached,String orderByFlie,boolean isAsc){
        return hibernateDAO.listByPageByCriteriaOrderBy(clazz, criterion, page, currentpage, isCached,orderByFlie,isAsc);
    }

    /**
     * SQL语句统计(不分页)
     *
     * @param listSqlString
     * @return
     */
    @Override
    public int CountBySQL(String listSqlString){
        return  hibernateDAO.CountBySQL(listSqlString);
    }
}
