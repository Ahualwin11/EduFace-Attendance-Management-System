package com.one.dao;

import com.one.bean.ClassBean;
import com.one.bean.StudentBean;
import com.one.bean.Attendence;
import com.one.util.DbUtil;

import java.sql.Connection;
import java.util.ArrayList;

public class AttendenceDao {
    private ArrayList<Attendence> arrayList = new ArrayList<Attendence>();

    public ArrayList<Attendence> selectSomeStudentByInfo(String sql) {
        arrayList.clear();
        Connection con = new DbUtil().getCon();
        StudentBean studentBean = null;
        ClassBean classBean = null;
//        RoomBean roomBean = null;
//        try {
//            this.preparedStatement = this.con.prepareStatement(sql);
//            ResultSet executeQuery = this.preparedStatement.executeQuery();
//            while(executeQuery.next()) {
//                classBean = new ClassBean(executeQuery.getInt(7),executeQuery.getString(8),executeQuery.getString(9),executeQuery.getDate(10));
//                roomBean = new RoomBean(executeQuery.getInt(11),executeQuery.getString(12),executeQuery.getInt(13),executeQuery.getInt(14),executeQuery.getInt(15),executeQuery.getString(16),executeQuery.getString(17),executeQuery.getInt(18),executeQuery.getString(19),executeQuery.getInt(20),executeQuery.getString(21),executeQuery.getDate(22));
//                studentBean  = new StudentBean(executeQuery.getInt(1),executeQuery.getString(2),executeQuery.getInt(3),executeQuery.getString(4),executeQuery.getString(5),executeQuery.getDate(6),roomBean,classBean);
//                arrayList.add(studentBean);
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }finally {
//            this.closeAll();
//        }
        return arrayList;
    }

}
