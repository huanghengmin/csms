package com.hzih.ra.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.District;

import java.util.List;

public interface DistrictDao extends BaseDao {

    //	List<District> getAllParents();
//
//	List<District> findChildByParent(Long parentId);
//
//	List<District> findChildFirst();
//
    District findById(String id);

//    PageResult findCityByProvinceCode(String code,int pageIndex, int pageLength) throws Exception;

    PageResult findCode(String code,int start,int limit) throws Exception;

//    PageResult findCountryByCityCode(String code,int pageIndex, int pageLength) throws Exception;
}
