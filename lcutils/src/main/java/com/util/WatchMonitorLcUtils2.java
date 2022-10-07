package com.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.lang.Console;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class WatchMonitorLcUtils2 {

    public static void main(String[] args) {

        File file = FileUtil.file( "C:\\Users\\Administrator\\Desktop\\11" );
        //这里只监听文件或目录的修改事件

        WatchMonitor.createAll(file, new SimpleWatcher(){
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Console.log("EVENT modify");
            }
        }).start();
    }
}
