package org.example;

public class Main {
    public static void main(String[] args) {
        MyMap map = new MyHashMap();
        map.put("Артем", 31);
        map.put("Ленар", 29);
        System.out.println(map.size());
        map.remove("Артем");
        System.out.println(map.size());
        System.out.println(map.get("Ленар"));
        map.clear();

    }
}
