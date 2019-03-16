package Lab1;

import java.util.concurrent.CountDownLatch;

public class ThreadQuick {

    public static void main(String[] args){
        int[] array = {3,2,5,8,4,7,6,9};
        QuickSort(array,0,array.length-1);

        for(int i = 0;i<array.length;i++){
            System.out.print(array[i]+"  ");
        }
    }

    public static void QuickSort(int[] array,int start,int end){
        if(start>end){
            return;
        }
        int p = getLocation(array,start,end);


        new Thread(new Runnable() {
            @Override
            public void run() {
                QuickSort(array,start,p-1);
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                QuickSort(array,p+1,end);
                //latch.countDown();
            }
        }).start();


    }

    public static int getLocation(int[] array,int start,int end){

        int k = array[start];
        int j = start;
        for(int i = start+1;i<=end;i++){
            if(array[i]<k){
                int temp = array[j+1];
                array[j+1] = array[i];
                array[i] = temp;
                j++;
            }
        }

        array[start] = array[j];
        array[j] = k;

        return j;
    }
}
