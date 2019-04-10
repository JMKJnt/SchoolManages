package com.cn.dao.interfaceDao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.util.List;

public interface BaseEntityDao {

    //新增保存
    public <T> String saveOrUpdate(T entity);
    //根据条件查询
    public <T> List<T> listByCriteria(Class<T> clazz, Criterion criterion, boolean isCached);
    //根据条件查询返回第一条
    public <T> List<T> listFirstByCriteria(Class<T> clazz, Criterion criterion, boolean isCached);
    //根据id查询数据list
    public <T> T listById(Class<T> clazz, String id, boolean isCached);
    //根据条件查询数据count
    public <T> int countByCriteria(Class<T> clazz, Criterion criterion, boolean isCached);
    //查询所有列表数据
    public <T> List<T> listByAll(Class<T> clazz, boolean isCached);
    //查询列表 排序
    public <T> List<T> listByCriteria(Class<T> clazz, Criterion criterion,
                                      Order order, boolean isCached);

    //根据sql分页查询
    public <T> List<T> listByPageBySQL(String listSqlString,int pageSize,int pageCurrent,boolean isCached);
    //根据sql不分页查询
    public <T> List<T> listBySQL(String listSqlString, boolean isCached);

    //根据id删除
    public <T> boolean deleteById(Class<T> clazz,String id);
    //根据关联id删除信息
    public String deleteEntityById(String entity, String filed, String Id);

    public <T>List<T>  listByPageCriteria(Class<T> clazz, Criterion criterion, int page, int currentpage, boolean isCached);
    /*
     分页排序查询
     */
    public <T> List<T> listByPageByCriteriaOrderBy(Class<T> clazz,
                                                   Criterion criterion, int pageSize, int pageCurrent,
                                                   boolean isCached,String orderByFlie,boolean isAsc);
    /**
     * SQL语句统计(不分页)
     *
     * @param listSqlString
     * @return
     */
    public int CountBySQL(String listSqlString);

}
