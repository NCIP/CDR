<%@ page import="nci.obbr.cahub.datarecords.CandidateRecord" %>
<%@ page import="nci.obbr.cahub.datarecords.CaseRecord" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %> 
<g:set var="bodyclass" value="choosehome" scope="request"/>
<html>
    <head>
        <title><g:message code="default.page.title"/></title>
        <meta name="layout" content="cahubTemplate" />
        <script type="text/javascript" src="${resource(dir:'js',file:'reflection.js')}" ></script>
    </head>
    <body>     
       <div id="nav" class="clearfix">
          <div id="navlist">
            <g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB_SUPER') || session.authorities?.contains('ROLE_ADMIN')}">
                <g:link controller="backoffice" class="list" action="index">Back Office</g:link>             
            </g:if> 
            <g:if test="${session.authorities?.contains('ROLE_NCI-FREDERICK_CAHUB_DM') || session.DM}">
               <g:link controller="query" class="list" action="list">Query Tracker</g:link>
                <g:link controller="deviation" class="list" action="list">Deviation List</g:link>               
            </g:if> 

          </div>
      </div>
      <div id="container" class="clearfix">
	<div id="homemenu">
          <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
          </g:if>
          <div class="inner ui-corner-all">
            <h1>Choose your Destination</h1>
            <div class="clearfix">
               <div class="cahubthumbmenu">
                 <g:if test="${'GTEX' in blockedStudyList}">
                   <img src="${resource(dir:'images',file:'gtexthumb2.jpg')}" class="reflect rheight33" />
                   <span>GTEX Home</span>
                   <span>BMS Home</span>
                 </g:if>
                 <g:else>
                   <g:link controller="home" action="gtexhome"><img src="${resource(dir:'images',file:'gtexthumb2.jpg')}" class="reflect rheight33" /><span>GTEX Home</span></g:link>
                   <g:link controller="home" action="bmshome"><span>BMS Home</span></g:link>                   
                 </g:else>
               </div>
              
              <div class="cahubthumbmenu">
                <g:if test="${'BPV' in blockedStudyList}">
                  <img src="${resource(dir:'images',file:'brnthumb.jpg')}" class="reflect rheight33" /><span>BPV Home</span>
                  <span>BRN Home</span>         
                </g:if>
                <g:else>
                  <g:link controller="home" action="bpvhome"><img src="${resource(dir:'images',file:'brnthumb.jpg')}" class="reflect rheight33" /><span>BPV Home</span></g:link>
                  <g:link controller="home" action="brnhome"><span>BRN Home</span></g:link>       
                </g:else>
              </div>
              
              <div class="cahubthumbmenu">
                <g:if test="${'BPV' in blockedStudyList}">
                  <span><img src="${resource(dir:'images',file:'elsi-consent.jpg')}" class="reflect rheight33" /><span>BPV ELSI Home</span></span>
                </g:if>
                <g:else>
                  <g:link controller="home" action="bpvelsihome"><img src="${resource(dir:'images',file:'elsi-consent.jpg')}" class="reflect rheight33" /><span>BPV ELSI Home</span></g:link>
                </g:else>
              </div>     
              
              <div class="cahubthumbmenu">
               <g:if test="${'CTC' in blockedStudyList}"> 
             <%--   <g:if test="${1==1}"> --%>
                 <%-- <span><img src="${resource(dir:'images',file:'ctcthumb_bw.jpg')}" class="reflect rheight33" /><span>CTC Home</span></span> --%>
                  <span><img src="${resource(dir:'images',file:'ctcthumb.jpg')}" class="reflect rheight33" /><span>CTC Home</span></span>
                </g:if>
                <g:else>
                  <g:link controller="home" action="ctchome"><img src="${resource(dir:'images',file:'ctcthumb.jpg')}" class="reflect rheight33" /><span>CTC Home</span></g:link>
                </g:else>
              </div>  
               
            </div>
            <div class="clearfix"><br/></div>
            <div class="clearfix">         
               <div class="cahubthumbmenu">
               <g:if test="${session.PRC == true}">
                  <g:link controller="home" action="prchome"><img src="${resource(dir:'images',file:'prcthumb2.jpg')}" class="reflect rheight33" /><span>PRC Home</span></g:link>
               </g:if>
               <g:else>
                  <img src="${resource(dir:'images',file:'prcthumb2.jpg')}" class="reflect rheight33" /><span>PRC Home</span>                          
               </g:else>
              </div>      
              <div class="cahubthumbmenu">
              <g:if test="${session.DM == true}">
                 <g:link controller="home" action="opshome"><img src="${resource(dir:'images',file:'opsthumb2.jpg')}" class="reflect rheight33" /><span>DM Home</span></g:link>
              </g:if>
              <g:else>
                 <img src="${resource(dir:'images',file:'opsthumb1.jpg')}" class="reflect rheight33" /><span>DM Home</span>
              </g:else>                            
              </div>
              <div class="cahubthumbmenu">
              <g:set var="cdrar_hostname" value="${AppSetting.findByCode('CDRAR_HOSTNAME')}" />
              <g:set var="cdrar_home" value="${AppSetting.findByCode('CDRAR_HOME')}" />
                <a href="${cdrar_hostname.value}${cdrar_home.value}"><img src="${resource(dir:'images',file:'cdrarthumb.jpg')}" class="reflect rheight33" /><span>Analytics & Reporting</span></a>
              </div>
              <div class="cahubthumbmenu"><g:link controller="home" action="vocabhome"><img src="${resource(dir:'images',file:'vocabthumb.jpg')}" class="reflect rheight33"  /><span>Vocabulary Home</span></g:link></div>
           </div>                          
        </div>
     </div>
   </div>
  </body>
</html>
