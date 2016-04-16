package com.hzih.ra.dao;

import cn.collin.commons.dao.BaseDao;
import com.hzih.ra.domain.Role;

public interface RoleDao extends BaseDao {

    public Role findByName(String name) throws Exception;
}
