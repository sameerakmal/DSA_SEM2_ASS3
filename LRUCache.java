import java.util.*;

class LRUCache {
    private final int capacity;
    private final Map<Integer, Integer> cache;
    private final LinkedHashMap<Integer, Integer> order;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.order = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        order.get(key);
        return cache.get(key);
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            cache.put(key, value);
            order.put(key, value);
        } else {
            if (cache.size() >= capacity) {
                int eldestKey = order.keySet().iterator().next();
                cache.remove(eldestKey);
                order.remove(eldestKey);
            }
            cache.put(key, value);
            order.put(key, value);
        }
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        System.out.println(lruCache.get(1)); 
        lruCache.put(3, 3); 
        System.out.println(lruCache.get(2)); 
        lruCache.put(4, 4); 
        System.out.println(lruCache.get(1)); 
        System.out.println(lruCache.get(3));
        System.out.println(lruCache.get(4)); 
    }
}
