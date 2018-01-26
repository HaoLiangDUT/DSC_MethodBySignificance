package com.liang.main;

import com.liang.util.IntHashSet;
import com.liang.collections.Multiset;
import com.liang.collections.TreeMultiset;
import com.liang.graph.Directedness;
import com.liang.graph.Edge;
import com.liang.graph.Graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class MutableNodeSet extends NodeSet {

	protected Multiset<Integer> externalBoundaryNodes = new TreeMultiset<Integer>();
	protected IntHashSet memberHashSet = new IntHashSet();
	public double[] inWeights = null;
	public double[] totalWeights = null;
	public MutableNodeSet(Graph graph) {
		super(graph);
		this.members = new TreeSet<Integer>();
		initializeInAndTotalWeights();
	}
	public MutableNodeSet(Graph graph, Collection<Integer> members) {
		this(graph);
		this.setMembers(members);
	}
	public MutableNodeSet(Graph graph, int[] members) {
		this(graph);
		this.setMembers(members);
	}
	private MutableNodeSet(MutableNodeSet nodeSet) {
		super(nodeSet.graph);

		this.members = new TreeSet<Integer>(nodeSet.members);
		for (int member: members) {
			this.memberHashSet.add(member);
		}

		totalInternalEdgeWeight = nodeSet.totalInternalEdgeWeight;
		totalBoundaryEdgeWeight = nodeSet.totalBoundaryEdgeWeight;

		inWeights = nodeSet.inWeights.clone();

		// totalWeights does not have to be cloned because the graph is the same,
		// therefore totalWeights is also the same
		totalWeights = nodeSet.totalWeights;
	}
	public MutableNodeSet(NodeSet nodeSet) {
		this(nodeSet.getGraph(), nodeSet.getMembers());
	}
	
	protected void initializeInAndTotalWeights() {
		int n = graph.getNodeCount();

		totalInternalEdgeWeight = 0.0;
		totalBoundaryEdgeWeight = 0.0;
		
		if (inWeights == null) {
			inWeights = new double[n];
		} else {
			// Optimization here: we can be sure that the only nonzero elements in inWeights are
			// for internal or boundary nodes, so it is enough to iterate over them if the graph
			// is large. Otherwise it is probably faster to simply fill the entire array with zeros.
			if (n >= 5000 && members.size() < n / 4) {
				for (int member: members) {
					inWeights[member] = 0.0;
				}
				for (int boundaryNode: externalBoundaryNodes.elementSet()) {
					inWeights[boundaryNode] = 0.0;
				}
			} else {
				Arrays.fill(inWeights, 0.0);
			}
		}

		if (totalWeights == null) {
			totalWeights = new double[n];
			for (Edge e: graph) {
				totalWeights[e.source] += e.weight;
				totalWeights[e.target] += e.weight;
			}
		}
	}
	public boolean add(int node) {
		if (memberHashSet.contains(node))
			return false;
		
		/* Things will change, invalidate the cached values */
		invalidateCache();
		
		/* First, increase the internal and the boundary weights with the
		 * appropriate amounts. Here we are actually increasing totalBoundaryEdgeWeight
		 * by outWeights[node] - inWeights[node] but make use of the fact that
		 * outWeights[node] = totalWeights[node] - inWeights[node] */
		totalInternalEdgeWeight += inWeights[node];
		totalBoundaryEdgeWeight += totalWeights[node] - 2 * inWeights[node];
		
		/* For each edge incident on the given node, make some adjustments to inWeights */
		for (int adjEdge: graph.getAdjacentEdgeIndicesArray(node, Directedness.ALL)) {
			int adjNode = graph.getEdgeEndpoint(adjEdge, node);
			if (adjNode == node)
				continue;
			
			inWeights[adjNode] += graph.getEdgeWeight(adjEdge);

			if (!memberHashSet.contains(adjNode)) {
				externalBoundaryNodes.add(adjNode);
			}
		}
		
		/* Add the node to the nodeset */
		memberHashSet.add(node);
		members.add(node);
		externalBoundaryNodes.setCount(node, 0);
		
		return true;
	}
	public int add(int[] nodes) {
		int result = 0;
		
		for (int i: nodes)
			if (this.add(i))
				result++;
		
		return result;
	}

	public void clear() {
		/* Things will change, invalidate the cached values */
		invalidateCache();

		/* This must be called _before_ we clear the members because it uses the old members */
		initializeInAndTotalWeights();

		externalBoundaryNodes.clear();
		members.clear();
		memberHashSet.clear();
	}

	public MutableNodeSet clone() {
		return new MutableNodeSet(this);
	}
	public NodeSet freeze() {
		return new NodeSet(this.graph, this.members);
	}

	@Override
	public int[] getExternalBoundaryNodes() {
		Set<Integer> nodes = externalBoundaryNodes.elementSet();
		int[] result = new int[nodes.size()];
		int i = 0;
		for (int member: nodes) {
			result[i++] = member;
		}
		return result;
	}

	@Override
	public double getInternalWeight(int nodeIndex) {
		return this.inWeights[nodeIndex];
	}
	public double getTotalAdjacentInternalWeight(int nodeIndex) {
		return this.inWeights[nodeIndex];
	}
	protected IntHashSet getMemberHashSet() {
		return memberHashSet;
	}
	private void invalidateCache() {
	}
	public boolean remove(int node) {
		if (!memberHashSet.contains(node))
			return false;
		invalidateCache();
		totalInternalEdgeWeight -= inWeights[node];
		totalBoundaryEdgeWeight -= totalWeights[node] - 2 * inWeights[node];
		
		/* For each edge incident on the given node, make some adjustments to inWeights */
		for (int adjEdge: graph.getAdjacentEdgeIndicesArray(node, Directedness.ALL)) {
			int adjNode = graph.getEdgeEndpoint(adjEdge, node);
			if (adjNode == node)
				continue;
			
			inWeights[adjNode] -= graph.getEdgeWeight(adjEdge);

			if (memberHashSet.contains(adjNode)) {
				externalBoundaryNodes.add(node);
			} else {
				externalBoundaryNodes.remove(adjNode);
			}
		}
		
		/* Remove the node from the nodeset */
		memberHashSet.remove(node);
		members.remove(node);

		return true;
	}

	public void remove(int[] nodes) {
		for (int i: nodes)
			this.remove(i);
	}
	@Override
	protected void setMembers(int[] members) {
		this.clear();
		for (int member: members)
			this.add(member);
	}
	public void setMembers(Iterable<Integer> members) {
		this.clear();
		for (int member: members)
			this.add(member);
	}
}
