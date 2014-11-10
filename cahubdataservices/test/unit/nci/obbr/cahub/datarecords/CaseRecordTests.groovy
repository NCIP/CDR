package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*
import grails.test.*

class CaseRecordTests extends GrailsUnitTestCase {
    
    def caseRecord
    def bssCase
    def studyCase
    def caseCollectionTypeCase
    def caseStatus
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(CaseRecord)
        mockForConstraintsTests(Study)
        mockForConstraintsTests(BSS)
        mockForConstraintsTests(CaseCollectionType)
        mockForConstraintsTests(CaseStatus)
        
        caseRecord = new CaseRecord()
        
        studyCase = new Study()
        studyCase.name = 'uniqueName'
        bssCase = new BSS()
        bssCase.study = studyCase
        caseCollectionTypeCase = new CaseCollectionType()
        caseCollectionTypeCase.name = 'uniqueName2'
        caseStatus = new CaseStatus()
        caseStatus.name = 'uniqueName3'
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidate() {
        //caseRecord.caseId = '12345'  
        
        assertFalse(caseRecord.validate())
        println "Case Record1:" + caseRecord
        println caseRecord.errors ?: "no error found"
        caseRecord.caseId = ''
        assertFalse(caseRecord.validate())
        println "Case Record2:" + caseRecord
        println caseRecord.errors ?: "no error found"
        
        caseRecord.caseId = '123450'  
        caseRecord.study = studyCase 
        caseRecord.bss = bssCase 
        caseRecord.caseCollectionType = caseCollectionTypeCase
        caseRecord.caseStatus = caseStatus
        assertTrue(caseRecord.validate())
        println "Case Record3:" + caseRecord
        println caseRecord.errors ?: "no error found"
    }
}
