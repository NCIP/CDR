<%
  def shipTrackingUrls = [:]
  nci.obbr.cahub.util.AppSetting.findAllByCodeLike('CDR_SHIPPINGEVENTS_TRACKING_URL_%').each{shipTrackingUrls[it.name] = it.value}
%>
<table>
    <thead>
        <tr>
            <th>Shipping Event ID</th>
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
