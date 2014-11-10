<%@ page import="nci.obbr.cahub.staticmembers.SOP" %>  
<g:set var="labelNumber" value="${1}" />
<g:render template="/formMetadata/timeConstraint" bean="${bpvBloodFormInstance.formMetadata}" var="formMetadata"/>
<g:render template="/caseRecord/caseDetails" bean="${bpvBloodFormInstance.caseRecord}" var="caseRecord" />
<g:if test="${params.action == 'create'}">
    <g:set var="sopNumber" value="${bpvBloodFormInstance?.formMetadata?.sops?.get(0)?.sopNumber}" scope="request"/>
    <g:set var="sopName" value="${bpvBloodFormInstance?.formMetadata?.sops?.get(0)?.sopName}" scope="request"/>
    <g:set var="sopVersion" value="${bpvBloodFormInstance?.formMetadata?.sops?.get(0)?.activeSopVer}" scope="request"/>
</g:if><g:else>
    <g:set var="sopNumber" value="${bpvBloodFormInstance?.collectProcessSOP?.sopNumber}" scope="request"/>
    <g:set var="sopName" value="${SOP.get(bpvBloodFormInstance?.collectProcessSOP?.sopId)?.sopName}" scope="request"/>
    <g:set var="sopVersion" value="${bpvBloodFormInstance?.collectProcessSOP?.sopVersion}" scope="request"/>
</g:else>
    <div class="list" >
        <div id="bloodform-tabs">
            <ul id="tabs-list"></ul>
            <g:render template="tab1v"/>
  <g:if test="${bpvBloodFormInstance?.version == 0}">
                            <tr class="mainSaveNContinue prop clearborder bottomsection">
                              <td class="name" colspan="2">
                                <div class="buttons">
                                   <span class="button"><input type="submit" name="_action_update" value="Save and Continue" class="save" /></span>
                                </div>
                              </td>
                            </tr>
                    </table>
                </div>
            </div>
      </div>
</g:if>
<g:else>  
                <tr class="prop bottomsection"><td colspan="2" class="formheader">Blood Collection Tube Details: Enter information for each tube collected</td></tr>
                <tr class="prop subentry clearborder bottomsection">
                    <td colspan="2">
                        <div id="drawTimesKey">
                          <h4>Blood Draw Times:</h4>
                          <div id="drawTimesKey-d1">D1 - <span></span></div>
                          <div id="drawTimesKey-d2" class="greentext"></div>
                        </div>
                        <div id="ParentTubes"><g:render template="parentTable"/></div>
                    </td>
                </tr><tr id="add1Row" class="subentry clearborder bottomsection"><td colspan="2" class="${hasErrors(bean: bpvBloodFormInstance, field: 'dnaParent', 'errors')} ${hasErrors(bean: bpvBloodFormInstance, field: 'rnaParent', 'errors')}"><button class="Btn" id="addParentBtn">Add</button></td></tr>
             </table>
          </div>
            <g:render template="tab2v"/>
            <g:render template="tab3v"/>
            <g:render template="tab4v"/> 
            <g:render template="tab5v${bloodFormVersion>1?2:''}"/>  
        </div>
    </div>
</g:else>
