<g:set var="bodyclass" value="varihome" scope="request"/>
<html>
    <head>
        <title><g:message code="default.page.title"/> - Vocabulary and Configuration</title>
        <meta name="layout" content="cahubTemplate" />
    </head>
    <body>
       <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities.contains('ROLE_ADMIN')}">
                <span class="menuButton"><g:link controller="backoffice" class="list" action="index">Back Office</g:link></span> 
            </g:if>   
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>caHUB CDR Data Services Vocabulary and Configuration</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                  <thead>
                  <tr>
                    <th>Controlled Vocabulary</th>
                    <th>CDR Configuration Tables</th>
                  </tr>
                  </thead>
                  <tr class="odd">
                    <td>
                      <span class="controller"><a href="/cahubdataservices/acquisitionType/">Acquisition Type</a></span>
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/BSS/">BSS List</a></span>
                    </td>
                  </tr>
                  <tr class="even">
                    <td>
                      <span class="controller"><a href="/cahubdataservices/acquisitionLocation/">Acquisition Location</a></span>
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/shippingEventType/">Shipping Events</a></span>
                    </td>
                  </tr>
                  <tr class="odd">
                    <td>
                      <span class="controller"><a href="/cahubdataservices/deathCause/">Death Cause (deprecated)</a></span>
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/inventoryStatus/">Inventory Status</a></span>
                    </td>
                  </tr>
                  <tr class="even">
                    <td>
                      <span class="controller"><a href="/cahubdataservices/histologicType/">Histological Types</a></span>
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/kitType/">Kit Type</a></span>
                    </td>
                  </tr>
                  <tr class="odd">
                    <td>
                      <span class="controller"><a href="/cahubdataservices/tumorStatus/">Tumor Status</a></span>
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/organization/">Organization</a></span>
                    </td>
                  </tr>
                  <tr class="even">
                    <td>
                      <span class="controller"><a href="/cahubdataservices/fixative/">Fixatives</a></span>
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/shippingContentType/">Shipping Contents</a></span>
                    </td>
                  </tr>
                  <tr class="odd">
                    <td>
                      <span class="controller"><a href="/cahubdataservices/protocol/">Protocol</a></span>
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/study/">Study</a></span>
                    </td>
                  </tr>
                  <tr class="even">
                    <td>
                      <span class="controller"><a href="/cahubdataservices/medicalCondition/">Medical Condition (deprecated)</a></span>
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/transactionType/">Transaction Type</a></span>
                    </td>
                  </tr>
                  <tr class="odd">
                    <td>
                      <span class="controller"><a href="/cahubdataservices/containerType/">Container Type</a></span>
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/caseCollectionType/">Collection Type</a></span>
                    </td>
                  </tr>
                  <tr class="even">
                    <td>
                      <span class="controller"><a href="/cahubdataservices/caseAttachmentType/">Case Attachment Type</a></span>
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/caseStatus/">Case Status</a></span>
                    </td>
                  </tr>
                  <tr class="odd">
                    <td>
                      &nbsp;
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/formMetadata/">Form Metadata</a></span>
                    </td>
                  </tr>     
                  <tr class="even">
                    <td>
                      &nbsp;
                    </td>
                    <td>
                      <span class="controller"><a href="/cahubdataservices/studyPhase/">Study Phase</a></span>
                    </td>
                  </tr>                    
                </table>                  
              <br />  
                <a href="/cahubdataservices/home/vocabhome/cvocab">Controlled Vocab Test Harness</a>
        </div>
        </div>
    </body>
</html>

