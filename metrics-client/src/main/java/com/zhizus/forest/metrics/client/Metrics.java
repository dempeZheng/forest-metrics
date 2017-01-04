package com.zhizus.forest.metrics.client;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Dempe on 2016/12/30.
 */
public class Metrics extends StopWatch {

    private final static Logger LOGGER = LoggerFactory.getLogger(Metrics.class);

    private static MetricsMap metricsMap = null;

    static {
        try {
            metricsMap = new MetricsMap();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private Meta meta;

    public static Metrics startWithUri(String uri) {
        Metrics metrics = new Metrics();
        metrics.start();
        metrics.meta = metricsMap.get(uri);
        if (metrics.meta == null) {
            metrics.meta = new Meta(uri);
            metricsMap.put(uri, metrics.meta);
        }
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

        public static Status getStatus(int value) {
            switch (value) {
                case 0:
                    return SUCCESS;
                case 1:
                    return FAILED;
                case 2:
                    return TIMEOUT;

            }
            return SUCCESS;
        }

        public int getValue() {
            return value;
        }
    }


}
