package com.one.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private String dburl = "jdbc:mysql://localhost:3306/itcast";//数据库连接地址
    private String dbusername = "root";//用户名
    private String dbpassword = "123456";//密码
    private String jdbcName = "com.mysql.cj.jdbc.Driver";//驱动名称

    //获取数据库连接
    public Connection getCon(){
        try {
            Class.forName(jdbcName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection(dburl,dbusername,dbpassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    //关闭数据库连接
    public void closeCon(Connection con) throws Exception{
        if(con != null){
            con.close();
        }
    }

    public static void main(String[] args) {
        DbUtil dbUtil = new DbUtil();
        try {
            dbUtil.getCon();
            System.out.println("数据库连接成功！");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }

    }

}
