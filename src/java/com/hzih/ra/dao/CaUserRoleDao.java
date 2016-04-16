package com.hzih.ra.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.CaUser;
import com.hzih.ra.domain.CaUserRole;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午9:50
 * To change this template use File | Settings | File Templates.
 */
public interface CaUserRoleDao extends BaseDao{

    //得到角色对应的用户列表
    public PageResult getUsersByRoleId(int roleId,int start,int limit)throws Exception;

    //添加用户到角色
    public void addUsersToRoleId(String uIds,int roleId)throws Exception;

    public void addUserToRoleId(int uId,int roleId)throws Exception;

    //删除所有roleId 关联
    public boolean delAllByRoleId(int roleId)throws Exception;

    public PageResult findCaUserByOtherRoleId(int roleId, int start, int limit);

    void delByRoleIdAndUserId(int i, int i1)throws Exception;

    void delByUserId(int i)throws Exception;

    List<CaUserRole> findByUserId(int id)throws Exception;
}
