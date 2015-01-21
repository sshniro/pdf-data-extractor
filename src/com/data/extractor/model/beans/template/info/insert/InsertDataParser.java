package com.data.extractor.model.beans.template.info.insert;

import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.pattern.PatternDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class InsertDataParser {

    @Expose
    private String status;
    @Expose
    private TextDataParser textDataParser;
    @Expose
    private TableDataParser tableDataParser;
    @Expose
    private ImageDataParser imageDataParser;
    @Expose
    private PatternDataParser patternDataParser;

    public TableDataParser getTableDataParser() {
        return tableDataParser;
    }

    public void setTableDataParser(TableDataParser tableDataParser) {
        this.tableDataParser = tableDataParser;
    }

    public ImageDataParser getImageDataParser() {
        return imageDataParser;
    }

    public void setImageDataParser(ImageDataParser imageDataParser) {
        this.imageDataParser = imageDataParser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TextDataParser getTextDataParser() {
        return textDataParser;
    }

    public void setTextDataParser(TextDataParser textDataParser) {
        this.textDataParser = textDataParser;
    }

    public PatternDataParser getPatternDataParser() {
        return patternDataParser;
    }

    public void setPatternDataParser(PatternDataParser patternDataParser) {
        this.patternDataParser = patternDataParser;
    }
}
