package com.zhizus.forest.metrics.client;

import com.google.common.base.Strings;
import com.zhizus.forest.metrics.gen.Ack;
import com.zhizus.forest.metrics.gen.MetricService;
import com.zhizus.forest.thrift.client.DefaultThriftClient;
import com.zhizus.forest.thrift.client.registry.conf.ConfRegistry;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dempe on 2016/12/30 0030.
 */
public class MetricsMap extends ConcurrentHashMap<String, Meta> {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetricsMap.class);

    private DefaultThriftClient client;

    private String configId;

    public MetricsMap() throws Exception {
        this.client = new DefaultThriftClient(new ConfRegistry("localhost:6666"), new GenericKeyedObjectPoolConfig());
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            public void run() {
                for (Meta meta : values()) {
                    try {
                        if (Strings.isNullOrEmpty(configId)) {
                            configId = getConfigId(new Config()).getConfigId();
                        }
                        if (Strings.isNullOrEmpty(configId)) {
                            LOGGER.error("config id is empty.");
                            continue;
                        }
                        Ack ack = sendMeta(meta);
                        //


                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    meta.reset();

                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public Ack sendMeta(Meta meta) throws Exception {
        LOGGER.info("meta:{}", meta);
        MetricService.Client iface = client.iface(MetricService.Client.class);
        return iface.sendMeta(meta.toMetaReq());
    }

    public Ack getConfigId(Config config) throws Exception {
        MetricService.Client iface = client.iface(MetricService.Client.class);
        return iface.getConfigId(config);
    }

}
