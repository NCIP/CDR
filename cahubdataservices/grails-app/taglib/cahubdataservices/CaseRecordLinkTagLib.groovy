/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cahubdataservices

/**
 *
 * @author umkis
 */
class CaseRecordLinkTagLib {
    
    def displayCaseRecordLink = {attrs, body ->
        
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id 
        
        def attName
        if ((name)&&(!name.trim().equals(''))) attName = "name=\""+name+"\" "
        def attId
        if ((id)&&(!id.trim().equals(''))) attId = "id=\""+id+"\" "
        def nameAndId 
        if ((attName)&&(attId)) nameAndId = attName + attId
        else if ((attName)&&(!attId)) nameAndId = attName
        else if ((!attName)&&(attId)) nameAndId = attId
        else nameAndId = ""
        
        def recordid = attrs.recordid
        def caseid = attrs.caseid 
        
        def caseRecord = attrs.caseRecord 
                
        if (!caseRecord)
        {
            def candidateRecord = attrs.candidateRecord
            if (candidateRecord) caseRecord = candidateRecord.caseRecord
        }
                
        if (caseRecord)
        {
            if ((!recordid)||(recordid.trim().equals(''))) recordid = caseRecord.id
            if ((!caseid)||(caseid.trim().equals(''))) caseid = caseRecord.caseId
        }
        
        if ((!caseid)&&(!recordid))
        {
            def nullFlavor = attrs.nullFlavor 
            if (!nullFlavor) nullFlavor = attrs.error 
            if (nullFlavor)
            {
                if (nullFlavor.trim().equalsIgnoreCase('error')) out.println "Link ERROR:No_case_ID"
                else if (nullFlavor.trim().equalsIgnoreCase('nothing')) {}
                else out.println nullFlavor
            }
            //else out.println "ERROR:No_case_ID"
            return
        }
        else if (!caseid)
        {
            def caseRecordInstance = nci.obbr.cahub.datarecords.CaseRecord.get(recordid)
            if (!caseRecordInstance) caseid = "UNKNOWN_CASE_ID"
            else caseid = caseRecordInstance.caseId
        }
        else if (!recordid) recordid = caseid
           
        def study = attrs.study
        def bss = attrs.bss 
        def status = attrs.status
        
        if (!study) study = caseRecord?.study?.code
        if (!bss) bss = caseRecord?.bss?.code
        if (!status) status = caseRecord?.caseStatus?.code
        
        def action = attrs.action ?: "display"
        def controller = attrs.controller ?: "caseRecord"
        boolean redlink = false
        
        def redlinkP = attrs.redlink
        if (redlinkP)
        {
            if (redlinkP instanceof String)
            {
                if (redlinkP.equalsIgnoreCase('true')||redlinkP.equalsIgnoreCase('yes')||redlinkP.equalsIgnoreCase('redlink')) redlink = true
                else if ((redlinkP.equalsIgnoreCase('check'))&&(caseRecord))
                {
                    redlink = ((caseRecord.caseStatus?.code == 'DATA') && (caseRecord.bpvSurgeryAnesthesiaForm?.surgeryDate?.plus(21)?.before(new Date())))
                }
            }
            else 
            {
                try
                {
                    redlink = redlinkP
                }
                catch(Exception ee)
                {
                    redlink = false
                }
            }
        }
        else
        {
            if (caseRecord)
            {
                redlink = ((caseRecord.caseStatus?.code == 'DATA') && (caseRecord.bpvSurgeryAnesthesiaForm?.surgeryDate?.plus(21)?.before(new Date())))
            }
        }
        
        
        
        def authorities = attrs.authorities 
        def org = attrs.org ?: ""
        
        def session = attrs.session
        if (session)
        {
            if (!authorities) authorities = session.authorities 
            if ((!org)||(org.trim().equals(''))) org = session.org?.code ?: ""
        }
        
        def linkLine
        
        //println 'CCCC case=' +caseid+ ', st1=' + session.study?.name + ', st2=' + study + ', status=' + status
        if (study == 'GTEX')
        {
            if (redlink)
            {
                out.println "<span class=\"redlink\">"
                out.println "<a ${nameAndId}href=\"/cahubdataservices/${controller}/${action}/${recordid}\">${caseid}</a>"
                out.println "</span>"   
            }
            else out.println "<a ${nameAndId}href=\"/cahubdataservices/${controller}/${action}/${recordid}\">${caseid}</a>"
        }
        else if (study == 'BPV')
        {
            
            if ((status == 'COMP')||(status == 'RELE')||(org == bss)||(org == 'VARI'))
            {
                //println 'AAA- ' + status
                out.println "<span class=\"${ redlink ? 'redlink' : ''}\">"
                out.println "<a ${nameAndId}href=\"/cahubdataservices/${controller}/${action}/${recordid}\">${caseid}</a>"
                out.println "</span>"
            }
            else if (authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                     authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") ||
                     authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_LDS") ||
                     authorities?.contains("ROLE_ADMIN"))
            {
                //println 'AAB- '
                out.println "<span class=\"${ redlink ? 'redlink' : ''}\">"
                out.println "<a ${nameAndId}href=\"/cahubdataservices/${controller}/${action}/${recordid}\">${caseid}</a>"
                out.println "</span>"
            }
            else 
            {

                  out.println "<b>" + caseid + "</b>"
            }
        }
        else
        {
            if (redlink)
            {
                out.println "<span class=\"redlink\">"
                out.println "<a ${nameAndId}href=\"/cahubdataservices/${controller}/${action}/${recordid}\">${caseid}</a>"
                out.println "</span>"   
            }
            else out.println "<a ${nameAndId}href=\"/cahubdataservices/${controller}/${action}/${recordid}\">${caseid}</a>"
        }
    }
}

