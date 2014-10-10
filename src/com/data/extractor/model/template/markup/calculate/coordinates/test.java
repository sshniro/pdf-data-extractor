package com.data.extractor.model.template.markup.calculate.coordinates;

/**
 * Created by niro273 on 9/1/14.
 */
public class test {


public static void main(String args[]){


    String str = "/home/test/uploads/PdfExtractor/" +
            "out/artifacts/PdfExtractor_war_exploded/uploads" +
            "/Sales_Order/Supplier_10";
    String[] splits = str.split("uploads",2);


//test with
    for(String s : splits)
        System.out.println(s);


}


}
