import java.util.*;

public class Centrality
{


    private ArrayList<ArrayList<Integer>> closenessCentralities = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> betweennessCentralities = new ArrayList<>();
    private ArrayList<Float> closenessCentralitiesw = new ArrayList<>();
    private ArrayList<Float> betweennessCentralitiesw = new ArrayList<>();
    
    public Centrality(Graph g)
    {
        calculateCentralities(g);
    }

    
    public class Node {
        public final Integer v;
        public final Float w;
        Node (Integer vertex, Float neighbour) {
            v = vertex;
            w = neighbour;
        }
    }
    

    public class NodeComparator implements Comparator<Node>{

        @Override
        public int compare(Node o1, Node o2) {
            if (o1.w < o2.w) return 1;
            else if (o1.w > o2.w) return -1;
            return 0;
        }   
        
    }
    
    

    private void calculateCentralities(Graph g) {
        int numNodes = g.getNumberOfVertices();
        float[] betweenessCentralities = new float[numNodes];
        int[][] weightsOfShortestPaths = new int[numNodes][];
        ArrayList<PriorityQueue<Node>> pqList = new ArrayList<PriorityQueue<Node>>();
        ArrayList<HashSet<Integer>> adjList = g.getAdjList();

  
        
        Stack<Integer> stack;

        // create a sigma list 
        float sigma[] = new float[numNodes];
        // create a delta list  
        float delta[] = new float[numNodes];
        // holds the distance for each iteration of the paths
        int distances[];
        // assign an empty queue
        Queue<Integer> queue;
        boolean visited[] = new boolean[numNodes];
        int component[] = new int[numNodes];
        // for each s in V
        // beginning with the starting node, for all Vertex V which is an element of the graph G do:
        visited[0] = true; 
        float[] closeness = new float[g.getNumberOfVertices()];
        pqList.add(new PriorityQueue<>(new NodeComparator()));
        
        int cN = 1;
        for(int startingNode = 0; startingNode < numNodes; startingNode++)
        {

            // assign the shortest paths list to use later on. Corresponds to P on paper
            ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
            stack = new Stack<Integer>();
            distances = new int[numNodes];
            if (!visited[startingNode]) {
                for(int i = 0; i < numNodes; i++) {
                    if (!visited[i]) {
                        component[i] = cN;
                    }
                }
                pqList.add(new PriorityQueue<>(new NodeComparator()));
                cN++;
            }
            for(int i = 0; i<numNodes; i++)
            {
                paths.add(new ArrayList<Integer>()); // create an array inside of an array, to store the different sequences shortest paths
                sigma[i] = 0;
                distances[i] = -1;
               
                delta[i] = 0f;
            }
            sigma[startingNode] = 1;
            distances[startingNode] = 0;
            
            queue = new ArrayDeque<Integer>();
            queue.add(startingNode);
            visited[startingNode] = true;
            int v; // current Node/Vertex
            // while Q not empty do:
            while(!queue.isEmpty())
            {
                // dequeue v from Q and push to S
                v = queue.remove();
                stack.push(v);
                HashSet<Integer> adjacent = adjList.get(v);
                // for each neighbour w of v/currentVertex do:
                for (Integer currentNeighbor : adjacent) {
                    if(distances[currentNeighbor] < 0)
                    {
                        queue.add(currentNeighbor);
                        visited[currentNeighbor] = true;
                        distances[currentNeighbor] = distances[v]+1;
                    }
                    if(distances[currentNeighbor] == distances[v]+1)
                    {
                        sigma[currentNeighbor] += sigma[v];
                        paths.get(currentNeighbor).add(v);
                    }
                    weightsOfShortestPaths[v] = distances; 
                    
                }
                closeness[startingNode] += distances[v];
            }

            


            
            //While Stack is not empty do:
            while(!stack.isEmpty())
            {
                // pop one by one
                v = stack.pop();
                // for each vertex in P/Paths, delta[w] = delta[w] + (sigma[w] / sigma[v]) * (1+ delta[v])
                java.util.Iterator<Integer> pathIterator = paths.get(v).iterator();
                int w; // w is the neighbour
                while(pathIterator.hasNext())
                {
                    w = pathIterator.next();
                    delta[w] += ((sigma[w])/(sigma[v]))*(1f + delta[v]);
                }
                if(v != startingNode){
                    betweenessCentralities[v] += delta[v]/2;

                }
                
            }

            closeness[startingNode] = 1/closeness[startingNode];
      
        }
        

        
        for (int k = 0; k < numNodes; k++) {
            pqList.get(component[k]).add(new Node(k, closeness[k]));
        }

        findTop(pqList, closenessCentralities, g,1);
        
        for (int k = 0; k < numNodes; k++) {
            pqList.get(component[k]).add(new Node(k, betweenessCentralities[k]));

        }
        
        findTop(pqList, betweennessCentralities, g,2);

        
    
    
    }
    
	private void findTop(ArrayList<PriorityQueue<Node>> pqList, ArrayList<ArrayList<Integer>> aL, Graph g, int metric) {
		aL.add(new ArrayList<>());

		

		if (metric == 1)
			closenessCentralitiesw.add(pqList.get(0).peek().w);
		else
			betweennessCentralitiesw.add(pqList.get(0).peek().w);

		aL.get(0).add(g.getVertex(pqList.get(0).poll().v));
		
		pqList.get(0).clear();

	}
    

    public Integer getClosenessCentrality(){
        return closenessCentralities.get(0).get(0);
    }
    public Integer getBetweenessCentrality(){
        return betweennessCentralities.get(0).get(0);
    }

	public Double getClosenessCentralitiesw() {
		return (double)closenessCentralitiesw.get(0);
	}

	public Integer getBetweennessCentralitiesw() {
		return Math.round(betweennessCentralitiesw.get(0));
	}
    
    

}