package nci.obbr.cahub

import nci.obbr.cahub.datarecords.CandidateRecord
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.staticmembers.BSS
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.staticmembers.CaseStatus
import nci.obbr.cahub.util.AppSetting
import nci.obbr.cahub.datarecords.ShippingEvent
import nci.obbr.cahub.util.querytracker.Query
import nci.obbr.cahub.staticmembers.QueryStatus
import nci.obbr.cahub.surveyrecords.InterviewRecord
import nci.obbr.cahub.datarecords.ctc.PatientRecord

import groovyx.net.http.HTTPBuilder
import java.text.SimpleDateFormat
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.XML
import static groovyx.net.http.ContentType.TEXT


class homeController {
    def ldaccService
    def prcReportService
    def caseRecordService
    def bpvCaseStatusService
    
    String maxView = AppSetting.findByCode("MAX_COUNT_HOME").value //applies to OBBR user only as of v1.1
    
    static scope = "request"
    
    def cbrinvfeed = { }
    
    def choosehome = {
        session.study = null 
        session.chosenHome = null 
        def blockedStudyList = nihUserStudyAccessCheck()
        
        return [blockedStudyList:blockedStudyList]
    }
    
    def homedispatcher = {

        if(session.serviceAccount == true){
            redirect(controller:"login", action:"notauth")
            return //needed for Grails 2
        }        
        
        def bss = BSS.findByCode(session.org?.code)

        def bpvbsslist = BSS.findAllByStudy(Study.findByCode('BPV'))
        def gtexbsslist = BSS.findAllByStudy(Study.findByCode('GTEX'))
        
        if(bss){

            if(bss in bpvbsslist && session.study?.code != 'BPVELSI' ){
                redirect(action: "bpvbsshome", params: params)
            }

            if(bss in gtexbsslist){
                redirect(action: "gtexbsshome", params: params)
            }
 
            if(session.study?.code == 'BPVELSI'){
                redirect(action: "bpvelsihome", params: params)  
            }                          
            
        }
        
        else if(session.org?.code == 'VARI'){
            redirect(action: "varihome", params: params)     
        }

        else if(session.org?.code == 'BROAD'){
            redirect(action: "broadhome", params: params)     
        }
        else if(session.org?.code == 'MBB'){
            redirect(action: "mbbhome", params: params)     
        }
        
        else{ //Stay here and show all, assuming org is OBBR

            if(session.chosenHome == 'PRC'){
                redirect(action: "prchome", params: params)
            }
            
            else if(session.chosenHome == 'VOCAB'){
                redirect(action: "vocabhome", params: params)
            }
            
            else if(session.chosenHome == 'GTEX'){
                redirect(action: "gtexhome", params: params)
            }            

            else if(session.chosenHome == 'BRN'){
                redirect(action: "brnhome", params: params)
            }            

            else if(session.chosenHome == 'BMS'){
                redirect(action: "bmshome", params: params)
            }            
            
            else if(session.chosenHome == 'BPV'){
                redirect(action: "bpvhome", params: params)
            }            
            
            else if(session.chosenHome == 'BPVELSI'){
                redirect(action: "bpvelsihome", params: params)
            }             
            
            else if(session.chosenHome == 'CTC'){
                redirect(action: "ctchome", params: params)
            }                 
            
            else if(!session.chosenHome || session.serviceAccount != true){
                //redirect(action: "home", params: params)     
                redirect(action: "choosehome", params: params)                
            }
        }
    }
    
    
    def gtexhome = { 
        
        def blockedStudyList = nihUserStudyAccessCheck()
        
        if('GTEX' in blockedStudyList){

            redirect(controller:"login", action:"denied")
        }
        
        else{

        //default GTEX home
        session.setAttribute("chosenHome", new String("GTEX"))
        def study = Study.findByCode("GTEX")
        session.study = study  
        def withdrCase = CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR'))
        def withdrCaseId = []
        withdrCase.each{
            withdrCaseId.add(it.caseId) 
        }
        def candidateRecordInstanceList
        if(withdrCase){
            def c = CandidateRecord.createCriteria()
            candidateRecordInstanceList = c.list{
                eq("study", study) 
                or{

                    not {'in'("caseRecord", withdrCase)}
                    isNull("caseRecord")
                }
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
        }else{
            def c = CandidateRecord.createCriteria()
            candidateRecordInstanceList = c.list{
                eq("study", study) 
                  
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
            
        }
         
        def shippingEventInstanceList
        if(withdrCaseId){
            def c2 = ShippingEvent.createCriteria()
            shippingEventInstanceList = c2.list{
                eq("study", study) 
                or{

                    not {'in'("caseId", withdrCaseId)}
                    isNull("caseId")
                }
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
        }else{
            def c2 = ShippingEvent.createCriteria()
            shippingEventInstanceList = c2.list{
                eq("study", study) 
                  
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
        }
         
        //  return [candidateRecordInstanceList: CandidateRecord.findAllByStudyAndCaseRecordNotInList(Study.findByCode('GTEX'), withdrCase, [max:maxView]),
        //        caseRecordInstanceList: CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode('GTEX'),CaseStatus.findByCode('WITHDR'),[max:maxView]),
        //       shippingEventInstanceList: ShippingEvent.findAllByStudy(Study.findByCode('GTEX'),[max:maxView])]     
         
        def caseRecordInstanceList=CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode('GTEX'),CaseStatus.findByCode('WITHDR'),[max:maxView])
       
        def specimenCount=[:]
        if(caseRecordInstanceList){
            def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: caseRecordInstanceList])
        
            count_result.each(){
                specimenCount.put(it[0], it[1])
             
            }
        }
        
        
        
        def queryCount = getQueryCountMap(caseRecordInstanceList)
        def queryCountCandidate = getQueryCountMapCandidate(candidateRecordInstanceList)
         def deviationCount = getDeviationCountMap(caseRecordInstanceList)
          
        def frozenList = caseRecordService.getFrozenList(caseRecordInstanceList)
        def brainList = caseRecordService.getBrainList(caseRecordInstanceList)  
        
        return [candidateRecordInstanceList: candidateRecordInstanceList,
            caseRecordInstanceList: caseRecordInstanceList,
            shippingEventInstanceList: shippingEventInstanceList, specimenCount:specimenCount, queryCount:queryCount,deviationCount:deviationCount, queryCountCandidate:queryCountCandidate,frozenList:frozenList,brainList:brainList]            
        
      }
    }    
    
    def gtexbsshome = {
        
        def bss = BSS.findByCode(session.org.code)
        //get all bss, parent and subs
        def bssList = BSS.findAllByParentBss(bss)
        
        def candidateList = []
        def allCasesList = [] 
        def gtexCaseList = []
        def bmsCaseList = []
        
        def sortedGtexCaseList = []
        def sortedBmsCaseList = []
        def sortedCandidateList = []

        /** bssList.each{
        candidateList.addAll(CandidateRecord.findAllByBss(it))
        allCasesList.addAll(CaseRecord.findAllByBss(it))

        }**/
        
        def withdrCase = CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR'))
        if(withdrCase){
            def c = CandidateRecord.createCriteria()
            sortedCandidateList=c.list{
                inList("bss", bssList)
                or{

                    not {'in'("caseRecord", withdrCase)}
                    isNull("caseRecord")
                }
                maxResults(5)
                order("dateCreated", "desc")

            }
        }else{
            def c = CandidateRecord.createCriteria()
            sortedCandidateList=c.list{
                inList("bss", bssList)
                 
                maxResults(5)
                order("dateCreated", "desc")

            }
        }
        
        /** allCasesList=CaseRecord.findAllByBssInList(bssList)
        
        bmsCaseList.addAll(allCasesList.findAll {it.study.code == 'BMS' && it.caseStatus.code !='WITHDR' })
        gtexCaseList.addAll(allCasesList.findAll {it.study.code == 'GTEX' && it.caseStatus.code !='WITHDR'})
            
        def sortedGtexCaseList = []
        def sortedBmsCaseList = []
        def sortedCandidateList = []
        
        sortedGtexCaseList = gtexCaseList.sort({a,b-> a.dateCreated.compareTo(b.dateCreated)}).reverse()
        sortedBmsCaseList = bmsCaseList.sort({a,b-> a.dateCreated.compareTo(b.dateCreated)}).reverse()
        sortedCandidateList = candidateList.sort({a,b-> a.dateCreated.compareTo(b.dateCreated)}).reverse()

        if(sortedGtexCaseList.size() > 5){
        sortedGtexCaseList = sortedGtexCaseList[0..5]
        }
        
        if(sortedBmsCaseList.size() > 5){
        sortedBmsCaseList = sortedBmsCaseList[0..5]
        }        
        
        if(sortedCandidateList.size() > 5){
        sortedCandidateList = sortedCandidateList[0..5]        
        }**/
        
        def c2 = CaseRecord.createCriteria()
        sortedGtexCaseList=c2.list{
            eq("study", Study.findByCode('GTEX'))
            ne("caseStatus", CaseStatus.findByCode('WITHDR') )
            inList("bss", bssList)
            maxResults(5)
            order("dateCreated", "desc")

        }
            

        def c3 = CaseRecord.createCriteria()
        sortedBmsCaseList=c3.list{
            eq("study", Study.findByCode('BMS'))
            ne("caseStatus", CaseStatus.findByCode('WITHDR') )
            inList("bss", bssList)
            maxResults(5)
            order("dateCreated", "desc")

        }
         
        def specimenCount=[:]
        if(sortedGtexCaseList){
            def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: sortedGtexCaseList])
        
            count_result.each(){
                specimenCount.put(it[0], it[1])
             
            }
        }
        def specimenCountBMS=[:]
        
