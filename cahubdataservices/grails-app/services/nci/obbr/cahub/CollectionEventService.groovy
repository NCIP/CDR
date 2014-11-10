package nci.obbr.cahub

import nci.obbr.cahub.util.AppSetting
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*
import grails.converters.deep.*
import org.codehaus.groovy.grails.commons.ApplicationHolder 

class CollectionEventService {

    static transactional = true
    
    def sendMailService

    def deleteCase(rawHttpRequestBody) {
        def payload = new XmlSlurper().parseText(rawHttpRequestBody)
        def errors

        def caseId = payload.caseId.text()
        def caseRecord = CaseRecord.findByCaseId(caseId)

        if(caseRecord) {
            if(caseRecord.caseStatus.equals(CaseStatus.findByCode("COLL")) ||
                (caseRecord.caseStatus.equals(CaseStatus.findByCode("QA")) && payload.studyCode.text() == "BRN")) {

                log.info("Case status is COLLECTED! Able to Delete...")
                log.info(" caseRecord.CaseId:     " +  caseRecord.caseId)
                log.info(" caseRecord.caseStatus: " +  caseRecord.caseStatus.toString())
                log.info(" payload.studyCode:     " +  payload.studyCode.text())

                caseRecord.specimens.each() {
                    log.info("deleting specimen ID: " + it.specimenId)
                    it.delete()
                }

                log.info("deleting Case ID: " + caseRecord.toString())
                caseRecord.delete()
            } else {
                log.info("Case status is NOT COLLECTED! Unable to Delete.")
                log.info(" caseRecord.CaseId:     " +  caseRecord.caseId)
                log.info(" caseRecord.caseStatus: " +  caseRecord.caseStatus.toString())
                log.info(" payload.studyCode:     " +  payload.studyCode.text())

                errors = "Case status is not \"COLL\" or (\"QA\" + \"BRN\" study) for case [${caseId}]"
                log.error("Case record DELETE failed")
                throwRuntimeError(errors, rawHttpRequestBody, "Case record DELETE FAIL:")
            }
        } else {
            errors = "Case [${caseId}] not found!"
            log.error("Case record DELETE failed")
            throwRuntimeError(errors, rawHttpRequestBody, "Case record DELETE FAIL:")
        }
    }

    def updateCase(rawHttpRequestBody) {
        def payload = new XmlSlurper().parseText(rawHttpRequestBody)
        def errors
        def caseRecord = CaseRecord.findByCaseId(payload.caseId.text())
        if (caseRecord) {
            def specimenList = payload.specimens.children()

            specimenList.each() {
                log.info("Updating Specimen ID: " +it.'@id'.text())
                def specimenRecord = SpecimenRecord.findBySpecimenId(it.'@id'.text())
                if(specimenRecord) {
                    specimenRecord.tissueType               = AcquisitionType.findByCode(it.tissueType.'@code'.text().toUpperCase())
                    specimenRecord.provisionalTissueType    = AcquisitionType.findByCode(it.tissueType.'@code'.text().toUpperCase())
                    //specimenRecord.tissueLocation           = AcquisitionLocation.findByCode(it.tissueLocation.'@code'.text().toUpperCase())
                    specimenRecord.fixative                 = Fixative.findByCode(it.fixative.'@code'.text().toUpperCase())
                    specimenRecord.parentSpecimen           = SpecimenRecord.findBySpecimenId(it.parentSpecimen.'@id'.text())
                    specimenRecord.tumorStatus              = TumorStatus.findByCode(it.tumorStatus.'@code'.text().toUpperCase())
                    specimenRecord.wasConsumed              = it.wasConsumed.text()
                    specimenRecord.internalComments         = it.comment.text()
                    specimenRecord.protocol                 = Protocol.findByCode(it.treatmentGroup.'@code'.text().toUpperCase())
                    specimenRecord.containerType            = ContainerType.findByCode(it.container.'@code'.text().toUpperCase())

                    if(specimenRecord.validate()) {
                        specimenRecord.save(failOnError:true)
                    } else {
                        log.info("Populating errors object for: " +it.'@id'.text())
                        errors = specimenRecord.errors
                        log.error("specimenRecord UPDATE failed for case "+ payload.caseId.text() + " Specimen: " + it.'@id'.text())
                        throwRuntimeError(errors, rawHttpRequestBody, "UPDATE FAIL:")
                    }
                } else {
                    errors = "Specimen ID [${it.'@id'.text()}] not found for Case [${payload.caseId.text()}]"
                    log.error("specimenRecord UPDATE failed for case " +payload.caseId.text()+ " Specimen ID not found!: " + it.'@id'.text())
                    throwRuntimeError(errors, rawHttpRequestBody, "Specimen record UPDATE FAIL:")
                }
            }

            log.error("The UPD method is only partially implemented -- Specimens are updated, but not the case.")
            // @TODO Resolve not-null property represents transient value when updating parent record
            // updateCaseRecord(caseRecord, payload)
        }
    }
    /* @TODO: Comment this out and test -- I think it's "cruft" - Tabor */
    def updateCaseRecord(caseRecord, payload) {
        def bss = getBSS(payload.bssCode.text(), payload)
        caseRecord = CaseRecord.findByCaseId(payload.caseId.text())
        if(caseRecord) {
            log.info("Updating Case ID: " + payload.caseId.text())
            caseRecord.study                = Study.findByCode(payload.studyCode.text())
            caseRecord.bss                  = bss
            caseRecord.caseStatus           = CaseStatus.findByCode(payload.collectionType.text().toUpperCase())
            caseRecord.caseCollectionType   = CaseCollectionType.findByCode(payload.collectionType.text())
            caseRecord.kitList              = payload.kitsUsed.text()
            caseRecord.primaryTissueType    = AcquisitionType.findByCode(payload.primaryTissueType.'@code'.text().toUpperCase())
            caseRecord.parentCase           = CaseRecord.findByCaseId(payload.parentCaseId.text())

            if(caseRecord.validate()) {
                caseRecord.save(failOnError:true)
            } else {
                errors = caseRecord.errors
                log.error("caseRecord UPDATE failed for case "+ payload.caseId.text())
                throwRuntimeError(errors, payload, "UPDATE FAIL:")
            }
        }
    }

