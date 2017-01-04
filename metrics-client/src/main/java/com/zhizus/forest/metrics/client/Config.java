package com.zhizus.forest.metrics.client;

import com.google.common.base.Strings;
import com.zhizus.forest.metrics.NetUtils;
import com.zhizus.forest.metrics.gen.MetaConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.Properties;

/**
 * Created by Dempe on 2016/12/30 0030.
 */
public class Config extends MetaConfig {

    private String fileName;

    private Properties properties;

    public Config(String fileName) throws IOException {
        this.fileName = fileName;
        load();
    }

    public Config() throws IOException {
        this("metrics.properties");
    }

    public void load() throws IOException {
        String path = URLClassLoader.getSystemResource(fileName).getPath();
        InputStream in = new FileInputStream(path);
        this.properties = new Properties();
        properties.load(in);
        String ip = getString("ip", NetUtils.getLocalAddress().getHostAddress());
        String version = getString("version", "1.0");
        String type = getString("type");
        String roomId = getString("roomId", "1");
        String serviceName = getString("serviceName", "forest-metrics");
        setVersion(version);
        setIp(ip);
        setRoomId(roomId);
        setType(type);
        setServiceName(serviceName);
    }

    private String getString(String key) {
        Object o = properties.get(key);
        return o == null ? "" : o.toString();
    }

    private String getString(String key, String defVal) {
        String value = getString(key);
        return Strings.isNullOrEmpty(value) ? defVal : value;

    }

}
