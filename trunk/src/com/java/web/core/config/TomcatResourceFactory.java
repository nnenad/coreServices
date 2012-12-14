
package com.java.web.core.config;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.HashMap;
import javax.naming.Name;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.RefAddr;
import javax.naming.spi.ObjectFactory;


public class TomcatResourceFactory implements ObjectFactory {

    public Object getObjectInstance(Object obj, Name name, Context cntx, Hashtable env) throws NamingException {
        HashMap<String, String> result = new HashMap<String, String>();
        RefAddr addr = null;
        Enumeration addrs = ((Reference) (obj)).getAll();
        while (addrs.hasMoreElements()) {
            addr = (RefAddr) addrs.nextElement();
            String key = (String) (addr.getType());
            String value = (String) addr.getContent();
            result.put(key, value);
        }
        return result;
    }


}
