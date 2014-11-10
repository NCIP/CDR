package nci.obbr.cahub.forms.ctc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.*

class CtcCrf extends CDRBaseClass{

    CaseRecord caseRecord
    Integer whichVisit
    PhlebotomySite phlebotomySite
    String needleType
    String needleGauge
    String treatmentStatus
    String dateSampleCollectedStr
    Date dateSampleCollected
    String dateSampleShippedStr
    Date dateSampleShipped
    String dateSampleReceivedStr
    Date dateSampleReceived
   // String dateSample24hReceivedStr
   // Date dateSample24hReceived
  //  String dateSample48hReceivedStr
   // Date dateSample48hReceived
   // String dateSample72hReceivedStr
  //  Date dateSample72hReceived
   // String dateSample96hReceivedStr
   // Date dateSample96hReceived
    String dateSample24hProcessedStr
    Date dateSample24hProcessed
    String dateSample48hProcessedStr
    Date dateSample48hProcessed
    String dateSample72hProcessedStr
    Date dateSample72hProcessed
    String dateSample96hProcessedStr
    Date dateSample96hProcessed
    String dateSampleCsProcessedStr
    Date dateSampleCsProcessed
    //String dateSampleBestProcessedStr
    //Date dateSampleBestProcessed
    boolean started=false
    Date dateSubmitted
    String submittedBy
    String comments
    
    String stageT
    String stageN
    String chemo
    String regimen
    String regimenDate
    String surgery
    String surgeryOther
    String surgeryDate
    String lymph
    String lymphOther
    String therapy
    String status
    String statusDeterBy
    String statusDeterByOther
    
    Boolean wasSubmitted
  
    
     static hasMany = [ctcSamples:CtcSample]
    
    static constraints = {
        caseRecord(nullable:false,blank:false)
        phlebotomySite(nullable:true,blank:true)
        needleType(nullable:true,blank:true)
        needleGauge(nullable:true,blank:true)
        treatmentStatus(nullable:true,blank:true)
        dateSampleCollectedStr(nullable:true,blank:true)
        dateSampleCollected(nullable:true,blank:true)
        dateSampleShippedStr(nullable:true,blank:true)
        dateSampleShipped(nullable:true,blank:true)
        dateSampleReceivedStr(nullable:true,blank:true)
        dateSampleReceived(nullable:true,blank:true)
        dateSample24hProcessedStr(nullable:true,blank:true)
        dateSample24hProcessed(nullable:true,blank:true)
        dateSample72hProcessedStr(nullable:true,blank:true)
        dateSample72hProcessed(nullable:true,blank:true)
        dateSampleCsProcessedStr(nullable:true,blank:true)
        dateSampleCsProcessed(nullable:true,blank:true)
        //dateSampleBestProcessedStr(nullable:true,blank:true)
        //dateSampleBestProcessed(nullable:true,blank:true)
        dateSubmitted(nullable:true,blank:true)
        submittedBy(nullable:true,blank:true)
        comments(nullable:true,blank:true )
        dateSampleReceivedStr(nullable:true,blank:true)
        dateSampleReceived(nullable:true,blank:true)
       // dateSample48hReceivedStr(nullable:true,blank:true)
       // dateSample48hReceived(nullable:true,blank:true)
       // dateSample72hReceivedStr(nullable:true,blank:true)
       // dateSample72hReceived(nullable:true,blank:true)
      //  dateSample96hReceivedStr(nullable:true,blank:true)
      //  dateSample96hReceived(nullable:true,blank:true)
        dateSample48hProcessedStr(nullable:true,blank:true)
        dateSample48hProcessed(nullable:true,blank:true)
        dateSample96hProcessedStr(nullable:true,blank:true)
        dateSample96hProcessed(nullable:true,blank:true)
        
        
         stageT(nullable:true,blank:true)
         stageN(nullable:true,blank:true)
         chemo(nullable:true,blank:true)
         regimen(nullable:true,blank:true)
         regimenDate(nullable:true,blank:true)
         surgery(nullable:true,blank:true)
         surgeryOther(nullable:true,blank:true)
         surgeryDate(nullable:true,blank:true)
         lymph(nullable:true,blank:true)
         lymphOther(nullable:true,blank:true)
         therapy(nullable:true,blank:true)
         status(nullable:true,blank:true)
         statusDeterBy(nullable:true,blank:true)
         statusDeterByOther(nullable:true,blank:true)
         wasSubmitted(nullable:true,blank:true)
  
    }

     static mapping = {
        table 'ctc_crf'
        id generator:'sequence', params:[sequence:'ctc_crf_pk']
     } 


     
    

    
    
}
