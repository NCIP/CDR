package nci.obbr.cahub.forms.gtex

import nci.obbr.cahub.datarecords.CandidateRecord
import nci.obbr.cahub.CDRBaseClass

abstract class IcdGtexBaseClass extends CDRBaseClass{

    CandidateRecord candidateRecord
    String protocolSiteNum
    String consentObtained
    String consentFailCode
    Date dateConsented
    String consentor //person obtaining consent/approaching candidate
    String consentorRelationship
    ConsentorRelationship_POSTM_OPO consentorRelationship_POSTM_OPO
    ConsentorRelationship_SURGI consentorRelationship_SURGI
    String otherConsentRelation
    String institutionICDVersion
    String specifiedLimitations
    Date dateIRBApproved  // 
    Date dateIRBExpires
    String comments

    static transients = ['consentorRelationship_POSTM_OPO', 'consentorRelationship_SURGI']
    
    static constraints = {
        

        candidateRecord(nullable:false, blank:false, unique:true)
        protocolSiteNum(nullable:false, blank:false)
        consentObtained(validator: {
            return (it == "Yes" || it == "No")
    	})
        //consentObtained(nullable:false, blank:false)
        consentFailCode(nullable:true, blank:true)
        dateConsented(nullable:true, blank:true)
        consentor(nullable:true, blank:true)          
        consentorRelationship(nullable:true, blank:true)        
        otherConsentRelation(nullable:true, blank:true)
        institutionICDVersion(nullable:true, blank:true)
        dateIRBApproved(nullable:true, blank:true)
        dateIRBExpires(nullable:true, blank:true)
        specifiedLimitations(nullable:true,blank:true,maxSize:4000)
        comments(nullable:true, blank:true, maxSize:4000)

    }

    enum YesNo {
        No("No"),
        Yes("Yes")



        final String value;

        YesNo(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Yes, No]
        }
    }
    
    enum ConsentorRelationship_SURGI {
          
        SELF("Self"),
        OTH("Other, Specify.")
        
        final String value;

        ConsentorRelationship_SURGI(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [SELF,OTH]
        }                      
    }
    
    enum ConsentorRelationship_POSTM_OPO {
          
        SELF("Self"),
        SPO("Spouse"),
        PAR("Parent"),
        CHI("Child"),
        SIB("Sibling"),
        OTH("Other, Specify.")
        
        final String value;

        ConsentorRelationship_POSTM_OPO(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [SELF,SPO,PAR,CHI,SIB,OTH]
        }                      
    }    
    
    
}
