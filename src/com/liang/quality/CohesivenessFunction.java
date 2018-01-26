package com.liang.quality;

import com.liang.main.MutableNodeSet;
import com.liang.main.NodeSet;
import com.liang.graph.Graph;

public class CohesivenessFunction implements QualityFunction {


	public double computeConductance(NodeSet nodeSet)
	{
		double conductance = 0 ;
		double m_out = nodeSet.totalBoundaryEdgeWeight;
		double m_in = nodeSet.totalInternalEdgeWeight;
		conductance = (m_out)/(2 * m_in + m_out);
		return conductance;
	}
	public double computeRatioCut(NodeSet nodeSet)
	{
		double ratioCut = 0;
		double m_out = nodeSet.totalBoundaryEdgeWeight;
		double nodes = nodeSet.size();
		ratioCut = m_out/nodes;
		return  ratioCut;
	}
	public double computeModularity(NodeSet nodeSet,Graph graph)
	{
		double modularity = 0;
		double edges = graph.getEdgeCount();
		double m_in = nodeSet.totalInternalEdgeWeight;
		double m_out = nodeSet.totalBoundaryEdgeWeight;
	 	modularity = m_in/edges - Math.pow((m_in+m_out)/(2*edges),2);
		return  modularity;
	}




	public double logHyperProbability(double a,double b,double c,double d)
	{
		double logH = logC(a,b) - logC(c-a,d-b) - logC(c,d);
		return logH/Math.log(10);
	}

	public double logC(double a,double b)
	{
		if(a == b || b == 0)
		{
			return 0;
		}
		else if(a >= 1000 && b >= 1000)
		{
			return a * Math.log(a) - (a-b) * Math.log(a-b) -b * Math.log(b);
		}
		else
		{
			return Graph.factorial[(int)a] - Graph.factorial[(int)b] - Graph.factorial[(int)(a-b)];
		}
	}

	public double sumLog(double nextP,double P){
		double common;
		double diffExponent;
		if(nextP > P){
			common = nextP;
			diffExponent = nextP - P;
		}
		else{
			common = P;
			diffExponent = P - nextP;
		}
		double result = common + ((Math.log(1+Math.pow(10,diffExponent)))) / Math.log(10);
		return result;
	}




	/*
	 compute p-value
	 */
	public double calculate(NodeSet nodeSet)
	{
		double Da,Ds,Dg; // 图的度和 子图的度和 剩余度和
		Ds = nodeSet.totalInternalEdgeWeight * 2 + nodeSet.totalBoundaryEdgeWeight;
		Da = Graph.total_degree;
		Dg = Graph.total_degree - Ds;
		double m_out = nodeSet.totalBoundaryEdgeWeight;
		double m_in = nodeSet.totalInternalEdgeWeight;
		double graphNodesNumber = Graph.nodesNumber;
		double nodesetNodesNumber = nodeSet.size();
		//System.out.println("out="+m_out+" in=" +m_in + " D=" + Da + " Ds=" + Ds);
		double item1 = Graph.factorial[(int)Ds] - ( Graph.factorial[(int)(m_in * 2)] + Graph.factorial[(int)(Ds - 2 * m_in)] );
		double item2 = Graph.factorial[(int)Dg] - ( Graph.factorial[(int)m_out] + Graph.factorial[(int) (Dg-m_out)] );
		double item3 = Graph.factorial[(int)Da] - ( Graph.factorial[(int)Ds] + Graph.factorial[(int)(Da-Ds)] );
		//System.out.println("item1 = " + item1 + "item2 = " +item2 + "item3 = " + item3 + "   " + Math.exp(item1+item2));
		// fisher 求和
		double result = 0;
//		int nodeNumber = nodeSet.size();
//		for(int j = (int)m_in; j <=(Ds)/2;j++)
//		{
//			int i = 0;
//			i = j*2;
//			double temp1 = Graph.factorial[(int)Ds] - ( Graph.factorial[i] + Graph.factorial[(int)(Ds - i)] );
//			double temp2 = Graph.factorial[(int)Dg] - ( Graph.factorial[(int)(Ds-i)] + Graph.factorial[(int) (Dg-Ds+i)] );
//			double temp3 = Graph.factorial[(int)Da] - ( Graph.factorial[(int)Ds] + Graph.factorial[(int)(Da-Ds)] );
//		//	System.out.println("temp1 = " + temp1 + "temp2 = " +temp2 + "temp3 = " + temp3 + "  " + Math.exp(temp1+temp2-temp3));
//			result = result + Math.exp(temp1+temp2-temp3+500);
//		//	System.out.println("result+=" + result);
//		}
		//System.out.println();
		result = Math.exp(item1+item2-item3);
		double q1 = (m_out*m_out)/((2*m_in)*(Dg-m_out));
		double upperbound = (1-Math.pow(q1,m_out+1))/(1-q1);
		result = result*upperbound;
		//System.out.println("item1 = " + item1 + " item2 = " +item2 + " item3 = " + item3 + "  upper = " + upperbound );
		//System.out.println("calculate = "  + (item1+item2-item3+Math.log(upperbound)));
		return item1+item2-item3+Math.log(upperbound);
	}



