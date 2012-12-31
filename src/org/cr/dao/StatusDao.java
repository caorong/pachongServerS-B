package org.cr.dao;

import org.cr.model.StatusBean;



public interface StatusDao {
	/**
	 * @Description 添加status
	 * @param statusBean
	 * @return
	 * @author caorong
	 */
	public int insertStatus(StatusBean status);	
	
	/**
	 * @Description 查询记录是否存在
	 * @param wid
	 * @return
	 * @author caorong
	 */
	int queryCountByWid(String wid);
	
}
