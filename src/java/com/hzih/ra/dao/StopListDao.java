package com.hzih.ra.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.StopList;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-31
 * Time: 下午3:47
 * To change this template use File | Settings | File Templates.
 */
public interface StopListDao extends BaseDao {
    public boolean add(StopList stopList)throws Exception;

    public boolean modify(StopList stopList)throws Exception;

    public boolean delete(StopList stopList)throws Exception;
    
    public boolean exist(String processName)throws Exception;

    public PageResult findByPages(String processName,String processId, int start, int limit)throws Exception;

    public List<StopList> getStopProcess();

    public StopList findById(String processId)throws Exception;
}
