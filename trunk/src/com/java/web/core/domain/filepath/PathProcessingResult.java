/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.domain.filepath;

/**
 *
 * @author Marija
 */
public class PathProcessingResult {
    
    private boolean hasPathError;
    private Exception exception;
    private String path;

    public PathProcessingResult(boolean hasPathError, String path){
        this.hasPathError = hasPathError;
        this.path = path;
    }
    public PathProcessingResult(){
        
    }
    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean hasPathError() {
        return hasPathError;
    }

    public void setHasPathError(boolean hasPathError) {
        this.hasPathError = hasPathError;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
