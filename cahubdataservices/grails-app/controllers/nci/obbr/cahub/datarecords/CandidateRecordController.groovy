package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.BSS
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.staticmembers.CaseStatus
import nci.obbr.cahub.staticmembers.CaseCollectionType
import nci.obbr.cahub.staticmembers.ActivityType
import org.springframework.web.context.request.RequestContextHolder
import nci.obbr.cahub.util.ComputeMethods
import grails.plugins.springsecurity.Secured
import nci.obbr.cahub.util.querytracker.Query
import nci.obbr.cahub.staticmembers.QueryStatus
import nci.obbr.cahub.forms.gtex.DonorEligibilityGtex
import nci.obbr.cahub.util.AppSetting
import grails.util.GrailsUtil

import org.codehaus.groovy.grails.commons.ApplicationHolder

class CandidateRecordController {

    def hubIdGenService
    def accessPrivilegeService
    def activityEventService
    def sendMailService
    //def utilService
    def org
    
    def beforeInterceptor = {
        org = session.org
    }     
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = CandidateRecord
    
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        def count
        def candidateList = []
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        def max = params.max
        def offset = params.offset
        if(!offset)
        offset = 0

        def needIndex = params.needIndex
        if(needIndex) {
            def caseRecord = CaseRecord.findByCaseId(needIndex)
            caseRecord.index()
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
        }else if(s=='study.name'){
            ass= 'study'
            s2='al.name'
                
        }else{
                
        }
               
