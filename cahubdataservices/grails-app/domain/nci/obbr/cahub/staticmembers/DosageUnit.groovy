package nci.obbr.cahub.staticmembers

class DosageUnit extends StaticMemberBaseClass {
    
    String toString(){
        if(description)
          return name + " - " + description
        else 
          return name
    }
    
     static mapping = {
      table 'st_dosage_unit'
      id generator:'sequence', params:[sequence:'st_dosage_unit_pk']
    }

    static constraints = {
    }
}
