package com.hzih.ra.service;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.AccessAddress;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public interface AccessAddressService {

    public AccessAddress checkUrl(String url)throws Exception;

    public boolean add(AccessAddress accessAddress)throws Exception;

    public boolean modify(AccessAddress accessAddress)throws Exception;

    public boolean delete(AccessAddress accessAddress)throws Exception;

    public boolean joinWhiteList(AccessAddress accessAddress)throws Exception;

    public boolean joinBlackList(AccessAddress accessAddress)throws Exception;

    public PageResult findByPages(String url,String status , int start, int limit)throws Exception;
}
