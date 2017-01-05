package com.zhizus.forest.metrics;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zhizus.forest.metrics.gen.MetaConfig;
import com.zhizus.forest.metrics.gen.MetaReq;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Dempe on 2016/12/31 0031.
 */
public class MetricsDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetricsDao.class);

    private MongoClient mongoClient;

    MongoDatabase db;

    public MetricsDao() {
        mongoClient = new MongoClient();
        db = mongoClient.getDatabase("forest-metrics");
    }

    public enum MetricField {
        ID("_id"), URI("uri"), CODES("codes"), COUNT("count"), TIME("time"), MAX_TIME("maxTime"),
        MIN_TIME("minTime"), SUCCESS_COUNT("successCount"), X_AXIS("xAxis"), VERSION("version"), IP("ip"), ROOM_ID("roomId"), TYPE("type");
        private String name;

        MetricField(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    protected Document getDocument(MetaReq meta, MetaConfig config) {
        return new Document(MetricField.URI.getName(), meta.getUri())
                .append(MetricField.COUNT.getName(), meta.getCount())
                .append(MetricField.MAX_TIME.getName(), meta.getMaxTime())
                .append(MetricField.MIN_TIME.getName(), meta.getMinTime())
                .append(MetricField.CODES.getName(), meta.getCodes())
                .append(MetricField.SUCCESS_COUNT.getName(), meta.getCodes().get(0))
                .append(MetricField.X_AXIS.getName(), System.currentTimeMillis() / (1000 * 60) * 1000 * 60)
                .append(MetricField.VERSION.getName(), config.getVersion())
                .append(MetricField.TYPE.getName(), config.getType())
                .append(MetricField.IP.getName(), config.getIp())
                .append(MetricField.ROOM_ID.getName(), config.getRoomId())
                .append(MetricField.TIME.getName(), meta.getTime());
    }

    public void insertOne(MetaReq meta, MetaConfig config) {
        Document document = getDocument(meta, config);
        LOGGER.info("insert document:{}", document);
        getCollection(config.getServiceName()).insertOne(document);
    }

    protected MongoCollection<Document> getCollection(String serviceName) {
//        return db.getCollection("metrics" + new SimpleDateFormat("yyyyMMdd").format(new Date()));
        return db.getCollection("metrics_" + serviceName);
    }


}
