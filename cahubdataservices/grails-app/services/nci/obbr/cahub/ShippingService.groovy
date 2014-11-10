package nci.obbr.cahub

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*
import java.text.SimpleDateFormat

class ShippingService {

    static transactional = true
    
    def sendMailService

    def saveShippingEvent(rawHttpRequestBody)  {

        def errors
        def payload = new XmlSlurper().parseText(rawHttpRequestBody)
        
        if (!("SHIPPINGEVENT".equals(payload.name().toUpperCase()))) {
            log.error("shippingEvent POST failed")
            throwRuntimeError("An event other than shipping event was posted. Please post a shipping event.", rawHttpRequestBody)
        }

        def sender = BSS.findByCode(payload.sender.text().toUpperCase())
        def recipient = BSS.findByCode(payload.recipient.text().toUpperCase())

        if(!sender) {
            sender = Organization.findByCode(payload.sender.text().toUpperCase())
        }

        if(!recipient) {
            recipient = Organization.findByCode(payload.recipient.text().toUpperCase())
        }
        
        
        def shipDate
        if (payload?.shipDateTime?.text())
        {
            shipDate = new Date().parse("dd-MMM-yy HH:mm:ss", payload.shipDateTime.text()) // 06-Jul-12 11:49:00
        }
        
        if (payload?.inspectionDateTime?.text()){
            shipDate = new Date().parse("dd-MMM-yy HH:mm:ss", payload.inspectionDateTime.text()) // 06-Jan-14 11:37:00
        }
        
        //create new shipping event
        def caseRecord
        def caseRecordList = []
//        if ("USED".equals(payload.eventType.text().toUpperCase()) || "REDISTRIBUTED".equals(payload.eventType.text().toUpperCase())) {
            if (payload.shippingContainers!="") {
            def isBatchShipment = "No"
                if (payload.shippingContainers.cases.caseId.size() > 1) {
                    isBatchShipment = "Yes"
                }
                def kitType = ""
                def identifier = ""
                payload.shippingContainers.shippingContainer.each{
                    if (kitType.equals("")) {
                        kitType = it.kitType.text()
                    } else {
                        kitType = kitType + "," + it.kitType.text()
                    }
                    
                    if (identifier.equals("")) {
                        identifier = it.'@identifier'.text() 
                    } else {
                        identifier = identifier + "," +it.'@identifier'.text()   
                    }
                }
                
                payload.shippingContainers.cases.caseId.each {
                    
                caseRecord = CaseRecord.findByCaseId(it.text())
                caseRecordList.add(caseRecord)
                if (caseRecord) {
                
                    def shippingEvent = new ShippingEvent(
                        bio4dEventId: payload.'@id'.text(),
                        study: Study.findByCode(payload.studyCode.text().toUpperCase()),
                        sender: sender.toString(),
                        recipient: recipient.toString(),
                        courier: payload.courier.text(),
                        trackingNumber: payload.trackingNumber.text(),
                        shipDateTime: shipDate,
                        sendingUser: payload.sendingUser.text(),
                        publicComments: payload.comments.text(),
                        shippingContentType: ShippingContentType.findByCode(it.manifest?.contents?.'@type'.text().toUpperCase()),
                        shippingEventType: ShippingEventType.findByCode(payload.eventType.text().toUpperCase()),
                        caseId: it.text(),
                        isBatchShipment: isBatchShipment,
                        kitType:kitType,
                        identifier:identifier
                    )
                
                        if(shippingEvent.validate()) {
                                shippingEvent.save(flush:true)
                        } else {
                            log.error("shippingEvent POST failed")
                            throwRuntimeError(shippingEvent.errors, rawHttpRequestBody)
                        }
                    
                    } else {
                            log.error("shippingEvent POST failed")
                            throwRuntimeError("Case [${it.text()}] does not exist.", rawHttpRequestBody)
                    }
                }
                
//                if(shippingEvent.validate()) {

                def contents = payload.manifest.contents
//                def caseId = shippingEvent.caseId
//                if (!caseId) {
//                    caseId = payload.manifest.contents?.caseId?.text()
//                }
                def retrievedShippingEvent
                payload.shippingContainers.shippingContainer.each{
                    contents = it.manifest.contents
                    if("TISSUE".equals(contents.'@type'.text().toUpperCase())) {
                        def specimenId
                        def specimenRecord
                        contents.specimens.specimen.each {
                            specimenId = it.'@id'.text()
                            if ("SLIDE".equals(it.container.'@code'.text().toUpperCase())) {
                                    def slideRecord = SlideRecord.findBySlideId(it.'@id'.text())
                                    //add shipping event to slideRecord
                                    if(slideRecord) {
                                        def slideCaseId = slideRecord?.specimenRecord?.caseRecord?.caseId
                                        def caseSlideMatch = false
                                        caseRecordList.each{
                                            if (slideCaseId.equals(it.caseId)) {
                                                caseSlideMatch = true
                                            }
                                        }
                                        if (caseSlideMatch) {
                                            retrievedShippingEvent = ShippingEvent.findByCaseIdAndBio4dEventId(slideRecord.specimenRecord.caseRecord.caseId, payload.'@id'.text())
                                            slideRecord.addToShipEvents(retrievedShippingEvent)
                                            //save slideRecord
                                            slideRecord.save(flush:true)
                                            //add saved slideRecord to parent shippingEvent
                                            retrievedShippingEvent.addToSlides(slideRecord).save(flush:true)
                                        } else {
                                            log.error("slideRecord POST failed")
                                            throwRuntimeError("Slide [${it.'@id'.text()}] does not belong to the Case(s) ${caseRecordList}.", rawHttpRequestBody)
                                        }

                                    } else {
                                        log.error("slideRecord POST failed")
                                        throwRuntimeError("Service error: Attempting to ship slide(s) that do not exist in CDR yet. Try sending a Processing Event first.", rawHttpRequestBody)
                                    }                                
                            } else {
                                specimenRecord = SpecimenRecord.findBySpecimenId(specimenId)
                                if(specimenRecord) {
                                        def specimenCaseId = specimenRecord?.caseRecord?.caseId
                                        def caseSpecimenMatch = false
                                        caseRecordList.each{
                                            if (specimenCaseId.equals(it.caseId)) {
                                                caseSpecimenMatch = true
                                            }
                                        }                                        
                                        if (caseSpecimenMatch) {
                                    retrievedShippingEvent = ShippingEvent.findByCaseIdAndBio4dEventId(specimenRecord.caseRecord.caseId, payload.'@id'.text())
                                    if(specimenRecord.validate()) {
                                        //add shipping event to specimenRecord
                                        specimenRecord.addToShipEvents(retrievedShippingEvent)
                                        //specimenRecord.containerType = ContainerType.findByCode(it.container.'@code'.text().toUpperCase())
                                        //save specimenRecord
                                        specimenRecord.save(flush:true)
                                        retrievedShippingEvent.shippingContentType = ShippingContentType.findByCode(contents.'@type'.text().toUpperCase())
                                        //add saved specimenRecord to parent shippingEvent
                                        retrievedShippingEvent.addToSpecimens(specimenRecord).save(flush:true)
                                    } else {
                                        log.error("specimenRecord POST failed")
                                        throwRuntimeError(specimenRecord.errors, rawHttpRequestBody)
                                    } 
                                } else {
                                            log.error("specimenRecord POST failed")
                                            throwRuntimeError("Specimen [${specimenId}] does not belong to the Case(s) ${caseRecordList}.", rawHttpRequestBody)
                                }
                                } else {
                                    def derivative = DerivativeRecord.findByDerivativeId(specimenId)
                                    def nDerivative = derivative
                                    if (nDerivative!=null) {
                                        while (nDerivative.parentDerivative) {
                                            nDerivative = nDerivative.parentDerivative
                                        }
                                        specimenRecord = nDerivative.parentSpecimen
                                        def specimenCaseId = specimenRecord?.caseRecord?.caseId
                                        def caseSpecimenMatch = false
                                        caseRecordList.each{
                                            if (specimenCaseId.equals(it.caseId)) {
                                                caseSpecimenMatch = true
                                            }
                                        }
                                        if (caseSpecimenMatch) {
                                            retrievedShippingEvent = ShippingEvent.findByCaseIdAndBio4dEventId(specimenCaseId, payload.'@id'.text())
                                            if(derivative.validate()) {
                                                derivative.addToShippingEvents(retrievedShippingEvent)
                                                derivative.save(flush:true)
                                                retrievedShippingEvent.shippingContentType = ShippingContentType.findByCode(contents.'@type'.text().toUpperCase())
                                                retrievedShippingEvent.addToDerivatives(derivative).save(flush:true)
                                            } else {
                                                log.error("Derivative POST failed")
                                                throwRuntimeError(derivative.errors, rawHttpRequestBody)
                                            }                                        
                                        } else {
                                            log.error("specimenRecord POST failed")
                                            throwRuntimeError("Specimen/Derivative [${specimenId}] does not belong to the Case(s) ${caseRecordList}.", rawHttpRequestBody)
                                        }
                                        
                                    
                                    } else {
                                        log.error("specimenRecord POST failed")
                                        throwRuntimeError("Specimen/Derivative [${specimenId}] does not exist.", rawHttpRequestBody)
                                    
                                    }
                                }
                            }
                        }
/*                        if (retrievedShippingEvent.kitType!=null){
                            retrievedShippingEvent.kitType = retrievedShippingEvent.kitType + "," +it.kitType.text()
                        } else {
                            retrievedShippingEvent.kitType = it.kitType.text()
                        }

                        if (retrievedShippingEvent.identifier!=null){
                            retrievedShippingEvent.identifier = retrievedShippingEvent.identifier + "," +it.'@identifier'.text()
                        } else {
                            retrievedShippingEvent.identifier = it.'@identifier'.text()
                        }*/
                        //save corresponding shippingEvent
                        retrievedShippingEvent.save(flush:true)
                    }
                    
                }
/*          } else {
//            log.error("shippingEvent POST failed")
//            throwRuntimeError(shippingEvent.errors, rawHttpRequestBody)
//          }
                
//        }*/
            
        } else {
            def trackingNumber = payload.trackingNumber?.text()
            if ("INSPECTION".equals(payload.eventType.text().toUpperCase())) {
                trackingNumber = "N/A"
            }
            def shippingEvent = new ShippingEvent(
                bio4dEventId: payload.'@id'.text(),
                study: Study.findByCode(payload.studyCode.text().toUpperCase()),
                sender: sender.toString(),
                recipient: recipient.toString(),
                courier: payload.courier.text(),
                trackingNumber: trackingNumber,
                shipDateTime: shipDate,
                sendingUser: payload.sendingUser.text(),
                publicComments: payload.comments.text(),
                shippingContentType: ShippingContentType.findByCode(payload.manifest?.contents?.'@type'.text().toUpperCase()),
                shippingEventType: ShippingEventType.findByCode(payload.eventType.text().toUpperCase()),
                caseId: payload.manifest.contents?.caseId?.text() //Case ID won't be present for unused kits
                //caseId: payload.shippingContainers.cases.caseId?.text() //Case ID won't be present for unused kits
            )

        if(shippingEvent.validate()) {
            def contents = payload.manifest.contents
//            def caseId = shippingEvent.caseId
//            if (!caseId) {
//                caseId = payload.shippingContainers.cases.caseId?.text()
//            }

        if ("INSPECTION".equals(payload.eventType.text().toUpperCase())) {
            shippingEvent.sourceShipEvt = payload.sourceShippingEvent.'@id'?.text()
            shippingEvent.sendingUser = payload.inspectingUser.text()
            payload.discrepancies.discrepancy.each {
                ShipDiscrepancy shipDiscrepancy = new ShipDiscrepancy();
                shipDiscrepancy.discrepancyId = it.'@id'?.text();
                shipDiscrepancy.name = it.name.text();
                shipDiscrepancy.value = it.value.text();
                shipDiscrepancy.save(failOnError:true,flush:true);
                shippingEvent.addToDiscreps(shipDiscrepancy).save(flush:true);
                shipDiscrepancy.setShipEvent(shippingEvent);
                contents = it.manifest.contents

//                if("TISSUE".equals(contents.'@type'.text().toUpperCase())) {
//                shippingEvent.shippingContentType = ShippingContentType.findByCode("TISSUE")
                def specimenId
                def specimenRecord
                contents.specimens.specimen.each {
                    specimenId = it.'@id'.text()
                    specimenRecord = SpecimenRecord.findBySpecimenId(specimenId)
                    if(specimenRecord) {
                        if(specimenRecord.validate()) {
                            specimenRecord.addToShipEvents(shippingEvent)
                            specimenRecord.addToDiscreps(shipDiscrepancy)
                            specimenRecord.save(flush:true)
                            shippingEvent.addToSpecimens(specimenRecord).save(flush:true)
                            shipDiscrepancy.addToSpecimens(specimenRecord).save(flush:true)
                            shippingEvent.caseId = specimenRecord.caseRecord.caseId
                        } else {
                            log.error("specimenRecord POST failed")
                            throwRuntimeError(specimenRecord.errors, rawHttpRequestBody)
                        }
                    } else {
                        log.error("specimenRecord POST failed")
                        throwRuntimeError("Specimen [${specimenId}] does not exist.", rawHttpRequestBody)
                    }
                }

                //save corresponding shippingEvent
                shippingEvent.save(flush:true)
//            }                
              contents = ""
                        
                        
            }
        } else {

            if("KIT".equals(contents.'@type'.text().toUpperCase())) {
                def kits = contents.children()
                def kitRecord

                kits.each() {
                    //if kit doesn't exist, create a new one
                    if(!KitRecord.findByKitId(it.'@id'.text())) {
                        
                        def itDate
                        if (it.expirationDate?.text())
                        {
                            itDate = new Date().parse("dd-MMM-yy HH:mm:ss", it.expirationDate.text())
                        }
                        kitRecord = new KitRecord(
                            kitId: it.'@id'.text(),
                            study: Study.findByCode(it.studyCode.text().toUpperCase()),
                            bss: BSS.findByCode(it.bssCode.text().toUpperCase()),
                            kitType: KitType.findByCode(it.kitType.text().toUpperCase()),
                            expirationDate: itDate,
                            identifier: it.'@identifier'.text(),
                            publicComments: it.comments.text()
                        )
                    } else { //else kit exists, so fetch it
                        kitRecord = KitRecord.findByKitId(it.'@id'.text())
                    }

                    if(kitRecord.validate()) {
                        //add shipping event to kitRecord
                        kitRecord.addToShipEvents(shippingEvent)
                        //save kitRecord
                        kitRecord.save(flush:true)
                        //add saved kitRecord to parent shippingEvent
                        shippingEvent.addToKits(kitRecord).save(flush:true)
                    } else {
                        log.error("kitRecord POST failed")
                        throwRuntimeError(kitRecord.errors, rawHttpRequestBody)
                    }
                }
                //save corresponding shippingEvent
                shippingEvent.save(flush:true)
            }
            
                        

            if("TISSUE".equals(contents.'@type'.text().toUpperCase())) {
                def specimenId
                def specimenRecord
                contents.specimens.specimen.each {
                    specimenId = it.'@id'.text()
                    specimenRecord = SpecimenRecord.findBySpecimenId(specimenId)
                    if(specimenRecord) {
                        if(specimenRecord.validate()) {
                            //add shipping event to kitRecord
                            specimenRecord.addToShipEvents(shippingEvent)
                            //specimenRecord.containerType = ContainerType.findByCode(it.container.'@code'.text().toUpperCase())
                            //save kitRecord
                            specimenRecord.save(flush:true)
                            //add saved kitRecord to parent shippingEvent
                            shippingEvent.addToSpecimens(specimenRecord).save(flush:true)
                        } else {
                            log.error("specimenRecord POST failed")
                            throwRuntimeError(specimenRecord.errors, rawHttpRequestBody)
                        }
                    } else {
                        log.error("specimenRecord POST failed")
                        throwRuntimeError("Specimen [${specimenId}] does not exist.", rawHttpRequestBody)
                    }
                }

                //save corresponding shippingEvent
                shippingEvent.save(flush:true)
            }

            if("SLIDES".equals(contents.'@type'.text().toUpperCase()) &&
                "PRENOTE".equals(payload.eventType.text().toUpperCase())) {
                def slideRecord
                contents.specimens.specimen.each {
                    slideRecord = addSlide(it.slide.'@id'.text(), it.'@id'.text(), recipient.toString(),
                        it.comments.text(), payload.sender.text().toUpperCase(), rawHttpRequestBody)

                    slideRecord.addToShipEvents(shippingEvent).save(flush:true)
                    shippingEvent.addToSlides(slideRecord).save(flush:true)

                    attachImage(it.image.'@id'.text(), slideRecord, it.comments.text(), rawHttpRequestBody)
                }

                shippingEvent.save(flush:true)
            } else if("SLIDES".equals(contents.'@type'.text().toUpperCase()) &&
                !"PRENOTE".equals(payload.eventType.text().toUpperCase())) {
                //save corresponding shippingEvent when not PRENOTE type
                //we want to create a new shipping event based on existing slides records
                //created by the preceding PRENOTE event.

                contents.specimens.specimen.each {
                    def slideRecord = SlideRecord.findBySlideId(it.slide.'@id'.text())
                    //add shipping event to slideRecord
                    if(slideRecord) {
                        slideRecord.addToShipEvents(shippingEvent)
                        //save slideRecord
                        slideRecord.save(flush:true)
                        //add saved slideRecord to parent shippingEvent
                        shippingEvent.addToSlides(slideRecord).save(flush:true)
                    } else {
                        log.error("slideRecord POST failed")
                        throwRuntimeError("Service error: Attempting to ship slide(s) that do not exist in CDR yet. Try sending PRENOTIFICATION event first.", rawHttpRequestBody)
                    }
                }

                shippingEvent.save(flush:true)
            }
        }

          } else {
            log.error("shippingEvent POST failed")
            throwRuntimeError(shippingEvent.errors, rawHttpRequestBody)
        }              
      }
        return errors
    }

