package nci.obbr.cahub.staticmembers

class Organization extends StaticMemberBaseClass{

    String shippingAddress
    String toString(){"$code"}
        
    static constraints = {
        shippingAddress(blank:true,nullable:true,widget:'textarea')
    }
    
     static mapping = {
      table 'st_organization'
      id generator:'sequence', params:[sequence:'st_organization_pk']
    }

       static searchable = {
        only = ['name', 'code']
        'name' name:'orgName'
        'code' name:'orgCode'
        root false
    }

    
}
