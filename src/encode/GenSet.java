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

public class GenSet {
    int[] data = new int[24];
    String rawStr = "";

    public GenSet(String codedString) {
        rawStr = codedString;
        String[] codes = codedString.split(",");
        for (int i = 0; i < 24; i++) {
            data[i] = Integer.parseInt(codes[i]);
        }
    }

    public int getCode(int position) {
        return data[position];
    }

    public String getRawData() {
        return rawStr;
    }
    
    public boolean isSame(GenSet v2){
        for(int i = 0; i < 24; i++){
            if(data[i] != v2.data[i])
                return false;
        }
        return true;
    }
}
