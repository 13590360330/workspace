package com.javabasic.service.officialjava.util.collection;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * TODO 映射
 * 映射是存储键和值之间关联关系(键值对)的对象,键和值都是对象,键必须唯一,但值可以重复,某些映射可以接受null键和null值
 * 注意:映射没有实现Iterable接口,这意味着不能使用for-each风格的for循环遍历映射,此外不能为映射获取迭代器,但是,可以获取映射的集合视图,
 * 集合视图允许使用for循环和迭代器
 * 映射围绕两个基本方法:get()和put(),前面提到过,尽管映射是集合框架的一部分,但映射不是集合,因为没有实现Collecttion接口,但是,可以获取映射的集合视图
 * entrySet()方法,该方法返回一个包含映射中元素的Set对象
 * keySet()      ,获取键的集合视图
 * values()      ,获取值的集合视图
 * 对于这3个集合视图,集合都是基于映射的,修改其中的一个集合会影响其他集合,集合视图是将映射集成到更大集合框架中的手段
 */
public class MapUsage {
    public static void main(String[] args) {
        /**
         * 1,映射接口定义了映射的特性和本质
         *     Map                    将唯一的键映射到值
         *     Map.Entry              描述映射中的元素(键/值对),这是Map的内部类
         *     NavigableMap           扩展SortedMap接口,以处理基于最接近匹配原则的键/值对检索
         *     SortedMap              扩展Map接口,从而以升序保存键
         *
         * 1.1,Map.Entry接口
         * Map接口声明的entrySet方法,返回的一个包含映射条目的Set对象,组的所有元素都是Map.Entry对象
         *     equals()                 boolean
         *     getKey()                 K                   返回该映射条目的键
         *     getValue()               V                   返回该映射条目的值
         *     hashcode()               int
         *     setValue()               V                   遇到异常会抛出
         */

        HashMap<String, Double> stringDoubleHashMap = new HashMap<>();
        stringDoubleHashMap.put( "Liu cheng", new Double( 1234.09 ) );
        stringDoubleHashMap.put( "Tom Smith", new Double( 234.09 ) );
        stringDoubleHashMap.put( "Jane Baker", new Double( 1200.10 ) );
        stringDoubleHashMap.put( "Tod Hall", new Double( 1634.15 ) );
        Set<java.util.Map.Entry<String, Double>> entries = stringDoubleHashMap.entrySet();
        entries.stream().forEach( x -> System.out.println( x.getKey() + ":" + x.getValue() ) );

        /**
         * 1.2,Map接口, interface Map<K,V>
         *     clear                    void
         *     compute(K k,BiFunction<? super V,? super V,? extends V> func)
         *                              default V            调用func以构造一个新值,如果func的返回值不是null,就把新的键值对添加到映射中,移除原来的配对,并返回新值,
         *                                                   如果func返回null,就移除原来的配对并返回null(JDK8)
         *     computeIfAbsent()        default V            ????????????
         *     computeIfPresent()       default V            ????????????
         *     containsKey(Objeck k)    boolean
         *     containsValue(Objeck v)  boolean
         *     entrySet()               Set<Map.Entry<K,V>>  返回包含映射中所有条目的Set对象,这个组包含Map.Entry类型的对象,因此,
         *                                                   该方法提供了调用映射的一个组视图
         *     equals(Object obj)       boolean
         *     forEach(BiConsumer<? super K,? super V> action)
         *                              default void         对调用映射中的每个元素执行action,如果在操作过程中移除了元素,会抛出ConcurrencyModificationException异常(JDK8)
         *     get(Object k)            V
         *     getOrDefault(Object k,V defval)
         *                              default v            如果映射中包含于k关联的值,就返回该值,否则返回defval(JDK8)
         *     hashcode()
         *     isEmpty()
         *     keyset()                Set<K>                返回包含映射中某些键的Set对象,这个方法提供了调用映射中键的一个组视图
         *     merge(K k,V v,BiFunction<? super V,? super V,? extends V> func)
         *                             default V             如果k没有包含在映射中,就添加(k,v),并返回v,否则,func基于原有的值返回一个新值,
         *                                                   键被更新为使用这个新值,并且merge()方法返回这个值,如果func返回的值为null,就从映射中删除现有的键和值,并返回null(JDK8)
         *     put(K k,V v)            V
         *     putAll(Map<? extends K,? extends V> m)
         *                             void                  将m中的所有条目添加到调用映射中
         *     putIfAbsent(K k,V v)    default V             如果此键值配对没有包含在调用映射中,或者现有的值为null,就将此配对添加到调用映射中,并返回原来的值,
         *                                                   如果之前不存在映射,或者值为null,就返回null(JDK8)
         *     remove(Object k)        V
         *     remove(Object k,Object v)
         *                             default boolean       如果(k,v)包含在调用映射中,就移除该配对,并返回true,否则,返回false(JDK8)
         *     replace(K k,V oldV,V newV)
         *                             default boolean       如果(k,oldV)包含在映射中,就用newV替换oldV,返回true,否则,返回false(JDK8)
         *     replace(K k,V v)        default V             如果k键存在,就将其值设置为V,并返回原来的值,否则,返回false(JDK8)
         *     replaceAll(BiFunction<? super K,? super V,? extends V> func)
         *                             default void          对调用映射的每个元素执行func,用func的返回结果替换元素,如果在操作中删除了元素,抛出ConcurrentModificationException(JDK8)
         *     size()
         *     values()                Collection<V>         返回包含映射中所有值的集合,该方法提供了调用映射中值的一个集合视图
         *
         * 1.3,SortedMap接口
         *     SortedMap接口扩展了Map接口,确保条目以键的升序保存
         *     扩展的方法:
         *     comparator()            Comparator<? super K> 返回调用的有序映射的比较器,如果调用映射使用的是自然排序,就返回null
         *     firstKey()              K
         *     lastKey()               K
         *     headMap(K end)          SortedMap<K,V>        返回由键小于end的那些条目组成的SortedMap
         *     subMap(K Start,K end)   SortedMap<K,V>        返回键在start和end之间(Start<=K<end)的SortedMap
         *     tailMap(K start)        SortedMap<K,V>        返回键Start<=K的SortedMap
         *
         * 1.4,NavigableMap接口
         *     扩展了SortedMap接口,支持基于最接近匹配原则的条目检索行为
         */

        /**
         * 2,映射类
         * 2.1,HashMap类  哈希映射
         *     HashMap扩展了AbstractMap类并实现了Map接口,它使用哈希表存储映射,这使得即使对于较大的集合,get(),put()方法执行时间不变,不保证元素顺序
         *     HashMap()
         *     HashMap(Map<? extends K,? extends V> m)
         *     HashMap(int capacity)
         *     HashMap(int capacity,float fillRatio)   //capacity默认容量是16,默认填充率是0.75
         *
         * 2.2,TreeMap类  树映射
         *     TreeMap扩展了AbstractMap类并实现了NavigableMap接口,但没有增加自己的方法,该类用于创建存储在树结构中的映射.TreeMap提供了有序存储键/值对的高效手段,并支持快速检索,
         *     应当注意,与哈希映射不同,树映射确保元素以键的升序存储.TreeMap是泛型类
         *     TreeMap()
         *     TreeMap(Comparator<? super K> comp)
         *     TreeMap(SortedMap<K,? extends V> sm)     //使用与sm相同的顺序进行排序
         *     TreeMap(Map<? extends K,? extends V> m)  //使用键的自然顺序进行排序
         */
        TreeMap<Object, Object> objectObjectTreeMap = new TreeMap<>();
        objectObjectTreeMap.put("Liu cheng", new Double( 1234.09 ) );
        objectObjectTreeMap.put("Tom Smith", new Double( 234.09 ) );
        objectObjectTreeMap.put("Jane Baker", new Double( 1200.10 ) );
        objectObjectTreeMap.put("Tod Hall", new Double( 1634.15 ) );
        Set<java.util.Map.Entry<Object, Object>> entries1 = objectObjectTreeMap.entrySet();
        entries1.stream().forEach( x-> System.out.println(x) );

        /**
         * 2.3,LinkedHashMap类
         *      LinkedHashMap扩展了HashMap类,在映射中以插入条目的顺序维护一个条目链表,从而可以按照插入顺序迭代整个映射,也就是说,迭代的顺序,是插入元素的顺序
         *      LinkedHashMap()
         *      LinkedHashMap(Map<? extends K,? extends V> m)
         *      LinkedHashMap(int capacity)
         *      LinkedHashMap(int capacity,float fillRatio)   //capacity默认容量是16,默认填充率是0.75
         *      LinkedHashMap(int capacity,float fillRatio,boolean Order) //Order为true,就使用访问顺序存储元素,Order为false,就使用插入顺序存储元素
         *
         *      LinkedHashMap只添加了一个方法removeEldestEntry(),重写此方法,使之返回true(默认返回false),移除映射中最旧的条目
         *
         * 2.4,IdentityHashMap类
         * 2.5,EnumMap类
         */



    }
}
