import java.util.Arrays;

class HashmapLinearProbing {
    private static class Entry {
        int key;
        int value;
        boolean isDeleted;

        Entry(int key, int value) {
            this.key = key;
            this.value = value;
            this.isDeleted = false;
        }
    }

    private Entry[] table;
    private int capacity;
    private int size;

    public HashmapLinearProbing(int capacity) {
        this.capacity = capacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    private int hash(int key) {
        return key % capacity;
    }

    public boolean find(int key) {
        int index = hash(key);
        int startIndex = index;
        while (table[index] != null) {
            if (!table[index].isDeleted && table[index].key == key) {
                return true;
            }
            index = (index + 1) % capacity;
            if (index == startIndex) {
                break;
            }
        }
        return false;
    }

    public void insert(int key, int value) {
        if (size == capacity) {
            throw new IllegalStateException("Hashmap is full");
        }
        int index = hash(key);
        while (table[index] != null && !table[index].isDeleted) {
            if (table[index].key == key) {
                table[index].value = value;
                return;
            }
            index = (index + 1) % capacity;
        }
        table[index] = new Entry(key, value);
        size++;
    }

    public void remove(int key) {
        int index = hash(key);
        int startIndex = index;
        while (table[index] != null) {
            if (!table[index].isDeleted && table[index].key == key) {
                table[index].isDeleted = true;
                size--;
                return;
            }
            index = (index + 1) % capacity;
            if (index == startIndex) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        HashmapLinearProbing map = new HashmapLinearProbing(10);

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
