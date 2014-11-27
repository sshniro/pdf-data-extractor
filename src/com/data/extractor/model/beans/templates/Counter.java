package com.data.extractor.model.beans.templates;

import com.google.gson.annotations.Expose;

public class Counter {

    @Expose
    private String reference;
    @Expose
    private int id;

    public int getId() {        return id;    }

    public void setId(int id) {        this.id = id;    }

    public String getReference() {        return reference;    }

    public void setReference(String reference) {        this.reference = reference;    }

}
