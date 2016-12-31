package com.zhizus.forest.metrics;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zhizus.forest.metrics.gen.Meta;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dempe on 2016/12/31 0031.
 */
@Repository
public class MetricsCollection {

    @Autowired
    private MongoClient mongoClient;

    MongoDatabase db;

    @PostConstruct
    public void init() {
        db = mongoClient.getDatabase("forest-metrics");
    }

    public enum MetricField {
        URI("uri"), CODE("code"), COUNT("count"), TIME("time");
        private String name;

        MetricField(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    protected Document getField(Meta meta) {
        return new Document(MetricField.URI.getName(), meta.getUri())
                .append(MetricField.CODE.getName(), meta.getCode())
                .append(MetricField.COUNT.getName(), meta.getCount())
                .append(MetricField.TIME.getName(), meta.getTime());
    }

    public void insertOne(Meta meta) {
        getCollection().insertOne(getField(meta));
    }

    protected MongoCollection<Document> getCollection() {
        return db.getCollection("metrics" + new SimpleDateFormat("yyyyMMdd").format(new Date()));
    }


}
