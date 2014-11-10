/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cahubdataservices

/**
 *
 * @author shivece
 */
class BpvElsiSurveyLinkTagLib {
	
    // survey links
    def bpvelsisurveylink = { attrs, body ->
            
        def sr = attrs.sr

        
            if(!sr.dateSubmitted && !sr.answers && session.DM == true && session.org.code == sr.interviewRecord.orgCode){
                out.println "<span class=\"no\">No</span> <a href=\"/cahubdataservices/survey/edit/${sr.id}\">(Start)</a>"              
            }
            else if(!sr.dateSubmitted && !sr.answers){
                out.println "<span class=\"no\">Not Started</span>"              
            }
            else if(!sr.dateSubmitted && sr.answers && session.DM == true && (session.org.code == sr.interviewRecord.orgCode || (session.org.code == 'OBBR' && session.DM == true && sr.interviewRecord.interviewStatus?.key in ['REMED', 'QA']))){
                out.println "<span class=\"incomplete\">In Progress</span> <a href=\"/cahubdataservices/survey/edit/${sr.id}\">(Edit)</a>"              
            }            
            else if((!sr.dateSubmitted && sr.answers) && (session.authorities?.contains("ROLE_BPV_ELSI_DA") && (session.org.code != sr.interviewRecord.orgCode) || session.org.code == 'OBBR')){
                out.println "<span class=\"incomplete\">In Progress</span>"              
            }  
//            else if (session.org?.code?.equals('OBBR') && (session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM")||session.DM) && (sr.interviewRecord.interviewStatus?.key in ['QA']))
//            {
//                // This if-block is for JIRA CDRQA-1263 (HUB-REQ-870)
//                out.println "<span class=\"yes\">Completed</span> <a href=\"/cahubdataservices/survey/edit/${sr.id}\">(Edit)</a>"
//            }
            else out.println "<span class=\"yes\">Completed</span> <a href=\"/cahubdataservices/survey/show/${sr.id}\">(View)</a>"              
                       

    }
    
    //CRF link
    def bpvelsicrflink = { attrs, body ->
            
        def crf = attrs.crf
        def ir = attrs.ir

            if(!crf && session.DM == true && session.org.code == ir.orgCode){
                out.println "<span class=\"no\">No</span> <a href=\"/cahubdataservices/bpvElsiCrf/create/${ir.id}\">(Start)</a>"             
            }
            
            else if(!crf){
                out.println "<span class=\"no\">Not Started</span>"             
            }        
        
            else if(crf && !crf.dateSubmitted && (session.DM == true && session.org.code == crf.interviewRecord.orgCode || (session.org.code == 'OBBR' && session.DM == true && crf.interviewRecord.interviewStatus?.key in ['REMED', 'QA']))){
                out.println "<span class=\"incomplete\">In Progress</span> <a href=\"/cahubdataservices/bpvElsiCrf/edit/${crf.id}\">(Edit)</a>"             
            }

            else if((crf && !crf.dateSubmitted) && (session.authorities?.contains("ROLE_BPV_ELSI_DA") && (session.org.code != crf.interviewRecord.orgCode) || session.org.code == 'OBBR')){
                out.println "<span class=\"incomplete\">In Progress</span>"             
            }        
//            else if (session.org?.code?.equals('OBBR') && (session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM")||session.DM) && (crf.interviewRecord.interviewStatus?.key in ['QA']))
//            {
//                // This if-block is for JIRA CDRQA-1263 (HUB-REQ-870)
//                out.println "<span class=\"yes\">Completed</span> <a href=\"/cahubdataservices/bpvElsiCrf/edit/${crf.id}\">(Edit)</a>" 
//            }
            else out.println "<span class=\"yes\">Completed</span> <a href=\"/cahubdataservices/bpvElsiCrf/show/${crf.id}\">(View)</a>"             
             
        

    }    
    
    //change interview status link
    def bpvelsistatuslink = { attrs, body ->

        def ir = attrs.ir
        def srSubmitted = false
        
        ir.surveys.each{
            if(it.dateSubmitted && srSubmitted == false ){
                srSubmitted = true
            }
        }
            //BSS role
            if(session.org?.code != 'OBBR' && 
                ir.orgCode == session.org.code && 
                ir.interviewStatus?.key in ['DATA','DATACOMP','REMED'] && 
                ir.bpvElsiCrf?.dateSubmitted &&
                (srSubmitted == true || !ir.surveys)){
                
                
                out.println "<a href=\"/cahubdataservices/interviewRecord/changeInterviewStatus/${ir.id}\">(Change)</a>"             
            }

            //caHUB DM/Super role
            if(session.org?.code == 'OBBR' &&
               session.DM == true &&
               ir.interviewStatus?.key in ['SITEQACOMP', 'QA', 'COMP', 'RELE', 'REMED'] &&
               ir.bpvElsiCrf?.dateSubmitted &&
               (srSubmitted == true || !ir.surveys)){

                out.println "<a href=\"/cahubdataservices/interviewRecord/changeInterviewStatus/${ir.id}\">(Change)</a>"             
            }        
        
    }    
    
    
}
