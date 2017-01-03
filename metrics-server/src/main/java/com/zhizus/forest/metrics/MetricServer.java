package com.zhizus.forest.metrics;

import com.zhizus.forest.metrics.gen.MetricService;
import com.zhizus.forest.thrift.server.AbstractThriftServer;
import org.apache.thrift.TProcessor;

/**
 * Created by Dempe on 2016/12/30 0030.
 */
public class MetricServer extends AbstractThriftServer {

    private MetricsIface iface;

    public MetricServer() {
        iface = new MetricsIface();
    }

    @Override
    public int getPort() {
        return 6666;
    }

    @Override
    public TProcessor getProcessor() {
        return new MetricService.Processor(iface);
    }

    public static void main(String[] args) {
        new MetricServer().start();
    }
}
