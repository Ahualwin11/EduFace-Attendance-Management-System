package com.one.dao;

import com.one.bean.Course_info;

import java.sql.*;
import java.util.ArrayList;

public class CourseDao {

    //查询所有课程
    //获取数据库中的数据并以list返回
    public static ArrayList<Course_info> getDbData() throws ClassNotFoundException, SQLException {
        //1,注册驱动信息
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2,获取连接对象
        String url = "jdbc:mysql://localhost:3306/itcast";
        Connection conn = DriverManager.getConnection(url, "root", "123456");
        String sql = "select cid,cname from course";
        //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
        PreparedStatement parameter = conn.prepareStatement(sql);
        //4,执行sql
        ResultSet result = parameter.executeQuery();
        //返回的数据List
        ArrayList<Course_info> list = new ArrayList<>();
        while (result.next()) {

            Course_info course_info = new Course_info();
            course_info.setCid(result.getInt("cid"));
            course_info.setCname(result.getString("cname"));
            list.add(course_info);
        }
        result.close();
        parameter.close();
        conn.close();

        return list;

    }
}
