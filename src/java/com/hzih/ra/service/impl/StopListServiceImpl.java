package com.hzih.ra.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.dao.StopListDao;
import com.hzih.ra.domain.StopList;
import com.hzih.ra.service.StopListService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-31
 * Time: 下午3:58
 * To change this template use File | Settings | File Templates.
 */
public class StopListServiceImpl implements StopListService {
    private StopListDao stopListDao;

    public StopListDao getStopListDao() {
        return stopListDao;
    }

    public void setStopListDao(StopListDao stopListDao) {
        this.stopListDao = stopListDao;
    }

    @Override
    public boolean add(StopList stopList) throws Exception {
        return stopListDao.add(stopList);
    }

    @Override
    public boolean modify(StopList stopList) throws Exception {
        return stopListDao.modify(stopList);
    }

    @Override
    public boolean delete(StopList stopList) throws Exception {
        return stopListDao.delete(stopList);
    }

    @Override
    public boolean exist(String processName) throws Exception {
        return stopListDao.exist(processName);
    }

    @Override
    public PageResult findByPages(String processName,String processId, int start, int limit) throws Exception {
        return stopListDao.findByPages(processName,processId, start, limit);
    }

    @Override
    public List<StopList> getStopProcess() {
        return stopListDao.getStopProcess();
    }

    @Override
    public StopList findById(String processId)throws Exception{
        return  stopListDao.findById(processId);
    }
}
