
<g:render template="caseDetails" bean="${bpvSixMonthFollowUpInstance.caseRecord}" var="caseRecord"/>
<g:set var="labelNumber" value="${1}"/>

<div class="list">
  <div  class="div-t">
    <div class="div-t-hcenter">6 Months Collection Data <br><p class="div-t-hcentsmallItal"> Please complete for all donor case where tissue was collected/processed and pathology reviewed</p></div>


    <div class="div-t-r clearfix">
      <div >

        <div class="left width-30"><label for="donorStatus">Donor Status: </label></div>

        <div class="left width-30 value ${hasErrors(bean: bpvSixMonthFollowUpInstance, field: 'donorStatus', 'errors')}">
          <div>
            <g:radio  id="donliv" name="donorStatus"  value="Living" checked="${bpvSixMonthFollowUpInstance.donorStatus =='Living'}" /><label for="donliv">Living</label> <br>
            <g:radio id="dondec" name="donorStatus"  value="Deceased" checked="${bpvSixMonthFollowUpInstance.donorStatus =='Deceased'}" /><label for="dondec">Deceased</label>
          </div>
        </div>
      </div>
    </div>

    <div class="div-t-r clearfix">
      

        <div class="left width-30">  <label for="recurrence">Recurrence: </label></div>

        <div class="left width-30 value ${hasErrors(bean: bpvSixMonthFollowUpInstance, field: 'recurrence', 'errors')}">
          <div >
          <g:radio  id="recno" name="recurrence"  value="No recurrence" checked="${bpvSixMonthFollowUpInstance.recurrence =='No recurrence'}" /><label for="recno">No recurrence</label> <br>
          <g:radio id="recloc" name="recurrence"  value="Local regional recurrence" checked="${bpvSixMonthFollowUpInstance.recurrence =='Local regional recurrence'}" /><label for="recloc">Local/regional recurrence</label><br>
          <g:radio id="recmetas" name="recurrence"  value="Metastasis" checked="${bpvSixMonthFollowUpInstance.recurrence =='Metastasis'}" /><label for="recmetas">Metastasis</label>
        </div>
      </div>
    </div>
  



  <div class="div-t-r clearfix">
    <div class="${hasErrors(bean: bpvSixMonthFollowUpInstance, field: 'comments', 'errors')}"><label for="visit">Comments: </label><br>

      <g:textArea class="textwide" name="comments" cols="40" rows="5" value="${bpvSixMonthFollowUpInstance?.comments}" />
    </div>
  </div>


  <g:if test="${bpvSixMonthFollowUpInstance.dmReviewDate}">

    <div class="div-t-r clearfix">
      <div >

        <div class="left width-30">  <label for="recurrence">DM Review Date: </label></div>

        <div class="left width-30 value"> 
          <g:formatDate date="${bpvSixMonthFollowUpInstance.dmReviewDate}" />
        </div>

      </div>
    </div>
  </g:if>


</div>
</div>

