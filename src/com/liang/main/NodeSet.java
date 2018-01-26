package com.liang.main;

import com.liang.util.IntArray;
import com.liang.util.IntHashSet;
import com.liang.graph.Directedness;
import com.liang.graph.Graph;
import com.liang.util.StringUtils;

import java.util.*;



public class NodeSet implements Iterable<Integer>{
	protected Graph graph = null;
	protected SortedSet<Integer> members = null;
	public double totalInternalEdgeWeight = 0.0;
	public double totalBoundaryEdgeWeight = 0.0;

	public NodeSet() {
		this.graph = null;
	}
	public NodeSet(Graph graph) {
		this.graph = graph;
	}
	public NodeSet(Graph graph, Collection<Integer> members) {
		this(graph);
		this.setMembers(members);
	}
	public NodeSet(Graph graph, int[] members) {
		this(graph);
		this.setMembers(members);
	}
	public boolean contains(int idx) {
		return members.contains(idx);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof NodeSet))
			return false;

		NodeSet other = (NodeSet)o;
		return other.graph.equals(this.graph) && other.members.equals(this.members);
	}

	public Graph getGraph() {
		return graph;
	}
	protected IntHashSet getMemberHashSet() {
		// We use an IntHashSet for membership checks, it's more efficient
		IntHashSet memberSet = new IntHashSet();
		for (int i: members)
			memberSet.add(i);
		return memberSet;
	}
	public SortedSet<Integer> getMembers() {
		return new TreeSet<Integer>(members);
	}
	public String[] getMemberNames() {
		String[] result = new String[this.members.size()];
		int i = 0;
		for (Integer member: this.members) {
			result[i] = this.graph.getNodeName(member);
			i++;
		}
		
		return result;
	}
	public int hashCode() {
		return graph.hashCode() + members.hashCode();
	}
	public boolean isEmpty() {
		return this.members.isEmpty();
	}
	public int size() {
		return this.members.size();
	}
	protected void setMembers(Iterable<Integer> members) {
		this.members = new TreeSet<Integer>();
		if (members == null)
			return;
		
		for (Integer member: members)
			this.members.add(member);
		
		recalculate();
	}
	protected void setMembers(int[] members) {
		this.members = new TreeSet<Integer>();
		if (members == null)
			return;
		
		for (int member: members)
			this.members.add(member);
		
		recalculate();
	}
	protected void recalculate() {
		IntHashSet memberHashSet = this.getMemberHashSet();
		
		this.totalBoundaryEdgeWeight = 0.0;
		this.totalInternalEdgeWeight = 0.0;
		
		for (int i: members) {
			int[] edgeIdxs = this.graph.getAdjacentEdgeIndicesArray(i, Directedness.ALL);
			for (int edgeIdx : edgeIdxs) {
				double weight = this.graph.getEdgeWeight(edgeIdx);
				int endpoint = this.graph.getEdgeEndpoint(edgeIdx, i);
				if (memberHashSet.contains(endpoint)) {
					/* This is an internal edge */
					this.totalInternalEdgeWeight += weight;
				} else {
					/* This is a boundary edge */
					this.totalBoundaryEdgeWeight += weight;
				}
			}
		}
		/* Internal edges were found twice, divide the result by two */
		this.totalInternalEdgeWeight /= 2.0;
	}
	public double getInternalWeight(int nodeIndex) {
		IntHashSet memberHashSet = this.getMemberHashSet();
		double result = 0.0;
		int[] edgeIdxs = this.graph.getAdjacentEdgeIndicesArray(nodeIndex, Directedness.ALL);
		for (int edgeIdx: edgeIdxs) {
			double weight = this.graph.getEdgeWeight(edgeIdx);
			int endpoint = this.graph.getEdgeEndpoint(edgeIdx, nodeIndex);
			if (memberHashSet.contains(endpoint)) {
				/* This is an internal edge */
				result += weight;
			}
		}
		
		return result;
	}
	public NodeSet getIntersectionWith(NodeSet other) {
		Set<Integer> smaller;
		IntHashSet larger;
		IntArray intersection = new IntArray();
		
		if (this.size() < other.size()) {
			smaller = this.members;
			larger = other.getMemberHashSet();
		} else {
			smaller = other.members;
			larger = this.getMemberHashSet();
		}
		
		for (int member: smaller)
			if (larger.contains(member))
				intersection.add(member);
		
		return new NodeSet(this.getGraph(), intersection.toArray());
	}
	public int getIntersectionSizeWith(NodeSet other) {
		int isectSize = 0;
		Set<Integer> smaller;
		Set<Integer> larger;

		if (this.size() < other.size()) {
			smaller = this.members;
			larger = other.members;
		} else {
			smaller = other.members;
			larger = this.members;
		}
		
		for (int member: smaller)
			if (larger.contains(member))
				isectSize++;
		
		return isectSize;
	}
	public double getTotalInternalEdgeWeight() {
		return this.totalInternalEdgeWeight;
	}
	public double getTotalBoundaryEdgeWeight() {
		return this.totalBoundaryEdgeWeight;
	}
	public int[] getExternalBoundaryNodes() {
		IntHashSet seen = new IntHashSet(this.getMemberHashSet());
		IntArray result = new IntArray();

		for (int i: members) {
			int[] edgeIdxs = this.graph.getAdjacentEdgeIndicesArray(i, Directedness.ALL);
			for (int edgeIdx: edgeIdxs) {
				int endpoint = this.graph.getEdgeEndpoint(edgeIdx, i);
				if (!seen.contains(endpoint)) {
					/* This is an external boundary node that we haven't seen yet */
					seen.add(endpoint);
					result.add(endpoint);
				}
			}
		}
		return result.toArray();
	}
	public Iterator<Integer> iterator() {
		return this.members.iterator();
	}
	public int[] toArray() {
		int i = 0;
		int[] result = new int[members.size()];
		for (int member: members) {
			result[i++] = member;
		}
		return result;
	}
	public String toString() {
		return toString(" ");
	}
	public String toString(String separator) {
		return StringUtils.join(getMemberNames(), separator);
	}
}
