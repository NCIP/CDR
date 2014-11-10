package nci.obbr.cahub.ldacc

import nci.obbr.cahub.CDRBaseClass

class Stock extends CDRBaseClass{

    static belongsTo = [extraction:Extraction]
    String lsid
    String materialType
    String volumeUnits
    Float volume
    String concentrationUnits
    Float concentration
    String yield
    Float storageTemperature
    String storageTemperatureUnit
    
    
    static auditable = false
   
    static mapping = {
      table 'ldacc_stock'
      id generator:'sequence', params:[sequence:'ldacc_stock_pk']
    }
    
     static constraints = {
         lsid(unique:true, nullable:false, blank:false, maxSize:50)
         materialType(nullable:true, blank:true)
         volumeUnits(nullable:true, blank:true)
         volume(nullable:true, blank:true)
         concentrationUnits(nullable:true, blank:true)
         concentration(nullable:true, blank:true)
         yield(nullable:true, blank:true)
         storageTemperature(nullable:true, blank:true)
         storageTemperatureUnit(nullable:true, blank:true)
     }
    
}
