package org.cr.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.cr.dao.impl.ReStatusDaoImpl;
import org.cr.dao.impl.StatusDaoImpl;
import org.cr.dao.impl.UserDaoImpl;
import org.cr.model.ReStatusBean;
import org.cr.model.StatusBean;
import org.cr.model.UserBean;
import org.cr.model.UserXmlBean;
import org.cr.util.Identities;
import org.cr.util.WordCheckUtil;
import org.cr.util.XMLUtil;

import weibo4j.Comments;
import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
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
	//全局dao实例
	private UserDaoImpl userDaoImpl;
	private StatusDaoImpl statusDaoImpl;
	private ReStatusDaoImpl reStatusDaoImpl;
	
	//验证是否关注原作者的controller
	private Friendships friendships;
	
	// 全局sleep时间
	private int sleepSec = 25;
	
	public ServiceThread(String access_token) {
		this.access_token = access_token;
		//从xml里获取给定的type然后update db
		XMLUtil xmlUtil = new XMLUtil();
		List<UserXmlBean> xmlLists = xmlUtil.getXmlUserLists("src/org/cr/resource/devUid.xml");
		for(UserXmlBean u: xmlLists){
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
	 * 抓一个人，获取他的200 dev(10)条微博，对每条做 1.深度操作 ，获得树节点然后入db
	 * 2.评论操作，分析评论，将结果(好评，坏评)，入db
	 * 
	 */
	@Override
	public void run() {
		
		// 对每个user抓取的微博条数
		int getWeiboCount = 100;
		// 好坏评论
		int commGoodCount = 0;
		int commBadCount = 0;
		//当前被转载微博的wid
		String repoWid;
		//当前被转载微博的uid
		String repoUid;
		//转发的二层的关注作者的flag  用于判断第三层是否需要记录进数据库
		boolean lv2IsFansFlag = true;
		
		Timeline timeline = new Timeline();
		timeline.client.setToken(access_token);
		
		/**sleep**/
		sleep(sleepSec*1000);
		
		Comments comments = new Comments();
		comments.client.setToken(access_token);   

		/**sleep**/
		sleep(sleepSec*1000);
		
		friendships = new Friendships();
		friendships.client.setToken(access_token);
		
		/**sleep**/
		sleep(sleepSec*1000);
		
		//dao 实例化
		statusDaoImpl = new StatusDaoImpl();
		reStatusDaoImpl = new ReStatusDaoImpl();
		userDaoImpl = new UserDaoImpl();
		
		while (true) {
			//遍历 所有的user id
			for(UserXmlBean userXmlBean: this.userLists){
				//根据weibo添加评论 然后人db 改flag
				StatusWapper statusWapper = null;
				try {
					statusWapper = timeline.getUserTimelineByUid(userXmlBean.getUid(), new Paging(1, getWeiboCount), 0, 1);
				} catch (WeiboException e) {
					e.printStackTrace();
				}
				/**sleep**/
				sleep(sleepSec*1000);
				
				List<Status> weibolists = statusWapper.getStatuses();
				for (Status st : weibolists) {
					//db里没有此 记录 才insert
					if (statusDaoImpl.queryCountByWid(st.getId()) == 0) {
						/********************* 1 判断weibo的好 坏数  ************************/
						//物尽其用，将status里的User放进db
						User user = st.getUser();
						if(user!=null && userDaoImpl.queryCountByUid(user.getId()) == 0){
							userDaoImpl.insertUser(this.revertBean(user));
						}
						CommentWapper commentWapper = null;
						try {
							commentWapper = comments.getCommentById(st.getId(), new Paging(1, 200), 0);
						} catch (WeiboException e) {
							e.printStackTrace();
						}
						/**sleep**/
						sleep(sleepSec*1000);
						
						List<Comment> commentslists = commentWapper.getComments();
						commGoodCount = 0;
						commBadCount = 0;
						for (Comment comm : commentslists) {
							// 对每条微博进行过滤好坏
							int ans = WordCheckUtil.wordCheck(comm.getText());
							if (ans == 1) {
								commGoodCount++;
							} else if (ans == 2) {
								commBadCount++;
							}
						}

						/********************* 2 进行weibo深度处理     2.1入 db *********************/
						StatusWapper reStatusWapper = null;
						try {
							reStatusWapper = timeline.getRepostTimeline(st.getId(), new Paging(1,200));
						} catch (WeiboException e) {
							e.printStackTrace();
						}
						/**sleep**/
						sleep(sleepSec*1000);
						
						//获取200条转发Status 集合
						List<Status> reStatus = reStatusWapper.getStatuses();
						//
						repoWid = st.getId();
						repoUid = st.getUser().getId();
						//第一层reStatus
						for(Status reSt : reStatus){
							//sina服务器有时返一个空壳  造成nullPointException
							if (reSt == null || reSt.getRetweetedStatus() == null)
								continue;
							//先入db再遍历其子节点
							//条件为--不关注--作者的数据入库
							this.insertRepostToDb(reSt, repoWid, repoUid, reSt.getRetweetedStatus().getId(), 1 ,"0");
							//条件为--可能关注--作者的数据入库
							this.insertRepostToDb(reSt, repoWid, repoUid, reSt.getRetweetedStatus().getId(), 1 ,"1");
							//flag 初始化
							lv2IsFansFlag = false;
							//当转载次数大于1才调API
							if(reSt.getRepostsCount() > 0){
								StatusWapper statusWapperlv2 = null;
								try {
									//转载了第一层的weibo集合
									statusWapperlv2 = timeline.getRepostTimeline(reSt.getId(), new Paging(1, 200));
								} catch (WeiboException e) {
									e.printStackTrace();
								}
								/**sleep**/
								sleep(sleepSec*1000);
								
								//转载了第一层的weibo集合
								List<Status> statuslv2 = statusWapperlv2.getStatuses();
								//第二层reStatus
								for(Status reStlv2 : statuslv2){
									//sina服务器有时会一个空壳  造成nullPointException
									if (reStlv2 == null || reStlv2.getRetweetedStatus() == null)
										continue;
									//条件为--可能关注--作者的数据入库 
									this.insertRepostToDb(reStlv2, repoWid, repoUid, reSt.getId(), 2, "1");
									//验证2层转载人是否关注原作者
									if(!this.isAuthorFans(reStlv2.getUser().getId(),repoUid)){
										//条件为--不关注--作者的数据入库	
										this.insertRepostToDb(reStlv2, repoWid, repoUid, reSt.getId(), 2, "0");
										//让后面的第三层录入数据库 不然不需要录入
										lv2IsFansFlag = true;
									}
									//当转载次数大于1才调API
									if(reStlv2.getRepostsCount()>0){
										StatusWapper statusWapperlv3 = null;
										try {
											statusWapperlv3 = timeline.getRepostTimeline(reStlv2.getId(), new Paging(1,200));
										} catch (WeiboException e) {
											e.printStackTrace();
										}
										/**sleep**/
										sleep(sleepSec*1000);
										
										//转载了第二层的weibo集合
										List<Status> statuslv3 = statusWapperlv3.getStatuses();
										//第三层reStatus
										for(Status reStlv3 : statuslv3){
											//sina服务器有时会一个空壳  造成nullPointException
											if (reStlv3 == null || reStlv3.getRetweetedStatus() == null)
												continue;
											//条件为--可能关注--作者的数据入库 
											this.insertRepostToDb(reStlv3, repoWid, repoUid, reStlv2.getId(), 3, "1");
											//验证3层转载人是否关注原作者  如果2层的flag不是true  直接忽略
											if(lv2IsFansFlag || !this.isAuthorFans(reStlv3.getUser().getId(), repoUid)){
												//条件为--不关注--作者的数据入库	
												this.insertRepostToDb(reStlv3, repoWid, repoUid, reStlv2.getId(), 3, "0");
											}
										}
									}
								}
							}

						}
						
						/********************* 3 并将微博的评论数等信息一起insert db  ************/
						// 构造模型
						StatusBean statusBean = new StatusBean();

						statusBean.setUid(st.getUser().getId());
						statusBean.setCreatedAt(st.getCreatedAt());
						statusBean.setWid(st.getId());
						statusBean.setText(st.getText());
						statusBean.setUrl(st.getSource().getUrl());

						statusBean.setRelationShip(st.getSource().getRelationship());
						statusBean.setName(st.getSource().getName());
						String flag = "1";
						if (!st.isFavorited()) {
							flag = "0";
						}
						statusBean.setFavorited(flag);
						flag = "1";
						if (!st.isTruncated()) {
							flag = "0";
						}
						statusBean.setTruncated(flag);
						statusBean.setThumbnailPic(st.getThumbnailPic());

						statusBean.setBmiddlePic(st.getBmiddlePic());
						statusBean.setOriginalPic(st.getOriginalPic());
						statusBean.setGeo(st.getGeo());
						statusBean.setLatitude(st.getLatitude());
						statusBean.setLongitude(st.getLongitude());

						statusBean.setRepostsCount(st.getRepostsCount() + "");
						statusBean.setCommentsCount(st.getCommentsCount() + "");
						statusBean.setAttitudescount(st.getCommentsCount() + "");
						//已经完成转载信息入库的操作(2b了，没有意义)
						statusBean.setRepostsFlag("1");

						statusBean.setCommentGoodCount(commGoodCount + "");
						statusBean.setCommentBadCount(commBadCount + "");
						//已经完成评论信息入库的操作(2b了，没有意义)
						statusBean.setCommentsFlag("1");

						log.debug("insert status data " + statusBean.toString());
						statusDaoImpl.insertStatus(statusBean);
					}
				}
			}
			// sleep 100s
			sleep(1000 * sleepSec);
		}
	}

	/**
	 * @description  将User转成UserBean
	 * @param User
	 * @return Userbean
	 * */
	private UserBean revertBean(User user){
		return new UserBean();
	}
	
	/**
	 * @descrpition 是否关注原来的作者
	 * @param String 待check的user id uid
	 * @param String 作者id
	 * @return 关注了原来作者== true
	 * */
	private boolean isAuthorFans(String uid, String authorId) {
		String friends[] = null;
		/**sleep**/
		sleep(sleepSec*1000);
		try {
			//默认一个用户关注的人不超过2000人
			friends = friendships.getFriendsIdsByUid(uid, 2000, 0);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		for(String friend : friends){
			if(friend.equals(authorId)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @description 封装insert转载
	 * @param Status
	 * @param String repoWid 作者微博id
	 * @param String repoUid 作者uid
	 * @param String fatherWid  父wid
	 * @param int deep
	 * @param String authorfansflag 0为不关注 1为关注 
	 * */
	private void insertRepostToDb(Status status, String repoWid, String repoUid, String fatherWid, int deep,String authorfansflag ) {
		ReStatusBean reStatusBean = new ReStatusBean();
		//生成唯一id
		reStatusBean.setId(Identities.create32LenUUID());
		reStatusBean.setUid(repoUid);
		reStatusBean.setWid(repoWid);
		reStatusBean.setFatherwid(fatherWid);
		reStatusBean.setSelfwid(status.getId());
		reStatusBean.setSelfuid(status.getUser().getId());
		reStatusBean.setDeepth(deep+"");
		reStatusBean.setText(status.getText());
		reStatusBean.setAuthorfansflag(authorfansflag);
		//保证不插入重复数据
		if(reStatusDaoImpl.queryReStatusByBean(reStatusBean) == 0){
			reStatusDaoImpl.insertReStatus(reStatusBean);
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
