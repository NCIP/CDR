package nci.obbr.cahub.forms.gtex

class IcdGtexNdri extends IcdGtexBaseClass{

    //consent information from base class

    String willingELSISubStudy
    
    //fields completed by consented participant
    YesNo eye //deprecated in v1.1
    YesNo brain
    YesNo nerveTissue
    YesNo spinalCord //deprecated in v1.1
    YesNo oralTissue //deprecated in v1.1
    YesNo heartTissue
    YesNo bloodVessel
    YesNo upperAirway //deprecated in v1.1
    YesNo lung
    YesNo bone //deprecated in v1.1
    YesNo muscle
    YesNo endocrine
    YesNo pancreas
    YesNo spleen
    YesNo stomach
    YesNo liver
    YesNo reproductive
    YesNo kidney
    YesNo bladder
    YesNo lymphNode
    YesNo skin
    YesNo adipose
    YesNo prostate //deprecated in v1.1
    YesNo bloodUrineSaliva
    YesNo breast
    
    
    //added in v1.1
    YesNo smallIntestine
    YesNo largeIntestine
    YesNo esophagus
    YesNo minorSalivary
    
    String specifiedLimitations

    

    static constraints = {                
        
        consentObtained(validator: { val, obj ->
                
                //def eye = (obj.properties["eye"]).toString()
                def brain = (obj.properties["brain"]).toString()
                def nerveTissue = (obj.properties["nerveTissue"]).toString()
                //def spinalCord = (obj.properties["spinalCord"]).toString()
                //def oralTissue = (obj.properties["oralTissue"]).toString()
                def heartTissue = (obj.properties["heartTissue"]).toString()
                def bloodVessel = (obj.properties["bloodVessel"]).toString()
                //def upperAirway = (obj.properties["upperAirway"]).toString()
                def lung = (obj.properties["lung"]).toString()
                //def bone = (obj.properties["bone"]).toString()
                def muscle = (obj.properties["muscle"]).toString()
                def endocrine = (obj.properties["endocrine"]).toString()
                def pancreas = (obj.properties["pancreas"]).toString()
                def spleen = (obj.properties["spleen"]).toString()
                def stomach = (obj.properties["stomach"]).toString()
                def liver = (obj.properties["liver"]).toString()
                def reproductive = (obj.properties["reproductive"]).toString()
                def kidney = (obj.properties["kidney"]).toString()
                def bladder = (obj.properties["bladder"]).toString()
                def lymphNode = (obj.properties["lymphNode"]).toString()
                def skin = (obj.properties["skin"]).toString()
                def adipose = (obj.properties["adipose"]).toString()
                //def prostate = (obj.properties["prostate"]).toString()
                def bloodUrineSaliva = (obj.properties["bloodUrineSaliva"]).toString()
                def breast = (obj.properties["breast"]).toString()
                def esophagus = (obj.properties["esophagus"]).toString()
                def smallIntestine = (obj.properties["smallIntestine"]).toString()
                def largeIntestine = (obj.properties["largeIntestine"]).toString()
                def minorSalivary = (obj.properties["minorSalivary"]).toString()
                
                
                def consentObtained = (obj.properties["consentObtained"]).toString()   
                        
                if (consentObtained == "No") {
                
                    if (/*(eye == "Yes") || */(brain == "Yes") || (nerveTissue == "Yes") || /*(spinalCord == "Yes") || */
                                    /*(oralTissue == "Yes") ||*/ (heartTissue == "Yes") || (bloodVessel == "Yes") || 
                                    /*(upperAirway == "Yes") ||*/ (lung == "Yes") || /*(bone == "Yes") ||*/ (muscle == "Yes") || 
                                    (endocrine == "Yes") || (pancreas == "Yes") || (spleen == "Yes") || (stomach == "Yes") || 
                                    (liver == "Yes") || (reproductive == "Yes") || (kidney == "Yes") || (bladder == "Yes") || 
                                    (lymphNode == "Yes") || (skin == "Yes") || (adipose == "Yes") || /*(prostate == "Yes") || */
                                    (bloodUrineSaliva == "Yes") || (breast == "Yes") || (esophagus == "Yes") ||
                                    (smallIntestine == "Yes") || (largeIntestine == "Yes") || (minorSalivary == "Yes")) {
 
                        return ['invalid.oragnselection.no']                 
                    }
                }
                else if (consentObtained == "Yes") {                
                    if (/*(eye == "No") && */(brain == "No") && (nerveTissue == "No") && /*(spinalCord == "No") && */
                                    /*(oralTissue == "No") && */(heartTissue == "No") && (bloodVessel == "No") && 
                                    /*(upperAirway == "No") && */(lung == "No") && /*(bone == "No") && */(muscle == "No") && 
                                    (endocrine == "No") && (pancreas == "No") && (spleen == "No") && (stomach == "No") && 
                                    (liver == "No") && (reproductive == "No") && (kidney == "No") && (bladder == "No") && 
                                    (lymphNode == "No") && (skin == "No") && (adipose == "No") && /*(prostate == "No") && */
                                    (bloodUrineSaliva == "No") && (breast == "No") && (esophagus == "No") && 
                                    (smallIntestine == "No") && (largeIntestine == "No") && (minorSalivary == "No")) {
                                    
                
                        return ['invalid.oragnselection.yes']
                    }
                }
                else {
                    return ['invalid.value.noselection']
                }
                
            }) 
        
         // Site Protocol Num validtation
        protocolSiteNum(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes" || consentObtained.toString() == "No") {
            //// return ['nullable', 'protocolSiteNum', IcdGtexSc]
            return (obj.properties["protocolSiteNum"] != null)
          }
          else {
            return true
          }
        })    
    
        
    
        // Consentor validation
        consentor(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes" || consentObtained.toString() == "No") {
            // return ['nullable', 'consentor', IcdGtexSc]
            return (obj.properties["consentor"] != null)
          }         
        })    
        
        // Custom validation for date consented 
        dateConsented(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes" || consentObtained.toString() == "No") {
            // return ['nullable', 'dateConsented', IcdGtexSc]
            return (obj.properties["dateConsented"] != null)
          }          
        })    
        
         consentorRelationship(validator: { val, obj ->
                def consentObtained = obj.properties["consentObtained"].toString()   
                def consentRelation = obj.properties["consentorRelationship"]
                
                //println " consentObtained : consentRelation " + consentObtained + ":" + consentRelation
                
                if (consentObtained == "Yes" || consentObtained.toString() == "No")  {
                    if (consentRelation == null) {    
                        return ['invalid.consent.relation.no']    
                    }
                    else {
                        if (consentRelation.toString().contains("OTH")) {
                            def otherConsentRelation = obj.properties["otherConsentRelation"]
                            
                            if (otherConsentRelation == null) {
                                return ['invalid.other.consent.relation']     
                            }                                                        
                        }                                        
                    }
                }
                else {
                    return true
                }                
            })    

        institutionICDVersion(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes") {
            // return ['nullable', 'institutionICDVersion', IcdGtexSc]
            return (obj.properties["institutionICDVersion"] != null)
          }
          else {
            return true
          }
        })    
    
        dateIRBApproved(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes") {
            // return ['nullable', 'dateIRBApproved', IcdGtexSc]
            return (obj.properties["dateIRBApproved"] != null)
          }
          else {
            return true
          }
        })    
    
        dateIRBExpires(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes") {
            // return ['nullable', 'dateIRBExpires', IcdGtexSc]
            return (obj.properties["dateIRBExpires"] != null)
          }
          else {
            return true
          }
        })    
    
        willingELSISubStudy(validator: {
            return (it == "Yes" || it == "No")
    	})
        
        //willingELSISubStudy(nullable:false, blank:false)
        eye(nullable:true, blank:true)
        brain(nullable:true, blank:true)
        nerveTissue(nullable:true, blank:true)
        spinalCord(nullable:true, blank:true)
        oralTissue(nullable:true, blank:true)
        heartTissue(nullable:true, blank:true)
        bloodVessel(nullable:true, blank:true)
        upperAirway(nullable:true, blank:true)
        lung(nullable:true, blank:true)
        bone(nullable:true, blank:true)
        muscle(nullable:true, blank:true)
        endocrine(nullable:true, blank:true)
        pancreas(nullable:true, blank:true)
        spleen(nullable:true, blank:true)
        stomach(nullable:true, blank:true)
        liver(nullable:true, blank:true)
        reproductive(nullable:true, blank:true)
        kidney(nullable:true, blank:true)
        bladder(nullable:true, blank:true)
        lymphNode(nullable:true, blank:true)
        skin(nullable:true, blank:true)
        adipose(nullable:true, blank:true)
        prostate(nullable:true, blank:true)
        bloodUrineSaliva(nullable:true, blank:true)
        breast(nullable:true, blank:true)
        specifiedLimitations(nullable:true, blank:true)
        
        //added in v1.1
        smallIntestine(nullable:true, blank:true)
        largeIntestine(nullable:true, blank:true)
        esophagus(nullable:true, blank:true)
        
        minorSalivary(nullable:true, blank:true)
            
    }

        static mapping = {
                table 'gtex_icd_ndri'
                id generator:'sequence', params:[sequence:'gtex_icd_ndri_pk']
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

    }

