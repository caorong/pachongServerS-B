
package org.cr.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import org.cr.dao.StatusDao;
import org.cr.model.StatusBean;
import org.cr.util.DBUtil;

/**
 * @description 
 * @author caorong
 * @date 2012-12-30
 */
public class StatusDaoImpl implements StatusDao {
	private SqlSession session = null;
	
	private SqlSession getSqlSession() {
		session = DBUtil.getSessionFactory().openSession();
		return session;
	}
	
	@Override
	public int insertStatus(StatusBean status) {
		int ans = 0;
		try {
			session = this.getSqlSession();
			StatusDao statusDao = session.getMapper(StatusDao.class);
			ans = statusDao.insertStatus(status);
			log.info("insert Status"+status.toString());
			session.commit();
			log.info("insert Status successed!!!");
		} catch (Exception e) {
			log.error("insert Status Failed!!! --->"+status.toString());
			e.printStackTrace();
		} finally{
			session.close();
		}
		return ans;
	}

 
	@Override
	public int queryCountByWid(String wid) {
		int ans = 0;
		try {
			session = this.getSqlSession();
			StatusDao statusDao = session.getMapper(StatusDao.class);
			ans = statusDao.queryCountByWid(wid);
		} catch (Exception e) {
			log.error("query Failed!!! ");
			e.printStackTrace();
		} finally{
			session.close();
		}
		return ans;
	}
	
	static Logger log = Logger.getLogger(StatusDaoImpl.class.getName());
}
