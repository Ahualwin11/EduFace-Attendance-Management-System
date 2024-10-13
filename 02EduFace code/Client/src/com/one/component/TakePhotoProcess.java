package com.one.component;

import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

public class TakePhotoProcess extends Thread {
    private static Logger logger = Logger.getLogger(TakePhotoProcess.class);

    private String imgPath;
    private Mat faceMat;
    private final static Scalar color = new Scalar(0, 0, 255);
    /*private static String picture = null;
    private boolean flag = true;
    private int count = 0;*/

    public TakePhotoProcess(String imgPath, Mat faceMat) {
        this.imgPath = imgPath;
        this.faceMat = faceMat;
    }

    public void run() {
        try {
            long currentTime = System.currentTimeMillis();
            StringBuffer samplePath = new StringBuffer();
            samplePath.append(imgPath).append(currentTime).append(".jpg");
            boolean flag = Imgcodecs.imwrite(samplePath.toString(), faceMat);
            logger.info(">>>>>>write image into->" + samplePath.toString());


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
