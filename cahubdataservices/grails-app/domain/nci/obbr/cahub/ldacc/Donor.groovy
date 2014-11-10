package nci.obbr.cahub.ldacc

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.CDRBaseClass
//import java.sql.*

class Donor extends CDRBaseClass{
    CaseRecord caseRecord
    String publicId
    String privateId
    String lsid
    
    String rawData
    static auditable = false
    static hasMany = [specimens:Specimen]
    
     static mapping = {
      table 'ldacc_donor'
      id generator:'sequence', params:[sequence:'ldacc_donor_pk']
        rawData sqlType: 'clob'

     }

   
    static constraints = {
        caseRecord(unique:true, nullable:false, blank:false)
        publicId(unique:true, nullable:false, blank:false, maxSize:50)
        privateId(unique:true, nullable:false, blank:false, maxSize:50)
        rawData(nullable:true, blank:true )
        lsid(nullable:true, blank:true )
    }
}
