package com.zhizus.forest.metrics.service;

import com.zhizus.forest.metrics.bean.App;
import com.zhizus.forest.metrics.dao.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dempe on 2017/1/5.
 */
@Service
public class AppService {

    @Autowired
    private AppDao appDao;

    public void save(App app) {
        app.setCreateAt(System.currentTimeMillis());
        appDao.save(app);
    }

    public List<App> find() {
        return appDao.find();
    }

    public long deleteByServiceName(String serviceName) {
        return appDao.deleteByServiceName(serviceName).getDeletedCount();
    }

}
