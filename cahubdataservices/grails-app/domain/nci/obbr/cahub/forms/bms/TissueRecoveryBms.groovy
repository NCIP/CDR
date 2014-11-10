package nci.obbr.cahub.forms.bms

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord

class TissueRecoveryBms  extends CDRBaseClass{
    CaseRecord caseRecord
    String restriction
   
    Date   dateStabilized
    String timeStabilized
    
    Date   dateInDewarLn2
    String timeInDewarLn2
    
    Date   dateInDewarDice
    String timeInDewarDice
    
    String comments
    Date dateSubmitted
    Boolean protocol1hStarted
    Boolean protocol4hStarted
    Boolean protocol6hStarted
    Boolean protocol15hStarted
    
    Date dateSampleLeave
    String timeSampleLeave
    
    Date dateSampleArrive
    String timeSampleArrive
    
    String easeOfUseLn21h
    String easeOfUseDice1h
    
    String easeOfUseLn24h
    String easeOfUseDice4h
    
    String easeOfUseLn26h
    String easeOfUseDice6h
    
    String easeOfUseLn215h
    String easeOfUseDice15h
    
    //String timeInDewar
    String envTemperature
    
    String skinContiguous
    String muscleContiguous
    String aortaContiguous
    String pancreasContiguous
    String adrenalContiguous
    String thyroidContiguous
    
    static mapping = {
                table 'bms_tissue_recovery'
                id generator:'sequence', params:[sequence:'bms_tissue_recovery_pk']
     }           

    
    
    static constraints = {
         caseRecord(nullable:false,blank:false)
         restriction(nullable:true,blank:true,widget:'textarea', maxSize:4000)
        
         dateStabilized(nullable:true,blank:true)
         timeStabilized(nullable:true,blank:true)
         dateInDewarLn2(nullable:true,blank:true)
         timeInDewarLn2(nullable:true,blank:true)
         dateInDewarDice(nullable:true,blank:true)
         timeInDewarDice(nullable:true,blank:true)
         comments(nullable:true,blank:true, widget:'textarea', maxSize:4000)
         dateSubmitted(nullable:true,blank:true)
         protocol1hStarted(nullable:true,blank:true)
         protocol4hStarted(nullable:true,blank:true)
         protocol6hStarted(nullable:true,blank:true)
         protocol15hStarted(nullable:true,blank:true)
         dateSampleLeave(nullable:true,blank:true)
         timeSampleLeave(nullable:true,blank:true)
         dateSampleArrive(nullable:true,blank:true)
         timeSampleArrive(nullable:true,blank:true)
         easeOfUseLn21h(nullable:true,blank:true)
         easeOfUseDice1h(nullable:true,blank:true)
         easeOfUseLn24h(nullable:true,blank:true)
         easeOfUseDice4h(nullable:true,blank:true)
         easeOfUseLn26h(nullable:true,blank:true)
         easeOfUseDice6h(nullable:true,blank:true)
         easeOfUseLn215h(nullable:true,blank:true)
         easeOfUseDice15h(nullable:true,blank:true)
         //timeInDewar(nullable:true,blank:true)
         envTemperature(nullable:true,blank:true)
         skinContiguous(nullable:true,blank:true)
         muscleContiguous(nullable:true,blank:true)
         aortaContiguous(nullable:true,blank:true)
         pancreasContiguous(nullable:true,blank:true)
         adrenalContiguous(nullable:true,blank:true)
         thyroidContiguous(nullable:true,blank:true)
       
    }
}
