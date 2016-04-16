package com.hzih.ra.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.dao.StopListDao;
import com.hzih.ra.domain.StopList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-31
 * Time: 下午3:48
 * To change this template use File | Settings | File Templates.
 */
public class StopListDaoImpl extends MyDaoSupport implements StopListDao {
    @Override
    public void setEntityClass() {
        this.entityClass = StopList.class;
    }

    @Override
    public boolean add(StopList stopList) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().save(stopList);
        flag = true;
        return flag;
    }

    @Override
    public boolean exist(String terminalId){
        String hql = new String("from StopList where processId=?");
        List<StopList> list = getHibernateTemplate().find(hql,new String[] { terminalId });
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    
    @Override
    public boolean modify(StopList terminalApp) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().saveOrUpdate(terminalApp);
        flag = true;
        return flag;
    }

    @Override
    public boolean delete(StopList terminalApp) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().delete(terminalApp);
        flag = true;
        return flag;
    }

    @Override
    public PageResult findByPages(String terminalAppName,String terminalId, int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from StopList where 1=1";
        List paramsList = new ArrayList();
        if (terminalAppName != null && terminalAppName.length() > 0) {
            hql += " and processName like ?";
            paramsList.add("%" + terminalAppName + "%");
        }
        if (terminalId != null && terminalId.length() > 0) {
            hql += " and processId like ?";
            paramsList.add("%" + terminalId + "%");
        }

        String countHql = "select count(*) " + hql;

        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
                pageIndex, limit);
        return ps;
    }
    //根据id查询记录
    @Override
    public StopList findById(String processId)throws Exception{
        String hql = new String("from StopList where processId="+processId);
        List<StopList> list = getHibernateTemplate().find(hql);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<StopList> getStopProcess() {
        String hql = new String("from StopList ");
        List<StopList> list = getHibernateTemplate().find(hql);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
}
