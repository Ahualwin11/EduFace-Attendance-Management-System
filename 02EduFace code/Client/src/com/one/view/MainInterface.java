package com.one.view;
import com.one.dao.TeacherDao;
import com.one.bean.Teacher;
import com.one.component.BackGroundPanel;
import com.one.util.PathUtils;
import com.one.util.ScreenUtils;
import com.one.util.StringUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
public class MainInterface {
    JFrame jf = new  JFrame("e点课堂");
    final int WIDTH = 1269;
    final int HEIGHT = 711;
    public static String UName;

    //客户端程序的入口
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainInterface window = new MainInterface();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public MainInterface() throws Exception {
        init();
    }
    //组装视图
    public void init() throws Exception{
        //设置窗口相关属性
        //设置窗口居中
        jf.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        jf.setResizable(false);//窗口大小固定
        //jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));
        jf.setIconImage(new ImageIcon(MainInterface.class.getResource("/image/logo.png")).getImage());
        //设置窗口内容
        //BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("login.png"))));
        BackGroundPanel bgPanel = new BackGroundPanel(new ImageIcon(MainInterface.class.getResource("/image/login.png")).getImage());
        //组装登录相关的元素
        Box vBox = Box.createVerticalBox();
        //组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("账    号：");
        uLabel.setFont(new Font("宋楷", Font.BOLD, 15));
        JTextField uField = new JTextField(15);

        uBox.add(Box.createHorizontalStrut(15));//水平隔一段距离
        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(5));//水平隔一段距离
        uBox.add(uField);

        //组装密码
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("密    码：");

        pLabel.setFont(new Font("宋楷", Font.BOLD, 15));
        //JTextField pField = new JTextField(15);
        JPasswordField pField = new JPasswordField(15);

        pBox.add(Box.createHorizontalStrut(15));
        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(5));
        pBox.add(pField);

        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        Box btn1Box = Box.createHorizontalBox();
        JButton registBtn_teacher1 = new JButton();
        JLabel jLabel_teacher1 = new JLabel("         登录    ");
        jLabel_teacher1.setFont(new Font("楷体", Font.BOLD, 20));
        jLabel_teacher1.setForeground(Color.WHITE);
        registBtn_teacher1.add(jLabel_teacher1);
        registBtn_teacher1.setMargin(new Insets(0,0,0,0));
        registBtn_teacher1.setBorderPainted(false);
        registBtn_teacher1.setBorder(null);
        registBtn_teacher1.setFocusPainted(false);
        registBtn_teacher1.setContentAreaFilled(false);


        JButton registBtn_teacher = new JButton();
        JLabel jLabel_teacher = new JLabel(" 点击注册");
        jLabel_teacher.setFont(new Font("楷体", Font.BOLD, 15));
        jLabel_teacher.setForeground(Color.blue);
        registBtn_teacher.add(jLabel_teacher);
        registBtn_teacher.setMargin(new Insets(0,0,0,0));
        registBtn_teacher.setBorderPainted(false);
        registBtn_teacher.setBorder(null);
        registBtn_teacher.setFocusPainted(false);
        registBtn_teacher.setContentAreaFilled(false);

        JLabel uLabel1 = new JLabel("                           没有账号？");
        uLabel1.setFont(new Font("宋楷", Font.BOLD, 15));
        btnBox.add(registBtn_teacher1);

        btn1Box.add(uLabel1 );
        btn1Box.add(registBtn_teacher);

        registBtn_teacher1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取用户输入的数据
                String username = uField.getText().toString();
                String password = pField.getText().toString();
                if (StringUtil.isEmpty(username)){
                    JOptionPane.showMessageDialog(jf,"用户名不能为空！");
                    return;
                }
                if (StringUtil.isEmpty(password)){
                    JOptionPane.showMessageDialog(jf,"密码不能为空！");
                    return;
                }
                TeacherDao teacherDao = new TeacherDao();
                Teacher teacherTmp = new Teacher();
                teacherTmp.setName(username);
                teacherTmp.setPassword(password);
                Teacher teacher = teacherDao.login(teacherTmp);
                if(teacher == null){
                    JOptionPane.showMessageDialog(jf,"用户名或密码错误！");
                    return;
                }
                //登录成功
                UName = username;
                try {
                      new Teacher_inter().init();
                    jf.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        registBtn_teacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //跳转到注册页面
                try {
                    new RegisterInterface_teacher().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //当前界面消失
                jf.dispose();
            }
        });

        vBox.add(Box.createVerticalStrut(270));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(55));
        vBox.add(btnBox);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(btn1Box);

        bgPanel.add(vBox);
        jf.add(bgPanel);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}
