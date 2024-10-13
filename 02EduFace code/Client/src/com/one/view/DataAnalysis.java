package com.one.view;
import com.one.component.ClassAttendanceChart;
import com.one.component.JTableImg;
import com.one.component.databaseCompent;
import com.one.util.PathUtils;
import com.one.util.ScreenUtils;
import com.one.component.AttendanceChart;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

public class DataAnalysis {
    static JFrame jf = new  JFrame("e点课堂");
    final int WIDTH = 1269;
    final int HEIGHT = 713;
public static void main(String[] args) throws Exception {
    new DataAnalysis().init();
}
    //组装试图
    public void init() throws Exception{
        //给窗口设置属性
        jf.setLocationRelativeTo(null);
        jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));
        jf.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        jf.setResizable(false);//窗口大小固定
        //设置分割面板
        JSplitPane sp = new JSplitPane();
        //支持连续布局
        sp.setDividerLocation(150);
        sp.setDividerSize(7);
        //设置左侧内容
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("数据分析");
        DefaultMutableTreeNode allChart = new DefaultMutableTreeNode("总体出勤");
        DefaultMutableTreeNode classChart = new DefaultMutableTreeNode("班级出勤");
        DefaultMutableTreeNode personChart = new DefaultMutableTreeNode("个人出勤");
        DefaultMutableTreeNode record = new DefaultMutableTreeNode("识别记录");
        root.add(allChart);
        root.add(classChart);
        root.add(personChart);
        root.add(record);

        Color color = new Color(195,224,239);
        JTree tree = new JTree(root);
        MyRenderer myRenderer = new MyRenderer();
        myRenderer.setBackgroundNonSelectionColor(color);
        myRenderer.setBackgroundSelectionColor(new Color(116,184,218));
        tree.setCellRenderer(myRenderer);
        tree.setBackground(color);
        //设置当前tree默认选择总体出勤
        tree.setSelectionRow(1);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            //当条目选中变化后，这个方法会执行
            @Override
            public void valueChanged(TreeSelectionEvent e) {

                //得到当前选中的结点对象
                Object lastPathComponent = e.getNewLeadSelectionPath().getLastPathComponent();
                if (allChart.equals(lastPathComponent)){
                    //sp.setRightComponent(new JLabel("这里进行录入人脸..."));
                    sp.setRightComponent(new AttendanceChart());
                    sp.setDividerLocation(150);//分割条折叠大小
                }else if (classChart.equals(lastPathComponent)){
                    //这里进行人脸识别
                    sp.setRightComponent(new ClassAttendanceChart());
                    sp.setDividerLocation(150);
                }else if (personChart.equals(lastPathComponent)){
                    //这里进行数据库
                    sp.setRightComponent(new databaseCompent());
                    sp.setDividerLocation(150);
                }else{
                    //这里进行识别记录
                    sp.setRightComponent(new JTableImg());
                    sp.setDividerLocation(150);
                }
            }
        });

        sp.setRightComponent(new AttendanceChart());//默认是人脸录入界面
        sp.setLeftComponent(tree);
        jf.add(sp);
        jf.setVisible(true);
    }

    public class MyRenderer extends DefaultTreeCellRenderer {
        private ImageIcon rootIcon = null;
        private ImageIcon faceRegistIcon = null;
        private ImageIcon checkfaceIcon = null;
        private ImageIcon databaseIcon = null;
        private ImageIcon recordIcon = null;

        //自定义结点绘制器
        public MyRenderer(){
            rootIcon = new ImageIcon(PathUtils.getRealPath("rootIcon.png"));
            faceRegistIcon = new ImageIcon(PathUtils.getRealPath("faceRegistIcon.png"));
            checkfaceIcon = new ImageIcon(PathUtils.getRealPath("checkfaceIcon.png"));
            databaseIcon = new ImageIcon(PathUtils.getRealPath("databaseIcon.png"));
            recordIcon = new ImageIcon(PathUtils.getRealPath("recordIcon.png"));

        }
        //当绘制树的每个结点时，都会调用这个方法
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            //使用默认绘制
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            ImageIcon image = null;
            switch (row){
                case 0:
                    image = rootIcon;
                    break;
                case 1:
                    image = faceRegistIcon;
                    break;
                case 2:
                    image = checkfaceIcon;
                    break;
                case 3:
                    image = databaseIcon;
                    break;
                case 4:
                    image = recordIcon;
                    break;
            }
            this.setIcon(image);
            return this;
        }
    }

}
