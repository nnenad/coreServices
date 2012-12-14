package com.java.web.core.ajax;

import com.java.web.core.lib.json.JSONException;
import com.java.web.core.lib.json.JSONObject;
import com.java.web.core.lib.json.JSONParser;
import java.util.HashMap;

public class AjaxResult {

    private boolean ok;
    private String message;
    private String error;
    private String clientScript;
    private HashMap<String, String> serverResponse;
    private String errorCode;
    private boolean login = false;
    
    public AjaxResult() {
        this.ok = true;
    }

    public AjaxResult(boolean okParam, String messageParam, String errorMessage, String errorCode) {
        this.ok = okParam;
        this.message = messageParam;
        this.error = errorMessage;
        this.errorCode = errorCode;
    }

    // <editor-fold defaultstate="collapsed" desc="getters & setters">
    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getClientScript() {
        return clientScript;
    }

    public void setClientScript(String clientScript) {
        this.clientScript = clientScript;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public boolean getLogin() {
        return login;
    }
    public void setLogin(boolean login) {
        this.login = login;
    }
    // </editor-fold>

    public JSONObject toJson() throws JSONException {
        JSONObject result = null;
        if (this.getServerResponse() != null) {
            result = new JSONObject(serverResponse);
            result.put("error", this.getError());
            result.put("errorCode", this.getErrorCode());
            result.put("ok", this.isOk());
            result.put("clientScript", this.getClientScript());
            result.put("message", this.getMessage());
            result.put("login", this.getLogin());
            return result;
        } else {
            result = JSONParser.createJsonObject("error", this.getError());
            result.put("ok", this.isOk());
            result.put("clientScript", this.getClientScript());
            result.put("errorCode", this.getErrorCode());
            result.put("message", this.getMessage());
            result.put("login", this.getLogin());
            return result;
        }
    }

    public static AjaxResult fromException(Exception ex) {
        AjaxResult result = new AjaxResult();
        result.setOk(false);
        result.setError(ex.getMessage());
        return result;
    }

    /**
     * @return the serverResponse
     */
    public HashMap<String, String> getServerResponse() {
        return serverResponse;
    }

    /**
     * @param serverResponse the serverResponse to set
     */
    public void setServerResponse(HashMap<String, String> serverResponse) {
        this.serverResponse = serverResponse;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
