<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <title>View Application Log</title>
        
    </head>
    <body>
        <div id="nav" class="clearfix">
            <div id="navlist">
                <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
                <a class="home" href="${createLink(uri: '/backoffice/index')}">Back Office Home</a>
                <g:if test="${ !flash.error  }"> 
                    <a class="download" href="${createLink(uri: '/backoffice/viewapplog?download=yes')}">Log File download</a>
                </g:if>
            </div>
        </div>
        <div id="container" class="clearfix">
            
            <g:if test="${flash.error}">
                <h1>View Application Log: Error Found</h1>
                <div class="errors">${flash.error}</div>
            </g:if>
            <g:else>
                <h1>View Application Log</h1>
                <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                </g:if>
                Log File Path: ${filePath.getAbsolutePath()}<br>
                <textarea wrap="off" readonly="readonly" rows="40" cols="150">${filePath.getText()}</textarea>
            </g:else>
        </div>
    </body>
</html>
