package com.demo;

import com.togo.api.Caribou;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 9:34 下午 2020/7/20
 **/
public class DemoService {

    public void query() {

    }

    private void init() {
        Caribou.init();
    }

    public static void main(String[] args) {
        DemoService demoService = new DemoService();
        demoService.init();
    }
}
