package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.staticmembers.FormMetadata
import nci.obbr.cahub.datarecords.SOPRecord


abstract class BpvFormBaseClass extends CDRBaseClass{

    Date dateSubmitted
    String submittedBy
    FormMetadata formMetadata
    
    static constraints = {
        dateSubmitted(blank:true, nullable:true)
        submittedBy(blank:true, nullable:true)
        formMetadata(blank:true, nullable:true)
    }    
    
}

