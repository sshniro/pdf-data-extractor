package com.data.extractor.model.beans.template.info.pattern;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class PatternDataElement {

    @Expose
    private List<ColumnDataBean> columnDataBeanList=new ArrayList<ColumnDataBean>();

}
