package com.javabasic.service.thinkinginjava.array.CreateData;

import com.javabasic.dao.Generator;
import com.javabasic.service.thinkinginjava.array.RandomGenerator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO 随机数补充
 */
public class Users {

    private RandomGenerator.Integer user_id = new RandomGenerator.Integer();
    private Name name = new Name();

    /**
     * 性别
     */
    public static class
    sex implements Generator<String> {
        private Random random = new Random();

        @Override
        public String next() {
            char[] buf = new char[1];
            if (random.nextInt( 2 ) == 1)
                buf[0] = '男';
            else
                buf[0] = '女';
            return new String( buf );   //new String[Char[]] 可以用Char[]初始化创建一个String对象
        }
    }

    ;

    /**
     * 银行卡号
     */
    public static class
    BankCard implements Generator<String> {

        private int mod = 100000;
        private Random r = new Random();
        String[] str = new String[]{"436745", "622280", "458123", "521899", "622260", "402674"
                , "622892", "622188", "602969", "622760", "409666", "438088", "622752", "427020"};

        @Override
        public String next() {
            return str[r.nextInt( str.length )]
                    + r.nextInt( mod )
                    + r.nextInt( mod );
        }
    }

    /**
     * 身份证号
     */
    public static class
    IdentityCard implements Generator<String> {

        private int mod = 100000;
        private Random r = new Random();
        String[] str = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "X"};

        @Override
        public String next() {
            return String.valueOf( r.nextInt( 5 ) )
                    + r.nextInt( 4 )
                    + r.nextInt( mod )
                    + r.nextInt( mod )
                    + r.nextInt( mod )
                    + str[r.nextInt( str.length )];
        }
    }

    /**
     * 手机号
     */
    public static class
    PhoneNum implements Generator<String> {

        private int mod = 1000;
        private Random r = new Random();
        String[] str = new String[]{"13", "15", "18", "17"};

        @Override
        public String next() {
            return str[r.nextInt( str.length )]
                    + r.nextInt( mod )
                    + r.nextInt( mod )
                    + r.nextInt( mod );
        }
    }


    /**
     * 姓名
     */
    public static class
    Name implements Generator<String> {
        private static Random r = new Random();

//                File file1 = new File( "..\\BigData\\src\\resources\\properties\\createdata\\chinese" );
        File file1 = new File( "../bigdata/properties/createdata/chinese" );
//                File file2 = new File( "..\\BigData\\src\\resources\\properties\\createdata\\surname" );
        File file2 = new File( "../bigdata/properties/createdata/surname" );

        private Set set = ReadFile.readFile( file1 );
        private Set set2 = ReadFile.readFile( file2 );

        private static class ReadFile {
            /**
             * 把一个文件的内容读取到一个对应编码的Set中去
             */
            private static Set<Character> readFile(File file) {
                try {
                    String s = FileUtils.readFileToString( file, "utf-8" );
                    ArrayList<Character> characters = new ArrayList<>();
                    for (int i = 0; i < s.length(); i++) {
                        characters.add( s.charAt( i ) );
                    }
                    Set<Character> collect = characters.stream().collect( Collectors.toSet() );
                    return collect;
                } catch (IOException e) {
                    throw new RuntimeException( e );
                }
            }
        }

        @Override
        public String next() {
            char[] buf = new char[r.nextInt( 3 )];
            for (int i = 0; i < buf.length; i++)
                buf[i] = (char) set.toArray()[r.nextInt( set.size() )];
            String o = set2.toArray()[r.nextInt( set2.size() )].toString();
            String str=o + new String( buf );
            while (str.length()<=1){
                str=str+(char)set.toArray()[r.nextInt( set.size() )];
            }
            return str;   //new String[Char[]] 可以用Char[]初始化创建一个String对象
        }
    }
}
