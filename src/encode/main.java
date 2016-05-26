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
        encode.EncodeManager em = new encode.EncodeManager();
        encode.DeCodeManager dm = new encode.DeCodeManager();
        encode.MatchManager mm = new encode.MatchManager();
        try {
            em.runTheEncode();
            mm.run();
            dm.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
