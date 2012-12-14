/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.servlet;

import com.java.web.core.config.managers.InstanceManager;
import com.java.web.core.config.managers.JNDIBasedParams;
import com.java.web.core.domain.access.Customstyle;
import com.java.web.core.domain.access.Product;
import com.java.web.core.lib.http.HttpContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vuk
 */
public class StyleServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpContext.init(this.getServletContext(), request, response);
        Product product = InstanceManager.getProductInstanceFromSession();
        String url = request.getRequestURL().toString();
        String rootPath = null;
        String resourcePath = null;
        ServletOutputStream out = response.getOutputStream();
        try {
            response.setContentType("text/html;charset=UTF-8");
            HashMap<String, String> globalParams = null;
            try {
                globalParams = JNDIBasedParams.current();
            } catch (NamingException ex) {
                Logger.getLogger(StyleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (String key : globalParams.keySet()) {
                if (key.equals("slave.styles.folder")) {
                    rootPath = globalParams.get(key);
                }
            }
            if (rootPath != null) {
                resolveContentType(url, response);
                resourcePath = resolveFilePath(url, product, rootPath);
                streamFile(out, resourcePath);
            }
        } finally {
            out.close();
        }
    }

    public void resolveContentType(String url, HttpServletResponse response) {
        if (url.endsWith(".css")) {
            response.setContentType("text/css");
        }
        if (url.endsWith(".jpg")) {
            response.setContentType("image/jpeg");
        }
        if (url.endsWith(".png")) {
            response.setContentType("image/png");
        }
        if (url.endsWith(".gif")) {
            response.setContentType("image/gif");
        }
    }

    public String resolveFilePath(String url, Product product, String rootPath) {
        String path = null;
        int index = url.lastIndexOf("/")+1;
        String resourceName = url.substring(index);
        Iterator stylesIt = product.getCustomstyles().iterator();
        Customstyle style = (Customstyle)stylesIt.next();
        if(style!=null){
            path = rootPath+"/"+style.getFoldername()+"/"+resourceName;
        }
        return path;
    }

    public void streamFile(ServletOutputStream out, String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream in = null;
        in = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(bytes)) != -1) {
            out.write(bytes, 0, bytesRead);
        }
        in.close();
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
