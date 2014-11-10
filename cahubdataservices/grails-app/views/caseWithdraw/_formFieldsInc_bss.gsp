<script type="text/javascript">
 $(document).ready(function() {

      
        
          $("#withdrawReqType7").click(function() {
             if(this.checked) {
                 $("#withdrawReqType8").show()
                 $("#withdrawReqType8").focus()
             } else {
                 $("#withdrawReqType8").hide()
                 $("#withdrawReqType8").val('')
             }
         });
        
                 
          $("#reason9").click(function() {
             if(this.checked) {
                 $("#reason10").show()
                 $("#reason10").focus()
             } else {
                 $("#reason10").hide()
                 $("#reason10").val('')
             }
         });
         
         
          $("#requestInitiator1").click(function(){
              
                     $("#l5").hide()
                     $("#l6").hide()
                     $("#l7").hide()
   
         });
                
         
         
        
         
         $("#requestInitiator2").click(function() {
                    $("#l5").hide()
                     $("#l6").hide()
                     $("#l7").hide()
           
         });
         
         $("#requestInitiator3").click(function() {
                    $("#l5").show()
                     $("#l6").show()
                     $("#l7").show()
           
           
            });
         
         });
         
         
        
</script>    


<h2>Section A: Case Recall Request Information</h2>

