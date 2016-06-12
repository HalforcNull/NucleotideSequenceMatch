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
public class FilesManager {
    
    
    public String getRawDataFile(){
        return "test.txt";
    }
    
    public String getRawDataEncodedFile(){
        return "rawEncode.txt";
    }
            
    public String getFilterForSameRawDataFile(){
        return "FindSame.txt";
    }
    
    public String getFilterForSameEncodedFile(){
        return "sameEncode.txt";
    }
    
    public String getSameResultFile(){
        return "sameResult.txt";
    }
    
    
    public String getFilterForDiffRawDataFile(){
        return "FindDiff.txt";
    }
    
    public String getFilterForDiffEncodedFile(){
        return "diffEncode.txt";
    }
    
    public String getDiffResultFile(){ 
        return "diffResult.txt";
    }    
    
    public String getMatchResultFile(){
        return "matchResult.txt";
    }
}
