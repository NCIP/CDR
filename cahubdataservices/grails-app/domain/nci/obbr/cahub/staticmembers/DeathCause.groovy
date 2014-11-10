package nci.obbr.cahub.staticmembers

class DeathCause extends StaticMemberBaseClass{

    String name
    
    String toString(){"$name"}


    static constraints = {
    }
    
     static mapping = {
      table 'st_Death_Cause'
      id generator:'sequence', params:[sequence:'st_Death_Cause_pk']
    }
}
