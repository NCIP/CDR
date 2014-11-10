package nci.obbr.cahub.util

import org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogEvent
import nci.obbr.cahub.util.pogo.AuditLogEventUnitRecord
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.CandidateRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.CDRBaseClass

class TrailAuditLogController {

    def SEPARATOR = '-'
    def foundObjects = [:]
    //def foundClasses = [:]
    def foundFullClassNames = [:]
    def foundFullClassActionNames = [:]
    //def auditLogEventIDs = []
    
    def index = { 
        //println 'A0.'
        //redirect(action: list, params: params) 
    }

  // the delete, save and update actions only accept POST requests
  //static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    def trailLog = {
      
        //params.each{key,value->
        //println "in fileupload list key: ${key}   value:${value}"
        // }
        //auditLogEventIDs = []
        if (!params.max) params.max = 20
        else params.max = params.int('max')
        if (!params.offset) params.offset = 0
        else params.offset = params.int('offset')
        
        //checkbox value 0=unchecked, 1=checked, 2=checkbox disabled  
        if (!params.candidaterecord) params.candidaterecord = 1
        else params.candidaterecord = params.int('candidaterecord')
        if (!params.caserecord) params.caserecord = 1
        else params.caserecord = params.int('caserecord')
        if (!params.specimens) params.specimens = 1
        else params.specimens = params.int('specimens')
        
        if (!params.sort)
        {
            params.sort = 'lastUpdated'
            params.bsort = 'lastUpdated'
        }
                
        if (!params.order) params.order = 'asc'
               
        def id = params.id
        
        def auditLogEventInstanceList = []
        int auditLogEventInstanceTotal = 0
        def tempList = []
        def errorMessage
        
        int index = -1
        
