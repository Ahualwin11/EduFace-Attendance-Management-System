package com.one.domain;


import com.one.bean.ClassBean;
import com.one.dao.BaseDao;
import com.one.dao.ClassDao;
import com.one.util.DateUtil;
import com.one.util.DbUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** 
* @author 作者 Your-Name: 
* @version 创建时间：2021年5月24日 上午11:55:26 
* 类说明 
*/
public class ClassDaoImpl extends BaseDao implements ClassDao {

	private ArrayList<ClassBean> arrayList = new ArrayList<>();
	
	@Override
	public int classNameIsExist(String className) {
		int id = 0;
		this.con = new DbUtil().getCon();
		String sql = "select pk_id from t_class where f_name = ?";
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			this.preparedStatement.setString(1, className);
			ResultSet executeQuery = this.preparedStatement.executeQuery();
			if(executeQuery.next()) {
				id = executeQuery.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return id;
	}

	@Override
	public boolean insertClass(ClassBean classBean) {
		boolean flag = false;
		this.con = new DbUtil().getCon();
		String sql = "INSERT INTO t_class VALUES (NULL,?,?,?)";
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			this.preparedStatement.setString(1, classBean.getF_name());
			this.preparedStatement.setString(2, classBean.getF_teacher());
			this.preparedStatement.setString(3, DateUtil.DateToSqlDate(classBean.getF_time()));
			if(this.preparedStatement.executeUpdate() > 0) {
				flag = true;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return flag;
	}

	@Override
	public boolean editClass(ClassBean classBean) {
		boolean flag = false;
		this.con = new DbUtil().getCon();
		String sql = "update t_class set f_name = ?,f_teacher = ?,f_time = ? where pk_id = ?";
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			this.preparedStatement.setString(1, classBean.getF_name());
			this.preparedStatement.setString(2, classBean.getF_teacher());
			this.preparedStatement.setString(3, DateUtil.DateToSqlDate(classBean.getF_time()));
			this.preparedStatement.setInt(4, classBean.getPk_id());
			if(this.preparedStatement.executeUpdate() > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return flag;
	}

	@Override
	public ArrayList<ClassBean> selectAllClass() {
		arrayList.clear();
		ClassBean tempClass;
		this.con = new DbUtil().getCon();
		String sql = "select pk_id,f_name,f_teacher,f_time from t_class";
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			ResultSet executeQuery = this.preparedStatement.executeQuery();
			while(executeQuery.next()) {
				tempClass = new ClassBean(executeQuery.getInt(1), executeQuery.getString(2), executeQuery.getString(3),executeQuery.getDate(4));
				arrayList.add(tempClass);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return arrayList;
	}

	@Override
	public boolean deleteClass(ClassBean classBean) {
		boolean flag = false;
		this.con = new DbUtil().getCon();
		String sql = "delete from t_class where pk_id = ?";
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			this.preparedStatement.setInt(1, classBean.getPk_id());
			if(this.preparedStatement.executeUpdate()>0) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return flag;
	}

	@Override
	public ArrayList<ClassBean> selectSomeClassByInfo(String sql) {
		arrayList.clear();
		ClassBean classBean = null;
		this.con = new DbUtil().getCon();
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			ResultSet executeQuery = this.preparedStatement.executeQuery();
			while(executeQuery.next()) {
				classBean = new ClassBean(executeQuery.getInt(1),executeQuery.getString(2),executeQuery.getString(3),executeQuery.getDate(4));
				arrayList.add(classBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return arrayList;
	}

}
