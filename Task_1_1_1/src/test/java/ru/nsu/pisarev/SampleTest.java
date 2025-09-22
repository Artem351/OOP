package ru.nsu.pisarev;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Random;


class SampleTest {

    @Test
    void checkSortedArr() {
        int[] sortedArr = {1,2,3,4,5,6,100};
        MyHeapsort.heapsort(sortedArr);
        int lenArr = sortedArr.length;

        int[] checkSortedArr = {1,2,3,4,5,6,100};
        for (int i=0;i<lenArr;i++)
            if (sortedArr[i] != checkSortedArr[i])
                fail();
        assertTrue(true);
    }
    @Test
    void checkReverseArr() {
        int[] sortedArr = {100,6,5,4,3,2,1};
        MyHeapsort.heapsort(sortedArr);
        int lenArr = sortedArr.length;

        int[] checkSortedArr = {1,2,3,4,5,6,100};
        for (int i=0;i<lenArr;i++)
            if (sortedArr[i] != checkSortedArr[i])
                fail();
        assertTrue(true);
    }

    @Test
    void checkChessArr() {
        int[] sortedArr = {1,100,2,99,3,98,4};
        MyHeapsort.heapsort(sortedArr);
        int lenArr = sortedArr.length;

        int[] checkSortedArr = {1,2,3,4,98,99,100};
        for (int i=0;i<lenArr;i++)
            if (sortedArr[i] != checkSortedArr[i])
                fail();
        assertTrue(true);
    }
    @Test
    void checkEvenLengthArr() {
        int[] sortedArr = {1,100,2,99,3,98};
        MyHeapsort.heapsort(sortedArr);
        int lenArr = sortedArr.length;

        int[] checkSortedArr = {1,2,3,98,99,100};
        for (int i=0;i<lenArr;i++)
            if (sortedArr[i] != checkSortedArr[i])
                fail();
        assertTrue(true);
    }
    @Test
    void checkLargeArr() {
        int size = 1_000_000;
        int[] sortedArr = new int[size];
        Random random =new Random();
        for(int i = 0;i<size;i++)
        {
            sortedArr[i] = random.nextInt(10_000_000);
        }
        MyHeapsort.heapsort(sortedArr);
        int lenArr = sortedArr.length;

        for (int i=0;i<lenArr-1;i++)
            if (sortedArr[i] >sortedArr[i+1]){
                System.out.println("error on "+i+"symbol.\n Need: "+sortedArr[i+1]+",but it is:"+sortedArr[i]);
                fail();
            }
        assertTrue(true);
    }
    @Test
    void checkCloneArr() {
        int size = 1_000_000;
        int[] sortedArr = new int[size];
        Random random =new Random();
        for(int i = 0;i<size;i++)
        {
            sortedArr[i] = random.nextInt(100);
        }
        MyHeapsort.heapsort(sortedArr);
        int lenArr = sortedArr.length;

        for (int i=0;i<lenArr-1;i++)
            if (sortedArr[i] >sortedArr[i+1]){
                System.out.println("error on "+i+"symbol.\n Need: "+sortedArr[i+1]+",but it is:"+sortedArr[i]);
                fail();
            }
        assertTrue(true);
    }
}