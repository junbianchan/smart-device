package co.darma.common.utils;

import java.util.Date;

public class DateConverter {
	public static Date fromLongToDate(Long dateInLong) {
		return new Date(dateInLong);
	}
	
	public static Long fromDateToLong(Date date) {
		return date.getTime();
	}
}
