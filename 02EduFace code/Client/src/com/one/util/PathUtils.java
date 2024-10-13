package com.one.util;

public class PathUtils {

    private static final String P_PATH="Client\\images\\";
    private static final String I_PATH="C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\";

    public static String getRealPath(String relativePath){

        return P_PATH+relativePath;
    }

    public static String getImgPath(String name){
        return I_PATH+name;
    }

}
