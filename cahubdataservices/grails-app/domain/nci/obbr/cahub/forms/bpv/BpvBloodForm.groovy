package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.datarecords.SOPRecord
// import nci.obbr.cahub.staticmembers.AcquisitionType

class BpvBloodForm extends BpvFormBaseClass {
    CaseRecord caseRecord

    SOPRecord collectProcessSOP
    String  bloodMinimum //YesNo
    String permissionToProceed //YesNo
    String bloodDrawType
    String bloodDrawTypeOs
    Date   dateTimeBloodDraw
    Date   dateTimeBloodDraw2
    BloodDrawTech bloodDrawNurse
    BloodDrawTech bloodDrawNurse2
    String bloodDrawNurseName
    String bloodDrawNurseName2
    String randomizationKey
    String randomizationKey2
    String bloodDrawNurseOs
    String bloodDrawNurseOs2
    String dnaParent
    Float dnaParentVol
    String rnaParent
    Float rnaParentVol
    String plasmaParent
    Float plasmaParentVol
    String serumParent
    Float serumParentVol
    
    String bloodSource
    String bloodSourceOs
    String bloodDrawComments
    Date   dateTimeBloodReceived
    Date   dateTimeBloodReceived2    
    String bloodReceiptTech
    String bloodReceiptTech2    
    Float  bloodReceiptTemp
    Float  bloodReceiptTemp2    
    Float  bloodReceiptHumidity
    Float  bloodReceiptHumidity2    
    SpecimenRecord wholeBloodParent
    Date   wholeBloodProcStart
    Date   wholeBloodProcEnd
    Date   wholeBloodProcFrozen
    Date   wholeBloodProcStorage
    String  wholeBloodProcSopDev
    String wholeBloodProcComments
    String wholeBloodStorageIssues
    String wholeBloodProcTech
    Float plasmaRemainingVol
    
    String dnaParBarCode
    Date   dnaPaxFrozen
    Date   dnaPaxStorage
    String dnaPaxProcSopDev //YesNo  
    String dnaPaxProcComments
    String dnaPaxStorageIssues
    String dnaPaxProcTech

    String rnaParBarCode
    Date   rnaPaxFrozen
    Date   rnaPaxStorage
    String  rnaPaxProcSopDev //YesNo  
    String rnaPaxProcComments
    String rnaPaxStorageIssues
    String rnaPaxProcTech
    
    
    String plasmaParBarcode    
    Date   plasmaProcStart
    String plasmaCTBarcode
    Float plasmaCTVol
    String plAl1Barcode
    String plAl1Vol
    String plAl2Barcode
    String plAl2Vol
    String plAl3Barcode
    String plAl3Vol
    String plAl4Barcode
    String plAl4Vol
    Date   plasmaProcEnd
    Date   plasmaProcFrozen
    Date   plasmaProcStorage
    String plasmaProcSopDev //YesNo  
    String plasmaProcComments
    String plasmaHemolysis //YesNo  
    String plasmaStorageIssues
    String plasmaProcTech
    String plasmaTransferTech
    String serumParBarcode
    Date   serumProcStart
    String seAl1Vol
    String seAl2Barcode
    String seAl2Vol
    String seAl3Barcode
    String seAl3Vol
    String seAl4Barcode
    String seAl4Vol
    Date   serumProcEnd
    Date   serumProcFrozen
    Date   serumProcStorage
    String serumProcSopDev //YesNo  
    String serumProcComments
    String serumStorageIssues
    String serumProcTech
    Date dateSubmitted
    Float bloodFormVersion 
    
    static belongsTo = CaseRecord
    
    String toString(){"$caseRecord.caseId"}    
    