        def className
        def action
        def objectId
        def parameters
        def childForms
        def goBackObjectId
        while (true)
        {
            if (!id)
            {
                errorMessage = 'Not found parameter value'
                break;
            }
            index = id.indexOf(SEPARATOR)
            
            if (index > 0)
            {
                className = id.substring(0, index)
                action = id.substring(index + 1)
                
                index = action.indexOf(SEPARATOR)
                if (index > 0)
                {
                    def action2 = action.substring(0, index)
                    def idPart = action.substring(index + 1)
                    index = idPart.indexOf(SEPARATOR)
                    if (index > 0)
                    {
                        objectId = idPart.substring(0, index)
                        parameters = idPart.substring(index + 1)
                    }
                    else objectId = idPart
                    action = action2
                }
                else errorMessage = 'Invalid Parameter Format(2):' + id
            }
            else errorMessage = 'Invalid Parameter Format:' + id
            
            if (errorMessage) break
            

            //tempList = AuditLogEvent.findAllByPersistedObjectIdAndClassName(objectId, className)
            def object = getObjectInstance(className, objectId)
            goBackObjectId = objectId 
            while ((!object)&&(className.equalsIgnoreCase('caseQMSignature')))
            {
                def caseRecordInstance
                
                def id_ 
                try { id_ = Integer.parseInt(objectId) }
                catch(NumberFormatException ne ) { id_ = -1 }
                if (id_ >= 0) caseRecordInstance = CaseRecord.get(id_)
                else caseRecordInstance = CaseRecord.findByCaseId(objectId)
                if (!caseRecordInstance) break
                def instances = nci.obbr.cahub.datarecords.qm.CaseQMSignature.findByCaseRecord(caseRecordInstance)  
                if (!instances) break
                
                object = getObjectInstance(className, '' + instances.id)
                if (object) objectId = '' + instances.id
                
                break;
            }
            //println 'A2. className=' + className + ', action=' + action + ', objectId=' + objectId
            
            if (!object)
            {
                errorMessage = 'Any log trail record cannot be found with className=' + className + ', objectId=' + objectId
                break
            }
            else if (object instanceof Exception) 
            {
                Exception ce = (Exception) object
                println '##Audit Log trail Failure 3 '+ce.getClass().getName()+' on ' + className + ', errMessage=' + ce.getMessage()
                ce.printStackTrace()
                errorMessage = ce.getClass().getName() + ' on getting class : ' + ce.getMessage()
                break
            }
            
            def classNameFull = object.getClass().getName()
            
            tempList = getAuditLogUnitRecordInstanceList(classNameFull, objectId)
             
            if ((!tempList)||(tempList.size() == 0))
            {
                errorMessage =  'null out put classNameFull='+classNameFull+',  objectId=' + objectId
                break
            }
//            else 
//            {
//                tempList.each()
//                    {
//                        println 'Find== classNameFull='+classNameFull+',  objectId=' + objectId + ', tempList it=' + it.className
//                    }
//            }
            def caseRecord
            def candidateRecord
            def specimens    
                 
            if ((classNameFull.equalsIgnoreCase('caseRecord'))||(classNameFull.toLowerCase().endsWith('.caserecord')))
            {
                tempList = []
                caseRecord = object
                candidateRecord = caseRecord.candidateRecord
                params.caserecord = 2
            }
            else if ((classNameFull.equalsIgnoreCase('candidateRecord'))||(classNameFull.toLowerCase().endsWith('.candidaterecord')))
            {
                tempList = []
                candidateRecord = object
                caseRecord = candidateRecord.caseRecord
                params.candidaterecord = 2
            }
            else
            {
                try
                {
                    caseRecord = object.caseRecord
                }
                catch(Exception ee)
                {
                    caseRecord = null
                }
                if (caseRecord)
                {
                    candidateRecord = caseRecord.candidateRecord
                }
                else
                {
                    try
                    {
                        candidateRecord = object.candidateRecord
                    }
                    catch(Exception ee)
                    {
                        candidateRecord = null
                    }
                    if (candidateRecord) caseRecord = candidateRecord.caseRecord
                }
            }

            if (caseRecord) specimens = caseRecord.specimens
            // checkbox value 0=unchecked, 1=checked, 2=checkbox disabled  
            if (!caseRecord) params.caserecord = 2
            if (!candidateRecord) params.candidaterecord = 2
            if (!specimens) params.specimens = 2

            if ((caseRecord)&&(params.caserecord > 0))
            {
                def tempList5 = getAuditLogUnitRecordInstanceList(caseRecord.getClass().getName(), caseRecord.id) 
                if (tempList5)
                {
                    tempList.addAll(tempList5)
                }
                //if (params.caserecord < 0) params.caserecord = 0
                //else params.caserecord = 1
            }

            if ((candidateRecord)&&(params.candidaterecord > 0))
            {
                def tempList5 = getAuditLogUnitRecordInstanceList(candidateRecord.getClass().getName(), candidateRecord.id) 
                if (tempList5)
                {
                    tempList.addAll(tempList5)
                }
                //if (params.candidaterecord < 0) params.candidaterecord = 0
                //else params.candidaterecord = 1
            }

            if ((specimens)&&(params.specimens > 0))
            {
                //int n = 0
                specimens.each()
                {
                    def tempList5 = getAuditLogUnitRecordInstanceList(it.getClass().getName(), it.id) 
                    if (tempList5)
                    {
                        tempList.addAll(tempList5)
                        //n++
                        //println 'CCC A6-' + n + ' specimenClass=' + it.getClass().getName() + ', specimenId=' + it.specimenId
                    }
                }
            }
            
            def rValue = getListForChildForms(className, params.childforms, object)
            if (rValue)
            {
                childForms = rValue[0]
                params.childforms = rValue[1]
                if (rValue[2]) tempList.addAll(rValue[2])
            }
            else params.childforms = 'NA'
            
            if (!tempList)
            {
                errorMessage = 'Nothing output'    
            }
            else if (tempList.size() == 0)
            {
                errorMessage = 'Output is an empty list.'    
            }
            else
            {
                if (params.sort)
                {
                    if (params.bsort)
                    {
                        def bsort = params.bsort
                        def border = 'asc'
                        int index2 = params.bsort.indexOf(SEPARATOR)
                        if (index2 > 0)
                        {
                            bsort = params.bsort.substring(0, index2)
                            border = params.bsort.substring(index2 + 1)
                        }
                        if ((params.sort == bsort)&&(params.order == border)) {}
                        else
                        {
                            params.offset = 0
                        }
                    }
                    
                    tempList = processSort(tempList, params.sort, params.order)
                }
                
                //todo sort
                //tempList.sort { it.lastUpdated }
                //int seq = 0
                int size = 0
                def auditLogEventIDs = []
                tempList.each(){
                    
                    if (isDuplicatedItem(auditLogEventIDs, it))
                    {
                        //println 'CCC Duplicate Item id=' + it.recordID + ', className' + it.className + ', objectId=' + it.persistedObjectId
                    }
                    else
                    {
                        auditLogEventInstanceTotal++
                        //seq++
                        if ((auditLogEventInstanceTotal > params.offset)&&(size < params.max))
                        {
                            auditLogEventInstanceList.add(it) 
                            size++
                        }
                        auditLogEventIDs.add(it.recordID)
                    }
                }
            }
            break
        }
              
        if (errorMessage)
        {
            auditLogEventInstanceTotal = 0
        }
        
