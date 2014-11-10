package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.datarecords.DataRecordBaseClass

class BpvSlidePrep extends BpvFormBaseClass {
    CaseRecord caseRecord
    String slidePrepTech
    String microtome
    String microtomeOs
    String microtomeBladeType
    String microtomeBladeTypeOs
    String microtomeBladeAge
    String microtomeBladeAgeOs
    String facedBlockPrep
    String facedBlockPrepOs
    String sectionThickness
    String sectionThicknessOs
    String slideCharge
    String slideChargeOs
    String waterBathTemp
    String waterBathTempOs
    String microtomeDailyMaint
    String microtomeDailyMaintOs
    String waterbathMaint
    String waterbathMaintOs
    String FFPEComments
    String heTimeInOven
    String heTimeInOvenOs
    String heOvenTemp
    String heOvenTempOs
    String heDeParrafinMethod
    String heDeParrafinMethodOs
    String heStainMethod
    String heStainMethodOs
    String heClearingMethod
    String heClearingMethodOs
    String heCoverSlipping
    String heCoverSlippingOs
    String heEquipMaint
    String heEquipMaintOs
    String heComments
    String siteSOPSlidePrep
    String siteSOPHEStain
    
    static constraints = {
        slidePrepTech(blank:true,nullable:true)
        microtome(blank:true,nullable:true)
        microtomeOs(blank:true,nullable:true)
        microtomeBladeType(blank:true,nullable:true)
        microtomeBladeTypeOs(blank:true,nullable:true)
        microtomeBladeAge(blank:true,nullable:true)
        microtomeBladeAgeOs(blank:true,nullable:true)
        facedBlockPrep(blank:true,nullable:true)
        facedBlockPrepOs(blank:true,nullable:true)
        sectionThickness(blank:true,nullable:true)
        sectionThicknessOs(blank:true,nullable:true)
        slideCharge(blank:true,nullable:true)
        slideChargeOs(blank:true,nullable:true)
        waterBathTemp(blank:true,nullable:true)
        waterBathTempOs(blank:true,nullable:true)
        microtomeDailyMaint(blank:true,nullable:true)
        microtomeDailyMaintOs(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        waterbathMaint(blank:true,nullable:true)
        waterbathMaintOs(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        FFPEComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        heTimeInOven(blank:true,nullable:true)
        heTimeInOvenOs(blank:true,nullable:true)
        heOvenTemp(blank:true,nullable:true)
        heOvenTempOs(blank:true,nullable:true)
        heDeParrafinMethod(blank:true,nullable:true)
        heDeParrafinMethodOs(blank:true,nullable:true)
        heStainMethod(blank:true,nullable:true)
        heStainMethodOs(blank:true,nullable:true)
        heClearingMethod(blank:true,nullable:true)
        heClearingMethodOs(blank:true,nullable:true)
        heCoverSlipping(blank:true,nullable:true)
        heCoverSlippingOs(blank:true,nullable:true)
        heEquipMaint(blank:true,nullable:true)
        heEquipMaintOs(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        heComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        siteSOPSlidePrep(blank:true,nullable:true)
        siteSOPHEStain(blank:true,nullable:true)
    }

    static mapping = {
         table 'bpv_slide_prep'
         id generator:'sequence', params:[sequence:'bpv_slide_prep_pk']
    }
}
