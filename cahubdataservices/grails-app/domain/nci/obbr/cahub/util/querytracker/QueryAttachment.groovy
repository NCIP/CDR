package nci.obbr.cahub.util.querytracker

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.util.FileUpload

class QueryAttachment extends CDRBaseClass {

    FileUpload fileUpload
    String uploadedBy
    
    static belongsTo = [query:Query, deviation:Deviation]
    
    static constraints = {
        fileUpload(nullable:true, blank:true)
        uploadedBy(nullable:true, blank:true)
        query(nullable:true, blank:true)
        deviation(nullable:true, blank:true)
    }
    
    static mapping = {
        table 'query_attachment'
        id generator:'sequence', params:[sequence:'query_attachment_pk']
    } 
}
