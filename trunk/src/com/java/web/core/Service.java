package com.java.web.core;

import com.java.web.core.lib.json.JSONException;
import javax.naming.NamingException;
import com.java.web.core.config.managers.MainConfigManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.java.web.core.lib.json.JSONObject;
import com.java.web.core.lib.json.JSONParser;

public abstract class Service {
    
    
    protected abstract JSONObject action(JSONObject object) throws Exception;
    protected boolean requiresLogin = true;

    public boolean getRequiresLogin() {
        return this.requiresLogin;
    }
   
    public JSONObject executeAction(JSONObject object) throws JSONException {
        JSONObject result = null;
        try {
            this.masterPart();
            result = this.clientPart(object);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            // TODO log!!!
            e.printStackTrace();
            String eText = traverseCauses(e);
            result = JSONParser.createJsonObject("error", eText);
            result.put("success", false);
        }
        return result;
    }
    private String traverseCauses(Throwable ex){
        Throwable exceptionWhichCaused = ex.getCause();
        if(exceptionWhichCaused == null){
            return ex.toString();
        }
        else{
            return traverseCauses(exceptionWhichCaused);
        }

    }
    private void masterPart() throws NamingException {
       MainConfigManager.getBaseBroker().openMasterSessionIfNeeded();
        try {
            this.doMaster();
        } finally {
            MainConfigManager.getBaseBroker().closeSession();
        }
    }

    private JSONObject clientPart(JSONObject object) throws Exception {
        JSONObject result = null;
      MainConfigManager.getBaseBroker().openSessionIfNeeded();
        try {
            MainConfigManager.getBaseBroker().beginTransaction();
            checkAccessRights();
            result = action(object);
            MainConfigManager.getBaseBroker().commitTransaction();
        } catch (Exception ex) {
            try {
                    MainConfigManager.getBaseBroker().rollbackTransaction();
            } catch (Exception exxx) {
            }
            throw new Exception(ex);
        } finally {
            MainConfigManager.getBaseBroker().closeSession();
        }
        return result;
    }
    protected abstract void checkAccessRights() throws Exception;

    protected void doMaster() {
        // STUB to override if needed
    }
}
