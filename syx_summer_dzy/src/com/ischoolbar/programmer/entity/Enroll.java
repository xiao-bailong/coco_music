package com.ischoolbar.programmer.entity;

import com.ischoolbar.programmer.annotation.Table;

/**
 * 图书分类实体
 * 
 *
 */
@Table(tableName = "enroll")
public class Enroll extends BaseEntity {
	private String name;
	private String univname;
	public String getMajorname() {
		return majorname;
	}
	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	private String idnum;
	public void setMajorname(String majorname) {
		this.majorname = majorname;
	}

	private String majorname;
	private int userid;
	public String getName() {
		return name;
	}

	public String getUnivname() {
		return univname;
	}

	public void setUnivname(String univname) {
		this.univname = univname;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public void setName(String name) {
		this.name = name;
	}
}
