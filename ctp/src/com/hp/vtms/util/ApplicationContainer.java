package com.hp.vtms.util;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContainer {

	private Map<String, Object> map = new HashMap<String, Object>();

	private static  ApplicationContainer applicationContainer;

	public synchronized static ApplicationContainer getInstance() {
		if (applicationContainer == null) {
			applicationContainer = new ApplicationContainer();
		}
		return applicationContainer;

	}

	private ApplicationContainer() {

	}

	public synchronized Object getObject(String key) {
		return map.get(key);

	}

	public synchronized void setObject(String key, Object value) {
		map.put(key, value);

	}

}