        if(sortedBmsCaseList){
            def count_result_bms = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: sortedBmsCaseList])
    
            count_result_bms.each(){
                specimenCountBMS.put(it[0], it[1])
             
            }
            
        }
        
        def queryCount = getQueryCountMap(sortedGtexCaseList)
        queryCount.putAll(getQueryCountMap(sortedBmsCaseList))
        def queryCountCandidate = getQueryCountMapCandidate(sortedCandidateList)
        
        def frozenList = caseRecordService.getFrozenList(sortedGtexCaseList)
        def brainList = caseRecordService.getBrainList(sortedGtexCaseList)       
        
        return [candidateRecordInstanceList: sortedCandidateList, gtexCaseList: sortedGtexCaseList, bmsCaseList: sortedBmsCaseList, specimenCount:specimenCount, specimenCountBMS:specimenCountBMS, queryCount:queryCount, queryCountCandidate:queryCountCandidate, frozenList:frozenList, brainList:brainList]
        
    }

    def bpvbsshome = {
        session.setAttribute("chosenHome", new String("BPV"))
        def study = Study.findByCode("BPV")
        session.study = study   
        
        def bss = BSS.findByCode(session.org.code)
        //get all bss, parent and subs
        def bssList = BSS.findAllByParentBss(bss)
        
        def candidateList = []
        def allCasesList = [] 
        def bpvCaseList = []

        def sortedBpvCaseList = []
        def sortedCandidateList = []
        
        
        /**bssList.each{
        candidateList.addAll(CandidateRecord.findAllByBss(it))
        allCasesList.addAll(CaseRecord.findAllByBss(it))

        }**/
        
        def withdrCase = CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR'))
        if(withdrCase){
            def c = CandidateRecord.createCriteria()
            sortedCandidateList=c.list{
                eq("study", study) 
                inList("bss", bssList)
                or{
                   
                    not {'in'("caseRecord", withdrCase)}
                    isNull("caseRecord")
                }
                maxResults(5)
                order("dateCreated", "desc")

            }
        }else{
            def c = CandidateRecord.createCriteria()
            sortedCandidateList=c.list{
                eq("study", study) 
                inList("bss", bssList)
                maxResults(5)
                order("dateCreated", "desc")

            }
            
        }
        
        /**  allCasesList=CaseRecord.findAllByBssInList(bssList)
        
        bpvCaseList.addAll(allCasesList.findAll {it.study.code == 'BPV'&& it.caseStatus.code !='WITHDR'})
            
        def sortedBpvCaseList = []
        def sortedCandidateList = []
        
        sortedBpvCaseList = bpvCaseList.sort({a,b-> a.dateCreated.compareTo(b.dateCreated)}).reverse()
        sortedCandidateList = candidateList.sort({a,b-> a.dateCreated.compareTo(b.dateCreated)}).reverse()

        
        if(sortedBpvCaseList.size() > 5){
        sortedBpvCaseList = sortedBpvCaseList[0..5]
        }        
        
        if(sortedCandidateList.size() > 5){
        sortedCandidateList = sortedCandidateList[0..5]        
        }**/
        
        
        def c2 = CaseRecord.createCriteria()
        sortedBpvCaseList=c2.list{
            eq("study", study) 
            ne("caseStatus", CaseStatus.findByCode('WITHDR') )
            inList("bss", bssList)
            maxResults(5)
            order("dateCreated", "desc")

        }
        
        def specimenCount=[:]
        //pmh CDRQA 1104 04/03/14
        def sixMonthFollowUpStat=[:]
        
        if(sortedBpvCaseList){
            def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: sortedBpvCaseList])
        
            count_result.each(){
                specimenCount.put(it[0], it[1])
             
            }
            sixMonthFollowUpStat = bpvCaseStatusService.getSixMonthFollowUpStatus(sortedBpvCaseList)
        }
        
        def queryCount = getQueryCountMap(sortedBpvCaseList)
        def queryCountCandidate = getQueryCountMapCandidate(sortedCandidateList)
        
        return [candidateRecordInstanceList: sortedCandidateList, caseRecordInstanceList: sortedBpvCaseList, specimenCount:specimenCount, queryCount:queryCount, queryCountCandidate:queryCountCandidate,sixMonthFollowUpStat:sixMonthFollowUpStat]
        
    }    

    def bpvelsihome = {
        def interviewList
        def interviewTotal
        
        Date searchFromDt 
        Date searchToDt   
        
        
        if(session.org.code == 'OBBR' || session.authorities?.contains("ROLE_BPV_ELSI_DA")){
            session.setAttribute("chosenHome", new String("BPVELSI"))
            def study = Study.findByCode("BPVELSI")
            session.study = study           
            
            params.max = Math.min(params.max ? params.int('max') : 25, 100)
            
            if (params.id) {
                [interviewRecordInstanceList: InterviewRecord.get(params.id), interviewRecordInstanceTotal: 1]
            } else {
                if (params.bssId) {
                    [interviewRecordInstanceList: InterviewRecord.findAllByOrgCode(params.bssId), interviewRecordInstanceTotal: InterviewRecord.findAllByInterviewStatus(params.intrStatus).size()]
                } else {
                    if (params.intrStatus) {
                        [interviewRecordInstanceList: InterviewRecord.findAllByInterviewStatus(params.intrStatus), interviewRecordInstanceTotal: InterviewRecord.findAllByInterviewStatus(params.intrStatus).size()]
                    } else {
                        if (params.survStatus) {
                            
                            if (params.survStatus.equals("COMP")) {
                                interviewList = InterviewRecord.executeQuery("Select i from SurveyRecord s inner join s.interviewRecord i where s.dateSubmitted is not null")
//                                println "COMP  interviewList.size() " + interviewList.size()
                            } else {
                                if (params.survStatus == "INPROG") {
                                    interviewList = InterviewRecord.executeQuery("Select i from SurveyRecord s inner join s.interviewRecord i where s.dateSubmitted is  null")
//                                    println "INPROG interviewList.size() " + interviewList.size()
                                } else {
                                    log.error ("Bad programming! params.survStatus = " + params.survStatus)
                                }
                            }
                            
                            interviewTotal = interviewList.size()
                            [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewTotal]
                        } else {
                            if (params.crfStatus){
                                
                                if (params.crfStatus.equals("COMP")) {
                                    interviewList = InterviewRecord.executeQuery("Select i from BpvElsiCrf crf inner join crf.interviewRecord i where crf.dateSubmitted is not null")
                                } else {
                                    if (params.crfStatus == "INPROG") {
                                        interviewList = InterviewRecord.executeQuery("Select i from BpvElsiCrf crf inner join crf.interviewRecord i where crf.dateSubmitted is  null")
                                    } else {
                                        log.error ("Bad programming! params.crfStatus = " + params.crfStatus)
                                    }
                                }
                                
                                interviewTotal = interviewList.size()
                                [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewTotal]
                            } else {
                                if (params.searchFromDate || params.searchToDate) {

                                    if (params.searchFromDate && params.searchFromDate != "Select Date") {
                                        searchFromDt = new SimpleDateFormat("MM/dd/yyyy").parse(params.searchFromDate)
                                    } else {
                                        searchFromDt = new SimpleDateFormat("MM/dd/yyyy").parse("08/01/2013")
                                    }
                                    if (params.searchToDate && params.searchToDate != "Select Date") {
                                        searchToDt = new SimpleDateFormat("MM/dd/yyyy").parse(params.searchToDate)
                                    } else {
                                        searchToDt = new Date()
                                    }

                                    if (searchFromDt > searchToDt) {
                                        flash.message = "From Date: " + params.searchFromDate + " cannot be greater than To Date: " + params.searchToDate
                                        [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewList?.count()]
                                    } else {
                                        interviewList = InterviewRecord.executeQuery("Select i from BpvElsiCrf crf left join crf.interviewRecord i where i.dateCreated >= :fromDt and i.dateCreated <= :toDt", [fromDt: searchFromDt, toDt: searchToDt])
                                        interviewTotal = interviewList.size()
                                        [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewTotal]
                                    }
                                } else {
                                    [interviewRecordInstanceList: InterviewRecord.list(params), interviewRecordInstanceTotal: InterviewRecord.count()]
                                }
                            }
                        }
                    }
                }
            }
        }
    
        else{
            //BSS
            def study = Study.findByCode("BPVELSI")
            session.study = study        
            
            params.max = Math.min(params.max ? params.int('max') : 25, 100)
            if (params.id) {
                interviewList = InterviewRecord.get(params.id)
                if (interviewList.orgCode.equals(session.org?.code)){
                    interviewTotal = 1
                } else {
                    flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'InterviewRecord.label', default: 'BPV ELSI Interview '), params.id])}"
                    interviewList = null
                    interviewTotal = 0
                }
                [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewTotal]
            } else {
                if (params.bssId) {
                    /* bss can only list their BSS, so this is a no-op */
                } else {
                    if (params.intrStatus) {
                        [interviewRecordInstanceList: InterviewRecord.findAllByInterviewStatusAndOrgCode(params.intrStatus, session.org?.code), interviewRecordInstanceTotal: InterviewRecord.findAllByInterviewStatus(params.intrStatus).size()]
                    } else {
                        if (params.survStatus) {
                            if (params.survStatus.equals("COMP")) {
                                interviewList = InterviewRecord.executeQuery("Select i from SurveyRecord s inner join s.interviewRecord i where s.dateSubmitted is not null and i.orgCode = :org", [org: session.org?.code])
                                println "COMP  interviewList.size() " + interviewList.size()
                            } else {
                                if (params.survStatus == "INPROG") {
                                    interviewList = InterviewRecord.executeQuery("Select i from SurveyRecord s inner join s.interviewRecord i where s.dateSubmitted is  null and i.orgCode = :org", [org: session.org?.code])
                                    println "INPROG interviewList.size() " + interviewList.size()
                                } else {
                                    log.error ("Bad programming! params.survStatus = " + params.survStatus)
                                }
                            }
                            
                            interviewTotal = interviewList.size()
                            [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewTotal]
                            
                        } else {
                            if (params.crfStatus) {
                                if (params.crfStatus.equals("COMP")) {
                                    interviewList = InterviewRecord.executeQuery("Select i from BpvElsiCrf crf inner join crf.interviewRecord i where crf.dateSubmitted is not null and i.orgCode = :org", [org: session.org?.code])
                                } else {
                                    if (params.crfStatus == "INPROG") {
                                        interviewList = InterviewRecord.executeQuery("Select i from BpvElsiCrf crf inner join crf.interviewRecord i where crf.dateSubmitted is  null and i.orgCode = :org", [org: session.org?.code])
                                    } else {
                                        log.error ("Bad programming! params.crfStatus = " + params.crfStatus)
                                    }
                                }
                                
                                interviewTotal = interviewList.size()
                                [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewTotal]
                            } else {
                                if (params.searchFromDate || params.searchToDate) {
                                    if (params.searchFromDate && params.searchFromDate != "Select Date") {
                                        searchFromDt = new SimpleDateFormat("MM/dd/yyyy").parse(params.searchFromDate)
                                    } else {
                                        searchFromDt = new SimpleDateFormat("MM/dd/yyyy").parse("08/01/2013")
                                    }
                                    if (params.searchToDate && params.searchToDate != "Select Date") {
                                        searchToDt = new SimpleDateFormat("MM/dd/yyyy").parse(params.searchToDate)
                                    } else {
                                        searchToDt = new Date()
                                    }

                                    if (searchFromDt > searchToDt) {
                                        flash.message = "From Date: " + params.searchFromDate + " cannot be greater than To Date: " + params.searchToDate
                                        [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewList?.count()]
                                    } else {
                                        interviewList = InterviewRecord.executeQuery("Select i from BpvElsiCrf crf left join crf.interviewRecord i where i.dateCreated >= :fromDt and i.dateCreated <= :toDt and i.orgCode = :org", [fromDt: searchFromDt, toDt: searchToDt, org: session.org?.code])
                                        interviewTotal = interviewList.size()
                                        [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewTotal]
                                    }
                                } else {
                                    interviewList = InterviewRecord.createCriteria().list(params) {
                                        eq("orgCode", session.org?.code)
                                    }
                                    interviewTotal = interviewList.size() /* InterviewRecord.findAllByOrgCode(session.org?.code)?.size() */
                                    [interviewRecordInstanceList: interviewList, interviewRecordInstanceTotal: interviewTotal]
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    def brnhome = {
        
        def blockedStudyList = nihUserStudyAccessCheck()
        
        if('BPV' in blockedStudyList){

            redirect(controller:"login", action:"denied")
        }
        
        else{        
        
        session.setAttribute("chosenHome", new String("BRN"))        
        def study = Study.findByCode("BRN")
        session.study = study        
        def caseList=[]
        def shipList=[]
        def all = CaseRecord.list([max:maxView])
        caseList.addAll(CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode('BRN'), CaseStatus.findByCode('WITHDR'), [max:maxView]))
        //shipList.addAll(ShippingEvent.findAllByStudy(Study.findByCode('BRN'),[max:maxView]))
        
        def withdrCase = CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR'))
        def withdrCaseId = []
        withdrCase.each{
            withdrCaseId.add(it.caseId) 
        }
        
        if(withdrCaseId){
            def c2 = ShippingEvent.createCriteria()
            shipList = c2.list{
                eq("study", study) 
                or{
                   
                    not {'in'("caseId", withdrCaseId)}
                    isNull("caseId")
                }
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
        }else{
            def c2 = ShippingEvent.createCriteria()
            shipList = c2.list{
                eq("study", study) 
               
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
            
        }
        
        
        def specimenCount=[:]
        
        if(caseList){
            def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: caseList])
        
            count_result.each(){
                specimenCount.put(it[0], it[1])
             
            }
        }
        return [caseRecordInstanceList:caseList,
            shippingEventInstanceList:shipList, specimenCount:specimenCount]
        
        }
    }    


    def bpvhome = {
        
        def blockedStudyList = nihUserStudyAccessCheck()
        
        if('BPV' in blockedStudyList){

            redirect(controller:"login", action:"denied")
        }
        
        else{        
        
        session.setAttribute("chosenHome", new String("BPV"))        
        def study = Study.findByCode("BPV")
        session.study = study        
        def caseList=[]
        def shipList=[]
        def candList=[]
        
        def withdrCase = CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR'))
        def withdrCaseId = []
        withdrCase.each{
            withdrCaseId.add(it.caseId) 
        }
        if(withdrCase){
            def c = CandidateRecord.createCriteria()
            candList = c.list{
                eq("study", study) 
                or{
                   
                    not {'in'("caseRecord", withdrCase)}
                    isNull("caseRecord")
                }
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
        }else{
            def c = CandidateRecord.createCriteria()
            candList = c.list{
                eq("study", study) 
              
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
        }
        
        if(withdrCaseId){
            
            def c2 = ShippingEvent.createCriteria()
            shipList = c2.list{
                eq("study", study) 
                or{

                    not {'in'("caseId", withdrCaseId)}
                    isNull("caseId")
                }
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
        }else{
            def c2 = ShippingEvent.createCriteria()
            shipList = c2.list{
                eq("study", study) 

                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
        }
        //  def all = CaseRecord.list([max:maxView])
        //   caseList.addAll(CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode('BPV'),CaseStatus.findByCode('WITHDR'), [max:maxView]))
        //shipList.addAll(ShippingEvent.findAllByStudy(Study.findByCode('BPV'),[max:maxView]))
        //candList.addAll(CandidateRecord.findAllByStudy(Study.findByCode('BPV'),[max:maxView]))
        
        caseList = CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode('BPV'),CaseStatus.findByCode('WITHDR'), [max:maxView])
        def specimenCount=[:]
        
        //pmh CDRQA 1104 04/03/14
        def sixMonthFollowUpStat=[:]
        if(caseList){
        
            def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: caseList])
        
            count_result.each(){
                specimenCount.put(it[0], it[1])
             
            }
            
                //pmh CDRQA 1104 04/03/14
                sixMonthFollowUpStat = bpvCaseStatusService.getSixMonthFollowUpStatus(caseList)
        }
        
        def queryCount = getQueryCountMap(caseList)
        def deviationCount = getDeviationCountMap(caseList)
        def queryCountCandidate = getQueryCountMapCandidate(candList)
        
        return [caseRecordInstanceList:caseList,
            shippingEventInstanceList:shipList,
            candidateRecordInstanceList:candList, specimenCount:specimenCount, queryCount:queryCount, deviationCount:deviationCount, queryCountCandidate:queryCountCandidate,sixMonthFollowUpStat:sixMonthFollowUpStat]
        }
    }        

    
    def prchome = {
        session.setAttribute("chosenHome", new String("PRC"))
        def caseList=[]
        def all = CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode('GTEX'),CaseStatus.findByCode('WITHDR'),[max:20])
        // all.each(){
        //  def map=prcReportService.getPrcCaseMap(it)
        //  caseList.add(map)
        // }
        caseList = prcReportService.getPrcCaseMaps(all)
        
        /** def caseListBms=[]
        def allBms=CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode('BMS'),CaseStatus.findByCode('WITHDR'),[max:maxView])
        allBms.each(){
        def map=prcReportService.getPrcCaseMapBms(it)
        caseListBms.add(map)
        }
        
        def caseListBrn=[]     
        def allBrn=CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode('BRN'),CaseStatus.findByCode('WITHDR'),[max:maxView])
        allBrn.each(){
        def map=prcReportService.getPrcCaseMapBrn(it)
        caseListBrn.add(map)
        }**/
     
        
        
        def caseListBpv=[]
        //def =CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode('BPV'),CaseStatus.findByCode('WITHDR'),[max:maxView])
        def allBpv = CaseRecord.executeQuery("from CaseRecord c where c.study.code='BPV' and c.caseStatus.code  != 'WITHDR' and c.hasLocalPathReview = 1 order by c.dateCreated desc", [max:20])
        //allBpv.each(){
        //   def map=prcReportService.getPrcCaseMapBpv(it)
        //  caseListBpv.add(map)
        // }
        
        caseListBpv=prcReportService.getPrcCaseMapsBpv(allBpv)
        
        return [caseList:caseList,  caseListBpv:caseListBpv,  showBRN:'Y']
        
    }

    def varihome = {
        //get study from request or session
        def pStudyCode = params.studyCode ?: session.study?.code

        //if null, default to GTEX
        pStudyCode = pStudyCode ?: 'GTEX'

        //put study obj in session
        def study = Study.findByCode(pStudyCode)
        session.study = study
        // println("before count: " + new Date())
        //def count = CaseRecord.findAllByStudyAndCaseStatusNotEqual(session.study, CaseStatus.findByCode('WITHDR')).size()
        //  def count = CaseRecord.countByStudyAndCaseStatusNotEqual(session.study, CaseStatus.findByCode('WITHDR'))
        def count = CaseRecord.countByStudy(session.study, CaseStatus.findByCode('WITHDR'))
      
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        // println("before list" + new Date())
        // def caseList = CaseRecord.findAllByStudyAndCaseStatusNotEqual(session.study,CaseStatus.findByCode('WITHDR'), params)
        def caseList = CaseRecord.findAllByStudy(session.study, params)
        //  println("after list" + new Date())
        
        def specimenCount=[:] 
        if(caseList){ 
            def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: caseList])
        
            count_result.each(){
                specimenCount.put(it[0], it[1])
             
            }
        }
       
        def queryCount = getQueryCountMap(caseList)
        
        return [caseRecordInstanceList: caseList, caseRecordInstanceTotal: count, selectedStudy:session.study.code, specimenCount:specimenCount, queryCount:queryCount]
    }

    def broadhome = {
        //get study from request or session
        def pStudyCode = params.studyCode ?: session.study?.code

        //if null, default to GTEX
        pStudyCode = pStudyCode ?: 'GTEX'

        def study = Study.findByCode(pStudyCode)
        session.study = study

        //def count = CaseRecord.findAllByStudyAndCaseStatusNotEqual(session.study, CaseStatus.findByCode('WITHDR')).size()
        def count = CaseRecord.countByStudyAndCaseStatusNotEqual(session.study, CaseStatus.findByCode('WITHDR'))
        
       
        
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        def caseList = CaseRecord.findAllByStudyAndCaseStatusNotEqual(session.study, CaseStatus.findByCode('WITHDR'), params)
        
        def specimenCount=[:]
        
        if(caseList){
            def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: caseList])
         
            count_result.each(){
                specimenCount.put(it[0], it[1])
             
            }
        }
        
        def queryCount = getQueryCountMap(caseList)
        
        return [caseRecordInstanceList: caseList, caseRecordInstanceTotal: count, selectedStudy:session.study.code, specimenCount:specimenCount, queryCount:queryCount]
    }

    def cdrarhome = {
        
       
    }
    
    def vocabhome = {
        session.setAttribute("chosenHome", new String("VOCAB"))
        
    }
    
    
    
    def bmshome = { 

        def blockedStudyList = nihUserStudyAccessCheck()
        
        if('GTEX' in blockedStudyList){

            redirect(controller:"login", action:"denied")
        }
        
        else{        
        
        session.setAttribute("chosenHome", new String("BMS"))
        def study = Study.findByCode("BMS")
        session.study = study        
        
        //  return [caseRecordInstanceList: CaseRecord.findAllByStudy(Study.findByCode('BMS'),[max:maxView]),
        //   shippingEventInstanceList: ShippingEvent.findAllByStudy(Study.findByCode('BMS'),[max:maxView])] 
             
        def withdrCase = CaseRecord.findAllByCaseStatus(CaseStatus.findByCode('WITHDR'))
        def withdrCaseId = []
        withdrCase.each{
            withdrCaseId.add(it.caseId) 
        }
        def shipList
        if(withdrCaseId){    
            def c2 = ShippingEvent.createCriteria()
            shipList = c2.list{
                eq("study", study) 
                or{

                    not {'in'("caseId", withdrCaseId)}
                    isNull("caseId")
                }
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
        }else{
            def c2 = ShippingEvent.createCriteria()
            shipList = c2.list{
                eq("study", study) 
                  
                maxResults(Integer.parseInt(maxView))
                order("dateCreated", "desc")


            }
        }
        
        def caseList = CaseRecord.findAllByStudyAndCaseStatusNotEqual(Study.findByCode('BMS'),CaseStatus.findByCode('WITHDR'),[max:maxView])
        
        def specimenCount=[:]
        
        if(caseList){
            def count_result = SpecimenRecord.executeQuery("select c.id, count(*) from SpecimenRecord s inner join s.caseRecord c where c in (:list) group by c.id",  [list: caseList])
         
            count_result.each(){
                specimenCount.put(it[0], it[1])
             
            }
        } 
        
        def queryCount = getQueryCountMap(caseList)
        
        return [caseRecordInstanceList: caseList,
            shippingEventInstanceList: shipList, specimenCountBMS:specimenCount, queryCount:queryCount]  
        }
    }  
    
    def opshome = {
        
        session.setAttribute("chosenHome", new String("OPS"))
    }
    
    def mbbhome = {
        //get study from request or session
        def pStudyCode = params.studyCode ?: session.study?.code
        
        //if null, default to GTEX
        pStudyCode = pStudyCode ?: 'GTEX'

        def study = Study.findByCode(pStudyCode)
        session.study = study
        
        

        // def result_count = SpecimenRecord.executeQuery("select distinct c from SpecimenRecord s inner join s.caseRecord c where s.brainTimeStartRemoval is not null and c.caseStatus.code  != 'WITHDR' order by c.dateCreated desc")
        // def count = result_count.size() 
        
        def count_arr = SpecimenRecord.executeQuery("select count(distinct c) from SpecimenRecord s inner join s.caseRecord c where s.tissueType.code='BRAIN' and c.caseStatus.code  != 'WITHDR' order by c.dateCreated desc")
        
        def count
        if(count_arr)
        count=count_arr.get(0)
        
        //def count = CaseRecord.findAllByStudyAndCaseStatusNotEqual(session.study, CaseStatus.findByCode('WITHDR')).size()
        // params.max = Math.min(params.max ? params.int('max') : 25, 100)
        // def caseList = CaseRecord.findAllByStudyAndCaseStatusNotEqual(session.study, CaseStatus.findByCode('WITHDR'), params)

        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        def max=params.max
        def offset=params.offset
        if(!offset)
        offset=0
        def caseList = SpecimenRecord.executeQuery("select distinct c from SpecimenRecord s inner join s.caseRecord c where s.tissueType.code='BRAIN' and c.caseStatus.code  != 'WITHDR' order by c.dateCreated desc", [max:max,offset:offset])
        def sermap =[:]
        caseList.each{
            
            def key=it.caseId 
            def list = prcReportService.getSerologyList2(it)
            def val=''
            if(list)
            val=list.join(',')
                          
            sermap.put(key, val)
        }
       
        def queryCount = getQueryCountMap(caseList)
        
        return [caseRecordInstanceList: caseList, caseRecordInstanceTotal: count, selectedStudy:session.study.code, sermap:sermap, queryCount:queryCount]
    }
    
    def more = {
        
        
        
    }  
    
    def ctchome = {
         def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase() 
          if (!session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB') && !session.authorities.contains('ROLE_ADMIN') &&  !AppSetting.findByCode('CTC_USER_LIST').bigValue.split(',').contains(username)) {
                redirect(controller: "login", action: "denied")
                return
         }
        
        def blockedStudyList = nihUserStudyAccessCheck()
        
        if('CTC' in blockedStudyList){

            redirect(controller:"login", action:"denied")
        }
        
        else{        
        
        session.setAttribute("chosenHome", new String("CTC"))
        def study = Study.findByCode("CTC")
        session.study = study  
        
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        def caseRecordInstanceList = CaseRecord.createCriteria().list(params) {
            eq("study", Study.findByCode('CTC'))
        }
        def caseRecordInstanceTotal = caseRecordInstanceList.size()
        
        // pmh CTC patient list 11/06/13
        // def patientRecordList=PatientRecord.list(params)
        def patientRecordList = PatientRecord.createCriteria().list{                               
            maxResults(Integer.parseInt("25"))
            order("dateCreated", "desc")

        }
        
        def patientRecordTotal = patientRecordList.size()
        
       // def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername().toLowerCase()
        
        [caseRecordInstanceList: caseRecordInstanceList, caseRecordInstanceTotal: caseRecordInstanceTotal,patientRecordList:patientRecordList,patientRecordTotal:patientRecordTotal,username:username]         
        
      }
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
    
    def nihUserStudyAccessCheck() {

        def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        
        def blockedStudyList = []
        
        def gtexDenyList = AppSetting.findByCode('NIH_USER_DENY_GTEX').bigValue?.split(',')
        def bpvDenyList = AppSetting.findByCode('NIH_USER_DENY_BPV').bigValue?.split(',')
        def ctcDenyList = AppSetting.findByCode('NIH_USER_DENY_CTC').bigValue?.split(',')

        if(username in gtexDenyList){
            blockedStudyList.add('GTEX')
        }
        if(username in bpvDenyList){
            blockedStudyList.add('BPV')
        }
        if(username in ctcDenyList){
            blockedStudyList.add('CTC')
        }
            
        return blockedStudyList        
    }
    
}
