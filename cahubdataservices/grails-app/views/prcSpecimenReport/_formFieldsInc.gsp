<style type="text/css">
 .prop .name{font-weight:bold;width:auto}
 .prop .value{width:auto}
</style>


<p /><br />
<!--get parent specimenRecord if new, else use existing one -->
        <g:if test="${!prcSpecimenReportInstance.specimenRecord}">
          <g:set var="fetchedSpecimenRecord" value="${nci.obbr.cahub.datarecords.SpecimenRecord.get(params.specimenRecord.id)}" />
        </g:if>
        <g:else>
          <g:set var="fetchedSpecimenRecord" value="${prcSpecimenReportInstance.specimenRecord}" />          
        </g:else>

<table border="0">
      <tr>
        <td><h2>Case & Specimen Details for Review</h2></td>
        <td><h2>Slides & Images</h2></td>
    </tr>
  <tr>
    <td>
      <table>
        <tr class="prop">
          <td valign="top" class="name">Case ID</td>
          <td valign="top" class="value">${fetchedSpecimenRecord.caseRecord.caseId}</td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name">Specimen ID</td>
            <td valign="top" class="value">${fetchedSpecimenRecord.specimenId}</td>
        </tr>
        <tr class="prop">
          <td valign="top" class="name">Tissue Site</td>
          <td valign="top" class="value">${fetchedSpecimenRecord.tissueType}</td>
        </tr>
    </table>
</td>
<td>
        <table>
                <tr>
            </tr>
              <tr class="prop">
            <td class="name">Slide #</td>
            <td class="name">Slide Label</td>
            <td class="name">Image Link (Aperio)</td>
            <td class="name">Reorder Requested?</td>
          </tr>
          <g:each in="${fetchedSpecimenRecord.slides}" var="s" status="i">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td class="name">${i +1}</td>
            <td class="name">
              <a href="" onclick="javascript:var w1=window.open('/cahubdataservices/slideRecord/edit/${s.id}', 'hub_slideReorder', 'location=1,status-1,scrollbars=0,width=450,height=600');w1.focus();return false;">${s.slideId}</a>
              </td>
              <td class="name"><a href="" onclick="javascript:var w2=window.open('https://microscopy.vai.org/imageserver/@@/@${s.imageRecord.imageId}/view.apml', 'hub_aperio', 'location=1,status-1,scrollbars=0,width=965,height=700');w2.focus();return false;">${s.imageRecord.imageId}</a></td>
            <td class="name"><g:if test="${s.requestReorder == true}">Yes</g:if><g:else>No</g:else></td>            
          </tr>                      
          </g:each>        

        </table>
      </td>
    </tr>
</table>
             
<p /><br />
<table border="0">
    <tr><td colspan="4"><h2>Findings/Diagnosis</h2></td></tr>   
    <tr class="prop">
        <td valign="top" class="name">
            <label for="specifyFindings"><g:message code="PrcSpecimenReport.specifyFindings.label" default="Specify findings" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'specifyFindings', 'errors')}">
            <g:textArea name="specifyFindings" cols="40" rows="5" value="${prcSpecimenReportInstance?.specifyFindings}" />
        </td>
        <td valign="top" class="name">
            <label for="whereFindingsFound"><g:message code="PrcSpecimenReport.whereFindingsFound.label" default="Slides/images where findings were found" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'whereFindingsFound', 'errors')}">
            <g:textArea name="whereFindingsFound" cols="40" rows="5" value="${prcSpecimenReportInstance?.whereFindingsFound}" />
        </td>
        <td valign="top" class="name">
            <g:message code="PrcSpecimenReport.analysisMadeFrom.label" default="Analysis was made from" />
        <br /><br />
        <g:checkBox name="analysisFromWSI" value="${prcSpecimenReportInstance?.analysisFromWSI}" /> <label for="analysisFromWSI">Whole slide image via Aperio</label><br />
        <g:checkBox name="analysisFromSlide" value="${prcSpecimenReportInstance?.analysisFromSlide}" /> <label for="analysisFromSlide">Slide</label><br />
        <g:checkBox name="analysisFromSlideAndWSI" value="${prcSpecimenReportInstance?.analysisFromSlideAndWSI}" /> <label for="analysisFromSlideAndWSI">WSI and slide</label><br />
        <g:checkBox name="analysisFromDVDWSI" value="${prcSpecimenReportInstance?.analysisFromDVDWSI}" /> <label for="analysisFromDVDWSI">DVD WSI</label><br />
        </td>
      </tr>

