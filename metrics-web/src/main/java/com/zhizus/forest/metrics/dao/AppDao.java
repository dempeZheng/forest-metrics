package com.zhizus.forest.metrics.dao;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.zhizus.forest.metrics.bean.App;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Dempe on 2017/1/5.
 */
@Repository
public class AppDao {

    @Value("${mongo.database}")
    private String dbName;

    @Autowired
    private MongoClient mongoClient;

    MongoCollection<Document> collection;

    @PostConstruct
    public void init() {
        this.collection = mongoClient.getDatabase(dbName).getCollection("metric_app");
    }

    private Document getDocument(App app) {
        return new Document().append("_id", app.getServiceName())
                .append("appKey", app.getAppKey())
                .append("createAt", app.getCreateAt())
                .append("createBy", app.getCreateBy());
    }

    public App getApp(Document document) {
        App app = new App();
        app.setAppKey(document.getString("appKey"));
        app.setCreateBy(document.getString("createBy"));
        app.setCreateAt(document.getLong("createAt"));
        app.setServiceName(document.getString("_id"));
        return app;
    }

    public void save(App app) {
        collection.insertOne(getDocument(app));
    }

    public List<App> find() {
        List<App> list = Lists.newArrayList();
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> iterator = documents.iterator();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            list.add(getApp(next));
        }
        return list;
    }

}
