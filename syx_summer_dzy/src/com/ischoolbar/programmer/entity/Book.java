package com.ischoolbar.programmer.entity;

import com.ischoolbar.programmer.annotation.Column;

/**
 * 图书实体
 * 
 *
 */
public class Book extends BaseEntity {
	
	private String name;//图书名称

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="university_category",isForeignEntity=true)
	private UniversityCategory universityCategory;//高校分类
	private String province;
	private String address;
	private String rank;
	private int status;//图书状态//原代码注释了包括get,set方法

	private int number = 1;//图书数量//原代码注释了包括get,set方法

	private int freeNumber = 1;//可借图书数量//原代码注释了包括get,set方法
	
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	private String info;//图书介绍
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public UniversityCategory getUniversityCategory() {
		return universityCategory;
	}

	public void setUniversityCategory(UniversityCategory universityCategory) {
		this.universityCategory = universityCategory;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getFreeNumber() {
		return freeNumber;
	}

	public void setFreeNumber(int freeNumber) {
		this.freeNumber = freeNumber;
	}

	public String getInfo() {
		return info;
	}
	

	public void setInfo(String info) {
		this.info = info;
	}
	
}
