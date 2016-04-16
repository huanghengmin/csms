package com.hzih.ra.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-30
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
public class Wifi {
    private long date;
    private int num;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Wifi{" +
                "date=" + date +
                ", num=" + num +
                '}';
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
