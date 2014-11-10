package nci.obbr.cahub

import nci.obbr.cahub.staticmembers.ContainerType
import nci.obbr.cahub.staticmembers.Fixative
import nci.obbr.cahub.staticmembers.AcquisitionType
import nci.obbr.cahub.datarecords.ChpBloodRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.forms.bpv.BpvBloodForm


class BpvBloodFormService {

    static transactional = true

    def bloodList() {
        def bloodListMap
        AcquisitionType.each() {
            if (it.code.contains("BLOOD")) {
                bloodListMap.add(it)
            }
        }
        return bloodListMap
    }
    
    def saveForm(params, request){
//        println "saveForm: "
//        println "params: " + params
        def bpvBloodFormInstance
        try {
             
            if (params.id) {
                // if the bpvBloodForm has previously been persisted then ...
                bpvBloodFormInstance = BpvBloodForm.get(params.id)
            } else {
                // otherwise
                bpvBloodFormInstance = new BpvBloodForm()
            }
            bpvBloodFormInstance.properties = params  // overwrite existing values (if they exist) with the values from the form
            def specimenId = params.specimenId
            if(specimenId){ // add specimen ID to caseRecord of bpvBloodForm
                def tissueType = new AcquisitionType()
                def fixative = new Fixative()
                def containerType = new ContainerType()
                
                switch (params.specimenRecordInstance.tissueType.code) {
                    case "BLOODW":
                        tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
                        fixative   = Fixative.findByCode("EDTAT")
                        containerType = ContainerType.findByCode("LAVEDTA")
                        break
                    case "BLOODPLAS": 
                        tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
                        fixative   = Fixative.findByCode("EDTAT")
                        containerType = ContainerType.findByCode("LAVEDTA")
                        break
                    case "BLOODSRM": 
                        tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
                        fixative   = Fixative.findByCode("SST")
                        containerType = ContainerType.findByCode("SST")
                        break
                    default:
                        tissueType = "ERROR"
                        
                }
                def caseRecordInstance = CaseRecord.findByCaseId(params.caseId)
                def specimenRecordInstance = new SpecimenRecord()
                def chpBloodRecordInstance = new ChpBloodRecord()
                if (caseRecordInstance) {
                    specimenRecordInstance.specimenId = specimenId
                    specimenRecordInstance.tissueType = tissueType
                    specimenRecordInstance.provisionalTissueType = tissueType
                    specimenRecordInstance.fixative = fixative
                    specimenRecordInstance.inQuarantine = true
                    specimenRecordInstance.wasConsumed = true  // for top-level blood tubes.  Have to see how this one plays out...
                    specimenRecordInstance.containerType = containerType
                    chpBloodRecordInstance.volume = params.volume.toFloat()
                    specimenRecordInstance.chpBloodRecord = chpBloodRecordInstance
                    chpBloodRecordInstance.specimenRecord = specimenRecordInstance
                    caseRecordInstance.addToSpecimens(specimenRecordInstance)
                    caseRecordInstance.bpvBloodForm = bpvBloodFormInstance
                    specimenRecordInstance.save(failOnError:true)
                    chpBloodRecordInstance.save(failOnError:true)
                    caseRecordInstance.save(failOnError:true)
                    bpvBloodFormInstance.caseRecord = caseRecordInstance
                    bpvBloodFormInstance.save(failOnError:true)
                } else {
                    //throw new exception() here
                }
            }
                        
        } catch(Exception e){
               // e.printStackTrace()
               throw new RuntimeException(e.toString())
        }                
                
    }
    

def saveParent (params, bpvBloodFormInstance, request) {
                if (params.version) {
                    def version = params.version.toLong()
                    if (bpvBloodFormInstance.version > version) {

                        bpvBloodFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvBloodFormInstance?.formMetadata?.cdrFormName] as Object[], "Another user has updated this BpvBloodForm while you were editing")
                        def warningMap = getWarningMap(bpvBloodFormInstance)
                        render(view: "edit", model: [bpvBloodFormInstance: bpvBloodFormInstance, warningMap: warningMap])
                        return
                    }
                }
                
        try {
            if (params.id) {
                bpvBloodFormInstance = BpvBloodForm.get(params.id)
            } else {
                bpvBloodFormInstance = new BpvBloodForm()
            }
            bpvBloodFormInstance.properties = params  

            def specimenId = (params.specimenId).trim()

            if(specimenId){ 
                def tissueType = new AcquisitionType()
                def fixative = new Fixative()
                def containerType = new ContainerType()
        tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
        containerType = ContainerType.findByCode(params.specimenRecordInstance.containerType.code)

                switch (params.specimenRecordInstance.containerType.code) {
                    case "DNAPAX":
                        fixative   = Fixative.findByCode("XG")
                        break
                    case "RNAPAX":
                        fixative   = Fixative.findByCode("XG")
                        break
                    case "LAVEDTA": 
                        fixative   = Fixative.findByCode("EDTA")
                        break
                    case "SST": 
                        fixative   = Fixative.findByCode("FRESH")
                        break
                    default:
                        tissueType = "ERROR"
                }
        
        
                def caseRecordInstance = CaseRecord.findByCaseId(params.caseId)
                def specimenRecordInstance = new SpecimenRecord()
                def chpBloodRecordInstance = new ChpBloodRecord()
                if (caseRecordInstance) {
                    specimenRecordInstance.specimenId = specimenId
                    specimenRecordInstance.tissueType = tissueType
                    specimenRecordInstance.provisionalTissueType = tissueType
                    specimenRecordInstance.fixative = fixative
                    specimenRecordInstance.inQuarantine = true
                    if (params.specimenRecordInstance.containerType.code.equalsIgnoreCase("DNAPAX") || params.specimenRecordInstance.containerType.code.equalsIgnoreCase("RNAPAX")) {
                        specimenRecordInstance.wasConsumed = false
                    } else {
                        specimenRecordInstance.wasConsumed = true
                    }
                    specimenRecordInstance.containerType = containerType
                    if (params.drawtime!="") {
                        specimenRecordInstance.bloodTimeDraw = params.drawtime
                    } else {
                        specimenRecordInstance.bloodTimeDraw = ""
                    }
                    
                    chpBloodRecordInstance.volume = params.volume.toFloat()
                    specimenRecordInstance.chpBloodRecord = chpBloodRecordInstance
                    chpBloodRecordInstance.specimenRecord = specimenRecordInstance
                    caseRecordInstance.bpvBloodForm = bpvBloodFormInstance
                        caseRecordInstance.addToSpecimens(specimenRecordInstance)                    
                    if (!(specimenRecordInstance.validate())) {
                        caseRecordInstance.removeFromSpecimens(specimenRecordInstance)
                        bpvBloodFormInstance.errors = specimenRecordInstance.errors
                        if (specimenRecordInstance.specimenId.indexOf("")) {
                            bpvBloodFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvBloodFormInstance?.formMetadata?.cdrFormName] as Object[], "Invalid specimen ID")
                        }
                        
                        bpvBloodFormInstance.caseRecord = caseRecordInstance
                        return bpvBloodFormInstance
                    } else {
                        specimenRecordInstance.save(failOnError:true)
                        chpBloodRecordInstance.save(failOnError:true)                        
                    }

                        caseRecordInstance.save(failOnError:true)
                        bpvBloodFormInstance.caseRecord = caseRecordInstance
                        bpvBloodFormInstance.save(failOnError:true)
                        return bpvBloodFormInstance

                } else {
                    throw new RuntimeException("Case Record instance missing")
                }
            }
                        
        } catch(Exception e){
               throw new RuntimeException(e.toString())
        }                
}    
    

