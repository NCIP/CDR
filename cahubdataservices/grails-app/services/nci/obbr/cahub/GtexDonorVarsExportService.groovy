package nci.obbr.cahub

import nci.obbr.cahub.forms.*
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*

import java.text.SimpleDateFormat

import grails.converters.deep.*

import org.custommonkey.xmlunit.*
import groovy.xml.StreamingMarkupBuilder

import nci.obbr.cahub.forms.gtex.BrainTissue
import nci.obbr.cahub.util.ComputeMethods
import nci.obbr.cahub.util.querytracker.*

class GtexDonorVarsExportService {

    static transactional = false

    def utilService
    def ldaccService
    def mbbService
    def prcReportService
    def bmsService



    def gtexPartialDonorVarsExport(caseId) {
        
        def list
        if(caseId) {
            list = CaseRecord.findByCaseId(caseId.toUpperCase())
        } else {
            list = CaseRecord.findAll()
        }

        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"

        def xmlDocument = builder.bind {
            mkp.xmlDeclaration()
            if(list) {
                def session = utilService.getSession()
                "GTEx_de_identified_donor_variables" {
                    for(c in list) {
                        if(c.bss.parentBss.code.matches(session.org.code) || session.org.code.matches("OBBR") || session.org.code.matches("VARI") || session.org.code.matches("BROAD")) {
                            "case"(id:c.caseId, collection_type:c.caseCollectionType?.name, status:c.caseStatus?.name) {
                                if(true) {
                                    def hasBrain = false
                                    def sList = c.specimens
                                    def green = ['0011','0014']
                                    def yellow = ['0001','0002','0003','0004','0005','0006', '0008', '0009', '0007', '0010']
                                    def green_list = []
                                    def green_btlist = []
                                    def green_hairlist = []
                                    def yellow_list = []
                                    def pink_list=[]
                                    def aqua_list = []
                                    def parentSpecimenBrainRemovalTime
                                    def parentSpecimenBrainRemovalDate = c.tissueRecoveryGtex?.firstTissueRemovedDate
                                    def parentbrainRemovedDateTime=mbbService.brainRemovedDateTime(c)
                                        
                                    for(s in sList) {
                                        String seq = s.gtexSequenceNum()
                                        if(seq == '0011' ) {
                                            hasBrain = true
                                            
                                        }

                                        if(green.contains(seq)) {
                                            if(s.tissueType.code=='BRAIN' && seq == '0011'){
                                                green_list.add(s)
                                                parentSpecimenBrainRemovalTime=s.brainTimeStartRemoval
                                               
                                            }
                                            if(s.tissueType.code=='HAIR'){
                                                green_hairlist.add(s)
                                            }
                                            if(!(s.tissueType.code=='BRAIN') && seq.contains('0011')){
                                                
                                                green_btlist.add(s)
                                            }
                                            
                                        } else if(yellow.contains(seq)) {
                                            yellow_list.add(s)
                                        } else {
                                            if(s.fixative.code == 'DICE'){
                                                //println "adding to pink kit "+s.fixativeId
                                                pink_list.add(s)
                                            }else{
                                                //println "adding to aqua kit "+s.fixativeId
                                                aqua_list.add(s)
                                            }
                                        }
                                    }

                                    def deathType
                                    def deathDate

                                    def collectionDate = c.tissueRecoveryGtex?.collectionDate
                                    def startTime = c.tissueRecoveryGtex?.collectionStartTime
                                    def collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)

                                    def firstBloodDrawDate = c.tissueRecoveryGtex?.firstBloodDrawDate
                                    def firstBloodDrawTime = c.tissueRecoveryGtex?.firstBloodDrawTime
                                    def firstBloodDrawDateTime = ldaccService.calculateDateWithTime(firstBloodDrawDate,firstBloodDrawTime)

                                    def firstTissueRemovedDate = c.tissueRecoveryGtex?.firstTissueRemovedDate
                                    def firstTissueRemovedTime = c.tissueRecoveryGtex?.firstTissueRemovedTime
                                    def firstTissueRemovedDateTime = ldaccService.calculateDateWithTime(firstTissueRemovedDate,firstTissueRemovedTime)

                                    def chestIncisionTime = c.tissueRecoveryGtex?.chestIncisionTime
                                    def chestIncisionDateTime = ldaccService.getDateTimeComp(collectionStartTime,chestIncisionTime)
                                    def surgeryStartTime = c.tissueRecoveryGtex?.surgeryStartTime
                                    def surgeryStartDateTime=ldaccService.getDateTimeComp(collectionStartTime,surgeryStartTime)

                                    def cross_clamp_time = c.tissueRecoveryGtex?.crossClampTime
                                    def crossClampDateTime = ldaccService.getDateTimeComp(collectionStartTime, cross_clamp_time)

                                    def deathMap = getDeathMap(c)
                                    deathType= deathMap.get("deathType")
                                    deathDate = deathMap.get("deathDate")

                                    tissue_recovery {
                                        def cRFTRFGTEX=c?.tissueRecoveryGtex
                                        def organsDonated=[]
                                        //Liver, Lungs, Kidneys
                                        if(cRFTRFGTEX?.donatedLiver){
                                            organsDonated.add('Liver')
                                        }
                                        if(cRFTRFGTEX?.donatedLung){
                                            organsDonated.add('Lung')
                                        }
                                        if(cRFTRFGTEX?.donatedHeart){
                                            organsDonated.add('Heart')
                                        }
                                        if(cRFTRFGTEX?.donatedKidney){
                                            organsDonated.add('Kidney')
                                        }
                                        if(cRFTRFGTEX?.donatedPancreas){
                                            organsDonated.add('Pancreas')
                                        }
                                        if(cRFTRFGTEX?.donatedOrganOther){
                                            organsDonated.add(cRFTRFGTEX.donatedOrganOther)
                                        }
                                        // println organsDonated.join(", ")
                                        def p_start = ldaccService.calculateInterval(deathDate,collectionStartTime)
                                        "interval_between_${deathType}_and_GTEx_procedure_start"(p_start[0], "de-identified":"true", "millisecond":p_start[1])
                                        if(c.caseCollectionType?.code == 'POSTM' || c.caseCollectionType?.code == 'OPO') {
                                            def incision = ldaccService.calculateInterval(deathDate,chestIncisionDateTime)
                                            "interval_between_${deathType}_and_chest_incision"(incision[0], "de-identified":"true", "millisecond":incision[1])
                                        }

                                        if(c.caseCollectionType?.code == 'OPO') {
                                            def cc = ldaccService.calculateInterval(deathDate,crossClampDateTime)
                                            //new from 4.5
                                            
                                            "interval_between_${deathType}_and_cross_clamp"(cc[0],"de-identified":"true", "millisecond":cc[1])
                                            //organs_tissues_donated(cRFTRFGTEX?.organsDonated)
                                            organs_tissues_donated(organsDonated.join(", "))
                                        }

                                        if(c.caseCollectionType?.code == 'SURGI') {
                                            amputation_type(cRFTRFGTEX?.amputationType)
                                            def cs = ldaccService.calculateInterval(deathDate,surgeryStartDateTime)
                                            "interval_between_${deathType}_and_surgery_start"(cs[0],"de-identified":"true", "millisecond":cs[1])
                                        }

                                        if(cRFTRFGTEX?.coreBodyTemp == -1000) {
                                            core_body_temperature()
                                        } else {
                                            core_body_temperature(cRFTRFGTEX?.coreBodyTemp)
                                        }

                                        temperature_scale (cRFTRFGTEX?.coreBodyTempScale)
                                        temperature_obtained_via(cRFTRFGTEX?.coreBodyTempLoc)
                                        sops{
                                            sop("code":"OP-0001", "ver":cRFTRFGTEX?.op0001?.sopVersion) 
                                            sop("code":"PR-0004", "ver":cRFTRFGTEX?.pr0004?.sopVersion)
                                            sop("code":"PM-0003", "ver":cRFTRFGTEX?.pm0003?.sopVersion)
                                        }

                                        specimens {
                                            if(c.caseCollectionType?.code == 'POSTM' || c.caseCollectionType?.code == 'OPO') {
                                                green_kit {
                                                    if(hasBrain) {
                                                        vent_less_24_hrs(cRFTRFGTEX?.ventLess24hrs)
                                                    }

                                                    for(s in green_list) {
                                                        specimen {
                                                            specimen_id(s.specimenId)

                                                            def brainTimeStartRemovalInterval
                                                            def brainTimeStartRemovalIntervalM
                                                            def brainTimeEndAliquotInterval
                                                            def brainTimeEndAliquotIntervalM
                                                            def brainTimeIceInterval
                                                            def brainTimeIceIntervalM

                                                            brainTimeStartRemovalInterval =ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime, s.brainTimeStartRemoval)[0]
                                                            brainTimeStartRemovalIntervalM =ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,s.brainTimeStartRemoval )[1]
                                                            brainTimeEndAliquotInterval =ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,s.brainTimeEndAliquot )[0]
                                                            brainTimeEndAliquotIntervalM =ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,s.brainTimeEndAliquot )[1]

                                                            def brainIceDateTime = ldaccService.getDateTimeComp(firstTissueRemovedDateTime, s.brainTimeIce)
                                                            brainTimeIceInterval = ldaccService.calculateInterval(deathDate, brainIceDateTime )[0]
                                                            brainTimeIceIntervalM = ldaccService.calculateInterval(deathDate,brainIceDateTime)[1]

                                                            String seq = s.gtexSequenceNum()
                                                            if(seq == '0011') {
                                                                tissue_type(s.tissueType?.name)
                                                                "interval_between_${deathType}_and_brain_start_remove"(brainTimeStartRemovalInterval, millisecond:brainTimeStartRemovalIntervalM, "de-identified":"true")
                                                                "interval_between_${deathType}_and_brain_end_aliquot"(brainTimeEndAliquotInterval, millisecond:brainTimeEndAliquotIntervalM,"de-identified":"true")
                                                                "interval_between_${deathType}_and_brain_put_on_ice"(brainTimeIceInterval, millisecond:brainTimeIceIntervalM,"de-identified":"true")
                                                            }

                                                           
                                                            prosector_comments(s.prosectorComments)
                                                        }
                                                    }
                                                    // start of brain sub tissues
                                                    /**
                                                    so for each element we need the following tags:
                                                    Brain_aliquot_id        (sample ID)
                                                    collaborator_id
                                                    collaborator_id_2
                                                    mass, with an attribute of units=mg
                                                    interval_between_brain_start_removal_and_brain_disection_time
                                                    interval_between_actual_death_time_and_brain_disection_time
                                                    interval_between_presumed_death_time_and_brain_disection_time
                                                    brain_tissue_location :    (Hippocampus, Substantia nigra, Anterior cingulate cortex (BA24), Amygdala, Caudate (basal ganglia), Nucleus accumbens (basal ganglia), Putamen (basal ganglia), Hypothalamus

                                                    
                                                     **/
                                                    green_sub_tissues_kit {
                                                        def btinstance
                                                        for(sbt in green_btlist) {
                                                            specimen {
                                                                specimen_id(sbt.specimenId)
                                                                
                                                                btinstance=BrainTissue.findBySpecimenRecord(sbt)
                                                                Brain_aliquot_id(btinstance.sampleId)
                                                                collaborator_id(btinstance.collSampleId)
                                                                collaborator_id_2(btinstance.collSampleId2)
                                                                mass("units_in_mg":btinstance.mass)
                                                                
                                                                // now define the time for brain removal
                                                                //a: death to dissection
                                                                def deathToDissectionInterval=ldaccService.calculateInterval(deathDate,btinstance.collectionDate, btinstance.collectionTimeStr)[0]
                                                                def deathToDissectionIntervalM=ldaccService.calculateInterval(deathDate,btinstance.collectionDate, btinstance.collectionTimeStr)[1]
                                                                
                                                                //b: death to removal of parent brain
                                                                def brainTimeStartRemovalInterval =ldaccService.calculateInterval(deathDate,parentSpecimenBrainRemovalDate,parentSpecimenBrainRemovalTime)[0]
                                                                def brainTimeStartRemovalIntervalM =ldaccService.calculateInterval(deathDate,parentSpecimenBrainRemovalDate,parentSpecimenBrainRemovalTime)[1]
                                                                                                                                
                                                                // c: removal to dissection                                                                       
                                                                def removalToDissectionInterval = ldaccService.calculateInterval(parentbrainRemovedDateTime, btinstance.collectionDate,btinstance.collectionTimeStr)[0]
                                                                def removalToDissectionTimeM=ldaccService.calculateInterval(parentbrainRemovedDateTime, btinstance.collectionDate,btinstance.collectionTimeStr)[1]
                                                                                                                                
                                                                "interval_between_brain_start_removal_and_brain_disection_time"(removalToDissectionInterval, millisecond:removalToDissectionTimeM, "de-identified":"true")
                                                                "interval_between_${deathType}_and_brain_disection_time"(deathToDissectionInterval, millisecond:deathToDissectionIntervalM, "de-identified":"true")
                                                               
                                                                brain_tissue_location(btinstance.tissueType)
                                                                //prosector_comments(sbt.prosectorComments)
                                                            }
                                                        }
                                                    }
                                                    // end of green brains sub tissues
                                                    
                                                    
                                                    // start of hair
                                                    
                                                    for(sh in green_hairlist) {
                                                        specimen {
                                                            specimen_id(sh.specimenId)

                                                            String seq = sh.gtexSequenceNum()
                                                            if(seq == '0014') {
                                                                tissue_type(sh.tissueType?.name)
                                                            }

                                                            prosector_comments(sh.prosectorComments)
                                                        }
                                                    }
                                                   
                                                    // end of hair
                                                
                                                }//end green_kit
                                                
                                                
                                              
                                            }// case

                                            yellow_kit {
                                                for(s in yellow_list) {
                                                    specimen {
                                                        specimen_id(s.specimenId)

                                                        def bloodTimeDrawInterval
                                                        def bloodTimeDrawIntervalM
                                                        def bloodTimeDrawInvertedInterval
                                                        def bloodTimeDrawInvertedIntervalM
                                                        def skinTimeIntoMediumInterval
                                                        def skinTimeIntoMediumIntervalM

                                                        bloodTimeDrawInterval = ldaccService.calculateInterval(deathDate,firstBloodDrawDateTime,s.bloodTimeDraw)[0]
                                                        bloodTimeDrawIntervalM = ldaccService.calculateInterval(deathDate,firstBloodDrawDateTime,s.bloodTimeDraw)[1]

                                                        bloodTimeDrawInvertedInterval = ldaccService.calculateInterval(deathDate,firstBloodDrawDateTime,s.bloodTimeDrawInverted)[0]
                                                        bloodTimeDrawInvertedIntervalM = ldaccService.calculateInterval(deathDate,firstBloodDrawDateTime,s.bloodTimeDrawInverted)[1]

                                                        def skinIntoMediumDateTime = ldaccService.getDateTimeComp(firstTissueRemovedDateTime, s.skinTimeIntoMedium)
                                                        skinTimeIntoMediumInterval = ldaccService.calculateInterval(deathDate,skinIntoMediumDateTime)[0]
                                                        skinTimeIntoMediumIntervalM= ldaccService.calculateInterval(deathDate,skinIntoMediumDateTime)[1]
                                                        tissue_type(s.tissueType?.name)

                                                        String seq = s.gtexSequenceNum()
                                                        if(seq == '0008') {
                                                            fixative(s.fixative?.name)
                                                           "interval_between_${deathType}_and_skin_into_medium"(skinTimeIntoMediumInterval,millisecond:skinTimeIntoMediumIntervalM,"de-identified":"true")
                                                        } else {
                                                            fixative(s.fixative?.name)
                                                            "interval_between_${deathType}_and_blood_draw"(bloodTimeDrawInterval, millisecond:bloodTimeDrawIntervalM,"de-identified":"true")
                                                            "interval_between_${deathType}_and_blood_draw_inverted"(bloodTimeDrawInvertedInterval, millisecond:bloodTimeDrawInvertedIntervalM,"de-identified":"true")
                                                        }
                                                        prosector_comments(s.prosectorComments)
                                                    }
                                                }
                                            }//yellow_kit

                                            aqua_kit {
                                                for(s in aqua_list) {
                                                    specimen {
                                                        specimen_id(s.specimenId)

                                                        def aliquotTimeRemovedInterval
                                                        def aliquotTimeRemovedIntervalM
                                                        def aliquotTimeStabilizedInterval
                                                        def aliquotTimeStabilizedIntervalM
                                                        def aliquotTimeFixedInterval
                                                        def aliquotTimeFixedIntervalM

                                                        aliquotTimeRemovedInterval =ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,s.aliquotTimeRemoved)[0]
                                                        aliquotTimeRemovedIntervalM =ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,s.aliquotTimeRemoved)[1]

                                                        def aliquotFixedDateTime = ldaccService.getDateTimeAfter(firstTissueRemovedDateTime, s.aliquotTimeFixed)
                                                        def aliquortStabilizedDateTime=ldaccService.getDateTimeAfter4To28(aliquotFixedDateTime,s.aliquotTimeStabilized)
                                                        aliquotTimeStabilizedInterval = ldaccService.calculateInterval(deathDate, aliquortStabilizedDateTime)[0]
                                                        aliquotTimeStabilizedIntervalM = ldaccService.calculateInterval(deathDate,aliquortStabilizedDateTime)[1]

                                                        aliquotTimeFixedInterval = ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,s.aliquotTimeFixed)[0]
                                                        aliquotTimeFixedIntervalM = ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,s.aliquotTimeFixed)[1]
                                                        tissue_type(s.tissueType?.name)
                                                        fixative(s.fixative?.name)

                                                        def loc = s.tissueLocation?.name
                                                        if(loc && loc=='Other, specify') {
                                                            tissue_location(s.otherTissueLocation)
                                                        } else {
                                                            tissue_location(s.tissueLocation?.name)
                                                        }

                                                        "interval_between_${deathType}_and_aliquot_removed"(aliquotTimeRemovedInterval, millisecond:aliquotTimeRemovedIntervalM, "de-identified":"true")
                                                        "interval_between_${deathType}_and_aliquot_put_in_fixative"(aliquotTimeFixedInterval, millisecond:aliquotTimeFixedIntervalM,"de-identified":"true")
                                                        "interval_between_${deathType}_and_aliquot_placed_in_stabilizer"(aliquotTimeStabilizedInterval, millisecond:aliquotTimeStabilizedIntervalM,"de-identified":"true")
                                                        size_diff_than_SOP(s.sizeDiffThanSOP)
                                                        prosector_comments(s.prosectorComments)
                                                    }//specimen
                                                }//for aqua_list
                                            }//aqua_kit
                                            
                                            pink_kit {
                                                for(p in pink_list) {
                                                    specimen {
                                                        specimen_id(p.specimenId)
                                                        def aliquotTimeRemovedInterval
                                                        def aliquotTimeRemovedIntervalM
                                                        def aliquotTimeStabilizedInterval
                                                        def aliquotTimeStabilizedIntervalM
                                                        def aliquotTimeFixedInterval
                                                        def aliquotTimeFixedIntervalM

                                                        aliquotTimeRemovedInterval =ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,p.aliquotTimeRemoved)[0]
                                                        aliquotTimeRemovedIntervalM =ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,p.aliquotTimeRemoved)[1]

                                                        def aliquotFixedDateTime = ldaccService.getDateTimeAfter(firstTissueRemovedDateTime, p.aliquotTimeFixed)
                                                        def aliquortStabilizedDateTime=ldaccService.getDateTimeAfter4To28(aliquotFixedDateTime,p.aliquotTimeStabilized)
                                                        aliquotTimeStabilizedInterval = ldaccService.calculateInterval(deathDate, aliquortStabilizedDateTime)[0]
                                                        aliquotTimeStabilizedIntervalM = ldaccService.calculateInterval(deathDate,aliquortStabilizedDateTime)[1]

                                                        aliquotTimeFixedInterval = ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,p.aliquotTimeFixed)[0]
                                                        aliquotTimeFixedIntervalM = ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,p.aliquotTimeFixed)[1]
                                                        tissue_type(p.tissueType?.name)
                                                        fixative(p.fixative?.name)

