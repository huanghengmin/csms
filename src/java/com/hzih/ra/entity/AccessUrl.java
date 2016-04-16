package com.hzih.ra.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-25
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
public class AccessUrl {
    private String url;
    private int num;

    public AccessUrl() {
    }

    @Override
    public String toString() {
        return "AccessUrl{" +
                "url='" + url + '\'' +
                ", num=" + num +
                '}';
    }

    public AccessUrl(String url, int num) {

        this.url = url;
        this.num = num;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
