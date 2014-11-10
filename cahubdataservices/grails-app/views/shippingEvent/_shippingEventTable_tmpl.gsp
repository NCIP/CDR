<%
  def shipTrackingUrls = [:]
  nci.obbr.cahub.util.AppSetting.findAllByCodeLike('CDR_SHIPPINGEVENTS_TRACKING_URL_%').each{shipTrackingUrls[it.name] = it.value}
%>
          <table>
                    <thead>
                      <tr><th colspan="9">Shipping Event List</th></tr>
                        <tr>
                            <th>Shipping Event ID</th>
                            <th>Study</th>
                            <th>Case ID</th>
                            <th>Content Type</th>
                            <th>Event Type</th>
                            <g:sortableColumn property="sender" title="Sender" />
                            <g:sortableColumn property="recipient" title="Recipient" />
                            <th>Tracking</th>
                            <g:sortableColumn property="shipDateTime" class="dateentry" title="Shipping Date" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${shippingEventInstanceList}" status="i" var="shippingEventInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link controller="shippingEvent" action="show" id="${shippingEventInstance.id}">${shippingEventInstance.bio4dEventId}</g:link></td>
                            <td>${shippingEventInstance.study}</td>
                            <td>
                                <g:if test="${!shippingEventInstance.caseId}">
                                    N/A
                                </g:if>
                                <g:else>
                                    <g:set var="caseRecordInstance" value="${ nci.obbr.cahub.datarecords.CaseRecord.findByCaseId(shippingEventInstance.caseId) }" />
                                    <g:if test="${!caseRecordInstance}">
                                        <font color="red">${shippingEventInstance.caseId}(Not Found)</font>
                                    </g:if>
                                    <g:else>
                                        <g:if test="${shippingEventInstance.study.toString().equalsIgnoreCase('BPV')}">
                                            <g:displayCaseRecordLink caseRecord="${caseRecordInstance}" session="${session}"/>
                                            %{--
                                            <g:if test="${caseRecordInstance.caseStatus.code == 'COMP' || caseRecordInstance.caseStatus.code == 'RELE' || session.org.code == caseRecordInstance.bss.code}">
                                              <span class="${caseRecordInstance?.caseStatus?.code == 'DATA' && caseRecordInstance?.bpvSurgeryAnesthesiaForm?.surgeryDate?.plus(21)?.before(new Date()) ? 'redlink' : ''}">
                                                <g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link>
                                              </span>
                                            </g:if>
                                            <g:elseif test='${session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_DM") ||
                                                              session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_SUPER") ||
                                                              session.authorities?.contains("ROLE_NCI-FREDERICK_CAHUB_LDS") ||
                                                              session.authorities?.contains("ROLE_ADMIN")}'>         
                                              <span class="${caseRecordInstance?.caseStatus?.code == 'DATA' && caseRecordInstance?.bpvSurgeryAnesthesiaForm?.surgeryDate?.plus(21)?.before(new Date()) ? 'redlink' : ''}">
                                                <g:link controller="caseRecord" action="display" id="${caseRecordInstance.id}">${caseRecordInstance.caseId}</g:link>
                                              </span>
                                            </g:elseif>
                                            <g:else>
                                                ${caseRecordInstance.caseId}                        
                                            </g:else>  
                                            --}%
                                        </g:if>
                                        <g:else>
                                            <g:if test="${session.org.code == 'OBBR'}">
                                                <g:link controller="caseRecord" action="display" id="${shippingEventInstance.caseId}">${shippingEventInstance.caseId}</g:link>
                                            </g:if>
                                            <g:else>
                                                ${shippingEventInstance.caseId}
                                            </g:else>                      
                                        </g:else> 
                                    </g:else>
                                 
                                </g:else>
                            </td>
                            <td>${shippingEventInstance.shippingContentType}</td>
                            <td>${shippingEventInstance.shippingEventType}</td>
                            <td>${shippingEventInstance.sender}</td>
                            <td>${shippingEventInstance.recipient}</td>
                            <td><g:if test="${shippingEventInstance.courier != null && shipTrackingUrls.get(shippingEventInstance.courier.toLowerCase()) != null && shippingEventInstance.trackingNumber.length() > 4}"><a target="_blank" href="${shipTrackingUrls.get(shippingEventInstance.courier.toLowerCase())}${shippingEventInstance.trackingNumber}" title="Track ${shippingEventInstance.trackingNumber}">${shippingEventInstance.trackingNumber}</a></g:if><g:else>${shippingEventInstance.trackingNumber}</g:else> (${shippingEventInstance.courier})</td>
                            <td><g:formatDate date="${shippingEventInstance.shipDateTime}" /></td>                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
