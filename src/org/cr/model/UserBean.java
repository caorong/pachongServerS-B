package org.cr.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A data class representing Basic user information element
 */
public class UserBean implements Serializable {

	private static final long serialVersionUID = -2204517401021442385L;
	private String uid;                      //用户UID
	private String screenName;            //微博昵称
	private String name;                  //友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)
//	private int province;                 //省份编码（参考省份编码表）
//	private int city;                     //城市编码（参考城市编码表）
	private String province;			  //up
	private String city;				  //up up
	private String location;              //地址
	private String description;           //个人描述
	private String url;                   //用户博客地址
	private String profileImageUrl;       //自定义图像
	private String userDomain;            //用户个性化URL
	
	private String gender;                //性别,m--男，f--女,n--未知
	private String followersCount;           //粉丝数
	private String friendsCount;             //关注数
	private String statusesCount;            //微博数
	private String favouritesCount;          //收藏数
	private Date createdAt;               //创建时间
//	private boolean following;            //保留字段,是否已关注(此特性暂不支持)
//	private boolean verified;             //加V标示，是否微博认证用户
	private String following;			//up
	private String verified;			//up up
	private String verifiedType;             //认证类型
//	private boolean allowAllActMsg;       //是否允许所有人给我发私信
//	private boolean allowAllComment;      //是否允许所有人对我的微博进行评论
//	private boolean followMe;             //此用户是否关注我
	private String allowAllActMsg;		//up
	
	private String allowAllComment;		//up up
	private String followMe;			//up up up
	private String avatarLarge;           //大头像地址
	private String biFollowersCount;         //互粉数
	private String remark;                //备注信息，在查询用户关系时提供此字段。
	private String lang;                  //用户语言版本
	private String verifiedReason;		  //认证原因
	private String weihao;				  //微號
	private String type;				//该user的type


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(String followersCount) {
		this.followersCount = followersCount;
	}

	public String getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(String friendsCount) {
		this.friendsCount = friendsCount;
	}

	public String getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(String statusesCount) {
		this.statusesCount = statusesCount;
	}

	public String getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(String favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getFollowing() {
		return following;
	}

	public void setFollowing(String following) {
		this.following = following;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public String getVerifiedType() {
		return verifiedType;
	}

	public void setVerifiedType(String verifiedType) {
		this.verifiedType = verifiedType;
	}

	public String getAllowAllActMsg() {
		return allowAllActMsg;
	}

	public void setAllowAllActMsg(String allowAllActMsg) {
		this.allowAllActMsg = allowAllActMsg;
	}

	public String getAllowAllComment() {
		return allowAllComment;
	}

	public void setAllowAllComment(String allowAllComment) {
		this.allowAllComment = allowAllComment;
	}

	public String getFollowMe() {
		return followMe;
	}

	public void setFollowMe(String followMe) {
		this.followMe = followMe;
	}

	public String getAvatarLarge() {
		return avatarLarge;
	}

	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
	}

	public String getBiFollowersCount() {
		return biFollowersCount;
	}

	public void setBiFollowersCount(String biFollowersCount) {
		this.biFollowersCount = biFollowersCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getVerifiedReason() {
		return verifiedReason;
	}

	public void setVerifiedReason(String verifiedReason) {
		this.verifiedReason = verifiedReason;
	}

	public String getWeihao() {
		return weihao;
	}

	public void setWeihao(String weihao) {
		this.weihao = weihao;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
