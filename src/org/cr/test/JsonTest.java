package org.cr.test;

import java.util.ArrayList;
import java.util.List;


import org.cr.model.UserXmlBean;

import weibo4j.org.json.JSONArray;

/**
 * @description 
 * @author caorong
 * @date 2013-1-5
 */
public class JsonTest {

	public static void main(String[] args) {
		List<UserXmlBean> lists = new ArrayList<UserXmlBean>();
		UserXmlBean xmlBean = new UserXmlBean();
		xmlBean.setName("xname");
		xmlBean.setType(2);
		xmlBean.setUid("xuid");
		UserXmlBean xmlBean1 = new UserXmlBean();
		xmlBean1.setName("1xname");
		xmlBean1.setType(3);
		xmlBean1.setUid("1xuid");
		lists.add(xmlBean);
		lists.add(xmlBean1);
		
		JSONArray jsonArray = new JSONArray(lists);
		System.out.println(jsonArray.toString());
	}
}
