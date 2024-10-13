package com.one.dao;

import com.one.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//创建一个数据库连接对象，整个项目与数据库打交道都用这个对象
public class BaseDao {
    public Connection con = new DbUtil().getCon();
    //这里都是后来加的
    protected PreparedStatement preparedStatement = null;
    protected void closeAll() {
        try {
            if(this.con != null) {
                this.con.close();
            }
            if(this.preparedStatement != null) {
                this.preparedStatement.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
