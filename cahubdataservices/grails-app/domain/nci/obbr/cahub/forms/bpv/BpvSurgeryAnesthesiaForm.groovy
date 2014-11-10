package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SOPRecord


class BpvSurgeryAnesthesiaForm extends BpvFormBaseClass {
    CaseRecord caseRecord
    Date surgeryDate
    String poSedDiv
    String poOpDiv
    String poAntiemDiv
    String poAntiAcDiv
    String poMedDiv
    String localAnesDiv
    String regAnesDiv

    String anesDiv
    String narcOpDiv
    String musRelaxDiv
    String inhalAnesDiv
    String addtlAnesDiv

    String insulDiv
    String steroidDiv
    String anitbioDiv
    String othMedDiv

    String poSed1Name
    Float poSed1Dose
    String poSed1Unit
    String poSed1Time
    String poSed2Name
    Float poSed2Dose
    String poSed2Unit
    String poSed2Time
    String poSed3Name
    Float poSed3Dose
    String poSed3Unit
    String poSed3Time
    String poSed4Name
    Float poSed4Dose
    String poSed4Unit
    String poSed4Time
    String poOp1Name
    Float poOp1Dose
    String poOp1Unit
    String poOp1Time
    String poOp2Name
    Float poOp2Dose
    String poOp2Unit
    String poOp2Time
    String poOp3Name
    Float poOp3Dose
    String poOp3Unit
    String poOp3Time
    String poOp4Name
    Float poOp4Dose
    String poOp4Unit
    String poOp4Time
    String poOp5Name
    Float poOp5Dose
    String poOp5Unit
    String poOp5Time
    String poAntiem1Name
    Float poAntiem1Dose
    String poAntiem1Unit
    String poAntiem1Time
    String poAntiem2Name
    Float poAntiem2Dose
    String poAntiem2Unit
    String poAntiem2Time
    String poAntiem3Name
    Float poAntiem3Dose
    String poAntiem3Unit
    String poAntiem3Time
    String poAntiAc1Name
    Float poAntiAc1Dose
    String poAntiAc1Unit
    String poAntiAc1Time
    String poAntiAc2Name
    Float poAntiAc2Dose
    String poAntiAc2Unit
    String poAntiAc2Time
    String poMed1Name
    Float poMed1Dose
    String poMed1Unit
    String poMed1Time
    String poMed2Name
    Float poMed2Dose
    String poMed2Unit
    String poMed2Time
    String poMed3Name
    Float poMed3Dose
    String poMed3Unit
    String poMed3Time

    String operatingRoom
    String anesIndStartTime

