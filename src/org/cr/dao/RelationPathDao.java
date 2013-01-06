/**
 * 
 */
package org.cr.dao;

import org.cr.model.RelationPathBean;


/**
 * @Description	
 * @author caorong
 * @date 2013-1-6
 * 
 */
public interface RelationPathDao {
	/**
	 * @Description 添加path
	 * @param relationPathBean
	 * @return
	 * @author caorong
	 */
	public int insertRelationPathBean(RelationPathBean relationPathBean);
	
	/**
	 * @Description 查询重复path
	 * @param relationPathBean
	 * @return
	 * @author caorong
	 */
	public int queryRelationPathBeanByBean(RelationPathBean relationPathBean);
}