    static constraints = {


        collectProcessSOP(blank:true,nullable:true)
        bloodMinimum(blank:true,nullable:true)
        permissionToProceed(blank:true,nullable:true)
        bloodDrawType(blank:true,nullable:true)
        bloodDrawTypeOs(blank:true,nullable:true)
        dateTimeBloodDraw(blank:true,nullable:true)
        dateTimeBloodDraw2(blank:true,nullable:true)
        bloodDrawNurse(blank:true,nullable:true)
        bloodDrawNurse2(blank:true,nullable:true)
        bloodDrawNurseName(blank:true,nullable:true)
        bloodDrawNurseName2(blank:true,nullable:true)
        bloodDrawNurseOs(blank:true,nullable:true)
        bloodDrawNurseOs2(blank:true,nullable:true)

        dnaParent(blank:true,nullable:true)
        dnaParentVol(blank:true,nullable:true)
        rnaParent(blank:true,nullable:true)
        rnaParentVol(blank:true,nullable:true)
        plasmaParent(blank:true,nullable:true)
        plasmaParentVol(blank:true,nullable:true)
        serumParent(blank:true,nullable:true)
        serumParentVol(blank:true,nullable:true)
        bloodSource(blank:true,nullable:true)
        bloodSourceOs(blank:true,nullable:true)
        bloodDrawComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        dateTimeBloodReceived(blank:true,nullable:true)
        dateTimeBloodReceived2(blank:true,nullable:true)        
        bloodReceiptTech(blank:true,nullable:true)
        bloodReceiptTech2(blank:true,nullable:true)        
        bloodReceiptTemp(blank:true,nullable:true)
        bloodReceiptTemp2(blank:true,nullable:true)        
        bloodReceiptHumidity(blank:true,nullable:true)
        bloodReceiptHumidity2(blank:true,nullable:true)        
        wholeBloodParent(blank:true,nullable:true)
        wholeBloodProcStart(blank:true,nullable:true)
        wholeBloodProcEnd(blank:true,nullable:true)
        wholeBloodProcFrozen(blank:true,nullable:true)
        wholeBloodProcStorage(blank:true,nullable:true)
        wholeBloodProcSopDev(blank:true,nullable:true)
        wholeBloodProcComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        wholeBloodStorageIssues(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        wholeBloodProcTech(blank:true,nullable:true)
        serumProcEnd(blank:true,nullable:true)

        dnaParBarCode(blank:true,nullable:true)
        dnaPaxFrozen(blank:true,nullable:true)
        dnaPaxStorage(blank:true,nullable:true)
        dnaPaxProcSopDev(blank:true,nullable:true)
        dnaPaxProcComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        dnaPaxStorageIssues(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        dnaPaxProcTech(blank:true,nullable:true)
        rnaParBarCode(blank:true,nullable:true)
        rnaPaxFrozen(blank:true,nullable:true)
        rnaPaxStorage(blank:true,nullable:true)
        rnaPaxProcSopDev(blank:true,nullable:true)
        rnaPaxProcComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        rnaPaxStorageIssues(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        rnaPaxProcTech(blank:true,nullable:true)
        plasmaParBarcode(blank:true,nullable:true)
        plasmaProcStart(blank:true,nullable:true)
        plasmaCTBarcode(blank:true,nullable:true)
        plasmaCTVol(blank:true,nullable:true)
        plAl1Barcode(blank:true,nullable:true)
        plAl1Vol(blank:true,nullable:true)
        plAl2Barcode(blank:true,nullable:true)
        plAl2Vol(blank:true,nullable:true)
        plAl3Barcode(blank:true,nullable:true)
        plAl3Vol(blank:true,nullable:true)
        plAl4Barcode(blank:true,nullable:true)
        plAl4Vol(blank:true,nullable:true)
        plasmaProcEnd(blank:true,nullable:true)
        plasmaProcFrozen(blank:true,nullable:true)
        plasmaProcStorage(blank:true,nullable:true)
        plasmaProcSopDev(blank:true,nullable:true)
        plasmaProcComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        plasmaHemolysis(blank:true,nullable:true)
        plasmaStorageIssues(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        plasmaProcTech(blank:true,nullable:true)
        serumParBarcode(blank:true,nullable:true)
        serumProcStart(blank:true,nullable:true)
        seAl1Vol(blank:true,nullable:true)
        seAl2Barcode(blank:true,nullable:true)
        seAl2Vol(blank:true,nullable:true)
        seAl3Barcode(blank:true,nullable:true)
        seAl3Vol(blank:true,nullable:true)
        seAl4Barcode(blank:true,nullable:true)
        seAl4Vol(blank:true,nullable:true)
        serumProcEnd(blank:true,nullable:true)
        serumProcFrozen(blank:true,nullable:true)
        serumProcStorage(blank:true,nullable:true)
        serumProcSopDev(blank:true,nullable:true)
        serumProcComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        serumStorageIssues(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        serumProcTech(blank:true,nullable:true)
        dateSubmitted(blank:true,nullable:true)
        plasmaRemainingVol(blank:true,nullable:true)
        bloodFormVersion(blank:true,nullable:true)
        plasmaTransferTech(blank:true,nullable:true)
        randomizationKey(blank:true,nullable:true)
        randomizationKey2(blank:true,nullable:true)
    }
	
    enum YesNo {
        No("No"),
        Yes("Yes")

        final String value;

        YesNo(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Yes, No]
        }
    }

    enum BloodDrawTech {
        Anesthesiologist("Anesthesiologist"),
        ConsentResearchAnalystCoordinator("Consent or Research Analyst/Coordinator"),
        Nurse("Nurse"),
        NurseAnesthetist("Nurse Anesthetist"),
        ORTechnician("OR Technician"),
        Phlebotomist("Phlebotomist"),
        Unknown("Unknown"),
        OtherSpecify("Other Specify")

        final String value;

        BloodDrawTech(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Anesthesiologist, ConsentResearchAnalystCoordinator, Nurse, NurseAnesthetist, ORTechnician, Phlebotomist, Unknown, OtherSpecify]
        }
    }
        
    static mapping = {
         table 'bpv_blood_form'
         id generator:'sequence', params:[sequence:'bpv_blood_form_pk']
    }
}
