package com.one.view;

import com.one.bean.ClassBean;
import com.one.component.BackGroundPanel;
import com.one.component.FaceRegister;
import com.one.dao.StudentDao_1;
import com.one.service.ClassService;
import com.one.domain.ClassServiceImpl;
import com.one.util.PathUtils;
import com.one.util.ScreenUtils;
import com.one.util.StringUtil;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class RegisterInterface_student {
    JFrame jf = new JFrame("注册");
    final int WIDTH = 1269;
    final int HEIGHT = 711;
    private ArrayList<ClassBean> classList;
    private ClassService classService = new ClassServiceImpl();
    public static String USERNAME;
    public static HashMap<String,Long> HmName = new HashMap<>();

    //组装视图
    public void init() throws Exception {
        //设置窗口属性
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);//窗口大小固定

        jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));
        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("stu_regist.jpg"))));//regist.png
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);

        Box vBox = Box.createVerticalBox();
        //组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("姓   名：");
        JTextField uField = new JTextField(15);

        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));//水平间隔
        uBox.add(uField);

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

        //组装手机号
        Box tBox = Box.createHorizontalBox();
        JLabel tLabel = new JLabel("学    号：");
        JTextField tField = new JTextField(15);

        tBox.add(tLabel);
        tBox.add(Box.createHorizontalStrut(20));//水平间隔
        tBox.add(tField);

        Box sBox = Box.createHorizontalBox();
        JLabel sLabel = new JLabel("班    级：");
        //下拉选项框
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

        //组装
        Box fBox = Box.createHorizontalBox();
        JButton face = new JButton("录入人脸");
        fBox.add(face);
        face.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                USERNAME = uField.getText();
                HmName.put(USERNAME,System.currentTimeMillis());
                System.out.println("录入人脸"+USERNAME);
                FaceRegister faceReconize = new FaceRegister();
            }
        });
        JButton backButton = new JButton("返回");
        Box btnBox = Box.createHorizontalBox();
        JButton registBtn = new JButton("注册");
        btnBox.add(registBtn);
        btnBox.add(Box.createHorizontalStrut(50));
        btnBox.add(backButton);

        Box rBox = Box.createHorizontalBox();
        vBox.add(Box.createVerticalStrut(190));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(tBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(sBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(gBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(fBox);
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
                String phone = tField.getText().trim();//学号
                String gender = bg.isSelected(maleBtn.getModel()) ? maleBtn.getText() : femaleBtn.getText();
                String classSelectedItem = ((ClassBean)comboBox.getSelectedItem()).toString();
                if (StringUtil.isEmpty(username) || StringUtil.isEmpty(phone)) {
                    JOptionPane.showMessageDialog(jf, "请将信息填写完整！");
                } else if (!FaceRegister.flag_face) {
                    JOptionPane.showMessageDialog(jf, "温馨提示：请录入人脸！");
                }else {
                    boolean flag = new StudentDao_1().registInfo(username, "123", gender, phone,classSelectedItem);//将注册的数据写入本地数据库
                    if (flag) {
                        //当前界面消失
                        try {
                            new Teacher_inter();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        jf.dispose();
                    } else {
                        JOptionPane.showMessageDialog(jf, "该学生已录入，请勿重复录入");
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
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
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new RegisterInterface_student().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
