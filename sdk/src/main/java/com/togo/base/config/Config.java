package com.togo.base.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 10:00 下午 2020/7/20
 **/
public class Config {

    private Config() {
    }

    private String name;

    public String getName() {
        return name;
    }

    public static Config getInstance(String path) {

        return ConfigFactory.getConfig(path);
    }

    private static class ConfigFactory {

        public static Config config;
        private static Properties properties;
        public static Config getConfig(String path) {
            if (config == null) {
                config = new Config();
                try {
                    properties = new Properties();
                    properties.load(Config.class.getClassLoader().getResourceAsStream(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                config.name = properties.getProperty("name");
            }

            return config;
        }
    }

}
