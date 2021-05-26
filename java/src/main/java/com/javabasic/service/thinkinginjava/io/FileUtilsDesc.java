package com.javabasic.service.thinkinginjava.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

/**
 * TODO [FileUtils]
 * <p>文件操作工具类<p>  org.apache.commons.io.FileUtils的介绍和额外添加的方法,对于文件或者目录的操作首选FileUtils工具包
 *
 * 补充:File类方法  isFile(),isDirectory(),exists()
 *
 * @author li_hao
 * @version 1.0
 * @date 2017年1月18日
 */
@SuppressWarnings({"resource", "unused"})
public class FileUtilsDesc extends FileUtils {
    /**
     * getUserDirectoryPath()                                                                                             获取用户的主目录路径
     * getUserDirectory()                                                                                                 获取代表用户主目录的文件
     * openInputStream(File file)                                                                                         根据指定的文件获取一个新的文件输入流
     * openOutputStream (File file)                                                                                       根据指定的文件获取一个新的文件输出流
     * byteCountToDisplaySize(long size)                                                                                  字节转换成直观带单位的值（包括单位GB，MB，KB或字节）
     * touch(File file)                                                                                                   创建一个空文件，若文件已经存在则只更改文件的最近修改时间
     * convertFileCollectionToFileArray(Collection files)                                                                 把相应的文件集合转换成文件数组
     * innerListFiles(Collection files, File directory,IOFileFilterfilter)                                                根据一个过滤规则获取一个目录下的文件
     * listFiles( File directory, IOFileFilterfileFilter, IOFileFilter dirFilter)                                         根据一个IOFileFilter过滤规则获取一个目录下的文件集合
     * iterateFiles( File directory, IOFileFilterfileFilter, IOFileFilter dirFilter)                                      根据一个IOFileFilter过滤规则获取一个目录下的文件集合的Iterator迭代器
     * toSuffixes(String[] extensions)                                                                                    把指定的字符串数组变成后缀名格式字符串数组
     * listFiles(File directory, String[]extensions, boolean recursive)                                                   查找一个目录下面符合对应扩展名的文件的集合
     * iterateFiles( File directory, String[]extensions, boolean recursive)                                               查找一个目录下面符合对应扩展名的文件的集合的迭代器Iterator
     * contentEquals(File file1, File file2)                                                                              判断两个文件是否相等,只能比较文件
     * copyFileToDirectory(File srcFile, File destDir)                                                                    拷贝一个文件到指定的目录文件
     * copyFileToDirectory(File srcFile, File destDir, booleanpreserveFileDate)                                           拷贝一个文件到指定的目录文件并且设置是否更新文件的最近修改时间
     * moveFile(FilesrcFile, File destFile)                                                                               复制文件到对应的文件中去
     * copyFile(File srcFile, File destFile)                                                                              拷贝文件到新的文件中并且保存最近修改时间
     * copyFile(File srcFile, File destFile,boolean preserveFileDate)                                                     拷贝文件到新的文件中并且设置是否保存最近修改时间
     * doCopyFile(File srcFile, File destFile, boolean preserveFileDate)                                                  拷贝文件到新的文件中并且设置是否保存最近修改时间
     * moveToDirectory(File src, File destDir, boolean createDestDir)                                                     移动文件或者目录到新的路径下，并且设置在目标路径不存在的情况下是否创建
     * moveDirectory(File srcDir, File destDir)                                                                           移动目录到新的目录并且删除老的目录
     * moveDirectoryToDirectory(File src, File destDir, booleancreateDestDir)                                             把一个目录移动到另一个目录中去
     * copyDirectoryToDirectory(File srcDir, File destDir)                                                                将一个目录拷贝到另一目录中，并且保存最近更新时间
     * copyDirectory(File srcDir, File destDir)                                                                           拷贝整个目录到新的位置，并且保存最近修改时间
     * copyDirectory(File srcDir, File destDir, boolean preserveFileDate)                                                 拷贝整个目录到新的位置，并且设置是否保存最近修改时间
     * copyDirectory(File srcDir, File destDir, FileFilter filter)                                                        拷贝过滤后的目录到指定的位置，并且保存最近修改时间
     * copyDirectory(File srcDir, File destDir,FileFilter filter, booleanpreserveFileDate)                                拷贝过滤后的目录到指定的位置，并且设置是否保存最近修改时间
     * copyInputStreamToFile(InputStream source, File destination)                                                        拷贝一个字节流到一个文件中，如果这个文件不存在则新创建一个，存在的话将被重写进内容
     * deleteDirectory(Filedirectory)                                                                                     递归的删除一个目录
     * deleteQuietly(File file)                                                                                           安静模式删除目录，操作过程中会抛出异常
     * cleanDirectory(Filedirectory)                                                                                      清除一个目录而不删除它
     * forceDelete(File file)                                                                                             删除一个文件，如果是目录则递归删除
     * readFileToString(File file, String encoding)                                                                       把一个文件的内容读取到一个对应编码的字符串中去
     * readFileToString(File file)                                                                                        读取文件的内容到虚拟机的默认编码字符串
     * readFileToByteArray(File file)                                                                                     把一个文件转换成字节数组返回
     * readLines(File file, String encoding)                                                                              把文件中的内容逐行的拷贝到一个对应编码的list中去
     * readLines(File file)                                                                                               把文件中的内容逐行的拷贝到一个虚拟机默认编码的list中去
     * lineIterator(File file, String encoding)                                                                           根据对应编码返回对应文件内容的行迭代器
     * lineIterator(File file)                                                                                            根据虚拟机默认编码返回对应文件内容的行迭代器
     * writeStringToFile(File file, String data, String encoding)                                                         根据对应编码把字符串写进对应的文件中
     * writeStringToFile(File file, String data)                                                                          根据虚拟机默认编码把字符串写进对应的文件中
     * write(File file, CharSequence data)                                                                                根据虚拟机默认的编码把CharSequence写入到文件中
     * write(File file, CharSequence data, String encoding)                                                               根据对应的编码把CharSequence写入到文件中
     * writeByteArrayToFile(File file, byte[] data)                                                                       把一个字节数组写入到指定的文件中
     * writeLines(File file,String encoding, Collection<?> lines)                                                         把集合中的内容根据对应编码逐项插入到文件中
     * writeLines(File file, Collection<?> lines)                                                                         把集合中的内容根据虚拟机默认编码逐项插入到文件中
     * writeLines(File file,String encoding, Collection<?> lines, String lineEnding)                                      把集合中的内容根据对应字符编码和行编码逐项插入到文件中
     * writeLines(File file, Collection<?> lines, String lineEnding)                                                      把集合中的内容根据对应行编码逐项插入到文件中
     * forceDeleteOnExit(File file)                                                                                       当虚拟机退出关闭时删除文件
     * deleteDirectoryOnExit(File directory)                                                                              当虚拟机退出关闭时递归删除一个目录
     * cleanDirectoryOnExit(Filedirectory)                                                                                在虚拟机退出或者关闭时清除一个目录而不删除它
     * forceMkdir(File directory)                                                                                         创建一个目录除了不存在的父目录其他所必须的都可以创建
     * sizeOf(Filefile)                                                                                                   获取文件或者目录的大小
     * sizeOfDirectory(Filedirectory)                                                                                     获取目录的大小
     * getTempDirectoryPath()                                                                                             获取系统的临时目录路径
     * getTempDirectory ()                                                                                                获取代表系统临时目录的文件
     * toFile(URL url)                                                                                                    根据一个Url来创建一个文件
     * decodeUrl(String url)                                                                                              对一个Url字符串进行将指定的URL按照RFC 3986进行转换
     * toFiles(URL[] urls)                                                                                                将一个URL数组转化成一个文件数组
     * toURLs(File[] files)                                                                                               将一个文件数组转化成一个URL数组
     * doCopyDirectory(FilesrcDir, File destDir, FileFilter filter, boolean preserveFileDate,List exclusionList)          内部拷贝目录的方法
     * copyURLToFile(URL source, File destination)                                                                        根据一个Url拷贝字节到一个文件中
     * copyURLToFile(URL source, File destination,int connectionTimeout, int readTimeout)                                 根据一个Url拷贝字节到一个文件中，并且可以设置连接的超时时间和读取的超时时间
     * waitFor(File file, int seconds)                                                                                    等待NFS来传播一个文件的创建，实施超时
     * isFileNewer(File file, File reference)                                                                             测试指定文件的最后修改日期是否比reference的文件新
     * isFileOlder(File file, File reference)                                                                             检测指定文件的最后修改日期是否比reference文件的晚
     * isFileNewer(File file, Date date)                                                                                  检测指定文件的最后修改时间是否在指定日期之前
     * isFileOlder(File file, Date date)                                                                                  检测指定文件的最后修改时间是否在指定日期之后
     * isFileNewer(File file, long timeMillis)                                                                            检测指定文件的最后修改时间（毫秒）是否在指定日期之前
     * isFileOlder(File file, long timeMillis)                                                                            检测指定文件的最后修改时间（毫秒）是否在指定日期之后
     * checksumCRC32(File file)                                                                                           计算使用CRC32校验程序文件的校验和
     * checksum(File file, Checksum checksum)                                                                             计算一个文件使用指定的校验对象的校验
     * isSymlink(File file)                                                                                               确定指定的文件是否是一个符号链接，而不是实际的文件
     */

/*
    List<File> fileList = (List<File>)FileUtils.listFiles(dir,null,false);//列出该目录下的所有文件，不递归
        fileList.stream().forEach(file -> System.out.println(file.getAbsolutePath()));
*/

