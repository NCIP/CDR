package nci.obbr.cahub.datarecords

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import nci.obbr.cahub.staticmembers.*
import grails.converters.*

import nci.obbr.cahub.util.AppSetting
import nci.obbr.cahub.util.ActivityEvent
import nci.obbr.cahub.util.querytracker.Query
import nci.obbr.cahub.util.querytracker.Deviation
import nci.obbr.cahub.datarecords.ctc.PatientRecord
import nci.obbr.cahub.util.CachedGtexDonorVarsExport

import org.codehaus.groovy.grails.commons.ApplicationHolder

class CaseRecordController {
    def prcReportService

    def scaffold = CaseRecord

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def caseRecordService
    def bpvWorkSheetService
    def bpvCaseStatusService
    def activityEventService
    def cachedGtexDonorVarsExportService
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
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

        if(session.org.code.matches("OBBR")) {
            // count = CaseRecord.findAllByStudyAndCaseStatusNotEqual(study, CaseStatus.findByCode('WITHDR')).size()
            //try to improve performance
            count = CaseRecord.countByStudyAndCaseStatusNotEqual(study, CaseStatus.findByCode('WITHDR'))         
            caseList = CaseRecord.findAllByStudyAndCaseStatusNotEqual(study, CaseStatus.findByCode('WITHDR'), params)
        } else {
            //BSS view
            // get parent BSS, with the given org code
            def bss = BSS.findByCode(session.org.code)

            // get all bss, parent and subs
            def bssList = BSS.findAllByParentBss(bss)

            // get all case records that have the following BSS
            // count = CaseRecord.findAllByStudyAndBssInList(study, bssList).size()
            //  caseList = CaseRecord.findAllByStudyAndBssInList(study, bssList, params)
            def c = CaseRecord.createCriteria()
            /** def result_count = c.list{
            eq("study", study)
            ne("caseStatus", CaseStatus.findByCode('WITHDR') )
            inList("bss", bssList)
            }
            count = result_count.size()**/
            
            count = c.count{
                eq("study", study)
                ne("caseStatus", CaseStatus.findByCode('WITHDR') )
                inList("bss", bssList)
            }
            
            def ass
            def alias = 'al'
            def s2
            
            def s = params.sort
            if(!s)
            s="dateCreated"
            
            if(s=='bss.code'){
                ass='bss'
                s2='al.code'
            }else if(s=='caseCollectionType.name'){
                ass='caseCollectionType'
                s2='al.name'
            }else if(s=='caseStatus.name'){
                ass= 'caseStatus'
                s2='al.name'
                
            }else{
                
            }
               
            def o = params.order
            if(!o)
            o="desc"
           
            def c2 = CaseRecord.createCriteria()
            
            if(ass){
                caseList=c2.list (max:max, offset:offset){
                    eq("study", study)
                    ne("caseStatus", CaseStatus.findByCode('WITHDR') )
                    inList("bss", bssList)
                    createAlias(ass, alias)
                    order(s2, o)

                }

            }else{
                caseList=c2.list (max:max, offset:offset){
                    eq("study", study)
                    ne("caseStatus", CaseStatus.findByCode('WITHDR') )
                    inList("bss", bssList)
                    order(s, o)

                }
            }
            
        }
        
        def specimenCount=[:]
        //pmh CDRQA 1104 04/03/14
        def sixMonthFollowUpStat=[:]
        
