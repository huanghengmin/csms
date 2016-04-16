package com.hzih.ra.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.CaPermission;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午9:51
 * To change this template use File | Settings | File Templates.
 */
public interface CaRolePermissionDao extends BaseDao {
    //得到角色对应的资源
    public PageResult getPerminssionsByRoleId(int roleId,int start,int limit)throws Exception;
    //添加资源到角色
    public void addPermissionsToRoleId(String pIds,int roleId)throws Exception;

    //删除所有roleId 关联
    public boolean delAllCaPermissionsByRoleId(int roleId)throws Exception;

    public PageResult findCaPermissionsByOtherRoleId(int roleId, int start, int limit);

    public void addPermissionToRoleId(int pId, int roleId)throws Exception;

    void delByRoleIdAndPermissionId(int i, int i1)throws Exception;

    void delByPermissionId(int i)throws Exception;
}
