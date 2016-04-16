package com.hzih.ra.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.dao.OrgcodeDao;
import com.hzih.ra.domain.Orgcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public class OrgcodeDaoImpl extends MyDaoSupport implements OrgcodeDao{
    @Override
    public void setEntityClass() {
         this.entityClass=Orgcode.class;
    }

    @Override
    public List list(String hql)throws Exception {
        List list = null;
        try {
            list = getHibernateTemplate().find(hql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Orgcode findOrgcode(String code) throws Exception{
        String hql = new String("from Orgcode where orgcode ='"+code+"'");
        List list = null;
        try {
            list = getHibernateTemplate().find(hql);
        } catch (Exception e) {
            logger.error("findOrgcode查找错误",e);
        }
        Orgcode orgcode = (Orgcode) list.get(0);
        return orgcode;
    }

    @Override
    public PageResult findProvinceCode(int pageIndex, int pageLength) throws Exception {
//        String hql = new String("from Orgcode where orgcode like '__0000000000'");
//        List<Orgcode> list = null;
//        try {
//            list = getHibernateTemplate().find(hql);
//        } catch (Exception e) {
//            logger.error("findProvinceCode查找错误",e);
//        }
//         return  list;


        String likeCode = "__0000000000";
        StringBuffer sb = new StringBuffer(" from Orgcode s where 1=1");
        List params = new ArrayList(0);// 手动指定容量，避免多次扩容
//        if (null!=likeCode&&!likeCode.equals("")) {
            sb.append(" and orgcode like '"+likeCode+"'");
//            params.add(likeCode);
//        }
        sb.append(" order by orgcode desc");
        String countString = "select count(*) " + sb.toString();
        String queryString = sb.toString();

        PageResult ps = this.findByPage(queryString, countString, params
                .toArray(), pageIndex, pageLength);
        logger.debug(ps == null ? "ps=null" : "ps.results.size:"
                + ps.getResults().size());
        return ps;

    }

    @Override
    public PageResult findCityByProvinceCode(String code,int pageIndex, int pageLength) throws Exception {
        String province_like_code = null;
        if(null!=code&&!"".equals(code)){
            code = code.substring(0,2);
            province_like_code = code+"__________" ;
        }
//        String hql = new String("from Orgcode where orgcode like '"+code+"__00000000'");
//        List<Orgcode> list = null;
//        try {
//            list = getHibernateTemplate().find(hql);
//        } catch (Exception e) {
//            logger.error("findProvinceCode查找错误",e);
//        }
//        return  list;
        StringBuffer sb = new StringBuffer(" from Orgcode s where 1=1");
        List params = new ArrayList(0);// 手动指定容量，避免多次扩容
//        if (null!=province_like_code&&!province_like_code.equals("")) {
            sb.append(" and orgcode like '"+province_like_code+"'");
//            params.add(province_like_code);
//        }
        sb.append(" order by orgcode desc");
        String countString = "select count(*) " + sb.toString();
        String queryString = sb.toString();

        PageResult ps = this.findByPage(queryString, countString, params
                .toArray(), pageIndex, pageLength);
        logger.debug(ps == null ? "ps=null" : "ps.results.size:"
                + ps.getResults().size());
        return ps;
        
    }
    //根据district表查询orgcode表
    @Override
    public PageResult findDataByDistrict(String code,int pageIndex, int pageLength) throws Exception {
        String province_like_code = null;
        if(null!=code&&!"".equals(code)){
            province_like_code = code+"______" ;
        }
        StringBuffer sb = new StringBuffer(" from Orgcode s where 1=1");
        List params = new ArrayList(0);// 手动指定容量，避免多次扩容
//        if (null!=province_like_code&&!province_like_code.equals("")) {
            sb.append(" and orgcode like '"+province_like_code+"'");
//            params.add(province_like_code);
//        }
        sb.append(" order by orgcode desc");
        String countString = "select count(*) " + sb.toString();
        String queryString = sb.toString();

        PageResult ps = this.findByPage(queryString, countString, params
                .toArray(), pageIndex, pageLength);
//        logger.debug(ps == null ? "ps=null" : "ps.results.size:"
//                + ps.getResults().size());
        return ps;

    }

    @Override
    public PageResult findOrgcodeByDistrict(String orgcode, int i, int limit) {
        String likeCode = orgcode+"______";
        StringBuffer sb = new StringBuffer(" from Orgcode where 1=1");
        List params = new ArrayList(0);// 手动指定容量，避免多次扩容
//        if (null!=likeCode&&!likeCode.equals("")) {
        sb.append(" and orgcode like '"+likeCode+"'");
//            params.add(likeCode);
//        }
        sb.append(" order by orgcode desc");
        String countString = "select count(*) " + sb.toString();
        String queryString = sb.toString();

        PageResult ps = this.findByPage(queryString, countString, params .toArray(), i, limit);
//        logger.debug(ps == null ? "ps=null" : "ps.results.size:"
//                + ps.getResults().size());
        return ps;
    }

    @Override
    public Orgcode findByOrgCode(String code) {
        String hql = new String("from Orgcode where orgcode ='"+code+"'");
        List<Orgcode> list = null;
        try {
            list = getHibernateTemplate().find(hql);
        } catch (Exception e) {
            logger.error("findOrgcode查找错误",e);
        }
        if(list!=null){
            return list.get(0);
        }
        return null;
    }


    public PageResult findProvinceByOrgCode(int pageIndex, int pageLength){
        return null;
    }
}
