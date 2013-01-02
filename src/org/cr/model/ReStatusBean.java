package org.cr.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @description 转载model
 * @author caorong
 * @date 2013-1-2
 */
public class ReStatusBean implements Serializable {

	private static final long serialVersionUID = 8829370967128130161L;

	private String id;
	private String wid;
	private String uid;
	private String fatherwid;
	private String selfwid;
	private String selfuid;
	private String deepth;
	private String text;
	private String authorfansflag;

	public String getAuthorfansflag() {
		return authorfansflag;
	}

	public void setAuthorfansflag(String authorfansflag) {
		this.authorfansflag = authorfansflag;
	}

	public String getSelfuid() {
		return selfuid;
	}

	public void setSelfuid(String selfuid) {
		this.selfuid = selfuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFatherwid() {
		return fatherwid;
	}

	public void setFatherwid(String fatherwid) {
		this.fatherwid = fatherwid;
	}

	public String getSelfwid() {
		return selfwid;
	}

	public void setSelfwid(String selfwid) {
		this.selfwid = selfwid;
	}

	public String getDeepth() {
		return deepth;
	}

	public void setDeepth(String deepth) {
		this.deepth = deepth;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
