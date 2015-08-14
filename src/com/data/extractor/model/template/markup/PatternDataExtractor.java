package com.data.extractor.model.template.markup;

public class PatternDataExtractor {

    public static void main(String[] args) {

        String extracted = "Size : 10 Column : 30 Price : 70 Test : 100";
        splitWordsByColon(extracted);
        System.out.println(extracted);
    }

    public static String splitWordsByColon(String line){

        String[] split = line.split(":");


        return  line;
    }

}
