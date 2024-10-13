package com.one.util;

import javax.annotation.processing.SupportedOptions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public  static  boolean isEmpty(String str){
        if("".equals(str) || str == null){
            return true;
        }
        return false;
    }
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /*
     * 进行字符串拼接，只针对的是sql语句，这个是特别定制的
     */
    public static StringBuffer splicingStrs(int len ,StringBuffer strBuf, String...strings) {
        for(int i = 0; i < len; i++) {
            if(i != len-1)
                strBuf.append(strings[i] + " and ");
            else
                strBuf.append(strings[i]);
        }
        return strBuf;
    }
}
