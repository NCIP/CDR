package nci.obbr.cahub.staticmembers

class SopVersion extends StaticMemberBaseClass {

    static hasMany = [sops:SOP]
    
    static belongsTo = SOP
    
    static mapping = {
        table 'st_sop_version'
        id generator:'sequence', params:[sequence:'st_sop_version_pk']
    }
    
    static constraints = {
    }
}
