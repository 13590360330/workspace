package other;

import io.netty.util.Recycler;

public class RecyclerDemo {
    //匿名内部类,这个变量用来回收或再利用User对象
    private static final Recycler<User> RECYCLER = new Recycler<User>() {
        //当回收站中没有User对象时,通过newObject创建User对象
        @Override
        protected User newObject(Handle<User> handle) {
            return new User( handle );
        }
    };

    static class User {

        private final Recycler.Handle<User> handle;

        public User(Recycler.Handle<User> handle) {
            this.handle = handle;
        }

        //将User回收到Recycler中
        public void recycle() {
            handle.recycle( this );
        }
    }

    public static void main(String[] args) {
        User user1 = RECYCLER.get();
        user1.recycle();
        User user2 = RECYCLER.get();
        user2.recycle();
        System.out.println( user1 == user2 );
    }
}
