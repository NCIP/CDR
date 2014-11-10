package nci.obbr.cahub.forms.gtex

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.QueryStatus

import grails.plugins.springsecurity.Secured 
import nci.obbr.cahub.staticmembers.SOP
import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.util.querytracker.Deviation

class TissueRecoveryGtexController {

    def staticMemberService
    def accessPrivilegeService
    def gtexTrfService
    
    def scaffold = TissueRecoveryGtex
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tissueRecoveryGtexInstanceList: TissueRecoveryGtex.list(params), tissueRecoveryGtexInstanceTotal: TissueRecoveryGtex.count()]
    }

    def create = {
        def tissueRecoveryGtexInstance = new TissueRecoveryGtex()
        tissueRecoveryGtexInstance.properties = params
        
        def brainFlag = gtexTrfService.getBrainFlag(tissueRecoveryGtexInstance.caseRecord)
        def frozenCaseNum = gtexTrfService.getFrozenCaseNum(tissueRecoveryGtexInstance.caseRecord)
        
        def filteredTissueList = staticMemberService.getAcquisitionTypeList("GTEX")
        def sopVerLists = generateSopVerList()
        
        return [tissueRecoveryGtexInstance: tissueRecoveryGtexInstance, brainFlag: brainFlag, frozenCaseNum: frozenCaseNum, filteredTissueList: filteredTissueList, sopVerLists: sopVerLists]
    }

    def save = {
        
        def tissueRecoveryGtexInstance = new TissueRecoveryGtex(params)
        saveSop(tissueRecoveryGtexInstance, params)

        
        def filteredTissueList = staticMemberService.getAcquisitionTypeList('GTEX')
        
        //cdrqa-542
        def brainFlag = gtexTrfService.getBrainFlag(tissueRecoveryGtexInstance.caseRecord)
        def frozenCaseNum = gtexTrfService.getFrozenCaseNum(tissueRecoveryGtexInstance.caseRecord)
        
        def resultMap = checkErrors(params, tissueRecoveryGtexInstance)
            
        if(resultMap){
            resultMap.each{    
                tissueRecoveryGtexInstance.errors.rejectValue(it.key, it.value)            
            }
            def sopVerLists = generateSopVerList()
            
            render(view: "create", model: [tissueRecoveryGtexInstance: tissueRecoveryGtexInstance, filteredTissueList: filteredTissueList, sopVerLists: sopVerLists,brainFlag:brainFlag,frozenCaseNum:frozenCaseNum])
            return            
        }
        else{
            //if no validation errors, create deviation(s)
            saveDeviation(tissueRecoveryGtexInstance, params)            
        }
                        
       
        if (tissueRecoveryGtexInstance.save(flush: true)) {

            flash.message = "${message(code: 'default.created.message', args: [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case'), tissueRecoveryGtexInstance?.caseRecord?.caseId])}"
            redirect(action: "edit", id: tissueRecoveryGtexInstance.id)
        }
        else {
            def sopVerLists = generateSopVerList()
            
            render(view: "create", model: [tissueRecoveryGtexInstance: tissueRecoveryGtexInstance, filteredTissueList: filteredTissueList, sopVerLists: sopVerLists,brainFlag:brainFlag,frozenCaseNum:frozenCaseNum])
        }
    }

    def show = {
        redirect(controller: "login", action: "denied")
        def tissueRecoveryGtexInstance = TissueRecoveryGtex.get(params.id)
        if (!tissueRecoveryGtexInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case'), tissueRecoveryGtexInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
        else {
            def brainFlag = gtexTrfService.getBrainFlag(tissueRecoveryGtexInstance.caseRecord)
            def frozenCaseNum = gtexTrfService.getFrozenCaseNum(tissueRecoveryGtexInstance.caseRecord)
            def filteredTissueList = staticMemberService.getAcquisitionTypeList('GTEX')
            def sopVerLists = generateSopVerList()
            
            return [tissueRecoveryGtexInstance: tissueRecoveryGtexInstance, brainFlag: brainFlag, frozenCaseNum: frozenCaseNum, filteredTissueList: filteredTissueList, sopVerLists: sopVerLists]
        }
    }

    def view = {
        def tissueRecoveryGtexInstance = TissueRecoveryGtex.get(params.id)
        
        if (!tissueRecoveryGtexInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case'), tissueRecoveryGtexInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
        else {
            
            def caseRecord = tissueRecoveryGtexInstance?.caseRecord
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'view')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }
            
            
            def brainFlag = gtexTrfService.getBrainFlag(tissueRecoveryGtexInstance.caseRecord)
            def frozenCaseNum = gtexTrfService.getFrozenCaseNum(tissueRecoveryGtexInstance.caseRecord)
            def filteredTissueList = staticMemberService.getAcquisitionTypeList('GTEX')
            def sopVerLists = generateSopVerList()
            
            return [tissueRecoveryGtexInstance: tissueRecoveryGtexInstance, brainFlag: brainFlag, frozenCaseNum: frozenCaseNum, filteredTissueList: filteredTissueList, sopVerLists: sopVerLists]
        }
    }    
    
    def edit = {
        def tissueRecoveryGtexInstance = TissueRecoveryGtex.get(params.id)
        
        def brainFlag = gtexTrfService.getBrainFlag(tissueRecoveryGtexInstance.caseRecord)
        def frozenCaseNum = gtexTrfService.getFrozenCaseNum(tissueRecoveryGtexInstance.caseRecord)
        

        /** if(tissueRecoveryGtexInstance.caseRecord.caseStatus.code != 'DATA' &&
        tissueRecoveryGtexInstance.caseRecord.caseStatus.code != 'DATACOMP' &&
        tissueRecoveryGtexInstance.caseRecord.caseStatus.code != 'REMED'){
           
        redirect(controller: "login", action: "denied")
           
        }**/
        
        
        def caseRecord = tissueRecoveryGtexInstance?.caseRecord
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
//        if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//            redirect(controller: "login", action: "denied")
//            return
//        }
        
        
        if (!tissueRecoveryGtexInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case'), tissueRecoveryGtexInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
        else {

            def filteredTissueList = staticMemberService.getAcquisitionTypeList('GTEX')
            def sopVerLists = generateSopVerList()
        
            return [tissueRecoveryGtexInstance: tissueRecoveryGtexInstance, brainFlag: brainFlag, frozenCaseNum: frozenCaseNum, filteredTissueList: filteredTissueList, sopVerLists: sopVerLists]
        }
    }
    
    def editWithValidation = {
        def tissueRecoveryGtexInstance = TissueRecoveryGtex.get(params.id)
        def brainFlag = gtexTrfService.getBrainFlag(tissueRecoveryGtexInstance.caseRecord)
        def frozenCaseNum = gtexTrfService.getFrozenCaseNum(tissueRecoveryGtexInstance.caseRecord)
        def caseRecord = tissueRecoveryGtexInstance?.caseRecord
        
        int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
        if (accessPrivilege > 0) {
             redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
             return
        }
//        if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//            redirect(controller: "login", action: "denied")
//            return
//        }
        
        if (!tissueRecoveryGtexInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case'), tissueRecoveryGtexInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
        else {
            def filteredTissueList = staticMemberService.getAcquisitionTypeList('GTEX')
            def sopVerLists = generateSopVerList()
            
            //def tlvDate=tissueRecoveryGtexInstance.teamLeadVeriDate
            //def collectionDate=tissueRecoveryGtexInstance.collectionDate
            
            def resultMap
            resultMap = checkErrors(params, tissueRecoveryGtexInstance)
            
            if(resultMap){
                resultMap.each{  
                    tissueRecoveryGtexInstance.errors.rejectValue(it.key, it.value) 
                }               
            } 
            
            render(view: "edit", model: [tissueRecoveryGtexInstance: tissueRecoveryGtexInstance, brainFlag: brainFlag, frozenCaseNum: frozenCaseNum, filteredTissueList: filteredTissueList, sopVerLists: sopVerLists])
        }
    }
    
    def update = {
        def tissueRecoveryGtexInstance = TissueRecoveryGtex.get(params.id)
        def filteredTissueList = staticMemberService.getAcquisitionTypeList('GTEX')
        
        def brainFlag = gtexTrfService.getBrainFlag(tissueRecoveryGtexInstance.caseRecord)
        def frozenCaseNum = gtexTrfService.getFrozenCaseNum(tissueRecoveryGtexInstance.caseRecord)         
        
        if (tissueRecoveryGtexInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tissueRecoveryGtexInstance.version > version) {
                    def sopVerLists = generateSopVerList()
                    
                    tissueRecoveryGtexInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case')] as Object[], "Another user has updated this TissueRecoveryGtex while you were editing")
                    render(view: "edit", model: [tissueRecoveryGtexInstance: tissueRecoveryGtexInstance, filteredTissueList: filteredTissueList, sopVerLists: sopVerLists,brainFlag: brainFlag, frozenCaseNum: frozenCaseNum])
                    return
                }
            }
                                                            
            
            //tissueRecoveryGtexInstance.clearErrors()
            //def tlvDate=tissueRecoveryGtexInstance.teamLeadVeriDate
            // def collectionDate=tissueRecoveryGtexInstance.collectionDate
            
            tissueRecoveryGtexInstance.properties = params
            saveSop(tissueRecoveryGtexInstance, params) 
            saveDeviation(tissueRecoveryGtexInstance, params)
            
            if (!tissueRecoveryGtexInstance.hasErrors() && tissueRecoveryGtexInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case'), tissueRecoveryGtexInstance?.caseRecord?.caseId])}"
                if (params.coreBodyTempLocHelper == 'Other') {
                    redirect(action: "editWithValidation", id: tissueRecoveryGtexInstance.id, params: [tl: "o"])
                } else {
                    redirect(action: "editWithValidation", id: tissueRecoveryGtexInstance.id)
                }
            }
            else {
                def sopVerLists = generateSopVerList()
                render(view: "edit", model: [tissueRecoveryGtexInstance: tissueRecoveryGtexInstance, brainFlag: brainFlag, frozenCaseNum: frozenCaseNum, filteredTissueList: filteredTissueList, sopVerLists: sopVerLists])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case'), tissueRecoveryGtexInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }

    
    //We don't want anyone else to delete 
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def tissueRecoveryGtexInstance = TissueRecoveryGtex.get(params.id)
        if (tissueRecoveryGtexInstance) {
            try {
                tissueRecoveryGtexInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case'), tissueRecoveryGtexInstance?.caseRecord?.caseId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case'), tissueRecoveryGtexInstance?.caseRecord?.caseId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TissueRecoveryGtex.label', default: 'GTex Tissue Recovery Form For Case'), tissueRecoveryGtexInstance?.caseRecord?.caseId])}"
            redirect(action: "list")
        }
    }

    Map generateSopVerList() {
        def sopVerLists = [:]
        def oldList = []
        def activeSopVer
        def newList = []
        
        oldList = SOP.findByCode('OP-0001')?.sopVersions?.sort{it.name}?.reverse()
        activeSopVer = SOP.findByCode('OP-0001')?.activeSopVer
        newList = [activeSopVer]
        for (oldVer in oldList) {
            if (oldVer.name != activeSopVer) {
                newList.add(oldVer.name)
            }
        }
        sopVerLists.put('op0001', newList)
        
        oldList = SOP.findByCode('PR-0004')?.sopVersions?.sort{it.name}?.reverse()
        activeSopVer = SOP.findByCode('PR-0004')?.activeSopVer
        newList = [activeSopVer]
        for (oldVer in oldList) {
            if (oldVer.name != activeSopVer) {
                newList.add(oldVer.name)
            }
        }
        sopVerLists.put('pr0004', newList)
        
        oldList = SOP.findByCode('PM-0003')?.sopVersions?.sort{it.name}?.reverse()
        activeSopVer = SOP.findByCode('PM-0003')?.activeSopVer
        newList = [activeSopVer]
        for (oldVer in oldList) {
            if (oldVer.name != activeSopVer) {
                newList.add(oldVer.name)
            }
        }
        sopVerLists.put('pm0003', newList)
        
        return sopVerLists 
    }
    
    void saveSop(tissueRecoveryGtexInstance, params) {
        def sopInstance
        
        if (params.op0001Ver) {
            if (tissueRecoveryGtexInstance.op0001) {
                tissueRecoveryGtexInstance.op0001?.sopVersion = params.op0001Ver
            } else {
                sopInstance = SOP.findByCode('OP-0001')
                tissueRecoveryGtexInstance.op0001 = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:params.op0001Ver).save(flush: true) 
            }
        }
        
        if (params.pr0004Ver) {
            if (tissueRecoveryGtexInstance.pr0004) {
                tissueRecoveryGtexInstance.pr0004?.sopVersion = params.pr0004Ver
            } else {
                sopInstance = SOP.findByCode('PR-0004')
                tissueRecoveryGtexInstance.pr0004 = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:params.pr0004Ver).save(flush: true) 
            }
        }
        
        if (params.pm0003Ver) {
            if (tissueRecoveryGtexInstance.pm0003) {
                tissueRecoveryGtexInstance.pm0003?.sopVersion = params.pm0003Ver
            } else {
                sopInstance = SOP.findByCode('PM-0003')
                tissueRecoveryGtexInstance.pm0003 = new SOPRecord(sopId:sopInstance.id, sopNumber:sopInstance.sopNumber, sopVersion:params.pm0003Ver).save(flush: true) 
            }
        }
    }
    
    void saveDeviation(tissueRecoveryGtexInstance, params) {
        def deviation

        if (params.memoCiNumOp0001) {

            if(tissueRecoveryGtexInstance?.deviationOp0001){
                tissueRecoveryGtexInstance.deviationOp0001.memoCiNum = params.memoCiNumOp0001
                tissueRecoveryGtexInstance.deviationOp0001.description = "Memo or Approved Deviation # "+ params.memoCiNumOp0001
                tissueRecoveryGtexInstance.deviationOp0001.dateDeviation = tissueRecoveryGtexInstance.collectionDate
            }

            else{
                    deviation = new Deviation(caseRecord:tissueRecoveryGtexInstance.caseRecord,
                                          description:"Memo or Approved Deviation # "+ params.memoCiNumOp0001,
                                          sop:"OP-0001",                      
                                          planned:"Yes",
                                          memoCiNum:params.memoCiNumOp0001,
                                          nonConformance:"No",
                                          type:"Minor",
                                          queryStatus:QueryStatus.findByCode('OPEN'),
                                          dateDeviation:tissueRecoveryGtexInstance.collectionDate).save(flush: true)
                                      
                tissueRecoveryGtexInstance.deviationOp0001 = deviation
            }
        }
        
        if (params.memoCiNumPr0004) {

            if(tissueRecoveryGtexInstance?.deviationPr0004){
                tissueRecoveryGtexInstance.deviationPr0004.memoCiNum = params.memoCiNumPr0004
                tissueRecoveryGtexInstance.deviationPr0004.description = "Memo or Approved Deviation # "+ params.memoCiNumPr0004
                tissueRecoveryGtexInstance.deviationPr0004.dateDeviation = tissueRecoveryGtexInstance.collectionDate
            }

            else{
                deviation = new Deviation(caseRecord:tissueRecoveryGtexInstance.caseRecord,
                                          description:"Memo or Approved Deviation # "+ params.memoCiNumPr0004,
                                          sop:"PR-0004",                      
                                          planned:"Yes",
                                          memoCiNum:params.memoCiNumPr0004,
                                          nonConformance:"No",
                                          type:"Minor",
                                          queryStatus:QueryStatus.findByCode('OPEN'),
                                          dateDeviation:tissueRecoveryGtexInstance.collectionDate).save(flush: true)               
                                      
                tissueRecoveryGtexInstance.deviationPr0004 = deviation
            }
        }        
        
        if (params.memoCiNumPm0003) {

            if(tissueRecoveryGtexInstance?.deviationPm0003){
                tissueRecoveryGtexInstance.deviationPm0003.memoCiNum = params.memoCiNumPm0003
                tissueRecoveryGtexInstance.deviationPm0003.description = "Memo or Approved Deviation # "+ params.memoCiNumPm0003
                tissueRecoveryGtexInstance.deviationPm0003.dateDeviation = tissueRecoveryGtexInstance.collectionDate
            }

            else{
                deviation = new Deviation(caseRecord:tissueRecoveryGtexInstance.caseRecord,
                                          description:"Memo or Approved Deviation # "+ params.memoCiNumPm0003,
                                          sop:"PM-0003",                      
                                          planned:"Yes",
                                          memoCiNum:params.memoCiNumPm0003,
                                          nonConformance:"No",
                                          type:"Minor",
                                          queryStatus:QueryStatus.findByCode('OPEN'),
                                          dateDeviation:tissueRecoveryGtexInstance.collectionDate).save(flush: true)
                                      
                tissueRecoveryGtexInstance.deviationPm0003 = deviation
            }
        }        
        
    }
    
    
    static Map checkErrors(params, tissueRecoveryGtexInstance){
        def resultMap = [:]
        
        // Do not check on the temperature fields for bss code 'RPCI' ( hub-cr-39)
        def bssCode = tissueRecoveryGtexInstance.caseRecord.bss.code
        
        if (!tissueRecoveryGtexInstance.collectionDate) {
            resultMap.put("collectionDate", 'tissueRecoveryGtex.collectionDate.blank')
        }           

        if (!tissueRecoveryGtexInstance.collectionStartTime) {
            resultMap.put("collectionStartTime", "tissueRecoveryGtex.collectionStartTime.blank")          
        }  
        
        if (tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code != 'SURGI') {
            if (!tissueRecoveryGtexInstance.chestIncisionTime) {
                resultMap.put("chestIncisionTime", "tissueRecoveryGtex.chestIncisionTime.blank")          
            } 
        } 
        
        if (tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code == 'SURGI' ||
            tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code == 'OPO') {
            if (!tissueRecoveryGtexInstance.crossClampTime) {
                resultMap.put("crossClampTime", "tissueRecoveryGtex.crossClampTime.blank")          
            }
        }        

        if (tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code == 'SURGI') {
            if (!tissueRecoveryGtexInstance.amputationType) {
                resultMap.put("amputationType", "tissueRecoveryGtex.amputationType.blank")          
            }   
        }
        
        if (!tissueRecoveryGtexInstance.firstBloodDrawDate) {
            resultMap.put("firstBloodDrawDate", "tissueRecoveryGtex.firstBloodDrawDate.blank")          
        }
        
        if (!tissueRecoveryGtexInstance.firstBloodDrawTime) {
            resultMap.put("firstBloodDrawTime", "tissueRecoveryGtex.firstBloodDrawTime.blank")          
        }
        
        if (!tissueRecoveryGtexInstance.firstTissueRemovedDate) {
            resultMap.put("firstTissueRemovedDate", "tissueRecoveryGtex.firstTissueRemovedDate.blank")          
        }
        
        if (!tissueRecoveryGtexInstance.firstTissueRemovedTime) {
            resultMap.put("firstTissueRemovedTime", "tissueRecoveryGtex.firstTissueRemovedTime.blank")          
        }
        
        if (tissueRecoveryGtexInstance.firstTissueRemoved.id == 0) {
            resultMap.put("firstTissueRemoved", "tissueRecoveryGtex.firstTissueRemoved.blank")          
        }
         
        if (!tissueRecoveryGtexInstance.teamLeadVeriDate) {
            resultMap.put("teamLeadVeriDate", "tissueRecoveryGtex.teamLeadVeriDate.blank")          
        } 
        
        if (!tissueRecoveryGtexInstance.teamLeader) {
            resultMap.put("teamLeader", "tissueRecoveryGtex.teamLeader.blank")          
        }  
        
        if (!tissueRecoveryGtexInstance.dataEnteredBy) {
            resultMap.put("dataEnteredBy", "tissueRecoveryGtex.dataEnteredBy.blank")          
        }     
        
        if (!tissueRecoveryGtexInstance.prosector1) {
            resultMap.put("prosector1", "tissueRecoveryGtex.prosector1.blank")          
        }  
        
        if (!tissueRecoveryGtexInstance.prosector2) {
            resultMap.put("prosector2", "tissueRecoveryGtex.prosector2.blank")          
        }  
        
        if (!tissueRecoveryGtexInstance.prosector3) {
            resultMap.put("prosector3", "tissueRecoveryGtex.prosector3.blank")          
        } 
        
        //hub-cr-39 changes: hide temparture fields if RPCI
        if (bssCode.contains('RPCI') && tissueRecoveryGtexInstance.caseRecord.caseCollectionType.code.equals('POSTM')) {
            // didnt want to have two NOT conditions. made it simple
        }
        else {
            if (tissueRecoveryGtexInstance.coreBodyTemp != -1000 && !tissueRecoveryGtexInstance.coreBodyTempLoc) {
                resultMap.put("coreBodyTempLoc", "Please select the location where the core body temperature was obtained.")          
            }        
            
            if (tissueRecoveryGtexInstance.coreBodyTemp == -1000 && tissueRecoveryGtexInstance.coreBodyTempLoc) {
                resultMap.put("coreBodyTemp", "Please enter the Core Body Temperature or remove Temperature Obtained Via.")          
            }        
            
            if (tissueRecoveryGtexInstance.coreBodyTemp != -1000 && params.coreBodyTempLocHelper == 'Other' && !tissueRecoveryGtexInstance.coreBodyTempLoc) {
                resultMap.put("coreBodyTempLoc", "Please specify the 'Other' location where the core body temperature was obtained.")          
            }
        }
        
        if(tissueRecoveryGtexInstance.donatedOther && !tissueRecoveryGtexInstance.donatedOrganOther){
            resultMap.put("donatedOrganOther", 'Please specify Other Organs that have been donated')
        }
        
        return resultMap
    }
    
    /**
    def brainFlag(caseId){
    //helper method to set flag on upper form fields of TRF
    def brainSpecimenId = caseId + '-0011'
        
    if(SpecimenRecord.findBySpecimenId(brainSpecimenId)){
    return true 
    }
    else{
    return false
    }

    }
     **/
    
    
    def migrate ={
        // helper method to migrate all the values from the 'organsDonated' field to the individual organs checkbox fields
        //mapping organs with those entered
        
        def organsDonatedMap=[:] 
       
        organsDonatedMap.put('adrenal','donatedOrganOther')	
        organsDonatedMap.put('adrenal (with kidneys)','donatedKidney')
        // organsDonatedMap.put('adrenal gland','donatedOrganOther')	
        // organsDonatedMap.put('adrenal glands','donatedOrganOther')
        // organsDonatedMap.put('adrenals','donatedOrganOther')
        //organsDonatedMap.put('and spleen','donatedOrganOther')
        organsDonatedMap.put('aorta','donatedHeart')
        organsDonatedMap.put('aorta)','donatedHeart')
        organsDonatedMap.put('appendage','donatedOrganOther')
        organsDonatedMap.put('bilateral kidneys','donatedKidney')	
        organsDonatedMap.put('coronary'	,'donatedHeart')	
        organsDonatedMap.put('coronary artery','donatedHeart')	
        organsDonatedMap.put('coronary)','donatedHeart')	
        organsDonatedMap.put('heart','donatedHeart')
        organsDonatedMap.put('heart ( coronary artery and aorta)','donatedHeart')		
        organsDonatedMap.put('heart (coronary artery','donatedHeart')	
        organsDonatedMap.put('heart (coronary artery)','donatedHeart')	
        organsDonatedMap.put('heart (coronary)'	,'donatedHeart')
        organsDonatedMap.put('heart (coronary/aorta)','donatedHeart')		
        organsDonatedMap.put('heart (ventricle'	,'donatedHeart')	
        organsDonatedMap.put('intestine','donatedOrganOther')
        organsDonatedMap.put('kidenys','donatedKidney')	
        organsDonatedMap.put('kidney','donatedKidney')
        organsDonatedMap.put('kidney (adrenal)'	,'donatedKidney')	
        organsDonatedMap.put('kidneys','donatedKidney')
        organsDonatedMap.put('kidneys (adrenal gland)','donatedKidney')	
        organsDonatedMap.put('kidneys (adrenal)','donatedKidney')
        organsDonatedMap.put('kidneys (adrenals)','donatedKidney')
        organsDonatedMap.put('kidneys. thyroid not procured due to medical examiner restricted recovery above the neck.','donatedKidney')	
        organsDonatedMap.put('left kidney','donatedKidney')	
        organsDonatedMap.put('liver','donatedLiver')
        organsDonatedMap.put('liver and kidneys (adrenal)','donatedLiver')	
        organsDonatedMap.put('lung', 'donatedLung')	
        organsDonatedMap.put('lungs', 'donatedLung')	
        organsDonatedMap.put('n/a','')
        organsDonatedMap.put('none','')
        organsDonatedMap.put('none - liver and kidneys were targeted but deemed unsuitable for transplant.', '')
        organsDonatedMap.put('pancreas'	, 'donatedPancreas')
        organsDonatedMap.put('pancreas (not whole)','donatedPancreas')	
        // organsDonatedMap.put('right and left', 'donatedOrganOther')	
        organsDonatedMap.put('right lung', 'donatedLung')	
        organsDonatedMap.put('ruled out due to concern renal cell carcinoma. biopsy showed no metastisis', '')
        organsDonatedMap.put('spleen', 'donatedOrganOther')	
        organsDonatedMap.put('spleen and pancreas', 'spleenpancreas')
        organsDonatedMap.put('whole body to iiam', 'donatedOrganOther'	)
       

       
        def odList = TissueRecoveryGtex.findAllByOrgansDonatedIsNotNull()
        def trgInstance
        def saveval
        def k
        def v
        odList.each{
            saveval=false
            trgInstance=it
            trgInstance.donatedOrganOther=''
            def instanceStr=trgInstance.organsDonated?.trim().toLowerCase()
            //println "instanceStr "+instanceStr
            organsDonatedMap.each{  
                k= it.key
              
                v= it.value
               
                if(instanceStr.contains(k)){
                   
                    if(v.equals('donatedLiver') && !instanceStr.contains('none')){
                       
                        trgInstance.donatedLiver=true
                        saveval=true
                        
                    }
                    if(v.equals('donatedKidney') && !instanceStr.contains('none')){
                        trgInstance.donatedKidney=true
                        saveval=true
                    }
                    if(v.equals('donatedLung')){
                        trgInstance.donatedLung=true
                        saveval=true
                    }
                    if(v.equals('donatedPancreas')){
                        trgInstance.donatedPancreas=true
                        saveval=true
                    }
                    if(v.equals('donatedHeart')){
                        trgInstance.donatedHeart=true
                        saveval=true
                    }
                    
                    if(k.equals('spleen') && (instanceStr.contains('kidneys (adrenal gland)') || instanceStr.contains('(adrenal)')) && instanceStr.contains('spleen')){
                             
                        if(!trgInstance.donatedOrganOther.contains('spleen')){
                            
                            if(trgInstance.donatedOrganOther){
                                trgInstance.donatedOrganOther=trgInstance.donatedOrganOther+','+'spleen'
                            }
                            else{
                                trgInstance.donatedOrganOther='spleen'
                            }
                        }
                        trgInstance.donatedOther=true
                        saveval=true
                        
                    }
                   
                    if(v.equals('donatedOrganOther') && !instanceStr.contains('with kidney') &&  !instanceStr.contains('kidneys (adrenal gland)') && !instanceStr.contains('(adrenal)') && !instanceStr.contains('kidneys (adrenals)')){
                             
                        if(trgInstance.donatedOrganOther) {                         
                            trgInstance.donatedOrganOther=trgInstance.donatedOrganOther+','+k
                        }
                        else
                        trgInstance.donatedOrganOther=k
                       
                        trgInstance.donatedOther=true
                        saveval=true
                        
                    }
                    if(v.equals('spleenpancreas')){
                        trgInstance.donatedPancreas=true
                        trgInstance.donatedOther=true
                         
                        
                        if(!trgInstance.donatedOrganOther.contains('spleen')){
                            
                            if(trgInstance.donatedOrganOther){
                                trgInstance.donatedOrganOther=trgInstance.donatedOrganOther+','+'spleen'
                            }
                            else{
                                trgInstance.donatedOrganOther='spleen'
                            }
                        }
                       
                        saveval=true
                    }
                }
            
        
            }
            
            if(saveval){
                trgInstance.save(failOnError:true)
            }
        }  
            
       
        render(" migration done.")   
    
        
    }
   
    def updateAqua() {
        def tissueRecoveryGtexInstance = TissueRecoveryGtex.get(params.id)
        render(template: "updateAqua", model: [tissueRecoveryGtexInstance: tissueRecoveryGtexInstance, bodyclass: "edit aquaUpdate"])
    }
}
