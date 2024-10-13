package com.one.domain;


import com.one.bean.ClassBean;
import com.one.bean.StudentBean;
import com.one.dao.BaseDao;
import com.one.dao.StudentDao;
import com.one.util.DateUtil;
import com.one.util.DbUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** 
* @author 作者 Your-Name: 
* @version 创建时间：2021年5月24日 下午3:00:36 
* 类说明 
*/
public class StudentDaoImpl extends BaseDao implements StudentDao {

	private ArrayList<StudentBean> arrayList = new ArrayList<StudentBean>();
	
	//根据班级id查找学生，这个方法其实没什么用，食之无味，弃之可惜，业务中没有这样的要求，并且如果有的话，这个代码还需要继续修改
	//	@Override
	//	public ArrayList<StudentBean> selectStudentByClassId(ClassBean classBean) {
	//		ArrayList<StudentBean> arrayList = new ArrayList<>();
	//		StudentBean studentBean;
	//		ClassBean claBean;
	//		RoomBean roomBean;
	//		this.con = DbUtil.getConnection();
	//		String sql = "SELECT t_student.pk_id,t_student.f_name,t_student.f_sex,t_student.f_photo,t_student.f_phone,t_student.f_time,t_class.pk_id,t_class.f_name,t_class.f_teacher,t_class.f_time,t_room.pk_id,t_room.f_address,t_room.f_maxNum,t_room.f_stucount,t_room.f_price,t_room.f_master,t_room.f_state,t_room.f_type,t_room.f_sextype,t_room.f_pay,t_room.f_time FROM t_class LEFT JOIN t_student ON t_student.fk_classid = ? AND t_student.fk_classid = t_class.pk_id JOIN t_room ON t_student.fk_roomid = t_room.pk_id";
	//		try {
	//			this.preparedStatement = this.con.prepareStatement(sql);
	//			this.preparedStatement.setInt(1, classBean.getPk_id());
	//			ResultSet executeQuery = this.preparedStatement.executeQuery();
	//			while(executeQuery.next()) {
	//				claBean = new ClassBean(executeQuery.getInt(7),executeQuery.getString(8),executeQuery.getString(9),executeQuery.getTime(10));
	//				roomBean = new RoomBean(executeQuery.getInt(11), executeQuery.getString(12), executeQuery.getInt(13), executeQuery.getInt(14), executeQuery.getInt(15), executeQuery.getString(16), executeQuery.getInt(17), executeQuery.getString(18), executeQuery.getInt(19), executeQuery.getString(20), executeQuery.getDate(21));
	//				studentBean = new StudentBean(executeQuery.getInt(1),executeQuery.getString(2),executeQuery.getInt(3),executeQuery.getString(4),executeQuery.getString(5),executeQuery.getDate(6),roomBean,claBean);
	//				arrayList.add(studentBean);
	//			}
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}finally {
	//			this.closeAll();
	//		}
	//		return arrayList;
	//	}

	
	//判断此班级内是否有学生
	@Override
	public boolean selectStudentByClassId(int classID) {
		boolean flag = false;
		String sql = "select t_student.pk_id FROM t_student LEFT JOIN t_class ON t_student.fk_classid = t_class.pk_id AND t_class.pk_id = ?";
		this.con = new DbUtil().getCon();
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			this.preparedStatement.setInt(1, classID);
			ResultSet executeQuery = this.preparedStatement.executeQuery();
			if(executeQuery.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return flag;
	}

	@Override
	public ArrayList<StudentBean> selectAllStudent() {
		arrayList.clear();
		String sql = "select t_student.pk_id,t_student.f_name,t_student.f_sex,t_student.f_photo,t_student.f_phone,t_student.f_time,t_class.pk_id,t_class.f_name,t_class.f_teacher,t_class.f_time,t_room.pk_id,t_room.f_address,t_room.f_maxnum,t_room.f_stucount,t_room.f_price,t_room.f_master,t_room.f_phone,t_room.f_state,t_room.f_type,t_room.f_sextype,t_room.f_pay,t_room.f_time FROM t_student LEFT JOIN t_class ON t_student.fk_classid = t_class.pk_id JOIN t_room ON t_student.fk_roomid = t_room.pk_id ";
		this.con = new DbUtil().getCon();
		StudentBean studentBean = null;
		ClassBean classBean = null;
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			ResultSet executeQuery = this.preparedStatement.executeQuery();
			while(executeQuery.next()) {
				classBean = new ClassBean(executeQuery.getInt(7),executeQuery.getString(8),executeQuery.getString(9),executeQuery.getDate(10));
				studentBean  = new StudentBean(executeQuery.getInt(1),executeQuery.getString(2),executeQuery.getInt(3),executeQuery.getString(5),executeQuery.getDate(6),classBean);
				arrayList.add(studentBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return arrayList;
	}

	@Override
	public ArrayList<StudentBean> selectSomeStudentByInfo(String sql) {
		arrayList.clear();
		this.con = new DbUtil().getCon();
		StudentBean studentBean = null;
		ClassBean classBean = null;
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			ResultSet executeQuery = this.preparedStatement.executeQuery();
			while(executeQuery.next()) {
				classBean = new ClassBean(executeQuery.getInt(7),executeQuery.getString(8),executeQuery.getString(9),executeQuery.getDate(10));
				studentBean  = new StudentBean(executeQuery.getInt(1),executeQuery.getString(2),executeQuery.getInt(3),executeQuery.getString(5),executeQuery.getDate(6),classBean);
				arrayList.add(studentBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return arrayList;
	}

	@Override
	public boolean addStudentInfo(StudentBean stu) {
		boolean flag = false;
		this.con = new DbUtil().getCon();
		String sql = "insert into t_student values(NULL,?,?,?,?,?,?,?)";
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			this.preparedStatement.setString(1,stu.getF_name());
			this.preparedStatement.setInt(2, stu.getF_sex());
			this.preparedStatement.setString(3, stu.getF_photo());
			this.preparedStatement.setString(4, stu.getF_phone());
			this.preparedStatement.setString(5, DateUtil.DateToSqlDate(stu.getF_time()));
			this.preparedStatement.setInt(6, stu.getClassBean().getPk_id());

			if(this.preparedStatement.executeUpdate() > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return flag;
	}



	@Override
	public boolean deleteStudentInfo(StudentBean stu) {
		boolean flag = false;
		String sql = "delete from t_student where pk_id = ?";
		this.con = new DbUtil().getCon();
		try {
			this.preparedStatement = this.con.prepareStatement(sql);
			this.preparedStatement.setInt(1, stu.getPk_id());
			if(this.preparedStatement.executeUpdate() > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return flag;
	}
}
