package com.hzih.ra.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.dao.DistrictDao;
import com.hzih.ra.domain.District;

import java.util.ArrayList;
import java.util.List;


public class DistrictDaoImpl extends MyDaoSupport implements DistrictDao {

    @Override
    public void setEntityClass() {
        this.entityClass=District.class;
    }

    /*@Override
     public List<District> findChildByParent(Long parentId) {
         Long nextId=parentId+10000;
         String hql="from District where id>="+parentId+" and id<"+nextId;
         List<District> list=super.getHibernateTemplate().find(hql);
         return list;
     }

     public List<District> findChildFirst() {
         String hql="from District where id=010101";
         List<District> list=super.getHibernateTemplate().find(hql);
         return list;
     }


     @Override
     public List<District> getAllParents() {
         String hql="from District where mod(id,10000)=0";
         List<District> list=super.getHibernateTemplate().find(hql);
         return list;
     }*/

    @Override
    public District findById(String id) {
        String hql="from District where id = "+id;
        List<District> list = super.getHibernateTemplate().find(hql);
        return list.get(0);
    }

    @Override
    public PageResult findCode(String code,int start,int limit) throws Exception {
        String filter_code = null;
        if(code==null){
            filter_code = "__0000";
        }else if(code.endsWith("0000")){
            String  start_code = code.substring(0,2);
            filter_code = start_code+"__00";
        }else if(!code.endsWith("0000")&&code.endsWith("00")){
            String  start_code = code.substring(0,4);
            filter_code = start_code+"__";
        }else {
            filter_code = code;
        }
        int pageIndex = start/limit+1;
        String hql = " from District where 1=1";
        List paramsList = new ArrayList();
        if (filter_code != null && filter_code.length() > 0) {
            hql += " and district_id != '"+code+"' and district_id like ?";
            paramsList.add("%" + filter_code + "%");
        }
        String countHql = "select count(*) " + hql;

        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
                pageIndex, limit);
        return ps;

    }


    /*@Override
    public PageResult findCityByProvinceCode(String code,int pageIndex, int pageLength) throws Exception {
        String province_like_code = null;
        if(null!=code&&!"".equals(code)){
            code = code.substring(0,2);
            province_like_code = code+"__00" ;
        }
        StringBuffer sb = new StringBuffer(" from District where 1=1");
        List params = new ArrayList(0);// 手动指定容量，避免多次扩容
//        if (null!=province_like_code&&!province_like_code.equals("")) {
            sb.append(" and district_id like '"+province_like_code+"'");
//            params.add(province_like_code);
//        }
        sb.append(" order by district_id desc");
        String countString = "select count(*) " + sb.toString();
        String queryString = sb.toString();

        PageResult ps = this.findByPage(queryString, countString, params
                .toArray(), pageIndex, pageLength);
        logger.debug(ps == null ? "ps=null" : "ps.results.size:"
                + ps.getResults().size());
        return ps;
    }*/


    /* @Override
    public PageResult findCountryByCityCode(String code,int pageIndex, int pageLength) throws Exception {
        String province_like_code = null;
        if(null!=code&&!"".equals(code)){
            code = code.substring(0,4);
            province_like_code = code+"__" ;
        }
        StringBuffer sb = new StringBuffer(" from District where 1=1");
        List params = new ArrayList(0);// 手动指定容量，避免多次扩容
//        if (null!=province_like_code&&!province_like_code.equals("")) {
            sb.append(" and district_id like '"+province_like_code+"'");
//            params.add(province_like_code);
//        }
        sb.append(" order by district_id desc");
        String countString = "select count(*) " + sb.toString();
        String queryString = sb.toString();

        PageResult ps = this.findByPage(queryString, countString, params
                .toArray(), pageIndex, pageLength);
        logger.debug(ps == null ? "ps=null" : "ps.results.size:"
                + ps.getResults().size());
        return ps;
    }*/

}
