package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*
import grails.test.*

class ChpTissueRecordTests extends GrailsUnitTestCase {
    
    def specimenRecord
    def caseRecord
    def chpTissueRecord
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(ChpTissueRecord)
        mockForConstraintsTests(CaseRecord)
        mockForConstraintsTests(SpecimenRecord)
        
        chpTissueRecord = new ChpTissueRecord()
        
        caseRecord = new CaseRecord()
        caseRecord.caseId = '123450'  
        
        specimenRecord = new SpecimenRecord()
        specimenRecord.caseRecord = caseRecord
        specimenRecord.specimenId = '112233'
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidate() {
                
        assertFalse(chpTissueRecord.validate())
        String chpTissueRecordStr2 = chpTissueRecord.specimenRecord ?: "Null specimenRecord.specimenId"
        println "ChpBlood Record2:" + chpTissueRecordStr2
        println chpTissueRecord.errors ?: "no error found"
        
        chpTissueRecord.specimenRecord = specimenRecord  
                
        assertTrue(chpTissueRecord.validate())
        println "ChpBlood Record3:" + chpTissueRecord
        println chpTissueRecord.errors ?: "no error found"
    }
}
