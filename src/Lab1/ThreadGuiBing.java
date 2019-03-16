package Lab1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadGuiBing {
    public static int cores = Runtime.getRuntime().availableProcessors();
    public static ExecutorService service = Executors.newFixedThreadPool(cores);
    public static ExecutorCompletionService<Integer> completionService
            = new ExecutorCompletionService<>(service);

    public static void main(String[] args){

        int[] array = {3,2,5,8,4,7,6,9};
        GuiBing(array);

        for(int i = 0;i<array.length;i++){
            System.out.print(array[i]+"   ");
        }

    }

    public static void GuiBing(int[] array){
        int n = array.length;
        MergeSort(array,0,n-1);

    }

    public static void MergeSort(int[] array,int start,int end){
        if(start>=end) return;
        CountDownLatch latch = new CountDownLatch(2);
        int mid = (start+end)/2;

        new Thread(new Runnable() {
            @Override
            public void run() {
                MergeSort(array,start,mid);
                latch.countDown();
            }
        }).start();



        new Thread(new Runnable() {
            @Override
            public void run() {
                MergeSort(array,mid+1,end);
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
            return;
        }

        merge(array,start,mid,end);

    }

    public static void merge(int[] array,int start,int mid,int end){

        int[] a = new int[end-start+1];

        int i = start;
        int j = mid+1;
        int t = 0;
        while (i<=mid || j<=end){

            if(j>end || (i<=mid && array[i]<=array[j])){
                a[t] = array[i];
                i++;
            }else {
                a[t] = array[j];
                j++;
            }

            t++;
        }

        t = start;
        for(int k = 0;k<end-start+1;k++){
            array[t] = a[k];
            t++;

        }


    }
}
