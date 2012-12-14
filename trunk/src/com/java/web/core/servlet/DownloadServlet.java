/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.servlet;

import com.java.web.core.config.managers.InstanceManager;
import com.java.web.core.lib.FilePathSeparatorReplacer;
import com.java.web.core.lib.http.HttpContext;
import com.java.web.core.lib.json.JSONException;
import com.java.web.core.lib.json.JSONObject;
import com.java.web.core.lib.json.JSONParser;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nenad
 */
public class DownloadServlet extends HttpServletBase {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DownloadServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DownloadServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    private void doDownload(HttpServletRequest req, HttpServletResponse resp,
            String filename, String original_filename)
            throws IOException {
        File f = new File(filename);
        int length = 0;
        ServletOutputStream op = resp.getOutputStream();
        ServletContext context = getServletConfig().getServletContext();
        String mimetype = context.getMimeType(filename);

        //
        //  Set the response and go!
        //
        //
        resp.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
        resp.setContentLength((int) f.length());
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + original_filename + "\"");

        //
        //  Stream to the requester.
        //
        byte[] bbuf = new byte[1024];
        DataInputStream in = new DataInputStream(new FileInputStream(f));

        while ((in != null) && ((length = in.read(bbuf)) != -1)) {
            op.write(bbuf, 0, length);
        }

        in.close();
        op.flush();
        op.close();
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpContext.init(this.getServletContext(), request, response);
        PrintWriter out = null;
        try {
            boolean inSession = InstanceManager.validateProductInstanceInSession();
            if (inSession) {
                boolean isLogedIn = checkLogIn();
                if (isLogedIn) {
                    response.setCharacterEncoding("UTF-8");

                    try {
                        String path = request.getParameter("path");
                        String fileName = this.getFileNameFromPath(path);
                        doDownload(request, response, path, fileName);
                    } catch (Exception ex) {
                        Logger.getLogger(DownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    out = response.getWriter();
                    out.println("<html>\n");
                    out.println("</head>\n");
                    out.println("<h1>You don't have permision to download file</h1>");
                    out.println("</body>\n");
                    out.println("</html>");
                    out.close();
                }
            } else {
                response.setContentType("text/html");
                out.write("Invalid URL");
            }
        } catch (NamingException ex) {
            Logger.getLogger(DownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
            HttpContext.clear();
        }
    }

    public String getFileNameFromPath(String path) throws Exception {
        path = FilePathSeparatorReplacer.replaceSeparators(path, true);
        int index = path.lastIndexOf(File.separator);
        String fileName = path.substring(index);
        return fileName;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    protected boolean checkLogIn() {
        return false;
    }
}
