import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
class Blockperiod {
    int[] start;
    int[] end;
    int numoff;
    Blockperiod()
    {
      start = null;
      end = null;
      numoff = 0;
    }
    Blockperiod(int[] s,int[] e, int n)
    {
  	  start = s;
  	  end = e;
  	  numoff = n;
    }
}
public class waterFlow {
    public static void BFS(Graph g, String src, FileWriter fw) throws IOException
    {
        Set<String> visited = new HashSet<String>();
        Queue<String> queue = new LinkedList<String>();
        Map<String, String> pathCosts = new HashMap<String, String>();
        List<String> directions = new LinkedList<String>();
        //add starting vertex
        queue.add(g.src);
        visited.add(g.src);
        //while the queue is not empty, keep doing
        while(!queue.isEmpty())
        {
        //remove from front of queue
        String child = queue.remove();
        for(String neighbor : g.getNeighbors(child))
        {
           
            if(!visited.contains(neighbor))
            {
                //cnt++;
                queue.add(neighbor);
                visited.add(neighbor);
                pathCosts.put(neighbor, child);
            }
        }
        }
        //prints paths, adding them gives length
   
        for(String destnode : g.dest)
        {
            //System.out.println(destnode);
            for(String str = destnode; str != null; str = pathCosts.get(str))
            {
                directions.add(str);
            }
            //only check the lengths for those destinations that have been reached
            int mindist = -1;
            if(directions.get(directions.size()-1) == g.src)
            {
               
                if(mindist < g.startPoint+ directions.size()-1)
                    {
                	mindist = g.startPoint+ directions.size()-1;
                	System.out.println(destnode+" "+mindist);
                	fw.append(destnode+" "+mindist+"\n");
                    break;
                    }
                	
            }
            //if it reaches here, none of the dest nodes have been reached else it would have returned
            System.out.println("None");
        	fw.append("None"+"\n");
            
            directions.clear();
        }
       // System.out.println(directions);
    }
    public static void DFS(Graph g, String src, FileWriter fw) throws IOException
    {
        Set<String> visited = new HashSet<String>();
        Deque<String> stack = new LinkedList<String>();
        Map<String, String> pathCosts = new HashMap<String, String>();
        List<String> directions = new LinkedList<String>();
        //add first element
        visited.add(src);
        stack.add(src);
        while (!stack.isEmpty()) {
                String currentVisit = stack.pop();
            //    System.out.println("Visiting " + currentVisit);
                for (String v : g.getNeighbors(currentVisit)) {
                        if (!visited.contains(v)) {
                                stack.push(v);
                                visited.add(v);
                                pathCosts.put(v, currentVisit);
                       }
                }
        }
       //parent node backtracking to get the paths
        for(String destnode : g.dest)
        {
            //System.out.println(destnode);
            for(String str = destnode; str != null; str = pathCosts.get(str))
            {
                directions.add(str);
            }
            //only check the lengths for those destinations that have been reached
           
            int mindist = -1;
            if(directions.get(directions.size()-1) == g.src)
            {
               
                if(mindist < g.startPoint+ directions.size()-1)
                {
                	mindist = g.startPoint+ directions.size()-1;
                	System.out.println(destnode+" "+mindist);
                	fw.append(destnode+" "+mindist+"\n");
                    
                    break; 
                }
            }  
            //if it reaches here, none of the dest nodes have been reached else it would have returned
            System.out.println("None");
            fw.append("None"+"\n");
            directions.clear();
        }

    }
    public static void getTestCase(String ipfile, String opfile)
    {
        try {
            BufferedReader br=new BufferedReader(new FileReader(ipfile));
            FileWriter fw = new FileWriter(opfile);
            String str="", line = "";
            //number of test cases
            int num = Integer.parseInt(br.readLine());
            while(num > 0)
            {
                str = "";
                //System.out.println("------start "+num);
                while((line=br.readLine())!=null)
                {
                    //break if it encounters an empty char
                    if(line.isEmpty()) break;
                    str += line.trim()+"\n";
                }
                String str_arr[] = str.split("\n");
                //initial parsing of task, src, dests, middlenodes, numpipes.
                Graph g1 = new waterFlow().new Graph();
                g1.task = str_arr[0];
                g1.src = str_arr[1];
                g1.dest = str_arr[2].split(" ");
                g1.mnodes = str_arr[3].split(" ");
                g1.numPipes = Integer.parseInt(str_arr[4]);
                //displaying the parsed vals
               // System.out.println("Task = "+g1.task);
                //System.out.println("Graph details");
                int k = 5;
                int i = 0;
                String[][] graph_mat = new String[g1.numPipes][];
                for(i= k; i < g1.numPipes + k; i++)
                {
                    graph_mat[i-k] = str_arr[i].split(" ");
                }   
                //UCS parsing, take edge distances and num of off periods as they are
                if(g1.task.equals("UCS"))
                {
                	
                	//int adjacency_matrix[][];
                    g1.nodelist = new ArrayList<String>();
                    //add the src nodes, the dest nodes and the middle nodes
                    g1.nodelist.add(g1.src);
                    for(int i1=0; i1 < g1.dest.length; i1++)
                    	g1.nodelist.add(g1.dest[i1]);
                    for(int i1=0; i1 < g1.mnodes.length; i1++)
                    	g1.nodelist.add(g1.mnodes[i1]);
                   // System.out.println(g1.nodelist);
                    //sort the list lexicographically
                    Collections.sort(g1.nodelist);
                    //create adjacency matrix
                    int arrsz = g1.nodelist.size();
                    g1.adjacency_matrix = new int[arrsz + 1][arrsz + 1];
                   // System.out.println(g1.nodelist);
                    for (int i1 = 1; i1 <= arrsz; i1++)
                    {
                        for (int j = 1; j <= arrsz; j++)
                        {
                            if (i1 == j)
                            {
                                g1.adjacency_matrix[i1][j] = 0;
                                continue;
                            }
                            if (g1.adjacency_matrix[i1][j] == 0)
                            {
                                g1.adjacency_matrix[i1][j] = 999;
                            }
                        }
                    }
                    g1.block = new Blockperiod[g1.numPipes];
                    for(int g = 0; g < g1.numPipes; g++)
                    {
                           g1.addEdge(graph_mat[g][0], graph_mat[g][1]);
                           
                          // Mynode newedge = );
                           g1.addEdgeCost(graph_mat[g][0], new waterFlow().new Mynode(graph_mat[g][1], Integer.parseInt(graph_mat[g][2])));
                           
                    	   int i_ind = g1.nodelist.indexOf(graph_mat[g][0])+1;
                    	   int j_ind = g1.nodelist.indexOf(graph_mat[g][1])+1;
                    	   String key = new String(i_ind+""+j_ind);
                    	   
                    	   String mykey = new String(graph_mat[g][0]+"_"+graph_mat[g][1]);
                    	   g1.adjacency_matrix[i_ind][j_ind] = Integer.parseInt(graph_mat[g][2]);
                              //create an array of blockperiod objects
                            int numoff = Integer.parseInt(graph_mat[g][3]);
                            int tstart[] = new int[numoff+1];
                            int tend[] = new int[numoff+1];
                            for(int z = 0; z < numoff; z++)
                            {
                                String range = graph_mat[g][z+4];
                                //getting the start and end times of the offperiods
                                
                                int start = Integer.parseInt(range.substring(0,range.indexOf("-")));
                                int end = Integer.parseInt(range.substring(range.indexOf("-")+1));
                                tstart[z] = start;
                                tend[z] = end;
                            }
                            g1.block[g] = new Blockperiod(tstart, tend, numoff);
                            g1.addCost(mykey, g1.block[g]);
                    }
                }
                //DFS and BFS parsing, ignore edge distances and off periods
                else
                {
                    for(int g = 0; g < g1.numPipes; g++)
                        g1.addEdge(graph_mat[g][0],graph_mat[g][1]);
                }
             
                String start_point = str_arr[i];
                g1.startPoint = Integer.parseInt(start_point);
               if(g1.task.equals("BFS"))
                   BFS(g1, g1.src, fw);
               else if(g1.task.equals("DFS"))
                   DFS(g1, g1.src, fw);
               else if(g1.task.equals("UCS"))
            	   UCSt(g1,g1.src,fw);
                num--;
                }
                    br.close();
                    fw.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
        }

    class Mynode {
        String label;
        int cost;
        int pathcost = 0;
        String parent;
        Mynode(String d, int c, String p)
        {
          label = d;
          cost = c;
          parent = p;
        }
        Mynode(String d, int c)
        {
          label = d;
          cost = c;
        }
     }
    class Graph{
        String src;
        String task;
        String[] dest;
        String[] mnodes;
        int numPipes;
        int startPoint;
        
        int [][]adjacency_matrix;
        Map<String, List<String>> edges = new HashMap<String, List<String>>();
        Map<String, List<Mynode>> edgeCost = new HashMap<String, List<Mynode>>();
        
        Map<String, List<Blockperiod>> listblk = new HashMap<String, List<Blockperiod>>();
        
        List<String> nodelist;
        Blockperiod[] block;
 
        public void addEdge(String src, String dest) {
            List<String> srcNeighbors = this.edges.get(src);
            if (srcNeighbors == null) {      this.edges.put(src, srcNeighbors = new ArrayList<String>()); }
            srcNeighbors.add(dest);
        }
        public void addEdgeCost(String src, Mynode dest) {
            List<Mynode> srcNeighbors = this.edgeCost.get(src);
            if (srcNeighbors == null) {      
            	
            	this.edgeCost.put(src, srcNeighbors = new ArrayList<Mynode>()); 
            	}
            srcNeighbors.add(dest);
        }
        
        public void addCost(String index, Blockperiod blk)
        {
            List<Blockperiod> blklist = this.listblk.get(index);
            if (blklist == null) {      this.listblk.put(index, blklist = new ArrayList<Blockperiod>()); }
            blklist.add(blk);
        }
        public Iterable<String> getNeighbors(String vertex) {
            List<String> neighbors = this.edges.get(vertex);
            if (neighbors == null) {        return Collections.emptyList();      }
            else {     return Collections.unmodifiableList(neighbors);         }
        }
        public List<Mynode> getNeighborsCosts(String vertex) {
            List<Mynode> neighbors = this.edgeCost.get(vertex);
            if (neighbors == null) {        return Collections.emptyList();      }
            else {   
            	//sort the neighbors based on cost and return
            	Collections.sort(neighbors, new waterFlow().new MyCostComparator());
         		return Collections.unmodifiableList(neighbors);         
            	}
        }    
    }
    class MyCostComparator implements Comparator<Mynode>{
   	 
        @Override
        public int compare(Mynode o1, Mynode o2) {
            return o1.cost - o2.cost;
        }
    }
    public static void UCSt(Graph g, String src, FileWriter fw) throws IOException
    {
    	   Set<Mynode> explored = new HashSet<Mynode>();
           PriorityQueue<Mynode> frontier = new PriorityQueue<Mynode>(g.nodelist.size(), new waterFlow().new MyCostComparator());
           Mynode root = new waterFlow().new Mynode(g.src,g.startPoint);
           root.pathcost = root.cost;
           //add that node to queue and to explored
		   frontier.add(root);
           //run algo for all destination nodes
		   //System.out.println(g.listblk);
		   
          //while the queue is not empty
               while(!frontier.isEmpty())
               {
            	   //remove from front of queue
            	   Mynode currnode = frontier.remove();
            	   explored.add(currnode);
            	   //check if goal node(s) is the starting node
            	//   System.out.println("currnode ="+ currnode.label);
            	   if(Arrays.asList(g.dest).contains(currnode.label))
            	   {
            		   System.out.println(currnode.label+" "+currnode.pathcost);
            		   fw.append(currnode.label+" "+currnode.pathcost+"\n");
            		   return;
            	   }
            	   
                   //get all children for node
                   List<Mynode> children = g.getNeighborsCosts(currnode.label);
                   
                   for(Mynode child : children)
                   {
                	   //if child has not been explored
                       if(!explored.contains(child))
                       {
                    	   //if child has not been added to frontier
                    	   if(!frontier.contains(child))
                    	   {
                    		 //check for availability, block periods, before adding to froniter
                    		   int available = 1;
                        	   for( Blockperiod blk :  g.listblk.get(new String(currnode.label+"_"+child.label)))
                          	 	{  
                          	 		for(int i1 =0; i1 < blk.start.length-1; i1++)
                          	 		{  //if the time interacts with ANY period, break
                          	 			if(currnode.pathcost%24 >= blk.start[i1] && currnode.pathcost%24 <= blk.end[i1])
                          	 			{
                          	 				available = 0;
                          	 				break;
                          	 			}
                          	 		}
                  	 			}
                               
                        	   //System.out.println(available);
                        	   if(available == 1){
                        		   child.pathcost = currnode.pathcost + child.cost;
                        		//   System.out.println("Curr child cost of "+child.label+" is "+child.cost+" and path cost is "+currnode.pathcost);
                        		   child.parent = currnode.label;
                        		   frontier.add(child);
                        	   }
                           }
                    	   //if child has been added to frontier
                    	   else
                    	   {
                    		   //check if it's pathcost is less than the current cost to reach that child
                    		   if(child.pathcost > currnode.pathcost + child.cost)
                    		   {
                    			   child.pathcost = currnode.pathcost + child.cost;
                    			   
                    		   }
                    	   }
                       }
                   }
               }
               fw.append("None"+"\n");
               return;
       }

    public static void main(String args[])
        {
            String ip = args[0];
            String op = "output.txt";
            getTestCase(ip, op);
                           
        }    
}