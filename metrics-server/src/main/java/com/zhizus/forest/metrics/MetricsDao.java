package com.zhizus.forest.metrics;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.zhizus.forest.metrics.gen.MetaReq;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        ID("_id"), CONFIG_ID("configId"), URI("uri"), CODES("codes"), COUNT("count"), TIME("time"), MAX_TIME("maxTime"),
        MIN_TIME("minTime"), SUCCESS_COUNT("successCount"), X_AXIS("xAxis");
        private String name;

        MetricField(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    protected Document getDocument(MetaReq meta) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return new Document(MetricField.URI.getName(), meta.getUri())
                .append(MetricField.CONFIG_ID.getName(), meta.getConfigId())
                .append(MetricField.COUNT.getName(), meta.getCount())
                .append(MetricField.MAX_TIME.getName(), meta.getMaxTime())
                .append(MetricField.MIN_TIME.getName(), meta.getMinTime())
                .append(MetricField.CODES.getName(), meta.getCodes())
                .append(MetricField.SUCCESS_COUNT.getName(), meta.getCodes().get(0))
                .append(MetricField.X_AXIS.getName(), format.format(new Date()))
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

    public FindIterable<Document> find() {
        FindIterable<Document> documents = getCollection().find();
        return documents;
    }

    public JSONObject findByUri(String uri) {
        FindIterable<Document> documents = getCollection().find(new BasicDBObject(MetricField.URI.getName(), uri));
        JSONObject result = new JSONObject();
        MongoCursor<Document> iterator = documents.iterator();
        JSONArray count = new JSONArray();
        JSONArray time = new JSONArray();
        JSONArray minTimeArr = new JSONArray();
        JSONArray maxTimeArr = new JSONArray();
        JSONArray avgTimeArr = new JSONArray();

        Map<Integer, Integer> map = Maps.newHashMap();
        map.put(0, 4);
        map.put(10,8);
        map.put(20, 7);
        map.put(30, 5);
        map.put(50, 0);
        map.put(100, 10);
        map.put(200, 3);
        map.put(500, 0);
        map.put(800, 0);
        map.put(1000, 0);
        map.put(2000, 0);
        map.put(5000, 0);
        while (iterator.hasNext()) {
            Document document = iterator.next();
            JSONArray countArray = new JSONArray();


            countArray.add(document.getString(MetricField.X_AXIS.getName()));
            countArray.add(document.getInteger(MetricField.COUNT.getName()));
            count.add(countArray);

            // maxTime
            JSONArray maxTimeArray = new JSONArray();
            maxTimeArray.add(document.getString(MetricField.X_AXIS.getName()));
            maxTimeArray.add(document.getInteger(MetricField.MAX_TIME.getName()));
            maxTimeArr.add(maxTimeArray);

            // minTime
            JSONArray minTimeArray = new JSONArray();
            minTimeArray.add(document.getString(MetricField.X_AXIS.getName()));
            minTimeArray.add(document.getInteger(MetricField.MIN_TIME.getName()));
            minTimeArr.add(minTimeArray);

            // avgTime
            JSONArray avgTimeArray = new JSONArray();
            avgTimeArray.add(document.getString(MetricField.X_AXIS.getName()));

            Double timeDouble = Double.valueOf(document.getLong(MetricField.TIME.getName()));
            Double countDouble = Double.valueOf(document.getInteger(MetricField.COUNT.getName()));
            Double avgTime = timeDouble == null || countDouble == null || countDouble == 0 ? 0D : (timeDouble / countDouble);
            avgTimeArray.add(avgTime);
            avgTimeArr.add(avgTimeArray);


        }

        JSONObject maxTimeJSON = new JSONObject();
        maxTimeJSON.put("name", "maxTime");
        maxTimeJSON.put("data", maxTimeArr);
        time.add(maxTimeJSON);

        JSONObject minTimeJSON = new JSONObject();
        minTimeJSON.put("name", "minTime");
        minTimeJSON.put("data", minTimeArr);
        time.add(minTimeJSON);

        JSONObject avgTimeJSON = new JSONObject();
        avgTimeJSON.put("name", "avgTime");
        avgTimeJSON.put("data", avgTimeArr);
        time.add(avgTimeJSON);

        JSONArray timeDisSeries = new JSONArray();
        JSONObject timeDisJSON = new JSONObject();
        JSONArray timeDis = new JSONArray();
        Iterator<Map.Entry<Integer, Integer>> mapIterator = map.entrySet().iterator();
        while (mapIterator.hasNext()){
            Map.Entry<Integer, Integer> next = mapIterator.next();
            JSONObject json = new JSONObject();
            json.put("name", next.getKey());
            json.put("y", next.getValue());
            timeDis.add(json);
        }
        timeDisJSON.put("name", "time");
        timeDisJSON.put("data", timeDis);
        timeDisSeries.add(timeDisJSON);


        result.put("count", count);
        result.put("time", time);
        result.put("timeDistribution", timeDisSeries);

        return result;
    }

    //db.getCollection('metrics20170103').aggregate([{$group:{_id:"$uri"} }])
    public List<String> listUri() {
        ArrayList<BasicDBObject> pipeline = Lists.newArrayList(new BasicDBObject("$group", new BasicDBObject("_id", "$uri")));
        AggregateIterable<Document> documents = getCollection().aggregate(pipeline);
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
    public JSONArray groupByUri() {
        ArrayList<BasicDBObject> pipeline = Lists.newArrayList(new BasicDBObject("$group",
                new BasicDBObject(MetricField.ID.getName(), "$uri")
                        .append(MetricField.TIME.getName(), new BasicBSONObject("$sum", "$time"))
                        .append(MetricField.COUNT.getName(), new BasicDBObject("$sum", "$count"))
                        .append(MetricField.MAX_TIME.getName(), new BasicDBObject("$max", "$maxTime"))
                        .append(MetricField.MIN_TIME.getName(), new BasicDBObject("$min", "$minTime")))
        );
        AggregateIterable<Document> aggregate = getCollection().aggregate(pipeline);
        MongoCursor<Document> iterator = aggregate.iterator();
        JSONArray array = new JSONArray();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            array.add(next);
        }
        return array;
    }


}
