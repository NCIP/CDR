<%@ page import="nci.obbr.cahub.staticmembers.Module" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<g:hasErrors bean="${slideRecord}">
    <div class="errors">
        <g:renderErrors bean="${slideRecord}" as="list"/>
    </div>
</g:hasErrors>

<table>
    <tbody>
      <tr>
          <th valign="top" class="name">
              Slide ID
          </th>
          <th valign="top" class="name">
              Module
          </th>
          <th class="editOnly" valign="top" style="text-align: center">
              Action
          </th>
      </tr>
      <g:each in="${slides}" status="i" var="slide">
          <tr class="${i%2 == 0 ? 'even' : 'odd'}">
              <td> <g:textField name="slideId_${slide.id}"  value="${slide?.slideId}"/></td>
                                           <g:if test="${CaseRecord.findById(cid).bpvWorkSheet && CaseRecord.findById(cid).bpvWorkSheet.formVersion==1}">
                                          <td><g:select name="module_${slide.id}" id="module" from="${[Module.findByCode('MODULE1'),Module.findByCode('MODULE2'), Module.findByCode('MODULE3N'), Module.findByCode('MODULE4N')] }" optionKey="id" value="${slide?.module?.id}"  noSelection="['': '']" /> </td>
                                          </g:if>
                                          <g:else>
                                              <td><g:select name="module_${slide.id}" id="module" from="${[Module.findByCode('MODULE1'),Module.findByCode('MODULE2'), Module.findByCode('MODULE5')] }" optionKey="id" value="${slide?.module?.id}"  noSelection="['': '']" /> </td>
                                          </g:else>
                                           <td style="text-align: center" class="editOnly timeEntry">
                                             <g:if test="${!slide.specimenRecord}">
                                              <g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" action="deleteSlide2" value="Delete" update="slideDialog" params="'caseId=${slide.caseId}'" id="${slide.id}">
                                                <span class="ui-icon ui-icon-trash">Delete</span>
                                               </g:remoteLink>
                                          </g:if>
                                           <g:else>
                                             &nbsp;
                                           </g:else>
                                           </td>
          </tr>
      </g:each>
    </tbody>
</table>
