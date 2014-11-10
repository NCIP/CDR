package nci.obbr.cahub.staticmembers

class BSS extends StaticMemberBaseClass{

    BSS parentBss
    Study study
    String protocolSiteNum
    String shippingAddress
    //Date dateIRBApproved
    //Date dateIRBExpires
    Boolean subSiteFacility
    BSS parentFacility
    String timeZone // selected from enum TimeZones
    String toString(){"$code"}

    static constraints = {
        parentBss(blank:true,nullable:true)
        study(blank:false,nullable:false)
        protocolSiteNum(blank:true,nullable:true)
        shippingAddress(blank:true,nullable:true,widget:'textarea')
        subSiteFacility(blank:true,nullable:true)
        parentFacility(blank:true,nullable:true)
        timeZone(blank:true,nullable:true)
       // dateIRBApproved(blank:true, nullable:true)
       // dateIRBExpires(blank:true, nullable:true)
    }
    
    static searchable ={
        only= ['name', 'code']
        'name' name:'BSSName'
        'code' name:'BSSCode'
        root false
    }
     
    //pmh: CDRQA:1114 
    enum TimeZones {
        
        HAWAII("Hawaii Time"),
        ALASKA("Alaska Time"),
        PACIFIC("Pacific Time"),
        MOUNTAIN("Mountain Time"),
        CENTRAL("Central Time"),
        EASTERN("Eastern Time")

        final String value;

        TimeZones(String value) {
            this.value = value;
        }

        String toString() {
            value;
        }

        String getKey() {
            name()
        }

        static list() {
            [HAWAII,ALASKA,PACIFIC,MOUNTAIN,CENTRAL,EASTERN]
        }
    }
      static mapping = {
      table 'st_bss'
      id generator:'sequence', params:[sequence:'st_bss_pk']
    }
}
