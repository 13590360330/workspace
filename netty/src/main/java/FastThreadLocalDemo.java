import io.netty.util.concurrent.FastThreadLocal;


/**
 * 多线程共享FastThreadLocal,作用和ThreadLocal类似，其性能更高，而且不需要关系内存泄漏的问题(在普通线程中(主线程)使用removeall方法释放对象)
 * <p>
 * 总结:ThreadLocal的value是存储对象的强引用,弱引用关联强引用,Entry[]中的key(ThreadLocal)是value的弱引用,当value强引用被释放时(remove),
 * 弱引用ThreadLocal指向的对象会在gc时被回收,如果在线程中用new对象的方式,不将引用指向其他对象,
 * 发生gc时则无法回收对象所以在多线程中创建本地变量推荐ThreadLocal能在一定程度避免oom的发生
 *
 *
 * <p>
 * 作用类似如下:为了避免多线程时new 多个对象
 * Synchronized实现内存共享，ThreadLocal为每个线程维护一个本地变量。
 * 1000个线程难道new1000个SimpleDataFormat？所以当时我们使用了线程池加上ThreadLocal包装SimpleDataFormat，
 * 再调用initialValue让每个线程有一个SimpleDataFormat的副本，从而解决了线程安全的问题，也提高了性能。
 * <p>
 * 简要言之：往ThreadLocal中填充的变量属于当前线程，该变量对其他线程而言是隔离的。
 * <p>
 * <p>
 * 使用ThreadLocal而不用new 对象的原因是前者是弱引用,后者是强引用(参考WeakReferenceDemo.java)
 * <p>
 * Java常通过使用弱引用来避免内存泄漏，例如在JDK中有一种内存变量ThreadLocal，通过ThreadLocal变量可以使共享的变量在不同的线程中有不同的副本，
 * 原理是在每一个Thread有一个threadLocalMap的属性，用来存放ThreadLocal对象，ThreadLocalMap中是通过一个Entry[]的散列表存放ThreadLocal变量以及ThreadLocal的value，
 * 而作为Entry的key的ThreadLocal就是使用的弱引用
 * 原因是如果不使用弱引用，那么当持有value的强引用释放掉后，当线程没有回收释放时，threadLocalMap会一直持有ThreadLocal以及value的强应用，
 * 导致value不能够被回收，从而造成内存泄漏。
 * 通过使用弱引用，当ThreadLocal的强引用释放掉后，通过一次系统gc检查，发现ThreadLocal对象只有threadLocalMap中Entry的若引用持有，
 * 此时根据弱引用的机制就会回收ThreadLocal对象，从而避免了内存泄露。当然ThreadLocal还有一些额外的保护措施，
 */
public class FastThreadLocalDemo {

    final class FastThreadLocalTest extends FastThreadLocal<Object> {
        @Override
        protected Object initialValue() throws Exception {
            return new Object();
        }
    }

    private final FastThreadLocalTest fastThreadLocalTest;

    public FastThreadLocalDemo() {
        fastThreadLocalTest = new FastThreadLocalTest();
    }


    public static void main(String[] args) {
        FastThreadLocalDemo fastThreadLocalDemo = new FastThreadLocalDemo();

        new Thread( new Runnable() {
            @Override
            public void run() {
                Object obj = fastThreadLocalDemo.fastThreadLocalTest.get();
                try {
                    for (int i = 0; i < 10; i++) {
                        //循环修改共享对象的值
                        fastThreadLocalDemo.fastThreadLocalTest.set( new Object() );
                        Thread.sleep( 1000 );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } ).start();

        Object obj1 = fastThreadLocalDemo.fastThreadLocalTest.get();
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Object obj2 = fastThreadLocalDemo.fastThreadLocalTest.get();
                    for (int i = 0; i < 10; i++) {
                        //不同线程之间获取的值是不同的(主线程和当前线程)
                        System.out.println( obj1 == fastThreadLocalDemo.fastThreadLocalTest.get() );
                        //每一个线程获取的值一直是同一个
                        System.out.println( obj2 == fastThreadLocalDemo.fastThreadLocalTest.get() );
                        Thread.sleep( 1000 );
                    }
                } catch (Exception e) {

                }
            }
        } ).start();
    }
}
