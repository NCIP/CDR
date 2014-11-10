package nci.obbr.cahub.util.appaccess
import nci.obbr.cahub.staticmembers.Organization

import grails.plugins.springsecurity.Secured 
import java.text.SimpleDateFormat
import nci.obbr.cahub.util.*

class AccountStatusController {
    static scaffold=AccountStatus
    def appUsersService
    def sendMailService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    
    def  urlHostName = AppSetting.findByCode('CDR_HOST')?.value
    def  urlhttpProtocol = AppSetting.findByCode('CDR_PROTOCOL')?.value

    def index = {
        //redirect(action: "list", params: params)
        render(view: "chooseHome")
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [accountStatusInstanceList: AccountStatus.list(params), accountStatusInstanceTotal: AccountStatus.count()]
    }

    def create = {
        def accountStatusInstance = new AccountStatus()
        accountStatusInstance.properties = params
        return [accountStatusInstance: accountStatusInstance]
    }
    
    def createApprequest = {
              
        def id =appUsersService.getAppUsersRequestID( params)
         
        redirect(action: "editRequest", id:id)
          
        
            
    }

    def save = {
        def accountStatusInstance = new AccountStatus(params)
        if (accountStatusInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'accountStatus.label', default: 'AccountStatus'), accountStatusInstance.id])}"
            redirect(action: "show", id: accountStatusInstance.id)
        }
        else {
            render(view: "create", model: [accountStatusInstance: accountStatusInstance])
        }
    }
    
    def show = {
        def accountStatusInstance = AccountStatus.get(params.id)
        
       
        if (!accountStatusInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountStatus.label', default: 'AccountStatus'), params.id])}"
            redirect(action: "list")
        }
        else {
            [accountStatusInstance: accountStatusInstance]
            
        }
    }

    
    
    def acctStatus = {
        def id = params.id
        
        def mastermap=[:]
        
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        params.sort = params.sort ?: "lastname"
        if (!params.offset) params.offset = 0
       
   
        def singleUserInstance= new AppUsers()
        def userlist=[]
        def templist=[]
        def total
        if( id !=null){
            
            singleUserInstance=AppUsers.findById(id)
            userlist.add(singleUserInstance)
        }
        else if(params.searchname && params.searchorg) {
            
            userlist=AppUsers.findAllByLastnameIlikeAndOrganization('%'+params.searchname+'%', Organization.findByCode(params.searchorg.toUpperCase()))
            userlist +=AppUsers.findAllByFirstnameIlikeAndOrganization('%'+params.searchname+'%', Organization.findByCode(params.searchorg.toUpperCase()))
            userlist=userlist.sort({a,b-> a.lastname.compareTo(b.lastname)})
            
            total=userlist.size()
        }
        else  if(params.searchname){
            total=    AppUsers.findAllByLastnameIlikeOrFirstnameIlike('%'+params.searchname+'%', '%'+params.searchname+'%').size()
            userlist=AppUsers.findAllByLastnameIlikeOrFirstnameIlike('%'+params.searchname+'%', '%'+params.searchname+'%',[max:params.max, offset:params.offset,sort:"lastname"])
          
        }  
        else  if(params.searchorg){
            total=    AppUsers.findAllByOrganization(Organization.findByCode(params.searchorg.toUpperCase())).size()
            userlist=AppUsers.findAllByOrganization(Organization.findByCode(params.searchorg.toUpperCase()),[max:params.max, offset:params.offset,sort:"lastname"])
                                 
        } 
        else{
               
            userlist.addAll(AppUsers.list(max:params.max, offset:params.offset, sort:"lastname"))
            total=userlist.size()
             
            if (userlist.size() ==25){
                total=AppUsers.list().size()
            }
            
            
        }
        
         
        def allAppsList = ApplicationListing.list()
        
        def accountStatusList = AccountStatus.list()
       
                           
        accountStatusList.each{
          
            def app =it.applicationListing.name
            def user = it.appUser
            def appUserSummary= mastermap.get(user.id)
            
            if (appUserSummary== null){
                
                appUserSummary=[:]
                //appUserSummary.put("user", user)
                appUserSummary.put("username", user)
                mastermap.put(user.id, appUserSummary)
            }
            else{
              
                mastermap.put(user.id, appUserSummary)
            }
            
            if (it.dateCreated2 !=null ){
              
               
                appUserSummary.put(app, "YES")
            }
            else{
                appUserSummary.put(app, "NO")
               
            }
            
        
        } 
        
        [mmap:mastermap, aul:userlist, apl: ApplicationListing.list(), total:total,searchname:params.searchname,searchorg:params.searchorg]
        // [mmap:mastermap, aul:userlist, apl: ApplicationListing.list(), total:userlist.size()]
         
        
    }
       
        

    
    def showDetail={
                                  
        def id = params.id
        
        def appUsersInstance = AppUsers.get(params.id)
        def accountStatusInstance = AccountStatus.findAllByAppUserAndApplicationListingNotInList(appUsersInstance,[ApplicationListing.findById(2),ApplicationListing.findById(4)],[sort:'applicationListing.name'])
       
        // new code on 09/07
        //def allappsList= ApplicationListing.list()
        // pmh 06/27/13: we dont need to list Bio4D and Open clinica from CDR version 5.0 onwards..
        def allappsList=ApplicationListing.findAllByNameNotInList(['Bio4D','Open Clinica'],[sort:'name'])
        def allappsname=[]
        allappsList.each{
            allappsname.add(it.name)
        }
        def appsforthisuser=[]
        def appsuserdoesnthave=[]
        
        //find out what apps the user has currently
        accountStatusInstance.each{
            appsforthisuser.add(it.applicationListing.name)
            
        }
        //find out what apps the user DOES NOT have to display in the showdetail page
        appsuserdoesnthave=allappsname.minus(appsforthisuser)
         
         
                     
        // end new code
       
        if (!appUsersInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appUsers.label', default: 'AppUsers'), params.id])}"
            
            redirect(action: "list")
        }
      
        
        // [asi:accountStatusInstance,aui:appUsersInstance]
        [asi:accountStatusInstance,aui:appUsersInstance,adv:appsuserdoesnthave]
        
    
        
    }

    def edit = {
        def accountStatusInstance = AccountStatus.get(params.id)
        if (!accountStatusInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountStatus.label', default: 'AccountStatus'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [accountStatusInstance: accountStatusInstance]
        }
    }
    
    def editRequest = {
        
        def canSubmit='No'
        def arid =params.id

        
        def error=[:]
        
        
        def appRequest = AppRequest.get(params.id)
        
                
        def myuser=appRequest.appUser
               
        def currentstatusmap=[:]
          
        def acc_statuslist =AccountStatus.findAllByAppUser(myuser)
        
        acc_statuslist.each{
            def key=it.applicationListing.name 
            def val = it.role
            
            currentstatusmap.put(key, val)
        }

        
                
        def approvedBy = appRequest.approvedBy
        if( appRequest.version>0 &&!approvedBy)
        appRequest.errors.rejectValue('approvedBy', 'Please enter name of the person approving this request')

        def errormap = getErrorMap(appRequest)
        
        //println("errormap size: " + errormap.size())
        
        errormap.each(){key, value->
            // println("errormap key: " + key)
            // println("errormap val: " + value)
            appRequest.errors.reject(key, value)
            error.put(key, "errors")
        }

        if(appRequest.status=='CREATED' && errormap.size() == 0 && approvedBy){
            canSubmit = "Yes"
        }
        //pmh 06/27/13
           
        def allowedAppsList
        allowedAppsList=ApplicationListing.findAllByNameNotInList(['Bio4D','Open Clinica'],[sort:'name'])  
             
        //pmh
        
        return [apr: appRequest, csmap:currentstatusmap, errorMap:error, canSubmit:canSubmit,allowedAppsList:allowedAppsList ]

        
    }

    def update = {
        def accountStatusInstance = AccountStatus.get(params.id)
        if (accountStatusInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (accountStatusInstance.version > version) {
                    
                    accountStatusInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'accountStatus.label', default: 'AccountStatus')] as Object[], "Another user has updated this AccountStatus while you were editing")
                    render(view: "edit", model: [accountStatusInstance: accountStatusInstance])
                    return
                }
            }
            accountStatusInstance.properties = params
            if (!accountStatusInstance.hasErrors() && accountStatusInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'accountStatus.label', default: 'AccountStatus'), accountStatusInstance.id])}"
                redirect(action: "show", id: accountStatusInstance.id)
            }
            else {
                render(view: "edit", model: [accountStatusInstance: accountStatusInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountStatus.label', default: 'AccountStatus'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def updateRequest={
        // params.each{key,value->
        // println "in action updateRequest key: ${key}   value:${value}"
        // }
        
        if(! params.status){
            params.status="created"
            flash.message ="Request updated"
        
        }
        
        if(params.status?.equalsIgnoreCase('created') ){
            try{
        
                appUsersService.createAppRequest(params)
                
            }
            
    
            catch(Exception e){
                e.printStackTrace()
                flash.message =" Error encountered while trying to update this request."
            
            }
        
        }
        if(params.status?.equalsIgnoreCase('assigned') ){
            
            def performedBy=params.get("performedBy")
            def datePerformed=params.get("datePerformed")
            
            def appRequest = AppRequest.get(params.id)
            appRequest?.performedBy=performedBy
            
            if(datePerformed && !datePerformed.equalsIgnoreCase('Select Date')){
                appRequest?.datePerformed=df.parse(datePerformed)   
            }
            else{
                appRequest?.datePerformed=null
            }
            if( datePerformed && performedBy){
                           
                appRequest.save(flush: true)
            }
        }
        redirect(action: "editRequest", id: params.id )
        // redirect(controller: "appRequest", action: "list")
        
    }
    
    
    def submitRequest={
        
        def appRequest = AppRequest.get(params.id)
        appRequest.status ='SUBMITTED'
        appRequest.dateSubmitted= new Date()
        appRequest.save(flush: true)
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
       
        // for production it should point to ncicahub_help@mail.nih.gov
        
        def  emailTo = AppSetting.findByCode('HELP_EMAIL')?.value
        if(!emailTo){
            emailTo='ncicahub_help@mail.nih.gov'
            //emailTo='pushpa.hariharan@nih.gov'
        }  
          
        // for test purpose point email to yourself
        // def emailTo='qili@mail.nih.gov'
        
        
        
        def btextURL= urlhttpProtocol+urlHostName+'/cahubdataservices/accountStatus/editRequest/'+params.id 
        // def btextContent="A request has been submitted by "+username +" for access to a caHUB Application. Please click on the link below for more details.\n\r"+btextURL
        def btextContent
        if( appRequest.action.equals('other')){
       
            btextContent="A request has been submitted by "+username +" for access to a caHUB Application. \n\rFollowing is the request: "+appRequest.otherDescription+"\n\rPlease click on the link below for more details.\n\r"+btextURL
        }
        else{
            btextContent="A request has been submitted by "+username +" for access to a caHUB Application. Please click on the link below for more details.\n\r"+btextURL
        }
       
        
    
        sendMailService.sendAppRequestEmail("Request Submitted for caHUB application access : Request ID ${appRequest.id}", btextContent, emailTo)
       
        redirect(controller:'appRequest', action:'aclist' )
    }
     
    def notifyAdmin={
       
        def appRequest = AppRequest.get(params.id)
        appRequest.status="TO_BE_COMPLETED"
        appRequest.save(flush: true)
          
        render("id: " + params.notify_id)
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
               
        def btextURL= urlhttpProtocol+urlHostName+'/cahubdataservices/accountStatus/editRequest/'+appRequest.id
        def btextContent
        def appname=ApplicationListing.findById(appRequest.applicationListing.id)
        
        if(!appRequest.action.equalsIgnoreCase("deactivate")){
            btextContent="Application Request  "+btextURL+ "  has been completed successfully. The user has access to "+ appname.name+" with the privileges mentioned in the request.\n\r sent by " +username
        }
        else{
            btextContent="Application Request  "+btextURL+ "  has been completed successfully. The user has been deactivated from "+ appname.name+" as per the details mentioned in the request.\n\r sent by " +username
        }
        
        // for production it should point to ncicahub_help@mail.nih.gov
        def  emailTo = AppSetting.findByCode('HELP_EMAIL')?.value
        if(!emailTo){
            emailTo='ncicahub_help@mail.nih.gov'
        }  
        
        
        
        sendMailService.sendAppRequestEmail("caHUB application access Notification: Action performed for Request number ${appRequest.id}", btextContent, emailTo)
     
        redirect(action: "editRequest", id: appRequest.id  )
          
    }
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def assignRequest={
        
      
        // def  urlHostName = AppSetting.findByCode('CDR_HOST')?.value
        //def  urlhttpProtocol = AppSetting.findByCode('CDR_PROTOCOL')?.value
        
        def btext= urlhttpProtocol+urlHostName+'/cahubdataservices/accountStatus/editRequest/'+params.id
        //  def btext= 'http://localhost:8080/cahubdataservices/accountStatus/editRequest/'+params.id
        //def btextContent=" Please complete the request as stated. Please forward all your concerns to ncicahub_help@mail.nih.gov\n\r"+btext+"\n\r\n\r"+" caHUB application help desk support"
        // trying to send mail now. figure out how to send to the right person in the applisting op_email
        def btextContent
        
        
        def appRequest = AppRequest.get(params.id)
        
      
        appRequest.status="ASSIGNED"
      
        
        appRequest.dateAssigned=new Date()
        
        if( appRequest.otherDescription){
            //  def btextContent="A request has been submitted by "+username +" for access to a caHUB Application. Following is the request\n\r"+appRequest.otherDescription+"\n\rPlease click on the link below for more details.\n\r"+btextURL
            btextContent=" Please complete the request as stated.\n\r Following is the request: "+appRequest.otherDescription+"\n\r"+btext+"\n\rPlease forward all your concerns to ncicahub_help@mail.nih.gov\n\r caHUB application help desk support"
        
        }
        else{
            btextContent=" Please complete the request as stated. Please forward all your concerns to ncicahub_help@mail.nih.gov\n\r"+btext+"\n\r\n\r"+" caHUB application help desk support"
        }
        
        
        
        // for production it should point to the APPS OPERATOR IN DB
        // for test purpose point email to yourself
       
        def emailTo=appRequest.applicationListing.opEmail
       
        
        if(!emailTo){
            //emailTo='qili@mail.nih.gov'
            emailTo='pushpa.hariharan@nih.gov'
        }
      
        sendMailService.sendAppRequestCreateEventEmail(params.id, btextContent, emailTo)
            
    
          
        redirect(controller: "appRequest", action: "aclist")
       
        
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def completeRequest={
        //params.each{key,value->
        //  println "in action completeRequest key: ${key}   value:${value}"
        //  }
        
        
        flash.message ="Application request has been granted. This process is now complete"
            
        def appRequest = AppRequest.get(params.id)
        appRequest.status="COMPLETED"
        appRequest.dateCompleted=new Date()
        appRequest.save(flush: true)
        appUsersService.updateAccountStatusOnRequestComplete(params)
        
        
        //redirect(action: "editRequest", id: params.id )
        redirect(controller: "appRequest", action: "inaclist")
        
        
    }
    
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def cancelRequest={
        // params.each{key,value->
        //    println "in action updateRequest key: ${key}   value:${value}"
        //}
        
        
        flash.message ="cancelled"
        
        def appRequest = AppRequest.get(params.id)
        appRequest.status="CANCELLED"
        appRequest.dateSubmitted=new Date()
        appRequest.save(flush: true)
            
          
        redirect(controller: "appRequest", action: "inaclist")
        
       
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def accountStatusInstance = AccountStatus.get(params.id)
        if (accountStatusInstance) {
            try {
                accountStatusInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'accountStatus.label', default: 'AccountStatus'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'accountStatus.label', default: 'AccountStatus'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accountStatus.label', default: 'AccountStatus'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def Map getErrorMap(appRequest){
          
        def errorMap=[:]
        
       
        
        def action=appRequest.action
        def status=appRequest.status
        def role= appRequest.role
        def applisting =appRequest.applicationListing
        def performedBy=appRequest.performedBy
        def datePerformed=appRequest.datePerformed
        def otherDescription=appRequest.otherDescription
            
        
        if(status?.equalsIgnoreCase('created') && !applisting){
            errorMap.put("appid" , "Please select the applicaton for which this request is being initiated")
        }
           
        
        //if(status?.equalsIgnoreCase('created') && (!role || !action) ){
        //if(status?.equalsIgnoreCase('created') && (!role && !action) ){
        //     println " test 1"  
        //   errorMap.put("action_" + appRequest.id, "please select action radio button and role")
               
        // }
        // if(status?.equalsIgnoreCase('created') && ( !role && (!action.equalsIgnoreCase('deactivate') ))){
        if(status?.equalsIgnoreCase('created') && ( !action || action.equalsIgnoreCase('index') )){   
                
            errorMap.put("action_" + appRequest.id, "Please select type of action being requested")
               
        }
        if(status?.equalsIgnoreCase('created') && (!role && (action.equalsIgnoreCase('create') || action.equalsIgnoreCase('change_role'))) ){
               
            errorMap.put("role_" + appRequest.id, "Please enter user role")
               
        }
        if(status?.equalsIgnoreCase('assigned') && (!performedBy || !datePerformed) ){
                
            errorMap.put("datePerformed", "Please enter the date when this request was performed")
               
        }
        if(status?.equalsIgnoreCase('assigned') && !performedBy ){
                
            errorMap.put("performedBy", "Please enter the name of the person performing this request")
               
        }
        if(action?.equalsIgnoreCase('other') && !otherDescription ){
                
            errorMap.put("otherDescription", "Please describe action requested in the justification text box. You have selected 'Other' for action")
               
        }
                 
        return errorMap
        
    }

    def mytest={
        //  def list =appUsersService.getEmailList('NDRI')
        // def list =appUsersService.getEmailList('RPCI')
        //  def list =appUsersService.getEmailList('BROAD')
        //def list =appUsersService.getEmailList('UNM')
        //def list =appUsersService.getEmailList('VARI')
        //def list =appUsersService.getEmailList('VUMC')
        //def list =appUsersService.getEmailList('MBB')
      
        def orgCode=params.orgCode
        // println("orgCode: " +orgCode)
        if(!orgCode)
        orgCode = 'NDRI'
           
        def list =appUsersService.getEmailList(orgCode)
        
        
        if(list)
        render(list.join('<br/>'))
        else
        render("no email ...")
    }
}