    /**
     * 获取windows/linux的项目根目录
     *
     * @return
     */
    public static String getConTextPath() {
        String fileUrl = Thread.currentThread().getContextClassLoader().getResource( "" ).getPath();
        if ("usr".equals( fileUrl.substring( 1, 4 ) )) {
            fileUrl = (fileUrl.substring( 0, fileUrl.length() - 16 ));//linux
        } else {
            fileUrl = (fileUrl.substring( 1, fileUrl.length() - 16 ));//windows
        }
        return fileUrl;
    }


    /**
     * 读取若干文件中所有数据
     *
     * @param listFilePath
     * @return
     */
    public static List<String> readFileContent_list(List<String> listFilePath) {
        List<String> listContent = new ArrayList<>();
        for (String filePath : listFilePath) {
            File file = new File( filePath );
            BufferedReader reader = null;
            try {
                reader = new BufferedReader( new FileReader( file ) );
                String tempString = null;
                int line = 1;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    listContent.add( tempString );
                    line++;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }
        return listContent;
    }

    /**
     * 判断list中元素是否完全相同（完全相同返回true,否则返回false）
     *
     * @param list
     * @return
     */
    private static boolean hasSame(List<? extends Object> list) {
        if (null == list)
            return false;
        return 1 == new HashSet<Object>( list ).size();
    }

    /**
     * 判断list中是否有重复元素（无重复返回true,否则返回false）
     *
     * @param list
     * @return
     */
    private static boolean hasSame2(List<? extends Object> list) {
        if (null == list)
            return false;
        return list.size() == new HashSet<Object>( list ).size();
    }

    /**
     * 增加/减少天数
     *
     * @param date
     * @param num
     * @return
     */
    public static Date DateAddOrSub(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime( date );
        startDT.add( Calendar.DAY_OF_MONTH, num );
        return startDT.getTime();
    }

    /* 得到文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        int point = fileName.lastIndexOf( '.' );
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring( point + 1, length );
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File( "E:\\日常文件\\临时目录" );
        File file1 = new File( "E:\\日常文件\\临时目录\\1.txt" );
        File file2 = new File( "../BigData/src/resources/logs/2.txt" );
        File file3 = new File( "E:\\日常文件\\临时目录\\3.txt" );

        /**
         * TODO 重定向
         */
        //重定向System.out 到file2
        PrintStream printStream = new PrintStream( new BufferedOutputStream( new FileOutputStream( file2 ) ), true );
        System.setOut( printStream );
        System.setErr( printStream );

        String[] str = {"txt", "dat", "properties"};
        Collection<File> files = FileUtils.listFiles( file, str, true );  //true  递归,  false 不递归
        System.out.println( "files:" + files );
        FileUtils.writeStringToFile( file1, "aaaa", "UTF-8" );
        BufferedInputStream bufferedInputStream = new BufferedInputStream( new FileInputStream( file2 ) );
//        FileInputStream InputStream =  new FileInputStream( file2 ) ;
        FileUtils.copyInputStreamToFile( bufferedInputStream, file3 );

//        new DataInputStream( );

        try (FileReader fileReader = new FileReader( file1 );
             FileInputStream fileInputStream = new FileInputStream( "E:\\日常文件\\临时目录" )) {
        } catch (FileNotFoundException e) {
            //推荐PrintWriter,PrintStream有缺陷 (java编程思想)P536页倒数第2段
            e.printStackTrace( new PrintWriter( System.out, true ).append( "测试 PrintStream \n" ) );
//            e.printStackTrace( new PrintWriter( new PrintStream( file2 ),true).append( "测试 PrintStream \n") );
//            e.printStackTrace( new PrintWriter( new FileWriter( file2 ), true ).append( "测试 PrintStream \n" ) );
//            e.printStackTrace( new PrintStream( file1,"utf-8" ).append( "测试 PrintStream \n") );
//            e.printStackTrace( new PrintStream( System.err ).printf( "aaa" ));
            e.printStackTrace( new PrintStream( System.err ).append( "测试 PrintStream \n", 0, 4 ) );
        } finally {
            printStream.close();
        }

    }
}

