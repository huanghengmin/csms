package com.hzih.ra.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.dao.DistrictDao;
import com.hzih.ra.domain.District;
import com.hzih.ra.service.DistrictService;
import org.apache.log4j.Logger;


public class DistrictServiceImpl implements DistrictService {

    private final static Logger logger = Logger.getLogger(DistrictServiceImpl.class);
    private DistrictDao districtDao;

//	@Override
//	public List<District> findChildByParent(Long parentId) {
//		return districtDao.findChildByParent(parentId);
//	}

//    @Override
//    public PageResult findCityByProvinceCode(String code, int pageIndex, int pageLength) throws Exception {
//        return districtDao.findCityByProvinceCode(code,pageIndex,pageLength);
//    }

//    @Override
//    public PageResult findProvinces(int pageIndex, int pageLength) throws Exception {
//        return districtDao.findCode(pageIndex, pageLength);
//    }

//    @Override
//    public PageResult findCountryByCityCode(String code, int pageIndex, int pageLength) throws Exception {
//        return districtDao.findCountryByCityCode(code,pageIndex,pageLength);
//    }

    public DistrictDao getDistrictDao() {
        return districtDao;
    }

    public void setDistrictDao(DistrictDao districtDao) {
        this.districtDao = districtDao;
    }

    public static Logger getLogger() {
        return logger;
    }

//	@Override
//	public List<District> findParents() {
//		return districtDao.getAllParents();
//	}

    @Override
    public District findById(String id) {
        return districtDao.findById(id);
    }

    @Override
    public PageResult findCode(String code, int start, int limit) throws Exception {
        return districtDao.findCode(code,start,limit);
    }
}
