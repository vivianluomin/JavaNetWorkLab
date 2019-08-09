package Lab3;

import javax.print.attribute.standard.NumberUp;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 12345;//监听的端口号
    private static final String FILE = "aa.txt";

    public static void main(String[] args) {
        System.out.println("服务器启动...\n");
        Server server = new Server();
        server.init();
    }

    public void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                // 一旦有堵塞, 则表示服务器与客户端获得了连接
                Socket client = serverSocket.accept();
                // 处理这次连接
                new HandlerThread(client);
            }
        } catch (Exception e) {
            System.out.println("服务器异常: " + e.getMessage());
        }
    }

    private class DonwnLoad implements Runnable{
        private Socket socket;
        public DonwnLoad(Socket client) {
            socket = client;
            new Thread(this).start();
        }
        @Override
        public void run() {
            try {
                File file = new File(FILE);
                FileInputStream inputStream = new FileInputStream(file);
                InputStreamReader read = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(read);
                OutputStream outputStream = socket.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(outputStream));
                String s;
                int i = 1;
                while ((s = reader.readLine())!= null){
                    s+=","+i;
                    writer.write(s,0,s.length());
                }
                writer.flush();
            }catch (FileNotFoundException e){
                e.printStackTrace();
                System.out.println("文件错误");
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    private class HandlerThread implements Runnable {
        private Socket socket;
        public HandlerThread(Socket client) {
            socket = client;
            new Thread(this).start();
        }

        public void run() {
            try {
                // 读取客户端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                // 处理客户端数据
                System.out.println("客户端发过来的内容:" + clientInputStr);

                String ret = input.readUTF();
                // 如接收到 "OK" 则断开连接
                if ("OK".equals(ret)) {
                    // 向客户端回复信息
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF("OK");
                    out.close();

                }else{
                    // 向客户端回复信息
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    System.out.print("请输入:\t");
                    // 发送键盘输入的一行
                    String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
                    out.writeUTF(s);
                    out.close();

                }

                input.close();
            } catch (Exception e) {
                System.out.println("服务器 run 异常: " + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("服务端 finally 异常:" + e.getMessage());
                    }
                }
            }
        }
    }
}
