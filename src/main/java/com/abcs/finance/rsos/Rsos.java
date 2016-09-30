package com.abcs.finance.rsos;


import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by cavasblack on 16/9/30.
 */
public class Rsos implements Watcher {

    private ZooKeeper zk;

    private String name;

    private String rootpath;

    private String connectString;

    private int timeout = 20000;

    public Rsos(String name, String rootpath, String connectString) {
        this.name = name;
        this.rootpath = rootpath;
        this.connectString = connectString;
        try {
            this.zk = new ZooKeeper(this.connectString, timeout, this);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void process(WatchedEvent watchedEvent) {

    }

    public Master createMaster() {
        return new Master(zk, name, rootpath);
    }
}
