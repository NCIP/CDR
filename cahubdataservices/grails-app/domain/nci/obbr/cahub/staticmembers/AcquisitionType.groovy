package nci.obbr.cahub.staticmembers

class AcquisitionType extends StaticMemberBaseClass{
 
      static hasMany = [acqLocs:AcquisitionLocation]
    
      static mapping = {
      table 'st_acquis_type'
      id generator:'sequence', params:[sequence:'st_acquis_type_pk']
    }
    
    
      static searchable ={
        only= ['name', 'code']
        'name' name:'tissueName'
        'code' name:'tissueCode'
        root false
    }
    
}
