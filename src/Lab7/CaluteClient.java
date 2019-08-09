package Lab7;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CaluteClient {

    public static void main(String[] args) {
        try {
            Calute queryStatus = (Calute) Naming.lookup(CaluteServer.RMI_URL);
            // 调用远程方法，该调用如同调用本地方法
            Scanner in = new Scanner(System.in);
            int a = in.nextInt();
            int b = in.nextInt();
            String c = in.next();
            float re = 0;
            switch (c){
                case "+":
                    re = queryStatus.add(a,b);
                    break;
                case "-":
                    re = queryStatus.jian(a,b);
                    break;
                case "*":
                    re = queryStatus.cheng(a,b);
                    break;
                case "/":
                    re = queryStatus.chu(a,b);
                    break;
                case "fib":
                    re = queryStatus.fib(a);
                    break;
                case "^":
                    re = queryStatus.mi(a,b);
                    break;
                case "n":
                    re = queryStatus.jiecheng(a);
                    break;
            }
            System.out.println(re);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
