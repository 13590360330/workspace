package com.sparkstreaming;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

public class FileFilter  implements PathFilter {

    @Override
    public boolean accept(Path path) {
        String tmpStr = path.getName();
        //path.getName()返回路劲字符串,要找的字符串如果indexOf不是负数(-1)就表示存在,就返回flase
        if(tmpStr.indexOf(".tmp") >= 0)
        {
            return false;
        }
        else if(tmpStr.indexOf("_COPYING_") >= 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
