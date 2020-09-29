package com.company;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
public class Main {

    public static void main(String[] args) {
        int[] arr;
        int size = 20;
        ArrayList<int[]> fin = new ArrayList<int[]>();

        arr = generateList(size);
        Arrays.sort(arr);

        fin = bruteForce(arr);

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
}
