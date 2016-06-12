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
public class EncodeManager {

    private String rawFile = "";
    private String encodedFile = "";
    
    public EncodeManager(String raw, String out){
        rawFile = raw;
        encodedFile = out;
    }
    
    public void runTheEncode() throws IOException {
        FileInputStream Fs = null;
        FileOutputStream FsOut = null;
        FileOutputStream FsOut2 = null;
        try {
            Fs = new FileInputStream(rawFile);
        } catch (Exception e) {
            System.out.println("Cannot open test.txt file: " + rawFile);
        }

        try {
            FsOut = new FileOutputStream(encodedFile);
        } catch (Exception e) {
            System.out.println("Cannot open the output file: " + encodedFile);
        }

        try {
            FsOut2 = new FileOutputStream("NCodesFilterResult.txt");
        } catch (Exception e) {
            System.out.println("Cannot open the N code output file.");
        }

        if (!encode(Fs, FsOut, FsOut2)) {
            System.out.println("Fail to encode.");
        }
    }

    boolean encode(FileInputStream inS, FileOutputStream outS, FileOutputStream outS2) throws IOException {

        Scanner sc = new Scanner(inS);
        boolean firstLine = true;
        while (sc.hasNextLine()) {
            int count = 0;
            int value = 0;
            
            String output = "";
            String line = sc.nextLine();

            for (int i = 0; i < line.length(); i++) {
                count++;
                char currentChar = line.charAt(i);
                switch (currentChar) {
                    case 'C':
                        value = value * 4;
                        break;
                    case 'G':
                        value = value * 4 + 1;
                        break;
                    case 'T':
                        value = value * 4 + 2;
                        break;
                    case 'A':
                        value = value * 4 + 3;
                        break;
                    case 'N':
                        value = -2;
                        break;
                    case '\r':
                    case '\n':
                        if (count == 1) {
                            value = -1;
                            break;
                        } else {
                            return false;
                        }
                    default:
                        return false;
                }

                if (value == -1) {
                    value = 0;
                    count = 0;
                    continue;
                }

                if (value == -2) {
                    outS2.write((line+"\r\n").getBytes());
                    output = "";
                    value = 0;
                    count = 0;
                    break;
                }

                if (count == 4) {
                    output = output + value;
                    output += ',';
                    value = 0;
                    count = 0;
                }
            }
            if( !output.isEmpty())
            {
                if(!firstLine)
                {
                    output="\r\n"+output;
                }
                firstLine = false;
                outS.write(output.getBytes());
            }

        }

        return true;
    }
}