<div class="list">
  <table>
    <tbody>



      <tr class="prop">
        <td valign="top" class="name">
          <label for="dateWithdrawalRcvd"><g:message code="caseWithdrawInstance.dateWithdrawalRcvd.label" default="Date Case Recall request made:" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'dateWithdrawalRcvd', 'errors')}">

    <g:jqDatePicker name="dateWithdrawalRcvd" value="${caseWithdrawInstance?.dateWithdrawalRcvd}" />
    </td>
    </tr>



    <tr class="prop">
      <td valign="top" class="name">
        <label for="withdrawReqType">Request initiator:</label>
      </td>




      <td  class="value ${hasErrors(bean: caseWithdrawInstance, field: 'requestInitiator', 'errors')}">
        <div>    
          <g:radio name="requestInitiator" id="requestInitiator1" value="NOK" checked="${caseWithdrawInstance?.requestInitiator == 'NOK'}"/> <label class="radio" for="requestInitiator1">NOK</label><span data-msg="Next of Kin" class="cahub-tooltip"></span> <br>
          <g:radio name="requestInitiator" id="requestInitiator2" value="Donor" checked="${caseWithdrawInstance?.requestInitiator == 'Donor'}"/> <label class="radio" for="requestInitiator2">Donor</label><br>
          <g:radio name="requestInitiator" id="requestInitiator3" value="Requestor" checked="${caseWithdrawInstance?.requestInitiator == 'Requestor'}"/> <label class="radio" for="requestInitiator3">Requestor</label><span data-msg="A requestor is one who initiates on behalf of the donor" class="cahub-tooltip"></span> &nbsp;&nbsp;&nbsp;<br>

        </div>
      </td>
    </tr>


    <tr class="prop ckb1" >
      <td valign="top" class="name">
        Reason for Case Recall: 
      </td>
      <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'reasonMedical', 'errors')}">
        <div>
          <label for="reason1">   <g:checkBox class="ckb1" id ="reason1" name="reasonMedical" value="${caseWithdrawInstance?.reasonMedical}" checked="${caseWithdrawInstance?.reasonMedical}" /> Medical</label><br/>
          <label for="reason2">     <g:checkBox  class="ckb1" id ="reason2" name="reasonPersonal" value="${caseWithdrawInstance?.reasonPersonal}" checked="${caseWithdrawInstance?.reasonPersonal}"/> Personal</label><br/>
          <label for="reason3">      <g:checkBox class="ckb1" id ="reason3" name="reasonCultural" value="${caseWithdrawInstance?.reasonCultural}" checked="${caseWithdrawInstance?.reasonCultural}"/> Cultural</label><br/>
          <label for="reason4">      <g:checkBox class="ckb1" id ="reason4" name="reasonReligious" value="${caseWithdrawInstance?.reasonReligious}" checked="${caseWithdrawInstance?.reasonReligious}"/> Religious</label><br/>
          <g:if test="${caseWithdrawInstance?.requestInitiator == 'NOK' || caseWithdrawInstance?.requestInitiator == 'Donor'}">
            <label id="l5" style="display:none" for="reason5">      <g:checkBox class="ckb1"  id ="reason5" name="reasonUndocumConsent" value="${caseWithdrawInstance?.reasonUndocumConsent}" checked="${caseWithdrawInstance?.reasonUndocumConsent}" /> Undocumented consent (no signed consent found)<br/></label>
            <label id="l6" style="display:none" for="reason6">      <g:checkBox class="ckb1"  id ="reason6" name="reasonProcError" value="${caseWithdrawInstance?.reasonProcError}" checked="${caseWithdrawInstance?.reasonProcError}"/> Processing Error <br/></label>
            <label id="l7" style="display:none" for="reason7">      <g:checkBox class="ckb1"  id ="reason7" name="reasonInclExcl" value="${caseWithdrawInstance?.reasonInclExcl}" checked="${caseWithdrawInstance?.reasonInclExcl}"/> Inclusion/Exclusion criteria<br/></label>
          </g:if>
          <g:else>
            <label id="l5" style="display:block" for="reason5">      <g:checkBox class="ckb1"  id ="reason5" name="reasonUndocumConsent" value="${caseWithdrawInstance?.reasonUndocumConsent}" checked="${caseWithdrawInstance?.reasonUndocumConsent}" /> Undocumented consent (no signed consent found)<br/></label>
            <label id="l6" style="display:block" for="reason6">      <g:checkBox class="ckb1"  id ="reason6" name="reasonProcError" value="${caseWithdrawInstance?.reasonProcError}" checked="${caseWithdrawInstance?.reasonProcError}"/> Processing Error <br/></label>
            <label id="l7" style="display:block" for="reason7">      <g:checkBox class="ckb1"  id ="reason7" name="reasonInclExcl" value="${caseWithdrawInstance?.reasonInclExcl}" checked="${caseWithdrawInstance?.reasonInclExcl}"/> Inclusion/Exclusion criteria<br/></label>
          </g:else>
          <label for="reason8">       <g:checkBox class="ckb1" id ="reason8" name="reasonUnknown" value="${caseWithdrawInstance?.reasonUnknown}" checked="${caseWithdrawInstance?.reasonUnknown}"/> Unknown/not given</label><br/>
          <label for="reason9">      <g:checkBox class="ckb1"  id ="reason9" name="reasonOther" value="${caseWithdrawInstance?.reasonOther}" checked="${caseWithdrawInstance?.reasonOther}"/> Other, please specify</label><br/>
          <label for="reason10">      <g:textField  id="reason10"  style="display:${caseWithdrawInstance?.reasonOther ?'display':'none'}" name="reasonOtherDescrip" value="${caseWithdrawInstance?.reasonOtherDescrip}" /></label>&nbsp;
        </div>
      </td>
    </tr>

    <!--
        <tr class="prop">
          <td valign="top" class="name">
            <label for="reasonComments">Comments: (for above choices)</label>
          </td>
          <td valign="top" class="value  ${hasErrors(bean: caseWithdrawInstance, field: 'reasonComments', 'errors')}">
        <g:textArea name="reasonComments" cols="40" rows="5" value="${caseWithdrawInstance?.reasonComments}" />
        </td>
        </tr>
    
    -->

    <tr class="prop">
      <td colspan="2" class="name ${hasErrors(bean: caseWithdrawInstance, field: 'reasonComments', 'errors')}">
        <label for="reasonComments">Comments: (for above choices)</label><br />
    <g:textArea class="textwide" name="reasonComments" cols="40" rows="5" value="${caseWithdrawInstance?.reasonComments}" />
    </td>
    </tr>


    <tr class="prop">
      <td valign="top" class="name">
        <label for="withdrawReqType">Type of Case Recall request:</label>
      </td>




      <td  class="value ${hasErrors(bean: caseWithdrawInstance, field: 'withdrawReqType', 'errors')}">
        <div>    
          <g:radio name="withdrawReqType" id="withdrawReqType1" value="All specimens and data" checked="${caseWithdrawInstance?.withdrawReqType == 'All specimens and data'}"/> <label class="radio" for="withdrawReqType1">All specimens and data</label><br>
          <g:radio name="withdrawReqType" id="withdrawReqType2" value="Specimen only" checked="${caseWithdrawInstance?.withdrawReqType == 'Specimen only'}"/> <label class="radio" for="withdrawReqType2">Specimen only</label><br>

          <g:radio name="withdrawReqType" id="withdrawReqType3" value="Data only" checked="${caseWithdrawInstance?.withdrawReqType == 'Data only'}"/> <label class="radio" for="withdrawReqType3">Data only</label><br>
          <g:radio name="withdrawReqType" id="withdrawReqType4" value="Follow Up" checked="${caseWithdrawInstance?.withdrawReqType == 'Follow Up'}"/> <label class="radio" for="withdrawReqType5">Follow up</label>&nbsp;&nbsp;&nbsp;<br>
          <g:radio name="withdrawReqType" id="withdrawReqType5" value="Slides only" checked="${caseWithdrawInstance?.withdrawReqType == 'Slides only'}"/> <label class="radio" for="withdrawReqType5">Slides only</label><br>
          <g:radio name="withdrawReqType" id="withdrawReqType6" value="Partial withdrawal of specimen and associated data" checked="${caseWithdrawInstance?.withdrawReqType == 'Partial withdrawal of specimen and associated data'}"/> <label class="radio" for="withdrawReqType6">Partial withdrawal of specimen and associated data</label><br>
          <g:radio name="withdrawReqType" id="withdrawReqType7" value="Other" checked="${caseWithdrawInstance?.withdrawReqType == 'Other'}"/> <label class="radio" for="withdrawReqType7">Other</label><br>
          <g:textField id="withdrawReqType8"  style="display:${caseWithdrawInstance?.withdrawReqType == 'Other'?'display':'none'}" name="withdrawReqTypeOther" value="${caseWithdrawInstance?.withdrawReqTypeOther}" />&nbsp;
        </div>
      </td>
    </tr>

    <!--display all specimen IDS here-->
    <tr class="prop"><td valign="top" class="name formheader" colspan="3">Specimens that will be Recalled (${caseWithdrawInstance.caseRecord.specimens.size()}):</td></tr>
    <tr>
      <td valign="top" class="value" colspan="3">
        <div class="list">
          <table class="nowrap">
            <thead>
              <tr>

                <th>Specimen Id</th>
                <th>Tissue Type</th>
                <th>Slide ID</th>


              </tr>
            </thead>
            <tbody>
            <g:each in="${caseWithdrawInstance.caseRecord.specimens}" status="i" var="s">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                <td class="itemid">${s.specimenId}</td>

                <td>${s.tissueType}</td>

                <td><g:if test="${s.slides}"> ${(s.slides.slideId).join(',')}</g:if></td>




              </tr>
            </g:each>
            </tbody>
          </table>
        </div>
      </td>

    </tr>
    <!--END display all specimen IDS -->



    <tr class="prop">
      <td valign="top" class="name">
        <label for="bssNameCompletingForm">Name of person completing form: </label>
      </td>
      <td valign="top" class="value  ${hasErrors(bean: caseWithdrawInstance, field: 'bssNameCompletingForm', 'errors')}">
    <g:hiddenField name="bssNameCompletingForm" value="${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}" />

    <g:if test="${caseWithdrawInstance.bssNameCompletingForm}">
${caseWithdrawInstance.bssNameCompletingForm}
    </g:if>
    <g:else>
${session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()}
    </g:else>
    </td>
    </tr>

    <g:if test="${caseWithdrawInstance?.bssNameCompletingForm}">

      <tr class="prop">
        <td valign="top" class="name">
          <label for="dateBSSEntrySubmitted"><g:message code="caseWithdrawInstance.dateBSSEntrySubmitted.label" default="Date form completed:" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: caseWithdrawInstance, field: 'dateBSSEntrySubmitted', 'errors')}">

          <g:formatDate date="${caseWithdrawInstance?.dateBSSEntrySubmitted}" />
    
        </td>
      </tr>
    </g:if>

   
    <tr class="prop">
      <td colspan="2" class="name ${hasErrors(bean: caseWithdrawInstance, field: 'bssSiteComments', 'errors')}">
        <label for="bssSiteComments">Site comments:</label><br />
    <g:textArea class="textwide" name="bssSiteComments" cols="40" rows="5" value="${caseWithdrawInstance?.bssSiteComments}" />
    </td>
    </tr>



    </tbody>
  </table>
</div>