    def createCaseRecord(rawHttpRequestBody) {
        //not sure why this is here anymore, but the call below took a while to figure out
        //so leaving for historians...
        //instantiate new specimen records from a shipping event
        //def specimenIdList = payload.specimens.children().collect{it.'@id'}
        def payload = new XmlSlurper().parseText(rawHttpRequestBody)

        def errors
        def defaultCaseStatus
        def studyCode = payload.studyCode.text()

        def isBRNCase = "BRN".equals(studyCode);
        def isBMSCase = "BMS".equals(studyCode);
        def isGTEXCase = "GTEX".equals(studyCode);
        
        def phase

        if(isBMSCase || isGTEXCase) {
            defaultCaseStatus = CaseStatus.findByCode("COLL") //set collected status
        } else if(isBRNCase) {
            defaultCaseStatus = CaseStatus.findByCode("QA") //set collected status
        }
        
        if (isGTEXCase){
            //stamp case with active GTEx phase
            phase = StudyPhase.findByCode(AppSetting.findByCode('ACTIVE_GTEX_PHASE').value)
        }        

        def bss = getBSS(payload.bssCode.text(), rawHttpRequestBody)
        def caseRecord = new CaseRecord(caseId: payload.caseId.text(),
            study: Study.findByCode(studyCode),
            bss: bss,
            caseStatus: defaultCaseStatus,
            caseCollectionType: CaseCollectionType.findByCode(payload.collectionType.text()),
            kitList: payload.kitsUsed.text(),
            cdrVer: ApplicationHolder.application.metadata['app.version'],
            primaryTissueType: AcquisitionType.findByCode(payload.primaryTissueType.'@code'.text().toUpperCase()),
            parentCase: CaseRecord.findByCaseId(payload.parentCaseId.text()),
            phase: phase
            //TODO: Add validation to check if parentCase exists, if parent case is OPO, etc.
        )
        
        if (isBMSCase) {
            if (!payload.parentCaseId.text()) {
                caseRecord.errors.rejectValue('parentCase', 'Parent case ID is required')
                errors = caseRecord.errors
                log.error("caseRecord POST failed")
                throwRuntimeError(errors, rawHttpRequestBody)
            } else if (!caseRecord.parentCase) {
                caseRecord.errors.rejectValue('parentCase', 'Parent case does not exist')
                errors = caseRecord.errors
                log.error("caseRecord POST failed")
                throwRuntimeError(errors, rawHttpRequestBody)
            }
        }
        
        if(caseRecord.validate()) {
            caseRecord.save(failOnError:true,flush:true)
        } else {
            errors = caseRecord.errors
            log.error("caseRecord POST failed")
            throwRuntimeError(errors, rawHttpRequestBody)
        }
        
        // set a default specimen id format value in order to throw an error in the case the
        // required BMS or GTEX study format is not stored in ST_APPSETTING (and returns null)
        def specimenIDFormat = "none"
        def parentCaseSpecimens = []

        if(isBMSCase) {
            specimenIDFormat = AppSetting.findByCode("BMS_SPECIMENID_FORMAT")?.value
            def parentCaseId = caseRecord.parentCase?.caseId
            if(parentCaseId) {
                def tmpSList = SpecimenRecord.findAllByCaseRecord(CaseRecord.findByCaseId(parentCaseId))
                tmpSList.each() {
                    //get all the specimens ending in 25
                    if(it.gtexSequenceNum().endsWith('25')) {
                        parentCaseSpecimens.add(it)
                    }
                }
            }
        } else if(isGTEXCase) {
            specimenIDFormat = AppSetting.findByCode("GTEX_SPECIMENID_FORMAT")?.value
        }

        def specimenId
        def specimenRecord
        def parentSpecimen
        def tissueTypeCode

        def specimenList = payload.specimens.children()
        specimenList.each() {
            specimenId = it.'@id'.text()
            if(specimenIDFormat && ("none".equals(specimenIDFormat) || specimenId.matches(specimenIDFormat))) {
                if(isBMSCase) {
                    tissueTypeCode = it.tissueType.'@code'.text().toUpperCase()
                    parentSpecimen = parentCaseSpecimens.find{it.tissueType.code == tissueTypeCode}
                } else {
                    parentSpecimen = SpecimenRecord.findBySpecimenId(it.parentSpecimen.'@id'.text())
                }

                specimenRecord = new SpecimenRecord(
                    specimenId: specimenId,
                    inQuarantine: true,
                    caseRecord:caseRecord,
                    tissueType: AcquisitionType.findByCode(it.tissueType.'@code'.text().toUpperCase()),
                    provisionalTissueType: AcquisitionType.findByCode(it.tissueType.'@code'.text().toUpperCase()),
                    //tissueLocation: AcquisitionLocation.findByCode(it.tissueLocation.'@code'.text().toUpperCase()),
                    fixative: Fixative.findByCode(it.fixative.'@code'.text().toUpperCase()),
                    //old way below
                    //parentSpecimen: SpecimenRecord.findBySpecimenId(it.parentSpecimen.'@id'.text()),
                    //new way
                    parentSpecimen: parentSpecimen,
                    tumorStatus: TumorStatus.findByCode(it.tumorStatus.'@code'.text().toUpperCase()),
                    wasConsumed: it.wasConsumed.text(),
                    internalComments: it.comment.text(),
                    protocol: Protocol.findByCode(it.treatmentGroup.'@code'.text().toUpperCase()),
                    containerType: ContainerType.findByCode(it.container.'@code'.text().toUpperCase())                                        
                )

                if(specimenRecord.validate()) {
                    specimenRecord.save(flush:true)
                } else {
                    errors = specimenRecord.errors
                    log.error("specimenRecord POST failed")
                    throwRuntimeError(errors, rawHttpRequestBody)
                }
            } else {
                errors = "Specimen ID [${specimenId}] does not match the required format."
                log.error("GTEX specimenRecord POST failed")
                throwRuntimeError(errors, rawHttpRequestBody)
            }
        }

        return errors
    }

    def getBSS(bssCode, payload) {
        def bss = BSS.findByCode(bssCode)
        //println bss
        if(!bss || !"RPCI".startsWith(bssCode?.toUpperCase())) {
            if(!bss?.protocolSiteNum && !bss?.parentFacility?.protocolSiteNum) { //check if parent facility has protocolSiteNum
                log.error("BSS code invalid")
                throwRuntimeError("Invalid BSS code [${bssCode}]. Check for missing protocolSiteNum in CDR.", payload)
            }
        }

        return bss
    }

    //[NOTE]: throw runtimeexeption is required to trigger rollback
    def throwRuntimeError(errors, xml, subject = "FAIL:") {
        log.error(errors)
        sendMailService.sendServiceEventEmail(subject, errors.toString() +"\n\n"+ xml)

        throw new RuntimeException(
            "<?xml version='1.0'?><collectionEvent><status>1</status><message>"
            + errors.toString()
            +"</message></collectionEvent>"
        )
    }
}
