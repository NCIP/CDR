package nci.obbr.cahub.forms.common.withdraw

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.BSS

import nci.obbr.cahub.forms.common.withdraw.CaseWithdraw
import nci.obbr.cahub.forms.common.withdraw.CaseWithdrawForms
import nci.obbr.cahub.util.AppSetting
import nci.obbr.cahub.util.FileUpload
import nci.obbr.cahub.staticmembers.CaseAttachmentType 

import grails.plugins.springsecurity.Secured
import groovy.xml.MarkupBuilder

class CaseWithdrawController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def caseWithdrawService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [caseWithdrawInstanceList: CaseWithdraw.list(params), caseWithdrawInstanceTotal: CaseWithdraw.count()]
    }

    def wd_cases ={
        def caseList = CaseRecord.executeQuery("select c from CaseRecord c where  c.caseStatus.code  = 'WITHDR' order by c.study.code, c.caseId")
        [caseList:caseList]
    }  
    
    def create = {
              
        def caseRecordInstance = CaseRecord.findById(params.caseid)
        
        //display brainbank in the approval page entity list only if the case has the braintime input
        def hasBrain=SpecimenRecord.findByCaseRecordAndBrainTimeStartRemovalIsNotNull(caseRecordInstance) ? 'YES' :'NO'
        
        def caseWithdrawInstance
        def caseWithdrawFormsInstance
        caseWithdrawInstance = CaseWithdraw.findByCaseRecord(caseRecordInstance)
        def fuploads
        def authority= params.authority
        
        def chkSignErrorsOnEntry='NO'
        def canSubmit = 'No'
        
        if(authority.equals('ELR')){
            if (caseWithdrawInstance.elrReviewer){
                canSubmit='Yes'
            }
            if(caseWithdrawInstance.elrSectionD){
                chkSignErrorsOnEntry='YES'
            }
        }
       
        if(authority.equals('QM')){
            if (caseWithdrawInstance.qmReviewer){
                canSubmit='Yes'
            }
            if(caseWithdrawInstance.qmSectionD){
                chkSignErrorsOnEntry='YES'
            }
        }
       
        if(authority.equals('OPS')){
            if (caseWithdrawInstance.opsReviewer){
                canSubmit='Yes'
            }
            if(caseWithdrawInstance.opsSectionD){
                chkSignErrorsOnEntry='YES'
            }
        }
        
        
        if(authority.equals('DIRECTOR')){
            if (caseWithdrawInstance.directorReviewer){
                canSubmit='Yes'
            }
            if(caseWithdrawInstance.drSectionD){
                chkSignErrorsOnEntry='YES'
            }
        }
        
        if(chkSignErrorsOnEntry?.equals('YES')){
            def serrorMap = checkError(caseWithdrawInstance,authority)
            serrorMap.each() {key, value ->
                caseWithdrawInstance.errors.rejectValue(key, value)
            }
        }
        
        if(authority.contains('CDR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCDR'))
        }
        if(authority.contains('CBR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCBR'))
        }
        if(authority.contains('PRC')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRPRC'))
        }
        if(authority.contains('LDACC')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRLDACC'))
        }
        if(authority.contains('BRAINBANK')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRBRAINBANK'))
        }
        if(authority.contains('ELR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRELR'))
        }
        if(authority.contains('QM')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRQM'))
        }
        if(authority.contains('OPS')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CROPS'))
        }
        if(authority.contains('DIRECTOR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRDIRECTOR'))
        }
        if(authority.contains('BSSCOMPLETE')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCOMPLETE'))
        }
        
     
        if(authority.contains('FL')){
            authority=authority[0..-3]
        }
        caseWithdrawFormsInstance=CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance,authority)
        
        if(fuploads){
            
            return [caseWithdrawInstance: caseWithdrawInstance, caseWithdrawFormsInstance:caseWithdrawFormsInstance, authority:params.authority, canSubmit:canSubmit, fuploads:fuploads,hasBrain:hasBrain]
        }
        else{
            return [caseWithdrawInstance: caseWithdrawInstance, caseWithdrawFormsInstance:caseWithdrawFormsInstance, authority:params.authority, canSubmit:canSubmit,hasBrain:hasBrain]
        }
        
       
    }
    

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])     
    def delete = {
        def caseWithdrawInstance = CaseWithdraw.get(params.id)
       
        if (caseWithdrawInstance) {
            try {
                caseWithdrawInstance.delete(flush: true)
               
               
                flash.message = "Deleted Recall ID: "+params.id
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
               
                flash.message = "Deleted Recall ID: "+params.id
                redirect(action: "show", id: params.id)
            }
        }
        else {
           
            flash.message = "Deleted Recall ID: "+params.id
            redirect(action: "list")
        }
    }
    
    
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
             
                redirect(action: "list")
            }
        }
    } 
    
    
    def edit = {
        
       
        def caseWithdrawInstance = CaseWithdraw.get(params.wid)
        
        //display brainbank in the approval page entity list only if the case has the braintime input
        def hasBrain=SpecimenRecord.findByCaseRecordAndBrainTimeStartRemovalIsNotNull(caseWithdrawInstance.caseRecord) ? 'YES' :'NO'
        
        
        def caseWithdrawFormsInstance = CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance,params.authority)
        
        def fuploads
        
        if(!params.authority){
            // redirect(action: "listAllRecallCases")
            redirect(action: "wdcmain", params: [id:caseWithdrawInstance.caseRecord.id])
           
        }
        if(params.authority.contains('CDR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCDR'))
        }
        if(params.authority.contains('CBR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCBR'))
        }
        if(params.authority.contains('PRC')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRPRC'))
        }
        if(params.authority.contains('LDACC')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRLDACC'))
        }
        if(params.authority.contains('BRAINBANK')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRBRAINBANK'))
        }
        if(params.authority.contains('ELR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRELR'))
        }
        if(params.authority.contains('QM')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRQM'))
        }
        if(params.authority.contains('OPS')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CROPS'))
        }
        if(params.authority.contains('DIRECTOR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRDIRECTOR'))
        }
        if(params.authority.contains('BSSCOMPLETE')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCOMPLETE'))
        }
        
        if (!caseWithdrawInstance || caseWithdrawFormsInstance) {
           
            redirect(action: "listAllRecallCases")
        }
        else {
            
            if(fuploads){
                return [caseWithdrawInstance: caseWithdrawInstance,caseWithdrawFormsInstance:caseWithdrawFormsInstance,fuploads:fuploads,hasBrain:hasBrain]
            }
            else{
                return [caseWithdrawInstance: caseWithdrawInstance,caseWithdrawFormsInstance:caseWithdrawFormsInstance,hasBrain:hasBrain]
            }
            
        
        }
    }
    
    def editWithValidation = {
        
        def caseWithdrawInstance = CaseWithdraw.get(params.wid)
        
        //display brainbank in the approval page entity list only if the case has the braintime input
        def hasBrain=SpecimenRecord.findByCaseRecordAndBrainTimeStartRemovalIsNotNull(caseWithdrawInstance.caseRecord) ? 'YES' :'NO'
       
        def caseWithdrawFormsInstance = CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance,params.authority)
        def authority=params.authority
        def caseid=params.caseid
        def canSubmit = 'No'
        def fuploads
        if (!caseWithdrawInstance && !caseWithdrawFormsInstance) {
            
            redirect(action: "listAllRecallCases")
        }
        else {
            if(authority?.equals('BSS')|| authority?.equals('APR')|| authority?.equals('BSSCOMPLETE')){
             
                def errorMap = checkError(caseWithdrawInstance,authority)
                
                errorMap.each() {key, value ->
                    caseWithdrawInstance.errors.rejectValue(key, value)
                }
            
                if (errorMap.size() == 0) {
                    canSubmit = 'Yes'
                }
                else{
                    flash.message=""
                }
              
            }
            else{
                if(authority?.equals('CDR')|| authority?.equals('CBR')|| authority?.equals('PRC')|| authority?.equals('LDACC')|| authority?.equals('BRAINBANK')){
             
                    def ferrorMap =checkFormsError(caseWithdrawFormsInstance,authority )
                    ferrorMap.each() {key, value ->
                        
                        caseWithdrawFormsInstance.errors.rejectValue(key, value)
                      
                    }
            
                    if (ferrorMap.size() == 0) {
                        canSubmit = 'Yes'
                    }
                    else{
                        flash.message=""
                    }
                    
                
                }
                           
            }
             
        }
        
        if(authority.contains('CDR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCDR'))
        }
        if(authority.contains('CBR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCBR'))
        }
        if(authority.contains('PRC')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRPRC'))
        }
        if(authority.contains('LDACC')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRLDACC'))
        }
        if(authority.contains('BRAINBANK')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRBRAINBANK'))
        }
        if(authority.contains('ELR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRELR'))
        }
        if(authority.contains('QM')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRQM'))
        }
        if(authority.contains('OPS')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CROPS'))
        }
        if(authority.contains('DIRECTOR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRDIRECTOR'))
        }
        if(authority.contains('BSSCOMPLETE')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCOMPLETE'))
        }
        
        
        if(fuploads){  
            
            render(view: "edit", model: [caseWithdrawInstance: caseWithdrawInstance, caseWithdrawFormsInstance:caseWithdrawFormsInstance,canSubmit: canSubmit, authority:authority, caseid:caseid,wid:caseWithdrawInstance.id,fuploads:fuploads,hasBrain:hasBrain])
        }
        else{
            render(view: "edit", model: [caseWithdrawInstance: caseWithdrawInstance, caseWithdrawFormsInstance:caseWithdrawFormsInstance,canSubmit: canSubmit, authority:authority, caseid:caseid,wid:caseWithdrawInstance.id,hasBrain:hasBrain])
        }
        
        
        
    }
    
    
    
    def entitiesFileUpload = {
        
       
        def entity=params.authority+'FL'
         
         
         
        def caseWithdrawInstance = CaseWithdraw.get(params.wid)
        
        def caseWithdrawFormsInstance = CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance,params.authority)
        //save the form before going to the upload page
        if(params.authority?.equals('CDR')|| params.authority?.equals('CBR')|| params.authority?.equals('PRC')|| params.authority?.equals('LDACC')|| params.authority?.equals('BRAINBANK')){
            caseWithdrawFormsInstance.properties = params
          
            flash.message = caseWithdrawService.saveThisInstance(caseWithdrawFormsInstance,params.authority,'saved', null)
        }
        else {
            caseWithdrawInstance.properties = params
            flash.message = caseWithdrawService.saveThisInstance(caseWithdrawInstance,params.authority,'saved', null)
        }
         
        redirect(action: "create", params: [caseid:params.id, authority:entity])
    }
    
    
    def finalReview = {
            
        //  params.each{key,value->
        //      println "in withdraw consent finalReview key: ${key}   value:${value}"
        //  }
         
        def caseRecordInstance = CaseRecord.findById(params.caseid)
        def caseWithdrawInstance
        def authority=params.authority
       
        caseWithdrawInstance = CaseWithdraw.findByCaseRecord(caseRecordInstance)
        def finalReview = caseWithdrawService.checkFinalReviewGoStatus(caseWithdrawInstance)
     
       
        if(finalReview.equals('YES')){
           
            redirect(action: "create", params: [caseid:caseWithdrawInstance.caseRecord.id, authority:authority])
        }
        else{    
            redirect(action: "wdcmain", params: [id:caseWithdrawInstance.caseRecord.id])
        }
    }
    
    
    def listAllRecallCases = {
    
        def count
        def caseList = []
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        
        def max=params.max
        def offset = params.offset
        if(!offset)
        offset = 0

        def study = session.study
        if(params.s) {
            study = Study.findByCode(params.s.toUpperCase())
            session.study = study
        }
        

        if(session.org.code.matches("OBBR")|| session.org.code.matches("BROAD")||session.org.code.matches("VARI")) {
         
              
            def result_count = CaseWithdraw.executeQuery("select distinct c from CaseWithdraw w inner join w.caseRecord c where c.study.id=? order by c.dateCreated desc", [study.id])
                   
            count = result_count.size() 
              
         
              
            caseList = CaseWithdraw.executeQuery("select distinct c from CaseWithdraw w inner join w.caseRecord c where c.study.id=? order by c.dateCreated desc", [study.id],[max:max,offset:offset])
           
           
        }else if (session.org.code.matches("MBB")){
            def result_count =SpecimenRecord.executeQuery("select count(distinct c) from SpecimenRecord s inner join s.caseRecord c where s.brainTimeStartRemoval is not null and c in (select distinct c from CaseWithdraw w inner join w.caseRecord c where c.study.id=?) order by c.dateCreated desc",[study.id])
            if(result_count)
            count = result_count.get(0)
            caseList = SpecimenRecord.executeQuery("select distinct c from SpecimenRecord s inner join s.caseRecord c where s.brainTimeStartRemoval is not null and c in (select distinct c from CaseWithdraw w inner join w.caseRecord c where c.study.id=?) order by c.dateCreated desc",[study.id], [max:max,offset:offset])
             
        } else {
            
            def bss = BSS.findByCode(session.org.code)

            // get all bss, parent and subs
            def bssList = BSS.findAllByParentBss(bss)
            
            
            def result_count = CaseWithdraw.executeQuery("select distinct c from CaseWithdraw w inner join w.caseRecord c where c.bss in(:list) order by c.dateCreated desc", [list: bssList])
                   
            count = result_count.size() 
              
          
              
            caseList = CaseWithdraw.executeQuery("select distinct c from CaseWithdraw w inner join w.caseRecord c where c.bss in(:list) order by c.dateCreated desc", [list: bssList], [max:max,offset:offset])
            print caseList.size()
            
          
            
        }
        
        // for each case find out if withdraw process form(cbr,prc,ldacc, brainbank) has been started or not
        def fmap= caseWithdrawService.getFormProgress(caseList)

        return [caseRecordInstanceList: caseList, caseRecordInstanceTotal: count, fmap:fmap]
    }
    
    
    
    def requestRecallForm = {
        def count
        def caseList = []
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        
        def max=params.max
        def offset = params.offset
        if(!offset)
        offset = 0

        def study = session.study
        if(params.s) {
            study = Study.findByCode(params.s.toUpperCase())
            session.study = study
        }

        if(session.org.code.matches("OBBR")|| session.org.code.matches("BROAD")||session.org.code.matches("VARI")||session.org.code.matches("MBB")) {
           
           
            caseList = CaseRecord.executeQuery("select c from CaseRecord c where c.study.id=?  and c not in (select distinct c2 from CaseWithdraw w inner join w.caseRecord c2) order by c.caseId", [study.id],[max:max,offset:offset])
           

        } else {
            
            
            def bss = BSS.findByCode(session.org.code)

            // get all bss, parent and subs
            def bssList = BSS.findAllByParentBss(bss)
            
              
            caseList = CaseWithdraw.executeQuery("select c from CaseRecord c where c.bss in(:list) and c not in (select distinct c2 from CaseWithdraw w inner join w.caseRecord c2) order by c.caseId", [list: bssList], [max:max,offset:offset])
            //print caseList.size()
            
          
            
        }

        return [caseRecordInstanceList: caseList]
    }
    
    
    def recallComplete = {
            
        //  params.each{key,value->
        //      println "in withdraw consent finalReview key: ${key}   value:${value}"
        //  }
         
        def caseRecordInstance = CaseRecord.findById(params.caseid)
        def caseWithdrawInstance
        def authority=params.authority
        def finalReview='YES'
        caseWithdrawInstance = CaseWithdraw.findByCaseRecord(caseRecordInstance)
        if (caseWithdrawInstance.finalCanNotifyDate ||  caseWithdrawInstance.finalCanNotifyDate|| caseWithdrawInstance.finalNotifiedDate|| caseWithdrawInstance.finalprovideProofDate || caseWithdrawInstance.finalNotifyviaMail || caseWithdrawInstance.finalNotifyviaPhone ||  caseWithdrawInstance.finalNotifyviaEmail || caseWithdrawInstance.finalNotifyviaOth  ){
                
            finalReview='CONTINUE'
       
        }
        
       
        if(finalReview.equals('YES')){
            redirect(action: "create", params: [caseid:caseWithdrawInstance.caseRecord.id, authority:authority])
            
        }
        else{  
            redirect(action:"editWithValidation",id: caseWithdrawInstance.id, params:[authority:authority, caseid:caseWithdrawInstance.caseRecord.id, wid:caseWithdrawInstance.id])
            // redirect(action: "wdcmain", params: [id:caseWithdrawInstance.caseRecord.id])
        }
    }
    
    
    def remove = {
        def caseWithdrawInstance = CaseWithdraw.get(params.id)
        flash.message=caseWithdrawService.remove(caseWithdrawInstance,params.authority)
       
        redirect(action: "create", params: [caseid:caseWithdrawInstance.caseRecord.id, authority:params.authority])
    }
    
    def removeFile = {
         
        def fileUploadInstance = FileUpload.get(params.id)
        def caseWithdrawInstance = CaseWithdraw.get(params.wid)
        def convertedFilePath = fileUploadInstance.filePath + File.separator + fileUploadInstance.fileName
        def file = new File(convertedFilePath)
        try {
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException("Failed to delete the file")
                }
            }
            fileUploadInstance.delete()
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
        //redirect(controller: 'caseRecord', action: "display", params: [id:fileUploadInstance.caseRecord.id])
        //In case there is no caseRecord for old data
        redirect(action: "create", params: [caseid:caseWithdrawInstance.caseRecord.id, authority:params.authority])
    }
    
    
    def complete_review = {
        def caseWithdrawInstance = CaseWithdraw.get(params.id)
        flash.message=caseWithdrawService.completeReview(caseWithdrawInstance,params.authority)
        
        redirect(action: "wdcmain", params: [id:caseWithdrawInstance.caseRecord.id])
        // redirect(action:"editWithValidation",id: caseWithdrawInstance.id, params:[authority:params.authority, wid:caseWithdrawInstance.id])
    }
    
    def save = {
     
        def authority=params.authority
        
        def caseid= params.id
        def caseRecordInstance = CaseRecord.findById(params.id)
        def caseWithdrawInstance
        def caseWithdrawFormsInstance
                
        caseWithdrawInstance = CaseWithdraw.findByCaseRecord(caseRecordInstance)
        
        
        
        // now save the form fields in the relevant object instance. FL stands for file uploads
        
        if(authority?.equals('CDR')|| authority?.equals('CBR')|| authority?.equals('PRC')|| authority?.equals('LDACC')|| authority?.equals('BRAINBANK')){
            caseWithdrawFormsInstance = CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance,authority)              
            caseWithdrawFormsInstance.properties = params
            caseWithdrawFormsInstance.hasStarted = 'true'
        }
        
        
        if(authority?.equals('BSS')|| authority?.equals('APR')|| authority?.contains('BSSCOMPLETE')){
            caseWithdrawInstance.properties = params
        }
        
        // now begin to save the instance and any given file
        if(authority?.equals('BSS')|| authority?.equals('APR')|| authority?.contains('BSSCOMPLETE')){
          
            
            try{
               
                if(authority.contains('BSSCOMPLETE')){
                    
                    flash.message = caseWithdrawService.saveThisInstance(caseWithdrawInstance,authority,'saved', request)
                    authority='BSSCOMPLETE'
                }
                else{
                    flash.message = caseWithdrawService.saveThisInstance(caseWithdrawInstance,authority,'saved', null)
                }
                
                
               
                redirect(action:"editWithValidation",id: caseWithdrawInstance.id, params:[authority:authority, caseid:caseid, wid:caseWithdrawInstance.id])
 
            }
            catch(Exception e){
                flash.message="Failed: " + e.toString()
                render(view: "create", model: [caseWithdrawInstance: caseWithdrawInstance, caseWithdrawFormsInstance:caseWithdrawFormsInstance,caseid:caseid, authority:authority])
            }
        } 
        else if(authority?.equals('CDR')|| authority?.equals('CBR')|| authority?.equals('PRC')|| authority?.equals('LDACC')|| authority?.equals('BRAINBANK')){
           
           
            try{
                flash.message = caseWithdrawService.saveThisInstance(caseWithdrawFormsInstance,authority,'saved', null)
                
                redirect(action:"editWithValidation",id: caseWithdrawInstance.id,  params:[authority:authority, caseid:caseid, wid:caseWithdrawInstance.id])
 
            }
            catch(Exception e){
                flash.message="Failed: " + e.toString()
                render(view: "create", model: [caseWithdrawInstance: caseWithdrawInstance, caseWithdrawFormsInstance:caseWithdrawFormsInstance,caseid:caseid, authority:authority])
            }
            
        }
        else if(authority?.equals('CDRFL')|| authority?.equals('CBRFL')|| authority?.equals('PRCFL')|| authority?.equals('LDACCFL')|| authority?.equals('BRAINBANKFL')){
           
           
            try{
                flash.message = caseWithdrawService.saveThisInstance(caseWithdrawInstance,authority,'saved', request)
                // make sure the page returns to the entity page
                authority=authority[0..-3]
               
                // redirect(action:"create", params:[caseid:caseWithdrawInstance.caseRecord.id,authority:authority])
                redirect(action:"editWithValidation",id: caseWithdrawInstance.id,  params:[authority:authority, caseid:caseid, wid:caseWithdrawInstance.id])
 
            }
            catch(Exception e){
                flash.message="Failed: " + e.toString()
                render(view: "create", model: [caseWithdrawInstance: caseWithdrawInstance, caseWithdrawFormsInstance:caseWithdrawFormsInstance,caseid:caseid, authority:authority])
            }
            
        }
        else{
            
            //def chkSignErrors='YES'
            //def serrorMap = checkError(caseWithdrawInstance,authority)
            flash.message=caseWithdrawService.saveSignature(caseWithdrawInstance, params, request,false)
            
            if(authority.contains('FL')){
                authority=authority[0..-3]
                //redirect(action:"create", params:[caseid:caseWithdrawInstance.caseRecord.id,authority:authority])
            }
            
            //redirect(action:"create", params:[caseid:caseWithdrawInstance.caseRecord.id,authority:authority,chkSignErrors:chkSignErrors])
            redirect(action:"create", params:[caseid:caseWithdrawInstance.caseRecord.id,authority:authority])
            
        }
    }
    
    
    def show = {
        
        // params.each{key,value->
        //     println "in withdraw consent SHOW key: ${key}   value:${value}"
        // }
        def authority=params.authority
        def fuploads
        
        if(!authority){
            authority='ALL'
        }
        def caseWithdrawInstance = CaseWithdraw.get(params.wid)
        
        //display brainbank in the approval page entity list only if the case has the braintime input
        def hasBrain=SpecimenRecord.findByCaseRecordAndBrainTimeStartRemovalIsNotNull(caseWithdrawInstance.caseRecord) ? 'YES' :'NO'
        
        
        def caseWithdrawFormsInstance = CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance,params.authority)
        
        if(authority.contains('CDR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCDR'))
        }
        if(authority.contains('CBR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCBR'))
        }
        if(authority.contains('PRC')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRPRC'))
        }
        if(authority.contains('LDACC')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRLDACC'))
        }
        if(authority.contains('BRAINBANK')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRBRAINBANK'))
        }
        if(authority.contains('ELR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRELR'))
        }
        if(authority.contains('QM')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRQM'))
        }
        if(authority.contains('OPS')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CROPS'))
        }
        if(authority.contains('DIRECTOR')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRDIRECTOR'))
        }
        if(authority.contains('BSSCOMPLETE')){
            
            fuploads=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCOMPLETE'))
        }
        
        if (!caseWithdrawInstance && !caseWithdrawFormsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'withdrawConsent.label', default: 'CaseWithdraw'), params.id])}"
             
            redirect(action: "listAllRecallCases")
        }
        else {
            
            if(fuploads){
                return [caseWithdrawInstance: caseWithdrawInstance, caseWithdrawFormsInstance:caseWithdrawFormsInstance,authority:authority,fuploads:fuploads,hasBrain:hasBrain]
            }
            else{
                return  [caseWithdrawInstance: caseWithdrawInstance, caseWithdrawFormsInstance:caseWithdrawFormsInstance,authority:authority,hasBrain:hasBrain]
            }
         
        }
    }
    
    
    def showAll = {
        
        def fuploadcdr
        def fuploadcbr
        def fuploadprc
        def fuploadldacc
        def fuploadbrainbank
        def fuploadelr
        def fuploadops
        def fuploadqm
        def fuploaddirector
        def fuploadcomplete
            
       
        def caseWithdrawInstance = CaseWithdraw.get(params.id)
        
        fuploadcdr=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCDR'))
        
        
            
        fuploadcbr=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCBR'))
       
       
            
        fuploadprc=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRPRC'))
       
       
            
        fuploadldacc=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRLDACC'))
        
        
            
        fuploadbrainbank=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRBRAINBANK'))
        
      
            
        fuploadelr=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRELR'))
       
        
            
        fuploadqm=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRQM'))
       
        
            
        fuploadops=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CROPS'))
     
        
            
        fuploaddirector=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRDIRECTOR'))
            
        
            
        fuploadcomplete=FileUpload.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId, CaseAttachmentType.findByCode('CRCOMPLETE'))
        
            
        def wfiList = CaseWithdrawForms.findAllByCaseWithdraw(caseWithdrawInstance,[sort:'authority',order:'asc'])
        // [caseWithdrawInstance: caseWithdrawInstance, wfiList:wfiList]
        [caseWithdrawInstance: caseWithdrawInstance, wfiList:wfiList, fuploadcdr:fuploadcdr,fuploadcbr:fuploadcbr, fuploadprc:fuploadprc, fuploadldacc:fuploadldacc,fuploadbrainbank:fuploadbrainbank, fuploadelr:fuploadelr, fuploadops:fuploadops, fuploadqm:fuploadqm, fuploaddirector:fuploaddirector,fuploadcomplete:fuploadcomplete]
        
    }
    
    
    
    def submit = {
        //println("start submit????????")
        // params.each{key,value->
        //     println "in withdraw consent SUBMIT key: ${key}   value:${value}"
        //  }
        def caseWithdrawInstance = CaseWithdraw.get(params.wid)
        def caseWithdrawFormsInstance 
        def authority=params.authority
        def caseid=params.caseRecord.id
        
        def username=session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        
        if(authority?.equals('CDR')|| authority?.equals('CBR')|| authority?.equals('PRC')|| authority?.equals('LDACC')|| authority?.equals('BRAINBANK')){
           
            caseWithdrawFormsInstance = CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance,authority)
            
        }
        
        if(authority?.equals('BSS')|| authority?.equals('APR')|| authority?.equals('BSSCOMPLETE')){
            caseWithdrawInstance.properties = params
           
            def errorMap =checkError(caseWithdrawInstance,authority )
            errorMap.each() {key, value ->
                caseWithdrawInstance.errors.rejectValue(key, value)
            }
            try{
            
                if (!caseWithdrawInstance.hasErrors() ){
              
                    caseWithdrawService.recallNotifyEmail(caseWithdrawInstance, authority, username)
                    
                    redirect(action: "wdcmain", params: [id:caseWithdrawInstance.caseRecord.id])
                } 
                 
                else{
                    flash.message =" errors while saving the instance. "
                    //redirect(action: "wdcmain", params: [id:caseWithdrawInstance.caseRecord.id])
                    redirect(action:"editWithValidation",id: caseWithdrawInstance.id, params:[authority:authority, caseid:caseid, wid:caseWithdrawInstance.id])
                }
                
            
            }
            catch(Exception e){
                
                flash.message="Failed: " + e.toString()
                caseWithdrawInstance.discard()
                redirect(action: "listAllRecallCases")
            }
            
        }
        
        else if(authority?.equals('CDR')|| authority?.equals('CBR')|| authority?.equals('PRC')|| authority?.equals('LDACC')|| authority?.equals('BRAINBANK')){
      
            caseWithdrawFormsInstance.properties = params
            
            
            def ferrorMap =checkFormsError(caseWithdrawFormsInstance,authority )
            ferrorMap.each() {key, value ->
               
                caseWithdrawFormsInstance.errors.rejectValue(key, value)
            }
            
            try{
                if (!caseWithdrawFormsInstance.hasErrors() ){
                    flash.message = caseWithdrawService.saveThisInstance(caseWithdrawFormsInstance,authority,'submitted', null)
               
                    caseWithdrawService.recallNotifyEmail(caseWithdrawInstance, authority, username)
                    
                    def finalReview = caseWithdrawService.checkFinalReviewGoStatus(caseWithdrawInstance)
                    
                    redirect(action: "wdcmain", params: [id:caseWithdrawInstance.caseRecord.id,finalReview:finalreview])
                    return
                
                }
                else{
                    redirect(action:"editWithValidation",id: caseWithdrawInstance.id, params:[authority:authority, caseid:caseid, wid:caseWithdrawInstance.id])
                }
            }
            catch(Exception e){
                
                caseWithdrawFormsInstance.discard()
                
                // redirect(action: "listAllRecallCases") 
                redirect(action: "wdcmain", params: [id:caseWithdrawInstance.caseRecord.id])
                                                                                
            }
            
        }
       
    }
    def submitSignature ={
     
        def caseRecordInstance = CaseRecord.findById(params.id)
        def caseWithdrawInstance
        def authority = params.authority
                
        caseWithdrawInstance = CaseWithdraw.findByCaseRecord(caseRecordInstance)
        
        def serrorMap = checkError(caseWithdrawInstance,authority)
        serrorMap.each() {key, value ->
            caseWithdrawInstance.errors.rejectValue(key, value)
        }
        if(serrorMap.size()== 0){
            flash.message=caseWithdrawService.saveSignature(caseWithdrawInstance, params, request, true)
        }
       
        redirect(action: "wdcmain", params: [id:params.id])
        
    }
    
    def update = {
        
        //params.each{key,value->
        //  println "in withdraw consent UPDATE key: ${key}   value:${value}"
        //  }
        def authority=params.authority
        def caseid=params.caseRecord.id
        
        def caseWithdrawInstance = CaseWithdraw.get(params.wid)
        def caseWithdrawFormsInstance = CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance,params.authority)
        
        if(authority?.equals('BSS')|| authority?.equals('APR')|| authority?.equals('BSSCOMPLETE')){
           
            
            caseWithdrawInstance.properties = params
            
            // if(params.caseStatus){
               
            // caseWithdrawInstance.caseStatus=params.caseStatus
            //  }
            try{
                if (!caseWithdrawInstance.hasErrors() ){
                    flash.message = caseWithdrawService.saveThisInstance(caseWithdrawInstance,authority,'updated', null) 
                            
                    redirect(action:"editWithValidation",id: caseWithdrawInstance.id, params:[authority:authority, caseid:caseid, wid:caseWithdrawInstance.id])
           
                }
            }
            catch(Exception e) {
                
                render(view: "edit", model: [caseWithdrawInstance: caseWithdrawInstance,caseWithdrawFormsInstance:caseWithdrawFormsInstance, authority:authority, caseid:caseid,wid:caseWithdrawInstance.id])
                
            }
            
        }
        
        if(authority?.equals('CDR')|| authority?.equals('CBR')|| authority?.equals('PRC')|| authority?.equals('LDACC')|| authority?.equals('BRAINBANK')){
            
            
            
            caseWithdrawFormsInstance.properties = params
            
            // if(params.caseStatus){
               
            //    caseWithdrawInstance.caseStatus=params.caseStatus
            //   }
            try{
                if (!caseWithdrawFormsInstance.hasErrors() ){
                    flash.message = caseWithdrawService.saveThisInstance(caseWithdrawFormsInstance,authority,'updated', null)
                    
                    redirect(action:"editWithValidation",id: caseWithdrawInstance.id, params:[authority:authority, caseid:caseid, wid:caseWithdrawInstance.id])
           
                }
            }
            catch(Exception e) {
              
                render(view: "edit", model: [caseWithdrawInstance: caseWithdrawInstance,caseWithdrawFormsInstance:caseWithdrawFormsInstance, authority:authority, caseid:caseid,wid:caseWithdrawInstance.id])
                
            }
        }
    }
    
    def wdcmain = {
            
        // params.each{key,value->
        //     println "in withdraw consent wdcmain key: ${key}   value:${value}"
        //  }
         
        def caseRecordInstance = CaseRecord.findById(params.id)
        def caseWithdrawInstance = caseWithdrawService.createConsent(caseRecordInstance)
        
        def finalReview = caseWithdrawService.checkFinalReviewGoStatus(caseWithdrawInstance)
            
        return [caseWithdrawInstance: caseWithdrawInstance,finalReview:finalReview]
    }
    
    
    
    Map checkFormsError(caseWithdrawFormsInstance, authority) {
        def ferrorMap = [:]
        
              
        if (authority.equals('CDR') && !caseWithdrawFormsInstance.datedataWdrawn) {
               
            ferrorMap.put('datedataWdrawn', 'Please specify date of data recall')
        }
        
        if (caseWithdrawFormsInstance.outcomeOfWDrawnData == 'Other' && !caseWithdrawFormsInstance.outcomeOfWDrawDataOther) {
               
            ferrorMap.put('outcomeOfWDrawDataOther', 'Please specify other outcome of recall of data')
        }
            
        if (!caseWithdrawFormsInstance.outcomeOfWDrawnData ) {
               
            ferrorMap.put('outcomeOfWDrawnData', 'Please specify outcome of recall of data')
        }
        //if (!caseWithdrawFormsInstance.verifiedBy) {
           
        //  ferrorMap.put('verifiedBy', 'Please specify if recall has been verified')
        // }
        if (!caseWithdrawFormsInstance.verifiedByRole ) {
           
            ferrorMap.put('verifiedByRole', 'Please specify role of person verifying the recall ')
        }
        if (!caseWithdrawFormsInstance.dateVerified) {
           
            ferrorMap.put('dateVerified', 'Please enter date of verification')
        }
        
       
        if( authority.equals('CBR')||authority.equals('PRC')||authority.equals('LDACC')||authority.equals('BRAINBANK')){
            
            if (!caseWithdrawFormsInstance.ackRcvdYesNO ) {
              
                ferrorMap.put('ackRcvdYesNO', 'Please acknowledge receipt of recall message ')
            }
            
            if (!caseWithdrawFormsInstance.datedataWdrawn ) {
               
                ferrorMap.put('datedataWdrawn', 'Please specify date data recall ')
            }
            
            if (!caseWithdrawFormsInstance.dateSpeciWithdraw ) {
               
                ferrorMap.put('dateSpeciWithdraw', 'Please specify date specimen recalled ')
            }
            
            if (!caseWithdrawFormsInstance.outcomeOfSpecimen ) {
               
                ferrorMap.put('outcomeOfSpecimen', 'Please specify outcome of specimen ')
            }
            
            if (caseWithdrawFormsInstance.outcomeOfSpecimen == 'Other' && !caseWithdrawFormsInstance.outcomeOfSpecimenOther) {
                
                ferrorMap.put('outcomeOfSpecimen', 'Please specify other outcome of speciemen')
            }
            
                        
            if (caseWithdrawFormsInstance.outcomeOfSpecimen == 'Shipped to other location, specify' && !caseWithdrawFormsInstance.specShippedTo) {
               
                ferrorMap.put('outcomeOfSpecimen', 'Please specify where specimen was shipped to')
            }
            
            if (!caseWithdrawFormsInstance.dateProcessingStarted ) {
               
                ferrorMap.put('dateProcessingStarted', 'Please specify when Data Processing started')
            }
           
        } 
        
        
        return ferrorMap
    }
        
    
    Map checkError(caseWithdrawInstance, authority) {
        def errorMap = [:]
     
        if(authority.equals('BSS')){
        
            if ( !caseWithdrawInstance.dateWithdrawalRcvd  ) {
               
                errorMap.put('dateWithdrawalRcvd', 'Please specify When recall request was made')
            }    
            //if ( !caseWithdrawInstance.bssNameCompletingForm) {
               
            //  errorMap.put('bssNameCompletingForm', 'Please specify who entered this form')
            //}  
            if ( !caseWithdrawInstance.withdrawReqType) {
              
                errorMap.put('withdrawReqType', 'Please specify type of recall request')
            } 
            
            if ( caseWithdrawInstance.reasonOther  && !caseWithdrawInstance.reasonOtherDescrip) {
              
                errorMap.put('reasonOtherDescrip', 'Please specify other reason for Next of Kin request for recall')
            }
            
            if (caseWithdrawInstance.withdrawReqType == 'Other' && !caseWithdrawInstance.withdrawReqTypeOther) {
               
                errorMap.put('withdrawReqTypeOther', 'Please specify type of recall request')
            }
            if ( !caseWithdrawInstance.requestInitiator ) {
              
                errorMap.put('requestInitiator', 'Please specify who is initiating this recall')
            }
            
            if ( caseWithdrawInstance.requestInitiator && 
                (!caseWithdrawInstance.reasonMedical && 
                    !caseWithdrawInstance.reasonPersonal &&
                    !caseWithdrawInstance.reasonCultural &&
                    !caseWithdrawInstance.reasonReligious &&
                    !caseWithdrawInstance.reasonUndocumConsent &&
                    !caseWithdrawInstance.reasonProcError &&
                    !caseWithdrawInstance.reasonInclExcl &&
                    !caseWithdrawInstance.reasonUnknown &&
                    !caseWithdrawInstance.reasonOther )) {
               
              
                errorMap.put('reasonMedical', 'Please specify reason for initiating this recall')
            }
        }
        if(authority.equals('APR')){
                  
            
            if (!caseWithdrawInstance.cahubApproveOrNot) {
               
                errorMap.put('cahubApproveOrNot', 'Please specify if recall has been approved or not')
            }
            if (!caseWithdrawInstance.ackRcvdYesNOCDR ) {
              
                errorMap.put('ackRcvdYesNOCDR', 'Please acknowledge recall notice received or not ')
            }
            if (!caseWithdrawInstance.approvedBy) {
               
                errorMap.put('approvedBy', 'Please specify who is approving this recall')
            }
            if (caseWithdrawInstance.cahubApproveOrNot.equals('YES') &&(!caseWithdrawInstance.assignToCDR && !caseWithdrawInstance.assignToCBR && !caseWithdrawInstance.assignToPRC && !caseWithdrawInstance.assignToLDACC && !caseWithdrawInstance.assignToBrainbank)) {
              
                errorMap.put('assignToBrainbank', 'Please specify entities for this recall process')
            }
            
           
        }
        if(authority.equals('BSSCOMPLETE')){
            
   
            if (!caseWithdrawInstance.finalNotifyviaMail &&!caseWithdrawInstance.finalNotifyviaPhone &&!caseWithdrawInstance.finalNotifyviaEmail &&!caseWithdrawInstance.finalNotifyviaOth) {
               
                errorMap.put('finalNotifyviaEmail', 'Please specify method of notification')
            }
            if (!caseWithdrawInstance.finalCanNotifyDate ) {
              
                errorMap.put('finalCanNotifyDate', 'Please specify when BSS can notify DON/NOK ')
            }
            if (!caseWithdrawInstance.finalNotifiedDate) {
               
                errorMap.put('finalNotifiedDate', 'Please specify date BSS notifies donor or NOK')
            }
           
            if (!caseWithdrawInstance.finalprovideProofDate) {
               
                errorMap.put('finalprovideProofDate', 'Please specify when BSS provides proof of recall to donor or NOK')
            }
            if (caseWithdrawInstance.finalNotifyviaOth && !caseWithdrawInstance.finalNotifyOthType) {
               
                errorMap.put('finalNotifyviaOth', 'Please specify other method of notification')
            }
           
        }
        
        if(authority.equals('ELR')){
                           
            if (!caseWithdrawInstance.elrReviewer) {
               
                errorMap.put('elrReviewer', 'Please enter ELR Reviewer name')
            }
        } 
        if(authority.equals('QM')){
               
            if (!caseWithdrawInstance.qmReviewer ) {
              
                errorMap.put('qmReviewer', 'Please enter QM Reviewer name')
            }
        } 
        if(authority.equals('OPS')){
                           
            if (!caseWithdrawInstance.opsReviewer) {
               
                errorMap.put('opsReviewer', 'Please enter OPS Reviewer name')
            }
        } 
        if(authority.equals('DIRECTOR')){
                           
            if (!caseWithdrawInstance.directorReviewer) {
               
                errorMap.put('directorReviewer', 'Please enter Director name')
            }
        } 
            
        return errorMap
    }
    
    static Map checkSignErrors(uploadedFile){
        def result = [:]
                
        
        if (uploadedFile.empty) {
           
            result.put("fileName", "File can't be empty. Please choose a file")             
        }
        else {
        
            def originalFileName = uploadedFile.originalFilename
           
                              
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
 