        if(caseList){
            def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: caseList])
            count_result.each(){
                specimenCount.put(it[0], it[1])
             
            }
            //pmh CDRQA 1104 04/03/14
            if(study?.code == 'BPV'){
                sixMonthFollowUpStat = bpvCaseStatusService.getSixMonthFollowUpStatus(caseList)
            }
             
        }
         
         
        def queryCount = getQueryCountMap(caseList)
        
        def deviationCount = getDeviationCountMap(caseList)
        
        
        def frozenList=null
        if(study?.code == 'GTEX'){
            frozenList= caseRecordService.getFrozenList(caseList)
        }

       
        def brainList=null
        if(study?.code == 'GTEX'){
            brainList = caseRecordService.getBrainList(caseList)
        }

        return [caseRecordInstanceList: caseList, caseRecordInstanceTotal: count, specimenCount:specimenCount, specimenCountBMS:specimenCount, queryCount:queryCount, deviationCount:deviationCount, frozenList:frozenList, brainList:brainList,sixMonthFollowUpStat:sixMonthFollowUpStat]
    }

    

    
    def create = {
        def caseRecordInstance = new CaseRecord()
        caseRecordInstance.properties = params
        return [caseRecordInstance: caseRecordInstance]
    }

    def save = {
        def caseRecordInstance = new CaseRecord(params)
        if (caseRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"
            redirect(action: "show", id: caseRecordInstance.id)
        }
        else {
            render(view: "create", model: [caseRecordInstance: caseRecordInstance])
        }
    }

    def show = {   
            
        def caseRecordInstance
        def altMap
            
        try{Long.parseLong(params.id)
            caseRecordInstance = CaseRecord.get(params.id)    
        }
        catch(Exception e){}
            
        if(!caseRecordInstance){
            caseRecordInstance = CaseRecord.findByCaseId(params.id)
        }
        
        if (!caseRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"
            redirect(action: "list")
        }
        else {
            //ensure user is entitled to access this caseRecord instance
            checkAccessPrivilege(caseRecordInstance)
        }
        altMap = bpvWorkSheetService.getPriority(caseRecordInstance)
            
        def brainTissue = SpecimenRecord.executeQuery("select s from SpecimenRecord s inner join s.caseRecord c where s.tissueType.code='BRAIN' and c.id=?", [caseRecordInstance.id])
        boolean hasBrain=false
        if(brainTissue)
        hasBrain=true
               
        String specimenCount='NA'
        if(caseRecordInstance.study.code =='GTEX'){
            specimenCount = prcReportService.getSpecimenCount(caseRecordInstance)
        }
        def releaseCount = CachedGtexDonorVarsExport.findAllByCaseId(caseRecordInstance.caseId).size()
        def queryCount = getQueryCount(caseRecordInstance)
        def deviationCount = getDeviationCount(caseRecordInstance)
        def qtAttachments = getQtAttachments(caseRecordInstance)
        def frozenList = caseRecordService.getFrozenList(caseRecordInstance)
        def brainList = caseRecordService.getBrainList(caseRecordInstance)                   

        //pmh : for CTC 11/05/13
        def prInstance=PatientRecord.findByPatientId(caseRecordInstance.caseId)
        def ctc_patient_visits=prInstance?.visit
        

       
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase()  
        [caseRecordInstance:caseRecordInstance, altMap:altMap, hasBrain:hasBrain, specimenCount:specimenCount, queryCount:queryCount, deviationCount:deviationCount, qtAttachments:qtAttachments,ctc_patient_visits:ctc_patient_visits, username:username,brainList:brainList,frozenList:frozenList, releaseCount:releaseCount]
            
    }


    def view = {
        def caseRecordInstance
        def altMap
        try{Long.parseLong(params.id)
            caseRecordInstance = CaseRecord.get(params.id)    
        }
        catch(Exception e){}
            
        if(!caseRecordInstance){
            caseRecordInstance = CaseRecord.findByCaseId(params.id)
        }
        
        if (!caseRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"
            redirect(action: "list")
        }
        else {
            //ensure user is entitled to access this caseRecord instance
            checkAccessPrivilege(caseRecordInstance)
        }
            
        altMap = bpvWorkSheetService.getPriority(caseRecordInstance)
        def queryCount = getQueryCount(caseRecordInstance)
        def deviationCount = getDeviationCount(caseRecordInstance)
        def qtAttachments = getQtAttachments(caseRecordInstance)
        def frozenList = caseRecordService.getFrozenList(caseRecordInstance)
        def brainList = caseRecordService.getBrainList(caseRecordInstance)  

        def brainTissue = SpecimenRecord.executeQuery("select s from SpecimenRecord s inner join s.caseRecord c where s.tissueType.code='BRAIN' and c.id=?", [caseRecordInstance.id])
        boolean hasBrain=false
        if(brainTissue){
            hasBrain=true    
        }
        
        [caseRecordInstance:caseRecordInstance, altMap:altMap, queryCount:queryCount, deviationCount:deviationCount, qtAttachments:qtAttachments,brainList:brainList,frozenList:frozenList,hasBrain:hasBrain]
            
    }

    //We don't want anyone else to edit case records
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def edit = {
        def caseRecordInstance = CaseRecord.get(params.id)
        
        if (!caseRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"
            redirect(action: "list")
        }
        else {
            //ensure user is entitled to access this caseRecord instance
            checkAccessPrivilege(caseRecordInstance)
        }
    }

    def update = {
        def caseRecordInstance = CaseRecord.get(params.id)
        boolean statusChanged = false
        def oldStatus
        def newStatus
        
        if (caseRecordInstance) {
            //ensure user is entitled to access this caseRecord instance
            checkAccessPrivilege(caseRecordInstance)
            if (params.version) {
                def version = params.version.toLong()
                if (caseRecordInstance.version > version) {
                    
                    caseRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'caseRecord.label', default: 'Case Record For Case '+caseRecordInstance.caseId)] as Object[], "Another user has updated this CaseRecord while you were editing")
                    
                    def altMap = bpvWorkSheetService.getPriority(caseRecordInstance)
                    def queryCount = getQueryCount(caseRecordInstance)
                    def deviationCount = getDeviationCount(caseRecordInstance)
                    def qtAttachments = getQtAttachments(caseRecordInstance)
                    def frozenList = caseRecordService.getFrozenList(caseRecordInstance)
                    def brainList = caseRecordService.getBrainList(caseRecordInstance)          

                    def modelMap = [caseRecordInstance:caseRecordInstance, altMap:altMap, queryCount:queryCount, deviationCount:deviationCount, qtAttachments:qtAttachments,brainList:brainList,frozenList:frozenList]
            
                    //render(view: "view", model: [caseRecordInstance: caseRecordInstance])
                    render(view: "view", model: modelMap)
                    return
                }
            }
            if (caseRecordInstance.caseStatus != CaseStatus.get(params.caseStatus.id)) {
                statusChanged = true
                oldStatus = caseRecordInstance.caseStatus?.name
                newStatus = CaseStatus.get(params.caseStatus.id)?.name
            }
            caseRecordInstance.properties = params

            if (!caseRecordInstance.hasErrors() && caseRecordInstance.save(flush: true)) {
                if(("RELE".equals(caseRecordInstance?.caseStatus?.code) || "COMP".equals(caseRecordInstance?.caseStatus?.code) || "REMED".equals(caseRecordInstance?.caseStatus?.code)
                        || "INVA".equals(caseRecordInstance?.caseStatus?.code) || "WITHDR".equals(caseRecordInstance?.caseStatus?.code) || "INIT".equals(caseRecordInstance?.caseStatus?.code))
                    && caseRecordInstance?.study?.code == 'GTEX') {
                    caseRecordInstance.dmFastTrack = 2
                }

                if(statusChanged) {
                    if (caseRecordInstance?.caseStatus?.code.equals("BSSQACOMP") && caseRecordInstance?.study?.code.equals("GTEX")) {
                        caseRecordService.emailGTExBSSQACompleteCase(caseRecordInstance)
                    }
                    caseRecordService.emailBpvBSSQACompleteCase(caseRecordInstance)
                    cachedGtexDonorVarsExportService.cacheReleasedCase(caseRecordInstance, null, null)

                    def activityType = ActivityType.findByCode("STATUSCHG")
                    def caseId = caseRecordInstance.caseId
                    def study = caseRecordInstance.study
                    def bssCode = caseRecordInstance.bss?.parentBss?.code
                    def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                    activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, oldStatus, newStatus)
                }

                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"

                if (caseRecordInstance.study.code == 'BRN') {
                    redirect(action: "show", id: caseRecordInstance.id)
                } else if (session.org.code == 'OBBR') {
                    redirect(action: "show", id: caseRecordInstance.id)
                } else {
                    redirect(action: "view", id: caseRecordInstance.id)
                }
            } else {
                render(view: "edit", model: [caseRecordInstance: caseRecordInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"
            redirect(action: "list")
        }
    }
    
    def ctcUpdate = {
        def caseRecordInstance = CaseRecord.get(params.id)
     
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase() 
        if((caseRecordInstance.study.code!='CTC' && !session.DM) || (caseRecordInstance.study.code=='CTC' && !session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') && !session.authorities.contains('ROLE_ADMIN') &&  !AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username))){
            redirect(controller: "login", action: "denied")
            return
        }
        
       
        caseRecordInstance.properties = params
        caseRecordInstance.save(flush: true)
            
        
        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"

               
        redirect(action: "accessCtc", id: caseRecordInstance.id)
                
     
    }
     

    //We don't want anyone else to delete case records
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def caseRecordInstance = CaseRecord.get(params.id)
        if (caseRecordInstance) {
            //ensure user is entitled to access this caseRecord instance
            checkAccessPrivilege(caseRecordInstance)
            try {
                caseRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_ADMIN','ROLE_NCI-FREDERICK_CAHUB_DM','ROLE_NCI-FREDERICK_CAHUB_SUPER'])
    def emailCase = {
        def caseRecordInstance = CaseRecord.get(params.id)
        caseRecordService.initCaseEmail(caseRecordInstance)
        render(template: "sendCaseEmail", model: [id:caseRecordInstance.id, caseStatus:caseRecordInstance.caseStatus.code, emailSent:true])
    }

    def checkAccessPrivilege(caseRecordInstance)  {
            
        //Check to see if retrieved instance matches user's organization
        // println "caseRecordInstance.study.code: " + caseRecordInstance.study.code
        // println "session.study.code:            " + session.study?.code
        if (!session.study?.code) {
            session.setAttribute("chosenHome", new String(caseRecordInstance.study.code))
            def study = Study.findByCode(caseRecordInstance.study.code)
            session.study = study
        }
            
        if(caseRecordInstance.study != session.study){
            session.study = caseRecordInstance.study
        }
        if(caseRecordInstance.caseStatus?.code == 'WITHDR' &&  !(session.org?.code == 'OBBR' && (session.DM || session.LDS)) && session.org?.code != 'VARI'){
            redirect(controller: "login", action: "denied")
        }else{

            if(caseRecordInstance.bss.parentBss?.code?.matches(session.org.code) || session.org.code.matches("OBBR")){ 
                if (caseRecordInstance.study.code == 'BPV') {
                    def bpvCaseStatus = bpvCaseStatusService.getStatus(caseRecordInstance)
                    session.setAttribute("bpvCaseStatus", bpvCaseStatus)
                }

                return [caseRecordInstance: caseRecordInstance]
            }

            else if(session.org.code == 'VARI' || session.org.code == 'BROAD'){

                return [caseRecordInstance: caseRecordInstance]
            }
            else{
                redirect(controller: "login", action: "denied")
            }    
        }
    }

    def changeCaseStatus = {

        def caseRecordInstance = CaseRecord.get(params.id)
        
        if (!caseRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'Case Status For Case'), caseRecordInstance.caseId])}"
            redirect(action: "list")
        }
        else {
            
            def currentStatus = caseRecordInstance.caseStatus
            def filteredStatusList = []
            
            if(currentStatus.code == 'DATA'){
                def s1 = CaseStatus.findByCode('DATACOMP');
                filteredStatusList.add(s1);
            }
            if(currentStatus.code == 'DATACOMP' || currentStatus.code == 'REMED'){
                if (currentStatus.code == 'DATACOMP' && session.org.code != 'OBBR' && session.study.code == 'BPV') {
                    def s3 = CaseStatus.findByCode('DATA')
                    filteredStatusList.add(s3)
                }
                def s2 = CaseStatus.findByCode('BSSQACOMP');
                filteredStatusList.add(s2);
            }                        

            return [caseRecordInstance: caseRecordInstance, filteredStatusList:filteredStatusList]
        }        
                                                                                                                            
    }               
    
    
    
    def changeCtcStatus = {

        def caseRecordInstance = CaseRecord.get(params.id)
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase() 
        if((caseRecordInstance.study.code!='CTC' && !session.DM) || (caseRecordInstance.study.code=='CTC' && !session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') && !session.authorities.contains('ROLE_ADMIN') &&  !AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username))){
            redirect(controller: "login", action: "denied")
            return
        }
        
        if (!caseRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'Case Status For Case'), caseRecordInstance.caseId])}"
            redirect(action: "list")
        }
        else {
            
            def currentStatus = caseRecordInstance.caseStatus
            def filteredStatusList = []
            
            filteredStatusList.add(CaseStatus.findByCode('DATA'))
            filteredStatusList.add(CaseStatus.findByCode('COMP'))
            
          
           
            [caseRecordInstance: caseRecordInstance, filteredStatusList:filteredStatusList]
        }        
                                                                                                                            
    }               
    
    def display = {
        def caseRecordInstance
        try {
            Long.parseLong(params.id)
            caseRecordInstance = CaseRecord.get(params.id)    
        } catch (Exception e) {}

        if (!caseRecordInstance) {
            caseRecordInstance = CaseRecord.findByCaseId(params.id)
        }
        
        //dispatcher method to figure out who is requesting the caserecord details page
        flash.message = flash.message
        if (session.org?.code == 'OBBR') {
            redirect(action: "show", id: params.id)            
        } else if ((session.org?.code == 'VARI' || session.org?.code == 'BROAD') && caseRecordInstance.study?.code == 'BMS') {
            redirect(controller: "login", action: "denied")
        } else if (session.org?.code == 'VARI' && (caseRecordInstance.study?.code == 'BPV' || caseRecordInstance.study?.code == 'BRN')) {
            redirect(action: "showbpvdeident", id: params.id)
        } else {
            redirect(action: "view", id: params.id)            
        }
    }

    def getSpecimenCount = {
        def c = CaseRecord.get(params.id).specimens.size()
        def payload = ['count': c]
        render "${params.callback.encodeAsURL()}([${payload as JSON}])"
    }
        
    def getQueryCount(caseRecordInstance) {
        def activeStatus = QueryStatus.findByCode('ACTIVE')
        def queryCount
        if (session.org?.code == 'OBBR') {
            queryCount = Query.executeQuery("select count(*) from Query i inner join i.caseRecord c inner join i.queryStatus s where c.id = ? and s.id = ?", [caseRecordInstance.id, activeStatus.id])
        } else {
            queryCount = Query.executeQuery("select count(*) from Query i inner join i.caseRecord c inner join i.queryStatus s inner join i.organization o where c.id = ? and s.id = ? and o.code like ?", [caseRecordInstance.id, activeStatus.id, session.org?.code + "%"])
        }
            
        return queryCount[0]
    }
        
    def getQueryCountMap(caseRecordInstanceList) {
        def queryCount = [:]
        if (caseRecordInstanceList) {
            def activeStatus = QueryStatus.findByCode("ACTIVE")
            def countResult
            if (session.org?.code == 'OBBR') {
                countResult= Query.executeQuery("select c.id, count(*) from Query i inner join i.caseRecord c inner join i.queryStatus s where c in (:list) and s.id = :activeStatus group by c.id",  [list:caseRecordInstanceList, activeStatus:activeStatus.id])
            } else {
                countResult= Query.executeQuery("select c.id, count(*) from Query i inner join i.caseRecord c inner join i.queryStatus s inner join i.organization o where c in (:list) and s.id = :activeStatus and o.code like :org group by c.id",  [list:caseRecordInstanceList, activeStatus:activeStatus.id, org:session.org?.code + "%"])
            }
            countResult.each() {
                queryCount.put(it[0], it[1]) 
            }
        }
            
        return queryCount
    }
        
    def getDeviationCount(caseRecordInstance) {
        def openStatus = QueryStatus.findByCode('OPEN')
        def progressStatus = QueryStatus.findByCode('PROGRESS')
        def statList =[openStatus.id,progressStatus.id]
        def deviationCount
        if (session.org?.code == 'OBBR') {
            //deviationCount = Query.executeQuery("select count(*) from Deviation d inner join d.caseRecord c inner join d.queryStatus s where c.id = ? and s.id = ?", [caseRecordInstance.id, openStatus.id])
            deviationCount = Query.executeQuery("select count(*) from Deviation d inner join d.caseRecord c inner join d.queryStatus s where c.id = :caseID and s.id in (:list)", [caseID:caseRecordInstance.id, list:statList])
        } else {
            // deviationCount = Query.executeQuery("select count(*) from Deviation d inner join d.caseRecord c inner join d.queryStatus s inner join d.bss b where c.id = ? and s.id = ? and b.code = ?", [caseRecordInstance.id, openStatus.id, session.org?.code])
            deviationCount = Query.executeQuery("select count(*) from Deviation d inner join d.caseRecord c inner join d.queryStatus s inner join d.bss b where c.id = :caseID and s.id in (:list) and b.code = :bssCode", [caseID:caseRecordInstance.id, list:statList, bssCode:session.org?.code])
        }
            
        return deviationCount[0]
    }

    
     def getDeviationCountMap(caseRecordInstanceList) {
        def openStatus = QueryStatus.findByCode('OPEN')
        def progressStatus = QueryStatus.findByCode('PROGRESS')
        def resolvedStatus = QueryStatus.findByCode('RESOLVED')
        def statList =[openStatus.id,progressStatus.id, resolvedStatus.id]
        def deviationCount=[:]
        def countResult
        if(caseRecordInstanceList){
            if (session.org?.code && session.org?.code == 'OBBR') {
                //deviationCount = Query.executeQuery("select count(*) from Deviation d inner join d.caseRecord c inner join d.queryStatus s where c.id = ? and s.id = ?", [caseRecordInstance.id, openStatus.id])
                countResult = Query.executeQuery("select c.id, count(*) from Deviation d inner join d.caseRecord c inner join d.queryStatus s where c in (:caselist) and s.id in (:list) group by c.id", [caselist:caseRecordInstanceList, list:statList])
            } else if(session.org?.code && session.org?.code != 'OBBR'){
                // deviationCount = Query.executeQuery("select count(*) from Deviation d inner join d.caseRecord c inner join d.queryStatus s inner join d.bss b where c.id = ? and s.id = ? and b.code = ?", [caseRecordInstance.id, openStatus.id, session.org?.code])
                countResult = Query.executeQuery("select c.id, count(*) from Deviation d inner join d.caseRecord c inner join d.queryStatus s inner join d.bss b where c in (:caselist) and s.id in (:list) and b.code = :bssCode group by c.id", [caselist:caseRecordInstanceList, list:statList, bssCode:session.org?.code])
            }else{

            }
        }  
         countResult.each() {
                deviationCount.put(it[0], it[1]) 
            }
        return deviationCount
    }
    
    def getQtAttachments(caseRecordInstance) {
        def result = []    
        def queryInstanceList = []
        
        if (session.org?.code == 'OBBR') {
            queryInstanceList = Query.createCriteria().list {
                createAlias("caseRecord", "c")
                eq("c.id", caseRecordInstance.id)
                //isNotNull("queryStatus")
            }
        } else {
            queryInstanceList = Query.createCriteria().list {
                createAlias("caseRecord", "c")
                eq("c.id", caseRecordInstance.id)
                createAlias("organization", "o")
                like("o.code", session.org?.code + "%")
                isNotNull("queryStatus")
            }
        }
            
        for (queryInstance in queryInstanceList) {
            for (queryAttachment in queryInstance.queryAttachments) {
                result.add(queryAttachment.fileUpload)
            }
        }
            
        return result
    }
    

    
    def showbpvdeident = {   
            
        def caseRecordInstance
        def altMap
            
        try{Long.parseLong(params.id)
            caseRecordInstance = CaseRecord.get(params.id)    
            // return [caseRecordInstance: caseRecordInstance]
        }
        catch(Exception e){}
            
        if(!caseRecordInstance){
            caseRecordInstance = CaseRecord.findByCaseId(params.id)
            // return [caseRecordInstance: caseRecordInstance]
        }
        
        if (!caseRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"
            redirect(action: "list")
        }
            
        altMap = bpvWorkSheetService.getPriority(caseRecordInstance)
        def queryCount = getQueryCount(caseRecordInstance) 
            
        [caseRecordInstance:caseRecordInstance, altMap:altMap, queryCount:queryCount]
            

    }
        
    
    def changeCaseFastTrackStatus = {
        def caseRecordInstance = CaseRecord.get(params.id)
        if (!caseRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'Case Status For Case'), caseRecordInstance.caseId])}"
            redirect(action: "list")
        }
        else {
            
            //def fastTrackCasesCount = []
            //def count = CaseRecord.executeQuery("select count(*) from CaseRecord c where c.dmFastTrack=1")
            def count = CaseRecord.executeQuery("select count(*) from CaseRecord c where c.dmFastTrack=1 and (caseStatus=1 or caseStatus=2 or caseStatus=3 or caseStatus=4 or caseStatus=5 or caseStatus=21)")
            def currentFTStatus = caseRecordInstance.dmFastTrack
            if (currentFTStatus==null) {
                currentFTStatus=0
            }
            int maxFastTrackCases = Integer.parseInt(AppSetting.findByCode("MAX_FASTTRACK_CASES")?.value)

            if(currentFTStatus == 0){
                if (count.get(0) == maxFastTrackCases || count.get(0) > maxFastTrackCases) {
                    //caseRecordInstance.errors.reject('', 'The maximum limit for cases that can be moved to the FastTrack queue(15) has been reached. You may remove one or more case(s) from that queue and retry adding the desired case to the FastTrack queue.')
                    render '<div class="errors"><ul><li>The maximum limit for cases that can be moved to the FastTrack queue (' + maxFastTrackCases + ') has been reached. You may remove one or more case(s) from that queue and retry adding the desired case to the FastTrack queue.</li></ul></div>'
                    response.status = 409 // To simulate an error
                } else {
                    caseRecordInstance.dmFastTrack = 1
                        
                    def activityType = ActivityType.findByCode("FASTTRACK")
                    def caseId = caseRecordInstance.caseId
                    def study = caseRecordInstance.study
                    def bssCode = caseRecordInstance.bss?.parentBss?.code
                    def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                    activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, null, null)
                    //render '<img src="/cahubdataservices/images/fastTrack.gif">' 
                    render '<img src="/cahubdataservices/images/fastTrack.gif" onmouseover="tooltip.show(\'Click to remove this case from the FastTrack queue\');" onmouseout="tooltip.hide();">'
                }
            }   else {
                caseRecordInstance.dmFastTrack = 0
                //render '<img src="/cahubdataservices/images/normalStatus.gif">'
                render '<img src="/cahubdataservices/images/normalStatus.gif" onmouseover="tooltip.show(\'Click to add this case into the FastTrack queue\');" onmouseout="tooltip.hide();">'
            }
        }
    }    

    def createCtcCase = {
        def caseRecord = new CaseRecord()
        caseRecord.properties = params
        caseRecord.caseCollectionType = CaseCollectionType.findByCode('SURGI')
        caseRecord.study = Study.findByCode('CTC')
        caseRecord.caseStatus = CaseStatus.findByCode('DATA')
        caseRecord.cdrVer = ApplicationHolder.application.metadata['app.version']

        def c = CaseRecord.createCriteria()
        
        def bssList = BSS.createCriteria().list(params) {
            eq("study", Study.findByCode('CTC'))
        }
        
        return [caseRecord: caseRecord, bssList: bssList]
    }
    
    
    
    def saveCtcCase = {
        def caseRecord = new CaseRecord(params)
        // println caseRecord.caseStatus.name
        //println "xx"
        def bssList = BSS.createCriteria().list(params) {
            eq("study", Study.findByCode('CTC'))
        }
            
        def resultMap = checkCtcErrors(caseRecord)        
        
        if(resultMap){
            resultMap.each{    
                caseRecord.errors.rejectValue(it.key, it.value)                
            }
            
            render(view: "createCtcCase", model: [caseRecord: caseRecord, bssList: bssList])
            return [caseRecord: caseRecord]            
        }
        else if (caseRecord.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecord.caseId])}"
            redirect(action: "show", id: caseRecord.id)
        }
        else {
            render(view: "create", model: [caseRecord: caseRecord])
        }            
    }    

    
    
    static Map checkCtcErrors(caseRecordInstance){
        
        def resultMap = [:]
        def ret 

        if (!caseRecordInstance.caseId) {

            resultMap.put("caseId", "Please enter a Case ID.")
        }
        
        return resultMap
    }    
    
  
    def accessCtc = {   
            
        def caseRecordInstance
        def altMap
            
        try{Long.parseLong(params.id)
            caseRecordInstance = CaseRecord.get(params.id)    
        }
        catch(Exception e){}
            
        if(!caseRecordInstance){
            caseRecordInstance = CaseRecord.findByCaseId(params.id)
        }
        
        
        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase() 
        // println("user name: " + username)
        if (!session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB')  && !session.authorities.contains('ROLE_ADMIN') && !AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)) {
            redirect(controller: "login", action: "denied")
            return
        }
        
        if (!caseRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseRecord.label', default: 'Case Record For Case'), caseRecordInstance.caseId])}"
            redirect(action: "list")
        }
   
        //pmh : for CTC 11/05/13
        def prInstance=PatientRecord.findByPatientId(caseRecordInstance.caseId)
        def ctc_patient_visits=prInstance?.visit
       
        // def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase()  
        [caseRecordInstance:caseRecordInstance,  ctc_patient_visits:ctc_patient_visits, username:username]
            
    }
    
    //returns case systemid through JSON to allow front end interaction with correct case
    def getcaseid = {
        def resultsMap = [:]
        def caseRecordInstance = CaseRecord.findByCaseId(params.id)
        if (!caseRecordInstance) {
            resultsMap.put("casesysid","")
        } else {
            resultsMap.put("casesysid",caseRecordInstance.id)
        }
        if(params.callback) {
            render "${params.callback.encodeAsURL()}(${resultsMap as JSON})"
        } else {
            render resultsMap as JSON
        }
    }
    
    def getcaseinventoryfeed = {
        def caseId = params.id
            if (caseId==null) {
                caseId = params.caseId
            }
            caseId = caseId.trim()
            //def caseRecordInstance = CaseRecord.findByCaseId(caseId)
            def caseInventFeedJSON = [:]
            caseInventFeedJSON = caseRecordService.getInventFeedForCase(caseId)
            render "${params.callback.encodeAsURL()}(${caseInventFeedJSON as JSON})"
    }
    
    
}
