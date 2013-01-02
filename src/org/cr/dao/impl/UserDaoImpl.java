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
	static Logger log = Logger.getLogger(UserDaoImpl.class.getName());
	
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
			log.info("insert user"+user.toString());
			session.commit();
			log.info("insert user Success!!! ");
		} catch (Exception e) {
			log.error("insert user Failed!!! ---> "+user.toString());
			e.printStackTrace();
		} finally{
			session.close();
		}
		return ans;
	}

	@Override
	public int queryCountByUid(String uid) {
		int ans = 0;
		try {
			session = this.getSqlSession();
			UserDao userDao = session.getMapper(UserDao.class);
			ans = userDao.queryCountByUid(uid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			session.close();
		}
		return ans;
	}

	@Override
	public UserBean querySingleUserByUid(String uid) {
		UserBean res = null;
		try {
			session = this.getSqlSession();
			UserDao userDao = session.getMapper(UserDao.class);
			res = userDao.querySingleUserByUid(uid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return res;
	}

	@Override
	public void updateSingleUser(UserBean user) {
		try {
			session = this.getSqlSession();
			UserDao userDao = session.getMapper(UserDao.class);
			userDao.updateSingleUser(user);
			log.info("update user"+user.toString());
			session.commit();
			log.info("update Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
