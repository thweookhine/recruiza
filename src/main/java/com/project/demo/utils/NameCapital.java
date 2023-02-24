package com.project.demo.utils;

public class NameCapital {
	
    public static String capitalizeFirstLetter(String text) {

        StringBuilder str = new StringBuilder();

        String[] tokens = text.split("\\s");// Can be space,comma or hyphen

        for (String token : tokens) {
            str.append(Character.toUpperCase(token.charAt(0))).append(token.substring(1)).append(" ");
        }
        
        str.toString().trim();// Trim trailing space
        
        return str.toString();

    }
    
    public static void main(String args []) {
    	System.out.println(capitalizeFirstLetter("java se develover"));
    }
}
