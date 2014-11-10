package nci.obbr.cahub.forms.bpv.worksheet

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.forms.bpv.BpvFormBaseClass

class BpvWorkSheet extends BpvFormBaseClass {

     CaseRecord caseRecord
     String parentSampleId
     String experimentId
     Date dateEidRecorded
     String dateEidRecordedStr
     Date dateSubmitted
     SpecimenRecord sample
     String chooseModule
     String submittedModule
     SOPRecord formSOP
     String comments
     Boolean m1
     Boolean m2
     Boolean m3
     Boolean m4
     Boolean nat
     Boolean ett
     Boolean m5
     
    Boolean sm1
    Boolean sm2
    Boolean sm3
    Boolean sm4
    Boolean snat
    Boolean sett
    Boolean sm5
    
    Integer formVersion
    
     static hasOne = [module1Sheet:Module1Sheet,module2Sheet:Module2Sheet,module3Sheet:Module3Sheet,module4Sheet:Module4Sheet,module3NSheet:Module3NSheet,module4NSheet:Module4NSheet,module5Sheet:Module5Sheet,qcAndFrozenSample:QcAndFrozenSample]
     
     
     static mapping = {
         table 'bpv_work_sheet'
         id generator:'sequence', params:[sequence:'bpv_work_sheet_pk']
    }
    
    static constraints = {
        parentSampleId(blank:true,nullable:true)
        experimentId(blank:true,nullable:true)
        dateEidRecorded(blank:true,nullable:true)
        dateEidRecordedStr(blank:true,nullable:true)
        module1Sheet(blank:true,nullable:true)
        module2Sheet(blank:true,nullable:true)
        module3Sheet(blank:true,nullable:true)
        module4Sheet(blank:true,nullable:true)
        module3NSheet(blank:true,nullable:true)
        module4NSheet(blank:true,nullable:true)
        module5Sheet(blank:true,nullable:true)
        qcAndFrozenSample(blank:true,nullable:true)
        dateSubmitted(blank:true,nullable:true)
        sample(blank:true,nullable:true)
        chooseModule(blank:true,nullable:true)
        submittedModule(blank:true,nullable:true)
        formSOP(blank:true,nullable:true)
        comments(blank:true,nullable:true, maxSize:4000)
        m1(blank:true,nullable:true)
        m2(blank:true,nullable:true)
        m3(blank:true,nullable:true)
        m4(blank:true,nullable:true)
        nat(blank:true,nullable:true)
        ett(blank:true,nullable:true)
         m5(blank:true,nullable:true)
        
        sm1(blank:true,nullable:true)
        sm2(blank:true,nullable:true)
        sm3(blank:true,nullable:true)
        sm4(blank:true,nullable:true)
        snat(blank:true,nullable:true)
        sett(blank:true,nullable:true)
        sm5(blank:true,nullable:true)
        
        formVersion(blank:true,nullable:true)
    }
}