	 /*
	 compute the p-value when adding a node into subgraph
	  */
	public double getAdditionAffinity( MutableNodeSet nodeSet, int index)
	{
		double Da,Ds,Dg;
		Da = Graph.total_degree;
		Ds = nodeSet.totalInternalEdgeWeight * 2+ nodeSet.totalBoundaryEdgeWeight + nodeSet.totalWeights[index];
		Dg = Da - Ds;
		double m_out = nodeSet.totalBoundaryEdgeWeight - nodeSet.inWeights[index] + (nodeSet.totalWeights[index] - nodeSet.inWeights[index]);
		double m_in = nodeSet.totalInternalEdgeWeight + nodeSet.inWeights[index];
		double graphNodesNumber = Graph.nodesNumber;
		double nodesetNodesNumber = nodeSet.size();
		double item1 = Graph.factorial[(int)Ds] - ( Graph.factorial[(int)(m_in * 2)] + Graph.factorial[(int)(Ds - 2 * m_in)] );
		double item2 = Graph.factorial[(int)Dg] - ( Graph.factorial[(int)m_out] + Graph.factorial[(int) (Dg-m_out)] );
		double item3 = Graph.factorial[(int)Da] - ( Graph.factorial[(int)Ds] + Graph.factorial[(int)(Da-Ds)] );
		// fisher 求和
		double result = 0;
//		int nodeNumber = nodeSet.size();
//		int i;
//		for(int j = (int)m_in; j <=(Ds)/2;j++)
//		{
//			i = j*2;
//			double temp1 = Graph.factorial[(int)Ds] - ( Graph.factorial[i] + Graph.factorial[(int)(Ds - i)] );
//			double temp2 = Graph.factorial[(int)Dg] - ( Graph.factorial[(int)(Ds-i)] + Graph.factorial[(int) (Dg-Ds+i)] );
//			double temp3 = Graph.factorial[(int)Da] - ( Graph.factorial[(int)Ds] + Graph.factorial[(int)(Da-Ds)] );
//			result = result + Math.exp(temp1+temp2-temp3+500);
//		}
		result = Math.exp(item1+item2-item3);
		double q1 = (m_out*m_out)/((2*m_in)*(Dg-m_out));
		double upperbound = (1-Math.pow(q1,m_out))/(1-q1);
		result = result*upperbound;
		return item1+item2-item3+Math.log(upperbound);
	}




	/*
	compute p-value when remove a node from subgraph
	 */
	public double getRemovalAffinity(MutableNodeSet nodeSet, int index)
	{
		double Da,Ds,Dg;
		Da = Graph.total_degree;
		Ds = nodeSet.totalInternalEdgeWeight * 2 + nodeSet.totalBoundaryEdgeWeight - nodeSet.totalWeights[index];
		Dg = Da - Ds;
		double m_out = nodeSet.totalBoundaryEdgeWeight -(nodeSet.totalWeights[index] - nodeSet.inWeights[index]) + nodeSet.inWeights[index];
		double m_in = nodeSet.totalInternalEdgeWeight - nodeSet.inWeights[index];
		double graphNodesNumber = Graph.nodesNumber;
		double nodesetNodesNumber = nodeSet.size();
		double item4 = Graph.factorial[(int)graphNodesNumber] - (Graph.factorial[(int)nodesetNodesNumber] + Graph.factorial[(int)(graphNodesNumber-nodesetNodesNumber)]);
		double item1 = Graph.factorial[(int)Ds] - ( Graph.factorial[(int)(m_in * 2)] + Graph.factorial[(int)(Ds - 2 * m_in)] );
		double item2 = Graph.factorial[(int)Dg] - ( Graph.factorial[(int)m_out] + Graph.factorial[(int) (Dg-m_out)] );
		double item3 = Graph.factorial[(int)Da] - ( Graph.factorial[(int)Ds] + Graph.factorial[(int)(Da-Ds)] );
		// fisher 求和
		double result = 0;
//		int nodeNumber = nodeSet.size();
//		int i;
//		for(int j = (int)m_in; j <=(Ds)/2;j++)
//		{
//			i = j*2;
//			double temp1 = Graph.factorial[(int)Ds] - ( Graph.factorial[i] + Graph.factorial[(int)(Ds - i)] );
//			double temp2 = Graph.factorial[(int)Dg] - ( Graph.factorial[(int)(Ds-i)] + Graph.factorial[(int) (Dg-Ds+i)] );
//			double temp3 = Graph.factorial[(int)Da] - ( Graph.factorial[(int)Ds] + Graph.factorial[(int)(Da-Ds)] );
//			result = result + Math.exp(temp1+temp2-temp3+500);
//		}
		double q1 = (m_out*m_out)/((2*m_in)*(Dg-m_out));
		double upperbound = (1-Math.pow(q1,m_out))/(1-q1);
		return item1+item2-item3+Math.log(upperbound);
	}

}
