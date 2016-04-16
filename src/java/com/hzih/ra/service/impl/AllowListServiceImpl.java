package com.hzih.ra.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.dao.AllowListDao;
import com.hzih.ra.domain.AllowList;
import com.hzih.ra.service.AllowListService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-21
 * Time: 上午9:24
 * To change this template use File | Settings | File Templates.
 */
public class AllowListServiceImpl implements AllowListService{
    private AllowListDao allowListDao;

    public AllowListDao getAllowListDao() {
        return allowListDao;
    }

    public void setAllowListDao(AllowListDao allowListDao) {
        this.allowListDao = allowListDao;
    }

    @Override
    public boolean add(AllowList stopList) throws Exception {
        return allowListDao.add(stopList);
    }

    @Override
    public boolean modify(AllowList stopList) throws Exception {
        return allowListDao.modify(stopList);
    }

    @Override
    public boolean delete(AllowList stopList) throws Exception {
        return allowListDao.delete(stopList);
    }

    @Override
    public boolean exist(String processName) throws Exception {
        return allowListDao.exist(processName);
    }

    @Override
    public PageResult findByPages(String processName,String processId, int start, int limit) throws Exception {
        return allowListDao.findByPages(processName,processId, start, limit);
    }

    @Override
    public List<AllowList> getStopProcess() {
        return allowListDao.getStopProcess();
    }

    @Override
    public AllowList findById(String processId)throws Exception{
        return  allowListDao.findById(processId);
    }
}
