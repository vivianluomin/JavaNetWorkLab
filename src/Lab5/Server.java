package Lab5;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private int flag = 0;
    //缓冲区大小/
    private int BLOCK = 4096;

    private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);

    private ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
    private Selector selector;


    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        int port = 7788;
        Server server = new Server(port);
        server.listen();
    }

    public Server(int port) throws IOException {
        // 打开服务器套接字通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 服务器配置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 检索与此通道关联的服务器套接字
        ServerSocket serverSocket = serverSocketChannel.socket();
        // 进行服务的绑定
        serverSocket.bind(new InetSocketAddress(port));
        // 通过open()方法找到Selector
        selector = Selector.open();
        // 注册到selector，等待连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server Start----7788:");
    }


    // 监听
    private void listen() throws IOException {
        while (true) {
            // 选择一组键，并且相应的通道已经打开
            selector.select();
            // 返回此选择器的已选择键集。
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                handleKey(selectionKey);
            }
        }
    }

    // 处理请求
    private void handleKey(SelectionKey selectionKey) throws IOException {
        // 接受请求
        ServerSocketChannel server = null;
        SocketChannel client = null;
        String receiveText;
        String sendText;
        int count = 0;
        // 测试此键的通道是否已准备好接受新的套接字连接。
        if (selectionKey.isAcceptable()) {
            // 返回为之创建此键的通道。
            server = (ServerSocketChannel) selectionKey.channel();
            // 接受到此通道套接字的连接。
            // 此方法返回的套接字通道（如果有）将处于阻塞模式。
            client = server.accept();
            // 配置为非阻塞
            client.configureBlocking(false);
            // 注册到selector，等待连接
            client.register(selector, SelectionKey.OP_READ);
        }  else if (selectionKey.isWritable()) {
            File file = new File("aa.txt");
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader read = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(read);

            //将缓冲区清空以备下次写入
            sendbuffer.clear();
            // 返回为之创建此键的通道。
            client = (SocketChannel) selectionKey.channel();

            String s;
            int i = 1;
            while ((s = reader.readLine())!= null){
                s+=","+i;
                sendbuffer.put(s.getBytes());
                sendbuffer.flip();
                client.write(sendbuffer);
                client.register(selector, SelectionKey.OP_READ);
            }
        }
    }


}