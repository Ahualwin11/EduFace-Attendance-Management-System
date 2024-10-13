package com.one.component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class CourseSchedule extends JFrame {

    public CourseSchedule() {
        setTitle("学期课程表");

        JPanel panel = new JPanel(new GridLayout(9, 8));

        JLabel[] header = { new JLabel("时间/星期"), new JLabel("星期一"), new JLabel("星期二"), new JLabel("星期三"),
                new JLabel("星期四"), new JLabel("星期五"), new JLabel("星期六"), new JLabel("星期日") };
        for (JLabel label : header) {
            label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setOpaque(true);
            label.setBackground(new Color(69, 90, 100));
            panel.add(label);
        }

        String[] times = { "8:00-8:45", "8:55-9:40", "10:00-10:45", "10:55-11:40",
                "14:00-14:45", "14:55-15:40", "16:00-16:45" ,"16:55-17:40"};
        for (int i = 0; i < times.length; i++) {
            JLabel timeLabel = new JLabel(times[i]);
            timeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            timeLabel.setOpaque(true);
            timeLabel.setBackground(new Color(144, 164, 174));
            panel.add(timeLabel);

            for (int j = 0; j < 7; j++) {
                JTextArea textArea = new JTextArea();
                textArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                textArea.setLineWrap(true); // 自动换行
                textArea.setWrapStyleWord(true); // 按单词换行
                JScrollPane scrollPane = new JScrollPane(textArea);
                panel.add(scrollPane);
                scrollPane.setViewportBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }

        add(panel);

        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // 关闭窗口装饰
        setVisible(true);

        // 窗口形状设置
        int arc = 30; // 圆弧半径
        RoundRectangle2D shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc);
        setShape(shape);
    }

    public static void main(String[] args) {
        new CourseSchedule();
    }
}