def savePlasmaCh (params, request) {
        def specimenRecordInstance = new SpecimenRecord()
        specimenRecordInstance.specimenId = (params.specimenId).trim()

        specimenRecordInstance.containerType = ContainerType.get(params.specimenRecordInstance.containerType.id)

        def caseRecordInstance = CaseRecord.findByCaseId(params.caseId)
        def bpvBloodFormInstance = CaseRecord.findByCaseId(params.caseId).bpvBloodForm
        def parentSpecRecInstance = new SpecimenRecord()


        def conTubSpecInstance = new SpecimenRecord()
        if (bpvBloodFormInstance.bloodFormVersion == 2) {
                parentSpecRecInstance = SpecimenRecord.findBySpecimenId(bpvBloodFormInstance.plasmaParBarcode) 
                bpvBloodFormInstance.caseRecord.specimens.each() {
                    if(it.containerType == ContainerType.findByCode("CONICT") && it.fixative == Fixative.findByCode("FRESH") && it.parentSpecimen != null && it.tissueType == AcquisitionType.findByCode("BLOODPLAS")
                        && it.parentSpecimen.specimenId.equals(bpvBloodFormInstance.plasmaParBarcode)) {
                        conTubSpecInstance = it
                    }
                }                
            } else {
                bpvBloodFormInstance.caseRecord.specimens.each() {
                    if(it.containerType == ContainerType.findByCode("CONICT") && it.fixative == Fixative.findByCode("FRESH") && it.parentSpecimen != null && it.tissueType == AcquisitionType.findByCode("BLOODPLAS")) {
                        conTubSpecInstance = it
                    }
                }
            }



        def chpPlBloodRecordInstance = new ChpBloodRecord()
        def plTissueType = new AcquisitionType()
        def plFixative = new Fixative()


        def chpBloodRecordInstance = new ChpBloodRecord()
        def tissueType = new AcquisitionType()
        def fixative = new Fixative()
        tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
        fixative   = Fixative.findByCode("FROZEN")

        if (params.coniTbVol=="") {
            params.coniTbVol="0"
        }                


        specimenRecordInstance.tissueType = tissueType
        specimenRecordInstance.provisionalTissueType = tissueType
        specimenRecordInstance.fixative = fixative
        specimenRecordInstance.parentSpecimen=conTubSpecInstance
        specimenRecordInstance.inQuarantine = true
        specimenRecordInstance.wasConsumed = false  // for Cryovial blood tubes.  Have to see how this one plays out...
        // specimenRecordInstance.containerType = containerType
        chpBloodRecordInstance.volume = params.volume.toFloat()
        if (bpvBloodFormInstance.bloodFormVersion == 2) {
            if (params.bloodFrozen!=null && params.bloodFrozen!="Select Date") {
                chpBloodRecordInstance.bloodFrozen = new Date(params.bloodFrozen)
            }
            if (params.scannedId?.trim()!="") {
                if (params.bloodStorage!=null && params.bloodStorage!="Select Date") {
                    chpBloodRecordInstance.bloodStorage = new Date(params.bloodStorage)
                }
                chpBloodRecordInstance.freezerType = params.freezerType
            }

        }
        specimenRecordInstance.chpBloodRecord = chpBloodRecordInstance
        chpBloodRecordInstance.specimenRecord = specimenRecordInstance
        caseRecordInstance.addToSpecimens(specimenRecordInstance)
        if (!(specimenRecordInstance.validate())) {
                caseRecordInstance.removeFromSpecimens(specimenRecordInstance)
                bpvBloodFormInstance.errors = specimenRecordInstance.errors
                if (specimenRecordInstance.specimenId.indexOf("")) {
                    bpvBloodFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvBloodFormInstance?.formMetadata?.cdrFormName] as Object[], "Invalid specimen ID")
                }

                bpvBloodFormInstance.caseRecord = caseRecordInstance
                //render(view: "plasmaTable", model: [bpvBloodFormInstance: bpvBloodFormInstance])
                return bpvBloodFormInstance
        } else {
            specimenRecordInstance.save(failOnError:true)                
            chpBloodRecordInstance.save(failOnError:true)            
        }

        caseRecordInstance.save(failOnError:true)    
        bpvBloodFormInstance.save(failOnError:true)        
        return bpvBloodFormInstance
}

