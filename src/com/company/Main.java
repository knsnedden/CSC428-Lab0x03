package com.company;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;


public class Main {

    public static void main(String[] args) {
        timeTrials();
    }

    public static int[] generateList(int N){
        Random randNum = new Random();
        int[] arr = new int[N];
        int x;

        for (int i = 0; i < N; ++i){
            x = randNum.nextInt((2*N)-(-2*N)) - (2*N);
            arr[i] = x;
        }

        return arr;
    }

    public static ArrayList<int[]> bruteForce(int[] arr){
        ArrayList<int[]> result = new ArrayList<int[]>();

        for (int i = 0; i < arr.length; ++i){
            for (int j = i + 1; j < arr.length; ++j){
                for (int k = j + 1; k < arr.length; ++k){
                    if (arr[i] + arr[j] + arr[k] == 0){
                        if (arr[i] != arr[j] && arr[i] != arr[k] && arr[j] != arr[k]){
                            int[] hold = new int[3];
                            hold[0] = arr[i];
                            hold[1] = arr[j];
                            hold[2] = arr[k];
                            result.add(hold);
                        }
                    }

                }
            }
        }
        return result;
    }

    public static ArrayList<int[]> fasterApproach(int[] arr){
        ArrayList<int[]> result = new ArrayList<int[]>();
        Arrays.sort(arr);

        // loop through the list to find every possible pair
        for (int i = 0; i < arr.length; ++i){
            for (int j = i+1; j < arr.length; ++j){
                if (arr[i] != arr[j]){
                    int x = (arr[i] + arr[j])*-1;
                    int index = binarySearch(arr,x,j);
                    if (index != -1 && arr[index] != arr[i] && arr[index] != arr[j]) {
                        int[] hold = new int[3];
                        hold[0] = arr[i];
                        hold[1] = arr[j];
                        hold[2] = arr[index];
                        result.add(hold.clone());
                    }
                }
            }
        }

        return result;
    }

    public static int binarySearch(int[] arr, int toFind, int low){
        int high = arr.length - 1;
        int m;

        while (low <= high){
            m = (low + high)/2;
            if (toFind > arr[m]){
                low = m + 1;
            }
            else if (toFind < arr[m]){
                high = m - 1;
            }
            else{
                return m;
            }
        }

        return -1;
    }

    public static ArrayList<int[]> fastestApproach(int[] arr){
        ArrayList<int[]> result = new ArrayList<int[]>();
        Arrays.sort(arr);

        for (int i = 0; i < arr.length - 2; ++i){
            int x = arr[i];
            int a = i + 1; // begin moving up at the element above i
            int b = arr.length - 1; // begin moving down at the last element
            while(a < b){
                if (arr[i] + arr[a] + arr[b] == 0){
                    int[] hold = new int[3];
                    hold[0] = arr[i];
                    hold[1] = arr[a];
                    hold[2] = arr[b];
                    result.add(hold.clone());

                    do{
                        a++;
                    }while (arr[a] == arr[a-1] && a < arr.length);
                    do{
                        b--;
                    }while (arr[b] == arr[b+1] && b > 0);
                }
                if(arr[i] + arr[a] + arr[b] > 0){
                    do{
                        b--;
                    }while (arr[b] == arr[b+1]);
                }else if(arr[i] + arr[a] +arr[b] < 0){
                    do{
                        a++;
                    }while (arr[a] == arr[a-1] && a < arr.length);
                }
            }
        }
        return result;
    }

    public static void timeTrials(){
        long difference = 0, maxTime = (long)Math.pow(2,30), timeBefore = 0, timeAfter = 0;
        long prevBTime = 0, prevFasterTime = 0, prevFastestTime = 0;
        float pdr = 0, dr = 0;
        int N = 4, prev_N = 0;
        boolean keepGoing = true;

        System.out.println("          Brute 3Sum                                                   Faster 3Sum                                                  Fastest 3Sum");
        System.out.println("   N    |       Time        |  Doubling Ratio  | Exp. Doubling Ratio |       Time        |  Doubling Ratio  | Exp. Doubling Ratio |       Time        |  Doubling Ratio  | Exp. Doubling Ratio |");
        while (keepGoing){
            System.out.printf("%7d |", N);
            int[] arr = new int[N];
            arr = generateList(N);
            if (prevBTime < maxTime) {
                timeBefore = getCpuTime();
                bruteForce(arr);
                timeAfter = getCpuTime();
                difference = timeAfter - timeBefore;
                System.out.printf("%18d |", difference);
                if (prevBTime == 0) {
                    System.out.printf("        na        |          na         |");
                } else {
                    pdr = (float) (Math.pow(N, 3) / Math.pow(prev_N, 3));
                    dr = (float) difference / (float) prevBTime;
                    System.out.printf("%17.3f |%20.0f |", dr, pdr);
                }
                prevBTime = difference;
            }
            else{
                System.out.printf("        --         |        --        |         --          |");
            }

            if (prevFasterTime < maxTime){
                timeBefore = getCpuTime();
                fasterApproach(arr);
                timeAfter = getCpuTime();
                difference = timeAfter - timeBefore;
                System.out.printf("%18d |", difference);
                if (prevFasterTime == 0){
                    System.out.printf("        na        |          na         |");
                }
                else{
                    pdr = (float) ((Math.pow(N,2)*Math.log(N))/(Math.pow(prev_N,2)*Math.log(prev_N)));
                    dr = (float) difference / (float) prevFasterTime;
                    System.out.printf("%17.3f |%20.0f |", dr, pdr);
                }
                prevFasterTime = difference;
            }
            else{
                System.out.printf("        --         |        --        |         --          |");
            }

            if (prevFastestTime< maxTime) {
                timeBefore = getCpuTime();
                fastestApproach(arr);
                timeAfter = getCpuTime();
                difference = timeAfter - timeBefore;
                System.out.printf("%18d |", difference);
                if (prevFastestTime == 0) {
                    System.out.printf("        na        |          na         |");
                } else {
                    pdr = (float) (Math.pow(N, 2) / Math.pow(prev_N, 2));
                    dr = (float) difference / (float) prevFastestTime;
                    System.out.printf("%17.3f |%20.0f |", dr, pdr);
                }
                prevFastestTime = difference;
            }
            else{
                System.out.printf("        --         |        --        |         --          |");
            }

            System.out.println();
            prev_N = N;
            N = N*2;
            if (prevBTime >= maxTime && prevFasterTime >= maxTime && prevFastestTime >= maxTime){
                keepGoing = false;
            }
        }
    }


    public static long getCpuTime(){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }
}
