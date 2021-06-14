package demo;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO Selector实例
 */
public class SelectorDemo {

    public static void main(String[] args) throws IOException {

        //创建一个Selector(NIO多路复用类)
        Selector selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        //将建立的连接设置为非阻塞
        channel.configureBlocking( false );

        //为了将Channel和Selector配合使用，必须将channel注册到selector上
        //register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。可以监听四种不同类型的事件：
        //1,Connect
        //2,Accept
        //3,Read
        //4,Write
        //通道触发了一个事件意思是该事件已经就绪。所以，某个channel成功连接到另一个服务器称为“连接就绪”。
        //一个server socket channel准备好接收新进入的连接称为“接收就绪”。一个有数据可读的通道可以说是“读就绪”。等待写数据的通道可以说是“写就绪”。
        //对不止一种事件感兴趣
        int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        //当向Selector注册Channel时，register()方法会返回一个SelectionKey对象。这个对象包含了一些你感兴趣的属性：
        //key1代表了注册到该Selector的通道。可以通过SelectionKey的selectedKeySet()方法访问这些对象,SelectionKey是包装了channel的类
        SelectionKey key1 = channel.register( selector, interestSet );

        //Selector可以注册多个通道,可以视为一个channel数组
        //轮询
        while (true) {
            //select()至少有一个通道在你注册的事件上就绪了。(可以理解为某通道的注册事件达成),返回值表示有多少通道已经就绪
            int readyChannels = selector.select();
            //阻塞
            if (readyChannels == 0) continue;
            //获取selector中OP_READ事件的channel集合(Set)selectedKeys
            Set selectedKeys = selector.selectedKeys();

            //这个循环遍历已选择键集中的每个键，并检测各个键所对应的通道的就绪事件。
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                ServerSocketChannel server = null;
                SocketChannel client = null;
                if (key.isAcceptable()) {
                    // 返回为之创建此键的通道。
                    server = (ServerSocketChannel) key.channel();
                    // 接受到此通道套接字的连接。
                    // 此方法返回的套接字通道（如果有）将处于阻塞模式。
                    client = server.accept();
                    // 配置为非阻塞
                    client.configureBlocking( false );
                    // 注册到selector，等待连接
                    client.register( selector, SelectionKey.OP_READ );
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }
}
