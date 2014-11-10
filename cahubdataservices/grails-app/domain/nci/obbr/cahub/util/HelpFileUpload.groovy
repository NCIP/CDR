package nci.obbr.cahub.util

import nci.obbr.cahub.CDRBaseClass

class HelpFileUpload extends CDRBaseClass{
    String uploadedBy
    String fileName
    String filePath
    String comments
    //String uploadTime    
    String studyCode
    

    static constraints = {
        uploadedBy(nullable:true, blank:true)
           
        fileName(nullable:true, blank:true)                        
        filePath(nullable:true, blank:true)
        comments(nullable:true, blank:true, maxSize:4000)
       // uploadTime(nullable:true, blank:true)       
        studyCode(nullable:true, blank:true)
    }
    
   // static mapping = {    
   //   sort id:"desc"          
  //  }
    
    static mapping = {

        table 'help_file_upload'
        id generator:'sequence', params:[sequence:'help_file_upload_pk']
     }  
}
