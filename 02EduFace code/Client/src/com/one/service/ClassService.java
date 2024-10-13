package com.one.service;

import com.one.bean.ClassBean;

import java.util.ArrayList;
import java.util.Date;

/** 
* @author 作者 Your-Name: 
* @version 创建时间：2021年5月24日 上午11:48:30 
* 类说明 
*/
public interface ClassService {
	
	//新增班级
	public abstract String addClass(ClassBean classBean);
	
	//修改班级
	public abstract ClassBean editClass(ClassBean classBean);
	
	//查询所有班级
	public abstract ArrayList<ClassBean> queryAllClass();
	
	//删除指定班级
	public abstract boolean deleteClass(ClassBean classBean);
	
	//查询满足条件的班级
	public abstract ArrayList<ClassBean> querySomeClassByInfo(String classNumb,String className,String classTeacher,Date date);
}
