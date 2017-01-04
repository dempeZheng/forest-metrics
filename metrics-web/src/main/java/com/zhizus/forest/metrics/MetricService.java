package com.zhizus.forest.metrics;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Dempe on 2017/1/4.
 */
@Service
public class MetricService {


    @Autowired
    private MetricsDao metricsDao;


    private Map<Integer, Integer> initTimeDistributionMap() {
        Map<Integer, Integer> map = Maps.newTreeMap();
        for (Integer region : initRegionList()) {
            map.put(region, 0);
        }
        return map;
    }

    public List<Integer> initRegionList() {
        return Lists.newArrayList(10, 20, 30, 50, 100, 200, 300, 500, 800, 1000, 2000, 5000);
    }

    public void incRegionMap(Map<Integer, Integer> regionMap, int value) {
        Integer regionKey = getRegionKey(value);
        Integer count = regionMap.get(regionKey);
        if (count == null) {
            count = 0;
            regionMap.put(regionKey, count);
        }
        regionMap.put(regionKey, ++count);
    }

    private Integer getRegionKey(int value) {
        List<Integer> regionList = initRegionList();
        for (Integer region : regionList) {
            if (value < region) {
                return region;
            }
        }
        return regionList.get(regionList.size() - 1);
    }

    private JSONArray wrapArray(Object xAxis, Object yAxis) {
        JSONArray array = new JSONArray();
        array.add(xAxis);
        array.add(yAxis);
        return array;
    }

    public JSONObject wrapChatData(FindIterable<Document> documents) {
        JSONObject result = new JSONObject();

        JSONArray countArr = new JSONArray();
        JSONArray errCountArr = new JSONArray();
        JSONArray minTimeArr = new JSONArray();
        JSONArray maxTimeArr = new JSONArray();
        JSONArray avgTimeArr = new JSONArray();
        Map<Integer, Integer> regionMap = initTimeDistributionMap();

        MongoCursor<Document> iterator = documents.iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();

            String xAxis = document.getString(MetricsDao.MetricField.X_AXIS.getName());
            Integer maxTime = document.getInteger(MetricsDao.MetricField.MAX_TIME.getName());
            Integer minTime = document.getInteger(MetricsDao.MetricField.MIN_TIME.getName());
            Integer count = document.getInteger(MetricsDao.MetricField.COUNT.getName());

            Double timeDouble = Double.valueOf(document.getLong(MetricsDao.MetricField.TIME.getName()));
            Double countDouble = Double.valueOf(count);
            Double avgTime = timeDouble == null || countDouble == null || countDouble == 0 ? 0D : (timeDouble / countDouble);

            List<Integer> codes = (List<Integer>) (document.get(MetricsDao.MetricField.CODES.getName()));
            int errCount = 0;
            if (codes != null && count != null) {
                errCount = count - codes.get(0) < 0 ? 0 : count - codes.get(0);
            }

            countArr.add(wrapArray(xAxis, count));
            errCountArr.add(wrapArray(xAxis, errCount));

            // maxTime
            maxTimeArr.add(wrapArray(xAxis, maxTime));
            // minTime
            minTimeArr.add(wrapArray(xAxis, minTime));
            // avgTime
            avgTimeArr.add(wrapArray(xAxis, avgTime));

            incRegionMap(regionMap, avgTime.intValue());
        }


        // 请求数
        JSONArray countSeries = new JSONArray();
        addSeries(countSeries, "count", countArr);
        addSeries(countSeries, "errCount", errCountArr);

        // 时延
        JSONArray timeSeries = new JSONArray();
        addSeries(timeSeries, "maxTime", maxTimeArr);
        addSeries(timeSeries, "minTime", minTimeArr);
        addSeries(timeSeries, "avgTime", avgTimeArr);


        // 时延分布
        JSONArray timeDis = mapToColumnData(regionMap);
        JSONArray timeDisSeries = new JSONArray();
        addSeries(timeDisSeries, "time", timeDis);


        result.put("count", countSeries);
        result.put("time", timeSeries);
        result.put("timeDistribution", timeDisSeries);
        return result;
    }

    public JSONObject findByUri(String uri) {
        return wrapChatData(metricsDao.findByUri(uri));
    }

    private void addSeries(JSONArray series, String name, JSONArray data) {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("data", data);
        series.add(json);
    }

    public JSONArray mapToColumnData(Map map) {
        JSONArray columnData = new JSONArray();
        Iterator<Map.Entry<Object, Object>> mapIterator = map.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<Object, Object> next = mapIterator.next();
            JSONObject json = new JSONObject();
            json.put("name", next.getKey());
            json.put("y", next.getValue());
            columnData.add(json);
        }
        return columnData;
    }


}
