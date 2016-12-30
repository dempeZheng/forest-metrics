package com.zhizus.forest.metrics.common;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Dempe on 2016/12/30.
 */
public class Metrics extends StopWatch {

    private final static Map<String, Map<Integer, Metrics.Meta>> uriCodeMap = Maps.newConcurrentMap();

    private Map<Integer, Metrics.Meta> metaMap;

    private Metrics.Meta meta;

    public static Metrics startWithUri(String uri) {
        Metrics metrics = new Metrics();
        metrics.start();
        metrics.metaMap = uriCodeMap.get(uri);
        return metrics;
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