                                                        def loc = p.tissueLocation?.name
                                                        if(loc && loc == 'Other, specify') {
                                                            tissue_location(p.otherTissueLocation)
                                                        } else {
                                                            tissue_location(p.tissueLocation?.name)
                                                        }

                                                        "interval_between_${deathType}_and_aliquot_removed"(aliquotTimeRemovedInterval, millisecond:aliquotTimeRemovedIntervalM, "de-identified":"true")
                                                        "interval_between_${deathType}_and_aliquot_put_in_fixative"(aliquotTimeFixedInterval, millisecond:aliquotTimeFixedIntervalM,"de-identified":"true")
                                                        "interval_between_${deathType}_and_aliquot_placed_in_stabilizer"(aliquotTimeStabilizedInterval, millisecond:aliquotTimeStabilizedIntervalM,"de-identified":"true")
                                                        adjacent_to_paxgene_tissue (p.adjacentToPaxTissue)
                                                        size_diff_than_SOP(p.sizeDiffThanSOP)
                                                        prosector_comments(p.prosectorComments)
                                                    }//specimen
                                                }//for pink_list
                                            }//pink_kit
                                            
                                            
                                        }//specimens
                                    }//CRFTRFGTEX
                                    case_report {
                                        def caseReportForm = c.caseReportForm
                                        demographics {
                                            def demographics=caseReportForm?.demographics
                                            if(demographics?.gender && demographics?.gender.toString() == 'Other') {
                                                gender(demographics?.otherGender)
                                            } else {
                                                gender(demographics?.gender)
                                            }
                                            
                                            def birthDate = demographics?.dateOfBirth
                                            def age2 =ldaccService.calculateAge(deathDate, birthDate)

                                            if(age2) {
                                                if(age2 > 89) {
                                                    age('90+',"de-identified":"true")
                                                } else {
                                                    age(age2,"de-identified":"true")
                                                }
                                            } else {
                                                age()
                                            }

                                            height(demographics?.height, unit:"in")
                                            weight(demographics?.weight, unit:"lb")
                                            BMI(demographics?.BMI)

                                            String race2 = ""
                                            if(demographics?.raceWhite) {
                                                race2 = race2+ ",White"
                                            }
                                            if(demographics?.raceAsian){
                                                race2 = race2+ ",Asian"
                                            }
                                            if(demographics?.raceBlackAmerican) {
                                                race2 = race2+ ",Black or African American"
                                            }
                                            if(demographics?.raceIndian) {
                                                race2 = race2 +",American Indian or Alaska Native"
                                            }
                                            if(demographics?.raceHawaiian) {
                                                race2 = race2 +",Native Hawaiian or other Pacific Islander"
                                            }
                                            if(demographics?.raceUnknown) {
                                                race2 = race2 +",Unknown"
                                            }

                                            if(race2 && race2.length() > 0) {
                                                race2 = race2.substring(1)
                                            }

                                            race(race2)
                                            ethnicity(demographics?.ethnicity)
                                        }//demographics

                                        if(c.caseCollectionType?.code == 'POSTM' || c.caseCollectionType?.code == 'OPO') {
                                            death_circumstances {
                                                def deathCircumstances = caseReportForm?.deathCircumstances
                                                manner_of_death(deathCircumstances?.mannerOfDeath) 
                                                hardy_scale(deathCircumstances?.hardyScale)

                                                if(deathCircumstances?.immediateCause=='Other') {
                                                    immediate_cause_of_death(deathCircumstances?.otherImmediate)
                                                } else {
                                                    immediate_cause_of_death(deathCircumstances?.immediateCause)
                                                }
                                            }//deathCircumstances
                                        }
                                    }//case_report
                                } else {
                                    case_status("not released")
                                } //case status
                            }//case
                        } else {
                            error("not authorized to view the case")
                        }
                    }//for
                }//GTEx-Donor-Variables
            } else {
                error {
                    error(message: 'Invalid caseId')
                }
            }
        }

        return xmlDocument.toString()
    }

    def gtexDonorVarsExport(caseId) {
        
        def list
        if(caseId) {
            list = CaseRecord.findByCaseId(caseId.toUpperCase())
        } else {
            list = CaseRecord.findAll()
        }

        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"

        def xmlDocument = builder.bind {
            mkp.xmlDeclaration()
            if(list) {
                def session = utilService.getSession()
                "GTEx_de_identified_donor_variables" {
                    for(c in list) {
                        if(c.bss.parentBss.code.matches(session.org.code) || session.org.code.matches("OBBR") || session.org.code.matches("VARI") || session.org.code.matches("BROAD")) {
                            "case"(id:c.caseId, collection_type:c.caseCollectionType?.name) {
                                if(true) {
                                    def hasBrain = false
                                    def sList = c.specimens
                                    def green = ['0011','0014']
                                    def yellow = ['0001','0002','0003','0004','0005','0006', '0008', '0009', '0007', '0010']
                                    def green_list=[]
                                    def green_btlist = []
                                    def green_hairlist = []
                                    def yellow_list=[]
                                    def pink_list=[]
                                    def aqua_list=[]
                                    def parentSpecimenBrainRemovalTime
                                    def parentSpecimenBrainRemovalDate = c.tissueRecoveryGtex?.firstTissueRemovedDate
                                    def parentbrainRemovedDateTime=mbbService.brainRemovedDateTime(c)
                                    
                                    for(s in sList) {
                                        String seq = s.gtexSequenceNum()
                                        if(seq == '0011' ) {
                                            hasBrain = true
                                            
                                        }

                                        if(green.contains(seq)) {
                                            if(s.tissueType.code=='BRAIN' && seq == '0011'){
                                                green_list.add(s)
                                                parentSpecimenBrainRemovalTime=s.brainTimeStartRemoval
                                               
                                            }
                                            if(s.tissueType.code=='HAIR'){
                                                green_hairlist.add(s)
                                            }
                                            if(!(s.tissueType.code=='BRAIN') && seq.contains('0011')){
                                                // println "other brain tissues" +s
                                                green_btlist.add(s)
                                            }
                                            
                                        } else if(yellow.contains(seq)) {
                                            yellow_list.add(s)
                                        } else {
                                           
                                            if(s.fixative.code == 'DICE'){
                                                //println "adding to pink kit "+s.fixativeId
                                                pink_list.add(s)
                                            }else{
                                                //println "adding to aqua kit "+s.fixativeId
                                                aqua_list.add(s)
                                            }
                                            
                                        }
                                    }
                                    
                                    

                                    def deathType
                                    def deathDate

                                    def collectionDate = c.tissueRecoveryGtex?.collectionDate
                                    def startTime = c.tissueRecoveryGtex?.collectionStartTime
                                    def collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)

                                    def firstBloodDrawDate = c.tissueRecoveryGtex?.firstBloodDrawDate
                                    def firstBloodDrawTime = c.tissueRecoveryGtex?.firstBloodDrawTime
                                    def firstBloodDrawDateTime = ldaccService.calculateDateWithTime(firstBloodDrawDate,firstBloodDrawTime)

                                    def firstTissueRemovedDate = c.tissueRecoveryGtex?.firstTissueRemovedDate
                                    def firstTissueRemovedTime = c.tissueRecoveryGtex?.firstTissueRemovedTime
                                    def firstTissueRemovedDateTime = ldaccService.calculateDateWithTime(firstTissueRemovedDate,firstTissueRemovedTime)

                                    def chestIncisionTime = c.tissueRecoveryGtex?.chestIncisionTime
                                    def chestIncisionDateTime = ldaccService.getDateTimeComp(collectionStartTime,chestIncisionTime)
                                    def surgeryStartTime = c.tissueRecoveryGtex?.surgeryStartTime
                                    def surgeryStartDateTime=ldaccService.getDateTimeComp(collectionStartTime,surgeryStartTime)
                                    def cross_clamp_time = c.tissueRecoveryGtex?.crossClampTime
                                    def crossClampDateTime = ldaccService.getDateTimeComp(collectionStartTime, cross_clamp_time)

                                    def deathMap = getDeathMap(c)
                                    deathType = deathMap.get("deathType")
                                    deathDate = deathMap.get("deathDate")

                                    eligible_for_study(c.candidateRecord?.isEligible)     
                                    donor_eligibility {
                                        def donorEligibilityGtex=c?.candidateRecord?.donorEligibilityGtex
                                        eligibility {
                                            allowed_minimum_organ_type(donorEligibilityGtex?.allowedMinOrganType)
                                            age_in_valid_range(donorEligibilityGtex?.age)
                                            BMI_in_valid_range(donorEligibilityGtex?.BMI)
                                            //collect_in_24h_death(donorEligibilityGtex?.collectIn24hDeath)
                                            if(!c?.candidateRecord?.cdrVer ||(c?.candidateRecord?.cdrVer && ComputeMethods.compareCDRVersion(c?.candidateRecord?.cdrVer, '5.2') < 0)){
                                               
                                                collect_in_24h_death(donorEligibilityGtex?.collectIn24hDeath)
                                            }
                                            else{
                                                
                                                collect_in_8hrs_after_death(donorEligibilityGtex?.collectIn8afterDeath)
                                                collect_all_in_24hrs_after_death(donorEligibilityGtex?.collectAllIn24afterDeath)
                                                
                                            }
                                            receive_blood_transfusion_in_48h(donorEligibilityGtex?.receiveTransfusionIn48h)
                                            diagnosed_metastatic_cancer(donorEligibilityGtex?.diagnosedMetastatis)
                                            received_chemotherapy_radiation_in_2y(donorEligibilityGtex?.receivedChemoIn2y)
                                            intravenous_drug_abuse_in_5y(donorEligibilityGtex?.drugAbuse)
                                            sex_with_someone_diagnosed_HIV(donorEligibilityGtex?.histOfSexWithHIV)
                                            exposed_to_HIV(donorEligibilityGtex?.contactHIV)
                                            history_of_reactive_assays(donorEligibilityGtex?.histOfReactiveAssays)
                                        }

                                        information {
                                            history_at_time_of_death {
                                                documented_sepsis(donorEligibilityGtex?.documentedSepsis)
                                                pneumonia(donorEligibilityGtex?.pneumonia)
                                                open_wounds(donorEligibilityGtex?.openWounds)
                                                high_unexplained_fever(donorEligibilityGtex?.highUnexplainedFever)
                                                positive_blood_cultures(donorEligibilityGtex?.positiveBloodCultures)
                                                abnormal_WBC(donorEligibilityGtex?.abnormalWBC)
                                                infected_lines(donorEligibilityGtex?.infectedLines)
                                                fungal_infections(donorEligibilityGtex?.fungalInfections)
                                                ascities(donorEligibilityGtex?.ascities)
                                                cellulites(donorEligibilityGtex?.cellulites)
                                            }

                                            blood_donation {
                                                past_blood_donations_denied(donorEligibilityGtex?.pastBloodDonations)
                                                blood_donation_denial_reason(donorEligibilityGtex?.bloodDonDenialReason)
                                            }

                                            received_blood_transfusion_in_another_country(donorEligibilityGtex?.bloodTransfusion)
                                            tissue_transplant {
                                                received_tissue_organ_transplant(donorEligibilityGtex?.humAnimTissueTransplant)
                                                tissue_transplant_comments(donorEligibilityGtex?.tissueTransplantComments)
                                            }

                                            recent_smallpox_vac(donorEligibilityGtex?.recentSmallpoxVac)
                                            contact_with_smallpox(donorEligibilityGtex?.contactWithSmallpox)
                                            dialysis_treatment(donorEligibilityGtex?.dialysisTreatment)
                                            current_diagnosis_of_cancer(donorEligibilityGtex?.currentCancerDiag)
                                            cancer_diagnosis_5y(donorEligibilityGtex?.cancerDiagPrec5yrs)
                                            TB_history(donorEligibilityGtex?.tBHistory)
                                            active_meningitis(donorEligibilityGtex?.activeMeningitis)
                                            active_encephalitis(donorEligibilityGtex?.activeEncephalitis)
                                            long_term_steroid_use(donorEligibilityGtex?.longtermSteroidUse)
                                            osteomyelitis(donorEligibilityGtex?.osteomyelitis)
                                            unexplained_seizures(donorEligibilityGtex?.unexplSeizures)
                                            unexplained_weakness(donorEligibilityGtex?.unexplWkness )
                                            exposure_to_toxics(donorEligibilityGtex?.exposureToToxics)
                                            no_physical_activity_4_weeks(donorEligibilityGtex?.noPhysicalActivity)
                                            resided_on_northern_European_military_base(donorEligibilityGtex?.residedOnMilitBase)
                                            bite_from_animal_with_rabies(donorEligibilityGtex?.biteFromAnimal)
                                            heroin_use(donorEligibilityGtex?.heroinUse)
                                            cocaine_use_in_5y(donorEligibilityGtex?.cocaineUse)
                                            men_sex_with_men(donorEligibilityGtex?.menWithMen)
                                            drugs_for_non_medical_use_in_5y(donorEligibilityGtex?.drugUseForNonMed)
                                            hemophilia(donorEligibilityGtex?.hemophilia)
                                            sex_for_money_or_drugs(donorEligibilityGtex?.sexForMoneyDrugs)

                                            sexual_activity_with_another_person {
                                                men_sex_with_men(donorEligibilityGtex?.menSexWithMen)
                                                drugs_for_non_medical_use_in_5y(donorEligibilityGtex?.drugsForNonMed5yrs)
                                                hemophilia(donorEligibilityGtex?.hemophiliaTreat)
                                                sex_for_money_or_drugs(donorEligibilityGtex?.sexForDrugsOrMoney)
                                            }

                                            not_able_to_be_tested_for_HIV(donorEligibilityGtex?.notTestedForHiv)
                                            other_evidence_of_HIV {
                                                unexplained_weight_loss(donorEligibilityGtex?.unexplWtLoss)
                                                night_sweats(donorEligibilityGtex?.nightSweats)
                                                spots_on_skin(donorEligibilityGtex?.spotsOnSkin)
                                                unexplained_lymphadenopathy(donorEligibilityGtex?.unexplLymphad)
                                                unexplained_temperature(donorEligibilityGtex?.unexplTemp)
                                                unexplained_cough(donorEligibilityGtex?.unexplCough)
                                                opportunistic_infections(donorEligibilityGtex?.oppInfections)
                                                sexually_transmitted_diseases(donorEligibilityGtex?.sexTansDis)
                                                signs_of_drug_abuse(donorEligibilityGtex?.signsOfDrugAbuse)
                                            }

                                            diagnosis_of_SARS(donorEligibilityGtex?.diagOfSars)
                                            history_of_WNV(donorEligibilityGtex?.histOfWestNile)
                                            history_of_contact_with_WNV(donorEligibilityGtex?.westNileContact)
                                            unexplained_weight_loss(donorEligibilityGtex?.unexplWeighttLoss)
                                            in_detention_center_72h(donorEligibilityGtex?.timeInDetCenter)
                                            tattoos_done_in_12m(donorEligibilityGtex?.tatttoos)
                                            received_human_growth_hormone(donorEligibilityGtex?.growthHarmone)
                                            prescription_pill_abuse(donorEligibilityGtex?.prescDrugAbuse)
                                            intravenous_drug_abuse_in_5y(donorEligibilityGtex?.intravenDrugAbuse)
                                            syphilis_infection_in_12m(donorEligibilityGtex?.syphilisTreat)
                                            gonorrhea_infection_in_12m(donorEligibilityGtex?.gonorrheaTreat)

                                            physical_contact_with_someone {
                                                hepatitis_B(donorEligibilityGtex?.hepatitisB)
                                                hepatitis_C(donorEligibilityGtex?.hepatitisC)
                                                HIV(donorEligibilityGtex?.hiv)
                                            }

                                            in_UK_3m_1980_1996(donorEligibilityGtex?.timeInUk)
                                            non_professional_piercing(donorEligibilityGtex?.nonProfpiercing)
                                            non_professional_tattoos(donorEligibilityGtex?.nonProfTattoos)
                                            resident_of_state_run_group_home(donorEligibilityGtex?.stateRunHome)
                                            in_Europe_5y_since_1980(donorEligibilityGtex?.timeInEurope)

                                            autoimmune_or_degenerative_neurological_disease {
                                                systemic_lupus(donorEligibilityGtex?.systLupus)
                                                sarcoidosis(donorEligibilityGtex?.sarcoidosis)
                                                scleroderma(donorEligibilityGtex?.scleroderma)
                                                reyes_syndrome(donorEligibilityGtex?.reyesSynd)
                                                rheumatoid_arthritis(donorEligibilityGtex?.rheumArthritis)
                                                heart_disease(donorEligibilityGtex?.heartDis)
                                                alzheimers(donorEligibilityGtex?.alzheimers)
                                                dementia_with_unknown_cause(donorEligibilityGtex?.dementia)
                                                multiple_sclerosis(donorEligibilityGtex?.multiSclero)
                                                amyotropic_lateral_sclerosis(donorEligibilityGtex?.lateralSclero)
                                                creutzfeldt_jakob_relatives(donorEligibilityGtex?.creutzfeldtJakob)
                                            }//history_of_disease                                        
                                            general_comments(donorEligibilityGtex?.endComments)
                                        }//information
                                    }//CRFInclExclGTEX

                                    tissue_recovery {
                                        def cRFTRFGTEX=c?.tissueRecoveryGtex
                                        def p_start = ldaccService.calculateInterval(deathDate,collectionStartTime)
                                        def organsDonated=[]
                                        //Liver, Lungs, Kidneys
                                        if(cRFTRFGTEX?.donatedLiver){
                                            organsDonated.add('Liver')
                                        }
                                        if(cRFTRFGTEX?.donatedLung){
                                            organsDonated.add('Lung')
                                        }
                                        if(cRFTRFGTEX?.donatedHeart){
                                            organsDonated.add('Heart')
                                        }
                                        if(cRFTRFGTEX?.donatedKidney){
                                            organsDonated.add('Kidney')
                                        }
                                        if(cRFTRFGTEX?.donatedPancreas){
                                            organsDonated.add('Pancreas')
                                        }
                                        if(cRFTRFGTEX?.donatedOrganOther){
                                            organsDonated.add(cRFTRFGTEX.donatedOrganOther)
                                        }
                                       
                                        "interval_between_${deathType}_and_GTEx_procedure_start"(p_start[0], "de-identified":"true", "millisecond":p_start[1])
                                        if(c.caseCollectionType?.code == 'POSTM' || c.caseCollectionType?.code == 'OPO') {
                                            def incision = ldaccService.calculateInterval(deathDate,chestIncisionDateTime)
                                            "interval_between_${deathType}_and_chest_incision"(incision[0], "de-identified":"true", "millisecond":incision[1])
                                        }

                                        if(c.caseCollectionType?.code == 'OPO') {
                                            def cc = ldaccService.calculateInterval(deathDate,crossClampDateTime)
                                            "interval_between_${deathType}_and_cross_clamp"(cc[0],"de-identified":"true", "millisecond":cc[1])
                                            //organs_tissues_donated(cRFTRFGTEX?.organsDonated)
                                            organs_tissues_donated(organsDonated.join(", "))
                                                
                                        }

                                        if(c.caseCollectionType?.code == 'SURGI') {
                                            amputation_type(cRFTRFGTEX?.amputationType)
                                            def cs = ldaccService.calculateInterval(deathDate,surgeryStartDateTime)
                                            "interval_between_${deathType}_and_surgery_start"(cs[0],"de-identified":"true", "millisecond":cs[1])
                                        }

                                        if(cRFTRFGTEX?.coreBodyTemp == -1000){
                                            core_body_temperature()
                                        } else {
                                            core_body_temperature(cRFTRFGTEX?.coreBodyTemp)
                                        }

                                        temperature_scale(cRFTRFGTEX?.coreBodyTempScale)
                                        temperature_obtained_via(cRFTRFGTEX?.coreBodyTempLoc)
                                        sops{
                                            sop("code":"OP-0001", "ver":cRFTRFGTEX?.op0001?.sopVersion) 
                                            sop("code":"PR-0004", "ver":cRFTRFGTEX?.pr0004?.sopVersion)
                                            sop("code":"PM-0003", "ver":cRFTRFGTEX?.pm0003?.sopVersion)
                                        }
                                        specimens {
                                            if(c.caseCollectionType?.code == 'POSTM' || c.caseCollectionType?.code == 'OPO') {
                                                green_kit {
                                                    if(hasBrain) {
                                                        vent_less_24_hrs(cRFTRFGTEX?.ventLess24hrs)
                                                    }

                                                    for(s in green_list) {
                                                        specimen {
                                                            specimen_id(s.specimenId)

                                                            def brainTimeStartRemovalInterval
                                                            def brainTimeStartRemovalIntervalM
                                                            def brainTimeEndAliquotInterval
                                                            def brainTimeEndAliquotIntervalM
                                                            def brainTimeIceInterval
                                                            def brainTimeIceIntervalM

                                                            brainTimeStartRemovalInterval =ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime, s.brainTimeStartRemoval)[0]
                                                            brainTimeStartRemovalIntervalM =ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,s.brainTimeStartRemoval)[1]

                                                            brainTimeEndAliquotInterval =ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,s.brainTimeEndAliquot)[0]
                                                            brainTimeEndAliquotIntervalM =ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,s.brainTimeEndAliquot)[1]

                                                            def brainIceDateTime = ldaccService.getDateTimeComp(firstTissueRemovedDateTime, s.brainTimeIce)
                                                            brainTimeIceInterval = ldaccService.calculateInterval(deathDate, brainIceDateTime)[0]
                                                            brainTimeIceIntervalM = ldaccService.calculateInterval(deathDate,brainIceDateTime)[1]

                                                            String seq = s.gtexSequenceNum()
                                                            if(seq == '0011') {
                                                                tissue_type(s.tissueType?.name)
                                                                "interval_between_${deathType}_and_brain_start_remove"(brainTimeStartRemovalInterval, millisecond:brainTimeStartRemovalIntervalM, "de-identified":"true")
                                                                "interval_between_${deathType}_and_brain_end_aliquot"(brainTimeEndAliquotInterval, millisecond:brainTimeEndAliquotIntervalM,"de-identified":"true")
                                                                "interval_between_${deathType}_and_brain_put_on_ice"(brainTimeIceInterval, millisecond:brainTimeIceIntervalM,"de-identified":"true")
                                                            }

                                                            if(seq == '0014') {
                                                                tissue_type(s.tissueType?.name)
                                                            }
                                                            prosector_comments(s.prosectorComments)
                                                        }
                                                    }
                                                    
                                                    // start of brain sub tissues
                                                    /**
                                                    so for each element we need the following tags:
                                                    Brain_aliquot_id        (sample ID)
                                                    collaborator_id
                                                    collaborator_id_2
                                                    mass, with an attribute of units=mg
                                                    interval_between_brain_start_removal_and_brain_disection_time
                                                    interval_between_actual_death_time_and_brain_disection_time
                                                    interval_between_presumed_death_time_and_brain_disection_time
                                                    brain_tissue_location :    (Hippocampus, Substantia nigra, Anterior cingulate cortex (BA24), Amygdala, Caudate (basal ganglia), Nucleus accumbens (basal ganglia), Putamen (basal ganglia), Hypothalamus

                                                    
                                                     **/
                                                    green_sub_tissues_kit {
                                                        def btinstance
                                                        for(sbt in green_btlist) {
                                                            specimen {
                                                                specimen_id(sbt.specimenId)
                                                                
                                                                btinstance=BrainTissue.findBySpecimenRecord(sbt)
                                                                Brain_aliquot_id(btinstance.sampleId)
                                                                collaborator_id(btinstance.collSampleId)
                                                                collaborator_id_2(btinstance.collSampleId2)
                                                                mass("units_in_mg":btinstance.mass)
                                                                
                                                                // now define the time for brain removal
                                                                //a: death to dissection
                                                                def deathToDissectionInterval=ldaccService.calculateInterval(deathDate,btinstance.collectionDate, btinstance.collectionTimeStr)[0]
                                                                def deathToDissectionIntervalM=ldaccService.calculateInterval(deathDate,btinstance.collectionDate, btinstance.collectionTimeStr)[1]
                                                                
                                                                //b: death to removal of parent brain
                                                                def brainTimeStartRemovalInterval =ldaccService.calculateInterval(deathDate,parentSpecimenBrainRemovalDate,parentSpecimenBrainRemovalTime)[0]
                                                                def brainTimeStartRemovalIntervalM =ldaccService.calculateInterval(deathDate,parentSpecimenBrainRemovalDate,parentSpecimenBrainRemovalTime)[1]
                                                                                                                                
                                                                // c: removal to dissection                                                                       
                                                                def removalToDissectionInterval = ldaccService.calculateInterval(parentbrainRemovedDateTime, btinstance.collectionDate,btinstance.collectionTimeStr)[0]
                                                                def removalToDissectionTimeM=ldaccService.calculateInterval(parentbrainRemovedDateTime, btinstance.collectionDate,btinstance.collectionTimeStr)[1]
                                                                                                                                
                                                                "interval_between_brain_start_removal_and_brain_disection_time"(removalToDissectionInterval, millisecond:removalToDissectionTimeM, "de-identified":"true")
                                                                "interval_between_${deathType}_and_brain_disection_time"(deathToDissectionInterval, millisecond:deathToDissectionIntervalM, "de-identified":"true")
                                                               
                                                                brain_tissue_location(btinstance.tissueType)
                                                               
                                                                //prosector_comments(sbt.prosectorComments)
                                                            }
                                                        }
                                                    }
                                                    // end of green brains sub tissues
                                                    
                                                    
                                                    // start of hair
                                                    
                                                    for(sh in green_hairlist) {
                                                        specimen {
                                                            specimen_id(sh.specimenId)

                                                            String seq = sh.gtexSequenceNum()
                                                            if(seq == '0014') {
                                                                tissue_type(sh.tissueType?.name)
                                                            }

                                                            prosector_comments(sh.prosectorComments)
                                                        }
                                                    }
                                                   
                                                    // end of hair
                                                
                                                }//end green_kit
                                            }

                                            yellow_kit {
                                                for(s in yellow_list) {
                                                    specimen {
                                                        specimen_id(s.specimenId)
                                                        def bloodTimeDrawInterval
                                                        def bloodTimeDrawIntervalM
                                                        def bloodTimeDrawInvertedInterval
                                                        def bloodTimeDrawInvertedIntervalM
                                                        def skinTimeIntoMediumInterval
                                                        def skinTimeIntoMediumIntervalM

                                                        bloodTimeDrawInterval = ldaccService.calculateInterval(deathDate,firstBloodDrawDateTime,s.bloodTimeDraw)[0]
                                                        bloodTimeDrawIntervalM = ldaccService.calculateInterval(deathDate,firstBloodDrawDateTime,s.bloodTimeDraw)[1]

                                                        bloodTimeDrawInvertedInterval = ldaccService.calculateInterval(deathDate,firstBloodDrawDateTime,s.bloodTimeDrawInverted)[0]
                                                        bloodTimeDrawInvertedIntervalM = ldaccService.calculateInterval(deathDate,firstBloodDrawDateTime,s.bloodTimeDrawInverted)[1]

                                                        def skinIntoMediumDateTime = ldaccService.getDateTimeComp(firstTissueRemovedDateTime, s.skinTimeIntoMedium)
                                                        skinTimeIntoMediumInterval = ldaccService.calculateInterval(deathDate,skinIntoMediumDateTime)[0]
                                                        skinTimeIntoMediumIntervalM= ldaccService.calculateInterval(deathDate,skinIntoMediumDateTime)[1]
                                                        tissue_type(s.tissueType?.name)

                                                        String seq = s.gtexSequenceNum()
                                                        if(seq == '0008') {
                                                            fixative(s.fixative?.name)
                                                            "interval_between_${deathType}_and_skin_into_medium"(skinTimeIntoMediumInterval,millisecond:skinTimeIntoMediumIntervalM,"de-identified":"true")
                                                        } else {
                                                            fixative(s.fixative?.name)
                                                            "interval_between_${deathType}_and_blood_draw"(bloodTimeDrawInterval, millisecond:bloodTimeDrawIntervalM,"de-identified":"true")
                                                            "interval_between_${deathType}_and_blood_draw_inverted"(bloodTimeDrawInvertedInterval, millisecond:bloodTimeDrawInvertedIntervalM,"de-identified":"true")
                                                        }

                                                        prosector_comments(s.prosectorComments)
                                                    }
                                                }
                                            }//yellow_kit

                                            aqua_kit {
                                                for(s in aqua_list) {
                                                    specimen {
                                                        specimen_id(s.specimenId)
                                                        def aliquotTimeRemovedInterval
                                                        def aliquotTimeRemovedIntervalM
                                                        def aliquotTimeStabilizedInterval
                                                        def aliquotTimeStabilizedIntervalM
                                                        def aliquotTimeFixedInterval
                                                        def aliquotTimeFixedIntervalM

                                                        aliquotTimeRemovedInterval =ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,s.aliquotTimeRemoved)[0]
                                                        aliquotTimeRemovedIntervalM =ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,s.aliquotTimeRemoved)[1]

                                                        def aliquotFixedDateTime = ldaccService.getDateTimeAfter(firstTissueRemovedDateTime, s.aliquotTimeFixed)
                                                        def aliquortStabilizedDateTime=ldaccService.getDateTimeAfter4To28(aliquotFixedDateTime,s.aliquotTimeStabilized)
                                                        aliquotTimeStabilizedInterval = ldaccService.calculateInterval(deathDate, aliquortStabilizedDateTime)[0]
                                                        aliquotTimeStabilizedIntervalM = ldaccService.calculateInterval(deathDate,aliquortStabilizedDateTime)[1]

                                                        aliquotTimeFixedInterval = ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,s.aliquotTimeFixed)[0]
                                                        aliquotTimeFixedIntervalM = ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,s.aliquotTimeFixed)[1]
                                                        tissue_type(s.tissueType?.name)
                                                        fixative(s.fixative?.name)

                                                        def loc = s.tissueLocation?.name
                                                        if(loc && loc == 'Other, specify') {
                                                            tissue_location(s.otherTissueLocation)
                                                        } else {
                                                            tissue_location(s.tissueLocation?.name)
                                                        }

                                                        "interval_between_${deathType}_and_aliquot_removed"(aliquotTimeRemovedInterval, millisecond:aliquotTimeRemovedIntervalM, "de-identified":"true")
                                                        "interval_between_${deathType}_and_aliquot_put_in_fixative"(aliquotTimeFixedInterval, millisecond:aliquotTimeFixedIntervalM,"de-identified":"true")
                                                        "interval_between_${deathType}_and_aliquot_placed_in_stabilizer"(aliquotTimeStabilizedInterval, millisecond:aliquotTimeStabilizedIntervalM,"de-identified":"true")
                                                        size_diff_than_SOP(s.sizeDiffThanSOP)
                                                        prosector_comments(s.prosectorComments)
                                                    }//specimen
                                                }//for aqua_list
                                            }//aqua_kit
                                            
                                            pink_kit {
                                                for(p in pink_list) {
                                                    specimen {
                                                        specimen_id(p.specimenId)
                                                        def aliquotTimeRemovedInterval
                                                        def aliquotTimeRemovedIntervalM
                                                        def aliquotTimeStabilizedInterval
                                                        def aliquotTimeStabilizedIntervalM
                                                        def aliquotTimeFixedInterval
                                                        def aliquotTimeFixedIntervalM

                                                        aliquotTimeRemovedInterval =ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,p.aliquotTimeRemoved)[0]
                                                        aliquotTimeRemovedIntervalM =ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,p.aliquotTimeRemoved)[1]

                                                        def aliquotFixedDateTime = ldaccService.getDateTimeAfter(firstTissueRemovedDateTime, p.aliquotTimeFixed)
                                                        def aliquortStabilizedDateTime=ldaccService.getDateTimeAfter4To28(aliquotFixedDateTime,p.aliquotTimeStabilized)
                                                        aliquotTimeStabilizedInterval = ldaccService.calculateInterval(deathDate, aliquortStabilizedDateTime)[0]
                                                        aliquotTimeStabilizedIntervalM = ldaccService.calculateInterval(deathDate,aliquortStabilizedDateTime)[1]

                                                        aliquotTimeFixedInterval = ldaccService.calculateInterval(deathDate, firstTissueRemovedDateTime,p.aliquotTimeFixed)[0]
                                                        aliquotTimeFixedIntervalM = ldaccService.calculateInterval(deathDate,firstTissueRemovedDateTime,p.aliquotTimeFixed)[1]
                                                        tissue_type(p.tissueType?.name)
                                                        fixative(p.fixative?.name)

                                                        def loc = p.tissueLocation?.name
                                                        if(loc && loc == 'Other, specify') {
                                                            tissue_location(p.otherTissueLocation)
                                                        } else {
                                                            tissue_location(p.tissueLocation?.name)
                                                        }

                                                        "interval_between_${deathType}_and_aliquot_removed"(aliquotTimeRemovedInterval, millisecond:aliquotTimeRemovedIntervalM, "de-identified":"true")
                                                        "interval_between_${deathType}_and_aliquot_put_in_fixative"(aliquotTimeFixedInterval, millisecond:aliquotTimeFixedIntervalM,"de-identified":"true")
                                                        "interval_between_${deathType}_and_aliquot_placed_in_stabilizer"(aliquotTimeStabilizedInterval, millisecond:aliquotTimeStabilizedIntervalM,"de-identified":"true")
                                                        adjacent_to_paxgene_tissue (p.adjacentToPaxTissue)
                                                        size_diff_than_SOP(p.sizeDiffThanSOP)
                                                        prosector_comments(p.prosectorComments)
                                                    }//specimen
                                                }//for pink_list
                                            }//pink_kit
                                            
                                                                                        
                                        }//specimens
                                    }//CRFTRFGTEX

                                    case_report {
                                        def caseReportForm=c.caseReportForm
                                        demographics {
                                            def demographics=caseReportForm?.demographics
                                            if(demographics?.gender?.toString() == 'Other') {
                                                gender(demographics?.otherGender)
                                            } else {
                                                gender(demographics?.gender)
                                            }

                                            def birthDate = demographics?.dateOfBirth
                                            def age2 =ldaccService.calculateAge(deathDate, birthDate)

                                            if(age2) {
                                                if(age2 > 89) {
                                                    age('90+',"de-identified":"true")
                                                } else {
                                                    age(age2,"de-identified":"true")
                                                }
                                            } else {
                                                age()
                                            }

                                            height(demographics?.height, unit:"in")
                                            weight(demographics?.weight, unit:"lb")
                                            BMI(demographics?.BMI)

                                            String race2 = ""
                                            if(demographics?.raceWhite) {
                                                race2 = race2+ ",White"
                                            }
                                            if(demographics?.raceAsian) {
                                                race2 = race2+ ",Asian"
                                            }
                                            if(demographics?.raceBlackAmerican) {
                                                race2 = race2+ ",Black or African American"
                                            }
                                            if(demographics?.raceIndian) {
                                                race2 = race2 +",American Indian or Alaska Native"
                                            }
                                            if(demographics?.raceHawaiian) {
                                                race2 = race2 +",Native Hawaiian or other Pacific Islander"
                                            }
                                            if(demographics?.raceUnknown) {
                                                race2 = race2 +",Unknown"
                                            }
                                            if(race2 && race2.length() > 0) {
                                                race2 = race2.substring(1)
                                            }

                                            race(race2)
                                            ethnicity(demographics?.ethnicity)
                                        }//demographics

                                        medical_history {
                                            def medicalHistory=caseReportForm?.medicalHistory
                                            primary_history_source(medicalHistory?.source)
                                            history_of_non_metastatic_cancer(medicalHistory?.nonMetastaticCancer)

                                            cancer_histories {
                                                if(medicalHistory?.nonMetastaticCancer?.toString() =='Yes'){
                                                    def ch_list = medicalHistory?.cancerHistories
                                                    for(ch in ch_list) {
                                                        cancer_history {
                                                            primary_tumor_site(ch.primaryTumorSite)
                                                            primary_tumor_site_icd_codes(ch.primaryTumorSiteCvocab?.icdCd ?:'')
                                                            Date firstd = null
                                                            if(ch.monthYearOfFirstDiagnosis && (new SimpleDateFormat('MM/yyyy')).format(ch.monthYearOfFirstDiagnosis) != '01/1900') {
                                                                firstd = ch.monthYearOfFirstDiagnosis
                                                            }

                                                        "years_between_${deathType}_and_first_diagnosis"(ldaccService.calculateDecimalYearFromMonth(deathDate,firstd ),"de-identified":"true")
                                                            history_of_any_treatments(ch.treatments)

                                                            Date lastd = null
                                                            if(ch.monthYearOfLastTreatment && (new SimpleDateFormat('MM/yyyy')).format(ch.monthYearOfLastTreatment) != '01/1900') {
                                                                lastd = ch.monthYearOfLastTreatment
                                                            }

                                                        "years_between_${deathType}_and_last_radiation_or_chemotherapy"(ldaccService.calculateDecimalYearFromMonth(deathDate,lastd ),"de-identified":"true")
                                                            medical_record(ch.medicalRecordExist)
                                                            history_source(ch.source ?:'')
                                                        }
                                                    }//for ch_list
                                                }
                                            }//cancer_history

                                            general_medical_histories {
                                                def g_list = medicalHistory?.generalMedicalHistories
                                                for(g in g_list) {
                                                    general_medical_history {
                                                        if(g.medicalConditionCvocab?.cVocabUserSelection){
                                                        
                                                            medical_condition(g.medicalConditionCvocab?.cVocabUserSelection)
                                                        }
                                                        else{
                                                            medical_condition(g.medicalCondition)
                                                        }
                                                        icd_codes(g.medicalConditionCvocab?.icdCd ?:'')
                                                        medical_condition_exists(g.chooseOption)
                                                        def newDate = null
                                                        if(g.yearOfOnset && g.yearOfOnset !='1900') {
                                                            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy")
                                                            String dateStr = "01/01/"+ g.yearOfOnset
                                                            newDate = df.parse(dateStr)
                                                        }

                                                        "years_between_${deathType}_and_onset"(ldaccService.calculateDecimalYearFromYear(deathDate,newDate), "de-identified":"true")
                                                        history_of_treatment(g.treatment)
                                                        medical_record_documentation(g.medicalRecord)
                                                        history_source(g.source ?:'')
                                                    }//general_medical_history
                                                }//for g_list
                                            }//general_medical_histories
                                        }//medicalHistory

                                        current_medications {
                                            def cm_list= caseReportForm?.concomitantMedications
                                            for(cm in cm_list) {
                                                medication {
                                                    if(cm.medicationNameCvocab?.cVocabUserSelection){
                                                        medication_name(cm.medicationNameCvocab?.cVocabUserSelection)
                                                    }
                                                    else{
                                                        medication_name(cm.medicationName)
                                                    }
                                                    dosage(cm.dosage)

                                                    if(!cm.amount || cm.amount== -1) {
                                                        amount()
                                                    } else {
                                                        amount(cm.amount)
                                                    }

                                                    dosage_form_unit(cm.dosageUnit)
                                                    frequncy(cm.frequency)
                                                    route(cm.route)

                                                    Date lastd = null
                                                    if(cm.dateofLastAdministration && (new SimpleDateFormat('MM/dd/yyyy')).format(cm.dateofLastAdministration) != '01/01/1900') {
                                                        lastd=cm.dateofLastAdministration
                                                    }

                                                    "years_between_${deathType}_and_last_administration"(ldaccService.calculateDecimalYear(deathDate, lastd),"de-identified":"true")
                                                    history_source(cm.source ?:'')
                                                }//medication
                                            }//for cm_list
                                        }//current_medications

                                        if(c.caseCollectionType?.code == 'POSTM' || c.caseCollectionType?.code == 'OPO' ) {
                                            death_circumstances {
                                                def deathCircumstances = caseReportForm?.deathCircumstances
                                                death_certificate_available(deathCircumstances?.deathCertificateAvailable)
                                                death_certificate_cause_of_death(deathCircumstances?.deathCertCauseVocab?.cVocabUserSelection ?:deathCircumstances?.deathCertificateCause)
                                                death_certificate_cause_of_death_icd_codes(deathCircumstances?.deathCertCauseVocab?.icdCd ?:'')
                                                "interval_between_${deathType}_and_pronounced_dead"(ldaccService.calculateHourMi(deathDate, deathCircumstances?.dateTimePronouncedDead),"de-identified":"true")

                                                if(c.caseReportForm?.deathCircumstances?.dateTimeActualDeath){
                                                    witnessed_death("Yes")
                                                } else {
                                                    witnessed_death("No")
                                                    "interval_between_${deathType}_and_last_seen_alive"(ldaccService.calculateHourMi(deathCircumstances?.dateTimeLastSeenAlive, deathDate), "de-identified":"true")
                                                }

                                                if(deathCircumstances?.placeOfDeath=='Other') {
                                                    place_of_death(deathCircumstances?.otherPlaceOfDeath)
                                                } else {
                                                    place_of_death(deathCircumstances?.placeOfDeath)
                                                }

                                                person_who_determined_date_time_of_death(deathCircumstances?.personDeterminedDeath)
                                                manner_of_death(deathCircumstances?.mannerOfDeath)
                                                hardy_scale(deathCircumstances?.hardyScale)
                                                autopsy_performed_by_coroner_or_ME(deathCircumstances?.autopsyPerformed)
                                                donor_on_a_ventilator_immediately_prior_to_death(deathCircumstances?.onVentilator)
                                                time_interval_on_ventilator(deathCircumstances?.ventilatorDuration, unit:'hour')
                                                    
                                                if(deathCircumstances?.immediateCause=='Other') {
                                                    immediate_cause_of_death(deathCircumstances?.otherImmediate)
                                                } else {
                                                    if(deathCircumstances?.immCodCvocab?.cVocabUserSelection){
                                                        immediate_cause_of_death(deathCircumstances?.immCodCvocab?.cVocabUserSelection)
                                                    }
                                                    else{
                                                        immediate_cause_of_death(deathCircumstances?.immediateCause)
                                                    }
                                                }
                                                immediate_cause_of_death_icd_codes(deathCircumstances?.immCodCvocab?.icdCd ?:'')
                                                interval_of_onset_to_death_for_immediate_cause(deathCircumstances?.immediateInterval, unit:"hour")
                                                if(deathCircumstances?.firstCause=='Other') {
                                                    first_underlying_cause_of_death(deathCircumstances?.otherFirstCause)
                                                } else {
                                                    if(deathCircumstances?.firstCodCvocab?.cVocabUserSelection){
                                                        first_underlying_cause_of_death(deathCircumstances?.firstCodCvocab?.cVocabUserSelection)
                                                    }
                                                    else{
                                                        first_underlying_cause_of_death(deathCircumstances?.firstCause)
                                                    }
                                                }
                                                first_underlying_cause_of_death_icd_codes(deathCircumstances?.firstCodCvocab?.icdCd ?:'')
                                                
                                                interval_of_onset_to_death_for_first_underlying_cause(deathCircumstances?.firstCauseInterval, unit:"hour")
                                                if(deathCircumstances?.lastCause=='Other') {
                                                    last_underlying_cause_of_death(deathCircumstances?.otherLastCause)
                                                } else {
                                                    if(deathCircumstances?.lastCodCvocab?.cVocabUserSelection){
                                                        last_underlying_cause_of_death(deathCircumstances?.lastCodCvocab?.cVocabUserSelection)
                                                    }
                                                    else{
                                                        last_underlying_cause_of_death(deathCircumstances?.lastCause)
                                                    }
                                                }
                                                last_underlying_cause_of_death_icd_codes(deathCircumstances?.lastCodCvocab?.icdCd ?:'')
                                                interval_of_onset_to_death_for_last_underlying_cause(deathCircumstances?.lastCauseInterval, unit:"hour")
                                                body_refrigerated(deathCircumstances?.wasRefrigerated)
                                                number_of_hours_in_refrigeration(deathCircumstances?.estimatedHours,unit:"hour")
                                            }//deathCircumstances
                                        } else {
                                            surgical_procedures {
                                                def surgicalProcedures = caseReportForm?.surgicalProcedures
                                                def sm_list = surgicalProcedures?.surgicalMedications
                                                pre_operative_medications {
                                                    duration_of_administration_of_pre_operative_medications_to_surgery(surgicalProcedures?.durationPreOpeMed, unit:"hr:min")
                                                    type_of_IV_sedation_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Medications' && sm.subCatgory =='intravenous (IV) sedation administered' ) {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    type_of_IV_opiate_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Medications' && sm.subCatgory =='IV Opiate administered' ) {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    type_of_IV_antiemetic_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Medications' && sm.subCatgory =='IV Antiemetic administered' ) {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    type_of_IV_antacid_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Medications' && sm.subCatgory =='IV Antacid administered' ) {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    type_of_other_IV_pre_operative_medications_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Medications' && !sm.subCatgory ) {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                                pre_operative_anesthesia {
                                                    duration_of_anesthesia_induction(surgicalProcedures?.durationAnesthesia, unit:"hr:min")
                                                    type_of_local_anesthesia_agents_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Anesthesia' && sm.subCatgory =='local anesthesia agents administered') {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    type_of_regional_anesthesia_agents_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Anesthesia' && sm.subCatgory =='regional (spinal/epidural) anesthesia agents  administered' ) {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    type_of_IV_anesthetic_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Anesthesia' && sm.subCatgory =='IV anesthetic administered' ) {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    type_of_IV_narcotic_opiate_anesthetic_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Anesthesia' && sm.subCatgory =='IV narcotic / opiate anesthetic administered' ) {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    type_of_IV_muscle_relaxant_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Anesthesia' && sm.subCatgory =='IV muscle relaxant administered') {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    type_of_inhalation_anesthetic_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Anesthesia' && sm.subCatgory =='Inhalation anesthetic administered' ) {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    type_of_other_anesthetics_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'Pre-Operative Anesthesia' && !sm.subCatgory ) {
                                                                if(sm.medicationName) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                                other_medications_administered_during_surgery {
                                                    type_of_local_anesthesia_agents_administered {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'during surgery' ) {
                                                                if(sm.medicationName && sm.subCatgory) {
                                                                    medication {
                                                                        type(sm.subCatgory)
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    any_other_medications {
                                                        for(sm in sm_list) {
                                                            if(sm.catgory == 'during surgery' ) {
                                                                if(sm.medicationName && !sm.subCatgory) {
                                                                    medication {
                                                                        medication_name(sm.medicationName)
                                                                        dosage(sm.dosage)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        serology_results {
                                            def serologyResult =caseReportForm?.serologyResult
                                            HIV_I_II_Ab(serologyResult?.HIV_I_II_Ab)
                                            HIV_I_II_Plus_O_Antibody(serologyResult?.HIV_I_II_Plus_O_antibody)
                                            HBsAg(serologyResult?.HBsAg)
                                            HBsAb(serologyResult?.HBsAb)
                                            HBcAb_Total(serologyResult?.HBcAb)
                                            HBcAb_IgM(serologyResult?.HBcAb_IgM)
                                            HCV_Ab(serologyResult?.HCV_Ab)
                                            EBV_IgG_Ab(serologyResult?.EBV_IgG_Ab)
                                            EBV_IgM_Ab(serologyResult?.EBV_IgM_Ab)
                                            RPR(serologyResult?.RPR)
                                            CMV_Total_Ab(serologyResult?.CMV_Total_Ab)
                                            HIV_1_NAT(serologyResult?.HIV_1_NAT)
                                            HCV_1_NAT(serologyResult?.HCV_1_NAT)
                                            PRR_VDRL(serologyResult?.PRR_VDRL)
                                        }//serlogy_result
                                    }//case_report

                                    if(c.prcReport != null && c.prcReport?.reviewDate != null) {
                                        PRC_summary {
                                            def specimenList = prcReportService.getPrcSpeciemenListByCase(c)
                                            specimens {
                                                for(s in specimenList ) {
                                                    specimen {
                                                        specimen_id(s.specimenRecord.specimenId)
                                                        tissue_type(s.specimenRecord.tissueType?.name)
                                                        autolysis(s.autolysis)
                                                        comments(s.comments)
                                                        inventory_status(s.inventoryStatus)
                                                    }
                                                }
                                            }
                                        }
                                    }                                    
                                } else {
                                    case_status("not released")
                                } //case status
                            }//case
                        } else {
                            error("not authorized to view the case")
                        }
                    }//for
            
                    dataQualityNotes {
                        def queryInstanceList
                        queryInstanceList = Query.findAllByCaseRecord(CaseRecord.findByCaseId(caseId))
                        def status = false
                        queryInstanceList.each {
                            if (it.queryStatus.equals(QueryStatus.findByCode("UNRESOLVED"))) {
                                status = true
                            } 
                        }
                        unresolvedQueries(status)
                    }
                }//GTEx-Donor-Variables
            } else {
                error {
                    error(message: 'Invalid caseId')
                }
            }
        }

        return xmlDocument.toString()
    }

    def bmsDonorVarsExport(caseId) {
        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"

        def xmlDocument = builder.bind {
            mkp.xmlDeclaration()
            def session = utilService.getSession()
            def c = CaseRecord.findByCaseId(caseId)
            if(c.bss.parentBss.code.matches(session.org.code) || session.org.code.matches("OBBR") || session.org.code.matches("VARI") || session.org.code.matches("BROAD")) {
                "BMS_de_identified_donor_variables" {
                    "case"(id:c.caseId, collection_type:c.caseCollectionType?.name, parent_case_id:c.parentCase.caseId) {
                        def deathMap = getDeathMap(c.parentCase)
                        def deathType = deathMap.get("deathType")
                        def deathDate = deathMap.get("deathDate")

                        tissue_recovery {
                            def trfGtex=c.parentCase?.tissueRecoveryGtex
                            def collectionDate = c.parentCase.tissueRecoveryGtex?.collectionDate
                            def startTime = c.parentCase.tissueRecoveryGtex?.collectionStartTime
                            def collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)
                            def p_start = ldaccService.calculateInterval(deathDate,collectionStartTime)

                            def trfBms=c.tissueRecoveryBms
                            def cardiatCessTime=bmsService.getCardiatCessTime(trfBms)
                            def ctime=ldaccService.calculateInterval(deathDate,cardiatCessTime)

                            def firstTissueRemovedDate = c.parentCase.tissueRecoveryGtex?.firstTissueRemovedDate
                            def firstTissueRemovedTime = c.parentCase.tissueRecoveryGtex?.firstTissueRemovedTime
                            def firstTissueRemovedDateTime = ldaccService.calculateDateWithTime(firstTissueRemovedDate,firstTissueRemovedTime)

                            tissue_recovery_collection_data {
                                "interval_between_${deathType}_and_GTEx_procedure_start"(p_start[0], "de-identified":"true", "millisecond":p_start[1])
                                "interval_between_${deathType}_and_cardiac_cessation_time"(ctime[0], "de-identified":"true", "millisecond":ctime[1])
                                "room_temperature_recorded"(trfBms.envTemperature)
                            }

                            specimens {
                                delay_timepoint_1_hour {
                                    def specimen1Map =new TreeMap(bmsService.getSpecimensFull(trfBms, '1'))
                                    def specimen1 = specimen1Map.values()
                                    specimen1.each {
                                        def specimenId = it.specimenId
                                        def tissue=it.tissue
                                        def tissueLoc = it.tissueLoc
                                        def fixative_name = it.fixative
                                        def protocol_name = it.protocol
                                        def timeRemovFromBody=it.timeRemovFromBody
                                        def time1 =ldaccService.calculateInterval(deathDate, timeRemovFromBody)
                                        def procTimeRemov=it.procTimeRemov
                                        def time2=ldaccService.calculateInterval(deathDate, procTimeRemov)
                                        def timeInFix=it.timeInFix
                                        def time3=ldaccService.calculateInterval(deathDate, timeInFix)
                                        def size=it.siz
                                        def tissue_cons=it.tissue_cons
                                        def comments=it.comments

                                        specimen {
                                            specimen_id(specimenId)
                                            tissue_type(tissue)
                                            tissue_location(tissueLoc)
                                            fixative(fixative_name)
                                            protocol(protocol_name)
                                            "interval_between_${deathType}_and_tissue_sample_removed_from_body"(time1[0], millisecond:time1[1], "de-identified":"true")
                                            "interval_between_${deathType}_and_aliquot_removed_from_tissue_sample"(time2[0], millisecond:time2[1], "de-identified":"true")
                                            "interval_between_${deathType}_and_aliquot_put_in_fixative"(time3[0], millisecond:time3[1], "de-identified":"true")
                                            size_diff_than_SOP(size)
                                            tissue_consistency(tissue_cons)
                                            prosector_comments(comments)
                                        }//specimen
                                    }

                                    report_easy_use_LN2(trfBms.easeOfUseLn21h)
                                    report_easy_use_dry_ice(trfBms.easeOfUseDice1h)
                                }//delay_timepoint_1_hour

                                delay_timepoint_4_hour {
                                    def specimen1Map =new TreeMap(bmsService.getSpecimensFull(trfBms, '4'))
                                    def specimen1 = specimen1Map.values()
                                    specimen1.each {
                                        def specimenId = it.specimenId
                                        def tissue=it.tissue
                                        def tissueLoc = it.tissueLoc
                                        def fixative_name = it.fixative
                                        def protocol_name = it.protocol
                                        def timeRemovFromBody=it.timeRemovFromBody
                                        def time1 =ldaccService.calculateInterval(deathDate, timeRemovFromBody)
                                        def procTimeRemov=it.procTimeRemov
                                        def time2=ldaccService.calculateInterval(deathDate, procTimeRemov)
                                        def timeInFix=it.timeInFix
                                        def time3=ldaccService.calculateInterval(deathDate, timeInFix)
                                        def size=it.siz
                                        def tissue_cons=it.tissue_cons
                                        def comments=it.comments

                                        specimen {
                                            specimen_id(specimenId)
                                            tissue_type(tissue)
                                            tissue_location(tissueLoc)
                                            fixative(fixative_name)
                                            protocol(protocol_name)
                                            "interval_between_${deathType}_and_tissue_sample_removed_from_body"(time1[0], millisecond:time1[1], "de-identified":"true")
                                            "interval_between_${deathType}_and_aliquot_removed_from_tissue_sample"(time2[0], millisecond:time2[1], "de-identified":"true")
                                            "interval_between_${deathType}_and_aliquot_put_in_fixative"(time3[0], millisecond:time3[1], "de-identified":"true")
                                            size_diff_than_SOP(size)
                                            tissue_consistency(tissue_cons)
                                            prosector_comments(comments)
                                        }//specimen
                                    }//each

                                    report_easy_use_LN2(trfBms.easeOfUseLn24h)
                                    report_easy_use_dry_ice(trfBms.easeOfUseDice4h)
                                }//delay_timepoint_4_hour

                                delay_timepoint_6_hour {
                                    def specimen1Map =new TreeMap(bmsService.getSpecimensFull(trfBms, '6'))
                                    def specimen1 = specimen1Map.values()
                                    specimen1.each {
                                        def specimenId = it.specimenId
                                        def tissue = it.tissue
                                        def tissueLoc = it.tissueLoc
                                        def fixative_name = it.fixative
                                        def protocol_name = it.protocol
                                        def timeRemovFromBody = it.timeRemovFromBody
                                        def time1 = ldaccService.calculateInterval(deathDate, timeRemovFromBody)
                                        def procTimeRemov = it.procTimeRemov
                                        def time2 = ldaccService.calculateInterval(deathDate, procTimeRemov)
                                        def timeInFix = it.timeInFix
                                        def time3 = ldaccService.calculateInterval(deathDate, timeInFix)
                                        def size = it.siz
                                        def tissue_cons = it.tissue_cons
                                        def comments = it.comments

                                        specimen {
                                            specimen_id(specimenId)
                                            tissue_type(tissue)
                                            tissue_location(tissueLoc)
                                            fixative(fixative_name)
                                            protocol(protocol_name)
                                            "interval_between_${deathType}_and_tissue_sample_removed_from_body"(time1[0], millisecond:time1[1], "de-identified":"true")
                                            "interval_between_${deathType}_and_aliquot_removed_from_tissue_sample"(time2[0], millisecond:time2[1], "de-identified":"true")
                                            "interval_between_${deathType}_and_aliquot_put_in_fixative"(time3[0], millisecond:time3[1], "de-identified":"true")
                                            size_diff_than_SOP(size)
                                            tissue_consistency(tissue_cons)
                                            prosector_comments(comments)
                                        }//specimen
                                    }//each

                                    report_easy_use_LN2(trfBms.easeOfUseLn26h)
                                    report_easy_use_dry_ice(trfBms.easeOfUseDice6h)
                                }//delay_timepoint_6_hour

                                delay_timepoint_15_hour {
                                    def specimen1Map =new TreeMap(bmsService.getSpecimensFull(trfBms, '15'))
                                    def specimen1 = specimen1Map.values()
                                    specimen1.each {
                                        def specimenId = it.specimenId
                                        def tissue = it.tissue
                                        def tissueLoc = it.tissueLoc
                                        def fixative_name = it.fixative
                                        def protocol_name = it.protocol
                                        def timeRemovFromBody = it.timeRemovFromBody
                                        def time1 = ldaccService.calculateInterval(deathDate, timeRemovFromBody)
                                        def procTimeRemov = it.procTimeRemov
                                        def time2 = ldaccService.calculateInterval(deathDate, procTimeRemov)
                                        def timeInFix = it.timeInFix
                                        def time3 = ldaccService.calculateInterval(deathDate, timeInFix)
                                        def size = it.siz
                                        def tissue_cons = it.tissue_cons
                                        def comments = it.comments

                                        specimen {
                                            specimen_id(specimenId)
                                            tissue_type(tissue)
                                            tissue_location(tissueLoc)
                                            fixative(fixative_name)
                                            protocol(protocol_name)
                                            "interval_between_${deathType}_and_tissue_sample_removed_from_body"(time1[0], millisecond:time1[1], "de-identified":"true")
                                            "interval_between_${deathType}_and_aliquot_removed_from_tissue_sample"(time2[0], millisecond:time2[1], "de-identified":"true")
                                            "interval_between_${deathType}_and_aliquot_put_in_fixative"(time3[0], millisecond:time3[1], "de-identified":"true")
                                            size_diff_than_SOP(size)
                                            tissue_consistency(tissue_cons)
                                            prosector_comments(comments)
                                        }//specimen
                                    }//each

                                    report_easy_use_LN2(trfBms.easeOfUseLn215h)
                                    report_easy_use_dry_ice(trfBms.easeOfUseDice15h)
                                }//delay_timepoint_6_hour
                            }//specimens

                            def dateStabilized=trfBms.dateStabilized
                            def timeStabilized = trfBms.timeStabilized
                            def dateTimeStabilized=getDateTime(dateStabilized, timeStabilized)
                            def time4=ldaccService.calculateInterval(deathDate, dateTimeStabilized)

                            def dateInDewarLn2=trfBms.dateInDewarLn2
                            def timeInDewarLn2=trfBms.timeInDewarLn2
                            def dateTimeDewarLn2=getDateTime(dateInDewarLn2, timeInDewarLn2)
                            def time5=ldaccService.calculateInterval(deathDate,dateTimeDewarLn2)

                            def dateInDewarDice=trfBms.dateInDewarDice
                            def timeInDewarDice=trfBms.timeInDewarDice
                            def dateTimeInDewarDice=getDateTime(dateInDewarDice, timeInDewarDice)
                            def time6=ldaccService.calculateInterval(deathDate,dateTimeInDewarDice)

                            additional_tissue_recovery_collection_data {
                                "interval_between_${deathType}_and_aliquot_placed_in_stabilizer"(time4[0], millisecond:time4[1],"de-identified":"true")
                                "interval_between_${deathType}_and_aliquot_held_in_dewar_for_LN2"(time5[0], millisecond:time5[1],"de-identified":"true")
                                "interval_between_${deathType}_and_aliquot_held_in_dewar_for_Dry_Ice"(time6[0], millisecond:time6[1],"de-identified":"true")
                                comments(trfBms.comments)
                            }
                        }//tissue_recovery

                        if(c.prcReport != null && c.prcReport?.reviewDate != null) {
                            PRC_summary {
                                def specimenList = prcReportService.getPrcSpeciemenListByCase(c)
                                specimens {
                                    for(s in specimenList ) {
                                        specimen {
                                            specimen_id(s.specimenRecord.specimenId)
                                            tissue_type(s.specimenRecord.tissueType?.name)
                                            comments(s.comments)
                                            inventory_status(s.inventoryStatus)
                                        }
                                    }
                                }
                            }
                        }
                    }//case
                }
            } else {
                error {
                    error(message: 'not authorized to view the case')
                }
            }
        }
    }

    def getPrcReport(caseId) {
        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"

        def xmlDocument = builder.bind {
            mkp.xmlDeclaration()
            def c = CaseRecord.findByCaseId(caseId)
            if(c) {
                if(c.prcReport != null && c.prcReport?.reviewDate != null ) {
                    "case"(id:c.caseId, collection_type:c.caseCollectionType?.name){
                        def prcReport = prcReportService.getPrcReport(c)
                        PRC_summary {
                            def resolutions = prcReportService.getPrcIssueResolutionDisplayList(prcReport)
                            issue_resolutions {
                                for(r in resolutions ) {
                                    issue_resolution {
                                        specimen_id(r.specimenId)
                                        tissue(r.tissue)
                                        issue_description(r.issueDescription)
                                        resolution_comments(r.resolutionComments)
                                        date_submitted(r.dateSubmitted)
                                    }
                                }
                            }

                            def specimenList = prcReportService.getPrcSpeciemenListByCase(c)
                            specimens {
                                for(s in specimenList ) {
                                    specimen {
                                        specimen_id(s.specimenRecord.specimenId)
                                        tissue_type(s.specimenRecord.tissueType?.name)
                                        def slideList = s.specimenRecord.slides
                                        def username = session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()

                                        if(session.org?.code != 'BROAD' && username != 'ldaccservice') {
                                            slides {
                                                for(sl in slideList) {
                                                    slide {
                                                        slide_id(sl.slideId)
                                                        image_id(sl.imageRecord?.imageId)
                                                    }
                                                }
                                            }
                                        }

                                        autolysis(s.autolysis)
                                        comments(s.comments)
                                        inventory_status(s.inventoryStatus)
                                    }
                                }
                            }

                            general_information {
                                overall_staining_of_slides(prcReport?.stainingOfSlides)
                                overall_staining_of_images(prcReport?.stainingOfImages)
                                overall_processing_embedding(prcReport?.processing)
                                additional_comments(prcReport?.comments)
                                if(c.caseCollectionType?.code == 'SURGI') {
                                    amputation_type {
                                        position_v(prcReport?.amputationType1)
                                        position_h(prcReport?.amputationType2)
                                    }
                                }
                            }

                            def sublist = prcReportService.getPrcReportSubList(prcReport)
                            submissions {
                                for(s in sublist ) {
                                    submission {
                                        submitted_by(s.submittedBy)
                                        date_submitted(s.dateSubmitted)
                                        report_version("V$s.reportVersion")
                                        date_reviewed(s.dateReviewed)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    prcReport {
                        status("1")
                        message {
                            error {
                                error(message: 'Summary Report is not avaliable')
                            }
                        }
                    }
                }
            } else {
                prcReport {
                    status("1")
                    message {
                        error {
                            error(message: "No Such Case '$caseid'")
                        }
                    }
                }
            }
        }
    }

    def getDeathMap(c) {
        def result=[:]
        def collectionDate = c.tissueRecoveryGtex?.collectionDate
        def startTime = c.tissueRecoveryGtex?.collectionStartTime
        def collectionStartTime = ldaccService.calculateDateWithTime(collectionDate,startTime)
        def cross_clamp_time = c.tissueRecoveryGtex?.crossClampTime
        def crossClampDateTime = ldaccService.getDateTimeComp(collectionStartTime, cross_clamp_time)

        if(c.caseCollectionType?.code == 'POSTM' || c.caseCollectionType?.code == 'OPO' ) {
            if(c.caseReportForm?.deathCircumstances?.dateTimeActualDeath) {
                result.put("deathType", "actual_death")
                result.put("deathDate", c.caseReportForm?.deathCircumstances?.dateTimeActualDeath)
                result.put("startTime", collectionStartTime)
            } else {
                result.put("deathType", "presumed_death")
                result.put("deathDate", c.caseReportForm?.deathCircumstances?.dateTimePresumedDeath)
                result.put("startTime", collectionStartTime)
            }
        } else {
            result.put("deathType", 'cross_clamp')
            result.put("deathDate", crossClampDateTime)
            result.put("startTime", collectionStartTime)
        }

        return result
    }

    def getDateTime(date, time) {
        if(!date || !time) {
            return ""
        }

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy")
        def date_str = df.format(date)
        def new_str = date_str+ " " + time
        SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy HH:mm")

        return df2.parse(new_str)
    }
    
   
    
    
    
}
