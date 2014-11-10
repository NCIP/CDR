package nci.obbr.cahub.staticmembers

class PhlebotomySite extends StaticMemberBaseClass {
    
     String toString(){
        //if(description)
         // return name + " - " + description
        //else 
          return name
    }
    
     static mapping = {
      table 'st_phlebotomy_site'
      id generator:'sequence', params:[sequence:'st_phlebotomy_site_pk']
    }

    static constraints = {
    }
}
