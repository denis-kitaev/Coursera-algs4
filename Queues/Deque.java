import java.util.NoSuchElementException;
import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {
    private int size = 0;
    private Node first = null;
    private Node last = null;

    /* 
        construct an empty deque
    */    
    public Deque() {
        
    }
    
    private class Node {
        public Item item;
        public Node next;
        public Node prev;
    }
    
    private void validateNull(Item item) {
        if (item == null) {
            throw new NullPointerException("Argument must be not null");
        }
    }
    
    /*
        is the deque empty?
    */
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        validateNull(item);
        
        if (size == 0) {
            first = new Node();
            first.item = item;
            last = first;
        } else if (size == 1) {
            last = first;
            
            first = new Node();
            first.item = item;
            first.next = last;
            
            last.prev = first;
            
        } else {
            Node oldfirst = first;
            
            
            first = new Node();
            first.item = item;
            
            first.next = oldfirst;
            oldfirst.prev = first;
        }

        size++;
    }
    
    public void addLast(Item item) {
        validateNull(item);
        
        if (size == 0) {
            last = new Node();
            last.item = item;
            first = last;
        } else if (size == 1) {
            first = last;
            
            last = new Node();
            last.item = item;
            last.prev = first;
            
            first.next = last;
        } else {
            Node oldlast = last;
            
            last = new Node();
            last.item = item;
            
            last.prev = oldlast;
            oldlast.next = last;
        }
        
        size++;
    }
    
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }
    
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        
        Item item = last.item;
        last = last.prev;
        
        size--; 
        return item;
    }
    
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current;
        
        public ListIterator() {
            current = first;
        }
    
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            Item item = current.item;
            current = current.next;
            
            return item;
        }
    
        public void remove() {
            /* not supported */ 
            throw new UnsupportedOperationException("No such method");
        } 
    }
    
    
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        
        for (int i = 0; i < 17; i++) {
            deque.addLast(i);
            deque.addFirst(i);
        }
        
        for (int i = 100; i < 120; i++) {
            deque.addLast(i);
        }
        
        for (int i : deque) 
            StdOut.println("deque iterator - " + i);
        
        for (int i = 0; i < 17; i++) {
            StdOut.println(deque.removeLast());
        }
        
        
        for (int i = 0; i < 6; i++) {
            StdOut.println(deque.removeFirst());
        }
        
    }
}
