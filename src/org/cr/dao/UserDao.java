package org.cr.dao;

import org.cr.model.UserBean;


public interface UserDao {
	/**
	 * @Description 添加user
	 * @param userBean
	 * @return
	 * @author caorong
	 */
	public int insertUser(UserBean user);	
	
	/**
	 * @Description 查询记录是否存在
	 * @param uid
	 * @return
	 * @author caorong
	 */
	int queryCountByUid(String uid);
	
}
