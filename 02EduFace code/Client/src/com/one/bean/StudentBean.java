package com.one.bean;

import java.util.Date;

/** 
* @author 作者 Your-Name: 
* @version 创建时间：2021年5月17日 下午2:55:22 
* 类说明 
*/
public class StudentBean {

	private int pk_id;
	private String f_name;
	private int f_sex;
	private String f_photo;
	private String f_phone;
	private Date f_time;
	private ClassBean classBean;

	public StudentBean() {

	}


	//stuName, gender, phone, date, classBean
	public StudentBean( String f_name, int f_sex, String f_phone, Date f_time,
					   ClassBean classBean) {
		super();
		this.f_name = f_name;
		this.f_sex = f_sex;
		this.f_phone = f_phone;
		this.f_time = f_time;
		this.classBean = classBean;
	}


	public StudentBean(int pk_id, String f_name, int f_sex, String f_phone, Date f_time,
					   ClassBean classBean) {
		super();
		this.pk_id = pk_id;
		this.f_name = f_name;
		this.f_sex = f_sex;
		this.f_phone = f_phone;
		this.f_time = f_time;
		this.classBean = classBean;
	}


	public int getPk_id() {
		return pk_id;
	}


	public void setPk_id(int pk_id) {
		this.pk_id = pk_id;
	}


	public String getF_name() {
		return f_name;
	}


	public void setF_name(String f_name) {
		this.f_name = f_name;
	}


	public int getF_sex() {
		return f_sex;
	}


	public void setF_sex(int f_sex) {
		this.f_sex = f_sex;
	}


	public String getF_photo() {
		return f_photo;
	}


	public void setF_photo(String f_photo) {
		this.f_photo = f_photo;
	}


	public String getF_phone() {
		return f_phone;
	}


	public void setF_phone(String f_phone) {
		this.f_phone = f_phone;
	}


	public Date getF_time() {
		return f_time;
	}


	public void setF_time(Date f_time) {
		this.f_time = f_time;
	}



	public ClassBean getClassBean() {
		return classBean;
	}


	public void setClassBean(ClassBean classBean) {
		this.classBean = classBean;
	}


}
