package nci.obbr.cahub

import nci.obbr.cahub.forms.gtex.crf.*
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*

import nci.obbr.cahub.util.ComputeMethods
import nci.obbr.cahub.datarecords.vocab.CVocabRecord

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import grails.converters.*
import static groovyx.net.http.ContentType.XML
import static groovyx.net.http.ContentType.TEXT
import org.codehaus.groovy.grails.web.json.*


class CaseReportFormService {

    static transactional = true
    def ldaccService
    //def utilService

    def saveForm(caseReportFormInstance) {
        
        try{
            caseReportFormInstance.status= CaseReportForm.Status.Editing
            Demographics demographics = new Demographics()
            demographics.save(failOnError:true)
            caseReportFormInstance.demographics = demographics



            MedicalHistory medicalHistory = new MedicalHistory()


            medicalHistory.save(failOnError:true)


            /*  // GeneralMedicalHistory generalMedicalHistory1 = new GeneralMedicalHistory(medicalCondition: 'Alzheimer’s/Dementia', medicalHistory:medicalHistory, displayOrder:'A' )
            GeneralMedicalHistory generalMedicalHistory1 = new GeneralMedicalHistory(medicalCondition: 'Alzheimer’s OR Dementia', medicalHistory:medicalHistory, displayOrder:'A', rown:1 )
            generalMedicalHistory1.save(failOnError:true)

            //GeneralMedicalHistory generalMedicalHistory2 = new GeneralMedicalHistory(medicalCondition: 'Ischemic Heart Disease', medicalHistory:medicalHistory, displayOrder:'B' )
            GeneralMedicalHistory generalMedicalHistory2 = new GeneralMedicalHistory(medicalCondition: 'Ischemic Heart Disease (coronary artery disease (CAD), coronary heart disease, ischemic cardiomyopathy)', medicalHistory:medicalHistory, displayOrder:'B', rown:2 )
            generalMedicalHistory2.save(failOnError:true)

            // GeneralMedicalHistory generalMedicalHistory3 = new GeneralMedicalHistory(medicalCondition: 'Cerebrovascular Disease', medicalHistory:medicalHistory, displayOrder:'C' )
            GeneralMedicalHistory generalMedicalHistory3 = new GeneralMedicalHistory(medicalCondition: 'Cerebrovascular Disease (stroke, TIA, embolism, aneurysm, other circulatory disorder affecting the brain)', medicalHistory:medicalHistory, displayOrder:'C', rown:3 )
            generalMedicalHistory3.save(failOnError:true)

            // GeneralMedicalHistory generalMedicalHistory4 = new GeneralMedicalHistory(medicalCondition: 'Heart Attack', medicalHistory:medicalHistory, displayOrder:'D' )
            GeneralMedicalHistory generalMedicalHistory4 = new GeneralMedicalHistory(medicalCondition: 'Heart attack, acute myocardial infarction, acute coronary syndrome', medicalHistory:medicalHistory, displayOrder:'D', rown:4 )
            generalMedicalHistory4.save(failOnError:true)

            GeneralMedicalHistory generalMedicalHistory5 = new GeneralMedicalHistory(medicalCondition: 'Renal Failure', medicalHistory:medicalHistory, displayOrder:'E', rown:5 )
            generalMedicalHistory5.save(failOnError:true)

            GeneralMedicalHistory generalMedicalHistory6 = new GeneralMedicalHistory(medicalCondition: 'Nephritis, Nephrotic Syndrome and/or Nephrosis', medicalHistory:medicalHistory, displayOrder:'F', rown:6 )
            generalMedicalHistory6.save(failOnError:true)

            // GeneralMedicalHistory generalMedicalHistory7 = new GeneralMedicalHistory(medicalCondition: 'Chronic Obstructive Pulmonary Disease (COPD)', medicalHistory:medicalHistory, displayOrder:'G' )
            GeneralMedicalHistory generalMedicalHistory7 = new GeneralMedicalHistory(medicalCondition: 'Chronic Respiratory Disease (Chronic Obstructive Pulmonary Syndrome (COPD) OR Chronic Lower Respiratory Disease (CLRD) (chronic bronchitis, emphysema, asthma))', medicalHistory:medicalHistory, displayOrder:'G', rown:7 )
            generalMedicalHistory7.save(failOnError:true)

            GeneralMedicalHistory generalMedicalHistory8 = new GeneralMedicalHistory(medicalCondition: 'Chronic Lower Respiratory Disease', medicalHistory:medicalHistory, displayOrder:'H', rown:8 )
            generalMedicalHistory8.save(failOnError:true)

            // GeneralMedicalHistory generalMedicalHistory9 = new GeneralMedicalHistory(medicalCondition: 'Influenza', medicalHistory:medicalHistory, displayOrder:'I' )
            GeneralMedicalHistory generalMedicalHistory9 = new GeneralMedicalHistory(medicalCondition: 'Influenza (acute viral infection including avian influenza)', medicalHistory:medicalHistory, displayOrder:'I', rown:9 )
            generalMedicalHistory9.save(failOnError:true)

            //GeneralMedicalHistory generalMedicalHistory10 = new GeneralMedicalHistory(medicalCondition: 'Pneumonia', medicalHistory:medicalHistory, displayOrder:'J' )
            GeneralMedicalHistory generalMedicalHistory10 = new GeneralMedicalHistory(medicalCondition: 'Pneumonia (acute respiratory infection affecting the lungs)', medicalHistory:medicalHistory, displayOrder:'J', rown:10 )
            generalMedicalHistory10.save(failOnError:true)

            //GeneralMedicalHistory generalMedicalHistory11 = new GeneralMedicalHistory(medicalCondition: 'Diabetes Mellitus Type I', medicalHistory:medicalHistory, displayOrder:'K' )
            GeneralMedicalHistory generalMedicalHistory11 = new GeneralMedicalHistory(medicalCondition: 'Diabetes mellitus type 1 (IDDM, formerly juvenile diabetes)', medicalHistory:medicalHistory, displayOrder:'K', rown:11 )
            generalMedicalHistory11.save(failOnError:true)

            // GeneralMedicalHistory generalMedicalHistory12 = new GeneralMedicalHistory(medicalCondition: 'Diabetes Mellitus Type II', medicalHistory:medicalHistory, displayOrder:'L' )
            GeneralMedicalHistory generalMedicalHistory12 = new GeneralMedicalHistory(medicalCondition: 'Diabetes mellitus type II (NIDDM, adult onset diabetes)', medicalHistory:medicalHistory, displayOrder:'L', rown:12 )
            generalMedicalHistory12.save(failOnError:true)

            // GeneralMedicalHistory generalMedicalHistory13 = new GeneralMedicalHistory(medicalCondition: 'Uremia', medicalHistory:medicalHistory, displayOrder:'M' )
            GeneralMedicalHistory generalMedicalHistory13 = new GeneralMedicalHistory(medicalCondition: 'Uremia (Kidney Disorder)', medicalHistory:medicalHistory, displayOrder:'M', rown:13 )
            generalMedicalHistory13.save(failOnError:true)

            // GeneralMedicalHistory generalMedicalHistory14 = new GeneralMedicalHistory(medicalCondition: 'Septicemia', medicalHistory:medicalHistory, displayOrder:'N' )
            GeneralMedicalHistory generalMedicalHistory14 = new GeneralMedicalHistory(medicalCondition: 'Bacterial Infections (including septicemia (bacteria in the blood), meningococcal disease, staphylococcal infection, streptococcus, sepsis)', medicalHistory:medicalHistory, displayOrder:'N', rown:14 )
            generalMedicalHistory14.save(failOnError:true)

            //GeneralMedicalHistory generalMedicalHistory15 = new GeneralMedicalHistory(medicalCondition: 'Hepatic Failure', medicalHistory:medicalHistory, displayOrder:'O' )
            GeneralMedicalHistory generalMedicalHistory15 = new GeneralMedicalHistory(medicalCondition: 'Liver Disease (liver abscess, failure, fatty liver syndrome, inherited liver insufficiency, acute/chronic hepatic insufficiency, necrobacillosis, rupture,', medicalHistory:medicalHistory, displayOrder:'O', rown:15 )
            generalMedicalHistory15.save(failOnError:true)

            GeneralMedicalHistory generalMedicalHistory16 = new GeneralMedicalHistory(medicalCondition: 'Arthritis', medicalHistory:medicalHistory, displayOrder:'P', rown:16 )
            generalMedicalHistory16.save(failOnError:true)

            // GeneralMedicalHistory generalMedicalHistory17 = new GeneralMedicalHistory(medicalCondition: 'Depression', medicalHistory:medicalHistory, displayOrder:'Q' )
            GeneralMedicalHistory generalMedicalHistory17 = new GeneralMedicalHistory(medicalCondition: 'Major depression (unipolar depression, major depressive disorder)', medicalHistory:medicalHistory, displayOrder:'Q', rown:17 )
            generalMedicalHistory17.save(failOnError:true)

            GeneralMedicalHistory generalMedicalHistory18 = new GeneralMedicalHistory(medicalCondition: 'Asthma', medicalHistory:medicalHistory, displayOrder:'R', rown:18 )
            generalMedicalHistory18.save(failOnError:true)

            GeneralMedicalHistory generalMedicalHistory19 = new GeneralMedicalHistory(medicalCondition: 'Hypertension', medicalHistory:medicalHistory, displayOrder:'S', rown:19 )
            generalMedicalHistory19.save(failOnError:true)


            GeneralMedicalHistory generalMedicalHistory20 = new GeneralMedicalHistory(medicalCondition: "Parkinson’s", medicalHistory:medicalHistory, displayOrder:'T', rown:20 )
            generalMedicalHistory20.save(failOnError:true)

            GeneralMedicalHistory generalMedicalHistory21 = new GeneralMedicalHistory(medicalCondition: "Schizophrenia", medicalHistory:medicalHistory, displayOrder:'U', rown:21 )
            generalMedicalHistory21.save(failOnError:true)*/
            
            //change the code to:
            
            def medicalConditions = MedicalCondition.findAll("from MedicalCondition order by rown");
            
            medicalConditions.each(){
                GeneralMedicalHistory generalMedicalHistory = new GeneralMedicalHistory()
                
                //PMH 03/19/13: from v 4.5 onwards remove 'Asthma' from the given medical condition in display page hub-cr-31
               // if(ComputeMethods.compareCDRVersion(caseReportFormInstance.caseRecord.cdrVer, '4.5') >=0 && it.name.equals('Chronic Respiratory Disease (Chronic Obstructive Pulmonary Syndrome (COPD) OR Chronic Lower Respiratory Disease (CLRD) (chronic bronchitis, emphysema, asthma))')){
               //     generalMedicalHistory.medicalCondition='Chronic Respiratory Disease (Chronic Obstructive Pulmonary Syndrome (COPD) OR Chronic Lower Respiratory Disease (CLRD) (chronic bronchitis, emphysema))'
               // }
               // else{
                    generalMedicalHistory.medicalCondition=it.name
               // }
                
                generalMedicalHistory.rown=it.rown
                generalMedicalHistory.displayOrder = 'A'
                generalMedicalHistory. medicalHistory=medicalHistory
                generalMedicalHistory.save(failOnError:true)
                
            }

            caseReportFormInstance.medicalHistory = medicalHistory
            
            
            
            SurgicalProcedures surgicalProcedures = new SurgicalProcedures()
            surgicalProcedures.save(failOnError:true)
                
            SurgicalMedication surgicalMedication1 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, medicationName: 'Midazolam', catgory:'Pre-Operative Medications', subCatgory:'intravenous (IV) sedation administered' )
            surgicalMedication1.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication2 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, medicationName: 'Lorazepam', catgory:'Pre-Operative Medications', subCatgory:'intravenous (IV) sedation administered')
            surgicalMedication2.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication3 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, medicationName: 'Diazepam', catgory:'Pre-Operative Medications', subCatgory:'intravenous (IV) sedation administered')
            surgicalMedication3.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication4 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, isOther1:true, catgory:'Pre-Operative Medications', subCatgory:'intravenous (IV) sedation administered')
            surgicalMedication4.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication5 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, isOther2:true, catgory:'Pre-Operative Medications', subCatgory:'intravenous (IV) sedation administered')
            surgicalMedication5.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication6 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, medicationName: 'Fentanyl', catgory:'Pre-Operative Medications', subCatgory:'IV Opiate administered')
            surgicalMedication6.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication7 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, medicationName: 'Hydromorphone', catgory:'Pre-Operative Medications', subCatgory:'IV Opiate administered')
            surgicalMedication7.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication8 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, medicationName: 'Meperidine', catgory:'Pre-Operative Medications', subCatgory:'IV Opiate administered' )
            surgicalMedication8.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication9 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, medicationName: 'Morphine', catgory:'Pre-Operative Medications', subCatgory:'IV Opiate administered')
            surgicalMedication9.save(failOnError:true)
                
            SurgicalMedication surgicalMedication10 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, isOther1:true, catgory:'Pre-Operative Medications', subCatgory:'IV Opiate administered')
            surgicalMedication10.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication11 = new SurgicalMedication(surgicalProcedures:surgicalProcedures, isOther2:true, catgory:'Pre-Operative Medications', subCatgory:'IV Opiate administered')
            surgicalMedication11.save(failOnError:true)
                
            SurgicalMedication surgicalMedication12 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,  medicationName: 'Droperidol', catgory:'Pre-Operative Medications', subCatgory:'IV Antiemetic administered' )
            surgicalMedication12.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication13 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,  medicationName: 'Ondansetron', catgory:'Pre-Operative Medications', subCatgory:'IV Antiemetic administered')
            surgicalMedication13.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication14 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,  isOther1:true, catgory:'Pre-Operative Medications', subCatgory:'IV Antiemetic administered')
            surgicalMedication14.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication15 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,  medicationName: 'Ranitidine', catgory:'Pre-Operative Medications', subCatgory:'IV Antacid administered' )
            surgicalMedication15.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication16 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,   isOther1:true, catgory:'Pre-Operative Medications', subCatgory:'IV Antacid administered')
            surgicalMedication16.save(failOnError:true)
                 
                
            SurgicalMedication surgicalMedication17 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,   medicationName: 'Lidocaine', catgory:'Pre-Operative Anesthesia', subCatgory:'local anesthesia agents administered' )
            surgicalMedication17.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication18 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,   medicationName: 'Procaine', catgory:'Pre-Operative Anesthesia', subCatgory:'local anesthesia agents administered')
            surgicalMedication18.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication19 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,   isOther1:true, catgory:'Pre-Operative Anesthesia', subCatgory:'local anesthesia agents administered')
            surgicalMedication19.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication20 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,   medicationName:'Bupivacaine', catgory:'Pre-Operative Anesthesia', subCatgory:'regional (spinal/epidural) anesthesia agents  administered')
            surgicalMedication20.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication21 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,   medicationName:'Lidocaine', catgory:'Pre-Operative Anesthesia', subCatgory:'regional (spinal/epidural) anesthesia agents  administered')
            surgicalMedication21.save(failOnError:true)
                 
                
            SurgicalMedication surgicalMedication22 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,   isOther1:true, catgory:'Pre-Operative Anesthesia', subCatgory:'regional (spinal/epidural) anesthesia agents  administered')
            surgicalMedication22.save(failOnError:true)
                
            SurgicalMedication surgicalMedication23 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Brevital', catgory:'Pre-Operative Anesthesia', subCatgory:'IV anesthetic administered')
            surgicalMedication23.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication24 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Etomidate', catgory:'Pre-Operative Anesthesia', subCatgory:'IV anesthetic administered')
            surgicalMedication24.save(failOnError:true)
                
            SurgicalMedication surgicalMedication25 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Ketamine', catgory:'Pre-Operative Anesthesia', subCatgory:'IV anesthetic administered')
            surgicalMedication25.save(failOnError:true)
                
            SurgicalMedication surgicalMedication26 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Propofol', catgory:'Pre-Operative Anesthesia', subCatgory:'IV anesthetic administered')
            surgicalMedication26.save(failOnError:true)
                
            SurgicalMedication surgicalMedication27 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Sodium thiopental', catgory:'Pre-Operative Anesthesia', subCatgory:'IV anesthetic administered' )
            surgicalMedication27.save(failOnError:true)
                
                
            SurgicalMedication surgicalMedication28 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    isOther1:true, catgory:'Pre-Operative Anesthesia', subCatgory:'IV anesthetic administered' )
            surgicalMedication28.save(failOnError:true)
                
            SurgicalMedication surgicalMedication29 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Fentanyl', catgory:'Pre-Operative Anesthesia', subCatgory:'IV narcotic / opiate anesthetic administered')
            surgicalMedication29.save(failOnError:true)
                
            SurgicalMedication surgicalMedication30 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Hydromorphone', catgory:'Pre-Operative Anesthesia', subCatgory:'IV narcotic / opiate anesthetic administered')
            surgicalMedication30.save(failOnError:true)
                
            SurgicalMedication surgicalMedication31 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Morphine', catgory:'Pre-Operative Anesthesia', subCatgory:'IV narcotic / opiate anesthetic administered' )
            surgicalMedication31.save(failOnError:true)
                
            SurgicalMedication surgicalMedication32 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Meperidine', catgory:'Pre-Operative Anesthesia', subCatgory:'IV narcotic / opiate anesthetic administered' )
            surgicalMedication32.save(failOnError:true)
                
            SurgicalMedication surgicalMedication33 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    isOther1:true, catgory:'Pre-Operative Anesthesia', subCatgory:'IV narcotic / opiate anesthetic administered' )
            surgicalMedication33.save(failOnError:true)
                
            SurgicalMedication surgicalMedication34 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Suxamethonium chloride', catgory:'Pre-Operative Anesthesia', subCatgory:'IV muscle relaxant administered')
            surgicalMedication34.save(failOnError:true)
                
            SurgicalMedication surgicalMedication35 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Pancuronium', catgory:'Pre-Operative Anesthesia', subCatgory:'IV muscle relaxant administered')
            surgicalMedication35.save(failOnError:true)
                
            SurgicalMedication surgicalMedication36 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Vercuronium', catgory:'Pre-Operative Anesthesia', subCatgory:'IV muscle relaxant administered')
            surgicalMedication36.save(failOnError:true)
                
            SurgicalMedication surgicalMedication37 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    isOther1:true, catgory:'Pre-Operative Anesthesia', subCatgory:'IV muscle relaxant administered' )
            surgicalMedication37.save(failOnError:true)
                
            SurgicalMedication surgicalMedication38 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Isoflurane', catgory:'Pre-Operative Anesthesia', subCatgory:'Inhalation anesthetic administered')
            surgicalMedication38.save(failOnError:true)
                
            SurgicalMedication surgicalMedication39 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    medicationName:'Nitrous Oxide', catgory:'Pre-Operative Anesthesia', subCatgory:'Inhalation anesthetic administered')
            surgicalMedication39.save(failOnError:true)
                
            SurgicalMedication surgicalMedication40 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,    isOther1:true, catgory:'Pre-Operative Anesthesia', subCatgory:'Inhalation anesthetic administered')
            surgicalMedication40.save(failOnError:true)
                
                
            SurgicalMedication surgicalMedication41 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,     catgory:'during surgery', subCatgory:'Insulin')
            surgicalMedication41.save(failOnError:true)
                
            SurgicalMedication surgicalMedication42 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,     catgory:'during surgery', subCatgory:'Steroids')
            surgicalMedication42.save(failOnError:true)
                 
            SurgicalMedication surgicalMedication43 = new SurgicalMedication(surgicalProcedures:surgicalProcedures,     catgory:'during surgery', subCatgory:'Antibiotics ' )
            surgicalMedication43.save(failOnError:true)
                 
                
            caseReportFormInstance.surgicalProcedures = surgicalProcedures
            
                
           
            DeathCircumstances deathCircumstances = new DeathCircumstances()
            deathCircumstances.save(failOnError:true)
            

            caseReportFormInstance.deathCircumstances = deathCircumstances
           


            // DeathCircumstances deathCircumstances = new DeathCircumstances()
            // deathCircumstances.save(failOnError:true)

            //  caseReportFormInstance.deathCircumstances = deathCircumstances

            SerologyResult serologyResult = new SerologyResult()
            serologyResult.save(failOnError:true)

            caseReportFormInstance.serologyResult = serologyResult
            caseReportFormInstance.save(failOnError:true)
        }catch(Exception e){
            throw new RuntimeException(e.toString())
        }

    }
    
    def saveGeneralMedicalRecord(params) {
        try{
             
            params.each(){key,value->
                def order
                if(key.startsWith("id")){
                    order=key.substring(2)
                    
                    def id = value
                    def chooseOption=params.get("chooseOption" + order)
                    def yearOfOnset = params.get("yearOfOnset" + order)
                    def treatment = params.get("treatment" + order)
                    def medicalRecord = params.get("medicalRecord" + order)
                    def source = params.get("source" + order)
                                         
                    def generalMedicalHistoryInstance = GeneralMedicalHistory.get(id)
                    if(chooseOption)
                    generalMedicalHistoryInstance.chooseOption = chooseOption
                    else
                    generalMedicalHistoryInstance.chooseOption =null

                    generalMedicalHistoryInstance.yearOfOnset = yearOfOnset
                    if(treatment)
                    generalMedicalHistoryInstance.treatment = treatment
                    else
                    generalMedicalHistoryInstance.treatment = null 

                    if(medicalRecord)
                    generalMedicalHistoryInstance.medicalRecord = medicalRecord
                    else
                    generalMedicalHistoryInstance.medicalRecord = null
                
                    if(source)
                    generalMedicalHistoryInstance.source = source
                    else
                    generalMedicalHistoryInstance.source = null
                    //pmh 04/03/13 vocab
                    def medicalConditionInstance
                    if(generalMedicalHistoryInstance.medicalCondition){
                        
                        if(!generalMedicalHistoryInstance.code){
                            medicalConditionInstance=MedicalCondition.findByName(generalMedicalHistoryInstance.medicalCondition)
                            generalMedicalHistoryInstance.code=medicalConditionInstance.code
                        }
                       
                    }

                    generalMedicalHistoryInstance.save(failOnError:true)

                }
                 
                
            }
            /*def generalMedicalHistoryInstance0 = GeneralMedicalHistory.get(params.id0)
            if(params.chooseOption0)
            generalMedicalHistoryInstance0.chooseOption = params.chooseOption0
            else
            generalMedicalHistoryInstance0.chooseOption =null

            generalMedicalHistoryInstance0.yearOfOnset = params.yearOfOnset0
            if(params.treatment0)
            generalMedicalHistoryInstance0.treatment = params.treatment0
            else
            generalMedicalHistoryInstance0.treatment = null 

            if(params.medicalRecord0)
            generalMedicalHistoryInstance0.medicalRecord = params.medicalRecord0
            else
            generalMedicalHistoryInstance0.medicalRecord = null

            generalMedicalHistoryInstance0.save(failOnError:true)


            // save object 1
            def generalMedicalHistoryInstance1 = GeneralMedicalHistory.get(params.id1)
            if(params.chooseOption1)
            generalMedicalHistoryInstance1.chooseOption = params.chooseOption1
            else
            generalMedicalHistoryInstance1.chooseOption =null

            generalMedicalHistoryInstance1.yearOfOnset = params.yearOfOnset1
            if(params.treatment1)
            generalMedicalHistoryInstance1.treatment = params.treatment1
            else
            generalMedicalHistoryInstance1.treatment = null 

            if(params.medicalRecord1)
            generalMedicalHistoryInstance1.medicalRecord = params.medicalRecord1
            else
            generalMedicalHistoryInstance1.medicalRecord = null

            generalMedicalHistoryInstance1.save(failOnError:true)


            //save object 2
            def generalMedicalHistoryInstance2 = GeneralMedicalHistory.get(params.id2)
            if(params.chooseOption2)
            generalMedicalHistoryInstance2.chooseOption = params.chooseOption2
            else
            generalMedicalHistoryInstance2.chooseOption =null

            generalMedicalHistoryInstance2.yearOfOnset = params.yearOfOnset2
            if(params.treatment2)
            generalMedicalHistoryInstance2.treatment = params.treatment2
            else
            generalMedicalHistoryInstance2.treatment = null 

            if(params.medicalRecord2)
            generalMedicalHistoryInstance2.medicalRecord = params.medicalRecord2
            else
            generalMedicalHistoryInstance2.medicalRecord = null

            generalMedicalHistoryInstance2.save(failOnError:true)


            //save object 3
            def generalMedicalHistoryInstance3 = GeneralMedicalHistory.get(params.id3)
            if(params.chooseOption3)
            generalMedicalHistoryInstance3.chooseOption = params.chooseOption3
            else
            generalMedicalHistoryInstance3.chooseOption =null

            generalMedicalHistoryInstance3.yearOfOnset = params.yearOfOnset3
            if(params.treatment3)
            generalMedicalHistoryInstance3.treatment = params.treatment3
            else
            generalMedicalHistoryInstance3.treatment = null 

            if(params.medicalRecord3)
            generalMedicalHistoryInstance3.medicalRecord = params.medicalRecord3
            else
            generalMedicalHistoryInstance3.medicalRecord = null

            generalMedicalHistoryInstance3.save(failOnError:true)



            //save object 4
            def generalMedicalHistoryInstance4 = GeneralMedicalHistory.get(params.id4)
            if(params.chooseOption4)
            generalMedicalHistoryInstance4.chooseOption = params.chooseOption4
            else
            generalMedicalHistoryInstance4.chooseOption =null

            generalMedicalHistoryInstance4.yearOfOnset = params.yearOfOnset4
            if(params.treatment4)
            generalMedicalHistoryInstance4.treatment = params.treatment4
            else
            generalMedicalHistoryInstance4.treatment = null 

            if(params.medicalRecord4)
            generalMedicalHistoryInstance4.medicalRecord = params.medicalRecord4
            else
            generalMedicalHistoryInstance4.medicalRecord = null

            generalMedicalHistoryInstance4.save(failOnError:true)



            //save object 5
            def generalMedicalHistoryInstance5 = GeneralMedicalHistory.get(params.id5)
            if(params.chooseOption5)
            generalMedicalHistoryInstance5.chooseOption = params.chooseOption5
            else
            generalMedicalHistoryInstance5.chooseOption =null

            generalMedicalHistoryInstance5.yearOfOnset = params.yearOfOnset5
            if(params.treatment5)
            generalMedicalHistoryInstance5.treatment = params.treatment5
            else
            generalMedicalHistoryInstance5.treatment = null 

            if(params.medicalRecord5)
            generalMedicalHistoryInstance5.medicalRecord = params.medicalRecord5
            else
            generalMedicalHistoryInstance5.medicalRecord = null

            generalMedicalHistoryInstance5.save(failOnError:true)


            //save object 6
            def generalMedicalHistoryInstance6 = GeneralMedicalHistory.get(params.id6)
            if(params.chooseOption6)
            generalMedicalHistoryInstance6.chooseOption = params.chooseOption6
            else
            generalMedicalHistoryInstance6.chooseOption =null

            generalMedicalHistoryInstance6.yearOfOnset = params.yearOfOnset6
            if(params.treatment6)
            generalMedicalHistoryInstance6.treatment = params.treatment6
            else
            generalMedicalHistoryInstance6.treatment = null 

            if(params.medicalRecord6)
            generalMedicalHistoryInstance6.medicalRecord = params.medicalRecord6
            else
            generalMedicalHistoryInstance6.medicalRecord = null

            generalMedicalHistoryInstance6.save(failOnError:true)


            //save object 7
            def generalMedicalHistoryInstance7 = GeneralMedicalHistory.get(params.id7)
            if(params.chooseOption7)
            generalMedicalHistoryInstance7.chooseOption = params.chooseOption7
            else
            generalMedicalHistoryInstance7.chooseOption =null

            generalMedicalHistoryInstance7.yearOfOnset = params.yearOfOnset7
            if(params.treatment7)
            generalMedicalHistoryInstance7.treatment = params.treatment7
            else
            generalMedicalHistoryInstance7.treatment = null 

            if(params.medicalRecord7)
            generalMedicalHistoryInstance7.medicalRecord = params.medicalRecord7
            else
            generalMedicalHistoryInstance7.medicalRecord = null

            generalMedicalHistoryInstance7.save(failOnError:true)


            //save object 8
            def generalMedicalHistoryInstance8 = GeneralMedicalHistory.get(params.id8)
            if(params.chooseOption8)
            generalMedicalHistoryInstance8.chooseOption = params.chooseOption8
            else
            generalMedicalHistoryInstance8.chooseOption =null

            generalMedicalHistoryInstance8.yearOfOnset = params.yearOfOnset8
            if(params.treatment8)
            generalMedicalHistoryInstance8.treatment = params.treatment8
            else
            generalMedicalHistoryInstance8.treatment = null 

            if(params.medicalRecord8)
            generalMedicalHistoryInstance8.medicalRecord = params.medicalRecord8
            else
            generalMedicalHistoryInstance8.medicalRecord = null

            generalMedicalHistoryInstance8.save(failOnError:true)


            //save object 9
            def generalMedicalHistoryInstance9 = GeneralMedicalHistory.get(params.id9)
            if(params.chooseOption9)
            generalMedicalHistoryInstance9.chooseOption = params.chooseOption9
            else
            generalMedicalHistoryInstance9.chooseOption =null

            generalMedicalHistoryInstance9.yearOfOnset = params.yearOfOnset9
            if(params.treatment9)
            generalMedicalHistoryInstance9.treatment = params.treatment9
            else
            generalMedicalHistoryInstance9.treatment = null 

            if(params.medicalRecord9)
            generalMedicalHistoryInstance9.medicalRecord = params.medicalRecord9
            else
            generalMedicalHistoryInstance9.medicalRecord = null

            generalMedicalHistoryInstance9.save(failOnError:true)


            //save object 10
            def generalMedicalHistoryInstance10 = GeneralMedicalHistory.get(params.id10)
            if(params.chooseOption10)
            generalMedicalHistoryInstance10.chooseOption = params.chooseOption10
            else
            generalMedicalHistoryInstance10.chooseOption =null

            generalMedicalHistoryInstance10.yearOfOnset = params.yearOfOnset10
            if(params.treatment10)
            generalMedicalHistoryInstance10.treatment = params.treatment10
            else
            generalMedicalHistoryInstance10.treatment = null 

            if(params.medicalRecord10)
            generalMedicalHistoryInstance10.medicalRecord = params.medicalRecord10
            else
            generalMedicalHistoryInstance10.medicalRecord = null

            generalMedicalHistoryInstance10.save(failOnError:true)


            //save object 11
            def generalMedicalHistoryInstance11 = GeneralMedicalHistory.get(params.id11)
            if(params.chooseOption11)
            generalMedicalHistoryInstance11.chooseOption = params.chooseOption11
            else
            generalMedicalHistoryInstance11.chooseOption =null

            generalMedicalHistoryInstance11.yearOfOnset = params.yearOfOnset11
            if(params.treatment11)
            generalMedicalHistoryInstance11.treatment = params.treatment11
            else
            generalMedicalHistoryInstance11.treatment = null 

            if(params.medicalRecord11)
            generalMedicalHistoryInstance11.medicalRecord = params.medicalRecord11
            else
            generalMedicalHistoryInstance11.medicalRecord = null

            generalMedicalHistoryInstance11.save(failOnError:true)

            //save object 12
            def generalMedicalHistoryInstance12 = GeneralMedicalHistory.get(params.id12)
            if(params.chooseOption12)
            generalMedicalHistoryInstance12.chooseOption = params.chooseOption12
            else
            generalMedicalHistoryInstance12.chooseOption =null

            generalMedicalHistoryInstance12.yearOfOnset = params.yearOfOnset12
            if(params.treatment12)
            generalMedicalHistoryInstance12.treatment = params.treatment12
            else
            generalMedicalHistoryInstance12.treatment = null 

            if(params.medicalRecord12)
            generalMedicalHistoryInstance12.medicalRecord = params.medicalRecord12
            else
            generalMedicalHistoryInstance12.medicalRecord = null

            generalMedicalHistoryInstance12.save(failOnError:true)


            //save object 13
            def generalMedicalHistoryInstance13 = GeneralMedicalHistory.get(params.id13)
            if(params.chooseOption13)
            generalMedicalHistoryInstance13.chooseOption = params.chooseOption13
            else
            generalMedicalHistoryInstance13.chooseOption =null

            generalMedicalHistoryInstance13.yearOfOnset = params.yearOfOnset13
            if(params.treatment13)
            generalMedicalHistoryInstance13.treatment = params.treatment13
            else
            generalMedicalHistoryInstance13.treatment = null 

            if(params.medicalRecord13)
            generalMedicalHistoryInstance13.medicalRecord = params.medicalRecord13
            else
            generalMedicalHistoryInstance13.medicalRecord = null

            generalMedicalHistoryInstance13.save(failOnError:true)


            //save object 14
            def generalMedicalHistoryInstance14 = GeneralMedicalHistory.get(params.id14)
            if(params.chooseOption14)
            generalMedicalHistoryInstance14.chooseOption = params.chooseOption14
            else
            generalMedicalHistoryInstance14.chooseOption =null

            generalMedicalHistoryInstance14.yearOfOnset = params.yearOfOnset14
            if(params.treatment14)
            generalMedicalHistoryInstance14.treatment = params.treatment14
            else
            generalMedicalHistoryInstance14.treatment = null 

            if(params.medicalRecord14)
            generalMedicalHistoryInstance14.medicalRecord = params.medicalRecord14
            else
            generalMedicalHistoryInstance14.medicalRecord = null

            generalMedicalHistoryInstance14.save(failOnError:true)


            //save object 15
            def generalMedicalHistoryInstance15 = GeneralMedicalHistory.get(params.id15)
            if(params.chooseOption15)
            generalMedicalHistoryInstance15.chooseOption = params.chooseOption15
            else
            generalMedicalHistoryInstance15.chooseOption =null

            generalMedicalHistoryInstance15.yearOfOnset = params.yearOfOnset15
            if(params.treatment15)
            generalMedicalHistoryInstance15.treatment = params.treatment15
            else
            generalMedicalHistoryInstance15.treatment = null 

            if(params.medicalRecord15)
            generalMedicalHistoryInstance15.medicalRecord = params.medicalRecord15
            else
            generalMedicalHistoryInstance15.medicalRecord = null

            generalMedicalHistoryInstance15.save(failOnError:true)




            //save object 16
            def generalMedicalHistoryInstance16 = GeneralMedicalHistory.get(params.id16)
            if(params.chooseOption16)
            generalMedicalHistoryInstance16.chooseOption = params.chooseOption16
            else
            generalMedicalHistoryInstance16.chooseOption =null

            generalMedicalHistoryInstance16.yearOfOnset = params.yearOfOnset16
            if(params.treatment16)
            generalMedicalHistoryInstance16.treatment = params.treatment16
            else
            generalMedicalHistoryInstance16.treatment = null 

            if(params.medicalRecord16)
            generalMedicalHistoryInstance16.medicalRecord = params.medicalRecord16
            else
            generalMedicalHistoryInstance16.medicalRecord = null

            generalMedicalHistoryInstance16.save(failOnError:true)



            //save object 17
            def generalMedicalHistoryInstance17 = GeneralMedicalHistory.get(params.id17)
            if(params.chooseOption17)
            generalMedicalHistoryInstance17.chooseOption = params.chooseOption17
            else
            generalMedicalHistoryInstance17.chooseOption =null

            generalMedicalHistoryInstance17.yearOfOnset = params.yearOfOnset17
            if(params.treatment17)
            generalMedicalHistoryInstance17.treatment = params.treatment17
            else
            generalMedicalHistoryInstance17.treatment = null 

            if(params.medicalRecord17)
            generalMedicalHistoryInstance17.medicalRecord = params.medicalRecord17
            else
            generalMedicalHistoryInstance17.medicalRecord = null

            generalMedicalHistoryInstance17.save(failOnError:true)



            //save object 18
            def generalMedicalHistoryInstance18 = GeneralMedicalHistory.get(params.id18)
            if(params.chooseOption18)
            generalMedicalHistoryInstance18.chooseOption = params.chooseOption18
            else
            generalMedicalHistoryInstance18.chooseOption =null

            generalMedicalHistoryInstance18.yearOfOnset = params.yearOfOnset18
            if(params.treatment18)
            generalMedicalHistoryInstance18.treatment = params.treatment18
            else
            generalMedicalHistoryInstance18.treatment = null 

            if(params.medicalRecord18)
            generalMedicalHistoryInstance18.medicalRecord = params.medicalRecord18
            else
            generalMedicalHistoryInstance18.medicalRecord = null

            generalMedicalHistoryInstance18.save(failOnError:true)


            //save object 19
            def generalMedicalHistoryInstance19 = GeneralMedicalHistory.get(params.id19)
            if(params.chooseOption19)
            generalMedicalHistoryInstance19.chooseOption = params.chooseOption19
            else
            generalMedicalHistoryInstance19.chooseOption =null

            generalMedicalHistoryInstance19.yearOfOnset = params.yearOfOnset19
            if(params.treatment19)
            generalMedicalHistoryInstance19.treatment = params.treatment19
            else
            generalMedicalHistoryInstance19.treatment = null 

            if(params.medicalRecord19)
            generalMedicalHistoryInstance19.medicalRecord = params.medicalRecord19
            else
            generalMedicalHistoryInstance19.medicalRecord = null

            generalMedicalHistoryInstance19.save(failOnError:true)



            //save object 20
            def generalMedicalHistoryInstance20 = GeneralMedicalHistory.get(params.id20)
            if(params.chooseOption20)
            generalMedicalHistoryInstance20.chooseOption = params.chooseOption20
            else
            generalMedicalHistoryInstance20.chooseOption =null

            generalMedicalHistoryInstance20.yearOfOnset = params.yearOfOnset20
            if(params.treatment20)
            generalMedicalHistoryInstance20.treatment = params.treatment20
            else
            generalMedicalHistoryInstance20.treatment = null 

            if(params.medicalRecord20)
            generalMedicalHistoryInstance20.medicalRecord = params.medicalRecord20
            else
            generalMedicalHistoryInstance20.medicalRecord = null
            generalMedicalHistoryInstance20.save(failOnError:true)*/

        }catch(Exception e){
            // println("catch Exception??? " + e.toString())
            throw new RuntimeException(e.toString())
        }
        
    }
    
    
    def saveSurgicalProcedures(params){       
        try{
            // println("start save")  
            
            def surgicalProceduresInstance = SurgicalProcedures.get(params.id)
            surgicalProceduresInstance.durationPreOpeMed = params.durationPreOpeMed
            surgicalProceduresInstance.durationAnesthesia =params.durationAnesthesia
            surgicalProceduresInstance.save(failOnError:true)
          
            params.each(){key,value->
                def theid
                if(value=='isid'){
                    theid=key
                    //println("in servuce: ${theid}")
                    //  if(theid == '61'){
                    // def mname = params["${key}_medication_name"]
                    //def dosage = params["${key}_dosage"]
                    //println("inservice: ${theid}, ${mname}, ${dosage}")
                    
                    // }
                    def surgicalMedication = SurgicalMedication.get(key)
                    surgicalMedication.dosage=params["${key}_dosage"]
                    if(surgicalMedication.isOther1 || surgicalMedication.isOther2 || surgicalMedication.catgory=='during surgery' 
                        ||(!surgicalMedication.subCatgory)){
                        surgicalMedication.medicationName=params["${key}_medication_name"]
                    }
                    surgicalMedication.save(failOnError:true)
                }
            
            }
            
            def newMedication1 = params.new_medication1
            def newDosage1 =params.new_dosage1
            
            if(newMedication1 ||newDosage1){
                SurgicalMedication surgicalMedication1 = new SurgicalMedication(surgicalProcedures:surgicalProceduresInstance, medicationName:newMedication1, catgory:'Pre-Operative Medications', dosage:newDosage1)
                surgicalMedication1.save(failOnError:true)
            }
          
            def newMedication2 = params.new_medication2
            def newDosage2 =params.new_dosage2
            
            if(newMedication2 ||newDosage2){
                SurgicalMedication surgicalMedication2 = new SurgicalMedication(surgicalProcedures:surgicalProceduresInstance, medicationName:newMedication2, catgory:'Pre-Operative Anesthesia', dosage:newDosage2 )
                surgicalMedication2.save(failOnError:true)
            }
           
            
            def newMedication3 = params.new_medication3
            def newDosage3 =params.new_dosage3
            
            if(newMedication3 ||newDosage3){
                SurgicalMedication surgicalMedication3 = new SurgicalMedication(surgicalProcedures:surgicalProceduresInstance, medicationName:newMedication3, catgory:'during surgery', dosage:newDosage3 )
                surgicalMedication3.save(failOnError:true)
            }
            
            def deleteid = params.delete
            if(deleteid){
                def surgicalMedication = SurgicalMedication.get(deleteid)
                surgicalMedication.delete(failOnError:true)
            }
           
        }catch(Exception e){
            // println("catch Exception??? " + e.toString())
            throw new RuntimeException(e.toString())
        }
          
    }

    
    def updateAge(){
        log.info((new Date()).toString() + " started update age")
        try{
            def caseList = CaseRecord.list()
            caseList.each(){
                def caseReportForm = it.caseReportForm
                def collectionDate = it.tissueRecoveryGtex?.collectionDate
                def startTime = it.tissueRecoveryGtex?.collectionStartTime
                def collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)
                def cross_clamp_time = it.tissueRecoveryGtex?.crossClampTime
                def crossClampDateTime = ldaccService.getDateTimeComp(collectionStartTime, cross_clamp_time)
                               
                def deathDate
                if(it.caseCollectionType?.code == 'POSTM' || it.caseCollectionType?.code == 'OPO' ){
                    if(caseReportForm?.deathCircumstances?.dateTimeActualDeath){
                                      
                        deathDate = caseReportForm?.deathCircumstances?.dateTimeActualDeath
                    }else{
                                      
                        deathDate = caseReportForm?.deathCircumstances?.dateTimePresumedDeath
                    }
                                   
                                                                    
                }else{
                                
                    deathDate = crossClampDateTime
                              
                }
             
                def demographics = it.caseReportForm?.demographics
             
                def birthDate = demographics?.dateOfBirth
                def age =ldaccService.calculateAge(deathDate, birthDate)
            
                if(age){
                    demographics.ageForIndex = age
                    demographics.save(failOnError:true)
                }
            
           
            }
        
        }catch(Exception e){
            // println("catch Exception??? " + e.toString())
            throw new RuntimeException(e.toString())
        }
         
        log.info((new Date()).toString() + " end update age")
    }
    
    
    //pmh: added this for the vocab task 04/03/13
    def updateMedicalConditionCodeVal(gmhList){
        def medicalConditionInstance
        
        for(g in gmhList){
            if(g.medicalCondition && !g.code){
                
                medicalConditionInstance=MedicalCondition.findByName(g.medicalCondition)
                g.code=medicalConditionInstance?.code
            }
        
        }
   
        return gmhList
    }
    
    
    
    
     
   def findVocab(vocab){
       def found = false
      def result = [:]
      result.put("found: ", found)
    //  def vocab = CVocabRecord.findById(vocab_id)
      
      def  cVocabUserSelection = vocab.cVocabUserSelection
      println("userSelection: " + cVocabUserSelection)
      def  cVocabType =vocab.cVocabType.code
      def  icdCd = vocab.icdCd
      
        def icdList=[]
        def tmp=[]
        if(icdCd)
            tmp = icdCd.split(",")
        tmp.each{
            icdList.add(it)
            icdList.add(it)
        }
         
        //println("icdList size: " + icdList.size())
        
      def  cVocabVer = vocab.cVocabVer
      
      def cuiList=[]
      if(vocab.cuiList){
          vocab.cuiList.each{
              cuiList.add(it.cui)
          }
      }
      
     
    //  println("cVocabUserSelection: " + cVocabUserSelection)
     // println("cVocabType: "  + cVocabType)
     // icdList.each{
        //  println("icd: " + it)
    //  }
     // println("cVocabVer: " + cVocabVer)
      
  //    cuiList.each{
     //     println("cui: " + it)
    //  }
        
        
        def typ=""
        
        if(cVocabType=='COD')
          typ='cod'
          
        if(cVocabType=='RX')
          typ='rx'
        
      
         if(cVocabType=='MEDCON')
          typ='cod'
        
        def http
        if(typ == 'rx' && cVocabVer == '120312'){
          http = new HTTPBuilder( 'http://fr-s-hub-solr-t.ncifcrf.gov:8080' )
        }else{
            http = new HTTPBuilder( 'http://fr-s-hub-solr-p.ncifcrf.gov:8080' )
        }
        http.request( GET, TEXT ) {
          uri.path = '/solr/' + typ
          //uri.query = [ fl:'*,score', q: 'id:cod_4', wt: "json" ]
          uri.query = [ fl:'*,score', q:cVocabUserSelection, wt:"json" ]

          headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'

          // response handler for a success response code:
          response.success = { resp, json ->
            println resp.statusLine
           // println("text: " + json.text)
           JSONObject userJson = JSON.parse(json.text)
           def  map = userJson.get("response")
          // println("numFound: " + map.get("numFound"))
           def docs = map.get("docs")
           println("doc size: " + docs.size())
           docs.each{
               if(!found){
               def list1=[]
               def list2=[]
                    
               def id = it.id
       
               def typ2 = it.typ
              // println("typ2: " + typ2)
               def ver = it.cvocabVer
              
               def cod = it.cod
               def rx=it.rx
             
               def cuis = it.cui
               cuis.each{it2->
                  // println("cui: " + it2)
                   list1.add(it2)
                   
               }
               def icdcds=it.ICDcd
               icdcds.each{it2->
                  // println("ICDcd: " + it2)
                   list2.add(it2)
                   
               }
               
               if(typ2 == 'cod'){
                   //println("I am here???")
                   if(ver == cVocabVer && cod == cVocabUserSelection && listEq(list1,cuiList ) && listEq(list2, icdList) ){
                       found= true
                       result.put("found", found)
                      def srcDefs = it.srcDef
                      def str=""
                        srcDefs.each{it2->
                        // println("srcDef: " + it2)
                         str = str + "<div>" + it2 + "</div>"
                   
                       }
                       result.put("data", str)
                       
                   }
                      
                        
                        
               }     
                  
                        
                if(typ2 == 'rx'){
                   //println("I am here???")
                   if(ver == cVocabVer && rx == cVocabUserSelection ){
                       found= true
                       result.put("found", found)
                      def genNames = it.genName
                      def str=""
                        genNames.each{it2->
                        // println("srcDef: " + it2)
                         str = str + "<div>" + it2 + "</div>"
                   
                       }
                       result.put("data", str)
                       
                   }
                      
                        
                        
               }              
                        
               }//!found
               
           }//each

          }//success
          // handler for any failure status code:
          response.failure = { resp ->
            println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
          }
     
        
        }
        

       return result
       
   }
   

   boolean listEq(list1, list2){
       boolean result = true
       if(!list1 && !list2)
       return true;
       
  
        
        
        list1.each{
            if(!list2.contains(it))
              result = false
        }
        
        list2.each{
            if(!list1.contains(it))
             result = false
        }
        
        //println("before rerutn: " + result)
        return result
   }

    
    def updateVocab(vocab, data){
        def typ=vocab.cVocabType.code
        
        if(typ=="COD" || typ=='MEDCON'){
          vocab.srcDef = data
          vocab.save(failOnError:true, flush: true)
        }
        
        if(typ=='RX'){
            vocab.genName=data
            vocab.save(failOnError:true, flush: true)
        
        }
           
        
    }
    
}
