package com.hzih.ra.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.CaRole;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
public interface CaRoleDao extends BaseDao{
    public boolean add(CaRole caRole)throws Exception;

    public boolean modify(CaRole caRole)throws Exception;

    public boolean delete(CaRole caRole)throws Exception;

    public CaRole findById(int id)throws Exception;

    public PageResult findByPages(String role_name, int start, int limit)throws Exception;

    void allow(CaRole ca_role);

    void stop(CaRole ca_role);
}
