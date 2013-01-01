package org.cr.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StatusBean implements Serializable {

	private static final long serialVersionUID = 131704989422863755L;
	
//	private User user = null;                            //作者信息
	private String uid;									 //作者id up
	private Date createdAt;                              //status创建时间
	private String wid;                                  //status id                
	private String text;                                 //微博内容
//	private Source source;                               //微博来源
    private String url;             				     //来源连接  source
    private String relationShip;      					 //是否关注  source
    private String name;            				     //来源文案名称  source
//	private boolean favorited;                           //是否已收藏
//	private boolean truncated;						 	 //是否被缩短
    private String favorited;							 //up
    private String truncated;							 //up up
	private String thumbnailPic;                         //微博内容中的图片的缩略地址
	private String bmiddlePic;                           //中型图片
	private String originalPic;                          //原始图片
//	private Status retweetedStatus = null;               //转发的博文，内容为status，如果不是转发，则没有此字段
	private String geo;                                  //地理信息，保存经纬度，没有时不返回此字段
	private double latitude = -1;                        //纬度
	private double longitude = -1;                       //经度
//	private int repostsCount;                            //转发数
//	private int commentsCount;                           //评论数
//	private int attitudescount; 						 //表态数"赞"
	private String repostsCount;						 //up
	private String commentsCount;						 //up up
	private String attitudescount;						 //up up up
	private String repostsFlag; 						 //是否对转发进行过深度操作flag

	private String commentGoodCount;					//好评数
	private String commentBadCount; 					 //坏评数
	private String commentsFlag;						 //是否对评论态度做过操作 flag	
	
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFavorited() {
		return favorited;
	}

	public void setFavorited(String favorited) {
		this.favorited = favorited;
	}

	public String getTruncated() {
		return truncated;
	}

	public void setTruncated(String truncated) {
		this.truncated = truncated;
	}

	public String getThumbnailPic() {
		return thumbnailPic;
	}

	public void setThumbnailPic(String thumbnailPic) {
		this.thumbnailPic = thumbnailPic;
	}

	public String getBmiddlePic() {
		return bmiddlePic;
	}

	public void setBmiddlePic(String bmiddlePic) {
		this.bmiddlePic = bmiddlePic;
	}

	public String getOriginalPic() {
		return originalPic;
	}

	public void setOriginalPic(String originalPic) {
		this.originalPic = originalPic;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getRepostsCount() {
		return repostsCount;
	}

	public void setRepostsCount(String repostsCount) {
		this.repostsCount = repostsCount;
	}

	public String getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(String commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getAttitudescount() {
		return attitudescount;
	}

	public void setAttitudescount(String attitudescount) {
		this.attitudescount = attitudescount;
	}

	public String getRepostsFlag() {
		return repostsFlag;
	}

	public void setRepostsFlag(String repostsFlag) {
		this.repostsFlag = repostsFlag;
	}

	public String getCommentsFlag() {
		return commentsFlag;
	}

	public void setCommentsFlag(String commentsFlag) {
		this.commentsFlag = commentsFlag;
	}
 
	public String getCommentGoodCount() {
		return commentGoodCount;
	}

	public void setCommentGoodCount(String commentGoodCount) {
		this.commentGoodCount = commentGoodCount;
	}

	public String getCommentBadCount() {
		return commentBadCount;
	}

	public void setCommentBadCount(String commentBadCount) {
		this.commentBadCount = commentBadCount;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
