import com.liang.graph.Graph;
import com.liang.main.MYMethod;
import com.liang.main.MutableNodeSet;
import com.liang.main.NodeSet;
import com.liang.main.ReadTrueCommunity;
import com.liang.quality.CohesivenessFunction;
import com.liang.quality.QualityFunction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main_test {

	public static void main(String[] args){

		Graph graph = new Graph(false);
		File file = new File("./datasets/LFR1.txt");
		BufferedReader reader = null;
		 try {
			    HashMap<String , Integer> map = new HashMap<String , Integer>();
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            while ((tempString = reader.readLine()) != null) {
	            	String str[] = tempString.split("\\s+");
	            	if(!map.containsKey(str[0])){
	            		int index = graph.createNode(str[0]);
	            		map.put(str[0], index);
	            	}
	            	if(!map.containsKey(str[1])){
	            		int index = graph.createNode(str[1]);
	            		map.put(str[1], index);
	            	}
	            	graph.createEdge(map.get(str[0]), map.get(str[1]));
	            }
	            int d = 2 * graph.getEdgeCount();
			 	//System.out.println(Graph.nodesNumber);
			    int maxEdges = (Graph.nodesNumber * (Graph.nodesNumber-1)) / 2;
	    		graph.compute_factorial(d);
	    		graph.compute_total_degree(d);



			 /* 真实社区*/
			 /*
			 ArrayList<Double> condutanceList = new ArrayList<>();
			 ArrayList<Double> pvalueList = new ArrayList<>();
			 ArrayList<Double> ratioCutList = new ArrayList<>();
			 ArrayList<Double> modularityList = new ArrayList<>();
			 QualityFunction qualityFunction = new CohesivenessFunction();
			 HashMap<Integer,ArrayList<String>> hashMap = new HashMap<>();
			 ReadTrueCommunity readTrueCommunity = new ReadTrueCommunity();
			 hashMap = readTrueCommunity.doRead();
			 Iterator iter = hashMap.entrySet().iterator();
			 while(iter.hasNext()) {
				 Map.Entry entry = (Map.Entry) iter.next();
				 Integer key = (Integer) entry.getKey();

				 ArrayList<String> val = (ArrayList<String>)entry.getValue();
				 //System.out.println(key+"----" + map.get("3"));
				 MutableNodeSet nodeSet = new MutableNodeSet(graph);
				 for(int i = 0 ; i < val.size() ; i++)
				 {
					 if(map.get(val.get(i)) != null)
					 nodeSet.add(map.get(val.get(i)));
					// System.out.print(map.get(val.get(i)) + " ");
				 }
				 pvalueList.add(qualityFunction.calculate(nodeSet));
				 condutanceList.add(qualityFunction.computeConductance(nodeSet));
				 ratioCutList.add(qualityFunction.computeRatioCut(nodeSet));
				 modularityList.add(qualityFunction.computeModularity(nodeSet,graph));
				 System.out.println();
			 }

			 for(int i = 0 ; i < condutanceList.size(); i++)
			 {
				 System.out.print(condutanceList.get(i) + " ");
			 }
			 System.out.println();
			 for(int i = 0 ; i < pvalueList.size(); i++)
			 {
				 System.out.print(pvalueList.get(i) + " ");
			 }
			 System.out.println();
			 for(int i = 0 ; i < ratioCutList.size(); i++)
			 {
				 System.out.print(ratioCutList.get(i) + " ");
			 }
			 System.out.println();
			 for(int i = 0 ; i < modularityList.size(); i++)
			 {
				 System.out.print(modularityList.get(i) + " ");
			 }
			 System.out.println();
			*/


			 MYMethod myMethod = new MYMethod(graph);myMethod.runONGraph();



//			 CohesivenessFunction cohesivenessFunction = new CohesivenessFunction();
//			 System.out.println("asdasd " + cohesivenessFunction.logHyperProbability(78,41,6555,613));
//			 System.out.println("asdasd " + cohesivenessFunction.logHyperProbability(78,50,6555,613));

//			 MutableNodeSet testNodeSet = new MutableNodeSet(graph);
//			 MutableNodeSet testNodeSet1 = new MutableNodeSet(graph);
//			 int []nodes = {0,1};
//			 int []nodes1 = {0,1,2};
//			 //int []nodes = {28,46,49,53,58,67,73,88,114};
//			 //int []nodes1 = {46,49,53,67,73,83,88,110,114};
//			 testNodeSet.add(nodes);
//			 testNodeSet1.add(nodes1);
//			 CohesivenessFunction cohesivenessFunction = new CohesivenessFunction();
//			 System.out.println(cohesivenessFunction.getAdditionAffinity(testNodeSet,2));
//			 System.out.println(cohesivenessFunction.getRemovalAffinity(testNodeSet1,2));




			 reader.close();

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }

	}
}
