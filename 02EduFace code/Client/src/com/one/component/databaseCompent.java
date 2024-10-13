package com.one.component;

import com.one.dao.TeacherDao;
import com.one.util.DbUtil;
import com.one.util.PathUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Enumeration;
import java.util.Vector;

public class databaseCompent extends Box {
    final int WIDTH = 700;
    final int HEIGHT = 600;
    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    private TableModel tableModel;

    public databaseCompent(){
        //垂直布局
        super(BoxLayout.Y_AXIS);
        //组装视图
        JPanel btnPanel = new JPanel();
        Color color = new Color(208,218,188);
        btnPanel.setBackground(color);//设置背景颜色
        btnPanel.setMaximumSize(new Dimension(WIDTH,80));//设置按纽栏的大小
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));//设置从右开始布局


        //组装表格
        String[] ts = {"姓名","班级","出勤次数","缺勤次数"};
        titles = new Vector<>();
        for (String title : ts) {
            titles.add(title);
        }
        tableData = new Vector<>();


        tableModel = new DefaultTableModel(tableData,titles);
        table = new JTable(tableModel){
            //设置不可编辑
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//设置只能选中一行
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);

        //表格居中
        DefaultTableCellRenderer dc=new DefaultTableCellRenderer();
        dc.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, dc);
        //设置每一列的列宽

        table.setRowHeight(40);
        resquestData();

    }
    public  void resquestData(){
        DbUtil dbUtil = new DbUtil();
        Connection con = dbUtil.getCon();
        String sql = "SELECT NAME, classid,\n" +
                "       SUM(CASE WHEN flag='出勤' THEN 1 ELSE 0 END) AS 出勤次数,\n" +
                "       SUM(CASE WHEN flag='缺勤' THEN 1 ELSE 0 END) AS 缺勤次数\n" +
                "FROM kaoqing\n" +
                "GROUP BY NAME\n" +
                "ORDER BY 出勤次数";
        Statement statement = null;
        try {
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();

            //清空tableData的数据
            //tableData.clear();

            int columns = metaData.getColumnCount();
            while (resultSet.next()) {
                Vector row = new Vector(columns);
                for (int i = 1; i <= columns; i++) {
                    row.addElement(resultSet.getObject(i));
                }
                tableData.addElement(row);
            }
            //刷新表格
            //tableModel.fireTableDataChanges();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
