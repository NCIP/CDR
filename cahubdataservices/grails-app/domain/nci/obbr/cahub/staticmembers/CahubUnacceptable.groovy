package nci.obbr.cahub.staticmembers

class CahubUnacceptable extends StaticMemberBaseClass {

    static constraints = {
    }
    
    static mapping = {
      table 'st_cahub_unacceptable'
      id generator:'sequence', params:[sequence:'st_cahub_unacceptable_pk']
    }
}
