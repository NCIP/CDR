package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.staticmembers.Fixative
import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.staticmembers.FormMetadata
import nci.obbr.cahub.staticmembers.SOP
import grails.plugins.springsecurity.Secured
import nci.obbr.cahub.util.ComputeMethods

class BpvTissueReceiptDissectionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def accessPrivilegeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvTissueReceiptDissectionInstanceList: BpvTissueReceiptDissection.list(params), bpvTissueReceiptDissectionInstanceTotal: BpvTissueReceiptDissection.count()]
    }

    def create = {
        def errorMap=[:]
        def bpvTissueReceiptDissectionInstance = new BpvTissueReceiptDissection()
        bpvTissueReceiptDissectionInstance.properties = params
        bpvTissueReceiptDissectionInstance.tumorSource =  bpvTissueReceiptDissectionInstance?.caseRecord?.primaryTissueType?.toString()
        
        def cdrVer = bpvTissueReceiptDissectionInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            
         
        return [bpvTissueReceiptDissectionInstance: bpvTissueReceiptDissectionInstance,version53:version53, errorMap:errorMap]
    }

    def save = {
        def bpvTissueReceiptDissectionInstance = new BpvTissueReceiptDissection(params)
        
        // Populate SOP Record
        def formMetadataInstance = FormMetadata.get(params.formMetadata.id)
        def sopInstance
        if (formMetadataInstance?.sops?.size() > 0) {
            sopInstance = SOP.get(formMetadataInstance?.sops?.get(0)?.id)
            bpvTissueReceiptDissectionInstance.receiptDissectSOP = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:sopInstance.activeSopVer).save(flush: true)
        }
        
        if(bpvTissueReceiptDissectionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
            redirect(action: "editWithValidation", id: bpvTissueReceiptDissectionInstance.id)
        } else {
            def warningMap = getWarningMap(bpvTissueReceiptDissectionInstance)
            render(view: "create", model: [bpvTissueReceiptDissectionInstance: bpvTissueReceiptDissectionInstance, warningMap:warningMap])
        }
    }

    def show = {
        def errorMap=[:]
        def bpvTissueReceiptDissectionInstance = BpvTissueReceiptDissection.get(params.id)
        if(!bpvTissueReceiptDissectionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvTissueReceiptDissectionInstance.caseRecord, session, 'view')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(bpvTissueReceiptDissectionInstance.caseRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }
            
            def cdrVer = bpvTissueReceiptDissectionInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            
            def warningMap = getWarningMap(bpvTissueReceiptDissectionInstance)
            def canResume = (accessPrivilegeService.checkAccessPrivilege(bpvTissueReceiptDissectionInstance.caseRecord, session, 'edit') == 0)
            [bpvTissueReceiptDissectionInstance: bpvTissueReceiptDissectionInstance, warningMap:warningMap,version53:version53, errorMap:errorMap, canResume: canResume]
        }
    }

    def edit = {
        def errorMap=[:]
        def bpvTissueReceiptDissectionInstance = BpvTissueReceiptDissection.get(params.id)
        if(!bpvTissueReceiptDissectionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvTissueReceiptDissectionInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvTissueReceiptDissectionInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            validateFields(bpvTissueReceiptDissectionInstance, errorMap)
            def canSubmit = !bpvTissueReceiptDissectionInstance.errors.hasErrors()
            bpvTissueReceiptDissectionInstance.clearErrors()
            def cdrVer = bpvTissueReceiptDissectionInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            
            def warningMap = getWarningMap(bpvTissueReceiptDissectionInstance)
            return [bpvTissueReceiptDissectionInstance: bpvTissueReceiptDissectionInstance, canSubmit: canSubmit, warningMap:warningMap,version53:version53, errorMap:errorMap]
        }
    }

    def editWithValidation = {
        def errorMap=[:]
        def bpvTissueReceiptDissectionInstance = BpvTissueReceiptDissection.get(params.id)
        if(!bpvTissueReceiptDissectionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvTissueReceiptDissectionInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvTissueReceiptDissectionInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            validateFields(bpvTissueReceiptDissectionInstance, errorMap)
            def cdrVer = bpvTissueReceiptDissectionInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            
            def warningMap = getWarningMap(bpvTissueReceiptDissectionInstance)
            render(view: "edit", model: [bpvTissueReceiptDissectionInstance: bpvTissueReceiptDissectionInstance, canSubmit: !bpvTissueReceiptDissectionInstance.errors.hasErrors(), warningMap:warningMap,version53:version53, errorMap:errorMap])
        }
    }

    def update = {
        def bpvTissueReceiptDissectionInstance = BpvTissueReceiptDissection.get(params.id)
        
        def cdrVer = bpvTissueReceiptDissectionInstance.caseRecord.cdrVer
        def  version53 = true
        if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
            version53 = false
        }  
        
        if (bpvTissueReceiptDissectionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvTissueReceiptDissectionInstance.version > version) {
                    bpvTissueReceiptDissectionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvTissueReceiptDissection while you were editing")
                    render(view: "edit", model: [bpvTissueReceiptDissectionInstance: bpvTissueReceiptDissectionInstance,version53:version53])
                    return
                }
            }
            bpvTissueReceiptDissectionInstance.properties = params
            if (!bpvTissueReceiptDissectionInstance.hasErrors() && bpvTissueReceiptDissectionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
                redirect(action: "editWithValidation", id: bpvTissueReceiptDissectionInstance.id)
            } else {
                def warningMap = getWarningMap(bpvTissueReceiptDissectionInstance)
                render(view: "edit", model: [bpvTissueReceiptDissectionInstance: bpvTissueReceiptDissectionInstance, warningMap:warningMap,version53:version53])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    def submit = {
        def errorMap=[:]
        def bpvTissueReceiptDissectionInstance = BpvTissueReceiptDissection.get(params.id)
        if(bpvTissueReceiptDissectionInstance) {
            
            def cdrVer = bpvTissueReceiptDissectionInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            } 
             
            if(params.version) {
                def version = params.version.toLong()
                if(bpvTissueReceiptDissectionInstance.version > version) {
                    
                    bpvTissueReceiptDissectionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case'] as Object[], "Another user has updated this BpvTissueReceiptDissection while you were editing")
                                       
                    def warningMap = getWarningMap(bpvTissueReceiptDissectionInstance)
                    render(view: "edit", model: [bpvTissueReceiptDissectionInstance: bpvTissueReceiptDissectionInstance, warningMap:warningMap,version53:version53])
                    return
                }
            }

            bpvTissueReceiptDissectionInstance.properties = params
            validateFields(bpvTissueReceiptDissectionInstance, errorMap)

            if(!bpvTissueReceiptDissectionInstance.hasErrors() && bpvTissueReceiptDissectionInstance.save(flush: true)) {
                bpvTissueReceiptDissectionInstance.dateSubmitted = new Date()
                bpvTissueReceiptDissectionInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()

                flash.message = "${message(code: 'default.submitted.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
                redirect(controller: "caseRecord", action: "display", id: bpvTissueReceiptDissectionInstance.caseRecord.id)
            } else {
                bpvTissueReceiptDissectionInstance.discard()
                def warningMap = getWarningMap(bpvTissueReceiptDissectionInstance)
                render(view: "edit", model: [bpvTissueReceiptDissectionInstance: bpvTissueReceiptDissectionInstance, warningMap:warningMap,version53:version53, errorMap:errorMap])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def bpvTissueReceiptDissectionInstance = BpvTissueReceiptDissection.get(params.id)
        if(bpvTissueReceiptDissectionInstance) {
            try {
                bpvTissueReceiptDissectionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
                redirect(action: "list")
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
                redirect(action: "show", id: params.id)
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueReceiptDissectionInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueReceiptDissectionInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    def resumeEditing = {
        def bpvTissueReceiptDissectionInstance = BpvTissueReceiptDissection.get(params.id)
        bpvTissueReceiptDissectionInstance.dateSubmitted = null
        bpvTissueReceiptDissectionInstance.submittedBy = null
        def cdrVer = bpvTissueReceiptDissectionInstance.caseRecord.cdrVer
        def  version53 = true
        if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
            version53 = false
        } 
         
        if(bpvTissueReceiptDissectionInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvTissueReceiptDissectionInstance.id)
        } else {
            def warningMap = getWarningMap(bpvTissueReceiptDissectionInstance)
            render(view: "show", model: [bpvTissueReceiptDissectionInstance: bpvTissueReceiptDissectionInstance, warningMap:warningMap,version53:version53])
        }
    }

    def validateFields(bpvTissueReceiptDissectionInstance, errorMap) {
         def cdrVer = bpvTissueReceiptDissectionInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
        if(("No").equals(bpvTissueReceiptDissectionInstance.bloodSamplesCollected)) {
            if(!bpvTissueReceiptDissectionInstance.bloodNotReceived &&
                !bpvTissueReceiptDissectionInstance.bloodNotDrawn &&
                !bpvTissueReceiptDissectionInstance.aliquotsNotBanked &&
                !bpvTissueReceiptDissectionInstance.hemolyzedUnusableBlood &&
                !bpvTissueReceiptDissectionInstance.otherReason) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('bloodNotReceived', 'Please select the reason(s) pre-operative blood was not banked')
                errorMap.put('bloodNotBankedReason', 'errors')
            } else if(bpvTissueReceiptDissectionInstance.otherReason && !bpvTissueReceiptDissectionInstance.specifiedOtherReason) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('specifiedOtherReason', 'Please specify the other reason(s) that pre-operative blood was not banked')
            }
        } 
        
        if(("Yes").equals(bpvTissueReceiptDissectionInstance.bloodSamplesCollected) || (bpvTissueReceiptDissectionInstance?.caseRecord?.primaryTissueType?.code=='LUNG' && version53  )) {
            if(!bpvTissueReceiptDissectionInstance.dateTimeTissueReceived) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('dateTimeTissueReceived', 'Question #3 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.tissueRecipient) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('tissueRecipient', 'Question #4 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.experimentalKeyId) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('experimentalKeyId', 'Question #7 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.parentTissueDissectedBy) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('parentTissueDissectedBy', 'Question #8 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.parentTissueDissectBegan) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('parentTissueDissectBegan', 'Question #9 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.parentTissueDissectEnded) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('parentTissueDissectEnded', 'Question #10 cannot be blank')
            }

            // This Validation block has been moved from the domain class in accordance with JIRA CDRQA-1322. 09/03/2014 umkis
            if ((bpvTissueReceiptDissectionInstance.parentTissueDissectBegan)&&(bpvTissueReceiptDissectionInstance.parentTissueDissectEnded))
            {
                if (bpvTissueReceiptDissectionInstance.parentTissueDissectBegan.compareTo(bpvTissueReceiptDissectionInstance.parentTissueDissectEnded) > 0)
                {
                    bpvTissueReceiptDissectionInstance.errors.rejectValue('parentTissueDissectEnded', 'Ended time is earlier than the begin time. Please use 24 hour system.')
                }
            }
            
            
            if(!bpvTissueReceiptDissectionInstance.grossAppearance) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('grossAppearance', 'Please select an option for question #11')
            } else if(bpvTissueReceiptDissectionInstance.grossAppearance == 'Other-specify' && !bpvTissueReceiptDissectionInstance.otherGrossAppearance) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('otherGrossAppearance', 'Please specify other gross appearance of parent tissue specimen')
            }

            if((!bpvTissueReceiptDissectionInstance.tumorSource)||(bpvTissueReceiptDissectionInstance.tumorSource.trim().equals(''))) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('tumorSource', 'Question #12 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.collectionProcedure) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('collectionProcedure', 'Please select an option for question #13')
            } else if(bpvTissueReceiptDissectionInstance.collectionProcedure == 'Other-specify' && !bpvTissueReceiptDissectionInstance.otherCollectionProcedure) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('otherCollectionProcedure', 'Please specify other tissue collection procedure')
            }

            if(!bpvTissueReceiptDissectionInstance.fixativeType) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('fixativeType', 'Please select an option for question #14')
            } else if(bpvTissueReceiptDissectionInstance.fixativeType == 'Other-specify' && !bpvTissueReceiptDissectionInstance.otherFixativeType) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('otherFixativeType', 'Please specify other fixative type')
            }

            if(!bpvTissueReceiptDissectionInstance.fixativeFormula) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('fixativeFormula', 'Question #15 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.fixativePH) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('fixativePH', 'Question #16 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.fixativeManufacturer) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('fixativeManufacturer', 'Question #17 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.fixativeLotNum) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('fixativeLotNum', 'Question #18 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.dateFixativeLotNumExpired) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('dateFixativeLotNumExpired', 'Question #19 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.fixativeProductNum) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('fixativeProductNum', 'Question #20 cannot be blank')
            }

            if(!bpvTissueReceiptDissectionInstance.fixativeProductType) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('fixativeProductType', 'Please select an option for question #21')
            } else if(bpvTissueReceiptDissectionInstance.fixativeProductType == 'Other-specify' && !bpvTissueReceiptDissectionInstance.otherFixativeProductType) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('otherFixativeProductType', 'Please specify other fixative product type')
            }

            if(!bpvTissueReceiptDissectionInstance.formalinFreshRecycled) {
                bpvTissueReceiptDissectionInstance.errors.rejectValue('formalinFreshRecycled', 'Please select an option for question #22')
            }
        } 
        
        if(!bpvTissueReceiptDissectionInstance.bloodSamplesCollected) {
            bpvTissueReceiptDissectionInstance.errors.rejectValue('bloodSamplesCollected', 'Please select \'Yes\' or \'No\' for question #1')
        }
    }
    
    Map getWarningMap(bpvTissueReceiptDissectionInstance) {
        Map warningMap = [:]
        
        if ((bpvTissueReceiptDissectionInstance.tumorSource)&&(!bpvTissueReceiptDissectionInstance.tumorSource.trim().equals('')))
        {                                                                        
            if (!bpvTissueReceiptDissectionInstance.tumorSource.equalsIgnoreCase(bpvTissueReceiptDissectionInstance.caseRecord?.primaryTissueType?.toString()))
            {
                warningMap.put('tumorSource', '\'Source of tumor tissue\' is not the same as the Primary Organ - ' + bpvTissueReceiptDissectionInstance.caseRecord?.primaryTissueType?.toString() + ".")
            }
        }
                
        return warningMap
    }
}
