package com.liang.quality;

import com.liang.graph.Graph;
import com.liang.main.MutableNodeSet;
import com.liang.main.NodeSet;

public interface QualityFunction {
	public double computeConductance(NodeSet nodeSet);
	public double computeRatioCut(NodeSet nodeSet);
	public double computeModularity(NodeSet nodeSet, Graph graph);
	public double calculate(NodeSet nodeSet);
	public double getAdditionAffinity(MutableNodeSet nodeSet, int index);
	public double getRemovalAffinity(MutableNodeSet nodeSet, int index);
}
