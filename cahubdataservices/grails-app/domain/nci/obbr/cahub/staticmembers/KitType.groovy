package nci.obbr.cahub.staticmembers

class KitType extends StaticMemberBaseClass{

    Study study

    static constraints = {
        study(blank:false,nullable:false)
    }
    
    static mapping = {
      table 'st_kit_type'
      id generator:'sequence', params:[sequence:'st_kit_type_pk']
    }
}