        def o = params.order
        if(!o)
        o="desc"
           
        
        if(session.org.code.matches("OBBR")){
            
            def withdrCase = CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR'))
     
            if(withdrCase) {       
            
                def c = CandidateRecord.createCriteria()
                count = c.count{
                    eq("study", Study.findByCode(session.study.code))
                    or{

                        not {'in'("caseRecord", CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR')))}
                        isNull("caseRecord")
                    }
                }
           
            }else{
                 
                def c = CandidateRecord.createCriteria()
                count = c.count{
                    eq("study", Study.findByCode(session.study.code))
                        
                }
                 
            }
             
            if(withdrCase){
           
                def c2 = CandidateRecord.createCriteria()
                if(ass){
                    candidateList=c2.list (max:max, offset:offset){
                        eq("study", Study.findByCode(session.study.code))
                        or{

                            not {'in'("caseRecord", CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR')))}
                            isNull("caseRecord")
                        }
                        createAlias(ass, alias)
                        order(s2, o)
                          

                    }
                }else{
                    candidateList=c2.list (max:max, offset:offset){
                        eq("study", Study.findByCode(session.study.code))
                        or{

                            not {'in'("caseRecord", CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR')))}
                            isNull("caseRecord")
                        }
                        order(s, o)

                    }
                }
            }else{
                
                def c2 = CandidateRecord.createCriteria()
                if(ass){
                    candidateList=c2.list (max:max, offset:offset){
                        eq("study", Study.findByCode(session.study.code))
                        createAlias(ass, alias)
                        order(s2, o)
                     

                    }
                }else{
                    candidateList=c2.list (max:max, offset:offset){
                        eq("study", Study.findByCode(session.study.code))
                       
                        order(s, o)
                    }
                }
                
            }

           
            // count = CandidateRecord.findAllByStudy(Study.findByCode(session.study.code)).size()
           
            // candidateList = CandidateRecord.findAllByStudy(Study.findByCode(session.study.code), params)
        } else {
            def bss = BSS.findByCode(session.org.code)
            //get all bss, parent and subs
            def bssList = BSS.findAllByParentBss(bss)
            
            def withdrCase = CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR'))
             
            if(withdrCase){
            
                def c = CandidateRecord.createCriteria()
                count = c.count{
                    inList("bss", bssList)
                    or{

                        not {'in'("caseRecord", CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR')))}
                        isNull("caseRecord")
                    }
                }

            }else{
                def c = CandidateRecord.createCriteria()
                count = c.count{
                    inList("bss", bssList)
                    
                }
                
                
                
            }
           
            if(withdrCase){
                def c2 = CandidateRecord.createCriteria()
                if(ass){
                    candidateList=c2.list (max:max, offset:offset){
                        inList("bss", bssList)
                        or{

                            not {'in'("caseRecord", CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR')))}
                            isNull("caseRecord")
                        }
                        createAlias(ass, alias)
                        order(s2, o)
                          

                    }
                }else{
                       
                    candidateList=c2.list (max:max, offset:offset){
                        inList("bss", bssList)
                        or{

                            not {'in'("caseRecord", CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR')))}
                            isNull("caseRecord")
                        }
                        order(s, o)

                    }
                       
                }
            }else{
                def c2 = CandidateRecord.createCriteria()
                if(ass){
                    candidateList=c2.list (max:max, offset:offset){
                        inList("bss", bssList)

                        createAlias(ass, alias)
                        order(s2, o)

                    }
                }else{
                    candidateList=c2.list (max:max, offset:offset){
                        inList("bss", bssList)

                        order(s, o)

                    }
                }
            }

            // count = CandidateRecord.findAllByBssInList(bssList).size();
            // candidateList = CandidateRecord.findAllByBssInList(bssList, params)
        }

        def queryCountCandidate = getQueryCountMapCandidate(candidateList)
        return [candidateRecordInstanceList: candidateList, candidateRecordInstanceTotal: count, queryCountCandidate: queryCountCandidate]
    }

    def create = {
        def candidateRecordInstance = new CandidateRecord()
        candidateRecordInstance.properties = params
        
        def bssSubList = bssSubList(org)
        
        def filteredCaseCollectionTypeList = filteredCaseCollectionTypeList(org)
        
        //println filteredCaseCollectionTypeList
        
        return [candidateRecordInstance: candidateRecordInstance, bssSubList: bssSubList, filteredCaseCollectionTypeList:filteredCaseCollectionTypeList]
    }

    def save = {
        def candidateRecordInstance = new CandidateRecord(params)
        
        def bssSubList = bssSubList(org)
        
        def filteredCaseCollectionTypeList = filteredCaseCollectionTypeList(org)        
        
        def resultMap = checkErrors(candidateRecordInstance)

        if(resultMap){
            resultMap.each{    
                candidateRecordInstance.errors.rejectValue(it.key, it.value)                
            }
            
            render(view: "create", model: [candidateRecordInstance: candidateRecordInstance, bssSubList: bssSubList, filteredCaseCollectionTypeList:filteredCaseCollectionTypeList])
            return [candidateRecordInstance: candidateRecordInstance]            
        }
        
        candidateRecordInstance.candidateId = hubIdGenService.genCandidateId(candidateRecordInstance.bss.code)
        
        //pmh 08/15/13 new for ver 5.2
        candidateRecordInstance.cdrVer=ApplicationHolder.application.metadata['app.version']
        if (candidateRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
            
            if(candidateRecordInstance.study.code == 'GTEX'){
                redirect(action: "modify", id: candidateRecordInstance.id)
            }
            else{
                redirect(action: "view", id: candidateRecordInstance.id)
            }
            
            def activityType = ActivityType.findByCode("CANDIDATE")
            def study = candidateRecordInstance.study
            def bssCode = candidateRecordInstance.bss?.parentBss?.code
            def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
            def candidateId = candidateRecordInstance.candidateId
            def identifier = candidateRecordInstance.id
            activityEventService.createEvent(activityType, "N/A", study, bssCode, null, username, candidateId, identifier)
        }
        else {
            render(view: "create", model: [candidateRecordInstance: candidateRecordInstance])
        }
    }

    def show = {
        def candidateRecordInstance = CandidateRecord.get(params.id)
        if (!candidateRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
            redirect(action: "list")
        }
        else {
            //ensure user is entitled to access this caseRecord instance
            checkAccessPrivilege(candidateRecordInstance)
        }
    }

    def view = {
        def candidateRecordInstance = CandidateRecord.get(params.id)
        if (!candidateRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
            redirect(action: "list")
        }
        else {
            //ensure user is entitled to access this caseRecord instance
            checkAccessPrivilege(candidateRecordInstance)
        }
    }    
    
    def edit = {
        
        redirect(controller: "login", action: "denied")
        
        def candidateRecordInstance = CandidateRecord.get(params.id)
        if (!candidateRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
            redirect(action: "list")
        }
        else {
            //ensure user is entitled to access this caseRecord instance
            checkAccessPrivilege(candidateRecordInstance)
        }
    }
    
    def modify = {
        def candidateRecordInstance = CandidateRecord.get(params.id)
        

        
        if(params.needIndex){
            def caseRecord = CaseRecord.findByCaseId(params.needIndex)
            if(caseRecord)
                caseRecord.index()
        }
        if (!candidateRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
            redirect(action: "list")
        }
        else {
            //ensure user is entitled to access this caseRecord instance
            // checkAccessPrivilege(candidateRecordInstance)
            // println("start check....")
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilegeCandidate(candidateRecordInstance, session, 'edit')
            if (accessPrivilege > 0) {
                redirect(controller: "login", action: ((accessPrivilege == 1) ? "denied" : "sessionconflict"))
                return
            }
            
           
        }
        def filteredCaseCollectionTypeList = filteredCaseCollectionTypeList(org)
        def queryCount = getQueryCount(candidateRecordInstance)
        def qtAttachments = getQtAttachments(candidateRecordInstance)
        [candidateRecordInstance:candidateRecordInstance, filteredCaseCollectionTypeList:filteredCaseCollectionTypeList, queryCount:queryCount, qtAttachments:qtAttachments]
    }    

    def update = {
        def candidateRecordInstance = CandidateRecord.get(params.id)
        boolean linkingCase = false
        if (candidateRecordInstance) {
            
            //ensure user is entitled to access this caseRecord instance
            checkAccessPrivilege(candidateRecordInstance)
            
            if (params.version) {
                def version = params.version.toLong()
                if (candidateRecordInstance.version > version) {
                    
                    candidateRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'candidateRecord.label', default: 'CandidateRecord')] as Object[], "Another user has updated this CandidateRecord while you were editing")
                    def queryCount = getQueryCount(candidateRecordInstance)
                    render(view: "modify", model: [candidateRecordInstance: candidateRecordInstance, queryCount: queryCount])
                    return
                }
            }
            candidateRecordInstance.properties = params
            
            def pre_case_id = params.pre_case_id
            def current_case_id = candidateRecordInstance.caseRecord?.caseId
            // println("pre: ${pre_case_id}   current: ${current_case_id}")
            
            def case_id
            if(!pre_case_id && current_case_id){
                case_id = current_case_id
                linkingCase = true
            }else if(pre_case_id && !current_case_id ){
                case_id = pre_case_id
            }else{
                
            }
            
            if (!candidateRecordInstance.hasErrors() && candidateRecordInstance.save(flush: true)) {
                              
                def filteredCaseCollectionTypeList = filteredCaseCollectionTypeList(org)                 
                
                def resultMap = checkErrors(candidateRecordInstance)

                if(resultMap){
                    resultMap.each{    
                        candidateRecordInstance.errors.rejectValue(it.key, it.value)                
                    }

                    def queryCount = getQueryCount(candidateRecordInstance)
                    render(view: "modify", model: [candidateRecordInstance: candidateRecordInstance, filteredCaseCollectionTypeList:filteredCaseCollectionTypeList, queryCount:queryCount])
                    return [candidateRecordInstance: candidateRecordInstance]            
                }                
                
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
                redirect(action: "modify", id: candidateRecordInstance.id,  params:[needIndex:case_id])
                
                if (linkingCase) {
                    def activityType = ActivityType.findByCode("CASELINK")
                    def caseId = candidateRecordInstance.caseRecord?.caseId
                    def study = candidateRecordInstance.study
                    def bssCode = candidateRecordInstance.bss?.parentBss?.code
                    def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                    def candidateId = candidateRecordInstance.candidateId
                    def identifier = candidateRecordInstance.id
                    activityEventService.createEvent(activityType, caseId, study, bssCode, null, username, candidateId, identifier)
                    
                    //pmh 03/24/14 cdrqa 1079
                    
                    def donorEligibilityGtexInstance = DonorEligibilityGtex.findByCandidateRecord(candidateRecordInstance)
                    if(donorEligibilityGtexInstance && donorEligibilityGtexInstance.humAnimTissueTransplant =="Yes"){
                        
                            //def caseId = candidateRecordInstance.caseRecord?.caseId
                            //def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                            def recipient = AppSetting.findByCode('GTEX_DONELIGQ15YES_DISTRO')?.bigValue
                            def env = "production".equalsIgnoreCase(GrailsUtil.environment) ? "" : " [${GrailsUtil.environment}]"
                            def emailSubject = "CDR Alert:$env Response for Donor Eligibility form  Q15 for ${caseId} is Yes"
                            def emailBody = "Donor Eligibility form  Q15 for  ${caseId} was submitted by ${username}.\n\nReceived a human and/or animal tissue and/or organ transplant or xenotransplant:\t YES\nComments: "+donorEligibilityGtexInstance.tissueTransplantComments
                            sendMailService.sendServiceEmail(recipient, emailSubject, emailBody, 'body')
                           
                    }
                    
                    // end pmh cdrqa 1079
                    
                  
                }
            }
            else {
                render(view: "edit", model: [candidateRecordInstance: candidateRecordInstance,  params:[needIndex:case_id]])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
            redirect(action: "list")
        }
    }

    //We don't want anyone else to delete 
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def candidateRecordInstance = CandidateRecord.get(params.id)
        if (candidateRecordInstance) {
            
            //ensure user is entitled to access this caseRecord instance
            checkAccessPrivilege(candidateRecordInstance)
            
            try {
                candidateRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
            redirect(action: "list")
        }
    }
    
    def linkCandidateToCase = {

        def candidateRecordInstance = CandidateRecord.get(params.id)
        
        if (!candidateRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'candidateRecord.label', default: 'Candidate Record For Candidate'), candidateRecordInstance.candidateId])}"
            redirect(action: "list")
        }
        else {
            //ensure user is entitled to access this caseRecord instance
            def filteredCaseList = filteredCaseList(candidateRecordInstance)

            return [candidateRecordInstance: candidateRecordInstance, filteredCaseList: filteredCaseList]
        }        
                                                                                                                            
    }       

                                                                                                            
    def checkAccessPrivilege(candidateRecordInstance)  {
                
        def bssSubList = bssSubList(org)
        
        def filteredCaseCollectionTypeList = filteredCaseCollectionTypeList(org)
        if ((!filteredCaseCollectionTypeList)||(filteredCaseCollectionTypeList?.size() == 0))
        filteredCaseCollectionTypeList = filteredCaseCollectionTypeList(candidateRecordInstance?.bss)
            
        //println 'CCC 222 filteredCaseCollectionTypeList?.size()'+filteredCaseCollectionTypeList?.size()+', candidateRecordInstance.bss=' +candidateRecordInstance.bss.code + ', String org='+org.code
        
        //Check to see if retrieved instance matches user's organization
        if(candidateRecordInstance.bss.parentBss.code.matches(session.org.code) || session.org.code.matches("OBBR")){  
            //pmh 03/15/13 took way the logic to determine version from the .gsp and have it here
            def  isCaseCurrentCDRVer = false
            
            if(candidateRecordInstance.caseRecord?.cdrVer !=null && ComputeMethods.compareCDRVersion(candidateRecordInstance.caseRecord?.cdrVer, session.appVer) == 0){
           
                isCaseCurrentCDRVer = true
            }
        
            def queryCount = getQueryCount(candidateRecordInstance)
            def qtAttachments = getQtAttachments(candidateRecordInstance)
            return [candidateRecordInstance: candidateRecordInstance, bssSubList: bssSubList, filteredCaseCollectionTypeList:filteredCaseCollectionTypeList,isCaseCurrentCDRVer:isCaseCurrentCDRVer,queryCount:queryCount, qtAttachments:qtAttachments]
        }
        else{
            redirect(controller: "login", action: "denied")
        }                
                
    }    
                                                                                                                       
    def bssSubList(org)  {

        def bss = BSS.findByCode(org.code)
        //get all bss, parent and subs
        def tmpList = []
        if(bss){
            tmpList = BSS.findAllByParentBss(bss)
        }
        else{
            tmpList = BSS.list()
        }
        def bssSubList = []
        
        tmpList.each{
            //strip BSSs that don't have a protocolSiteNum
            if(it.protocolSiteNum){
                bssSubList.add(it)
            }
        }
        return bssSubList
    }            
    
    def filteredCaseList(candidateRecordInstance) {
            
        //set the study from the passed candidateRecord
        def study = candidateRecordInstance.study
            
        def bss = candidateRecordInstance.bss
        //get all bss, parent and subs
        def bssList = BSS.findAllByParentBss(bss.parentBss)
        
        def allCasesList = [] 
        def caseList = []
        
        bssList.each{
            allCasesList.addAll(CaseRecord.findAllByBss(it))
        }
        if(study.code == 'GTEX'){
            //want to scrub out any BMS cases
            allCasesList.removeAll(allCasesList.findAll {it.study.code != 'GTEX'})
        }
        allCasesList.each{
            if(!it.candidateRecord){ //we only want cases that aren't linked yet
                if(it.caseCollectionType == candidateRecordInstance.caseCollectionType){ //we only want cases that match the candidate's collection type
                    caseList.addAll(it)
                }
            }
        }
        
        caseList.sort({a,b-> a.caseId.compareTo(b.caseId)})
            
        return caseList                    
            
    }
                                         
    def filteredCaseCollectionTypeList(org){

        def typeList = nci.obbr.cahub.staticmembers.CaseCollectionType.list()
        def filteredCaseCollectionTypeList = []
        
        def candidateRecordInstance = CandidateRecord.get(params.id)
        //println 'CCC Call checkErrorsCollectionTypeBSS(session.study.code=' +session.study.code + ', String org='+org.code+', String bssCode='+candidateRecordInstance?.bss?.code+')'
        
        if(session.study?.code == 'GTEX'){
            
            if ((candidateRecordInstance?.bss?.code)||(org?.code)) {
           
                def bssCode
                if (candidateRecordInstance?.bss?.code) bssCode = candidateRecordInstance.bss.code
                else bssCode = org.code
                
                def filteredCaseCollectionTypeList2 = []
                typeList.each{
                    
        
                    def ret = checkErrorsCollectionTypeBSS(session.study.code, it.code, bssCode)
                    if (!ret){
                        //println 'CCC Call inserted colType='+it.code
                        filteredCaseCollectionTypeList2.add(it)
                    }
                }
                if ((filteredCaseCollectionTypeList2.size() > 0)&&
                    (filteredCaseCollectionTypeList2.size() < typeList.size())) 
                return filteredCaseCollectionTypeList2
            }
            
            if(org.code != 'NDRI'){
            
                typeList.each{
            
                    if(it.code != 'SURGI'){
                        filteredCaseCollectionTypeList.add(it)
                    }
                }            
            }
            else{
                
                filteredCaseCollectionTypeList = typeList
          
            }            
        }
        else if(session.study?.code == 'BPV'){

            typeList.each{
                            
                def ret
                //println 'CCC Call checkErrorsCollectionTypeBSS(session.study.code=' +session.study.code + ', String colType='+it+', String bssCode='+candidateRecordInstance?.bss?.code+')'
        
                if (candidateRecordInstance?.bss?.code) {
                    ret = checkErrorsCollectionTypeBSS(session.study.code, it.code, candidateRecordInstance.bss.code)
                    if (!ret) filteredCaseCollectionTypeList.add(it)
                }
                else {
                    ret = checkErrorsCollectionTypeBSS(session.study.code, it.code, org.code)
                    if (!ret) filteredCaseCollectionTypeList.add(it)
                }
            }            
        }
        
        else{

            filteredCaseCollectionTypeList = typeList
          
        }

        return filteredCaseCollectionTypeList
        
    }
    
    def getQueryCount(candidateRecordInstance) {
        def activeStatus = QueryStatus.findByCode('ACTIVE')
        def queryCount
        if (session.org?.code == 'OBBR') {
            queryCount = Query.executeQuery("select count(*) from Query i inner join i.candidateRecord c inner join i.queryStatus s where c.id = ? and s.id = ?", [candidateRecordInstance.id, activeStatus.id])
        } else {
            queryCount = Query.executeQuery("select count(*) from Query i inner join i.candidateRecord c inner join i.queryStatus s inner join i.organization o where c.id = ? and s.id = ? and o.code like ?", [candidateRecordInstance.id, activeStatus.id, session.org?.code + "%"])
        }

        return queryCount[0]
    }
    
    def getQueryCountMapCandidate(candidateRecordInstanceList) {
        def queryCountCandidate = [:]
        if (candidateRecordInstanceList) {
            def activeStatus = QueryStatus.findByCode("ACTIVE")
            def countResult
            if (session.org?.code == 'OBBR') {
                countResult= Query.executeQuery("select c.id, count(*) from Query i inner join i.candidateRecord c inner join i.queryStatus s where c in (:list) and s.id = :activeStatus group by c.id",  [list:candidateRecordInstanceList, activeStatus:activeStatus.id])
            } else {
                countResult= Query.executeQuery("select c.id, count(*) from Query i inner join i.candidateRecord c inner join i.queryStatus s inner join i.organization o where c in (:list) and s.id = :activeStatus and o.code like :org group by c.id",  [list:candidateRecordInstanceList, activeStatus:activeStatus.id, org:session.org?.code + "%"])
            }
            countResult.each() {
                queryCountCandidate.put(it[0], it[1]) 
            }
        }

        return queryCountCandidate
    }
    
    def getQtAttachments(candidateRecordInstance) {
        def result = []    
        def queryInstanceList = []

        if (session.org?.code == 'OBBR') {
            queryInstanceList = Query.createCriteria().list {
                createAlias("candidateRecord", "c")
                eq("c.id", candidateRecordInstance.id)
                //isNotNull("queryStatus")
            }
        } else {
            queryInstanceList = Query.createCriteria().list {
                createAlias("candidateRecord", "c")
                eq("c.id", candidateRecordInstance.id)
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
    
    static Map checkErrors(candidateRecordInstance){
        def resultMap = [:]
        
        def bss = BSS.get(candidateRecordInstance.bss.id)
        def colltype = CaseCollectionType.get(candidateRecordInstance.caseCollectionType.id)
        def studyCode = candidateRecordInstance.study?.code
  
        def ret 
        if ((studyCode)&&(bss)&&(colltype)) {
            ret = checkErrorsCollectionTypeBSS(studyCode, colltype.code, bss.code)
            if (ret) resultMap.put("caseCollectionType", ret)
        }
        
        
        /*                                       
        if(colltype.code == "SURGI"){
        if(bss.code != 'NDRI-DUCOM' && bss.code != 'NDRI-AEMC' && bss.code != 'NDRI-VCU' && bss.code != 'UNM' && bss.code != 'VUMC'){
        resultMap.put("caseCollectionType", 'candidateRecord.caseCollectionType.surgicalNotAuthorized')
        }
        }               
        if(colltype.code != "SURGI"){
        if(bss.code == 'NDRI-DUCOM' || bss.code == 'NDRI-AEMC' || bss.code == 'NDRI-VCU'){
        resultMap.put("caseCollectionType", 'candidateRecord.caseCollectionType.surgicalAuthorized')
        }
        } 
         */
        return resultMap
    }
    
    static String checkErrorsCollectionTypeBSS(String study, String colType, String bssCode){
        
        //println 'checkErrorsCollectionTypeBSS(session.study.code=' +study + ', String colType='+colType+', String bssCode='+bssCode+')'
        
        if (study == 'GTEX')
        {
            if (bssCode.startsWith('NDRI-'))
            {
                if(colType == 'SURGI'){
                    if(bssCode != 'NDRI-DUCOM' && bssCode != 'NDRI-AEMC' && bssCode != 'NDRI-VCU'){
                        return 'candidateRecord.caseCollectionType.surgicalNotAuthorized'
                    }
                }               
                if(colType != 'SURGI'){
                    if(bssCode == 'NDRI-DUCOM' || bssCode == 'NDRI-AEMC' || bssCode == 'NDRI-VCU'){
                        return 'candidateRecord.caseCollectionType.surgicalAuthorized'
                    }
                }    
            }
            //else if (bssCode == 'NDRI')
        }
        else if(study == 'BPV')
        {
            if(colType != 'SURGI') return 'candidateRecord.caseCollectionType.surgicalAuthorized'
        }
        return null
    }                                                       
}

