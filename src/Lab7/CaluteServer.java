package Lab7;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class CaluteServer {

    public static String RMI_URL = "rmi://localhost:8888/RHello";

    public static void main(String[] args) {
        try {
            CaluteImp queryStatus = new CaluteImp();
            // 注册表创建
            LocateRegistry.createRegistry(8888);
            // 绑定远端对象到名字
            Naming.rebind(RMI_URL, queryStatus);
            System.out.println(">>>>>INFO:远程RMIQueryStatus对象绑定成功！");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