    def updateShippingEvent(rawHttpRequestBody){
        
        def errors
        def payload = new XmlSlurper().parseText(rawHttpRequestBody)        
        def retrievedShippingEvent
        
        if (!("SHIPPINGEVENT".equals(payload.name().toUpperCase()))) {
            log.error("shippingEvent POST failed")
            throwRuntimeError("An event other than shipping event was posted. Please post a shipping event.", rawHttpRequestBody)
        }
        
        if (payload.shippingContainers!="") {
        
        if (payload.shippingContainers.cases.caseId.size() > 1) {
            payload.shippingContainers.cases.caseId.each {
                retrievedShippingEvent = ShippingEvent.findAllByBio4dEventId(payload.'@id'?.text())
                if (retrievedShippingEvent) {
                    retrievedShippingEvent.each {
                        def shipEvent = it
                        if(!shipEvent.receivingUser && !shipEvent.receiptDateTime){
                            shipEvent.receivingUser = payload.receivingUser.text()
                            String pattern = "dd-MMM-yy hh:mm:ss";
                            shipEvent.receiptDateTime = Date.parse(pattern, payload.receiptDateTime.text())
                            payload.shippingContainers.shippingContainer.each {
                                def shipContainerId = it.'@id'?.text();
                                    it.discrepancies.discrepancy.each {
                                        ShipDiscrepancy shipDiscrepancy = new ShipDiscrepancy();
                                        shipDiscrepancy.discrepancyId = it.'@id'?.text();
                                        shipDiscrepancy.shippingContainerId = shipContainerId;
                                        shipDiscrepancy.name = it.name.text();
                                        shipDiscrepancy.value = it.value.text();
                                        shipDiscrepancy.save(failOnError:true,flush:true);
                                        shipEvent.addToDiscreps(shipDiscrepancy);
                                    }
                                }
                            }
                            shipEvent.save(flush:true)
                    }
                } else {
                        log.error("receiptEvent UPDATE failed")
                        throwRuntimeError("Service error: Matching shipping event not found. Please check shipping event ID.", rawHttpRequestBody)
                       }
            }
        } else {
        
        retrievedShippingEvent = ShippingEvent.findByBio4dEventId(payload.'@id'?.text())
        
        if(retrievedShippingEvent){
            
            if(!retrievedShippingEvent.receivingUser && !retrievedShippingEvent.receiptDateTime){
            
                retrievedShippingEvent.receivingUser = payload.receivingUser.text()
                String pattern = "dd-MMM-yy hh:mm:ss";
                retrievedShippingEvent.receiptDateTime = Date.parse(pattern, payload.receiptDateTime.text())
                payload.shippingContainers.shippingContainer.each {
                    def shipContainerId = it.'@id'?.text();
                    it.discrepancies.discrepancy.each {
                        ShipDiscrepancy shipDiscrepancy = new ShipDiscrepancy();
                        shipDiscrepancy.discrepancyId = it.'@id'?.text();
                        shipDiscrepancy.shippingContainerId = shipContainerId;
                        shipDiscrepancy.name = it.name.text();
                        shipDiscrepancy.value = it.value.text();
                        shipDiscrepancy.save(failOnError:true,flush:true);
                        retrievedShippingEvent.addToDiscreps(shipDiscrepancy);
                    }
                }

                retrievedShippingEvent.save(flush:true)

            
            } 
            else {
                 log.error("receiptEvent UPDATE failed")
                 throwRuntimeError("Service error: Matching shipping event already marked as received. Please check shipping event ID.", rawHttpRequestBody)                
            }
        }
            else {
                 log.error("receiptEvent UPDATE failed")
                 throwRuntimeError("Service error: Matching shipping event not found. Please check shipping event ID.", rawHttpRequestBody)
                 }
        }
        } else {
            retrievedShippingEvent = ShippingEvent.findByBio4dEventId(payload.'@id'?.text())

            if(retrievedShippingEvent){

                if(!retrievedShippingEvent.receivingUser && !retrievedShippingEvent.receiptDateTime){
                    retrievedShippingEvent.receivingUser = payload.receivingUser.text()
                    String pattern = "dd-MMM-yy hh:mm:ss";
                    retrievedShippingEvent.receiptDateTime = Date.parse(pattern, payload.receiptDateTime.text())
                    retrievedShippingEvent.save(flush:true)
                } 
                else {
                    log.error("receiptEvent UPDATE failed")
                    throwRuntimeError("Service error: Matching shipping event already marked as received. Please check shipping event ID.", rawHttpRequestBody)                
                }
            }
                else {
                    log.error("receiptEvent UPDATE failed")
                    throwRuntimeError("Service error: Matching shipping event not found. Please check shipping event ID.", rawHttpRequestBody)
                    }            
        }
        return errors
    }
    
    
    def addSlide(slideId, specimenId, orgCode, comments, createdBy, xml) {
        def slideRecord
        if(slideId) {
            slideRecord = SlideRecord.findBySlideId(slideId)
            if(!slideRecord) {
                def specimenRecord = SpecimenRecord.findBySpecimenId(specimenId)
                if(specimenRecord) {
                    slideRecord = new SlideRecord(
                        specimenRecord: specimenRecord,
                        slideId: slideId,
                        slideLocation: Organization.findByCode(orgCode),
                        publicComments: comments,
                        createdBy: createdBy
                    )

                    if(slideRecord.validate()) {
                        slideRecord.save(flush:true)
                    } else {
                        log.error("slideRecord POST failed")
                        throwRuntimeError(slideRecord.errors, xml)
                    }
                } else {
                    log.error("slideRecord POST failed")
                    throwRuntimeError("Specimen [${specimenId}] does not exist.", xml)
                }
            } else {
                log.info("Slide [${slideId}] already exists.")
            }
        } else {
            log.error("slideRecord POST failed")
            throwRuntimeError("No slide id provided.", xml)
        }

        return slideRecord
    }

