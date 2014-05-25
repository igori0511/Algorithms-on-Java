
/**
 * @Igor Zimenco
 * @18.09.2013
 */
import java.util.NoSuchElementException;
import java.util.Iterator;
public class RandomizedQueue<Item> implements Iterable<Item>
{
     // Queue array 
   private Item[] q;
   // size of the Queue
   private int N;
   
   /**
    * Construct a Queue
    */
   public RandomizedQueue()
   {
       q = (Item[]) new Object[1];
       N = 0;
   }
   
   /**
    * @return true if the list if empty else otherwise
    */
   public boolean isEmpty()
   {
       return N == 0;
   }
   /**
    * @return size of the queue
    */
   public int size()
   {
       return N;
   }
   // resize the array if necesary
   private void resize(int capacity)
   {
       // make a bigges array
       Item[] copy = (Item[]) new Object[capacity];
       
       // copy existing items
       for(int i = 0; i < N; i++) 
       {
          copy[i] = q[i];
       }
       // make q array point to new array
       q = copy;
       
    }
     
   /**
    * add String to the queue
    */
   public void enqueue(Item item)
   {
       // throw an exception if client tries to add null item
       if(item == null) throw new java.lang.NullPointerException("bad input, null item");
       // resize queue if necessary
       if(N == q.length) resize(2 * q.length);
   
       // add a String to queue
       q[N++] = item;

   }

   
   /**
    * @return String from the queue
    */
   public Item dequeue()
   {
      // throw an exception if the client tries to get an item from an empty queue
      if (isEmpty()) throw new NoSuchElementException("Queue underflow");
      // index of the random item
      int index = StdRandom.uniform(N);
      int swapIndex = --N;
      // return an random item
      Item item = q[index];
      q[index] = null;
      //swap values to maintain order
      Item temp = q[swapIndex];
      q[swapIndex]   = q[index];
      q[index]  = temp;
      // resize the array if necessary
      if (N > 0 && N == q.length/4) resize(q.length/2); 
     
      // return item
      return item;
   }
   
   /**
    * return the sample item in the queue
    * @return item of type Item
    */
   public Item sample()
   {
       // throw an exception if the client tries to get an item fro man empty queue
      if (isEmpty()) throw new NoSuchElementException("Queue underflow");
      //will keep our index
      int index = StdRandom.uniform(N);
      // return element at that index
      return q[index];
   }
   
   /**
     *@return iterator 
    */
   public Iterator<Item> iterator(){return new QueueIterator();}
   
   //public class that implements the iterator interface
   private class QueueIterator implements Iterator<Item>
   {
       private Item[] indexArray;
       private int index;
       private int size;
       private int swapIndex;
       
       public QueueIterator()
       {
          // set size to N
          size = N;
          //construct a temporary array
          indexArray = (Item[])new Object[size]; 
          
          // copy items in that array
          for(int i = 0; i < size; i++) 
          {
              indexArray[i] = q[i];
              
          }
          
       }
       
       // check if the exists
       public boolean hasNext() {return size > 0;}
       
       // not suported
       public void remove() {throw new java.lang.UnsupportedOperationException("remove not supported");}
       
       // returns items in random order
       public Item next()
       {
           
          if(!hasNext()) throw new java.util.NoSuchElementException("no more elements to return"); 
          // generate random index
          
          index = StdRandom.uniform(size);
          swapIndex = --size;
          //get item at given index
          Item item = indexArray[index];
          // set that item to null
          indexArray[index] = null;
          //swap values to maintain order
          Item temp = indexArray[swapIndex];
          indexArray[swapIndex]  = indexArray[index];
          indexArray[index]  = temp;
          // return random item 
          return item;
       }
   }
   
}
