package com.one.view;

import com.one.component.BackGroundPanel;
import com.one.dao.TeacherDao;
import com.one.util.PathUtils;
import com.one.util.ScreenUtils;
import com.one.util.StringUtil;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RegisterInterface_teacher {
    JFrame jf = new JFrame("注册");
    final int WIDTH = 1269;
    final int HEIGHT = 711;
    //组装视图
    public void init() throws Exception {
        //设置窗口属性
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);//窗口大小固定

        jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));
        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("regist.png"))));//regist.png
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);

        Box vBox = Box.createVerticalBox();
        //组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("姓   名：");
        JTextField uField = new JTextField(15);

        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));//水平间隔
        uBox.add(uField);

        //组装密码
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("密    码：");
        JPasswordField pField = new JPasswordField(15);

        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(20));//水平间隔
        pBox.add(pField);

        //组装性别
        Box gBox = Box.createHorizontalBox();
        JLabel gLabel = new JLabel("性    别：");
        JRadioButton maleBtn = new JRadioButton("男", true);
        JRadioButton femaleBtn = new JRadioButton("女", false);
        //实现单选
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleBtn);
        bg.add(femaleBtn);

        gBox.add(gLabel);
        gBox.add(Box.createHorizontalStrut(20));
        gBox.add(maleBtn);
        gBox.add(femaleBtn);
        gBox.add(Box.createHorizontalStrut(120));

        //组装工号
        Box tBox = Box.createHorizontalBox();
        JLabel tLabel = new JLabel("工    号：");
        JTextField tField = new JTextField(15);

        tBox.add(tLabel);
        tBox.add(Box.createHorizontalStrut(20));//水平间隔
        tBox.add(tField);

        Box btnBox = Box.createHorizontalBox();
        JButton registBtn = new JButton("注册并登录");
        btnBox.add(registBtn);


        Box rBox = Box.createHorizontalBox();
        JButton backBtn = new JButton();
        JLabel back = new JLabel("已有账号，返回登录");
        back.setFont(new Font("楷体", Font.BOLD, 15));
        back.setForeground(Color.blue);
        backBtn.add(back);
        backBtn.setMargin(new Insets(0, 0, 0, 0));
        backBtn.setBorderPainted(false);
        backBtn.setBorder(null);
        backBtn.setFocusPainted(false);
        backBtn.setContentAreaFilled(false);
        rBox.add(Box.createHorizontalStrut(100));
        rBox.add(backBtn);

        vBox.add(Box.createVerticalStrut(190));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(tBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(gBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(btnBox);
        vBox.add(Box.createVerticalStrut(100));
        vBox.add(rBox);
        bgPanel.add(vBox);
        jf.add(bgPanel);

        //注册按钮
        registBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = uField.getText().trim();
                String password = pField.getText().trim();
                String phone = tField.getText().trim();
                String gender = bg.isSelected(maleBtn.getModel()) ? maleBtn.getText() : femaleBtn.getText();
                if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password) || StringUtil.isEmpty(phone)) {
                    JOptionPane.showMessageDialog(jf, "请将信息填写完整！");
                }  else {
                    boolean flag = new TeacherDao().registInfo(username, password, gender, phone);//将注册的数据写入本地数据库
                    if (flag) {
                        try {
                            new Teacher_inter().init();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        //当前界面消失
                        jf.dispose();
                    } else {
                        JOptionPane.showMessageDialog(jf, "用户名已存在");
                    }
                }
            }
        });

        //返回登录页面按钮
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MainInterface().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //当前界面消失
                jf.dispose();
            }
        });
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

    }

    public static void main(String[] args) {
        try {
            new RegisterInterface_teacher().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
