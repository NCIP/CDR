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
              <td>${slide.slideId}</td>
                <td> ${slide?.module}</td>
              <td style="text-align: center" class="editOnly timeEntry">
                  <g:remoteLink class="deleteOnly button ui-button  ui-state-default ui-corner-all removepadding" action="deleteSlide" value="Delete" update="slideDialog" params="'caseId=${slide.caseId}'" id="${slide.id}">
                      <span class="ui-icon ui-icon-trash">Delete</span>
                  </g:remoteLink>
              </td>
          </tr>
      </g:each>
    </tbody>
</table>
