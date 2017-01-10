package com.zhizus.forest.metrics.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import com.zhizus.forest.metrics.service.MetricChartService;
import com.zhizus.forest.metrics.bean.App;
import com.zhizus.forest.metrics.dao.MetricChartDao;
import com.zhizus.forest.metrics.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private MetricChartDao metricsDao;

    @Autowired
    private MetricChartService metricChartService;

    @Autowired
    private AppService appService;

    @RequestMapping("/index.do")
    public ModelAndView index(@RequestParam(required = false) String serviceName, ModelAndView modelAndView) throws Exception {
        modelAndView.setViewName("forest/metrics");
        List<App> appList = appService.find();
        modelAndView.addObject("appList", appList);
        String name = Strings.isNullOrEmpty(serviceName) && appList != null && appList.size() > 0
                ? appList.get(0).getServiceName() : serviceName;
        modelAndView.addObject("serviceName", name);
        return modelAndView;
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(@RequestParam String serviceName, @RequestParam String uri, ModelAndView modelAndView) throws Exception {
        modelAndView.setViewName("forest/metrics_details");
        modelAndView.addObject("ips", metricsDao.listFields(serviceName, uri, "ip"));
        modelAndView.addObject("version", metricsDao.listFields(serviceName, uri, "version"));
        modelAndView.addObject("rooms", metricsDao.listFields(serviceName, uri, "roomId"));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/listByUri.do")
    public String listByUri(@RequestParam String serviceName,
                            @RequestParam String uri,
                            @RequestParam(required = false) String ip,
                            @RequestParam(required = false) String roomId,
                            @RequestParam(required = false) String version,
                            @RequestParam(required = false) String type,
                            @RequestParam(required = false) String time) {
        return metricChartService.groupByXAxis(serviceName, uri, ip, roomId, version, type, time).toJSONString();
    }



    @ResponseBody
    @RequestMapping("/groupByUri.do")
    public String groupByUri(@RequestParam String serviceName) {
        return JSONArray.toJSONString(metricChartService.groupByUri(serviceName));
    }


    @RequestMapping("/readme.do")
    public String readme() {
        return "forest/readme";
    }
}
