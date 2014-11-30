package com.data.extractor.model.template.upload;

import com.data.extractor.model.beans.upload.template.UploadStatus;
import org.apache.commons.fileupload.FileItem;

import java.util.Iterator;
import java.util.List;


public class AssignFormValues {
    /*
    This method returns the form value of mainCategory , subCategory and templateName
    as the templateProperties Array
     */
    public UploadStatus assignTemplateProperties(List items,UploadStatus uploadStatus){

        Iterator propertyIterator = items.iterator();

        while (propertyIterator.hasNext()) {
            FileItem templateProperty = (FileItem) propertyIterator.next();
            // Only parse the request which are of form filed
            if (templateProperty.isFormField()) {
                String fieldName = templateProperty.getFieldName();

                // Assign the Value for the mainCategory
                if(fieldName.equals("parent")){
                    uploadStatus.setParent(templateProperty.getString());
                }
                // Assign the value for the subcategory
                if(fieldName.equals("text")){
                    uploadStatus.setText(templateProperty.getString());
                }
                // Assign the value for the template name
                if(fieldName.equals("templateName")){
                    uploadStatus.setTemplateName(templateProperty.getString());
                }
            }
        }
        return uploadStatus;
    }

}
