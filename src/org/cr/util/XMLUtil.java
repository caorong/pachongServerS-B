package org.cr.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.cr.model.UserXmlBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * @description 将xml里的数据提举到Bean
 * @author caorong
 * @date 2012-12-31
 */
public class XMLUtil {
	
	static Logger log = Logger.getLogger(XMLUtil.class.getName());
	
	private DocumentBuilder db = null;
	private Document doc = null;
	
	
	public List<String> getCommList(String path){
		try {// 获得documentBuilder
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		try {// 获得 Document 这个Document就是一个XML文件在内存中的镜像
			doc = db.parse(new File(path));
		} catch (SAXException  e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Element element = doc.getDocumentElement();
		NodeList userNodes = element.getElementsByTagName("comm");
		List<String> commlists = new ArrayList<String>();
		for (int i = 0; i < userNodes.getLength(); i++) {
			Element userElement = (Element) userNodes.item(i);
			NodeList chileNodes = userElement.getChildNodes();
			for (int j = 0; j < chileNodes.getLength(); j++) {
				//将Cdata里的数据 放进去
				if (chileNodes.item(j).getNodeType() == Node.CDATA_SECTION_NODE) {	
					//去空格
					commlists.add(chileNodes.item(j).getNodeValue().trim());
				}
			}
		}
		return commlists;
	}
	
	public List<UserXmlBean> getXmlUserLists(String path){
		try {// 获得documentBuilder
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		try {// 获得 Document 这个Document就是一个XML文件在内存中的镜像
			doc = db.parse(new File(path));
		} catch (SAXException  e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 在xml文件里,只有一个根元素,先把根元素拿出来看看
		Element element = doc.getDocumentElement();

		NodeList userNodes = element.getElementsByTagName("user");
		List<UserXmlBean> userList = new ArrayList<UserXmlBean>();
		for (int i = 0; i < userNodes.getLength(); i++) {
			Element userElement = (Element) userNodes.item(i);
			UserXmlBean user = new UserXmlBean();
			NodeList chileNodes = userElement.getChildNodes();
			user.setName(userElement.getAttribute("id"));//将属性即 uid对于的中文名 记录进集合
			for (int j = 0; j < chileNodes.getLength(); j++) {
				if (chileNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if ("uid".equals(chileNodes.item(j).getNodeName())) {
						user.setUid(chileNodes.item(j).getFirstChild().getNodeValue());
					} else if ("type".equals(chileNodes.item(j).getNodeName())) {
						user.setType(Integer.parseInt(chileNodes.item(j).getFirstChild().getNodeValue()));
					}
				}
			}
			userList.add(user);
			log.info("insert to xml list:---> " + user.toString());
		}
		return userList;
	}
}
