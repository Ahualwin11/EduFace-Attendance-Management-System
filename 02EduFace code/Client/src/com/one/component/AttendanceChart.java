package com.one.component;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

import com.one.util.JDBCUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class AttendanceChart extends Box {
    public AttendanceChart() {

        super(BoxLayout.Y_AXIS);
        initUI();
        this.setVisible(true);
    }

    private void initUI() {
        JPanel Panel = new JPanel();
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

        HashMap<String, int[]> data = getData();
        CategoryDataset dataset = createDataset(data);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1100, 660));
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
                "课程出勤柱状图", // 图表标题
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
        rangeAxis.setRange(0, 70);

        return chart;
    }

}