        def goBackUrl_0 = '/' + className + '/' + action + '/' + goBackObjectId
        if (parameters) goBackUrl_0 = goBackUrl_0 + '?' + parameters
        [auditLogEventInstanceList: auditLogEventInstanceList, errorMessage:errorMessage, childForms:childForms, auditLogEventInstanceTotal: auditLogEventInstanceTotal, className: className, objectId: objectId, goBackUrl: goBackUrl_0 ]
    }
    
    private boolean isDuplicatedItem(auditLogEventIDs, auditLogEventInstance)
    {
        boolean isDuplicate = false
        auditLogEventIDs.each(){
                    if (it == auditLogEventInstance.recordID) isDuplicate = true
                    //println 'it='+it +', instanceId=' + auditLogEventInstance.recordID + ', isDuplicate=' + isDuplicate
                }
        return isDuplicate
    }
    
    private List processSort(auditLogEventInstanceList, sort, order)
    {
        if (params.sort)
        {
            if (!order) order = 'asc'
            if (sort.equalsIgnoreCase('dateCreated'))
            {
                if (order.equalsIgnoreCase('asc')) auditLogEventInstanceList.sort({a,b-> a.dateCreated.compareTo(b.dateCreated)})
                else auditLogEventInstanceList.sort({a,b-> b.dateCreated.compareTo(a.dateCreated)})
            }
            if (sort.equalsIgnoreCase('lastUpdated'))
            {
                if (order.equalsIgnoreCase('asc')) auditLogEventInstanceList.sort({a,b-> a.lastUpdated.compareTo(b.lastUpdated)})
                else auditLogEventInstanceList.sort({a,b-> b.lastUpdated.compareTo(a.lastUpdated)})
            }
            if (sort.equalsIgnoreCase('actor'))
            {
                if (order.equalsIgnoreCase('asc')) auditLogEventInstanceList.sort({a,b-> a.actor.compareTo(b.actor)})
                else auditLogEventInstanceList.sort({a,b-> b.actor.compareTo(a.actor)})
            }
            if (sort.equalsIgnoreCase('eventName'))
            {
                if (order.equalsIgnoreCase('asc')) auditLogEventInstanceList.sort({a,b-> a.eventName.compareTo(b.eventName)})
                else auditLogEventInstanceList.sort({a,b-> b.eventName.compareTo(a.eventName)})
            }
            if (sort.equalsIgnoreCase('className'))
            {
                if (order.equalsIgnoreCase('asc')) auditLogEventInstanceList.sort({a,b-> a.className.compareTo(b.className)})
                else auditLogEventInstanceList.sort({a,b-> b.className.compareTo(a.className)})
            }
            if (sort.equalsIgnoreCase('persistedObjectId'))
            {
                if (order.equalsIgnoreCase('asc')) auditLogEventInstanceList.sort({a,b-> a.persistedObjectId.compareTo(b.persistedObjectId)})
                else auditLogEventInstanceList.sort({a,b-> b.persistedObjectId.compareTo(a.persistedObjectId)})
            }
            if (sort.equalsIgnoreCase('propertyName'))
            {
                if (order.equalsIgnoreCase('asc')) auditLogEventInstanceList.sort({a,b-> a.propertyName.compareTo(b.propertyName)})
                else auditLogEventInstanceList.sort({a,b-> b.propertyName.compareTo(a.propertyName)})
            }
            if (sort.equalsIgnoreCase('oldValue'))
            {
                if (order.equalsIgnoreCase('asc')) auditLogEventInstanceList.sort({a,b-> a.oldValue.compareTo(b.oldValue)})
                else auditLogEventInstanceList.sort({a,b-> b.oldValue.compareTo(a.oldValue)})
            }
            if (sort.equalsIgnoreCase('newValue'))
            {
                if (order.equalsIgnoreCase('asc')) auditLogEventInstanceList.sort({a,b-> a.newValue.compareTo(b.newValue)})
                else auditLogEventInstanceList.sort({a,b-> b.newValue.compareTo(a.newValue)})
            }
            if (sort.equalsIgnoreCase('note'))
            {
                if (order.equalsIgnoreCase('asc')) auditLogEventInstanceList.sort({a,b-> a.note.compareTo(b.note)})
                else auditLogEventInstanceList.sort({a,b-> b.note.compareTo(a.note)})
            }
        }
        return auditLogEventInstanceList
    }
  
    private AuditLogEventUnitRecord getAuditLogEventUnitRecord(auditLogEventInstance) {
       
        def classNameFull = getClassNameFull(auditLogEventInstance.className)
        def objectId = auditLogEventInstance.persistedObjectId
        
        def controllerNameFull
        //Class cls
        String actionLink
        def action
        while (true)
        {
            controllerNameFull = classNameFull + 'Controller'
           
            if (foundFullClassActionNames) {
                actionLink = foundFullClassActionNames.get(controllerNameFull)  
            }
            
            if (actionLink) break
                       
            try
            {
                java.lang.reflect.Method[] m = Thread.currentThread().contextClassLoader.loadClass(controllerNameFull).getDeclaredMethods() 
                if (m)
                {
                    int c = -1
                    for(java.lang.reflect.Method m1: m)
                    {
                        int n = -1
                        
                        if (m1.getName().equalsIgnoreCase('display')) n = 3
                        if (m1.getName().equalsIgnoreCase('view')) n = 2
                        if (m1.getName().equalsIgnoreCase('show')) n = 1
                        if (c < n)
                        {
                            if (n == 3) action = '/display/'
                            if (n == 2) action = '/view/'
                            if (n == 1) action = '/show/'
                            c = n
                        }
                    }
                }
            }
            catch(Exception ce)
            {
                action = null
                println 'TrailAuditLog.getAuditLogEventUnitRecord() 1 Exception=' + ce.getClass().getName() + ', action=' + actionLink + ', controllerName=' + controllerNameFull
                
            }

            break
        }
        
        def finalLink
        
        if (actionLink)
        {
            finalLink = actionLink + objectId
        }
        else if (action)
        { 
            int index = classNameFull.lastIndexOf('.')
            def contName = classNameFull.substring(index + 1)

            def finalLink3 = '/' + contName.substring(0,1).toLowerCase() + contName.substring(1) + action
            foundFullClassActionNames.put(controllerNameFull, finalLink3)
            finalLink = finalLink3 + objectId
        }
        
        return new AuditLogEventUnitRecord( recordID: auditLogEventInstance.id, 
                                           dateCreated: auditLogEventInstance.dateCreated,
                                           lastUpdated: auditLogEventInstance.lastUpdated,
                                           actor: auditLogEventInstance.actor,
                                           uri: auditLogEventInstance.uri,
                                           className: classNameFull,
                                           persistedObjectId: objectId,
                                           eventName: auditLogEventInstance.eventName,
                                           propertyName: auditLogEventInstance.propertyName,
                                           oldValue: auditLogEventInstance.oldValue,
                                           newValue: auditLogEventInstance.newValue,
                                           link: finalLink)                                
    }
    
    private List getAuditLogEventUnitRecordList(auditLogEventInstanceList) {
       
        def auditLogEventUnitRecordList = []
        //println 'CCCC ZZZZ1 auditLogEventInstanceList=' + auditLogEventInstanceList 
        if ((auditLogEventInstanceList)&&(auditLogEventInstanceList.size() > 0))
        {
            //println 'CCCC ZZZZ2 auditLogEventInstanceList.size()=' + auditLogEventInstanceList.size() 
            auditLogEventInstanceList?.each(){
            
                def newInstance = getAuditLogEventUnitRecord(it)
            
                Object object = getObjectInstance(it.className, it.persistedObjectId)

                if ((object)&& !(object instanceof Exception))
                {
                    newInstance.note = getNote(object)
                }

                auditLogEventUnitRecordList.add(newInstance)
            }
        }
        else
        {
            return null
        }
        
        return auditLogEventUnitRecordList
    }
    private String getClassNameFull(className)
    {
        return getClassNameFull(className, null)
    }
    private String getClassNameFull(className, objectId)
    {
        if (className.equalsIgnoreCase('CaseQMSignature')) return 'nci.obbr.cahub.datarecords.qm.CaseQMSignature'
        if (className.indexOf('.') > 0)
        {
            if (className.equals('nci.obbr.cahub.forms.bpv.BpvClinicalDataEntry'))
            {
                return 'nci.obbr.cahub.forms.bpv.clinicaldataentry.BpvClinicalDataEntry' 
            }
            if (className.equals('nci.obbr.cahub.forms.bpv.TherapyRecord'))
            {
                return 'nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord' 
            }
            //println 'CCC 31 already full className=' +className
            return className
        }
        
        def classNameN = className.substring(0, 1).toUpperCase() + className.substring(1)
        //println 'CCC 32 className=' +classNameN +', objectId=' + objectId
        def res
        if (foundFullClassNames) {
            res = foundFullClassNames.get(className)       
        }
        if (res) return res
        
        def tempList2
       
        def tempC = AuditLogEvent.findByClassNameLike('%.' + classNameN)
        
        if (!tempC)
        {
            tempC = AuditLogEvent.findByClassName(classNameN)
        }
        if (tempC)
        {
            String cs
            if (tempC.className.equals('nci.obbr.cahub.forms.bpv.BpvClinicalDataEntry'))
            {
                cs = 'nci.obbr.cahub.forms.bpv.clinicaldataentry.BpvClinicalDataEntry' 
            }
            else if (tempC.className.equals('nci.obbr.cahub.forms.bpv.TherapyRecord'))
            {
                cs = 'nci.obbr.cahub.forms.bpv.clinicaldataentry.TherapyRecord' 
            }
            else
            {
                cs = tempC.className
            }
            //println 'CCC 34 find tempC.className=' + tempC.className
            foundFullClassNames.put(className, cs)
            return cs
        }
       
        if (!objectId) return null
        
        tempList2 = AuditLogEvent.findAllByPersistedObjectId(objectId)
        
                
        if (!tempList2)
        {
            return null
        }
        def cn       
        tempList2.each(){

            def classNameC = it.className
            if (classNameC)
            {
                if ((classNameC.equalsIgnoreCase(className))||(classNameC.toLowerCase().endsWith('.' + className.toLowerCase())))
                {
                    //println 'CCC 37 find getClassNameFull()=' + classNameC
                    cn = classNameC
                }
            }
        }
        //println 'CCC 38 cn=' + cn
        if (cn) return cn
        //println 'CCC 39'
        return null
        
        
    }
    
    private Object getObjectInstance(className, objectId)
    {
        String classNameFull = getClassNameFull(className, objectId)
        
        if (!classNameFull)
        {
            if (className.equals('SOP')) {}
            else
            {
                classNameFull = getClassNameFull(className + 'Record', objectId)
            }
            if (!classNameFull) return null
        }
        //println 'CCCC!!!! classNameFull=' + classNameFull
        def objectTemp
        if (foundObjects) {
            objectTemp = foundObjects.get(classNameFull + SEPARATOR + objectId)        
        }
                    
        //println 'CCC 12 className='+className + ', objectId=' + objectId +', classNameFull=' + classNameFull + ', object=' + objectTemp
        if (objectTemp)
        {
            return objectTemp
        }
        //println 'CCC 12 X'
        try
        {
            objectTemp = Thread.currentThread().contextClassLoader.loadClass(classNameFull).get(objectId)
        }
        catch(Exception ee)
        {
            //objectTemp = null
            return ee
            //return '22 TrailAuditLog.getObjectInstance() Exception=' + ee.getClass().getName() + ', className=' + className + ', classNameFull=' + classNameFull + ', message=' + ee.getMessage()
        }
                
        if (objectTemp)
        {
            //println 'CCC 12 A'
            foundObjects.put(classNameFull + SEPARATOR + objectId, objectTemp)
            return objectTemp
        }
        //println 'CCC 12 B'
        return null
    }
    private List getAuditLogUnitRecordInstanceList(className, objectId)
    {
        String classNameFull = getClassNameFull(className, objectId)
        if (!classNameFull) return null
        def tempList = AuditLogEvent.findAllByPersistedObjectIdAndClassName(objectId, classNameFull)
        return getAuditLogEventUnitRecordList(tempList)
    }
    private String getNote(object)
    {
        if (!object) return null
        if (object instanceof CaseRecord)
        {
            return 'CaseId:' + object.caseId
        }
        else if (object instanceof CandidateRecord)
        {
            return 'CandidateId:' + object.candidateId
        }
        else if (object instanceof SpecimenRecord)
        {
            return 'SpecimenId:' + object.specimenId
        }
        else if (object instanceof nci.obbr.cahub.forms.gtex.crf.SurgicalMedication)
        {
            return object.medicationName
        }
        
        CaseRecord caseRecord
        try
        {
            caseRecord = object.caseRecord
        }
        catch(Exception ee)
        {
            return null
        }
        if (caseRecord) return 'CaseId:' + caseRecord.caseId
        return null
    }
    
    private Object[] getListLinkToParent(childClassName, parentObject)
    {
        def tempListMain = []
        def foundObjects = []
        String parentClassName = parentObject.getClass().getName()
        String propertyName
        int idx = parentClassName.lastIndexOf('.')  
        
        if (idx >= 0) propertyName = parentClassName.substring(idx + 1)
        else propertyName = parentClassName
            
            def tempList = AuditLogEvent.findAllByClassName(getClassNameFull(childClassName))
            
            if ((!tempList)||(tempList.size() == 0))
            {
                println 'TrailAuditLog.getListLinkToParent Error('+childClassName+'):Null result list'
                return null
            }
            
            for(AuditLogEvent eventRecord:tempList)
            {
                Object obj = getObjectInstance(eventRecord.className, eventRecord.persistedObjectId)
                if (!obj) continue
                if (obj instanceof Exception) continue
                Object obj2
                try
                {
                    obj2 = obj."${propertyName.substring(0,1).toLowerCase() + propertyName.substring(1)}"
                }
                catch(Exception ee)
                {
                    println 'TrailAuditLog.getListLinkToParent Error('+childClassName+'):' + ee.getMessage()
                }
                 
                if (obj2 == parentObject)
                {
                    //println 'getListLinkToParent child('+childClassName+'): found'
                    tempListMain.add(eventRecord)
                    foundObjects.add(obj2)
                    //continue
                }
            }
        if (!tempListMain)
        {
            println 'TrailAuditLog.getListLinkToParent Error('+childClassName+'):No object found.'
            return null
        }
        return [ tempListMain, foundObjects ]
    }
    
    private Object[] getListForChildForms(String className, String childFormsDisplay, Object object)
    {
        if (!object)
        { 
            return null
        }
        
        def classMap = [:]
        def tempListMain = []
        def objects = []
        def childForms2 = []
        def childFormsDisplay2 = ''
        
        classMap.put(className, object)
        boolean isFirstRun = true
        def alreadyDoneClassMap = [:]
        while((classMap)&&(classMap.size() > 0))   
        {
            def classMap2 = [:]
            String childFormsDisplay3
            childFormsDisplay3 = childFormsDisplay
            
                        
            classMap.each() { key, value ->
                
                String key2 = key
                int idx = key.indexOf(':')
                if (idx > 0) key2 = key.substring(0, idx)
                Object[] rValue    
                if (!isAlreadyDoneClassMap(alreadyDoneClassMap, key2, value))
                {
                    rValue = getListForChildFormsRecursive(key2, childFormsDisplay3, value, isFirstRun)    
                }
                else println 'Trail Audit Log: Duplicate Object: childClassName=' + key2 + ', objectClass=' + value.getClass().getName() + ', objectID=' + value.id
                
                if (rValue)
                {
                    if (isFirstRun)
                    {
                        childForms2 = rValue[0]
                        childFormsDisplay2 = rValue[1]
                    }
                    
                    if (rValue[2])
                    {
                        tempListMain.addAll(rValue[2])
                        alreadyDoneClassMap.put(key2, value)
                    }
                    if (rValue[3]) classMap2 = addClassMap(classMap2, rValue[3])
                }
                else 
                {
                    if (isFirstRun) return null
                }
            } 
            isFirstRun = false
            classMap = classMap2
        }
        
        return [childForms2, childFormsDisplay2, getAuditLogEventUnitRecordList(tempListMain)]
    }
    private boolean isAlreadyDoneClassMap(Map alreadyDoneClassMap, String childClassName, Object parentObject)
    {
        if (!alreadyDoneClassMap) return false
        boolean isFound = false
        alreadyDoneClassMap.each() { key, value ->
            if ((childClassName?.equals(key))&&(parentObject == value)) isFound = true
        }
        return isFound
    }
    private Map addClassMap(map1, map2)
    {
        if (!map2) return map1
        if (map1 == null) map1 = [:]
        int seq = map1.size()
        map2.each() { key, value ->
            seq++
            map1.put(key + ':' + seq, value)
        }
        return map1
    }
    
    private Object[] getListForChildFormsRecursive(String className, String childFormsDisplay, Object object, boolean isFirstRun)
    {   
        if (!object)
        { 
            //println 'CCCC K99- Object is null'
            return null
        }
        def childForms = getChildFormList(className)
        
        if (!childForms) return null
        def tempListMain = []
        def objects = []
        def childForms2 = []
        def childFormsDisplay2 = ''
        def classMap = [:]
        def classMapSeq = 0
        // println 'CCCC K0-- thisClass=' + className + ', childFormsDisplay=' + childFormsDisplay
        for(int i=0;i<childForms.size();i++)
        {
            String achar
            
            if (isFirstRun)
            {
                try
                {
                    achar = childFormsDisplay.substring(i, i+1)
                    if ((achar.equals('X'))||(achar.equals('O'))||(achar.equals('D'))) {}
                    else
                    {
                        achar = 'X'
                    }
                }
                catch(Exception ee)
                {
                    achar = 'X' 
                }
            }
            else
            {
                achar = 'O' 
            }
            
                       
            def childClassName = childForms.get(i)
            
            def isMulti = false
            def hasChild = false
            def isLinkToParent = false
            
            while((childClassName.startsWith('*'))||(childClassName.startsWith('!'))||(childClassName.startsWith('^')))
            {
                if (childClassName.startsWith('*'))
                {
                    isMulti = true
                    childClassName = childClassName.substring(1)
                }
                if (childClassName.startsWith('!'))
                {
                    hasChild = true
                    childClassName = childClassName.substring(1)
                }
                if (childClassName.startsWith('^'))
                {
                    isLinkToParent = true
                    childClassName = childClassName.substring(1)
                }
            }
                        
            if (!hasChild)
            {
                if (getChildFormList(childClassName)) hasChild = true
            }
            
            childForms2.add(childClassName)
            if ((achar == 'X')||(achar == 'D'))
            {
                childFormsDisplay2 = childFormsDisplay2 + achar
                continue
            }
            def classNameFull = getClassNameFull(childClassName)
            if (!classNameFull)
            {
                childFormsDisplay2 = childFormsDisplay2 + 'D'
                continue
            }
            // println '   CCCC K1-- child=' + childClassName
            if (isLinkToParent)
            {
                def ret = getListLinkToParent(childClassName, object)
                if (ret)
                {
                    def tempList = ret[0]
                    if ((tempList)&&(tempList.size() > 0))
                    {
                        tempListMain.addAll(tempList)
                        def obj1 = ret[1]
                        if ((hasChild)&&(obj1))
                        {
                            obj1.each() {
                                classMapSeq++
                                classMap.put(childClassName + ':' + classMapSeq, it)
                            }
                        }
                    }
                    else achar = 'D'
                }
                else achar = 'D'
                childFormsDisplay2 = childFormsDisplay2 + achar
                continue
            }
            
            try
            {
                
                def propertyName = childClassName.substring(0,1).toLowerCase() + childClassName.substring(1)
                if (isMulti)
                {
                    if (childClassName.equalsIgnoreCase('SurveyAnswer'))
                    {
                        propertyName = 'answers'
                    }
                    else if ((childClassName.equalsIgnoreCase('SurveyRecord'))||(childClassName.equalsIgnoreCase('Survey')))
                    {
                        propertyName = 'surveys'
                    }
                    else if (childClassName.endsWith('y'))
                    {
                        propertyName = propertyName.substring(0, (propertyName.length()-1)) + 'ies'
                    }
                    else if (childClassName.endsWith('s'))
                    {
                        propertyName = propertyName + 'es'
                    }
                    else propertyName = propertyName + 's'
                }
                
                def obj1
                int turn = 0
                while(true)
                {
                    try
                    {
                        obj1 = object."${propertyName}"
                        if (obj1) break
                    }
                    catch(Exception ee)
                    {
                        if (turn > 0)
                        {
                            println 'Audit Log Trail(' +turn+'): Error on getting object from ' + childClassName + ', propertyName='+ propertyName + ', ErrorClass' + ee.getClass().getName() + ", message=" + ee.getMessage()
                            break
                        }
                    }
                    turn++
                    
                    java.lang.reflect.Field[] fields = object.getClass().getDeclaredFields() 
                    if ((!fields)||(fields.length == 0)) break
                    def meetObjects = []
                    def fieldNameStored
                    def fieldType
                    for(int k=0;k<fields.length;k++)
                    {
                        java.lang.reflect.Field field = fields[k]
                        String fieldName = field.getName() 
                        String classTypeName = field.getType().getName()
                        //if (!classTypeName.startsWith('nci.')) continue
                        //println 'CCCC GGG fieldName=' + fieldName + ', classTypeName=' + classTypeName + ', isMulti=' + isMulti
                        if (isMulti)
                        {
                            if (classTypeName.endsWith('List')) {}
                            else if (classTypeName.endsWith('.Set')) {}
                            else continue
                            //println 'CC2 fieldName=' + fieldName + ', classTypeName=' + classTypeName + ', isMulti=' + isMulti
                            def obj2
                            try
                            {
                                obj2 = object."${fieldName}"
                            }
                            catch(Exception ee1)
                            {
                                println 'TrailAuditLog.getListForChildFormsRecursive() Exception classTypeName=' + classTypeName + ', fieldName=' + fieldName + ', ee=' + ee1.getMessage()
                                continue
                            }
                            if (!obj2)
                            {
                                println 'TrailAuditLog.getListForChildFormsRecursive() No object found classTypeName=' + classTypeName + ', fieldName=' + fieldName
                                continue
                            }
                            boolean isTheClass = true
                            obj2.each() {
                                String classTypeName2 = it.getClass().getName()
                                if ((classTypeName2.equals(childClassName))||(classTypeName2.endsWith('.' + childClassName)))
                                {}
                                else
                                {
                                    isTheClass = false 
                                }
                            }
                            if (isTheClass)
                            {
                                meetObjects.add(field)
                                fieldNameStored = fieldName
                                fieldType = classTypeName
                            }
                        }
                        else
                        {
                            //if (!classTypeName.startsWith('nci.')) continue
                            //println 'CCC1 fieldName=' + fieldName + ', classTypeName=' + classTypeName + ', isMulti=' + isMulti
                            if ((classTypeName.equals(childClassName))||(classTypeName.endsWith('.' + childClassName)))
                            {
                                meetObjects.add(field)
                                fieldNameStored = fieldName
                                fieldType = classTypeName
                            }
                        }
                    }
                    if (meetObjects.size() == 0)
                    {
                        println 'TrailAuditLog.getListForChildFormsRecursive() NOT found the property propertyName=' + propertyName +  ', fieldType=' + fieldType + ', isMulti=' + isMulti  + ', childClass=' + childClassName
                        break
                    }
                    else if (meetObjects.size() == 1)
                    {
                        propertyName = fieldNameStored
                        //println 'CC5 find the property fieldName=' + propertyName + ', fieldType=' + fieldType + ', isMulti=' + isMulti  + ', childClass=' + childClassName
                        continue
                    }
                    
                    fieldNameStored = null
                    String cn = childClassName.toLowerCase()
                    meetObjects.each() {
                        
                        String fieldName = it.getName() 
                        String fn = fieldName.toLowerCase()
                        
                        if (!fieldNameStored)
                        {
                            if (fn.equals(cn)) fieldNameStored = fieldName
                            else
                            {
                                String base
                                String target
                                
                                if (fn.length() > cn.length())
                                {
                                    base = fn
                                    target = cn
                                }
                                else 
                                {
                                    base = cn
                                    target = fn
                                }
                                
                                for(int j=0;j<2;j++)
                                {
                                    while(true)
                                    {
                                        int index = base.indexOf(target) 
                                        if (index >= 0)
                                        {
                                            fieldNameStored = fieldName
                                            break
                                        }
                                        target = target.substring(0, (target.length() - 1))
                                        if (target.length() < 5) break
                                    }
                                    if (fieldNameStored) break
                                    
                                    if (base.equals(fn))
                                    {
                                        base = cn
                                        target = fn
                                    }
                                    else
                                    {
                                        base = fn
                                        target = cn
                                    }
                                }
                            }
                        }
                    }
                    if (fieldNameStored)
                    {
                        propertyName = fieldNameStored
                        println 'TrailAuditLog.getListForChildFormsRecursive() property found!! fieldName=' + propertyName + ', fieldType=' + fieldType + ', isMulti=' + isMulti  + ', childClass=' + childClassName
                        continue
                    }
                }
                
                //println 'CCCC C1 ::' + className + ':' + childClassName + ':' + propertyName + ':' + isMulti
                if (obj1)
                {
                    if (hasChild)
                    {
                        if (isMulti)
                        {
                            obj1.each() {
                                
                                classMapSeq++
                                //println 'CCCC C2 ::' + childClassName + ':' + classMapSeq +':'+ it.getClass().getName()
                                classMap.put(childClassName + ':' + classMapSeq, it)
                                objects.addAll(it)
                            }
                        }
                        else
                        {
                            classMapSeq++
                            classMap.put(childClassName + ':' + classMapSeq, obj1)
                            objects.add(obj1)
                        }
                    }
                }
                else
                {
                    println 'Audit Log Trail: Null object from ' + className + ', propertyName=' + propertyName
                    achar = 'D'
                }
                
                /*
                if (childClassName.equalsIgnoreCase('Demographics'))
                {
                    def obj = object.demographics
                    if (obj) objects.add(obj)
                    else achar = 'D'
                }
                if (childClassName.equalsIgnoreCase('MedicalHistory'))
                {
                    def obj = object.medicalHistory
                    if (obj)
                    {
                        objects.add(obj)
                    
                        def obj2 = obj.cancerHistories
                        if (obj2) objects.addAll(obj2)
                        def obj3 = obj.generalMedicalHistories
                        if (obj3) objects.addAll(obj3)
                    }
                    else achar = 'D'
                    //String cc = 'C'
                    //def result = getListForCaseReportForm2(childClassName, cc, obj)
                    //if (result) tempListMain.addAll(result[2])
                }
                if (childClassName.equalsIgnoreCase('ConcomitantMedication'))
                {
                    def obj = object.concomitantMedications
                    
                    if (obj) objects.addAll(obj)
                    else achar = 'D'
                }
                if (childClassName.equalsIgnoreCase('DeathCircumstances'))
                {
                    def obj = object.deathCircumstances
                    if (obj) objects.add(obj)
                    else achar = 'D'
                }
                if (childClassName.equalsIgnoreCase('serologyResult'))
                {
                    def obj = object.serologyResult
                    if (obj) objects.add(obj)
                    else achar = 'D'
                }
                if (childClassName.equalsIgnoreCase('surgicalProcedures'))
                {
                    def obj = object.surgicalProcedures
                    if (obj)
                    {
                        objects.add(obj)
                        def obj2 = obj.surgicalMedications
                        if (obj2) objects.addAll(obj2)
                    }
                    else achar = 'D'
                    
                    //String cc = 'CC'
                    //def result = getListForCaseReportForm2(childClassName, cc, obj)
                    //if (result) tempListMain.addAll(result[2])
                }
                if (childClassName.equalsIgnoreCase('CancerHistory'))
                {
                    def obj = object.cancerHistories
                    if (obj) objects.addAll(obj)
                    else achar = 'D'
                }
                if (childClassName.equalsIgnoreCase('GeneralMedicalHistory'))
                {
                    def obj = object.generalMedicalHistories
                    if (obj) objects.addAll(obj)
                    else achar = 'D'
                }
                if (childClassName.equalsIgnoreCase('SurgicalMedication'))
                {
                    def obj = object.surgicalMedications
                    if (obj) objects.addAll(obj)
                    else achar = 'D'
                }
                if (childClassName.equalsIgnoreCase('TherapyRecord'))
                {
                    def obj = object.therapyRecords
                    if (obj) objects.addAll(obj)
                    else achar = 'D'
                }
                */
                
            }
            catch(Exception ee)
            {
                println 'Audit Log Trail Exception -- childClassName=' + childClassName + ', Exception=' + ee.getClass().getName() + ', message=' + ee.getMessage()
                ee.printStackTrace()
                //continue
            }
            childFormsDisplay2 = childFormsDisplay2 + achar
        }
        
        
            if (objects)
            {
                for(Object obj:objects)
                {
                    if (obj)
                    {
                        def tempList3 = AuditLogEvent.findAllByPersistedObjectIdAndClassName(obj.id, obj.getClass().getName())
                        if (tempList3) tempListMain.addAll(tempList3)
                        //else println 'CCC GG Not Found Class id=' + obj.id + ', Class' + obj.getClass().getName()
                    }

                }
                
            }
            
        
        
        //if (!tempListMain) return null
        return [childForms2, childFormsDisplay2, tempListMain, classMap]
    }
    
    private List getChildFormList(String className)
    {
        def childForms 
        
        if (className.equalsIgnoreCase('BpvWorkSheet'))
        {
            childForms = ['^Module1Sheet', '^Module2Sheet', '^Module3Sheet', '^Module4Sheet', '^QcAndFrozenSample']
        }
        if (className.equalsIgnoreCase('CaseReportForm'))
        {
            childForms = ['*ConcomitantMedication', 'DeathCircumstances', 'Demographics', '!MedicalHistory', 'SerologyResult', '!SurgicalProcedures']
        }
        if (className.equalsIgnoreCase('MedicalHistory'))
        {
            childForms = ['*CancerHistory', '*GeneralMedicalHistory']
        }
        if (className.equalsIgnoreCase('SurgicalProcedures'))
        {
            childForms = ['*SurgicalMedication']
        }
        if (className.equalsIgnoreCase('BpvClinicalDataEntry'))
        {
            childForms = ['*TherapyRecord']
        }
        if ((className.equalsIgnoreCase('Survey'))||(className.equalsIgnoreCase('SurveyRecord')))
        {
            childForms = ['*SurveyAnswer']
        }
        if ((className.equalsIgnoreCase('InterviewRecord'))||(className.equalsIgnoreCase('Interview')))
        {
            childForms = ['*SurveyRecord', 'BpvElsiCrf']
        }
        return childForms
    }
    
}
