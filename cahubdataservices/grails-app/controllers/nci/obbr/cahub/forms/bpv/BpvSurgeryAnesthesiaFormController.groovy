package nci.obbr.cahub.forms.bpv

import grails.converters.JSON
import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.staticmembers.FormMetadata
import nci.obbr.cahub.staticmembers.SOP
import grails.plugins.springsecurity.Secured

class BpvSurgeryAnesthesiaFormController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def accessPrivilegeService
    
    def scaffold = BpvSurgeryAnesthesiaForm

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvSurgeryAnesthesiaFormInstanceList: BpvSurgeryAnesthesiaForm.list(params), bpvSurgeryAnesthesiaFormInstanceTotal: BpvSurgeryAnesthesiaForm.count()]
    }

    def create = {
        def bpvSurgeryAnesthesiaFormInstance = new BpvSurgeryAnesthesiaForm()
        bpvSurgeryAnesthesiaFormInstance.properties = params
        return [bpvSurgeryAnesthesiaFormInstance: bpvSurgeryAnesthesiaFormInstance]
    }

    def save = {
        def bpvSurgeryAnesthesiaFormInstance = new BpvSurgeryAnesthesiaForm(params)
        
        // Populate SOP Record
        def formMetadataInstance = FormMetadata.get(params.formMetadata.id)
        def sopInstance
        if (formMetadataInstance?.sops?.size() > 0) {
            sopInstance = SOP.get(formMetadataInstance?.sops?.get(0)?.id)
            bpvSurgeryAnesthesiaFormInstance.formSOP = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:sopInstance.activeSopVer).save(flush: true)
        }
        
        if (bpvSurgeryAnesthesiaFormInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance?.caseRecord?.caseId])}"
            redirect(action: "editWithValidation", id: bpvSurgeryAnesthesiaFormInstance.id)
        } else {
            render(view: "create", model: [bpvSurgeryAnesthesiaFormInstance: bpvSurgeryAnesthesiaFormInstance])
        }
    }

     def show = {
        def bpvSurgeryAnesthesiaFormInstance = BpvSurgeryAnesthesiaForm.get(params.id)
        if(!bpvSurgeryAnesthesiaFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvSurgeryAnesthesiaFormInstance.caseRecord, session, 'view')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(bpvSurgeryAnesthesiaFormInstance.caseRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }            
            def canResume = (accessPrivilegeService.checkAccessPrivilege(bpvSurgeryAnesthesiaFormInstance.caseRecord, session, 'edit') == 0)
            [bpvSurgeryAnesthesiaFormInstance: bpvSurgeryAnesthesiaFormInstance, canResume: canResume]
        }
    }

    def edit = {
        def bpvSurgeryAnesthesiaFormInstance = BpvSurgeryAnesthesiaForm.get(params.id)
        if(!bpvSurgeryAnesthesiaFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvSurgeryAnesthesiaFormInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvSurgeryAnesthesiaFormInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            
            validateFields(bpvSurgeryAnesthesiaFormInstance)
            def canSubmit = !bpvSurgeryAnesthesiaFormInstance.errors.hasErrors()
            bpvSurgeryAnesthesiaFormInstance.clearErrors()
            return [bpvSurgeryAnesthesiaFormInstance: bpvSurgeryAnesthesiaFormInstance, canSubmit: canSubmit]
        }
    }

    def editWithValidation = {
        def bpvSurgeryAnesthesiaFormInstance = BpvSurgeryAnesthesiaForm.get(params.id)
        if(!bpvSurgeryAnesthesiaFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvSurgeryAnesthesiaFormInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvSurgeryAnesthesiaFormInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            
            validateFields(bpvSurgeryAnesthesiaFormInstance)
            render(view: "edit", model: [bpvSurgeryAnesthesiaFormInstance: bpvSurgeryAnesthesiaFormInstance, canSubmit: !bpvSurgeryAnesthesiaFormInstance.errors.hasErrors()])
        }
    }

    def update = {
        def bpvSurgeryAnesthesiaFormInstance = BpvSurgeryAnesthesiaForm.get(params.id)
        if (bpvSurgeryAnesthesiaFormInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bpvSurgeryAnesthesiaFormInstance.version > version) {
                    
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BPV Surgery/Anesthesia Form while you were editing")
                    render(view: "edit", model: [bpvSurgeryAnesthesiaFormInstance: bpvSurgeryAnesthesiaFormInstance])
                    return
                }
            }
            bpvSurgeryAnesthesiaFormInstance.properties = params
            if (!bpvSurgeryAnesthesiaFormInstance.hasErrors() && bpvSurgeryAnesthesiaFormInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance?.caseRecord?.caseId])}"
                //redirect(action: "edit", id: bpvSurgeryAnesthesiaFormInstance.id)
                redirect(action: "editWithValidation", id: bpvSurgeryAnesthesiaFormInstance.id)
            } else {
                render(view: "edit", model: [bpvSurgeryAnesthesiaFormInstance: bpvSurgeryAnesthesiaFormInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }

    def submit = {
        def bpvSurgeryAnesthesiaFormInstance = BpvSurgeryAnesthesiaForm.get(params.id)
        if(bpvSurgeryAnesthesiaFormInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvSurgeryAnesthesiaFormInstance.version > version) {
                    
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case'] as Object[], "Another user has updated this BpvSurgeryAnesthesiaForm while you were editing")
                    render(view: "edit", model: [bpvSurgeryAnesthesiaFormInstance: bpvSurgeryAnesthesiaFormInstance])
                    return
                }
            }

            bpvSurgeryAnesthesiaFormInstance.properties = params
            validateFields(bpvSurgeryAnesthesiaFormInstance)

            if(!bpvSurgeryAnesthesiaFormInstance.hasErrors() && bpvSurgeryAnesthesiaFormInstance.save(flush: true)) {
                bpvSurgeryAnesthesiaFormInstance.dateSubmitted = new Date()
                bpvSurgeryAnesthesiaFormInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()

                flash.message = "${message(code: 'default.submitted.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance.caseRecord.caseId])}"
                redirect(controller: "caseRecord", action: "display", id: bpvSurgeryAnesthesiaFormInstance.caseRecord.id)
            } else {
                bpvSurgeryAnesthesiaFormInstance.discard()
                render(view: "edit", model: [bpvSurgeryAnesthesiaFormInstance: bpvSurgeryAnesthesiaFormInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def bpvSurgeryAnesthesiaFormInstance = BpvSurgeryAnesthesiaForm.get(params.id)
        if(bpvSurgeryAnesthesiaFormInstance) {
            try {
                bpvSurgeryAnesthesiaFormInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance.caseRecord.caseId])}"
                redirect(action: "list")
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance.caseRecord.caseId])}"
                redirect(action: "show", id: params.id)
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    def resumeEditing = {
        def bpvSurgeryAnesthesiaFormInstance = BpvSurgeryAnesthesiaForm.get(params.id)
        bpvSurgeryAnesthesiaFormInstance.dateSubmitted = null
        bpvSurgeryAnesthesiaFormInstance.submittedBy = null

        if(bpvSurgeryAnesthesiaFormInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvSurgeryAnesthesiaFormInstance.id)
        } else {
            render(view: "show", model: [bpvSurgeryAnesthesiaFormInstance: bpvSurgeryAnesthesiaFormInstance])
        }
    }
    
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_NCI-FREDERICK_CAHUB_DM','ROLE_ADMIN']) 
    def forceSubmit = {
        def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        def bpvSurgeryAnesthesiaFormInstance = BpvSurgeryAnesthesiaForm.get(params.id)
        bpvSurgeryAnesthesiaFormInstance.submittedBy = "forcedby:" + username
        bpvSurgeryAnesthesiaFormInstance.dateSubmitted = new Date()

            if (bpvSurgeryAnesthesiaFormInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.forcesubmissionsuccess.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance.caseRecord.caseId])}"
                //redirect(action: "show", id: bpvSurgeryAnesthesiaFormInstance.id)
                redirect(controller: "caseRecord", action: "display", id: bpvSurgeryAnesthesiaFormInstance.caseRecord.id)
            }        
            else {
                bpvSurgeryAnesthesiaFormInstance.discard()
                flash.message = "${message(code: 'default.forcesubmissionfailed.message', args: [bpvSurgeryAnesthesiaFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvSurgeryAnesthesiaFormInstance.caseRecord.caseId])}"                
                redirect(controller: "caseRecord", action: "display", id: bpvSurgeryAnesthesiaFormInstance.caseRecord.id)
            }            
    }    

    def validateFields(bpvSurgeryAnesthesiaFormInstance) {
        String primaryTissueType = bpvSurgeryAnesthesiaFormInstance.caseRecord?.primaryTissueType?.toString()
        boolean isPrimaryTissueTypeLung = "Lung".equals(primaryTissueType)
        boolean isPrimaryTissueTypeOvary = "Ovary".equals(primaryTissueType)
        boolean isPrimaryTissueTypeColon = "Colon".equals(primaryTissueType)
        boolean isPrimaryTissueTypeKidney = "Kidney".equals(primaryTissueType)

        setError(bpvSurgeryAnesthesiaFormInstance, 'surgeryDate', bpvSurgeryAnesthesiaFormInstance.surgeryDate, 'Surgery date is required.');

        
        if(bpvSurgeryAnesthesiaFormInstance.poSedDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('poSedDiv', 'Please specify Yes/No to state if Pre-operative IV Sedation drugs were administered.')
        } else {
            if(bpvSurgeryAnesthesiaFormInstance.poSedDiv.equals("Yes")) {
                if(!bpvSurgeryAnesthesiaFormInstance.poSed1Name && !bpvSurgeryAnesthesiaFormInstance.poSed2Name &&
                    !bpvSurgeryAnesthesiaFormInstance.poSed3Name && !bpvSurgeryAnesthesiaFormInstance.poSed4Name) {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('poSed1Name', 'Please choose at least one Pre-operative IV Sedation drug and enter its dosage, unit and time')
                } else {
                    if(bpvSurgeryAnesthesiaFormInstance.poSed1Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed1Dose', bpvSurgeryAnesthesiaFormInstance.poSed1Dose, 'The Pre-operative IV Sedation administered drug (Diazepam) entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed1Unit', bpvSurgeryAnesthesiaFormInstance.poSed1Unit, 'The Pre-operative IV Sedation administered drug (Diazepam) entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed1Time', bpvSurgeryAnesthesiaFormInstance.poSed1Time, 'The Pre-operative IV Sedation administered drug (Diazepam) entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.poSed2Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed2Dose', bpvSurgeryAnesthesiaFormInstance.poSed2Dose, 'The Pre-operative IV Sedation administered drug (Lorazepam) entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed2Unit', bpvSurgeryAnesthesiaFormInstance.poSed2Unit, 'The Pre-operative IV Sedation administered drug (Lorazepam) entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed2Time', bpvSurgeryAnesthesiaFormInstance.poSed2Time, 'The Pre-operative IV Sedation administered drug (Lorazepam) entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.poSed3Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed3Dose', bpvSurgeryAnesthesiaFormInstance.poSed3Dose, 'The Pre-operative IV Sedation administered drug (Midazolam) entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed3Unit', bpvSurgeryAnesthesiaFormInstance.poSed3Unit, 'The Pre-operative IV Sedation administered drug (Midazolam) entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed3Time', bpvSurgeryAnesthesiaFormInstance.poSed3Time, 'The Pre-operative IV Sedation administered drug (Midazolam) entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.poSed4Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed4Dose', bpvSurgeryAnesthesiaFormInstance.poSed4Dose, 'The Other IV Sedation administered drug entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed4Unit', bpvSurgeryAnesthesiaFormInstance.poSed4Unit, 'The Other IV Sedation administered drug entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poSed4Time', bpvSurgeryAnesthesiaFormInstance.poSed4Time, 'The Other IV Sedation administered drug entry is incomplete. Please enter the time.');
                    }
                }
            }
        }


        if(bpvSurgeryAnesthesiaFormInstance.poOpDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('poOpDiv', 'Please specify Yes/No to state if Pre-operative IV Opiate drugs were administered.')
        } else {
            if(bpvSurgeryAnesthesiaFormInstance.poOpDiv.equals("Yes")) {        
                if(!bpvSurgeryAnesthesiaFormInstance.poOp1Name && !bpvSurgeryAnesthesiaFormInstance.poOp2Name &&
                        !bpvSurgeryAnesthesiaFormInstance.poOp3Name && !bpvSurgeryAnesthesiaFormInstance.poOp4Name && !bpvSurgeryAnesthesiaFormInstance.poOp5Name) {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('poOp1Name', 'Please choose at least one Pre-operative IV Opiates drug and enter its dosage, unit and time')
                } else {
                    if(bpvSurgeryAnesthesiaFormInstance.poOp1Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp1Dose', bpvSurgeryAnesthesiaFormInstance.poOp1Dose, 'The Pre-operative IV Opiates administered drug (Fentanyl) entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp1Unit', bpvSurgeryAnesthesiaFormInstance.poOp1Unit, 'The Pre-operative IV Opiates administered drug (Fentanyl) entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp1Time', bpvSurgeryAnesthesiaFormInstance.poOp1Time, 'The Pre-operative IV Opiates administered drug (Fentanyl) entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.poOp2Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp2Dose', bpvSurgeryAnesthesiaFormInstance.poOp2Dose, 'The Pre-operative IV Opiates administered drug (Hydromorphone) entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp2Unit', bpvSurgeryAnesthesiaFormInstance.poOp2Unit, 'The Pre-operative IV Opiates administered drug (Hydromorphone) entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp2Time', bpvSurgeryAnesthesiaFormInstance.poOp2Time, 'The Pre-operative IV Opiates administered drug (Hydromorphone) entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.poOp3Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp3Dose', bpvSurgeryAnesthesiaFormInstance.poOp3Dose, 'The Pre-operative IV Opiates administered drug (Meperidine) entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp3Unit', bpvSurgeryAnesthesiaFormInstance.poOp3Unit, 'The Pre-operative IV Opiates administered drug (Meperidine) entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp3Time', bpvSurgeryAnesthesiaFormInstance.poOp3Time, 'The Pre-operative IV Opiates administered drug (Meperidine) entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.poOp4Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp4Dose', bpvSurgeryAnesthesiaFormInstance.poOp4Dose, 'The Pre-operative IV Opiates administered drug (Morphine) entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp4Unit', bpvSurgeryAnesthesiaFormInstance.poOp4Unit, 'The Pre-operative IV Opiates administered drug (Morphine) entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp4Time', bpvSurgeryAnesthesiaFormInstance.poOp4Time, 'The Pre-operative IV Opiates administered drug (Morphine) entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.poOp5Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp5Dose', bpvSurgeryAnesthesiaFormInstance.poOp5Dose, 'The Other IV Opiates administered drug entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp5Unit', bpvSurgeryAnesthesiaFormInstance.poOp5Unit, 'The Other IV Opiates administered drug entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poOp5Time', bpvSurgeryAnesthesiaFormInstance.poOp5Time, 'The Other IV Opiates administered drug entry is incomplete. Please enter the time.');
                    }
                }
            }
        }

        
        if(bpvSurgeryAnesthesiaFormInstance.poAntiemDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('poAntiemDiv', 'Please specify Yes/No to state if Pre-operative IV Antiemetic drugs were administered.')
        } else {
            if(bpvSurgeryAnesthesiaFormInstance.poAntiemDiv.equals("Yes")) {
                if(!bpvSurgeryAnesthesiaFormInstance.poAntiem1Name &&
                    !bpvSurgeryAnesthesiaFormInstance.poAntiem2Name && !bpvSurgeryAnesthesiaFormInstance.poAntiem3Name) {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('poAntiem1Name', 'Please choose at least one Pre-operative IV Antiemetics drug and enter its dosage, unit and time')
                } else {
                    if(bpvSurgeryAnesthesiaFormInstance.poAntiem1Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiem1Dose', bpvSurgeryAnesthesiaFormInstance.poAntiem1Dose, 'The Pre-operative IV Antiemetics administered drug (Droperidol) entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiem1Unit', bpvSurgeryAnesthesiaFormInstance.poAntiem1Unit, 'The Pre-operative IV Antiemetics administered drug (Droperidol) entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiem1Time', bpvSurgeryAnesthesiaFormInstance.poAntiem1Time, 'The Pre-operative IV Antiemetics administered drug (Droperidol) entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.poAntiem2Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiem2Dose', bpvSurgeryAnesthesiaFormInstance.poAntiem2Dose, 'The Pre-operative IV Antiemetic administered drug (Ondansetron) entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiem2Unit', bpvSurgeryAnesthesiaFormInstance.poAntiem2Unit, 'The Pre-operative IV Antiemetic administered drug (Ondansetron) entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiem2Time', bpvSurgeryAnesthesiaFormInstance.poAntiem2Time, 'The Pre-operative IV Antiemetic administered drug (Ondansetron) entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.poAntiem3Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiem3Dose', bpvSurgeryAnesthesiaFormInstance.poAntiem3Dose, 'The Other IV Antiemetic administered drug entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiem3Unit', bpvSurgeryAnesthesiaFormInstance.poAntiem3Unit, 'The Other IV Antiemetic administered drug entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiem3Time', bpvSurgeryAnesthesiaFormInstance.poAntiem3Time, 'The Other IV Antiemetic administered drug entry is incomplete. Please enter the time.');
                    }
                }
            }
        }

        
        if(bpvSurgeryAnesthesiaFormInstance.poAntiAcDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('poAntiAcDiv', 'Please specify Yes/No to state if Pre-operative IV Anti-acid drugs were administered.')
        } else {
            if(bpvSurgeryAnesthesiaFormInstance.poAntiAcDiv.equals("Yes")) {        
                if(!bpvSurgeryAnesthesiaFormInstance.poAntiAc1Name && !bpvSurgeryAnesthesiaFormInstance.poAntiAc2Name) {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('poAntiAc1Name', 'Please choose at least one Pre-operative IV Anti-acids drug and enter its dosage, unit and time')
                } else {
                    if(bpvSurgeryAnesthesiaFormInstance.poAntiAc1Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiAc1Dose', bpvSurgeryAnesthesiaFormInstance.poAntiAc1Dose, 'The Pre-operative IV Anti-acids administered drug (Ranitidine) entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiAc1Unit', bpvSurgeryAnesthesiaFormInstance.poAntiAc1Unit, 'The Pre-operative IV Anti-acids administered drug (Ranitidine) entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiAc1Time', bpvSurgeryAnesthesiaFormInstance.poAntiAc1Time, 'The Pre-operative IV Anti-acids administered drug (Ranitidine) entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.poAntiAc2Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiAc2Dose', bpvSurgeryAnesthesiaFormInstance.poAntiAc2Dose, 'The Other IV Anti-acids administered drug entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiAc2Unit', bpvSurgeryAnesthesiaFormInstance.poAntiAc2Unit, 'The Other IV Anti-acids administered drug entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'poAntiAc2Time', bpvSurgeryAnesthesiaFormInstance.poAntiAc2Time, 'The Other IV Anti-acids administered drug entry is incomplete. Please enter the time.');
                    }
                }
            }
        }

        if(bpvSurgeryAnesthesiaFormInstance.poMedDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('poMedDiv', 'Please specify Yes/No to state if Other Pre-operative IV Medications were administered.')
        } else {
                if(bpvSurgeryAnesthesiaFormInstance.poMedDiv.equals("Yes")) {
                    if(!bpvSurgeryAnesthesiaFormInstance.poMed1Name && !bpvSurgeryAnesthesiaFormInstance.poMed2Name && !bpvSurgeryAnesthesiaFormInstance.poMed3Name) {
                        bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('poMed1Name', 'Please enter one (or more) Pre-operative IV drug(s) used but not mentioned above and enter its dosage, unit and time')
                    } else {
                        if(bpvSurgeryAnesthesiaFormInstance.poMed1Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'poMed1Dose', bpvSurgeryAnesthesiaFormInstance.poMed1Dose, 'The Other Pre-operative IV Medications first entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'poMed1Unit', bpvSurgeryAnesthesiaFormInstance.poMed1Unit, 'The Other Pre-operative IV Medications first entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'poMed1Time', bpvSurgeryAnesthesiaFormInstance.poMed1Time, 'The Other Pre-operative IV Medications first entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.poMed2Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'poMed2Dose', bpvSurgeryAnesthesiaFormInstance.poMed2Dose, 'The Other Pre-operative IV Medications second entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'poMed2Unit', bpvSurgeryAnesthesiaFormInstance.poMed2Unit, 'The Other Pre-operative IV Medications second entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'poMed2Time', bpvSurgeryAnesthesiaFormInstance.poMed2Time, 'The Other Pre-operative IV Medications second entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.poMed3Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'poMed3Dose', bpvSurgeryAnesthesiaFormInstance.poMed3Dose, 'The Other Pre-operative IV Medications third entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'poMed3Unit', bpvSurgeryAnesthesiaFormInstance.poMed3Unit, 'The Other Pre-operative IV Medications third entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'poMed3Time', bpvSurgeryAnesthesiaFormInstance.poMed3Time, 'The Other Pre-operative IV Medications third entry is incomplete. Please enter the time.');
                        }
                    }
            }
        }

        //setError(bpvSurgeryAnesthesiaFormInstance, 'anesIndStartTime', bpvSurgeryAnesthesiaFormInstance.anesIndStartTime, 'Time Anesthesia Induction began is required');

        if(bpvSurgeryAnesthesiaFormInstance.localAnesDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('localAnesDiv', 'Please specify Yes/No to state if Local Anesthesia Agents were administered.')
        } else {
               if(bpvSurgeryAnesthesiaFormInstance.localAnesDiv.equals("Yes")) {
                    if(!bpvSurgeryAnesthesiaFormInstance.localAnes1Name &&
                        !bpvSurgeryAnesthesiaFormInstance.localAnes2Name &&
                            !bpvSurgeryAnesthesiaFormInstance.localAnes3Name) {
                        bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('localAnes1Name', 'Please choose at least one Local Anesthesia Agents drug and enter its dosage, unit and time')
                    } else {
                        if(bpvSurgeryAnesthesiaFormInstance.localAnes1Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'localAnes1Dose', bpvSurgeryAnesthesiaFormInstance.localAnes1Dose, 'The Local Anesthesia Agents administered drug (Lidocaine) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'localAnes1Unit', bpvSurgeryAnesthesiaFormInstance.localAnes1Unit, 'The Local Anesthesia Agents administered drug (Lidocaine) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'localAnes1Time', bpvSurgeryAnesthesiaFormInstance.localAnes1Time, 'The Local Anesthesia Agents administered drug (Lidocaine) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.localAnes2Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'localAnes2Dose', bpvSurgeryAnesthesiaFormInstance.localAnes2Dose, 'The Local Anesthesia Agents administered drug (Procaine) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'localAnes2Unit', bpvSurgeryAnesthesiaFormInstance.localAnes2Unit, 'The Local Anesthesia Agents administered drug (Procaine) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'localAnes2Time', bpvSurgeryAnesthesiaFormInstance.localAnes2Time, 'The Local Anesthesia Agents administered drug (Procaine) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.localAnes3Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'localAnes3Dose', bpvSurgeryAnesthesiaFormInstance.localAnes3Dose, 'The Other IV Local Anesthesia Agents administered drug entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'localAnes3Unit', bpvSurgeryAnesthesiaFormInstance.localAnes3Unit, 'The Other IV Local Anesthesia Agents administered drug entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'localAnes3Time', bpvSurgeryAnesthesiaFormInstance.localAnes3Time, 'The Other IV Local Anesthesia Agents administered drug entry is incomplete. Please enter the time.');
                        }
                    }
                }
        }

        
        if(bpvSurgeryAnesthesiaFormInstance.regAnesDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('regAnesDiv', 'Please specify Yes/No to state if Regional Anesthesia Agents were administered.')
        } else {
               if(bpvSurgeryAnesthesiaFormInstance.regAnesDiv.equals("Yes")) {
                    if(!bpvSurgeryAnesthesiaFormInstance.regAnes1Name &&
                        !bpvSurgeryAnesthesiaFormInstance.regAnes2Name &&
                            !bpvSurgeryAnesthesiaFormInstance.regAnes3Name) {
                        bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('regAnes1Name', 'Please choose at least one Regional (Spinal/Epidural) Anesthesia Agents drug and enter its dosage, unit and time')
                    } else {
                        if(bpvSurgeryAnesthesiaFormInstance.regAnes1Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'regAnes1Dose', bpvSurgeryAnesthesiaFormInstance.regAnes1Dose, 'The Regional (Spinal/Epidural) Anesthesia Agents administered drug (Bupivacaine) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'regAnes1Unit', bpvSurgeryAnesthesiaFormInstance.regAnes1Unit, 'The Regional (Spinal/Epidural) Anesthesia Agents administered drug (Bupivacaine) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'regAnes1Time', bpvSurgeryAnesthesiaFormInstance.regAnes1Time, 'The Regional (Spinal/Epidural) Anesthesia Agents administered drug (Bupivacaine) entry is incomplete. Please enter the time.')
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.regAnes2Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'regAnes2Dose', bpvSurgeryAnesthesiaFormInstance.regAnes2Dose, 'The Regional (Spinal/Epidural) Anesthesia Agents administered drug (Lidocaine) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'regAnes2Unit', bpvSurgeryAnesthesiaFormInstance.regAnes2Unit, 'The Regional (Spinal/Epidural) Anesthesia Agents administered drug (Lidocaine) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'regAnes2Time', bpvSurgeryAnesthesiaFormInstance.regAnes2Time, 'The Regional (Spinal/Epidural) Anesthesia Agents administered drug (Lidocaine) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.regAnes3Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'regAnes3Dose', bpvSurgeryAnesthesiaFormInstance.regAnes3Dose, 'The Other Spinal/Regional Anesthetic administered drug entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'regAnes3Unit', bpvSurgeryAnesthesiaFormInstance.regAnes3Unit, 'The Other Spinal/Regional Anesthetic administered drug entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'regAnes3Time', bpvSurgeryAnesthesiaFormInstance.regAnes3Time, 'The Other Spinal/Regional Anesthetic administered drug entry is incomplete. Please enter the time.');
                        }
                    }
               }
        }

        
        if(bpvSurgeryAnesthesiaFormInstance.anesDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('anesDiv', 'Please specify Yes/No to state if IV Anesthesia Agents were administered.')
        } else {
               if(bpvSurgeryAnesthesiaFormInstance.anesDiv.equals("Yes")) {
                    if(!bpvSurgeryAnesthesiaFormInstance.anes1Name && !bpvSurgeryAnesthesiaFormInstance.anes2Name &&
                        !bpvSurgeryAnesthesiaFormInstance.anes3Name && !bpvSurgeryAnesthesiaFormInstance.anes4Name &&
                            !bpvSurgeryAnesthesiaFormInstance.anes5Name && !bpvSurgeryAnesthesiaFormInstance.anes6Name) {
                        bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('anes1Name', 'Please choose at least one IV Anesthesia Agents drug and enter its dosage, unit and time')
                    } else {
                        if(bpvSurgeryAnesthesiaFormInstance.anes1Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes1Dose', bpvSurgeryAnesthesiaFormInstance.anes1Dose, 'The IV Anesthesia Agents administered drug (Brevital) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes1Unit', bpvSurgeryAnesthesiaFormInstance.anes1Unit, 'The IV Anesthesia Agents administered drug (Brevital) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes1Time', bpvSurgeryAnesthesiaFormInstance.anes1Time, 'The IV Anesthesia Agents administered drug (Brevital) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.anes2Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes2Dose', bpvSurgeryAnesthesiaFormInstance.anes2Dose, 'The IV Anesthesia Agents administered drug (Etomidate) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes2Unit', bpvSurgeryAnesthesiaFormInstance.anes2Unit, 'The IV Anesthesia Agents administered drug (Etomidate) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes2Time', bpvSurgeryAnesthesiaFormInstance.anes2Time, 'The IV Anesthesia Agents administered drug (Etomidate) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.anes3Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes3Dose', bpvSurgeryAnesthesiaFormInstance.anes3Dose, 'The IV Anesthesia Agents administered drug (Ketamine) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes3Unit', bpvSurgeryAnesthesiaFormInstance.anes3Unit, 'The IV Anesthesia Agents administered drug (Ketamine) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes3Time', bpvSurgeryAnesthesiaFormInstance.anes3Time, 'The IV Anesthesia Agents administered drug (Ketamine) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.anes4Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes4Dose', bpvSurgeryAnesthesiaFormInstance.anes4Dose, 'The IV Anesthesia Agents administered drug (Propofol) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes4Unit', bpvSurgeryAnesthesiaFormInstance.anes4Unit, 'The IV Anesthesia Agents administered drug (Propofol) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes4Time', bpvSurgeryAnesthesiaFormInstance.anes4Time, 'The IV Anesthesia Agents administered drug (Propofol) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.anes5Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes5Dose', bpvSurgeryAnesthesiaFormInstance.anes5Dose, 'The IV Anesthesia Agents administered drug (Sodium Thiopental) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes5Unit', bpvSurgeryAnesthesiaFormInstance.anes5Unit, 'The IV Anesthesia Agents administered drug (Sodium Thiopental) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes5Time', bpvSurgeryAnesthesiaFormInstance.anes5Time, 'The IV Anesthesia Agents administered drug (Sodium Thiopental) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.anes6Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes6Dose', bpvSurgeryAnesthesiaFormInstance.anes6Dose, 'The Other IV Anesthesia Agents administered drug entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes6Unit', bpvSurgeryAnesthesiaFormInstance.anes6Unit, 'The Other IV Anesthesia Agents administered drug entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'anes6Time', bpvSurgeryAnesthesiaFormInstance.anes6Time, 'The Other IV Anesthesia Agents administered drug entry is incomplete. Please enter the time.');
                        }
                    }
               }
        }

        
        if(bpvSurgeryAnesthesiaFormInstance.narcOpDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('narcOpDiv', 'Please specify Yes/No to state if IV Narcotic/Opiate Agents were administered.')
        } else {
               if(bpvSurgeryAnesthesiaFormInstance.narcOpDiv.equals("Yes")) {        
                    if(!bpvSurgeryAnesthesiaFormInstance.narcOp1Name && !bpvSurgeryAnesthesiaFormInstance.narcOp2Name &&
                            !bpvSurgeryAnesthesiaFormInstance.narcOp3Name && !bpvSurgeryAnesthesiaFormInstance.narcOp4Name && !bpvSurgeryAnesthesiaFormInstance.narcOp5Name) {
                        bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('narcOp1Name', 'Please choose at least one IV Narcotic/Opiate Agents drug and enter its dosage, unit and time')
                    } else {
                        if(bpvSurgeryAnesthesiaFormInstance.narcOp1Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp1Dose', bpvSurgeryAnesthesiaFormInstance.narcOp1Dose, 'The IV Narcotic/Opiate Agents administered drug (Fentanyl) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp1Unit', bpvSurgeryAnesthesiaFormInstance.narcOp1Unit, 'The IV Narcotic/Opiate Agents administered drug (Fentanyl) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp1Time', bpvSurgeryAnesthesiaFormInstance.narcOp1Time, 'The IV Narcotic/Opiate Agents administered drug (Fentanyl) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.narcOp2Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp2Dose', bpvSurgeryAnesthesiaFormInstance.narcOp2Dose, 'The IV Narcotic/Opiate Agents administered drug (Hydromorphone) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp2Unit', bpvSurgeryAnesthesiaFormInstance.narcOp2Unit, 'The IV Narcotic/Opiate Agents administered drug (Hydromorphone) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp2Time', bpvSurgeryAnesthesiaFormInstance.narcOp2Time, 'The IV Narcotic/Opiate Agents administered drug (Hydromorphone) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.narcOp3Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp3Dose', bpvSurgeryAnesthesiaFormInstance.narcOp3Dose, 'The IV Narcotic/Opiate Agents administered drug (Meperidine) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp3Unit', bpvSurgeryAnesthesiaFormInstance.narcOp3Unit, 'The IV Narcotic/Opiate Agents administered drug (Meperidine) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp3Time', bpvSurgeryAnesthesiaFormInstance.narcOp3Time, 'The IV Narcotic/Opiate Agents administered drug (Meperidine) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.narcOp4Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp4Dose', bpvSurgeryAnesthesiaFormInstance.narcOp4Dose, 'The IV Narcotic/Opiate Agents administered drug (Morphine) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp4Unit', bpvSurgeryAnesthesiaFormInstance.narcOp4Unit, 'The IV Narcotic/Opiate Agents administered drug (Morphine) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp4Time', bpvSurgeryAnesthesiaFormInstance.narcOp4Time, 'The IV Narcotic/Opiate Agents administered drug (Morphine) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.narcOp5Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp5Dose', bpvSurgeryAnesthesiaFormInstance.narcOp5Dose, 'The Other Narcotics/Opiates administered drug entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp5Unit', bpvSurgeryAnesthesiaFormInstance.narcOp5Unit, 'The Other Narcotics/Opiates administered drug entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'narcOp5Time', bpvSurgeryAnesthesiaFormInstance.narcOp5Time, 'The Other Narcotics/Opiates administered drug entry is incomplete. Please enter the time.');
                        }
                    }
               }
        }


        if(bpvSurgeryAnesthesiaFormInstance.musRelaxDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('musRelaxDiv', 'Please specify Yes/No to state if IV Muscle Relaxants were administered.')
        } else {
               if(bpvSurgeryAnesthesiaFormInstance.musRelaxDiv.equals("Yes")) {
                    if(!bpvSurgeryAnesthesiaFormInstance.musRelax1Name && !bpvSurgeryAnesthesiaFormInstance.musRelax2Name &&
                        !bpvSurgeryAnesthesiaFormInstance.musRelax3Name && !bpvSurgeryAnesthesiaFormInstance.musRelax4Name) {
                        bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('musRelax1Name', 'Please choose at least one IV Muscle Relaxants drug and enter its dosage, unit and time')
                    } else {
                        if(bpvSurgeryAnesthesiaFormInstance.musRelax1Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax1Dose', bpvSurgeryAnesthesiaFormInstance.musRelax1Dose, 'The IV Muscle Relaxants administered drug (Pancuronium) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax1Unit', bpvSurgeryAnesthesiaFormInstance.musRelax1Unit, 'The IV Muscle Relaxants administered drug (Pancuronium) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax1Time', bpvSurgeryAnesthesiaFormInstance.musRelax1Time, 'The IV Muscle Relaxants administered drug (Pancuronium) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.musRelax2Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax2Dose', bpvSurgeryAnesthesiaFormInstance.musRelax2Dose, 'The IV Muscle Relaxants administered drug (Suxamethonium Chloride) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax2Unit', bpvSurgeryAnesthesiaFormInstance.musRelax2Unit, 'The IV Muscle Relaxants administered drug (Suxamethonium Chloride) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax2Time', bpvSurgeryAnesthesiaFormInstance.musRelax2Time, 'The IV Muscle Relaxants administered drug (Suxamethonium Chloride) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.musRelax3Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax3Dose', bpvSurgeryAnesthesiaFormInstance.musRelax3Dose, 'The IV Muscle Relaxants administered drug (Vercuronium) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax3Unit', bpvSurgeryAnesthesiaFormInstance.musRelax3Unit, 'The IV Muscle Relaxants administered drug (Vercuronium) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax3Time', bpvSurgeryAnesthesiaFormInstance.musRelax3Time, 'The IV Muscle Relaxants administered drug (Vercuronium) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.musRelax4Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax4Dose', bpvSurgeryAnesthesiaFormInstance.musRelax4Dose, 'The Other Muscle Relaxants administered drug entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax4Unit', bpvSurgeryAnesthesiaFormInstance.musRelax4Unit, 'The Other Muscle Relaxants administered drug entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'musRelax4Time', bpvSurgeryAnesthesiaFormInstance.musRelax4Time, 'The Other Muscle Relaxants administered drug entry is incomplete. Please enter the time.');
                        }
                    }
                }
        }

        
        if(bpvSurgeryAnesthesiaFormInstance.inhalAnesDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('inhalAnesDiv', 'Please specify Yes/No to state if Inhalation Anesthesia Agents were administered.')
        } else {
               if(bpvSurgeryAnesthesiaFormInstance.inhalAnesDiv.equals("Yes")) {
                    if(!bpvSurgeryAnesthesiaFormInstance.inhalAnes1Name &&
                        !bpvSurgeryAnesthesiaFormInstance.inhalAnes2Name && !bpvSurgeryAnesthesiaFormInstance.inhalAnes3Name) {
                        bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('inhalAnes1Name', 'Please choose at least one Inhalation Anesthesia Agents drug and enter its dosage, unit and time')
                    } else {
                        if(bpvSurgeryAnesthesiaFormInstance.inhalAnes1Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'inhalAnes1Dose', bpvSurgeryAnesthesiaFormInstance.inhalAnes1Dose, 'The Inhalation Anesthesia Agents administered drug (Isoflurane) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'inhalAnes1Unit', bpvSurgeryAnesthesiaFormInstance.inhalAnes1Unit, 'The Inhalation Anesthesia Agents administered drug (Isoflurane) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'inhalAnes1Time', bpvSurgeryAnesthesiaFormInstance.inhalAnes1Time, 'The Inhalation Anesthesia Agents administered drug (Isoflurane) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.inhalAnes2Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'inhalAnes2Dose', bpvSurgeryAnesthesiaFormInstance.inhalAnes2Dose, 'The Inhalation Anesthesia Agents administered drug (Nitrous Oxide) entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'inhalAnes2Unit', bpvSurgeryAnesthesiaFormInstance.inhalAnes2Unit, 'The Inhalation Anesthesia Agents administered drug (Nitrous Oxide) entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'inhalAnes2Time', bpvSurgeryAnesthesiaFormInstance.inhalAnes2Time, 'The Inhalation Anesthesia Agents administered drug (Nitrous Oxide) entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.inhalAnes3Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'inhalAnes3Dose', bpvSurgeryAnesthesiaFormInstance.inhalAnes3Dose, 'The Other Inhalation Anesthesia Agents administered drug entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'inhalAnes3Unit', bpvSurgeryAnesthesiaFormInstance.inhalAnes3Unit, 'The Other Inhalation Anesthesia Agents administered drug entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'inhalAnes3Time', bpvSurgeryAnesthesiaFormInstance.inhalAnes3Time, 'The Other Inhalation Anesthesia Agents administered drug entry is incomplete. Please enter the time.');
                        }
                    }
               }
        }

        
        if(bpvSurgeryAnesthesiaFormInstance.addtlAnesDiv == null) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('addtlAnesDiv', 'Please specify Yes/No to state if any Additional Anesthesia Agents were administered.')
        } else {
               if(bpvSurgeryAnesthesiaFormInstance.addtlAnesDiv.equals("Yes")) {
                    if(!bpvSurgeryAnesthesiaFormInstance.addtlAnes1Name &&
                        !bpvSurgeryAnesthesiaFormInstance.addtlAnes2Name && !bpvSurgeryAnesthesiaFormInstance.addtlAnes3Name) {
                        bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('addtlAnes1Name', 'Please enter one (or more) Additional Anesthesia Agents drug(s) used but not mentioned above and enter its dosage, unit and time')
                    } else {
                        if(bpvSurgeryAnesthesiaFormInstance.addtlAnes1Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'addtlAnes1Dose', bpvSurgeryAnesthesiaFormInstance.addtlAnes1Dose, 'The Additional Anesthesia Agents Used first entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'addtlAnes1Unit', bpvSurgeryAnesthesiaFormInstance.addtlAnes1Unit, 'The Additional Anesthesia Agents Used first entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'addtlAnes1Time', bpvSurgeryAnesthesiaFormInstance.addtlAnes1Time, 'The Additional Anesthesia Agents Used first entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.addtlAnes2Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'addtlAnes2Dose', bpvSurgeryAnesthesiaFormInstance.addtlAnes2Dose, 'The Additional Anesthesia Agents Used second entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'addtlAnes2Unit', bpvSurgeryAnesthesiaFormInstance.addtlAnes2Unit, 'The Additional Anesthesia Agents Used second entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'addtlAnes2Time', bpvSurgeryAnesthesiaFormInstance.addtlAnes2Time, 'The Additional Anesthesia Agents Used second entry is incomplete. Please enter the time.');
                        }

                        if(bpvSurgeryAnesthesiaFormInstance.addtlAnes3Name) {
                            setError(bpvSurgeryAnesthesiaFormInstance, 'addtlAnes3Dose', bpvSurgeryAnesthesiaFormInstance.addtlAnes3Dose, 'The Additional Anesthesia Agents Used third entry is incomplete. Please enter the dosage.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'addtlAnes3Unit', bpvSurgeryAnesthesiaFormInstance.addtlAnes3Unit, 'The Additional Anesthesia Agents Used third entry is incomplete. Please enter the unit.');
                            setError(bpvSurgeryAnesthesiaFormInstance, 'addtlAnes3Time', bpvSurgeryAnesthesiaFormInstance.addtlAnes3Time, 'The Additional Anesthesia Agents Used third entry is incomplete. Please enter the time.');
                        }
                    }
               }
        }

        if((bpvSurgeryAnesthesiaFormInstance.insulinAdmin.equals(null) || "Undef".equals(bpvSurgeryAnesthesiaFormInstance.insulinAdmin)) && 
                (bpvSurgeryAnesthesiaFormInstance.steroidAdmin.equals(null) || "Undef".equals(bpvSurgeryAnesthesiaFormInstance.steroidAdmin)) && 
                  (bpvSurgeryAnesthesiaFormInstance.antibioAdmin.equals(null) || "Undef".equals(bpvSurgeryAnesthesiaFormInstance.antibioAdmin)) &&
                    (bpvSurgeryAnesthesiaFormInstance.othMedAdmin.equals(null) || "Undef".equals(bpvSurgeryAnesthesiaFormInstance.othMedAdmin))) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('insulinAdmin', 'Please specify Yes/No to state if any Insulin drugs were administered.')
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('steroidAdmin', 'Please specify Yes/No to state if any Steroid drugs were administered.')
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('antibioAdmin', 'Please specify Yes/No to state if any Antibiotic drugs were administered.')
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('othMedAdmin', 'Please specify Yes/No to state if any Other medication were administered.')
        } else {
            if("Yes".equals(bpvSurgeryAnesthesiaFormInstance.insulinAdmin)) {
                if(!bpvSurgeryAnesthesiaFormInstance.insul1Name && !bpvSurgeryAnesthesiaFormInstance.insul2Name) {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('insul1Name', 'Please enter details for at least one of the other (Insulin) medications administered during surgery.')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('insul1Dose', '')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('insul1Unit', '')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('insul1Time', '')
                } else {
                    if(bpvSurgeryAnesthesiaFormInstance.insul1Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'insul1Dose', bpvSurgeryAnesthesiaFormInstance.insul1Dose, 'The other medications administered during surgery first Insulin entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'insul1Unit', bpvSurgeryAnesthesiaFormInstance.insul1Unit, 'The other medications administered during surgery first Insulin entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'insul1Time', bpvSurgeryAnesthesiaFormInstance.insul1Time, 'The other medications administered during surgery first Insulin entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.insul2Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'insul2Dose', bpvSurgeryAnesthesiaFormInstance.insul2Dose, 'The other medications administered during surgery second Insulin entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'insul2Unit', bpvSurgeryAnesthesiaFormInstance.insul2Unit, 'The other medications administered during surgery second Insulin entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'insul2Time', bpvSurgeryAnesthesiaFormInstance.insul2Time, 'The other medications administered during surgery second Insulin entry is incomplete. Please enter the time.');
                    }
                }
            }

            if("Yes".equals(bpvSurgeryAnesthesiaFormInstance.steroidAdmin)) {
                if(!bpvSurgeryAnesthesiaFormInstance.steroid1Name && !bpvSurgeryAnesthesiaFormInstance.steroid2Name) {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('steroid1Name', 'Please enter details for at least one of the other (Steroid) medications administered during surgery.')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('steroid1Dose', '')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('steroid1Unit', '')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('steroid1Time', '')
                } else {
                    if(bpvSurgeryAnesthesiaFormInstance.steroid1Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'steroid1Dose', bpvSurgeryAnesthesiaFormInstance.steroid1Dose, 'The other medications administered during surgery first Steroid entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'steroid1Unit', bpvSurgeryAnesthesiaFormInstance.steroid1Unit, 'The other medications administered during surgery first Steroid entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'steroid1Time', bpvSurgeryAnesthesiaFormInstance.steroid1Time, 'The other medications administered during surgery first Steroid entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.steroid2Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'steroid2Dose', bpvSurgeryAnesthesiaFormInstance.steroid2Dose, 'The other medications administered during surgery second Steroid entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'steroid2Unit', bpvSurgeryAnesthesiaFormInstance.steroid2Unit, 'The other medications administered during surgery second Steroid entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'steroid2Time', bpvSurgeryAnesthesiaFormInstance.steroid2Time, 'The other medications administered during surgery second Steroid entry is incomplete. Please enter the time.');
                    }
                }
            }

            if("Yes".equals(bpvSurgeryAnesthesiaFormInstance.antibioAdmin)) {
                if(!bpvSurgeryAnesthesiaFormInstance.antibio1Name && !bpvSurgeryAnesthesiaFormInstance.antibio2Name) {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('antibio1Name', 'Please enter details for at least one of the other (Antibiotic) medications administered during surgery.')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('antibio1Dose', '')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('antibio1Unit', '')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('antibio1Time', '')
                } else {
                    if(bpvSurgeryAnesthesiaFormInstance.antibio1Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'antibio1Dose', bpvSurgeryAnesthesiaFormInstance.antibio1Dose, 'The other medications administered during surgery first Antibiotic entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'antibio1Unit', bpvSurgeryAnesthesiaFormInstance.antibio1Unit, 'The other medications administered during surgery first Antibiotic entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'antibio1Time', bpvSurgeryAnesthesiaFormInstance.antibio1Time, 'The other medications administered during surgery first Antibiotic entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.antibio2Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'antibio2Dose', bpvSurgeryAnesthesiaFormInstance.antibio2Dose, 'The other medications administered during surgery second Antibiotic entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'antibio2Unit', bpvSurgeryAnesthesiaFormInstance.antibio2Unit, 'The other medications administered during surgery second Antibiotic entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'antibio2Time', bpvSurgeryAnesthesiaFormInstance.antibio2Time, 'The other medications administered during surgery second Antibiotic entry is incomplete. Please enter the time.');
                    }
                }
            }

            if("Yes".equals(bpvSurgeryAnesthesiaFormInstance.othMedAdmin)) {
                if(!bpvSurgeryAnesthesiaFormInstance.med1Name && !bpvSurgeryAnesthesiaFormInstance.med2Name) {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('med1Name', 'Please enter details for at least one of the other (Other Medication) medications administered during surgery.')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('med1Dose', '')
//                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('med1Time', '')
                } else {
                    if(bpvSurgeryAnesthesiaFormInstance.med1Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'med1Dose', bpvSurgeryAnesthesiaFormInstance.med1Dose, 'The other medications administered during surgery first Other Medication entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'med1Unit', bpvSurgeryAnesthesiaFormInstance.med1Unit, 'The other medications administered during surgery first Other Medication entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'med1Time', bpvSurgeryAnesthesiaFormInstance.med1Time, 'The other medications administered during surgery first Other Medication entry is incomplete. Please enter the time.');
                    }

                    if(bpvSurgeryAnesthesiaFormInstance.med2Name) {
                        setError(bpvSurgeryAnesthesiaFormInstance, 'med2Dose', bpvSurgeryAnesthesiaFormInstance.med2Dose, 'The other medications administered during surgery second Other Medication entry is incomplete. Please enter the dosage.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'med2Unit', bpvSurgeryAnesthesiaFormInstance.med2Unit, 'The other medications administered during surgery second Other Medication entry is incomplete. Please enter the unit.');
                        setError(bpvSurgeryAnesthesiaFormInstance, 'med2Time', bpvSurgeryAnesthesiaFormInstance.med2Time, 'The other medications administered during surgery second Other Medication entry is incomplete. Please enter the time.');
                    }
                }
            }
        }

        if(!bpvSurgeryAnesthesiaFormInstance.firstIncisTime) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('firstIncisTime', 'Time of First Incision is required.')
        }

        if(isPrimaryTissueTypeColon || isPrimaryTissueTypeLung || isPrimaryTissueTypeKidney || isPrimaryTissueTypeOvary) {
            if(!bpvSurgeryAnesthesiaFormInstance.surgicalProc) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('surgicalProc', 'Surgical Procedure is required.')
            } else if(bpvSurgeryAnesthesiaFormInstance.surgicalProc == 'Other-specify' && !bpvSurgeryAnesthesiaFormInstance.otherSurgicalProc) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('otherSurgicalProc', 'Please specify other surgical procedure')
            }
        }

        if(isPrimaryTissueTypeKidney || isPrimaryTissueTypeOvary) {
            if(!bpvSurgeryAnesthesiaFormInstance.surgicalMethod) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('surgicalMethod', 'Surgical method is required.')
            } else if(bpvSurgeryAnesthesiaFormInstance.surgicalMethod == 'Other-specify' && !bpvSurgeryAnesthesiaFormInstance.otherSurgicalMethod) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('otherSurgicalMethod', 'Please specify other surgical method')
            }
        }

        
        
        int n = 0
        while(true)
        {
            n++
            
            def side = ''
            def left = ''
            def firstClampTime
            def secondClampTime
            def organResecTime
            if (n == 1)
            {
                side = 'Left'
                firstClampTime = bpvSurgeryAnesthesiaFormInstance.firstClampTimeLeft
                secondClampTime = bpvSurgeryAnesthesiaFormInstance.secondClampTimeLeft
                organResecTime = bpvSurgeryAnesthesiaFormInstance.organResecTimeLeft
            }
            else if (n == 2)
            {
                if(!isPrimaryTissueTypeOvary) break 
                side = 'Right'
                firstClampTime = bpvSurgeryAnesthesiaFormInstance.firstClampTimeRight
                secondClampTime = bpvSurgeryAnesthesiaFormInstance.secondClampTimeRight
                organResecTime = bpvSurgeryAnesthesiaFormInstance.organResecTimeRight
            }
            else break;
                        
            if(isPrimaryTissueTypeOvary) left = '- '+side+' '
            else left = ''
            
            if(!firstClampTime) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('firstClampTime' + side, 'Time of First Clamp '+left+'is required.')
            }
            else
            {
                if(bpvSurgeryAnesthesiaFormInstance.firstIncisTime) {
                    if (bpvSurgeryAnesthesiaFormInstance.firstIncisTime.compareTo(firstClampTime) > 0 )
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('firstClampTime' + side, 'Time of First Clamp '+left+'is less than Time of First Incision.')
                }
                def compareTime = firstClampTime
                def seq = 'First'
                if (secondClampTime) {
                    if (firstClampTime.compareTo(secondClampTime) > 0 )
                    {
                        bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('secondClampTime' + side, 'Time of Second Clamp '+left+'is less than Time of First Clamp.')
                    }
                    else
                    {
                        compareTime = secondClampTime
                        seq = 'Second'
                    }
                }
                
                if(organResecTime) {
                    if (compareTime.compareTo(organResecTime) > 0 )
                        bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('organResecTime' + side, 'Time of Organ Resection '+left+'is less than Time of '+seq+' Clamp.')
                }
            }
            
            if(!organResecTime) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('organResecTime' + side, 'Time of Organ Resection '+left+'is required.')
            }
        }
        
            
        

