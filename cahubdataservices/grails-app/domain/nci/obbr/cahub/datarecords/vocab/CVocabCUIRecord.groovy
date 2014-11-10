package nci.obbr.cahub.datarecords.vocab
import nci.obbr.cahub.datarecords.*

class CVocabCUIRecord extends DataRecordBaseClass{

    String cui //CUI string
    
    String toString(){"$cui"}
    
    static constraints = {
        cui(blank:false,nullable:false)
    }
    
   static mapping = {
      table 'dr_cvocab_cui'
      id generator:'sequence', params:[sequence:'dr_cvocab_cui_pk']
      sort id:"desc"  
    }    
}
