package nci.obbr.cahub

import grails.plugins.springsecurity.Secured

class privilegeController {

    def index = { }
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN','ROLE_NCI-FREDERICK_CAHUB_DM'])
    def toggleDM = {

     //put flag into session indicating a Data Manager is logged in
        if(session.DM == true){
            session.setAttribute("DM", new Boolean (false))
            flash.message = "DM flag disabled."
            //redirects don't work when method called by remotefunction
            redirect(uri:params.returnPage)        
        }
        else{
            session.setAttribute("DM", new Boolean (true))
            flash.message = "DM flag enabled. You now have data manager access!"
            //redirects don't work when method called by remotefunction
            redirect(uri:params.returnPage)      
      }
    }
   
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN','ROLE_NCI-FREDERICK_CAHUB_DM'])
    def togglePRC = {
        
     //put flag into session indicating a Data Manager is logged in
        if(session.PRC == true){
            session.setAttribute("PRC", new Boolean (false))
            flash.message = "PRC flag disabled."
            //redirects don't work when method called by remotefunction
            redirect(uri:params.returnPage)     
        }

        else{
            session.setAttribute("PRC", new Boolean (true))
            flash.message = "PRC flag enabled. You now have PRC access!"
            //redirects don't work when method called by remotefunction
            redirect(uri:params.returnPage)     
      }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN','ROLE_NCI-FREDERICK_CAHUB_LDS','ROLE_NCI-FREDERICK_CAHUB_DM'])
    def toggleLDS = {
        
     //put flag into session indicating a Data Manager is logged in
        if(session.LDS == true){
            session.setAttribute("LDS", new Boolean (false))
            flash.message = "LDS flag disabled."
            //redirects don't work when method called by remotefunction
            redirect(uri:params.returnPage)       
        }

        else{
            session.setAttribute("LDS", new Boolean (true))
            flash.message = "LDS flag enabled.  You have full LDS access!"            
            //redirects don't work when method called by remotefunction
            redirect(uri:params.returnPage)     
      }
    }    

    
    
}
