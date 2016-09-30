package com.abcs.finance.rsos;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Created by cavasblack on 16/9/30.
 */
public class Master implements Watcher {

    private ZooKeeper zk;

    private String rootpath;

    private String name;

    public Master(ZooKeeper zk, String name, String rootpath) {
        this.zk = zk;
        this.rootpath = rootpath;
        this.name = name;
        this.init();
    }

    private void init() {
        try {
            this.zk.exists(rootpath, false);
            this.zk.getChildren(this.rootpath, this);
//            this.zk.getChildren(this.rootpath, true, this);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                this.zk.create(this.rootpath, this.name.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            } catch (Exception e1) {
                e1.printStackTrace();
                System.exit(0);
            }
            this.init();
        }


    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.getPath());
    }
}
