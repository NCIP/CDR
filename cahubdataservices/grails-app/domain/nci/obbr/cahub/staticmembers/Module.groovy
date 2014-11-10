package nci.obbr.cahub.staticmembers


class Module  extends StaticMemberBaseClass{
    
    static mapping = {
      table 'st_module'
      id generator:'sequence', params:[sequence:'st_module_pk']
    }
	
    static constraints = {
    }
}

