package com.one.view;

import com.one.component.BackGroundPanel;
import com.one.component.CourseSchedule;
import com.one.component.FaceRecognize;
import com.one.util.PathUtils;
import com.one.util.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Student_inter {
    JFrame jf = new  JFrame("e点课堂");
    final int WIDTH = 1269;
    final int HEIGHT = 713;
    public static String UName;
    public static String Usertype;
    //客户端程序的入口
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Student_inter window = new Student_inter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Student_inter() throws Exception {
        init();
    }
    //组装视图
    public void init() throws Exception{
        //设置窗口相关属性
        //设置窗口居中
        jf.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        jf.setResizable(false);//窗口大小固定

        jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));
        //设置窗口内容
        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("student.png"))));
        bgPanel.setLayout(null);
        //考勤记录
        JButton stuControl = new JButton();
        stuControl.setBounds(432,250,147,62);
        JLabel jLabel_teacher = new JLabel(" 考勤记录");
        jLabel_teacher.setFont(new Font("等线", Font.PLAIN, 23));
        stuControl.add(jLabel_teacher);
        stuControl.setMargin(new Insets(0,0,0,0));
        stuControl.setBorderPainted(false);
        stuControl.setBorder(null);
        stuControl.setFocusPainted(false);
        stuControl.setContentAreaFilled(false);
        stuControl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(jf,"这里进行学生管理...");
            }
        });
        //课程表
        JButton classTable = new JButton();
        classTable.setBounds(733,250,147,62);
        JLabel class_label = new JLabel(" 课程表");
        class_label.setFont(new Font("等线", Font.PLAIN, 23));
        classTable.add(class_label);
        classTable.setMargin(new Insets(0,0,0,0));
        classTable.setBorderPainted(false);
        classTable.setBorder(null);
        classTable.setFocusPainted(false);
        classTable.setContentAreaFilled(false);
        classTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CourseSchedule();
            }
        });
        //考勤记录明细
        JButton kaoqBtn = new JButton();
        kaoqBtn.setBounds(432,480,147,62);
        JLabel kaoq_label = new JLabel(" 异常申报");
        kaoq_label.setFont(new Font("等线", Font.PLAIN, 23));
        kaoqBtn.add(kaoq_label);
        kaoqBtn.setMargin(new Insets(0,0,0,0));
        kaoqBtn.setBorderPainted(false);
        kaoqBtn.setBorder(null);
        kaoqBtn.setFocusPainted(false);
        kaoqBtn.setContentAreaFilled(false);
        kaoqBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(jf,"这里进行考勤记录明细...");
            }
        });
        //进入官网
        JButton shujuBtn = new JButton();
        shujuBtn.setBounds(733,480,147,62);
        JLabel shuju_label = new JLabel(" 进入官网");
        shuju_label.setFont(new Font("等线", Font.PLAIN, 23));
        shujuBtn.add(shuju_label);
        shujuBtn.setMargin(new Insets(0,0,0,0));
        shujuBtn.setBorderPainted(false);
        shujuBtn.setBorder(null);
        shujuBtn.setFocusPainted(false);
        shujuBtn.setContentAreaFilled(false);
        shujuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.baidu.com"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //快速签到
        JButton kuaisu = new JButton();
        kuaisu.setBounds(100,290,147,55);
        JLabel ks_label = new JLabel("快速签到");
        ks_label.setFont(new Font("等线", Font.PLAIN, 23));
        kuaisu.add(ks_label);
        kuaisu.setMargin(new Insets(0,0,0,0));
        kuaisu.setBorderPainted(false);
        kuaisu.setBorder(null);
        kuaisu.setFocusPainted(false);
        kuaisu.setContentAreaFilled(false);
        kuaisu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FaceRecognize frame = null;

                    frame = new FaceRecognize();

                frame.setVisible(true);
                frame.invokeCamera(frame, FaceRecognize.videoCamera);
            }
        });
        //今天日课表
        JLabel c1_jLabel = new JLabel("软件工程 1-2 电信楼 ");
        c1_jLabel.setFont(new Font("等线", Font.PLAIN, 15));
        JLabel c2_jLabel = new JLabel("软件工程 1-2 电信楼 ");
        c2_jLabel.setFont(new Font("等线", Font.PLAIN, 15));
        c1_jLabel.setBounds(64,468,198,39);
        c2_jLabel.setBounds(64,500,198,39);

        //待办事项
        JTextField textField1 = new JTextField();
        textField1.setBounds(1028,195,150,30);
        JTextField textField2 = new JTextField();
        textField2.setBounds(1028,273,150,30);
        textField1.setBorder(null);
        textField2.setBorder(null);

        bgPanel.add(stuControl);
        bgPanel.add(classTable);
        bgPanel.add(kuaisu);
        bgPanel.add(kaoqBtn);
        bgPanel.add(shujuBtn);
        bgPanel.add(c1_jLabel);
        bgPanel.add(c2_jLabel);
        bgPanel.add(textField1);
        bgPanel.add(textField2);
        jf.add(bgPanel);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }


}
