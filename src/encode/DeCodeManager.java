/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Halforc
 */
public class DeCodeManager {
    String[] codeDictionary = new String[256];
    public DeCodeManager()
    {
        generateCodeDictionary();
    }
            
    public void run(String resultFile) throws IOException
    {
        FileInputStream Fs = null;
        FileOutputStream FsOut = null;
        try {
            Fs = new FileInputStream(resultFile);
        } catch (Exception e) {
            System.out.println("Cannot open test.txt file.");
        }

        try {
            FsOut = new FileOutputStream("FinalResult_PlainText.txt");
        } catch (Exception e) {
            System.out.println("Cannot open the output file.");
        }

        if (!decode(Fs, FsOut)) {
            System.out.println("Fail to encode.");
        }
    }
    
    
    void generateCodeDictionary() {
        for (int i = 0; i < 256; i++) {
            codeDictionary[i] = "" + codeIntToChar(i / 64) + codeIntToChar(i % 64 / 16) + codeIntToChar(i % 16 / 4) + codeIntToChar(i % 4);
        }

        System.out.println("Dictionary Build up");
    }

    char codeIntToChar(int codeBit) {
        switch (codeBit) {

            case 0:
                return 'C';
            case 1:
                return 'G';
            case 2:
                return 'T';
            case 3:
                return 'A';
            default:
                return 'F';
        }
    }

    boolean decode(FileInputStream inS, FileOutputStream outS) throws IOException {
        Scanner sc = new Scanner(inS);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            
            if(line.isEmpty()||line.charAt(0) == '\r'||line.charAt(0) == '\n'){
                outS.write("\r\n".getBytes());
                continue;
            }
            
            if (line.charAt(0) == '#') {
                outS.write((line+"\r\n").getBytes());
                continue;
            }
            
            if (decodeLine(line, outS)) {
                continue;
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    boolean decodeLine(String line, FileOutputStream outS) throws IOException {
            String[] strArray = line.split(",");
            int[] codes = new int[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                codes[i] = Integer.parseInt(strArray[i]);
            }
        for (int i = 0; i < codes.length; i++) {
            int code = codes[i];
            if (code > 255) {
                return false;
            }

            outS.write(codeDictionary[codes[i]].getBytes());
        }
        outS.write("\r\n".getBytes());
        return true;
    }
}
