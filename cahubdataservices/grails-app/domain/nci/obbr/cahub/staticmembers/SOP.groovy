package nci.obbr.cahub.staticmembers

class SOP extends StaticMemberBaseClass {

    String sopNumber
    String sopName
    String activeSopVer
    Study study
    
    static hasMany = [formMetadata:FormMetadata, sopVersions:SopVersion]
    
    static belongsTo = FormMetadata
    
    String toString(){"$name"}
    
    static mapping = {
        table 'st_sop'
        id generator:'sequence', params:[sequence:'st_sop_pk']
    }
    
    static constraints = {
        sopNumber(nullable:true,blank:true)
        sopName(nullable:true,blank:true)
        activeSopVer(nullable:true,blank:true)
        study(nullable:false,blank:false)
    }
}
