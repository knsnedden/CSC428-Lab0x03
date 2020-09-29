package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] arr;
        int size = 10;

        arr = generateList(size);
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
}
