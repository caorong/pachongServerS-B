package org.cr.service;

import org.apache.log4j.Logger;
import org.cr.dao.impl.UserDaoImpl;
import org.cr.model.UserBean;

import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;

import weibo4j.Friendships;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;

/**
 * @description 获得想要抓取的人的uid 并入DB
 * @author caorong
 * @date 2012-12-31
 */
public class ServiceGetAuthorUidThread implements Runnable {

	static Logger log = Logger.getLogger(ServiceGetAuthorUidThread.class.getName());
	
	private String access_token;
	
	public ServiceGetAuthorUidThread(String access_token) {
		this.access_token = access_token;
		
		Friendships fm = new Friendships();
		fm.client.setToken(this.access_token);
		
		try {
			UserWapper users = fm.getFriendsByIDAndCount("1057297283",200);
			
			for(User u : users.getUsers()){
//				System.out.println(u.getId());
//				System.out.println(u.getName());
				UserBean userBean = new UserBean();

				userBean.setUid(u.getId());
				userBean.setScreenName(u.getScreenName());
				userBean.setName(u.getName());
				userBean.setProvince(u.getProvince()+"");
				userBean.setCity(u.getCity()+"");
				
				userBean.setLocation(u.getLocation());
				userBean.setDescription(u.getDescription());
				userBean.setUrl(u.getUrl());
				userBean.setProfileImageUrl(u.getProfileImageUrl());
				userBean.setUserDomain(u.getUserDomain());
				
				userBean.setGender(u.getGender());
				userBean.setFollowersCount(u.getFollowersCount()+"");
				userBean.setFriendsCount(u.getFriendsCount()+"");
				userBean.setStatusesCount(u.getStatusesCount()+"");
				userBean.setFavouritesCount(u.getFavouritesCount()+"");
				
				userBean.setCreatedAt(u.getCreatedAt());
				String flag = "1";
				if(!u.isFollowing()){
					flag = "0";
				}
				userBean.setFollowing(flag);
				flag = "1";
				if(!u.isVerified()){
					flag = "0";
				}
				userBean.setVerified(flag);
				userBean.setVerifiedType(u.getverifiedType()+"");
				flag = "1";
				if(!u.isAllowAllActMsg()){
					flag = "0";
				}
				userBean.setAllowAllActMsg(flag);
				
				flag = "1";
				if(!u.isallowAllComment()){
					flag = "0";
				}
				userBean.setAllowAllComment(flag);
				flag = "1";
				if(!u.isFollowMe()){
					flag = "0";
				}
				userBean.setFollowMe(flag);
				userBean.setAvatarLarge(u.getAvatarLarge());
				userBean.setBiFollowersCount(u.getBiFollowersCount()+"");
				userBean.setRemark(u.getRemark());
				
				userBean.setLang(u.getLang());
				userBean.setVerifiedReason(u.getVerifiedReason());
				userBean.setWeihao(u.getWeihao());
//				userBean.setType(type)
				
				//将bean放进数据库
				UserDaoImpl userDaoImpl = new UserDaoImpl();
				// 查询db里是否已存在相同数据
				if (userDaoImpl.queryCountByUid(userBean.getUid()) == 0) {
					log.debug("insert data"+userBean.toString());
					userDaoImpl.insertUser(userBean);
				}
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		
	}

}
