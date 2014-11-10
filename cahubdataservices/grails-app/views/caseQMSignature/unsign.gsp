<g:set var="bodyclass" value="caseqmsignature unsign" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <title>Unsign QM Signature</title>
        <meta http-equiv="refresh" content="${'7; url=/cahubdataservices/caseRecord/show/' + caseRecordInstance.id }" />
    </head>
    <body>
      <div id="nav" class="clearfix">
         <div id="navlist"></div>
      </div>
      <div id="container" class="clearfix">
        <div class="message">Your QM signature on ${caseRecordInstance.caseId} is successfully unsigned.. Redirecting to the case detail page in 5 seconds...</div>
      </div>
    </body>
</html>
