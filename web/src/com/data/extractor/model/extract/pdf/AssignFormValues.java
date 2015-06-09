package com.data.extractor.model.extract.pdf;


import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import org.apache.commons.fileupload.FileItem;

import java.util.Iterator;
import java.util.List;

public class AssignFormValues {

    /*
    This method returns the form value of mainCategory , subCategory and templateName
    as the templateProperties Array
     */
    public ExtractStatus assignTemplateProperties(List items,ExtractStatus extractStatus){

        Iterator propertyIterator = items.iterator();

        while (propertyIterator.hasNext()) {
            FileItem templateProperty = (FileItem) propertyIterator.next();
            // Only parse the request which are of form filed
            if (templateProperty.isFormField()) {
                String fieldName = templateProperty.getFieldName();

                // Assign the Value for the mainCategory
                if(fieldName.equals("parent")){
                    extractStatus.setParent(templateProperty.getString());
                }
                // Assign the value for the template name
                if(fieldName.equals("text")){
                    extractStatus.setDocumentId(templateProperty.getString());
                }
            }
        }
        return extractStatus;
    }
}
