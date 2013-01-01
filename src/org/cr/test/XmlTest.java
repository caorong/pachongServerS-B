package org.cr.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.cr.util.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @description 
 * @author caorong 
 * @date 2013-1-1
 */
public class XmlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		for(String s : new XMLUtil().getCommList("src/org/cr/resource/goodComm.xml")){
			System.out.println(s);
		}
		
		
		
		
		DocumentBuilder db = null;
		Document doc = null;
		try {// 获得documentBuilder
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		try {// 获得 Document 这个Document就是一个XML文件在内存中的镜像
			doc = db.parse(new File("src/org/cr/resource/goodComm.xml"));
		} catch (SAXException  e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 在xml文件里,只有一个根元素,先把根元素拿出来看看
		Element element = doc.getDocumentElement();

		NodeList userNodes = element.getElementsByTagName("comm");
//		List<UserXmlBean> userList = new ArrayList<UserXmlBean>();
		for (int i = 0; i < userNodes.getLength(); i++) {
			Element userElement = (Element) userNodes.item(i);
			
//			UserXmlBean user = new UserXmlBean();
			NodeList chileNodes = userElement.getChildNodes();
//			user.setName(userElement.getAttribute("id"));//将属性即 uid对于的中文名 记录进集合
//			System.out.println("length:" + chileNodes.getLength() ); 
//			System.out.println("0: " + chileNodes.item(0).getNodeName());
//			System.out.println("1: " + chileNodes.item(1).getNodeName());
//			System.out.println("2: " + chileNodes.item(2).getNodeName());
			for (int j = 0; j < chileNodes.getLength(); j++) {
				if (chileNodes.item(j).getNodeType() == Node.CDATA_SECTION_NODE) {	
//					System.out.println(chileNodes.item(j).getNodeValue());
//						 chileNodes.item(j).getFirstChild().getNodeValue();
				}
			}
//			userList.add(user);
		}
	}
}
