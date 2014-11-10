<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<g:set var="bodyclass" value="candidaterecord view" scope="request"/>
<g:set var="fcount" value="${1}" />
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'candidateRecord.label', default: 'Candidate Record')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.view.label" args="[entityName]" /> Details for ${candidateRecordInstance.candidateId}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${candidateRecordInstance}">
            <div class="errors">
                <g:renderErrors bean="${candidateRecordInstance}" as="list" />
            </div>
            </g:hasErrors>
            <div id="view">
            <g:form  method="post" >
                <g:hiddenField name="id" value="${candidateRecordInstance?.id}" />
                <g:hiddenField name="version" value="${candidateRecordInstance?.version}" />
                <div class="dialog">
                    <g:if test="${candidateRecordInstance.study?.code == 'BPV'}">
                        <g:render template="formFieldsBpvInc" />
                    </g:if>
                    <g:else>
                        <g:render template="formFieldsInc" />
                    </g:else>
                  
                    <g:if test="${session.DM == true}">
                        <table>
                          <tbody>
                            <tr class="prop"><td valign="top" class="name formheader" colspan="2">Query Tracker Attachments:</td></tr>
                            <tr>
                              <td valign="top" class="value" colspan="2">
                                <g:if test="${qtAttachments}">
                                  <div class="list">
                                    <table>
                                      <thead>
                                        <tr>
                                          <th>File Name</th>
                                          <th class="dateentry">Date Uploaded</th>
                                          <th>Category</th>
                                          <g:if test="${session.org.code == 'OBBR'}">
                                            <th><nobr> Hide From BSS</nobr></th>
                                          </g:if>
                                          <th>Comments</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <g:each in="${qtAttachments}" status="i" var="qtAttachment">
                                          <g:if test="${qtAttachment.hideFromBss == true && session.org.code != 'OBBR'}"><!--dont show file for a BSS if 'hide from BSS' is selected --> </g:if>
                                          <g:else>
                                            <g:set var="fcount" value="${fcount + 1}" />
                                            <tr class="${(fcount % 2) == 0 ? 'odd' : 'even'}">
                                              <td><g:link controller="fileUpload" action="download" id="${qtAttachment.id}">${qtAttachment.fileName}</g:link></td>
                                              <td><nobr>${qtAttachment.uploadTime}</nobr></td>
                                              <td><nobr>${qtAttachment.category}</nobr></td>
                                              <g:if test="${session.org.code == 'OBBR'}">
                                                <td><nobr>${qtAttachment.hideFromBss ?'Yes':'No'}</nobr></td>
                                              </g:if>
                                              <td class="unlimitedstr"><div>${fieldValue(bean: qtAttachment, field: "comments")}</div></td>
                                            </tr>
                                          </g:else>
                                        </g:each>
                                      </tbody>
                                    </table>
                                  </div>
                                </g:if>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                    </g:if>
                  
                </div>
            </g:form>
            </div>
        </div>
      
        <script type="text/javascript">
            $(document).ready(function(){
                $('#view :input').attr('disabled', true);
            });

        </script>
      
    </body>
</html>
