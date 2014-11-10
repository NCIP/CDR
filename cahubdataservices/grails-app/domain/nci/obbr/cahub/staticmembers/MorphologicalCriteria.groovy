package nci.obbr.cahub.staticmembers

class MorphologicalCriteria extends StaticMemberBaseClass {
    
     String toString(){
        //if(description)
         // return name + " - " + description
        //else 
          return name
    }
    
     static mapping = {
      table 'st_morphological_criteria'
      id generator:'sequence', params:[sequence:'st_morphological_criteria_pk']
    }

    static constraints = {
    }
}
