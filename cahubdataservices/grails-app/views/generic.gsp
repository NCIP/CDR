<g:set var="urlparamsArray" value="${urlparamsArray?:params?.urlparams?.split(',')}" scope="request"/>
<g:set var="pagetitle" value="${title?:params?.title}" scope="request"/>
<g:set var="bodyclass" value="${bodyclass?:params?.bodyclass}" scope="request"/>
<g:set var="navArray" value="${navArray?:params?.navigation?.split(',')}" scope="request"/>
<g:set var="divArray" value="${divArray?:params?.divs?.split(',')}" scope="request"/>
<g:set var="jsArray" value="${jsArray?:params?.jsvalues?.split(',')}" scope="request"/>
<g:set var="cssArray" value="${cssArray?:params?.cssvalues?.split(',')}" scope="request"/>
<g:set var="includesArray" value="${includesArray?:params?.includes?.split(',')}" scope="request"/>
<g:set var="extjs" value="${extjs?:params?.extjs}" scope="request"/>
<g:set var="appsettingused" value="${params?.appsettingused ? "\""+nci.obbr.cahub.util.AppSetting.findByCode(params.appsettingused)?.bigValue+"\"" : ""}" scope="request"/>
<g:if test="${env != 'production'}"><%-- cache buster--%><g:set var="d" value="${new Date()}" /><g:set var="ts" value="${d.format('yyyy-MM-dd:HH')}" /></g:if>
<html>
    <head>
        <title>${pagetitle}</title>  
        <g:each in="${jsArray}" status="i" var="jsStringValue">
          <script type="text/javascript" src="${resource(dir:'js',file:jsStringValue)}?v<g:meta name='app.version'/>-${ts ?: ''}"></script></g:each>
        <g:each in="${cssArray}" status="i" var="cssStringValue">
          <link rel="stylesheet" href="${resource(dir:'css',file: cssStringValue)}?v<g:meta name='app.version'/>-${ts ?: ''}" /></g:each>
        <meta name="layout" content="cahubTemplate"/> 
        <script type="text/javascript">var paramVals0 = "${params.get("param"+0)}";<g:each in="${urlparamsArray}" status="i" var="urlparamsStringValue">var ${urlparamsStringValue} = "${params[urlparamsStringValue]}";</g:each> ${params?.appsettingused?("var " + params?.appsettingused + " = " + appsettingused + ";"):""} ${controllerData?("var controllerData = "+controllerData+";"):""}</script>
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
         <g:each in="${navArray}" status="i" var="menuItem"><a href="${menuItem?.split(';')[0]}" class="${menuItem?.split(';')[1]}">${menuItem?.split(';')[2]}</a></g:each>
        </div>
      </div>
      <div id="container" class="clearfix">
        <h1><g:if test="${params?.h1studysession == true}">${session.study?.name} </g:if>${pagetitle}</h1>
        <g:each in="${divArray}" status="i" var="eachDivId"><div id="${eachDivId}"></div></g:each>
        <g:each in="${includesArray}" status="i" var="includesStringValue"><g:render template = "${includesStringValue}" /></g:each>
      </div>
    </body>
</html>

