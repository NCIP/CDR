package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SlideRecord
import nci.obbr.cahub.datarecords.SpecimenRecord

import nci.obbr.cahub.staticmembers.Study
import nci.obbr.cahub.staticmembers.Protocol
import nci.obbr.cahub.staticmembers.Fixative
import nci.obbr.cahub.staticmembers.Organization
import nci.obbr.cahub.staticmembers.FormMetadata

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import nci.obbr.cahub.staticmembers.Module

class BpvSlidePrepController {
    def bpvSlidePrepService
    def scaffold = BpvSlidePrep
    def bpvSlidePrepInstance
    def bpvSlideRecord = new SlideRecord()
    def bpvSlideList = []
    def accessPrivilegeService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)
        [bpvSlidePrepInstanceList: BpvSlidePrep.list(params), bpvSlidePrepInstanceTotal: BpvSlidePrep.count()]
    }

    def create = {
        def bpvSlidePrepInstance = new BpvSlidePrep()
        bpvSlidePrepInstance.properties = params

        bpvSlidePrepInstance.formMetadata = FormMetadata.get(params.formMetadata.id)
        return [bpvSlidePrepInstance: bpvSlidePrepInstance, slides:SlideRecord.findAllByCaseId(params.caseRecord.id,[sort: "slideId"])]
    }

    def save = {
        def bpvSlidePrepInstance = new BpvSlidePrep(params)
        if(bpvSlidePrepInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [bpvSlidePrepInstance?.formMetadata?.cdrFormName + ' for Case', bpvSlidePrepInstance.caseRecord.caseId])}"
            redirect(action: "editWithValidation", id: bpvSlidePrepInstance.id)
        } else {
            render(view: "create", model: [bpvSlidePrepInstance: bpvSlidePrepInstance])
        }
    }

    def show = {
        def bpvSlidePrepInstance = BpvSlidePrep.get(params.id)
        if(!bpvSlidePrepInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSlidePrepInstance?.formMetadata?.cdrFormName + ' for Case', bpvSlidePrepInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvSlidePrepInstance.caseRecord, session, 'view')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(bpvSlidePrepInstance.caseRecord, session, 'view')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }
            
            def canResume = (accessPrivilegeService.checkAccessPrivilege(bpvSlidePrepInstance.caseRecord, session, 'edit') == 0)
            [bpvSlidePrepInstance: bpvSlidePrepInstance, slides:SlideRecord.findAllByCaseId(bpvSlidePrepInstance?.caseRecord.id,[sort: "slideId"]), canResume: canResume]
        }
    }

    def edit = {
        def bpvSlidePrepInstance = BpvSlidePrep.get(params.id)
        if(!bpvSlidePrepInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSlidePrepInstance?.formMetadata?.cdrFormName + ' for Case', bpvSlidePrepInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvSlidePrepInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvSlidePrepInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            checkSlidePrepError(bpvSlidePrepInstance)
            def canSubmit = !bpvSlidePrepInstance.errors.hasErrors()
            bpvSlidePrepInstance.clearErrors()
            return [bpvSlidePrepInstance: bpvSlidePrepInstance, canSubmit:canSubmit, slides:SlideRecord.findAllByCaseId(bpvSlidePrepInstance?.caseRecord.id,[sort: "slideId"])]
        }
    }

    def editWithValidation = {
        def bpvSlidePrepInstance = BpvSlidePrep.get(params.id)
        if(!bpvSlidePrepInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSlidePrepInstance?.formMetadata?.cdrFormName + ' for Case', bpvSlidePrepInstance.caseRecord.caseId])}"
            redirect(action: "list")
        } else {
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(bpvSlidePrepInstance.caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
            if (bpvSlidePrepInstance.submittedBy != null) {
                redirect(controller: "login", action: "denied")
                return
            }
            
            checkSlidePrepError(bpvSlidePrepInstance)
            render(view: "edit", model: [bpvSlidePrepInstance: bpvSlidePrepInstance, canSubmit: !bpvSlidePrepInstance.errors.hasErrors(), slides:SlideRecord.findAllByCaseId(bpvSlidePrepInstance?.caseRecord.id,[sort: "slideId"])])
        }
    }

    def addSlide = {
        //println("add slide1")
        def caseId = params.caseId
        def slideRecord = new SlideRecord()
        def bssCode = CaseRecord.get(caseId).bss.code

        def location = Organization.findByCode(bssCode)
        if(location) {
            slideRecord = new SlideRecord(
                caseId: caseId,
                slideId: params.slideId,
                slideLocation: location,
                createdBy: bssCode,
                module: Module.findById(params.module)
            )

            if(slideRecord.validate()) {
                slideRecord.save(flush: true, failOnError: false)
            }
        } else {
            slideRecord.errors.rejectValue("slideId", "Slide BSS [${bssCode}] location not found.")
        }
        render(view: "slideTable", model: [slideRecord: slideRecord, slides:SlideRecord.findAllByCaseId(caseId,[sort: "slideId"])])
    }
    
    
    def addSlide2 = {
        //println("add slide2")
        def caseId = params.caseId
        def slideRecord = new SlideRecord()
        def bssCode = CaseRecord.get(caseId).bss.code

        def location = Organization.findByCode(bssCode)
        if(location) {
            slideRecord = new SlideRecord(
                caseId: caseId,
                slideId: params.slideId,
                slideLocation: location,
                createdBy: bssCode,
                module: Module.findById(params.module)
            )

            if(slideRecord.validate()) {
                slideRecord.save(flush: true, failOnError: false)
            }
        } else {
            slideRecord.errors.rejectValue("slideId", "Slide BSS [${bssCode}] location not found.")
        }
        
       // println("here/////")
        render(view: "slideTable2", model: [slideRecord: slideRecord, slides:SlideRecord.findAllByCaseId(caseId,[sort: "slideId"]), cid:caseId])
    }

    def deleteSlide = {
        def slideRecord = SlideRecord.get(params.id)
        try {
            slideRecord.delete(flush: true)
        } catch(org.hibernate.exception.ConstraintViolationException e) {
            e.printStackTrace
            slideRecord.errors.reject("slideId", "An error occurred while deleting Slide ID [${slideRecord.slideId}].")
        }
        render(view: "slideTable", model: [slideRecord:slideRecord, slides:SlideRecord.findAllByCaseId(params.caseId,[sort: "slideId"])])
    }
       
    
    def deleteSlide2 = {
        def slideRecord = SlideRecord.get(params.id)
        try {
            slideRecord.delete(flush: true)
        } catch(org.hibernate.exception.ConstraintViolationException e) {
            e.printStackTrace
            slideRecord.errors.reject("slideId", "An error occurred while deleting Slide ID [${slideRecord.slideId}].")
        }
        
        render(view: "slideTable2", model: [slideRecord:slideRecord, slides:SlideRecord.findAllByCaseId(params.caseId,[sort: "slideId"]), cid:params.caseId])
    }
    def update = {
        def bpvSlidePrepInstance = BpvSlidePrep.get(params.id)
        if(bpvSlidePrepInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvSlidePrepInstance.version > version) {
                    bpvSlidePrepInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvSlidePrepInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvSlidePrep while you were editing")
                    redirect(action: "edit", id: bpvSlidePrepInstance.id)
                    return
                }
            }

            bpvSlidePrepInstance.properties = params
            if(!bpvSlidePrepInstance.hasErrors() ) {
               def msg= bpvSlidePrepService.updateForm(params)
                flash.message = "${message(code: 'default.updated.message', args: [bpvSlidePrepInstance?.formMetadata?.cdrFormName + ' for Case', bpvSlidePrepInstance.caseRecord.caseId])}" + " " + msg
                redirect(action: "editWithValidation", id: bpvSlidePrepInstance.id)
            } else {
                render(view: "edit", model: [bpvSlidePrepInstance: bpvSlidePrepInstance, canSubmit: !bpvSlidePrepInstance.errors.hasErrors(), slides:SlideRecord.findAllByCaseId(bpvSlidePrepInstance?.caseRecord.id,[sort: "slideId"])])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSlidePrepInstance?.formMetadata?.cdrFormName + ' for Case', bpvSlidePrepInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    def submit = {
        def bpvSlidePrepInstance = BpvSlidePrep.get(params.id)
        if(bpvSlidePrepInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(bpvSlidePrepInstance.version > version) {
                    bpvSlidePrepInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvSlidePrepInstance?.formMetadata?.cdrFormName] as Object[],  "Another user has updated this BpvSlidePrep while you were editing")
                    render(view: "edit", model: [bpvSlidePrepInstance: bpvSlidePrepInstance])
                    return
                }
            }

            bpvSlidePrepInstance.properties = params
            checkSlidePrepError(bpvSlidePrepInstance)

            if(!bpvSlidePrepInstance.hasErrors()) {
               // println("no errors")
                def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
                bpvSlidePrepService.submitForm(bpvSlidePrepInstance, username)

                flash.message = "${message(code: 'default.submitted.message', args: [bpvSlidePrepInstance?.formMetadata?.cdrFormName + ' for Case', bpvSlidePrepInstance.caseRecord.caseId])}"
                redirect(controller: "caseRecord", action: "display", id: bpvSlidePrepInstance.caseRecord.id)
            } else {
                //println("has errors")
                redirect(action: "edit", id:bpvSlidePrepInstance.id)
            }
        } else {
            
            redirect(action: "list")
        }
    }

    def resumeEditing = {
        def bpvSlidePrepInstance = BpvSlidePrep.get(params.id)
        bpvSlidePrepInstance.dateSubmitted = null
        bpvSlidePrepInstance.submittedBy = null

        if(bpvSlidePrepInstance.save(flush: true)) {
            redirect(action: "edit", id: bpvSlidePrepInstance.id)
        } else {
            render(view: "show", model: [bpvSlidePrepInstance: bpvSlidePrepInstance, slides:SlideRecord.findAllByCaseId(bpvSlidePrepInstance?.caseRecord.id,[sort: "slideId"])])
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def delete = {
        def bpvSlidePrepInstance = BpvSlidePrep.get(params.id)
        if(bpvSlidePrepInstance) {
            try {
                bpvSlidePrepInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [bpvSlidePrepInstance?.formMetadata?.cdrFormName + ' for Case', bpvSlidePrepInstance.caseRecord.caseId])}"
                redirect(action: "list")
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [bpvSlidePrepInstance?.formMetadata?.cdrFormName + ' for Case', bpvSlidePrepInstance.caseRecord.caseId])}"
                redirect(action: "show", id: params.id)
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [bpvSlidePrepInstance?.formMetadata?.cdrFormName + ' for Case', bpvSlidePrepInstance.caseRecord.caseId])}"
            redirect(action: "list")
        }
    }

    Map checkSlidePrepError(bpvSlidePrepInstance) {
        if(!bpvSlidePrepInstance.slidePrepTech) {
            bpvSlidePrepInstance.errors.rejectValue('slidePrepTech', "Slide Prep Technician Name is a required field.")
        }

        if(!bpvSlidePrepInstance.siteSOPSlidePrep) {
            bpvSlidePrepInstance.errors.rejectValue('siteSOPSlidePrep', "Slide Prep SOP is a required field.")
        }

        if(!bpvSlidePrepInstance.microtome) {
            bpvSlidePrepInstance.errors.rejectValue('microtome', "Microtome is a required field.")
        } else if(bpvSlidePrepInstance.microtome.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.microtomeOs) {
            bpvSlidePrepInstance.errors.rejectValue('microtomeOs', "Other Microtome (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.microtomeBladeType) {
            bpvSlidePrepInstance.errors.rejectValue('microtomeBladeType', "Microtome Blade Type is a required field.")
        } else if(bpvSlidePrepInstance.microtomeBladeType.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.microtomeBladeTypeOs) {
            bpvSlidePrepInstance.errors.rejectValue('microtomeBladeTypeOs', "Other Microtome Blade Type (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.microtomeBladeAge) {
            bpvSlidePrepInstance.errors.rejectValue('microtomeBladeAge', "Microtome Blade Age is a required field.")
        } else if(bpvSlidePrepInstance.microtomeBladeAge.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.microtomeBladeAgeOs) {
            bpvSlidePrepInstance.errors.rejectValue('microtomeBladeAgeOs', "Other Microtome Blade Age (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.facedBlockPrep) {
            bpvSlidePrepInstance.errors.rejectValue('facedBlockPrep', "Preparation of block face for sectioning is a required field.")
        } else if(bpvSlidePrepInstance.facedBlockPrep.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.facedBlockPrepOs) {
            bpvSlidePrepInstance.errors.rejectValue('facedBlockPrepOs', "Other Faced Block Prep (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.sectionThickness) {
            bpvSlidePrepInstance.errors.rejectValue('sectionThickness', "Section Thickness is a required field.")
        } else if(bpvSlidePrepInstance.sectionThickness.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.sectionThicknessOs) {
            bpvSlidePrepInstance.errors.rejectValue('sectionThicknessOs', "Other Section Thickness (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.slideCharge) {
            bpvSlidePrepInstance.errors.rejectValue('slideCharge', "Slide Charge is a required field.")
        } else if(bpvSlidePrepInstance.slideCharge.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.slideChargeOs) {
            bpvSlidePrepInstance.errors.rejectValue('slideChargeOs', "Other Slide Charge (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.waterBathTemp) {
            bpvSlidePrepInstance.errors.rejectValue('waterBathTemp', "Water Bath Temp is a required field.")
        } else if(bpvSlidePrepInstance.waterBathTemp.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.waterBathTempOs) {
            bpvSlidePrepInstance.errors.rejectValue('waterBathTempOs', "Other Water Bath Temp (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.microtomeDailyMaint) {
            bpvSlidePrepInstance.errors.rejectValue('microtomeDailyMaint', "Microtome Maintenance is a required field.")
        } else if(bpvSlidePrepInstance.microtomeDailyMaint.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.microtomeDailyMaintOs) {
            bpvSlidePrepInstance.errors.rejectValue('microtomeDailyMaintOs', "Please record any deviations from Microtome Daily Maintenance SOP.")
        }

        if(!bpvSlidePrepInstance.waterbathMaint) {
            bpvSlidePrepInstance.errors.rejectValue('waterbathMaint', "Waterbath Maintenance is a required field.")
        } else if(bpvSlidePrepInstance.waterbathMaint.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.waterbathMaintOs) {
            bpvSlidePrepInstance.errors.rejectValue('waterbathMaintOs', "Please record any deviations from the Water Bath Daily Maintenance SOP.")
        }

        if(!bpvSlidePrepInstance.siteSOPHEStain) {
            bpvSlidePrepInstance.errors.rejectValue('siteSOPHEStain', "H&E Stain SOP is a required field.")
        }

        if(!bpvSlidePrepInstance.heTimeInOven) {
            bpvSlidePrepInstance.errors.rejectValue('heTimeInOven', "H&E Time In Oven is a required field.")
        } else if(bpvSlidePrepInstance.heTimeInOven.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.heTimeInOvenOs) {
            bpvSlidePrepInstance.errors.rejectValue('heTimeInOvenOs', "Other H&E Time In Oven (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.heOvenTemp) {
            bpvSlidePrepInstance.errors.rejectValue('heOvenTemp', "H&E Oven Temp is a required field.")
        } else if(bpvSlidePrepInstance.heOvenTemp.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.heOvenTempOs) {
            bpvSlidePrepInstance.errors.rejectValue('heOvenTempOs', "Other H&E Oven Temp (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.heDeParrafinMethod) {
            bpvSlidePrepInstance.errors.rejectValue('heDeParrafinMethod', "H&E De-paraffin Method is a required field.")
        } else if(bpvSlidePrepInstance.heDeParrafinMethod.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.heDeParrafinMethodOs) {
            bpvSlidePrepInstance.errors.rejectValue('heDeParrafinMethodOs', "Other H&E De-paraffin Method (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.heStainMethod) {
            bpvSlidePrepInstance.errors.rejectValue('heStainMethod', "H&E Stain Method is a required field.")
        } else if(bpvSlidePrepInstance.heStainMethod.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.heStainMethodOs) {
            bpvSlidePrepInstance.errors.rejectValue('heStainMethodOs', "Other H&E Stain Method (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.heClearingMethod) {
            bpvSlidePrepInstance.errors.rejectValue('heClearingMethod', "H&E Clearing Method is a required field.")
        } else if(bpvSlidePrepInstance.heClearingMethod.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.heClearingMethodOs) {
            bpvSlidePrepInstance.errors.rejectValue('heClearingMethodOs', "Other H&E Clearing Method (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.heCoverSlipping) {
            bpvSlidePrepInstance.errors.rejectValue('heCoverSlipping', "H&E Cover Slipping is a required field.")
        } else if(bpvSlidePrepInstance.heCoverSlipping.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.heCoverSlippingOs) {
            bpvSlidePrepInstance.errors.rejectValue('heCoverSlippingOs', "Other H&E Cover Slipping (specify) is a required field.")
        }

        if(!bpvSlidePrepInstance.heEquipMaint) {
            bpvSlidePrepInstance.errors.rejectValue('heEquipMaint', "H&E equipment maintenance is a required field.")
        } else if(bpvSlidePrepInstance.heEquipMaint.equalsIgnoreCase('Other (specify)') && !bpvSlidePrepInstance.heEquipMaintOs) {
            bpvSlidePrepInstance.errors.rejectValue('heEquipMaintOs', "Please record any deviations from the H&E Equipment Maintenance SOP.")
        }
        
         List list2 = SlideRecord.findAllByCaseId(bpvSlidePrepInstance.caseRecord.id.toString())
          for(int i = 0; i < list2.size(); i++){
               def s = list2.get(i)
             
               
            if(!s.module){
                  bpvSlidePrepInstance.errors.reject("m_" +s.slideId, "module for slide " + s.slideId + " is a required field");
            }
          }
        
        if(bpvSlidePrepInstance.caseRecord.bpvWorkSheet?.dateSubmitted){
            List list = SlideRecord.findAllByCaseId(bpvSlidePrepInstance.caseRecord.id.toString())
          //  println("slides size: " + list.size())
           // def s = list.get(0)
           // println("s id: " + s.slideId + " m: " + s.module?.code)
           for(int i = 0; i < list.size(); i++){
              def s = list.get(i)
              if(s.module?.code == 'MODULE1' && !bpvSlidePrepInstance.caseRecord.bpvWorkSheet.m1){
                    bpvSlidePrepInstance.errors.reject(s.slideId, 'slide ' + s.slideId + " is in module I. but module I does not exists in worksheet")
                   // println("catch?????")
                }
                
                if(s.module?.code == 'MODULE2' && !bpvSlidePrepInstance.caseRecord.bpvWorkSheet.m2){
                    bpvSlidePrepInstance.errors.reject(s.slideId, 'slide ' + s.slideId + " is in module II. but module II does not exists in worksheet")
                }
                
                 if(s.module?.code == 'MODULE3N' && !bpvSlidePrepInstance.caseRecord.bpvWorkSheet.m3){
                    bpvSlidePrepInstance.errors.reject(s.slideId, 'slide ' + s.slideId + " is in module III. but module III does not exists in worksheet")
                }
                
                if(s.module?.code == 'MODULE4N' && !bpvSlidePrepInstance.caseRecord.bpvWorkSheet.m4){
                    bpvSlidePrepInstance.errors.reject(s.slideId, 'slide ' + s.slideId + " is in module IV. but module IV does not exists in worksheet")
                }
                
                 if(s.module?.code == 'MODULE5' && !bpvSlidePrepInstance.caseRecord.bpvWorkSheet.m5){
                    bpvSlidePrepInstance.errors.reject(s.slideId, 'slide ' + s.slideId + " is in module V. but module V does not exists in worksheet")
                }

               
           }
            
        }
    }
}
