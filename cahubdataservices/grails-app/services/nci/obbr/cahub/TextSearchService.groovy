package nci.obbr.cahub

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.util.querytracker.*
import nci.obbr.cahub.datarecords.ctc.*

class TextSearchService {

    static transactional = true
    
    
      def index_all = {
        Thread.start {
          
            log.info((new Date()).toString() + " started index CaseRecord")
            CaseRecord.index()
            CandidateRecord.index()
            Query.index()
            PatientRecord.index()
            log.info((new Date()).toString() + " End index CaseRecord")
       
          
        }
       
    }


    def index_patient = {
        Thread.start {
          
            log.info((new Date()).toString() + " started index PatientRecord")
            PatientRecord.index()
            log.info((new Date()).toString() + " End index PatientRecord")
       
          
        }
       
    }
   
}
