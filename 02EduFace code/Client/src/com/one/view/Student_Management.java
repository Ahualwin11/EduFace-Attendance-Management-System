package com.one.view;

import com.one.bean.ClassBean;
import com.one.component.BackGroundPanel;
import com.one.component.FaceRegister;

import com.one.bean.Student;
import com.one.service.ClassService;
import com.one.domain.ClassServiceImpl;
import com.one.util.JDBCUtils;
import com.one.util.PathUtils;

import com.one.util.StringUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

import static java.awt.Color.*;

public class Student_Management {
    static public JComboBox stuClassComb;
    static public JTextField stuName;
    static public ArrayList<ClassBean> classList;
    static public ClassService classService = new ClassServiceImpl();
    public static DefaultTableModel dtm = null;
    public static String USERNAME;
    public static JTable table = null;
    public static HashMap<String,Long> HmName = new HashMap<>();

    //获取数据库中的数据并以list返回
    public static List<Student> getDbData() throws ClassNotFoundException, SQLException {
        //1,注册驱动信息
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2,获取连接对象
        String url = "jdbc:mysql://localhost:3306/itcast";
        Connection conn = DriverManager.getConnection(url, "root", "123456");
        String sql = "SELECT id,NAME,sex,stuid,classid FROM student_1;";
        //3,连接对象conn的方法prepareStatement获取SQL语句的预编译对象
        PreparedStatement parameter = conn.prepareStatement(sql);
        //4,执行sql
        ResultSet result = parameter.executeQuery();
        //返回的数据List
        List<Student> list = new ArrayList<>();
        while (result.next()) {
            Student student = new Student();
            student.setId(result.getInt("id"));
            student.setName(result.getString("NAME"));
            student.setSex(result.getString("sex"));
            student.setStuid(result.getString("stuid"));
            student.setClassid(result.getString("classid"));
            list.add(student);
        }
        result.close();
        parameter.close();
        conn.close();
        return list;
    }

