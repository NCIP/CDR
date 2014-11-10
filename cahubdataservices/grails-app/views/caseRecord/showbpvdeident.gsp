<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.SpecimenRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.ChpTissueRecord" %>
<g:set var="bodyclass" value="caserecord show" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate" />
        <g:set var="entityName" value="${message(code: 'caseRecord.label', default: 'Case Record')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
<script type="text/javascript" src="${resource(dir:'js',file:'caserecord.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>        
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>  
          </div>
      </div>
      <div id="container" class="clearfix">
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <h1>Show Case Record Details for ${caseRecordInstance.caseId}</h1>        


<div class="dialog">
    <table>
        <tbody>

            <tr class="prop toptable">
                <td valign="top" class="name">Case ID:</td>
                <td valign="top" class="value" id="caseRecordID">${caseRecordInstance.caseId}</td>
            </tr>
          
            <tr class="prop toptable">
                <td valign="top" class="name">Case Status:</td>
                <td valign="top" class="value" >
                    <span class="ca-tooltip-nobg" data-msg="<b>${caseRecordInstance.caseStatus}</b>. ${caseRecordInstance.caseStatus.description}">${caseRecordInstance.caseStatus}</span>                                                         
                </td>
            </tr>
            
            <tr class="prop toptable">
                <td valign="top" class="name">BSS:</td>
                <td valign="top" class="value">
                    ${caseRecordInstance?.bss.name} (${caseRecordInstance?.bss.code})
                </td>
            </tr>
            
            <tr class="prop toptable">
                <td valign="top" class="name">Primary Organ:</td>
                <td valign="top" class="value">
                    ${caseRecordInstance.primaryTissueType}  
                </td>
            </tr>            
          
           
            <tr class="prop toptable">
                <td valign="top" class="name">Study:</td>
                <td valign="top" class="value">${caseRecordInstance.study}</td>
            </tr>

           
            <tr class="prop toptable">
                <td valign="top" class="name"><g:message code="caseRecord.kits.label" default="Kits Used:" /></td>
                <td valign="top" style="text-align: left;" class="value">
                   <g:if test="${caseRecordInstance.kitList}">
                    ${caseRecordInstance.kitList.replace(',',', ')} 
                     </g:if> 
                   <g:else>&nbsp;</g:else>
                </td>
            </tr>
            
            <tr class="prop toptable">
                <td valign="top" class="name"><g:message code="caseRecord.lastUpdated.label" default="Last Updated:" /></td>
                <td valign="top" class="value"><g:formatDate date="${caseRecordInstance?.lastUpdated}" /></td>
            </tr>

            <tr class="prop toptable">
                <td valign="top" class="name"><g:message code="caseRecord.dateCreated.label" default="Date Created:" /></td>
                <td valign="top" class="value"><g:formatDate date="${caseRecordInstance?.dateCreated}" /></td>
            </tr>

            <tr class="prop toptable">
                <td valign="top" class="name">CDR Version When Created:</td>
                <td valign="top" class="value">${caseRecordInstance.cdrVer}</td>
            </tr>  

            <tr class="prop toptable">
              <td valign="top" class="name">Query Tracker:</td>
              <td valign="top" class="value">
                <a href="/cahubdataservices/query/listByCase?caseRecord.id=${caseRecordInstance.id}">View Query list (<span class="${queryCount == 0 ? 'yes' : 'no'}">${queryCount}</span>)</a>
              </td>
            </tr>
            
            <tr class="prop"><td valign="top" class="name formheader" colspan="2">Specimens (${caseRecordInstance.specimens.size()}):</td></tr>
            <tr>
                <td valign="top" colspan="2" class="value">
                    <div class="list">
                        <table id="specimenstable" class="nowrap">
                            <thead>
                                <tr>
                                    <th>1st Gen</th>
                                    <th>2nd Gen</th>
                                    <th>3rd Gen</th>
                                    <th>Tissue Type</th> 
                                     <th>Priority</th> 
                                    <th>Fixative</th>
                                    <th>Container</th>
                                    <th>Consumed?</th>
                                    <th>Slides</th>
                                    <th>Images</th> 
                                    <g:if test="${(session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB") || session.authorities?.contains("ROLE_ORG_VARI") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                                  session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_PRC") || session.authorities?.contains("ROLE_ADMIN"))}">
                                                          <th>Inv Status</th>
                                    </g:if>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    def parity = 0
                                %>
                                <g:each in="${caseRecordInstance.specimens}" status="i" var="s">
                                  <g:if test="${!s.parentSpecimen}">
                                    <tr class="${(parity++ % 2) == 0 ? 'odd' : 'even'}" id="${s.specimenId.toUpperCase()}">
                                      <td class="itemid specimenid">${s.specimenId}</td>
                                      <td></td>
                                      <td></td>
                                      <td>${s.tissueType}</td>
                                      <g:if test="${altMap.get(s.specimenId)=='PRIORITY1'}">
                                        <td>I</td>
                                      </g:if>
                                      <g:elseif test="${altMap.get(s.specimenId)=='PRIORITY2'}">
                                        <td>II</td>
                                      </g:elseif>
                                      <g:elseif test="${altMap.get(s.specimenId)=='PRIORITY3a'}">
                                        <td>IIIA</td>
                                      </g:elseif>
                                       <g:elseif test="${altMap.get(s.specimenId)=='PRIORITY3b'}">
                                        <td>IIIB</td>
                                      </g:elseif>
                                      <g:else><td>&nbsp;</td></g:else>
                                      <td>${s.fixative}</td>
                                      <td>${s.containerType}</td>
                                      <td><g:if test="${s.wasConsumed}">Yes</g:if><g:else>No</g:else></td>                            
                                      <td nowrap="nowrap">
                                        <g:each in="${s.slides?}" var="sl" status="l">
                                          ${sl.slideId}
                                          <br />
                                        </g:each>
                                      </td>
                                      <td>
                                      <g:each in="${s.slides?}" var="sl" status="l">
                                          ${sl.imageRecord?.imageId}
                                        <br />
                                      </g:each>                
                                      </td>
                                            <g:if test="${(session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB") || session.authorities?.contains("ROLE_ORG_VARI") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                                          session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_PRC") || session.authorities?.contains("ROLE_ADMIN"))}">
                                                      <td id="${s.id}"><span class="sp_status"></span></td>
                                            </g:if>
                                    </tr>
                                    <g:set var="slist" value="${SpecimenRecord.findAllByParentSpecimen(s)}" />
                                    <g:each in="${slist}" status="j" var="u">
                                      <g:if test="${!u.parentSpecimen.parentSpecimen}">
                                        <tr class="${(parity++ % 2) == 0 ? 'odd' : 'even'}" id="${u.specimenId.toUpperCase()}">
                                          <td></td>
                                          <td class="specimenid">${u.specimenId}</td>
                                          <td></td>
                                          <td>${u.tissueType}</td>
                                          <g:if test="${altMap.get(u.specimenId)=='PRIORITY1'}">
                                        <td>I</td>
                                      </g:if>
                                      <g:elseif test="${altMap.get(u.specimenId)=='PRIORITY2'}">
                                        <td>II</td>
                                      </g:elseif>
                                      <g:elseif test="${altMap.get(u.specimenId)=='PRIORITY3a'}">
                                        <td>IIIA</td>
                                      </g:elseif>
                                       <g:elseif test="${altMap.get(u.specimenId)=='PRIORITY3b'}">
                                        <td>IIIB</td>
                                      </g:elseif>
                                      <g:else><td>&nbsp;</td></g:else>
                                          <td>${u.fixative}</td>
                                          <td>${u.containerType}</td>
                                          <td><g:if test="${u.wasConsumed}">Yes</g:if><g:else>No</g:else></td>                            
                                          <td nowrap="nowrap">
                                            <g:each in="${u.slides?}" var="sl" status="l">
                                              <span style="line-height:20px;">
                                                ${sl.slideId}
                                                <br />
                                              </span>
                                            </g:each>
                                          </td>
                                        <td>
                                        <g:each in="${u.slides?}" var="sl" status="l">
                                          <span style="line-height:20px;">
                                            ${sl.imageRecord?.imageId}
                                          <br />
                                          </span>
                                        </g:each>                
                                        </td>
                                            <g:if test="${(session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB") || session.authorities?.contains("ROLE_ORG_VARI") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                                          session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_PRC") || session.authorities?.contains("ROLE_ADMIN"))}">
                                                      <td id="${u.id}"><span class="sp_status"></span></td>
                                            </g:if>                                        

                                        </tr>
                                        <g:set var="ulist" value="${SpecimenRecord.findAllByParentSpecimen(u)}" />
                                        <g:each in="${ulist}" status="k" var="v">
                                          <tr class="${(parity++ % 2) == 0 ? 'odd' : 'even'}" id="${v.specimenId.toUpperCase()}">
                                            <td></td>
                                            <td></td>
                                            <td class="specimenid">${v.specimenId}</td>
                                            <td>${v.tissueType}</td>
                                            <g:if test="${altMap.get(v.specimenId)=='PRIORITY1'}">
                                              <td>I</td>
                                            </g:if>
                                            <g:elseif test="${altMap.get(v.specimenId)=='PRIORITY2'}">
                                                  <td>II</td>
                                            </g:elseif>
                                            <g:elseif test="${altMap.get(v.specimenId)=='PRIORITY3a'}">
                                              <td>IIIA</td>
                                           </g:elseif>
                                       <g:elseif test="${altMap.get(v.specimenId)=='PRIORITY3b'}">
                                        <td>IIIB</td>
                                      </g:elseif>
                                      <g:else><td>&nbsp;</td></g:else>
                                            <td>${v.fixative}</td>
                                            <td>${v.containerType}</td>
                                            <td><g:if test="${v.wasConsumed}">Yes</g:if><g:else>No</g:else></td>                            
                                            <td nowrap="nowrap">
                                              <g:each in="${v.slides?}" var="sl" status="l">
                                                ${sl.slideId}
                                                <br />
                                              </g:each>
                                            </td>
                                            <td>
                                            <g:each in="${v.slides?}" var="sl" status="l">
                                                ${sl.imageRecord?.imageId}
                                              <br />
                                            </g:each>                
                                            </td>
                                            <g:if test="${(session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB") || session.authorities?.contains("ROLE_ORG_VARI") || session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                                          session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_PRC") || session.authorities?.contains("ROLE_ADMIN"))}">
                                                      <td id="${v.id}"><span class="sp_status"></span></td>
                                            </g:if>
                                          </tr>
                                        </g:each>
                                      </g:if>      <%-- parentSpecimen.parentSpecimen --%>
                                    </g:each>      <%-- slist --%>
                                  </g:if>          <%-- parentSpecimen --%>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
            
        </tbody>
    </table>
</div>



      </div>
        
    </body>
</html>