def savePlasmaConTb (params, request) {
                def specimenRecordInstance = new SpecimenRecord()
                specimenRecordInstance.specimenId = (params.specimenId).trim()
                specimenRecordInstance.containerType = ContainerType.findByCode("CONICT")
                def caseRecordInstance = CaseRecord.findByCaseId(params.caseId)
                def bpvBloodFormInstance = CaseRecord.findByCaseId(params.caseId).bpvBloodForm
                def parentSpecRecInstance = new SpecimenRecord()
                if (bpvBloodFormInstance.bloodFormVersion == 2) {
                    parentSpecRecInstance = SpecimenRecord.findBySpecimenId(bpvBloodFormInstance.plasmaParBarcode)
                    //bpvBloodFormInstance.plasmaCTBarcode = specimenRecordInstance.specimenId
                } else {
                    bpvBloodFormInstance.caseRecord.specimens.each() {
                        if(it.containerType == ContainerType.findByCode("LAVEDTA") && it.fixative == Fixative.findByCode("EDTA") && it.parentSpecimen == null && it.tissueType == AcquisitionType.findByCode("BLOODPLAS")) {
                            parentSpecRecInstance = it
                        }
                    }
                }

                specimenRecordInstance.tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
                specimenRecordInstance.provisionalTissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
                specimenRecordInstance.fixative = Fixative.findByCode("FRESH")
                specimenRecordInstance.parentSpecimen=parentSpecRecInstance
                specimenRecordInstance.inQuarantine = true
                specimenRecordInstance.wasConsumed = true
                
        
                if (params.volume=="") {
                    params.volume=0
                }
                def chpPlBloodRecordInstance = new ChpBloodRecord()
                chpPlBloodRecordInstance.volume = params.volume.toFloat()
                specimenRecordInstance.chpBloodRecord = chpPlBloodRecordInstance
                chpPlBloodRecordInstance.specimenRecord = specimenRecordInstance
                caseRecordInstance.addToSpecimens(specimenRecordInstance)

                if (specimenRecordInstance.validate()) {
                
                        specimenRecordInstance.save(failOnError:true)
                        chpPlBloodRecordInstance.save(failOnError:true)
                } else {
                        caseRecordInstance.removeFromSpecimens(specimenRecordInstance)
                }
                caseRecordInstance.save(failOnError:true)    
                bpvBloodFormInstance.caseRecord = caseRecordInstance
                bpvBloodFormInstance.save(failOnError:true)                
                return bpvBloodFormInstance
    }    
    
    
