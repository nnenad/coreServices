
package com.java.web.core.config.build;

import com.java.web.core.internationalization.MlManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;


public class BuildML {

    /*
     * var _ML = {
     *   'en' : {
     *      'yes':'yes','no':'no'
     *   },
     *   'no' : {
     *      'yes':'ja','no':'nain'
     *   }
     * }
     */
    public static void main(String[] args) {

        try {
            if (args.length < 1) {
                System.out.println("Arguments:");
                System.out.println("1) root folder to create locale js file");
                return;
            }

            String filename = args[0] + File.separator + "locale.js";
            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF8"));
            try {
                fw.append("var _ML={");
                boolean firstLocale = true;
                for (int i=0; i<MlManager.ImplementedLocales.length; i++) {
                    String localeCode = MlManager.ImplementedLocales[i];

                    if (firstLocale) firstLocale = false;
                    else fw.append(",");
                    
                    fw.append("'");
                    fw.append(localeCode.replace("'", "\\'"));
                    fw.append("':{");
                    
                    Locale locale = MlManager.getLocale(localeCode);
                    System.out.println(filename);
                    ResourceBundle bundle = ResourceBundle.getBundle("com.java.web.core.internationalization.MlDict_js", locale);
                    
                    boolean firstKey = true;
                    for (Enumeration<String> keys = bundle.getKeys(); keys.hasMoreElements(); ) {
                        String key = keys.nextElement();
                        String value = bundle.getString(key);
                        if (firstKey) firstKey = false;
                        else fw.append(",");
                        
                        fw.append(String.format("'%s':'%s'",
                                key.replace("'", "\\'"),
                                value.replace("'", "\\'")));
                    }

                    fw.append("}"); // locale end
                }
                fw.append("}");
            } finally {
                fw.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

}
