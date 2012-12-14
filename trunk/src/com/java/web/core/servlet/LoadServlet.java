package com.java.web.core.servlet;

import com.java.web.core.Constants;
import com.java.web.core.config.managers.InstanceManager;
import com.java.web.core.config.managers.MainConfigManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.java.web.core.lib.http.HttpContext;
import com.java.web.core.sso.SSOParameters;
import java.io.File;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public abstract class LoadServlet extends HttpServletBase {

    public abstract void alreadyLogedInCheck(SSOParameters ssoParameters);
    
    public abstract SSOParameters processUrlParameters(Map pathInfo, Properties urlInitProperties);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        
        Properties urlInitProperties = new Properties();
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        HttpContext.init(this.getServletContext(),request, response);
        Map parameterMap = request.getParameterMap();
        SSOParameters ssoParameters = this.processUrlParameters(parameterMap,urlInitProperties);
        try {
            response.setContentType("text/html;charset=utf-8");
            out = response.getWriter();
            if (InstanceManager.validateProductInstanceInSession()) {
                alreadyLogedInCheck(ssoParameters);
                MainConfigManager.getContentGenerator().execute(out,urlInitProperties);
            } else {
                out.write("Invalid URL");
            }
        } catch (Exception ex) {
            this.handleException(ex, out, request, response);
        } finally {
            if (out != null) {
                out.close();
            }
            HttpContext.clear();
        }
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
		System.out.println("Log4JInitServlet is initializing log4j");
		String log4jLocation = config.getInitParameter("log4j-properties-location");

		ServletContext sc = config.getServletContext();

		if (log4jLocation == null) {
			System.err.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File yoMamaYesThisSaysYoMama = new File(log4jProp);
			if (yoMamaYesThisSaysYoMama.exists()) {
				System.out.println("Initializing log4j with: " + log4jProp);
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.err.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			}
		}
		super.init(config);
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
