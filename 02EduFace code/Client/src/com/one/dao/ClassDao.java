package com.one.dao;


import com.one.bean.ClassBean;

import java.util.ArrayList;

/** 
* @author 作者 Your-Name: 
* @version 创建时间：2021年5月24日 上午11:52:52 
* 类说明 
*/
public interface ClassDao {
	
	//判断班级名是否存在
	public abstract int classNameIsExist(String className);
	
	//新增班级
	public abstract boolean insertClass(ClassBean classBean);
	
	//修改班级
	public abstract boolean editClass(ClassBean classBean);
	
	//查找全部班级信息
	public abstract ArrayList<ClassBean> selectAllClass();
	
	//删除班级
	public abstract boolean deleteClass(ClassBean classBean);
	
	//查询符合某种条件的班级
	public abstract ArrayList<ClassBean> selectSomeClassByInfo(String sql);
}
