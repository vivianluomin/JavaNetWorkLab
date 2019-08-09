package LeetCode;

import java.util.ArrayDeque;
import java.util.Queue;

public class palindrome {

    public static void main(String[] args){

        palindrome p = new palindrome();
        System.out.println(p.minCut("aabab"));

    }

    public int minCut(String s) {

        int[] dp = new int[s.length()];
        for(int i = 0 ;i<s.length();i++){
            if(isPalindrome(s.substring(0,i+1))){
                dp[i] = 0;
                continue;
            }else {
                dp[i] = i;
            }

            for(int j = 1;j<=i;j++){
                if(isPalindrome(s.substring(j,i+1))){
                    dp[i] = Math.min(dp[i],dp[j-1]+1);
                }else {

                    dp[i] = Math.min(dp[i],dp[j-1]+i-j+1);
                }
            }
        }

        return dp[s.length()-1];
    }



    public boolean isPalindrome(String s){
        char[] ss = s.toCharArray();
        int i = 0;
        int j = ss.length-1;
        while (i<=j){
           if(ss[i]!=ss[j]){
               return false;
           }
           i++;
           j--;
        }

        return true;
    }
}
