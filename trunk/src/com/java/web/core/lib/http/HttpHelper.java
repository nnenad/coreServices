
package com.java.web.core.lib.http;

import com.java.web.core.lib.*;
import com.java.web.core.lib.http.HttpContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpHelper {

    private HttpHelper() { }
    
    public static void redirect(String url) {
        HttpServletResponse response = HttpContext.response();
        response.addHeader("Location: ", url);
    }

    public static Cookie getCookieByName(Cookie[] cookies, String cookieName) {
        Cookie result = null;
        if (cookies != null) {
            for(int i=0; i<cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName()))
                result = cookie;
            }
        }
        return result;
    }
    public static String getCookieValue(Cookie[] cookies, String cookiename, String defaultValue) {
        Cookie cookie = getCookieByName(cookies, cookiename);
        return cookie != null ? cookie.getValue() : defaultValue;
    }

}
