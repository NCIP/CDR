package nci.obbr.cahub

import nci.obbr.cahub.staticmembers.*

class HubIdGenService {

    static transactional = true

    //CandidateRecord ID generation
    def genCandidateId(bssCode){
        
        def uuid = genUUID()
        
        def crc32id = genCRC32(uuid) 
        
        def candidateId = bssCode + '-' + crc32id + '-C'
        
        return candidateId
        
    }

    //InterviewRecord ID generation
    def genInterviewId(orgCode){
        
        def uuid = genUUID()
        
        def crc32id = genCRC32(uuid) 
        
        def interviewId = orgCode + '-' + crc32id + '-I'
        
        return interviewId
        
    }    
    
    //FileUpload filename supplemental ID generation
    def genFilenameKey(){
        
        def uuid = genUUID()
        
        def crc32id = genCRC32(uuid) 
        
        def filenameKey = crc32id
        
        return filenameKey
        
    }      
    
    //generate a java UUID
    def genUUID() {

        def newUUID

        newUUID = java.util.UUID.randomUUID().toString()

        return newUUID

        //a uuid that CRC32s to 7 chars
        //return "a31e5559-2688-4f03-80dc-78260155e464"

    }

    //create a random 8 character alphanumeric for ID generation purposes
    def genCRC32(uuid){
        def newCRC32
        def str

        newCRC32 = new java.util.zip.CRC32()

        newCRC32.update(uuid.getBytes())
        
        str = Long.toHexString(newCRC32.getValue())

        if(str.size() == 7){ //CRC32 can generate checksums of 7 chars.  we always need 8.
            str = "0" + str //append a 0 to make CRC 8 if size is 7
        }

        return str.toUpperCase()

    }
    
    

/* DEPRICATED
    def genCaseRecord(caseId, code){

      def caseRecord = new CaseRecord()
      def bss = new BSS()

      caseRecord.caseId = caseId.toUpperCase()
      bss = BSS.findByCode(code)
      caseRecord.bss = bss
      caseRecord.caseStatus = CaseStatus.findByCode("INIT")
      caseRecord.hubCaseUUID = genUUID()
      caseRecord.hubCaseId = genCRC32(caseRecord.hubCaseUUID)
      caseRecord.hubCaseId = caseRecord.caseId[0..3] + "-" + caseRecord.hubCaseId

      return caseRecord

    }
*/        
    
}
