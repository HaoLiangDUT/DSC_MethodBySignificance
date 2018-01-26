package com.liang.main;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/25.
 */
public class ReadTrueCommunity {
    public HashMap<Integer,ArrayList<String>>  doRead()
    {
        int max = 0;
        HashMap<Integer,ArrayList<String>> hashMap = new HashMap<>();
        File file = new File("./gokd/railway-community.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String temp = bufferedReader.readLine();
            int line = 1;
            while(temp != null)
            {
                String [] stringArray = temp.split("\\s+");
                ArrayList<String> arrayList = new ArrayList<>();
                for(int i = 0 ; i < stringArray.length; i++)
                {
                    arrayList.add(stringArray[i]);
                }
                hashMap.put(line,arrayList);
                line++;
                temp = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator iter = hashMap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Integer key = (Integer) entry.getKey();
            // System.out.print(key+"----");
            ArrayList<String> val = (ArrayList<String>)entry.getValue();
            Iterator it = val.iterator();
            while (it.hasNext())
            {
                System.out.print(it.next()+" ");
            }
            System.out.println();



        }System.out.println();
        return hashMap;

    }
}
