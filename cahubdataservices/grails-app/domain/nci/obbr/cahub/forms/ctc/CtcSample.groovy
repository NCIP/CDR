package nci.obbr.cahub.forms.ctc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.datarecords.*

class CtcSample extends CDRBaseClass {
   CtcCrf ctcCrf
   Fixative tubeType
   String benchTime
   String tubeId
   
   
   String measureTech
   String furtherProcessed
  
   String dateSampleStainedStr
   Date dateSampleStained
   String dateSampleImagedStr
   Date dateSampleImaged
   String dateSampleAnalyzedStr
   Date dateSampleAnalyzed
   String ctcValueStr
   Float ctcValue
   String dateLoadedDccStr
   Date dateLoadedDcc
   String experiment
   String status
   SpecimenRecord sample
   
    String probes
    String criteria
    String probes4Cs
    String criteria4Cs
    
     static transients = ['probes', 'criteria']
   
   
    static hasMany = [probeSelections:ProbeSelection, criteriaSelections:CriteriaSelection]
   

   
    static constraints = {
        ctcCrf(nullable:false,blank:false)
        measureTech(nullable:true,blank:true)
        furtherProcessed(nullable:true,blank:true)
        dateSampleStainedStr(nullable:true,blank:true)
        dateSampleStained(nullable:true,blank:true)
        dateSampleImagedStr(nullable:true,blank:true)
        dateSampleImaged(nullable:true,blank:true)
        dateSampleAnalyzedStr(nullable:true,blank:true)
        dateSampleAnalyzed(nullable:true,blank:true)
        ctcValueStr(nullable:true,blank:true)
        ctcValue(nullable:true,blank:true)
        dateLoadedDccStr(nullable:true,blank:true)
        dateLoadedDcc(nullable:true,blank:true)
        tubeType(nullable:true,blank:true)
        benchTime(nullable:true,blank:true)
        experiment(nullable:true,blank:true)
        tubeId(nullable:true,blank:true)
        status(nullable:true,blank:true)
        sample(nullable:true,blank:true)
        probes4Cs(nullable:true,blank:true)
        criteria4Cs(nullable:true,blank:true, maxSize:4000)
        
        
    }
    
   
     static mapping = {
        table 'ctc_sample'
        id generator:'sequence', params:[sequence:'ctc_sample_pk']
     } 

    
   


}
