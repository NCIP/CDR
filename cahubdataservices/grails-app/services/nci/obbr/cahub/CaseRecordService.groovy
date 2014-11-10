package nci.obbr.cahub

import groovy.xml.XmlUtil
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException
import grails.converters.deep.*
import groovyx.net.http.*
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.XML
import static groovyx.net.http.ContentType.TEXT


import groovy.xml.StreamingMarkupBuilder

import nci.obbr.cahub.util.AppSetting
import nci.obbr.cahub.util.ActivityEvent

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.ActivityType


class CaseRecordService {

    static transactional = false

    def utilService
    def sendMailService
    def bpvWorkSheetService
    def activityEventService

    def emailBpvBSSQACompleteCase(caseRecordInstance) {
        if("BSSQACOMP".equalsIgnoreCase(caseRecordInstance?.caseStatus?.code) && "BPV".equalsIgnoreCase(caseRecordInstance?.study?.code)) {
        //&& !ActivityEvent.findByCaseIdAndActivityType(caseRecordInstance.caseId,ActivityType.findByCode('EMAIL'))) {
            initCaseEmail(caseRecordInstance)
        }
    }
    
    def emailGTExBSSQACompleteCase(caseRecordInstance) {
//        println "caseRecordInstance.caseId: " + caseRecordInstance.caseId
        def cdrARlink = AppSetting.findByCode('CDRAR_HOSTNAME')?.value
//        println "cdrARlink: " + cdrARlink
        def http = new HTTPBuilder(cdrARlink)

        def sepsisFlag 
        def emailString

        http.get( path: '/cahubanalytics/caseReportFormChecks/sepsisCheck/'+caseRecordInstance.caseId) { response, json ->
            sepsisFlag = json
        }
        if (sepsisFlag =~ "fail"){
//            println "Sepsis report:"
            emailString = "The Case status for " + caseRecordInstance.caseId + " has been changed to BSSQACOMP. \n" +
            "A response on the eligibility or medical history form indicates sepsis. \n" +
            "Please review the link below: \n" +
            cdrARlink+"/cahubanalytics/report/sepsisView/"+caseRecordInstance.caseId
            activityEventService.createEvent(ActivityType.findByCode("SEPSIS"), caseRecordInstance.caseId, caseRecordInstance.study, caseRecordInstance.bss.name, null, utilService.getCurrentUsername(), emailString, null)
        }
        
    }

    def initCaseEmail(caseRecordInstance) {
        if(caseRecordInstance) {            
            def result = sendCaseRecordCBR(caseRecordInstance.caseId, caseRecordInstance.study?.code)
            if (result!=202) {
                emailCaseRecord(caseRecordInstance.caseId, caseRecordInstance.study?.code)
            }
            activityEventService.createEvent(ActivityType.findByCode("EMAIL"), caseRecordInstance.caseId, caseRecordInstance.study, caseRecordInstance.bss?.parentBss?.code, null, utilService.getCurrentUsername(), null, null)
        }
    }

    def emailCaseRecord(caseId, studyCode) {
        def content = XmlUtil.serialize(utilService.invokeWebService(null, "/rest/caserecord", [caseid: caseId]).trim())
        
        def recipient = AppSetting.findByCode('CBR_COLLECTION_EMAIL')?.value
        sendMailService.sendServiceEmailWithAttachment(recipient, "${studyCode} Case data for " +caseId, content, caseId)
        return [msg:'success']
    }
    
    def sendCaseRecordCBR(caseId, studyCode) {
    def brimsService = AppSetting.findByCode('CBR_SERVICE_URL')?.bigValue
    def brimsServiceAuth = AppSetting.findByCode('CBR_SERVICE_AUTH')?.bigValue
    def newPost = new HTTPBuilder(brimsService + AppSetting.findByCode('CBR_COLLECTION_SERVICE_ENDPOINT')?.bigValue)
    def caseRecordXml = getCaseXMLRecord(caseId)
    def result
    try {
    newPost.request(Method.POST, ContentType.XML) { 
            request.addHeader("Authorization", brimsServiceAuth)
            //request.addHeader("Authorization", "Basic Y2RyLnNlcnZpY2U6bmV4dCBlbWFpbA==")
            request.addHeader("Content-Type","application/xml")
            body = caseRecordXml
            response.success = { resp ->
                result = resp.status
            }
            response.failure = { resp ->
                result = resp.status
            }
        }
    } catch (HttpResponseException hre) {
                    hre.printStackTrace()
        }
      /*      if (result==202) {
                render "Case record "+params.caseid+" posted to BRIMS successfully"
            } else {
                render "Case record "+params.caseid+" POST to BRIMS failed. Error code "+result
            } */
        return result
    }    

