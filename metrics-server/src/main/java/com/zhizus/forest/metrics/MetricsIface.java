package com.zhizus.forest.metrics;

import com.zhizus.forest.metrics.gen.Ack;
import com.zhizus.forest.metrics.gen.Meta;
import com.zhizus.forest.metrics.gen.Metrics;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dempe on 2016/12/31 0031.
 */
@Service
public class MetricsIface implements Metrics.Iface {

    @Autowired
    private MetricsCollection metricsCollection;

    @Override
    public Ack sendMeta(Meta meta) throws TException {
        metricsCollection.insertOne(meta);
        return new Ack().setConfigId(111);
    }

    @Override
    public boolean ping() throws TException {
        return true;
    }
}
