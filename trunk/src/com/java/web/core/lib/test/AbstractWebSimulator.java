package com.java.web.core.lib.test;

import com.java.web.core.config.managers.JNDIBasedParams;
import com.java.web.core.lib.http.HttpContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractWebSimulator {

    public void initHttpContext(String serverName) {
        JNDIBasedParams.createEmpty();
        ServletContext ctx = getServletContext();
        HttpContext.init(ctx, getRequestImpl(ctx), getResponceImpl());
    }

    public static void clearHttpContext() {
        HttpContext.clear();
    }

    public abstract ServletContext getServletContext();

    public abstract HttpServletRequest getRequestImpl(ServletContext ctx);

    public abstract HttpServletResponse getResponceImpl();
}
