package nci.obbr.cahub.staticmembers

class Protocol extends StaticMemberBaseClass {

    String delayToFixation
    String timeInFixative
    Study  study
    String toString(){"$name"}
    Integer delayHour
    Integer displayOrder
    
    static constraints = {
        name(blank:false,nullable:false,unique:false)  // overriding StaticMemberBaseClass
        study(blank:false,nullable:false)
        delayHour(blank:true,nullable:true)
        displayOrder(blank:true,nullable:true)
        
    }
    
    static mapping = {
      table 'st_protocol'
      id generator:'sequence', params:[sequence:'st_protocol_pk']
    }
}
