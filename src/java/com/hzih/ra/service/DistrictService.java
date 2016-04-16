package com.hzih.ra.service;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.District;

import java.util.List;


public interface DistrictService {

/**
 * 查询所有省、直辖市级
 */
//	public List<District> findParents();

    /**
     * 根据省、直辖市查询下属市、区、县
     */
//	public List<District> findChildByParent(Long parentId);

//    PageResult findCityByProvinceCode(String code,int pageIndex, int pageLength) throws Exception;

//    PageResult findProvinces(int pageIndex, int pageLength) throws Exception;

//    PageResult findCountryByCityCode(String code,int pageIndex, int pageLength) throws Exception;


    District findById(String id);


    PageResult findCode(String code,int start,int limit) throws Exception;

}
