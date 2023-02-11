package com.project.demo.utils;

public class Lastcharremover {
    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }
    
    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }
}
