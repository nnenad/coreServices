package com.java.web.core;

import com.java.web.core.lib.http.HttpContext;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class ServiceDictionary {

	private static Map<String, String> actions;
    private static Map<String,ArrayList<String>> modulesMap;
    private static boolean initialized = false;


	private static synchronized void  initActions() {
		actions = new HashMap<String, String>();
                modulesMap = new HashMap<String,ArrayList<String>>();

		try {
            String path = HttpContext.context().getRealPath("/WEB-INF/classes/actions.xml");
			File file = new File(path);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			loadNodes(doc, "action", actions);
            initialized = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadNodes(Document dokument, String tag, Map<String, String> lista) {
		try {
			NodeList listaNodova = dokument.getElementsByTagName(tag);

			for (int i = 0; i < listaNodova.getLength(); i++) {

				Node nod = listaNodova.item(i);

				if (nod.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nod;

					NodeList descList = element.getElementsByTagName("description");
					Element descElement = (Element) descList.item(0);
					NodeList desc = descElement.getChildNodes();
					String akcija = desc.item(0).getNodeValue();

					NodeList classList = element.getElementsByTagName("class");
					Element classElement = (Element) classList.item(0);
					NodeList clss = classElement.getChildNodes();
					String klasa = clss.item(0).getNodeValue();

                    NodeList modulesList = element.getElementsByTagName("modules");
					Element modulesElement = (Element) modulesList.item(0);
					NodeList modules = modulesElement.getChildNodes();
                    ArrayList<String> modulesArray = new ArrayList<String>();
                    for (int j = 0; j < modules.getLength(); j++) {
                        Node nod1 = modules.item(j);
                        if(nod1.getNodeType() == Node.ELEMENT_NODE){
                            Element element1 = (Element) nod1;
                            NodeList modulList1 = element1.getChildNodes();
                            String modul = modulList1.item(0).getNodeValue();
                            modulesArray.add(modul);
                        }
                    }
                    modulesMap.put(klasa, modulesArray);
					lista.put(akcija, klasa);
				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Map<String, String> getAkcije() {
            if(!initialized)
                initActions();
		return actions;
	}
    public static Map<String,ArrayList<String>> getModules() {
            if(!initialized)
                initActions();
		return modulesMap;
	}

}
