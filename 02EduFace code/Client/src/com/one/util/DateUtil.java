package com.one.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @author 作者 Your-Name: 
* @version 创建时间：2021年5月24日 下午12:06:46 
* 类说明 
*/
public class DateUtil {
	public static String DateToSqlDate(Date utilDate) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String sqlDate = sdf.format(utilDate);
	    return sqlDate;
	}
}
