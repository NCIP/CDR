package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.prc.*
import nci.obbr.cahub.datawarehouse.*
import nci.obbr.cahub.forms.bpv.BpvTissueForm
import nci.obbr.cahub.forms.bpv.BpvSlidePrep

class SpecimenRecord extends DataRecordBaseClass{

    CaseRecord caseRecord
    SpecimenRecord parentSpecimen
    String specimenId
    String publicId //donor id, hub id, whatever
    Fixative fixative
    ContainerType containerType
    Boolean inQuarantine 
    Boolean wasConsumed=false //true means specimen was consumed as a means to an end, and no longer exists
    Boolean isDepleted  //true means insufficient amount of specimen is available for research
    Protocol protocol

    AcquisitionType tissueType //final tissue type
    AcquisitionType provisionalTissueType //pre PRC review tissue type
    AcquisitionLocation tissueLocation
    String otherTissueLocation

    //TRF specific fields
    String sizeDiffThanSOP
    String prosectorComments
    

    //GTEx all tissues except blood brain skin (except aliquotTimeStabilized for pink kit)
    String aliquotTimeRemoved
    String aliquotTimeStabilized
    String aliquotTimeFixed
    
    //GTEx Brain
    String brainTimeStartRemoval
    String brainTimeEndAliquot
    String brainTimeIce
    
    //GTEx Blood && BPV blood form draw times. The rest of the BPV blood data is in the blood form class.
    String bloodTimeDraw
    String bloodTimeDrawInverted
    
    //Skin
    String skinTimeIntoMedium    
    
    //Tumor
    TumorStatus tumorStatus
    PrcSpecimen prcSpecimen
    
    //GTEx pink kit only (frozen DICE)
    String adjacentToPaxTissue
    
    
     
     
   
    
    static hasMany = [slides:SlideRecord, pathologyReview:PrcSpecimenReport, shipEvents:ShippingEvent, discreps:ShipDiscrepancy, processEvents:ProcessingEvent]
    static hasOne  = [bpvTissueForm:BpvTissueForm, chpTissueRecord:ChpTissueRecord, chpBloodRecord:ChpBloodRecord, specimenDw: SpecimenDw]
    
    
     def static searchable = {
         only= ['id', 'specimenId',  'internalComments']
         'internalComments'  name:'specimenComments'
         prcSpecimen component: true
         slides component: true
         tissueType component: true
         root false
         
     }
     
    
    
    String toString(){"$specimenId"}

    String gtexSequenceNum() {
        String sequenceNum

        // this code should also work if we are simply extracting the last
        // four digits/characters -- regardless of the specimenid length or format
        //def specimenIdLength = specimenId?.length()
        //if(specimenIdLength >= 4) {
        //    sequenceNum = specimenId.substring(specimenIdLength - 4)
        //}

        if("BMS".equals(caseRecord?.study?.code)) {
            sequenceNum = specimenId[8..11]
        } else {
            sequenceNum = specimenId[12..15]
        }

        return sequenceNum;
    }

    static mapping = {

        table 'dr_specimen'
        id generator:'sequence', params:[sequence:'dr_specimen_pk']
        //tissueLocations column:'dr_specimen_st_acquis_loc'
        
        slides sort:"slideId"
    }    
    
    static constraints = {

        caseRecord(blank:false,nullable:false)
        parentSpecimen(blank:true,nullable:true)
        specimenId(blank:false,nullable:false,unique:true)
        publicId(blank:true,nullable:true,unique:true)
        tissueType(nullable:false, blank:false)
        provisionalTissueType(nullable:false, blank:false)
        tissueLocation(nullable:true, blank:true)   
        otherTissueLocation(nullable:true, blank:true)   
        fixative(blank:false,nullable:false)        
        inQuarantine(blank:true,nullable:true)
        shipEvents(blank:true,nullable:true)
        wasConsumed(blank:true,nullable:true)
        isDepleted(blank:true,nullable:true)
        protocol(blank:true,nullable:true)
        containerType(blank:true,nullable:true)
        chpBloodRecord(blank:true,nullable:true)
        chpTissueRecord(blank:true,nullable:true)
        bpvTissueForm(blank:true,nullable:true)
        specimenDw(blank:true,nullable:true)
        

        //TRF specific fields
        prosectorComments(nullable:true, blank:true, widget:'textarea',maxSize:4000)

        //all tissues except blood brain skin
        aliquotTimeRemoved(nullable:true, blank:true)
        aliquotTimeFixed(nullable:true, blank:true)
        aliquotTimeStabilized(nullable:true, blank:true)        
        sizeDiffThanSOP(nullable:true, blank:true)


        //Brain
        brainTimeStartRemoval(nullable:true, blank:true)
        brainTimeEndAliquot(nullable:true, blank:true)
        brainTimeIce(nullable:true, blank:true)

        //Blood
        bloodTimeDraw(nullable:true, blank:true)
        bloodTimeDrawInverted(nullable:true, blank:true)

        //Skin
        skinTimeIntoMedium(nullable:true, blank:true)
        
        //Tumor
        tumorStatus(nullable:true, blank:true)
        prcSpecimen(nullable:true, blank:true)
        
        //Pink
        adjacentToPaxTissue(nullable:true, blank:true)
        discreps(nullable:true, blank:true)
        processEvents(nullable:true, blank:true)
    }
}
