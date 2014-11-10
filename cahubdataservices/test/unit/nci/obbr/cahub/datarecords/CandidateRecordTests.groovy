package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*
import grails.test.*

class CandidateRecordTests extends GrailsUnitTestCase {
    
    def candidateRecord
    def bssCandidate
    def studyCandidate
    def caseCollectionTypeCandidate
    
    protected void setUp() {
        super.setUp()
        mockForConstraintsTests(CandidateRecord)
        mockForConstraintsTests(Study)
        mockForConstraintsTests(BSS)
        mockForConstraintsTests(CaseCollectionType)
        
        candidateRecord = new CandidateRecord()
        
        studyCandidate = new Study()
        studyCandidate.name = 'uniqueName'
        bssCandidate = new BSS()
        bssCandidate.study = studyCandidate
        caseCollectionTypeCandidate = new CaseCollectionType()
        caseCollectionTypeCandidate.name = 'uniqueName2'
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testValidate() {
        //candidateRecord.candidateId = '12345' 
        
        assertFalse(candidateRecord.validate())
        println "Candidate Record1:" + candidateRecord
        println candidateRecord.errors ?: "no error found"
        candidateRecord.candidateId = ''
        assertFalse(candidateRecord.validate())
        println "Candidate Record2:" + candidateRecord
        println candidateRecord.errors ?: "no error found"
        
        candidateRecord.candidateId = '123450'
        candidateRecord.study = studyCandidate 
        candidateRecord.bss = bssCandidate 
        candidateRecord.caseCollectionType = caseCollectionTypeCandidate
        assertTrue(candidateRecord.validate())
        println "Candidate Record3:" + candidateRecord
        println candidateRecord.errors ?: "no error found"
    }
}
