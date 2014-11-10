package nci.obbr.cahub.datarecords
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import java.text.SimpleDateFormat
import grails.plugins.springsecurity.Secured

class ChpTissueRecordController {

    // Export service provided by Export plugin	
    def exportService
    
    def ChpTissueMashup
    
    def scaffold = ChpTissueRecord
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        def chpTissueList = ChpTissueRecord.getAll()
        def chpTissueKeep = []
        def chpService = new nci.obbr.cahub.ChpService()
        chpTissueList.each() {
            if (it) {
                
                def chpTissueMashup = new ChpTissueMashup (
                    id:             it.chpTissueRecord.id,
                    specimenRecord: it,
                    surgDate:       it.parentSpecimen.bpvTissueForm.surgDate,
                    firstIncis:     it.parentSpecimen.bpvTissueForm.firstIncis,
                    clamp1Time:     it.parentSpecimen.bpvTissueForm.clamp1Time,
                    clamp2Time:     it.parentSpecimen.bpvTissueForm.clamp2Time,
                    resectTime:     it.parentSpecimen.bpvTissueForm.resectTime,
                    grossTimeIn:    it.parentSpecimen.bpvTissueForm.grossTimeIn,
                    grossDiagx:     it.parentSpecimen.bpvTissueForm.grossDiagx,
                    grossTimeOut:   it.parentSpecimen.bpvTissueForm.grossTimeOut,
                    tissDissecTime: it.parentSpecimen.bpvTissueForm.tissDissecTime,
                    acquisType:     it.tissueType,
                    tumorStatus:    it.tumorStatus,
                    dissecStartTime: it.parentSpecimen.bpvTissueForm.dissecStartTime,
                    dissecEndTime:  it.parentSpecimen.bpvTissueForm.dissecEndTime,
                    fixative:       it.fixative,
                    protocol:       it.protocol,
                    protoDelayToFix: it.protocol.delayToFixation,
                    protoTimeInFix: it.protocol.timeInFixative,
                    timeInCass:     it.chpTissueRecord.timeInCass,
                    timeInFix:      it.chpTissueRecord.timeInFix,
                    timeInProcessor: it.chpTissueRecord.timeInProcessor,
                    procTimeEnd:    it.chpTissueRecord.procTimeEnd,
                    procTimeRemov:  it.chpTissueRecord.procTimeRemov,
                    timeEmbedded:   it.chpTissueRecord.timeEmbedded,
                    calcDelayToFix: chpService.timeDiff(it.chpTissueRecord, "DTF"),
                    calcTimeInFix:  chpService.timeDiff(it.chpTissueRecord, "TIF")
                )
                
                // it.add(calcTifDiff: chpService.timeDiff(it, "TIF"))
                chpTissueKeep.add(chpTissueMashup)
            }
        }
        render(view: "listall", model: [chpTissueRecordInstanceList: chpTissueKeep, chpTissueRecordInstanceTotal: chpTissueKeep.size(), chpService:chpService])
        
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        // println "params: " + params
        // println CaseRecord.findByCaseId(params.id)
        // println CaseRecord.findByCaseId(params.id).specimens.chpTissueRecord
        
        
        
        def parent = SpecimenRecord.findBySpecimenId(params.id)            /* Fetch a list of specimens associated with all the same parent */
        // println "parent: " + parent
        def specimenList = SpecimenRecord.findAllByParentSpecimen(parent)
        def bpvTissueForm = SpecimenRecord.findBySpecimenId(params.id).bpvTissueForm  /* bpvTissueForm is attached to the parentSpecimen and applies to all tissues in the list */
        def chpTissueKeep = []
        def surgDate = bpvTissueForm.surgDate
        def chpService = new nci.obbr.cahub.ChpService()
        def grossDiagnosis
        
