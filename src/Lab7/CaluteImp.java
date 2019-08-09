package Lab7;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CaluteImp extends UnicastRemoteObject implements Calute {

    public CaluteImp()throws RemoteException{

    }
    @Override
    public float add(float a, float b) throws RemoteException {
        return a+b;
    }

    @Override
    public float jian(float a, float b) throws RemoteException {
        return a-b;
    }

    @Override
    public float cheng(float a, float b) throws RemoteException {
        return a*b;
    }

    @Override
    public float chu(float a, float b) throws RemoteException {
        if(b==0){
            return 0;
        }
        return a/b;
    }

    @Override
    public float fib(int a) throws RemoteException {

        return Recursion(a);
    }

    @Override
    public float jiecheng(float a) throws RemoteException {
        if(a == 1) return 1;
        return a*jiecheng(a-1);
    }

    @Override
    public float mi(float a, float b) throws RemoteException {
        if(b == 1) return a;
        return a*mi(a,b-1);
    }

    private int Recursion(int n){

        if(n==1){
            return 0;
        }

        if(n==2){
            return 1;
        }
        return Recursion(n-1)+Recursion(n-2);
    }
}
