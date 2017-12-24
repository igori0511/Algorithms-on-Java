import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class  Outcast{
    
    private WordNet out;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet)
    {
        this.out = wordnet;
    }
    
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns)
    {
        int dist;
        int index = -1;
        int max = Integer.MIN_VALUE;
        
        
        for(int i = 0; i < nouns.length;i++)
        {
            dist = 0;
            for(int j = 0; j < nouns.length;j++)
            {
                dist += (out.distance(nouns[i],nouns[j]));
            }
            
            if(max < dist){
                max = dist;
                index = i;
            } 
        }
 
        return nouns[index];     
    }
          
           
    // for unit testing of this class (such as the one below)
    public static void main(String[] args)
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}