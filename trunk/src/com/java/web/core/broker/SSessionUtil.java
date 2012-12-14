package com.java.web.core.broker;

import com.java.web.core.lib.http.HttpContext;
import org.hibernate.Session;
import com.java.web.core.Constants;
import java.util.Date;

public abstract class SSessionUtil {

    public Session getCurrentSession() {
        Session s = HttpContext.getRequestVar(Constants.KEY_HIBERANTE_SESSION);
        return s;
    }

    public void setSession(Session s) {
        setFilters(s, null, null);
        HttpContext.setRequestVar(Constants.KEY_HIBERANTE_SESSION, s);
    }

    public void closeSession() {
        Session s = HttpContext.getRequestVar(Constants.KEY_HIBERANTE_SESSION);
        if (s != null && s.isOpen()) {
            setSession(null);
            try {
                s.close();
            } catch (Exception ex) {
            }
        }
    }
    public abstract void setFilters(Session s, Date today, Date tomorrow);

}