</table>                      
<p /><br />
<table border="0" style="border-bottom:0px;width:100%;">
  <tr><td colspan="10"><h2>Microscopic (n=Negative, m=Mild, mo=Moderate, s=Severe)</h2></td></tr>                        
    <tr class="prop">
        <td valign="top" class="name">
            <label for="totalTissuePieces"><g:message code="PrcSpecimenReport.totalTissuePieces.label" default="Total number of tissue pieces" /></label>
        </td>
        <td valign="top" style="width:50px;" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'totalTissuePieces', 'errors')}">
            <g:textField size="2" name="totalTissuePieces" value="${fieldValue(bean: prcSpecimenReportInstance, field: 'totalTissuePieces')}" />
        </td>

        <td valign="top" class="name">
            <label for="lengthSpec"><g:message code="PrcSpecimenReport.lengthSpec.label" default="Length / Width of Specimen (mm)" /></label>
        </td>
        <td valign="top" style="width:50px;white-space:nowrap" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'lengthSpec', 'errors')}">
            <g:textField size="2" name="lengthSpec" value="${fieldValue(bean: prcSpecimenReportInstance, field: 'lengthSpec')}" />&nbsp; x
        </td>
        <td valign="top" style="width:50px;" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'widthSpec', 'errors')}">
            <g:textField size="2" name="widthSpec" value="${fieldValue(bean: prcSpecimenReportInstance, field: 'widthSpec')}" />
        </td> 
        <td valign="top" class="name">
            <label for="tissueAreaEntire"><g:message code="PrcSpecimenReport.tissueAreaEntire.label" default="Tissue area - entire (sq mm)" /></label>
        </td>
        <td valign="top" style="width:50px;" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'tissueAreaEntire', 'errors')}">
            <g:textField size="2" name="tissueAreaEntire" value="${fieldValue(bean: prcSpecimenReportInstance, field: 'tissueAreaEntire')}" />
        </td>
        <td style="width:100%;"></td>
    </tr>
</table>
               

<table border="0" style="border-top:0px;">                        
    <tr class="prop">
        <td valign="top" class="name">
            <label for="crush"><g:message code="PrcSpecimenReport.crush.label" default="Crush" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'crush', 'errors')}">
            <g:select name="crush" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.crush?.name()}" noSelection="['': '']" />
        </td>

        <td valign="top" class="name">
            <label for="autolysis"><g:message code="PrcSpecimenReport.autolysis.label" default="Autolysis" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'autolysis', 'errors')}">
            <g:select name="autolysis" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.autolysis?.name()}" noSelection="['': '']" />
        </td>
        <td valign="top" class="name">
            <label for="otherMicroFindings"><g:message code="PrcSpecimenReport.otherMicroFindings.label" default="Other Microscopic Findings" /></label>
        </td>
        <td rowspan=10" valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'otherMicroFindings', 'errors')}">
            <g:textArea name="otherMicroFindings" cols="40" rows="5" value="${prcSpecimenReportInstance?.otherMicroFindings}" />
        </td>                                
    </tr>
    <tr class="prop">
        <td valign="top" class="name">
            <label for="necrosis"><g:message code="PrcSpecimenReport.necrosis.label" default="Necrosis" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'necrosis', 'errors')}">
            <g:select name="necrosis" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.necrosis?.name()}" noSelection="['': '']" />
        </td>

        <td valign="top" class="name">
            <label for="fibrosis"><g:message code="PrcSpecimenReport.fibrosis.label" default="Fibrosis" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'fibrosis', 'errors')}">
            <g:select name="fibrosis" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.fibrosis?.name()}" noSelection="['': '']" />
        </td>
   </tr>    
   <tr class="prop">                           
        <td valign="top" class="name">
            <label for="atrophy"><g:message code="PrcSpecimenReport.atrophy.label" default="Atrophy" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'atrophy', 'errors')}">
            <g:select name="atrophy" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.atrophy?.name()}" noSelection="['': '']" />
        </td>

        <td valign="top" class="name">
            <label for="edema"><g:message code="PrcSpecimenReport.edema.label" default="Edema" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'edema', 'errors')}">
            <g:select name="edema" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.edema?.name()}" noSelection="['': '']" />
        </td>
  </tr>
  <tr class="prop">
        <td valign="top" class="name">
            <label for="congestion"><g:message code="PrcSpecimenReport.congestion.label" default="Congestion" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'congestion', 'errors')}">
            <g:select name="congestion" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.congestion?.name()}" noSelection="['': '']" />
        </td>

        <td valign="top" class="name">
            <label for="acuteInflamation"><g:message code="PrcSpecimenReport.acuteInflamation.label" default="Acute Inflamation" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'acuteInflamation', 'errors')}">
            <g:select name="acuteInflamation" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.acuteInflamation?.name()}" noSelection="['': '']" />
        </td>
    </tr>
    <tr class="prop">
        <td valign="top" class="name">
            <label for="chronicInflamation"><g:message code="PrcSpecimenReport.chronicInflamation.label" default="Chronic Inflamation" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'chronicInflamation', 'errors')}">
            <g:select name="chronicInflamation" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.chronicInflamation?.name()}" noSelection="['': '']" />
        </td>
        <td valign="top" class="name">
            <label for="hemorrhage"><g:message code="PrcSpecimenReport.hemorrhage.label" default="Hemorrhage" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'hemorrhage', 'errors')}">
            <g:select name="hemorrhage" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.hemorrhage?.name()}" noSelection="['': '']" />
        </td>
    </tr>
