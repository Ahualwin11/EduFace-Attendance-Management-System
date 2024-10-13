package com.one.service;


import com.one.bean.StudentBean;

import java.util.ArrayList;

public interface StudentService {
	
	//查询所有学生
	public abstract ArrayList<StudentBean> queryAllStudent();
	
	//查询符合条件的学生
	public abstract ArrayList<StudentBean> querySomeStudentByInfo(StudentBean stu);
	
	//学生入住
	public abstract boolean addStudent(StudentBean stu);
	
	//学生换房
	public abstract boolean exchangeRoom(StudentBean stu,int oldId,int newId);

	//学生退房
	public abstract boolean deleteStudent(StudentBean stu);
}
