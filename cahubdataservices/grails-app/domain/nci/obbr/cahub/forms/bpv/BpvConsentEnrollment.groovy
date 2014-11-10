package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.datarecords.CandidateRecord
import nci.obbr.cahub.datarecords.SOPRecord
import nci.obbr.cahub.staticmembers.AcquisitionType

class BpvConsentEnrollment extends BpvFormBaseClass {
    
    String inputtedCaseId
    String inputtedKitList
    String bssIrbProtocol
    CandidateRecord candidateRecord
    String tissueBankId
    AcquisitionType primaryTissueType
    Date dob
    String gender
    String otherGender
    
    String race
    Boolean raceIndian = false
    Boolean raceAsian = false
    Boolean raceWhite = false
    Boolean raceBlack = false
    Boolean raceHawaiian = false
    Boolean raceNotReported = false
    Boolean raceUnknown = false
    
    String ethnicity
    String jewish
    
    String ethnicBackground
    Boolean africa = false
    Boolean centralAfrican = false
    Boolean northAfrican = false
    Boolean southAfrican = false
    Boolean americas = false
    Boolean africanAmerican = false
    Boolean northAmerican = false
    Boolean latinAmerican = false
    Boolean caribbean = false
    Boolean southAmerican = false
    Boolean asia = false
    Boolean southeastAsian = false
    Boolean southAsian = false
    Boolean centralAsian = false
    Boolean chinese = false
    Boolean korean = false
    Boolean japanese = false
    Boolean indian = false
    Boolean australiaNewZealand = false
    Boolean australian = false
    Boolean newZealander = false
    Boolean europe = false
    Boolean easternEuropean = false
    Boolean icelandic = false
    Boolean mediterranean = false
    Boolean scandinavian = false
    Boolean westernEuropean = false
    Boolean intercontinental = false
    Boolean middleEastern = false
    Boolean hispanic = false
    Boolean otherEthnic = false
    String otherEthnicStr
    
    Date dateConsented
    String age
    String meetAge
    String consentFormVer
    String nameObtainedConsent
    String consentorRelationship
    String otherConsentRelation
    Date dateIRBApproved
    Date dateIRBExpires
    String willingELSISubStudy
    String specifiedLimitations
    Date dateWitnessed
    Date dateVerified
    String signedDated
    SOPRecord formSOP
    String localFacility //used for UPMC hospitals

