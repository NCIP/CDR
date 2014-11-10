package nci.obbr.cahub.prctumor

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.staticmembers.*

class PrcForm extends CDRBaseClass {
    CaseRecord caseRecord
    SlideRecord slideRecord
    String reviewedBy
    Date reviewDate
    String organOrigin
    String otherOrganOrigin
    
    String sarcomatoid
    String sarcomatoidDesc
    
    Float tumorDimension
    Float pctTumorArea
    Float pctTumorCellularity
     
    Float pctViablTumor 
    Float pctNecroticTumor
    Float pctViableNonTumor
    Float pctNonCellular
    Float hisTotal
    
    String nonCellularDesc
    
    String gradingSystem
    
    String grade
    
    
    String meetsCriteria
    String reasonNotMeet
        
    
    
    String pathologyComments
    
    String consistentLocalPrc
    String reasonNotCons
    
    String status
    String category
    
    HistologicType histologicType
    String histologicTypeDetail
    String otherHistologicType
    
    
    static mapping = {
      table 'prc_tumor_form'
      id generator:'sequence', params:[sequence:'prc_tumor_form_pk']
    }
    
    
    static constraints = {
        caseRecord(unique:false, nullable:false, blank:false)
        slideRecord(unique:true, nullable:false, blank:false)
        reviewedBy(nullable:true, blank:true)
        reviewDate(nullable:true, blank:true)
        organOrigin(nullable:true, blank:true)
        otherOrganOrigin(nullable:true, blank:true)
        tumorDimension(nullable:true, blank:true)
        pctTumorArea(nullable:true, blank:true)
        pctTumorCellularity(nullable:true, blank:true)
        pctViablTumor(nullable:true, blank:true)
        pctNecroticTumor(nullable:true, blank:true)
        pctViableNonTumor(nullable:true, blank:true)
        pctNonCellular(nullable:true, blank:true)
        hisTotal(nullable:true, blank:true)
        nonCellularDesc(nullable:true, blank:true)
        gradingSystem(nullable:true, blank:true)
        //whoGrade(nullable:true, blank:true)
       // twoTierGrade(nullable:true, blank:true)
        grade(nullable:true, blank:true)
        meetsCriteria(nullable:true, blank:true)
        reasonNotMeet(nullable:true, blank:true)
        pathologyComments(nullable:true, blank:true)
        consistentLocalPrc(nullable:true, blank:true)
        status(nullable:true, blank:true)
        reasonNotCons(nullable:true, blank:true)
        sarcomatoid(nullable:true, blank:true)
        sarcomatoidDesc(nullable:true, blank:true)
        histologicType(nullable:true, blank:true)
        histologicTypeDetail(nullable:true, blank:true)
        otherHistologicType(nullable:true, blank:true)
        
        
    }
}
