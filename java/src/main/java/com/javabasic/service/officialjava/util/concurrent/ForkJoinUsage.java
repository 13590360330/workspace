package com.javabasic.service.officialjava.util.concurrent;

/**
 * TODO Fork/Join(并行/连接)框架 (JDK7)
 * Fork/Join简化了多线程的创建和使用,并且自动使用多处理器,这两个特性使得Fork/Join框架成为并行编程的最佳选择
 */
public class ForkJoinUsage {

    public static void main(String[] args) {

        /**
         * 1,Fork/Join框架位于 java.util.concurrent包中,Fork/Join框架中处于核心地位的是以下4个类:
         *     ForkJoinTask<V>: 用来定义任务的抽象类
         *     ForkJoinPool: 管理ForkJoinTask的执行
         *     RecursiveAction:ForkJoinTask<V>的子类,用于不返回值的任务
         *     RecursiveTask<V>:ForkJoinTask<V>的子类,用于返回值的任务
         *
         *     下面是它们之间的关系:
         *     ForkJoinPool管理ForkJoinTask的执行,ForkJoinTask是抽象类,
         *     RecursiveAction和RecusiveTask对ForkJoinTask进行了扩展
         *
         * 2,ForkJoinTask<V>
         *     ForkJoinTask表示任务的轻量级抽象,而不是执行线程,ForkJoinTask通过线程(由ForkJoinPool类型的线程池负责管理)来执行,
         *     通过这种机制,可以使用少量的实际线程来管理大量任务,因此与线程相比,ForkJoinTask非常高效
         *     核心方法:
         *     final ForkJoinTask<V> fork()
         *     final V join()
         *     fork()方法为调用任务的异步执行提交调用任务,这意味着调用fork()方法的线程将持续运行,在JDK8中,如果没有在ForkJoinPool中执行时调用fork()方法,
         *     则会自动使用一个公共池,join()方法等待调用该方法的任务终止,任务结果被返回,因此,通过使用fork()和join()方法,可以开始一个或多个新任务,然后等待它们结束
         *     重要的方法:
         *     final V invoke()
         *     该方法将并行(fork)和连接(join)操作合并到单个调用中,因为可以开始一个任务,并等待该任务结束,    invoke()返回调用任务的结果
         *     final void invokeAll(ForkJoinTask<?> taskA,ForkJoinTask<?> taskB)
         *     final void invokeAll(ForkJoinTask<?> ......taskList)
         *     invokeAll()方法可以同时调用多个任务
         *
         * 3,RecursiveAction: ForkJoinTask的其中一个子类,这个类用于封装不返回结果的任务,我们要重写RecursiveAction的抽象方法compute(),
         * 此方法代表任务的计算部分,compute()是受保护的和抽象的,所以必须被子类实现(除非子类也是抽象的),一般来说,RecursiveAction用于为不返回结果的任务实现递归的,分而治之的策略
         *     protected abstract void compute()
         *
         * 4,RecursiveTask<V>: ForkJoinTask的其中一个子类,这个类用于封装返回结果的任务,结果类型是由V指定,其他同上重写compute(),但要返回结果
         *     protected abstract V compute()
         *
         * 5,ForkJoinPool:ForkJoinTask的执行发生在ForkJoinPool类中,该类还管理任务的执行,所以,为了执行ForkJoinTask,首先必须有ForkJoinPool对象,从JDK8开始,有两种方式可获得ForkJoinPool对象
         * 首先,可以使用ForkJoinPool构造函数显示创建一个,其次,可以使用所谓的公共池,JDK8新增的公共池是一个静态的ForkJoinPool对象,可供程序员使用
         *     ForkJoinPool()
         *     ForkJoinPool(int pLevel)
         *     第一个构造函数创建默认池,支持的并行级别等于系统中可用处理器的数量,第二个构造函数,指定并行级别,值必须在0和处理器数量之间
         *     记住,并行级别没有限制线程池能够管理的任务的数量,ForkJoinPool能够管理大大超过其并行级别的任务数,此外,并行级别只是目标,而不是要确保的结果
         *
         *   创建好ForkjoinPool实例后,就可以通过大量不同的方法开始执行任务,第一个任务通常被认为是主任务,通常,由主任务开始其他的由池管理的子任务,开始主任务的一种常用方式是对ForkJoinPool实例调用
         * invoke()方法,这个方法开始由task指定的任务,并且返回任务结果,并且返回任务结果,这意味着调用代码会进行等待,直到invoke()方法返回为止
         *     <T> T invoke(ForkJoinTask<T> task)
         *   为了开始不用等待完成的任务,可以使用execute()方法,这个方法也是FookJoinPool定义的,下面是该方法的一种形式
         *     void execute(ForkJoinTask<?> task)
         *     void execute(Runnable task)
         *     对于这个方法,会开始由task指定的任务,但是调用代码不会等待任务完成,相反,调用代码将继续异步执行,execute(Runnable task)允许指定Runnable对象,而不是ForkJoinTask任务,
         *   这种方式桥接了java传统的多线程编程方式和新的Fork/Join框架
         *
         *
         *   如果没有显示的创建池,就会自动使用公共池,公共池提供了默认并行级别,使用系统属性可以设置默认的并行级别,通过ForkJoinPool定义的commonPool()方法,
         * 可以获得对公共池的引用,尽管有时候没有必要这么做
         *     static ForkJoinPool commonPool()
         *
         * 6,(重点)使用公共池(公共池也是ForkJoinPool的一个对象)开启任务有两种级别方法,首先通过commonPool()方法获得对公共池的引用,然后使用该引用来调用前面描述过的invoke()或execute()方法,
         * 其次,在计算环境外,对任务调用ForkJoinTask方法,如fork()或invoke()方法,在这种情况下,将自动使用公共池,
         * 换句话说,如果任务没有在ForkJoinPool内运行,则fork()或invoke()将使用公共池启动任务
         *
         * 7,ForkJoinPool使用一种称为工作挪用(work stealing)的方式来管理线程的执行.每个工作者线程维护一个任务队列,如果某个工作者线程的任务队列是空的,这个工作者线程将从其他工作者线程取得任务(提高效率,线程之间负载均衡)
         *   另外一点(重点):ForkJoinPool使用守护线程(daemon thread),当主线程结束时,守护线程也会结束,当所有用户线程都终止时,守护线程自动终止,因此,不需要显示的关闭ForkJoinPool,可以通过shutdown()方法关闭ForkJoinPool,shutdown()方法对公共池不起作用
         *
         * 8,分而治之的策略:Fork/Join框架会用到基于递归的分而治之的策略,这就是将ForkJoinTask的两个子类称为RecursiveAction和RecursiveTask的原因
         *     分而治之:将任务递归的划分成更小的子任务,直到子任务足够小,从而能够被连续地处理为止.
         *     分而治之的使用关键之一,在于正确地选择临界点,根据经验,任务应当在100-10000个计算步骤之间的某个位置执行,每个计算步骤时间越长,临界点应当越小,计算步骤时间越短,临界点应当越大
         *
         * 9,取消任务(ForkJoinTask定义)
         *     boolean cancel(boolean interruptOK)
         *   是否已经取消
         *     final boolean isCancelled()
         *   查看任务状态
         *     final boolean isCompletedNormally()  (完成时没有抛异常或者cancel()返回true)
         *   查看任务状态
         *     final boolean isCompletedAbnormally() (完成时抛异常或者cancel()返回true)
         *   重启任务
         *     void reinitialize() (这个方法会重置任务状态,但是对已经被修改的数据不会还原)
         *   是否在任务内部执行,(因为invokeAll()和fork()只能在ForkJoinTask中调用)
         *     inForkJoinPool()
         *   将Runnable或Callable对象转换成ForkJoinTask对象,ForkJoinTask定义
         *     adapt()
         */


    }

}
