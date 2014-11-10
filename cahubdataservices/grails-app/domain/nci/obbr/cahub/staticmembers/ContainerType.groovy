package nci.obbr.cahub.staticmembers

class ContainerType extends StaticMemberBaseClass {

    static constraints = {
    }
    
    static mapping = {
      table 'st_container'
      id generator:'sequence', params:[sequence:'container_pk']
    }
}
