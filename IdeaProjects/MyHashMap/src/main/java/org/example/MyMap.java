package org.example;

public interface MyMap<K,V> {

    int size();  // размер коллекции

    boolean isEmpty(); // пустая ли коллекция

    Object get(Object key); // получить элемент по ключу

    Object put(Object key, Object value); // добавить элемент в коллекцию

    Object remove(Object key); // удалить элемент коллекции по ключу

    void clear(); // очистить всю коллекцию

    interface Entry<K,V> {
        K getKey();
        V getValue();
    }
}
