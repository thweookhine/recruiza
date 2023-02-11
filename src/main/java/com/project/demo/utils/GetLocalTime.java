package com.project.demo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetLocalTime {
    public static String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now).toString();
	} 
}
