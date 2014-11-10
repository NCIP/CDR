package nci.obbr.cahub.staticmembers

class TumorStatus extends StaticMemberBaseClass{
    
    
    static mapping = {
      table 'st_tumor_status'
      id generator:'sequence', params:[sequence:'st_tumor_status_pk']
    }

    static constraints = {
    }
}
