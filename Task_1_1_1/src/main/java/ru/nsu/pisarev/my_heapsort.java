package ru.nsu.pisarev;

/**
 * Sample class to simulate 1.1 task functionality
 */
public class my_heapsort {

    public static int parentNode(int childNode){
        return childNode != 0 ? childNode % 2 == 0 ? (childNode / 2 - 1) : (childNode / 2) : -1;
    }
    public static int leftChild(int parentNode, int len_arr){
        int leftChildNode =  parentNode * 2 + 1;
        return leftChildNode < len_arr ? leftChildNode : -1;
    }
    public static int rightChild(int parentNode, int len_arr){
        int rightChildNode =  parentNode * 2 + 2;
        return rightChildNode < len_arr ? rightChildNode : -1;
    }
    public static int brotherNode(int child, int len_arr){
        return child % 2 == 0 ? (child > 0 ? child - 1 : -1) : (child < len_arr - 1 ? child + 1 : -1);
    }
    public static boolean nodeExists(int len_arr,int node){
        return (node!=-1 && node<len_arr);
    }
    public static void siftDown(int[] arr,int len_arr,int elem){
        int biggest = elem;
        int cmpWith = leftChild(elem,len_arr);
        if (nodeExists(len_arr,cmpWith) && arr[elem]<arr[cmpWith]){
            biggest = cmpWith;
        }
        cmpWith = rightChild(elem, len_arr);
        if (nodeExists(len_arr,cmpWith) && arr[biggest]<arr[cmpWith]){
            biggest = cmpWith;
        }
        if (biggest !=elem){
            int temp = arr[elem];
            arr[elem] =arr[biggest];
            arr[biggest] =temp;
            siftDown(arr,len_arr, biggest);
        }
    }
    public static void buildHeap(int []arr, int start, int end){

        for (int i = end-1; i >= start; i--) {
            siftDown(arr, end, i);
        }
        int p = 1;
    }
    public static void heapsort_wrap(int[] arr,int size){
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
    public static void heapsort(int[] arr){
        heapsort_wrap(arr,arr.length);
    }
}