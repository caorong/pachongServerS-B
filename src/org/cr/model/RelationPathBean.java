package org.cr.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Description 绘制用户关系图的model
 * @author caorong
 * @date 2013-1-5
 * 
 */
public class RelationPathBean implements Serializable {

	private static final long serialVersionUID = -6305918582607920900L;

	private String id;
	// 用于给数据库表示
	private String Centeruid;
	private String uid;
	private String xstart;
	private String ystart;
	private String xend;
	private String yend;
	private String name;
	// 圆的半径()
	private String noder;
	// 深度，根据深度决定颜色
	private String deep;

	public RelationPathBean() {
	}

	/**
	 * @param CenterUid
	 * @param uid
	 * @param xs
	 * @param ys
	 * @param xe
	 * @param ye
	 * @param name
	 * @param noder
	 * @param deep
	 * */
	public RelationPathBean(String centeruid, String uid, String x, String y,
			String ex, String ey, String name, String noder, String deep) {
		this.Centeruid = centeruid;
		this.uid = uid;
		this.xstart = x;
		this.ystart = y;
		this.xend = ex;
		this.yend = ey;
		this.name = name;
		this.noder = noder;
		this.deep = deep;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoder() {
		return noder;
	}

	public void setNoder(String noder) {
		this.noder = noder;
	}

	public String getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
	}

	public String getCenteruid() {
		return Centeruid;
	}

	public void setCenteruid(String centeruid) {
		Centeruid = centeruid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getXstart() {
		return xstart;
	}

	public void setXstart(String xstart) {
		this.xstart = xstart;
	}

	public String getYstart() {
		return ystart;
	}

	public void setYstart(String ystart) {
		this.ystart = ystart;
	}

	public String getXend() {
		return xend;
	}

	public void setXend(String xend) {
		this.xend = xend;
	}

	public String getYend() {
		return yend;
	}

	public void setYend(String yend) {
		this.yend = yend;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
