package com.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Arrays;

/**
 * author:liuch
 * desc: 文件和目录监控   待完善
 */
public class WatchMonitorLcUtils {

    public static void main(String[] args) {
        File file = FileUtil.file( "C:\\Users\\Administrator\\Desktop\\11" );
        //这里只监听文件或目录的修改事件
        WatchMonitor watchMonitor = WatchMonitor.create( file,20, WatchMonitor.EVENTS_ALL );
        watchMonitor.setWatcher( new Watcher() {

            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                DateTime timenow = DateTime.now();
                Object obj = event.context();
                String format = String.format( "%s\\%s", currentPath.toString(), obj );
                if (FileUtil.isDirectory( new File( format ) ) && FileUtil.isNotEmpty( new File( format ) )) {
                    File[] ls = FileUtil.ls( format );
                    Console.log( String.format( "{%s}创建目录：目录{%s\\%s}", timenow, currentPath, obj ) );
                    Arrays.stream( ls ).forEach( filename -> Console.log( String.format( "{%s}复制：目录{%s}，文件{%s}", timenow, currentPath, filename ) ) );
                } else if(FileUtil.isDirectory( new File( format ) ) && FileUtil.isEmpty( new File( format ))){
                    Console.log( String.format( "{%s}创建目录：目录{%s\\%s}", timenow, currentPath, obj ) );
                } else {
                    Console.log( String.format( "{%s}创建文件：目录{%s}，文件{%s}", timenow, currentPath, obj ) );
                }
            }

            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                DateTime timenow = DateTime.now();
                Object obj = event.context();
                String format = String.format( "%s\\%s", currentPath.toString(), obj );
                //对文件夹的修改，绝大部分是修改目录名，这种情况的流程是先删除原先的目录，创建新目录，因此这里不用考虑文件夹的修改
                if (FileUtil.isDirectory( new File( format ) ) && FileUtil.isNotEmpty( new File( format ) )) {
                } else if(FileUtil.isDirectory( new File( format ) ) && FileUtil.isEmpty( new File( format ))){
                } else {
                    Console.log( String.format( "{%s}修改目录：目录{%s}，文件{%s}", timenow, currentPath, obj ) );
                }
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                DateTime timenow = DateTime.now();
                Object obj = event.context();
                Console.log( String.format( "{%s}删除：目录{%s\\%s}", timenow, currentPath, obj ) );
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                DateTime timenow = DateTime.now();
                Object obj = event.context();
                Console.log( String.format( "{%s}创建：目录{%s}，文件{%s}", timenow, currentPath, obj ) );
            }
        } );

        //设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
        watchMonitor.setMaxDepth( 30 );
        //启动监听
        watchMonitor.start();
    }
}
