package nci.obbr.cahub.forms.gtex.crf
import grails.plugins.springsecurity.Secured

class SerologyResultController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def accessPrivilegeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [serologyResultInstanceList: SerologyResult.list(params), serologyResultInstanceTotal: SerologyResult.count()]
    }

    def create = {
        def serologyResultInstance = new SerologyResult()
        serologyResultInstance.properties = params
        return [serologyResultInstance: serologyResultInstance]
    }

    def save = {
        def serologyResultInstance = new SerologyResult(params)
               def caseReportForm = CaseReportForm.findById(params.formid)
               
        if (serologyResultInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'serologyResult.label', default: 'Serology Results for '), caseReportForm.caseRecord.caseId])}"
            redirect(action: "show", id: serologyResultInstance.id)
        }
        else {
            render(view: "create", model: [serologyResultInstance: serologyResultInstance])
        }
    }

    def show = {
        def serologyResultInstance = SerologyResult.get(params.id)
        if (!serologyResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'serologyResult.label', default: 'SerologyResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            [serologyResultInstance: serologyResultInstance]
        }
    }

    def edit = {
        def serologyResultInstance = SerologyResult.get(params.id)
        if (!serologyResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'serologyResult.label', default: 'SerologyResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            
            def caseReportForm = CaseReportForm.findBySerologyResult(serologyResultInstance)
            def caseRecord = caseReportForm.caseRecord
            int accessPrivilege = accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')
            if (accessPrivilege > 0) {
                 redirect(controller: "login", action: ((accessPrivilege==1)?"denied":"sessionconflict"))
                 return
            }
//            if (!accessPrivilegeService.checkAccessPrivilege(caseRecord, session, 'edit')) {
//                redirect(controller: "login", action: "denied")
//                return
//            }    
            
            
            def version = serologyResultInstance.version
            if(version == 0){
                return [serologyResultInstance: serologyResultInstance,  formid:params.formid]
            }else{
                def result= checkError(serologyResultInstance)
              
                if(result){
                    result.each(){key,value->
                        serologyResultInstance.errors.rejectValue(key, value)
                    }
                    return [serologyResultInstance: serologyResultInstance,  formid:params.formid]
                
                }else{
              
                    return [serologyResultInstance: serologyResultInstance,  formid:params.formid]  
                }    
                
            }   
            
          
        }
    }

    
    static Map checkError(serologyResultInstance){
         
        def result = [:]
            
        if((serologyResultInstance.HIV_I_II_Ab.equals('Positive') ||serologyResultInstance.HIV_I_II_Ab.equals('Indeterminate')) && serologyResultInstance.HIV_I_II_Ab_Verified == null){
             
            result.put("HIV_I_II_Ab_Verified", "HIV_I_II_Ab: Please respond  if  "+serologyResultInstance.HIV_I_II_Ab +" results have been verified")
            
        }
        if((serologyResultInstance.HIV_I_II_Plus_O_antibody.equals('Positive') ||serologyResultInstance.HIV_I_II_Plus_O_antibody.equals('Indeterminate')) && serologyResultInstance.HIVI_IIPlusOAb_Verified == null){
             
            result.put("HIVI_IIPlusOAb_Verified", "HIV_I_II_Plus_O_antibody: Please respond  if  "+serologyResultInstance.HIV_I_II_Plus_O_antibody +" results have been verified")
        }
        if((serologyResultInstance.HBsAg.equals('Positive') ||serologyResultInstance.HBsAg.equals('Indeterminate')) && serologyResultInstance.HBsAg_Verified == null){
             
            result.put("HBsAg_Verified", "HBsAg: Please respond  if  "+serologyResultInstance.HBsAg +" results have been verified")
        }
        if((serologyResultInstance.HBsAb.equals('Positive') ||serologyResultInstance.HBsAb.equals('Indeterminate')) && serologyResultInstance.HBsAb_Verified == null){
             
            result.put("HBsAb_Verified", "HBsAb: Please respond  if  "+serologyResultInstance.HBsAb +" results have been verified")
        }
                 
        if((serologyResultInstance.HBcAb.equals('Positive') ||serologyResultInstance.HBcAb.equals('Indeterminate')) && serologyResultInstance.HBcAb_Verified == null){
             
            result.put("HBcAb_Verified", "HBcAb: Please respond  if  "+serologyResultInstance.HBcAb +" results have been verified")
            
        }
        
        if((serologyResultInstance.HBcAb_IgM.equals('Positive') ||serologyResultInstance.HBcAb_IgM.equals('Indeterminate')) && serologyResultInstance.HBcAb_IgM_Verified == null){
             
            result.put("HBcAb_IgM_Verified", "HBcAb_IgM: Please respond  if  "+serologyResultInstance.HBcAb_IgM +" results have been verified")
            
        }
        
                
        if((serologyResultInstance.HCV_Ab.equals('Positive') ||serologyResultInstance.HCV_Ab.equals('Indeterminate')) && serologyResultInstance.HCV_Ab_Verified == null){
             
            result.put("HCV_Ab_Verified", "HCV_Ab: Please respond  if  "+serologyResultInstance.HCV_Ab +" results have been verified")
        }
        
        
        
        if((serologyResultInstance.EBV_IgG_Ab.equals('Positive') ||serologyResultInstance.EBV_IgG_Ab.equals('Indeterminate')) && serologyResultInstance.EBV_IgG_Ab_Verified == null){
             
            result.put("EBV_IgG_Ab_Verified", "EBV_IgG_Ab: Please respond  if  "+serologyResultInstance.EBV_IgG_Ab +" results have been verified")
        }
        
                
        if((serologyResultInstance.EBV_IgM_Ab.equals('Positive') ||serologyResultInstance.EBV_IgM_Ab.equals('Indeterminate')) && serologyResultInstance.EBV_IgM_Ab_Verified == null){
             
            result.put("EBV_IgM_Ab_Verified", "EBV_IgM_Ab: Please respond  if  "+serologyResultInstance.EBV_IgM_Ab +" results have been verified")
        }
                        
        if((serologyResultInstance.RPR.equals('Positive') ||serologyResultInstance.RPR.equals('Indeterminate')) && serologyResultInstance.RPR_Verified == null){
             
            result.put("RPR_Verified", "RPR: Please respond  if  "+serologyResultInstance.RPR +" results have been verified")
        }
        
        if((serologyResultInstance.CMV_Total_Ab.equals('Positive') ||serologyResultInstance.CMV_Total_Ab.equals('Indeterminate')) && serologyResultInstance.CMV_Total_Ab_Verified == null){
             
            result.put("CMV_Total_Ab_Verified", "CMV_Total_Ab: Please respond  if  "+serologyResultInstance.CMV_Total_Ab +" results have been verified")
        }
        
        
        if((serologyResultInstance.HIV_1_NAT.equals('Positive') ||serologyResultInstance.HIV_1_NAT.equals('Indeterminate')) && serologyResultInstance.HIV_1_NAT_Verified == null){
             
            result.put("HIV_1_NAT_Verified", "HIV_1_NAT: Please respond  if  "+serologyResultInstance.HIV_1_NAT +" results have been verified")
        }
        
        
        if((serologyResultInstance.HCV_1_NAT.equals('Positive') ||serologyResultInstance.HCV_1_NAT.equals('Indeterminate')) && serologyResultInstance.HCV_1_NAT_Verified == null){
             
            result.put("HCV_1_NAT_Verified", "HCV_1_NAT: Please respond  if  "+serologyResultInstance.HCV_1_NAT +" results have been verified")
            
        }
        
        
        
        if((serologyResultInstance.PRR_VDRL.equals('Positive') ||serologyResultInstance.PRR_VDRL.equals('Indeterminate')) && serologyResultInstance.PRR_VDRL_Verified == null){
             
            result.put("PRR_VDRL", "PRR_VDRL: Please respond  if  "+serologyResultInstance.PRR_VDRL +" results have been verified")
        }
        
       
        /* def HIV_I_II_Ab = serologyResultInstance.HIV_I_II_Ab
        if(!HIV_I_II_Ab){
        result.add('The "HIV I/II Ab" is a required field')
        }
        
        def HIV_I_II_Plus_O_antibody = serologyResultInstance.HIV_I_II_Plus_O_antibody
        if(!HIV_I_II_Plus_O_antibody){
        result.add(' "HIV I/II Plus O Antibody" is a required field')
        }*/
        
        return result
    }
    
    def update = {
        def serologyResultInstance = SerologyResult.get(params.id)
        if (serologyResultInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (serologyResultInstance.version > version) {
                    
                    serologyResultInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'serologyResult.label', default: 'SerologyResult')] as Object[], "Another user has updated this SerologyResult while you were editing")
                    render(view: "edit", model: [serologyResultInstance: serologyResultInstance])
                    return
                }
            }
            serologyResultInstance.properties = params
            def caseReportForm = CaseReportForm.findById(params.formid)
            /*  if (!serologyResultInstance.hasErrors() && serologyResultInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'serologyResult.label', default: 'SerologyResult'), serologyResultInstance.id])}"
            // redirect(action: "show", id: serologyResultInstance.id)
            redirect(action:"edit", params:[id:serologyResultInstance.id, formid:params.formid])
            }
            else {
            flash.message ="data not saved"
            //render(view: "edit", model: [serologyResultInstance: serologyResultInstance])
            redirect(action:"edit", params:[id:serologyResultInstance.id, formid:params.formid])
            }*/
            if(serologyResultInstance.HIV_I_II_Ab.equals('Not Performed') || serologyResultInstance.HIV_I_II_Ab.equals('Negative')){
             
                serologyResultInstance.HIV_I_II_Ab_Verified = null
            }
            if(serologyResultInstance.HIV_I_II_Plus_O_antibody.equals('Not Performed') || serologyResultInstance.HIV_I_II_Plus_O_antibody.equals('Negative'))  {
             
               serologyResultInstance.HIVI_IIPlusOAb_Verified = null
            }
            if(serologyResultInstance.HBsAg.equals('Not Performed') || serologyResultInstance.HBsAg.equals('Negative'))  {
             
                serologyResultInstance.HBsAg_Verified = null
            }
            if(serologyResultInstance.HBsAb.equals('Not Performed') || serologyResultInstance.HBsAb.equals('Negative'))  {
             
                serologyResultInstance.HBsAb_Verified = null
            }
                 
            if(serologyResultInstance.HBcAb.equals('Not Performed') || serologyResultInstance.HBcAb.equals('Negative'))  {
             
                serologyResultInstance.HBcAb_Verified = null
            
            }
            if(serologyResultInstance.HBcAb_IgM.equals('Not Performed') || serologyResultInstance.HBcAb_IgM.equals('Negative'))  {
             
                serologyResultInstance.HBcAb_IgM_Verified = null
            
            }
             
            if(serologyResultInstance.HCV_Ab.equals('Not Performed') ||serologyResultInstance.HCV_Ab.equals('Negative')  ){
             
                serologyResultInstance.HCV_Ab_Verified = null
            }
               
            if(serologyResultInstance.EBV_IgG_Ab.equals('Not Performed') ||serologyResultInstance.EBV_IgG_Ab.equals('Negative'))  {
             
                serologyResultInstance.EBV_IgG_Ab_Verified = null
            }
               
            if(serologyResultInstance.EBV_IgM_Ab.equals('Not Performed') ||serologyResultInstance.EBV_IgM_Ab.equals('Negative'))  {
             
                serologyResultInstance.EBV_IgM_Ab_Verified = null
            }
                        
            if(serologyResultInstance.RPR.equals('Not Performed') ||serologyResultInstance.RPR.equals('Negative'))  {
             
                serologyResultInstance.RPR_Verified = null
            }
        
            if(serologyResultInstance.CMV_Total_Ab.equals('Not Performed') || serologyResultInstance.CMV_Total_Ab.equals('Negative')) {
             
                serologyResultInstance.CMV_Total_Ab_Verified = null
            }
        
        
            if(serologyResultInstance.HIV_1_NAT.equals('Not Performed') || serologyResultInstance.HIV_1_NAT.equals('Negative'))  {
             
                serologyResultInstance.HIV_1_NAT_Verified = null
            }
        
        
            if(serologyResultInstance.HCV_1_NAT.equals('Not Performed') ||serologyResultInstance.HCV_1_NAT.equals('Negative'))  {
             
                serologyResultInstance.HCV_1_NAT_Verified = null
            
            }
        
        if(serologyResultInstance.HIV_I_II_Ab.equals('Not Performed') || serologyResultInstance.HIV_I_II_Ab.equals('Negative')){
             
                serologyResultInstance.HIV_I_II_Ab_Verified = null
            }
        
            if(serologyResultInstance.PRR_VDRL.equals('Not Performed') ||serologyResultInstance.PRR_VDRL.equals('Negative'))  {
             
                serologyResultInstance.PRR_VDRL_Verified = null
            }
        
        
        
            try{
                serologyResultInstance.save(failOnError:true, flush: true)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'serologyResult.label', default: 'Serology Results for '), caseReportForm.caseRecord.caseId])}"
             
                redirect(action:"edit", params:[id:serologyResultInstance.id, formid:params.formid])
                 
            }catch(Exception e){
                flash.message = "Failed: " + e.toString()
                redirect(action:"edit", params:[id:serologyResultInstance.id, formid:params.formid])
            }
            
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'serologyResult.label', default: 'SerologyResult'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN']) 
    def delete = {
        def serologyResultInstance = SerologyResult.get(params.id)
        if (serologyResultInstance) {
            try {
                serologyResultInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'serologyResult.label', default: 'SerologyResult'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'serologyResult.label', default: 'SerologyResult'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'serologyResult.label', default: 'SerologyResult'), params.id])}"
            redirect(action: "list")
        }
    }
}
