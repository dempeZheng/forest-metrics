package com.zhizus.forest.metrics.client;

import org.apache.commons.lang3.time.StopWatch;

/**
 * Created by Dempe on 2016/12/30.
 */
public class Metrics extends StopWatch {

    private final static MetricsMap metricsMap = new MetricsMap();

    private Meta meta;

    public static Metrics startWithUri(String uri) {
        Metrics metrics = new Metrics();
        metrics.start();
        metrics.meta = metricsMap.get(uri);
        return metrics;
    }

    public void success() {
        meta.code(Status.SUCCESS).gather(getTime());
    }

    public void failed() {
        meta.code(Status.FAILED).gather(getTime());
    }

    public void timeOut() {
        meta.code(Status.TIMEOUT).gather(getTime());
    }

    public enum Status {
        SUCCESS(0), FAILED(1), TIMEOUT(2);
        private int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
