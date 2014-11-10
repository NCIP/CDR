package nci.obbr.cahub.forms.common.withdraw

import nci.obbr.cahub.datarecords.CaseRecord
//import nci.obbr.cahub.forms.bpv.BpvFormBaseClass

class CaseWithdrawForms {
   
    CaseWithdraw caseWithdraw
    
    String authority
    Date datedataWdrawn
    String outcomeOfWDrawnData
    String comments
    String verifiedBy
    String verifiedByRole
    Date dateVerified
    Date dateSpeciWithdraw
    String outcomeOfWDrawDataOther
    String outcomeOfSpecimen
    String outcomeOfSpecimenOther
    String specShippedTo
    
    String ackRcvdYesNO
    Date dateAckRcvd
    
    
    Date dateSubmitted
    String submittedBy
    
    Date dateProcessingStarted
    
    // variable to show if form has been started or not
    boolean hasStarted=false
    
   //pmh 03/28/14
    Date dateCreated
    Date lastUpdated
       
    String toString(){"$caseRecord.caseId"}
    
    static auditable = true
    
    static constraints = {
        
        
        // general fields
        // authority: is it is CBR or CDR or LDACC or BRain Bank?
        authority(blank:true, nullable:true)
        dateSubmitted(blank:true, nullable:true)
        submittedBy(blank:true, nullable:true)
        // form specifics
        ackRcvdYesNO(blank:true, nullable:true)
        dateAckRcvd(blank:true, nullable:true)
        datedataWdrawn(blank:true, nullable:true)
        outcomeOfWDrawnData(blank:true, nullable:true)
        comments(blank:true, nullable:true,widget:'textarea',maxSize:4000)
        verifiedBy(blank:true, nullable:true)
        verifiedByRole(blank:true, nullable:true)
        dateVerified(blank:true, nullable:true)
        dateSpeciWithdraw(blank:true, nullable:true)
        outcomeOfWDrawDataOther(blank:true, nullable:true)
        outcomeOfSpecimenOther(blank:true, nullable:true)
        outcomeOfSpecimen(blank:true, nullable:true)
        specShippedTo(blank:true, nullable:true)
        dateProcessingStarted(blank:true, nullable:true)
        dateCreated(blank:true, nullable:true)
        lastUpdated(blank:true, nullable:true)
       
    }
    
    static mapping = {
        table 'case_withdraw_forms'
        id generator:'sequence', params:[sequence:'case_withdraw_forms_pk']
    }
}

