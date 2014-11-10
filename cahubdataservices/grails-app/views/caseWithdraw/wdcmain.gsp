<%@ page import="nci.obbr.cahub.forms.common.withdraw.CaseWithdraw" %>
<%@ page import="nci.obbr.cahub.forms.common.withdraw.CaseWithdrawForms" %>
<g:set var="bodyclass" value="casewithdraw wdcmain" scope="request"/>
<html>
  <head>
    <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'CaseRecallConsent.label', default: 'Biospecimen and Data Recall ')}" />
  <g:set var="caseId" value="${caseWithdrawInstance?.caseRecord?.caseId}" />
  <g:set var="canModify" value="${session.DM }" />
  <title><g:message code="default.create.label" args="[entityName]" /></title>

  <g:javascript>
    function setAuthority(whoami){

    document.getElementById("authority").value=whoami
    }

    function setStatus(casestatus){

    document.getElementById("caseStatus").value=casestatus
    }

    function setWithdrawFinalConfirm(){

    document.getElementById("withdrawFinalConfirm").value='YES'
    }
  </g:javascript>

</head>
<body>
  <div id="nav">
    <div id="navlist">
      <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
      <g:link class="list" controller="caseWithdraw" action="listAllRecallCases" >Case Recall Home</g:link>

    </div>
  </div>
  <div id="container" class="clearfix">
    <h1><g:message code="default.create.label.with.case.id" args="[entityName,caseId]" /></h1>

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>

    <g:hasErrors bean="${caseWithdrawInstance}">
      <div class="errors">
        <g:renderErrors bean="${caseWithdrawInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" class="tdwrap tdtop">

      <div class="dialog">
        <g:hiddenField name="caseRecord.id" value="${caseWithdrawInstance.caseRecord.id}" />
        <g:hiddenField name="caseid" value="${caseWithdrawInstance.caseRecord.id}" />
        <g:hiddenField name="id" value="${caseWithdrawInstance.caseRecord.id}" />
        <g:hiddenField name="wid" value="${caseWithdrawInstance.id}" />
        <g:hiddenField name="authority" id="authority" />
    
        <g:if test="${session.study.code.equals('GTEX')}">
          <g:render template="caseDetails" bean="${caseWithdrawInstance?.caseRecord}" var="caseRecord" />
        </g:if>
        <g:else><g:render template="/caseRecord/caseDetails" bean="${caseWithdrawInstance.caseRecord}" var="caseRecord" /> </g:else>
      </div>

      <table class="tdtop">
        <tbody>  

          <!-- INITIAL REQUEST FORM: BSS ENTRY FORM -->

          <tr colspan="3"><td> <h3> SECTION A:</h3></td> </tr> 
          <tr class="prop">

            <td> Case Recall Request Information form  </td>
        <g:if test="${!caseWithdrawInstance.dateBSSEntrySubmitted && !caseWithdrawInstance.caseStatus && session.DM == true}">
          <td><g:actionSubmit  action="create" value="START"  onclick="setAuthority('BSS')"/></td>
        </g:if>
        <g:if test="${!caseWithdrawInstance.dateBSSEntrySubmitted && caseWithdrawInstance.caseStatus.equals('STARTED') && session.DM == true}">
          <td>STARTED &nbsp;<g:actionSubmit  action="save" value="CONTINUE"  onclick="setAuthority('BSS')"/></td>
        </g:if>
        
        <g:if test="${caseWithdrawInstance.dateBSSEntrySubmitted }">

          <td> SUBMITTED   </td>

          <td><g:actionSubmit  action="show" value="VIEW"  onclick="setAuthority('BSS')"/></td>
        </g:if>
        </tr>
        

        <!--  NEXT IS  APPROVAL BY caHUB---->

        <tr colspan="3"><td> &nbsp;</td> </tr> 
        <g:if test="${caseWithdrawInstance.dateBSSEntrySubmitted}">
          <tr colspan="3"><td> <h3> SECTION B: </h3></td> </tr> 
          <tr class="prop">
            <td> Acknowledgment and Approval by caHUB </td>
          <g:if test="${!caseWithdrawInstance.dateApproved && !caseWithdrawInstance.approvedBy && session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && (session.DM == true)}">

            <td><g:actionSubmit  action="create" value="INITIATE PROCESS"  onclick="setAuthority('APR')"/></td>
          </g:if>
          <g:elseif test="${!caseWithdrawInstance.dateApproved && caseWithdrawInstance.approvedBy && session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && (session.DM == true)}">

            <td><g:actionSubmit  action="save" value="CONTINUE"  onclick="setAuthority('APR')"/></td>

          </g:elseif>
          <g:else>


            <g:if test="${caseWithdrawInstance.cahubApproveOrNot.equals('NO') }">
              <td>DENIED
              <g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB')&& !caseWithdrawInstance.caseStatus?.contains('caHUB') && session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && (session.DM == true)}">
                <g:actionSubmit  action="create" value="APPROVE"  onclick="setAuthority('APR')"/>
              </g:if>
              </td>
            </g:if>

            <g:if test="${caseWithdrawInstance.cahubApproveOrNot.equals('YES') && caseWithdrawInstance.dateApproved && caseWithdrawInstance.caseStatus?.contains('caHUB') }">

              <td>APPROVED </td>
              <td><g:actionSubmit  action="show" value="VIEW "  onclick="setAuthority('APR')"/></td>
            </g:if>
          </g:else>

          </tr>
        </g:if>


        <tr colspan="3"><td> &nbsp;</td> </tr> 



        <!-- NOW DISPLAY FORMS TO BE ENTERED BY ENTITIES(CDR,CBR,PRC,LDACC,BRAINBANK)---->

        <g:if test="${caseWithdrawInstance.cahubApproveOrNot.equals('YES') }">
          <tr colspan="3"><td> <h3> SECTION C: </h3></td> </tr> 



          <tr class="prop">
            <td> Data Recall and Recall Notification/Verifications </td>

          </tr>

          <tr class="prop">
          <g:if test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToCDR && !caseWithdrawInstance.caseStatus.contains('CDR') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CDR') && !CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CDR').dateProcessingStarted}">
            <td> CDR </td>



            <g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && session.getAttribute('DM')}">

              <td><g:actionSubmit  action="create" value="CDR VERIFY"  onclick="setAuthority('CDR')"/></td>
            </g:if>
            <g:else><td>&nbsp;</td></g:else>
          </g:if>


          <g:elseif test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToCDR && !caseWithdrawInstance.caseStatus.contains('CDR') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CDR') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CDR').dateProcessingStarted }">
            <td> CDR </td>
            <td> STARTED 
            <g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') &&session.getAttribute('DM')}">

              <g:actionSubmit  action="save" value="CDR VERIFY"   onclick="setAuthority('CDR')"/>
              </td>

            </g:if>
          </g:elseif>

          <g:elseif test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToCDR && caseWithdrawInstance.caseStatus.contains('CDR')}">
            <td> CDR </td>
            <td> PROCESSED </td>
            <td><g:actionSubmit  action="show" value="VIEW "  onclick="setAuthority('CDR')"/></td>
          </g:elseif>



          </tr>
          <tr class="prop">



          <g:if test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToCBR && !caseWithdrawInstance.caseStatus.contains('CBR') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CBR') && !CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CBR').dateProcessingStarted }">
            <td> CBR </td>
            <g:if test="${ (session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && session.getAttribute('DM')) || session.authorities?.contains('ROLE_ORG_VARI')}">

              <td><g:actionSubmit  action="create" value="CBR VERIFY"  onclick="setAuthority('CBR')"/></td>
            </g:if>
            <g:else><td>&nbsp;</td></g:else>
          </g:if>

          <g:elseif test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToCBR && !caseWithdrawInstance.caseStatus.contains('CBR') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CBR') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'CBR').dateProcessingStarted }">
            <td> CBR </td>
            <td> STARTED 
            <g:if test="${ (session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') &&session.getAttribute('DM')) || session.authorities?.contains('ROLE_ORG_VARI')}">

             <g:actionSubmit  action="save" value="CBR VERIFY"   onclick="setAuthority('CBR')"/>
              </td>
            </g:if>
            </td>
          </g:elseif>

          <g:elseif test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToCBR && caseWithdrawInstance.caseStatus.contains('CBR')}">
            <td> CBR </td>
            <td> PROCESSED </td>
            <td><g:actionSubmit  action="show" value="VIEW "  onclick="setAuthority('CBR')"/></td>
          </g:elseif>

          </tr>   

          <tr class="prop">


          <g:if test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToLDACC && !caseWithdrawInstance.caseStatus.contains('LDACC') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'LDACC') && !CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'LDACC').dateProcessingStarted }">
            <td> LDACC </td>
            <g:if test="${ (session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && session.getAttribute('DM')) || session.authorities?.contains('ROLE_ORG_BROAD')}">

              <td><g:actionSubmit  action="create" value="LDACC VERIFY"  onclick="setAuthority('LDACC')"/></td>
            </g:if>
            <g:else><td>&nbsp;</td></g:else>
          </g:if>

          <g:elseif test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToLDACC && !caseWithdrawInstance.caseStatus.contains('LDACC') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'LDACC') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'LDACC').dateProcessingStarted}">
            <td> LDACC </td>
            <td> STARTED 
            <g:if test="${ (session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && session.getAttribute('DM')) || session.authorities?.contains('ROLE_ORG_BROAD')}">

              <g:actionSubmit  action="save" value="LDACC VERIFY"   onclick="setAuthority('LDACC')"/>
              </td>
            </g:if>
            </td>
          </g:elseif>

          <g:elseif test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToLDACC && caseWithdrawInstance.caseStatus.contains('LDACC')}">
            <td> LDACC </td>
            <td> PROCESSED </td>
            <td><g:actionSubmit  action="show" value="VIEW "  onclick="setAuthority('LDACC')"/></td>
          </g:elseif>

          </tr>

          <tr class="prop">

          <g:if test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToPRC && !caseWithdrawInstance.caseStatus.contains('PRC') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'PRC') && !CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'PRC').dateProcessingStarted}">
            <td> PRC </td>   
            <g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && (session.getAttribute('DM')|| session.getAttribute('PRC') )}">

              <td><g:actionSubmit  action="create" value="PRC VERIFY"  onclick="setAuthority('PRC')"/></td>
            </g:if>
            <g:else><td>&nbsp;</td></g:else>
          </g:if>

          <g:elseif test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToPRC && !caseWithdrawInstance.caseStatus.contains('PRC') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'PRC') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'PRC').dateProcessingStarted}">
            <td> PRC </td>
            <td> STARTED 
            <g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && (session.getAttribute('DM') || session.getAttribute('PRC'))}">

              <g:actionSubmit  action="save" value="PRC VERIFY"   onclick="setAuthority('PRC')"/>
              </td>
            </g:if>
            </td>
          </g:elseif>

          <g:elseif test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToPRC && caseWithdrawInstance.caseStatus.contains('PRC')}">
            <td> PRC </td>   
            <td> PROCESSED </td>
            <td><g:actionSubmit  action="show" value="VIEW "  onclick="setAuthority('PRC')"/></td>
          </g:elseif>

          </tr>
          <tr class="prop">


          <g:if test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToBrainbank && !caseWithdrawInstance.caseStatus.contains('BRAINBANK') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'BRAINBANK') && !CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'BRAINBANK').dateProcessingStarted}">
            <td> BRAIN BANK </td>
            <g:if test="${ (session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && session.getAttribute('DM')) || session.authorities?.contains('ROLE_ORG_MBB')}">

              <td><g:actionSubmit  action="create" value="BRAINBANK VERIFY"  onclick="setAuthority('BRAINBANK')"/></td>
            </g:if>
            <g:else><td>&nbsp;</td></g:else>
          </g:if>

          <g:elseif test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToBrainbank && !caseWithdrawInstance.caseStatus.contains('BRAINBANK') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'BRAINBANK') && CaseWithdrawForms.findByCaseWithdrawAndAuthority(caseWithdrawInstance, 'BRAINBANK').dateProcessingStarted}">
            <td> BRAIN BANK </td>
            <td> STARTED 
            <g:if test="${ (session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB') && session.getAttribute('DM')) || session.authorities?.contains('ROLE_ORG_MBB')}">

             <g:actionSubmit  action="save" value="BRAINBANK VERIFY"   onclick="setAuthority('BRAINBANK')"/>
             </td>
            </g:if>
            </td>
          </g:elseif>


          <g:elseif test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.assignToBrainbank && caseWithdrawInstance.caseStatus.contains('BRAINBANK')}">
            <td> BRAIN BANK </td>

            <td> PROCESSED </td>
            <td><g:actionSubmit  action="show" value="VIEW "  onclick="setAuthority('BRAINBANK')"/></td>
          </g:elseif>


          </tr>
        </g:if>
        <tr colspan="3"><td> &nbsp;</td> </tr> 


        <!-- NEXT:  FINAL SIGNATURES/REVIEWS-->

        <g:if test="${caseWithdrawInstance.dateApproved && caseWithdrawInstance.cahubApproveOrNot.equals('YES') && finalReview?.equals('YES') }">

          <tr class="prop"><td><h3>SECTION D: FINAL REVIEWS </h3></td> 
          </tr>

          <tr class="prop">

            <td>ELR Verifications</td>
          <g:if test="${!caseWithdrawInstance.dateELRReviewed && session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB')}"> 
            <td><g:actionSubmit  action="finalReview" value="ELR Signature & Uploads"  onclick="setAuthority('ELR')"/></td> </g:if>
          <g:elseif test="${caseWithdrawInstance.dateELRReviewed}">
            <td> <span class="ui-icon ui-icon-check left"></span><h5>Reviewed</h5></td>
            <td><g:actionSubmit  action="show" value="VIEW "  onclick="setAuthority('ELR')"/></td>
          </g:elseif>


          </tr>



          <tr class="prop">

            <td>  OPS Verifications </td>

          <g:if test="${!caseWithdrawInstance.dateOPSReviewed && session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB')}"> 
            <td><g:actionSubmit  action="finalReview" value="OPS Signature & Uploads"  onclick="setAuthority('OPS')"/></td> </g:if>
          <g:elseif test="${caseWithdrawInstance.dateOPSReviewed}">
            <td> <span class="ui-icon ui-icon-check left"></span><h5>Reviewed</h5></td>
            <td><g:actionSubmit  action="show" value="VIEW "  onclick="setAuthority('OPS')"/></td>
          </g:elseif>


          </tr>

          <g:if test="${caseWithdrawInstance.dateELRReviewed && caseWithdrawInstance.dateOPSReviewed }"> 

            <tr class="prop">

              <td>  QM Verifications </td>

            <g:if test="${!caseWithdrawInstance.dateQMReviewed && session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB')}"> 
              <td><g:actionSubmit  action="finalReview" value="QM Signature & Uploads"  onclick="setAuthority('QM')"/></td> </g:if>
            <g:elseif test="${caseWithdrawInstance.dateQMReviewed}">
              <td> <span class="ui-icon ui-icon-check left"></span><h5>Reviewed</h5></td>
              <td><g:actionSubmit  action="show" value="VIEW "  onclick="setAuthority('QM')"/></td>
            </g:elseif>


            </tr>

          </g:if>

          <g:if test="${caseWithdrawInstance.dateELRReviewed && caseWithdrawInstance.dateOPSReviewed && caseWithdrawInstance.dateQMReviewed }"> 

            <tr class="prop">

              <td>  DIRECTOR Verifications </td>

            <g:if test="${!caseWithdrawInstance.dateDirectorReviewed && session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB')}"> 
              <td><g:actionSubmit  action="finalReview" value="DIRECTOR Signature & Uploads"  onclick="setAuthority('DIRECTOR')"/></td> </g:if>
            <g:elseif test="${caseWithdrawInstance.dateDirectorReviewed}">
              <td> <span class="ui-icon ui-icon-check left"></span><h5>Reviewed</h5></td>
              <td><g:actionSubmit  action="show" value="VIEW "  onclick="setAuthority('DIRECTOR')"/></td>
            </g:elseif>

          </g:if>
          </tr>

        </g:if>
        
        <!-- AT LAST COMES THE COMPLETE FORM-->
        <tr colspan="3"><td> &nbsp;</td> </tr> 
        
        <g:if test="${caseWithdrawInstance.dateELRReviewed && caseWithdrawInstance.dateOPSReviewed && caseWithdrawInstance.dateQMReviewed && caseWithdrawInstance.dateDirectorReviewed && !caseWithdrawInstance.finalCompleteDate}">
          <tr class="prop"><td><h3> SECTION E: Final Notification of Case Recall Request </h3></td> </tr>
          
           <g:if test="${session.getAttribute('DM')}">
            
            <tr class="prop"> <td>Complete Recall Notification </td> <td><g:actionSubmit  action="recallComplete" value="COMPLETE"  onclick="setAuthority('BSSCOMPLETE')"/></td> 
          </g:if>
          
        </g:if>
        <g:if test="${caseWithdrawInstance.dateELRReviewed && caseWithdrawInstance.dateOPSReviewed && caseWithdrawInstance.dateQMReviewed && caseWithdrawInstance.dateDirectorReviewed && caseWithdrawInstance.finalCompleteDate}">
          <tr class="prop">
            <td><h3> SECTION E: Final Notification of Case Recall Request</h3></td> 
            <td>COMPLETED</td>
            <td><g:actionSubmit  action="show" value="VIEW"  onclick="setAuthority('BSSCOMPLETE')"/></td>
          </tr>

        </g:if>
        
      </table>
    </g:form>


</body>
</html>
