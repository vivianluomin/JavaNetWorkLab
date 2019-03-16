package Lab1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadArray {

    public static void main(String[] args){

        int[] array = new int[100];
        int sum = 0;
        for(int i = 0;i<100;i++){
            array[i] = i*3;
            sum+=i*3;
        }
        System.out.println(sum);
        System.out.println("--------------");
        System.out.println(threadArray(array));

    }

    public static int threadArray(int[] array){
        int sum = 0;
        int cores = Runtime.getRuntime().availableProcessors();
        //int cores = 4;
        System.out.println("cores: "+cores);
        ExecutorService service =  Executors.newFixedThreadPool(cores);
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(service);

        int n = array.length;
        int step = n/cores;
        for(int i = 0;i<cores;i++){
            int start = step*i;
            int endt = step*i+step;
            if(endt>n){
                endt = n;
            }
            subArray subArray = new subArray(array,start,endt);
            if(!service.isShutdown()){
                completionService.submit(subArray);
            }
        }

        try {
            for(int i =0;i<cores;i++){
                int t = completionService.take().get();
                System.out.println("t:  "+t);
                sum+=t;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return sum;
    }
}

class subArray implements Callable<Integer>{

    int[] sub;
    int start;
    int end;

    public subArray(int[] sub,int start,int end) {
        this.sub = sub;
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {

        int sum = 0;

        for(int i =start;i<end;i++){
            sum+=sub[i];
        }
        return sum;
    }
}
