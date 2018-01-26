package com.liang.graph;

import java.util.Iterator;
public class EdgeIterator implements Iterator<Edge> {
	protected Graph graph = null;
	public int edgeIndex = 0;
	public EdgeIterator(Graph graph) {
		this.graph = graph;
		this.edgeIndex = 0;
	}
	public boolean hasNext() {
		return this.edgeIndex < this.graph.getEdgeCount();
	}
	public Edge next() {
		Edge result = new Edge(this.graph, this.edgeIndex);
		this.edgeIndex++;
		return result;
	}
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
