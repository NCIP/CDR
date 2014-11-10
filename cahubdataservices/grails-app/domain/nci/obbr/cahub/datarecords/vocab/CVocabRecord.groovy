package nci.obbr.cahub.datarecords.vocab
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.forms.gtex.crf.*

class CVocabRecord extends DataRecordBaseClass{

    String cVocabId //id of CVocab object persisted at time of creation
    String cVocabUserSelection 
    String cVocabUserOverride
    CVocabType cVocabType
    String icdCd
    String cVocabVer
    String srcDef
    String genName
    String pdqCd

    static hasMany = [cuiList:CVocabCUIRecord]
    
    static constraints = {
        cVocabId(blank:true,nullable:true)
        cVocabUserSelection(blank:true,nullable:true)
        cVocabUserOverride(blank:true,nullable:true)
        cVocabType(blank:true,nullable:true)
        icdCd(blank:true,nullable:true)
        cVocabVer(blank:true,nullable:true)
        srcDef(blank:true,nullable:true)
        genName(blank:true,nullable:true)
        pdqCd(blank:true,nullable:true, maxSize:4000)
    }
    
   static mapping = {
      table 'dr_cvocab'
      id generator:'sequence', params:[sequence:'dr_cvocab_pk']
      
      sort id:"desc" 
        srcDef sqlType: 'clob'
        genName sqlType: 'clob'
    }    
}
