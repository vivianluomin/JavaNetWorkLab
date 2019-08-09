package LeetCode;

public class GasStation {

    public int canCompleteCircuit(int[] gas, int[] cost) {

        for(int i = 0;i<gas.length;i++){
            if(canTravel(i,gas,cost)){
                return i;
            }
        }


        return -1;
    }

    public boolean canTravel(int start,int[] gas,int cost[]){

        int i = start;
        int leftGas = gas[start];
        do {
            if(leftGas-cost[i]>=0){
                leftGas = leftGas-cost[i];
                i++;
                i = i%gas.length;
                leftGas +=gas[i];
            }else {
                return false;
            }
        }while (i!=start);


        return true;
    }
}
