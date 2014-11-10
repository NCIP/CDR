package nci.obbr.cahub.prc

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*

class PrcSpecimenReport extends PrcReportBaseClass{

    //Specimen
    SpecimenRecord specimenRecord
    //Findings/Diagnosis
    String specifyFindings
    String whereFindingsFound
    boolean analysisFromWSI
    boolean analysisFromSlide
    boolean analysisFromSlideAndWSI
    boolean analysisFromDVDWSI

    //Slides
    //inherit from specimen
    //is specimen of high quality/low quality? enum
    //add slide checkout location enum

    //Microscopic
    int totalTissuePieces
    int lengthSpec
    int widthSpec
    int tissueAreaEntire
    SeverityScale crush = SeverityScale.Negative
    SeverityScale autolysis = SeverityScale.Negative
    SeverityScale necrosis = SeverityScale.Negative
    SeverityScale fibrosis = SeverityScale.Negative
    SeverityScale atrophy = SeverityScale.Negative
    SeverityScale edema = SeverityScale.Negative
    SeverityScale congestion = SeverityScale.Negative
    SeverityScale acuteInflamation = SeverityScale.Negative
    SeverityScale chronicInflamation = SeverityScale.Negative
    SeverityScale hemorrhage = SeverityScale.Negative
    String otherMicroFindings

    //Image Annotation
    String annotationDesc
    
    //Artifacts
    Fixative fixative = Fixative.PAXgene
    SeverityScale chatter = SeverityScale.Negative
    SeverityScale fracture = SeverityScale.Negative
    SeverityScale staining = SeverityScale.Negative
    String artifactComments

  
    //Assessment
    AcceptableScale caseAcceptable
    String caseAcceptableOther

    //Closing
    String closingComments
    CAPTStaff authorizedReviewer1
    CAPTStaff authorizedReviewer2
    CAPTStaff authorizedReviewer3    
    CAPTStaff authorizedReviewer4
    CAPTStaff authorizedReviewer5
    ReviewType reviewType1 = ReviewType.Initial
    ReviewType reviewType2 = ReviewType.Initial    
    ReviewType reviewType3 = ReviewType.Initial    
    ReviewType reviewType4 = ReviewType.Initial
    ReviewType reviewType5 = ReviewType.Initial
    Date reviewDate1    
    Date reviewDate2
    Date reviewDate3
    Date reviewDate4
    Date reviewDate5
    String digitalSig1
    String digitalSig2
    String digitalSig3
    String digitalSig4
    String digitalSig5

    String toString(){id}
    
    static constraints = {

        caseRecord(nullable:true) //override baseclass constraint since pathreview belongs to a specimenrecord and not a caserecord
        //Findings/Diagnosis
        specifyFindings(blank:false,nullable:false,widget:'textarea',maxSize:4000)
        whereFindingsFound(blank:false,nullable:false,widget:'textarea',maxSize:4000)
        analysisFromWSI(blank:true,nullable:true)
        analysisFromSlide(blank:true,nullable:true)
        analysisFromSlideAndWSI(blank:true,nullable:true)
        analysisFromDVDWSI(blank:true,nullable:true)

        //Slides
        //inherit from specimen
        //is specimen of high quality/low quality? enum
        //add slide checkout location enum

        //Microscopic
        totalTissuePieces(blank:true,nullable:true)
        lengthSpec(blank:true,nullable:true)
        widthSpec(blank:true,nullable:true)
        tissueAreaEntire(blank:true,nullable:true)
        crush(blank:true,nullable:true)
        autolysis(blank:true,nullable:true)
        necrosis(blank:true,nullable:true)
        fibrosis(blank:true,nullable:true)
        atrophy(blank:true,nullable:true)
        edema(blank:true,nullable:true)
        congestion(blank:true,nullable:true)
        acuteInflamation(blank:true,nullable:true)
        chronicInflamation(blank:true,nullable:true)
        hemorrhage(blank:true,nullable:true)
        otherMicroFindings(blank:true,nullable:true,widget:'textarea',maxSize:4000)

        //Artifacts
        fixative(blank:true,nullable:true)
        chatter(blank:true,nullable:true)
        fracture(blank:true,nullable:true)
        staining(blank:true,nullable:true)
        artifactComments(blank:true,nullable:true,widget:'textarea') 
        
        //Annotation
        annotationDesc(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        
        //Assessment
        caseAcceptable(blank:true,nullable:true)
        caseAcceptableOther(blank:true,nullable:true,widget:'textarea',maxSize:4000)     
        
        //Closing
        closingComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        authorizedReviewer1(blank:true,nullable:true)
        authorizedReviewer2(blank:true,nullable:true)
        authorizedReviewer3(blank:true,nullable:true)   
        authorizedReviewer4(blank:true,nullable:true)
        authorizedReviewer5(blank:true,nullable:true)
        reviewType1(blank:true,nullable:true)
        reviewType2(blank:true,nullable:true)
        reviewType3(blank:true,nullable:true)    
        reviewType4(blank:true,nullable:true)
        reviewType5(blank:true,nullable:true)
        reviewDate1(blank:true,nullable:true)
        reviewDate2(blank:true,nullable:true)
        reviewDate3(blank:true,nullable:true)
        reviewDate4(blank:true,nullable:true)
        reviewDate5(blank:true,nullable:true)
        digitalSig1(blank:true,nullable:true)
        digitalSig2(blank:true,nullable:true)
        digitalSig3(blank:true,nullable:true)
        digitalSig4(blank:true,nullable:true)
        digitalSig5(blank:true,nullable:true)        

    }

     static mapping = {
                table 'prc_specimen_report'
                id generator:'sequence', params:[sequence:'prc_specimen_report_pk']
     }

    enum SeverityScale {
        Negative("Negative"),
        Mild("Mild"),
        Moderate("Moderate"),
        Severe("Severe")

        final String value;

        SeverityScale(String value){
            this.value = value;
        }

        String toString(){
            value;
        }

        String getKey(){
            name()
        }

        static list() {
            [Negative, Mild, Moderate, Severe]
        }
    }

    enum Fixative {
        PAXgene("PAXgene"),
        Formalin("Formalin"),
        Other("Other")

        final String value;

        Fixative(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [PAXgene, Formalin, Other]
        }
    }

    enum AcceptableScale {
        Yes("Yes"),
        No("No"),
        Maybe("Maybe, specify"),        
        Limitations("With Limitations, specify")


        final String value;

        AcceptableScale(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Yes, Maybe, No, Limitations]
        }
    }
    
    enum CAPTStaff {
        Branton("Philip Branton MD"),
        Compton("Carolyn Compton, MD PhD"),
        Madden("John Madden, MD PhD"),
        Robb("James Robb, MD"),
        Sobin("Leslie Sobin, MD")

        final String value;

        CAPTStaff(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Branton, Compton, Madden, Robb, Sobin]
        }
    }

    enum ReviewType {
        Initial("Initial"),
        QC("QC"),
        Additional("Additional Review")

        final String value;

        ReviewType(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Initial, QC, Additional]
        }
    }


}

