package nci.obbr.cahub.prc
import nci.obbr.cahub.CDRBaseClass

class RandomCaseSequence extends CDRBaseClass {
    Integer seqNumber
   
    
     static mapping = {
      table 'random_case_sequence'
      id generator:'sequence', params:[sequence:'random_case_sequence_pk']
    }
    static constraints = {
    }
}
