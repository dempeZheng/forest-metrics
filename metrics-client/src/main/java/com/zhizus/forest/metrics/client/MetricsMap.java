package com.zhizus.forest.metrics.client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dempe on 2016/12/30 0030.
 */
public class MetricsMap extends ConcurrentHashMap<String, Meta> {

    public MetricsMap() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            public void run() {
                for (Meta meta : values()) {
                    send(meta);
                    meta.reset();
                }
            }
        }, 1, 1, TimeUnit.MINUTES);
    }

    public void send(Meta meta) {

    }
}
