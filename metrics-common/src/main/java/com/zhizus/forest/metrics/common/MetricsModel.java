package com.zhizus.forest.metrics.common;

/**
 * Created by Dempe on 2016/12/29.
 */
public class MetricsModel {
    private String serviceName;//服务名
    private String uri;//监控的地址
    private int maxTime;//最大耗时
    private int minTime;//最小耗时
    private int avgTime;//平均耗时
    private int totalCount;//总共调用次数
    private int errCount;// 失败次数

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getMinTime() {
        return minTime;
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
    }

    public int getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(int avgTime) {
        this.avgTime = avgTime;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getErrCount() {
        return errCount;
    }

    public void setErrCount(int errCount) {
        this.errCount = errCount;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "MetricsModel{" +
                "serviceName='" + serviceName + '\'' +
                ", uri='" + uri + '\'' +
                ", maxTime=" + maxTime +
                ", minTime=" + minTime +
                ", avgTime=" + avgTime +
                ", totalCount=" + totalCount +
                ", errCount=" + errCount +
                '}';
    }
}
