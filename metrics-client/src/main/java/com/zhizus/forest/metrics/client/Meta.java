package com.zhizus.forest.metrics.client;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Dempe on 2016/12/30 0030.
 */
public class Meta {
    private String uri;
    private int code;
    private AtomicInteger count = new AtomicInteger(0);
    private long time;
    private int[] codeArr = new int[3];

    public Meta code(Metrics.Status status) {
        int code = status.getValue();
        codeArr[code] = ++codeArr[code];
        return this;
    }

    public Meta gather(long time) {
        synchronized (this) {
            this.time += time;
        }
        count.incrementAndGet();
        return this;
    }

    public void reset() {
        this.count = new AtomicInteger(0);
        this.time = 0;
    }
}