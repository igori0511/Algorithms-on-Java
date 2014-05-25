import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class WordNet{
    
    private ArrayList<String> idToNouns;
    private HashMap<String,Set<Integer>> nounsToId;
    private Digraph G;
    private SAP sap;
    private DirectedCycle cycle;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
      
      //initialize data structures
      this.idToNouns = new ArrayList<String>();
      this.nounsToId = new HashMap<String,Set<Integer>>(); 
      //first stage, create two input streams
      In inSyn = new In(synsets);
      int synsetId = -1;
      In inHyp = new In(hypernyms);
 
      //second stage, read the synsets and store them
      while(!inSyn.isEmpty())
        {
            // read the first line of the file and split it
            String[] a = inSyn.readLine().split(",");
            // split the second foeld by spaces
            String[] b = a[1].split(" ");
            //parse our integer
            synsetId = Integer.parseInt(a[0]);
            // associate every word with a sysen id
            // there can multiple id's for a given noun
            for(String x: b)
            {
               // if the key already in the HasMap
               // add the value to the set
               if(nounsToId.containsKey(x)) 
               {
                  Set<Integer> temp =  nounsToId.get(x);
                  temp.add(synsetId);
               }
               //else make a new set
               //and add the value to it
               else
               {
                  Set<Integer> temp =  new HashSet<Integer>();
                  temp.add(synsetId);
                  nounsToId.put(x,temp); 
               }
            }
           // associate synset id with a synset 
           idToNouns.add(a[1]);
        }
        //prit for testing
       // System.out.println(nounsToId);
        //System.out.println(idToNouns); 
        //third stage read hypernyms to create a digraph
        this.G = new Digraph(synsetId+1);
        //System.out.println(synsetId);
        while(!inHyp.isEmpty())
        {
            //split the string
            String[] a = inHyp.readLine().split(",");
            for(int i = 1; i < a.length;i++)
            {
                this.G.addEdge(Integer.parseInt(a[0]),Integer.parseInt(a[i]));
            }
            
        }
        //System.out.println(G);
        this.cycle = new DirectedCycle(G);
        int size = G.V();
        int count = 0;
        for(int i = 0; i < size;i++)
        {
            for(int x:G.adj(i))
            {
                count++;
                break;
            }
        }

        if(!cycle.hasCycle() && (G.V() - count) == 1)
        { 
            this.sap = new SAP(this.G);
        }
        else{
           throw new java.lang.IllegalArgumentException("Graph isn't a rooted DAG!!"); 
        }
    }
    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns()
    {
        return nounsToId.keySet();
    }
    
    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        return nounsToId.containsKey(word);
    }
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        int distance = -1;
        //check if nouns are in the synset
        if(nounsToId.containsKey(nounA) && nounsToId.containsKey(nounB))
        {
                       
            Set<Integer> first  = this.nounsToId.get(nounA);
            Set<Integer> second = this.nounsToId.get(nounB);
            
            if(!first.isEmpty() && !second.isEmpty())
            {
               distance = this.sap.length(first,second);
            }
            
        }
        else{
            throw new java.lang.IllegalArgumentException("Illigal arguments");
        }
        
        
        return distance;
    }
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        int commonAncest = -1;
        //check if nouns are in the synset
        if(nounsToId.containsKey(nounA) && nounsToId.containsKey(nounB))
        {
                       
            Set<Integer> first  = this.nounsToId.get(nounA);
            Set<Integer> second = this.nounsToId.get(nounB);
            
            if(!first.isEmpty() && !second.isEmpty())
            {
               commonAncest = this.sap.ancestor(first,second);
            }
            
        }
        else{
            throw new java.lang.IllegalArgumentException("Illigal arguments");
        }
        
        
        return this.idToNouns.get(commonAncest);
    }
    // for unit testing of this class
    public static void main(String[] args)
    {
         WordNet test = new WordNet(args[0],args[1]);

        
    }

}