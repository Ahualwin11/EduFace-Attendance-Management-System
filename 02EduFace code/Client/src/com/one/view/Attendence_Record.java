package com.one.view;

import com.one.bean.ClassBean;
import com.one.component.BackGroundPanel;
import com.one.dao.CourseDao;
import com.one.bean.Attendence;
import com.one.bean.Course_info;
import com.one.bean.Student;
import com.one.service.ClassService;
import com.one.domain.ClassServiceImpl;
import com.one.util.JDBCUtils;
import com.one.util.PathUtils;
import com.one.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdesktop.swingx.JXDatePicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.*;

import static java.awt.Color.GRAY;

public class Attendence_Record {
    private ArrayList<Attendence> attList;
    static public JComboBox stuClassComb;
    static public JComboBox kcombox;
    static public JComboBox jieciBox;
    static public JTextField stuName;
    static public JXDatePicker datePicker;
    static public ArrayList<ClassBean> classList;
    static public ClassService classService = new ClassServiceImpl();
    public static DefaultTableModel dtm = null;
    public static JTable table = null;
    public static ArrayList<Attendence> attendenceList;
    //获取数据库中的数据并以list返回
    public static ArrayList<Attendence> getDbData() throws ClassNotFoundException, SQLException {
        //1,注册驱动信息
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2,获取连接对象
        String url = "jdbc:mysql://localhost:3306/itcast";
        Connection conn = DriverManager.getConnection(url, "root", "123456");
        String sql = "SELECT id,stuid,name,sex,classid,banji,jieci,flag,attendencedate FROM kaoqing;";
        //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
        PreparedStatement parameter = conn.prepareStatement(sql);
        //4,执行sql
        ResultSet result = parameter.executeQuery();
        //返回的数据List
        ArrayList<Attendence> list = new ArrayList<>();
        while (result.next()) {
            Attendence attendence = new Attendence();
            attendence.setId(result.getInt("id"));
            attendence.setStuid(result.getString("stuid"));
            attendence.setName(result.getString("NAME"));
            attendence.setSex(result.getString("sex"));
            attendence.setClassid(result.getString("classid"));
            attendence.setBanji(result.getString("Banji"));
            attendence.setJieci(result.getString("Jieci"));
            attendence.setFlag(result.getString("flag"));
            attendence.setDate(result.getDate("attendencedate"));
            list.add(attendence);
        }
        result.close();
        parameter.close();
        conn.close();
        return list;
    }
    //创建窗口，以列表展示从数据库中获取的数据
    public static void showFrame(List<Attendence> list) throws IOException {
        //1，设定窗口
        JFrame frame = new JFrame("考勤管理");
        frame.setResizable(false);//窗口大小固定
        frame.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(SystemColor.control);
        menuBar.setForeground(new Color(255, 255, 255));
        menuBar.setFont(new Font("等线", Font.PLAIN, 20));

        //2,添加table
        String[] index = {"序号", "学号", "姓名", "性别", "班级","课程","节次","出勤","时间"};
        Object[][] data = new Object[list.size()][index.length];
        //3,向data中添加数据
        for (int i = 0; i < list.size(); i++) {
            Attendence attendence = list.get(i);
            data[i][0] = attendence.getId();
            data[i][1] = attendence.getStuid();
            data[i][2] = attendence.getName();
            data[i][3] = attendence.getSex();
            data[i][4] = attendence.getClassid();
            data[i][5] = attendence.getBanji();
            data[i][6] = attendence.getJieci();
            data[i][7] = attendence.getFlag() ;
            data[i][8] = attendence.getDate();
        }
        //4,创建一个默认的表格模型
        DefaultTableModel defaultModel = new DefaultTableModel(data, index);
        table = new JTable(defaultModel);
        table.setBackground(new Color(245, 245, 245));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(25);
        dtm = (DefaultTableModel) table.getModel();

        //5，给表格设置滚动条
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(30, 5, 740, 320);
        jScrollPane.getViewport().setBackground(new Color(240, 240, 240));
        jScrollPane.setPreferredSize(new Dimension(700, 300));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //添加labe
        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("kaoqBg.png"))));
        bgPanel.setLayout(null);
        bgPanel.setBounds(0, 100, 800, 400);
        //修改panel的布局管理器
        JPanel panel = new JPanel(null);
        panel.setBackground(GRAY);
        panel.setBounds(0, 0, 800, 50);
        frame.add(bgPanel);
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240));
        panel.setPreferredSize(new Dimension(700, 50));
        //6，添加表格、滚动条到容器中
        frame.setJMenuBar(menuBar);
        JMenu mnNewMenu_1 = new JMenu(" 考勤记录");
        mnNewMenu_1.setIcon(new ImageIcon(PathUtils.getRealPath("学生.png")));
        mnNewMenu_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        mnNewMenu_1.setBackground(new Color(255, 255, 255));
        mnNewMenu_1.setHorizontalAlignment(SwingConstants.CENTER);
        menuBar.add(mnNewMenu_1);

        JMenuItem mntmNewMenuItem_4 = new JMenuItem("导出考勤记录表");
        mntmNewMenuItem_4.setHorizontalAlignment(SwingConstants.CENTER);
        mntmNewMenuItem_4.setIcon(new ImageIcon(PathUtils.getRealPath("新增.png")));
        mntmNewMenuItem_4.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mntmNewMenuItem_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date());
                fileName += ".xlsx"; // 文件名为当天日期加上.xlsx后缀
                File defaultFile = new File(fileChooser.getCurrentDirectory(), fileName);
                fileChooser.setSelectedFile(defaultFile);
                int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try {
                        XSSFWorkbook workbook = new XSSFWorkbook();
                        XSSFSheet sheet = workbook.createSheet("学生信息表");
                        XSSFRow headerRow = sheet.createRow(0);
                        headerRow.createCell(0).setCellValue("序号");
                        headerRow.createCell(1).setCellValue("学号");
                        headerRow.createCell(2).setCellValue("姓名");
                        headerRow.createCell(3).setCellValue("性别");
                        headerRow.createCell(4).setCellValue("班级");
                        headerRow.createCell(5).setCellValue("课程");
                        headerRow.createCell(6).setCellValue("节次");
                        headerRow.createCell(7).setCellValue("考勤");
                        headerRow.createCell(8).setCellValue("日期");
                        for (int i = 0; i < list.size(); i++) {
                            Attendence attendence = list.get(i);
                            XSSFRow row = sheet.createRow(i + 1);
                            Date date = attendence.getDate();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                            String day = sdf.format(date);
                            row.createCell(0).setCellValue(attendence.getId());
                            row.createCell(1).setCellValue(attendence.getStuid());
                            row.createCell(2).setCellValue(attendence.getName());
                            row.createCell(3).setCellValue(attendence.getSex());
                            row.createCell(4).setCellValue(attendence.getClassid());
                            row.createCell(5).setCellValue(attendence.getBanji());
                            row.createCell(6).setCellValue(attendence.getJieci());
                            row.createCell(7).setCellValue(attendence.getFlag());
                            row.createCell(8).setCellValue(day);
                        }
                        FileOutputStream outputStream = new FileOutputStream(fileToSave);
                        workbook.write(outputStream);
                        workbook.close();
                        JOptionPane.showMessageDialog(frame, "导出成功！");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "导出失败：" + ex.getMessage());
                    }
                }
            }
        });
        mntmNewMenuItem_4.setBackground(new Color(255, 255, 255));
        mnNewMenu_1.add(mntmNewMenuItem_4);

        JMenuItem mntmNewMenuItem_3 = new JMenuItem(" 考勤明细");
        mntmNewMenuItem_3.setIcon(new ImageIcon(PathUtils.getRealPath("列表.png")));
        mntmNewMenuItem_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mntmNewMenuItem_3.setBackground(new Color(255, 255, 255));
        mntmNewMenuItem_3.setHorizontalAlignment(SwingConstants.CENTER);
        mntmNewMenuItem_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bgPanel.setVisible(false);
                frame.setLayout(null);
                JButton btnNewButton_2 = new JButton("查询");
                btnNewButton_2.setBounds(460, 370, 81, 21);
                btnNewButton_2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String studentName = stuName.getText();
                        String studentClass = stuClassComb.getSelectedItem().toString();
                        String banji = kcombox.getSelectedItem().toString();
                        String jieci = jieciBox.getSelectedItem().toString();
                        System.out.println(studentClass);
                        System.out.println(banji);
                        System.out.println(jieci);
                        Date date  = datePicker.getDate();
                        if (StringUtil.isEmpty(studentName) && banji.equals("全匹配")&& studentClass.equals("全匹配")&& jieci.equals("全匹配")&& date==null) {
                            queryAllAttendence();
                            return;
                        }
                        Attendence attendence = new Attendence();
                        attendence.setName(studentName);
                        attendence.setClassid(studentClass);
                        attendence.setBanji(banji);
                        attendence.setJieci(jieci);
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            if (date==null){
                                String day = "1999-1-1";
                                Date parse = sdf.parse(day);
                                attendence.setDate(parse);
                            }else {
                                String dayTime = sdf.format(date);
                                attendence.setDate(sdf.parse(dayTime));
                            }
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        dtm.setRowCount(0);
                        try {
                            attendenceList = JDBCUtils.querySomeAttenceByInfo(attendence);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        for(Attendence attendence_1 : attendenceList) {
                            Vector v = new Vector();
                            v.add(attendence_1.getId());
                            v.add(attendence_1.getStuid());
                            v.add(attendence_1.getName());
                            v.add(attendence_1.getSex());
                            v.add(attendence_1.getClassid());
                            v.add(attendence_1.getBanji());
                            v.add(attendence_1.getJieci());
                            v.add(attendence_1.getFlag() );
                            v.add(attendence_1.getDate());
                            dtm.addRow(v);
                        }
                    }
                });
                frame.add(btnNewButton_2);
                JButton btnNewButton_2_1 = new JButton("返回");
                btnNewButton_2_1.setBounds(560, 370, 81, 21);
                btnNewButton_2_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            new Teacher_inter().init();
                            frame.dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                frame.add(btnNewButton_2_1);
                stuName = new JTextField();
                stuName.setBounds(140, 340, 99, 21);
                frame.add(stuName);
                stuName.setColumns(10);

                JLabel jieciLabel_1 = new JLabel("节  次");
                jieciLabel_1.setFont(new Font("等线", Font.PLAIN, 14));
                jieciLabel_1.setBounds(270, 340, 68, 23);
                frame.add(jieciLabel_1);

                String[] str ={"全匹配","1-2节","3-4节","5-6节","7-8节","9-10节"};
                jieciBox = new JComboBox<>(str);
                jieciBox.setBounds(320, 340, 99, 21);
                frame.add(jieciBox);

                JLabel dateLabel = new JLabel("日  期");
                dateLabel.setFont(new Font("等线", Font.PLAIN, 14));
                dateLabel.setBounds(460, 340, 68, 23);
                frame.add(dateLabel);

                datePicker = new JXDatePicker();
                datePicker.getEditor().setFont(new Font("微软雅黑", Font.PLAIN, 13));
                datePicker.setBounds(510, 340, 109, 21);
                frame.add(datePicker);

                JLabel courseLabel_1 = new JLabel("课 程");
                courseLabel_1.setFont(new Font("等线", Font.PLAIN, 14));
                courseLabel_1.setBounds(270, 370, 68, 23);
                frame.add(courseLabel_1);

                kcombox = new JComboBox<>();
                kcombox.setBounds(320, 370, 99, 21);
                ArrayList<Course_info> courseList;
                try {
                    courseList = CourseDao.getDbData();
                    Course_info[] courseBeans = new Course_info[courseList.size()+1];
                    courseBeans[0] = new Course_info(-1, "全匹配");
                    for (int i = 0; i < courseList.size(); i++) {
                        courseBeans[i+1] = courseList.get(i);
                    }
                    kcombox.setModel(new DefaultComboBoxModel<Course_info>(courseBeans));
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                frame.add(kcombox);

                JLabel classLabel_1 = new JLabel("班       级");
                classLabel_1.setFont(new Font("等线", Font.PLAIN, 14));
                classLabel_1.setBounds(40, 370, 68, 23);
                frame.add(classLabel_1);

                stuClassComb = new JComboBox();
                stuClassComb.setBounds(140, 370, 99, 21);
                classList = classService.queryAllClass();
                ClassBean[] classBeans = new ClassBean[classList.size() + 1];
                classBeans[0] = new ClassBean(-1, "全匹配");
                for (int i = 0; i < classList.size(); i++) {
                    classBeans[i + 1] = classList.get(i);
                }
                stuClassComb.setModel(new DefaultComboBoxModel<ClassBean>(classBeans));
                frame.add(stuClassComb);

                JLabel lblNewLabel = new JLabel("学生姓名");
                lblNewLabel.setFont(new Font("等线", Font.PLAIN, 14));
                lblNewLabel.setBounds(40, 340, 68, 23);
                frame.add(lblNewLabel);

                frame.add(jScrollPane);
                frame.setVisible(true);
                try {
                    defaultModel.setRowCount(0); // Clear the table
                    List<Attendence> list = getDbData(); // Retrieve data again
                    for (int i = 0; i < list.size(); i++) {
                        Attendence attendence = list.get(i);
                        Object[] rowData = {attendence.getId(), attendence.getStuid(),attendence.getName(), attendence.getSex(), attendence.getClassid(), attendence.getBanji(),attendence.getJieci(),attendence.getFlag(),attendence.getDate()};
                        defaultModel.addRow(rowData); // Add new data to the table
                    }
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        mnNewMenu_1.add(mntmNewMenuItem_3);

        frame.setVisible(true);
    }

    private static void queryAllAttendence() {
        //展示表中原有信息
        dtm.setRowCount(0);
        ArrayList<Attendence> attList = null;
        try {
            attList = JDBCUtils.getAllAttendence();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Attendence att : attList) {
            Vector v = new Vector();
            v.add(att.getId());
            v.add(att.getStuid());
            v.add(att.getName());
            v.add(att.getSex());
            v.add(att.getClassid());
            v.add(att.getBanji());
            v.add(att.getJieci());
            v.add(att.getFlag());
            v.add(att.getDate());
            dtm.addRow(v);
        }
    }

    private static void queryStudentByNameAndClass(String stuName, String className) {
        //展示表中原有信息
        dtm.setRowCount(0);
        ArrayList<Student> stuList = null;
        try {
            stuList = JDBCUtils.queryStuByNameAndClass(stuName, className);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        for (Student stu : stuList) {
            Vector v = new Vector();
            v.add(stu.getId());
            v.add(stu.getName());
            v.add(stu.getSex());
            v.add(stu.getClassid());
            v.add(stu.getStuid());
            dtm.addRow(v);
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        showFrame(getDbData());
    }
}