//        if(!bpvSurgeryAnesthesiaFormInstance.temperature1 && !bpvSurgeryAnesthesiaFormInstance.timeTemp1 && !bpvSurgeryAnesthesiaFormInstance.temperature2 && !bpvSurgeryAnesthesiaFormInstance.timeTemp2) {
//            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('temperature1', 'Please answer question #24 regarding the temperature details')
//            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('timeTemp1', '')
//            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('temperature2', '')
//            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('timeTemp2', '')
//        } else {
            if(!bpvSurgeryAnesthesiaFormInstance.temperature1) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('temperature1', 'Please enter the First temperature recorded for the participant')
            }
            if(!bpvSurgeryAnesthesiaFormInstance.timeTemp1) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('timeTemp1', 'Please enter the Time when the First temperature was recorded for the participant')
            }
            if(!bpvSurgeryAnesthesiaFormInstance.temperature2) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('temperature2', 'Please enter the Second temperature recorded for the participant')
            }
            if(!bpvSurgeryAnesthesiaFormInstance.timeTemp2) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('timeTemp2', 'Please enter the Time when the Second temperature was recorded for the participant')
            }
//        }

        //println 'CCC CO2 Level value=' + bpvSurgeryAnesthesiaFormInstance.co2LevelValue + ', Unit=' + bpvSurgeryAnesthesiaFormInstance.co2LevelUnit.toString() + ', Other Unit=\'' + bpvSurgeryAnesthesiaFormInstance.co2LevelUnitOther + '\''
        if(!bpvSurgeryAnesthesiaFormInstance.co2LevelValue) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('co2LevelValue', 'Carbon Dioxide level (CO2) recorded at time closest to organ resection is required.')
        }
        else
        {
            if(!bpvSurgeryAnesthesiaFormInstance.co2LevelUnit) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('co2LevelUnit', 'The Unit data for Carbon Dioxide level (CO2) is required.')
            }   
            else if(bpvSurgeryAnesthesiaFormInstance.co2LevelUnit.toString() == 'Other, Specify')
            {
                //println 'CCC CO2 trim()=\'' + bpvSurgeryAnesthesiaFormInstance.co2LevelUnitOther?.trim() + '\''
                if (!bpvSurgeryAnesthesiaFormInstance.co2LevelUnitOther || bpvSurgeryAnesthesiaFormInstance.co2LevelUnitOther.trim() == '') 
                {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('co2LevelUnit', 'The Unit data for Carbon Dioxide level (CO2) is not completely specified yet.')
                }
                else 
                {
                    bpvSurgeryAnesthesiaFormInstance.co2LevelUnitOther = bpvSurgeryAnesthesiaFormInstance.co2LevelUnitOther.trim()
                    //println 'CCC CO2 final value=\'' + bpvSurgeryAnesthesiaFormInstance.co2LevelUnitOther + '\''
                }
            }  
        }
        //if(!bpvSurgeryAnesthesiaFormInstance.co2Level) {
        //    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('co2Level', 'Carbon Dioxide level (CO2) recorded at time closest to organ resection is required')
        //}

        if(isPrimaryTissueTypeOvary || isPrimaryTissueTypeColon || isPrimaryTissueTypeLung) {
            if(bpvSurgeryAnesthesiaFormInstance.isAscitesFldColl.equals(null) || bpvSurgeryAnesthesiaFormInstance.isAscitesFldColl.equals("Undef")) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('isAscitesFldColl', 'Please choose Yes/No to state if Ascites fluid was collected.')
            } else {
                if(bpvSurgeryAnesthesiaFormInstance.isAscitesFldColl == 'Yes' && !bpvSurgeryAnesthesiaFormInstance.collAscFluid) {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('collAscFluid', 'Please enter the volume of Ascites fluid collected.')
                }                
            }

        }

        if(isPrimaryTissueTypeOvary) {
            if(bpvSurgeryAnesthesiaFormInstance.isPelvicWashColl.equals(null) || bpvSurgeryAnesthesiaFormInstance.isPelvicWashColl.equals("Undef")) {
                bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('isPelvicWashColl', 'Pelvic Washings collection record is required.')
            } else {
                if(bpvSurgeryAnesthesiaFormInstance.isPelvicWashColl == 'Yes' && !bpvSurgeryAnesthesiaFormInstance.collPelvicWash) {
                    bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('collPelvicWash', 'Please enter the volume of Pelvic Washings collected.')
                }
            }
        }

        if(!bpvSurgeryAnesthesiaFormInstance.specOrLeavingTime) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue('specOrLeavingTime', 'Time when the specimen left Operating Room is required.')
        }
    }

    def setError(bpvSurgeryAnesthesiaFormInstance, name, value, message) {
        if(!value) {
            bpvSurgeryAnesthesiaFormInstance.errors.rejectValue(name, message)
        }
    }
}