    //创建窗口，以列表展示从数据库中获取的数据
    public static void showFrame(List<Student> list) throws IOException {

        //1，设定窗口
        JFrame frame = new JFrame("学生管理");
        frame.setResizable(false);//窗口大小固定
        frame.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));
        frame.setSize(800, 503);
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(SystemColor.control);
        menuBar.setForeground(new Color(255, 255, 255));
        menuBar.setFont(new Font("等线", Font.PLAIN, 20));

        //2,添加table
        String[] index = {"序号", "姓名", "性别", "班级", "学号"};
        Object[][] data = new Object[list.size()][index.length];
        //3,向data中添加数据
        for (int i = 0; i < list.size(); i++) {
            Student student = list.get(i);
            data[i][0] = student.getId();
            data[i][1] = student.getName();
            data[i][2] = student.getSex();
            data[i][3] = student.getClassid();
            data[i][4] = student.getStuid();
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
        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getRealPath("stuControl.png"))));
        bgPanel.setLayout(null);
        bgPanel.setBounds(0, 100, 800, 400);
        //通过panel组合button，label
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

        JMenu mnNewMenu_1 = new JMenu(" 学生管理");
        mnNewMenu_1.setIcon(new ImageIcon(PathUtils.getRealPath("学生.png")));
        mnNewMenu_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        mnNewMenu_1.setBackground(new Color(255, 255, 255));
        mnNewMenu_1.setHorizontalAlignment(SwingConstants.CENTER);
        menuBar.add(mnNewMenu_1);

        JMenuItem mntmNewMenuItem_2 = new JMenuItem("导入花名册");
        mntmNewMenuItem_2.setHorizontalAlignment(SwingConstants.CENTER);
        mntmNewMenuItem_2.setIcon(new ImageIcon(PathUtils.getRealPath("新增.png")));
        mntmNewMenuItem_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        mntmNewMenuItem_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File fileToImport = fileChooser.getSelectedFile();
                    try {
                        FileInputStream inputStream = new FileInputStream(fileToImport);
                        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                        XSSFSheet sheet = workbook.getSheetAt(0);
                        Iterator<Row> rowIterator = sheet.iterator();
                        // skip header row
                        if (rowIterator.hasNext()) {
                            rowIterator.next();
                        }
                        while (rowIterator.hasNext()) {
                            DataFormatter formatter = new DataFormatter();
                            Row row = rowIterator.next();

                            Cell cell0 = row.getCell(0);
                            String id = formatter.formatCellValue(cell0);

                            Cell cell1 = row.getCell(1);
                            String NAME = formatter.formatCellValue(cell1);

                            Cell cell2 = row.getCell(2);
                            String sex = formatter.formatCellValue(cell2);

                            Cell cell3 = row.getCell(3);
                            String classid = formatter.formatCellValue(cell3);

                            Cell cell4 = row.getCell(4);
                            String stuid = formatter.formatCellValue(cell4);

                            Student student = new Student();
                            student.setName(NAME);
                            student.setStuid(stuid);
                            if (!id.isEmpty()) {
                                student.setId(Integer.parseInt(id));
                            }
                            student.setSex(sex);
                            student.setClassid(classid);

                            if (insertIntoDatabase(student)) {
                            } else {
                                JOptionPane.showMessageDialog(frame, "已经存在相同的课程，不需要插入");
                            }
                        }
                        JOptionPane.showMessageDialog(frame, "导入成功！");
                        workbook.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "导入失败：" + ex.getMessage());
                    }
                }
            }
        });
        mntmNewMenuItem_2.setBackground(new Color(255, 255, 255));
        mnNewMenu_1.add(mntmNewMenuItem_2);

        JMenuItem mntmNewMenuItem_4 = new JMenuItem("导出花名册");
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
                        headerRow.createCell(1).setCellValue("姓名");
                        headerRow.createCell(2).setCellValue("性别");
                        headerRow.createCell(3).setCellValue("班级");
                        headerRow.createCell(4).setCellValue("学号");
                        for (int i = 0; i < list.size(); i++) {
                            Student student = list.get(i);
                            XSSFRow row = sheet.createRow(i + 1);
                            row.createCell(0).setCellValue(student.getId());
                            row.createCell(1).setCellValue(student.getName());
                            row.createCell(2).setCellValue(student.getSex());
                            row.createCell(3).setCellValue(student.getClassid());
                            row.createCell(4).setCellValue(student.getStuid());
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

        JMenuItem mntmNewMenuItem_3 = new JMenuItem(" 学生列表");
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
                btnNewButton_2.setBounds(260, 340, 81, 21);
                btnNewButton_2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String studentName = stuName.getText();
                        ClassBean studentClass = (ClassBean) stuClassComb.getSelectedItem();
                        Student studentBean = new Student();
                        String className = studentClass.getF_name();
                        studentBean.setName(studentName);
                        studentBean.setClassid(className);
                        if (StringUtil.isEmpty(studentName) && studentClass.getF_name().equals("全匹配")) {
                            queryAllStudent();
                            return;
                        }
                        if (!StringUtil.isEmpty(studentName) && studentClass.getF_name().equals("全匹配")) {
                            dtm.setRowCount(0);
                            try {
                                Student stu = JDBCUtils.queryOneAll(studentName);//通过姓名得到的学生
                                if (stu.getName() != null) {
                                    Vector v = new Vector();
                                    v.add(stu.getId());
                                    v.add(stu.getName());
                                    v.add(stu.getSex());
                                    v.add(stu.getClassid());
                                    v.add(stu.getStuid());
                                    dtm.addRow(v);
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            return;
                        }
                        if (!StringUtil.isEmpty(studentName) && !studentClass.getF_name().equals("全匹配")) {
                            queryStudentByNameAndClass(studentName, className);
                            return;
                        }
                        if (StringUtil.isEmpty(studentName) && !studentClass.getF_name().equals("全匹配")) {
                            queryStudentByClass(className);
                        }
                    }
                });
                frame.add(btnNewButton_2);
                JButton btnNewButton_2_1 = new JButton("返回");
                btnNewButton_2_1.setBounds(260, 370, 81, 21);
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

                JButton btnNewButton_1_1_1 = new JButton("人脸录入");
                btnNewButton_1_1_1.setBounds(360, 340, 100, 21);
                btnNewButton_1_1_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int row = table.getSelectedRow();
                        if (row != -1) {
                            String name = (String) table.getValueAt(row, 1);
                            USERNAME = name;
                            HmName.put(USERNAME,System.currentTimeMillis());
                            new FaceRegister();
                            System.out.println("姓名: " + name);
                        }
                    }
                });
                frame.add(btnNewButton_1_1_1);
                JButton btnNewButton_3_1 = new JButton("查看学生详细信息");
                btnNewButton_3_1.setBounds(360, 370, 190, 21);
                btnNewButton_3_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println();
                    }
                });

                stuName = new JTextField();
                stuName.setBounds(140, 340, 99, 21);
                frame.add(stuName);
                stuName.setColumns(10);

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

                JLabel lblNewLabel_1 = new JLabel("班       级");
                lblNewLabel_1.setFont(new Font("等线", Font.PLAIN, 14));
                lblNewLabel_1.setBounds(40, 370, 68, 23);
                frame.add(lblNewLabel_1);

                frame.add(jScrollPane);
                frame.setVisible(true);
                try {
                    defaultModel.setRowCount(0); // Clear the table
                    List<Student> list = getDbData(); // Retrieve data again
                    for (int i = 0; i < list.size(); i++) {
                        Student student = list.get(i);
                        Object[] rowData = {student.getId(), student.getName(), student.getSex(), student.getClassid(), student.getStuid()};
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

    private static void queryAllStudent() {
        //展示表中原有信息
        dtm.setRowCount(0);
        ArrayList<Student> stuList = null;
        try {
            stuList = JDBCUtils.queryAllStudent();
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

    private static void queryStudentByClass(String className) {
        //展示表中原有信息
        dtm.setRowCount(0);
        ArrayList<Student> stuList = null;
        try {
            stuList = JDBCUtils.queryByClass(className);
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

    private static boolean checkIfCourseExists(Student student) {
        String query = "SELECT COUNT(*) FROM student_1 WHERE id=? AND NAME=? AND sex=? AND classid=? AND stuid=? ";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root", "123456");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // 设置参数
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getSex());
            preparedStatement.setString(4, student.getClassid());
            preparedStatement.setString(5, student.getStuid());
            // 执行查询操作
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            // 关闭资源
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return count > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL错误：" + e.getMessage());
            return false;
        }
    }

    private static boolean insertIntoDatabase(Student student) {
        if (checkIfCourseExists(student) == true) {
            return false;
        }
        String query = "INSERT INTO student_1 (id,NAME, sex, classid, stuid) VALUES (?,?, ?, ?, ?) ";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/itcast", "root", "123456");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // 设置参数
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getSex());
            preparedStatement.setString(4, student.getClassid());
            preparedStatement.setString(5, student.getStuid());
            // 执行插入操作
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL错误：" + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        showFrame(getDbData());
    }
}
