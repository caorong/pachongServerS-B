package org.cr.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.cr.dao.impl.UserDaoImpl;
import org.cr.model.UserBean;
import org.cr.model.UserXmlBean;
import org.cr.util.XMLUtil;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

/**
 * @description 抓取总线程
 * @author caorong
 * @date 2012-12-31
 */
public class ServiceThread implements Runnable {

	static Logger log = Logger.getLogger(ServiceThread.class.getName());
	
	private String access_token;
	//需要抓取的人的uid
	private List<UserXmlBean> userLists;
	
	public ServiceThread(String access_token) {
		this.access_token = access_token;
		//从xml里获取给定的type然后update db
		XMLUtil xmlUtil = new XMLUtil();
		List<UserXmlBean> xmlLists = xmlUtil.getXmlUserLists("src/org/cr/resource/devUid.xml");
		for(UserXmlBean u: xmlLists){
//			System.out.println(u.toString());
			UserDaoImpl userDaoImpl = new UserDaoImpl();
			UserBean tmpbean = userDaoImpl.querySingleUserByUid(u.getUid());
			//改动type不同的入db  未设置type或者type不想等的
			if (tmpbean.getType() == null || !tmpbean.getType().equals(u.getType() + "")) {
				tmpbean.setType(u.getType() + "");
				userDaoImpl.updateSingleUser(tmpbean);
			}
		}
		this.userLists = xmlLists;
	}

	/**
	 * 抓一个人，获取他的200 dev(10)条微博，对每条做
	 * 1.深度操作 ，获得树节点然后入db
	 * 2.评论操作，分析评论，将结果(好评，坏评)，入db
	 * 
	 */
	@Override
	public void run() {
		while (true) {
			// 全局sleep时间
			int sleepSec = 100;
			// 对每个user抓取的微博条数
			int getWeiboCount = 10;
			
			Timeline timeline = new Timeline();
			timeline.client.setToken(access_token);
			
			Comments comments = new Comments();
			comments.client.setToken(access_token);
			
			//遍历 所有的user id
			for(UserXmlBean userXmlBean: this.userLists){
				//根据weibo添加评论 然后人db 改flag
				StatusWapper statusWapper = null;
				try {
					statusWapper = timeline.getUserTimelineByUid(userXmlBean.getUid(), new Paging(1, getWeiboCount), 0, 1);
				} catch (WeiboException e) {
					e.printStackTrace();
				}
				List<Status> weibolists = statusWapper.getStatuses();
				for(Status st:weibolists){
					
					//1 获取微博的评论数
					
					
					//2 判断weibo的好 坏数 
					CommentWapper commentWapper = null;
					try {
						commentWapper = comments.getCommentById(st.getId(), new Paging(1, 200), 0);
					} catch (WeiboException e) {
						e.printStackTrace();
					}
					
					List<Comment> commentslists = commentWapper.getComments();
					for(Comment comm : commentslists){
						//对每条微博进行过滤好坏
						System.out.println(comm.getText());
					}
				}
				
				
			}
			
			// sleep 100s
			sleep(1000 * sleepSec);
		}
	}

	/**
	 * sleep
	 */
	private void sleep(long time) {
		if (time <= 0)
			time = 500;
		try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
