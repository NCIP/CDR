package nci.obbr.cahub.staticmembers

class CtcProbe extends StaticMemberBaseClass {
    
    static mapping = {
      table 'st_ctc_probe'
      id generator:'sequence', params:[sequence:'st_ctc_probe_pk']
    }

    static constraints = {
    }
}
