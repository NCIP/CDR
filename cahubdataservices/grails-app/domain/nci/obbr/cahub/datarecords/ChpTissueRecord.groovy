package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*

class ChpTissueRecord extends DataRecordBaseClass {

    SpecimenRecord specimenRecord
    
    Date     timeInCass
    Date     timeInFix
    Date     timeInProcessor
    Date     procTimeEnd
    Date     procTimeRemov
    Date     timeEmbedded
    Date     timeRemovFromBody
    
    String procTimeRemovDate
    String procTimeRemovTime
    
    String timeInFixDate
    String timeInFixTime
    String tissueConsistency 
    
    String timeRemovFromBodyDate
    String timeRemovFromBodyTime

    static constraints = {
        
        timeInCass(blank:true,nullable:true)
        timeInFix(blank:true,nullable:true)
        timeInProcessor(blank:true,nullable:true)
        // calcTimeInFix(blank:true,nullable:true)
        procTimeEnd(blank:true,nullable:true)
        procTimeRemov(blank:true,nullable:true)
        timeEmbedded(blank:true,nullable:true)
        // calcRemov2Embed(blank:true,nullable:true)
        procTimeRemovDate(blank:true,nullable:true)
        procTimeRemovTime(blank:true,nullable:true)
        timeInFixDate(blank:true,nullable:true)
        timeInFixTime(blank:true,nullable:true)
        tissueConsistency(blank:true,nullable:true)
        timeRemovFromBody(blank:true,nullable:true)
        timeRemovFromBodyDate(blank:true,nullable:true)
        timeRemovFromBodyTime(blank:true,nullable:true)
    }
    
    static belongsTo = SpecimenRecord
    
    String toString(){"$specimenRecord.specimenId"}
    
    static mapping = {
      table 'dr_chp_tissue'
      id generator:'sequence', params:[sequence:'dr_chp_tissue_pk']
    }

}
