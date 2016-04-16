package com.hzih.ra.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-21
 * Time: 上午9:18
 * To change this template use File | Settings | File Templates.
 */
public class AllowList {

    private String processId;

    public AllowList(String processId) {
        this.processId = processId;
    }

    private String processName;

    public AllowList() {
    }

    public AllowList(String processId, String processName) {
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