        specimenList.each() {
            if (it.protocol) {
//                println "it              : " + it
//                println "it.caseRecord   : " + it.caseRecord
//                println "it.bpvTissueForm: " + it.bpvTissueForm
//                println it.dump()
                grossDiagnosis = it.parentSpecimen.bpvTissueForm.grossDiagx
                def chpTissueMashup = new ChpTissueMashup (
                    id:             it.chpTissueRecord.id,
                    specimenRecord: it,
                    surgDate:       it.parentSpecimen.bpvTissueForm.surgDate,
                    firstIncis:     it.parentSpecimen.bpvTissueForm.firstIncis,
                    clamp1Time:     it.parentSpecimen.bpvTissueForm.clamp1Time,
                    clamp2Time:     it.parentSpecimen.bpvTissueForm.clamp2Time,
                    resectTime:     it.parentSpecimen.bpvTissueForm.resectTime,
                    grossTimeIn:    it.parentSpecimen.bpvTissueForm.grossTimeIn,
                    grossDiagx:     it.parentSpecimen.bpvTissueForm.grossDiagx,
                    grossTimeOut:   it.parentSpecimen.bpvTissueForm.grossTimeOut,
                    tissDissecTime: it.parentSpecimen.bpvTissueForm.tissDissecTime,
                    acquisType:     it.tissueType,
                    tumorStatus:    it.tumorStatus,
                    dissecStartTime: it.parentSpecimen.bpvTissueForm.dissecStartTime,
                    dissecEndTime:  it.parentSpecimen.bpvTissueForm.dissecEndTime,
                    fixative:       it.fixative,
                    protocol:       it.protocol,
                    protoDelayToFix: it.protocol.delayToFixation,
                    protoTimeInFix: it.protocol.timeInFixative,
                    timeInCass:     it.chpTissueRecord.timeInCass,
                    timeInFix:      it.chpTissueRecord.timeInFix,
                    timeInProcessor: it.chpTissueRecord.timeInProcessor,
                    procTimeEnd:    it.chpTissueRecord.procTimeEnd,
                    procTimeRemov:  it.chpTissueRecord.procTimeRemov,
                    timeEmbedded:   it.chpTissueRecord.timeEmbedded,
                    calcDelayToFix: chpService.timeDiff(it.chpTissueRecord, "DTF", it.caseRecord.bss),
                    calcTimeInFix:  chpService.timeDiff(it.chpTissueRecord, "TIF", it.caseRecord.bss)
                )
                
                // it.add(calcTifDiff: chpService.timeDiff(it, "TIF"))
                chpTissueKeep.add(chpTissueMashup)
                // surgDate = it.specimenRecord.caseRecord.bpvTissueForm.surgDate
            }
        }
        
        if (params?.format && params.format != "html"){ 
            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format] 
            response.setHeader("Content-disposition", "attachment; filename=${params.id}-chp-tissue.${params.extension}")
            
            List fields = ["specimenRecord", "surgDate", "acquisType", "tumorStatus", "firstIncis", "clamp1Time", "clamp2Time", "resectTime", "grossTimeIn", "grossDiagx", "grossTimeOut", "tissDissecTime", "dissecStartTime", "dissecEndTime", "timeInCass", "timeInFix", "fixative", "protocol", "protoDelayToFix","calcDelayToFix","protoTimeInFix","calcTimeInFix", "timeInProcessor","procTimeEnd", "procTimeRemov", "timeEmbedded"]
            Map labels =  ["specimenRecord":"Specimen ID", "surgDate":	"surgDate",	"acquisType":"acquisType", "tumorStatus":"tumorStatus",	"firstIncis":"firstIncis",	"clamp1Time":"clamp1Time",	"clamp2Time":"clamp2Time",	"resectTime":"resectTime",	"grossTimeIn":"grossTimeIn",	"grossDiagx":"grossDiagx",	"grossTimeOut":"grossTimeOut",	"tissDissecTime":"tissDissecTime", "dissecStartTime":"dissecStartTime", "dissecEndTime":"dissecEndTime", "timeInCass":"time Into Cass", "timeInFix":"time Into Fix", "fixative":"fixative", "protocol":"protocol", "protoDelayToFix":"protoDelayToFix", "calcDelayToFix":"calcDelayToFix", "protoTimeInFix":"protoTimeInFix", "calcTimeInFix":"calcTimeInFix", "timeInProcessor":"timeInProcessor", "procTimeEnd":"procTimeEnd", "procTimeRemov":"procTimeRemov", "timeEmbedded":"timeEmbedded"]
            Map formatters = [surgDate: dateFormatter, firstIncis: dateTimeFormatter, clamp1Time: dateTimeFormatter, clamp2Time: dateTimeFormatter, resectTime: dateTimeFormatter, grossTimeIn: dateTimeFormatter, grossTimeOut: dateTimeFormatter, tissDissecTime: dateTimeFormatter, dissecStartTime: dateTimeFormatter, dissecEndTime: dateTimeFormatter, timeInCass: dateTimeFormatter, timeInFix: dateTimeFormatter, timeInProcessor: dateTimeFormatter, procTimeEnd: dateTimeFormatter, procTimeRemov: dateTimeFormatter, timeEmbedded: dateTimeFormatter]
            Map parameters = [: ]
            
