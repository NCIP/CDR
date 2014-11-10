package nci.obbr.cahub.forms.ctc
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.CDRBaseClass

class ProbeSelection extends CDRBaseClass{
    CtcSample ctcSample
    CtcProbe ctcProbe
    boolean selected=false
    static constraints = {
        ctcSample(nullable:false,blank:false)
        ctcProbe(nullable:true,blank:true)
    }
    static mapping = {
        table 'probe_selection'
        id generator:'sequence', params:[sequence:'probe_selection_pk']
     } 
}