</table>   

<p /><br />
<table border="0">
    <tr><td colspan="10"><h2>Image Annotations</h2></td></tr>                   
    <tr class="prop">
        <td style="width:250px;" valign="top" class="name">
            <label for="annotationDesc"><g:message code="PrcSpecimenReport.annotationDesc.label" default="Image Annotation Description" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'annotationDesc', 'errors')}">
            <g:textArea name="annotationDesc" cols="40" rows="5" value="${prcSpecimenReportInstance?.annotationDesc}" />
        </td>
    </tr>
</table>          
<p /><br />
<table border="0">
     <tr><td colspan="6"><h2>Artifacts (n=Negative, m=Mild, mo=Moderate, s=Severe)</h2></td></tr>
       <tr class="prop">
          <td valign="top" class="name">
              <label for="chatter"><g:message code="PrcSpecimenReport.chatter.label" default="Chatter" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'chatter', 'errors')}">
              <g:select name="chatter" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.chatter?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name">
              <label for="fracture"><g:message code="PrcSpecimenReport.fracture.label" default="Fracture" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'fracture', 'errors')}">
              <g:select name="fracture" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.fracture?.name()}" noSelection="['': '']" />
          </td>                                
          <td valign="top" class="name">
              <label for="staining"><g:message code="PrcSpecimenReport.staining.label" default="Staining" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'staining', 'errors')}">
              <g:select name="staining" from="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$SeverityScale?.values()*.name()}" value="${prcSpecimenReportInstance?.staining?.name()}" noSelection="['': '']" />
          </td>                                      
      </tr>
      <tr class="prop">
          <td valign="top" class="name">
              <label for="fixative"><g:message code="PrcSpecimenReport.fixative.label" default="Fixative" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'fixative', 'errors')}">
              <g:select name="fixative" from="${nci.obbr.cahub.prc.PrcSpecimenReport$Fixative?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$Fixative?.values()*.name()}" value="${prcSpecimenReportInstance?.fixative?.name()}" noSelection="['': '']" />
          </td>

          <td valign="top" class="name">
              <label for="artifactComments"><g:message code="PrcSpecimenReport.artifactComments.label" default="Artifact Comments" /></label>
          </td>
          <td colspan="3" valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'artifactComments', 'errors')}">
              <g:textArea name="artifactComments" cols="40" rows="5" value="${prcSpecimenReportInstance?.artifactComments}" />
          </td>
      </tr>
