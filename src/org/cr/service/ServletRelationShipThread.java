/**
 * 
 */
package org.cr.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.cr.dao.impl.RelationPathDaoImpl;
import org.cr.dao.impl.UserDaoImpl;
import org.cr.model.RelationPathBean;
import org.cr.model.UserBean;
import org.cr.util.Identities;

import weibo4j.Friendships;
import weibo4j.model.Paging;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;

/**
 * @Description
 * @author caorong
 * @date 2013-1-6
 * 
 */
public class ServletRelationShipThread implements Runnable {

	static Logger log = Logger.getLogger(ServletRelationShipThread.class.getName());

	private String access_token;
	private UserDaoImpl userDaoImpl;
	private RelationPathDaoImpl relationPathDaoImpl;

	public ServletRelationShipThread(String access_token) {
		this.access_token = access_token;
	}

	@Override
	public void run() {
		// init
		userDaoImpl = new UserDaoImpl();
		relationPathDaoImpl = new RelationPathDaoImpl();
		
		// 谁的uid用户关系
		String printedUid = "1796537952";
		// 每层获取多少条
		int getRelationCount = 10;

		Friendships friendships = new Friendships();
		friendships.client.setToken(access_token);

		UserWapper userWapper = null;
		try {
			userWapper = friendships.getFriendsBilateral(printedUid, 0,new Paging(1, getRelationCount));
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		List<User> lv1Users = userWapper.getUsers();

		// canvas 属性初始化
		List<RelationPathBean> relationlists = new ArrayList<RelationPathBean>();

		// lv1层坐标 start
		int xlv1s = 660;
		int ylv1s = 300;
		// end
		int xlv1e = 660;
		int ylv1e = 300;
		// lv2层坐标
		int xlv2s;
		int ylv2s;
		// end
		int xlv2e;
		int ylv2e;

		// 半径 lv1&lv2
		int rlv1 = 200;
		int rlv2 = 80;
		// 中心点
		/**
		 * $(window).width(); 1366. $(window).height(); 600
		 * */
		// xlv1e = xlv1s + rlv1
		RelationPathBean relationPathBean = new RelationPathBean(printedUid,printedUid, 660 + "", 300 + "", 660 + "", 300 + "", "曹融", "10","0");
		relationlists.add(relationPathBean);
		this.insertRelationPathToDb(relationPathBean);
//		relationPathDaoImpl.insertRelationPathBean(relationPathBean);
		// lv1 lv2 的个数 
		int lengthlv1 = lv1Users.size();
		int lengthlv2;
		// 循环变量
		int ilv1 = 0;
		int ilv2 = 0;
		// 开始遍历1层
		for (User u1 : lv1Users) {
			// 防止待检测的人
			if (u1 != null) {
				// 检测db里是否有这个User对象，有的话直接从db中获取
				if (userDaoImpl.queryCountByUid(u1.getId()) != 0) {
					// db中存在，从db获取啥
					// UserBean userlv1 =
					// userService.querySingleUserByUid(u1.getId());
				} else {
					// db中不存在，将找到的人存进db
					this.insertUserToDb(u1);
				}
				// 计算坐标
				xlv1s = 660;
				ylv1s = 300;
				xlv1e = (int) (xlv1s + rlv1* Math.cos(ilv1 * (360 / lengthlv1) * (Math.PI / 180)));
				ylv1e = (int) (ylv1s + rlv1* Math.sin(ilv1 * (360 / lengthlv1) * (Math.PI / 180)));
				ilv1++;

				// 创建并插入path队列
				RelationPathBean relatlv1 = new RelationPathBean(printedUid,u1.getId(), xlv1s + "", ylv1s + "", xlv1e + "", ylv1e
								+ "", u1.getName(),lengthlv1 + "" , "1");
				relationlists.add(relatlv1);
				//   将RelationPathBean插入db 使下次不必再调api直接调db
				this.insertRelationPathToDb(relatlv1);
				// 开始遍历2层
				try {
					userWapper = friendships.getFriendsBilateral(u1.getId(), 0,new Paging(1, getRelationCount));
				} catch (WeiboException e) {
					e.printStackTrace();
				}
				List<User> lv2Users = userWapper.getUsers();
				// 2层的大小
				lengthlv2 = lv2Users.size();
				for (User u2 : lv2Users) {
					// 防止 注销
					if (u2 != null) {
						// 检测db里是否有这个User对象，有的话直接从db中获取
						if (userDaoImpl.queryCountByUid(u2.getId()) != 0) {
							// db中存在，从db获取
							UserBean userlv2 = userDaoImpl
									.querySingleUserByUid(u2.getId());
						} else {
							// db中不存在，将找到的人存进db
							this.insertUserToDb(u2);
						}
						// 第一层的头是第二层的末尾 初始化
						xlv2s = xlv1e;
						ylv2s = ylv1e;
						xlv2e = (int) (xlv2s + rlv2* Math.cos(ilv2 * (360 / lengthlv2)* (Math.PI / 180)));
						ylv2e = (int) (ylv2s + rlv2* Math.sin(ilv2 * (360 / lengthlv2)* (Math.PI / 180)));
						ilv2++;
						// 先检测list里是否已经有uid相同的人
						for (RelationPathBean retmp : relationlists) {
							if (retmp.getUid().equals(u2.getId())) {
								// 修改他们end 让他们指向同一个end点,再插入
								xlv2e = Integer.parseInt(retmp.getXend());
								ylv2e = Integer.parseInt(retmp.getYend());
								break;
							}
						}
						// create and insert
						RelationPathBean relatlv2 = new RelationPathBean(printedUid, u2.getId(), xlv2s + "", ylv2s + "",
								xlv2e + "", ylv2e + "", u2.getName(),lengthlv2 + "", "2");
						relationlists.add(relatlv2);
						this.insertRelationPathToDb(relatlv2);
					}
				}
			}
		}
	}

	
	public void insertRelationPathToDb(RelationPathBean r){
		//生成唯一id
		r.setId(Identities.create32LenUUID());
		if (relationPathDaoImpl.queryRelationPathBeanByBean(r) == 0) {
			relationPathDaoImpl.insertRelationPathBean(r);
		}
	}
	
	/**
	 * 将User转换并插入db
	 * */
	public void insertUserToDb(User u) {
		UserBean userBean = new UserBean();

		userBean.setUid(u.getId());
		userBean.setScreenName(u.getScreenName());
		userBean.setName(u.getName());
		userBean.setProvince(u.getProvince() + "");
		userBean.setCity(u.getCity() + "");

		userBean.setLocation(u.getLocation());
		userBean.setDescription(u.getDescription());
		userBean.setUrl(u.getUrl());
		userBean.setProfileImageUrl(u.getProfileImageUrl());
		userBean.setUserDomain(u.getUserDomain());

		userBean.setGender(u.getGender());
		userBean.setFollowersCount(u.getFollowersCount() + "");
		userBean.setFriendsCount(u.getFriendsCount() + "");
		userBean.setStatusesCount(u.getStatusesCount() + "");
		userBean.setFavouritesCount(u.getFavouritesCount() + "");

		userBean.setCreatedAt(u.getCreatedAt());
		String flag = "1";
		if (!u.isFollowing()) {
			flag = "0";
		}
		userBean.setFollowing(flag);
		flag = "1";
		if (!u.isVerified()) {
			flag = "0";
		}
		userBean.setVerified(flag);
		userBean.setVerifiedType(u.getverifiedType() + "");
		flag = "1";
		if (!u.isAllowAllActMsg()) {
			flag = "0";
		}
		userBean.setAllowAllActMsg(flag);

		flag = "1";
		if (!u.isallowAllComment()) {
			flag = "0";
		}
		userBean.setAllowAllComment(flag);
		flag = "1";
		if (!u.isFollowMe()) {
			flag = "0";
		}
		userBean.setFollowMe(flag);
		userBean.setAvatarLarge(u.getAvatarLarge());
		userBean.setBiFollowersCount(u.getBiFollowersCount() + "");
		userBean.setRemark(u.getRemark());

		userBean.setLang(u.getLang());
		userBean.setVerifiedReason(u.getVerifiedReason());
		userBean.setWeihao(u.getWeihao());

		userDaoImpl.insertUser(userBean);
	}
}
