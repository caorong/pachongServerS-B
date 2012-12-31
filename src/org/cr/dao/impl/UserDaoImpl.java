/**
 * @description 
 * @author caorong
 * @date 2012-12-30
 * 
 */
package org.cr.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.cr.dao.UserDao;
import org.cr.model.UserBean;
import org.cr.util.DBUtil;

/**
 * @description
 * @author caorong
 * @date 2012-12-30
 */
public class UserDaoImpl implements UserDao {
	private SqlSession session = null;

	private SqlSession getSqlSession() {
		session = DBUtil.getSessionFactory().openSession();
		return session;
	}

	@Override
	public int insertUser(UserBean user) {
		int ans = 0;
		try {
			session = this.getSqlSession();
			UserDao userDao = session.getMapper(UserDao.class);
			ans = userDao.insertUser(user);
			log.debug("insert user"+user.toString());
			session.commit();
			log.debug("insert user Success!!! ");
		} catch (Exception e) {
			log.error("insert user Failed!!! ---> "+user.toString());
			e.printStackTrace();
		}
		return ans;
	}

	@Override
	public int queryCountByUid(String uid) {
		session = this.getSqlSession();
		UserDao userDao = session.getMapper(UserDao.class);
		return userDao.queryCountByUid(uid);
	}

	static Logger log = Logger.getLogger(UserDaoImpl.class.getName());
}
