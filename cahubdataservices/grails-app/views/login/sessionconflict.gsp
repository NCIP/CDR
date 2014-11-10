
<g:set var="bodyclass" value="session conflict" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <title>Session Conflict</title>
        <meta http-equiv="refresh" content="12; url=/cahubdataservices/home/" />
    </head>
    <body>
      <div id="nav" class="clearfix">
         <div id="navlist"></div>
      </div>
      <div id="container" class="clearfix">
        %{-- <div class="errors"><ul><li>Sorry, a <b>Session Conflict</b> is found. This may be caused by <b>another web browser or tab</b> with a caHUB page. Go back and retry after check the consistency of the study or the authorities.</li></ul></div> --}%
        <div class="errors"><ul><li>Sorry, a <b>Session Conflict</b> has been detected. This may be caused by <b>another web browser or tab</b> opened to the CDR, or has been <b>closed without logging out</b>. Please close other browsers or tabs and start over from your home page. Redirecting to your home page in 10 seconds.</li></ul></div>
      </div>
    </body>
</html>
