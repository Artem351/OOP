package ru.nsu.pisarev;

/**
 * Sample class to simulate 1.1 task functionality
 */
public final class MyHeapsort {
    public static void heapsort(int[] arr){
        heapsortWrap(arr, arr.length);
    }

    private static int leftChild(int parentNode, int lenArr){
        int leftChildNode =  parentNode * 2 + 1;
        return leftChildNode < lenArr ? leftChildNode : -1;
    }

    private static int rightChild(int parentNode, int lenArr){
        int rightChildNode =  parentNode * 2 + 2;
        return rightChildNode < lenArr ? rightChildNode : -1;
    }

    private static boolean nodeExists(int lenArr, int node){
        return (node!=-1 && node<lenArr);
    }

    private static void siftDown(int[] arr, int lenArr, int elem){
        int biggest = elem;
        int cmpWith = leftChild(elem,lenArr);
        if (nodeExists(lenArr,cmpWith) && arr[elem]<arr[cmpWith]){
            biggest = cmpWith;
        }
        cmpWith = rightChild(elem, lenArr);
        if (nodeExists(lenArr,cmpWith) && arr[biggest]<arr[cmpWith]){
            biggest = cmpWith;
        }
        if (biggest !=elem){
            int temp = arr[elem];
            arr[elem] =arr[biggest];
            arr[biggest] =temp;
            siftDown(arr,lenArr, biggest);
        }
    }

    private static void buildHeap(int []arr, int start, int end){
        for (int i = end-1; i >= start; i--) {
            siftDown(arr, end, i);
        }
    }

    private static void heapsortWrap(int[] arr, int size){
        buildHeap(arr,0,size);
        int last = size-1;
        while(last>0){
            int temp = arr[0];
            arr[0] = arr[last];
            arr[last]= temp;
            siftDown(arr,last,0);
            last--;
        }
    }

    private MyHeapsort() {
        //Only static methods
    }
}