</table>

    
<p /><br />
<table border="0">
    <tr><td colspan="4"><h2>Assessment</h2></td></tr>
    <tr class="prop">
        <td valign="top" class="name">
            <label for="caseAcceptable"><g:message code="PrcSpecimenReport.caseAcceptable.label" default="Is case morpholocigally acceptable for release into inventory?" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'caseAcceptable', 'errors')}">
            <g:select name="caseAcceptable" from="${nci.obbr.cahub.prc.PrcSpecimenReport$AcceptableScale?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$AcceptableScale?.values()*.name()}" value="${prcSpecimenReportInstance?.caseAcceptable?.name()}" noSelection="['': '']" />
        </td>
    </tr>

    <tr class="prop">
        <td valign="top" class="name">
            <label for="caseAcceptableOther"><g:message code="PrcSpecimenReport.caseAcceptableOther.label" default="Specify reasons for No, Maybe, or With Limitations" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'caseAcceptableOther', 'errors')}">
            <g:textArea name="caseAcceptableOther" cols="40" rows="5" value="${prcSpecimenReportInstance?.caseAcceptableOther}" />
        </td>

        <td valign="top" class="name">
            <label for="closingComments"><g:message code="PrcSpecimenReport.closingComments.label" default="Closing Comments" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: prcSpecimenReportInstance, field: 'closingComments', 'errors')}">
            <g:textArea name="closingComments" cols="40" rows="5" value="${prcSpecimenReportInstance?.closingComments}" />
        </td>
    </tr>
</table>

<p /><br />
<table border="0">
    <tr><td colspan="4"><h2>Molecular Studies</h2></td></tr>
    <tr class="prop">
      <td valign="top" class="name">RIN Value</td>
      <td valign="top" class="value"></td>
    </tr>  
</table>

