package nci.obbr.cahub.datarecords.qm

import nci.obbr.cahub.datarecords.CaseRecord

class CaseQMSignatureController {

    def index = { }
    
    def sign = {
        def caseRecordInstance
        //println "edit params=" + params
        
        for(int k=0;k<2;k++)
        {
            def key
            if (k == 0) key = params.id?.toString()
            if (k == 1) key = params.caseRecordId?.toString()
            if ((!key)||(key.trim().length() == 0)) continue
            def id_ 
            try { id_ = Integer.parseInt(key) }
            catch(NumberFormatException ne ) { id_ = -1 }
            if (id_ >= 0) caseRecordInstance = CaseRecord.get(id_)
            else caseRecordInstance = CaseRecord.findByCaseId(key)
            if (caseRecordInstance) break
        }
               
        def instances = CaseQMSignature.findAllByCaseRecord(caseRecordInstance)
        def instance
        
        def currID = params.signedUserId
        if (!currID) currID = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
        
        if (instances)
        {
            def ins
            instances.each() {
                ins = it
                if (currID.equals(it.userId)) instance = it
            }
            if ((!instance)&&(instances.size() == 1))
            {
                //def ins = CaseQMSignature.findByCaseRecord(caseRecordInstance)
                if ((!ins.userId)||(ins.userId.trim().length() > 0)) instance = ins
            }
        }
        
        
        //def caseid = caseRecordInstance.caseId
        def res = true
        def message
        def signingmode = params.signingmode
        
        if ((signingmode?.equalsIgnoreCase('sign'))||(signingmode?.equalsIgnoreCase('edit')))
        {
            def signedDateVal
            if ((params.signedDate)&&(params.signedDate.toString().trim().toLowerCase().startsWith('select'))) params.signedDate = null
            if (params.signedDate)
            {
                java.text.SimpleDateFormat dFormat 
                try
                {
                    String achar = params.signedDate.substring(2, 3)
                    if (achar == '/') dFormat = new java.text.SimpleDateFormat("MM/dd/yyyy")
                    else dFormat = new java.text.SimpleDateFormat("yyyy-MM-dd")
                    signedDateVal = dFormat.parse(params.signedDate.toString().substring(0, 10))
                    if ((new Date()).compareTo(signedDateVal) < 0)
                    {
                        message = 'This date value is later than today: ' + params.signedDate
                        res = false
                    }
                }
                catch(Exception ee)
                {
                    message = 'Invalid sign date format: ' + params.signedDate
                    res = false
                }
            }
            else
            {
                message = 'Sign Date is needed.'
                res = false
            }
            if ((!params.signedUserId)||(params.signedUserId.trim().equals('')))
            {
                message = 'Signer\'s user id is needed.'
                res = false
            }
            if (res)
            {
                if (instance)
                {   
                    //if (signingmode.equalsIgnoreCase('sign'))
                    //{
                    //    message = 'You have already signed on this case.'
                    //    res = false
                    //}
                    //else
                    //{
                        def signedDate = instance.signedDate
                        def userId = instance.userId
                        def comments = instance.comments
                        if (userId?.trim().equals('')) userId = null
                        if ((userId)&&(!userId.equals(session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername())))
                        {
                            message = 'You are not authorized.'
                            res = false   
                        }
                        else if ((signedDate?.compareTo(signedDateVal) == 0)&&(userId?.equals(params.signedUserId))&&(comments.equals(params.comment)))
                        {
                            message = 'There were no changes to this form.'
                            res = false
                        }
                        else 
                        {
                            instance.signedDate = signedDateVal
                            instance.userId = params.signedUserId
                            instance.comments = params.comment
                            res = instance.save(flush: true)
                        }
                    //}
                }
                else
                {
                    //if (signingmode.equalsIgnoreCase('edit'))
                    //{
                    //    message = 'Your QM signature on this case is not found.'
                    //    res = false
                    //}
                    //else
                    //{
                        instance = new CaseQMSignature(caseRecord:caseRecordInstance, signedDate:signedDateVal, userId: params.signedUserId, comments: params.comment)
                        res = instance.save(flush: true) 
                        //if (res) message = 'This QM signature is successfully updated.'
                        //else message = 'Updating QM signature against '+caseRecordInstance.caseId+' case is failed.'
                    //}
                }
                if (!message)
                {
                    if (res) message = 'Your QM signature is successfully signed.'
                    else message = 'QM Signing on '+caseRecordInstance.caseId+' case is failed.'
                }
            }
        }
        else if (signingmode?.equalsIgnoreCase('unsign'))
        {
            if (instance)
            {
                if ((!instance.userId)||(instance.userId.trim().equals('')))
                {
                    res = false
                    message = 'The case '+caseRecordInstance.caseId+' is already unsigned.'
                }
                else if (instance.userId == session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername())
                {
                    instance.signedDate = null//:signedDateVal
                    instance.userId = null//: params.signedUserId
                    res = instance.save(flush: true)
                    if (res)
                    {
                        
                        flash.message = "Your QM signature on case " + caseRecordInstance.caseId +" is successfully unsigned."
                        redirect(controller:"caseRecord", action: "show", id: caseRecordInstance.id)
                        return
                    }
                    else message = 'Unsigning your signature on '+caseRecordInstance.caseId+' case is failed.'
//                    try {
//                        instance.delete(flush: true)
//                        message = 'Your QM signature on '+caseRecordInstance.caseId+' is successfully unsigned.'
//                    }
//                    catch (Exception e) {
//                        message = 'Unsigning your signature on '+caseRecordInstance.caseId+' case is failed. : ' + e.getMessage()
//                        res = false
//                    }
                }
                else
                {
                    res = false
                    message = 'You are not authorized. Unsigning must be done by the signer.'
                }
            }
            else
            {
                res = false
                message = 'System can not find QM signature on the case.'
            }
        }
        else
        {
            message = 'Invalid signing mode: ' + signingmode
            res = false
        }
        
        def instances2 = CaseQMSignature.findAllByCaseRecord(caseRecordInstance)
        instance = null
        instances = []
        
        if (instances2)
        {
            instances2.each() {
                if (it.userId)
                {
                    instances.add(it)
                    if (currID.equals(it.userId)) instance = it
                }
            }
            instances.sort({a,b-> a.signedDate.compareTo(b.signedDate)})
        }
        else instances = null
        
        render(view: "edit", model:[caseQMSignatureInstances: instances, selfInstance: instance, caseRecordInstance:caseRecordInstance, message: message, result: res])
        
    }
    
