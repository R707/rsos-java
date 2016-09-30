package com.abcs.finance.rsos.utils;

import com.abcs.finance.rsos.callback.IWatchCallback;
import com.abcs.finance.rsos.callback.IWatchDirCallback;
import com.abcs.finance.rsos.callback.IWatchFileCallback;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cavasblack on 16/9/30.
 */
public class ZkCli implements Watcher, AsyncCallback.ChildrenCallback, AsyncCallback.DataCallback {

    private String connectString;

    private ZooKeeper zk;

    private int timeout = 20000;

    private HashMap<String, IWatchCallback> callbacks;

    public ZkCli(String connectString) throws IOException {
        this.connectString = connectString;
        this.zk = new ZooKeeper(connectString, timeout, this);
        this.callbacks = new HashMap<String, IWatchCallback>();
    }

    public void watchDir(String path, IWatchDirCallback callback) throws KeeperException, InterruptedException {
        this.callbacks.put(path, callback);
        this.zk.getChildren(path, this, this, null);
    }

    public void watchFile(String path, IWatchFileCallback callback) {
        this.callbacks.put(path, callback);
        this.zk.getData(path, this, this, null);
    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
        if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
            if (this.callbacks.get(watchedEvent.getPath()) != null) {
                try {
                    this.watchDir(watchedEvent.getPath(), (IWatchDirCallback) this.callbacks.get(watchedEvent.getPath()));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
            if (this.callbacks.get(watchedEvent.getPath()) != null) {
                this.watchFile(watchedEvent.getPath(), (IWatchFileCallback) this.callbacks.get(watchedEvent.getPath()));
            }
        }
    }

    public void processResult(int i, String s, Object o, List<String> list) {
//        System.out.println(i + ":" + s + ":" + o + ":" + list);
        if (this.callbacks.get(s) != null) {
            ((IWatchDirCallback) this.callbacks.get(s)).change(list);
        }
    }

    public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
        if (this.callbacks.get(s) != null) {
            ((IWatchFileCallback) this.callbacks.get(s)).change(new String(bytes));
        }
    }
}
