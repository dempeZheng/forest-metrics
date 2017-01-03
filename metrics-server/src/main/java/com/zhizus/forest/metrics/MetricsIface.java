package com.zhizus.forest.metrics;

import com.zhizus.forest.metrics.gen.Ack;
import com.zhizus.forest.metrics.gen.MetaConfig;
import com.zhizus.forest.metrics.gen.MetaReq;
import com.zhizus.forest.metrics.gen.MetricService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Dempe on 2016/12/31 0031.
 */
public class MetricsIface implements MetricService.Iface {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetricsIface.class);

    private MetricsCollection metricsCollection;

    public MetricsIface() {
        metricsCollection = new MetricsCollection();
    }

    @Override
    public Ack sendMeta(MetaReq metaReq) throws TException {
        LOGGER.info("metaReq:{}", metaReq);
        metricsCollection.insertOne(metaReq);
        return new Ack((short) 0);
    }

    @Override
    public Ack getConfigId(MetaConfig config) throws TException {
        Ack ack = new Ack();
        ack.setCode((short) 0);
        // todo 根据配置生成hash
        ack.setConfigId("forest-metircs-configId");
        return ack;
    }

    @Override
    public boolean ping() throws TException {
        return true;
    }
}
