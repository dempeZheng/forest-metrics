package com.zhizus.forest.metrics;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.zhizus.forest.metrics.gen.MetaConfig;
import com.zhizus.forest.metrics.gen.MetaReq;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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


    public AggregateIterable<Document> groupByXAxis(String serviceName, String uri, String ip, String roomId, String version, String type, long sTime, long eTime) {
        BasicDBObject filter = new BasicDBObject(MetricField.URI.getName(), uri);
        if (!Strings.isNullOrEmpty(ip)) {
            filter.append(MetricField.IP.getName(), ip);
        }
        if (!Strings.isNullOrEmpty(roomId)) {
            filter.append(MetricField.ROOM_ID.getName(), roomId);
        }
        if (!Strings.isNullOrEmpty(version)) {
            filter.append(MetricField.VERSION.getName(), version);
        }
        if (!Strings.isNullOrEmpty(type)) {
            filter.append(MetricField.TYPE.getName(), type);
        }
        filter.append(MetricField.X_AXIS.getName(), new BasicDBObject("$gt", sTime).append("$lt", eTime));

        ArrayList<BasicDBObject> pipeline = Lists.newArrayList(new BasicDBObject().append("$match", filter),
                new BasicDBObject().append("$group", new BasicDBObject("_id", "$" + MetricField.X_AXIS.getName())
                        .append(MetricField.COUNT.getName(), new BasicDBObject("$sum", "$" + MetricField.COUNT.getName()))
                        .append(MetricField.MAX_TIME.getName(), new BasicDBObject("$max", "$" + MetricField.MAX_TIME.getName()))
                        .append(MetricField.MIN_TIME.getName(), new BasicDBObject("$min", "$" + MetricField.MIN_TIME.getName()))
                        .append(MetricField.TIME.getName(), new BasicDBObject("$sum", "$" + MetricField.TIME.getName()))
                        .append("array", new BasicDBObject("$push", "$" + MetricField.CODES.getName()))
                ), new BasicDBObject().append("$sort", new BasicDBObject(MetricField.ID.getName(), 1)));
        return getCollection(serviceName).aggregate(pipeline);
    }

    //db.getCollection('metrics20170103').aggregate([{$group:{_id:"$uri"} }])
    public List<String> listUri(String serviceName) {
        ArrayList<BasicDBObject> pipeline = Lists.newArrayList(new BasicDBObject("$group", new BasicDBObject("_id", "$uri")));
        AggregateIterable<Document> documents = getCollection(serviceName).aggregate(pipeline);
        MongoCursor<Document> iterator = documents.iterator();
        List<String> uriList = Lists.newArrayList();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            uriList.add(next.getString("_id"));
        }
        return uriList;
    }

    public List<String> listFields(String serviceName, String uri, String filed) {
        ArrayList<BasicDBObject> pipeline = Lists.newArrayList(new BasicDBObject().append("$match", new BasicDBObject("uri", uri)),
                new BasicDBObject().append("$group", new BasicDBObject("_id", "$" + filed)));
        AggregateIterable<Document> documents = getCollection(serviceName).aggregate(pipeline);
        MongoCursor<Document> iterator = documents.iterator();
        List<String> uriList = Lists.newArrayList();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            uriList.add(next.getString("_id"));
        }
        return uriList;
    }

    /**
     * db.getCollection('metrics20170103').aggregate(
     * [{$group:{_id:"$uri",
     * time:{$sum:"$time"},
     * count:{$sum:"$count"},
     * maxTime:{$max:"$maxTime"},
     * minTime:{$min:"$minTime"}  }}])
     */
    public JSONArray groupByUri(String serviceName) {
        ArrayList<BasicDBObject> pipeline = Lists.newArrayList(new BasicDBObject("$group",
                new BasicDBObject(MetricField.ID.getName(), "$uri")
                        .append(MetricField.TIME.getName(), new BasicBSONObject("$sum", "$time"))
                        .append(MetricField.COUNT.getName(), new BasicDBObject("$sum", "$count"))
                        .append(MetricField.MAX_TIME.getName(), new BasicDBObject("$max", "$maxTime"))
                        .append(MetricField.MIN_TIME.getName(), new BasicDBObject("$min", "$minTime")))
        );
        AggregateIterable<Document> aggregate = getCollection(serviceName).aggregate(pipeline);
        MongoCursor<Document> iterator = aggregate.iterator();
        JSONArray array = new JSONArray();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            array.add(next);
        }
        return array;
    }


}
