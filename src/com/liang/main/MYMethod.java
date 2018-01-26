package com.liang.main;

import com.liang.quality.CohesivenessFunction;
import com.liang.quality.QualityFunction;
import com.liang.graph.Directedness;
import com.liang.graph.Graph;
import com.liang.util.StringUtils;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2017/7/7.
 */
public class MYMethod {

    public static int hypothesisCount = 0;
    private Graph graph;
    private Map<Integer,Integer> hashMapDegree;
    private int count;

    // 存放每个节点的聚集系数
    private static double[] jujixishu ;
    private Map<Integer,Double> hashMapjujixishu;





    public MYMethod(Graph graph)
    {
        count = 0;
        this.graph = graph;
        int nodeSize = graph.getNodeCount();
        int []degree = new int[nodeSize];
        // 初始化存储所有节点的index及其对应的度
        double dede = 0;
        hashMapDegree = new HashMap<Integer, Integer>();
        for(int i = 0 ; i < nodeSize; i++)
        {
            hashMapDegree.put(i,graph.getDegree(i));
            dede += graph.getDegree(i);
        }
        System.out.println("average degree = " + dede/graph.getNodeCount());

        jujixishu = new double[nodeSize];
        // 计算每个节点的聚集系数
        for(int i = 0 ; i < nodeSize ;i++)
        {
            int [] adjacentNodes = graph.getAdjacentEdgeIndicesArray(i,Directedness.ALL);
            int adjacentNodesCount = adjacentNodes.length;
            int [] adjacentEdges = graph.getAdjacentEdgeIndicesArray(i,Directedness.ALL);
            int adjacentEdgesCount = adjacentEdges.length;
            //System.out.println("Ei = "+ adjacentEdgesCount + " Ki =" + adjacentNodesCount);
            jujixishu[i] =(double)(2 * adjacentEdgesCount) / (adjacentNodesCount * (adjacentNodesCount - 1));
            //System.out.println("聚集系数" + i + "= " +jujixishu[i]);
        }
        hashMapjujixishu = new HashMap<>();
        for(int i = 0 ; i < nodeSize ;i++)
        {
            hashMapjujixishu.put(i,jujixishu[i]);
        }

    }

    public void runONGraph() throws IOException {
        ArrayList<Double> condutanceList = new ArrayList<>();
        ArrayList<Double> pvalueList = new ArrayList<>();



        // 将节点的度排序 list.get(0) 拿到最大的map.entry
        List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(hashMapDegree.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        //System.out.println(list.get(0).getKey());

        List<Map.Entry<Integer, Double>> listJu = new ArrayList<Map.Entry<Integer, Double>>(hashMapjujixishu.entrySet());
        Collections.sort(listJu, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
       // System.out.println(listJu.get(0).getKey());


        // 注意这里存放度的list和存放节点的list均是自己定义的int值为标记，并不是图中节点的index
        // 现在改为key以及存放节点的list中存放的全是index

        // 初始化arraylist存放所有节点，当其为空时程序停止。
        ArrayList<Integer> arrayListNodes = new ArrayList<>();
        for (int i = 0 ; i < graph.getNodeCount(); i++)
        {
            arrayListNodes.add(i);
        }

        // runOnce的测试代码

        /*
        int nodeIndex = list.get(0).getKey();
        int [] outNodes = graph.getAdjacentNodeIndicesArray(nodeIndex, Directedness.ALL);
        MutableNodeSet nodeSet = new MutableNodeSet(graph);
        nodeSet.add(nodeIndex);
        nodeSet.add(outNodes);
        MutableNodeSet resultSet = new MutableNodeSet(graph);
        resultSet = runOnce(nodeSet);
        for (int i = 0 ; i < resultSet.size() ; i++)
        {
            System.out.print(resultSet.getMemberNames()[i]+ " ");
        }
        */

        // 存放去冗余的nodeset集合。
        ArrayList<String[]> arrayListResult = new ArrayList<String[]>();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("./result/resultTemp.txt"))));

