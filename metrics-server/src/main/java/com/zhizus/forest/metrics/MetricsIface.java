package com.zhizus.forest.metrics;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.zhizus.forest.metrics.gen.Ack;
import com.zhizus.forest.metrics.gen.MetaConfig;
import com.zhizus.forest.metrics.gen.MetaReq;
import com.zhizus.forest.metrics.gen.MetricService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Dempe on 2016/12/31 0031.
 */
public class MetricsIface implements MetricService.Iface {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetricsIface.class);

    private MetricsDao metricsDao;

    private Map<String, MetaConfig> configMap = Maps.newConcurrentMap();

    public MetricsIface() {
        metricsDao = new MetricsDao();
    }

    @Override
    public Ack sendMeta(MetaReq metaReq) throws TException {
        LOGGER.info("metaReq:{}", metaReq);
        String configId = metaReq.getConfigId();
        if (Strings.isNullOrEmpty(configId) || configMap.get(configId) == null) {
            return new Ack((short) 1);
        }
        MetaConfig metaConfig = configMap.get(configId);
        metricsDao.insertOne(metaReq, metaConfig);
        return new Ack((short) 0);
    }

    @Override
    public Ack getConfigId(MetaConfig config) throws TException {
        Ack ack = new Ack();
        ack.setCode((short) 0);
        String configId = getSign(config);
        configMap.put(configId, config);
        ack.setConfigId(configId);
        return ack;
    }

    @Override
    public boolean ping() throws TException {
        return true;
    }


    public String getSign(MetaConfig config) {
        StringBuilder sb = new StringBuilder();
        sb.append(config.getServiceName()).append(config.getIp()).append(config.getRoomId()).append(config.getVersion()).append(config.getType());
        return md5LowerCase(sb.toString());
    }

    public static String md5LowerCase(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte element : b) {
                i = element;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("md5 digest error!", e);
        }
        return plainText;
    }

}
