import java.util.NoSuchElementException;
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int size = 0;
    
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = s[i];
        }
        
        s = copy;
    }
    
    private void validateNull(Item item) {
        if (item == null) {
            throw new NullPointerException("Argument must be not null");
        }
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;    
    }
    
    // add the item
    public void enqueue(Item item) {
        validateNull(item);
        if (size == s.length) {
            resize(s.length * 2);
        }
        
        s[size++] = item;
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }
        
        if (size == s.length / 4) {
            resize(s.length / 2);
        }
        
        for (int i = 0; i < size; i++)
            StdOut.printf("s[%d] = %d\n", i, s[i]);
        
        int i = StdRandom.uniform(0, size);
        Item item = s[i];
        
        s[i] = s[size - 1];
        size--;
        return item; 
    }
    
    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }
        
        int i = StdRandom.uniform(0, size);
        return s[i];
    }
    
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int i;
        
        public ArrayIterator() {
            i = size;
        }
        
        public boolean hasNext() {
            return i > 0;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return s[--i];
        }
        
        public void remove() {
            /* not supported */ 
            throw new UnsupportedOperationException("No such method");
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        for (int i = 0; i < 17; i++) {
            queue.enqueue(i);
        }
        
        for (int i = 0; i < 6; i++) {
            StdOut.println(queue.sample());
        }
        
        for (int i : queue) 
            StdOut.println("deque iterator - " + i);
        
        
        for (int i = 0; i < 17; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
