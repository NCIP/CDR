package nci.obbr.cahub.util
import grails.plugins.springsecurity.Secured
import groovy.xml.MarkupBuilder

class HelpFileUploadController {
    
    def scaffold = HelpFileUpload
    

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [helpFileUploadInstanceList: HelpFileUpload.list(params), helpFileUploadInstanceTotal: HelpFileUpload.count()]
    }

    def create = {
        def helpFileUploadInstance = new HelpFileUpload()
        helpFileUploadInstance.properties = params
        return [helpFileUploadInstance: helpFileUploadInstance]
    }

    /**def save = {
    def helpFileUploadInstance = new HelpFileUpload(params)
    if (helpFileUploadInstance.save(flush: true)) {
    flash.message = "${message(code: 'default.created.message', args: [message(code: 'helpFileUpload.label', default: 'HelpFileUpload'), helpFileUploadInstance.id])}"
    redirect(action: "show", id: helpFileUploadInstance.id)
    }
    else {
    render(view: "create", model: [helpFileUploadInstance: helpFileUploadInstance])
    }
    }
     **/
    
    def save = {
        def helpFileUploadInstance = new HelpFileUpload(params)
         
        
        // params.each{key,value->  
        //   println "in help save key: ${key}   value:${value}"
        //}
        
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        def pathUploads = AppSetting.findByCode("HELP_GUIDE_PATH").value
        
        if(params.studyCode.equals('GTEX')){
            pathUploads=pathUploads+'gtex/'
        }
        else if(params.studyCode.equals('BPV')){
            pathUploads=pathUploads+'bpv/'
        }
        else if(params.studyCode.equals('OBBR')){
            pathUploads=pathUploads+'obbr/'
        }
        
        
        //handle incoming file
        def uploadedFile = request.getFile('fileName') 
      
        def resultmap= checkError(uploadedFile, helpFileUploadInstance)
       
        if(resultmap){   
            resultmap.each(){key, value->
                // println key
                helpFileUploadInstance.errors.reject(key, value)
               
            }
                     
        }
        
           
        def originalFileName = uploadedFile.originalFilename.replace(' ', '_') //replace whitespace with underscores
        helpFileUploadInstance.fileName =pathUploads+uploadedFile.originalFilename
       
        if(!resultmap){
            def strippedFileName = originalFileName?.substring(0,originalFileName?.lastIndexOf('.'))                    
            def fileExtension = originalFileName?.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size())                           
            //def newFileName = strippedFileName + "-" + Date.parse((new Date()).getDateTimeString()) + "." + fileExtension
            // def newFileName = strippedFileName  + "." + fileExtension
            // def newFileName =originalFileName
       
            File dir = new File(pathUploads)
            
            if (!dir.exists()) {
                dir.mkdirs()
            }
            // uploadedFile.transferTo( new File(pathUploads, newFileName) )
            uploadedFile.transferTo( new File(pathUploads, originalFileName) )
                                                                       
            helpFileUploadInstance.filePath = pathUploads 
            //helpFileUploadInstance.fileName = newFileName
            helpFileUploadInstance.fileName = originalFileName
            //helpFileUploadInstance.uploadTime = new Date().getDateTimeString()   
           
            helpFileUploadInstance.studyCode =params?.studyCode?.toUpperCase()
           
            helpFileUploadInstance.uploadedBy = username
            helpFileUploadInstance.save()
            
            //    sendMailService.sendFileUploadEventEmail(helpFileUploadInstance)            
            flash.message = "${message(code: 'default.uploaded.message', args: [message(code: 'fileUpload.label', default: 'File'), helpFileUploadInstance.fileName])}"
        
            redirect(action: "list", id: helpFileUploadInstance.id)
            return
        
        }
        render(view: "create", model: [helpFileUploadInstance: helpFileUploadInstance])
       
    }


    def show = {
        def helpFileUploadInstance = HelpFileUpload.get(params.id)
        if (!helpFileUploadInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpFileUpload.label', default: 'HelpFileUpload'), params.id])}"
            redirect(action: "list")
        }
        else {
            [helpFileUploadInstance: helpFileUploadInstance]
        }
    }

    def edit = {
        def helpFileUploadInstance = HelpFileUpload.get(params.id)
        if (!helpFileUploadInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpFileUpload.label', default: 'HelpFileUpload'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [helpFileUploadInstance: helpFileUploadInstance]
        }
    }

    def update = {
        def helpFileUploadInstance = HelpFileUpload.get(params.id)
        if (helpFileUploadInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (helpFileUploadInstance.version > version) {
                    
                    helpFileUploadInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'helpFileUpload.label', default: 'HelpFileUpload')] as Object[], "Another user has updated this HelpFileUpload while you were editing")
                    render(view: "edit", model: [helpFileUploadInstance: helpFileUploadInstance])
                    return
                }
            }
            helpFileUploadInstance.properties = params
            if (!helpFileUploadInstance.hasErrors() && helpFileUploadInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'helpFileUpload.label', default: 'HelpFileUpload'), helpFileUploadInstance.id])}"
                redirect(action: "show", id: helpFileUploadInstance.id)
            }
            else {
                render(view: "edit", model: [helpFileUploadInstance: helpFileUploadInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpFileUpload.label', default: 'HelpFileUpload'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def helpFileUploadInstance = HelpFileUpload.get(params.id)
        if (helpFileUploadInstance) {
            try {
                helpFileUploadInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'helpFileUpload.label', default: 'HelpFileUpload'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'helpFileUpload.label', default: 'HelpFileUpload'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpFileUpload.label', default: 'HelpFileUpload'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    static Map checkError(uploadedFile, helpFileUploadInstance){
        def result = [:]
                
        if (!helpFileUploadInstance.studyCode) {
            
            result.put("studyCode", "Please choose Study code- Whos is this user guide for? GTeX, BPV or OBBR?")             
        }
       
        if (uploadedFile.empty) {
            
            result.put("fileName", "File can't be empty. Please choose a file")             
        }
        else {
        
            def originalFileName = uploadedFile.originalFilename
            // println "in erorr check uploadedFile original name" + originalFileName
        
                              
            if (originalFileName == null)  {      
                
                result.put("fileName", "File can't be empty. Please choose a file")
            }
            else if ( (! originalFileName.toString().endsWith(".pdf")) &&
                (! originalFileName.toString().endsWith(".zip")) &&
                (! originalFileName.toString().endsWith(".PDF")) &&
                (! originalFileName.toString().endsWith(".ZIP")) &&
                (! originalFileName.toString().endsWith(".doc")) &&
                (! originalFileName.toString().endsWith(".docx")) &&
                (! originalFileName.toString().endsWith(".DOC")) &&
                (! originalFileName.toString().endsWith(".DOCX"))) {
                      
              
                result.put("fileName", "You can only upload a pdf, zip, doc or docx file type.  Please choose the right file to upload.")               
            }        
          
        }
       
        return result
        
    }
}
