<html>
    <head>
        <title><g:message code="default.page.title"/></title>
        <meta name="layout" content="main" />
        <style type="text/css" media="screen">

        #nav {
            margin-top:20px;
            margin-left:30px;
            width:228px;
            float:left;

        }
        .homePagePanel * {
            margin:0px;
        }
        .homePagePanel .panelBody ul {
            list-style-type:none;
            margin-bottom:10px;
        }
        .homePagePanel .panelBody h1 {
            /* text-transform:uppercase; */
            font-size:1.1em;
            margin-bottom:10px;
        }
        .homePagePanel .panelBody {
            background: url(images/leftnav_midstretch.png) repeat-y top;
            margin:0px;
            padding:15px;
        }
        .homePagePanel .panelBtm {
            background: url(images/leftnav_btm.png) no-repeat top;
            height:20px;
            margin:0px;
        }

        .homePagePanel .panelTop {
            background: url(images/leftnav_top.png) no-repeat top;
            height:11px;
            margin:0px;
        }
        h2 {
            margin-top:15px;
            margin-bottom:15px;
            font-size:1.2em;
        }
        #pageBody {
            margin-left:280px;
            margin-right:20px;
        }
        </style>
    </head>
    <body>
            <div id="nav" class="clearfix">
            <div class="homePagePanel">
                <div class="panelTop"></div>
                <div class="panelBody">
                    <h1><g:message code="default.page.title"/> controllers</h1>
                </div>
                <div class="panelBtm"></div>
            </div>
        </div>
        <div id="pageBody">

            <h1><g:message code="default.page.title"/></h1>

            <div style="margin:10px;">
                <h2>Data Controllers</h2>
                <ul>
                    <li class="controller"><a href="/cahubdataservices/caseRecord/index">nci.obbr.cahub.datarecords.CaseRecordController</a></li>
                    <li class="controller"><a href="/cahubdataservices/specimenRecord/index">nci.obbr.cahub.datarecords.SpecimenRecordController</a></li>
                    <li class="controller"><a href="/cahubdataservices/PrcSpecimenReport/index">nci.obbr.cahub.prc.PrcSpecimenReport</a></li>
                    <li class="controller"><a href="/cahubdataservices/slideRecord/index">nci.obbr.cahub.datarecords.SlideRecordController</a></li>
                    <li class="controller"><a href="/cahubdataservices/imageRecord/index">nci.obbr.cahub.datarecords.ImageRecordController</a></li>
                    <li class="controller"><a href="/cahubdataservices/rest/index">nci.obbr.cahub.datarecords.restController</a></li>                    
                    <li class="controller"><a href="/cahubdataservices/caseStatus/index">nci.obbr.cahub.staticmembers.CaseStatusController</a></li>
                    <li class="controller"><a href="/cahubdataservices/study/index">nci.obbr.cahub.staticmembers.StudyController</a></li>
                    <li class="controller"><a href="/cahubdataservices/auditLogEvent/index">org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogEventController</a></li>
                </ul>
                <h2>CRF Controllers</h2>
                  <ul>
                    <li class="controller"><a href="/cahubdataservices/CRFInclExclGTEX/index">nci.obbr.cahub.forms.CRFInclExclGTEXController</a></li>
                    <li class="controller"><a href="/cahubdataservices/IcdGtexNdri/index">nci.obbr.cahub.forms.gtex.IcdGtexNdriController</a></li>
                    <li class="controller"><a href="/cahubdataservices/IcdGtexRpci/index">nci.obbr.cahub.forms.gtex.IcdGtexRpciController</a></li>
                    <li class="controller"><a href="/cahubdataservices/IcdGtexSc/index">nci.obbr.cahub.forms.gtex.IcdGtexScController</a></li>
                </ul>                    
                <h2>All Controllers</h2>
                  <ul>
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                        <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
                    </g:each>
                </ul>
            </div>
        </div>

    </body>
</html>
