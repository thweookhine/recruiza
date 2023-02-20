package com.project.demo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class codeGenerator {

    static DateFormat dateFormatyear = new SimpleDateFormat("yyyy");
    static DateFormat dateFormatmonth = new SimpleDateFormat("MM");
    static DateFormat dateFormatday = new SimpleDateFormat("dd");
    static Date date = new Date();

    //generate Code for User
    public static String generateUserCode(long lastId){
        String result = dateFormatyear.format(date).substring(2,4) + dateFormatmonth.format(date) + dateFormatday.format(date);
  
        StringBuilder reverse = new StringBuilder();
        reverse.append(result);
        reverse.reverse();
        return "U" + reverse + String.format("%03d", lastId);
    }

    //generate Code for Applicants
    public static String generateApplicantCode(long lastId){
        String result = dateFormatyear.format(date).substring(2,4) + dateFormatmonth.format(date) + dateFormatday.format(date);
  
        StringBuilder reverse = new StringBuilder();
        reverse.append(result);
        reverse.reverse();
        return "A" + reverse + String.format("%03d", lastId);
    }

    //generate Code for Departments
    public static String generateDepartmentCode(long lastId){
        return "D" + String.format("%03d", lastId);
    }
    //generate Code for Teams
    public static String generateTeamCode(long lastId){
        return "T" + String.format("%03d", lastId);
    }
    //generate Code for Job positions
    public static String generateJobCode(long lastId){
        return "J" + String.format("%03d", lastId);
    }
    
    //generate Code for Job Post
    public static String generateJobPostCode(long lastId){
        return "JP" + String.format("%03d", lastId);
    }

  //generate Code for RecruitementResource
    public static String generateRecruitementResource(long lastId) {
    	return "R" + String.format("%03d", lastId);
    }
    
    public static void main(String[] args) {
        //Use like this
        System.out.println("User Code - "+generateUserCode(99));
        System.out.println("Applicant Code - "+generateApplicantCode(69));
        System.out.println("Department Code - "+generateDepartmentCode(3));
        System.out.println("Job Code - "+generateJobCode(10));
    }
}
