package Lab7;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calute extends Remote {

    float add(float a,float b)throws RemoteException;
    float jian(float a,float b)throws RemoteException;
    float cheng(float a,float b)throws RemoteException;
    float chu(float a,float b)throws RemoteException;
    float fib(int a)throws RemoteException;
    float jiecheng(float a)throws RemoteException;
    float mi(float a,float b)throws RemoteException;

}
