package com.one.component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.one.view.RegisterInterface_student;
import com.one.view.Student_Management;
import com.one.util.FileBean;
import com.one.util.ImageUtils;
import com.one.util.OpenCVUtil;
import com.one.util.PathUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceRegister extends JFrame {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    //开源日志工具Logger
    private static String USERNAME;
    private static String cascadeFileFullPath = "D:\\opencv\\opencv\\sources\\data\\lbpcascades\\lbpcascade_frontalface.xml";
    private static String photoPath;
    private static String modelFolder = "D:\\opencv-demo\\model";
    private static String modelPath = "D:\\opencv-demo\\model\\"+ USERNAME +"face_model.yml";
    private JPanel contentPane;
    public static VideoPanel videoCamera = new VideoPanel();
    private static  Size faceSize = new Size(165, 200);
    private static VideoCapture capture = new VideoCapture();
    private static List<Mat> images = new ArrayList<Mat>();
    private static FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
    private static CascadeClassifier faceCascade = new CascadeClassifier();
    private static boolean trainSwitch = false;
    private static boolean identifySwitch = false;
    public static boolean flag_face = false;
    public static void main(String[] args) {
        new FaceRegister();
    }
    public void invokeCamera(JFrame frame, VideoPanel videoPanel) {
        new Thread() {
            public void run() {
                CascadeClassifier faceCascade = new CascadeClassifier();//级联分类器
                faceCascade.load(cascadeFileFullPath);
                try {
                    capture.open(0);
                    Scalar color = new Scalar(0, 255, 0);
                    MatOfRect faces = new MatOfRect();
                    if (capture.isOpened()) {
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
                                        // 定义聚焦框的四个顶点
                                        Point topLeft = new Point(x, y);
                                        Point topRight = new Point(x+width, y);
                                        Point bottomLeft = new Point(x, y+height);
                                        Point bottomRight = new Point(x+width, y+height);
                                        // 定义线段的长度
                                        int lineLength = 20;
                                        // 定义线段的颜色和粗细
                                        Scalar lineColor = new Scalar(255, 255, 255);
                                        int thickness = 2;
                                        // 在每个顶点处绘制两条线段
                                        Imgproc.line(faceMat, topLeft, new Point(topLeft.x + lineLength, topLeft.y), lineColor, thickness);
                                        Imgproc.line(faceMat, topLeft, new Point(topLeft.x, topLeft.y + lineLength), lineColor, thickness);

                                        Imgproc.line(faceMat, topRight, new Point(topRight.x - lineLength, topRight.y), lineColor, thickness);
                                        Imgproc.line(faceMat, topRight, new Point(topRight.x, topRight.y + lineLength), lineColor, thickness);

                                        Imgproc.line(faceMat, bottomLeft, new Point(bottomLeft.x + lineLength, bottomLeft.y), lineColor, thickness);
                                        Imgproc.line(faceMat, bottomLeft, new Point(bottomLeft.x, bottomLeft.y - lineLength), lineColor, thickness);

                                        Imgproc.line(faceMat, bottomRight, new Point(bottomRight.x - lineLength, bottomRight.y), lineColor, thickness);
                                        Imgproc.line(faceMat, bottomRight, new Point(bottomRight.x, bottomRight.y - lineLength), lineColor, thickness);

                                        videoPanel.setImageWithMat(faceMat);
                                        frame.repaint();
                                    }
                                }
                            } else {
                                break;
                            }
                            Thread.sleep(50);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }.start();
    }
    public static void release(){
        capture.release();//关闭摄像头
    }
    public static boolean isOpen(){
        if(capture.isOpened()){
            return true;
        }else{
            return false;
        }
    }
    public FaceRegister() {
        if(RegisterInterface_student.USERNAME!= null && Student_Management.USERNAME != null){
            long time1 = RegisterInterface_student.HmName.get(RegisterInterface_student.USERNAME);
            long time2 = Student_Management.HmName.get(Student_Management.USERNAME);
            USERNAME = time1 > time2 ? RegisterInterface_student.USERNAME:Student_Management.USERNAME;
        }else{
            USERNAME = Student_Management.USERNAME==null ? RegisterInterface_student.USERNAME:Student_Management.USERNAME;
        }
        photoPath = "D:\\opencv-demo\\face\\"+ USERNAME +"\\";
        System.out.println(USERNAME +"1234");
        File photoFile = new File(photoPath);
        if (!photoFile.exists()) {
            photoFile.mkdir();
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0, 0, 1269, 715);//Jframe
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("D:\\code blocks\\idea java code\\Face_CheckIn_System\\Face_CheckIn_System\\Client\\images\\logo.png").getImage());//小图标

        contentPane = new JPanel();//内容面板
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel cameraGroup = new JPanel();//灰色1hao
        cameraGroup.setBounds(102,107,643,530);
        contentPane.add(cameraGroup);
        cameraGroup.setLayout(null);

        JPanel cameraGroup_1 = new JPanel();
        cameraGroup_1.setBounds(835,133,397,380);
        contentPane.add(cameraGroup_1);
        cameraGroup_1.setLayout(null);

        JProgressBar progressBar = new JProgressBar(0, 20);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        JLabel label = new JLabel("倒计时: 20", JLabel.CENTER);
        // 设置组件的位置和大小
        progressBar.setBounds(51, 459, 400, 30);
        label.setBounds(491, 459, 100, 30);

        Timer timer1 = new Timer(1000, new ActionListener() {
            int i = 20;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (i == -1) {
                    ((Timer) e.getSource()).stop();
                    JOptionPane.showMessageDialog(contentPane, "录入成功");
                    FaceRegister.release();
                    setVisible(false);
                }
                progressBar.setValue(20 - i);
                label.setText("倒计时: " + i);
                i--;
            }
        });
        cameraGroup.add(progressBar);
        cameraGroup.add(label);
        //提示语
        JLabel vdLabel = new JLabel("请正视摄像头");
        vdLabel.setFont(new Font("楷体", Font.CENTER_BASELINE, 35));
        vdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        vdLabel.setBounds(200,15,244,52);
        // 存储提示语的字符串数组
        String[] prompts = {"请直视摄像头", "点头", "向左摇头", "向右摇头", "张嘴", "眨眼"};
        // 当前显示的提示语的索引（存储在一个长度为1的整数数组中）
        final int[] currentPromptIndex = {0};

        cameraGroup.add(vdLabel);
        videoCamera.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        videoCamera.setBounds(51,69,540,380);
        cameraGroup.add(videoCamera);

        ImageIcon icon = new ImageIcon("D:\\code blocks\\idea java code\\Face_CheckIn_System\\Face_CheckIn_System\\Client\\images\\face_shibie.png");
        JLabel jLabel = new JLabel(icon);
        JPanel jPanel = new JPanel();
        jPanel.add(jLabel);
        jPanel.setBounds(0, 0, 1269, 715);
        jPanel.setOpaque(true);
        add(jPanel);

        VideoPanel videoPreview = new VideoPanel();//照片
        videoPreview.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        videoPreview.setBounds(50,82,294,188);//包围照片的框框
        cameraGroup_1.add(videoPreview);

        JLabel lblNewLabel = new JLabel("照片");
        lblNewLabel.setFont(new Font("楷体", Font.CENTER_BASELINE, 35));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(148,13,99,30);//照片二字
        cameraGroup_1.add(lblNewLabel);

        JPanel buttonGroup = new JPanel();
        buttonGroup.setBounds(10,300,310,60);
        contentPane.add(buttonGroup);
        buttonGroup.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton photoButton = new JButton("拍照");
        photoButton.setIcon(new ImageIcon(PathUtils.getRealPath("拍照.png")));
        photoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuffer photoPathStr = new StringBuffer();
                photoPathStr.append(photoPath);
                try {
                    if (capture.isOpened()) {
                        Mat myFace = new Mat();
                        while (true) {
                            capture.read(myFace);
                            if (!myFace.empty()) {
                                Image previewImg = ImageUtils.scale2(myFace, 188,293 , true);// 等比例缩放
                                TakePhotoProcess takePhoto = new TakePhotoProcess(photoPath.toString(), myFace);
                                takePhoto.start();// 照片写盘
                                videoPreview.SetImageWithImg(previewImg);// 在预览界面里显示等比例缩放的照片
                                videoPreview.repaint();// 让预览界面重新渲染
                                break;
                            }
                        }
                    }
                } catch (Exception ex) {
                }
            }
        });
        buttonGroup.add(photoButton);

        JButton trainButton = new JButton("录入");
        trainButton.setIcon(new ImageIcon(PathUtils.getRealPath("录入.png")));
        trainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!trainSwitch){
                        trainSwitch = true;
                    }
                    new Thread() {
                        public void run() {
                            try {
                                if (capture.isOpened()) {
                                    // 创建一个定时器，每隔3秒触发一次
                                    Timer timer = new Timer(3500, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            // 更新当前显示的提示语的索引
                                            currentPromptIndex[0] = (currentPromptIndex[0] + 1) % prompts.length;
                                            // 更改JLabel的文本
                                            vdLabel.setText(prompts[currentPromptIndex[0]]);
                                            // 如果已经遍历到最后一句提示语，则停止定时器
                                            if (currentPromptIndex[0] == prompts.length - 1) {
                                                ((Timer)e.getSource()).stop();
                                                if (trainSwitch) {
                                                    trainSwitch = false;
                                                    faceCascade.load(cascadeFileFullPath);
                                                    List<FileBean> trainSamples = OpenCVUtil.getPicFromFolder(photoPath);
                                                    Map<String, List<Integer>> labelOfPerson = new HashMap<String, List<Integer>>();
                                                    if (trainSamples != null && trainSamples.size() > 0) {
                                                        new Thread() {
                                                            private int index = 0;

                                                            public void run() {
                                                                for (FileBean sample : trainSamples) {
                                                                    if (sample.getFileType().equalsIgnoreCase("jpg")
                                                                            || sample.getFileType().equalsIgnoreCase("pgm")) {
                                                                        MatOfRect faces = new MatOfRect();
                                                                        Mat grayFrame = new Mat();
                                                                        Mat src = Imgcodecs.imread(sample.getFileFullPath());
                                                                        Imgproc.cvtColor(src, grayFrame, Imgproc.COLOR_BGR2GRAY);
                                                                        // 采集人脸
                                                                        faceCascade.detectMultiScale(grayFrame, faces);
                                                                        Rect[] facesArray = faces.toArray();
                                                                        // logger.info(">>>>>>facesArray.length->" + facesArray.length);
                                                                        if (facesArray.length >= 1) {
                                                                            for (int i = 0; i < facesArray.length; i++) {
                                                                                String labelInfo = sample.getFolderName();
                                                                                if (labelOfPerson.get(labelInfo) == null) {
                                                                                    index++;
                                                                                    List<Integer> ids = new ArrayList<Integer>();
                                                                                    ids.add(index);
                                                                                    labelOfPerson.put(labelInfo, ids);
                                                                                } else {
                                                                                    labelOfPerson.get(labelInfo).add(index);
                                                                                }
                                                                                faceRecognizer.setLabelInfo(index, labelInfo);
                                                                                Mat faceROI = new Mat(grayFrame, facesArray[i]);
                                                                                Mat trainFace = new Mat();
                                                                                Imgproc.resize(faceROI, trainFace, faceSize);
                                                                                images.add(trainFace);
                                                                                try {
                                                                                    Thread.sleep(20);
                                                                                } catch (Exception e) {
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                int[] labelsOfInt = new int[images.size()];
                                                                int i = 0;
                                                                for (String key : labelOfPerson.keySet()) {
                                                                    List<Integer> labelIdList = labelOfPerson.get(key);
                                                                    for (Integer labelId : labelIdList) {
                                                                        labelsOfInt[i] = labelId;
                                                                        i++;
                                                                    }
                                                                }
                                                                MatOfInt labels = new MatOfInt(labelsOfInt);
                                                                // 调用训练方法
                                                                faceRecognizer.train(images, labels);
                                                                // 输出持久化模型文件 训练一次后就可以一直调用
                                                                faceRecognizer.save(modelFolder + "\\" + USERNAME + "face_model.yml");
                                                            }
                                                        }.start();
                                                        //保存填写的信息
                                                    }
                                                } else {
                                                    trainSwitch = true;
                                                }
                                                flag_face = true;
                                            }
                                        }
                                    });
                                    // 启动定时器
                                    timer.start();
                                    timer1.start();
                                    Mat myFace = new Mat();
                                    while (trainSwitch) {
                                        capture.read(myFace);
                                        if (!myFace.empty()) {
                                            Image previewImg = ImageUtils.scale2(myFace, 165, 200, true);// 等比例缩放
                                            TakePhotoProcess takePhoto = new TakePhotoProcess(photoPath.toString(),
                                                    myFace);
                                            takePhoto.start();// 照片写盘
                                            Thread.sleep(60);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }.start();
                } catch (Exception ex) {
                }

            }
        });
        buttonGroup.add(trainButton);

        JButton backButton = new JButton("返回");
        backButton.setIcon(new ImageIcon(PathUtils.getRealPath("拍照.png")));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                release();
                try {
                    new RegisterInterface_student().init();
                    release();
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonGroup.add(backButton);

        cameraGroup_1.add(buttonGroup);
        setVisible(true);
        invokeCamera(this, this.videoCamera);
    }
}