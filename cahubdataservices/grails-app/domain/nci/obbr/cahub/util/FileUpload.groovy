package nci.obbr.cahub.util

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.CaseAttachmentType
import nci.obbr.cahub.staticmembers.Study

class FileUpload {
    CaseRecord caseRecord
    String caseId
    String fileName
    String filePath
    String comments
    String uploadTime    
    String bssCode
    CaseAttachmentType category
    Study study
    Boolean hideFromBss
    
     def static searchable = {
         only= ['id', 'category']
         category component: true
        
         root false
         
     }

    static constraints = {
        caseRecord(nullable:true, blank:true)
        caseId(nullable:true, blank:true)        
        fileName(nullable:true, blank:true)                        
        filePath(nullable:true, blank:true)
        comments(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        uploadTime(nullable:true, blank:true)       
        bssCode(nullable:true, blank:true)
        category(nullable:true, blank:true)
        study(nullable:true, blank:true)
        hideFromBss(nullable:true, blank:true)
       
    }
    
    static mapping = {    
      sort id:"desc"          
    }
}
