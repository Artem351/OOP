package ru.nsu.pisarev;



public class Main {
    public static void main(String[] args) {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.update("one", 1.0);
        System.out.println(hashTable.get("one")); // 1.0


        for (java.util.Map.Entry<String, Number> e : hashTable) {
            System.out.println(e);
        }


        HashTable<String, Number> other = new HashTable<>();
        other.put("one", 1.0);
        System.out.println("equal: " + hashTable.equals(other));
    }
}

