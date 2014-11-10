package nci.obbr.cahub.util

import groovy.xml.MarkupBuilder
import grails.plugins.springsecurity.Secured 
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.ActivityType
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.staticmembers.CaseAttachmentType

class FileUploadController {

    def sendMailService
    def activityEventService
    def accessPrivilegeService
    def hubIdGenService
    
    def scaffold = FileUpload
    
    //static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static allowedMethods = [save: "POST", update: "POST"]
    
           
    def index = {
        redirect(action: "list", params: params)
    }
    
    
     
    def list = {
        //params.each{key,value->
        //println "in fileupload list key: ${key}   value:${value}"
        //}
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        if (!params.offset) params.offset = 0
        else params.offset = params.int('offset')
        
        def studyP = Study.findByCode(session?.study?.code)
        
        if (!params.sort) params.sort = "uploadTime"
        if (!params.order) params.order = "desc"
        def query = {
            
            eq("study", studyP)
            and {
                isNull("caseId")
            }
            
            if (params.sort){
                order(params.sort,params.order)
            }
        }
        
        def criteria = FileUpload.createCriteria()
        
        def fileUploadInstanceList = criteria.list(query)
        
        if (params.sort == "uploadTime")
        {
            if (params.order.equalsIgnoreCase('asc')) fileUploadInstanceList.sort({a,b-> transFormatDate(a.uploadTime).compareTo(transFormatDate(b.uploadTime))})
            else fileUploadInstanceList.sort({a,b-> transFormatDate(b.uploadTime).compareTo(transFormatDate(a.uploadTime))})
        }
        
        def fileUploadInstanceList2 = []
        def count = 0
        def totalCount = 0
        fileUploadInstanceList.each(){
            
            //String date = it.uploadTime
            //it.uploadTime = transFormatDate(date, false)
            if(session?.org?.code.equals("OBBR")){
                totalCount++
                if ((totalCount > params.offset)&&(count < params.max))
                {
                    fileUploadInstanceList2.add(it)
                    count++
                }
            }
            else
            {
                //println 'CCC Record ' + it.bssCode
                if(it.hideFromBss){

                }
                else if(!it.bssCode.equals(session?.org?.code) && !it.bssCode.equals('OBBR')){

                }
                else
                {
                    totalCount++
                    if ((totalCount > params.offset)&&(count < params.max))
                    {
                        fileUploadInstanceList2.add(it)
                        count++
                    }
                }
            }
        }
            
        
        
        [fileUploadInstanceList: fileUploadInstanceList2, fileUploadInstanceTotal: totalCount, isTest:false]
    }
    
    def create = {
        
        def fileUploadInstance = new FileUpload()
        fileUploadInstance.properties = params
        
        // hide the case recall categories for general file uploads
        def gencatList=CaseAttachmentType.executeQuery("from CaseAttachmentType where (name is null or name not like 'Case Recall%') and code != 'OTHER' order by name")
        gencatList.add(CaseAttachmentType.findByCode('OTHER'))
        
        return [fileUploadInstance: fileUploadInstance,gencatList:gencatList]
      
    }

    def save = {
        def fileUploadInstance = new FileUpload(params)
        
        def gencatList=CaseAttachmentType.executeQuery("from CaseAttachmentType where (name is null or name not like 'Case Recall%') and code != 'OTHER' order by name")
        gencatList.add(CaseAttachmentType.findByCode('OTHER'))
        //handle incoming file
        def uploadedFile = request.getFile('myFile')    
            
        def result= checkError(uploadedFile, fileUploadInstance)
            
        if(result){
            result.each{                
                fileUploadInstance.errors.reject(it, it)                
            }
            
            render(view: "create", model: [fileUploadInstance: fileUploadInstance,gencatList:gencatList])
            return            
        }
        
        
        if (fileUploadInstance.save(flush: true)) {
            def originalFileName = uploadedFile.originalFilename.replace(' ', '_') //replace whitespace with underscores
            def strippedFileName = originalFileName.substring(0,originalFileName.lastIndexOf('.'))                    
            def fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size())       
            def fileKey = hubIdGenService.genFilenameKey() //get an 8 character alpha numeric and append to filename. replaces former lengthy time in ms.
            def newFileName = strippedFileName + "-" + fileKey + "." + fileExtension
            
            def pathUploads
            def pathUploadsAttachments
            //path depends upon if it is a CASE specific upload or general upload
            if(fileUploadInstance.caseRecord){
                pathUploads = File.separator + 'var' + File.separator + 'storage' + File.separator + 'cdrds-filestore' + File.separator + session.study.code
                pathUploadsAttachments = pathUploads + File.separator + params.caseId + File.separator + 'attachments'
            }
            else{
                pathUploads = File.separator + 'var' + File.separator + 'storage' + File.separator + 'cdrds-filestore' + File.separator + session.org?.code
                pathUploadsAttachments = pathUploads + File.separator + 'attachments'
            }
            File dir = new File(pathUploadsAttachments)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            uploadedFile.transferTo( new File(pathUploadsAttachments, newFileName) )
                                                                       
            fileUploadInstance.filePath = pathUploadsAttachments
            fileUploadInstance.fileName = newFileName
            fileUploadInstance.uploadTime = new Date().getDateTimeString()              
            fileUploadInstance.bssCode = session.org?.code
            if(fileUploadInstance.caseRecord){
                fileUploadInstance.study=CaseRecord.get(params.caseRecord.id)?.study
            }
            fileUploadInstance.save()
            
            //sendMailService.sendFileUploadEventEmail(fileUploadInstance)
            def activityType = ActivityType.findByCode("FILEUPLOAD")
            def caseId = params.caseId
            def study = fileUploadInstance.study
            def bssCode =session.org?.code
            
            def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, newFileName, null)
            
