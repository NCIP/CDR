package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.staticmembers.ContainerType
import nci.obbr.cahub.staticmembers.Fixative
import nci.obbr.cahub.staticmembers.AcquisitionType
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.CaseStatus
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.datarecords.ChpBloodRecord
import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.staticmembers.FormMetadata
import nci.obbr.cahub.staticmembers.SOP
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import groovy.time.*
import nci.obbr.cahub.util.ComputeMethods

class BpvBloodFormController {

    def scaffold = BpvBloodForm
    def bpvBloodFormService
    
    def accessPrivilegeService    
    
    static allowedMethods = [update: "POST", delete: "POST"]

    def index = {
        println "list: "
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvBloodFormInstanceList: BpvBloodForm.list(params), bpvBloodFormInstanceTotal: BpvBloodForm.count()]
    }

    def create = {
        def bpvBloodFormInstance = new BpvBloodForm()
        def specimenRecordInstance = new SpecimenRecord()
        def chpBloodRecordInstance = new ChpBloodRecord()

        def caseRecordInstance = CaseRecord.get(params.caseRecord.id)
        if (!caseRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecordInstance.label', default: 'CaseRecord'), params.id])}"
        } else {
            bpvBloodFormInstance.caseRecord = caseRecordInstance
        }

        bpvBloodFormInstance.properties = params
        redirect(action: "save", id: bpvBloodFormInstance.id)
    }

    def edit = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        if(!bpvBloodFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvBloodFormInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvBloodFormInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            
            
            checkError(bpvBloodFormInstance)
            def canSubmit = !bpvBloodFormInstance.errors.hasErrors()
            bpvBloodFormInstance.clearErrors()

            def specimenRecordInstance = new SpecimenRecord()
            def chpBloodRecordInstance = new ChpBloodRecord()
            def warningMap = getWarningMap(bpvBloodFormInstance)
            
            def cdrVer = bpvBloodFormInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            return [bpvBloodFormInstance: bpvBloodFormInstance, specimenRecordInstance: specimenRecordInstance, chpBloodRecordInstance: chpBloodRecordInstance, bpvBloodList: generateBloodList(), bpvContainerList:generateContainerList(bpvBloodFormInstance.bloodFormVersion), canSubmit:canSubmit, warningMap:warningMap,version53:version53]
        }
    }

    def editWithValidation = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        if(!bpvBloodFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvBloodFormInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvBloodFormInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }            
            
            checkError(bpvBloodFormInstance)
            def specimenRecordInstance = new SpecimenRecord()
            def chpBloodRecordInstance = new ChpBloodRecord()
            def warningMap = getWarningMap(bpvBloodFormInstance)
            def cdrVer = bpvBloodFormInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            render(view: "edit", model: [loc: params.loc, bpvBloodFormInstance: bpvBloodFormInstance, specimenRecordInstance: specimenRecordInstance, chpBloodRecordInstance: chpBloodRecordInstance, bpvBloodList: generateBloodList(), bpvContainerList:generateContainerList(bpvBloodFormInstance.bloodFormVersion), canSubmit: !bpvBloodFormInstance.errors.hasErrors(), warningMap: warningMap,version53:version53])
        }
    }

    def save = {
        /*        if (params.bloodMinimum.equalsIgnoreCase("No")) {
        println "In No"
        def bpvBloodFormInstance = new BpvBloodForm()
        bpvBloodFormInstance.discard();
        bpvBloodFormInstance.bloodMinimum = "No"
        } else {
        println "In Yes"*/
        def bpvBloodFormInstance = new BpvBloodForm(params)
        //        }
        //bpvBloodFormInstance = CaseRecord.findByCaseId(caseRecord.id).bpvBloodForm
        def specimenRecordInstance = new SpecimenRecord()
        def chpBloodRecordInstance = new ChpBloodRecord()
        bpvBloodFormInstance.caseRecord.caseStatus = CaseStatus.findByCode("DATA")
        
        bpvBloodFormInstance.bloodFormVersion = 3.0       
        
        // Populate SOP Record
        def formMetadataInstance = FormMetadata.get(params.formMetadata.id)
        def sopInstance
        if (formMetadataInstance?.sops?.size() > 0) {
            sopInstance = SOP.get(formMetadataInstance?.sops?.get(0)?.id)
            bpvBloodFormInstance.collectProcessSOP = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:sopInstance.activeSopVer).save(flush: true)
        } 
        
        //        if (bpvBloodFormInstance.save(flush: true)) {
        if (!bpvBloodFormInstance.hasErrors() && bpvBloodFormInstance.save(flush: true)) {
            //flash.message = "${message(code: 'default.created.message', args: [message(code: 'bpvBloodForm.label', default: 'BpvBloodForm'), bpvBloodFormInstance.id])}"
            //redirect(action: "show", id: bpvBloodFormInstance.id)
            def warningMap = getWarningMap(bpvBloodFormInstance)
            def cdrVer = bpvBloodFormInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            render(view: "edit", model: [bpvBloodFormInstance: bpvBloodFormInstance, specimenRecordInstance: specimenRecordInstance, chpBloodRecordInstance: chpBloodRecordInstance, bpvBloodList: generateBloodList(), bpvContainerList:generateContainerList(bpvBloodFormInstance.bloodFormVersion), warningMap: warningMap,version53:version53])
        }
        else {
            redirect(action: "editWithValidation", id: bpvBloodFormInstance.id)
            //render(view: "create", model: [bpvBloodFormInstance: bpvBloodFormInstance, specimenRecordInstance: specimenRecordInstance, chpBloodRecordInstance: chpBloodRecordInstance, bpvBloodList: generateBloodList(), bpvContainerList:generateContainerList()])
        }
    }

    def show = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        if(!bpvBloodFormInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvBloodFormInstance.caseRecord, session, 'view')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(bpvBloodFormInstance.caseRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }            
            
            def warningMap = getWarningMap(bpvBloodFormInstance)
            def cdrVer = bpvBloodFormInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            
            def canResume = (accessPrivilegeService.checkAccessPrivilege(bpvBloodFormInstance.caseRecord, session, 'edit') == 0)
            [bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap,version53:version53, canResume: canResume]
        }
    }

    def update = {
        if (params.action == "save"){
            def bpvBloodFormInstance 
            if (params.id) {
                // after initial update on create
                bpvBloodFormInstance = BpvBloodForm.get(params.id)
            } else {
                // first time through; record doesn't exist yet
                bpvBloodFormInstance = params
            }

            if (bpvBloodFormInstance) {
                
                def cdrVer = bpvBloodFormInstance.caseRecord.cdrVer
                def  version53 = true
                if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                    version53 = false
                }  
                if (params.version) {
                    def version = params.version.toLong()
                    if (bpvBloodFormInstance.version > version) {

                        bpvBloodFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvBloodFormInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvBloodForm while you were editing")
                        def warningMap = getWarningMap(bpvBloodFormInstance)
                        render(view: "edit", model: [bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap,version53:version53])
                        return
                    }
                }
                bpvBloodFormService.saveForm(params, request)
                
                def caseRecord = CaseRecord.findByCaseId(params.caseId)
                if (caseRecord) {
                } else {
                }
                bpvBloodFormInstance = CaseRecord.findByCaseId(params.caseId).bpvBloodForm
                if (bpvBloodFormInstance) {
                    flash.message = "${message(code: 'default.updated.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
                    if(!params.loc) {
                        redirect(action: "editWithValidation", id: bpvBloodFormInstance.id)
                    } else {
                        redirect(action: "editWithValidation", id: bpvBloodFormInstance.id, params: [loc: params.loc])
                    }
                } else {
                    flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
                    redirect(action: "list")
                }
            }
            else {
                flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
                redirect(action: "list")
            }
            
            /*       } else {
            if (params.action == "save_pl1_aliquot") {
            def specimenRecordInstance = new SpecimenRecord()
            specimenRecordInstance.specimenId = params.specimenId
                
            specimenRecordInstance.containerType = ContainerType.get(params.specimenRecordInstance.containerType.id)
                
            def caseRecordInstance = CaseRecord.findByCaseId(params.caseId)
            def bpvBloodFormInstance = CaseRecord.findByCaseId(params.caseId).bpvBloodForm
            def parentSpecRecInstance = new SpecimenRecord()
            parentSpecRecInstance = SpecimenRecord.get(params.parPlBarCd)
            //parentSpecRecInstance.specimenId = params.parBarCd
            def chpBloodRecordInstance = new ChpBloodRecord()
            def tissueType = new AcquisitionType()
            def fixative = new Fixative()
            // def containerType = new ContainerType()
            switch (params.specimenRecordInstance.tissueType.code) {
            case "BLOODW":
            tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
            fixative   = Fixative.findByCode("CRYOV")
            //containerType = ContainerType.findByCode("CRYOV")
            break
            case "BLOODPLAS": 
            tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
            fixative   = Fixative.findByCode("EDTAT")
            // containerType = ContainerType.findByCode("LAVEDTA")
            break
            case "BLOODSRM": 
            tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
            fixative   = Fixative.findByCode("SST")
            // containerType = ContainerType.findByCode("SST")
            break
            default:
            tissueType = "ERROR"
                        
            }
            specimenRecordInstance.tissueType = tissueType
            specimenRecordInstance.provisionalTissueType = tissueType
            specimenRecordInstance.fixative = fixative
            specimenRecordInstance.parentSpecimen=parentSpecRecInstance
                
            specimenRecordInstance.inQuarantine = true
            specimenRecordInstance.wasConsumed = true  // for Cryovial blood tubes.  Have to see how this one plays out...
            // specimenRecordInstance.containerType = containerType
            chpBloodRecordInstance.volume = params.volume.toFloat()
            specimenRecordInstance.chpBloodRecord = chpBloodRecordInstance
            chpBloodRecordInstance.specimenRecord = specimenRecordInstance
            caseRecordInstance.addToSpecimens(specimenRecordInstance)
                
            specimenRecordInstance.save(failOnError:true)
            chpBloodRecordInstance.save(failOnError:true)
            caseRecordInstance.save(failOnError:true)
                
            if (bpvBloodFormInstance) {
            flash.message = "${message(code: 'default.updated.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName, bpvBloodFormInstance.id])}"
            //redirect(action: "edit", id: bpvBloodFormInstance.id)
            redirect(action: "editWithValidation", id: bpvBloodFormInstance.id)
            } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName, params.caseId])}"
            redirect(action: "list")
            }
                
               
            } else if (params.action == "save_srm_aliquot") {
            def specimenRecordInstance = new SpecimenRecord()
            specimenRecordInstance.specimenId = params.specimenId
                
            specimenRecordInstance.containerType = ContainerType.get(params.specimenRecordInstance.containerType.id)
                
            def caseRecordInstance = CaseRecord.findByCaseId(params.caseId)
            def bpvBloodFormInstance = CaseRecord.findByCaseId(params.caseId).bpvBloodForm
            def parentSpecRecInstance = new SpecimenRecord()
            parentSpecRecInstance = SpecimenRecord.get(params.parSrmBarCd)
            //parentSpecRecInstance.specimenId = params.parBarCd
            def chpBloodRecordInstance = new ChpBloodRecord()
            def tissueType = new AcquisitionType()
            def fixative = new Fixative()
            // def containerType = new ContainerType()
            switch (params.specimenRecordInstance.tissueType.code) {
            case "BLOODW":
            tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
            fixative   = Fixative.findByCode("CRYOV")
            //containerType = ContainerType.findByCode("CRYOV")
            break
            case "BLOODPLAS": 
            tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
            fixative   = Fixative.findByCode("EDTAT")
            // containerType = ContainerType.findByCode("LAVEDTA")
            break
            case "BLOODSRM": 
            tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
            fixative   = Fixative.findByCode("SST")
            // containerType = ContainerType.findByCode("SST")
            break
            default:
            tissueType = "ERROR"
                        
            }
            specimenRecordInstance.tissueType = tissueType
            specimenRecordInstance.provisionalTissueType = tissueType
            specimenRecordInstance.fixative = fixative
            specimenRecordInstance.parentSpecimen=parentSpecRecInstance

            specimenRecordInstance.inQuarantine = true
            specimenRecordInstance.wasConsumed = true  // for Cryovial blood tubes.  Have to see how this one plays out...
            // specimenRecordInstance.containerType = containerType
            chpBloodRecordInstance.volume = params.volume.toFloat()
            specimenRecordInstance.chpBloodRecord = chpBloodRecordInstance
            chpBloodRecordInstance.specimenRecord = specimenRecordInstance
            caseRecordInstance.addToSpecimens(specimenRecordInstance)
            specimenRecordInstance.save(failOnError:true)
            chpBloodRecordInstance.save(failOnError:true)
            caseRecordInstance.save(failOnError:true)
            if (bpvBloodFormInstance) {
            flash.message = "${message(code: 'default.updated.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName, bpvBloodFormInstance.id])}"
            redirect(action: "edit", id: bpvBloodFormInstance.id)
            } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName, params.caseId])}"
            redirect(action: "list")
            }
                
            }*/
        } 
    }
    

    def updateParent = {
        //        if (params.action == "save"){
        def bpvBloodFormInstance 
        if (params.id) {
            // after initial update on create
            bpvBloodFormInstance = BpvBloodForm.get(params.id)

        } else {
            // first time through; record doesn't exist yet

            bpvBloodFormInstance = params
        }

        if (bpvBloodFormInstance) {
            def cdrVer = bpvBloodFormInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            bpvBloodFormInstance = bpvBloodFormService.saveParent (params, bpvBloodFormInstance, request)
            def caseRecord = CaseRecord.findByCaseId(params.caseId)
            //bpvBloodFormInstance = CaseRecord.findByCaseId(params.caseId).bpvBloodForm
            if (bpvBloodFormInstance) {
                def warningMap = getWarningMap(bpvBloodFormInstance)
                render(template: "parentTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap,version53:version53])
            } else {
                flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName, params.caseId])}"
                redirect(action: "list")
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName, params.id])}"
            redirect(action: "list")
        }            
    }
    
    
    def editParent = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        def specimenRecordInstance = SpecimenRecord.get((params.specParentId).trim())
        
        def tissueType = new AcquisitionType()
        def fixative = new Fixative()
        def containerType = new ContainerType()
        specimenRecordInstance.specimenId = (params.specimenId).trim()
        specimenRecordInstance.chpBloodRecord.volume = params.volume.toFloat()
        
        def cdrVer = bpvBloodFormInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            
        if (params.drawtime!="") {
            specimenRecordInstance.bloodTimeDraw = params.drawtime
        } else {
            specimenRecordInstance.bloodTimeDraw = ""
        }        
        if (!(specimenRecordInstance.validate())) {
            bpvBloodFormInstance.errors = specimenRecordInstance.errors
            def warningMap = getWarningMap(bpvBloodFormInstance)
            render(template: "parentTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap,version53:version53])
            return
        }
        if (!specimenRecordInstance.hasErrors() && specimenRecordInstance.save(flush: true)) {
            def warningMap = getWarningMap(bpvBloodFormInstance)
            render(template: "parentTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap,version53:version53])
            return
        }
        else {
            def warningMap = getWarningMap(bpvBloodFormInstance)
            render(template: "parentTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap,version53:version53])
        }
    }

    def editPlasma = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        def specimenRecordInstance = SpecimenRecord.get((params.specPlasmaId).trim())
        specimenRecordInstance.chpBloodRecord.volume = params.volume.toFloat()
        if (bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) {
            if (params.bloodFrozen!=null && params.bloodFrozen!="Select Date") {
                specimenRecordInstance.chpBloodRecord.bloodFrozen = new Date(params.bloodFrozen)
            }
            if (params.scannedId?.trim()!="") {
                if (params.bloodStorage!=null && params.bloodStorage!="Select Date") {
                    specimenRecordInstance.chpBloodRecord.bloodStorage = new Date(params.bloodStorage)
                }
                if (params.freezerType!=null) {
                    specimenRecordInstance.chpBloodRecord.freezerType = params.freezerType
                }
            }
        }
        def plasmaConicalTube
        if (params.coniTbCdEd!=null) {
            plasmaConicalTube = params.coniTbCdEd
        }        
        if (!specimenRecordInstance.hasErrors() && specimenRecordInstance.save(flush: true)) {
            render(template: "plasmaTable", model: [updatediv:true, plasmaConicalTube:plasmaConicalTube, bpvBloodFormInstance: bpvBloodFormInstance])
            return
        }        
      
    }
   
    def editPlasmaCT = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        def specimenRecordInstance = SpecimenRecord.get((params.specPlasmaCTId).trim())
        specimenRecordInstance.chpBloodRecord.volume = params.volume.toFloat()
    }
    
    
    def editSerum = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        def specimenRecordInstance = SpecimenRecord.get((params.specSerumId).trim())
        specimenRecordInstance.chpBloodRecord.volume = params.volume.toFloat()
        if (!specimenRecordInstance.hasErrors() && specimenRecordInstance.save(flush: true)) {
            render(template: "serumTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance])
            return
        }        
    }
    

    def updatePlasmaCh = {
        //        if (params.action == "save"){
        def bpvBloodFormInstance = bpvBloodFormService.savePlasmaCh (params, request)
        def plasmaConicalTube
        if (params.coniTbCd!=null) {
            plasmaConicalTube = params.coniTbCd
        }
        if (bpvBloodFormInstance) {
            render(template: "plasmaTable", model: [updatediv:true, plasmaConicalTube:plasmaConicalTube, bpvBloodFormInstance: bpvBloodFormInstance])
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName, params.caseId])}"
            redirect(action: "list")
        }
    }
    

    def updatePlasmaConTb = {
        def bpvBloodFormInstance = bpvBloodFormService.savePlasmaConTb (params, request)
        if (bpvBloodFormInstance) {
            def warningMap = getWarningMap(bpvBloodFormInstance)
            render(template: "parentTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap])
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName, params.caseId])}"
            redirect(action: "list")
        }
    }
    
                                                                                

    def updateSerumCh = {
                
        def bpvBloodFormInstance = bpvBloodFormService.saveSerumCh (params, request)
        if (bpvBloodFormInstance) {
            render(template: "serumTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance])
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName, params.caseId])}"
            redirect(action: "list")
        }            
    }
    

    def updateWholeCellPelletCh = {
                
        def bpvBloodFormInstance = bpvBloodFormService.saveWcpCh (params, request)
        if (bpvBloodFormInstance) {
            render(template: "wcpTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance])
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName, params.caseId])}"
            redirect(action: "list")
        }            
    }

    def editWholeCellPellet = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        def specimenRecordInstance = SpecimenRecord.get((params.specWCId).trim())
        specimenRecordInstance.chpBloodRecord.volume = params.volume.toFloat()
        if (!specimenRecordInstance.hasErrors() && specimenRecordInstance.save(flush: true)) {
            render(template: "wcpTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance])
            return
        }        
    }    
    

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        if(bpvBloodFormInstance) {
            try {
                bpvBloodFormInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
                redirect(action: "list")
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
                redirect(action: "show", id: params.id)
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    def submit = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        if(bpvBloodFormInstance) {
            
             
            def cdrVer = bpvBloodFormInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            if(params.version) {
                def version = params.version.toLong()
                if(bpvBloodFormInstance.version > version) {
                    bpvBloodFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case'] as Object[], "Another user has updated this BpvBloodForm while you were editing")
                    def warningMap = getWarningMap(bpvBloodFormInstance)
                    render(view: "edit", model: [bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap,version53:version53])
                    return
                }
            }

            bpvBloodFormInstance.properties = params
            checkError(bpvBloodFormInstance)
           

            if(!bpvBloodFormInstance.hasErrors() && bpvBloodFormInstance.save(flush: true)) {
                bpvBloodFormInstance.dateSubmitted = new Date()
                bpvBloodFormInstance.submittedBy = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()

                flash.message = "${message(code: 'default.submitted.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
                redirect(controller: "caseRecord", action: "display", id: bpvBloodFormInstance.caseRecord.id)
            } else {
                bpvBloodFormInstance.discard()
                def specimenRecordInstance = new SpecimenRecord()
                def chpBloodRecordInstance = new ChpBloodRecord()
                def warningMap = getWarningMap(bpvBloodFormInstance)
                render(view: "edit", model: [bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvBloodFormInstance?.formMetadata?.cdrFormName + ' for Case', bpvBloodFormInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    def resumeEditing = {
        def bpvBloodFormInstance = BpvBloodForm.get(params.id)
        bpvBloodFormInstance.dateSubmitted = null
        bpvBloodFormInstance.submittedBy = null

        if(bpvBloodFormInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvBloodFormInstance.id)
        } else {
            def warningMap = getWarningMap(bpvBloodFormInstance)
            def cdrVer = bpvBloodFormInstance.caseRecord.cdrVer
            def  version53 = true
            if(ComputeMethods.compareCDRVersion(cdrVer, '5.3') < 0){
                version53 = false
            }  
            render(view: "show", model: [bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap,version53:version53])
        }
    }

    def getParentSpecimens = {
        def specimensList = CaseRecord.get(params.id).specimens
        def psList = ""
        //           psList = "<ul>"
        specimensList.each() {
            if(it.parentSpecimen == null){
                psList = psList + "<option value='"+it.id+"'>"+it.specimenId+"</option>"
                //psList = psList + "<li id='"+it.id+"'>"+it.specimenId+"</li>"
            }
        }
        //           psList = psList + "</ul>"
        def payload = ['psList': psList]
        render "${params.callback.encodeAsURL()}([${payload as JSON}])"
        //render text: psList as JSON, contentType: 'text/plain'
    } 
        
    def getPlasmaParentSpecimens = {
        def specimensList = CaseRecord.get(params.id).specimens
        def psList = ""
        //           psList = "<ul>"
        specimensList.each() {
            if(it.parentSpecimen == null && it.tissueType == AcquisitionType.findByCode("BLOODPLAS")) {
                //psList = psList + "<option value='"+it.id+"'>"+it.specimenId+"</option>"
                psList = it.specimenId
            }
        }
        //           psList = psList + "</ul>"
        def payload = ['psList': psList]
        render "${params.callback.encodeAsURL()}([${payload as JSON}])"
        //render text: psList as JSON, contentType: 'text/plain'
    } 
        
    def getSerumParentSpecimens = {
        def specimensList = CaseRecord.get(params.id).specimens
        def psList = ""
        //           psList = "<ul>"
        specimensList.each() {
            if(it.parentSpecimen == null && it.tissueType == AcquisitionType.findByCode("BLOODSRM")) {
                psList = it.specimenId
            }
        }
        //           psList = psList + "</ul>"
        def payload = ['psList': psList]
        render "${params.callback.encodeAsURL()}([${payload as JSON}])"
        //render text: psList as JSON, contentType: 'text/plain'
    } 
    
    def getDnaParentSpecimens = {
        def specimensList = CaseRecord.get(params.id).specimens
        def psList = ""
        //           psList = "<ul>"
        specimensList.each() {
            if(it.parentSpecimen == null && it.containerType == ContainerType.findByCode("DNAPAX")) {
                psList = it.specimenId
            }
        }
        //           psList = psList + "</ul>"
        def payload = ['psList': psList]
        render "${params.callback.encodeAsURL()}([${payload as JSON}])"
        //render text: psList as JSON, contentType: 'text/plain'
    } 

    def getRnaParentSpecimens = {
        def specimensList = CaseRecord.get(params.id).specimens
        def psList = ""
        //           psList = "<ul>"
        specimensList.each() {
            if(it.parentSpecimen == null && it.containerType == ContainerType.findByCode("RNAPAX")) {
                psList = it.specimenId
            }
        }
        //           psList = psList + "</ul>"
        def payload = ['psList': psList]
        render "${params.callback.encodeAsURL()}([${payload as JSON}])"
        //render text: psList as JSON, contentType: 'text/plain'
    } 

    
        
    def generateBloodList() {
        def bpvBloodList = AcquisitionType.withCriteria {
            or {
                eq "code", "BLOODPLAS"
                eq "code", "BLOODSRM"
                eq "code", "BLOODRNA"
                eq "code", "BLOODDNA"
                eq "code", "BLOODCP"
            }
            //like ('code',"BLOOD%")
        }
        return bpvBloodList
    }

    def generateContainerList(bfversion) {
        def bpvContainerList = ContainerType.withCriteria {
            or {
                eq "code", "RNAPAX"
                eq "code", "DNAPAX"
                eq "code", "LAVEDTA"
                eq "code", "SST"
            }
            //like ('code',"BLOOD%")
        }
        return bfversion == 2?bpvContainerList.sort{it.name}:bpvContainerList
    }
    
    def deleteSpecimen = {

        def specimenRecordInstance = SpecimenRecord.get(params.id)
        def countIfParent = SpecimenRecord.executeQuery("select count(*) from SpecimenRecord c where c.parentSpecimen="+specimenRecordInstance.id)
        def bpvBloodFormInstance = specimenRecordInstance.caseRecord.bpvBloodForm
        def plasmaConicalTube = specimenRecordInstance.parentSpecimen?.specimenId
        if (params.tube.equalsIgnoreCase("Plasma")) {
            def parentSpecimenId
            if (specimenRecordInstance.containerType == ContainerType.findByCode("CONICT")) {
                parentSpecimenId = specimenRecordInstance.parentSpecimen?.specimenId
            } else {
                parentSpecimenId = specimenRecordInstance.parentSpecimen.parentSpecimen.specimenId
            }
            if (!bpvBloodFormInstance.plasmaParBarcode.equals(parentSpecimenId) ){
                    bpvBloodFormInstance.caseRecord.specimens.each() {
                        if(it.containerType == ContainerType.findByCode("CONICT") && it.fixative == Fixative.findByCode("FRESH") && it.parentSpecimen != null && it.tissueType == AcquisitionType.findByCode("BLOODPLAS")
                            && it.parentSpecimen.specimenId.equals(bpvBloodFormInstance.plasmaParBarcode)) {
                            plasmaConicalTube = it.specimenId
                        }
                    }                
                }
        }
        def caseRecordInstance = specimenRecordInstance.caseRecord
        if (countIfParent.get(0) > 0) {
            render '<img src="/cahubdataservices/images/removeDisabled.png" onmouseover="tooltip.show(\'To remove this tube, you must delete its child tubes\');" onload="tooltip.show(\'To remove this tube, you must delete its child tubes\');" onmouseout="tooltip.hide();">'
            response.status = 409 // To simulate an error
        } else {
            caseRecordInstance.removeFromSpecimens(specimenRecordInstance).save()
            specimenRecordInstance.delete(flush: true)
            if (params.tube.equalsIgnoreCase("Parent")) {
                def warningMap = getWarningMap(bpvBloodFormInstance)
                render(template: "parentTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap])
            } else if (params.tube.equalsIgnoreCase("Plasma")) {
                render(template: "plasmaTable", model: [updatediv:true, plasmaConicalTube:plasmaConicalTube, bpvBloodFormInstance: bpvBloodFormInstance])
            } else if (params.tube.equalsIgnoreCase("Serum")) {
                render(template: "serumTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance])
            } else if (params.tube.equalsIgnoreCase("Wcp")) {
                render(template: "wcpTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance])
            } else if (params.tube.equalsIgnoreCase("PlasCT")) {
                def warningMap = getWarningMap(bpvBloodFormInstance)
                render(template: "parentTable", model: [updatediv:true, bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap])
            }
        }
    }    
    
    def validateSpecimen = {
        def result = 'true'
        
        if (SpecimenRecord.findBySpecimenId(params.specimenId) != null) {
            result = 'false'
        }
        
        render result
    }
    
    def checkError(bpvBloodFormInstance) {
         def rnaTube = "No"
        def dnaTube = "No"
        def plasmaPar = "No"
        def plasmaChildren = "No"
        def plasmaConicalTube = "No"        
        def serumPar = "No"
        def serumChildren = "No"
        def bloodDraw2 = "No"
        def plasmaSpecimen
        def serumSpecimen
        def wcpSpecimen = 0
        def plasmaMinusEighty = 0
        def plasmaLN = 0

        if (bpvBloodFormInstance.bloodMinimum.equals(null)) {
            bpvBloodFormInstance.errors.rejectValue('bloodMinimum', 'bpvErr2')
        } else {
            if (bpvBloodFormInstance.bloodMinimum.equals("Yes")) {
                if (bpvBloodFormInstance.bloodDrawType.equals(null)) {
                    bpvBloodFormInstance.errors.rejectValue('bloodDrawType', 'bpvErr3')
                } else if (bpvBloodFormInstance.bloodDrawType.equals('Other (specify)') && bpvBloodFormInstance.bloodDrawTypeOs.equals(null)) {
                    bpvBloodFormInstance.errors.rejectValue('bloodDrawTypeOs', 'bpvErr4')
                }
                if (bpvBloodFormInstance.dateTimeBloodDraw.equals(null)) {
                    bpvBloodFormInstance.errors.rejectValue('dateTimeBloodDraw', 'bpvErr5')
                } else {
                    if (bpvBloodFormInstance.dateTimeBloodDraw > new Date()) {
                        bpvBloodFormInstance.errors.rejectValue('dateTimeBloodDraw', 'bpvErr33')
                    }
                }

                if (bpvBloodFormInstance.bloodDrawNurse.equals(null)) {
                    bpvBloodFormInstance.errors.rejectValue('bloodDrawNurse', 'bpvErr6')
                } else if (bpvBloodFormInstance.bloodDrawNurse.toString().equals('Other Specify') && bpvBloodFormInstance.bloodDrawNurseOs.equals(null)) {
                    bpvBloodFormInstance.errors.rejectValue('bloodDrawNurseOs', 'bpvErr7')            
                }
                if (bpvBloodFormInstance.bloodDrawNurse.toString() != 'Unknown' && bpvBloodFormInstance.bloodDrawNurseName.equals(null)) {
                    bpvBloodFormInstance.errors.rejectValue('bloodDrawNurseName', 'bpvErr8')
                }
                    
                if (bpvBloodFormInstance.bloodSource.equals('Other (specify)') && bpvBloodFormInstance.bloodSourceOs.equals(null)) {
                    bpvBloodFormInstance.errors.rejectValue('bloodSourceOs', 'bpvErr12')
                }        
                if (bpvBloodFormInstance.dateTimeBloodReceived.equals(null)) {
                    bpvBloodFormInstance.errors.rejectValue('dateTimeBloodReceived', 'bpvErr13')
                } else {
                    if (bpvBloodFormInstance.dateTimeBloodReceived < bpvBloodFormInstance.dateTimeBloodDraw && bpvBloodFormInstance.dateTimeBloodDraw < new Date()) {
                        bpvBloodFormInstance.errors.rejectValue('dateTimeBloodReceived', 'bpvErr34')
                    }
                    //                    if (!bpvBloodFormInstance.dateTimeBloodDraw2.equals(null)) {                    
                    //                        if (bpvBloodFormInstance.dateTimeBloodReceived < bpvBloodFormInstance.dateTimeBloodDraw2 && bpvBloodFormInstance.dateTimeBloodDraw2 < new Date()) {
                    //                            bpvBloodFormInstance.errors.rejectValue('dateTimeBloodReceived', 'bpvErr61')
                    //                        }
                    //                    }                    
                    if (bpvBloodFormInstance.dateTimeBloodReceived > new Date()) {
                        bpvBloodFormInstance.errors.rejectValue('dateTimeBloodReceived', 'bpvErr35')
                    }                    
                }
                
                if ((bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) && bpvBloodFormInstance.bloodReceiptTech.equals(null)) {
                    bpvBloodFormInstance.errors.rejectValue('bloodReceiptTech', 'bpvErr84')
                }

                def dateTimeBloodRecd
                if (bpvBloodFormInstance.caseRecord.specimens!=null) {
                    //                    if (bpvBloodFormInstance.caseRecord.specimens.size() == 0) {
                    //                        bpvBloodFormInstance.errors.rejectValue('', 'bpvErr9')
                    //                    } else {
                    bpvBloodFormInstance.caseRecord.specimens.each() {
                        //println "Container Type::parent::specimenId::Fixative::Tissue--"+it.containerType+"::"+it.parentSpecimen+"::"+it.specimenId+"::"+it.fixative+"::"+it.tissueType
                        if(it.containerType == ContainerType.findByCode("RNAPAX")) {
                            rnaTube = "Yes"
                        } else if (it.containerType == ContainerType.findByCode("DNAPAX")) {
                            dnaTube = "Yes"
                        } else if(it.containerType == ContainerType.findByCode("LAVEDTA") && it.fixative == Fixative.findByCode("EDTA") && it.parentSpecimen == null && it.tissueType == AcquisitionType.findByCode("BLOODPLAS")) {
                            plasmaPar = "Yes"
                            plasmaSpecimen = it
                            if (bpvBloodFormInstance.bloodFormVersion != 2) {
                                bpvBloodFormInstance.plasmaParBarcode = it.specimenId
                            }
                        } else if(it.containerType == ContainerType.findByCode("CRYOV") && it.parentSpecimen != null && it.tissueType == AcquisitionType.findByCode("BLOODPLAS")) {
                            if (bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) {
                                if(bpvBloodFormInstance.plasmaParBarcode!=null && it.parentSpecimen.parentSpecimen.specimenId.equals(bpvBloodFormInstance.plasmaParBarcode)) {
                                    //plasmaChildren = "Yes"
                                    //plasmaChildCount++                                    
                                    if (it.chpBloodRecord.freezerType!=null && it.chpBloodRecord.freezerType.equals("-80°C Freezer")) {
                                        plasmaMinusEighty++    
                                    } else if (it.chpBloodRecord.freezerType!=null && it.chpBloodRecord.freezerType.equals("LN Freezer")) {
                                        plasmaLN++
                                    }
                                }
                            } else {                            
                                plasmaChildren = "Yes"
                            }
                        } else if(it.containerType == ContainerType.findByCode("CONICT") && it.parentSpecimen != null && it.tissueType == AcquisitionType.findByCode("BLOODPLAS")) {
                            if (bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) {
                                if(bpvBloodFormInstance.plasmaParBarcode!=null && it.parentSpecimen.specimenId.equals(bpvBloodFormInstance.plasmaParBarcode)) {
                                    plasmaConicalTube = "Yes"
                                }
                            } else {
                                plasmaConicalTube = "Yes"
                            }
                        } else if(it.containerType == ContainerType.findByCode("SST") && it.parentSpecimen == null && it.tissueType == AcquisitionType.findByCode("BLOODSRM")) {
                            serumPar = "Yes"
                            serumSpecimen = it
                        } else if(it.containerType == ContainerType.findByCode("CRYOV") && it.parentSpecimen != null && it.tissueType == AcquisitionType.findByCode("BLOODSRM")) {
                            serumChildren = "Yes"
                        } else if(it.containerType == ContainerType.findByCode("CRYOV") && it.parentSpecimen != null && it.tissueType == AcquisitionType.findByCode("BLOODCP") && 
                            it.parentSpecimen.specimenId.equals(bpvBloodFormInstance.plasmaParBarcode)) {
                            wcpSpecimen++
                        }
                        
                        if(it.bloodTimeDraw!=null) {
                            if (it.bloodTimeDraw.equals("D2")) {
                                bloodDraw2 = "Yes"
                            }
                        }
                    }
                    
                    if (bloodDraw2.equals("Yes")) {
                        if (bpvBloodFormInstance.dateTimeBloodDraw2.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('dateTimeBloodDraw2', 'bpvErr66')
                        } else {
                            if (bpvBloodFormInstance.dateTimeBloodDraw2 > new Date()) {
                                bpvBloodFormInstance.errors.rejectValue('dateTimeBloodDraw2', 'bpvErr60')
                            } 
                            if (bpvBloodFormInstance.dateTimeBloodDraw > bpvBloodFormInstance.dateTimeBloodDraw2) {
                                bpvBloodFormInstance.errors.rejectValue('dateTimeBloodDraw2', 'bpvErr62')
                            }                    
                        }
                        
                        if (bpvBloodFormInstance.bloodDrawNurse2.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('bloodDrawNurse2', 'bpvErr63')
                        } else if (bpvBloodFormInstance.bloodDrawNurse2.toString().equals('Other Specify') && bpvBloodFormInstance.bloodDrawNurseOs2.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('bloodDrawNurseOs2', 'bpvErr64')
                        }
                        if (bpvBloodFormInstance.bloodDrawNurse2.toString() != 'Unknown' && bpvBloodFormInstance.bloodDrawNurseName2.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('bloodDrawNurseName2', 'bpvErr65')
                        }
                        if (bpvBloodFormInstance.dateTimeBloodReceived2.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('dateTimeBloodReceived2', 'bpvErr69')
                        } else {
                            if (bpvBloodFormInstance.dateTimeBloodReceived2 < bpvBloodFormInstance.dateTimeBloodDraw2) {
                                bpvBloodFormInstance.errors.rejectValue('dateTimeBloodReceived2', 'bpvErr70')
                            }

                            if (bpvBloodFormInstance.dateTimeBloodReceived2 > new Date()) {
                                bpvBloodFormInstance.errors.rejectValue('dateTimeBloodReceived2', 'bpvErr71')
                            }                    
                        }   
                        
                        if ((bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) && bpvBloodFormInstance.bloodReceiptTech2.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('bloodReceiptTech2', 'bpvErr85')
                        }
                    }
                    

                    if (bpvBloodFormInstance.bloodFormVersion != 2 && bpvBloodFormInstance.bloodFormVersion != 3) {
                        if (rnaTube.equals("No")) {
                            bpvBloodFormInstance.errors.rejectValue('rnaParent', 'bpvErr10')
                        }
                    } 
                         if (rnaTube.equals("Yes")) {
                        def rnaSpecimen = SpecimenRecord.findBySpecimenId(bpvBloodFormInstance.rnaParBarCode)

                        dateTimeBloodRecd = ""
                        if (rnaSpecimen?.containerType == ContainerType.findByCode("RNAPAX") && rnaSpecimen?.bloodTimeDraw.equals("D2")) {
                            dateTimeBloodRecd = bpvBloodFormInstance.dateTimeBloodReceived2
                        } else {
                            dateTimeBloodRecd = bpvBloodFormInstance.dateTimeBloodReceived
                        }
                        if (bpvBloodFormInstance.rnaParBarCode.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('rnaParBarCode', 'bpvErr68')
                        }                                
                        if (bpvBloodFormInstance.rnaPaxFrozen.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('rnaPaxFrozen', 'bpvErr19')
                        } else {

                            if (dateTimeBloodRecd > bpvBloodFormInstance.rnaPaxFrozen) {
                                bpvBloodFormInstance.errors.rejectValue('rnaPaxFrozen', 'bpvErr36')
                            }
                            if (bpvBloodFormInstance.rnaPaxFrozen > new Date()) {
                                bpvBloodFormInstance.errors.rejectValue('rnaPaxFrozen', 'bpvErr37')
                            }                                    
                        }
                        if (bpvBloodFormInstance.rnaPaxStorage.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('rnaPaxStorage', 'bpvErr20')
                        } else {
                            if (dateTimeBloodRecd > bpvBloodFormInstance.rnaPaxStorage) {
                                bpvBloodFormInstance.errors.rejectValue('rnaPaxStorage', 'bpvErr38')
                            }                    
                            if (bpvBloodFormInstance.rnaPaxStorage > new Date()) {
                                bpvBloodFormInstance.errors.rejectValue('rnaPaxStorage', 'bpvErr39')
                            }
                            //                                    if (bpvBloodFormInstance.rnaPaxStorage < bpvBloodFormInstance.rnaPaxFrozen) {
                            //                                        bpvBloodFormInstance.errors.rejectValue('rnaPaxStorage', 'bpvErr31')
                            //                                    }
                        }
                        if (bpvBloodFormInstance.rnaPaxProcTech.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('rnaPaxProcTech', 'bpvErr21')
                        }
                        if (bpvBloodFormInstance.rnaPaxProcSopDev.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('rnaPaxProcSopDev', 'bpvErr22')
                        } else {
                            if (bpvBloodFormInstance.rnaPaxProcSopDev.equals('No') && bpvBloodFormInstance.rnaPaxProcComments.equals(null)) {
                                bpvBloodFormInstance.errors.rejectValue('rnaPaxProcComments', 'bpvErr23')
                            }
                        }
                    }                                    
                    
                    if (bpvBloodFormInstance.bloodFormVersion != 2 && bpvBloodFormInstance.bloodFormVersion != 3) {
                        if (dnaTube.equals("No")) {
                            bpvBloodFormInstance.errors.rejectValue('dnaParent', 'bpvErr11')
                        } 
                    }
                        if (dnaTube.equals("Yes")) {
                        def dnaSpecimen = SpecimenRecord.findBySpecimenId(bpvBloodFormInstance.dnaParBarCode)

                        dateTimeBloodRecd = ""
                        if (dnaSpecimen?.containerType == ContainerType.findByCode("DNAPAX") && dnaSpecimen?.bloodTimeDraw.equals("D2")) {
                            dateTimeBloodRecd = bpvBloodFormInstance.dateTimeBloodReceived2
                        } else {
                            dateTimeBloodRecd = bpvBloodFormInstance.dateTimeBloodReceived
                        }                        
                        if (bpvBloodFormInstance.dnaParBarCode.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('dnaParBarCode', 'bpvErr67')
                        }
                        if (bpvBloodFormInstance.dnaPaxFrozen.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('dnaPaxFrozen', 'bpvErr14')
                        } else {
                            if (dateTimeBloodRecd > bpvBloodFormInstance.dnaPaxFrozen) {
                                bpvBloodFormInstance.errors.rejectValue('dnaPaxFrozen', 'bpvErr40')
                            }                    
                            if (bpvBloodFormInstance.dnaPaxFrozen > new Date()) {
                                bpvBloodFormInstance.errors.rejectValue('dnaPaxFrozen', 'bpvErr41')
                            }                                    
                        }
                        if (bpvBloodFormInstance.dnaPaxStorage.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('dnaPaxStorage', 'bpvErr15')
                        } else {
                            if (dateTimeBloodRecd > bpvBloodFormInstance.dnaPaxStorage) {
                                bpvBloodFormInstance.errors.rejectValue('dnaPaxStorage', 'bpvErr42')
                            }                    
                            if (bpvBloodFormInstance.dnaPaxStorage > new Date()) {
                                bpvBloodFormInstance.errors.rejectValue('dnaPaxStorage', 'bpvErr43')
                            }                                    
                            //                                    if (bpvBloodFormInstance.dnaPaxStorage < bpvBloodFormInstance.dnaPaxFrozen) {
                            //                                        bpvBloodFormInstance.errors.rejectValue('dnaPaxStorage', 'bpvErr30')
                            //                                    }
                        }
                        if (bpvBloodFormInstance.dnaPaxProcTech.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('dnaPaxProcTech', 'bpvErr16')
                        }
                        if (bpvBloodFormInstance.dnaPaxProcSopDev.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('dnaPaxProcSopDev', 'bpvErr17')
                        } else {
                            if (bpvBloodFormInstance.dnaPaxProcSopDev.equals('No') && bpvBloodFormInstance.dnaPaxProcComments.equals(null)) {
                                bpvBloodFormInstance.errors.rejectValue('dnaPaxProcComments', 'bpvErr18')
                            }
                        }                                
                    }
                    //                    }
                }

                if (bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) {
                    if (plasmaPar.equals("No")) {
                        bpvBloodFormInstance.errors.rejectValue('plasmaParent', 'bpvErr72')
                    } else {
                        if (!bpvBloodFormInstance.plasmaParBarcode) {
                            bpvBloodFormInstance.errors.rejectValue('plasmaParBarcode', 'bpvErr80')
                        } else {
                                plasmaSpecimen = SpecimenRecord.findBySpecimenId(bpvBloodFormInstance.plasmaParBarcode)
                        }
                    }
                }
                if (plasmaPar.equals("Yes") && bpvBloodFormInstance.plasmaParBarcode!=null) {
                    if (plasmaConicalTube.equals("Yes")) {
                        if (bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) {
                            if (bpvBloodFormInstance.bloodFormVersion == 2) {
                                if (plasmaMinusEighty < 3) {
                                    bpvBloodFormInstance.errors.rejectValue('', 'bpvErr81')
                                } else if (plasmaMinusEighty > 3) {
                                    bpvBloodFormInstance.errors.rejectValue('', 'bpvErr83')
                                }
                                if (plasmaLN < 3) {
                                    bpvBloodFormInstance.errors.rejectValue('', 'bpvErr82')
                                }                             
                            }
                        } else {
                            if (plasmaChildren.equals("No")) {
                                bpvBloodFormInstance.errors.rejectValue('', 'bpvErr24')
                            }                            
                        }
                        
                        


//                        def plasmaSpecimen = SpecimenRecord.findBySpecimenId(bpvBloodFormInstance.plasmaParBarcode)
//
                        dateTimeBloodRecd = ""
                        if (plasmaSpecimen?.containerType == ContainerType.findByCode("LAVEDTA") && plasmaSpecimen?.fixative == Fixative.findByCode("EDTA") && plasmaSpecimen?.parentSpecimen == null && plasmaSpecimen?.tissueType == AcquisitionType.findByCode("BLOODPLAS") && plasmaSpecimen?.bloodTimeDraw.equals("D2")) {
                            dateTimeBloodRecd = bpvBloodFormInstance.dateTimeBloodReceived2
                        } else {
                            dateTimeBloodRecd = bpvBloodFormInstance.dateTimeBloodReceived
                        }
                        
                        if (!bpvBloodFormInstance.plasmaProcStart.equals(null)) {
                            if (dateTimeBloodRecd > bpvBloodFormInstance.plasmaProcStart) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcStart', 'bpvErr44')
                            }                    
                            if (bpvBloodFormInstance.plasmaProcStart > new Date()) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcStart', 'bpvErr45')
                            }
                        } else {
                            if (bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcStart', 'bpvErr86')
                            }
                        }

                        if (!bpvBloodFormInstance.plasmaProcEnd.equals(null)) {
                            if (dateTimeBloodRecd > bpvBloodFormInstance.plasmaProcEnd) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcEnd', 'bpvErr46')
                            }                    
                            if (bpvBloodFormInstance.plasmaProcEnd > new Date()) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcEnd', 'bpvErr47')
                            }
                        }

                        if (!bpvBloodFormInstance.plasmaProcFrozen.equals(null)) {
                            if (dateTimeBloodRecd > bpvBloodFormInstance.plasmaProcFrozen) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcFrozen', 'bpvErr48')
                            }                    
                            if (bpvBloodFormInstance.plasmaProcFrozen > new Date()) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcFrozen', 'bpvErr49')
                            }
                        }

                        if (!bpvBloodFormInstance.plasmaProcStorage.equals(null)) {
                            if (dateTimeBloodRecd > bpvBloodFormInstance.plasmaProcStorage) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcStorage', 'bpvErr50')
                            }                    
                            if (bpvBloodFormInstance.plasmaProcStorage > new Date()) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcStorage', 'bpvErr51')
                            }
                        }                        
                        
                        if (bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) {
                            if (bpvBloodFormInstance.plasmaProcTech.equals(null)) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcTech', 'bpvErr87')
                            }
                            
                            if (bpvBloodFormInstance.plasmaTransferTech.equals(null)) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaTransferTech', 'bpvErr88')
                            }                            
                            
                        }
                        

                        if (bpvBloodFormInstance.plasmaProcSopDev.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('plasmaProcSopDev', 'bpvErr25')
                        } else {
                            if (bpvBloodFormInstance.plasmaProcSopDev.equals('No') && bpvBloodFormInstance.plasmaProcComments.equals(null)) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaProcComments', 'bpvErr26')
                            }
                        }
                        
                        if (bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) {
                             if (bpvBloodFormInstance.plasmaHemolysis.equals(null)) {
                                bpvBloodFormInstance.errors.rejectValue('plasmaHemolysis', 'bpvErr89')
                             }
                        }
                        
                    } else {
                        bpvBloodFormInstance.errors.rejectValue('', 'bpvErr32')
                    }
                }
                
                
                if (bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) {
                    if (plasmaPar.equals("Yes") && bpvBloodFormInstance.plasmaParBarcode!=null) {
                        def plasmaParentSpecimen = SpecimenRecord.findBySpecimenId(bpvBloodFormInstance.plasmaParBarcode)
                        
                        if (bpvBloodFormInstance.bloodFormVersion == 2) {
                            if (wcpSpecimen < 3) {
                                bpvBloodFormInstance.errors.rejectValue('', 'bpvErr79')
                            }
                        }

                        if (bpvBloodFormInstance.plasmaRemainingVol.equals(null) || bpvBloodFormInstance.plasmaRemainingVol.equals("0") || bpvBloodFormInstance.plasmaRemainingVol.equals("0.0")) {
                            bpvBloodFormInstance.errors.rejectValue('plasmaRemainingVol', 'bpvErr95')
                        }
                        
                            dateTimeBloodRecd = ""
                            if (plasmaParentSpecimen?.bloodTimeDraw.equals("D2")) {
                                dateTimeBloodRecd = bpvBloodFormInstance.dateTimeBloodReceived2
                            } else {
                                dateTimeBloodRecd = bpvBloodFormInstance.dateTimeBloodReceived
                            }
                            
                            if (!bpvBloodFormInstance.wholeBloodProcEnd.equals(null)) {
                                if (dateTimeBloodRecd > bpvBloodFormInstance.wholeBloodProcEnd) {
                                    bpvBloodFormInstance.errors.rejectValue('wholeBloodProcEnd', 'bpvErr92')
                                }                    
                                if (bpvBloodFormInstance.wholeBloodProcEnd > new Date()) {
                                    bpvBloodFormInstance.errors.rejectValue('wholeBloodProcEnd', 'bpvErr93')
                                }
                            } else {
                                    bpvBloodFormInstance.errors.rejectValue('wholeBloodProcEnd', 'bpvErr94') 
                            }                        

                            if (!bpvBloodFormInstance.wholeBloodProcFrozen.equals(null)) {
                                if (dateTimeBloodRecd > bpvBloodFormInstance.wholeBloodProcFrozen) {
                                    bpvBloodFormInstance.errors.rejectValue('wholeBloodProcFrozen', 'bpvErr73')
                                }                    
                                if (bpvBloodFormInstance.wholeBloodProcFrozen > new Date()) {
                                    bpvBloodFormInstance.errors.rejectValue('wholeBloodProcFrozen', 'bpvErr74')
                                }
                            } else {
                                    bpvBloodFormInstance.errors.rejectValue('wholeBloodProcFrozen', 'bpvErr90') 
                            }

                            if (!bpvBloodFormInstance.wholeBloodProcStorage.equals(null)) {
                                if (dateTimeBloodRecd > bpvBloodFormInstance.wholeBloodProcStorage) {
                                    bpvBloodFormInstance.errors.rejectValue('wholeBloodProcStorage', 'bpvErr75')
                                }                    
                                if (bpvBloodFormInstance.wholeBloodProcStorage > new Date()) {
                                    bpvBloodFormInstance.errors.rejectValue('wholeBloodProcStorage', 'bpvErr76')
                                }
                            } else {
                                    bpvBloodFormInstance.errors.rejectValue('wholeBloodProcStorage', 'bpvErr91')                             
                            }
                            
                        if (bpvBloodFormInstance.wholeBloodProcTech.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('wholeBloodProcTech', 'bpvErr96')
                        }                        

                            if (bpvBloodFormInstance.wholeBloodProcSopDev.equals(null)) {
                                bpvBloodFormInstance.errors.rejectValue('wholeBloodProcSopDev', 'bpvErr77')
                            } else {
                                if (bpvBloodFormInstance.wholeBloodProcSopDev.equals('No') && bpvBloodFormInstance.wholeBloodProcComments.equals(null)) {
                                    bpvBloodFormInstance.errors.rejectValue('wholeBloodProcComments', 'bpvErr78')
                                }
                            }                        
                        
                        
                    }
                }
                

                if (serumPar.equals("Yes")) {
                    //println "Serumchildren ::"+serumChildren
                    if (serumChildren.equals("No")) {
                        bpvBloodFormInstance.errors.rejectValue('', 'bpvErr27')
                    }

//                    def serumSpecimen = SpecimenRecord.findBySpecimenId(bpvBloodFormInstance.serumParBarcode)
//
                    dateTimeBloodRecd = ""
                    if (serumSpecimen?.containerType == ContainerType.findByCode("SST") && serumSpecimen?.parentSpecimen == null && serumSpecimen?.tissueType == AcquisitionType.findByCode("BLOODSRM") && serumSpecimen?.bloodTimeDraw.equals("D2")) {
                        dateTimeBloodRecd = bpvBloodFormInstance.dateTimeBloodReceived2
                    } else {
                        dateTimeBloodRecd = bpvBloodFormInstance.dateTimeBloodReceived
                    }
                        
                    if (!bpvBloodFormInstance.serumProcStart.equals(null)) {
                        if (dateTimeBloodRecd > bpvBloodFormInstance.serumProcStart) {
                            bpvBloodFormInstance.errors.rejectValue('serumProcStart', 'bpvErr52')
                        }                    
                        if (bpvBloodFormInstance.serumProcStart > new Date()) {
                            bpvBloodFormInstance.errors.rejectValue('serumProcStart', 'bpvErr53')
                        }
                    }

                    if (!bpvBloodFormInstance.serumProcEnd.equals(null)) {
                        if (dateTimeBloodRecd > bpvBloodFormInstance.serumProcEnd) {
                            bpvBloodFormInstance.errors.rejectValue('serumProcEnd', 'bpvErr54')
                        }                    
                        if (bpvBloodFormInstance.serumProcEnd > new Date()) {
                            bpvBloodFormInstance.errors.rejectValue('serumProcEnd', 'bpvErr55')
                        }
                    }

                    if (!bpvBloodFormInstance.serumProcFrozen.equals(null)) {
                        if (dateTimeBloodRecd > bpvBloodFormInstance.serumProcFrozen) {
                            bpvBloodFormInstance.errors.rejectValue('serumProcFrozen', 'bpvErr56')
                        }                    
                        if (bpvBloodFormInstance.serumProcFrozen > new Date()) {
                            bpvBloodFormInstance.errors.rejectValue('serumProcFrozen', 'bpvErr57')
                        }
                    }

                    if (!bpvBloodFormInstance.serumProcStorage.equals(null)) {
                        if (dateTimeBloodRecd > bpvBloodFormInstance.serumProcStorage) {
                            bpvBloodFormInstance.errors.rejectValue('serumProcStorage', 'bpvErr58')
                        }                    
                        if (bpvBloodFormInstance.serumProcStorage > new Date()) {
                            bpvBloodFormInstance.errors.rejectValue('serumProcStorage', 'bpvErr59')
                        }
                    }                    
                    
                    if (bpvBloodFormInstance.serumProcSopDev.equals(null)) {
                        bpvBloodFormInstance.errors.rejectValue('serumProcSopDev', 'bpvErr28')
                    } else {
                        if (bpvBloodFormInstance.serumProcSopDev.equals('No') && bpvBloodFormInstance.serumProcComments.equals(null)) {
                            bpvBloodFormInstance.errors.rejectValue('serumProcComments', 'bpvErr29')
                        }
                    }
                }        
            
            }
        }
    }
    
    Map getWarningMap(bpvBloodFormInstance) {
        Map warningMap = [:]
        float dnaVol = 0
        float rnaVol = 0
        def plasmaPar = "No"        
        def plasmaMinusEighty = 0
        def plasmaLN = 0
        def wcpSpecimen = 0        
        
        for (specimen in bpvBloodFormInstance.caseRecord?.specimens) {
            if(specimen.specimenId =~   "^" +bpvBloodFormInstance.caseRecord.caseId + "-[0-9][0-9]\$" ){
                }else{
                    warningMap.put('specimenId_'+specimen.specimenId, specimen.specimenId + " not in format ["+bpvBloodFormInstance.caseRecord.caseId+"]-XX")
                }
        }      
        
        
        if (bpvBloodFormInstance.bloodFormVersion == null) {
        for (specimen in bpvBloodFormInstance.caseRecord?.specimens) {
            if (specimen.tissueType?.code == 'BLOODDNA') {
                dnaVol += specimen.chpBloodRecord?.volume
            }
            if (specimen.tissueType?.code == 'BLOODRNA') {
                rnaVol += specimen.chpBloodRecord?.volume
            }
        }
        
        if (dnaVol > 0 && dnaVol < 7) {
            warningMap.put('volume_BLOODDNA', 'Minimum collection amount 7.0 ml was not met for DNA PAXgene tube(s)')
        }
        
        if (rnaVol > 0 && rnaVol < 2) {
            warningMap.put('volume_BLOODRNA', 'Minimum collection amount 2.0 ml was not met for RNA PAXgene tube(s)')
        }
        
        if (bpvBloodFormInstance.rnaPaxFrozen && bpvBloodFormInstance.rnaPaxStorage) {
            use (TimeCategory) {
                if (bpvBloodFormInstance.rnaPaxStorage < bpvBloodFormInstance.rnaPaxFrozen + 24.hours) {
                    warningMap.put('rnaPaxStorage', 'Time between RNA PAXgene tube was frozen and RNA PAXgene tube was transferred is less than 24 hours')
                } else if(bpvBloodFormInstance.rnaPaxStorage > bpvBloodFormInstance.rnaPaxFrozen + 72.hours) {
                    warningMap.put('rnaPaxStorage', 'Time between RNA PAXgene tube was frozen and RNA PAXgene tube was transferred exceeds 72 hours')
                }
            }
        }
        
        if (bpvBloodFormInstance.dateTimeBloodDraw != null && bpvBloodFormInstance.plasmaProcFrozen) {
            use (TimeCategory) {
                if (bpvBloodFormInstance.plasmaProcFrozen > bpvBloodFormInstance.dateTimeBloodDraw + 90.minutes) {
                    warningMap.put('plasmaProcFrozen', 'Time between blood was drawn and Plasma aliquots were frozen is greater than 90 minutes')
                }
            }
        }
       } else {
           if (bpvBloodFormInstance.bloodFormVersion == 2 || bpvBloodFormInstance.bloodFormVersion == 3) {
            float plasmaVol = 0
            float wcpVol = 0
            for (specimen in bpvBloodFormInstance.caseRecord?.specimens) {
                if(specimen.containerType == ContainerType.findByCode("LAVEDTA") && specimen.fixative == Fixative.findByCode("EDTA") && specimen.parentSpecimen == null && specimen.tissueType == AcquisitionType.findByCode("BLOODPLAS")) {
                    if(bpvBloodFormInstance.plasmaParBarcode!=null && specimen.specimenId.equals(bpvBloodFormInstance.plasmaParBarcode)) {
                        plasmaPar = "Yes"
                        plasmaVol+= specimen.chpBloodRecord?.volume
                    }
                }
                
                if(specimen.containerType == ContainerType.findByCode("CRYOV") && specimen.parentSpecimen != null && specimen.tissueType?.code == 'BLOODCP') {
                    if(bpvBloodFormInstance.plasmaParBarcode!=null && specimen.parentSpecimen.specimenId.equals(bpvBloodFormInstance.plasmaParBarcode)) {
                        wcpSpecimen++
                        wcpVol+= specimen.chpBloodRecord?.volume
                    }
                }
                
                if (specimen.tissueType?.code == 'BLOODDNA') {
                    dnaVol += specimen.chpBloodRecord?.volume
                }
                if (specimen.tissueType?.code == 'BLOODRNA') {
                    rnaVol += specimen.chpBloodRecord?.volume
                }
                if (bpvBloodFormInstance.bloodFormVersion > 2) {
                    if (specimen.chpBloodRecord.freezerType!=null && specimen.chpBloodRecord.freezerType.equals("-80°C Freezer")) {
                        plasmaMinusEighty++    
                    } else if (specimen.chpBloodRecord.freezerType!=null && specimen.chpBloodRecord.freezerType.equals("LN Freezer")) {
                        plasmaLN++
                    }
                }
                    
            }
            
/*        if (plasmaVol > 0 && plasmaVol < 8) {
            warningMap.put('volume_BLOODPLAS', 'Minimum collection amount 8.0 ml was not met for Plasma tube(s)')
        }*/
        
        if (dnaVol > 0 && dnaVol < 4) {
            warningMap.put('volume_BLOODDNA', 'Minimum collection amount 4.0 ml was not met for DNA PAXgene tube')
        }
        
        if (rnaVol > 0 && rnaVol < 1) {
            warningMap.put('volume_BLOODRNA', 'Minimum collection amount 1.0 ml was not met for RNA PAXgene tube')
        }
        
        if (bpvBloodFormInstance.bloodFormVersion > 2) {
            if (plasmaPar.equals("Yes") && bpvBloodFormInstance.plasmaParBarcode!=null) {
                    if (plasmaMinusEighty < 6) {
                        warningMap.put('plasmaTubes_warn1', 'Total number of aliquots transferred to -80°C Freezer is less than 6')
                    } else if (plasmaMinusEighty > 6) {
                        warningMap.put('plasmaTubes_warn1', 'Total number of aliquots transferred to -80°C Freezer is more than 6')
                    }
                    if (plasmaLN < 6) {
                        warningMap.put('plasmaTubes_warn2', 'Total number of aliquots transferred to LN Freezer is less than 6')
                    }
                    
                    if (wcpSpecimen < 3) {
                        warningMap.put('wcpTubes', 'Total number of Whole Cell Pellet aliquots for the chosen EDTA tube is less than 3')
                    }
            }   
        }
                
        if (bpvBloodFormInstance.rnaPaxFrozen && bpvBloodFormInstance.rnaPaxStorage) {
            use (TimeCategory) {
                if (bpvBloodFormInstance.rnaPaxStorage < bpvBloodFormInstance.rnaPaxFrozen + 24.hours) {
                    warningMap.put('rnaPaxStorage', 'Time between RNA PAXgene tube was frozen and RNA PAXgene tube was transferred is less than 24 hours')
                } else if(bpvBloodFormInstance.rnaPaxStorage > bpvBloodFormInstance.rnaPaxFrozen + 72.hours) {
                    warningMap.put('rnaPaxStorage', 'Time between RNA PAXgene tube was frozen and RNA PAXgene tube was transferred exceeds 72 hours')
                }
            }
        }                
        

               
           }            
       }
        return warningMap
    }
}