<p /><br />
<table border="0">
      <tr><td colspan="4"><h2>Authorization & Additional Reviews </h2></td></tr>
      <tr class="prop">
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'authorizedReviewer1', 'errors')}">
              <label for="authorizedReviewer1"><g:message code="PrcSpecimenReport.authorizedReviewer1.label" default="Reviewer 1" /></label>
              <g:select name="authorizedReviewer1" from="${nci.obbr.cahub.prc.PrcSpecimenReport$CAPTStaff?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$CAPTStaff?.values()*.name()}" value="${prcSpecimenReportInstance?.authorizedReviewer1?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'reviewType1', 'errors')}">
              <label for="reviewType1"><g:message code="PrcSpecimenReport.additonalReview1.label" default="Type of review" /></label>
              <g:select name="reviewType1" from="${nci.obbr.cahub.prc.PrcSpecimenReport$ReviewType?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$ReviewType?.values()*.name()}" value="${prcSpecimenReportInstance?.reviewType1?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'reviewDate1', 'errors')}">
              <label for="reviewDate1"><g:message code="PrcSpecimenReport.reviewDate1.label" default="Date completed" /></label>
              <calendar:datePicker name="reviewDate1" precision="day" value="${prcSpecimenReportInstance?.reviewDate1}" default="none" />
          </td>                                
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'digitalSig1', 'errors')}">
              <label for="digitalSig1"><g:message code="PrcSpecimenReport.digitalSig1.label" default="Signature" /></label>
              <g:textField name="digitalSig1" value="${prcSpecimenReportInstance?.digitalSig1}" />
          </td>
      </tr>
      <tr class="prop">
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'authorizedReviewer2', 'errors')}">
              <label for="authorizedReviewer2"><g:message code="PrcSpecimenReport.authorizedReviewer2.label" default="Reviewer 2" /></label>
              <g:select name="authorizedReviewer2" from="${nci.obbr.cahub.prc.PrcSpecimenReport$CAPTStaff?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$CAPTStaff?.values()*.name()}" value="${prcSpecimenReportInstance?.authorizedReviewer2?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'reviewType2', 'errors')}">
              <label for="reviewType2"><g:message code="PrcSpecimenReport.additonalReview2.label" default="Type of review" /></label>
              <g:select name="reviewType2" from="${nci.obbr.cahub.prc.PrcSpecimenReport$ReviewType?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$ReviewType?.values()*.name()}" value="${prcSpecimenReportInstance?.reviewType2?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'reviewDate2', 'errors')}">
              <label for="reviewDate2"><g:message code="PrcSpecimenReport.reviewDate2.label" default="Date completed" /></label>
              <calendar:datePicker name="reviewDate2" precision="day" value="${prcSpecimenReportInstance?.reviewDate2}" default="none" />
          </td>                                
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'digitalSig2', 'errors')}">
              <label for="digitalSig2"><g:message code="PrcSpecimenReport.digitalSig2.label" default="Signature" /></label>
              <g:textField name="digitalSig2" value="${prcSpecimenReportInstance?.digitalSig2}" />
          </td>
      </tr>
      <tr class="prop">
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'authorizedReviewer3', 'errors')}">
              <label for="authorizedReviewer3"><g:message code="PrcSpecimenReport.authorizedReviewer3.label" default="Reviewer 3" /></label>
              <g:select name="authorizedReviewer3" from="${nci.obbr.cahub.prc.PrcSpecimenReport$CAPTStaff?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$CAPTStaff?.values()*.name()}" value="${prcSpecimenReportInstance?.authorizedReviewer3?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'reviewType3', 'errors')}">
              <label for="reviewType3"><g:message code="PrcSpecimenReport.additonalReview3.label" default="Type of review" /></label>
              <g:select name="reviewType3" from="${nci.obbr.cahub.prc.PrcSpecimenReport$ReviewType?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$ReviewType?.values()*.name()}" value="${prcSpecimenReportInstance?.reviewType3?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'reviewDate3', 'errors')}">
              <label for="reviewDate3"><g:message code="PrcSpecimenReport.reviewDate3.label" default="Date completed" /></label>
              <calendar:datePicker name="reviewDate3" precision="day" value="${prcSpecimenReportInstance?.reviewDate3}" default="none" />
          </td>                                
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'digitalSig3', 'errors')}">
              <label for="digitalSig3"><g:message code="PrcSpecimenReport.digitalSig3.label" default="Signature" /></label>
              <g:textField name="digitalSig3" value="${prcSpecimenReportInstance?.digitalSig3}" />
          </td>
      </tr>
      <tr class="prop">
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'authorizedReviewer4', 'errors')}">
              <label for="authorizedReviewer4"><g:message code="PrcSpecimenReport.authorizedReviewer4.label" default="Reviewer 4" /></label>
              <g:select name="authorizedReviewer4" from="${nci.obbr.cahub.prc.PrcSpecimenReport$CAPTStaff?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$CAPTStaff?.values()*.name()}" value="${prcSpecimenReportInstance?.authorizedReviewer4?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'reviewType4', 'errors')}">
              <label for="reviewType4"><g:message code="PrcSpecimenReport.additonalReview4.label" default="Type of review" /></label>
              <g:select name="reviewType4" from="${nci.obbr.cahub.prc.PrcSpecimenReport$ReviewType?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$ReviewType?.values()*.name()}" value="${prcSpecimenReportInstance?.reviewType4?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'reviewDate4', 'errors')}">
              <label for="reviewDate4"><g:message code="PrcSpecimenReport.reviewDate4.label" default="Date completed" /></label>
              <calendar:datePicker name="reviewDate4" precision="day" value="${prcSpecimenReportInstance?.reviewDate4}" default="none" />
          </td>                                
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'digitalSig4', 'errors')}">
              <label for="digitalSig4"><g:message code="PrcSpecimenReport.digitalSig4.label" default="Signature" /></label>
              <g:textField name="digitalSig4" value="${prcSpecimenReportInstance?.digitalSig4}" />
          </td>
      </tr>
      <tr class="prop">
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'authorizedReviewer5', 'errors')}">
              <label for="authorizedReviewer5"><g:message code="PrcSpecimenReport.authorizedReviewer5.label" default="Reviewer 5" /></label>
              <g:select name="authorizedReviewer5" from="${nci.obbr.cahub.prc.PrcSpecimenReport$CAPTStaff?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$CAPTStaff?.values()*.name()}" value="${prcSpecimenReportInstance?.authorizedReviewer5?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'reviewType5', 'errors')}">
              <label for="reviewType5"><g:message code="PrcSpecimenReport.additonalReview5.label" default="Type of review" /></label>
              <g:select name="reviewType5" from="${nci.obbr.cahub.prc.PrcSpecimenReport$ReviewType?.values()}" keys="${nci.obbr.cahub.prc.PrcSpecimenReport$ReviewType?.values()*.name()}" value="${prcSpecimenReportInstance?.reviewType5?.name()}" noSelection="['': '']" />
          </td>
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'reviewDate5', 'errors')}">
              <label for="reviewDate5"><g:message code="PrcSpecimenReport.reviewDate5.label" default="Date completed" /></label>
              <calendar:datePicker name="reviewDate5" precision="day" value="${prcSpecimenReportInstance?.reviewDate5}" default="none" />
          </td>                                
          <td valign="top" class="name ${hasErrors(bean: prcSpecimenReportInstance, field: 'digitalSig5', 'errors')}">
              <label for="digitalSig5"><g:message code="PrcSpecimenReport.digitalSig5.label" default="Signature" /></label>
              <g:textField name="digitalSig5" value="${prcSpecimenReportInstance?.digitalSig5}" />
          </td>
      </tr>                        

</table>