            flash.message = "${message(code: 'default.uploaded.message', args: [message(code: 'fileUpload.label', default: 'File'), fileUploadInstance.fileName])}"
            if(fileUploadInstance.caseRecord){
                fileUploadInstance.caseRecord.index()
                redirect(controller: 'caseRecord', action: "display", params: [id:fileUploadInstance.caseRecord.id])
            }
            else{
                //redirect(controller: 'home', action: "more")
                redirect(action: "list")
            }
            
        }                                                               
        else {
            render(view: "create", model: [fileUploadInstance: fileUploadInstance,gencatList:gencatList])
        }
    }

    def show = {
        def fileUploadInstance = FileUpload.get(params.id)
        
        def caseRecord = fileUploadInstance.caseRecord
        
        if(fileUploadInstance && !fileUploadInstance.bssCode.equals(session?.org?.code) && !(session?.org?.code.equals('OBBR') && session.DM==true)){
            redirect(controller: "login", action: "denied")
            return
        }
        
        if (caseRecord && !accessPrivilegeService.checkAccessPrivUploads(caseRecord, session)) {
            redirect(controller: "login", action: "denied")
            return
        }
        if (!fileUploadInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), params.id])}"
            redirect(action: "list")
        }
        else {
            [fileUploadInstance: fileUploadInstance]
        }
    }

    def edit = {
        def fileUploadInstance = FileUpload.get(params.id)
        // hide the case recall categories for general file uploads
        def gencatList=CaseAttachmentType.executeQuery("from CaseAttachmentType where (name is null or name not like 'Case Recall%') and code != 'OTHER' order by name")
        gencatList.add(CaseAttachmentType.findByCode('OTHER'))
        
          if(fileUploadInstance && !fileUploadInstance.bssCode.equals(session?.org?.code) && !(session?.org?.code.equals('OBBR') && session.DM==true)){
            redirect(controller: "login", action: "denied")
            return
        }
        
        if (!fileUploadInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [fileUploadInstance: fileUploadInstance,gencatList:gencatList]
        }
    }

    def update = {
        
        def fileUploadInstance = FileUpload.get(params.id)  
        def caseRecord = fileUploadInstance.caseRecord
        // hide the case recall categories for general file uploads
        def gencatList=CaseAttachmentType.executeQuery("from CaseAttachmentType where (name is null or name not like 'Case Recall%') and code != 'OTHER' order by name")
        gencatList.add(CaseAttachmentType.findByCode('OTHER'))
        
        fileUploadInstance.properties = params
        //if(params.category.id.equals('null')){
        // fileUploadInstance.errors.reject("category", "Please enter category")
        // println "error category"
        // }
                
        if (fileUploadInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (fileUploadInstance.version > version) {
                    
                    fileUploadInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'fileUpload.label', default: 'FileUpload')] as Object[], "Another user has updated this FileUpload while you were editing")
                    render(view: "edit", model: [fileUploadInstance: fileUploadInstance,gencatList:gencatList])
                    return
                }
                
                
            }
            
            
            if (!fileUploadInstance.hasErrors() && fileUploadInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), fileUploadInstance.id])}"
                if(caseRecord){
                    caseRecord.index()
                    redirect(controller: 'caseRecord', action: "display", params: [id:caseRecord.id])
                }
                else{
                    //redirect(controller: 'home', action: "more")
                    redirect(action: "show", id: fileUploadInstance.id)
                }
                
            }
            else {
               
                render(view: "edit", model: [fileUploadInstance: fileUploadInstance,gencatList:gencatList])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN','ROLE_BSS_SC','ROLE_BSS_RPCI','ROLE_BSS_NDRI','ROLE_NCI-FREDERICK_CAHUB_DM','ROLE_NCI-FREDERICK_CAHUB_LDS','ROLE_BSS_UNM','ROLE_BSS_VUMC', 'ROLE_ORG_VARI','ROLE_ORG_BROAD'])
    def download = {
        def fileUploadInstance = FileUpload.get(params.id)
        if (!fileUploadInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fileUpload.label', default: 'FileUpload'), params.id])}"
            redirect(action: "list")
        }
        else {   
            def convertedFilePath = fileUploadInstance.filePath +"/"+fileUploadInstance.fileName
            def file = new File(convertedFilePath)
            if (file.exists()) {
                def inputStream = new FileInputStream(file)
                response.setContentType("application/octet-stream")
                response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
                response.outputStream << inputStream // Performing a binary stream copy
                inputStream.close()
                response.outputStream.close()
            } else {
                flash.message = "File not found, please remove: " + file.getName()
                //redirect(controller: 'caseRecord', action: "display", params: [id:fileUploadInstance.caseRecord.id])
                //In case there is no caseRecord for old data
                redirect(controller: 'caseRecord', action: "display", params: [id:CaseRecord.findByCaseId(fileUploadInstance.caseId).id])
            }
        }
    }
    
    def remove = {
        
        def fileUploadInstance = FileUpload.get(params.id)
        def caseRecord = fileUploadInstance.caseRecord
        
        if(fileUploadInstance && !fileUploadInstance.bssCode.equals(session?.org?.code) && !(session?.org?.code.equals('OBBR') && session.DM==true)){
            redirect(controller: "login", action: "denied")
            return
        }
        else{
            // println("caseRecord.id: " + caseRecord.id)
            def convertedFilePath = fileUploadInstance.filePath + File.separator + fileUploadInstance.fileName
            def file = new File(convertedFilePath)
            try {
                if (file.exists()) {
                    if (!file.delete()) {
                        throw new IOException("Failed to delete the file")
                    }
                }
                fileUploadInstance.delete(flush: true)
            } catch (Exception e) {
                e.printStackTrace()
                throw new RuntimeException(e.toString())
            }
        
            if(caseRecord){
                caseRecord.index()
                redirect(controller: 'caseRecord', action: "display", params: [id:caseRecord.id])
            }
            else{
                //redirect(controller: 'home', action: "more")
                redirect(action: "list")
            }
        }
    }
    
    
    
    static List checkError(uploadedFile, fileUploadInstance){
        def result = []
                
        if(fileUploadInstance.caseRecord){
            def caseId = fileUploadInstance.caseId
            if(!caseId){
                result.add("Case Id is a required field.")
            } 
            if(!fileUploadInstance.category.id){
                result.add("Category is a required field.")
            }  
        }
        
        if (uploadedFile.empty) {
            
            result.add("File can't be empty. Please choose a file.")             
        }
        else {
        
            def originalFileName = uploadedFile.originalFilename.toLowerCase()
            //            println "uploadedFile original name" + originalFileName
        
            // Custom validation                  
            if (originalFileName == null)  {      
                
                result.add("File can't be empty. Please choose a file.")
            }
            else if ( (! originalFileName.toString().endsWith(".pdf")) &&
                (! originalFileName.toString().endsWith(".zip")) &&
                (! originalFileName.toString().endsWith(".doc")) &&
                (! originalFileName.toString().endsWith(".docx"))) {
                      
              
                result.add("You can only upload a pdf, zip, doc or docx file type.  Please choose the right file to upload.")               
            }          
          
        }
        //println("result size:" + result.size())
        return result
        
    }
    private String transFormatDate(String dDate)
    {
        return transFormatDate(dDate, true)
    }
    private String transFormatDate(String dDate, boolean isForSort)
    {
        //"6/3/13 10:50:22 AM"
        String format1 = "M/d/yy hh:mm:ss a"
        String format2 = "yyyyMMddHHmmss"
        String format3 = "yyyy-MMM-d hh:mm:ss a"
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format1)
        String format
        Date date
        int n = 0
        while(true)
        {
            if (n == 0) format = format1
            else if (n == 1) format = format2
            else if (n == 2) format = format3
            else throw new Exception("Unparable date :" + dDate )
            try
            {
                sdf = new java.text.SimpleDateFormat(format)
                date = sdf.parse(dDate) 
            }
            catch(Exception)
            {
                date = null
            }
            if (date) break;
            n++
        }
               
        if (isForSort) format = format2
        else format = format1
        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat(format)
	String dateS = sdf2.format(date)
        //println 'a=' + dDate + ', b=' + dateS + ', isForSort=' + isForSort + ', n=' + n
        return dateS
    }

}