    static constraints = {
        inputtedCaseId(blank:true, nullable:true)
        inputtedKitList(blank:true, nullable:true)
        bssIrbProtocol(blank:true, nullable:true)
        candidateRecord(blank:false, nullable:false)
        tissueBankId(blank:true, nullable:true)
        primaryTissueType(blank:true, nullable:true)
        dob(blank:true, nullable:true)
        gender(blank:true, nullable:true)
        otherGender(blank:true, nullable:true)
        race(blank:true, nullable:true)
        raceIndian(blank:true, nullable:true)
        raceAsian(blank:true, nullable:true)
        raceWhite(blank:true, nullable:true)
        raceBlack(blank:true, nullable:true)
        raceHawaiian(blank:true, nullable:true)
        raceNotReported(blank:true, nullable:true)
        raceUnknown(blank:true, nullable:true)
        ethnicity(blank:true, nullable:true)
        jewish(blank:true, nullable:true)
        ethnicBackground(blank:true, nullable:true, maxSize:1000)
        africa(blank:true, nullable:true)
        centralAfrican(blank:true, nullable:true)
        northAfrican(blank:true, nullable:true)
        southAfrican(blank:true, nullable:true)
        americas(blank:true, nullable:true)
        africanAmerican(blank:true, nullable:true)
        northAmerican(blank:true, nullable:true)
        latinAmerican(blank:true, nullable:true)
        caribbean(blank:true, nullable:true)
        southAmerican(blank:true, nullable:true)
        asia(blank:true, nullable:true)
        southeastAsian(blank:true, nullable:true)
        southAsian(blank:true, nullable:true)
        centralAsian(blank:true, nullable:true)
        chinese(blank:true, nullable:true)
        korean(blank:true, nullable:true)
        japanese(blank:true, nullable:true)
        indian(blank:true, nullable:true)
        australiaNewZealand(blank:true, nullable:true)
        australian(blank:true, nullable:true)
        newZealander(blank:true, nullable:true)
        europe(blank:true, nullable:true)
        easternEuropean(blank:true, nullable:true)
        icelandic(blank:true, nullable:true)
        mediterranean(blank:true, nullable:true)
        scandinavian(blank:true, nullable:true)
        westernEuropean(blank:true, nullable:true)
        intercontinental(blank:true, nullable:true)
        middleEastern(blank:true, nullable:true)
        hispanic(blank:true, nullable:true)
        otherEthnic(blank:true, nullable:true)
        otherEthnicStr(blank:true, nullable:true)
        dateConsented(blank:true, nullable:true)
        age(blank:true, nullable:true)
        meetAge(blank:true, nullable:true)
        consentFormVer(blank:true, nullable:true)
        nameObtainedConsent(blank:true, nullable:true)
        consentorRelationship(blank:true, nullable:true)
        otherConsentRelation(blank:true, nullable:true)
        dateIRBApproved(blank:true, nullable:true)
        dateIRBExpires(blank:true, nullable:true)
        willingELSISubStudy(blank:true, nullable:true)
        specifiedLimitations(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        dateWitnessed(blank:true, nullable:true)
        dateVerified(blank:true, nullable:true)
        signedDated(blank:true, nullable:true)
        formSOP(blank:true, nullable:true)
        localFacility(blank:true, nullable:true)
    }
    
    static mapping = {
        table 'bpv_consent_enrollment'
        id generator:'sequence', params:[sequence:'bpv_consent_enrollment_pk']
    }
    
    
    String calculateEthnicBackground() {
        String str=""
        
        if (africa) {
            str = str + ", Africa"
        }
        if (centralAfrican) {
            str = str + ", Central African"
        }
        if (northAfrican) {
            str = str + ", North African"
        }
        if (southAfrican) {
            str = str + ", South African"
        }
        if (americas) {
            str = str + ", Americas"
        }
        if (africanAmerican) {
            str = str + ", African American"
        }
        if (northAmerican) {
            str = str + ", North American"
        }
        if (latinAmerican) {
            str = str + ", Latin American"
        }
        if (caribbean) {
            str = str + ", Caribbean"
        }
        if (southAmerican) {
            str = str + ", South American"
        }
        if (asia) {
            str = str + ", Asia"
        }
        if (southeastAsian) {
            str = str + ", Southeast Asian"
        }
        if (southAsian) {
            str = str + ", South Asian"
        }
        if (centralAsian) {
            str = str + ", Central Asian"
        }
        if (chinese) {
            str = str + ", Chinese"
        }
        if (korean) {
            str = str + ", Korean"
        }
        if (japanese) {
            str = str + ", Japanese"
        }
        if (indian) {
            str = str + ", Indian"
        }
        if (australiaNewZealand) {
            str = str + ", Australia/New Zealand"
        }
        if (australian) {
            str = str + ", Australian"
        }
        if (newZealander) {
            str = str + ", New Zealander"
        }
        if (europe) {
            str = str + ", Europe"
        }
        if (easternEuropean) {
            str = str + ", Eastern European"
        }
        if (icelandic) {
            str = str + ", Icelandic"
        }
        if (mediterranean) {
            str = str + ", Mediterranean"
        }
        if (scandinavian) {
            str = str + ", Scandinavian"
        }
        if (westernEuropean) {
            str = str + ", Western European"
        }
        if (intercontinental) {
            str = str + ", Intercontinental"
        }
        if (middleEastern) {
            str = str + ", Middle Eastern"
        }
        if (hispanic) {
            str = str + ", Hispanic"
        }
        if (otherEthnicStr && otherEthnicStr.length() > 0) {
            str = str + ", ${otherEthnicStr}"
        }
        
        if (str && str.length() > 0) {
            str = str.substring(2)
        }
     
        return str
    }
}
