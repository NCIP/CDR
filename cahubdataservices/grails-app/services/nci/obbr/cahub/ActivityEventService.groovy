package nci.obbr.cahub

import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.util.ActivityEvent
import nci.obbr.cahub.util.AppSetting
import grails.util.GrailsUtil
import nci.obbr.cahub.staticmembers.BSS
import nci.obbr.cahub.util.querytracker.Query 


class ActivityEventService {

    static transactional = true
    def prcReportService1 = new PrcReportService()
    def env = "production".equalsIgnoreCase(GrailsUtil.environment) ? "" : " [${GrailsUtil.environment}]"
    def sendMailService
    def AppUsersService

    def createEvent(activityType, caseId, study, bssCode, restEventId, initiator, additionalInfo1, additionalInfo2) {
        new ActivityEvent(
            activityType: activityType, 
            caseId: caseId, 
            study: study,
            bssCode: bssCode,
            restEventId: restEventId, 
            initiator: initiator,
            additionalInfo1: additionalInfo1,
            additionalInfo2: additionalInfo2
        ).save(failOnError: false, flush: true)
        sendEmail(activityType, caseId, study, bssCode, restEventId, initiator, additionalInfo1, additionalInfo2)
    }
    
    def sendEmail(activityType, caseId, study, bssCode, restEventId, initiator, additionalInfo1, additionalInfo2) {
        def recipient
        def emailSubject
        def emailBody

        switch(activityType.code) {
            case "COREUNACC": 
                recipient = AppSetting.findByCode('GTEX_CORE_UNACC_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env Unacceptable core tissue for ${caseId}"
                emailBody = additionalInfo1
                break
            case "SEPSIS": 
                recipient = AppSetting.findByCode('SEPSIS_REPORT_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env Sepsis report for ${caseId} and status changed to BSSQACOMP"
                emailBody = additionalInfo1
                break
             case "PFFCOMP": 
                recipient = AppSetting.findByCode('PRC_ACTIVITY_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env Procurement feedback form  for ${caseId} was submitted"
                emailBody = "Procurement feedback form for ${caseId} was submitted by ${initiator}."
                break
            case "PFFFZNCOMP": 
                recipient = AppSetting.findByCode('PRC_ACTIVITY_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env Procurement feedback form  FZN for ${caseId} was submitted"
                emailBody = "Procurement feedback form FZN for ${caseId} was submitted by ${initiator}."
                break
            case "PRCCOMP": 
                recipient = AppSetting.findByCode('PRC_ACTIVITY_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env PRC report for ${caseId} was submitted"
                emailBody = "PRC report for ${caseId} was submitted by ${initiator}."
                break
            case "PRCFZNCOMP": 
                recipient = AppSetting.findByCode('PRC_ACTIVITY_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env PRC report FZN for ${caseId} was submitted"
                emailBody = "PRC report FZN for ${caseId} was submitted by ${initiator}."
                break
            case "STATUSCHG": 
                recipient = AppSetting.findByCode('CASE_STATUS_CHANGE_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env Case status for ${caseId} was changed"
                emailBody = "Case status for ${caseId} was changed to ${additionalInfo2} by ${initiator}. Previous status was ${additionalInfo1}."
                break
            case "SHIP": 
                def splits1 = additionalInfo1.split(',')
                def splits2 = additionalInfo2.split(',')
                def emailList1 = (getEmailList(splits1[0]).length() > 0) ? ',' + getEmailList(splits1[0]) : ''
                def emailList2 = (getEmailList(splits1[1]).length() > 0) ? ',' + getEmailList(splits1[1]) : ''
                recipient = AppSetting.findByCode('SHIPPING_EVENT_DISTRO')?.bigValue + emailList1 + emailList2
                emailSubject = "CDR Alert:$env Shipping event for ${caseId} was received"
                emailBody = "Specimens for ${caseId} were shipped by ${splits1[0]} to ${splits1[1]}. CBR event ID: ${restEventId}.\n\n${splits2[0]} tracking #: ${splits2[1]}"
                break
            case "UNUSEDKIT": 
                def splits1 = additionalInfo1.split(',')
                def splits2 = additionalInfo2.split(',')
                def emailList1 = (getEmailList(splits1[0]).length() > 0) ? ',' + getEmailList(splits1[0]) : ''
                def emailList2 = (getEmailList(splits1[1]).length() > 0) ? ',' + getEmailList(splits1[1]) : ''
                recipient = AppSetting.findByCode('SHIPPING_EVENT_DISTRO')?.bigValue + emailList1 + emailList2
                emailSubject = "CDR Alert:$env New ${study} kit(s) were shipped"
                emailBody = "New ${study} kit(s) were shipped by ${splits1[0]} to ${splits1[1]}. CBR event ID: ${restEventId}.\n\n${splits2[0]} tracking #: ${splits2[1]}"
                break
            case "COLLECT":
                def emailList = (getEmailList(additionalInfo1).length() > 0) ? ',' + getEmailList(additionalInfo1) : ''
                recipient = AppSetting.findByCode('COLLECTION_EVENT_DISTRO')?.bigValue + emailList
                emailSubject = "CDR Alert:$env Collection event for ${caseId} was received"
                emailBody = "${caseId} collection has occurred at ${additionalInfo1}. CBR event ID: ${restEventId}."
                break
            case "IMAGEREADY": 
                recipient = AppSetting.findByCode('APERIO_IMAGE_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env Aperio images for ${caseId} are ready"
                emailBody = "Aperio images for ${caseId} are now available. CBR event ID: ${restEventId}."
                break
            case "FASTTRACK": 
                recipient = AppSetting.findByCode('DM_FAST_TRACK_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env FastTrack was requested for ${caseId}"
                emailBody = "FastTrack was requested for ${caseId} by ${initiator}."
                break
            case "NEWRIN": 
                recipient = AppSetting.findByCode('LDACC_RIN_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env New RIN data from LDACC for ${caseId} was received"
                emailBody = "New RIN data for ${caseId} are now available."
                break
            case "FILEUPLOAD":
                recipient = AppSetting.findByCode('FILE_UPLOAD_DISTRO')?.bigValue
                if (caseId) {
                    emailSubject = "CDR Alert:$env New file was uploaded for ${caseId}"
                    emailBody = "New file ${additionalInfo1} was uploaded for ${caseId} by ${initiator}."
                } else {
                    emailSubject = "CDR Alert:$env New file was uploaded for ${study}"
                    emailBody = "New file ${additionalInfo1} was uploaded for ${study} by ${initiator}."
                }
                break
            case "BPVCASE":
                recipient = AppSetting.findByCode('BPV_CASE_CREATION_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env New BPV case ${caseId} was created"
                emailBody = "New BPV case ${caseId} was created at ${bssCode} by ${initiator}."
                break
            case "PRCAVAILABLE": 
                recipient = AppSetting.findByCode('PRC_REPORT_AVAILABLE_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env GTEx PRC report for ${caseId} is available"
                emailBody = "GTEx PRC report for ${caseId} was reviewed by ${initiator}."
                break
            case "SHIPPINGRECEIPT": 
                recipient = AppSetting.findByCode('SHIPPING_RECEIPT_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env Shipping receipt for ${restEventId} was received"
                if (caseId.equals("N/A")) {
                    emailBody = "${study} shipment receipt for ${restEventId} was received."
                } else {
                    emailBody = "${study} shipment receipt for ${restEventId} was received. Case ID: ${caseId}"
                }
                break
            case "SHIPRECPTDISC": 
                recipient = AppSetting.findByCode('SHIPPING_RECEIPT_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env Shipping receipt for ${restEventId} was received with discrepancies"
                if (caseId.equals("N/A")) {
                    emailBody = "${study} shipment receipt for ${restEventId} was received with discrepancies."
                } else {
                    emailBody = "${study} shipment receipt for ${restEventId} was received with discrepancies. Case ID: ${caseId}"
                }
                break                
            case "PRCFORQC": 
                recipient = AppSetting.findByCode('PRC_REPORT_FLAGGED_QC_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env ${caseId} PRC report was flagged for QC review"
                emailBody = "${caseId} PRC report was flagged for QC review. The report was submitted by ${additionalInfo1}." 
                break
            case "QUERY": 
                if (Query.get(additionalInfo1)?.task == 'FZN') {
                    recipient = AppSetting.findByCode('QT_RECUT_DISTRO')?.bigValue
                    emailSubject = "CDR Alert:$env Create slides for frozen tissues"
                    emailBody = "New request: Create slides for frozen tissues of ${caseId}. Query Tracker ID: ${additionalInfo1}"
                } else if (Query.get(additionalInfo1)?.task in ['HEPB', 'HEPC', 'HIV', 'HIVASSAY', 'OTHERINFECT']) {
                    recipient = AppSetting.findByCode('BPV_INFECTIOUS_DATA_DISTRO')?.bigValue
                    emailSubject = "CDR Alert:$env Infectious disease data captured for Case ${caseId}"
                    emailBody = "Infectious disease data was entered and submitted for Case ${caseId}. Please review the Clinical Data Entry Form and alert the CBR as per SOP."
                } else {
                    def bssEmailList = (getEmailList(bssCode).length() > 0) ? ',' + getEmailList(bssCode) : ''
                    recipient = AppSetting.findByCode('NEW_QUERY_TRACKER_DISTRO')?.bigValue + bssEmailList
                    emailSubject = "CDR Alert:$env New Query Tracker was created"
                    if (caseId) {
                        emailBody = "New Query Tracker ${additionalInfo1} for ${caseId} was created." 
                    } else {
                        emailBody = "New Query Tracker ${additionalInfo1} was created." 
                    }
                }
                break
            case "PROCFEEDAVAI": 
                /**if(bssCode == 'NDRI'){
                    recipient = AppSetting.findByCode('PROC_FEEDBACK_AVAILABLE_NDRI_DISTRO')?.bigValue
                }else if(bssCode=='RPCI'){
                    recipient = AppSetting.findByCode('PROC_FEEDBACK_AVAILABLE_RPCI_DISTRO')?.bigValue
                }else{
                     recipient = AppSetting.findByCode('PROC_FEEDBACK_AVAILABLE_DISTRO')?.bigValue
                }**/
                
                recipient = appUsersService.getEmailList(bssCode).join(',')
                emailSubject = "CDR Alert:$env GTEx procurement feedback for ${caseId} is available"
                emailBody = "GTEx procurement feedback for ${caseId} was reviewed by ${initiator}."
                break
             case "PROCFEEDFZNAVAI": 
                /**if(bssCode == 'NDRI'){
                    recipient = AppSetting.findByCode('PROC_FEEDBACK_AVAILABLE_NDRI_DISTRO')?.bigValue
                }else if(bssCode=='RPCI'){
                    recipient = AppSetting.findByCode('PROC_FEEDBACK_AVAILABLE_RPCI_DISTRO')?.bigValue
                }else{
                     recipient = AppSetting.findByCode('PROC_FEEDBACK_AVAILABLE_DISTRO')?.bigValue
                }**/
                recipient = appUsersService.getEmailList(bssCode).join(',')
                emailSubject = "CDR Alert:$env GTEx procurement feedback FZN for ${caseId} is available"
                emailBody = "GTEx procurement feedback FZN for ${caseId} was reviewed by ${initiator}."
                break
            case "INTERVIEW": 
                recipient = AppSetting.findByCode('NEW_INTERVIEW_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env New BPV ELSI Interview was created"
                emailBody = "New BPV ELSI Interview was created at ${bssCode} by ${initiator}. Interview ID: ${additionalInfo1}"
                break
            case "INTERVIEWSTATUS": 
                def splits1 = additionalInfo1.split(',')
                def splits2 = additionalInfo2.split(',')
                recipient = AppSetting.findByCode('INTERVIEW_STATUS_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env Interview status for ${splits1[0]} was changed"
                emailBody = "Interview status for ${splits1[0]} was changed to ${splits2[1]} by ${initiator}. Previous status was ${splits2[0]}."
                break
            case "PROCESSEVT": 
                def splits1 = additionalInfo1.split(',')
                def splits2 = additionalInfo2.split(',')            
                recipient = AppSetting.findByCode('PROCESSING_EVENT_DISTRO')?.bigValue
                if (splits1[0].equals("IMAGES") || splits1[0].equals("IMAGE")) {
                    def caseRecord = CaseRecord.findByCaseId(caseId)
                    def pathologist = prcReportService1.paxgReportSubmittedBy(caseRecord)
                    recipient = recipient + "," + AppSetting.findByCode('APERIO_IMAGE_DISTRO')?.bigValue
                    if (splits2[1].equals("Dry Ice")) {
                        emailSubject = "CDR Alert:$env Aperio images for ${caseId} are ready for Frozen specimens."
                        emailBody = "Aperio images for ${caseId} are now available for Frozen specimens. CBR event ID: ${restEventId}. CSR for PAXgene submitted by ${pathologist}."
                    } else if (splits2[1].equals("PAXgene")) {
                        emailSubject = "CDR Alert:$env Aperio images for ${caseId} are ready for PAXgene specimens."
                        emailBody = "Aperio images for ${caseId} are now available for PAXgene specimens. CBR event ID: ${restEventId}."
                    } else {
                        emailSubject = "CDR Alert:$env Aperio images for ${caseId} are ready."
                        emailBody = "Aperio images for ${caseId} are now available. CBR event ID: ${restEventId}. "
                    }
                } else {
                    emailSubject = "CDR Alert:$env Processing Event ${restEventId} generated for ${splits1[0]}."
                    emailBody = "${splits1[0]} were added to the case ${caseId} with the Processing event ${restEventId}."
                }
                break
            case "SHIPINSP": 
                recipient = AppSetting.findByCode('SHIPPING_EVENT_DISTRO')?.bigValue
                emailSubject = "CDR Alert:$env Inspection event ${restEventId} reporting discrepant specimens."
                emailBody = "Discrepant specimens were reported with Inspection event ${restEventId}. Case ID: ${caseId}"
                break                                
            default:
                recipient = "noreply@cahub.ncifcrf.gov"
                emailSubject = "Default subject"
                emailBody = "Default message"
        }
        
        sendMailService.sendActivityEventEmail(recipient, emailSubject, emailBody)
    }
    
    def getEmailList(orgCodePayload) {
        def orgCode = BSS.findByCode(orgCodePayload) ? BSS.findByCode(orgCodePayload).parentBss?.code : orgCodePayload
        def emailList = AppUsersService.getEmailList(orgCode)
        def result = ''
        
        for (i in emailList) {
            result = result + ',' + i    
        }
        if (result && result.length() > 0) {
            result = result.substring(1)
        }
        
        return result
    }
}
