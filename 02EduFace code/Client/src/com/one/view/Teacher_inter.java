package com.one.view;
import com.one.component.BackGroundPanel;
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
import java.sql.SQLException;

public class Teacher_inter {
    static JFrame jf = new  JFrame("e点课堂");
    final int WIDTH = 1269;
    final int HEIGHT = 713;
    public static String UName;
    public static String Usertype;
    //客户端程序的入口
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Teacher_inter window = new Teacher_inter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Teacher_inter() throws Exception {
        init();
    }
    //组装视图
    public void init() throws Exception{
        //设置窗口相关属性
        jf.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        jf.setResizable(false);//窗口大小固定
        jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));
        //设置窗口内容
        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("teacher.png"))));
        bgPanel.setLayout(null);
        //组装登录相关的元素
        //学生管理
        JButton stuControl = new JButton();
        stuControl.setBounds(432,375,147,62);
        JLabel jLabel_teacher = new JLabel(" 学生管理");
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
                try {
                    new Student_Management().showFrame(Student_Management.getDbData());
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //考勤记录明细
        JButton kaoqBtn = new JButton();
        kaoqBtn.setBounds(733,225,147,62);
        JLabel kaoq_label = new JLabel(" 考勤记录");
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
                try {
                    new Attendence_Record().showFrame(Attendence_Record.getDbData());
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //学生录入
        JButton stu_interBtn = new JButton();
        stu_interBtn.setBounds(432,225,147,62);
        JLabel stu_inter_label = new JLabel(" 学生录入");
        stu_inter_label.setFont(new Font("等线", Font.PLAIN, 23));
        stu_interBtn.add(stu_inter_label);
        stu_interBtn.setMargin(new Insets(0,0,0,0));
        stu_interBtn.setBorderPainted(false);
        stu_interBtn.setBorder(null);
        stu_interBtn.setFocusPainted(false);
        stu_interBtn.setContentAreaFilled(false);
        stu_interBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new RegisterInterface_student().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        //进入官网
        JButton guanwBtn = new JButton();
        guanwBtn.setBounds(733,375,147,62);
        JLabel guanw_label = new JLabel(" 校园官网");
        guanw_label.setFont(new Font("等线", Font.PLAIN, 23));
        guanwBtn.add(guanw_label);
        guanwBtn.setMargin(new Insets(0,0,0,0));
        guanwBtn.setBorderPainted(false);
        guanwBtn.setBorder(null);
        guanwBtn.setFocusPainted(false);
        guanwBtn.setContentAreaFilled(false);
        guanwBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jf,"即将进入官网");
                try {
                    Desktop.getDesktop().browse(new URI("https://www.hncu.edu.cn/index.htm"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //数据分析
        JButton shujuBtn = new JButton();
        shujuBtn.setBounds(733,535,147,62);
        JLabel shuju_label = new JLabel(" 数据分析");
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
                    new DataAnalysis().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //异常申报
        JButton exceptBtn = new JButton();
        exceptBtn.setBounds(432,535,147,62);
        JLabel except_label = new JLabel(" 异常申报");
        except_label.setFont(new Font("等线", Font.PLAIN, 23));
        exceptBtn.add(except_label);
        exceptBtn.setMargin(new Insets(0,0,0,0));
        exceptBtn.setBorderPainted(false);
        exceptBtn.setBorder(null);
        exceptBtn.setFocusPainted(false);
        exceptBtn.setContentAreaFilled(false);
        exceptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new except_shen_bao().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //异常申报
        JButton kuaisu = new JButton();
        kuaisu.setBounds(100,290,147,55);
        JLabel ks_label = new JLabel("一键点名");
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
                try {
                    new teacher_sent().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
        bgPanel.add(stu_interBtn);
        bgPanel.add(stuControl);
        bgPanel.add(guanwBtn);
        bgPanel.add(exceptBtn);
        bgPanel.add(kaoqBtn);
        bgPanel.add(shujuBtn);
        bgPanel.add(c1_jLabel);
        bgPanel.add(c2_jLabel);
        bgPanel.add(textField1);
        bgPanel.add(textField2);
        bgPanel.add(kuaisu);
        jf.add(bgPanel);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}