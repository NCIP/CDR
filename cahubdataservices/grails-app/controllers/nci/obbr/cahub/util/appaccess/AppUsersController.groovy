package nci.obbr.cahub.util.appaccess

import nci.obbr.cahub.staticmembers.Organization
import grails.plugins.springsecurity.Secured
     

class AppUsersController {
    
    static scaffold=AppUsers

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        params.sort = params.sort ?: "lastname"
        def userlist=[]
        def total
         if(params.searchname && params.searchorg) {
            
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
        
        
        
       // [appUsersInstanceList: AppUsers.list(params), appUsersInstanceTotal: AppUsers.count()]
        [appUsersInstanceList: userlist, appUsersInstanceTotal: total,searchname:params.searchname,searchorg:params.searchorg]
        
    }

    def create = {
        def appUsersInstance = new AppUsers()
        appUsersInstance.properties = params
        return [appUsersInstance: appUsersInstance]
    }

    def save = {
        def appUsersInstance = new AppUsers(params)
         def email = params.email
        if(email){
            appUsersInstance.email = email.trim()
        }
        if (appUsersInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'appUsers.label', default: 'AppUsers'), appUsersInstance.id])}"
            redirect(action: "show", id: appUsersInstance.id)
        }
        else {
            render(view: "create", model: [appUsersInstance: appUsersInstance])
        }
    }

    def show = {
        def appUsersInstance = AppUsers.get(params.id)
        if (!appUsersInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appUsers.label', default: 'AppUsers'), params.id])}"
            redirect(action: "list")
        }
        else {
            [appUsersInstance: appUsersInstance]
        }
    }
    
    

    def edit = {
        def appUsersInstance = AppUsers.get(params.id)
        if (!appUsersInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appUsers.label', default: 'AppUsers'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [appUsersInstance: appUsersInstance]
        }
    }

    def update = {
        def appUsersInstance = AppUsers.get(params.id)
        if (appUsersInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (appUsersInstance.version > version) {
                    
                    appUsersInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'appUsers.label', default: 'AppUsers')] as Object[], "Another user has updated this AppUsers while you were editing")
                    render(view: "edit", model: [appUsersInstance: appUsersInstance])
                    return
                }
            }
            appUsersInstance.properties = params
            def email = params.email
            if(email){
               appUsersInstance.email = email.trim()
             }

            if (!appUsersInstance.hasErrors() && appUsersInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'appUsers.label', default: 'AppUsers'), appUsersInstance.id])}"
                redirect(action: "show", id: appUsersInstance.id)
            }
            else {
                render(view: "edit", model: [appUsersInstance: appUsersInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appUsers.label', default: 'AppUsers'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def appUsersInstance = AppUsers.get(params.id)
        if (appUsersInstance) {
            try {
                appUsersInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'appUsers.label', default: 'AppUsers'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'appUsers.label', default: 'AppUsers'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appUsers.label', default: 'AppUsers'), params.id])}"
            redirect(action: "list")
        }
    }
}
