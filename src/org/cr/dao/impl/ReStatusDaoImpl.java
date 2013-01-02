package org.cr.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.cr.dao.ReStatusDao;
import org.cr.model.ReStatusBean;
import org.cr.util.DBUtil;

/**
 * @description
 * @author caorong
 * @date 2013-1-2
 */
public class ReStatusDaoImpl implements ReStatusDao {

	static Logger log = Logger.getLogger(ReStatusDaoImpl.class.getName());

	private SqlSession session = null;

	private SqlSession getSqlSession() {
		session = DBUtil.getSessionFactory().openSession();
		return session;
	}

	@Override
	public void insertReStatus(ReStatusBean reStatusBean) {
		try {
			session = this.getSqlSession();
			ReStatusDao reStatusDao = session.getMapper(ReStatusDao.class);
			reStatusDao.insertReStatus(reStatusBean);
			log.info("insert ReStatus " + reStatusBean.toString());
			session.commit();
			log.info("insert ReStatus Successed!!! ");
		} catch (Exception e) {
			log.error("insert ReStatus Failed!!! ---->"
					+ reStatusBean.toString());
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public int queryReStatusByBean(ReStatusBean reStatusBean) {
		int ans = 0;
		try {
			session = this.getSqlSession();
			ReStatusDao reStatusDao = session.getMapper(ReStatusDao.class);
			ans = reStatusDao.queryReStatusByBean(reStatusBean);
		} catch (Exception e) {
			log.error("query Failed!!! ");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ans;
	}
}
