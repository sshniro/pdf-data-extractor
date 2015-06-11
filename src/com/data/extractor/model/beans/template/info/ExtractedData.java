package com.data.extractor.model.beans.template.info;



import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataElement;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="extractedData")
public class ExtractedData {

    private List<TextDataElement> textDataElements = new ArrayList<TextDataElement>();

    private List<ImageDataElement> imageDataElements = new ArrayList<ImageDataElement>();

    private List<TableDataElement> tableDataElements = new ArrayList<TableDataElement>();

    private List<RegexDataElement> regexDataElements= new ArrayList<RegexDataElement>();

    private List<PatternDataElement> patternDataElements= new ArrayList<PatternDataElement>();

    public List<TextDataElement> getTextDataElements() {
        return textDataElements;
    }

    public void setTextDataElements(List<TextDataElement> textDataElements) {
        this.textDataElements = textDataElements;
    }

    public List<ImageDataElement> getImageDataElements() {
        return imageDataElements;
    }

    public void setImageDataElements(List<ImageDataElement> imageDataElements) {
        this.imageDataElements = imageDataElements;
    }

    public List<TableDataElement> getTableDataElements() {
        return tableDataElements;
    }

    public void setTableDataElements(List<TableDataElement> tableDataElements) {
        this.tableDataElements = tableDataElements;
    }

    public List<RegexDataElement> getRegexDataElements() {
        return regexDataElements;
    }

    public void setRegexDataElements(List<RegexDataElement> regexDataElements) {
        this.regexDataElements = regexDataElements;
    }

    public List<PatternDataElement> getPatternDataElements() {
        return patternDataElements;
    }

    public void setPatternDataElements(List<PatternDataElement> patternDataElements) {
        this.patternDataElements = patternDataElements;
    }

}
