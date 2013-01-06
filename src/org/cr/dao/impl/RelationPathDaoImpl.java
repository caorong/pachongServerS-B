/**
 * 
 */
package org.cr.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.cr.dao.RelationPathDao;
import org.cr.model.RelationPathBean;
import org.cr.util.DBUtil;

/**
 * @Description	
 * @author caorong
 * @date 2013-1-6
 * 
 */
public class RelationPathDaoImpl implements RelationPathDao {
	static Logger log = Logger.getLogger(RelationPathDaoImpl.class.getName());
	
	private SqlSession session = null;

	private SqlSession getSqlSession() {
		session = DBUtil.getSessionFactory().openSession();
		return session;
	}
	
	@Override
	public int insertRelationPathBean(RelationPathBean relationPathBean) {
		int ans = 0;
		try {
			session = this.getSqlSession();
			RelationPathDao relationPathDao = session.getMapper(RelationPathDao.class);
			ans = relationPathDao.insertRelationPathBean(relationPathBean);
			log.info("insert relationPathBean"+relationPathBean.toString());
			session.commit();
			log.info("insert relationPathBean Success!!! ");
		}catch (Exception e) {
			log.error("insert relationPathBean Failed!!! ---> "+relationPathBean.toString());
			e.printStackTrace();
		} finally{
			session.close();
		}
		return ans;
	}

	@Override
	public int queryRelationPathBeanByBean(RelationPathBean relationPathBean) {
		int ans = 0;
		try {
			session = this.getSqlSession();
			RelationPathDao relationPathDao = session.getMapper(RelationPathDao.class);
			ans = relationPathDao.queryRelationPathBeanByBean(relationPathBean);
		} catch (Exception e) {
			log.error("query Failed!!! ");
			e.printStackTrace();
		}finally{
			session.close();
		}
		return ans;
	}

}
