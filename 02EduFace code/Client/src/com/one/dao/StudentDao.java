package com.one.dao;


import com.one.bean.StudentBean;

import java.util.ArrayList;

/**
* @author 作者 Your-Name: 
* @version 创建时间：2021年5月24日 下午2:58:17 
* 类说明 
*/
public interface StudentDao {

	//判断此班级内是否有学生
	public abstract boolean selectStudentByClassId(int classID);
	
	//查找全部学生
	public abstract ArrayList<StudentBean> selectAllStudent();
	
	//查找符合查询条件的学生
	public abstract ArrayList<StudentBean> selectSomeStudentByInfo(String sql);
	
	//新增学生
	public abstract boolean addStudentInfo(StudentBean stu);
	//删除学生
	public abstract boolean deleteStudentInfo(StudentBean stu);

}
