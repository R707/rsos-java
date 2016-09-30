package com.abcs.finance.rsos.callback;

/**
 * Created by cavasblack on 16/9/30.
 */
public interface IWatchFileCallback extends IWatchCallback{
    public void change(String data);
}
