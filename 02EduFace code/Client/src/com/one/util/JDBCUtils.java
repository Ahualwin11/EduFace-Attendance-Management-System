package com.one.util;

import com.one.bean.Attendence;
import com.one.bean.Student;
import com.one.view.Attendence_Record;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class JDBCUtils {
    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/itcast";
            String user = "root";
            String password = "123456";
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    public static void release(Connection con, Statement stat){
        try {
            con.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void release(Connection con, Statement stat, ResultSet rs){
        try {
            con.close();
            stat.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Update(String sql){
        Connection con = JDBCUtils.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            int i = pst.executeUpdate();
            JDBCUtils.release(con,pst);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int getCount(String sql){
        int count = 0;
        Connection con = JDBCUtils.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                count ++;
            }
            JDBCUtils.release(con,pst,rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static Student queryOneAll(String name) throws SQLException {
        //ArrayList<Student> students = new ArrayList<>();
        Connection con = JDBCUtils.getConnection();
        Statement stat = con.createStatement();
        String sql = "select * from student_1 where name = '"+name+"'";
        ResultSet rs = stat.executeQuery(sql);
        Student stu = new Student();
        while (rs.next()){
            stu.setId(rs.getInt("id"));
            stu.setName(name);
            stu.setStuid(rs.getString("stuid"));
            stu.setSex(rs.getString("sex"));
            stu.setClassid((rs.getString("classid")));
            //students.add(stu);
        }
        release(con,stat,rs);
        return stu;
    }

    public static HashMap<String,int[]> getCourseAndFlag() throws Exception {
        HashMap<String, int[]> map = new HashMap<String, int[]>();

        Connection con = JDBCUtils.getConnection();
        Statement stat = con.createStatement();
        String sql = "SELECT banji,\n" +
                "       SUM(CASE WHEN flag='出勤' THEN 1 ELSE 0 END) AS 出勤人数,\n" +
                "       SUM(CASE WHEN flag='缺勤' THEN 1 ELSE 0 END) AS 缺勤人数\n" +
                "FROM kaoqing\n" +
                "GROUP BY banji;";
        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()){
            String course = rs.getString(1);
            int chuq = rs.getInt(2);
            int queq =  rs.getInt(3);
            map.put(course,new int[]{chuq,queq});
        }
        return  map;
    }

    public static ArrayList<Student> queryByClass(String classid) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        Connection con = JDBCUtils.getConnection();
        Statement stat = con.createStatement();
        String sql = "select * from student_1 where classid = '"+classid+"'";
        ResultSet rs = stat.executeQuery(sql);

        while (rs.next()){
            Student stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setName(rs.getString("name"));
            stu.setStuid(rs.getString("stuid"));
            stu.setSex(rs.getString("sex"));
            stu.setClassid((rs.getString("classid")));
            students.add(stu);
        }
        release(con,stat,rs);
        return students;
    }

    public static ArrayList<Student> queryAllStudent() throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        Connection con = JDBCUtils.getConnection();
        Statement stat = con.createStatement();
        String sql = "select * from student_1 ";
        ResultSet rs = stat.executeQuery(sql);

        while (rs.next()){
            Student stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setName(rs.getString("name"));
            stu.setStuid(rs.getString("stuid"));
            stu.setSex(rs.getString("sex"));
            stu.setClassid((rs.getString("classid")));
            students.add(stu);
        }
        release(con,stat,rs);
        return students;
    }

    public static ArrayList<Student> queryStuByNameAndClass(String stuName,String className) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        Connection con = JDBCUtils.getConnection();
        Statement stat = con.createStatement();
        String sql = "select * from student_1 where name='"+stuName+"' and classid='"+className+"'";
        ResultSet rs = stat.executeQuery(sql);

        while (rs.next()){
            Student stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setName(rs.getString("name"));
            stu.setStuid(rs.getString("stuid"));
            stu.setSex(rs.getString("sex"));
            stu.setClassid((rs.getString("classid")));
            students.add(stu);
        }
        release(con,stat,rs);
        return students;
    }

    public static ArrayList<Attendence> getAllAttendence() throws ClassNotFoundException, SQLException {
        //1,注册驱动信息

        Connection con = JDBCUtils.getConnection();
        Statement stat = con.createStatement();
        String sql = "SELECT id,stuid,name,sex,classid,banji,jieci,flag,attendencedate FROM kaoqing;";
        //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
        ResultSet result = stat.executeQuery(sql);
        //返回的数据List
        ArrayList<Attendence> list = new ArrayList<>();
        while (result.next()) {
            Attendence attendence = new Attendence();
            attendence.setId(result.getInt("id"));
            attendence.setStuid(result.getString("stuid"));
            attendence.setName(result.getString("NAME"));
            attendence.setSex(result.getString("sex"));
            attendence.setClassid(result.getString("classid"));
            attendence.setBanji(result.getString("Banji"));
            attendence.setJieci(result.getString("Jieci"));
            attendence.setFlag(result.getString("flag"));
            attendence.setDate(result.getDate("attendencedate"));
            list.add(attendence);
        }
        release(con,stat,result);
        return list;

    }

    public static ArrayList<Attendence> getAttendenceByTime(String time) throws ClassNotFoundException, SQLException {
        //1,注册驱动信息

        Connection con = JDBCUtils.getConnection();
        Statement stat = con.createStatement();
        String sql = "SELECT id,stuid,name,sex,classid,banji,jieci,flag,attendencedate FROM kaoqing where attendencedate = '" + time + "'";
        //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
        ResultSet result = stat.executeQuery(sql);
        //返回的数据List
        ArrayList<Attendence> list = new ArrayList<>();
        while (result.next()) {
            Attendence attendence = new Attendence();
            attendence.setId(result.getInt("id"));
            attendence.setStuid(result.getString("stuid"));
            attendence.setName(result.getString("NAME"));
            attendence.setSex(result.getString("sex"));
            attendence.setClassid(result.getString("classid"));
            attendence.setBanji(result.getString("Banji"));
            attendence.setJieci(result.getString("Jieci"));
            attendence.setFlag(result.getString("flag"));
            attendence.setDate(result.getDate("attendencedate"));
            list.add(attendence);
        }
        release(con,stat,result);
        return list;

    }


    public static ArrayList<String> queryKaoqClass() throws ClassNotFoundException, SQLException {
        Connection con = JDBCUtils.getConnection();
        Statement stat = con.createStatement();
        String sql = "SELECT classid FROM kaoqing GROUP BY classid";
        //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
        ResultSet result = stat.executeQuery(sql);
        //返回的数据List
        ArrayList<String> list = new ArrayList<>();
        while (result.next()) {
            list.add(result.getString(1));
        }
        release(con,stat,result);
        return list;

    }

    public static int[] queryKaoqNumber(String classid) throws ClassNotFoundException, SQLException {
        Connection con = JDBCUtils.getConnection();
        Statement stat = con.createStatement();
        String sql = "SELECT classid,\n" +
                "       SUM(CASE WHEN flag='出勤' THEN 1 ELSE 0 END) AS 出勤人数,\n" +
                "       SUM(CASE WHEN flag='缺勤' THEN 1 ELSE 0 END) AS 缺勤人数\n" +
                "FROM kaoqing\n" +
                "where classid = '"+classid+"'\n"+
                "GROUP BY classid" ;
        System.out.println(sql);
        //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
        ResultSet result = stat.executeQuery(sql);
        //返回的数据List
        int[] num = new int[2];
        while (result.next()) {
            num[0] = result.getInt(2);
            num[1] = result.getInt(3);
        }
        release(con,stat,result);
        return num;

    }


    public static ArrayList<Attendence> querySomeAttenceByInfo(Attendence a) throws SQLException, ClassNotFoundException {
        StringBuffer sql = new StringBuffer("SELECT * FROM kaoqing where ");
        String sqls[] = new String[6];
        int i = 0;
        if(!StringUtil.isEmpty(a.getName())) {
            sqls[i++] = "name like '%" + a.getName()+"%' ";
        }
        if(!a.getClassid().equals("全匹配")) {
            sqls[i++] = "classid = '" + a.getClassid()+"'";
        }
        if(!a.getBanji().equals("全匹配")) {
            sqls[i++] = "banji = '" + a.getBanji()+"'";
        }
        if(!a.getJieci().equals("全匹配")) {
            sqls[i++] = "jieci = '" + a.getJieci()+"'";
        }
        if(!a.getDate().toString().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(a.getDate());
            if(!format.equals("1999-01-01")){
                sqls[i++] = "attendencedate like '%" + sdf.format(a.getDate()) +"%' ";
            }
        }
        if(i == 0) {
            ArrayList<Attendence> dbData = Attendence_Record.getDbData();
            return dbData;
        }


        Connection con = JDBCUtils.getConnection();
        Statement stat = con.createStatement();
        sql = StringUtil.splicingStrs(i, sql, sqls);
        System.out.println(sql);
        ResultSet result = stat.executeQuery(sql.toString());


        //返回的数据List
        ArrayList<Attendence> list = new ArrayList<>();
        while (result.next()) {
            Attendence attendence = new Attendence();
            attendence.setId(result.getInt("id"));
            attendence.setStuid(result.getString("stuid"));
            attendence.setName(result.getString("NAME"));
            attendence.setSex(result.getString("sex"));
            attendence.setClassid(result.getString("classid"));
            attendence.setBanji(result.getString("Banji"));
            attendence.setJieci(result.getString("Jieci"));
            attendence.setFlag(result.getString("flag"));
            attendence.setDate(result.getDate("attendencedate"));
            list.add(attendence);
        }
        release(con,stat,result);
        return list;

    }
}
