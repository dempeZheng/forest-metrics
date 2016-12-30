package com.zhizus.forest.metrics.client;

import com.google.common.collect.Queues;
import com.zhizus.forest.metrics.common.MetricsModel;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Dempe on 2016/12/30.
 */
public class MetricsReporter {
    public LinkedBlockingQueue<MetricsModel> queue = Queues.newLinkedBlockingQueue();
}
