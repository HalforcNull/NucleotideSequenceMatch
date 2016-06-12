/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Halforc
 */
public class main {

    /**
     * @param args the command line arguments
     */
    String[] codeDictionary = new String[256];

    public static void main(String[] args) {
        EncodeManager em;
        FilesManager fm = new FilesManager();
        DeCodeManager dm = new DeCodeManager();
        MatchManager mm = new MatchManager();
        FilterManager filterM;
        try {
            System.out.println("Now enconding files: "+ fm.getRawDataFile());
            em = new EncodeManager(fm.getRawDataFile(), fm.getRawDataEncodedFile());
            em.runTheEncode();
            
            System.out.println("Now enconding files: "+ fm.getFilterForDiffRawDataFile());
            em = new EncodeManager(fm.getFilterForDiffRawDataFile(), fm.getFilterForDiffEncodedFile());
            em.runTheEncode();
            
            System.out.println("Filtering for different. \r\n\r\n\r\n\r\n");
            
            filterM = new FilterManager(false, fm.getRawDataEncodedFile(), fm.getFilterForDiffEncodedFile(), fm.getDiffResultFile());
            filterM.runFilter();
            
            System.out.println("Filtering for different. Done.\r\n\r\n\r\n\r\n ");
            
            System.out.println("Now enconding files: "+ fm.getFilterForSameRawDataFile());
            em = new EncodeManager(fm.getFilterForSameRawDataFile(), fm.getFilterForSameEncodedFile());
            em.runTheEncode();
            
            System.out.println("Filtering for same. \r\n\r\n\r\n\r\n");
            
            filterM = new FilterManager(true, fm.getDiffResultFile(), fm.getFilterForSameEncodedFile(), fm.getSameResultFile());
            filterM.runFilter();
            
            System.out.println("Filtering for same. Done. \r\n\r\n\r\n\r\n");
            
            mm.run(fm.getSameResultFile(), fm.getMatchResultFile());
            
            dm.run(fm.getMatchResultFile());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
