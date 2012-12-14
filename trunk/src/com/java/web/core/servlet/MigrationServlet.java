/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.servlet;

import com.java.web.core.Constants;
import com.java.web.core.config.managers.InstanceManager;
import com.java.web.core.lib.http.HttpContext;
import com.java.web.core.migration.MigrationExecutor;
import com.java.web.core.migration.MigrationManager;
import com.java.web.core.migration.MigrationResult;
import com.java.web.core.migration.MigrationWrapper;
import com.java.web.core.migration.Result;
import com.java.web.core.migration.Status;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vuk
 */
public abstract class MigrationServlet extends HttpServletBase {

    private ThreadLocal<String> actParam = new ThreadLocal<String>();

    public abstract boolean alreadyLogedInCheck();

    public void setActParam(String actParam) {
        //set default param value = App
        if (actParam == null) {
            this.actParam.set(Constants.MIGRATION_TYPE_APP);
            return;
        }
        this.actParam.set(actParam);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        HttpContext.init(this.getServletContext(), request, response);
        PrintWriter out = response.getWriter();


        try {
            setActParam(request.getParameter("msGroup"));
//            Refactor to use master users not application users
//            if (InstanceManager.validateProductInstanceInSession()) {
//                boolean isLoggedIn = alreadyLogedInCheck();
//                if (isLoggedIn) {

                    printHTMLHeader(out, actParam.get());

                    //probni kod, izdvajanje parametra(Slave ili Master)


                    out.println("<pre>");
                    //add parameter for choosing master/slave configuration
                    MigrationManager mngr = new MigrationManager(MigrationManager.getDefaultConfiguration(actParam.get()));
                    try {
                        Status status = mngr.getMasterStatus();
                        out.print("Latest executed migration: ");
                        out.println(status.getLatestMigration() != null ? status.getLatestMigration().getName() : "- none -");
                        out.println("Pending migrations:");
                        for (Iterator<MigrationWrapper> i = status.getPendingMigrations().iterator(); i.hasNext();) {
                            MigrationWrapper w = i.next();
                            out.print("  ");
                            out.println(w.getName());
                        }
                        if (status.getPendingMigrations().size() == 0) {
                            out.println("  - none -");
                        }
                    } catch (Exception ex) {
                        this.printException(ex, out);
                    }
                    out.println("</pre>");

                    out.println("</body></html>");


//                } else {
//                    out.println("<html><head><title>Migrations</title></head><body>");
//                    out.println("You have no right to access this service");
//                    out.println("</body></html>");
//                }
//            } else {
//                out.println("<html><head><title>Migrations</title></head><body>");
//                out.println("You have no right to access this service");
//                out.println("</body></html>");
//            }
        } catch (Exception ex2) {
            this.printException(ex2, out);
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {

            out.println("<html><head><title>Migrations</title></head><body>");
            out.println("<p><a href='?'>Back</a></p>");



            out.println("<pre>");
            Result result = MigrationExecutor.executeUp(actParam.get());
            try {
                for (Iterator<MigrationResult> i = result.iterator(); i.hasNext();) {
                    MigrationResult r = i.next();
                    boolean isOK = r.isOK();
                    if (isOK) {
                        out.print("OK - ");
                    } else {
                        out.print("ERROR - ");
                    }
                    out.println(r.getMigration().getName());
                    if (!isOK) {
                        this.printException(r.getError(), out);
                    }
                }
            } catch (Exception ex) {
                this.printException(ex, out);
            }
            out.println("</pre>");
            out.println("</body></html>");

        } catch (Exception ex2) {
            this.printException(ex2, out);
        } finally {
            out.close();
        }
    }

    private void printException(Exception ex, PrintWriter out) {
        out.println(ex.toString());
        StackTraceElement[] stack = ex.getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            out.println(stack[i].toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private String checkedRadioButton(String checked) {

        if (checked.equals(Constants.MIGRATION_TYPE_APP)) {
            return "<input type='radio' name='msGroup' value='App' checked> App<br>" +
                    "<input type='radio' name='msGroup' value='Core'> Core<br>" +
                    "<input type='radio' name='msGroup' value='Test'> Test<br>" +
            "<input type='radio' name='msGroup' value='Down'> Down<br>";

        }
        if (checked.equals(Constants.MIGRATION_TYPE_CORE)) {
            return "<input type='radio' name='msGroup' value='App' > App<br>" +
                    "<input type='radio' name='msGroup' value='Core' checked> Core<br>" +
                    "<input type='radio' name='msGroup' value='Test'> Test<br>" +
            "<input type='radio' name='msGroup' value='Down'> Down<br>";
        }
        if (checked.equals(Constants.MIGRATION_TYPE_TEST)) {
            return "<input type='radio' name='msGroup' value='App' > App<br>" +
                    "<input type='radio' name='msGroup' value='Core'> Core<br>" +
                    "<input type='radio' name='msGroup' value='Test' checked> Test<br>" +
            "<input type='radio' name='msGroup' value='Down'> Down<br>";
        }
        if (checked.equals(Constants.MIGRATION_TYPE_DOWN)) {
            return "<input type='radio' name='msGroup' value='App' > App<br>" +
                    "<input type='radio' name='msGroup' value='Core'> Core<br>" +
                    "<input type='radio' name='msGroup' value='Test'> Test<br>" +
            "<input type='radio' name='msGroup' value='Down' checked> Down<br>";
        }
        return "parameter error";
    }
    //print HTML header depend on migration App/Core/Test parameter

    private void printHTMLHeader(PrintWriter out, String actParam) {
        out.println("<html><head><title>Migrations</title></head><body>");
        out.println("<form method='post'>");
        out.println("<input type='submit' name='btnGO' value='GO' />");
        out.println("</form>");
        //form for master/slave migration selection
        out.println("<form method='get'>");
        // checked radio button, depend on param
        out.println(checkedRadioButton(actParam));
        out.println("<input type='submit' name='btnSwitch' value='switch' />");
        out.println("</form>");

    }
}
