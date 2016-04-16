package com.hzih.ra.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.dao.AccessAddressDao;
import com.hzih.ra.domain.AccessAddress;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 */
public class AccessAddressDaoImpl extends MyDaoSupport implements AccessAddressDao{
    @Override
    public void setEntityClass() {
        this.entityClass = AccessAddress.class;
    }

    @Override
    public AccessAddress checkUrl(String url) throws Exception {
        String hql="from AccessAddress accessAddress where accessAddress.url ='"+url+"'";
        List<AccessAddress> accessAddressList = null;
        try {
            accessAddressList = super.getHibernateTemplate().find(hql);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        if(accessAddressList!=null&&accessAddressList.size()>0){
            return accessAddressList.get(0);
        }   else {
            return null;
        }
    }

    @Override
    public boolean add(AccessAddress accessAddress) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().save(accessAddress);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean modify(AccessAddress accessAddress) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().saveOrUpdate(accessAddress);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(AccessAddress accessAddress) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().delete(accessAddress);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean joinWhiteList(AccessAddress accessAddress) throws Exception {
        boolean flag =false;
        String s="update AccessAddress accessAddress set accessAddress.status = "+accessAddress.getStatus()+" where accessAddress.url='"+accessAddress.getUrl()+"'";
        Session session = super.getSession();
        try{
            session.beginTransaction();
            Query query=session.createQuery(s);
            query.executeUpdate();
            session.getTransaction().commit();
            flag=true;

        } catch (Exception e){

        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public boolean joinBlackList(AccessAddress accessAddress) throws Exception {
        boolean flag =false;
        String s="update AccessAddress accessAddress set accessAddress.status = "+accessAddress.getStatus()+" where accessAddress.url='"+accessAddress.getUrl()+"'";
        Session session = super.getSession();
        try{
            session.beginTransaction();
            Query query=session.createQuery(s);
            query.executeUpdate();
            session.getTransaction().commit();
            flag=true;

        } catch (Exception e){

        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public PageResult findByPages(String url, String status, int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from AccessAddress s where 1=1";
        List paramsList = new ArrayList();
        if (url != null && url.length() > 0) {
            hql += " and url like ?";
            paramsList.add("%" + url + "%");
        }
        if (status != null && status.length() > 0) {
            hql += " and status=?";
            paramsList.add(Integer.parseInt(status));
        }
        String countHql = "select count(*) " + hql;

        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
                pageIndex, limit);
        return ps;
    }

}
