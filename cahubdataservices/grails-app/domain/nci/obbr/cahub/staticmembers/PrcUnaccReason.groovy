package nci.obbr.cahub.staticmembers


class PrcUnaccReason extends StaticMemberBaseClass {

    static constraints = {
      //  code(blank:false,nullable:false,unique:true)
    }
    
    
     static mapping = {
      table 'st_prc_unacc_reason'
      id generator:'sequence', params:[sequence:'st_prc_unacc_reason_pk']
    }
}
