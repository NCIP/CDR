<%@ page import="nci.obbr.cahub.prc.PrcReport" %>
<%@ page import="nci.obbr.cahub.forms.gtex.TissueRecoveryGtex" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="prcreport view" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${prcReportInstance?.caseRecord?.caseId}" />
        <title><g:message code="default.edit.label.prcreport" args="[entityName]" /></title>
       <g:javascript>
           $(document).ready(function(){
           
               $(".do").click(function(){
                     var paid =(jQuery(this).get(0).id).substring(2)
                     //alert("psid: " + psid)
                     document.getElementById("paid").value = paid
                   
                      
                });
           });
           
          function openImageWin(image_id){
            // var w2=window.open('https://microscopy.vai.org/imageserver/@@/@'+image_id + '/view.apml', 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
            //var w2=window.open('https://microscopy.vai.org/ViewImage.php?ImageId='+image_id, 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
            var w2=window.open('${AppSetting.findByCode("APERIO_URL").value}'+image_id, 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
              w2.focus();
           }
        </g:javascript>
    </head>
    <body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          </div>
      </div>
        <div id="container" class="clearfix">
        <g:render template="/prcReport/viewInc1" bean="${prcReportInstance}" />
        <g:render template="/prcReport/viewInc2" bean="${prcReportInstance}" />     
        </div>
     </body>
</html>
