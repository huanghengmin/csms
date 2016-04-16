package com.hzih.ra.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
public class AccessAddress {
    private String url;
    private int status;

    public AccessAddress() {
    }

    public AccessAddress(String url, int status) {
        this.url = url;
        this.status = status;
    }

    public AccessAddress(String url) {

        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
