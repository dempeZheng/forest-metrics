package com.zhizus.forest.metrics.client;

import com.google.common.collect.Lists;
import com.zhizus.forest.metrics.gen.MetaReq;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Dempe on 2016/12/30 0030.
 */
public class Meta {
    private String uri;
    private AtomicInteger count = new AtomicInteger(0);
    private long time;
    private int maxTime;
    private int minTime;
    private int[] codeArr = new int[3];

    public Meta(String uri) {
        this.uri = uri;
    }

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

    public MetaReq toMetaReq() {
        MetaReq req = new MetaReq();
        req.setUri(uri);
        req.setCount(count.get());
        req.setTime(time);
        req.setMaxTime(maxTime);
        req.setMinTime(minTime);
        List<Integer> codes = Lists.newArrayList(codeArr.length);
        for (int code : codeArr) {
            codes.add(code);
        }
        req.setCodes(codes);
        return req;
    }

    public void reset() {
        this.count = new AtomicInteger(0);
        this.time = 0;
        this.codeArr = new int[3];
        this.maxTime = 0;
        this.minTime = 0;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "uri='" + uri + '\'' +
                ", count=" + count +
                ", time=" + time +
                ", maxTime=" + maxTime +
                ", minTime=" + minTime +
                ", codeArr=" + Arrays.toString(codeArr) +
                '}';
    }
}