
package com.java.web.core.lib.http;

import com.java.web.core.config.managers.MainConfigManager;
import com.java.web.core.config.managers.RuntimeProperties;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpContext {

    private static HashMap<String,Object> appScope = new HashMap<String,Object>();
    private static ThreadLocal<ServletContext> _servletContext = new ThreadLocal<ServletContext>();
    private static ThreadLocal<HttpServletRequest> _request = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> _response = new ThreadLocal<HttpServletResponse>();

    public static void init(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        _servletContext.set(context);
        _request.set(request);
        _response.set(response);
    }
    public static void clear() {
        MainConfigManager.getBaseBroker().closeSession();
        _servletContext.remove();
        _request.remove();
        _response.remove();
    }

    public static ServletContext context() {
        ServletContext result = _servletContext.get();
        return result;
    }
    public static HttpServletRequest request() {
        HttpServletRequest result = _request.get();
        return result;
    }
    public static HttpServletResponse response() {
        HttpServletResponse result = _response.get();
        return result;
    }


    public static <T> T getApplicationVar(String name) {
        if (appScope == null)
            throw new NullPointerException();
        return (T)appScope.get(name);
    }
    public static <T> void setApplicationVar(String name, T t) {
        if (appScope == null)
            throw new NullPointerException();
        appScope.put(name, t);
    }
    public static <T> T getSessionVar(String name) {
        HttpServletRequest req = request();
        if (req == null)
            throw new NullPointerException();
        HttpSession session = req.getSession(true);
        return (T)session.getAttribute(name);
    }
    public static <T> void setSessionVar(String name, T t) {
       HttpServletRequest req = request();
        if (req == null)
            throw new NullPointerException();
        HttpSession session = req.getSession(true);
        session.setAttribute(name, t);
    }
    public static <T> T getRequestVar(String name) {
        HttpServletRequest req = request();
        if (req == null)
            throw new NullPointerException();
        return (T)req.getAttribute(name);
    }
    public static <T> void setRequestVar(String name, T t) {
       HttpServletRequest req = request();
        if (req == null)
            throw new NullPointerException();
        req.setAttribute(name, t);
    }

    public static String runtimePropertiesGetProperty(String key) {
        Properties rp = getRuntimeProperties();
        String result = rp.getProperty(key);
        return result;
    }
    public static boolean runtimePropertiesContainsKey(String key) {
        Properties rp = getRuntimeProperties();
        boolean result = rp.containsKey(key);
        return result;
    }
    public static Properties getRuntimeProperties() {
        Properties rp = RuntimeProperties.getRuntimePropertiesInstance(context());
        return rp;
    }
    public static String getIPAddress(){
        return _request.get().getRemoteAddr();
    }
}