    String localAnes1Name
    Float localAnes1Dose
    String localAnes1Unit
    String localAnes1Time
    String localAnes2Name
    Float localAnes2Dose
    String localAnes2Unit
    String localAnes2Time
    String localAnes3Name
    Float localAnes3Dose
    String localAnes3Unit
    String localAnes3Time
    String regAnes1Name
    Float regAnes1Dose
    String regAnes1Unit
    String regAnes1Time
    String regAnes2Name
    Float regAnes2Dose
    String regAnes2Unit
    String regAnes2Time
    String regAnes3Name
    Float regAnes3Dose
    String regAnes3Unit
    String regAnes3Time
    String anes1Name
    Float anes1Dose
    String anes1Unit
    String anes1Time
    String anes2Name
    Float anes2Dose
    String anes2Unit
    String anes2Time
    String anes3Name
    Float anes3Dose
    String anes3Unit
    String anes3Time
    String anes4Name
    Float anes4Dose
    String anes4Unit
    String anes4Time
    String anes5Name
    Float anes5Dose
    String anes5Unit
    String anes5Time
    String anes6Name
    Float anes6Dose
    String anes6Unit
    String anes6Time
    String narcOp1Name
    Float narcOp1Dose
    String narcOp1Unit
    String narcOp1Time
    String narcOp2Name
    Float narcOp2Dose
    String narcOp2Unit
    String narcOp2Time
    String narcOp3Name
    Float narcOp3Dose
    String narcOp3Unit
    String narcOp3Time
    String narcOp4Name
    Float narcOp4Dose
    String narcOp4Unit
    String narcOp4Time
    String narcOp5Name
    Float narcOp5Dose
    String narcOp5Unit
    String narcOp5Time
    String musRelax1Name
    Float musRelax1Dose
    String musRelax1Unit
    String musRelax1Time
    String musRelax2Name
    Float musRelax2Dose
    String musRelax2Unit
    String musRelax2Time
    String musRelax3Name
    Float musRelax3Dose
    String musRelax3Unit
    String musRelax3Time
    String musRelax4Name
    Float musRelax4Dose
    String musRelax4Unit
    String musRelax4Time
    String inhalAnes1Name
    Float inhalAnes1Dose
    String inhalAnes1Unit
    String inhalAnes1Time
    String inhalAnes2Name
    Float inhalAnes2Dose
    String inhalAnes2Unit
    String inhalAnes2Time
    String inhalAnes3Name
    Float inhalAnes3Dose
    String inhalAnes3Unit
    String inhalAnes3Time
    String addtlAnes1Name
    Float addtlAnes1Dose
    String addtlAnes1Unit
    String addtlAnes1Time
    String addtlAnes2Name
    Float addtlAnes2Dose
    String addtlAnes2Unit
    String addtlAnes2Time
    String addtlAnes3Name
    Float addtlAnes3Dose
    String addtlAnes3Unit
    String addtlAnes3Time

    String insulinAdmin
    String insul1Name
    Float insul1Dose
    String insul1Unit
    String insul1Time
    String insul2Name
    Float insul2Dose
    String insul2Unit
    String insul2Time

    String steroidAdmin
    String steroid1Name
    Float steroid1Dose
    String steroid1Unit
    String steroid1Time
    String steroid2Name
    Float steroid2Dose
    String steroid2Unit
    String steroid2Time

    String antibioAdmin
    String antibio1Name
    Float antibio1Dose
    String antibio1Unit
    String antibio1Time
    String antibio2Name
    Float antibio2Dose
    String antibio2Unit
    String antibio2Time

    String othMedAdmin
    String med1Name
    Float med1Dose
    String med1Unit
    String med1Time
    String med2Name
    Float med2Dose
    String med2Unit
    String med2Time

    String firstIncisTime
    String surgicalProc
    String otherSurgicalProc
    String surgicalMethod
    String otherSurgicalMethod
    String firstClampTimeLeft
    String firstClampTimeRight
    String secondClampTimeLeft
    String secondClampTimeRight
    String organResecTimeLeft
    String organResecTimeRight
    Float inVivoIntOpIschemPdLeft
    Float inVivoIntOpIschemPdRight
    String excFirst15PostAnesInd
    String excAnesInd2OrganExc
    Float temperature1
    String temperature1Unit
    String timeTemp1
    Float temperature2
    String temperature2Unit
    String timeTemp2

    String epochsO2
    //String co2Level
    Float co2LevelValue
    CO2Unit co2LevelUnit
    String co2LevelUnitOther
    Float albuminCount
    Float rbcCount
    Float plateletCount
    Float frozPlasma

    Float bloodLossb4OrganExc
    String bloodLossRecPt
    Float urineVolb4Exc
    String urineVolRecPt
    String isAscitesFldColl
    Float collAscFluid
    String isPelvicWashColl
    Float collPelvicWash
    Float durFastingb4Surg
    String bowelPrepb4Surg
    String notableEvtsSurg
    String isElecOpNote
    String isElecAnesNote
    String specOrLeavingTime
    Date dateSubmitted
    SOPRecord formSOP

    static belongsTo = CaseRecord

    String toString(){"$caseRecord.caseId"}

