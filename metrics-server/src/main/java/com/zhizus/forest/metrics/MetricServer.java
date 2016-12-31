package com.zhizus.forest.metrics;

import com.zhizus.forest.metrics.gen.Metrics;
import com.zhizus.forest.thrift.server.AbstractThriftServer;
import org.apache.thrift.TProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dempe on 2016/12/30 0030.
 */
@Service
public class MetricServer extends AbstractThriftServer implements InitializingBean {

    @Autowired
    private MetricsIface iface;

    @Override
    public int getPort() {
        return 6666;
    }

    @Override
    public TProcessor getProcessor() {
        return new Metrics.Processor(iface);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }
}
