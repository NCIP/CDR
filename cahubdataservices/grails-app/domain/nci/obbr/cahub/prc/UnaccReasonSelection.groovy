package nci.obbr.cahub.prc

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.CDRBaseClass

class UnaccReasonSelection extends CDRBaseClass {
    PrcUnaccReason reason
    Boolean selected=false
    PrcSpecimen  prcSpecimen
    
    
     static mapping = {
      table 'prc_unacc_reason_selection'
      id generator:'sequence', params:[sequence:'prc_unacc_reason_selection_pk']
    }
    
    static constraints = {
    }
}
