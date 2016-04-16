package com.hzih.ra.servlet;

import java.util.TimerTask;

public class OnLineUserTask extends TimerTask {
    private OnLineUserTimingUpdate onLineTimingUpdate = new OnLineUserTimingUpdate();

    @Override
    public void run() {
        //在线用户定时更新任务
       onLineTimingUpdate.updateOnLine();
    }

} 