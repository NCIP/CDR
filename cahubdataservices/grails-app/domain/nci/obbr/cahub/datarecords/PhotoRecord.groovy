package nci.obbr.cahub.datarecords

import nci.obbr.cahub.forms.bpv.BpvTissueGrossEvaluation

class PhotoRecord extends DataRecordBaseClass {

    String fileName
    String filePath
    String comments
    
    static belongsTo = [bpvTissueGrossEvaluation:BpvTissueGrossEvaluation]
    
    static constraints = {
        fileName(blank:true,nullable:true)
        filePath(blank:true,nullable:true)
        comments(blank:true,nullable:true)
    }
    
    static mapping = {
        table 'dr_photo'
        id generator:'sequence', params:[sequence:'dr_photo_pk']
    } 
}
