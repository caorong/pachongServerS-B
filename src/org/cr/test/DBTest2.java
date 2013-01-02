package org.cr.test;

import java.util.Iterator;

import org.cr.dao.impl.ReStatusDaoImpl;
import org.cr.model.ReStatusBean;
import org.cr.util.Identities;

/**
 * @description
 * @author caorong
 * @date 2013-1-2
 */
public class DBTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReStatusBean re = new ReStatusBean();
		re.setDeepth("1");
		re.setFatherwid("faww");
		re.setId("id");
		re.setSelfwid("selfww");
		re.setSelfuid("selfuid");
		re.setText("text");
		re.setUid("uid");
		re.setWid("wid");
		re.setAuthorfansflag("1");
		ReStatusDaoImpl reStatusDaoImpl = new ReStatusDaoImpl();
		for (int i = 0; i < 10; i++) {
			String uniID = Identities.create32LenUUID();
			re.setId(uniID);
//			System.out.println("Uni ID:------->"+uniID);
			re.setUid(Identities.randomString(20));
			if (reStatusDaoImpl.queryReStatusByBean(re) == 0) {
				reStatusDaoImpl.insertReStatus(re);
			}
		}
	}
}
