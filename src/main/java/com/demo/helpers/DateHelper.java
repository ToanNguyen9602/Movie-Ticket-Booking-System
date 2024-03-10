package com.demo.helpers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.lang.NonNull;

public class DateHelper {
	
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd"; 
	
	public static List<Date> getDatesBetween(Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar) || calendar.equals(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        datesInRange.add(calendar.getTime());
        return datesInRange;
    }
	
	public static LocalDate mapFromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static String formatDate(@NonNull Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

}
