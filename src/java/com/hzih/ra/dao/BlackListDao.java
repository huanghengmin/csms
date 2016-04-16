package com.hzih.ra.dao;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.BlackList;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 下午5:11
 * To change this template use File | Settings | File Templates.
 */
public interface BlackListDao  {

    public List<BlackList> findAll();

    public BlackList checkUrl(String url)throws Exception;

    public boolean add(BlackList blackList)throws Exception;

    public boolean modify(BlackList blackList)throws Exception;

    public boolean delete(BlackList blackList)throws Exception;

    public PageResult findByPages(String url, int start, int limit)throws Exception;
}