            exportService.export(params.format, response.outputStream, chpTissueKeep, fields, labels, formatters, parameters) 
        }
        
        
        // println chpKeep.size()    
        [chpTissueRecordInstanceList: chpTissueKeep.sort({a,b-> a.protocol.toString().compareTo(b.protocol.toString())}), chpTissueRecordInstanceTotal: chpTissueKeep.size(), surgDate: surgDate, caseRecord: parent.caseRecord, chpService:chpService, grossDiagnosis:grossDiagnosis]
    }

    def dateFormatter = { domain, value ->
        
        String S = String.format("%1\$TD", value)
        return S
			}
    def dateTimeFormatter = { domain, value ->
        
        String S = String.format("%1\$TD %1\$TH:%1\$TM:%1\$TS", value)
        return S
			}
    
    def create = {
        def chpTissueRecordInstance = new ChpTissueRecord()
        chpTissueRecordInstance.properties = params
        return [chpTissueRecordInstance: chpTissueRecordInstance]
    }

    def save = {
        def chpTissueRecordInstance = new ChpTissueRecord(params)
        if (chpTissueRecordInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord'), chpTissueRecordInstance.id])}"
            redirect(action: "show", id: chpTissueRecordInstance.id)
        }
        else {
            render(view: "create", model: [chpTissueRecordInstance: chpTissueRecordInstance])
        }
    }

    def show = {
        
        def chpTissueRecordInstance = ChpTissueRecord.get( params.id )
        
        if (!chpTissueRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            [chpTissueRecordInstance: chpTissueRecordInstance]
        }
    }

    def edit = {
        
        def chpTissueRecordInstance = ChpTissueRecord.get( params.id )
        if (!chpTissueRecordInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [chpTissueRecordInstance: chpTissueRecordInstance]
        }
    }

    def update = {
        def chpTissueRecordInstance = ChpTissueRecord.get(params.id)
        if (chpTissueRecordInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (chpTissueRecordInstance.version > version) {
                    
                    chpTissueRecordInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord')] as Object[], "Another user has updated this ChpTissueRecord while you were editing")
                    render(view: "edit", model: [chpTissueRecordInstance: chpTissueRecordInstance])
                    return
                }
            }
            chpTissueRecordInstance.properties = params
            if (!chpTissueRecordInstance.hasErrors() && chpTissueRecordInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord'), chpTissueRecordInstance.id])}"
                redirect(action: "show", id: chpTissueRecordInstance.id)
            }
            else {
                render(view: "edit", model: [chpTissueRecordInstance: chpTissueRecordInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord'), params.id])}"
            redirect(action: "list")
        }
    }

    

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def chpTissueRecordInstance = ChpTissueRecord.get(params.id)
        if (chpTissueRecordInstance) {
            try {
                chpTissueRecordInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chpTissueRecord.label', default: 'ChpTissueRecord'), params.id])}"
            redirect(action: "list")
        }
    }

}
