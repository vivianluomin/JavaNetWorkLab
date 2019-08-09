package Lab5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Client {

    private static int flag = 0;

    private static int BLOCK = 4096;

    private static ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);

    private static ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
    //服务器端地址/
    private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(
            "localhost", 7788);

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        // 打开socket通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置为非阻塞方式
        socketChannel.configureBlocking(false);
        // 打开选择器
        Selector selector = Selector.open();
        // 注册连接服务端socket动作
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        // 连接
        socketChannel.connect(SERVER_ADDRESS);
        HashMap<String,Integer> data = new HashMap<>();

        Set<SelectionKey> selectionKeys;
        Iterator<SelectionKey> iterator;
        SelectionKey selectionKey;
        SocketChannel client;
        String receiveText;
        String sendText;
        int count = 0;

        while (true) {

            selector.select();
            //返回此选择器的已选择键集。
            selectionKeys = selector.selectedKeys();
            iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                selectionKey = iterator.next();
                if (selectionKey.isConnectable()) {
                    System.out.println("client connect");
                    client = (SocketChannel) selectionKey.channel();
                    // 判断此通道上是否正在进行连接操作。
                    // 完成套接字通道的连接过程。
                    if (client.isConnectionPending()) {
                        client.finishConnect();
                        System.out.println("完成连接!");
                    }
                    client.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    client = (SocketChannel) selectionKey.channel();
                    //将缓冲区清空以备下次读取
                    receivebuffer.clear();
                    //读取服务器发送来的数据到缓冲区中
                    count = client.read(receivebuffer);
                    if (count > 0) {
                        receiveText = new String(receivebuffer.array(), 0, count);
                       String[] aa = receiveText.split(",");
                       data.put(aa[0],Integer.valueOf(aa[1]));
                        client.register(selector, SelectionKey.OP_WRITE);
                    }

                }
            }
            selectionKeys.clear();
        }
    }
}