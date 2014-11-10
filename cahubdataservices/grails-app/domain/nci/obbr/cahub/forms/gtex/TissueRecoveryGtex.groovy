package nci.obbr.cahub.forms.gtex

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.AcquisitionType
import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.util.querytracker.Deviation

class TissueRecoveryGtex extends CDRBaseClass{
    
    CaseRecord caseRecord
    Date collectionDate 
    String collectionStartTime //Time of Start of Body Preparation for POSTM, Time of Tissue Recovery Procedure Start for OPO
    
    Date firstBloodDrawDate
    String firstBloodDrawTime
    
    Date firstTissueRemovedDate
    String firstTissueRemovedTime
    AcquisitionType firstTissueRemoved
    
    String chestIncisionTime 
    String crossClampTime //for OPO or SURGI
    Double coreBodyTemp = -1000.0 //default value to indicate no temperature was captured
    String coreBodyTempLoc
    String coreBodyTempScale
    String organsDonated
    //hub-cr-36
    
    Boolean donatedLiver = false
    Boolean donatedKidney = false
    Boolean  donatedHeart = false
    Boolean  donatedLung = false
    Boolean  donatedPancreas = false
    Boolean  donatedOther = false
    String   donatedOrganOther
    // hub-cr-36
            
    String amputationType
    String ventLess24hrs //green kit question
    
    String dataEnteredBy
    Date teamLeadVeriDate
    String teamLeader
    String prosector1
    String prosector2
    String prosector3
    String prosector4
    String prosector5
    String prosector6
    String prosector7
    String prosector8
    
    String restriction
    
    String surgeryStartTime
    
    //Pink kit (frozens)
    Date dateInDryIce
    String timeInMinus80
    Date dateInMinus80
    String timeInDryIce
    String reasonReqMinFrznNotCol
    
    // SOPs
    SOPRecord op0001
    SOPRecord pr0004
    SOPRecord pm0003
    
    Deviation deviationOp0001
    Deviation deviationPr0004
    Deviation deviationPm0003
    
    static mapping = {
        table 'gtex_tissue_recovery'
        id generator:'sequence', params:[sequence:'gtex_tissue_recovery_pk']
    }           

    static constraints = {
        
        caseRecord(nullable:false,blank:false)
        collectionDate(nullable:true,blank:true)
        collectionStartTime(nullable:true,blank:true)
        firstBloodDrawDate(nullable:true,blank:true)
        firstBloodDrawTime(nullable:true,blank:true)
    
        firstTissueRemovedDate(nullable:true,blank:true)
        firstTissueRemovedTime(nullable:true,blank:true)
        firstTissueRemoved(nullable:true,blank:true)        
        
        chestIncisionTime(nullable:true,blank:true)
        crossClampTime(nullable:true,blank:true)
        coreBodyTemp(nullable:true,blank:true)
        coreBodyTempLoc(nullable:true,blank:true)
        coreBodyTempScale(nullable:true,blank:true)
        organsDonated(nullable:true,blank:true)
        amputationType(nullable:true,blank:true)
        ventLess24hrs(nullable:true,blank:true)
        dataEnteredBy(nullable:true,blank:true)
        teamLeadVeriDate(nullable:true,blank:true)
        teamLeader(nullable:true,blank:true)    
        prosector1(nullable:true,blank:true)    
        prosector2(nullable:true,blank:true)    
        prosector3(nullable:true,blank:true)    
        prosector4(nullable:true,blank:true)    
        prosector5(nullable:true,blank:true)    
        prosector6(nullable:true,blank:true)            
        prosector7(nullable:true,blank:true)            
        prosector8(nullable:true,blank:true) 
        restriction(nullable:true, blank:true, widget:'textarea',maxSize:4000)
        surgeryStartTime(nullable:true,blank:true)
        
        dateInDryIce(nullable:true,blank:true)
        timeInDryIce(nullable:true,blank:true)
        dateInMinus80(nullable:true,blank:true)
        timeInMinus80(nullable:true,blank:true)
        
        donatedOrganOther(nullable:true,blank:true)
        donatedLiver (nullable:true, blank:true)
        donatedKidney (nullable:true, blank:true)
        donatedHeart(nullable:true, blank:true)
        donatedLung(nullable:true, blank:true)
        donatedPancreas(nullable:true, blank:true)
        donatedOther(nullable:true, blank:true)
        reasonReqMinFrznNotCol(nullable:true, blank:true, widget:'textarea',maxSize:4000)
        
        op0001(nullable:true, blank:true)
        pr0004(nullable:true, blank:true)
        pm0003(nullable:true, blank:true)
        
        deviationOp0001(nullable:true, blank:true)
        deviationPr0004(nullable:true, blank:true)
        deviationPm0003(nullable:true, blank:true)
        
    }   
    
}

