package com.abcs.finance.rsos.callback;

import java.util.List;

/**
 * Created by cavasblack on 16/9/30.
 */
public interface IWatchDirCallback extends IWatchCallback{
    public void change(List<String> list);
}
