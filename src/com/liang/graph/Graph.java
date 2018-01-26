package com.liang.graph;

import com.liang.util.DoubleArray;
import com.liang.util.IntArray;
import com.liang.util.StringArray;
import com.liang.main.NodeSet;

import java.util.*;


public class Graph implements Iterable<Edge> {


	protected boolean directed = false;
	protected int numberOfNodes = 0;
	protected StringArray nodeNames = new StringArray();
	protected IntArray edgesOut = new IntArray(32, 536870912 /* = 2^29 */);
	protected IntArray edgesIn = new IntArray(32, 536870912 /* = 2^29 */);
	protected DoubleArray weights = new DoubleArray(32, 536870912 /* = 2^29 */);
	protected ArrayList<IntArray> outEdgeAdjacencyLists = new ArrayList<IntArray>();
	protected ArrayList<IntArray> inEdgeAdjacencyLists = new ArrayList<IntArray>();
	public static double[] factorial = new double[1000000];
	public static int total_degree;
	public static int nodesNumber = 0;
	public static int edgesNumber = 0;


	public Graph() {
		this(false);
	}

	public Graph(boolean directed) {
		this.directed = directed;
	}
	
	public void compute_factorial(int n){
		for(int i = 0; i<= n; i++){
			if(i == 0){
				factorial[i] = 0.0;
			}
			else{
				factorial[i] = factorial[i-1] + Math.log(i);
			}
		}
	}
	
	public void compute_total_degree(int n){
		total_degree = n;
	}
	

	public int createNode() {
		return this.createNode(null);
	}
	public int createNode(String name) {
		numberOfNodes++;
		nodesNumber++;
		outEdgeAdjacencyLists.add(new IntArray());
		inEdgeAdjacencyLists.add(new IntArray());
		nodeNames.add(name);
		return numberOfNodes-1;
	}
	public int[] createNodes(int new_node_count) {
		int[] result = new int[new_node_count];
		int n = numberOfNodes;
		for (int i = 0; i < new_node_count; i++) {
			outEdgeAdjacencyLists.add(new IntArray());
			inEdgeAdjacencyLists.add(new IntArray());
			nodeNames.add(null);
			result[i] = n+i;
		}
		numberOfNodes += new_node_count;
		return result;
	}



	public int createEdge(int src, int dest) {
		return createEdge(src, dest, 1.0);
	}
	public int createEdge(int src, int dest, double weight) {
		if (src >= numberOfNodes)
			createNodes(src - numberOfNodes + 1);
		if (dest >= numberOfNodes)
			createNodes(dest - numberOfNodes + 1);
		
		int edgeID = edgesOut.size();
		edgesOut.add(src);
		edgesIn.add(dest);
		weights.add(weight);
		outEdgeAdjacencyLists.get(src).add(edgeID);
		inEdgeAdjacencyLists.get(dest).add(edgeID);
		if (!directed) {
			outEdgeAdjacencyLists.get(dest).add(edgeID);
			inEdgeAdjacencyLists.get(src).add(edgeID);
		}
		edgesNumber++;
		return edgeID;
	}


	public int getNodeCount() {
		return numberOfNodes;
	}
	public int getEdgeCount() {
		return edgesOut.size();
	}
	public int[] getAdjacentNodeIndicesArray(int nodeIndex, Directedness mode) {
		int[] edges = this.getAdjacentEdgeIndicesArray(nodeIndex, mode);
		int i, n = edges.length;
		for (i = 0; i < n; i++) {
			int edge = edges[i];
			if (this.edgesIn.get(edge) == nodeIndex)
				edges[i] = this.edgesOut.get(edge);
			else
				edges[i] = this.edgesIn.get(edge);
		}
		return edges;
	}
	public int[] getAdjacentEdgeIndicesArray(int nodeIndex, Directedness mode) {
		if (!directed || mode == Directedness.OUT) {
			return outEdgeAdjacencyLists.get(nodeIndex).toArray();
		}
		
		if (mode == Directedness.IN) {
			return inEdgeAdjacencyLists.get(nodeIndex).toArray();
		}
		
		int[] outEdgesArray = outEdgeAdjacencyLists.get(nodeIndex).toArray();
		int[] inEdgesArray = inEdgeAdjacencyLists.get(nodeIndex).toArray();
		int i = outEdgesArray.length, n = i + inEdgesArray.length, j = 0;
		int[] result = new int[n];
		result = Arrays.copyOf(outEdgesArray, n);
		while (i < n) {
			result[i] = inEdgesArray[j];
			i++; j++;
		}
		return result;
	}
	public String getNodeName(int nodeIndex) { return this.nodeNames.get(nodeIndex); }
	public String[] getNodeNames() { return this.nodeNames.toArray(); }
	public double getEdgeWeight(int edgeIndex) { return this.weights.get(edgeIndex); }
	public double[] getEdgeWeights() {
		return this.weights.toArray();
	}
	public int getEdgeEndpoint(int edgeIndex, int knownVertex) {
		int idx = edgesOut.get(edgeIndex);
		if (idx == knownVertex)
			return edgesIn.get(edgeIndex);
		return idx;
	}
	public Iterator<Edge> iterator() {
		return new EdgeIterator(this);
	}
	public List<Edge> getEdgeList() {
		List<Edge> result = new ArrayList<Edge>(this.getEdgeCount());
		for (Edge edge: this)
			result.add(edge);
		return result;
	}
	public int getDegree(int nodeIndex) {
		return getDegree(nodeIndex, Directedness.ALL);
	}
	public int getDegree(NodeSet ns){
		int sub_degree = 0;
		SortedSet<Integer> nodes = ns.getMembers();		
        Iterator it = nodes.iterator();  
        while(it.hasNext()) {  
            int tmp = (Integer)it.next();  
            sub_degree += getDegree(tmp, Directedness.ALL);
        }   
		return sub_degree;
	}
	public int getDegree(int nodeIndex, Directedness mode) {
		if (!directed || mode == Directedness.OUT)
			return outEdgeAdjacencyLists.get(nodeIndex).size();
		if (mode == Directedness.IN)
			return inEdgeAdjacencyLists.get(nodeIndex).size();
		return outEdgeAdjacencyLists.get(nodeIndex).size() + inEdgeAdjacencyLists.get(nodeIndex).size();
	}
	public double getStrength(int nodeIndex) {
		return getStrength(nodeIndex, Directedness.ALL);
	}
	public double getStrength(int nodeIndex, Directedness mode) {
		IntArray neis = null;
		double result = 0.0;
		
		if (!directed || mode != Directedness.IN) {
			neis = outEdgeAdjacencyLists.get(nodeIndex);
			for (int eidx: neis.toArray())
				result += weights.get(eidx);
		}
		
		if (directed && mode == Directedness.IN) {
			neis = inEdgeAdjacencyLists.get(nodeIndex);
			for (int eidx: neis.toArray())
				result += weights.get(eidx);
		}
		return result;
	}
}
