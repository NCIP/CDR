package nci.obbr.cahub.util

class AppSetting {

    //basic name value pairs to hold application settings
    
    String name
    String code
    String value
    String description
    String bigValue
    
    static constraints = {
        name(nullable:false,blank:false)
        code(nullable:false,blank:false,unique:true)
        value(nullable:false,blank:false)
        description(nullable:true,blank:true,widget:'textarea',maxSize:4000)
        bigValue(nullable:true,blank:true,widget:'textarea',maxSize:4000)
        
    }
      static mapping = {
      table 'st_appsetting'
      id generator:'sequence', params:[sequence:'st_appsetting_pk']
    }    
    
    static auditable = true
}
