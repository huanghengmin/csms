package com.hzih.ra.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 下午5:07
 * To change this template use File | Settings | File Templates.
 */
public class BlackList {
    private String url;
//    private int status;

//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }

//    @Override
//    public String toString() {
//        return "BlackList{" +
//                "url='" + url + '\'' +
//                ", status=" + status +
//                '}';
//    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "BlackList{" +
                "url='" + url + '\'' +
                '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public BlackList(String url, int status) {
//        this.url = url;
//        this.status = status;
//    }

    public BlackList() {

    }

    public BlackList(String url) {

        this.url = url;
    }
}
