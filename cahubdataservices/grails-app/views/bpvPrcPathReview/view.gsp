

<%@ page import="nci.obbr.cahub.prc.bpv.BpvPrcPathReview" %>
<%@ page import="nci.obbr.cahub.util.ComputeMethods" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="bpvlocalpathreview view bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="PRC Pathology Review Form" />
        <g:set var="slideId" value="${bpvPrcPathReviewInstance?.slideRecord?.slideId}" />
        <title><g:message code="default.view.label" args="[entityName]" /></title>
        
        <g:javascript>
            $(document).ready(function(){
                $('#view :input').attr('disabled', true);
            });
            
            function openImageWin(imageId){
                //var w = window.open('https://microscopy.vai.org/imageserver/@@/@' + imageId + '/view.apml', 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
                  var w=window.open('${AppSetting.findByCode("APERIO_URL").value}'+imageId, 'hub_aperio', 'location=1,status=1,scrollbars=1,resizable=1,width=965,height=700');
                w.focus();
            }
        </g:javascript>
    </head>
    <body>
      <div id="nav" class="clearfix">
        <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="Home"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.view.label.with.slide.id" args="[entityName,slideId]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${bpvPrcPathReviewInstance}">
                <div class="errors">
                    <g:renderErrors bean="${bpvPrcPathReviewInstance}" as="list" />
                </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${bpvPrcPathReviewInstance?.id}" />
                <g:hiddenField name="version" value="${bpvPrcPathReviewInstance?.version}" />
                <div id="view">
                    <div class="dialog">
                        <g:render template="/caseRecord/caseDetails" bean="${bpvPrcPathReviewInstance.caseRecord}" var="caseRecord" />
                        
                        <g:if test="${bpvPrcPathReviewInstance?.status == 'Reviewed'}">
                            <table>
                                <tbody>
                                    <tr class="prop">
                                        <td>
                                            <div class="clearfix">
                                                <dl class="formdetails left">
                                                    <dt>Name of pathologist who reviewed Slide:</dt>
                                                    <dd>
                                                        ${bpvPrcPathReviewInstance?.reviewedBy}
                                                    </dd>
                                                </dl>
                                                <dl class="formdetails left">
                                                    <dt>Date of Slide review:</dt>
                                                    <dd>
                                                        <g:formatDate format="MM/dd/yyyy HH:mm:ss" date="${bpvPrcPathReviewInstance?.reviewDate}"/>
                                                    </dd>
                                                </dl>
                                            </div>
                                        </td>
                                    </tr>
                               </tbody>
                            </table>
                        </g:if>
                        
                        <g:set var="labelNumber" value="${1}"/>
                        <g:set var="isLater5_3" value="${ nci.obbr.cahub.util.ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance.caseRecord.cdrVer, '5.3') }" />
                        
                        <div class="list">
                            <table class="tdwrap">
                                <tbody>

                                    <tr class="prop">
                                        <g:if test="${bpvPrcPathReviewInstance?.category=='Ovary'}">
                                            <td colspan="2" class="formheader">Ovarian Slide Pathology Review</td>
                                        </g:if>
                                        <g:if test="${bpvPrcPathReviewInstance?.category=='Kidney'}">
                                            <td colspan="2" class="formheader">Kidney Slide Pathology Review</td>
                                        </g:if>
                                        <g:if test="${bpvPrcPathReviewInstance?.category=='Colon'}">
                                            <td colspan="2" class="formheader">Colon Slide Pathology Review</td>
                                        </g:if>
                                        <g:if test="${bpvPrcPathReviewInstance?.category=='Lung'}">
                                            <td colspan="2" class="formheader">Lung Slide Pathology Review</td>
                                        </g:if>
                                    </tr>

                                    <tr class="prop">
                                        <td class="name" >${labelNumber++}. Slide ID examined by pathologist:</td>
                                        <td class="value" >
                                            <g:if test="${session.org.code == 'OBBR'}">
                                                <g:link controller="slideRecord" action="show" id="${bpvPrcPathReviewInstance?.slideRecord?.id}">${bpvPrcPathReviewInstance?.slideRecord?.slideId},</g:link>&nbsp;${bpvPrcPathReviewInstance?.slideRecord?.module}
                                            </g:if>
                                            <g:else>
                                                ${bpvPrcPathReviewInstance?.slideRecord?.slideId}, ${bpvPrcPathReviewInstance?.slideRecord?.module}
                                            </g:else>
                                        </td>
                                    </tr>

                                    <tr class="prop">
                                        <td class="name" >${labelNumber++}. Parent Specimen ID of the sample from which this slide was derived:</td>
                                        <td class="value" >
                                            ${bpvPrcPathReviewInstance?.slideRecord?.specimenRecord?.specimenId}
                                        </td>
                                    </tr>

                                    <tr class="prop">
                                        <td class="name" colspan="2">
                                            ${labelNumber++}. Organ of origin:
                                            <span data-msg="If <b>Other</b> was selected, record other organ of origin" class="cahub-tooltip"></span>
                                            <div class="subentry value formvaluediv">
                                                <g:if test="${bpvPrcPathReviewInstance?.category=='Ovary'}">
                                                    <g:radio name="organOrigin" id='o1' value="Ovary" checked="${bpvPrcPathReviewInstance?.organOrigin =='Ovary'}"/>&nbsp;<label for="o1">Ovary</label><br/>
                                                    <g:radio name="organOrigin" id='o2' value="Peritoneum" checked="${bpvPrcPathReviewInstance?.organOrigin =='Peritoneum'}"/>&nbsp;<label for="o2">Peritoneum</label><br/>
                                                    <g:radio name="organOrigin" id='o4' value="Fallopian tube" checked="${bpvPrcPathReviewInstance?.organOrigin =='Fallopian tube'}"/>&nbsp;<label for="o4">Fallopian tube</label><br/>
                                                    <g:radio name="organOrigin" id='o3' value="Indeterminate" checked="${bpvPrcPathReviewInstance?.organOrigin =='Indeterminate'}"/>&nbsp;<label for="o3">Indeterminate</label><br/>
                                                </g:if>
                                                <g:if test="${bpvPrcPathReviewInstance?.category=='Kidney'}">
                                                    <g:radio name="organOrigin" id='o1' value="Kidney" checked="${bpvPrcPathReviewInstance?.organOrigin =='Kidney'}"/>&nbsp;<label for="o1">Kidney</label><br/>
                                                </g:if>
                                                <g:if test="${bpvPrcPathReviewInstance?.category=='Colon'}">
                                                    <g:radio name="organOrigin" id='o1' value="Cecum" checked="${bpvPrcPathReviewInstance?.organOrigin =='Cecum'}"/>&nbsp;<label for="o1">Cecum</label><br/>
                                                    <g:radio name="organOrigin" id='o2' value="Colon, ascending" checked="${bpvPrcPathReviewInstance?.organOrigin =='Colon, ascending'}"/>&nbsp;<label for="o2">Colon, ascending</label><br/>
                                                    <g:radio name="organOrigin" id='o3' value="Colon, descending" checked="${bpvPrcPathReviewInstance?.organOrigin =='Colon, descending'}"/>&nbsp;<label for="o3">Colon, descending</label><br/>
                                                    <g:radio name="organOrigin" id='o4' value="Colon, sigmoid" checked="${bpvPrcPathReviewInstance?.organOrigin =='Colon, sigmoid'}"/>&nbsp;<label for="o4">Colon, sigmoid</label><br/>
                                                    <g:radio name="organOrigin" id='o5' value="Colon, transverse" checked="${bpvPrcPathReviewInstance?.organOrigin =='Colon, transverse'}"/>&nbsp;<label for="o5">Colon, transverse</label><br/>
                                                    <g:radio name="organOrigin" id='o6' value="Hepatic flexure" checked="${bpvPrcPathReviewInstance?.organOrigin =='Hepatic flexure'}"/>&nbsp;<label for="o6">Hepatic flexure</label><br/>
                                                    <g:radio name="organOrigin" id='o7' value="Rectosigmoid junction" checked="${bpvPrcPathReviewInstance?.organOrigin =='Rectosigmoid junction'}"/>&nbsp;<label for="o7">Rectosigmoid junction</label><br/>
                                                    <g:radio name="organOrigin" id='o8' value="Rectum" checked="${bpvPrcPathReviewInstance?.organOrigin =='Rectum'}"/>&nbsp;<label for="o8">Rectum</label><br/>
                                                    <g:radio name="organOrigin" id='o9' value="Splenic flexure" checked="${bpvPrcPathReviewInstance?.organOrigin =='Splenic flexure'}"/>&nbsp;<label for="o9">Splenic flexure</label><br/>
                                                </g:if>
                                                <g:if test="${bpvPrcPathReviewInstance?.category=='Lung'}">
                                                    <g:radio name="organOrigin" id='o1' value="Lung" checked="${bpvPrcPathReviewInstance?.organOrigin =='Lung'}"/>&nbsp;<label for="o1">Lung</label><br/>
                                                </g:if>
                                                <g:radio name="organOrigin" id='o10' value="Other" checked="${bpvPrcPathReviewInstance?.organOrigin =='Other'}"/>&nbsp;<label for="o10">Other</label>
                                            </div>
                                            <g:if test="${bpvPrcPathReviewInstance?.organOrigin == 'Other'}">
                                                <span id="oo" style="display: block" class="value"><br /><span class="subentry">Specify other organ of origin:</span> <g:textField id="otherOrganOrigin" name="otherOrganOrigin" value="${bpvPrcPathReviewInstance?.otherOrganOrigin}" /></span>  
                                            </g:if>
                                            <g:else>
                                                <span id="oo" style="display: none"  class="value"><br /><span class="subentry">Specify other organ of origin:</span> <g:textField id="otherOrganOrigin" name="otherOrganOrigin" value="${bpvPrcPathReviewInstance?.otherOrganOrigin}" /></span>  
                                            </g:else>
                                        </td>
                                    </tr>

                                    <tr class="prop">
                                        <td colspan="2" class="name" >
                                            ${labelNumber++}. Histologic type:
                                            <span data-msg="If <b>Other</b> was selected, record other histologic type" class="cahub-tooltip"></span>
                                            <g:if test="${bpvPrcPathReviewInstance?.category=='Ovary'||bpvPrcPathReviewInstance?.category=='Lung'}">
                                            <span data-msg="Specify histologic type details if the field pops up" class="cahub-tooltip"></span>
                                            </g:if>
                                            <div class="subentry value formvaluediv">
                                                <g:each in="${hisList}" status="i" var="his">
                                                    <g:radio class="hisRadio" name="hhistologicType.id" value="${his.id}" id="his_${his.code}" checked="${bpvPrcPathReviewInstance?.histologicType?.id == his.id}"/>
                                                    <label for="his_${his.code}">  ${his.name}
                                                        <g:if test="${his.who_code}"> (WHO code: ${his.who_code}) </g:if>
                                                    </label><br/>
                                                    <g:if test="${(his.code == 'C7' || his.code == 'C8' || his.code == 'C9' || his.code == 'C78') && bpvPrcPathReviewInstance.histologicType?.id == his.id}">
                                                        <span><br/>Specify types and %:<br/><g:textArea name="otherHistologicType" cols="40" rows="5" value="${bpvPrcPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                    </g:if>
                                                    <g:if test="${(his.code == 'C20' || his.code == 'OTHER') && bpvPrcPathReviewInstance.histologicType?.id == his.id}">
                                                        <span><br/>Specify type:<br/><g:textArea name="otherHistologicType" cols="40" rows="5" value="${bpvPrcPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                    </g:if>
                                                </g:each>
                                            </div>
                                        </td>
                                    </tr>

                                    <g:if test="${bpvPrcPathReviewInstance?.category=='Kidney'}">
                                        <tr class="prop clearborder">
                                            <td class="name" >
                                                ${labelNumber++}. Presence of Sarcomatoid features:
                                                <span data-msg="If <b>Present</b>, describe Sarcomatoid features" class="cahub-tooltip"></span>
                                            </td>
                                            <td class="value" >
                                                <div>
                                                    <g:radio name="sarcomatoid" id='sa1' value="Not identified" checked="${bpvPrcPathReviewInstance?.sarcomatoid =='Not identified'}"/>&nbsp;<label for="sa1">Not identified</label><br/>
                                                    <g:radio name="sarcomatoid" id='sa2' value="Present" checked="${bpvPrcPathReviewInstance?.sarcomatoid =='Present'}"/>&nbsp;<label for="sa2">Present</label>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr class="prop subentry">
                                            <td colspan="2" class="name" >
                                                <g:if test="${bpvPrcPathReviewInstance?.sarcomatoid == 'Present'}">
                                                    <span id="sad" style="display: block" class="value">Describe Sarcomatoid features: <br/><g:textArea id="sarcomatoidDesc" name="sarcomatoidDesc" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.sarcomatoidDesc}" /></span>  
                                                </g:if>
                                                <g:else>
                                                    <span id="sad" style="display: none"  class="value">Describe Sarcomatoid features: <br/><g:textArea id="sarcomatoidDesc" name="sarcomatoidDesc" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.sarcomatoidDesc}" /></span>  
                                                </g:else>
                                            </td>
                                        </tr>
                                    </g:if>

                                    <tr class="prop">
                                        <td class="name" >
                                            ${labelNumber++}. Greatest tumor dimension on slide:
                                        </td>
                                        <td class="value" ><g:textField id="tumorDimension" name="tumorDimension" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'tumorDimension')}" /> (mm)</td>
                                    </tr>  

                                    <tr class="prop">
                                        <td class="name" >
                                            ${labelNumber++}. 
                                            <g:if test="${ (isLater5_3 >= 0) }">Percent of cross-sectional surface area of entire slide composed of tumor focus (Includes necrotic tumor) :</g:if>
                                            <g:else>Percent of cross-sectional surface area composed of tumor focus:</g:else>
                                        </td>
                                        <td class="value" ><g:textField id="pctTumorArea" name="pctTumorArea" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctTumorArea')}" /> %</td>
                                    </tr>

                                    <tr class="prop">
                                        <td class="name" >
                                            ${labelNumber++}. 
                                            <g:if test="${ (isLater5_3 >= 0) }">Percent of tumor nuclei by cell count of the entire slide (number of tumor epithelial cell nuclei as compared to all cell nuclei):</g:if>
                                            <g:else>Percent of tumor cellularity by cell count of the entire slide:</g:else>
                                        </td>
                                        <td class="value" ><g:textField id="pctTumorCellularity" name="pctTumorCellularity" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctTumorCellularity')}" /> %</td>
                                    </tr>
                                    
                                    <g:if test="${ (isLater5_3 >= 0) }">
                                        <tr class="prop">
                                            <td class="name" >
                                                ${labelNumber++}. Percent of cross-sectional surface area of entire slide composed of necrotic tissue:
                                            </td>
                                            <td class="value"><g:textField id="pctNecroticTissue" name="pctNecroticTissue" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctNecroticTissue')}" /> %</td>
                                        </tr>
                                    </g:if>

                                    <tr class="prop">
                                        <g:if test="${ (isLater5_3 >= 0) }">
                                            <td colspan="2" class="formheader name"><font color="blue">Note :</font> BPV Case Acceptance Criteria Require Necrosis Percentage of &lt;20% of the entire slide AND Tumor Content of &ge;50% Tumor Nuclei.<g:if test="${ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance?.caseRecord?.cdrVer, '5.4.1') < 0}"><br><br>Histologic Profile Quantitative Assessment of Tumor Should Total 100%.</g:if> </td>
                                        </g:if>
                                        <g:else>
                                            <td colspan="2" class="formheader name">Histologic Profile Quantitative Assessment Should Total 100%. BPV Case Acceptance Criteria Require Necrosis Percentage of <20% AND Tumor Content of &ge;50% Tumor Cells by Surface Area.</td>
                                        </g:else>
                                    </tr>

                                    <tr class="prop">
                                        <td class="name" colspan="2">
                                            ${labelNumber++}. Histologic Profile Quantitative Assessment:
                                            <span data-msg="If present, describe Non-Cellular component" class="cahub-tooltip"></span><br />
                                            <table class="formvaluediv">
                                                <tr>
                                                    <td >Percent Viable Tumor by surface area<g:if test="${ (isLater5_3 >= 0) }"> (not including stroma)</g:if></td>
                                                    <td class="value" style=" width:25%"><g:textField id="p1" name="pctViablTumor" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctViablTumor')}" SIZE="10" /> %</td>
                                                </tr>
                                                <tr>
                                                    <td >Percent Necrotic Tumor by surface area</td>
                                                    <td class="value" ><g:textField id="p2" name="pctNecroticTumor" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctNecroticTumor')}" SIZE="10" /> %</td>
                                                </tr> 
                                                <tr>
                                                    <td>
                                                        <g:if test="${ (isLater5_3 >= 0) }">Percent Tumor Stroma by surface area</g:if>
                                                        <g:else>Percent Viable Non-Tumor Tissue by surface area</g:else>
                                                    </td>
                                                    <td class="value" ><g:textField id="p3" name="pctViableNonTumor" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctViableNonTumor')}" SIZE="10" /> %</td>
                                                </tr>
                                                <tr>
                                                    <td>Percent Non-Cellular Component by surface area (i.e., mucin, hemorrhage, blood clot, etc.)</td>
                                                    <td class="value"><g:textField id="p4" name="pctNonCellular" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctNonCellular')}"  SIZE="10" /> %</td>
                                                </tr>
                                                <tr>
                                                    <td class="formheader">Histologic Profile Total % (should equal 100%)</td>
                                                    <td class="formheader"><g:textField id="t" name="hisTotal" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'hisTotal')}" SIZE="10" /> %</td>
                                                </tr> 
                                            </table>
                                        </td> 
                                    </tr>
                                    
                                    <g:if test="${bpvPrcPathReviewInstance?.category=='Ovary'}">
                                        <tr class="prop clearborder">
                                            <td colspan="2" class="name" >${labelNumber++}. What histologic grading system was applied?
                                                <div class="value formvaluediv subentry">
                                                    <g:radio name="gradingSystem" id='g1' value="WHO Grading System" checked="${bpvPrcPathReviewInstance?.gradingSystem =='WHO Grading System'}"/>&nbsp;<label for="g1">WHO Grading System</label><br/>
                                                    <g:radio name="gradingSystem" id='g2' value="Two-Tier Grading System" checked="${bpvPrcPathReviewInstance?.gradingSystem =='Two-Tier Grading System'}"/>&nbsp;<label for="g2">Two-Tier Grading System</label><br/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr class="prop subentry" id="who" style="display:${bpvPrcPathReviewInstance?.gradingSystem == 'WHO Grading System' ? 'display' : 'none'}">
                                            <td class="name" colspan="2">Histologic Grade (WHO Grading System):<br />
                                                <div class="value formvaluediv">
                                                    <g:radio name="grade" id='w1' value="G1: Well Differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G1: Well Differentiated'}"/>&nbsp;<label for="w1">G1: Well Differentiated</label><br/>
                                                    <g:radio name="grade" id='w2' value="G2: Moderately differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G2: Moderately differentiated'}"/>&nbsp;<label for="w2">G2: Moderately differentiated</label><br/>
                                                    <g:radio name="grade" id='w3' value="G3: Poorly differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G3: Poorly differentiated'}"/>&nbsp;<label for="w3">G3: Poorly differentiated</label><br/>
                                                    <g:radio name="grade" id='w4' value="G4: Undifferentiated" checked="${bpvPrcPathReviewInstance?.grade =='G4: Undifferentiated'}"/>&nbsp;<label for="w4">G4: Undifferentiated</label><br/>
                                                    <g:radio name="grade" id='w5' value="GX: Cannot be assessed" checked="${bpvPrcPathReviewInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="w5">GX: Cannot be assessed</label>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr class="prop subentry" id="t2t" style="display:${bpvPrcPathReviewInstance?.gradingSystem == 'Two-Tier Grading System' ? 'display' : 'none'}">
                                            <td colspan="2" class="name" >Two-Tier Grading System Grade:
                                                <span data-msg="Two-Teir Grading may be applied to serous carcinomas and immature teratomas only" class="cahub-tooltip"></span>
                                                <div class="value formvaluediv">
                                                    <g:radio name="grade" id='tt1' value="Low grade" checked="${bpvPrcPathReviewInstance?.grade =='Low grade'}"/>&nbsp;<label for="tt1">Low grade</label><br/>
                                                    <g:radio name="grade" id='tt2' value="High grade" checked="${bpvPrcPathReviewInstance?.grade =='High grade'}"/>&nbsp;<label for="tt2">High grade</label><br/>
                                                </div>
                                            </td >
                                        </tr>
                                    </g:if>   
                                    <g:if test="${bpvPrcPathReviewInstance?.category=='Kidney'}">
                                        <tr class="prop">
                                            <td class="name" colspan="2">${labelNumber++}. Histologic Grade (Fuhrman Nuclear Grading System):<br />
                                                <div class="subentry value formvaluediv">
                                                    <g:radio name="grade" id='fn1' value="G1: Nuclei round, uniform, approximately 10µm; nucleoli inconspicuous or absent" checked="${bpvPrcPathReviewInstance?.grade =='G1: Nuclei round, uniform, approximately 10µm; nucleoli inconspicuous or absent'}"/>&nbsp;<label for="fn1">G1: Nuclei  round, uniform, approximately 10µm; nucleoli inconspicuous or absent</label><br/>
                                                    <g:radio name="grade" id='fn2' value="G2: Nuclei slightly irregular, approximately 15µm; nucleoli evident" checked="${bpvPrcPathReviewInstance?.grade =='G2: Nuclei slightly irregular, approximately 15µm; nucleoli evident'}"/>&nbsp;<label for="fn2">G2: Nuclei  slightly irregular, approximately 15µm; nucleoli evident</label><br/>
                                                    <g:radio name="grade" id='fn3' value="G3: Nuclei very irregular, approximately 20µm; nucleoli large and prominent" checked="${bpvPrcPathReviewInstance?.grade =='G3: Nuclei very irregular, approximately 20µm; nucleoli large and prominent'}"/>&nbsp;<label for="fn3">G3: Nuclei very irregular, approximately 20µm; nucleoli large and prominent</label><br/>
                                                    <g:radio name="grade" id='fn4' value="G4: Nuclei bizarre and multilobulated, 20µm or greater; nucleoli prominent, chromatin clumped" checked="${bpvPrcPathReviewInstance?.grade =='G4: Nuclei bizarre and multilobulated, 20µm or greater; nucleoli prominent, chromatin clumped'}"/>&nbsp;<label for="fn4">G4: Nuclei bizarre and multilobulated, 20µm or greater; nucleoli prominent, chromatin clumped</label><br/>
                                                    <g:radio name="grade" id='fn5' value="GX: Cannot be assessed" checked="${bpvPrcPathReviewInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="fn5">GX: Cannot be assessed</label>
                                                </div>
                                            </td>
                                        </tr>
                                    </g:if>
                                    <g:if test="${bpvPrcPathReviewInstance?.category=='Colon'}">
                                        <tr class="prop">
                                            <td class="name" colspan="2">${labelNumber++}. AJCC 7th Edition Histologic Grade:<br />
                                                <div class="subentry value formvaluediv">
                                                    <g:radio name="grade" id='ajccColon1' value="G1: Well differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G1: Well differentiated'}"/>&nbsp;<label for="ajccColon1">G1: Well differentiated</label><br/>
                                                    <g:radio name="grade" id='ajccColon2' value="G2: Moderately differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G2: Moderately differentiated'}"/>&nbsp;<label for="ajccColon2">G2: Moderately differentiated</label><br/>
                                                    <g:radio name="grade" id='ajccColon3' value="G3: Poorly differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G3: Poorly differentiated'}"/>&nbsp;<label for="ajccColon3">G3: Poorly differentiated</label><br/>
                                                    <g:radio name="grade" id='ajccColon4' value="G4: Undifferentiated" checked="${bpvPrcPathReviewInstance?.grade =='G4: Undifferentiated'}"/>&nbsp;<label for="ajccColon4">G4: Undifferentiated</label><br/>
                                                    <g:radio name="grade" id='ajccColon5' value="GX: Cannot be assessed" checked="${bpvPrcPathReviewInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="ajccColon5">GX: Cannot be assessed</label>
                                                </div>
                                            </td>
                                        </tr>
                                    </g:if>
                                    <g:if test="${bpvPrcPathReviewInstance?.category=='Lung'}">
                                        <tr class="prop">
                                            <td class="name" colspan="2">${labelNumber++}. AJCC 7th Edition Histologic Grade:<br />
                                                <div class="subentry value formvaluediv">
                                                    <g:radio name="grade" id='ajccLung1' value="G1: Well differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G1: Well differentiated'}"/>&nbsp;<label for="ajccLung1">G1: Well differentiated</label><br/>
                                                    <g:radio name="grade" id='ajccLung2' value="G2: Moderately differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G2: Moderately differentiated'}"/>&nbsp;<label for="ajccLung2">G2: Moderately differentiated</label><br/>
                                                    <g:radio name="grade" id='ajccLung3' value="G3: Poorly differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G3: Poorly differentiated'}"/>&nbsp;<label for="ajccLung3">G3: Poorly differentiated</label><br/>
                                                    <g:radio name="grade" id='ajccLung4' value="GX: Cannot be assessed" checked="${bpvPrcPathReviewInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="ajccLung4">GX: Cannot be assessed</label>
                                                </div>
                                            </td>
                                        </tr>
                                    </g:if>
                                      
                                    <tr class="prop">
                                    <td class="name" >
                                        ${labelNumber++}. Did pathology review of the H&E slide derived from QC FFPE tumor tissue confirm the histological type to be eligible for BPV study? 
                                    </td>
                                    <td class="value">
                                        <div>
                                            <g:radio name="histoEligible" id="he1"  value="Yes" checked="${bpvPrcPathReviewInstance?.histoEligible =='Yes'}"/>&nbsp;<label for="he1">Yes</label><br/>
                                            <g:radio name="histoEligible" id="he2" value="No" checked="${bpvPrcPathReviewInstance?.histoEligible =='No'}"/>&nbsp;<label for="he2">No</label><br/>
                                        </div> 
                                    </td>
                                     </tr>
                                  
                                    <tr class="prop clearborder">
                                        <td class="name" >
                                            ${labelNumber++}. This slide meets the microscopic analysis criteria of the BPV project of necrosis percentage of <20% and tumor content of &ge;50% tumor <g:if test="${ (isLater5_3 >= 0) }">nuclei</g:if> <g:else>cells by surface area</g:else>:
                                                <span data-msg="If <b>No</b> is selected, specify what findings do not meet the microscopic analysis criteria of the BPV project" class="cahub-tooltip"></span>
                                        </td>
                                        <td class="value"  class="value">
                                            <span class="value">
                                                <div>
                                                    <g:radio name="meetsCriteria" id='m1' value="Yes" checked="${bpvPrcPathReviewInstance?.meetsCriteria =='Yes'}"/>&nbsp;<label for="m1">Yes</label><br/>
                                                    <g:radio name="meetsCriteria" id='m2' value="No" checked="${bpvPrcPathReviewInstance?.meetsCriteria =='No'}"/>&nbsp;<label for="m2">No</label><br/>
                                                </div>
                                            </span> 
                                        </td>
                                      </tr>

                                      <tr class="prop subentry">
                                        <td colspan="2" class="name">
                                            <g:if test="${bpvPrcPathReviewInstance?.meetsCriteria == 'No'}">
                                                <span class="value" id="f" style="display:display">Specify findings: <br/><g:textArea name="reasonNotMeet" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.reasonNotMeet}" id="re" /></span>
                                            </g:if>
                                            <g:else>
                                                <span id="f" style="display:none">Specify findings: <br/><g:textArea name="reasonNotMeet" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.reasonNotMeet}" id="re" /></span>
                                            </g:else>
                                        </td>
                                    </tr>

                                    <tr class="prop">
                                        <td colspan="2" class="name" >
                                            ${labelNumber++}. Pathology review comments:<br />
                                            <g:textArea name="pathologyComments" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.pathologyComments}" />
                                        </td>
                                    </tr>

                                    <tr class="prop">
                                        <td colspan="2" class="formheader">
                                            <g:if test="${ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance?.caseRecord?.cdrVer, '5.0') >= 0}">
                                                Concordance with Local Pathology Report
                                            </g:if>
                                            <g:else>
                                                Concordance with Diagnostic Pathology Report
                                            </g:else>
                                        </td>
                                    </tr>

                                    <tr class="prop clearborder">
                                        <td class="name" >
                                            <g:if test="${ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance?.caseRecord?.cdrVer, '5.0') >= 0}">
                                                ${labelNumber++}. This slide is consistent with the findings of the local BSS pathologist:
                                                <span data-msg="If <b>No</b> is selected, specify what findings are not consistent with the local BSS pathologist" class="cahub-tooltip"></span>
                                            </g:if>
                                            <g:else>
                                                ${labelNumber++}. This slide is consistent with the findings of the Diagnostic Pathology Report for this case:
                                                <span data-msg="If <b>No</b> is selected, specify what findings are not consistent with the Diagnostic Pathology Report" class="cahub-tooltip"></span>
                                            </g:else>
                                        </td>
                                        <td class="value" >
                                            <span class="value">
                                                <div>
                                                    <g:radio name="consistentLocalPrc" id='c1' value="Yes" checked="${bpvPrcPathReviewInstance?.consistentLocalPrc =='Yes'}"/>&nbsp;<label for="c1">Yes</label><br/>
                                                    <g:radio name="consistentLocalPrc" id='c2' value="No" checked="${bpvPrcPathReviewInstance?.consistentLocalPrc =='No'}"/>&nbsp;<label for="c2">No</label><br/>
                                                </div>
                                            </span>                                    
                                        </td>
                                </tr>

                                <tr class="prop subentry">
                                  <td colspan="2" class="name">
                                            <g:if test="${bpvPrcPathReviewInstance?.consistentLocalPrc == 'No'}">
                                                <span  class="value" id="f2" style="display:display">Specify findings: <br/><g:textArea name="reasonNotCons" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.reasonNotCons}" id="re2" /></span>
                                            </g:if>
                                            <g:else>
                                                <span id="f2" style="display:none">Specify findings: <br/><g:textArea name="reasonNotCons" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.reasonNotCons}" id="re2" /></span>
                                            </g:else>
                                        </td>
                                    </tr>

                                </tbody>
                            </table>     

                            <g:each in="${bpvPrcPathReviewInstance?.bpvPrcExpSampleReviews}" status="i" var="bpvPrcExpSampleReview">
                                <table class="tdwrap">
                                    <tbody>

                                        <g:if test="${i == 0}">
                                            <tr class="prop">
                                                <td colspan="2" class="formheader">Review for Experimental Samples</td>
                                            </tr>
                                        </g:if>
                                      
                                        <tr class="prop">
                                            <td colspan="2">
                                                <div class="clearfix">
                                                    <dl class="formdetails left">
                                                        <dt>Specimen ID:</dt>
                                                        <dd>
                                                            <g:if test="${session.org.code == 'OBBR'}">
                                                                <g:link controller="specimenRecord" action="show" id="${bpvPrcExpSampleReview?.slideRecord?.specimenRecord?.id}">${bpvPrcExpSampleReview?.slideRecord?.specimenRecord?.specimenId}</g:link>
                                                            </g:if>
                                                            <g:else>
                                                                ${bpvPrcExpSampleReview?.slideRecord?.specimenRecord?.specimenId}
                                                            </g:else>
                                                        </dd>
                                                    </dl>
                                                    <dl class="formdetails left">
                                                        <dt>Slide ID:</dt>
                                                        <dd>
                                                            <g:if test="${session.org.code == 'OBBR'}">
                                                                <g:link controller="slideRecord" action="show" id="${bpvPrcExpSampleReview?.slideRecord?.id}">${bpvPrcExpSampleReview?.slideRecord?.slideId}</g:link>
                                                            </g:if>
                                                            <g:else>
                                                                ${bpvPrcExpSampleReview?.slideRecord?.slideId}
                                                            </g:else>
                                                        </dd>
                                                    </dl>
                                                    <dl class="formdetails left">
                                                        <dt>Image ID:</dt>
                                                        <dd>
                                                            <g:if test="${session.org.code == 'OBBR'}">
                                                                <a href="javascript:openImageWin('${bpvPrcExpSampleReview?.slideRecord?.imageRecord?.imageId}')">${bpvPrcExpSampleReview?.slideRecord?.imageRecord?.imageId}</a>
                                                            </g:if>
                                                            <g:else>
                                                                ${bpvPrcExpSampleReview?.slideRecord?.imageRecord?.imageId}
                                                            </g:else>
                                                        </dd>
                                                    </dl>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr class="prop clearborder">
                                            <td class="name" >
                                                Organ of origin and histologic type are the same as the QC section:
                                            </td>
                                            <td class="value" >
                                                <div>
                                                    <g:radio name="sameOriginType_${bpvPrcExpSampleReview.id}" id="sameOriginType_yes_${bpvPrcExpSampleReview.id}" value="Yes" checked="${bpvPrcExpSampleReview?.sameOriginType == 'Yes'}"/>&nbsp;<label for="sameOriginType_yes_${bpvPrcExpSampleReview.id}">Yes</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <g:radio name="sameOriginType_${bpvPrcExpSampleReview.id}" id="sameOriginType_no_${bpvPrcExpSampleReview.id}" value="No" checked="${bpvPrcExpSampleReview?.sameOriginType == 'No'}"/>&nbsp;<label for="sameOriginType_no_${bpvPrcExpSampleReview.id}">No</label>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr class="prop clearborder">
                                            <td class="name" >
                                                This slide meets the microscopic analysis criteria for the BPV project of <20% necrosis and tumor content of &ge;50% <g:if test="${ (isLater5_3 >= 0) }">tumor nuclei</g:if><g:else>of cells by surface area</g:else>:
                                            </td>
                                            <td class="value" >
                                                <div>
                                                    <g:radio name="meetsCriteria_${bpvPrcExpSampleReview.id}" id="meetsCriteria_yes_${bpvPrcExpSampleReview.id}" value="Yes" checked="${bpvPrcExpSampleReview?.meetsCriteria == 'Yes'}"/>&nbsp;<label for="meetsCriteria_yes_${bpvPrcExpSampleReview.id}">Yes</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <g:radio name="meetsCriteria_${bpvPrcExpSampleReview.id}" id="meetsCriteria_no_${bpvPrcExpSampleReview.id}" value="No" checked="${bpvPrcExpSampleReview?.meetsCriteria == 'No'}"/>&nbsp;<label for="meetsCriteria_no_${bpvPrcExpSampleReview.id}">No</label>
                                                </div>
                                            </td>
                                        </tr>
                                   <g:if test="${ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance?.caseRecord?.cdrVer, '5.4.1') >= 0}">
                                      <tr class="prop clearborder">
                                        <td class="name" >
                                            Percent tumor nuclei:
                                        </td>
                                         <td class="value" >
                                            <div>
                                             <g:if test="${ComputeMethods.compareCDRVersion(bpvPrcPathReviewInstance?.caseRecord?.cdrVer, '5.5') >= 0}">
                                             
                                               <g:radio name="percTumor_${bpvPrcExpSampleReview.id}"  value="0" id="percTumor1"checked="${bpvPrcExpSampleReview?.percTumor == '0'}"/>&nbsp;<label for="percTumor1">0</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <g:radio name="percTumor_${bpvPrcExpSampleReview.id}"  value="1-24" id="percTumor1"checked="${bpvPrcExpSampleReview?.percTumor == '1-24'}"/>&nbsp;<label for="percTumor1">1-24</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                              </g:if>
                                                <g:else>
                                                <g:radio name="percTumor_${bpvPrcExpSampleReview.id}"  value="0-24" id="percTumor1"checked="${bpvPrcExpSampleReview?.percTumor == '0-24'}"/>&nbsp;<label for="percTumor1">0-24</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                              </g:else>
  
                                               
                                                <g:radio name="percTumor_${bpvPrcExpSampleReview.id}"  value="25-49" id="percTumor2"checked="${bpvPrcExpSampleReview?.percTumor == '25-49'}"/>&nbsp;<label for="percTumor2">25-49</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <g:radio name="percTumor_${bpvPrcExpSampleReview.id}"  value="50-74" id="percTumor3"checked="${bpvPrcExpSampleReview?.percTumor == '50-74'}"/>&nbsp;<label for="percTumor4">50-74</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <g:radio name="percTumor_${bpvPrcExpSampleReview.id}"  value="75-100" id="percTumor3"checked="${bpvPrcExpSampleReview?.percTumor == '75-100'}"/>&nbsp;<label for="percTumor4">75-100</label>
                                                
                                            </div>                                    
                                        </td>
                                       
                                    </tr>
                                      </g:if>

                                        <tr class="prop">
                                            <td colspan="2" class="name">
                                                <span  class="value">
                                                    Comments:<br /><g:textArea class="textwide" name="reasonNotMeet_${bpvPrcExpSampleReview.id}" cols="40" rows="5" value="${bpvPrcExpSampleReview?.reasonNotMeet}" />
                                                </span>
                                            </td>
                                        </tr>

                                    </tbody>
                                </table>
                            </g:each>
                          
                        </div>
                    </div>
                </div>
                <g:if test="${(session.authorities.contains('ROLE_NCI-FREDERICK_CAHUB_PRC') || session.getAttribute('PRC')) && bpvPrcPathReviewInstance?.status == 'Reviewed'
                              && bpvPrcPathReviewInstance?.slideRecord?.specimenRecord?.caseRecord?.candidateRecord?.isEligible 
                              && bpvPrcPathReviewInstance?.slideRecord?.specimenRecord?.caseRecord?.candidateRecord?.isConsented}">
                    <div class="buttons">
                        <span class="button"><g:actionSubmit class="edit" action="reedit" value="Resume Editing" /></span>
                    </div>
                </g:if>
            </g:form>
        </div>
         <g:if test="${AppSetting.findByCode('PRC_DISCLAIMER')?.bigValue}">
                   <br/>
                  <p>${AppSetting.findByCode('PRC_DISCLAIMER').bigValue}</p>
                </g:if>
    </body>
</html>
