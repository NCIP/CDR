package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*
import grails.test.*

class ChpBloodRecordTests extends GrailsUnitTestCase {
    
    def specimenRecord
    def caseRecord
    def chpBloodRecord
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(ChpBloodRecord)
        mockForConstraintsTests(CaseRecord)
        mockForConstraintsTests(SpecimenRecord)
        
        chpBloodRecord = new ChpBloodRecord()
        
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
        //caseRecord.caseId = '12345'  
        
        assertFalse(chpBloodRecord.validate())
        String chpBloodRecordStr = chpBloodRecord.specimenRecord ?: "Null specimenRecord.specimenId"
        println "ChpBlood Record1:" + chpBloodRecordStr
        println chpBloodRecord.errors ?: "no error found"
        chpBloodRecord.specimenRecord = specimenRecord  
        assertFalse(chpBloodRecord.validate())
        String chpBloodRecordStr2 = chpBloodRecord.specimenRecord ?: "Null specimenRecord.specimenId"
        println "ChpBlood Record2:" + chpBloodRecordStr2
        println chpBloodRecord.errors ?: "no error found"
        
        //chpBloodRecord.specimenRecord = specimenRecord  
        
        chpBloodRecord.volUnits = "mmHg"
        chpBloodRecord.bloodProcStart = new Date() 
        chpBloodRecord.bloodProcEnd = new Date()   
        chpBloodRecord.bloodFrozen = new Date() 
        chpBloodRecord.bloodStorage = new Date() 
        
        assertTrue(chpBloodRecord.validate())
        println "ChpBlood Record3:" + chpBloodRecord
        println chpBloodRecord.errors ?: "no error found"
    }
}
