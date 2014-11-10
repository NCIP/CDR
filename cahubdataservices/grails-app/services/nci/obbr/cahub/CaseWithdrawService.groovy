package nci.obbr.cahub

import nci.obbr.cahub.forms.common.withdraw.CaseWithdraw
import nci.obbr.cahub.forms.common.withdraw.CaseWithdrawForms
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.util.FileUpload
import java.text.SimpleDateFormat
import nci.obbr.cahub.staticmembers.*;
import nci.obbr.cahub.util.*;

class CaseWithdrawService {

    static transactional = true
    def sendMailService
    
    // create an instance of the the two domains 
    def createConsent(caseRecordInstance){
        
        def caseWithdrawInstance
        def caseWithdrawFormsInstance
       
        
        
        try{
        
            
            if(caseRecordInstance){
                caseWithdrawInstance = CaseWithdraw.findByCaseRecord(caseRecordInstance)
           
            }
       
            if (!caseWithdrawInstance) {
                //  if (!caseWithdrawInstance || caseWithdrawInstance?.cahubApproveOrNot.equals('NO')) {
                caseWithdrawInstance = new CaseWithdraw()
           
                caseWithdrawInstance.caseRecord=CaseRecord.findById(caseRecordInstance.id)
                caseWithdrawInstance.save(failOnError:true)
            
                // create all the child forms now
                
                caseWithdrawFormsInstance = new CaseWithdrawForms()
                caseWithdrawFormsInstance.authority='BRAINBANK'
                caseWithdrawFormsInstance.caseWithdraw=caseWithdrawInstance
                caseWithdrawFormsInstance.save(failOnError:true)
                
                caseWithdrawFormsInstance = new CaseWithdrawForms()
                caseWithdrawFormsInstance.authority='CBR'
                caseWithdrawFormsInstance.caseWithdraw=caseWithdrawInstance
                caseWithdrawFormsInstance.save(failOnError:true)
                
                caseWithdrawFormsInstance = new CaseWithdrawForms()
                caseWithdrawFormsInstance.authority='CDR'
                caseWithdrawFormsInstance.caseWithdraw=caseWithdrawInstance
                caseWithdrawFormsInstance.save(failOnError:true)
                          
                caseWithdrawFormsInstance = new CaseWithdrawForms()
                caseWithdrawFormsInstance.authority='LDACC'
                caseWithdrawFormsInstance.caseWithdraw=caseWithdrawInstance
                caseWithdrawFormsInstance.save(failOnError:true)
                caseWithdrawFormsInstance = new CaseWithdrawForms()
                caseWithdrawFormsInstance.authority='PRC'
                caseWithdrawFormsInstance.caseWithdraw=caseWithdrawInstance
                caseWithdrawFormsInstance.save(failOnError:true)
             
            }
            
           
        }
        catch(Exception e){
          
            throw new RuntimeException(e.toString())
        }
        return caseWithdrawInstance
      
    }

    
    def recallNotifyEmail(caseWithdrawInstance, author,username){
        
        def  emailTo 
        def subj
        def btext
        def withdrawFormInstance
        
        // FIRST SAVE ALL THE ADDITIONAL FIELDS WHATEVER REQUIRED THAT WERE NOT IN THE PARAMS
        try{
            
        
            if(author.equals('BSS')){
            
                 
                caseWithdrawInstance.dateBSSEntrySubmitted = new Date()
                
                caseWithdrawInstance.caseStatus="Verified By- BSS"
                caseWithdrawInstance.save(failOnError:true) 
                 
            }
            
            if(author.equals('APR')){
                // save the case status in the case record added this on 01/18/13 only on submit. not on save
                if(caseWithdrawInstance.cahubApproveOrNot.equals('YES')){
                    def caseRecord=caseWithdrawInstance.caseRecord
                    caseRecord.caseStatus=CaseStatus.findByCode('WDCPROG')
                    caseRecord.save(failOnError:true)
                }
                // end pmh 01/28/13
                caseWithdrawInstance.dateApproved = new Date()
                def casestat =caseWithdrawInstance.caseStatus
                             
                caseWithdrawInstance.caseStatus=casestat+":caHUB"
                caseWithdrawInstance.save(failOnError:true) 
                 
            }
            if(author.equals('BSSCOMPLETE')){
            
                def casestat =caseWithdrawInstance.caseStatus
                                
                caseWithdrawInstance.caseStatus=casestat+":"+author
                
                caseWithdrawInstance.finalCompleteBy=username
                caseWithdrawInstance.finalCompleteDate=new Date()
                caseWithdrawInstance.save(failOnError:true) 
            }
            
            
            if(author.equals('CDR')||author.equals('CBR')|| author.equals('LDACC')|| author.equals('PRC')|| author.equals('BRAINBANK')){
               
                // withdrawFormInstance = CaseWithdrawForms.findByWithdrawconsentAndAuthority(caseWithdrawInstance,author)
                withdrawFormInstance = CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance,author)
                withdrawFormInstance.dateSubmitted = new Date()
                withdrawFormInstance.submittedBy = username
                withdrawFormInstance.save(failOnError:true) 
                 
                def casestat =caseWithdrawInstance.caseStatus
                                
                caseWithdrawInstance.caseStatus=casestat+":"+author
                caseWithdrawInstance.save(failOnError:true) 
                
                
            }
           
            
            // now begin to send all the emails to the concerned entities
            
            if(author.equals('BSS')){
                
                
                // email only to CDR on completiong of form 1(section A)
                //emailTo = emailid_for_testing
                emailTo = AppSetting.findByCode("RECALL_APR_CONTACT").value
                subj='Biospecimen Case Recall filed for case ID: '+caseWithdrawInstance?.caseRecord?.caseId 
                btext='A Biospecimen case recall has been filed by BSS for case ID: '+caseWithdrawInstance?.caseRecord?.caseId + '\n\r Please refer to the recall notification details on the CDR\n\r BSS person who filled the form: '+caseWithdrawInstance.bssNameCompletingForm
            
                sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo) 
            }
            if(author.equals('APR')){
             
                subj='Biospecimen Case Recall Approved for case ID: '+caseWithdrawInstance?.caseRecord?.caseId 
                if(caseWithdrawInstance.cahubApproveOrNot.equals('YES') ){
                    
                    if(caseWithdrawInstance.assignToCDR){
                        emailTo = AppSetting.findByCode("RECALL_CDR_CONTACT").value
                        btext='A Biospecimen case recall has been approved for case ID: '+caseWithdrawInstance?.caseRecord?.caseId +'\n\r There is work to be completed and verified by CDR. \n\rPlease refer to the  recall notification details on the CDR\n\r BSS person who filled the form: '+caseWithdrawInstance.bssNameCompletingForm
                        sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo)
                    }
                    if(caseWithdrawInstance.assignToCBR ){
                        emailTo =AppSetting.findByCode("RECALL_CBR_CONTACT").value
                        btext='A Biospecimen case recall has been approved for case ID: '+caseWithdrawInstance?.caseRecord?.caseId +'\n\r There is work to be completed and verified by CBR. \n\rPlease refer to the  recall notification details on the CDR\n\r BSS person who filled the form: '+caseWithdrawInstance.bssNameCompletingForm
                        sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo)
                    }
                    if(caseWithdrawInstance.assignToLDACC){
                        emailTo =AppSetting.findByCode("RECALL_LDACC_CONTACT").value
                        btext='A Biospecimen case recall has been approved for case ID: '+caseWithdrawInstance?.caseRecord?.caseId +'\n\r There is work to be completed and verified by LDACC. \n\rPlease refer to the  recall notification details on the CDR\n\r BSS person who filled the form: '+caseWithdrawInstance.bssNameCompletingForm
                        sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo)
                    }
                    if(caseWithdrawInstance.assignToPRC ){
                        emailTo =AppSetting.findByCode("RECALL_PRC_CONTACT").value
                        btext='A Biospecimen case recall has been approved for case ID: '+caseWithdrawInstance?.caseRecord?.caseId +'\n\r There is work to be completed and verified by PRC. \n\rPlease refer to the  recall notification details on the CDR\n\r BSS person who filled the form: '+caseWithdrawInstance.bssNameCompletingForm
                        sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo)
                    }
                    if(caseWithdrawInstance.assignToBrainbank ){
                        emailTo =AppSetting.findByCode("RECALL_BRAINBANK_CONTACT").value
                        btext='A Biospecimen case recall has been approved for case ID: '+caseWithdrawInstance?.caseRecord?.caseId +'\n\r There is work to be completed and verified by BRAIN BANK. \n\rPlease refer to the  recall notification details on the CDR\n\r BSS person who filled the form: '+caseWithdrawInstance.bssNameCompletingForm
                        sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo)
                    }
                }
                
                
                           
                 
            }
            if(author.equals('CDR')){
          
                
                // CDR emails to all after completing the task
                emailTo = AppSetting.findByCode("RECALL_ADMIN_CONTACT").value
                subj='CDR verified Biospecimen case recall filed for case ID: '+caseWithdrawInstance?.caseRecord?.caseId 
                btext='A recall request has been completed and verified by CDR for case ID: '+caseWithdrawInstance?.caseRecord?.caseId + '\n\r Please refer to the  recall notification details on the CDR\n\r Person who filled the form: '+withdrawFormInstance?.verifiedBy
            
                sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo) 
            }
                
            if(author.equals('CBR')){
          
                
                // CBR emails to all after completing the task
                emailTo = AppSetting.findByCode("RECALL_ADMIN_CONTACT").value
                subj='CBR verified Biospecimen case recall filed for case ID: '+caseWithdrawInstance?.caseRecord?.caseId 
                btext='A recall request has been completed and verified by CBR for case ID: '+caseWithdrawInstance?.caseRecord?.caseId + '\n\r Please refer to the  recall notification details on the CDR\n\r Person who filled the form: '+withdrawFormInstance?.verifiedBy
            
                sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo) 
            }
            if(author.equals('PRC')){
          
                
                // PRC emails to all after completing the task
                emailTo = AppSetting.findByCode("RECALL_ADMIN_CONTACT").value
                subj='PRC verified Biospecimen case recall filed for case ID: '+caseWithdrawInstance?.caseRecord?.caseId 
                btext='A recall request has been completed and verified by PRC for case ID: '+caseWithdrawInstance?.caseRecord?.caseId + '\n\r Please refer to the  recall notification details on the CDR\n\r Person who filled the form: '+withdrawFormInstance?.verifiedBy
            
                sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo) 
            }
            if(author.equals('LDACC')){
          
                
                // LDACC emails to all after completing the task
                emailTo = AppSetting.findByCode("RECALL_ADMIN_CONTACT").value
                subj='LDACC verified Biospecimen case recall filed for case ID: '+caseWithdrawInstance?.caseRecord?.caseId 
                btext='A recall request has been completed and verified by LDACC for case ID: '+caseWithdrawInstance?.caseRecord?.caseId + '\n\r Please refer to the  recall notification details on the CDR\n\r Person who filled the form: '+withdrawFormInstance?.verifiedBy
            
                sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo) 
            }
            if(author.equals('BRAINBANK')){
          
                
                // BrainBank emails to all after completing the task
                emailTo = AppSetting.findByCode("RECALL_ADMIN_CONTACT").value
                subj='BRAIN BANK verified Biospecimen case recall filed for case ID: '+caseWithdrawInstance?.caseRecord?.caseId 
                btext='A recall request has been completed and verified by BRAIN BANK for case ID: '+caseWithdrawInstance?.caseRecord?.caseId + '\n\r Please refer to the  recall notification details on the CDR\n\r Person who filled the form: '+withdrawFormInstance?.verifiedBy
            
                sendMailService.sendCaseRecallConsentNotifyEmail(subj, btext,emailTo) 
            }
            
        
        }catch(Exception e){
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
    
    // check to see if all the concerned entities have verified at their end
    //before we can proceed to final review
    def checkFinalReviewGoStatus(caseWithdrawInstance){
        def finalReview='YES'
      
        def whoisInvolvedList =[]
       
        
        
        if (caseWithdrawInstance.assignToCDR){
            whoisInvolvedList.add('CDR')
            
        }
        if (caseWithdrawInstance.assignToCBR){
            whoisInvolvedList.add('CBR')
            
        }
        if (caseWithdrawInstance.assignToPRC){
            whoisInvolvedList.add('PRC')
            
        }
        if (caseWithdrawInstance.assignToLDACC){
            whoisInvolvedList.add('LDACC')
            
        }
        if (caseWithdrawInstance.assignToBrainbank){
            whoisInvolvedList.add('BRAINBANK')
           
        }
                
        
        whoisInvolvedList.each{
             
            if(!caseWithdrawInstance.caseStatus?.contains(it)){
                
                finalReview='NO'
                      
            }
            
        }
        return finalReview
        
    }
    
    // save an form instance( withdrawconsent or withdrawforms)
    
    def saveThisInstance(recallInstance,authority,action, request){
        
        // recallInstance can be either caseWithdrawInstance or withdrawFormInstance
        def fmessage
        
        try{
            
            if(authority.equals('BSS')){
                fmessage='Recall request form '+action
                if(!recallInstance.caseStatus){
                    recallInstance.caseStatus="STARTED"
                    recallInstance.bssSectionA=true
                }
            }
            if(authority.equals('APR')){
               
                fmessage='Recall Approval form '+action
                recallInstance.aprSectionB=true
                
            }
            if(authority.equals('CDR')||authority.equals('CBR')||authority.equals('PRC')||authority.equals('LDACC')||authority.equals('BRAINBANK')){
                fmessage=authority+' verification form '+action
                
            }
            if(authority.equals('ELR')||authority.equals('QM')||authority.equals('OPS')||authority.equals('DIRECTOR')){
                fmessage=authority+' signature form '+action
            }
            if(authority.equals('BSSCOMPLETE')){
                fmessage=' BSS final recall form '+action
            }
            
            
            if(request){
                
                def uploadedFile = request.getFile("fileName")
         
                if(uploadedFile){
                    def originalFileName = uploadedFile.originalFilename.replace(' ', '_')
                    
                    if(originalFileName){
                        def current_time = (new Date()).getTime()
                        def caseRecord = recallInstance.caseRecord
                        def studyCode = caseRecord.study.code
                        def pathUploads = AppSetting.findByCode("FILE_STORAGE").value
                        //pathUploads=pathUploads+File.separator +studyCode+File.separator + caseRecord.caseId+File.separator +authority+ File.separator
                        pathUploads=pathUploads+File.separator +studyCode+File.separator + caseRecord.caseId+File.separator +'attachments'+ File.separator
                        def strippedFileName = originalFileName?.substring(0,originalFileName?.lastIndexOf('.'))                    
                        def fileExtension = originalFileName?.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size()) 
               
                        def newFileName = strippedFileName + "-" + current_time + "." + fileExtension
           
                        File dir = new File(pathUploads)
            
                        if (!dir.exists()) {
                            dir.mkdirs()
                        }
            
       
                        uploadedFile.transferTo( new File(pathUploads, newFileName) )
            
                        def fileUpload = new FileUpload()
                        fileUpload.caseRecord = recallInstance.caseRecord
                        fileUpload.fileName= newFileName
                        fileUpload.filePath=pathUploads
                        
                        fileUpload.uploadTime = new Date().getDateTimeString()
                        
                        if(authority.equals('CDRFL')){
                        
                            fileUpload.category =CaseAttachmentType.findByCode('CRCDR')
                            fmessage=' CDR verification file saved '
                        }
                        if(authority.equals('CBRFL')){
                        
                            fileUpload.category =CaseAttachmentType.findByCode('CRCBR')
                            fmessage=' CBR verification file saved '
                        }
                        if(authority.equals('LDACCFL')){
                        
                            fileUpload.category =CaseAttachmentType.findByCode('CRLDACC')
                            fmessage=' LDACC verification file saved '
                        }
                        if(authority.equals('PRCFL')){
                        
                            fileUpload.category =CaseAttachmentType.findByCode('CRPRC')
                            fmessage=' PRC verification file saved '
                        }
                        if(authority.equals('BRAINBANKFL')){
                        
                            fileUpload.category =CaseAttachmentType.findByCode('CRBRAINBANK')
                            fmessage=' BRAINBANK verification file saved '
                        }
                        if(authority.equals('BSSCOMPLETEFL')){
                        
                            fileUpload.category =CaseAttachmentType.findByCode('CRCOMPLETE')
                            
                        }
                        fileUpload.caseId=recallInstance.caseRecord.caseId
                        fileUpload?.caseRecord.id=recallInstance.caseRecord.id
               
                        fileUpload.save(failOnError:true)
                      
                    } 
          
          
            
                }else{
                    
                }
            }
           
          
            recallInstance.save(failOnError:true)
           
            
        }catch(Exception e){
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
        return fmessage
        
    }
    
    
    
    // remove a file that has been uploaded 
    def remove(caseWithdrawInstance,authority){
        def fileUploadInstance
        def retmsg="File removed"
    
        try{
        
    
            if(authority=='BSSCOMPLETE'){
                fileUploadInstance = caseWithdrawInstance.proofOfNotifyFile
                caseWithdrawInstance.proofOfNotifyFile=null
            }
        
            caseWithdrawInstance.save(failOnError:true)
       
            if(fileUploadInstance){
                // retmsg=saveThisInstance(caseWithdrawInstance,authority,'file deleted')
        
                fileUploadInstance.delete(failOnError:true)
                def convertedFilePath = fileUploadInstance?.filePath + File.separator + fileUploadInstance?.fileName
                def file = new File(convertedFilePath)
           
                if (file.exists()) {
                    if (!file.delete()) {
                        retmsg="Failed to delete the file"
                        throw new IOException("Failed to delete the file")
                    }
                }

             
                
            
           
            }
        
       
        
            
        }catch(Exception e){
          
            throw new RuntimeException(e.toString())   
        }
        return retmsg
        
    }
    
    
    
    def completeReview(){
        
    }
    
    
    // Find out if for a withdraw case whether CDR,CBR, PRC, LDACC, BRAINBANK withdrawforms has started or not
    
    def getFormProgress(caseList){
        
        def fmap=[:]
        def cwi
        def cwfiList
        def caseid
        caseList.each{
            caseid= it.id
            
            cwi = CaseWithdraw.findByCaseRecord(it)
      
            cwfiList = CaseWithdrawForms.findAllByCaseWithdrawAndHasStarted(cwi, true)
             
            fmap.put(caseid,cwfiList)
               
           
        }  
        return fmap
    }
    
    // this method is exclusive for SECTION D final reviews
    def saveSignature(caseWithdrawInstance, params, request, finalSubmit){
        def authority = params.authority
        if(authority.contains('FL')){
            authority=authority[0..-3]
        }
        def fmessage= " Verification saved for "+ authority
        def uploadedFile = request.getFile("fileName")
        
        try{
         
            if(uploadedFile){
                
                def originalFileName = uploadedFile.originalFilename.replace(' ', '_')
                
                if(originalFileName){
                   
                    def current_time = (new Date()).getTime()
                    def caseRecord = caseWithdrawInstance.caseRecord
                    def studyCode = caseRecord.study.code
                    
                    def pathUploads = AppSetting.findByCode("FILE_STORAGE").value
                    //pathUploads=pathUploads+File.separator +studyCode+File.separator + caseRecord.caseId+File.separator +authority+ File.separator
                    pathUploads=pathUploads+File.separator +studyCode+File.separator + caseRecord.caseId+File.separator +'attachments'+ File.separator
                    def strippedFileName = originalFileName?.substring(0,originalFileName?.lastIndexOf('.'))                    
                    def fileExtension = originalFileName?.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size()) 
               
                    def newFileName = strippedFileName + "-" + current_time + "." + fileExtension
           
                    File dir = new File(pathUploads)
            
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
            
       
                    uploadedFile.transferTo( new File(pathUploads, newFileName) )
            
                    def fileUpload = new FileUpload()
                    fileUpload.caseRecord = caseWithdrawInstance.caseRecord
                    fileUpload.fileName= newFileName
                    fileUpload.filePath=pathUploads
                    fileUpload.uploadTime = new Date().getDateTimeString()
                    //fileUpload.category =authority+" RECALL VERIFY"
                    if(authority.equals('ELR')){
                                                
                        fileUpload.category =CaseAttachmentType.findByCode('CRELR')
                    }
                    if(authority.equals('QM')){
                        
                        fileUpload.category =CaseAttachmentType.findByCode('CRQM')
                    }
                    if(authority.equals('OPS')){
                        
                        fileUpload.category =CaseAttachmentType.findByCode('CROPS')
                    }
                    if(authority.equals('DIRECTOR')){
                        
                        fileUpload.category =CaseAttachmentType.findByCode('CRDIRECTOR')
                    }
                    
                    fileUpload.caseId=caseWithdrawInstance.caseRecord.caseId
                    fileUpload?.caseRecord.id=caseWithdrawInstance.caseRecord.id
                    fileUpload.save(failOnError:true)
            
                    fmessage= authority+ " Verification file saved"
           
                }
               
            }   
                    
            if(authority.equals('ELR')){
                fmessage=""
                if(params.elrReviewer){
                    caseWithdrawInstance.elrReviewer=params.elrReviewer
                   
                    if(finalSubmit){caseWithdrawInstance.dateELRReviewed=new Date()}
                    caseWithdrawInstance.save(flush: true)
                }
                caseWithdrawInstance.elrSectionD=true
               
            }
            if(authority.equals('QM')){
                fmessage=""
                if(params.qmReviewer){
                    caseWithdrawInstance.qmReviewer=params.qmReviewer
                    
                    if(finalSubmit){caseWithdrawInstance.dateQMReviewed = new Date()}
                    caseWithdrawInstance.save(flush: true)
                }
                caseWithdrawInstance.qmSectionD=true
            }
            if(authority.equals('OPS')){
                fmessage=""
                if(params.opsReviewer){
                    caseWithdrawInstance.opsReviewer=params.opsReviewer
                    
                    if(finalSubmit){caseWithdrawInstance.dateOPSReviewed = new Date()}
                    caseWithdrawInstance.save(flush: true)
                }
                caseWithdrawInstance.opsSectionD=true
            }
            
            if(authority.equals('DIRECTOR')){
                fmessage=""
                if(params.directorReviewer){
                    caseWithdrawInstance.directorReviewer=params.directorReviewer
                    
                    if(finalSubmit){caseWithdrawInstance.dateDirectorReviewed = new Date()}
                    caseWithdrawInstance.save(flush: true)
                }
                caseWithdrawInstance.drSectionD=true
            }
            
        
        }
        catch(Exception e){
            throw new RuntimeException(e.toString())   
        }
       
        return fmessage
        
    }
    
}
