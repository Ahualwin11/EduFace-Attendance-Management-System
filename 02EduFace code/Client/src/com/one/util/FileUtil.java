package com.one.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {
    private static final String P_PATH="D:\\opencv-demo\\face\\";
    private static final String MYSQL_PATH ="C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\";
    public static String getFacePath(String name){
        return P_PATH+name;
    }

    public static void fileCopy(String oldpath,String newpath){
        try {

            //封装数据源
            FileInputStream fis = new FileInputStream(oldpath);
            //封装目的地
            FileOutputStream fop = new FileOutputStream(newpath);
            //复制数据
            int by = 0;
            while ((by = fis.read())!= -1){
                fop.write(by);
            }
            fis.close();
            fop.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
