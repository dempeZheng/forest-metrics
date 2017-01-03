package com.zhizus.forest.metrics.simulator;

import com.google.common.collect.Lists;
import com.zhizus.forest.metrics.client.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dempe on 2017/1/3.
 */
public class MetricsClientSimulator {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetricsClientSimulator.class);

    public static void main(String[] args) throws Exception {
        ArrayList<String> uriList = Lists.newArrayList("echo", "test", "hello", "metrics");
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            String uri = uriList.get(rand.nextInt(1000) % 4);
            Metrics metrics = Metrics.startWithUri(uri);
            int time = rand.nextInt(1000);
            TimeUnit.MILLISECONDS.sleep(time);
            LOGGER.info("uri:{},sleep time:{}", uri, time);
            metrics.success();
        }
    }
}
