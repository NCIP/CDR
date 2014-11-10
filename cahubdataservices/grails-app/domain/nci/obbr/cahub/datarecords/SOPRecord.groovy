package nci.obbr.cahub.datarecords

class SOPRecord extends DataRecordBaseClass{

    int sopId //id of SOP staticmember persisted at time of creation
    String sopNumber //public SOP number assigned from PM/Quality-- capturing just incase it changes over time
    String sopVersion //activeSopVer field of SOP staticmember persisted at time of creation
    
    String toString(){"$sopId"}
    
    static constraints = {
        sopId(blank:false,nullable:false)
        sopNumber(blank:false,nullable:false)
        sopVersion(blank:false,nullable:false)
    }
    
   static mapping = {
      table 'dr_sop_record'
      id generator:'sequence', params:[sequence:'dr_sop_record_pk']
      
      sort id:"desc"  
    }    
}
