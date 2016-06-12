/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;

import java.io.*;
import java.util.*;

/**
 *
 * @author Halforc
 */
public class FilterManager {

    private boolean isLeftBothHad = false;
    private String inputFile ="";
    private String filterFile = "";
    private String outputFile = "";
    
    public FilterManager(boolean leftBothHad, String inputFile, String filterFile, String outputFile) {
        isLeftBothHad = leftBothHad;
        this.inputFile = inputFile;
        this.filterFile = filterFile;
        this.outputFile = outputFile;
    }

    public void runFilter() {
        FileInputStream fIn1 = null;
        FileInputStream fIn2 = null;
        FileOutputStream fOut = null;

            try {
                fIn1 = new FileInputStream(inputFile);
            } catch (Exception e) {
                System.out.println("Error in open Encoded File: "+ this.inputFile);
            }

            try {
                fIn2 = new FileInputStream(this.filterFile);
            } catch (Exception e) {
                System.out.println("Error in open Encoded File: " + this.filterFile);
            }

            try {
                fOut = new FileOutputStream(this.outputFile);
            } catch (Exception e) {
                System.out.println("Error in open result file: " + this.outputFile);
            }
        
        if (fIn1 == null || fOut == null) {
            return;
        }
        
        try {
            FiltGenSet(fIn1, fIn2, fOut);
        } catch (Exception e) {
            System.out.println("Error in filtering file. : " + e.getMessage() );
        }
    }

    private void FiltGenSet(FileInputStream fIn, FileInputStream fFilter, FileOutputStream fResult) throws IOException {
        List<GenSet> OriginalSet = new ArrayList<>();
        List<GenSet> FilterSet = new ArrayList<>();
        BufferedReader filterBR = new BufferedReader(new InputStreamReader(fFilter));
        String currentLine;
        while ((currentLine = filterBR.readLine()) != null) {
            FilterSet.add(new GenSet(currentLine));
        }

        BufferedReader originalBR = new BufferedReader(new InputStreamReader(fIn));
        while ((currentLine = originalBR.readLine()) != null) {
            OriginalSet.add(new GenSet(currentLine));
        }

        boolean isHaveAnySame = false;
        for (int i = 0; i < OriginalSet.size(); i++) {
            System.out.println("Working on No. " + (i + 1) + "; Process: " + i * 100 / OriginalSet.size() + "%");
            for (int j = 0; j < FilterSet.size(); j++) {

                if (OriginalSet.get(i).isSame(FilterSet.get(j))) {
                    isHaveAnySame = true;
                    break;
                }
            }

            if (isHaveAnySame && isLeftBothHad) {
                fResult.write((OriginalSet.get(i).rawStr + "\r\n").getBytes());
            }

            if (!isHaveAnySame && !isLeftBothHad) {
                fResult.write((OriginalSet.get(i).rawStr + "\r\n").getBytes());
            }
        }
    }
}
