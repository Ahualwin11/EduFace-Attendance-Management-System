package com.one.component;

import com.one.bean.ClassBean;
import com.one.service.ClassService;
import com.one.domain.ClassServiceImpl;
import com.one.util.JDBCUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ClassAttendanceChart extends Box {

    private ArrayList<ClassBean> classList;
    private ClassService classService = new ClassServiceImpl();
    private ChartPanel chartPanel;
    public ClassAttendanceChart() {

        super(BoxLayout.Y_AXIS);
        initUI();
        this.setVisible(true);
    }

    private void initUI() {
        JPanel Panel = new JPanel();

        JLabel sLabel = new JLabel("选择班级");
        sLabel.setFont(new Font("等线", Font.PLAIN, 15));
        //下拉选项框
        Box sBox = Box.createHorizontalBox();
        JComboBox comboBox = new JComboBox<>();
        classList = classService.queryAllClass();
        ClassBean[] classBeans = new ClassBean[classList.size()];
        for (int i = 0; i < classList.size(); i++) {
            classBeans[i] = classList.get(i);
        }
        comboBox.setModel(new DefaultComboBoxModel<ClassBean>(classBeans));
        sBox.add(sLabel);
        sBox.add(Box.createHorizontalStrut(20));
        sBox.add(comboBox);
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



        DefaultPieDataset dataset = new DefaultPieDataset();

        int[] num_1 = new int[2];
        try {
            num_1 = JDBCUtils.queryKaoqNumber("21计科一班");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(Arrays.toString(num_1));
        dataset.setValue("出勤", num_1[0]);
        dataset.setValue("缺勤", num_1[1]);



        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, true, false);
        chartPanel = new ChartPanel(chart);

        PiePlot plot_1 = (PiePlot) chart.getPlot();
        plot_1.setSectionPaint("出勤", new Color(106,179,217));
        plot_1.setSectionPaint("缺勤", new Color(255,170,68));


        comboBox.addActionListener(e -> {
            String selectedClass = comboBox.getSelectedItem().toString();
            System.out.println(selectedClass);
            int[] num = new int[2];
            try {
                num = JDBCUtils.queryKaoqNumber(selectedClass);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println(Arrays.toString(num));
            dataset.setValue("出勤", num[0]);
            dataset.setValue("缺勤", num[1]);

            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setSectionPaint("出勤", new Color(106,179,217));
            plot.setSectionPaint("缺勤", new Color(255,170,68));
        });
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1100, 660));
        Panel.add(sBox);
        Panel.add(chartPanel);
        add(Panel);

    }
    private HashMap<String, int[]> getData() {

        HashMap<String, int[]> data = null;
        try {
            data = JDBCUtils.getCourseAndFlag();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        data.put("计算机基础", new int[]{10, 3});
//        data.put("软件工程", new int[]{8, 5});
//        data.put("计算机组成原理", new int[]{10, 5});
        return data;
    }
    private CategoryDataset createDataset(HashMap<String, int[]> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String key : data.keySet()) {
            dataset.addValue(data.get(key)[0], "出勤人数", key);
            dataset.addValue(data.get(key)[1], "缺勤人数", key);
        }
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "总体出勤柱状图", // 图表标题
                "课程", // X轴标签
                "学生人数", // Y轴标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向
                true, // 显示图例
                true, // 使用工具提示
                false // 生成URL
        );

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(106,179,217));
        renderer.setSeriesPaint(1, new Color(255,170,68));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 15);

        return chart;
    }

}


