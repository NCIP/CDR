package nci.obbr.cahub

import nci.obbr.cahub.util.appaccess.*
import java.text.SimpleDateFormat
//import nci.obbr.cahub.staticmembers.ApplicationListing

class AppUsersService {

    static transactional = true
   

      
    
    def getAppUsersRequestID( params){
        
        def id = params.id
        
        def appUsersInstance = AppUsers.get(params.id)
       
        def applist = ApplicationListing.list()
        def reqobj= new AppRequest()
        reqobj.appUser=appUsersInstance
        reqobj.save(failOnError:true)
        
            
        return reqobj.id
          
    }
    
    def createAppRequest(params){
       
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
             
        
        def appRequest = AppRequest.get(params.id) 
        if(params.status){
            params.status=params.status.toUpperCase()
        }
        
        appRequest.properties=params
        
        def action=params.get("action_"+params.id)
        
        if(action){
            appRequest.action=action
        }
        if(params.appid){
        
            appRequest.applicationListing=ApplicationListing.findByName(params.appid)
              
        }
        else{
            appRequest.applicationListing=null
        }
       
        def status = appRequest.status
       
               
        if(!appRequest.status){
            appRequest.status == 'CREATED'
        }
        appRequest.save(failOnError:true)
        
       
        
    }
    
    def updateAccountStatusOnRequestComplete(params){
        // SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        
        
        def appRequest = AppRequest.get(params.id) 
        
       
        def user=appRequest.appUser
       
      
        def acc_statuslist =AccountStatus.findAllByAppUser(user)
        
                        
            def accstatus =AccountStatus.findByApplicationListingAndAppUser(appRequest.applicationListing,user)
         //    def accstatus =status_map.get(appRequest.applicationListing.name)
            if (accstatus == null){
               
                accstatus=new AccountStatus()
                accstatus.applicationListing=appRequest.applicationListing
                accstatus.appUser=user
            }
            
            
            if(appRequest.action.equals("create")){
                
                accstatus.dateCreated2=appRequest.datePerformed
                //accstatus.dateCreated2=new Date()
                accstatus.role=appRequest.role
                accstatus.dateDeactivated=null
                                       
            }
            else if(appRequest.action.equals("deactivate")){
                
                accstatus.dateCreated2=null
                accstatus.role=null
                accstatus.dateDeactivated=appRequest.datePerformed
            }
            else if(appRequest.action.equals("change_role")){
                accstatus.role=appRequest.role
                    
            }
            else{
                    
            }
            accstatus.save(failOnError:true)
                
          
      
     
    }
   
    def getEmailList(String orgCode){
        def list=AppUsers.executeQuery("select a.email from AppUsers a inner join a.organization o where a.receiveAlerts=true and o.code=?", [orgCode])
       // list.each{
          //  println(it)
       // }
        return list
    }
        
}