    def getCaseXMLRecord(caseid) {
        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"

        def xmlDocument = builder.bind {
            mkp.xmlDeclaration()
            if(caseid) {
                def c = CaseRecord.findByCaseId(caseid?.toUpperCase())
                if(c) {
                    def session = utilService.getSession()
                    if(c.bss.parentBss.code.matches(session.org.code) || session.org.code.matches("OBBR") || session.org.code.matches("VARI") ||
                        (session.org.code.matches("BROAD") && (c.caseStatus?.code == 'RELE' || c.caseStatus?.code == 'COMP') && (c.study.code == 'GTEX' || c.study.code == 'BMS'))) {
                        def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()

                        def altMap=[:]
                        if(c.study.code == 'BPV') {
                            altMap = bpvWorkSheetService. getPriority(c)
                        }

                        caseRecord(id:c.id) {
                            caseId(c.caseId)
                            parentCaseId(c.parentCase?.caseId)
                            study(code:c.study.code, c.study.name)
                            caseStatus(code:c.caseStatus.code, c.caseStatus.name)

                            if(session.org?.code != 'BROAD' && username !='ldaccservice') {
                                bss(code:c.bss.code, c.bss.name)
                            }

                            primaryOrgan(code:c.primaryTissueType?.code, c.primaryTissueType)
                            dateCreated(c.dateCreated)
                            def slist = c.specimens

                            specimens {
                                for(s in slist) {
                                    specimen(id:s.id) {
                                        specimenId(s.specimenId)
                                        parentSpecimenId(s.parentSpecimen?.specimenId)
                                        tissueType(code:s.tissueType?.code, s.tissueType)
                                        tissueLocation(code:s.tissueLocation?.code, s.tissueLocation)
                                        fixative(code:s.fixative?.code, s.fixative)
                                        container(code:s.containerType?.code, s.containerType)
                                        wasConsumed(s.wasConsumed)
                                        tumorStatus(code: s.tumorStatus?.code, s.tumorStatus)

                                        specimenExtension() {
                                            def alt = altMap.get(s.specimenId)
                                            if(alt == 'PRIORITY1') {
                                                attribute() {
                                                    name('PRIORITY')
                                                    value('I')
                                                }
                                            }
                                            if(alt == 'PRIORITY2') {
                                                attribute() {
                                                    name('PRIORITY')
                                                    value('II')
                                                }
                                            }
                                            if(alt == 'PRIORITY3a') {
                                                attribute() {
                                                    name('PRIORITY')
                                                    value('IIIA')
                                                }
                                            }
                                            if(alt == 'PRIORITY3b') {
                                                attribute() {
                                                    name('PRIORITY')
                                                    value('IIIB')
                                                }
                                            }
                                        }

                                        if(session.org?.code != 'BROAD' && username !='ldaccservice'){
                                            def sllist = s.slides
                                            slides {
                                                for(sl in sllist) {
                                                    slide(id:sl.slideId)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        error {
                            error(message: 'Not authorized or case not released')
                        }
                    }
                } else {
                    caseRecord {
                        error(message: 'Case ID does not exist')
                    }
                }
            } else {
                caseRecord {
                    error(message: 'Case ID not provided')
                }
            }
        }

        return xmlDocument.toString()
    }
    
    def getFrozenList(caseList){
	def frozenList = []
        def hasFzn
        caseList.each(){
            hasFzn = SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s  where s.caseRecord.id=? and s.fixative.code='DICE' ", [it.id])
            if(hasFzn)
              frozenList.add(it.id)
            }        
            return frozenList
    }
    
    def getBrainList(caseList){
        def brainList = []
        def hasBrain
        caseList.each(){
            hasBrain = SpecimenRecord.executeQuery("select distinct s from SpecimenRecord s  where s.caseRecord.id=? and s.tissueType.code='BRAIN' ", [it.id])
            if(hasBrain)
              brainList.add(it.id)
            }                
            return brainList
    }    
    
    
    def getInventFeedForCase(caseId) {

            caseId = caseId.trim()
            def caseRecordInstance = CaseRecord.findByCaseId(caseId)
            def brimsService = AppSetting.findByCode('CBR_SERVICE_URL')?.bigValue
            def brimsServiceAuth = AppSetting.findByCode('CBR_SERVICE_AUTH')?.bigValue    
            def newPost = new HTTPBuilder(brimsService+"/cases/"+caseId)
            def specimenList = [:]
            def specimens = []
            def result
            def caseRecord// = new StringBuffer()
            try {
            newPost.request(Method.GET, ContentType.TEXT) { 
                    request.addHeader("Authorization", brimsServiceAuth)
                    request.addHeader("Content-Type","application/json")
                    response.success = { resp, reader ->
                        result = resp.status
                        caseRecord = reader.getText()

                    }
                    response.failure = { resp, reader ->
                        result = resp.status
                    }            
                }
            } catch (HttpResponseException hre) {
                            hre.printStackTrace()
                }
                    if (result==200) {
                         def payload = new XmlSlurper().parseText(caseRecord)
                           payload.specimens.specimen.each{
                               def specimen = [:]
                               if (!it.containerCode.text().equals("SLIDE") && !it.containerCode.text().equals("IMAGE")) {
                               specimen.put("id",it.specimenId.text().toUpperCase())
                               specimen.put("invent_status",it.inventoryStateCode.text())
                               specimens.add(specimen)
                               }
                           }
                           specimenList.put("specimens",specimens)
                    }
                    return specimenList
    }    
        
}
