/**
 * Generic data structure Deque
 * @author Igor Zimenco
 * @version 17.09.2013
 */
import java.util.Iterator;
import java.lang.UnsupportedOperationException;
public class Deque<Item> implements Iterable<Item>
{
    private Node first,last; // pointers that will point to the begining and the end of deque
    private int N;           // will contain size
    // implement a basic structure for deque
    private class Node
    {
        Node next; // will point to the next node
        Item item; // will contain data
        Node prev;  // will point to previous node
        
    }
    
    /**
     * Construc an empty deque
     */
    public Deque()
    {
        // make a empty deque,with size 0;
        last  = null; 
        first = null;
        N     = 0;
    }
    
    /**
     * Check if deque is empty
     * @return true if the list is empty false if not.
     */
    public boolean isEmpty()
    {
        return (last == null || first == null);
    }
    
    // throw null pointer exception
    private void nullException(Item item)
    {
        // throw an exception if the item is null
        if(item == null) throw new java.lang.NullPointerException("bad data null");
    }
    //throw no such element exception
    private void noElemExp()
    {
        if(isEmpty()) throw new java.util.NoSuchElementException("Deque underflow");
    }
    
    /**
     * Determines the size of the deque
     * @return the size of the deque
     */
    public int size()
    {
       return N; // return size of the queue 
    }
    
    /**
     * Insert the item at the front of hte queue
     * @param  item of generic type
     * @return void
     */
    public void addFirst(Item item)
    {
        // throw an exception if the item is null
        nullException(item);
        
        Node old = first;   // make a tempory node that point to the first one
        first = new Node(); // create a new node
        first.item = item;  // insert data to that node
        
        if(isEmpty())       // consider the case where the deque is empty
        {
            first.next = null;
            first.prev = null;
            last = first;
        }
        else
        {
           first.next = old;   // make new node point to the next node 
           first.prev = null;  // make previous point to null
           old.prev   = first; // make oldnodes previos point to first node
        }
        N++; // make size +1
    }
    
    /**
     * Insert the item at the end of the deque
     * @param item of generic type
     * @return void
     */
    public void addLast(Item item)
    {
        // throw an exception if the item is null
        nullException(item);
        
        Node old = last;   // make a tempory node that point to the last one
        last = new Node(); // create a new node
        last.item = item;  // insert data to that node
        
        if(isEmpty())       // consider the case where the deque is empty
        {
            last.next = null;
            last.prev = null;
            first = last;
        }
        else
        {
           last.next = null;  // make new node point to null
           last.prev = old;   // make previous point to old
           old.next  = last;  // make oldnodes next point to last node
        } 
        N++; // make size +1
    }  
    
    /**
     * Delete and return the item at the front from deque
     * @param none
     * @return item of generit type
     */
    public Item removeFirst()
    {
        // throw an exception if client tries to get an element from an empty deque
        noElemExp();
        
        Item item = first.item;  // get data
        Node old = first;        // make temporary node
        first = first.next;      // make first point to next node
        
        if(!isEmpty())
        {
            first.prev = null;       // make previous point to null
        }
        
        old.next   = null;       // make old next point to null
        
        if(isEmpty()) last = null; // consider the case where the list is empty
        
        N--; // make size -1
        
        return item; // return data
    }
    
     /**
     * Delete and return the item at the end from deque
     * @param none
     * @return item of generic type
     */
    public Item removeLast()
    {
        // throw an exception if client tries to get an element from an empty deque
        noElemExp();
        
        Item item = last.item;  // get data
        Node old = last;        // make  a temporary node
        last = last.prev;       // make last point to prev node
        
        if(!isEmpty())
        {
            last.next = null;       // make next node point to null
        }
        old.prev  = null;       // make old previous point to null
        
        if(isEmpty()) first = null; // consider the case where the list is empty
        
        N--; // make size -1
        
        return item; // return data
    }
    
    /**
     *@return iterator 
     */
    public Iterator<Item> iterator(){return new DequeIterator();}
    
    // private class that implements the Iterator interface
    private class DequeIterator implements Iterator<Item>
    {
        private Node current;
        
        public DequeIterator()
        {
            current = first;
        }
        // check if the Deque if empty
        public boolean hasNext() {return current != null;}
        // remove an element "not suported"
        public void remove() {throw new java.lang.UnsupportedOperationException("remove not supported");}
        // return item in the Deque
        public Item next() 
        {
        
        if(!hasNext()) throw new java.util.NoSuchElementException("no more elements to return");   
                        
        Item item = current.item;  // get data
        current   = current.next;
      
        return item; // return data
        }
        
    }
}
