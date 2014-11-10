<g:set var="bodyclass" value="varihome" scope="request"/>
<html>
    <head>
        <title><g:message code="default.page.title"/> - More...</title>
        <meta name="layout" content="cahubTemplate" />
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            
           <g:link controller="home"  class="list" action="more">More...</g:link>
          </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Additional Pages</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                  <thead>
                  <tr>
                    <th>&nbsp;</th>
                  </tr>
                  </thead>
                  <tr class="even">
                    <td>
                      <span class="controller"><g:link controller="caseWithdraw"  class="list" action="listAllRecallCases" >Access Recalled Case Area</g:link></span>
                    </td>
                  </tr>
                  <tr class="odd">
                    <td>
                      <span class="controller"><g:link controller="fileUpload"  class="list" action="list" >View & Upload general files</g:link></span>
                    </td>
                  </tr>
                  <g:if test="${session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_DM') || session.authorities.contains('ROLE_ADMIN') || session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB') || session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER')}">
                    <tr class="odd">
                      <td>
                        <span class="controller"><g:link controller="home" action="cbrinvfeed">Inventory Feed from BRIMS</g:link></span>
                      </td>
                    </tr>
                  </g:if>                  
                </table>
            </div>
        </div>
    </body>
</html>

