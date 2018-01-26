package com.liang.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/17.
 */
public class RemoveRedundancy
{
    public String[] concat(String[] a , String [] b)
    {
        String[] c = new String[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        Set set = new HashSet();
        for(int i = 0 ; i < c.length ;i++)
            set.add(c[i]);
        return (String[])set.toArray(new String[set.size()]);
    }


    public ArrayList<String[]> removeRedundacy(ArrayList<String[]> result)
    {
        int overlapCount = 0;
        for(int i = 0 ; i < result.size() ; i++)
        {
            String[] a1 = result.get(i);
            //for(String h :a1) System.out.print(h+" ");System.out.println();

            for(int j = 0; j < i ; j++)
            {
                overlapCount = 0;
                String[] a2 = result.get(j);
                for(String c:a1)
                {
                    for(String d : a2)
                    {
                        if(d == c)
                        {
                            overlapCount++;
                            //System.out.print(d+" ");
                        }
                    }
                }
                //System.out.println();
               // System.out.println("i = " + i + " overlapCount="+overlapCount + " a1Length = "+ a1.length+ " a2Length = " + a2.length);
                if((double)overlapCount/(double)a1.length > 0.5)
                {
                    //for(String h :a1) System.out.print(h+" ");System.out.println("a1");
                   // for(String h :a2) System.out.print(h+" ");System.out.println("a2");
                    String[] a3 = concat(a1,a2);
                   // for(String h :a3) System.out.print(h+" ");System.out.println("a3   " + a3.length);
                    result.remove(a1);
                    result.set(j,a3);
                    i--;
                    break;
                }
            }
            //System.out.println();
        }
        return result;
    }

}
