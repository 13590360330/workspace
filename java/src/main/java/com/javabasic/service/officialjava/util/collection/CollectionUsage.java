package com.javabasic.service.officialjava.util.collection;


import java.util.*;

/**
 * TODO 集合框架
 * java集合框架标准化了程序处理对象组的方式
 * 框架必须满足的要求,1:高性能  2:框架必须允许不同类型的集合以类似的方式进行工作,并且具有高度的互操作性  3:扩展或改造集合必须易于实现
 * 为了满足这些要求,整个集合框架基于一套标准接口进行构造
 * 最后,必须添加可以将标准数组集成到集合框架中的机制
 * <p>
 * Java提供了特定的类似存储和管理对象组,例如Dictionary,Vector,Stack和Properties
 * <p>
 * 集合框架定义了一些可以应用于集合和映射的算法,这些算法被定义成Collections类中的静态方法,见P552
 *
 * 集合框架针对最通用的编程任务,当需要存储和检索信息时,可考虑使用集合,请记住,集合不是专门针对"大任务"而设计的,当应用于存储单一任务数据时,他们很有效
 */
public class CollectionUsage {

    public static void main(String[] args) {

        /**
         * 1,基本集合 动态数组,链表,数以及哈希表
         *   集合框架除了集合外,还定义了一些映射接口和类,尽管映射是集合框架的组成部分,但严格意义上讲,它们不是集合,不过,可以获得映射的集合视图(collection-view),
         *   这种视图包含来自映射的元素,并存储在集合中,因此,作为一种选择,可以像处理集合一样处理映射的内容
         *
         * 2,迭代器Iterator为访问集合的元素提供了通用,标准的方式,每次访问一个元素,因此迭代器提供了枚举集合内容的一种方式,因为每个集合都提供了迭代器,
         * 所以可以通过Iterator定义的方法访问所有集合类的元素
         *
         * 3,spliterator支持并行迭代的并行迭代器 jdk8的新特性
         *
         * 4,JDK5,整个集合框架针对泛型进行了重新设计,所有集合现在都是泛型的,泛型添加了集合一直缺失的一个特性,类型安全性
         *
         * 5,集合只能存储引用,不能存储基本类型值,基本类型值需要用类型封装器封装
         *
         * 6.Collection接口声明的方法(参考书P506):
         *     add(E obj)                                                 boolean
         *     addAll(Collection<? extends E> c)                          boolean
         *     contains(Object obj)                                       boolean
         *     containsAll(Collection<?> c)                               boolean
         *     equals(Object obj)                                         boolean
         *     hashcode()                                                 int
         *     isEmpty()                                                  boolean
         *     iterator()                                                 Iterator<E>
         *     spliterator()     JDK8                                     default Spliterator<E>
         *     Stream()                                                   default Stream<E>
         *     parallelStream()                                           default Stream<E>
         *     remove(Object obj)                                         boolean
         *     removeAll(Collection<?> c)                                 boolean
         *     removeIf(Predicate<? super E> predicate)                   default boolean
         *     retaimAll(Collection<?> c)(除了s元素,移除所有元素)           boolean
         *     size()                                                     int
         *     toArray()                                                  Object[]
         *     toArray(T array[])                                         <T> T[] toArray(T array[])
         *
         * 7,集合接口
         *     7.1,List接口定义了:--列表
         *         列表可以包含重复的元素
         *         比较重要的方法:
         *             add(int index,E obj)
         *             sort(Compartator<? super E> comp)(JDK8)
         *             subList(int start,int end)
         *             get(int index)
         *             indexOf(Object Obj)
         *             lastIndexOf(Object Obj)
         *             listIterator()
         *             listIterator(int index)                            返回从index位置开始的索引
         *             remove(int index)                                  移除并返回该元素,后面所有的元素的索引都被减1
         *             repalceAll(UnaryOperator<E> opToApply)             使用opToApply函数获得的值更新列表中的每个元素(JDK8)
         *             set(int index,E obj)                                           修改元素
         *
         *     7.2,Set接口       --组
         *         组中不允许有重复的元素,add添加重复元素时返回false
         *        7.2.1,SortedSet接口,扩展了(继承)Set,并且声明了以升序进行排序的组行为  参考书P510
         *        7.2.2,NavigableSet接口,扩展了SortedSet接口,并且声明了支持基于最接近匹配原则检索元素的集合行为
         *            ceiling(E obj)                      E               在组中查找大于等于obj的最小元素,如果找到了,就返回元素,否则返回null
         *            higher(E obj)                       E               查找组中大于obj的最大元素,如果找到了,就返回元素,否则返回null
         *            floor(E obj)                        E               查找组中小于等于obj的最大元素,如果找到了,就返回元素,否则返回null
         *            lower(E obj)                        E               查找组中小于obj的最大元素,如果找到了,就返回元素,否则返回null
         *            descendingIterator()                Iterator<E>     返回一个从最大元素向最小元素移动的迭代器,换句话说,返回一个反向迭代器
         *            descendingSet()                     NavigableSet<E> 返回用来翻转调用组的NavigableSet对象,结果组基于调用组
         *            headSet(E upperBound,boolean incl)  NavigableSet<E> 返回的NavigableSet对象包含调用组中小于upperBound的所有元素,如果incl为true,那么包含等于upperBound的那个元素,结果组基于调用组
         *            tailSet(E lowerBound,boolean incl)  NavigableSet<E> 返回的NavigableSet对象包含调用组中大于lowerBound的所有元素,如果incl为true,那么包含等于lowerBound的那个元素,结果组基于调用组
         *            pollFirst()                         E               返回第一个元素,原组中移除该元素,应为继承自SortedSet接口,所以该元素具有最小值,如果小于改值或者组为空,返回null
         *            pollLast()                          E               返回最后一个元素,原组中移除该元素,应为继承自SortedSet接口,所以该元素具有最大值,如果大于改值或者组为空,返回null
         *            subSet(E lowerBound,boolean lowIncl,
         *             E upperBound,boolean highincl)     NavigableSet<E> 返回的NavigableSet对象包含调用组中小于upperBound且大于lowerBound的所有元素,如果highincl(lowIncl)为true,那么包含等于upperBound(lowerBound)的那个元素,结果组基于调用组
         *
         *
         *    7.3,Queue接口     --队列
         *        队列通常是先进先出的列表
         *            element()                          E                 返回头部元素,不移除,队列为空--NoSuchElementException
         *            remove()                           E                 返回头部元素,移除,队列为空--NoSuchElementException
         *            offer(E obj)                       boolean           添加
         *            peek()                             E                 返回头部元素,不移除,队列为空,返回null
         *            poll()                             E                 返回头部元素,移除,队列为空,返回null
         *        7.3.1,Deque 接口  双端队列  P512
         *
         *
         */


        /**
         *8,集合类
         *    8.1,ArrayList类,扩展了AbstractList类并实现了List接口
         *        ArrayList<E>                           定义空的ArrayList()
         *        ArrayList(Collection<? extends E> c)   返回一个用集合c的元素初始化的ArrayList对象
         *        ArrayList(int capacity)
         *        <T> T[] toArray(T array[])的使用
         */
        List c = Arrays.asList( "", "1", "2" );

        ArrayList<Integer> al = new ArrayList<>();
        al.add( 1 );
        al.add( 2 );
        al.add( 3 );
        al.add( 4 );
        Integer ia[] = new Integer[al.size()];
        //集合可以直接转换数组,Collection接口定义,不需要先转换成stream
        ia = al.toArray( ia );

        /**
         *    8.2,LinkedList类,扩展了AbstractSequentialList类,实现了List,Deque以及Queue接口,并且它还提供了一种链表数据结构
         *        LinkedList<>                            定义空的LinkedList()
         *        LinkedList(Collection<? extends E> c)   返回一个用集合c的元素初始化的ArrayList对象
         *
         *    8.3,HashSet类,扩展了AbstractSet类并实现了Set接口,该类用于创建使用哈希表存储元素的集合,不能保证元素的顺序
         *        hash表使用了称之为散列的机制存储信息,在散列机制中,键的信息用于确定唯一的值,称为哈希码,然后将哈希码用作索引,在索引位置存储与键关联的数据,
         *        将键转换成哈希码是自动执行的-您永远不会看到哈希码本身,散列机制的优势是add(),contians(),remove()以及size()方法的执行时间保持不变,即使是
         *        很大的组也是这样
         *        HashSet()
         *        HashSet(int capacity)                   capacity初始容量(默认16),fillRatio填充率(默认0.75)
         *        HashSet(int capacity,float fillRatio)   capacity初始容量(默认16),fillRatio填充率(0.0~1.0)
         *        HashSet(Collection<? extends E> c)
         *
         *    8.4,LinkedHashSet类,LinkedHashSet扩展了HashSet,它没有添加自己的方法,LinkedHashSet是有序的,迭代时以插入的顺序返回
         *        LinkedHashSet维护组中条目的一个链表,链表中条目的顺序,也就是插入它们的顺序,迭代时以插入的顺序返回,toString也是按顺序
         *
         *    8.5,TreeSet类,扩展了AbstractSet类并实现了NavigableSet接口,用于创建使用树进行存储的组,对象以升序存储
         */
        TreeSet treeSet = new TreeSet<String>();
        treeSet.add( "f" );
        treeSet.add( "g" );
        treeSet.add( "b" );
        treeSet.add( "e" );
        treeSet.add( "a" );
        System.out.println( treeSet );
        System.out.println( treeSet.subSet( "e", true, "g", true ) );

        /**
         *     8.6,PriorityQueue类,扩展了AbstractQueue类并实现了Queue接口,用于创建根据队列的比较器来判定优先次序的队列
         *         PriorityQueue()                                          空队列,起始容量为11
         *         PriorityQueue(int capacity)
         *         PriorityQueue(Comparator<? super E> comp) JDK8新增
         *         PriorityQueue(int capacity,Comparator<? super E> comp)
         *         PriorityQueue(Collection<? extends E> c)                 构造函数接收参数创建的队列,容量会自动增长
         *         PriorityQueue(PriorityQueue<? extends E> c)              构造函数接收参数创建的队列,容量会自动增长
         *         PriorityQueue(SortedSet<? extends E> c)                  构造函数接收参数创建的队列,容量会自动增长
         *
         *         注意,迭代PriorityQueue类对象,获取的数据顺序是不确定的,需要用到Queue接口定义的方法
         *
         *     8.7,ArrayDeque类,扩展了AbstractCollection类并实现了Deque,没有添加自己的方法,方法见Deque
         *         类似ArrayList,ArrayDeque是动态数组,没有容量限制(Deque接口支持限制容量的实现,不过这种限制非必须)
         *         ArrayDeque()                            定义空的ArrayDeque()  初始容量为16
         *         ArrayDeque(Collection<? extends E> c)   构造函数接收参数创建的队列,容量会自动增长
         *         ArrayDeque(int size)
         */
        ArrayDeque<String> strings = new ArrayDeque<>();
        strings.push( "a" );
        strings.push( "d" );
        strings.push( "v" );
        strings.push( "c" );
        strings.push( "b" );
        System.out.println( strings );

        /**
         *    8.8,EnumSet类  参考书P524
         *    8.9,RandomAccess接口,这个接口不包含成员,通过实现这个接口,可表明集合支持高效的随机访问其中的元素,ArrayList和遗留的Vector类实现了这个接口
         *        可以使用instanceof来判定类是否实现了某个接口或者对象是某个类的实例
         */

        System.getenv().entrySet();
    }
}
