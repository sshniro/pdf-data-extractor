package com.data.extractor.model.template.markup;

import com.data.extractor.model.beans.template.info.pattern.FormPairData;

import java.util.ArrayList;
import java.util.List;

public class PatternDataExtractor {

//    public static void main(String[] args) {
//
//        String extracted = "Size: L.L Qty Ordered: 93 Cost per Unit: $ 10.00";
//        String[] split = splitWordsByColon(extracted);
//        int arrayLength = split.length;
//
//        List<FormPairData> pairs = new ArrayList<FormPairData>();
//
//        for (int i=0;i<arrayLength;i++){
//            FormPairData pairData = new FormPairData();
//            /* If it is a last attribute */
//            if(i == arrayLength-1){
//                break;
//            }
//            if(i == arrayLength-2){
//                split[i+1] = "eol";
//            }
//            split[i] = split[i] + ":";
//            pairData.setStart(split[i]);
//            pairData.setEnd(split[i+1]);
//            pairs.add(pairData);
//        }
//    }

    public List<FormPairData> processRequest(String extracted){
        String[] split = splitWordsByColon(extracted);
        int arrayLength = split.length;

        List<FormPairData> pairs = new ArrayList<FormPairData>();

        for (int i=0;i<arrayLength;i++){
            FormPairData pairData = new FormPairData();
            /* If it is a last attribute */
            if(i == arrayLength-1){
                break;
            }
            if(i == arrayLength-2){
                split[i+1] = "eol";
            }
            split[i] = split[i] + ":";
            pairData.setStart(split[i]);
            pairData.setEnd(split[i+1]);
            pairs.add(pairData);
        }
        return pairs;
    }

    public static String[] splitWordsByColon(String line){
        String[] split = line.split(":");
        return  split;
    }


}
