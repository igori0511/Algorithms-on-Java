/**
 * @Igor Zimenco
 * @19.09.2013
 */
public class Subset {
   public static void main(String[] args)
   {
       int numSub = Integer.parseInt(args[0]);
       
       RandomizedQueue<String> randomSet = new RandomizedQueue<String>();
       
       while(!StdIn.isEmpty())
       {
         String str = StdIn.readString();
         randomSet.enqueue(str);
       }
       
       for(int i = 0; i < numSub; i++)
       {
           StdOut.println(randomSet.dequeue());
       }
       
   }
}