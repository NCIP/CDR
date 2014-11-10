package nci.obbr.cahub.forms.gtex

class IcdGtexRpci extends IcdGtexBaseClass{

    //fields completed by consented participant
    YesNoNA heart
    YesNoNA corneasOnly
    YesNoNA pericardium
    YesNoNA lungs
    YesNoNA boneConnective
    YesNoNA boneConnectiveUpper
    YesNoNA boneConnectiveLower
    YesNoNA boneConnectiveVertebral
    YesNoNA saphenousVeins
    YesNoNA liver
    YesNoNA femoralVeinAtery
    YesNoNA kidney
    YesNoNA nerves
    YesNoNA pancreasIslet
    YesNoNA heartValves
    YesNoNA skin
    YesNoNA intestines
    YesNoNA aortoIliacAtery
    YesNoNA eyes
    YesNoNA adipose
    YesNoNA samplesAsNeeded
    YesNoNA otherOrganTissue
    String specifyOtherOrganTissue

    //Anatomical Gift options
    boolean transplantToPerson //Transplantation to another person or persons only.
    boolean transplantToResearch //Transplantation, research, education of the advancement of science.
    boolean additionalRecovery //Additional organs, tissues, and sample may be recovered for research only purposes.

    // New additions for release 2.0
    YesNoNA gtexAuthAddendum

    static mapping = {
         table 'gtex_icd_rpci'
         id generator:'sequence', params:[sequence:'gtex_icd_rpci_pk']
    }

    static constraints = {

        consentObtained(validator: { val, obj ->
                         
                // GTEx Addendum value ?
                def gtexAuthAddendum = (obj.properties["gtexAuthAddendum"]).toString()

                // Consent obtained ?
                def consentObtained = (obj.properties["consentObtained"]).toString()

                //println "consentObtained" + consentObtained

                // The values of gtexAuthAddendum and consentObtained have to match
                if (consentObtained == "No") {
                    if (gtexAuthAddendum == "Yes")
                    {
                        return ['invalid.oragnselection.no']
                    }
                }
                else if (consentObtained == "Yes") {
                    if ((gtexAuthAddendum == "No"))
                    {
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
            return (obj.properties["consentor"] != null)
          }
          else {
            return true
          }
        })

        // Custom validation for date consented
        dateConsented(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes" || consentObtained.toString() == "No") {
            return (obj.properties["dateConsented"] != null)
          }
          else {
            return true
          }
        })

         consentorRelationship(validator: { val, obj ->
                def consentObtained = obj.properties["consentObtained"].toString()
                def consentRelation = obj.properties["consentorRelationship"]

                //println " consentObtained : consentRelation " + consentObtained + consentRelation

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
            return (obj.properties["institutionICDVersion"] != null)
          }
          else {
            return true
          }
        })

        dateIRBApproved(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes") {
            return (obj.properties["dateIRBApproved"] != null)
          }
          else {
            return true
          }
        })

        dateIRBExpires(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes") {
            return (obj.properties["dateIRBExpires"] != null)
          }
          else {
            return true
          }
        })



        heart(nullable:true, blank:true)
        corneasOnly(nullable:true, blank:true)
        pericardium(nullable:true, blank:true)
        lungs(nullable:true, blank:true)
        boneConnective(nullable:true, blank:true)
        boneConnectiveUpper(nullable:true, blank:true)
        boneConnectiveLower(nullable:true, blank:true)
        boneConnectiveVertebral(nullable:true, blank:true)
        saphenousVeins(nullable:true, blank:true)
        liver(nullable:true, blank:true)
        femoralVeinAtery(nullable:true, blank:true)
        kidney(nullable:true, blank:true)
        nerves(nullable:true, blank:true)
        pancreasIslet(nullable:true, blank:true)
        heartValves(nullable:true, blank:true)
        skin(nullable:true, blank:true)
        intestines(nullable:true, blank:true)
        aortoIliacAtery(nullable:true, blank:true)
        eyes(nullable:true, blank:true)
        adipose(nullable:true, blank:true)
        samplesAsNeeded(nullable:true, blank:true)
        otherOrganTissue(nullable:true, blank:true)
        specifyOtherOrganTissue(nullable:true, blank:true)
//
//        otherOrganTissue(validator: { val, obj ->
//          def otherTissue = (obj.properties["otherOrganTissue"]).toString()
//          //println "otherTissue drop down" + otherTissue
//          if (otherTissue == "Yes") {
//            return (obj.properties["specifyOtherOrganTissue"] != null)
//          }else {
//              return true
//          }
//
//        })
//
//        specifyOtherOrganTissue(nullable:true, blank:true, validator: { val, obj ->
//          def otherTissueText = obj.properties["specifyOtherOrganTissue"]
//          def otherTissueDropDown = obj.properties["otherOrganTissue"]
//
//          if (otherTissueText != null) {
//                return (otherTissueDropDown.toString() == "Yes")
//          }
//          else {
//              return true
//          }
//        })

        transplantToPerson(nullable:true, blank:true) //Transplantation to another person or persons only.
        transplantToResearch(nullable:true, blank:true) //Transplantation, research, education of the advancement of science.
        additionalRecovery(nullable:true, blank:true) //Additional organs, tissues, and sample may be recovered for research only purposes.

    }



    enum YesNoNA {
        No("No"),
        Yes("Yes"),
        NA("Not Applicable")

        final String value;

        YesNoNA(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Yes, No, NA]
        }
    }

}
