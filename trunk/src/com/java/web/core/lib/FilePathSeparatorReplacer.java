/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.web.core.lib;

import com.java.web.core.domain.filepath.PathProcessingResult;
import com.java.web.core.internationalization.MlManager;
import java.io.File;

/**
 *
 * @author Vuk
 */
public class FilePathSeparatorReplacer {
    public static String replaceSeparators(String path, boolean session) throws Exception{
        
        String result = path;
        if(path.indexOf("/") != -1){
            result = path.replaceAll("/", separatorReal(File.separator));
        }
        if(path.indexOf("\\") != -1){
            result = path.replaceAll("\\\\", separatorReal(File.separator));
        }
        if(path.contains("..") && session){
            Exception e = new Exception(MlManager.gettext("Path contains invalid character \"..\""));  
            throw e;
        }else if(path.contains("..")){
            throw new Exception("Path contains invalid character \"..\"");
            
        }
        return result;
    }
    public static String separatorReal(String separator){
        String separatorReal = separator;
        if(separator.equals("\\")){
            separatorReal = "\\\\";
        }
        return separatorReal;
    }
    
    public static PathProcessingResult replaceSeparators(String path){
        PathProcessingResult result = new PathProcessingResult();
        if(path.contains("..")){
            Exception ex =  new Exception("Path contains invalid character \"..\"");   
            result.setHasPathError(true);
            result.setException(ex);
        }
        String resultPath = path;
        if(path.indexOf("/") != -1){
            resultPath = resultPath.replaceAll("/", separatorReal(File.separator));
        }
        if(path.indexOf("\\") != -1){
            resultPath = resultPath.replaceAll("\\\\", separatorReal(File.separator));
        }
        result.setPath(resultPath);
        return result;
    }
}
