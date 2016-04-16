package com.hzih.ra.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.ra.dao.PermissionDao;
import com.hzih.ra.domain.Permission;

public class PermissionDaoImpl extends MyDaoSupport implements PermissionDao {

	@Override
	public void setEntityClass() {
		this.entityClass = Permission.class;
	}

}
