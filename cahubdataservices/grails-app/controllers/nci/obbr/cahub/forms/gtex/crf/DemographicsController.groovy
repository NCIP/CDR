package nci.obbr.cahub.forms.gtex.crf

import nci.obbr.cahub.util.ComputeMethods
import grails.plugins.springsecurity.Secured

class DemographicsController {
    
    def accessPrivilegeService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [demographicsInstanceList: Demographics.list(params), demographicsInstanceTotal: Demographics.count()]
    }

    
    def create = {
        def demographicsInstance = new Demographics()
        demographicsInstance.properties = params
        return [demographicsInstance: demographicsInstance]
    }

    def save = {
        
        // println("here???")
        def demographicsInstance = new Demographics(params)
        //  println("in save  formid ${formid}")
        
        if (demographicsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'demographics.label', default: 'Demographics'), demographicsInstance.id])}"
            redirect(action: "show", id: demographicsInstance.id)
            //redirect(action:"edit", params:[id:demographicsInstance.id, formid:params.formid])
        }
        else {
            render(view: "create", model: [demographicsInstance: demographicsInstance])
            //redirect(action:"edit", params:[id:demographicsInstance.id, formid:params.formid])
        }
    }

    def show = {
        def demographicsInstance = Demographics.get(params.id)
        if (!demographicsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'demographics.label', default: 'Demographics'), params.id])}"
            redirect(action: "list")
        }
        else {
            [demographicsInstance: demographicsInstance]
        }
    }

    def edit = {
        def demographicsInstance = Demographics.get(params.id)
        if (!demographicsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'demographics.label', default: 'Demographics'), params.id])}"
            redirect(action: "list")
        }
        else {
            def caseReportFormInstance=CaseReportForm.findByDemographics(demographicsInstance)
            def caseRecord = caseReportFormInstance?.caseRecord
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }
            
            //def show45VersionUpdates=false
            //if(ComputeMethods.compareCDRVersion(caseReportFormInstance.caseRecord.cdrVer, '4.5') >= 0){
            //    show45VersionUpdates=true
           // }
            
            def version = demographicsInstance.version
            if(version == 0){
                return [demographicsInstance: demographicsInstance, formid:params.formid] 
            }else{
                   
                def result= checkError(demographicsInstance)
                if(result){
                    result.each(){key,value->
                        // println("it: " + it)
                        demographicsInstance.errors.rejectValue(key, value)
                    }//each
                    return [demographicsInstance: demographicsInstance, formid:params.formid]
                }else{
                    return [demographicsInstance: demographicsInstance, formid:params.formid] 
                }
                  
            }
            
          
        }
    }

    def update = {
       
        def demographicsInstance = Demographics.get(params.id)
         
         
        if (demographicsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (demographicsInstance.version > version) {
                    
                    demographicsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'demographics.label', default: 'Demographics')] as Object[], "Another user has updated this Demographics while you were editing")
                    render(view: "edit", model: [demographicsInstance: demographicsInstance])
                    return
                }
            }
            demographicsInstance.properties = params          
            def caseReportFormInstance=CaseReportForm.findByDemographics(demographicsInstance)
            
            try{
                demographicsInstance.save(failOnError:true,flush:true)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'demographics.label', default: 'Demographics for '), caseReportFormInstance.caseRecord.caseId])}"
                redirect(action:"edit", params:[id:demographicsInstance.id, formid:params.formid])
            }catch(Exception e){
                flash.message = "Failed: " + e.toString()
                redirect(action:"edit", params:[id:demographicsInstance.id, formid:params.formid]) 
            }
            
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'demographics.label', default: 'Demographics'), params.id])}"
            redirect(action: "list")
        }
    }

    static  Map checkError(demographicsInstance){
        def result = [:]
        def gender = demographicsInstance.gender
        
        if(!gender){
            result.put("gender", "The gender is a required field.")
        }        
        
        def dateOfBirth =   demographicsInstance.dateOfBirth
        if(!dateOfBirth){
            result.put("dateOfBirth", "The date of birth is a required field.")
        }
        
        if(gender && gender.toString() == "Other"){
            def otherGender = demographicsInstance.otherGender
            if(!otherGender){
                result.put("otherGender", "Other gender is a required field.")
            }
        }
        def height = demographicsInstance.height
        if(height == 0){
            result.put("height", "The height can not be 0.")
        }
        
        def weight = demographicsInstance.weight
        if(weight == 0){
            result.put("weight", "The weight can not be 0.")
        }
        
        /* def race =  demographicsInstance.race
        if(!race){
        result.add("The Race is a required field")
        }*/
        def raceWhite=demographicsInstance.raceWhite
        def raceAsian=demographicsInstance.raceAsian
        def raceBlackAmerican=demographicsInstance.raceBlackAmerican
        def raceIndian=demographicsInstance.raceIndian
        def raceHawaiian=demographicsInstance.raceHawaiian
        def raceUnknown=demographicsInstance.raceUnknown
        
        if(!raceWhite && !raceAsian && !raceBlackAmerican && !raceIndian && !raceHawaiian && !raceUnknown){
            result.put("race", "The race is a required field.")
        }
        
        def ethnicity = demographicsInstance.ethnicity
        if(!ethnicity){
            result.put("ethnicity", "The ethnicity is a required field.")
        }
        //println("result size:" + result.size())
        return result
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])     
    def delete = {
        def demographicsInstance = Demographics.get(params.id)
        if (demographicsInstance) {
            try {
                demographicsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'demographics.label', default: 'Demographics'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'demographics.label', default: 'Demographics'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'demographics.label', default: 'Demographics'), params.id])}"
            redirect(action: "list")
        }
    }
}
