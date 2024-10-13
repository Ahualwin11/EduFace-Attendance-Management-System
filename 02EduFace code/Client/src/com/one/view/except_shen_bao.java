package com.one.view;

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

public class except_shen_bao {
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
                    except_shen_bao window = new except_shen_bao();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public except_shen_bao() throws Exception {
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
        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("exception.png"))));
        //组装登录相关的元素
        Box vBox = Box.createVerticalBox();
        //组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("姓        名：");
        uLabel.setFont(new Font("宋楷", Font.BOLD, 15));
        JTextField uField = new JTextField(15);

        uBox.add(Box.createHorizontalStrut(15));//水平隔一段距离
        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(5));//水平隔一段距离
        uBox.add(uField);

        //组装密码
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("联系方式：");
        pLabel.setFont(new Font("宋楷", Font.BOLD, 15));
        //JTextField pField = new JTextField(15);
        JTextField pField = new JTextField(15);

        pBox.add(Box.createHorizontalStrut(15));
        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(5));
        pBox.add(pField);
        //组装身份类型

        Box sBox = Box.createHorizontalBox();
        JLabel sLabel = new JLabel("设备地点：");
        sLabel.setFont(new Font("宋楷", Font.BOLD, 15));
        //下拉选项框
        String[] str ={"一教学楼","二教学楼","三教学楼","工训楼","电信楼","管理楼","土木楼","建规楼"};
        JComboBox comboBox = new JComboBox<>(str);

        sBox.add(Box.createHorizontalStrut(15));
        sBox.add(sLabel);
        sBox.add(Box.createHorizontalStrut(5));
        sBox.add(comboBox);

        Box sayBox = Box.createHorizontalBox();
        JLabel say_Label = new JLabel("陈    述：");
        say_Label.setFont(new Font("宋楷", Font.BOLD, 15));
        TextArea textArea = new TextArea();
        textArea.setColumns(10);
        textArea.setRows(10);

        sayBox.add(Box.createHorizontalStrut(15));
        sayBox.add(say_Label);
        sayBox.add(Box.createHorizontalStrut(5));
        sayBox.add(textArea);

        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton loginBtn = new JButton("提交");
        loginBtn.addActionListener(new ActionListener() {
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
                    JOptionPane.showMessageDialog(jf,"联系方式不能为空！");
                    return;
                }
                JOptionPane.showMessageDialog(jf,"提交成功！");
            }
        });
        btnBox.add(Box.createHorizontalStrut(50));
        btnBox.add(loginBtn);

        JButton btnNewButton_2_1 = new JButton("返回");
        btnNewButton_2_1.setBounds(260, 370, 81, 21);
        btnNewButton_2_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Teacher_inter().init();
                    jf.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnBox.add(Box.createHorizontalStrut(50));
        btnBox.add(btnNewButton_2_1);

        //注册按钮
        vBox.add(Box.createVerticalStrut(180));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(23));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(23));
        vBox.add(sBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(sayBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(btnBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(btnBox);
        bgPanel.add(vBox);
        jf.add(bgPanel);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}
