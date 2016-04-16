package com.hzih.ra.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.dao.AccessAddressDao;
import com.hzih.ra.domain.AccessAddress;
import com.hzih.ra.service.AccessAddressService;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
public class AccessAddressServiceImpl implements AccessAddressService{
    public AccessAddressDao getAccessAddressDao() {
        return accessAddressDao;
    }

    public void setAccessAddressDao(AccessAddressDao accessAddressDao) {
        this.accessAddressDao = accessAddressDao;
    }

    private AccessAddressDao accessAddressDao;

    @Override
    public AccessAddress checkUrl(String url) throws Exception {
        return accessAddressDao.checkUrl(url);
    }

    @Override
    public boolean add(AccessAddress accessAddress) throws Exception {
        return accessAddressDao.add(accessAddress);
    }

    @Override
    public boolean modify(AccessAddress accessAddress) throws Exception {
        return accessAddressDao.modify(accessAddress);
    }

    @Override
    public boolean delete(AccessAddress accessAddress) throws Exception {
        return accessAddressDao.delete(accessAddress);
    }

    @Override
    public boolean joinWhiteList(AccessAddress accessAddress) throws Exception {
        return accessAddressDao.joinWhiteList(accessAddress);
    }

    @Override
    public boolean joinBlackList(AccessAddress accessAddress) throws Exception {
        return accessAddressDao.joinBlackList(accessAddress);
    }

    @Override
    public PageResult findByPages(String url, String status, int start, int limit) throws Exception {
        return accessAddressDao.findByPages(url,status,start,limit);
    }


}
