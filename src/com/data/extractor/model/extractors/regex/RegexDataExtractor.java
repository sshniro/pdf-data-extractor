package com.data.extractor.model.extractors.regex;

public class RegexDataExtractor {

    public String extract(String rawText , String startTag , String endTag){
        String[] splits;
        try {
            splits = rawText.split(startTag ,2);
            try {
                splits = splits[1].split(endTag,2);
            }catch (ArrayIndexOutOfBoundsException e){
                return null;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
        return splits[0];
    }
}