        while(!arrayListNodes.isEmpty()){

            if(list.isEmpty()) break;
            Map.Entry<Integer,Integer> map = list.get(0);
           // Map.Entry<Integer,Double> map = listJu.get(0);

            int nodeIndex = map.getKey();
            if(graph.getDegree(nodeIndex) == 1) break;
            System.out.println("nodeIndex="+nodeIndex + " degree = " + map.getValue());
            MutableNodeSet nodeSet = new MutableNodeSet(graph);
            int [] outNodes = graph.getAdjacentNodeIndicesArray(nodeIndex, Directedness.ALL);
            for(int i = 0 ; i < outNodes.length;i++){System.out.print(outNodes[i] + " ");}System.out.println();
            nodeSet.add(nodeIndex);
            nodeSet.add(outNodes);
            MutableNodeSet resultSet = new MutableNodeSet(graph);
            resultSet = runOnce(nodeSet);
            count++;
            QualityFunction qualityFunction = new CohesivenessFunction();
            double resultPvalue = qualityFunction.calculate(resultSet);
            System.out.println("resultPvalue="+ resultPvalue);
            //System.out.println("resultSize="  + resultSet.size());

            if(resultSet != null) {
                if(qualityFunction.computeConductance(resultSet) < 1 && resultSet.size() > 2) {
                    arrayListResult.add(resultSet.getMemberNames());
                    pvalueList.add(qualityFunction.calculate(resultSet));
                    condutanceList.add(qualityFunction.computeConductance(resultSet));
                }

                for (int a : resultSet) {
                    // 从排序好的hashMap中删除掉结果集合中的所有节点
                    for (Iterator it = list.iterator(); it.hasNext(); ) {
                        Map.Entry<Integer, Integer> map1 = (Map.Entry<Integer, Integer>) it.next();
                        if (map1.getKey() == a || map1.getKey() == nodeIndex) {
                            it.remove();
                        }
                    }

                    // 从存储所有节点的集合中删除掉结果集合中的所有节点
                    for (Iterator it = arrayListNodes.iterator(); it.hasNext(); ) {
                        if ((int) it.next() == a) {
                            it.remove();
                        }
                    }
                }

                //打印结果集节点index
                if(resultSet.size() > 2)
                {
                    TreeSet<Integer> treeSet = (TreeSet<Integer>) resultSet.getMembers();
                    Iterator<Integer> iterator = treeSet.iterator();
                    while (iterator.hasNext())
                    {
                        System.out.print(iterator.next() + " ");
                    }
                    System.out.println();
                }


                //打印结果集真实名字
                if (resultSet.size() > 2) {
//                    for(int i = 0 ; i < resultSet.size(); i++)
//                    {
//                        System.out.print(resultSet.getMemberNames()[i] + " " );
//                    }
//                    System.out.println();

                    String[] names = resultSet.getMemberNames();

//                    // 如果是数字的话想排序好输出
//                    for(int i = 0 ; i < names.length ; i++)
//                    {
//                        for(int j = i ; j < names.length ; j++)
//                        {
//                            if(Integer.parseInt(names[i]) > Integer.parseInt(names[j]))
//                            {
//                                String change = names[i];
//                                names[i] = names[j];
//                                names[j] = change;
//                            }
//                        }
//                    }
                    for(int i = 0 ; i < names.length; i++)
                    {
                        System.out.print(names[i] + " " );
                    }
                    System.out.println();

                    String str = StringUtils.join(names, "\t");
                    bw.write(str);
                    bw.newLine();
                }
            }

            System.out.println();
        }
        bw.close();


        // 结果去冗余
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("./result/FinalResult.txt"))));
        RemoveRedundancy removeRedundancy = new RemoveRedundancy();
        ArrayList<String[]> result = removeRedundancy.removeRedundacy(arrayListResult);
        System.out.println("FinalResultSize = " + result.size());
//        for(int i = 0 ; i < result.size();i++)
//        {
//            String [] jk = result.get(i);
//            for(int j = 0 ; j < jk.length ;j++)
//            {
//                for(int k = j ; k < jk.length;k++ )
//                {
//                    if(Integer.parseInt(jk[j]) > Integer.parseInt(jk[k]))
//                    {
//                        String temp = jk[j];
//                        jk[j] = jk[k];
//                        jk[k] = temp;
//                    }
//                }
//            }
//        }
        for(int i = 0 ; i < result.size();i++)
        {
            for(int j = 0 ; j < result.get(i).length ; j++) {
                System.out.print(result.get(i)[j] + " ");
            }
            System.out.println();
            String [] resultNames = result.get(i);
            String str = StringUtils.join(resultNames, "\t");
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }
       // System.out.println("asd" + result.get(0).length);
        bufferedWriter.close();




    }




    private MutableNodeSet runOnce(MutableNodeSet nodeSet)
    {
        MutableNodeSet nowNodeset = new MutableNodeSet(graph);
        nowNodeset = nodeSet;
        QualityFunction qualityFunction = new CohesivenessFunction();
        double pvalue = qualityFunction.calculate(nodeSet);
        System.out.println("pvalue=" + pvalue);
        double bestPvalue = pvalue;
        boolean isAddBest = true;
        int addORdelNode = -1 ;

        // 首先在nodeSet外部的所有节点中找到增加进去使得pvalue最小的节点
        double lastPvalue = bestPvalue;
        for (int node: nodeSet.getExternalBoundaryNodes())
        {
            double pvalueAdd = qualityFunction.getAdditionAffinity(nodeSet,node);
           // System.out.println("pvalueAdd = " + pvalueAdd + "  node = " + node);
            //if(pvalueAdd < bestPvalue && pvalueAdd!=0 &&( -Math.log(pvalueAdd) - -Math.log(bestPvalue) > 6))
            if(pvalueAdd < bestPvalue && (lastPvalue-pvalueAdd) > 12)
            {
                bestPvalue = pvalueAdd;
                addORdelNode = node;
            }
            hypothesisCount++;
        }

        // 在nodeSet中的所有节点找到删除一个节点使得pvalue最小的节点
        lastPvalue = bestPvalue;
        for (int node:nodeSet)
        {
            double pvalueDel = qualityFunction.getRemovalAffinity(nodeSet,node);

            //if(  (pvalueDel < bestPvalue && pvalueDel!=0)  || ( (-Math.log(bestPvalue) - -Math.log(pvalueDel)) < 2 && pvalueDel!=1))
            if(pvalueDel < bestPvalue && (lastPvalue-pvalueDel) > 0)
            {
                bestPvalue = pvalueDel;
                addORdelNode = node;
                isAddBest = false;
            }
            hypothesisCount++;
        }

        System.out.println("bestPvalue = " + bestPvalue);
        System.out.println("addORdel = " + addORdelNode);
        System.out.println("isAddBest = " + isAddBest);


        //  如果这一回合没有加点也没有删点的话，返回结果集。如果不是的递归加点之后的集合或者删点之后的集合
        if(addORdelNode == -1)
        {
            //if(bestPvalue < 0.01)
                return nowNodeset;
           // else
            //    return null;
        }
        else {
            if (isAddBest) {
                nowNodeset.add(addORdelNode);
                return runOnce(nowNodeset);
            } else {
                nowNodeset.remove(addORdelNode);
                return runOnce(nowNodeset);
            }
        }
    }

}
