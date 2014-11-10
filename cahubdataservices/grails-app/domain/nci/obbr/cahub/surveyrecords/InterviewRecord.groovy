package nci.obbr.cahub.surveyrecords

class InterviewRecord extends SurveyRecordBaseClass{
    
    String interviewId
    String orgCode
    InterviewStatus interviewStatus
    String studyConsent
    
    static hasMany = [surveys:SurveyRecord]    
    static hasOne = [bpvElsiCrf:BpvElsiCrf]    
    
    static constraints = {
        interviewId(unique:true, blank:false, nullable:false)
        orgCode(blank:false, nullable:false)        
        interviewStatus(blank:false, nullable:false)        
        bpvElsiCrf(blank:true,nullable:true)
        studyConsent(blank:false,nullable:false)
    }

    
    enum InterviewStatus {

        DATA("Data Entry Underway"),
        DATACOMP("Data Entry Complete"),
        SITEQACOMP("Site QA Complete"),
        REMED("Remediation"),        
        QA("QA Review"),
        COMP("Complete"),
        RELE("Released")

        final String value;

        InterviewStatus(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [DATA, DATACOMP, SITEQACOMP, REMED, QA, COMP, RELE]
        }
    }            
    
    enum SiteInterviewStatus {

        DATA("Data Entry Underway"),
        DATACOMP("Data Entry Complete"),
        SITEQACOMP("Site QA Complete")

        final String value;

        SiteInterviewStatus(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [DATA, DATACOMP, SITEQACOMP]
        }
    }      
    
    enum DmInterviewStatus {

        QA("QA Review"),
        COMP("Complete"),
        RELE("Released"),
        REMED("Remediation")                

        final String value;

        DmInterviewStatus(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [QA, COMP, RELE, REMED]
        }
    }        
    
   static mapping = {
      table 'sv_interview'
      id generator:'sequence', params:[sequence:'sv_interview_pk']
      
      sort id:"desc"  
    }          
    
}
