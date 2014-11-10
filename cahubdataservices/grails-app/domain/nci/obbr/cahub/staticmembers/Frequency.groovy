package nci.obbr.cahub.staticmembers

class Frequency extends StaticMemberBaseClass {
    
     String toString(){
        if(description)
          return name + " - " + description
        else 
          return name
    }
    
     static mapping = {
      table 'st_frequency'
      id generator:'sequence', params:[sequence:'st_frequency_pk']
    }

    static constraints = {
    }
}
