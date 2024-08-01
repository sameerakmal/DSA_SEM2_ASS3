import java.util.LinkedList;

class HashmapSeparateChaining {
    private static class Entry {
        int key;
        int value;

        Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Entry>[] table;
    private int capacity;

    public HashmapSeparateChaining(int capacity) {
        this.capacity = capacity;
        this.table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash(int key) {
        return key % capacity;
    }

    public boolean find(int key) {
        int index = hash(key);
        for (Entry entry : table[index]) {
            if (entry.key == key) {
                return true;
            }
        }
        return false;
    }

    public void insert(int key, int value) {
        int index = hash(key);
        for (Entry entry : table[index]) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }
        table[index].add(new Entry(key, value));
    }

    public void remove(int key) {
        int index = hash(key);
        table[index].removeIf(entry -> entry.key == key);
    }

    public static void main(String[] args) {
        HashmapSeparateChaining map = new HashmapSeparateChaining(10);

        map.insert(1, 10);
        map.insert(2, 20);
        map.insert(12, 120);

        System.out.println(map.find(1)); 
        System.out.println(map.find(12)); 
        System.out.println(map.find(3)); 

        map.remove(1);
        System.out.println(map.find(1)); 
    }
}
