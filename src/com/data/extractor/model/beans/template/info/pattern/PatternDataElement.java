package com.data.extractor.model.beans.template.info.pattern;

import com.data.extractor.controllers.*;
import com.data.extractor.controllers.HeaderDataBean;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class PatternDataElement {
    public List<HeaderDataBean> getHeaderDataBeanList() {
        return headerDataBeanList;
    }

    public void setHeaderDataBeanList(List<HeaderDataBean> headerDataBeanList) {
        this.headerDataBeanList = headerDataBeanList;
    }

    @Expose
    private List<HeaderDataBean> headerDataBeanList = new ArrayList<HeaderDataBean>();





}