    static constraints = {
        surgeryDate(blank:true, nullable:true)

        poSedDiv(blank:true, nullable:true)
        poOpDiv(blank:true, nullable:true)
        poAntiemDiv(blank:true, nullable:true)
        poAntiAcDiv(blank:true, nullable:true)
        poMedDiv(blank:true, nullable:true)
        localAnesDiv(blank:true, nullable:true)
        regAnesDiv(blank:true, nullable:true)

        anesDiv(blank:true, nullable:true)
        narcOpDiv(blank:true, nullable:true)
        musRelaxDiv(blank:true, nullable:true)
        inhalAnesDiv(blank:true, nullable:true)
        addtlAnesDiv(blank:true, nullable:true)

        insulDiv(blank:true, nullable:true)
        steroidDiv(blank:true, nullable:true)
        anitbioDiv(blank:true, nullable:true)
        othMedDiv(blank:true, nullable:true)

        poSed1Name(blank:true, nullable:true)
        poSed1Dose(blank:true, nullable:true)
        poSed1Unit(blank:true, nullable:true)
        poSed1Time(blank:true, nullable:true)
        poSed2Name(blank:true, nullable:true)
        poSed2Dose(blank:true, nullable:true)
        poSed2Unit(blank:true, nullable:true)
        poSed2Time(blank:true, nullable:true)
        poSed3Name(blank:true, nullable:true)
        poSed3Dose(blank:true, nullable:true)
        poSed3Unit(blank:true, nullable:true)
        poSed3Time(blank:true, nullable:true)
        poSed4Name(blank:true, nullable:true)
        poSed4Dose(blank:true, nullable:true)
        poSed4Unit(blank:true, nullable:true)
        poSed4Time(blank:true, nullable:true)
        poOp1Name(blank:true, nullable:true)
        poOp1Dose(blank:true, nullable:true)
        poOp1Unit(blank:true, nullable:true)
        poOp1Time(blank:true, nullable:true)
        poOp2Name(blank:true, nullable:true)
        poOp2Dose(blank:true, nullable:true)
        poOp2Unit(blank:true, nullable:true)
        poOp2Time(blank:true, nullable:true)
        poOp3Name(blank:true, nullable:true)
        poOp3Dose(blank:true, nullable:true)
        poOp3Unit(blank:true, nullable:true)
        poOp3Time(blank:true, nullable:true)
        poOp4Name(blank:true, nullable:true)
        poOp4Dose(blank:true, nullable:true)
        poOp4Unit(blank:true, nullable:true)
        poOp4Time(blank:true, nullable:true)
        poOp5Name(blank:true, nullable:true)
        poOp5Dose(blank:true, nullable:true)
        poOp5Unit(blank:true, nullable:true)
        poOp5Time(blank:true, nullable:true)
        poAntiem1Name(blank:true, nullable:true)
        poAntiem1Dose(blank:true, nullable:true)
        poAntiem1Unit(blank:true, nullable:true)
        poAntiem1Time(blank:true, nullable:true)
        poAntiem2Name(blank:true, nullable:true)
        poAntiem2Dose(blank:true, nullable:true)
        poAntiem2Unit(blank:true, nullable:true)
        poAntiem2Time(blank:true, nullable:true)
        poAntiem3Name(blank:true, nullable:true)
        poAntiem3Dose(blank:true, nullable:true)
        poAntiem3Unit(blank:true, nullable:true)
        poAntiem3Time(blank:true, nullable:true)
        poAntiAc1Name(blank:true, nullable:true)
        poAntiAc1Dose(blank:true, nullable:true)
        poAntiAc1Unit(blank:true, nullable:true)
        poAntiAc1Time(blank:true, nullable:true)
        poAntiAc2Name(blank:true, nullable:true)
        poAntiAc2Dose(blank:true, nullable:true)
        poAntiAc2Unit(blank:true, nullable:true)
        poAntiAc2Time(blank:true, nullable:true)
        poMed1Name(blank:true, nullable:true)
        poMed1Dose(blank:true, nullable:true)
        poMed1Unit(blank:true, nullable:true)
        poMed1Time(blank:true, nullable:true)
        poMed2Name(blank:true, nullable:true)
        poMed2Dose(blank:true, nullable:true)
        poMed2Unit(blank:true, nullable:true)
        poMed2Time(blank:true, nullable:true)
        poMed3Name(blank:true, nullable:true)
        poMed3Dose(blank:true, nullable:true)
        poMed3Unit(blank:true, nullable:true)
        poMed3Time(blank:true, nullable:true)

        operatingRoom(blank:true, nullable:true)
        anesIndStartTime(blank:true, nullable:true)

        localAnes1Name(blank:true, nullable:true)
        localAnes1Dose(blank:true, nullable:true)
        localAnes1Unit(blank:true, nullable:true)
        localAnes1Time(blank:true, nullable:true)
        localAnes2Name(blank:true, nullable:true)
        localAnes2Dose(blank:true, nullable:true)
        localAnes2Unit(blank:true, nullable:true)
        localAnes2Time(blank:true, nullable:true)
        localAnes3Name(blank:true, nullable:true)
        localAnes3Dose(blank:true, nullable:true)
        localAnes3Unit(blank:true, nullable:true)
        localAnes3Time(blank:true, nullable:true)
        regAnes1Name(blank:true, nullable:true)
        regAnes1Dose(blank:true, nullable:true)
        regAnes1Unit(blank:true, nullable:true)
        regAnes1Time(blank:true, nullable:true)
        regAnes2Name(blank:true, nullable:true)
        regAnes2Dose(blank:true, nullable:true)
        regAnes2Unit(blank:true, nullable:true)
        regAnes2Time(blank:true, nullable:true)
        regAnes3Name(blank:true, nullable:true)
        regAnes3Dose(blank:true, nullable:true)
        regAnes3Unit(blank:true, nullable:true)
        regAnes3Time(blank:true, nullable:true)
        anes1Name(blank:true, nullable:true)
        anes1Dose(blank:true, nullable:true)
        anes1Unit(blank:true, nullable:true)
        anes1Time(blank:true, nullable:true)
        anes2Name(blank:true, nullable:true)
        anes2Dose(blank:true, nullable:true)
        anes2Unit(blank:true, nullable:true)
        anes2Time(blank:true, nullable:true)
        anes3Name(blank:true, nullable:true)
        anes3Dose(blank:true, nullable:true)
        anes3Unit(blank:true, nullable:true)
        anes3Time(blank:true, nullable:true)
        anes4Name(blank:true, nullable:true)
        anes4Dose(blank:true, nullable:true)
        anes4Unit(blank:true, nullable:true)
        anes4Time(blank:true, nullable:true)
        anes5Name(blank:true, nullable:true)
        anes5Dose(blank:true, nullable:true)
        anes5Unit(blank:true, nullable:true)
        anes5Time(blank:true, nullable:true)
        anes6Name(blank:true, nullable:true)
        anes6Dose(blank:true, nullable:true)
        anes6Unit(blank:true, nullable:true)
        anes6Time(blank:true, nullable:true)
        narcOp1Name(blank:true, nullable:true)
        narcOp1Dose(blank:true, nullable:true)
        narcOp1Unit(blank:true, nullable:true)
        narcOp1Time(blank:true, nullable:true)
        narcOp2Name(blank:true, nullable:true)
        narcOp2Dose(blank:true, nullable:true)
        narcOp2Unit(blank:true, nullable:true)
        narcOp2Time(blank:true, nullable:true)
        narcOp3Name(blank:true, nullable:true)
        narcOp3Dose(blank:true, nullable:true)
        narcOp3Unit(blank:true, nullable:true)
        narcOp3Time(blank:true, nullable:true)
        narcOp4Name(blank:true, nullable:true)
        narcOp4Dose(blank:true, nullable:true)
        narcOp4Unit(blank:true, nullable:true)
        narcOp4Time(blank:true, nullable:true)
        narcOp5Name(blank:true, nullable:true)
        narcOp5Dose(blank:true, nullable:true)
        narcOp5Unit(blank:true, nullable:true)
        narcOp5Time(blank:true, nullable:true)
        musRelax1Name(blank:true, nullable:true)
        musRelax1Dose(blank:true, nullable:true)
        musRelax1Unit(blank:true, nullable:true)
        musRelax1Time(blank:true, nullable:true)
        musRelax2Name(blank:true, nullable:true)
        musRelax2Dose(blank:true, nullable:true)
        musRelax2Unit(blank:true, nullable:true)
        musRelax2Time(blank:true, nullable:true)
        musRelax3Name(blank:true, nullable:true)
        musRelax3Dose(blank:true, nullable:true)
        musRelax3Unit(blank:true, nullable:true)
        musRelax3Time(blank:true, nullable:true)
        musRelax4Name(blank:true, nullable:true)
        musRelax4Dose(blank:true, nullable:true)
        musRelax4Unit(blank:true, nullable:true)
        musRelax4Time(blank:true, nullable:true)
        inhalAnes1Name(blank:true, nullable:true)
        inhalAnes1Dose(blank:true, nullable:true)
        inhalAnes1Unit(blank:true, nullable:true)
        inhalAnes1Time(blank:true, nullable:true)
        inhalAnes2Name(blank:true, nullable:true)
        inhalAnes2Dose(blank:true, nullable:true)
        inhalAnes2Unit(blank:true, nullable:true)
        inhalAnes2Time(blank:true, nullable:true)
        inhalAnes3Name(blank:true, nullable:true)
        inhalAnes3Dose(blank:true, nullable:true)
        inhalAnes3Unit(blank:true, nullable:true)
        inhalAnes3Time(blank:true, nullable:true)
        addtlAnes1Name(blank:true, nullable:true)
        addtlAnes1Dose(blank:true, nullable:true)
        addtlAnes1Unit(blank:true, nullable:true)
        addtlAnes1Time(blank:true, nullable:true)
        addtlAnes2Name(blank:true, nullable:true)
        addtlAnes2Dose(blank:true, nullable:true)
        addtlAnes2Unit(blank:true, nullable:true)
        addtlAnes2Time(blank:true, nullable:true)
        addtlAnes3Name(blank:true, nullable:true)
        addtlAnes3Dose(blank:true, nullable:true)
        addtlAnes3Unit(blank:true, nullable:true)
        addtlAnes3Time(blank:true, nullable:true)

        insulinAdmin(blank:true, nullable:true)
        insul1Name(blank:true, nullable:true)
        insul1Dose(blank:true, nullable:true)
        insul1Unit(blank:true, nullable:true)
        insul1Time(blank:true, nullable:true)
        insul2Name(blank:true, nullable:true)
        insul2Dose(blank:true, nullable:true)
        insul2Unit(blank:true, nullable:true)
        insul2Time(blank:true, nullable:true)

        steroidAdmin(blank:true, nullable:true)
        steroid1Name(blank:true, nullable:true)
        steroid1Dose(blank:true, nullable:true)
        steroid1Unit(blank:true, nullable:true)
        steroid1Time(blank:true, nullable:true)
        steroid2Name(blank:true, nullable:true)
        steroid2Dose(blank:true, nullable:true)
        steroid2Unit(blank:true, nullable:true)
        steroid2Time(blank:true, nullable:true)

        antibioAdmin(blank:true, nullable:true)
        antibio1Name(blank:true, nullable:true)
        antibio1Dose(blank:true, nullable:true)
        antibio1Unit(blank:true, nullable:true)
        antibio1Time(blank:true, nullable:true)
        antibio2Name(blank:true, nullable:true)
        antibio2Dose(blank:true, nullable:true)
        antibio2Unit(blank:true, nullable:true)
        antibio2Time(blank:true, nullable:true)

        othMedAdmin(blank:true, nullable:true)
        med1Name(blank:true, nullable:true)
        med1Dose(blank:true, nullable:true)
        med1Unit(blank:true, nullable:true)
        med1Time(blank:true, nullable:true)
        med2Name(blank:true, nullable:true)
        med2Dose(blank:true, nullable:true)
        med2Unit(blank:true, nullable:true)
        med2Time(blank:true, nullable:true)

        firstIncisTime(blank:true, nullable:true)
        surgicalProc(blank:true, nullable:true)
        otherSurgicalProc(blank:true, nullable:true)
        surgicalMethod(blank:true, nullable:true)
        otherSurgicalMethod(blank:true, nullable:true)
        firstClampTimeLeft(blank:true, nullable:true)
        firstClampTimeRight(blank:true, nullable:true)
        secondClampTimeLeft(blank:true, nullable:true)
        secondClampTimeRight(blank:true, nullable:true)
        organResecTimeLeft(blank:true, nullable:true)
        organResecTimeRight(blank:true, nullable:true)
        inVivoIntOpIschemPdLeft(blank:true, nullable:true)
        inVivoIntOpIschemPdRight(blank:true, nullable:true)
        excFirst15PostAnesInd(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        excAnesInd2OrganExc(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        temperature1(blank:true, nullable:true)
        temperature1Unit(blank:true, nullable:true)
        timeTemp1(blank:true, nullable:true)
        temperature2(blank:true, nullable:true)
        temperature2Unit(blank:true, nullable:true)
        timeTemp2(blank:true, nullable:true)

        epochsO2(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        //co2Level(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        co2LevelValue(blank:true, nullable:true)
        co2LevelUnit(blank:true, nullable:true)
        co2LevelUnitOther(blank:true, nullable:true)
        albuminCount(blank:true, nullable:true)
        rbcCount(blank:true, nullable:true)
        plateletCount(blank:true, nullable:true)
        frozPlasma(blank:true, nullable:true)

        bloodLossb4OrganExc(blank:true, nullable:true)
        bloodLossRecPt(blank:true, nullable:true)
        urineVolb4Exc(blank:true, nullable:true)
        urineVolRecPt(blank:true, nullable:true)
        isAscitesFldColl(blank:true, nullable:true)
        collAscFluid(blank:true, nullable:true)
        isPelvicWashColl(blank:true, nullable:true)
        collPelvicWash(blank:true, nullable:true)
        durFastingb4Surg(blank:true, nullable:true)
        bowelPrepb4Surg(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        notableEvtsSurg(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        isElecOpNote(blank:true, nullable:true)
        isElecAnesNote(blank:true, nullable:true)
        specOrLeavingTime(blank:true, nullable:true)
        dateSubmitted(blank:true, nullable:true)
        formSOP(blank:true, nullable:true)
    }

    enum YesNo {
        No("No"),
        Yes("Yes")

        final String value;

        YesNo(String value) {
            this.value = value;
        }
        String toString() {
            value;
        }
        String getKey() {
            name()
        }
        static list() {
            [Yes, No]
        }
    }
    
    enum CO2Unit {
                
        mEqSlashL("mEq/L"),
        mmHg("mmHg"),
        mmolSlashL("mmol/L"),
        OTH("Other, Specify")

        final String value;

        CO2Unit(String value) {
            this.value = value;
        }

        String toString() {
            value;
        }

        String getKey() {
            name()
        }

        static list() {
            [mEqSlashL,mmHg,mmolSlashL,OTH]
        }
    }


    static mapping = {
        table 'bpv_surgery_anesthesia_form'
        id generator:'sequence', params:[sequence:'bpv_surgery_anesthesia_form_pk']
    }
}
