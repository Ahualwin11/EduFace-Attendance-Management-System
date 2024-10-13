package com.one.dao;


import com.one.bean.Student;
import com.one.util.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;

public class StudentDao_1 extends BaseDao{

    //学生登录
    public Student login(Student student){
        String sql = "select * from student_1 where name=? and password=?";
        Student studentRst = null;
        try {
            //把sql语句传给数据库对象
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, student.getName());
            prst.setString(2, student.getPassword());
            ResultSet rs = prst.executeQuery();
            if(rs.next()){
                studentRst = new Student();
                studentRst.setId(rs.getInt("id"));
                studentRst.setName(rs.getString("name"));
                studentRst.setPassword(rs.getString("password"));
                studentRst.setCreateDate(rs.getString("createDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentRst;
    }



    //注册信息
    public boolean registInfo(String name,String password,String sex,String stuid,String classid){
        boolean flag = false;//假设用户已存在
        String sql = "select * from student_1 where name=?";
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

        String sqlString = "INSERT INTO student_1(NAME,PASSWORD,sex,stuid,classid) VALUES(?,?,?,?,?)";
        try {
            prst =  con.prepareStatement(sqlString);
            prst.setString(1,name);
            prst.setString(2,password);
            prst.setString(3,sex);
            prst.setString(4,stuid);
            prst.setString(5,classid);
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
    public String editPassword(Student student,String newPassword){
        String sql = "select * from student_1 where name=? and password=?";
        PreparedStatement prst = null;
        int id = 0;
        try {
            con.prepareStatement(sql);
            prst.setString(1,student.getName());
            prst.setString(2,student.getPassword());
            ResultSet rs = prst.executeQuery();
            if(!rs.next()){
                return "旧密码错误";
            }
            id = rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String retString = "修改失败";
        String sqlString = "update student_1 set password = ? where name = ? and password = ?";

        try {
            prst =  con.prepareStatement(sqlString);
            prst.setString(1,newPassword);
            prst.setString(2,student.getName());
            prst.setString(3,student.getPassword());
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

    //得到所有学生姓名
    //获取数据库中的数据并以list返回
    public  ArrayList<String> getAllNames() throws ClassNotFoundException, SQLException {
        //1,注册驱动信息
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2,获取连接对象
        String url = "jdbc:mysql://localhost:3306/itcast";
        Connection conn = DriverManager.getConnection(url, "root", "123456");
        String sql = "select name from student_1";
        //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
        PreparedStatement parameter = conn.prepareStatement(sql);
        //4,执行sql
        ResultSet result = parameter.executeQuery();
        //返回的数据List
        ArrayList<String> list = new ArrayList<>();
        while (result.next()) {
            String name = result.getString("name");
            list.add(name);
        }
        result.close();
        parameter.close();
        conn.close();

        return list;

    }


    public  ArrayList<Student> getStudentByInfo(String sql) throws ClassNotFoundException, SQLException {

        //1,注册驱动信息
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2,获取连接对象
        String url = "jdbc:mysql://localhost:3306/itcast";
        Connection conn = DriverManager.getConnection(url, "root", "123456");
        //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
        PreparedStatement parameter = conn.prepareStatement(sql);
        //4,执行sql
        ResultSet rs = parameter.executeQuery();
        //返回的数据List
        ArrayList<Student> list = new ArrayList<>();
        list.clear();
        Student student = null;
        while (rs.next()) {
            student = new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5));
            list.add(student);
        }
        rs.close();
        parameter.close();
        conn.close();

        return list;

    }

//    public ArrayList<Student> querySomeStudentByInfo(Student stu) {
//        StringBuffer sql = new StringBuffer("select * from student_1");
//        String sqls[] = new String[4];
//        int i = 0;
//        if(!StringUtil.isEmpty(stu.getName())) {
//            sqls[i++] = "t_student.f_name like '%" + stu.getName()+"%' ";
//        }
//        if(stu.getClassBean().getPk_id()!=-1) {
//            sqls[i++] = "t_class.pk_id = " + stu.getClassBean().getPk_id();
//        }
//        if(i == 0) {
//            return studentDao.selectAllStudent();
//        }
//        sql = StringUtil.splicingStrs(i, sql, sqls);
//        return studentDao.selectSomeStudentByInfo(sql.toString());
//    }
}
