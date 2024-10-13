package com.one.component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.one.dao.StudentDao_1;
import com.one.bean.Attendence;
import com.one.bean.Student;
import com.one.view.RegisterInterface_student;
import com.one.view.Teacher_inter;
import com.one.view.teacher_sent;
import com.one.util.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class FaceRecognize extends JFrame {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    }

    private static String USERNAME = RegisterInterface_student.USERNAME;

    private static final String cascadeFileFullPath = "D:\\opencv\\opencv\\sources\\data\\lbpcascades\\lbpcascade_frontalface.xml";
    private static final String photoPath = "D:\\opencv-demo\\face\\" + USERNAME + "\\";
    private JPanel contentPane;
    public static VideoPanel videoCamera = new VideoPanel();
    private static final Size faceSize = new Size(165, 200);
    private static VideoCapture capture = new VideoCapture();
    private static boolean trainSwitch = false;//train按钮为on/off设计
    private static boolean identifySwitch = false;
    private static String time_2;
    private static HashMap<String, Integer> hm = new HashMap<String, Integer>();
    public static JTable table = null;
    public static DefaultTableModel dtm = null;
    public static ArrayList<String> nameList;
    public static JLabel cntLabel;
    public static JPanel cameraGroup1;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        FaceRecognize frame = new FaceRecognize();
        frame.setVisible(true);
        frame.invokeCamera(frame, videoCamera);
    }
    public void invokeCamera(JFrame frame, VideoPanel videoPanel) {//开启摄像头
        new Thread() {
            public void run() {
                CascadeClassifier faceCascade = new CascadeClassifier();
                faceCascade.load(cascadeFileFullPath);
                try {
                    capture.open(0);
                    Scalar color = new Scalar(0, 255, 0);//BGR
                    MatOfRect faces = new MatOfRect();
                    // Mat faceFrames = new Mat();
                    if (capture.isOpened()) {
                        System.out.println(">>>>>>video camera in working");
                        //logger.info(">>>>>>video camera in working");
                        Mat faceMat = new Mat();
                        while (true) {
                            capture.read(faceMat);
                            if (!faceMat.empty()) {
                                faceCascade.detectMultiScale(faceMat, faces);
                                Rect[] facesArray = faces.toArray();
                                if (facesArray.length >= 1) {
                                    for (int i = 0; i < facesArray.length; i++) {
                                        // 获取人脸矩形框的坐标和尺寸
                                        Rect rect = facesArray[i];
                                        int x = rect.x;
                                        int y = rect.y;
                                        int width = rect.width;
                                        int height = rect.height;
                                        // 计算圆弧的中心点和半径
                                        Point center = new Point(x + width / 2, y + height / 2);
                                        Size axes = new Size(width / 2, height / 2);
                                        // 定义圆弧的起始角度和结束角度
                                        // 定义圆弧的起始角度和结束角度
                                        double startAngle1 = 15 + System.currentTimeMillis() % 360;
                                        double endAngle1 = 75 + System.currentTimeMillis() % 360;
                                        double startAngle2 = 105 + System.currentTimeMillis() % 360;
                                        double endAngle2 = 165 + System.currentTimeMillis() % 360;
                                        double startAngle3 = 195 + System.currentTimeMillis() % 360;
                                        double endAngle3 = 255 + System.currentTimeMillis() % 360;
                                        double startAngle4 = 285 + System.currentTimeMillis() % 360;
                                        double endAngle4 = 345 + System.currentTimeMillis() % 360;
                                        // 绘制四个圆弧
                                        Imgproc.ellipse(faceMat, center, axes, 0, startAngle1, endAngle1, color, 2);
                                        Imgproc.ellipse(faceMat, center, axes, 0, startAngle2, endAngle2, color, 2);
                                        Imgproc.ellipse(faceMat, center, axes, 0, startAngle3, endAngle3, color, 2);
                                        Imgproc.ellipse(faceMat, center, axes, 0, startAngle4, endAngle4, color, 2);
                                        // 绘制四个圆弧
                                        Point start1 = new Point(center.x + axes.width * Math.cos(startAngle1 * Math.PI / 180.0), center.y + axes.height * Math.sin(startAngle1 * Math.PI / 180.0));
                                        Point end1 = new Point(center.x + (axes.width + 10) * Math.cos(startAngle1 * Math.PI / 180.0), center.y + (axes.height + 10) * Math.sin(startAngle1 * Math.PI / 180.0));
                                        Point start2 = new Point(center.x + axes.width * Math.cos(startAngle2 * Math.PI / 180.0), center.y + axes.height * Math.sin(startAngle2 * Math.PI / 180.0));
                                        Point end2 = new Point(center.x + (axes.width + 10) * Math.cos(startAngle2 * Math.PI / 180.0), center.y + (axes.height + 10) * Math.sin(startAngle2 * Math.PI / 180.0));
                                        Point start3 = new Point(center.x + axes.width * Math.cos(startAngle3 * Math.PI / 180.0), center.y + axes.height * Math.sin(startAngle3 * Math.PI / 180.0));
                                        Point end3 = new Point(center.x + (axes.width + 10) * Math.cos(startAngle3 * Math.PI / 180.0), center.y + (axes.height + 10) * Math.sin(startAngle3 * Math.PI / 180.0));
                                        Point start4 = new Point(center.x + axes.width * Math.cos(startAngle4 * Math.PI / 180.0), center.y + axes.height * Math.sin(startAngle4 * Math.PI / 180.0));
                                        Point end4 = new Point(center.x + (axes.width + 10) * Math.cos(startAngle4 * Math.PI / 180.0), center.y + (axes.height + 10) * Math.sin(startAngle4 * Math.PI / 180.0));
                                        Imgproc.line(faceMat, start1, end1, color, 2);
                                        Imgproc.line(faceMat, start2, end2, color, 2);
                                        Imgproc.line(faceMat, start3, end3, color, 2);
                                        Imgproc.line(faceMat, start4, end4, color, 2);
                                        videoPanel.setImageWithMat(faceMat);
                                        frame.repaint();
                                    }
                                }
                            } else {
                                break;
                            }
                            Thread.sleep(200);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }.start();
    }
    public static void release() {
        capture.release();//关闭摄像头
    }
    public static boolean isOpen() {
        if (capture.isOpened()) {
            return true;
        } else {
            return false;
        }
    }
    private static void setLabel(Mat im, String label, Point or, Scalar color) {
        int fontface = Core.FONT_HERSHEY_SIMPLEX;
        double scale = 0.8;
        int thickness = 2;
        int[] baseline = new int[1];
        Size text = Imgproc.getTextSize(label, fontface, scale, thickness, baseline);
        Imgproc.rectangle(im, new Point(or.x, or.y),
                new Point(or.x + text.width, or.y - text.height - baseline[0] - baseline[0]), color, Core.FILLED);
        Imgproc.putText(im, label, new Point(or.x, or.y - baseline[0]), fontface, scale, new Scalar(255, 255, 255),
                thickness);
    }

    public FaceRecognize() {
        File demo1 = new File("D:\\opencv-demo\\face");
        File demo2 = new File("D:\\opencv-demo\\model");
        if(!demo1.exists()){
            demo1.mkdir();
        }
        if(!demo2.exists()){
            demo2.mkdir();
        }
        File photoFile = new File(photoPath);
        if (!photoFile.exists()) {
            photoFile.mkdir();
        }
        //得到所有人的名字的键值对，值置为0
        StudentDao_1 stu = new StudentDao_1();
        ArrayList<Student> stulist = null;
        try {
            String sql = "select * from student_1 where classid = '"+teacher_sent.classid+"'";
            stulist = stu.getStudentByInfo(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Student s : stulist) {
            hm.put(s.getName(), 0);
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setBounds(0, 0, 1269, 715);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(new ImageIcon("D:\\code blocks\\idea java code\\Face_CheckIn_System\\Face_CheckIn_System\\Client\\images\\logo.png").getImage());//小图标

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel cameraGroup = new JPanel();
        cameraGroup.setBounds(40, 107, 756, 506);
        contentPane.add(cameraGroup);
        cameraGroup.setLayout(null);

        cameraGroup1 = new JPanel();
        cameraGroup1.setBounds(820, 107, 400, 506);
        contentPane.add(cameraGroup1);
        cameraGroup1.setLayout(null);

        JLabel videoDescriptionLabel = new JLabel("请正视摄像头");
        videoDescriptionLabel.setFont(new Font("楷体", Font.CENTER_BASELINE, 20));
        videoDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        videoDescriptionLabel.setBounds(240, 5, 280, 55);
        cameraGroup.add(videoDescriptionLabel);

        JLabel videoDescriptionLabel1 = new JLabel("考勤表↓");
        videoDescriptionLabel1.setFont(new Font("楷体", Font.CENTER_BASELINE, 20));
        videoDescriptionLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        videoDescriptionLabel1.setBounds(20, 5, 280, 55);
        cameraGroup1.add(videoDescriptionLabel1);

        cntLabel = new JLabel("已出勤：0 未出勤："+hm.size());
        cntLabel.setBounds(10,450,280, 55);
        cameraGroup1.add(cntLabel);
        //2,添加table
        String[] index = {"序号", "姓名", "出勤"};
        ArrayList<Attendence> list = null;
        try {
            long time_1 = teacher_sent.longtime;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time_2 = sdf.format((Object) time_1);
            list = JDBCUtils.getAttendenceByTime(time_2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] data = new Object[list.size()][index.length];
        //3,向data中添加数据
        for (int i = 0; i < list.size(); i++) {
            Attendence attendence = list.get(i);
            data[i][0] = i+1;
            data[i][1] = attendence.getName();
            data[i][2] = attendence.getFlag() ;
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
        jScrollPane.setBounds(5, 70, 380, 380);
        jScrollPane.getViewport().setBackground(new Color(240, 240, 240));
        jScrollPane.setPreferredSize(new Dimension(700, 300));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cameraGroup1.add(jScrollPane);
        videoCamera.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        videoCamera.setBounds(50, 50, 640, 400);
        cameraGroup.add(videoCamera);

        ImageIcon icon = new ImageIcon("D:\\code blocks\\idea java code\\Face_CheckIn_System\\Face_CheckIn_System\\Client\\images\\face_shibie.png");
        JLabel jLabel = new JLabel(icon);
        JPanel jPanel = new JPanel();
        jPanel.add(jLabel);
        jPanel.setBounds(0, 0, 1269, 715);
        jPanel.setOpaque(true);
        add(jPanel);

        File file = new File("D:\\opencv-demo\\model");
        File[] fs = file.listFiles();
        FaceRecognizer[] face = new FaceRecognizer[fs.length];//文件夹下训练的所有人脸模型
        for (int i = 0; i < fs.length; i++) {
            face[i] = LBPHFaceRecognizer.create();//人脸识别类对象
            face[i].read(fs[i].getAbsolutePath());
        }

        JButton backButton = new JButton("返回");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identifySwitch = false;
                release();
                try {
                    release();
                    new Teacher_inter().init();
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton identifyButton = new JButton("识别ON/OFF");
        identifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (identifySwitch) {
                    identifySwitch = false;
                } else {
                    identifySwitch = true;
                }
                CascadeClassifier faceCascade = new CascadeClassifier();
                faceCascade.load(cascadeFileFullPath);//加载lbpcascade_frontalface.xml文件
                new Thread() {
                    public void run() {
                        String personName = "unknown";
                        try {
                            if (capture.isOpened()) {
                                Mat faceMat = new Mat();//储图像信息的内存对象，可以理解为一个包含所有强度值的像素点矩阵
                                while (identifySwitch) {
                                    capture.read(faceMat);
                                    if (!faceMat.empty()) {
                                        MatOfRect faces = new MatOfRect();
                                        Mat grayFrame = new Mat();
                                        // 灰度处理
                                        Imgproc.cvtColor(faceMat, grayFrame, Imgproc.COLOR_BGR2GRAY);
                                        faceCascade.detectMultiScale(grayFrame, faces);
                                        Rect[] facesArray = faces.toArray();//所有的脸
                                        Scalar color = new Scalar(255, 0, 0);
                                        for (int i = 0; i < facesArray.length; i++) {
                                            int flag = 0;
                                            for (int j = 0; j < face.length; j++) {
                                                int[] predictedLabel = new int[1];//用来打印姓名的
                                                double[] confidence = new double[1];//可信度
                                                Mat faceROI = new Mat(grayFrame, facesArray[i]);
                                                Mat trainFace = new Mat();
                                                Imgproc.resize(faceROI, trainFace, faceSize);
                                                face[j].predict(trainFace, predictedLabel, confidence);
                                                if (confidence[0] < 50) {
                                                    personName = face[j].getLabelInfo(predictedLabel[0]);
                                                  // System.out.println(personName);
                                                    Integer value = hm.get(personName);
                                                    hm.put(personName, ++value);
                                                    //写数据库
                                                    if (value == 1) {
                                                       long longtime = teacher_sent.longtime;
                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                        String time = sdf.format((Object) longtime);
                                                        System.out.println(time);

                                                        String sql = "UPDATE kaoqing SET flag='出勤' WHERE name = '" + personName+ "' and attendencedate = '" + time + "'";
                                                        long new_time_1 = System.currentTimeMillis();
                                                        String time_1 = sdf.format((Object) new_time_1);
                                                        JDBCUtils.Update(sql);
                                                        updateTable_1();
                                                      //  System.out.println(personName);
                                                    }
                                                    setLabel(faceMat, personName, facesArray[i].tl(), color);
                                                    videoCamera.setImageWithMat(faceMat);
                                                    flag = 1;
                                                }
                                                if (flag == 1) break;
                                            }
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        identifyButton.setBounds(225, 460, 100, 30);
        backButton.setBounds(365, 460, 100, 30);
        cameraGroup.add(identifyButton);
        cameraGroup.add(backButton);
    }
    private static void updateTable_1() {
        //展示表中原有信息
        dtm.setRowCount(0);
        int i = 0 ;
        nameList = new ArrayList<>();
        for (String s : hm.keySet()) {
            Integer value = hm.get(s);
             if (value>=1){
                 nameList.add(s);
                 i++;
             }
        }
        for (String s : hm.keySet()) {
            Integer value = hm.get(s);
            if (value==0){
                nameList.add(s);
            }
        }
        for(int j = 0; j < nameList.size(); j++){
            Vector v = new Vector();
            v.add(j+1);
            String name = nameList.get(j);
            v.add(name);
            if(hm.get(name) == 0){
                v.add("缺勤");
                dtm.addRow(v);
            }else{
                v.add("出勤");
                dtm.addRow(v);
                table.setRowSelectionInterval(j, j);
                table.setSelectionForeground(Color.BLUE);
            }
        }
        String s1 = "已出勤：" + i+" 未出勤：";
        String s2 = s1 + "" + (nameList.size() - i);
        cntLabel.setVisible(false);
        cntLabel = new JLabel(s2);
        cntLabel.setBounds(10,450,280, 55);
        cameraGroup1.add(cntLabel);
        }
    }


