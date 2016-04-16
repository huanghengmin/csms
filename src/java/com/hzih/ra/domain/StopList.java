package com.hzih.ra.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-31
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
public class StopList {
    private String processId;

    public StopList(String processId) {
        this.processId = processId;
    }

    private String processName;

    public StopList() {
    }

    public StopList(String processId, String processName) {

        this.processId = processId;
        this.processName = processName;
    }

    public String getProcessId() {

        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
