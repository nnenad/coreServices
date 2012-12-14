
package com.java.web.core.internationalization;


import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import com.java.web.core.lib.http.HttpContext;
import com.java.web.core.Constants;

public class MlManager {

    public static final String[] ImplementedLocales = {
        "no",
        "en"
    };

    private static final ConcurrentHashMap<String, Locale> _locals = createLocals();

    private static ConcurrentHashMap<String, Locale> createLocals() {
        ConcurrentHashMap<String, Locale> result = new ConcurrentHashMap<String, Locale>();
        result.put("no", new Locale("no", "NO"));
        result.put("en", Locale.UK);
        return result;
    }

    public static Locale getLocale(String localeCode) {
        Locale result = _locals.get(localeCode);
        return result;
    }

    public static Locale getCurrentLocale(){
        Locale result = HttpContext.getSessionVar(Constants.KEY_CURRENT_LOCALE);
        if (result == null) {
            result = _locals.get("no");
            HttpContext.setSessionVar(Constants.KEY_CURRENT_LOCALE, result);
        }
        return result;
    }
    public static String getCurrentLocaleCode() {
        Locale locale = getCurrentLocale();
        String result = locale.getLanguage();
        return result;
    }
    public static void setCurrentLocale(String localeCode) {
        if (!_locals.containsKey(localeCode))
            throw new IllegalArgumentException(MlManager.gettext("Unsupported locale") + " " + localeCode);
        HttpContext.setSessionVar(Constants.KEY_CURRENT_LOCALE, _locals.get(localeCode));
    }

    public static String gettext(String txt) {
        String result = gettext(txt, getCurrentLocale());
        return result;
    }
    public static String gettext(String txt, String localeCode) {
        if (!_locals.containsKey(localeCode))
            throw new IllegalArgumentException(MlManager.gettext("Unsupported locale") + " " + localeCode);
        String result = gettext(txt, getLocale(localeCode));
        return result;
    }
    public static String gettext(String txt, Locale locale) {
        String result = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("com.java.web.core.internationalization.MlDict", locale);
            result = bundle.getString(txt);
        } catch (Exception ex) { }
        return result != null ? result : txt;
    }

}
