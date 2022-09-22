package org.example;


public class MyHashMap implements MyMap {

    private static final int DEFAULT_INITIAL_CAPACITY = 16; // начальная емкость

    private static final float DEFAULT_LOAD_FACTOR = 0.75F; // коэффициент загрузки

    private int size = 0;

    Node[] table = new Node[DEFAULT_INITIAL_CAPACITY]; // создаем массив из 16 узлов

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Object get(Object key) {
        int hashValue = hash(key);

        int i = indexFor(hashValue, table.length);
        for (Node node = table[i]; node != null; node = node.next) {
            if (node.key.equals(key) && hashValue == node.hash) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        int hashValue = hash(key);

        int i = indexFor(hashValue, table.length);
        // если в i есть данные и ключ тот же, перезаписать
        for (Node node = table[i]; node != null; node = node.next) {
            Object k;
            if (node.hash == hashValue && ((k = node.key) == key || key.equals(k))) {
                Object oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }

        addEntry(key, value, hashValue, i);
        return null;

    }

    @Override
    public Object remove(Object key) {
        int hashValue = hash(key);

        int i = indexFor(hashValue, table.length);
        for (Node node = table[i]; node != null; node = node.next) {
            if (node.key.equals(key) && hashValue == node.hash) {
                size--;
                return node = null;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        if (table != null && size > 0) {
            size = 0;
            for (int i = 0; i < table.length; i++) {
                table[i] = null;
            }
        }
    }

    public void addEntry(Object key, Object value, int hashValue, int i) {
        // если согласованная длина массива превышена, расширяем емкость
        if (++size >= table.length * DEFAULT_LOAD_FACTOR) {
            Node[] newTable = new Node[table.length << 1]; // создаем массив вдвое больше старого
            transfer(table, newTable); // копируем старый массив в новый
            table = newTable; // теперь переменная table ссылается на новый массив
        }
        // получить данные в i
        Node eNode = table[i];
        // Добавить узел, указать узел рядом с предыдущим узлом
        table[i] = new Node(hashValue, key, value, eNode);
    }

    public void transfer(Node[] src, Node[] newTable) {// src ссылается на старый массив Entry
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {// пройти старый массив Entry
            Node e = src[j]; // Получить каждый элемент старого массива Entry
            if (e != null) {
                src[j] = null; // Освободить ссылку на объект старого массива Entry (после цикла for старый массив Entry больше не ссылается ни на какие объекты)
                do {
                    Node next = e.next;
                    int i = indexFor(e.hash, newCapacity); // пересчитать положение каждого элемента в массиве
                    e.next = newTable[i];
                    newTable[i] = e; // поместить элемент в массив
                    e = next; // доступ к элементам в следующей цепочке ввода
                } while (e != null);
            }
        }
    }


    // получить позицию вставки элемента
    public int indexFor(int hashValue, int length) {
        return hashValue % length;
    }

    // получить значение хеш-кода в соответствии с хеш-кодом объекта Object
    public int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static class Node implements MyMap.Entry {
        int hash; // хэш-значение
        Object key;
        Object value;
        Node next; // указатель на следующий узел

        Node(int hash, Object key, Object value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public Object getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }
    }

}
