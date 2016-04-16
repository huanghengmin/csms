package com.hzih.ra.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.AllowList;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-21
 * Time: 上午9:20
 * To change this template use File | Settings | File Templates.
 */
public interface AllowListDao extends BaseDao {
    public boolean add(AllowList stopList)throws Exception;

    public boolean modify(AllowList stopList)throws Exception;

    public boolean delete(AllowList stopList)throws Exception;

    public boolean exist(String processName)throws Exception;

    public PageResult findByPages(String processName,String processId, int start, int limit)throws Exception;

    public List<AllowList> getStopProcess();

    public AllowList findById(String processId)throws Exception;
}
