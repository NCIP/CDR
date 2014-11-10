<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<h1>BBRB Help Home</h1>
<g:if test="${flash.message}">
  <div class="message">${flash.message}</div>
</g:if>
  <h3>Download Help Files</h3>
<div class="list">
     <table>
      <thead>
        <tr>
          <g:sortableColumn style="white-space:nowrap" property="fileName" title="${message(code: 'helpFileUpload.fileName.label', default: 'File Name')}" />
          <g:sortableColumn style="white-space:nowrap" property="studyCode" title="${message(code: 'helpFileUpload.studyCode.label', default: 'Study Code')}" />
          <g:sortableColumn style="white-space:nowrap" property="dateCreated" title="${message(code: 'helpFileUpload.dateCreated.label', default: 'Date Uploaded')}" />
          <g:if test="${session.org.code == 'OBBR' && session.DM == true}">
           <th></th>
          </g:if>
        </tr>
      </thead>
      <tbody>
    <g:each in="${helpFileUploadInstanceList}" status="i" var="helpFileUploadInstance">
      <g:if test="${session.org.code == 'OBBR' && !session.DM == true}">
        <g:if test="${helpFileUploadInstance.studyCode.equals('ALL') || helpFileUploadInstance.studyCode.equals('OBBR') }">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><a style="white-space:nowrap" href="/cahubdataservices/help/download/${helpFileUploadInstance.id}" class="filelabel" title="Download">${fieldValue(bean: helpFileUploadInstance, field: "fileName")}</a>
              <a href="/cahubdataservices/help/download/${helpFileUploadInstance.id}" class="uibutton removepadding" title="Download"><span class="ui-icon ui-icon-circle-arrow-s">Download</span></a>
              <h6>Comments</h6>
              <p>${fieldValue(bean: helpFileUploadInstance, field: "comments")}</p>
            </td>

            <td style="white-space:nowrap" class="filelabel"> ${fieldValue(bean: helpFileUploadInstance, field: "studyCode")}</td>
            <td style="white-space:nowrap"><g:formatDate date="${helpFileUploadInstance.dateCreated}" /></td>

          <g:if test="${session.org.code == 'OBBR' && session.DM == true}">
            <td><a href="/cahubdataservices/help/remove/${helpFileUploadInstance.id}" class="uibutton removepadding" title="Remove"><span class="ui-icon ui-icon-trash">Remove</span></a></td>
          </g:if>
          </tr>
        </g:if>
      </g:if>

      <g:else>
        <!-- show all files irrespective of study code-->
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          <td><a style="white-space:nowrap" href="/cahubdataservices/help/download/${helpFileUploadInstance.id}" class="filelabel" title="Download">${fieldValue(bean: helpFileUploadInstance, field: "fileName")}</a>
            <a href="/cahubdataservices/help/download/${helpFileUploadInstance.id}" class="uibutton removepadding" title="Download"><span class="ui-icon ui-icon-circle-arrow-s">Download</span></a>
            <h6>Comments</h6>
            <p>${fieldValue(bean: helpFileUploadInstance, field: "comments")}</p>
          </td>
          <td style="white-space:nowrap" class="filelabel"> ${fieldValue(bean: helpFileUploadInstance, field: "studyCode")}</td>
          <td style="white-space:nowrap"><g:formatDate date="${helpFileUploadInstance.dateCreated}" /></td>

        <g:if test="${session.org.code == 'OBBR' && session.DM == true}">
          <td><a href="/cahubdataservices/help/remove/${helpFileUploadInstance.id}" class="uibutton removepadding" title="Remove"><span class="ui-icon ui-icon-trash">Remove</span></a></td>
        </g:if>
        </tr>

      </g:else>
    </g:each>
    </tbody>
  </table>
</div>  

