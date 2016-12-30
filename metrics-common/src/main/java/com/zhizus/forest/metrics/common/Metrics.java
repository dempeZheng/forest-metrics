package com.zhizus.forest.metrics.common;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Dempe on 2016/12/30.
 */
public class Metrics extends StopWatch {

    private final static Map<String, Map<Integer, Meta>> uriCodeMap = Maps.newConcurrentMap();

    private static Map<String, Metrics> metricsMap = Maps.newConcurrentMap();

    private Map<Integer, Meta> metaMap;

    private Meta meta;


    public Metrics startWithUri(String uri) {
        super.start();
        this.metaMap = uriCodeMap.get(uri);
        return this;
    }

    public void success() {
        this.meta = metaMap.get(Status.SUCCESS.getValue());
        meta.gather(getTime());
    }
    public void failed() {
        this.meta = metaMap.get(Status.FAILED.getValue());
        meta.gather(getTime());
    }
    public void timeOut() {
        this.meta = metaMap.get(Status.TIMEOUT.getValue());
        meta.gather(getTime());
    }

    public enum Status {
        SUCCESS(200), FAILED(500), TIMEOUT(408);
        private int value;

        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static synchronized Metrics getMetrics(String serviceName) {
        Metrics metrics = metricsMap.get(serviceName);
        if (metrics == null) {
            metrics = new Metrics(serviceName);
            metricsMap.put(serviceName, metrics);
        }
        return metrics;
    }

    private String serviceName;

    private Metrics(String serviceName) {
        this.serviceName = serviceName;
    }


    /**
     * Created by Dempe on 2016/12/30.
     */
    public class Meta {
        private String uri;
        private int code;
        private AtomicInteger count;
        private long time;

        public Meta() {
        }

        private Meta(String uri, int code) {
            this.uri = uri;
            this.code = code;
        }

        public void gather(long time) {
            synchronized (this) {
                this.time += time;
            }
            count.incrementAndGet();

        }

        private synchronized int add(int time) {
            this.time += time;
            return time;
        }

        private int incrementAndGet() {
            return count.incrementAndGet();
        }

        public void reset() {
            this.count = new AtomicInteger(0);
            this.time = 0;
        }
    }
}
