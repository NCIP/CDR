package nci.obbr.cahub

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.datarecords.*
import grails.converters.deep.*
import grails.util.GrailsUtil
import groovy.sql.Sql
import nci.obbr.cahub.forms.*
import nci.obbr.cahub.util.AppSetting
import groovyx.net.http.*
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import groovyx.net.http.HttpResponseException

import java.text.SimpleDateFormat


class restController {

    def ldaccService
    def chpEventService
    def shippingService
    def sendMailService
    def prcReportService
    def caseRecordService
    def staticMemberService
    def activityEventService
    def collectionEventService
    def processingService    


    def caseRecordRestActions = {
        switch(request.method) {
            case "GET":
                def res1
                try
                {
                    res1 = caseRecordService.getCaseXMLRecord(params.caseid) 
                }
                catch(Exception ee)
                {
                    res1 = 'K2 errmessage=' + ee.getMessage()
                    println res1
                    ee.printStackTrace()
                }
                //def res = caseRecordService.getCaseXMLRecord(params.caseid)
            render contentType: "text/xml", encoding: "UTF-8", text: res1
            break
        }
    }
    
    def cbrApiEventRestActions = {
                def event = []
                def payload
        switch(request.method) {
            case "GET":
                try
                {
                    if (params.cdrid!=null && params.cdrid!="") {
                        def apiEvt = CbrApiEvent.findByBio4dEventIdAndId(params.id, params.cdrid)
                        if (apiEvt) {
                            event.add(apiEvt)
                        }
                    } else {
                        event = CbrApiEvent.findAllByBio4dEventId(params.id) 
                    }
                    
                    if (event.size() > 0) {
                        if (event.size() > 1) {
                            payload = "<b>Multiple events were found with the event ID "+ params.id +". Please choose one from the list below.</b><br> <ul>"
                            event.each {
                                def eventStatus 
                                if (it.eventStatus) {
                                    eventStatus = it.eventStatus
                                } else {
                                    eventStatus = "SUCCESS"
                                }
                                    
                                payload = payload + "<li><span><a href=\"/cahubdataservices/rest/getcbrapievent/"+ it.bio4dEventId + "/" + +it.id + "\">" + it.eventType + "</a>    :: " + eventStatus + "</span></li>" + "<br>"
                            }
                            payload = payload + "</ul>"
                        } else {
                            payload = event.get(0).rawData
                        }
                    } else {
                        //payload = "<?xml version='1.0'?><cbrApiEvent><error message='Event ID not provided or incorrect' /></cbrApiEvent>\n"
                        payload = null
                    }
//                    println "Event : "+res1.rawData
                } catch(Exception e)
                {
                    println event
                    e.printStackTrace()
                }
                if (payload!=null) {
                    if (event.size() > 1) {
                        render contentType: "text/html", encoding: "UTF-8", text: payload
                    } else {
                        render contentType: "text/xml", encoding: "UTF-8", text: payload
                    }
                } else {
                    render text: '<?xml version="1.0"?><cbrApiEvent><error message="Event ID not provided or incorrect" /></cbrApiEvent>\n', contentType:"text/xml", encoding:"UTF-8"
                }
            
            break
        }
    }    

    def shippingEventRestActions = {
        switch(request.method){
            case "POST":
            //Get raw HTTP request body.
            def rawHttpRequestBody = request.reader.text
            //Put body into XmlSlurper, assuming it's XML
            
            def payload = new XmlSlurper().parseText(rawHttpRequestBody)
            def cbrApiEventType = ""
            if ("USED".equals(payload.eventType.text().toUpperCase())) {
                cbrApiEventType = "SHIPPING - USED"
            } else if ("REDISTRIBUTED".equals(payload.eventType.text().toUpperCase())){
                cbrApiEventType = "SHIPPING - REDISTRIBUTED"
            } else if ("INSPECTION".equals(payload.eventType.text().toUpperCase())){
                cbrApiEventType = "SHIPPING - INSPECTION"
            } else if ("UNUSED".equals(payload.eventType?.text()?.toUpperCase())) {
                cbrApiEventType = "SHIPPING - UNUSED"
            } else if ("RECEIPT".equals(payload.eventType?.text()?.toUpperCase())) {
                cbrApiEventType = "SHIPPING - RECEIPT"
            } else {
                cbrApiEventType = "SHIPPING - UNKNOWN"
            }
            CbrApiEvent apiEvent = new CbrApiEvent()
            apiEvent.bio4dEventId = payload.'@id'.text()
            apiEvent.eventType = cbrApiEventType
            apiEvent.rawData = rawHttpRequestBody
            apiEvent.eventStatus = "FAIL"
            apiEvent.save(flush:true)
            
            if ("".equals(payload.eventType.text().toUpperCase())) {
                    render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    processingEvent{
                        status('-1')
                        message('shipping event failed. Event type is missing in the shipping event.')
                    }
                }
                sendMailService.sendServiceEventEmail('FAIL:', rawHttpRequestBody)
                break
            } else if (!("USED".equals(payload.eventType.text().toUpperCase())) && !("REDISTRIBUTED".equals(payload.eventType.text().toUpperCase())) && !("INSPECTION".equals(payload.eventType.text().toUpperCase())) && !("UNUSED".equals(payload.eventType.text().toUpperCase()))
                   && !("RECEIPT".equals(payload.eventType.text().toUpperCase()))) {
                            render(contentType:"text/xml"){
                            mkp.xmlDeclaration()
                            processingEvent{
                                status('-1')
                                message('shipping event failed. Unknown event type for a shipping event.')
                                }
                            }
                            sendMailService.sendServiceEventEmail('FAIL:', rawHttpRequestBody)
                            break
                   }
            
            def errors //result from service.  Returns map of errors
            def typeFlag = payload.manifest.contents.'@type' //remember what content type was sent
            
            if ("RECEIPT".equals(payload.eventType?.text()?.toUpperCase())){
                errors = shippingService.updateShippingEvent(rawHttpRequestBody)
                
            }
            else{
                           
                //Normal shipping event
                errors = shippingService.saveShippingEvent(rawHttpRequestBody)
            }
           
            if(!errors){
                response.status = 201 // Created
                apiEvent.eventStatus = "SUCCESS"
                apiEvent.save(flush:true)
                sendMailService.sendServiceEventEmail('SUCCESS:', rawHttpRequestBody)
                
                def activityType
                def caseId
                
                if ("PRENOTE".equals(payload.eventType?.text()?.toUpperCase())) {
                    activityType = ActivityType.findByCode("IMAGEREADY")
                    caseId = payload.manifest?.contents?.caseId?.text()
                } else if ("UNUSED".equals(payload.eventType?.text()?.toUpperCase())) {
                    activityType = ActivityType.findByCode("UNUSEDKIT")
                    caseId = payload.manifest?.contents?.caseId?.text()
                } else if ("RECEIPT".equals(payload.eventType?.text()?.toUpperCase())) {
                    def retrievedShippingEvent = ShippingEvent.findByBio4dEventId(payload.'@id'?.text())
                    caseId = retrievedShippingEvent?.caseId
                    if (retrievedShippingEvent?.discreps?.size()>0) {
                        activityType = ActivityType.findByCode("SHIPRECPTDISC")
                    } else {
                        activityType = ActivityType.findByCode("SHIPPINGRECEIPT")
                    }
                } else if ("INSPECTION".equals(payload.eventType?.text()?.toUpperCase())) {
                    def retrievedShippingEvent = ShippingEvent.findByBio4dEventId(payload.'@id'?.text())
                    caseId = retrievedShippingEvent?.caseId
//                    if (retrievedShippingEvent.discreps?.size()>0) {
//                        activityType = ActivityType.findByCode("SHIPRECPTDISC")
//                    } else {
                        activityType = ActivityType.findByCode("SHIPINSP")
//                    }
                } else {
                    activityType = ActivityType.findByCode("SHIP")
                    caseId = payload.manifest?.contents?.caseId?.text()
                }
                
                
                if (payload.shippingContainers!="") {
                    payload.shippingContainers.cases.caseId.each {
                        def study = Study.findByCode(payload.studyCode?.text()?.toUpperCase())
                        def bssCode
                        def restEventId = payload.'@id'?.text()
                        def initiator = payload.sendingUser?.text()
                        if ("INSPECTION".equals(payload.eventType?.text()?.toUpperCase())) {
                            initiator = payload.inspectingUser?.text()
                        }
                        def sender = payload.sender?.text()
                        def recipient = payload.recipient?.text()
                        def courier = payload.courier?.text()
                        def trackingNumber = payload.trackingNumber?.text()
                        caseId = it.text()
                        if (!caseId) {
                            caseId = "N/A"
                            bssCode = BSS.findByCode(recipient)?.parentBss?.code
                        } else {
                            bssCode = CaseRecord.findByCaseId(caseId)?.bss?.parentBss?.code
                        }

                        if (activityType.code.equals("SHIP") || activityType.code.equals("UNUSEDKIT")) {
                            def additionalInfo1 = sender + "," + recipient
                            def additionalInfo2 = courier + "," + trackingNumber
                            activityEventService.createEvent(activityType, caseId, study, bssCode, restEventId, initiator, additionalInfo1, additionalInfo2)
                        } else {
                            activityEventService.createEvent(activityType, caseId, study, bssCode, restEventId, initiator, sender, recipient)
                        }                        
                    }
                } else {
                    def study = Study.findByCode(payload.studyCode?.text()?.toUpperCase())
                    def bssCode
                    def restEventId = payload.'@id'?.text()
                    def initiator = payload.sendingUser?.text()
                    if ("INSPECTION".equals(payload.eventType?.text()?.toUpperCase())) {
                        initiator = payload.inspectingUser?.text()
                    }
                    def sender = payload.sender?.text()
                    def recipient = payload.recipient?.text()
                    def courier = payload.courier?.text()
                    def trackingNumber = payload.trackingNumber?.text()

                    if (!caseId) {
                        caseId = "N/A"
                        bssCode = BSS.findByCode(recipient)?.parentBss?.code
                    } else {
                        bssCode = CaseRecord.findByCaseId(caseId)?.bss?.parentBss?.code
                    }

                    if (activityType.code.equals("SHIP") || activityType.code.equals("UNUSEDKIT")) {
                        def additionalInfo1 = sender + "," + recipient
                        def additionalInfo2 = courier + "," + trackingNumber
                        activityEventService.createEvent(activityType, caseId, study, bssCode, restEventId, initiator, additionalInfo1, additionalInfo2)
                    } else {
                        activityEventService.createEvent(activityType, caseId, study, bssCode, restEventId, initiator, sender, recipient)
                    }
                }
                
                           
                
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    shippingEvent{
                        status('0')
                        message(typeFlag.text() + ' shipping event successfully saved.')
                    }                    
                }
            }
           
