package nci.obbr.cahub.staticmembers

class CVocabType extends StaticMemberBaseClass {
    
    static mapping = {
        table 'st_cvocab_type'
        id generator:'sequence', params:[sequence:'st_cvocab_type_pk']
    }
    
    static constraints = {
    }
}
