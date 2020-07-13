package com.togo.launcher;

import java.util.Map;

/**
 * @Author taiyn
 * @Description process unit
 * @Date 5:09 下午 2020/6/30
 **/
public interface ProcessUnit {

    /**
     * @Author taiyn
     * @Description execute
     *
     * @Date 5:14 下午 2020/6/30
     * @Param []
     * @return com.ziroom.trade.rent.inquire.common.caribou.launcher.ProcessUnitResult
     **/
    ProcessUnitResult execute(Map<String, Object> parameter);

    /**
     * @Author taiyn
     * @Description the id of process unit
     *
     * @Date 5:15 下午 2020/6/30
     * @Param []
     * @return java.lang.String
     **/
    String id();

    /**
     * @Author taiyn
     * @Description type
     *
     * @Date 5:25 下午 2020/6/30
     * @Param []
     * @return int
     **/
    int type();
}
