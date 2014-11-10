package nci.obbr.cahub.forms.gtex

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.*

class BrainBankFeedback extends CDRBaseClass{
    
    CaseRecord caseRecord
    Date dateSubmitted
    String submittedBy
    
    //section A
    String contentTempOnArr 
    //String contentTempOnArrOther
    String wholeBrainWt
    String comments
    String missingStructExists
    String acceptForFurtherProc
    boolean noLeftHemisProc=false
    boolean noRightHemisProc= false
    boolean wholeBrainNoProc= false
    String noLeftHemisProcReason
    String noRightHemisProcReason
    String wholeBrainNoProcReason
    
    
    //sectionB
    String wasBrainProcImmed
    String storedImmed
    String storedImmedType
    String storedInComments
    Date processDate
    String processedBy
    String hpComments
    String damComments
    String structComments
    
    //sectionD
    String qaPerson1
    String qaPerson2
    Date qaDate1
    Date qaDate2
    String qaComments
    
    //section E
    String uploadedFile
    
    //check if the form has been started. to decide if error checks need to done or not
    Boolean feedbackStarted = false
    
    static hasMany = [brainStructures: BrainStructures, brainDamage:BrainDamage]
 
    static constraints = {
       
        //section A
        contentTempOnArr (nullable:true,blank:true)
        wholeBrainWt(nullable:true,blank:true)
        comments(blank:true, nullable:true,widget:'textarea',maxSize:4000)
        missingStructExists(nullable:true,blank:true)
        acceptForFurtherProc(nullable:true,blank:true)
        noLeftHemisProcReason(blank:true, nullable:true,widget:'textarea',maxSize:4000)
        noRightHemisProcReason(blank:true, nullable:true,widget:'textarea',maxSize:4000)
        wholeBrainNoProcReason(blank:true, nullable:true,widget:'textarea',maxSize:4000)
        
        //sectionB
        wasBrainProcImmed(nullable:true,blank:true)
        storedImmedType(nullable:true,blank:true)
        storedImmed(nullable:true,blank:true)
        storedInComments(blank:true, nullable:true,widget:'textarea',maxSize:4000)
        processDate(nullable:true,blank:true)
        processedBy(nullable:true,blank:true)
        
        
        //sectionC?
        qaPerson1(nullable:true,blank:true)
        qaPerson2(nullable:true,blank:true)
        qaDate1(nullable:true,blank:true)
        qaDate2(nullable:true,blank:true)
        qaComments(blank:true, nullable:true,widget:'textarea',maxSize:4000)
        
        
        //section D
        uploadedFile(nullable:true,blank:true)
        
        dateSubmitted(nullable:true, blank:true)
        submittedBy(nullable:true, blank:true)
        
        feedbackStarted(nullable:true, blank:true)
       
        hpComments(nullable:true,blank:true)
        damComments(nullable:true,blank:true)
        structComments(nullable:true,blank:true)
        
    
    }
    static mapping = {
        table 'gtex_brainbank_feedback'
        id generator:'sequence', params:[sequence:'brainbank_feedback_pk']
    } 
}
