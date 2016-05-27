/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;

/**
 *
 * @author Halforc
 */
import java.io.*;
import java.util.*;

public class MatchManager {

    int[] diffDictionary = new int[65535];

    public MatchManager() {
        initDiffTable();
    }

    public void run() {
        FileInputStream fIn = null;
        FileOutputStream fOut = null;

        try {
            fIn = new FileInputStream("EncodedFile.txt");
        } catch (Exception e) {
            System.out.println("Error in open Encoded File");
        }

        try {
            fOut = new FileOutputStream("result.txt");
        } catch (Exception e) {
            System.out.println("Error in open result file");
        }

        if (fIn == null || fOut == null) {
            return;
        }
        
        try
        {
            matchGen(fIn, fOut);
        }catch (Exception e) {
            System.out.println("Error in process result file");
        }

    }

    void matchGen(FileInputStream fIn, FileOutputStream fOut) throws IOException {
        String currentLine = null;
        List<GenSet> loadedSets = new ArrayList<GenSet>();
        BufferedReader br = new BufferedReader( new InputStreamReader(fIn) );
        while ((currentLine = br.readLine()) != null) {
            loadedSets.add(new GenSet(currentLine));
        }

        for (int i = 0; i < loadedSets.size() - 1; i++) {
            System.out.println("Working on No. " + i + "; Process: " + i*100/loadedSets.size()+"%" );
            boolean isTheFirstMatch = true;
            String output = "";
            for (int j = i + 1; j < loadedSets.size(); j++) {
                int difs = diffCount(loadedSets.get(i), loadedSets.get(j));
                if (difs > 3) {
                    continue;
                }
                
                if( isTheFirstMatch )
                {
                    output = "\r\n\r\n\r\n#\t\t\t\tMatch result for No. " + i + ".  Code: \r\n" + loadedSets.get(i).getRawData() + "\r\n";
                    fOut.write(output.getBytes());
                    isTheFirstMatch = false;
                }
                
                output = "#\t　No. " + j + ", mis: " + difs + "\r\n"+ loadedSets.get(j).getRawData() + "\r\n";
                fOut.write(output.getBytes());
            }
        }

    }

    public int diffCount(GenSet gs1, GenSet gs2) {
        int diffResult = 0;
        for (int i = 0; i < 24; i++) {
            int gsCode1 = gs1.getCode(i);
            int gsCode2 = gs2.getCode(i);
            
            if(gsCode1 != gsCode2)
            {
                diffResult += diffDictionary[gs1.getCode(i) * 256 + gs2.getCode(i)];
            }
            if (diffResult > 3) {
                return diffResult;
            }
        }

        return diffResult;
    }

    public void initDiffTable() {
        for (int i = 0; i < 65535; i++) {
            diffDictionary[i] = calculateDiffGen(i / 256, i % 256);
        }
    }

    public int calculateDiffGen(int code1, int code2) {
        int result = 0;
        if (code1 > 255 || code2 > 255) {
            return -1;
        }

        if (code1 % 4 != code2 % 4) {
            result++;
        }

        if (code1 / 4 % 4 != code2 / 4 % 4) {
            result++;
        }

        if (code1 / 16 % 4 != code2 / 16 % 4) {
            result++;
        }

        if (code1 / 64 % 4 != code2 / 64 % 4) {
            result++;
        }
        
        return result;
    }
}
