package com.zhizus.forest.metrics.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.zhizus.forest.metrics.MetricsDao;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Dempe on 2016/12/29.
 */
@Controller
@RequestMapping("/metric")
public class MetricsController {


    @Autowired
    private MetricsDao metricsDao;

    @RequestMapping("/index")
    public ModelAndView index(ModelAndView modelAndView) throws Exception {
        modelAndView.setViewName("forest/metrics");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/list")
    public String list() {
        FindIterable<Document> documents = metricsDao.find();
        MongoCursor<Document> iterator = documents.iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();
        }
        return JSON.toJSONString(documents);
    }

    @ResponseBody
    @RequestMapping("/uriList")
    public String uriList() {
        return  JSONArray.toJSONString(metricsDao.listUri());
    }

    @ResponseBody
    @RequestMapping("/groupByUri")
    public String groupByUri() {
        return  JSONArray.toJSONString(metricsDao.groupByUri());
    }

}