    def edit = {
        def caseRecordInstance
        //println "params=" + params
        for(int k=0;k<2;k++)
        {
            def key
            if (k == 0) key = params.id?.toString()
            if (k == 1) key = params.caseRecordId?.toString()
            if ((!key)||(key.trim().length() == 0)) continue
            def id_ 
            try { id_ = Integer.parseInt(key) }
            catch(NumberFormatException ne ) { id_ = -1 }
            if (id_ >= 0) caseRecordInstance = CaseRecord.get(id_)
            else caseRecordInstance = CaseRecord.findByCaseId(key)
            if (caseRecordInstance) break
        }
        
        def instances2 = CaseQMSignature.findAllByCaseRecord(caseRecordInstance)
        def instance
        def instances = []
        def currentUser = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()           
        
        if (instances2)
        {
            instances2.each() {
                if (it.userId) 
                {
                    instances.add(it)
                    if (currentUser.equals(it.userId)) instance = it
                }
            }
            instances.sort({a,b-> a.signedDate.compareTo(b.signedDate)})
        }
        else instances = null
        
        return [caseQMSignatureInstances: instances, selfInstance: instance, caseRecordInstance:caseRecordInstance, message: null, result: true]
    }
    
    def viewWithCase = {
        def caseRecordInstance
        
        
        try{Long.parseLong(params.id)
            caseRecordInstance = CaseRecord.get(params.id) 
            
        }
        catch(Exception e){}
            
        if(!caseRecordInstance){
            caseRecordInstance = CaseRecord.findByCaseId(params.id)
            
        }
        def instance = CaseQMSignature.findAllByCaseRecord(caseRecordInstance)
        
        render(view: "view", model:[caseQMSignatureInstance: instance.get(0), withEditButton: true])
    }
    
    def view = {
        
        def instance = CaseQMSignature.get(params.id)
        return [caseQMSignatureInstance: instance, withEditButton: false]
    }
    
    def viewWithEdit = {
        def caseRecordInstance
        
        try{Long.parseLong(params.id)
            caseRecordInstance = CaseRecord.get(params.id)    
        }
        catch(Exception e){}
            
        if(!caseRecordInstance){
            caseRecordInstance = CaseRecord.findByCaseId(params.id)
        }
        def instances2 = CaseQMSignature.findAllByCaseRecord(caseRecordInstance)
        def instance
        def instances = []
        def currentUser = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()       
        if (instances2)
        {
            instances2.each() {
                if (it.userId) 
                {
                    instances.add(it)
                    if (currentUser.equals(it.userId)) instance = it
                }
            }
            instances.sort({a,b-> a.signedDate.compareTo(b.signedDate)})
        }
        else instances = null
        
        //render(view: "view", model:[caseQMSignatureInstances: instances, withEditButton: true])
        //return [caseQMSignatureInstance: instance]
        render(view: "edit", model:[caseQMSignatureInstances: instances, selfInstance: instance, caseRecordInstance:caseRecordInstance, message: null, result: true])
        
    }
}
