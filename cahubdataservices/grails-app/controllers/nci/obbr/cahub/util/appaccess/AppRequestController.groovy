package nci.obbr.cahub.util.appaccess
import grails.plugins.springsecurity.Secured
    
class AppRequestController {
    
    // static scaffold=AppRequest

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
       
       
        def applist=AppRequest.list()
      
        
        def sortedAppList
        if(applist?.size() > 0){
           
            sortedAppList = applist.sort({a,b-> a.lastUpdated.compareTo(b.lastUpdated)}).reverse()
        }
        
        
        [appRequestInstanceList: sortedAppList, total: AppRequest.count(), ]
    }
    
    def aclist = {
      
       
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
     
        def  total=AppRequest.findAllByStatusInList(['CREATED', 'SUBMITTED', 'ASSIGNED', 'TO_BE_COMPLETED']).size()
        def  applist=AppRequest.findAllByStatusInList(['CREATED', 'SUBMITTED', 'ASSIGNED','TO_BE_COMPLETED'], params)
       
        
     
        
     
    
        def sortedAppList=[]
        if(applist?.size() > 0){
           
            sortedAppList = applist.sort({a,b-> a.lastUpdated.compareTo(b.lastUpdated)}).reverse()
        }
        
        
        [appRequestInstanceList: sortedAppList, appRequestInstanceTotal: AppRequest.count(), total:total]
    }
    
    
    def inaclist = {
       
       
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
     
       
        def total=AppRequest.findAllByStatusInList(['CANCELLED','COMPLETED']).size()
        def applist=AppRequest.findAllByStatusInList(['CANCELLED','COMPLETED'], params)
        
        
        // def applist=AppRequest.list(params)
    
        def sortedAppList
        if(applist?.size() > 0){
           
            sortedAppList = applist.sort({a,b-> a.lastUpdated.compareTo(b.lastUpdated)}).reverse()
        }
        
        
        [appRequestInstanceList: sortedAppList, appRequestInstanceTotal: AppRequest.count(), total:total]
    }
    
     
    
   
    def create = {
        def appRequestInstance = new AppRequest()
        appRequestInstance.properties = params
        return [appRequestInstance: appRequestInstance]
    }

    def save = {
        def appRequestInstance = new AppRequest(params)
        if (appRequestInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), appRequestInstance.id])}"
            redirect(action: "show", id: appRequestInstance.id)
        }
        else {
            render(view: "create", model: [appRequestInstance: appRequestInstance])
        }
    }

    def show = {
        def appRequestInstance = AppRequest.get(params.id)
        if (!appRequestInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
            redirect(action: "list")
        }
        else {
            [appRequestInstance: appRequestInstance]
        }
    }

    def edit = {
        def appRequestInstance = AppRequest.get(params.id)
        if (!appRequestInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [appRequestInstance: appRequestInstance]
        }
    }

    def update = {
        def appRequestInstance = AppRequest.get(params.id)
        if (appRequestInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (appRequestInstance.version > version) {
                    
                    appRequestInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'appRequest.label', default: 'AppRequest')] as Object[], "Another user has updated this AppRequest while you were editing")
                    render(view: "edit", model: [appRequestInstance: appRequestInstance])
                    return
                }
            }
            appRequestInstance.properties = params
            if (!appRequestInstance.hasErrors() && appRequestInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), appRequestInstance.id])}"
                redirect(action: "show", id: appRequestInstance.id)
            }
            else {
                render(view: "edit", model: [appRequestInstance: appRequestInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def appRequestInstance = AppRequest.get(params.id)
      
        if (appRequestInstance) {
            try {
                appRequestInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'appRequest.label', default: 'AppRequest'), params.id])}"
            redirect(action: "list")
        }
    }
}
