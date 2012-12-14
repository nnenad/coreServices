/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marija
 */
public class HttpServletBase extends HttpServlet{
    protected void handleException(Exception ex, PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        try {
            Logger.getLogger(AjaxServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.setContentType("text/html;charset=utf-8");
            if (out == null)
                out = response.getWriter();
            try {
                out.write("Error");
                this.printException(ex, out);
            } finally {
                out.close();
            }
        } catch (Exception ex1) {
            Logger.getLogger(AjaxServlet.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }


    private void printException(Exception ex, PrintWriter out) {
        out.println(ex.toString());
        StackTraceElement[] stack = ex.getStackTrace();
        for (int i=0; i<stack.length; i++) {
            out.println(stack[i].toString());
        }
    }
}