def saveSerumCh (params, request) {
                def specimenRecordInstance = new SpecimenRecord()
                specimenRecordInstance.specimenId = (params.specimenId).trim()
                
                specimenRecordInstance.containerType = ContainerType.get(params.specimenRecordInstance.containerType.id)
                
                def caseRecordInstance = CaseRecord.findByCaseId(params.caseId)
                def bpvBloodFormInstance = CaseRecord.findByCaseId(params.caseId).bpvBloodForm
                def parentSpecRecInstance = new SpecimenRecord()
                
                if (params.parSrmBarCd.equals(null) || params.parSrmBarCd.equals("")) {
                    bpvBloodFormInstance.errors.reject('', 'Bar code for the Parent serum specimen is missing')
                    render(view: "serumTable", model: [bpvBloodFormInstance: bpvBloodFormInstance])
                    return
                } else {
                    parentSpecRecInstance = SpecimenRecord.findBySpecimenId(params.parSrmBarCd)
                }

                def chpBloodRecordInstance = new ChpBloodRecord()
                def tissueType = new AcquisitionType()
                def fixative = new Fixative()
                tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
                fixative   = Fixative.findByCode("FROZEN")
                
                specimenRecordInstance.tissueType = tissueType
                specimenRecordInstance.provisionalTissueType = tissueType
                specimenRecordInstance.fixative = fixative
                specimenRecordInstance.parentSpecimen=parentSpecRecInstance
                
                specimenRecordInstance.inQuarantine = true
                specimenRecordInstance.wasConsumed = false  // for Cryovial blood tubes.  Have to see how this one plays out...
                // specimenRecordInstance.containerType = containerType
                chpBloodRecordInstance.volume = params.volume.toFloat()
                specimenRecordInstance.chpBloodRecord = chpBloodRecordInstance
                chpBloodRecordInstance.specimenRecord = specimenRecordInstance
                caseRecordInstance.addToSpecimens(specimenRecordInstance)
                if (!(specimenRecordInstance.validate())) {
                        caseRecordInstance.removeFromSpecimens(specimenRecordInstance)
                        bpvBloodFormInstance.errors = specimenRecordInstance.errors
                        if (specimenRecordInstance.specimenId.indexOf("")) {
                            bpvBloodFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvBloodFormInstance?.formMetadata?.cdrFormName] as Object[], "Invalid specimen ID")
                        }
                        
                        bpvBloodFormInstance.caseRecord = caseRecordInstance
                        //render(view: "serumTable", model: [bpvBloodFormInstance: bpvBloodFormInstance])
                        return bpvBloodFormInstance
                } else {
                    specimenRecordInstance.save(failOnError:true)
                    chpBloodRecordInstance.save(failOnError:true)                    
                }
                caseRecordInstance.save(failOnError:true)
                bpvBloodFormInstance.save(failOnError:true)                
                return bpvBloodFormInstance    
    }    
    
