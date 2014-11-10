package nci.obbr.cahub.prc

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord

public abstract class PrcReportBaseClass extends CDRBaseClass {

    CaseRecord caseRecord
    
    static constraints = {
        caseRecord(blank:false,nullable:false)
    }    
 
}
