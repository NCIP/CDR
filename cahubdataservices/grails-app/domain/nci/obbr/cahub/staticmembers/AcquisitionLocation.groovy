package nci.obbr.cahub.staticmembers

class AcquisitionLocation extends StaticMemberBaseClass{
      
     
    
      static mapping = {
      table 'st_acquis_loc'
      id generator:'sequence', params:[sequence:'st_acqui_loc_pk']
    }
    
    
    static constraints = {
    }
}
