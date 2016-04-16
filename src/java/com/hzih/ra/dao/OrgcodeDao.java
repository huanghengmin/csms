package com.hzih.ra.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.Orgcode;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public interface OrgcodeDao extends BaseDao{

    public List list(String hql)throws Exception;
    
    public Orgcode findOrgcode(String code)throws Exception;

    public PageResult findProvinceCode(int pageIndex, int pageLength) throws Exception;

    public PageResult findCityByProvinceCode(String code,int pageIndex, int pageLength)throws Exception;

    public PageResult findDataByDistrict(String code,int pageIndex, int pageLength) throws Exception;

    PageResult findOrgcodeByDistrict(String orgcode, int i, int limit);

    Orgcode findByOrgCode(String code);
}
