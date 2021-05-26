package com.javabasic.service.officialjava.util.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO Stream流API
 * 流是数据的渠道,流操作数据源,入数组或集合,流本身不存储数据,而只是移动数据,在移动的过程中可能会对数据执行过滤,排序或者其他操作
 * <p>
 * lambda表达式回顾:
 * 1,格式: MyNumber myNum = (x) -> x*2;
 * 2,lambda表达式有一个重大特性,方法的引用  ClassName::Method (::是JDK8新增的一个分隔符,专门用于方法的引用)
 * 3,stream内部只能有final变量,外部的异常捕获不能捕获stream内的异常
 */
public class StreamUsage {

    public static void main(String[] args) {
        /**
         * 1,BaseStream是基础接口,它定义了所有流都可以使用的基本功能,它是一个泛型接口,其声明如下:
         * interface BaseStream<T,S extends BaseStream<T,S>>
         *     close()            void              关闭流(很少有流需要被关闭)
         *     isParallel()       boolean           判断并行流还是顺序流
         *     iterator()         Iterator<T>       获得流的迭代器(终端操作)
         *     onClose(Runnable handler)
         *                        S                 返回一个新流,handler指定了改流的关闭处理程序,当关闭该流时,将调用这个处理程序(中间操作)
         *     parallel()         S                 基于调用流,返回一个并行流,如果调用流是并行流,就返回该流(中间操作) 并行流失基于ForkJoinPool来实现的
         *     sequential()       S                 基于调用流,返回一个顺序流,如果调用流是顺序流,就返回该流(中间操作)
         *     unordered()        S                 基于调用流,返回一个无序流,如果调用流是无序流,就返回该流(中间操作)
         *     splitertor()       Splitertor<T>     获得流的splitertor,并返回其引用(终端操作)
         *<p>
         * 2,Stream<T>: BaseStream接口派生出了几个流接口,其中最具一般性的是Stream接口,其声明:interface Stream<T>
         *     除了继承自BaseStream的方法外,Stream接口还定义了几个自己的方法,
         *     其中除了filter,map,sorted,其他都是终端操作,中间操作返回新流,中间操作不是立即发生的,当执行终端操作时才会发生,终端操作会消费流,这种操作用于产生结果,一个流被消费后,就不能重用了;
         *     collect<Collector<? super T,A,R> collectorFunc>
         *                        <R,A>R            将元素收集到一个可以修改的容器中,并返回该容器,这被称为可变缩减操作,R指定结果容器的类型,
         *                                          T指定调用流的元素类型,A指定内部累加的类型,collectorFunc指定收集过程的工作方式(终端操作)
         *     collect(Supplier<R> supplier,BiConsumer<R, ? super T> accumulator,BiConsumer<R, R> combiner)
         *                        <R> R
         *     collect(Collector<? super T, A, R> collector)
         *                        <R, A> R
         *<p>
         *     count()            long              (终端操作)
         *<p>
         *     filter(Predicate<? super T> pred)
         *                        Stream<T>         产生一个流,其中包含调用流中满足pred指定的谓词的元素(中间操作)
         *<p>
         *     forEach(Consumer<? super T> action)
         *                        void              对每个元素执行action,不一定保留并行流的顺序 (终端操作)
         *     forEachOrdered(Consumer<? super T> action)
         *                        void              对每个元素执行action,按顺序处理 (终端操作),上面的操作更快是并行的
         *<p>
         *     map(Function<? super T,? extends R> mapFunc)
         *                        <R> Stream<R>     对调用流中的元素应用mapFunc,产生包含这些元素的一个新流(中间操作)
         *     mapToDouble(ToDoubleFunction<? super T> mapFunc)
         *                       DoubleStream                     (中间操作)
         *     mapToInt(ToIntFunction<? super T> mapFunc)
         *                       IntStream                        (中间操作)
         *     mapToLong(ToLongFunction<? super T> mapFunc)
         *                       LongStream                       (中间操作)
         *<p>
         *     flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
         *                      <R> Stream<R>        flatmap是为了处理原始流中的每个元素映射到结果流中的多个元素的情况,一对多的情况
         *     flatMapToInt(Function<? super T, ? extends IntStream> mapper)
         *                      IntStream
         *     flatMapToLong(Function<? super T, ? extends LongStream> mapper)
         *                      LongStream
         *     flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper)
         *                      DoubleStream
         *<p>
         *     max(Comparator<? super T> comp)
         *                      Optional<T>          使用comp指定的排序,找出并返回调用流中的最大元素(终端操作)
         *     min(Comparator<? super T> comp)
         *                      Optional<T>          使用comp指定的排序,找出并返回调用流中的最小元素(终端操作))
         *<p>
         *     reduce(T identityVal,BinaryOperator<T> accumulator)
         *                      T                    基于流中的元素,返回结果,这被叫做缩减操作(终端操作))
         *     reduce(BinaryOperator<T> accumulator)
         *                      Optional<T>
         *     reduce(U identity,BiFunction<U, ? super T, U> accumulator,BinaryOperator<U> combiner)
         *                      <U> U
         *<p>
         *     sorted()         Stream<T>            自然顺序排序 (中间操作)
         *     sorted(Comparator<? super T> comparator)
         *                      Stream<T>
         *<p>
         *     toArray()        object[]             (终端操作))
         *     toArray(IntFunction<A[]> generator)
         *                      <A> A[]
         *<p>
         *     distinct()       Stream<T>
         *     peek(Consumer<? super T> action)
         *                      Stream<T>
         *     limit(long maxSize)
         *                      Stream<T>
         *     skip(long n)     Stream<T>
         *<p>
         *     anyMatch(Predicate<? super T> predicate)
         *                      boolean                判断流中的一个或多个元素是否满足指定谓词(类似正则匹配的作用),或者说是否存在匹配的数据
         *     allMatch(Predicate<? super T> predicate)
         *                      boolean                是否都匹配
         *     noneMatch(Predicate<? super T> predicate)
         *                      boolean
         *<p>
         *     findFirst()     Optional<T>
         *     findAny()       Optional<T>              查找当前流中的元素
         *<p>
         *     剩下的都是做了实现的方法,如of() --类似于(List.of("4", "5", "6")),
         *     concat(),findFirst(),findAny(),empty()等还有一些自行了解
         *<p>
         *     以上方法中的Function是java.util.function包中声明的一个泛型函数式接口,其抽象方法为apply(T val)
         *     以上方法中的Consumer是java.util.function包中声明的一个泛型函数式接口,其抽象方法为accept()
         *     以上方法中的Predicate是java.util.function包中声明的一个泛型函数式接口,其抽象方法为test()
         *     以上方法中的BinaryOperator是java.util.function包中声明的一个泛型函数式接口,它扩展了BiFunction函数式接口,其抽象方法为T apply(T val,T val2)
         *     以上方法中的BiFunction函数式接口,其抽象方法为R apply(T val,U val2) --R指定了结果类型,T和U 分别是第一第二个操作数类型
         *<p>
         * 3,因为Stream操作的是对象的引用,所以不能直接操作基本类型,为了处理基本类型流,流API定义了以下接口:
         *     DoubleStream
         *     IntStream
         *     LongStream
         *<p>
         * 4,Collection接口定义了stream()方法,可以从调用集合获得一个流,因为每个集合流都实现了Collection接口,所以可以使用stream()
         * 方法获得任意类型的集合的流
         */

        ArrayList<Integer> myList = new ArrayList<>();
        myList.add( 7 );
        myList.add( 18 );
        myList.add( 19 );
        myList.add( 24 );
        myList.add( 17 );
        myList.add( 5 );
        Stream<Integer> stream = myList.stream();
        //传递方法的引用
        Optional<Integer> min = stream.min( Integer::compare );
        if (min.isPresent()) System.out.println( min.get() );
        System.out.println();

//        myList.stream().sorted((x,y)->Integer.compare( x,y ));
        Optional<Integer> max = myList.stream().max( Integer::compare );
        if (max.isPresent()) System.out.println( max.get() );
        System.out.println();

        Optional<Integer> reduce5 = myList.stream().reduce( Integer::sum );
        if (max.isPresent()) System.out.println( reduce5.get() );
        System.out.println();

        myList.stream().sorted().filter( x -> (x % 2) == 1 ).forEach( System.out::println );
        System.out.println();

        String s="abcdefg hijklmn opqrst uvWWWWXXXX YYYY AAAdfsdfs BBJHLJ; dka***<<asdfa >>>>";
        char[] chars = s.toCharArray();
        for (char i:chars){
            if(Character.isUpperCase( i )) System.out.println(i);
        }

        char[] chars1 = s.toCharArray();
        int[] m={1,2,3};
        double[] m2={1.1,2.1,3.0};
        Boolean[] m1={true,false};
        Stream.of( m ).forEachOrdered( System.out::println );
        Arrays.stream( m ).forEachOrdered( System.out::println );
        Arrays.stream( m1 ).forEachOrdered( System.out::println );
        Arrays.stream( m2 ).forEachOrdered( System.out::println );
        Arrays.stream( new char[][]{chars1} ).forEachOrdered( System.out::println ); //特殊

        /**
         * 5,缩减操作 reduce
         *     reduce(T identityVal,BinaryOperator<T> accumulator)
         *                      T                    基于流中的元素,返回结果,这被叫做缩减操作(终端操作))
         *     reduce(BinaryOperator<T> accumulator)
         *                      Optional<T>
         *     reduce(U identity,BiFunction<U, ? super T, U> accumulator,BinaryOperator<U> combiner)
         *                      <U> U
         *
         *     以上方法中的BiFunction函数式接口,其抽象方法为R apply(T val,U val2) --R指定了结果类型,T和U 分别是第一第二个操作数类型
         *     以上方法中的BinaryOperator是java.util.function包中声明的一个泛型函数式接口,它扩展了BiFunction函数式接口,其抽象方法为T apply(T val,T val2)
         *     在用到reduce()中时,val将包含前一个结果,val2将包含下一个元素,在第一次调用时,取决于所使用的reduce()版本,val将包含单位值或第一个元素
         *     累加器(BiFunction)及所有的并行流 必须满足以下三个约束:
         *     a,无状态
         *     b,不干预
         *     c,结合性
         *     a,指操作不依耐任何状态信息,每个元素都单独处理. b,指操作不会改变数据源. C,关联性,指给定一个关联运算符,在一系列操作中使用该运算符时,先处理哪一对操作数无关紧要
         *
         */

        Optional<Integer> reduce = myList.stream().reduce( (a, b) -> a * b );
        if (reduce.isPresent()) System.out.println( "reduce:" + reduce.get() );

        // 定义reduce(T identityVal,BinaryOperator<T> accumulator) 中identityVal是这样一个值:对于涉及identityVal和流中任意元素的累积操作,得到的结果就是元素自身,没有改变
        //例如如果是加法 identityVal=0,因为0+x=x,对于乘法 identityVal=1,1*x=x
        Integer reduce1 = myList.stream().reduce( 1, (a, b) -> a * b );
        System.out.println( "reduce1:" + reduce1 );

        //求偶数的积
        Integer reduce2 = myList.stream().reduce( 1, (a, b) -> {
            if (b % 2 == 0) return a * b;
            else return a;
        } );
        System.out.println( "reduce2:" + reduce2 );

        //reduce(U identity,BiFunction<U, ? super T, U> accumulator,BinaryOperator<U> combiner)   accumulator(累加器)先求平方根,combiner(合并器)将结果相乘,下面两种算法结果是不一样的
        Double reduce3 = myList.parallelStream().reduce( 1.0, (a, b) -> a * Math.sqrt( b ), (a, b) -> a * b );
        double reduce4 = myList.parallelStream().mapToDouble( x -> new Double( x ) ).reduce( 1, (a, b) -> a * Math.sqrt( b ) );
        System.out.println( "reduce3:" + reduce3 );
        System.out.println( "reduce4:" + reduce4 );

        /**
         * 6,收集
         * 从流中获取集合,为了执行这种操作,流API提供了collect()方法,它有两种形式
         *     a,<R,A> R collect(Collector<? super T,A,R> collectorFunc)   --R结果类型,T元素类型,A内部累积类型
         *         interface Collector<T,A,R>接口是在java.util.stream包中声明的 --R,T,A同上
         *         我们将使用Collectors类提供的两个预定义收集器,还有其他方法自己看,Collectors类在java.util.stream包中声明
         *         static<T> Collector<T,?,List<T>> toList()
         *         static<T> Collector<T,?,Set<T>> toSet()
         *     b,<R> R collect(Supplier<R> target,BiConsumer<R,? super T> accumulator,BiConsumer <R,R> combiner) target指定如何创建用于保存结果的对象,
         *         accumulator将一个元素添加到结果中,combiner合并两个部分结果
         */
        Set<Integer> collect = myList.stream().collect( Collectors.toSet() );
        //nameAndPhone.collect(()->new LinkedList<>(),
        //                     (list,element) -> list.add(element)
        //                     (listA,listB) -> listA.addAll(listB))
        //Map<Type, Dish> byType = dishes.stream().collect(toMap(Dish::getType, d -> d));
        //拼起来
        //直接连接
        //String join1 = dishes.stream().map(Dish::getName).collect(Collectors.joining());
        //逗号
        //String join2 = dishes.stream().map(Dish::getName).collect(Collectors.joining(", "));

        /**
         * 7,并行迭代器 Spliterator
         *     我们主要用到Spliterator的三个方法
         *     a,boolean tryAdvance(Consumer<? super T> action):  如果有下一个元素要处理tryAdvance返回true,否则返回false,相当于迭代器iterator的next()和hasNext()
         *     而Consumer是一个函数式接口,action指定了在迭代中的下一个元素上执行的操作,
         *     b,Spliterator<T> trySplit()将迭代器中的元素分为两部分,返回一部分新的Spliterator
         *     c,default void forEachRemaining(Consumer<? super T> action) 这个方法对每个未处理的元素应用action,然后返回
         */
        Spliterator<Integer> spliterator1 = myList.stream().spliterator();
        while (spliterator1.tryAdvance( System.out::println )) ;

        Spliterator<Integer> spliterator = myList.stream().spliterator();
        Spliterator<Integer> integerSpliterator = spliterator.trySplit();
        if (integerSpliterator != null) {
            System.out.println( "Output from splitItr2,显示一部分: " );
            integerSpliterator.forEachRemaining( System.out::println );
        }
        System.out.println( "\nOutput from splitItr,显示剩余数据:" );
        spliterator.forEachRemaining( x -> System.out.println( x ) );

    }
}