    def attachImage(imageId, slideRecord, comments, xml) {
        if(imageId) {
            def imageRecord = ImageRecord.findBySlideRecord(slideRecord)
            if(!imageRecord) {
                //add image to parent slideRecord
                imageRecord = new ImageRecord(
                    slideRecord: slideRecord,
                    imageId: imageId,
                    publicComments: comments
                )

                if(imageRecord.validate()) {
                    imageRecord.save(flush:true)
                } else {
                    log.error("imageRecord POST failed")
                    throwRuntimeError(imageRecord.errors, xml)
                }
            } else {
                log.error("imageRecord POST failed")
                throwRuntimeError("Image [${imageRecord?.imageId}] already exists for Slide [${slideRecord?.slideId}]", xml)
            }
        } else {
            log.info("No image provided for Slide [${slideRecord?.slideId}]")
        }
    }

    //[NOTE]: throw runtimeexeption is required to trigger rollback
    def throwRuntimeError(errors, xml, subject = "FAIL:") {
        log.error(errors)
        sendMailService.sendServiceEventEmail(subject, errors.toString() +"\n\n"+ xml)

        throw new RuntimeException(
            "<?xml version='1.0'?><shippingEvent><status>1</status><message>"
            + errors.toString()
            +"</message></shippingEvent>"
        )
    }
}
