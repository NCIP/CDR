<%@ page import="nci.obbr.cahub.util.FileUpload" %>
<%@ page import="nci.obbr.cahub.staticmembers.CaseAttachmentType" %>

<g:set var="uploadcategory" value="CR${authority} "/>

<BR><BR><BR>
<div>

<g:if test="${FileUpload?.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId,CaseAttachementType?.findByCode(uploadcategory))}">


  <div class="list">
    <table>
      <thead>
        <tr>
          
          <th>File Name</th>
          <th class="dateentry">Date Uploaded</th>
          <th> </th> 
       
      
      </tr>
      </thead>
      <tbody>
      <g:each in="${FileUpload?.findAllByCaseIdAndCategory(caseWithdrawInstance?.caseRecord?.caseId,CaseAttachementType?.findByCode(uploadcategory))}" status="i" var="fileUploadInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          <td><g:link controller="fileUpload" action="download" id="${fileUploadInstance.id}">${fileUploadInstance.fileName}</g:link></td>
        <td><nobr><g:formatDate date="${fileUploadInstance.uploadTime}" /></nobr></td>
       
      
          <td>
          <g:link class="ui-button ui-state-default ui-corner-all removepadding" controller="fileUpload" action="remove" id="${fileUploadInstance.id}" onclick="return confirm('Are you sure to remove the file?')"><span class="ui-icon ui-icon-trash">Remove</span></g:link>
          </td>
       
       
        </tr>
      </g:each>
      </tbody>
    </table>
  </div>
</g:if>
</div>
