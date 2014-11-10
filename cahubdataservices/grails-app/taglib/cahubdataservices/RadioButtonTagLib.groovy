package cahubdataservices

class RadioButtonTagLib {

    def yesNoRadioPicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name
        def checked = attrs.checked
              
        out.println"<div>"
        if (checked == null || checked == "Undef") {
           // <label for="male">Male</label>
            
           out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\"/>"   
           out.println "<label for=\"${id}_yes\">Yes</label>"
           out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
           out.println "<input type=\"radio\" name=\"${name}\" value=\"No\"  id=\"${id}_no\" />"   
           out.println "<label for=\"${id}_no\" >No</label>"        
           out.println "<input type=\"radio\" name=\"${name}\" value=\"Undef\"  checked=\"checked\" id=\"${id}_undef\" />"                                    
        }else {
                
            if (checked == "Yes") {

                
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" checked=\"checked\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\">Yes</label>" 
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" />"  
                out.println "<label for=\"${id}_no\" >No</label>"                

            }
            else if(checked == "No") {
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\"  id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\">Yes</label>" 
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" checked=\"checked\" id=\"${id}_no\" />"  
                out.println "<label for=\"${id}_no\" >No</label>"            

            }
        }
        out.println"</div>"
        
        out.println "<script>"
        out.println "\$(\'#${id}_undef\').hide()"       
        out.println "</script>"                                                  
    }
    
    def noYesRadioPicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name
        
        def checked = attrs.checked
               
        out.println"<div>"
        if (checked == null || checked == "Undef") {
            out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" />"
            out.println "<label for=\"${id}_no\" >No</label>"
            out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
            out.println "<label for=\"${id}_yes\" >Yes</label>" 
            out.println "<input type=\"radio\" name=\"${name}\" value=\"Undef\"  checked=\"checked\" id=\"${id}_undef\" />"
            
        }else {
                
            if (checked == "Yes") {

                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" />"
                out.println "<label for=\"${id}_no\">No</label>"
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" checked=\"checked\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\" >Yes</label>" 

            }
            else if(checked == "No") {
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" checked=\"checked\" id=\"${id}_no\" />"
                out.println "<label for=\"${id}_no\">No</label>"
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\" >Yes</label>"                                            
            }            
        }
        out.println"</div>"             
        out.println "<script>"
        out.println "\$(\'#${id}_undef\').hide()"       
        out.println "</script>"    
        
                    
    }
    
    def noUnknownYesRadioPicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name
        
        def checked = attrs.checked
               
        out.println"<div>"
        if (checked == null || checked == "Undef") {
            out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" /> "
            out.println "<label for=\"${id}_no\">No</label><br />"
            out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_unknown\" />"  
            out.println "<label for=\"${id}_unknown\">Unknown</label>" 
            out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
            out.println "<label for=\"${id}_yes\" >Yes</label>"     
            out.println "<input type=\"radio\" name=\"${name}\" value=\"Undef\"  checked=\"checked\" id=\"${id}_undef\" />"
            
        }else {
                
            if (checked == "Yes") {

                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" /> "
                out.println "<label for=\"${id}_no\">No</label><br />"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" id=\"${id}_unknown\" />"  
                out.println "<label for=\"${id}_unknown\">Unknown</label>" 
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" checked=\"checked\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\" >Yes</label>"             

            }
            else if(checked == "No") {
                                
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" checked=\"checked\" id=\"${id}_no\" /> "
                out.println "<label for=\"${id}_no\">No</label><br />"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" id=\"${id}_unknown\" />"  
                out.println "<label for=\"${id}_unknown\">Unknown</label>" 
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\" >Yes</label>"             
            }        
            else {
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\"  id=\"${id}_no\" /> "
                out.println "<label for=\"${id}_no\">No</label><br />"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" checked=\"checked\" id=\"${id}_unknown\" />"  
                out.println "<label for=\"${id}_unknown\">Unknown</label>" 
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\" >Yes</label>"             
            }
        }
        out.println"</div>"
              
        out.println "<script>"
        out.println "\$(\'#${id}_undef\').hide()"       
        out.println "</script>" 
       
    }
    
    def noYesUnknownRadioPicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name                        
        
        def checked = attrs.checked
               
        out.println"<div>"
        if (checked == null || checked == "Undef") {
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" />"
                out.println "<label for=\"${id}_no\">No</label>"
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\" >Yes</label> <br />"    
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" id=\"${id}_unknown\" />"  
                out.println "<label for=\"${id}_unknown\" >Unknown</label>"    
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Undef\"  checked=\"checked\" id=\"${id}_undef\" />"
            
        }else {
                
            if (checked == "Yes") {

                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" />"
                out.println "<label for=\"${id}_no\">No</label>"
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" checked=\"checked\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\"  >Yes</label> <br />"    
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" id=\"${id}_unknown\" />"  
                out.println "<label for=\"${id}_unknown\" >Unknown</label>" 
                         

            }
            else if(checked == "No") {
                                
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" checked=\"checked\" id=\"${id}_no\" />"
                out.println "<label for=\"${id}_no\">No</label>"
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\" >Yes</label> <br />"  
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" id=\"${id}_unknown\" />"  
                out.println "<label for=\"${id}_unknown\" >Unknown</label>" 
                      
            }        
            else {
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\"  id=\"${id}_no\" />"
                out.println "<label for=\"${id}_no\">No</label>"
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\" >Yes</label> <br />"  
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" checked=\"checked\" id=\"${id}_unknown\" />"  
                out.println "<label for=\"${id}_unknown\" >Unknown</label>" 
                           
            }
        }
        out.println"</div>"
        out.println "<script>"
        out.println "\$(\'#${id}_undef\').hide()"       
        out.println "</script>" 
        
    }
    
    
    
    def yesNoUnknownRadioPicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name
              
        def checked = attrs.checked       
        out.println"<div>"
        if (checked == null) {
            
            out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
            out.println "<label for=\"${id}_yes\">Yes</label>"   
            out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" />"
            out.println "<label for=\"${id}_no\">No</label>" 
            out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" id=\"${id}_unknown\" />"  
            out.println "<label for=\"${id}_unknown\">Unknown</label>"       
            
        }else {
                
            if (checked == "Yes") {
                
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" checked=\"checked\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\">Yes</label>"    
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" />"
                out.println "<label for=\"${id}_no\">No</label>" 
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" id=\"${id}_unknown\" />"  
                out.println "<label for=\"${id}_unknown\">Unknown</label>" 
                         

            }
            else if(checked == "No") {                     
                
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\">Yes</label>"  
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" checked=\"checked\" id=\"${id}_no\" />"
                out.println "<label for=\"${id}_no\">No</label>" 
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" id=\"${id}_unknown\" />"  
                out.println "<label for=\"${id}_unknown\">Unknown</label>"                       
            }        
            else {
                
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\">Yes</label>"  
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\"  id=\"${id}_no\" />"
                out.println "<label for=\"${id}_no\">No</label>"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Unknown\" checked=\"checked\" id=\"${id}_unknown\" />"  
                out.println "<label for=\"${id}_unknown\">Unknown</label>" 
                           
            }
        }
        out.println"</div>"                    
    }
    
    
    def icdYesNoRadioPicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name
        def checked = attrs.checked
              
        out.println"<div>"
        if (checked == null || checked == "Undef") {                     
           out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\"/>"   
           out.println "<label for=\"${id}_yes\">Yes</label> <br />"
           out.println "<input type=\"radio\" name=\"${name}\" value=\"No\"  id=\"${id}_no\" />"   
           out.println "<label for=\"${id}_no\" >No</label> <br />"        
           out.println "<input type=\"radio\" name=\"${name}\" value=\"Undef\"  checked=\"chekced\" id=\"${id}_undef\" />"                                    
        }else {
                
            if (checked == "Yes") {

                
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" checked=\"checked\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\">Yes</label><br />" 
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" />"  
                out.println "<label for=\"${id}_no\" >No</label>"                

            }
            else if(checked == "No") {
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\"  id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\">Yes</label><br />" 
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" checked=\"checked\" id=\"${id}_no\" />"  
                out.println "<label for=\"${id}_no\" >No</label>"            

            }
        }
        out.println"</div>"
        
        out.println "<script>"
        out.println "\$(\'#${id}_undef\').hide()"       
        out.println "</script>"                                                  
    }
    
    def bpvYesNoRadioPicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name
        def checked = attrs.checked
              
        out.println"<div>"
        if (checked == null || checked == "Undef") {
            out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" id=\"${id}_yes\"/>"   
            out.println "<label for=\"${id}_yes\">Yes</label>"
            out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            out.println "<input type=\"radio\" name=\"${name}\" value=\"No\"  id=\"${id}_no\" />"   
            out.println "<label for=\"${id}_no\" >No</label>"
        } else {   
            if (checked == "Yes") {
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\" checked=\"checked\" id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\">Yes</label>" 
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" id=\"${id}_no\" />"  
                out.println "<label for=\"${id}_no\" >No</label>"
            } else if (checked == "No") {
                out.println "<input type=\"radio\" name=\"${name}\" value=\"Yes\"  id=\"${id}_yes\" />"  
                out.println "<label for=\"${id}_yes\">Yes</label>" 
                out.println "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                out.println "<input type=\"radio\" name=\"${name}\" value=\"No\" checked=\"checked\" id=\"${id}_no\" />"  
                out.println "<label for=\"${id}_no\" >No</label>"
            }
        }
        out.println"</div>"                                                 
    }
}
