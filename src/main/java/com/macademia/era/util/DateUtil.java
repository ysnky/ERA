package com.macademia.era.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by ysnky on Jul 7, 2020
 *
 */

public class DateUtil {
	
	protected final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static Date stringToDate(String strDate) {
		return stringToDate(strDate, AppConstant.DATE_FORMAT);
	}
	
	public static Date stringToDate(String strDate, String dateFormat) {
		return stringToDate(strDate, dateFormat, null);
	}
	
	public static Date stringToDate(String strDate, String dateFormat, String local) {

		Date date = null;
		try {
			if (local == null) {
				date = new SimpleDateFormat(dateFormat).parse(strDate);
			} else {
				date = new SimpleDateFormat(dateFormat, new Locale(local)).parse(strDate);	
			}
			
//			date = new SimpleDateFormat(dateFormat).parse(strDate);
		} catch (ParseException e) {
			logger.error(GlobalUtil.exceptionToString(e));
		}
		return date;
	}
	
	
	public static LocalDate stringToDateOnlyDate(String strDate, String dateFormat, String local) {
		Date date = stringToDate(strDate, dateFormat, local);
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	

	
	

//	public static LocalDate backDate(String dayMinus) {
//		 return backDate(Integer.parseInt(dayMinus));	
//	}
//	
//	public static LocalDate backDate(int dayMinus) {
//		return LocalDate.now().minusDays(dayMinus);	
//	}
	
	public static Date addDate(int days) {
		LocalDate localDate = LocalDate.now().plusDays(days);	
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	
	
	public static String dateToString(Date date, String dateFormat) {

		String str = null;
		try {
			str = new SimpleDateFormat(dateFormat).format(date);
		} catch (Exception e) {
			logger.error(GlobalUtil.exceptionToString(e));
		}
		return str;
	}
	
	public static LocalDate stringToDateL(String strDate, String dateFormat) {
		return stringToDateL(strDate, dateFormat, null);
	}
	public static LocalDate stringToDateL(String strDate, String dateFormat, String local) {
		DateTimeFormatter formatter;
		if (local == null) {
			formatter = DateTimeFormatter.ofPattern(dateFormat);
		} else {
			formatter = DateTimeFormatter.ofPattern(dateFormat, new Locale(local));	
		}
		
		LocalDate date = LocalDate.parse(strDate, formatter);
		return date;
	}
	
	public static String dateToString(LocalDate date, String dateFormat) {
		
		String str = null;
		try {
//			str = new SimpleDateFormat(dateFormat).format(date);
			str = date.format(DateTimeFormatter.ofPattern(dateFormat));
		} catch (Exception e) {
			logger.error(GlobalUtil.exceptionToString(e));
		}
		return str;
	}
	
	public static String dateToString(Date date) {

		String str = null;
		try {
			str = new SimpleDateFormat(AppConstant.DATE_FORMAT_FULL).format(date);
		} catch (Exception e) {
			logger.error(GlobalUtil.exceptionToString(e));
		}
		return str;
	}
	
	
	public static Date onlyDate(Date startTime, int back) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(startTime); 
		if (back != 0) {
			c.add(Calendar.DATE, back);
		}
		c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);

	    return c.getTime();		
	}
	
	public static Date onlyDate(Date startTime) {
		return onlyDate(startTime, 0);
	}
}
