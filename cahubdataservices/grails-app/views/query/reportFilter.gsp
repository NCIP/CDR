
<g:if test="${env != 'production'}">
    <%-- cache buster--%>
    <g:set var="d" value="${new Date()}" />
    <g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" />
</g:if>
<g:set var="bodyclass" value="query reportFilter" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${message(code: 'query.label', default: 'Query')}" />
        <script type="text/javascript" src="${resource(dir:'js',file:'query.js')}?v<g:meta name='app.version'/>-${ts ?: ''}"></script>
        <title>Report Filter</title>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1>Report Filter</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form method="post" target="_blank">
                <div class="list">
                    <table class="tdwrap">
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="overDue">Days overdue:</label>
                                </td>
                                <td valign="top" class="value">
                                    <g:textField class="numInt" name="overDue" size="4" maxlength="4" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="orgFilter">Organization:</label>
                                </td>
                                <td valign="top" class="value">
                                    <g:select name="orgFilter.id" from="${nci.obbr.cahub.staticmembers.Organization.list()}" optionKey="id" noSelection="['': '']" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status">Issue status:</label>
                                </td>
                                <td valign="top" class="value">
                                    <g:select name="status" from="${['Active', 'Addressed', 'Resolved', 'Closed']}" noSelection="['': '']" />
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="report" value="Generate Report" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