            //error message generated by service, if any
            
            break
            case "GET":

            if(params.caseid){
                def record = CaseRecord.findByCaseId(params.caseid)
                if(record){
                    render record as XML
                }
                else{render text: '<?xml version="1.0"?><caseRecord><error message="Case ID does not exist" /></caseRecord>\n', contentType:"text/xml", encoding:"UTF-8"}

            }

            else{render text: '<?xml version="1.0"?><caseRecord><error message="Case ID not provided" /></caseRecord>\n', contentType:"text/xml", encoding:"UTF-8"}
            break

        }
    }
    
    def processingEventRestActions = {
        switch(request.method){
            case "POST":
            //Get raw HTTP request body.
            def rawHttpRequestBody = request.reader.text
            //Put body into XmlSlurper, assuming it's XML
            
            def payload = new XmlSlurper().parseText(rawHttpRequestBody)
            
            def errors //result from service.  Returns map of errors
            def typeFlag = payload.manifest.contents.'@type' //remember what content type was sent
            def contents = payload.manifest.contents
            def cbrApiEventType = ""
            
            if("SLIDES".equals(payload.eventType.text().toUpperCase()) || "SLIDE".equals(payload.eventType.text().toUpperCase())) {
                cbrApiEventType = "PROCESSING - SLIDES"
            } else if("IMAGES".equals(payload.eventType.text().toUpperCase()) || "IMAGE".equals(payload.eventType.text().toUpperCase())) {
                cbrApiEventType = "PROCESSING - IMAGES"
            } else if("ALIQUOT".equals(payload.eventType.text().toUpperCase()) || "ALIQUOTS".equals(payload.eventType.text().toUpperCase())) {
                cbrApiEventType = "PROCESSING - ALIQUOTS"
            } else if ("EMBED".equals(payload.eventType.text().toUpperCase())) {
                cbrApiEventType = "PROCESSING - EMBED"
            } else {
                cbrApiEventType = "PROCESSING - UNKNOWN"    
            }

            CbrApiEvent apiEvent = new CbrApiEvent()
            apiEvent.bio4dEventId = payload.'@id'.text()
            apiEvent.eventType = cbrApiEventType
            apiEvent.rawData = rawHttpRequestBody
            apiEvent.eventStatus = "FAIL"
            apiEvent.save(flush:true)
            if ("".equals(payload.eventType.text().toUpperCase())) {
                //log.error("processingEvent POST failed")
                    render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    processingEvent{
                        status('-1')
                        message(typeFlag.text() + ' processing event failed. Event type is missing in the processing event.')
                    }
                }
                sendMailService.sendServiceEventEmail('FAIL:', rawHttpRequestBody)
                break
            }  else if (!("SLIDES".equals(payload.eventType.text().toUpperCase())) && !("IMAGES".equals(payload.eventType.text().toUpperCase())) && !("ALIQUOT".equals(payload.eventType.text().toUpperCase())) 
                && !("EMBED".equals(payload.eventType.text().toUpperCase())) && !("SLIDE".equals(payload.eventType.text().toUpperCase())) && !("IMAGE".equals(payload.eventType.text().toUpperCase()))
                && !("ALIQUOTS".equals(payload.eventType.text().toUpperCase()))) {
                    render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    processingEvent{
                            status('-1')
                            message(typeFlag.text() + ' processing event failed. Unknown event type for a processing event.')
                        }
                    }
                    sendMailService.sendServiceEventEmail('FAIL:', rawHttpRequestBody)
                    break
                   }
            errors = processingService.saveProcessingEvent(rawHttpRequestBody)
            
            if(!errors){
                response.status = 201 // Created
                apiEvent.eventStatus = "SUCCESS"
                apiEvent.save(flush:true)
                sendMailService.sendServiceEventEmail('SUCCESS:', rawHttpRequestBody)
                
                def activityType = ActivityType.findByCode("PROCESSEVT")
                def caseId = "N/A"
                def slideRecord
                def fixative
                def additionalInfo2 = payload.processingSteps?.processName?.text()
                contents.specimens.specimen.each {
                if ("IMAGES".equals(payload.eventType?.text()?.toUpperCase()) || "IMAGE".equals(payload.eventType?.text()?.toUpperCase())) {
                    slideRecord = SlideRecord.findBySlideId(it.'@id'?.text())
                    caseId = slideRecord?.specimenRecord?.caseRecord?.caseId
                    fixative = slideRecord?.specimenRecord?.fixative
                    additionalInfo2 = payload.processingSteps?.processName?.text() + "," + fixative
                } else if ("SLIDES".equals(payload.eventType?.text()?.toUpperCase()) || "SLIDE".equals(payload.eventType?.text()?.toUpperCase())) {
                    slideRecord = SlideRecord.findBySlideId(it.derivatives.specimen.getAt(0).'@id'?.text())
                    caseId = slideRecord.specimenRecord.caseRecord.caseId
                } else if ("ALIQUOT".equals(payload.eventType?.text()?.toUpperCase()) || "ALIQUOTS".equals(payload.eventType?.text()?.toUpperCase())) {
                      def specimenRecord  = SpecimenRecord.findBySpecimenId(it.'@id'.text())
                      if (specimenRecord) {
                          caseId = specimenRecord.caseRecord.caseId
                      } else {
                          def derivative = DerivativeRecord.findByDerivativeId(it.'@id'.text())
                          if (derivative) {
                                while (derivative.parentDerivative) {
                                    derivative = derivative.parentDerivative
                                }
                                specimenRecord  = derivative.parentSpecimen
                          }
                          caseId = specimenRecord.caseRecord.caseId
                      }
                      
                    }
                }
                def study = Study.findByCode(payload.studyCode?.text()?.toUpperCase())
                def bssCode = Organization.findByCode(payload.site?.text()).code
                def restEventId = payload.'@id'?.text()
                def initiator = payload.performedBy?.text()
                def sender = payload.performedBy?.text()
                def recipient = payload.performedBy?.text()
                def eventType = payload.eventType?.text()
                def eventDateTime = payload.eventDateTime?.text()
                def additionalInfo1 = eventType + "," + eventDateTime
                    if (!("EMBED".equals(payload.eventType?.text()?.toUpperCase()))) {
                        activityEventService.createEvent(activityType, caseId, study, bssCode, restEventId, initiator, additionalInfo1, additionalInfo2)
                    }
                
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    processingEvent{
                        status('0')
                        message(typeFlag.text() + ' processing event successfully saved.')
                    }
                }
            }  else {                
                response.status = 500 // Not Found
                sendMailService.sendServiceEventEmail('FAIL:', rawHttpRequestBody)
                log.error(errors.toString())                
                    render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    processingEvent{
                        status('-1')
                        message(typeFlag.text() + " processing event failed. Please check the server's log files for specific information.")
                    }
                }
            }
           
            //error message generated by service, if any
            
            break
            case "GET":

            if(params.caseid){
                def record = CaseRecord.findByCaseId(params.caseid)
                if(record){
                    render record as XML
                }
                else{render text: '<?xml version="1.0"?><caseRecord><error message="Case ID does not exist" /></caseRecord>\n', contentType:"text/xml", encoding:"UTF-8"}

            }

            else{render text: '<?xml version="1.0"?><caseRecord><error message="Case ID not provided" /></caseRecord>\n', contentType:"text/xml", encoding:"UTF-8"}
            break

        }
    }    

    
    def collectionEventRestActions = {
        switch(request.method){
            case "POST":
            //Get raw HTTP request body.
            def rawHttpRequestBody = request.reader.text
            //Put body into XmlSlurper, assuming its XML
            
            def errors //result from service.  Returns map of errors
            def payload = new XmlSlurper().parseText(rawHttpRequestBody)        
            CbrApiEvent apiEvent = new CbrApiEvent()
            apiEvent.bio4dEventId = payload.'@id'.text()
            apiEvent.eventType = "COLLECTION"
            apiEvent.rawData = rawHttpRequestBody
            apiEvent.eventStatus = "FAIL"
            apiEvent.save(flush:true)
            
            def transTypeFlag = payload.transactionType?.text()
            
            if(!transTypeFlag){
                transTypeFlag = "INS" //set default to INS for backwards compatibility
            }
            
            switch(transTypeFlag) {
                case "INS":
                errors = collectionEventService.createCaseRecord(rawHttpRequestBody)
                break
                /* */
                case "UPD":
                errors = collectionEventService.updateCase(rawHttpRequestBody)
                break
                    
                case "DEL":
                errors = collectionEventService.deleteCase(rawHttpRequestBody)
                break
                default:
                log.error("Invalid Transaction Type: " + transTypeFlag)
            }
            
            
            if(!errors){
                response.status = 201 // Created
                apiEvent.eventStatus = "SUCCESS"
                apiEvent.save(flush:true)
                sendMailService.sendServiceEventEmail('SUCCESS:', rawHttpRequestBody)
                
                def activityType = ActivityType.findByCode("COLLECT")
                def caseId = payload.caseId?.text()
                def study = Study.findByCode(payload.studyCode?.text()?.toUpperCase())
                def bssCode = BSS.findByCode(payload.bssCode?.text()?.toUpperCase())?.parentBss?.code
                def restEventId = payload.'@id'?.text()
                def initiator = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                if (!initiator) {
                    initiator = "CBR"
                }
                def childBssCode = payload.bssCode?.text()
                activityEventService.createEvent(activityType, caseId, study, bssCode, restEventId, initiator, childBssCode, null)
                
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    collectionEvent{
                        status('0')
                        message("Collection transaction " + TransactionType.findByCode(transTypeFlag) + " was successful.")
                    }
                }
            } else {
                response.status = 500 // Not Found

                sendMailService.sendServiceEventEmail('FAILED:', rawHttpRequestBody)

                log.error(errors.toString())
                
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    collectionEvent{
                        status('-1')
                        message("Collection transaction " + TransactionType.findByCode(transTypeFlag) + " Failed.  Please check the server's log files for specific information")
                    }
                }
            }
            //error message generated by service, if any
            
            
            break
            case "GET":

            if(params.caseid){
                def record = CaseRecord.findByCaseId(params.caseid)
                if(record){
                    render record as XML
                }
                else{render text: '<?xml version="1.0"?><caseRecord><error message="caseId does not exist" /></caseRecord>\n', contentType:"text/xml", encoding:"UTF-8"}

            }

            else{render text: '<?xml version="1.0"?><caseRecord><error message="caseId not provided" /></caseRecord>\n', contentType:"text/xml", encoding:"UTF-8"}
            break

        }
    }

    def getValidBSS = {
        switch(request.method){
            case "GET":

            def list

            if(params.code){
                println params.code
                list = BSS.findByCode(params.code.toUpperCase())
            }
            if(!params.code){
                list = BSS.findAll()
            }

            if(list){
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    validBSSList{
                        for(b in list){
                            bss(){
                                code(b.code)
                                name(b.name)
                                bssParent(b.parentBss)
                                study(b.study.code)
                                subSiteFacility(b.subSiteFacility)
                                parentFacility(b.parentFacility)
                            }
                        }
                    }
                }
            }

            else{render text: '<?xml version="1.0"?><error><error message="Invalid BSS" /></error>\n', contentType:"text/xml", encoding:"UTF-8"}

            break
        }
    }

    def getValidAcquisitionLocation = {
        switch(request.method){
            case "GET":

            def list

            if(params.code){
                list = AcquisitionLocation.findByCode(params.code.toUpperCase())
            }
            if(!params.code){
                list = AcquisitionLocation.findAll()
            }

            if(list){
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    validAcquisitionLocationList{
                        for(a in list){
                            acquisitionLocation(){
                                code(a.code)
                                name(a.name)
                                description(a.description)
                            }
                        }
                    }
                }
            }

            else{render text: '<?xml version="1.0"?><error><error message="Invalid Acquisition Location" /></error>\n', contentType:"text/xml", encoding:"UTF-8"}

            break
        }
    }

    def getValidAcquisitionType = {
        switch(request.method) {
            case "GET":
            def list
            def inputCode = params.code?.toUpperCase()

            if("GTEX".equals(inputCode) || "BRN".equals(inputCode) || "BMS".equals(inputCode) || "BPV".equals(inputCode) || "MBB".equals(inputCode)) {
                list = staticMemberService.getAcquisitionTypeList(inputCode)
            } else if("ALL".equals(inputCode)) {
                list = AcquisitionType.findAll()
            } else if(inputCode) {
                list = AcquisitionType.findByCode(inputCode)
            }

            if(list) {
                render(contentType:"text/xml") {
                    mkp.xmlDeclaration()
                    validAcquisitionTypeList {
                        for(a in list) {
                            acquisitionType() {
                                code(a.code)
                                name(a.name)
                                description(a.description)
                            }
                        }
                    }
                }
            } else {
                render text: '<?xml version="1.0"?><error><error message="Invalid acquisition type. Pass the param all or a study code or an acquisition code." /></error>\n', contentType:"text/xml", encoding:"UTF-8"
            }
            break
        }
    }

    def getValidFixative = {
        switch(request.method) {
            case "GET":
            def list
            def inputCode = params.code?.toUpperCase()

            if("GTEX".equals(inputCode) || "BRN".equals(inputCode) || "BMS".equals(inputCode) || "BPV".equals(inputCode)) {
                list = staticMemberService.getFixativeList(inputCode)
            } else if("ALL".equals(inputCode)) {
                list = Fixative.findAll()
            } else if(inputCode) {
                list = Fixative.findByCode(inputCode)
            }

            if(list) {
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    validFixativeList {
                        for(f in list) {
                            fixative() {
                                code(f.code)
                                name(f.name)
                                description(f.description)
                            }
                        }
                    }
                }
            } else {
                render text: '<?xml version="1.0"?><error><error message="Invalid fixative. Pass the param all or study code or fixative code." /></error>\n', contentType:"text/xml", encoding:"UTF-8"
            }
            break
        }
    }

    def getValidContainerType = {
        switch(request.method) {
            case "GET":
            def list
            def inputCode = params.code?.toUpperCase()

            if("GTEX".equals(inputCode) || "BRN".equals(inputCode) || "BMS".equals(inputCode) || "BPV".equals(inputCode)) {
                list = staticMemberService.getContainerTypeList(inputCode)
            } else if("ALL".equals(inputCode)) {
                list = ContainerType.findAll()
            } else if(inputCode) {
                list = ContainerType.findByCode(inputCode)
            }

            if(list) {
                render(contentType:"text/xml") {
                    mkp.xmlDeclaration()
                    validContainerTypeList {
                        for(a in list) {
                            containerType() {
                                code(a.code)
                                name(a.name)
                                description(a.description)
                            }
                        }
                    }
                }
            } else {
                render text: '<?xml version="1.0"?><error><error message="Invalid container type. Pass the param all or a study code or an container code." /></error>\n', contentType:"text/xml", encoding:"UTF-8"
            }
            break
        }
    }

    def getValidKit = {
        switch(request.method){
            case "GET":

            def list

            if(params.code){
                list = KitType.findByCode(params.code.toUpperCase())
            }
            if(!params.code){
                list = KitType.findAll()
            }

            if(list){
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    validKitList{
                        for(k in list){
                            kitType(){
                                code(k.code)
                                name(k.name)
                                description(k.description)
                                study(k.study.code)
                            }
                        }
                    }
                }
            }

            else{render text: '<?xml version="1.0"?><error><error message="Invalid kit type" /></error>\n', contentType:"text/xml", encoding:"UTF-8"}

            break
        }
    }     


    def getValidShippingContentType = {
        switch(request.method){
            case "GET":

            def list

            if(params.code){
                list = ShippingContentType.findByCode(params.code.toUpperCase())
            }
            if(!params.code){
                list = ShippingContentType.findAll()
            }

            if(list){
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    validShippingContentTypeList{
                        for(s in list){
                            shippingContentType(){
                                code(s.code)
                                name(s.name)
                                description(s.description)
                            }
                        }
                    }
                }
            }

            else{render text: '<?xml version="1.0"?><error><error message="Invalid shipping content type" /></error>\n', contentType:"text/xml", encoding:"UTF-8"}

            break
        }
    }     

    def getValidOrganization = {
        switch(request.method){
            case "GET":

            def list

            if(params.code){
                list = Organization.findByCode(params.code.toUpperCase())
                
                if(!list){
                    list = BSS.findByCode(params.code.toUpperCase())
                }
            }
            if(!params.code){
                list = Organization.findAll()
                list += BSS.findAll()
            }

            if(list){
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    validOrganizationList{
                        for(o in list){
                            organization(){
                                code(o.code)
                                name(o.name)
                                description(o.description)
                            }
                        }
                    }
                }
            }

            else{render text: '<?xml version="1.0"?><error><error message="Invalid organization or BSS" /></error>\n', contentType:"text/xml", encoding:"UTF-8"}

            break
        }
    }      
    
    
   
    
    def getPrcReport = {
        switch(request.method){
            case "GET":
            
            def c
            def study

            if(params.code){
                c = CaseRecord.findByCaseId(params.code)
                if(c){
                    study=c.study.code
                }
            }
           
            
            if(c){
                if(c.prcReport != null && c.prcReport?.reviewDate != null ){
                    render(contentType:"text/xml"){
                        // mkp.xmlDeclaration()
                        mkp.yieldUnescaped('<?xml version="1.0" encoding="UTF-8" ?>') 
                    
                    "case"(id:c.caseId, collection_type:c.caseCollectionType?.name, parent_case_id:c.parentCase?.caseId){
                            def prcReport = prcReportService.getPrcReport(c)
                             
                             
                            
                            PRC_summary{
                                def resolutions = prcReportService.getPrcIssueResolutionDisplayList(prcReport)
                                issue_resolutions{
                                    for (r in resolutions ){
                                        issue_resolution{  
                                            specimen_id(r.specimenId)
                                            tissue(r.tissue)
                                            issue_description(r.issueDescription)
                                            resolution_comments(r.resolutionComments)
                                            date_submitted(r.dateSubmitted)
                                        
                                        }
                                  
                                    }
                                }  
                                def specimenList =prcReportService.getPrcSpeciemenListByCase(c)
                                specimens{
                                    for(s in specimenList ){
                                        specimen{
                                            specimen_id(s.specimenRecord.specimenId)
                                            tissue_type(s.specimenRecord.tissueType?.name)
                                            if(study=='BMS'){
                                                fixative(s.specimenRecord.fixative.name)
                                                delay_hour(s.specimenRecord.protocol.delayHour)
                                            }
                                            def slideList =s.specimenRecord.slides
                                            def username= session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                                            if(session.org?.code != 'BROAD' && username !='ldaccservice'){
                                                slides{
                                                    for(sl in slideList){
                                                        slide{
                                                            slide_id(sl.slideId)
                                                            image_id(sl.imageRecord?.imageId)
                                                        }
                                                   
                                                    }
                                                }
                                            }
                                           
                                            if(study=='BMS' && (session.org?.code == 'BROAD' || username =='ldaccservice')){
                                                // println("username: " + username)
                                                autolysis()
                                            }else{
                                                autolysis(s.autolysis)
                                            }
                                            comments(s.comments)
                                            //release_to_inventory(s.releaseToInventory)
                                            inventory_status(s.inventoryStatus)

                                            // project_manager_follow_up(s.projectManagerFU)
                                        }
                                    }
                                }  
                                   
                                
                                general_information{
                                      
                                    overall_staining_of_slides(prcReport?.stainingOfSlides) 
                                    overall_staining_of_images(prcReport?.stainingOfImages)
                                    overall_processing_embedding(prcReport?.processing)
                                    additional_comments(prcReport?.comments)
                                    if(c.caseCollectionType?.code == 'SURGI'){
                                        amputation_type{
                                              
                                            position_v(prcReport?.amputationType1)
                                            position_h(prcReport?.amputationType2)
                                        }
                                    }
                                }
                                    
                                
                                def sublist = prcReportService.getPrcReportSubList(prcReport)
                                submissions{
                                    for (s in sublist ){
                                        submission{
                                            submitted_by(s.submittedBy) 
                                            date_submitted(s.dateSubmitted)
                                            report_version("V" + s.reportVersion)
                                            date_reviewed(s.dateReviewed)
                                        }
                                
                                    }
                            
                                }
                                    
                                
                            }
                           
                            
                            /*def issueList =prcReportService.getPrcIssueListByCase(c)
                            issues{
                            for(i in issueList){
                            issue{
                            specimen_id(i.specimenRecord.specimenId)
                            issue_description(i.issueDescription)
                            pending_further_follow_up(i.pendingFurtherFollowUp)
                            resolved(i.resolved)
                            resolution_comments(i.resolutionComments)
                            }
                            }
                            }*/
                               
                               
                            
                            
                       
                        }
                    }
                }else {
                    render text: '<?xml version="1.0"?><prcReport><status>1</status><message><error><error message="Summary Report is not avaliable" /></error></message></prcReport>\n', contentType:"text/xml", encoding:"UTF-8"
                }
            }else {
                render text: '<?xml version="1.0"?><prcReport><status>1</status><message><error><error message="No Such Case ' + params.code + '" /></error></message></prcReport>\n', contentType:"text/xml", encoding:"UTF-8"
            }
            break
        }
    }
    
    
    
    
    

    
    
    
    def getPrcSpecimenReport = {
        switch(request.method){
            case "GET":
            
            def list

            if(params.specimenId){
                list = PrcSpecimenReport.findAll("from PrcSpecimenReport c where c.specimenRecord.specimenId=?", [params.specimenId])
            }
               
             
            if(!params.specimenId){
                list = PrcSpecimenReport.findAll()
            }
              
            
            if(list){
                render(contentType:"text/xml"){
                    // mkp.xmlDeclaration()
                    mkp.yieldUnescaped('<?xml version="1.0" encoding="UTF-8" ?>') 
                    pathRevGTEX {
                        for(c in list){
                            specimen(c.specimenRecord.specimenId)
                            findings_diagnosis{
                                specifyFindings(c.specifyFindings)
                                whereFindingsFound(c.whereFindingsFound)
                                analysisFromWSI(c.analysisFromWSI)
                                analysisFromSlide(c.analysisFromSlide)
                                analysisFromSlideAndWSI(c.analysisFromSlideAndWSI)
                                analysisFromDVDWSI(c.analysisFromDVDWSI)
                                     
                            }
                                 
                            microscopic{
                                totalTissuePieces(c.totalTissuePieces)
                                lengthSpec(c.lengthSpec)
                                widthSpec(c.widthSpec)
                                tissueAreaEntire(c.tissueAreaEntire)
                                crush(c.crush)
                                autolysis(c.autolysis)
                                necrosis(c.necrosis)
                                fibrosis(c.fibrosis)
                                atrophy(c.atrophy)
                                edema(c.edema)
                                congestion(c.congestion)
                                acuteInflamation(c.acuteInflamation)
                                chronicInflamation(c.chronicInflamation)
                                hemorrhage(c.hemorrhage)
                                otherMicroFindings(c.otherMicroFindings)
                            }
                            image_annotation{
                                annotationDesc(c.annotationDesc)
                            }
                                 
                            artifacts{
                                fixative(c.fixative)
                                chatter(c.chatter)
                                fracture(c.fracture)
                                staining(c.staining)
                                artifactComments(c.artifactComments)
                            }
                                 
                            assessment{
                                caseAcceptable(c.caseAcceptable)
                                caseAcceptableOther(c.caseAcceptableOther)
                                closingComments(c.closingComments)
                            }
                            
                            authorizedReviewer{
                                authorizedReviewer1(c.authorizedReviewer1)
                                reviewType1(c.reviewType1)
                                reviewDate1(c.reviewDate1)
                                digitalSig1(c.digitalSig1)
                                authorizedReviewer2(c.authorizedReviewer2)
                                reviewType2(c.reviewType2)
                                reviewDate2(c.reviewDate2)
                                digitalSig2(c.digitalSig2)
                                authorizedReviewer3(c.authorizedReviewer3)
                                reviewType3(c.reviewType3)
                                reviewDate3(c.reviewDate3)
                                digitalSig3(c.digitalSig3)
                                authorizedReviewer4(c.authorizedReviewer4)
                                reviewType4(c.reviewType4)
                                reviewDate4(c.reviewDate4)
                                digitalSig4(c.digitalSig4)
                                authorizedReviewer5(c.authorizedReviewer5)
                                reviewType5(c.reviewType5)
                                reviewDate5(c.reviewDate5)
                                digitalSig5(c.digitalSig5)
                            }
                        }
                    }
                }
                  
            }else {
                render text: '<?xml version="1.0"?><prcSpecimenReport><status>1</status><message><error><error message="No Pathology Report exists for specimen ID ' + params.specimenId + '" /></error></message></prcSpecimenReport>\n', contentType:"text/xml", encoding:"UTF-8"
            }
            break
        }
    }
    
    def importLDACCDonor = {
        switch(request.method){
            case "POST":
            def rawHttpRequestBody = request.reader.text
            def payload = new XmlSlurper().parseText(rawHttpRequestBody)
            
            ldaccService.donorInstantiation(payload)
              
              
             
            response.status = 201 // Created
            render(contentType:"text/xml"){
                mkp.xmlDeclaration()
                responseMessage{
                    //TODO fix response message
                    success(' donor records created')
                }
            }
            break
        }
    }
    
    def chpEventRestActions = {
        switch(request.method){
            case "POST":
            def rawHttpRequestBody = request.reader.text
            
            def payload = new XmlSlurper().parseText(rawHttpRequestBody)
            def errors //result from service.  Returns map of errors
            // def typeFlag = payload.transactionType.text()
            def typeFlag = payload.transactionType
            println "chpEvent Transaction Type:   " + typeFlag.text()
            
            def chpObjectType = payload.primaryTissueType
            // println payload
            println "chpEvent PrimaryTissue Type: " + chpObjectType.text()
           
            
            if (chpObjectType.text() == "BLOOD") {
                println "chpObjectFlag = BLOOD: "    + chpObjectType.class.name
                switch(typeFlag.text()) {
                    case "INS":
                    println "Calling Insert"
                    errors = chpEventService.chpBloodRecordInsert(payload)
                    break
                    case "UPD":
                    println "Calling Update"
                    errors = chpEventService.chpBloodRecordUpdate(payload)
                    break
                    /* 
                    case "DEL":
                    println "Calling Delete"
                    chpEventService.chpBloodRecordDelete(payload)
                    break
                     */
                }
            } else {
                switch(typeFlag.text()) {
                    case "INS":
                    errors = chpEventService.chpTissueRecordInsert(payload)
                    break
                    
                    case "UPD":
                    println "Updating..."
                    // @TODO make this method aware of the BpvTissueForm Domain Class
                    errors = chpEventService.chpTissueRecordUpdate(payload)
                    break
                    /*
                    case "DEL":
                    chpEventService.chpRecordDelete(payload)
                    break
                     */
                }
            }
             
            if(!errors){
                response.status = 201 // Created
                render(contentType:"text/xml"){
                    mkp.xmlDeclaration()
                    responseMessage{
                        //TODO fix response message
                        success(' chp event service successful! ')
                    }
                }
            }
            
            
            break
            /*
            case "GET":
            chpEventService.chpRecordSelect(payload)
            break
             */
        }
            
    }

    def emailTest ={
        sendMailService.sendServiceEventEmail('SUCCESS:', "testing.... Please ignore this message")
        render "good"
    }
    
    // inserted by umkis 01/04/2013 as a utility of selenium test
    def searchId = {
        switch(request.method){
            case "POST":
            def bd = request.reader.text
            //println 'REST SearchId: BodyText=' + bd
                
            def paramStr 
            try
            { 
                paramStr = bd.substring(bd.indexOf('<body>') + 6, bd.indexOf('</body>'))  
            }
            catch(Exception ee)
            {
                paramStr = bd
            }
                
            def line
            def paramName
            def paramVal
                
            def part
            def key
            def value
            def classPath
            def url
            def sql
            def user 
            def pass
            while(true)
            {
                paramStr = paramStr.trim()
                if (paramStr == '') break
                int idx = paramStr.indexOf('`')
                if (idx < 0)
                {
                    line = paramStr
                    paramStr = ''
                }
                else
                {
                    line = paramStr.substring(0, idx)
                    paramStr = paramStr.substring(idx + 1)
                }
                idx = line.indexOf(':')
                if (idx < 0) idx = line.indexOf('=')
                if (idx < 0) continue
                paramName = line.substring(0, idx).trim()
                paramVal = line.substring(idx + 1).trim()
                if (paramName == '') continue
                if (paramVal == '') continue
                //println '     searchId() Parameter name=' + paramName  + ', value=' + paramVal
                    
                if (paramName.toLowerCase() == 'part') part = paramVal
                if (paramName.toLowerCase() == 'key') key = paramVal
                if (paramName.toLowerCase() == 'value') value = paramVal
                if (paramName.toLowerCase() == 'classpath') classPath = paramVal
                if (paramName.toLowerCase() == 'url') url = paramVal
                if (paramName.toLowerCase() == 'sql') sql = paramVal
                if (paramName.toLowerCase() == 'user') user = paramVal
                if (paramName.toLowerCase() == 'pass') pass = paramVal
            }
            
            if (!part)
            {
                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:NO PART DATA</time></returnValue>'
                return
            }
                                
            def ret
                              
            if (part.toLowerCase() == 'sql') 
            {
                if (!sql) render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:NO SQL</time></returnValue>'
                def sqlStat = sql;
                def caseid = value;
                if (sqlStat == 'test')
                {
                    if (!caseid) sqlStat = 'select case_id from dr_case where case_id=\'BPV-UNM9201\''
                    else sqlStat = 'select case_id from dr_case where case_id=\''+caseid+'\''
                }
                else if (sqlStat == 'test2')
                {
                    if (!caseid) sqlStat = 'update st_appsetting set value=\'rr2@rr.com\' where code=\'LDACC_RIN_DISTRO\''
                    else sqlStat = 'update st_appsetting set value=\''+caseid+'\' where code=\'LDACC_RIN_DISTRO\''

                }
                else if (sqlStat == 'test3')
                {
                    if (!caseid) sqlStat = 'update st_appsettings set value=\'rr3@rr.com\' where code=\'LDACC_RIN_DISTRO\''
                    else sqlStat = 'update st_appsettings set value=\''+caseid+'\' where code=\'LDACC_RIN_DISTRO\''

                }
                else if (sqlStat == 'test4')
                {
                    if (!caseid) sqlStat = 'update st_appsetting set value=\'rr4@rr.com\' where code=\'LDACC_RIN_DISTROQ\''
                    else sqlStat = 'update st_appsetting set value=\''+caseid+'\' where code=\'LDACC_RIN_DISTROQ\''

                }

                if (!classPath) classPath = "oracle.jdbc.driver.OracleDriver";
                if (!url) url = "jdbc:oracle:thin:@localhost:1521:XE";

                def databaseDefaultUsrID = user;
                if (!databaseDefaultUsrID) databaseDefaultUsrID = "cdrds";

                def databaseDefaultPassword = pass;
                if (!databaseDefaultPassword) databaseDefaultPassword = "admin";
                //println '!!! Parameters 3  url=' + url + ', user=' +databaseDefaultUsrID +', pass='+ databaseDefaultPassword +'classPath='+ classPath + ', sql=' + sqlStat
                try
                {
                    def db = Sql.newInstance(url, databaseDefaultUsrID, databaseDefaultPassword, classPath)

                    if (sqlStat.toLowerCase().startsWith('select'))
                    {
                        def res
                        def n = 0
                        db.eachRow(sqlStat){ row ->
                            res = row[0]
                            n++
                            println 'result=' + res + ', n=' + n;
                        }
                        if (n == 0 )
                        {
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>NULL_VALUE:__</time></returnValue>'
                            return
                        }
                        else
                        {
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>DATA_FOUND:'+res+'</time></returnValue>'
                            return
                        }
                    }
                    else 
                    {
                        int n = db.executeUpdate(sqlStat)
                        if (n == 0)
                        {
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:Nothing changed</time></returnValue>'
                            return
                        }
                        else if (n == 1)
                        {
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>UPDATE_OK:__</time></returnValue>'
                            return
                        }
                        else 
                        {
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>UPDATE_ONE_MORE:'+n+'</time></returnValue>'
                            return
                        }
                    }
                }
                catch(Exception ee)
                {
                    render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:'+ee.getMessage()+'</time></returnValue>'
                    return
                }
            }
            else
            {
                if (!key)
                {
                    render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:NO Key DATA</time></returnValue>'
                    return
                }
                
                def dataFound
                    
                if ((part.toLowerCase().startsWith('email'))||(part.toLowerCase().startsWith('appsetting'))||(part.toLowerCase().startsWith('file'))) 
                {
                    if ((part.toLowerCase().startsWith('emailquery'))||(part.toLowerCase().startsWith('appsettingquery'))) 
                    {
                        if (key.equalsIgnoreCase('all'))
                        {
                            def appsettings = AppSetting.getAll()
                            dataFound = ''
                            appsettings.each{
                                
                                def valueApp = it.value
                                if (valueApp?.equalsIgnoreCase('see big value')) 
                                {
                                    valueApp = it.bigValue
                                }
                                if (valueApp && it.code)
                                {
                                    int idx1 = valueApp.indexOf("@")
                                    if (idx1 > 0)
                                    {
                                       int idx2 = valueApp.substring(idx1).indexOf(".") 
                                       if (idx2 > 0)
                                       {
                                           dataFound = dataFound + it.code + '~' + it.value + '|'
                                           //println part + '-' + key + ' : search = ' + it.code + '~' + it.value;
                                       }
                                    }
                                }
                            }
                            if (dataFound.trim().length() == 0) 
                            {
                                dataFound = null
                            }
                            else
                            {
                                dataFound = dataFound.substring(0, dataFound.length() - 1)
                            }
                        }
                        else 
                        {
                            def appSetting = AppSetting.findByCode(key)
                            if (appSetting)
                            {
                                def isBigValue = false
                                if ((part.equalsIgnoreCase('appsettingquery_big'))) isBigValue = true
                                else if ((part.equalsIgnoreCase('appsettingquery')))
                                {
                                    def valueApp = appSetting.value
                                    if (valueApp?.equalsIgnoreCase('see big value')) isBigValue = true
                                }
                                
                                if (isBigValue) dataFound = appSetting.bigValue 
                                else dataFound = appSetting.value 
                            }
                        }
                    }
                    else if ((part.toLowerCase().startsWith('emailreset'))||(part.toLowerCase().startsWith('appsettingupdate')))
                    {
                        if (!value)
                        {
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:NO VALUE DATA</time></returnValue>'
                            return
                        }
                        if (key.equalsIgnoreCase('all'))
                        {
                            def value2 = value + '|'
                            def achar
                            def codeApp = ''
                            def valueApp = ''
                            def isCode = true
                            for(int i=0;i<value2.length();i++)
                            {
                                achar = value2.substring(i, i+1)
                                if (achar.equals('|'))
                                {
                                    if ((codeApp.trim().equals(''))||(valueApp.trim().equals('')))
                                    {
                                        println part + '-' + key + ' : WARNING1:Invalid Format of APP Email List ' + value;
                                        //render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:INVALID EMAIL LIST - \''+value+'\'</time></returnValue>'
                                        //return
                                    }
                                    else
                                    {
                                        def appSetting = AppSetting.findByCode(codeApp)
                                        if (!appSetting)
                                        {
                                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:NOT FOUND - \''+key+'\'</time></returnValue>'
                                            return
                                        }
                                        appSetting.value = valueApp
                                        if (!appSetting.save())
                                        {
                                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:SAVE FAILURE - \''+key+'\'</time></returnValue>'
                                            return
                                        }
                                        //else
                                        //{
                                        //    println part + '-' + key + ' : eamil update = ' + codeApp + '~' + valueApp;
                                        //}
                                    }
                                                                        
                                    codeApp = ''
                                    valueApp = ''
                                    isCode = true
                                }
                                else if (achar.equals('~'))
                                {
                                    if (!isCode)
                                    {
                                        println part + '-' + key + ' : WARNING2:Invalid Format of APP Email List ' + value;
                                        //render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:INVALID EMAIL LIST - \''+value+'\'</time></returnValue>'
                                        //return
                                    }
                                    isCode = false
                                }
                                else
                                {
                                    if (isCode)
                                    {
                                        codeApp = codeApp + achar
                                    }
                                    else
                                    {
                                        valueApp = valueApp + achar
                                    }
                                }
                            }
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>UPDATE_OK:'+key+'</time></returnValue>'
                            return
                        }
                        else
                        {
                            def appSetting = AppSetting.findByCode(key)
                            if (!appSetting)
                            {
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:NOT FOUND - \''+key+'\'</time></returnValue>'
                                return
                            }
                                                        
                            def isBigValue = false
                            
                            if (part.equalsIgnoreCase('appsettingupdate_big')) isBigValue = true
                            else if (part.equalsIgnoreCase('appsettingupdate'))
                            {
                                if (appSetting.value == 'see big value') isBigValue = true
                            }
                                                        
                            def alreadyValue = appSetting.value
                            if (isBigValue) alreadyValue = appSetting.bigValue
                            
                            if (alreadyValue.equals(value))
                            {
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>NO_CHANGE:SAME VALUE - \''+key+'\'</time></returnValue>'
                                return
                            }
                            
                            if (isBigValue) appSetting.bigValue = value
                            else appSetting.value = value
                            
                            //appSetting.save()
                            if (!appSetting.save())
                            {
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:SAVE FAILURE - \''+key+'\'</time></returnValue>'
                                return
                            }
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>UPDATE_OK:'+key+'</time></returnValue>'
                            return
                        }
                    } 
                    else if (part.toLowerCase().startsWith('fileupload'))
                    {
                        if (!value)
                        {
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:NO Value DATA</time></returnValue>'
                            return
                        }
                        
                        String decodedValue
                        String decodedKey
                        try
                        {
                            decodedValue = java.net.URLDecoder.decode(value, "UTF-8")
                            decodedKey = java.net.URLDecoder.decode(key, "UTF-8")
                        }
                        catch(Exception ee)
                        {
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:onDecoding-'+ee.getMessage()+'</time></returnValue>'
                            return
                        }
                        
                        java.io.File file = new java.io.File(decodedKey)
                        java.io.File dir = file.getParentFile()
                        if (!dir.exists())
                        {
                            if (!dir.mkdirs())
                            {
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:onCreatingDir-'+dir.getAbsolutePath()+'</time></returnValue>'
                                return
                            }
                        }
                        
                        java.io.FileWriter fw
                        
                        try
                        {
                            fw = new java.io.FileWriter(decodedKey)
                            fw.write(decodedValue)
                            fw.close()
                        }
                        catch(Exception ie)
                        {
                            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:onWriting-'+ee.getMessage()+'</time></returnValue>'
                            if (fw != null)
                            {
                                try { fw.close() } catch(Exception e1) { println 'fw.close() error : ' + e1.getMessage() }
                            }
                            return
                        }
                        
                        render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>UPDATE_OK:fileSaved-'+decodedKey+'</time></returnValue>'
                        return
                    }
                }
                else if (part.toLowerCase().startsWith('case'))
                { 
                    while(value)
                    {
                        int n = -1
                        try { n = Integer.parseInt(value) } 
                        catch(NumberFormatException ne) { break }
                        if (key.indexOf('*') < 0) break
                           
                        while(('' + n).length() == value.length())
                        {
                            def caseKey = key.replace('*', '' + n) 
                            def caseRecord = CaseRecord.findByCaseId(caseKey)
                            //println ' ## search case id (' + n + ') : ' + caseRecord?.caseId
                            while (!caseRecord) 
                            {
                                def specimenRecord = SpecimenRecord.findBySpecimenIdLike('%' + caseKey + '%')
                                if (specimenRecord)
                                {
                                    println ' ## Already Used case ID : ' + caseKey
                                    break
                                }
                                println ' ## search available case id num : ' + n
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>AVAILABLE_ID_NUM:' + n + '</time></returnValue>'
                                return
                            }
                            n++
                        }
                        break
                    }
                    if ((key.indexOf('*') > 0)&&(value)) key = key.replace('*', value)
                    def caseRecord = CaseRecord.findByCaseId(key)
                    if (caseRecord) dataFound = caseRecord.caseId 
                        
                }
                else if (part.toLowerCase().startsWith('kit'))
                { 
                    while(value)
                    {
                        int n = -1
                        try { n = Integer.parseInt(value) } 
                        catch(NumberFormatException ne) { break }
                        if (key.indexOf('*') < 0) break
                           
                        while(('' + n).length() == value.length())
                        {
                            def kitRecord = KitRecord.findByKitId(key.replace('*', '' + n))
                            if (!kitRecord) 
                            {
                                println ' ## search available kit id num : ' + n
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>AVAILABLE_ID_NUM:' + n + '</time></returnValue>'
                                return
                            }
                            n++
                        }
                        break
                    }
                    if ((key.indexOf('*') > 0)&&(value)) key = key.replace('*', value)
                    def kitRecord = KitRecord.findByKitId(key)
                    if (kitRecord) dataFound = kitRecord.kitId
                }
                else if (part.toLowerCase().startsWith('ship'))
                { 
                    while(value)
                    {
                        int n = -1
                        try { n = Integer.parseInt(value) } 
                        catch(NumberFormatException ne) { break }
                        if (key.indexOf('*') < 0) break
                           
                        while(('' + n).length() == value.length())
                        {
                            def shippingEvent = ShippingEvent.findByBio4dEventId(key.replace('*', '' + n))
                            if (!shippingEvent) 
                            {
                                println ' ## search available shipping event id num : ' + n
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>AVAILABLE_ID_NUM:' + n + '</time></returnValue>'
                                return
                            }
                            n++
                        }
                        break
                    }
                    if ((key.indexOf('*') > 0)&&(value)) key = key.replace('*', value)
                    def shippingEvent = ShippingEvent.findByBio4dEventId(key)
                    if (shippingEvent) dataFound = shippingEvent.bio4dEventId
                }
                else if (part.toLowerCase().startsWith('image'))
                {  
                    while(value)
                    {
                        int n = -1
                        try { n = Integer.parseInt(value) } 
                        catch(NumberFormatException ne) { break }
                        if (key.indexOf('*') < 0) break
                           
                        while(('' + n).length() == value.length())
                        {
                            def imageRecord = ImageRecord.findByImageId(key.replace('*', '' + n))
                            if (!imageRecord) 
                            {
                                println ' ## search available image id num : ' + n
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>AVAILABLE_ID_NUM:' + n + '</time></returnValue>'
                                return
                            }
                            n++
                        }
                        break
                    }
                    if ((key.indexOf('*') > 0)&&(value)) key = key.replace('*', value)
                    def imageRecord = ImageRecord.findByImageId(key)
                    if (imageRecord) dataFound = imageRecord.imageId
                }
                else if (part.toLowerCase().startsWith('slide'))
                { 
                    while(value)
                    {
                        int n = -1
                        try { n = Integer.parseInt(value) } 
                        catch(NumberFormatException ne) { break }
                        if (key.indexOf('*') < 0) break
                           
                        while(('' + n).length() == value.length())
                        {
                            def slideRecord = SlideRecord.findBySlideId(key.replace('*', '' + n))
                            if (!slideRecord) 
                            {
                                println ' ## search available slide id num : ' + n
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>AVAILABLE_ID_NUM:' + n + '</time></returnValue>'
                                return
                            }
                            n++
                        }
                        break
                    }
                    if ((key.indexOf('*') > 0)&&(value)) key = key.replace('*', value)
                    def slideRecord = SlideRecord.findBySlideId(key)
                    if (slideRecord) dataFound = slideRecord.slideId
                }
                else if (part.toLowerCase().startsWith('photo'))
                { 
                    while(value)
                    {
                        int n = -1
                        try { n = Integer.parseInt(value) } 
                        catch(NumberFormatException ne) { break }
                        if (key.indexOf('*') < 0) break
                           
                        while(('' + n).length() == value.length())
                        {
                            def photoRecord = PhotoRecord.findByFileName(key.replace('*', '' + n))
                            if (!photoRecord) 
                            {
                                println ' ## search available num for photo file name : ' + n
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>AVAILABLE_ID_NUM:' + n + '</time></returnValue>'
                                return
                            }
                            n++
                        }
                        break
                    }
                    if ((key.indexOf('*') > 0)&&(value)) key = key.replace('*', value)
                    def photoRecord = PhotoRecord.findByFileName(key)
                    if (photoRecord) dataFound = photoRecord.fileName
                }
                else if (part.toLowerCase().startsWith('specimen'))
                { 
                    while(value)
                    {
                        int n = -1
                        try { n = Integer.parseInt(value) } 
                        catch(NumberFormatException ne) { break }
                        if (key.indexOf('*') < 0) break
                           
                        while(('' + n).length() == value.length())
                        {
                            def specimenRecord = SpecimenRecord.findBySpecimenId(key.replace('*', '' + n))
                            if (!specimenRecord) 
                            {
                                println ' ## search available specimen id num : ' + n
                                render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>AVAILABLE_ID_NUM:' + n + '</time></returnValue>'
                                return
                            }
                            n++
                        }
                        break
                    }
                    if ((key.indexOf('*') > 0)&&(value)) key = key.replace('*', value)
                    def specimenRecord = SpecimenRecord.findBySpecimenId(key)
                    if (specimenRecord) dataFound = specimenRecord.specimenId
                }
                else if (part.toLowerCase().startsWith('form'))
                { 
                    def formList = nci.obbr.cahub.staticmembers.FormMetadata.list()
                        
                    formList.each{
                        if (it?.paperFormName)
                        {
                            int idx = it.paperFormName.toLowerCase().indexOf(key.toLowerCase())
                            //println '#### Paper Form Name:' + it.paperFormName + ', key=-' + key + '-, index=' + idx
                            if (idx >= 0) dataFound = it.paperFormName;
                        }
                    }
                }
                else 
                {
                    render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:Invalid part (' +part+ ')</time></returnValue>'
                    return
                }
                    
                if (!dataFound) render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>NULL_VALUE:'+key+'</time></returnValue>'
                else render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>DATA_FOUND:' + dataFound + '</time></returnValue>'
                return
            }    
                    
            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:Unknown</time></returnValue>'
            return
                    
                
                            
            
            break;
            case "GET":
            
                
            render '<?xml version=\"1.0\" encoding=\"UTF-8\" ?><returnValue><time>ERROR:GET not supported</time></returnValue>'
            
            
            
            
            break
        }
    }    
    
    
