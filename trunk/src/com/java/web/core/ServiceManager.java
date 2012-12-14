package com.java.web.core;



public class ServiceManager {

    private ServiceManager() { }

	public static Service getAction(String action) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String class_name = ServiceDictionary.getAkcije().get(action);
        return (Service) Class.forName(class_name).newInstance();
	}
    
}
