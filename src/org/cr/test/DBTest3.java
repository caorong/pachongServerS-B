/**
 * 
 */
package org.cr.test;

import org.cr.dao.impl.RelationPathDaoImpl;
import org.cr.model.RelationPathBean;
import org.cr.util.Identities;

/**
 * @Description	
 * @author caorong
 * @date 2013-1-6
 * 
 */
public class DBTest3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RelationPathDaoImpl relationPathDaoImpl = new RelationPathDaoImpl();
		RelationPathBean relationPathBean = new RelationPathBean("cuid","u","x","y","ex","ey","name","n","d");
		
		for(int i=0;i<10;i++){
			relationPathBean.setId(Identities.create32LenUUID());
			if(relationPathDaoImpl.queryRelationPathBeanByBean(relationPathBean)==0){
				relationPathDaoImpl.insertRelationPathBean(relationPathBean);
			}
		}
		
	}

}