def saveWcpCh (params, request) {
                def specimenRecordInstance = new SpecimenRecord()
                specimenRecordInstance.specimenId = (params.specimenId).trim()
                
                specimenRecordInstance.containerType = ContainerType.get(params.specimenRecordInstance.containerType.id)
                
                def caseRecordInstance = CaseRecord.findByCaseId(params.caseId)
                def bpvBloodFormInstance = CaseRecord.findByCaseId(params.caseId).bpvBloodForm
                def parentSpecRecInstance = new SpecimenRecord()
/*                bpvBloodFormInstance.caseRecord.specimens.each() {
                    if(it.containerType == ContainerType.findByCode("LAVEDTA") && it.fixative == Fixative.findByCode("EDTA") && it.parentSpecimen == null && it.tissueType == AcquisitionType.findByCode("BLOODPLAS")) {
                        parentSpecRecInstance = it
                    }
                }*/
                if (params.parPlBarCd.equals(null) || params.parPlBarCd.equals("")) {
                    bpvBloodFormInstance.errors.reject('', 'Bar code for the Parent EDTA (Plasma) tube is missing')
                    render(view: "wcpTable", model: [bpvBloodFormInstance: bpvBloodFormInstance])
                    return
                } else {
                    parentSpecRecInstance = SpecimenRecord.findBySpecimenId(params.parPlBarCd)
                }
        
                def chpBloodRecordInstance = new ChpBloodRecord()
                def tissueType = new AcquisitionType()
                def fixative = new Fixative()
                tissueType = AcquisitionType.findByCode(params.specimenRecordInstance.tissueType.code)
                fixative   = Fixative.findByCode("FROZEN")
                
                specimenRecordInstance.tissueType = tissueType
                specimenRecordInstance.provisionalTissueType = tissueType
                specimenRecordInstance.fixative = fixative
                specimenRecordInstance.parentSpecimen=parentSpecRecInstance
                
                specimenRecordInstance.inQuarantine = true
                specimenRecordInstance.wasConsumed = false
                chpBloodRecordInstance.volume = params.volume.toFloat()
                specimenRecordInstance.chpBloodRecord = chpBloodRecordInstance
                chpBloodRecordInstance.specimenRecord = specimenRecordInstance
                caseRecordInstance.addToSpecimens(specimenRecordInstance)
                if (!(specimenRecordInstance.validate())) {
                        caseRecordInstance.removeFromSpecimens(specimenRecordInstance)
                        bpvBloodFormInstance.errors = specimenRecordInstance.errors
                        if (specimenRecordInstance.specimenId.indexOf("")) {
                            bpvBloodFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [bpvBloodFormInstance?.formMetadata?.cdrFormName] as Object[], "Invalid specimen ID")
                        }
                        
                        bpvBloodFormInstance.caseRecord = caseRecordInstance
                        return bpvBloodFormInstance
                } else {
                    specimenRecordInstance.save(failOnError:true)
                    chpBloodRecordInstance.save(failOnError:true)                    
                }
                caseRecordInstance.save(failOnError:true)
                bpvBloodFormInstance.save(failOnError:true)                
                return bpvBloodFormInstance    
    }    
    
    Map getWarningMap(bpvBloodFormInstance) {
        Map warningMap = [:]
        float dnaVol = 0
        float rnaVol = 0
        
        for (specimen in bpvBloodFormInstance.caseRecord?.specimens) {
            if (specimen.tissueType?.code == 'BLOODDNA') {
                dnaVol += specimen.chpBloodRecord?.volume
            }
            if (specimen.tissueType?.code == 'BLOODRNA') {
                rnaVol += specimen.chpBloodRecord?.volume
            }
        }
        
        if (dnaVol > 0 && dnaVol < 7) {
            warningMap.put('volume_BLOODDNA', 'Minimum collection amount 7.0 ml was not met for DNA PAXgene tube(s)')
        }
        
        if (rnaVol > 0 && rnaVol < 2) {
            warningMap.put('volume_BLOODRNA', 'Minimum collection amount 2.0 ml was not met for RNA PAXgene tube(s)')
        }
        
        if (bpvBloodFormInstance.rnaPaxFrozen && bpvBloodFormInstance.rnaPaxStorage) {
            use (TimeCategory) {
                if (bpvBloodFormInstance.rnaPaxStorage < bpvBloodFormInstance.rnaPaxFrozen + 24.hours) {
                    warningMap.put('rnaPaxStorage', 'Time between RNA PAXgene tube was frozen and RNA PAXgene tube was transferred is less than 24 hours')
                } else if(bpvBloodFormInstance.rnaPaxStorage > bpvBloodFormInstance.rnaPaxFrozen + 72.hours) {
                    warningMap.put('rnaPaxStorage', 'Time between RNA PAXgene tube was frozen and RNA PAXgene tube was transferred exceeds 72 hours')
                }
            }
        }
        
        if (bpvBloodFormInstance.dateTimeBloodDraw != null && bpvBloodFormInstance.plasmaProcFrozen) {
            use (TimeCategory) {
                if (bpvBloodFormInstance.plasmaProcFrozen > bpvBloodFormInstance.dateTimeBloodDraw + 90.minutes) {
                    warningMap.put('plasmaProcFrozen', 'Time between blood was drawn and Plasma aliquots were frozen is greater than 90 minutes')
                }
            }
        }
        
        return warningMap
    }    
    
}
