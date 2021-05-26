package com.javabasic.service.officialjava.basic;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class basic {
    /**
     * java是一种强类型化的语言,这一点很重要,java的安全性和健壮性正是部分来自这一事实,
     * 强类型化意味着什么? 首先,每个变量具有一种类型,每个表达式具有一种类型,并且每种类型都是严格定义的,其次,所有赋值,不管是显示的,
     * 还是在方法调用中通过参数传递的,都要进行类型兼容性检查,java编译器检查所有表达式和参数,以确保类型是兼容的,任何类型不匹配都是错误,
     * 泛型的出现,正是为了强化这一特性
     * <p>
     * 1,基本类型  (8位 byte,16位 char short,32位int float,64为long double)
     * 整型:
     * byte    :有符号的8位类型,范围:-128-127,多用于数据流和二进制数据
     * short   :有符号的16位类型,范围:-32768-32768
     * int     :有符号的32位类型
     * long    :有符号的64位类型
     * 浮点型:
     * float   :宽度32位  1.4e-045~3.4e+0.38   单精度 占用空间是双精度的一半,对精度要求不高时使用
     * double  :宽度64位  4.9e-324~1.8e+308    双精度 在针对高速数学运算进行了优化的某些现代处理器上,双精度数值运算熟读更快,
     * 所有超越数学函数,是所有,如sin(),cos(),sqrt()......,都返回双精度值
     * 字符型:
     * char    :c/c++中的char是8位,java中的是16位原因是java使用Unicode表示字符,
     * Unicode定义了一个完全国际化的字符集,包含了表示人类语言中的所有字符,为此,Unicode需要16位宽,范围0~65536,没有负的char值
     * ASCII标准字符集的范围是0~127
     * java的(int-char)强转是按Unicode来的额
     * 布尔型:
     * boolean
     * <p>
     * 2,常见的封装器与基础类型数据或者基础类型变量之间的引用就属于装箱拆箱
     * 基本数据类型从小到大的顺序(强转的顺序,强转不同与装箱拆箱,各有用途)：
     * byte→short→int→long→float→double
     * char→int
     * boolean不参与转换。
     * 强转:long i=(long)123
     * 装箱;Long n=i,Long m=123
     * <p>
     * 3, Java中有8种基本数据类型boolean、byte、short、char、int、flaot、long、double，基本数据类型作为Java语言的一部分，
     * 但基本数据类型不是对象，基本数据类型放在堆栈中，对象放在堆中。堆的读写速度远不及栈，如果使用基本数据类型相当于在栈上进行操作，
     * 对变量的创建和销毁速度非常快。相反，如果用类进行定义变量，需要在堆中进行操作，创建和销毁速度都比较慢。
     * <p>
     * 出于性能方面的考量，为了提高性能这样做是合理的。但有些地方必须用到对象，基本数据类型不是对象，怎么办呢？
     * Java针对每种基本数据类型提供了包装类，即Boolean、Byte等。这样就解决了基本数据类型面向对象用的问题。
     * <p>
     * 同时，也正是包装类的使用，说明Java是一种纯OO的语言。
     * <p>
     * <p>
     * 4,运算符,见参考书64页  注意左移 << 右边用0补充,对表达式进行求值时,byte和short会自动提升为int,如果是负数,会进行符号扩展,
     * 高阶位使用1填充,要是还想获得byte型结果,需要抛弃前面3个字节,32位->8位
     * 右移 >>
     * 无符号右移 >>> 用0移进高阶位
     */

    public static void main(String[] args) {
        BigDecimal bigDecimal = BigDecimal.valueOf( 0.012345698654222200011111111111111111111111111111 );
//        System.out.println(bigDecimal);

        Character chr = 'a';
        Character.valueOf( 'a' );

        String s = "abcdefg hijklmn opqrst uvWWWWXXXX YYYY AAAdfsdfs BBJHLJ; dka***<<asdfa >>>>";
        char[] chars = s.toCharArray();
        for (char i : chars) {
            if (Character.isUpperCase( i )) System.out.println( i );
        }

        char[] chars1 = s.toCharArray();
        String[] m = {"aaa", "bbb", "ccc"};
        Boolean[] a = {true, false};
        Stream.of( m ).forEachOrdered( System.out::println );
        Arrays.stream( m ).forEachOrdered( System.out::println );
        Arrays.stream( a ).forEachOrdered( System.out::println );
        Arrays.stream( new char[][]{chars1} ).forEachOrdered( System.out::println ); //特殊

        /**
         * TODO Random
         * seed 原来如果没有种子的话，程序会取当前日期的毫秒数来作为种子，所以每次执行种子都会不同，因为每次时间的毫秒数是不一样的，
         * 所以随机出来的数也就会不同,值与seed,netint参数,及netint指向的位置有关,random是一个随机数集
         * 1,不带参数的nextInt()会生成所有有效的整数（包含正数，负数，0）
         *
         * 2、带参的nextInt(int x)则会生成一个范围在0~x（不包含X）内的任意正整数
         */
        Random random = new Random( 47 );
        for (int i = 1; i < 100; i++) {
            System.out.println( "100:" + random.nextInt( 100 ) );  //random是一个随机数集
        }
        System.out.println( randomInt( 10, 100 ) );
        System.out.println( (int) 'A' );   //java的强转是按Unicode来的额
        System.out.println( (char) 65 );   //Unicode

        /**
         * Unidcoe
         * null对应的uniode码为0
         */
        System.out.println( Integer.toHexString( 'A' ) );
        System.out.println( (char) Integer.parseInt( "41", 16 ) );

    }

    /**
     * 生成[min, max]之间的随机整数
     *
     * @param min 最小整数
     * @param max 最大整数
     */
    private static int randomInt(int min, int max) {
        return new Random().nextInt( max ) % (max - min + 1) + min;
    }

}
