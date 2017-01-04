package com.zhizus.forest.metrics.controller;

import com.alibaba.fastjson.JSONArray;
import com.zhizus.forest.metrics.MetricChatService;
import com.zhizus.forest.metrics.MetricsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Dempe on 2016/12/29.
 */
@Controller
@RequestMapping("/metric")
public class MetricsController {

    private final static String serviceName = "forest-metrics";

    @Autowired
    private MetricsDao metricsDao;

    @Autowired
    private MetricChatService metricChatService;

    @RequestMapping("/index")
    public ModelAndView index(ModelAndView modelAndView) throws Exception {
        modelAndView.setViewName("forest/metrics");
        return modelAndView;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam String uri, ModelAndView modelAndView) throws Exception {
        modelAndView.setViewName("forest/metrics_details");
        modelAndView.addObject("ips", metricsDao.listFields(serviceName, uri, "ip"));
        modelAndView.addObject("version", metricsDao.listFields(serviceName, uri, "version"));
        modelAndView.addObject("rooms", metricsDao.listFields(serviceName, uri, "roomId"));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/listByUri")
    public String listByUri(@RequestParam String uri,
                            @RequestParam(required = false) String ip,
                            @RequestParam(required = false) String roomId,
                            @RequestParam(required = false) String version,
                            @RequestParam(required = false) String type) {
        return metricChatService.groupByXAxis(serviceName, uri, ip, roomId, version, type).toJSONString();
    }

    @ResponseBody
    @RequestMapping("/uriList")
    public String uriList() {
        return JSONArray.toJSONString(metricsDao.listUri(serviceName));
    }

    @ResponseBody
    @RequestMapping("/groupByUri")
    public String groupByUri() {
        return JSONArray.toJSONString(metricsDao.groupByUri(serviceName));
    }

}
