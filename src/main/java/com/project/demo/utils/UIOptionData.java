package com.project.demo.utils;

import java.util.ArrayList;

public class UIOptionData {
    // Sample code
    public static ArrayList<String> generateCourseTime(){
        ArrayList<String> timeForClass = new ArrayList<>();
        timeForClass.add("9-12");
        timeForClass.add("1-4");
        timeForClass.add("3-6");
        return timeForClass;
    }
    public static ArrayList<String> generateResourceType(){
    	ArrayList<String> resourceType = new ArrayList<>();
    	resourceType.add("Agency");
    	resourceType.add("University");
    	resourceType.add("DirectRecruit");
    	resourceType.add("Training Center");
    	resourceType.add("Others");
        return resourceType;

    }
}
