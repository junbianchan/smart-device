package co.darma.common;

import java.util.Date;

public class DateConverter {
	public static Date fromLongToDate(Long dateInLong) {
		return new Date(dateInLong);
	}

	public static Long fromDateToLong(Date date) {
		return date.getTime();
	}

	public static java.sql.Timestamp toSqlDate(Date date) {
		return new java.sql.Timestamp(date.getTime());
	}
}
