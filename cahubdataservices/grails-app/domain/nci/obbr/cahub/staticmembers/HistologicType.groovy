package nci.obbr.cahub.staticmembers

class HistologicType extends StaticMemberBaseClass{
    
    String who_code
    
    String toString(){"$name"}
    
      static mapping = {
      table 'st_histologic_type'
      id generator:'sequence', params:[sequence:'st_histologic_type_pk']
    }
    
      static constraints = {
         who_code(nullable:true,blank:true)
      }
   
}
