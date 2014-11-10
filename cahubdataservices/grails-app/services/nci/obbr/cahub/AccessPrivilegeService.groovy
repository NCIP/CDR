package nci.obbr.cahub

class AccessPrivilegeService {

    static transactional = true

    def checkAccessPrivilege(caseRecordInstance, session, action) {
        
        if ((!session)||(!caseRecordInstance)||(!action)) return 1
        String studycode = session.study?.code
        
        boolean isSessionConflict = false
	if (!studycode)
	{
	    if (caseRecordInstance.study) session.study = caseRecordInstance.study
	}
	else if (caseRecordInstance.study)
	{
	    if (!caseRecordInstance.study.code.equals(studycode)) isSessionConflict = true
	}
	if (isSessionConflict)
	{
	    println 'Session Conflict caseRecode.study=' + caseRecordInstance.study?.code + ', session.study=' + studycode + ', session=' + session
	    return 2
        }
        
        if (checkAccessPrivilege2(caseRecordInstance, session, action)) return 0
        else return 1
    }
    
    def checkAccessPrivilege2(caseRecordInstance, session, action) {
        if (action.equals('edit')) {

            if ((caseRecordInstance.bss?.parentBss?.code?.equals(session.org?.code) || session.org?.code?.equals('OBBR')) && session.DM == true && 
                (caseRecordInstance.caseStatus?.code == 'INIT' || caseRecordInstance.caseStatus?.code == 'DATA' || (!session.study?.code?.equals('BPV') && caseRecordInstance.caseStatus?.code == 'DATACOMP') || caseRecordInstance.caseStatus?.code == 'REMED')) { 
                return true
            } else {
                // New condition added for 5.3
                String studycode = session.study?.code
                if (!studycode)
                {
                    if (session.org?.code?.equals('OBBR') && session.DM == true) studycode = caseRecordInstance.study?.code
                }
                if (session.org?.code?.equals('OBBR') && session.DM == true && (studycode?.equals('GTEX') || studycode?.equals('BPV')) && caseRecordInstance.caseStatus?.code == 'QA') {
                    return true
                } else {
                    return false
                }
            } 
        } else if (action.equals('show')) {
            if (session.org?.code?.equals('OBBR') && (caseRecordInstance.caseStatus?.code != 'WITHDR' || session.DM == true || session.LDS == true)) {
                return true
            } else {
                return false
            }
        } else if (action.equals('view')) {
            if ((caseRecordInstance.bss?.parentBss?.code?.equals(session.org?.code) && caseRecordInstance.caseStatus?.code != 'WITHDR') || 
                (session.org?.code?.equals('OBBR') && (caseRecordInstance.caseStatus?.code != 'WITHDR' || session.DM == true || session.LDS == true))) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }
    
    def checkAccessPrivilegeCandidate(candidateRecordInstance, session, action) {
        def caseRecordInstance = candidateRecordInstance.caseRecord
        
        if (caseRecordInstance) {
            return checkAccessPrivilege(caseRecordInstance, session, action)
        } else {
            return 0
        }
    }
    
    def checkAccessPrivilegePrc(prcReport, session, action) {
        def caseRecordInstance = prcReport.caseRecord
        def reviewedBy = prcReport.reviewedBy
        
        if (action.equals('view')) {
            if (((caseRecordInstance.bss?.parentBss?.code?.equals(session.org?.code) || session.org?.code?.equals('VARI') || session.org?.code?.equals('BROAD')) && caseRecordInstance.caseStatus?.code != 'WITHDR' && reviewedBy != null) || 
                (session.org?.code?.equals('OBBR') && (caseRecordInstance.caseStatus?.code != 'WITHDR' || session.DM == true || session.LDS == true))) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }
    
    
    def checkSurveyAccessPrivilege(interviewRecordInstance, session, action) {
        if (action.equals('edit')) {
            if ((interviewRecordInstance.orgCode.equals(session.org?.code) || session.org?.code?.equals('OBBR')) && session.DM == true && 
                interviewRecordInstance.interviewStatus?.key in ['DATA', 'REMED']) { 
                return true
            } else {
                return false
            } 
        } else if (action.equals('show')) {
            if ((interviewRecordInstance.orgCode.equals(session.org?.code)) || session.org?.code?.equals('OBBR') || session.authorities?.contains("ROLE_BPV_ELSI_DA")) {

                return true
            } else {

                return false
            }
        } else {
            return false
        }
    }    
    
    def checkAccessPrivilegeQuery(queryInstance, session) {
        if (session.org?.code?.equals('OBBR')) {
            return true
        } else {
            if (queryInstance.organization?.code?.matches(session.org?.code + ".*") && queryInstance.queryStatus) {
                return true
            } else {
                return false
            }
        }
    }
    
    //pmh
    def checkAccessPrivUploads(caseRecordInstance, session) {
        
        if ((caseRecordInstance.bss?.parentBss?.code?.equals(session.org?.code) || session.org?.code?.equals('OBBR')) && session.DM == true ) { 
            return true
        } else {
            return false
        } 
        
    }
}
