package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.CDRBaseClass

class SerologyResult extends CDRBaseClass {
    
    String HIV_I_II_Ab
    String HIV_I_II_Plus_O_antibody
    String HBsAg
    String HBsAb
    String HBcAb
    String HBcAb_IgM
    String HCV_Ab
    String EBV_IgG_Ab
    String EBV_IgM_Ab
    String RPR
    String CMV_Total_Ab
    String HIV_1_NAT
    String HCV_1_NAT
    String PRR_VDRL
    
    YesNo HIV_I_II_Ab_Verified
    YesNo HIVI_IIPlusOAb_Verified
    YesNo HBsAg_Verified
    YesNo HBsAb_Verified
    YesNo HBcAb_Verified
    YesNo HBcAb_IgM_Verified
    YesNo HCV_Ab_Verified
    YesNo EBV_IgG_Ab_Verified
    YesNo EBV_IgM_Ab_Verified
    YesNo RPR_Verified
    YesNo CMV_Total_Ab_Verified
    YesNo HIV_1_NAT_Verified
    YesNo HCV_1_NAT_Verified
    YesNo PRR_VDRL_Verified
    
       
    
    static mapping = {
        table 'gtex_crf_serology'
        id generator:'sequence', params:[sequence:'col_serology_pk' ]
    }
    
    static constraints = {
        HIV_I_II_Ab(nullable:true, blank:true)
        HIV_I_II_Plus_O_antibody(nullable:true, blank:true)
        HBsAg(nullable:true, blank:true)
        HBsAb(nullable:true, blank:true)
        HBcAb(nullable:true, blank:true)
        HBcAb_IgM(nullable:true, blank:true)
        HCV_Ab(nullable:true, blank:true)
        EBV_IgG_Ab(nullable:true, blank:true)
        EBV_IgM_Ab(nullable:true, blank:true)
        RPR(nullable:true, blank:true)
        CMV_Total_Ab(nullable:true, blank:true)
        HIV_1_NAT(nullable:true, blank:true)
        HCV_1_NAT(nullable:true, blank:true)
        PRR_VDRL(nullable:true, blank:true)
         
        
        HIV_I_II_Ab_Verified(nullable:true, blank:true)
        HIVI_IIPlusOAb_Verified(nullable:true, blank:true)
        HBsAg_Verified(nullable:true, blank:true)
        HBsAb_Verified(nullable:true, blank:true)
        HBcAb_Verified(nullable:true, blank:true)
        HBcAb_IgM_Verified(nullable:true, blank:true)
        HCV_Ab_Verified(nullable:true, blank:true)
        EBV_IgG_Ab_Verified(nullable:true, blank:true)
        EBV_IgM_Ab_Verified(nullable:true, blank:true)
        RPR_Verified(nullable:true, blank:true)
        CMV_Total_Ab_Verified(nullable:true, blank:true)
        HIV_1_NAT_Verified(nullable:true, blank:true)
        HCV_1_NAT_Verified(nullable:true, blank:true)
        PRR_VDRL_Verified(nullable:true, blank:true)
        
      
    }
    
    
    enum Result {
        Not_Performed("Not Performed"),
        Positive("Positive"),
        Negative("Negative"),
        Indeterminate("Indeterminate")


        final String value;

        Result(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Not_Performed, Positive, Negative, Indeterminate]
        }
    }
    
    enum YesNo {
        No("No"),
        Yes("Yes")
        // Unknown("Unknown")
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
          
            // [Yes, No, Unknown]
            [Yes, No]
        }
    }
    
    
    
}
