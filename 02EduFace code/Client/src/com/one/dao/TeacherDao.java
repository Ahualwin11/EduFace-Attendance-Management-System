package com.one.dao;


import com.one.bean.Teacher;
import com.one.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDao extends BaseDao{

    //教师登录
    public Teacher login(Teacher teacher){
        String sql = "select * from teacher where name=? and password=?";
        Teacher teacherRst = null;
        try {
            //把sql语句传给数据库对象
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1,teacher.getName());
            prst.setString(2,teacher.getPassword());
            ResultSet rs = prst.executeQuery();
            if(rs.next()){
                teacherRst = new Teacher();
                teacherRst.setId(rs.getInt("id"));
                teacherRst.setName(rs.getString("name"));
                teacherRst.setPassword(rs.getString("password"));
                teacherRst.setCreateDate(rs.getString("createDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherRst;
    }



    //注册信息
    public boolean registInfo(String name,String password,String sex,String phoneNumber){
        boolean flag = false;//假设用户已存在
        String sql = "select * from teacher where name=?";
        PreparedStatement prst = null;
        try {
            prst = con.prepareStatement(sql);
            prst.setString(1,name);
            ResultSet rs = prst.executeQuery();
            if(rs.next()){
                return flag;//用户已存在
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlString = "INSERT INTO teacher(NAME,PASSWORD,sex,phoneNumber) VALUES(?,?,?,?)";
        try {
            prst =  con.prepareStatement(sqlString);
            prst.setString(1,name);
            prst.setString(2,password);
            prst.setString(3,sex);
            prst.setString(4,phoneNumber);
            prst.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /*//获取photoFile中的随机一张图片写入数据库
        File[] imgs = photoFile.listFiles();
        System.out.println("----------photofile--------"+photoFile.getAbsolutePath());
        for (File img : imgs) {
            if(img.getName().endsWith(".jpg")){
                //复制到数据库安全路径下
                System.out.println("----------old------"+img.getAbsolutePath());
                    FileUtil.fileCopy(img.getAbsolutePath(), img.getName());//文件复制到数据库安全路径下
                    //写入数据库
                    new TeacherDao().insertImage(MainInterface.UName,img.getName());
                }
            }
        }*/





    //修改信息--密码
    public String editPassword(Teacher teacher,String newPassword){
        String sql = "select * from teacher where name=? and password=?";
        PreparedStatement prst = null;
        int id = 0;
        try {
            con.prepareStatement(sql);
            prst.setString(1,teacher.getName());
            prst.setString(2,teacher.getPassword());
            ResultSet rs = prst.executeQuery();
            if(!rs.next()){
                return "旧密码错误";
            }
            id = rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String retString = "修改失败";
        String sqlString = "update teacher set password = ? where name = ? and password = ?";

        try {
            prst =  con.prepareStatement(sqlString);
            prst.setString(1,newPassword);
            prst.setString(2,teacher.getName());
            prst.setString(3,teacher.getPassword());
            int rst = prst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retString;
    }

    public int getCount(String sql){
        int count = 0;
        Connection con = JDBCUtils.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
               count =  rs.getInt("count(*)");
            }
            JDBCUtils.release(con,pst,rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
