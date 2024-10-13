package com.one.component;

import com.one.util.JDBCUtils;
import com.one.util.PathUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class JTableImg extends Box {

    private JTable table;
    private Object[][] data;

    public JTableImg() {
        //垂直布局
        super(BoxLayout.Y_AXIS);
        //组装视图
        JPanel btnPanel = new JPanel();
        Color color = new Color(195,224,239);
        btnPanel.setBackground(color);//设置背景颜色
        btnPanel.setMaximumSize(new Dimension(1100,50));//设置按纽栏的大小
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("系统识别记录");

        btnPanel.add(label);

        setSize(1100,650);//设置窗口大小

        table = new JTable();
        String[] columnNames = new String[] {"姓名", "人像"};
        String P_PATH="D:\\opencv-demo\\face";

        //获取图片路径
        File file = new File(P_PATH);
        File[] listFiles = file.listFiles();//文件夹
        int size = listFiles.length;
        data = new Object[size][2];
        int j = 0;
        for (File listFile : listFiles) {
            data[j][0] = listFile.getName();
            if(listFile.isDirectory()){
                File[] files = listFile.listFiles();//姓名文件夹
                if(files.length != 0){
                    File file1 = files[files.length-1];//最后一张jpg
                    String path = file1.getAbsolutePath();
                    data[j][1] = new ImageIcon(path);
                }
                //出勤
                String sql = "UPDATE teacher SET attendence = TRUE WHERE NAME = '"+listFile.getName()+"'";
                JDBCUtils.Update(sql);
            }
            j++;
        }

        JButton openBtn = new JButton("男女比例");
        openBtn.setIcon(new ImageIcon(PathUtils.getRealPath("kaoq.png")));
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //创建主题样式
                StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
                //设置主题样式
                ChartFactory.setChartTheme(standardChartTheme);
                //设置标题字体
                standardChartTheme.setExtraLargeFont(new Font("宋体", Font.BOLD, 20));
                // 设置字体，去除中文乱码
                StandardChartTheme sct = new StandardChartTheme("CN");
                sct.setExtraLargeFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 15));
                sct.setRegularFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 15));
                sct.setLargeFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 15));

                ChartFactory.setChartTheme(sct);

                // 创建数据

                String sql1 = "SELECT DISTINCT(NAME) FROM kaoqing WHERE sex = '男' AND flag='出勤'";
                int male = JDBCUtils.getCount(sql1);//男
                String sql2 = "SELECT DISTINCT(NAME) FROM kaoqing WHERE sex = '女' AND flag='出勤'";
                int female = JDBCUtils.getCount(sql2);//男

                // 创建一个饼图数据集
                DefaultPieDataset dataset = new DefaultPieDataset();
                dataset.setValue("男生", male);
                dataset.setValue("女生", female);

                // 用数据集创建一个饼图
                JFreeChart chart = ChartFactory.createPieChart("男女比例", dataset, true, true, false);

                // 设置饼图的背景色为白色
                chart.setBackgroundPaint(Color.white);

                ChartFrame chartFrame = new ChartFrame("Test", chart);
                chartFrame.pack();
                chartFrame.setLocationRelativeTo(null);
                chartFrame.setVisible(true);
            }
        });
        btnPanel.add(openBtn);

        this.add(btnPanel);
        //将表格数据数组放入表格模型,并重写getColumnClass方法
        table.setModel(new DefaultTableModel(data,columnNames){
            @Override   //核心步骤2：重写getColumnClass方法
            public Class<?> getColumnClass(int columnIndex) {
                return getValueAt(0, columnIndex).getClass();
            }
        });
        //设置每一列的列宽
        table.getColumn(columnNames[0]).setPreferredWidth(200);
        table.getColumn(columnNames[1]).setPreferredWidth(440);

        table.setRowHeight(350);
        JScrollPane  jsp = new JScrollPane(table);
        //jsp.setPreferredSize(new Dimension(500, 400));
        jsp.setBounds(0,100,1100,640);
        this.add(jsp);

        //表格居中
        DefaultTableCellRenderer dc=new DefaultTableCellRenderer();
        dc.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, dc);
        //this.add(contentPane);

    }


}
