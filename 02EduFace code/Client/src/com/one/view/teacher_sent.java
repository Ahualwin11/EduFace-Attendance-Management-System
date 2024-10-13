package com.one.view;

import com.one.bean.ClassBean;
import com.one.bean.Course_info;
import com.one.component.BackGroundPanel;
import com.one.component.FaceRecognize;
import com.one.dao.CourseDao;
import com.one.bean.Student;
import com.one.service.ClassService;
import com.one.domain.ClassServiceImpl;
import com.one.domain.CourseServiceImpl;
import com.one.util.JDBCUtils;
import com.one.util.PathUtils;
import com.one.util.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class teacher_sent {
    JFrame jf = new JFrame("发布签到");
    JFrame currentWindow = jf;
    final int WIDTH = 652;
    final int HEIGHT = 531;
    public static boolean flag;
    private ArrayList<ClassBean> classList;
    private ArrayList<Course_info> courseList;
    private ClassService classService = new ClassServiceImpl();
    private ClassService courseService = new CourseServiceImpl();
    public static long longtime;
    public static String classid;

    //组装视图
    public void init() throws Exception {
        //设置窗口属性
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);//窗口大小固定
        jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));
        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("sent.png"))));//regist.png
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);
        bgPanel.setLayout(null);

        //发布时长
        Box gBox = Box.createHorizontalBox();
        JLabel gLabel = new JLabel("选择节次");
        gLabel.setFont(new Font("等线", Font.PLAIN, 15));
        JRadioButton Btn12 = new JRadioButton("1-2节", true);
        JRadioButton Btn34 = new JRadioButton("3-4节", false);
        JRadioButton Btn56 = new JRadioButton("5-6节", false);
        JRadioButton Btn78 = new JRadioButton("7-8节", false);
        JRadioButton Btn90 = new JRadioButton("9-10节", false);
        //实现单选
        ButtonGroup bg = new ButtonGroup();
        bg.add(Btn12);
        bg.add(Btn34);
        bg.add(Btn56);
        bg.add(Btn78);
        bg.add(Btn90);

        gBox.add(gLabel);
        gBox.add(Box.createHorizontalStrut(20));
        gBox.add(Btn12);
        gBox.add(Btn34);
        gBox.add(Btn56);
        gBox.add(Btn78);
        gBox.add(Btn90);
        gBox.add(Box.createHorizontalStrut(120));

        Box sBox = Box.createHorizontalBox();
        JLabel sLabel = new JLabel("选择班级");
        sLabel.setFont(new Font("等线", Font.PLAIN, 15));
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

        Box kBox = Box.createHorizontalBox();
        JLabel kLabel = new JLabel("选择课程");
        kLabel.setFont(new Font("等线", Font.PLAIN, 15));
        //下拉选项框
        JComboBox kcombox = new JComboBox<>();
        courseList = CourseDao.getDbData();
        Course_info[] courseBeans = new Course_info[courseList.size()];
        for (int i = 0; i < courseList.size(); i++) {
            courseBeans[i] = courseList.get(i);
        }
        kcombox.setModel(new DefaultComboBoxModel<Course_info>(courseBeans));
        kBox.add(kLabel);
        kBox.add(Box.createHorizontalStrut(20));
        kBox.add(kcombox);

        JButton confirm = new JButton();
        JLabel confirm_label = new JLabel("      确定");
        confirm_label.setFont(new Font("等线", Font.PLAIN, 20));
        confirm_label.setForeground(Color.white);
        confirm.add(confirm_label);
        confirm.setMargin(new Insets(0, 0, 0, 0));
        confirm.setBorderPainted(false);
        confirm.setBorder(null);
        confirm.setFocusPainted(false);
        confirm.setContentAreaFilled(false);

        JButton concel = new JButton();
        JLabel concel_label = new JLabel("      取消");
        concel_label.setFont(new Font("等线", Font.PLAIN, 20));
        concel_label.setForeground(Color.white);
        concel.add(concel_label);
        concel.setMargin(new Insets(0, 0, 0, 0));
        concel.setBorderPainted(false);
        concel.setBorder(null);
        concel.setFocusPainted(false);
        concel.setContentAreaFilled(false);

        kBox.setBounds(126, 84, 386, 31);
        sBox.setBounds(126, 176, 386, 31);
        gBox.setBounds(126, 258, 386, 31);
        confirm.setBounds(165, 376, 96, 31);
        concel.setBounds(395, 376, 96, 31);

        bgPanel.add(kBox);
        bgPanel.add(sBox);
        bgPanel.add(gBox);
        bgPanel.add(confirm);
        bgPanel.add(concel);
        jf.add(bgPanel);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWindow.setVisible(false); // 隐藏当前窗口
                //获取课程
                Object selectedCourse = kcombox.getSelectedItem();
                if (selectedCourse != null) {
                    System.out.println(selectedCourse.toString());
                }
                //获取班级
                Object selectedClass = comboBox.getSelectedItem();
                if (selectedClass != null) {
                    System.out.println(selectedClass.toString());
                }
                classid = selectedClass.toString();
                //获取节次
                String jieci = null;
                if (Btn12.isSelected()) {
                    jieci = Btn12.getText();
                } else if (Btn34.isSelected()) {
                    jieci = Btn34.getText();
                } else if (Btn56.isSelected()) {
                    jieci = Btn56.getText();
                } else if (Btn78.isSelected()) {
                    jieci = Btn78.getText();
                } else {
                    jieci = Btn90.getText();
                }
                System.out.println(jieci);
                //将这个班的生写到考勤记录表
                ArrayList<Student> stus = null;
                try {
                    stus = JDBCUtils.queryByClass(selectedClass.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                for (Student stu : stus) {
                    System.out.println(stu.getSex() + " " + stu.getClassid() + " " + stu.getName());
                    //插入
                    longtime = System.currentTimeMillis();
                    String sql = "INSERT INTO kaoqing (NAME,stuid,sex,classid,banji,jieci) VALUES('" + stu.getName() + "','" + stu.getStuid() + "','" + stu.getSex() + "','" + stu.getClassid() + "','" + selectedCourse.toString() + "','" + jieci + "')";
                    JDBCUtils.Update(sql);
                }
                FaceRecognize frame = null;
                frame = new FaceRecognize();

                frame.setVisible(true);
                frame.invokeCamera(frame, FaceRecognize.videoCamera);
            }
        });
        concel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "确定要退出吗？", "退出", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // 用户选择了“是”，在这里执行退出操作
                    currentWindow.setVisible(false); // 隐藏当前窗口
                    try {
                        new Teacher_inter().init();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new teacher_sent().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
