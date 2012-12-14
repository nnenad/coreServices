package com.java.web.core.servlet;

import com.java.web.core.lib.http.HttpContext;
import com.java.web.core.Service;
import com.java.web.core.ServiceManager;
import com.java.web.core.ajax.AjaxResult;
import com.java.web.core.config.managers.InstanceManager;
import com.java.web.core.lib.json.JSONObject;
import com.java.web.core.lib.json.JSONParser;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AjaxServlet extends HttpServletBase {
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        this.doPost(request, response);
    }

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpContext.init(this.getServletContext(), request, response);
        PrintWriter out = null;
        try {
            if (InstanceManager.validateProductInstanceInSession()) {
            AjaxResult ar = new AjaxResult();
            out = response.getWriter();
            JSONObject result = null;
            JSONObject object = JSONParser.parseJsonObject(request.getReader());
            String actionName = request.getParameter("action");         
            Service action = ServiceManager.getAction(actionName);
            boolean processingResult = servicePreprocessing(ar,action);
            if(processingResult){
                result = action.executeAction(object);
                response.setContentType("application/json");
                out.write(result.toString());
            }else{
                response.setContentType("application/json");
                result = ar.toJson();
                out.write(result.toString());
            }
            }
            else{
                response.setContentType("text/html");
                out.write("Invalid URL");
            }
            
        } catch (Exception ex) {
            this.handleException(ex, out, request, response);
        } finally {
            if (out != null)
                out.close();
            HttpContext.clear();
        }
    }
    protected abstract boolean servicePreprocessing(AjaxResult result, Service service);


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
