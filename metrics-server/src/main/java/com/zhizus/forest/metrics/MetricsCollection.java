package com.zhizus.forest.metrics;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zhizus.forest.metrics.gen.MetaReq;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dempe on 2016/12/31 0031.
 */
public class MetricsCollection {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetricsCollection.class);

    private MongoClient mongoClient;

    MongoDatabase db;

    public MetricsCollection() {
        mongoClient = new MongoClient();
        db = mongoClient.getDatabase("forest-metrics");
    }

    public enum MetricField {
        CONFIG_ID("configId"), URI("uri"), CODES("codes"), COUNT("count"), TIME("time"), MAX_TIME("maxTime"), MIN_TIME("minTime");
        private String name;

        MetricField(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    protected Document getDocument(MetaReq meta) {
        return new Document(MetricField.URI.getName(), meta.getUri())
                .append(MetricField.CONFIG_ID.getName(), meta.getConfigId())
                .append(MetricField.COUNT.getName(), meta.getCount())
                .append(MetricField.MAX_TIME.getName(), meta.getMaxTime())
                .append(MetricField.MIN_TIME.getName(), meta.getMinTime())
                .append(MetricField.CODES.getName(), meta.getCodes())
                .append(MetricField.TIME.getName(), meta.getTime());
    }

    public void insertOne(MetaReq meta) {
        Document document = getDocument(meta);
        LOGGER.info("insert document:{}", document);
        getCollection().insertOne(document);
    }

    protected MongoCollection<Document> getCollection() {
        return db.getCollection("metrics" + new SimpleDateFormat("yyyyMMdd").format(new Date()));
    }


}
