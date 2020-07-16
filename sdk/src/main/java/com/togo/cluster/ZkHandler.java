package com.togo.cluster;

import com.togo.base.exception.ZKException;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author taiyn
 * @Description notify others members event with zookeeper in a cluster
 * @Date 6:05 下午 2020/5/26
 **/
public class ZkHandler implements Watcher {

    private Logger log = LoggerFactory.getLogger(ZkHandler.class);

    private ZooKeeper zooKeeper;
    private SQLUpdater sqlUpdater;

    public ZkHandler(String host, int timeout) {
        try {
            this.zooKeeper = new ZooKeeper(host, timeout, this);
        } catch (IOException e) {
            throw new ZKException("create zk client error", e);
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
            log.info("data changed, path: [{}]", watchedEvent.getPath());
            String queryId = getData(watchedEvent.getPath());
            log.info("data changed, data: [{}]", queryId);
            sqlUpdater.updateSQLById(queryId);
        }
    }

    /**
     * create node if not exist, and set watcher to it
     */
    public void init(String node, SQLUpdater sqlUpdater) {
        createNode(node);
        watch(node);
        this.sqlUpdater = sqlUpdater;
    }

    public void createNode(String node) {

        if (exists(node, false))
            return;
        int i = node.lastIndexOf('/');
        if (i > 0) {
            // 递归创建上一级路径
            createNode(node.substring(0, i));
        }

        try {
            zooKeeper.create(node, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * set watcher by exist function
     */
    public void watch(String node) {
        // throw exception when the node does not exist, which will be set watcher
        if (!exists(node, true)) {

            log.error("the watched node does not exist, node: [{}]", node);
            throw new ZKException("the watched node does not exist");
        }
    }

    public void update(String node, String data) {

        int version = version(node);
        setData(node, data, version);
    }

    public int version(String node) {

        try {
            return zooKeeper.exists(node, true).getVersion();
        } catch (KeeperException | InterruptedException e) {
            log.error("zk get version error, node: [{}]", node);
            throw new ZKException(e);
        }
    }

    private boolean exists(String node, boolean watch) {

        try {
            Stat stat = zooKeeper.exists(node, watch);
            return stat != null;
        } catch (KeeperException | InterruptedException e) {
            log.error("zk exists error, node: [{}]", node);
            throw new ZKException(e);
        }
    }

    private String getData(String node) {
        try {
            byte[] data = zooKeeper.getData(node, this, null);
            return new String(data, StandardCharsets.UTF_8);
        } catch (KeeperException | InterruptedException e) {
            log.error("zk get data error, node: [{}]", node);
            throw new ZKException(e);
        }
    }

    private void setData(String node, String data, int version) {

        try {
            zooKeeper.setData(node, data.getBytes(), version);
        } catch (KeeperException | InterruptedException e) {
            log.error("zk set data error, node: [{}], data: [{}], version: [{}]", node, data, version);
            throw new ZKException(e);
        }
    }
}
