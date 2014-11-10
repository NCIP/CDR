package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.staticmembers.FormMetadata
import nci.obbr.cahub.staticmembers.SOP
import grails.plugins.springsecurity.Secured

class BpvTissueProcessEmbedController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def accessPrivilegeService    

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvTissueProcessEmbedInstanceList: BpvTissueProcessEmbed.list(params), bpvTissueProcessEmbedInstanceTotal: BpvTissueProcessEmbed.count()]
    }

    def create = {
        def bpvTissueProcessEmbedInstance = new BpvTissueProcessEmbed()
        bpvTissueProcessEmbedInstance.properties = params
        bpvTissueProcessEmbedInstance.expKeyBarcodeId = bpvTissueProcessEmbedInstance.caseRecord?.bpvTissueReceiptDissection?.experimentalKeyId

        return [bpvTissueProcessEmbedInstance: bpvTissueProcessEmbedInstance]
    }

    def save = {
        def bpvTissueProcessEmbedInstance = new BpvTissueProcessEmbed(params)
        
        // Populate SOP Record
        def formMetadataInstance = FormMetadata.get(params.formMetadata.id)
        def sopInstance
        if (formMetadataInstance?.sops?.size() > 0) {
            sopInstance = SOP.get(formMetadataInstance?.sops?.get(0)?.id)
            bpvTissueProcessEmbedInstance.processingSOP = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:sopInstance.activeSopVer).save(flush: true)
        }
        if (formMetadataInstance?.sops?.size() > 1) {
            sopInstance = SOP.get(formMetadataInstance?.sops?.get(1)?.id)
            bpvTissueProcessEmbedInstance.embeddingSOP = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:sopInstance.activeSopVer).save(flush: true)
        }
        
        if(bpvTissueProcessEmbedInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance?.caseRecord?.caseId])}"
            redirect(action: "editWithValidation", id: bpvTissueProcessEmbedInstance.id)
        } else {
            render(view: "create", model: [bpvTissueProcessEmbedInstance: bpvTissueProcessEmbedInstance])
        }
    }

    def show = {
        def bpvTissueProcessEmbedInstance = BpvTissueProcessEmbed.get(params.id)
        if(!bpvTissueProcessEmbedInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvTissueProcessEmbedInstance.caseRecord, session, 'view')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(bpvTissueProcessEmbedInstance.caseRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }            
            def canResume = (accessPrivilegeService.checkAccessPrivilege(bpvTissueProcessEmbedInstance.caseRecord, session, 'edit') == 0)
            [bpvTissueProcessEmbedInstance: bpvTissueProcessEmbedInstance, canResume: canResume]
        }
    }

    def edit = {
        def bpvTissueProcessEmbedInstance = BpvTissueProcessEmbed.get(params.id)
        if(!bpvTissueProcessEmbedInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvTissueProcessEmbedInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvTissueProcessEmbedInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            
            validateFields(bpvTissueProcessEmbedInstance)
            def canSubmit = !bpvTissueProcessEmbedInstance.errors.hasErrors()
            bpvTissueProcessEmbedInstance.clearErrors()
            return [bpvTissueProcessEmbedInstance: bpvTissueProcessEmbedInstance, canSubmit: canSubmit]
        }
    }

    def editWithValidation = {
        def bpvTissueProcessEmbedInstance = BpvTissueProcessEmbed.get(params.id)
        if(!bpvTissueProcessEmbedInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvTissueProcessEmbedInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvTissueProcessEmbedInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            
            validateFields(bpvTissueProcessEmbedInstance)
            render(view: "edit", model: [bpvTissueProcessEmbedInstance: bpvTissueProcessEmbedInstance, canSubmit: !bpvTissueProcessEmbedInstance.errors.hasErrors()])
        }
    }

    def update = {
        def bpvTissueProcessEmbedInstance = BpvTissueProcessEmbed.get(params.id)
        if(bpvTissueProcessEmbedInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvTissueProcessEmbedInstance.version > version) {
                    bpvTissueProcessEmbedInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvTissueProcessEmbed while you were editing")
                    render(view: "edit", model: [bpvTissueProcessEmbedInstance: bpvTissueProcessEmbedInstance])
                    return
                }
            }

            bpvTissueProcessEmbedInstance.properties = params
            if(!bpvTissueProcessEmbedInstance.hasErrors() && bpvTissueProcessEmbedInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance.caseRecord.caseId])}"
                redirect(action: "editWithValidation", id: bpvTissueProcessEmbedInstance.id)
            } else {
                render(view: "edit", model: [bpvTissueProcessEmbedInstance: bpvTissueProcessEmbedInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    def submit = {
        def bpvTissueProcessEmbedInstance = BpvTissueProcessEmbed.get(params.id)
        if(bpvTissueProcessEmbedInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvTissueProcessEmbedInstance.version > version) {

                    bpvTissueProcessEmbedInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case'] as Object[], "Another user has updated this BpvTissueProcessEmbed while you were editing")
                    render(view: "edit", model: [bpvTissueProcessEmbedInstance: bpvTissueProcessEmbedInstance])
                    return
                }
            }

            bpvTissueProcessEmbedInstance.properties = params
            validateFields(bpvTissueProcessEmbedInstance)

            if(!bpvTissueProcessEmbedInstance.hasErrors() && bpvTissueProcessEmbedInstance.save(flush: true)) {
                bpvTissueProcessEmbedInstance.dateSubmitted = new Date()
                bpvTissueProcessEmbedInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()

                flash.message = "${message(code: 'default.submitted.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance.caseRecord.caseId])}"
                redirect(controller: "caseRecord", action: "display", id: bpvTissueProcessEmbedInstance.caseRecord.id)
            } else {
                bpvTissueProcessEmbedInstance.discard()
                render(view: "edit", model: [bpvTissueProcessEmbedInstance: bpvTissueProcessEmbedInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def bpvTissueProcessEmbedInstance = BpvTissueProcessEmbed.get(params.id)
        if(bpvTissueProcessEmbedInstance) {
            try {
                bpvTissueProcessEmbedInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance.caseRecord.caseId])}"
                redirect(action: "list")
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance.caseRecord.caseId])}"
                redirect(action: "show", id: params.id)
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvTissueProcessEmbedInstance?.formMetadata?.cdrFormName + ' for Case', bpvTissueProcessEmbedInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    def resumeEditing = {
        def bpvTissueProcessEmbedInstance = BpvTissueProcessEmbed.get(params.id)
        bpvTissueProcessEmbedInstance.dateSubmitted = null
        bpvTissueProcessEmbedInstance.submittedBy = null

        if(bpvTissueProcessEmbedInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvTissueProcessEmbedInstance.id)
        } else {
            render(view: "show", model: [bpvTissueProcessEmbedInstance: bpvTissueProcessEmbedInstance])
        }
    }

    def validateFields(bpvTissueProcessEmbedInstance) {
        if(!bpvTissueProcessEmbedInstance.expKeyBarcodeId) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('expKeyBarcodeId', 'Question #1 cannot be blank')
        }

        if(!bpvTissueProcessEmbedInstance.tissProcessorMdl) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('tissProcessorMdl', 'Please select an option for question #4')
        } else if(bpvTissueProcessEmbedInstance.tissProcessorMdl == 'Other, Specify' && !bpvTissueProcessEmbedInstance.othTissProcessorMdl) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othTissProcessorMdl', 'Please specify other for question #4')
        }

        if(!bpvTissueProcessEmbedInstance.procMaintenance) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('procMaintenance', 'Please select an option for question #5')
        } else if(bpvTissueProcessEmbedInstance.procMaintenance == 'No - Specify' && !bpvTissueProcessEmbedInstance.othProcMaintenance) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othProcMaintenance', 'Please specify other for question #5')
        }

        if(!bpvTissueProcessEmbedInstance.alcoholType) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('alcoholType', 'Please select an option for question #6')
        } else if(bpvTissueProcessEmbedInstance.alcoholType == 'Other, Specify' && !bpvTissueProcessEmbedInstance.othAlcoholType) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othAlcoholType', 'Please specify other for question #6')
        }

        if(!bpvTissueProcessEmbedInstance.clearingAgt) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('clearingAgt', 'Please select an option for question #7')
        } else if(bpvTissueProcessEmbedInstance.clearingAgt == 'Other, Specify' && !bpvTissueProcessEmbedInstance.othClearingAgt) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othClearingAgt', 'Please specify other for question #7')
        }

        if(!bpvTissueProcessEmbedInstance.alcoholStgDur) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('alcoholStgDur', 'Please select an option for question #8')
        } else if(bpvTissueProcessEmbedInstance.alcoholStgDur == 'No - Specify' && !bpvTissueProcessEmbedInstance.othAlcoholStgDur) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othAlcoholStgDur', 'Please specify other for question #8')
        }

        if(!bpvTissueProcessEmbedInstance.dehydrationProcDur) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('dehydrationProcDur', 'Please select an option for question #9')
        } else if(bpvTissueProcessEmbedInstance.dehydrationProcDur == 'No - Specify' && !bpvTissueProcessEmbedInstance.othDehydrationProcDur) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othDehydrationProcDur', 'Please specify other for question #9')
        }

        if(!bpvTissueProcessEmbedInstance.temperatureDehyd) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('temperatureDehyd', 'Please select an option for question #10')
        } else if(bpvTissueProcessEmbedInstance.temperatureDehyd == 'No - Specify' && !bpvTissueProcessEmbedInstance.othTemperatureDehyd) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othTemperatureDehyd', 'Please specify other for question #10')
        }

        if(!bpvTissueProcessEmbedInstance.numStages) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('numStages', 'Please select an option for question #11')
        } else if(bpvTissueProcessEmbedInstance.numStages == 'No - Specify' && !bpvTissueProcessEmbedInstance.othNumStages) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othNumStages', 'Please specify other for question #11')
        }

        if(!bpvTissueProcessEmbedInstance.clearingAgtDur) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('clearingAgtDur', 'Please select an option for question #12')
        } else if(bpvTissueProcessEmbedInstance.clearingAgtDur == 'No - Specify' && !bpvTissueProcessEmbedInstance.othClearingAgtDur) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othClearingAgtDur', 'Please specify other for question #12')
        }

        if(!bpvTissueProcessEmbedInstance.temperatureClearingAgt) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('temperatureClearingAgt', 'Please select an option for question #13')
        } else if(bpvTissueProcessEmbedInstance.temperatureClearingAgt == 'No - Specify' && !bpvTissueProcessEmbedInstance.othTemperatureClearingAgt) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othTemperatureClearingAgt', 'Please specify other for question #13')
        }

        if(!bpvTissueProcessEmbedInstance.paraffImpreg) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('paraffImpreg', 'Please select an option for question #14')
        } else if(bpvTissueProcessEmbedInstance.paraffImpreg == 'No - Specify' && !bpvTissueProcessEmbedInstance.othParaffImpreg) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othParaffImpreg', 'Please specify other for question #14')
        }

        if(!bpvTissueProcessEmbedInstance.tempParaffinProc) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('tempParaffinProc', 'Please select an option for question #15')
        } else if(bpvTissueProcessEmbedInstance.tempParaffinProc == 'No - Specify' && !bpvTissueProcessEmbedInstance.othTempParaffinProc) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othTempParaffinProc', 'Please specify other for question #15')
        }

        if(!bpvTissueProcessEmbedInstance.typeParaffin) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('typeParaffin', 'Question #18 cannot be blank')
        }

        if(!bpvTissueProcessEmbedInstance.paraffinManufacturer) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('paraffinManufacturer', 'Please select an option for question #19')
        } else if(bpvTissueProcessEmbedInstance.paraffinManufacturer == 'Other, Specify' && !bpvTissueProcessEmbedInstance.otherParaffinManufacturer) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('otherParaffinManufacturer', 'Please specify other for question #19')
        }

        if(!bpvTissueProcessEmbedInstance.paraffinProductNum) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('paraffinProductNum', 'Question #20 cannot be blank')
        }

        if(!bpvTissueProcessEmbedInstance.paraffinLotNum) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('paraffinLotNum', 'Question #21 cannot be blank')
        }

        if(!bpvTissueProcessEmbedInstance.tempParaffinEmbed) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('tempParaffinEmbed', 'Question #22 cannot be blank')
        }

        if(!bpvTissueProcessEmbedInstance.paraffinUsage) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('paraffinUsage', 'Please select an option for question #23')
        } else if(bpvTissueProcessEmbedInstance.paraffinUsage == 'Other, Specify' && !bpvTissueProcessEmbedInstance.otherParaffinUsage) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('otherParaffinUsage', 'Please specify other for question #23')
        }

        if(!bpvTissueProcessEmbedInstance.waxAge) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('waxAge', 'Question #24 cannot be blank')
        }

        if(!bpvTissueProcessEmbedInstance.totalTimeBlocksCooled) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('totalTimeBlocksCooled', 'Question #25 cannot be blank')
        }

        if(!bpvTissueProcessEmbedInstance.siteSOPProcEmbed) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('siteSOPProcEmbed', 'Question #26 cannot be blank')
        }

        if(bpvTissueProcessEmbedInstance.storedFfpeBlocksPerSop == 'No - Specify' && !bpvTissueProcessEmbedInstance.othStoredFfpeBlocksPerSop) {
            bpvTissueProcessEmbedInstance.errors.rejectValue('othStoredFfpeBlocksPerSop', 'Please specify other for question #27')
        }
    }
}
