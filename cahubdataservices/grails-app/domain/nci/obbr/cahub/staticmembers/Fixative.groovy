package nci.obbr.cahub.staticmembers

class Fixative extends StaticMemberBaseClass{
     Integer displayOrder
    
      static constraints = {
            displayOrder(blank:true,nullable:true)
      }
    
     static mapping = {
      table 'st_fixative'
      id generator:'sequence', params:[sequence:'Fixative_pk']
    }

}
