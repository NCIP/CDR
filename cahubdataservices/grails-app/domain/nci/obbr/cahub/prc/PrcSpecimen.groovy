package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*

class PrcSpecimen extends CDRBaseClass {

    SpecimenRecord specimenRecord
    String autolysis
    String comments="pieces"
    //String releaseToInventory
    //String projectManagerFU
    InventoryStatus inventoryStatus
    
    String filePath
    String caption
    
    String rin
    String row
     static transients = ['rin', 'row']
     
    static hasMany = [unaccReasons:UnaccReasonSelection]
    
    static mapping = {
      table 'prc_specimen'
      id generator:'sequence', params:[sequence:'prc_specimen_pk']
    }
    
    
     def static searchable = {
         only= ['id', 'comments']
         'comments'  name:'prcComments'
         root false
         
     }
    
    static constraints = {
         specimenRecord(unique:true, nullable:false, blank:false)
         autolysis(nullable:true, blank:true, maxSize:10)
         comments(nullable:true, blank:true, maxSize:4000)
       //  releaseToInventory(nullable:true, blank:true, maxSize:10)
        // projectManagerFU(nullable:true, blank:true, maxSize:10)
         filePath(nullable:true, blank:true)
         caption(nullable:true, blank:true)
         inventoryStatus(nullable:true, blank:true)
         
    }
}
