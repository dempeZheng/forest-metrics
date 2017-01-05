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
    public String uri;
    public AtomicInteger count = new AtomicInteger(0);
    public long time;
    public int maxTime;
    public int minTime;
    public int[] codeArr = new int[3];

    public Meta(String uri) {
        this.uri = uri;
    }

    public Meta code(Metrics.Status status) {
        int code = status.getValue();
        codeArr[code] = codeArr[code] + 1;
        return this;
    }

    public Meta gather(long time) {
        synchronized (this) {
            this.time += time;
        }
        if (maxTime < time) {
            maxTime = (int) time;
        }
        if (minTime > time || maxTime == 0) {
            minTime = (int) time;
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