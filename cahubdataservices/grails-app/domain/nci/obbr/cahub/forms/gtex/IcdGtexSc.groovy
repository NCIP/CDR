package nci.obbr.cahub.forms.gtex

class IcdGtexSc extends IcdGtexBaseClass{

    String specifiedLimitations

    
       static mapping = {
                table 'gtex_icd_sc'
                id generator:'sequence', params:[sequence:'gtex_icd_sc_pk']
	}

    static constraints = {
          
        // protocol Site Num can't b enull
        protocolSiteNum(validator: {
            return (it != null)
    	})
    
        // Consentor validation: can't be null
         consentor(validator: {
            return (it != null)
    	})
                    
        
        // Custom validation for date consented 
        dateConsented(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes" || consentObtained.toString() == "No") {
            //return ['true', 'dateConsented', IcdGtexSc]
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
                
                if (consentObtained == "Yes" || consentObtained == "No")  {
                    
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
            //return ['true', 'institutionICDVersion', IcdGtexSc]
            return (obj.properties["institutionICDVersion"] != null)
          }
          else {
            return true
          }
        })    
    
        dateIRBApproved(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes") {
            //return ['true', 'dateIRBApproved', IcdGtexSc]
            return (obj.properties["dateIRBApproved"] != null)
          }
          else {
            return true
          }
        })    
    
        dateIRBExpires(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes") {
            //return ['true', 'dateIRBExpires', IcdGtexSc]
            return (obj.properties["dateIRBExpires"] != null)
          }
          else {
            return true
          }
        })    

    specifiedLimitations(blank:false, validator: { val, obj ->
          def consentObtained = obj.properties["consentObtained"]
          if (consentObtained.toString() == "Yes") {
            //return ['true', 'specifiedLimitations', IcdGtexSc]
            return (obj.properties["specifiedLimitations"] != null)
          }
          else {
            return true
          }
        })              
    }
}
