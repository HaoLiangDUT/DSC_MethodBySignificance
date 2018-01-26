package com.liang.graph;

public class Edge {
	public int index;
	public int source;
	public int target;
	public double weight;
	public Edge(Graph graph, int edgeIndex) {
		this.index = edgeIndex;
		this.target = graph.edgesIn.get(edgeIndex);
		this.source = graph.edgesOut.get(edgeIndex);
		this.weight = graph.weights.get(edgeIndex);
	}
	public String toString() {
		return this.source+" --> "+this.target+": "+this.weight;
	}
}
