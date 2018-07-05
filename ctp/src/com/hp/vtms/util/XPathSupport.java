package com.hp.vtms.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.jaxen.NamespaceContext;
import org.jaxen.SimpleNamespaceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class XPathSupport {

	private static Logger _LOG = LoggerFactory.getLogger(XPathSupport.class);

	public String getUrl(byte[] streambyte, String xPathString) {

		DocumentFactory factory = DocumentFactory.getInstance();
		SAXReader reader = new SAXReader(factory);

		InputStream is = byte2Input(streambyte);

		Document document = null;
		try {
			if (is != null) {
				document = reader.read(is);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		Map<String, String> namespaceMap = new HashMap<String, String>();
		namespaceMap.put("t", "http://www.vmware.com/vcloud/v1.5");
		XPath xPath = null;
		Node node = null;

		NamespaceContext namespaceContext = new SimpleNamespaceContext(namespaceMap);
		try {
			try {
				xPath = factory.createXPath(xPathString);
			} catch (Exception e) {

				e.getStackTrace();
			}
			if(xPath!=null){
			xPath.setNamespaceContext(namespaceContext);
			try {
				node = xPath.selectSingleNode(document);
			} catch (Exception e) {

				e.getStackTrace();
			}
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		String result = "";
		if (node != null) {
			result = node.selectSingleNode("@href").getText();
		}
		return result;

	}

	public String getLoginUrl(byte[] streambyte, String xPathString) {

		DocumentFactory factory = DocumentFactory.getInstance();
		SAXReader reader = new SAXReader(factory);

		InputStream is = byte2Input(streambyte);

		Document document = null;
		try {
			if (is != null) {
				document = reader.read(is);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		Map<String, String> namespaceMap = new HashMap<String, String>();
		namespaceMap.put("t", "http://www.vmware.com/vcloud/versions");
		XPath xPath = null;
		Node node = null;

		NamespaceContext namespaceContext = new SimpleNamespaceContext(namespaceMap);
		try {
			try {
				xPath = factory.createXPath(xPathString);
			} catch (Exception e) {

				e.getStackTrace();
			}
			if(xPath!=null){
			xPath.setNamespaceContext(namespaceContext);
			try {
				node = xPath.selectSingleNode(document);
			} catch (Exception e) {

				e.getStackTrace();
			}
			}

		} catch (Exception e) {
			e.getStackTrace();
		}

		String result = "";
		if (node != null) {
			result = node.selectSingleNode("@href").getText();
		}
		return result;

	}

	public String getStatus(byte[] streambyte, String xPathString) {

		DocumentFactory factory = DocumentFactory.getInstance();
		SAXReader reader = new SAXReader(factory);

		InputStream is = byte2Input(streambyte);

		Document document = null;
		try {
			if (is != null) {
				document = reader.read(is);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		Map<String, String> namespaceMap = new HashMap<String, String>();
		namespaceMap.put("t", "http://www.vmware.com/vcloud/v1.5");
		XPath xPath = null;
		Node node = null;

		NamespaceContext namespaceContext = new SimpleNamespaceContext(namespaceMap);
		try {
			try {
				xPath = factory.createXPath(xPathString);
			} catch (Exception e) {

				e.getStackTrace();
			}
       if(xPath!=null){
			xPath.setNamespaceContext(namespaceContext);
			try {
				node = xPath.selectSingleNode(document);
			} catch (Exception e) {

				e.getStackTrace();
			}
         }
		} catch (Exception e) {
			e.getStackTrace();
		}

		String result = "";
		if (node != null) {
			result = node.selectSingleNode("@status").getText();
		}
		return result;

	}

	public String getTicketInfo(byte[] streambyte, String xPathString) {

		DocumentFactory factory = DocumentFactory.getInstance();
		SAXReader reader = new SAXReader(factory);

		InputStream is = byte2Input(streambyte);

		Document document = null;
		try {
			if (is != null) {
				document = reader.read(is);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		Map<String, String> namespaceMap = new HashMap<String, String>();
		namespaceMap.put("t", "http://www.vmware.com/vcloud/v1.5");
		XPath xPath = null;
		Node node = null;

		NamespaceContext namespaceContext = new SimpleNamespaceContext(namespaceMap);
		try {
			try {
				xPath = factory.createXPath(xPathString);
			} catch (Exception e) {

				e.getStackTrace();
			}
           if(xPath!=null){
			xPath.setNamespaceContext(namespaceContext);
			try {
				node = xPath.selectSingleNode(document);
			} catch (Exception e) {

				e.getStackTrace();
			}
           }
		} catch (Exception e) {
			e.getStackTrace();
		}

		String result = "";
		if (node != null) {
			result = node.getText();
		}
		return result;

	}

	public InputStream byte2Input(byte[] buf) {
		InputStream input = null;
		if (buf != null) {
			input = new ByteArrayInputStream(buf);
		}

		return input;
	}
}
