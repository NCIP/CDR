package nci.obbr.cahub

import groovy.sql.Sql

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datawarehouse.SpecimenDw
import nci.obbr.cahub.util.AppSetting
import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.datawarehouse.CaseDw

class CaseDwService {

    static transactional = true
    
    def gtexDonorVarsExportService
    def ldaccService
    def dataSource
    
    def deleteCaseDw(caseDwInstance) {
        def counter = 0
        if (caseDwInstance) {
            try {
                caseDwInstance.delete(flush: true)
                counter ++
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                log.error("${message(code: 'default.not.deleted.message', args: [message(code: 'caseDw.label', default: 'CaseDw'), params.id])}")
            }
        }
        return counter
    }
    
    def populateCaseDw(caseDwInstance) {
        
        groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
        
        def developmentFlag = false
        def gtexCases 
        def gtexCasesDev = []
        def caseDwIncludeStatus = AppSetting.findByCode('SPECIMEN_DW_INCLUDE_STATUS')?.value
        def specimenDwInstance = new SpecimenDw()
        def deathDate
        def saveBrainValues
        def localCaseMap
        def deathMap = [:]
        def caseRec
        BigDecimal numSpec = 0
        BigDecimal sumFixTime = 0
        def procDuration = 0
        def minFixTime=0, avgFixTime=0, maxFixTime=0
        def aliquotTimeFixedInterval, aliquotTimeFixedIntervalM
        def firstTissueRemovedDate 
        def firstTissueRemovedTime
        def firstTissueRemovedDateTime
        def hasBrain = "No"
        def frozenCollection
        def hasRin
        def tissueInFixative, firstTissueInFixative, lastTissueInFixative
        def timeInFixative, firstTimeInFixative, lastTimeInFixative
        def skipCase = false
        def localPMI
        int i = 0, j = 1
        def cross_clamp_time 
        def collectionDate 
        def startTime 
        def crossClampDateTime 
        def collectionStartTime
        def cc /* = ldaccService.calculateInterval(deathDate,crossClampDateTime) */
        BigDecimal xClampMillis
        
        if (developmentFlag) { /* c.study.code='GTEX' and (c.phase.code='BP' or c.phase.code='IP' or c.phase.code='PP')" */
            gtexCases = CaseRecord.executeQuery("select c from CaseRecord c where c.study.code='GTEX' and c.caseCollectionType.code='OPO'")
            println "gtexCases.size() " + gtexCases.size()
            println "caseDwIncludeStatus: " + caseDwIncludeStatus
            println "gtexCases[j].caseStatus: " + gtexCases[j].caseStatus
            while (i <= 100 ) {
                if (caseDwIncludeStatus.contains(gtexCases[j].caseStatus?.code+",")) {
                    gtexCasesDev.add(gtexCases[j])
                    i++
                }
                j++
            }
        } else {
            gtexCases = CaseRecord.findAllByStudy(Study.findByCode("GTEX"))
            gtexCases.each(){
                if (caseDwIncludeStatus.contains(it.caseStatus?.code+",")){ // the comma (,) is so that DATA, does not match DATACOMP,
                    gtexCasesDev.add(it)
                }
            }
        }
        gtexCasesDev.each() {
            caseRec = it // for preservation inside the specimen loop
//            if (caseRec.caseId == "GTEX-000353") { 
            caseDwInstance = CaseDw.findByCaseRecord(it)
            if (caseDwInstance) {
                caseDwInstance.specimens.each() {
                    it.delete()
                }
                caseDwInstance.delete(flush:true)
            }
            caseDwInstance = new CaseDw()

            caseDwInstance.caseId = it.caseId
            caseDwInstance.caseCollectionType = it.caseCollectionType
            
            minFixTime = (24 * 60 * 60 * 1000)
//              println "(24 * 60 * 60 * 1000) = " + minFixTime
            maxFixTime = 0
            skipCase = false
            hasRin = false
            frozenCollection = ""
            xClampMillis = 0
            avgFixTime = 0
            sumFixTime = 0
            numSpec = 0
            cross_clamp_time = 0
            deathMap = gtexDonorVarsExportService.getDeathMap(it)  
//            println "deathMap: " + deathMap
            hasBrain = "No"
//            println "cross_clamp_time: " + cross_clamp_time
            collectionDate = caseRec.tissueRecoveryGtex?.collectionDate
            startTime = caseRec.tissueRecoveryGtex?.collectionStartTime
            collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)
            deathDate = deathMap.get("deathDate")
            def p_start = ldaccService.calculateInterval(deathDate,collectionStartTime) // don't know if I need this...
            if(caseRec.caseCollectionType?.code == 'OPO') {
                cross_clamp_time = caseRec.tissueRecoveryGtex?.crossClampTime
                crossClampDateTime = ldaccService.getDateTimeComp(collectionStartTime, cross_clamp_time)
//            println "crossClampDateTime: " + crossClampDateTime
                cc = ldaccService.calculateInterval(deathDate,crossClampDateTime)
                if (cc[1]) {
//                    log.info( "caseRec: "+ caseRec + " cc: " + cc)
                    xClampMillis = cc[1].toLong()
                }
            }
            firstTissueRemovedDate = caseRec.tissueRecoveryGtex?.firstTissueRemovedDate
            firstTissueRemovedTime = caseRec.tissueRecoveryGtex?.firstTissueRemovedTime
            firstTissueRemovedDateTime = ldaccService.calculateDateWithTime(firstTissueRemovedDate,firstTissueRemovedTime)
            localPMI = ldaccService.calculateInterval(deathMap.get("deathDate"), deathMap.get("startTime"))
//            println "localPMI: " + localPMI
            caseDwInstance.caseRecord = caseRec
            caseDwInstance.dateCreated = new Date()
            caseDwInstance.lastUpdated = new Date()
            if (localPMI[1] != ''){
                caseDwInstance.PMI = localPMI[1].toLong() // localPMI[0] = eg "19 hour(s), 10 minute(s)", localPMI[1] = eg 69000000
            }
            caseDwInstance.save(failOnError: true)
            caseRec.specimens.each(){
//                println "it.specimenId: " + it.specimenId
                if (!it.fixative){
//                    log.error("Error!  Missing fixative for GTEx Specimen: " + it.specimenId + " tissueType: " + it.tissueType)
                }
                if (it.fixative?.code.equals('DICE')) {
                        frozenCollection = 'Frozen'
                }
                if (it.tissueType.code.equals("BRAIN")) {
                    hasBrain = "Yes"
                }
                if (it.fixative?.code.equals("XG") && it.tissueType.code != "BLOODW") {
                    specimenDwInstance = new SpecimenDw()
                    specimenDwInstance.caseDw = caseDwInstance
//                    println "specimenRecord: " + it.id + " " + it
                    specimenDwInstance.specimenRecord = it
                    specimenDwInstance.specimenId = it.specimenId
                    specimenDwInstance.tissueType = it.tissueType
                    specimenDwInstance.tissueLocation = it.tissueLocation
                    specimenDwInstance.fixative = it.fixative
//                    log.info("it.specimenId: " + it.specimenId + " deathDate: " + deathDate + " firstTissueRemovedDateTime: " + firstTissueRemovedDateTime + " it.aliquotTimeFixed: " + it.aliquotTimeFixed)
                    if (it.aliquotTimeFixed =~ /\d\d:\d\d/){
                        aliquotTimeFixedInterval = ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,it.aliquotTimeFixed)[1]
                        aliquotTimeFixedIntervalM = ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,it.aliquotTimeFixed)[0]
//                        println "caseRec.caseId: " + caseRec.caseId
                        if (aliquotTimeFixedInterval >= 86400000) { // more than 10 hours ischemic time
//                            println "specimenDwInstance.specimenId: " + specimenDwInstance.specimenId
//                            println "aliquotTimeFixedInterval: " + aliquotTimeFixedInterval + " deathDate: " + deathDate + " firstTissueRemovedDateTime: " + firstTissueRemovedDateTime + " it.aliquotTimeFixed: " + it.aliquotTimeFixed
//                            println "aliquotTimeFixedIntervalM: " + aliquotTimeFixedIntervalM + " ldaccService.calculateInterval: " + ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,it.aliquotTimeFixed)
                        }
                        tissueInFixative = it.tissueType
                        timeInFixative = it.aliquotTimeFixed
                    }
                    if (aliquotTimeFixedInterval) {
                        if (xClampMillis != 0) {
                            aliquotTimeFixedInterval = aliquotTimeFixedInterval - xClampMillis
                        }
                        if (aliquotTimeFixedInterval > 0) { // exclude cases with negative ischemic times as likely dirty data
                            specimenDwInstance.ischemicTime = aliquotTimeFixedInterval
                        } else {
                            skipCase = true
                        }
                    }
                    if (!skipCase){
                        sql.eachRow("select latestRin('"+it.specimenId+"') as latest_rin from dual", { row ->
//                            println "row: " + row
//                            println "row.datatype: " + row.getProperties()
                            specimenDwInstance.latestRin = row.latest_rin?.toDouble()
                        })
                        if (specimenDwInstance.latestRin) hasRin = true
                        if ((specimenDwInstance.ischemicTime) && specimenDwInstance.ischemicTime < minFixTime ) {
                            minFixTime = specimenDwInstance.ischemicTime
                            firstTissueInFixative = tissueInFixative
                            firstTimeInFixative = timeInFixative
                        }
//                          println "minFixTime:                      " + minFixTime
//                          println "specimenDwInstance.ischemicTime: " + specimenDwInstance.ischemicTime
                        if (specimenDwInstance.ischemicTime > maxFixTime ) {
                            maxFixTime = specimenDwInstance.ischemicTime
                            lastTissueInFixative = tissueInFixative
                        }
                        if (aliquotTimeFixedInterval) {
                            numSpec++
                            sumFixTime += aliquotTimeFixedInterval.toLong()
                        }
                        
                        specimenDwInstance.dateCreated = new Date()
//                          specCollection.add(specimenDwInstance)
//                          log.info( "Ending Specimen: " + specimenDwInstance )
                        specimenDwInstance.save(failOnError: true)
                        specimenDwInstance = new SpecimenDw()
                    } 
                }
            }
            if (numSpec > 0) {
                avgFixTime = sumFixTime.divide(numSpec, 10, BigDecimal.ROUND_HALF_UP)
            }
            procDuration = maxFixTime - minFixTime
            if (!skipCase){
//                println "caseRec: " + caseRec
                
                caseDwInstance.brain = hasBrain
                caseDwInstance.frozen = frozenCollection
                caseDwInstance.minFixTime = minFixTime
                caseDwInstance.avgFixTime = avgFixTime
                caseDwInstance.maxFixTime = maxFixTime
                caseDwInstance.procedureDuration = procDuration
                caseDwInstance.firstTissueInFixative = firstTissueInFixative
                caseDwInstance.firstTimeInFixative = firstTimeInFixative
                caseDwInstance.lastTissueInFixative = lastTissueInFixative
                log.info( "Saving case: " + caseRec )
                caseDwInstance.save(failOnError: true)
            } else {
                log.error("Skipping case: " + caseRec + " for negative ischemic time!")
//                println "caseDwInstance.specimens.size() " + caseDwInstance.specimens.size() 
//                caseDwInstance.specimens.each() {
//                    println "deleting specimens..."
//                    it.delete()
//                }
//                println "deleting caseDw..."
//                caseDwInstance.delete(flush:true)
//                println "deleted."
            }
//        } // if caserec = GTEX-000353
        }
        log.info("populateCaseDw is complete!")
        return caseDwInstance
    }
}