def postCaseRecordToBrims = {
    def newPost = new HTTPBuilder("https://brims.vai.org/cdrservicesbeta/api/collectionimport/bpv")
    def caseRecordXml = caseRecordService.getCaseXMLRecord(params.caseid)
    def result
    try {
    newPost.request(Method.POST, ContentType.XML) { 
            request.addHeader("Authorization", "Basic YmV0YS5jZHJzZXJ2aWNlOmxlaWRvJA==")
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
/*            if (result==202) {
                render "Case record "+params.caseid+" posted to BRIMS successfully"
            } else {
                render "Case record "+params.caseid+" POST to BRIMS failed. Error code "+result
            } */
            return result
    }    
    
    
def getCaseRecordFromBrims = {
    def caseId = params.id
    if (caseId==null) {
        caseId = params.caseId
    }
    caseId = caseId.trim()
    def brimsService = AppSetting.findByCode('CBR_SERVICE_URL')?.bigValue
    def brimsServiceAuth = AppSetting.findByCode('CBR_SERVICE_AUTH')?.bigValue    
    def newPost = new HTTPBuilder(brimsService+"/cases/"+caseId)

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
                //render contentType: "text/xml", text: caseRecord
                //render(template: "caseRecordOutput", model: [caseRecord:caseRecord])
                render caseRecord
            } else {
                render "Failed to get the case record "+caseId+", Error code: "+result//+params.caseid+" POST to BRIMS failed. 
            }
            return caseRecord
    }

def getSpecimenRecordFromBrims = {
    def specimenId = params.id
    if (specimenId==null) {
        specimenId = params.specimenId
    }    
    specimenId = specimenId.trim()
    def brimsService = AppSetting.findByCode('CBR_SERVICE_URL')?.bigValue
    def brimsServiceAuth = AppSetting.findByCode('CBR_SERVICE_AUTH')?.bigValue        
    def newPost = new HTTPBuilder(brimsService+"/specimens/"+specimenId)

    def result
    def specimenRecord// = new StringBuffer()
    try {
    newPost.request(Method.GET, ContentType.TEXT) { 
            request.addHeader("Authorization", brimsServiceAuth)
            request.addHeader("Content-Type","application/json")
            //body = ""
            response.success = { resp, reader ->
                result = resp.status
                specimenRecord = reader.getText()
            }
            response.failure = { resp, reader ->
                result = resp.status
            }            
        }
    } catch (HttpResponseException hre) {
                    hre.printStackTrace()
        }
            if (result==200) {
                //render "Case record sucess \n"+specimenRecord//"+params.caseid+" posted to BRIMS successfully"
                //render contentType: "text/xml", encoding: "UTF-8", text: specimenRecord
                render specimenRecord
            } else {
                render "Failed to get the specimen record "+specimenId+", Error code "+result//+params.caseid+" POST to BRIMS failed. 
            }
            return specimenRecord
    }

def getSpecimensByIdentifiersFromBrims = {
    def brimsService = AppSetting.findByCode('CBR_SERVICE_URL')?.bigValue
    def brimsServiceAuth = AppSetting.findByCode('CBR_SERVICE_AUTH')?.bigValue            
    def newPost = new HTTPBuilder(brimsService+"/specimens/searchbyidentifiers")

    def result
    def specimens// = new StringBuffer()
    def newId = (params.identifiers).split(",")
    def identifiers = []
    newId.each {
        identifiers.add('"'+it.trim()+'"')
    }

    try {
    newPost.request(Method.POST, ContentType.TEXT) { 
            request.addHeader("Authorization", brimsServiceAuth)
            request.addHeader("Content-Type","application/json")
            body = identifiers//'["GTEX-000791-0125", "GTEX-000792-0125", "3019715", "3019876"]'
            response.success = { resp, reader ->
                result = resp.status
                specimens = reader.getText()
            }
            response.failure = { resp, reader ->
                result = resp.status
            }            
        }
    } catch (HttpResponseException hre) {
                    hre.printStackTrace()
        }

            if (result==200) {
                //render "Case record sucess \n"+specimenRecord//"+params.caseid+" posted to BRIMS successfully"
                //render contentType: "text/xml", encoding: "UTF-8", text: specimens
                render specimens
            } else {
                render "No specimen identified by the identifiers provided. Error code "+result//+params.caseid+" POST to BRIMS failed. 
            }
            return specimens
    }    
 
    def getLDACCPublicDonorId = {
        switch(request.method){
            case "GET":
                def publicDonorId
                try
                {
                    publicDonorId = ldaccService.getPublicDonorId(params.caseid)
                    if (publicDonorId.indexOf("Donor not found") > -1 || publicDonorId.indexOf("Case ID not provided") > -1) {
                        response.status = 404
                    }
                }
                catch(Exception e){
                    e.printStackTrace()
                    response.status = 500
                }                
                render contentType: "text/xml", encoding: "UTF-8", text: publicDonorId
                break
        }

        
    }
    
}
