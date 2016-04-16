package com.hzih.ra.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-30
 * Time: 下午2:47
 * To change this template use File | Settings | File Templates.
 */
public class BlueTooth {
    private long date;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "BlueTooth{" +
                "date=" + date +
                ", num=" + num +
                '}';
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
