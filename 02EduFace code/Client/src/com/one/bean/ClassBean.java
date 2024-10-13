package com.one.bean;

import java.util.Date;

/** 
* @author 作者 Your-Name: 
* @version 创建时间：2021年5月17日 下午2:55:29 
* 类说明 
*/
public class ClassBean {
	
	private int pk_id;
	private String f_name;
	private String f_teacher;
	private Date f_time;

	public ClassBean() {
		
	}
	
	
	
	public ClassBean(int pk_id, String f_name) {
		super();
		this.pk_id = pk_id;
		this.f_name = f_name;
	}



	public ClassBean(String f_name, String f_teacher, Date f_time) {
		super();
		this.f_name = f_name;
		this.f_teacher = f_teacher;
		this.f_time = f_time;
	}

	public ClassBean(int pk_id, String f_name, String f_teacher, Date f_time) {
		super();
		this.pk_id = pk_id;
		this.f_name = f_name;
		this.f_teacher = f_teacher;
		this.f_time = f_time;
	}


	public int getPk_id() {
		return pk_id;
	}


	public void setPk_id(int pk_id) {
		this.pk_id = pk_id;
	}


	public String getF_teacher() {
		return f_teacher;
	}


	public void setF_teacher(String f_teacher) {
		this.f_teacher = f_teacher;
	}


	public Date getF_time() {
		return f_time;
	}


	public void setF_time(Date f_time) {
		this.f_time = f_time;
	}


	public String getF_name() {
		return f_name;
	}


	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	@Override
	public String toString() {
		return f_name;
	}
}
