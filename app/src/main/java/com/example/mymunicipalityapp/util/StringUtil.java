package com.example.mymunicipalityapp.util;



public class StringUtil {


    public static boolean isEmptyString(String str){

        if(str.isEmpty())
            return true;

        return str.replace(" " , "").length() == 0;
    }


}
