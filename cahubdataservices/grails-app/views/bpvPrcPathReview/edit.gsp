<%@ page import="nci.obbr.cahub.prc.bpv.BpvPrcPathReview" %>
<%@ page import="nci.obbr.cahub.util.ComputeMethods" %>
<%@ page import="nci.obbr.cahub.util.AppSetting" %>
<g:set var="bodyclass" value="bpvlocalpathreview edit bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="PRC Pathology Review Form" />
        <g:set var="slideId" value="${bpvPrcPathReviewInstance?.slideRecord?.slideId}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>

        <g:javascript>
            $(document).ready(function() {
                $("#o1").change(function() {
                    document.getElementById("otherOrganOrigin").value = ''
                    $("#oo").hide()
                }); 

                $("#o2").change(function() {
                    document.getElementById("otherOrganOrigin").value = ''
                    $("#oo").hide()
                });

                $("#o3").change(function() {
                    document.getElementById("otherOrganOrigin").value = ''
                    $("#oo").hide()
                });

                $("#o4").change(function() {
                    document.getElementById("otherOrganOrigin").value = ''
                    $("#oo").hide()
                });

                $("#o5").change(function() {
                    document.getElementById("otherOrganOrigin").value = ''
                    $("#oo").hide()
                });

                $("#o6").change(function() {
                    document.getElementById("otherOrganOrigin").value = ''
                    $("#oo").hide()
                });

                $("#o7").change(function() {
                    document.getElementById("otherOrganOrigin").value = ''
                    $("#oo").hide()
                });

                $("#o8").change(function() {
                    document.getElementById("otherOrganOrigin").value = ''
                    $("#oo").hide()
                });

                $("#o9").change(function() {
                    document.getElementById("otherOrganOrigin").value = ''
                    $("#oo").hide()
                });

                $("#o10").change(function() {  
                    $("#oo").show()
                });  

                $(".hisRadio").change(function() {
                    $(".hisDetail").hide()
                
                    if (this.id == 'his_C7') {
                        $("#SC7").show()
                    } else if (this.id == 'his_C8') {
                        $("#SC8").show()
                    } else if (this.id == 'his_C9') {
                        $("#SC9").show()
                    } else if (this.id == 'his_C20') {
                        $("#SC20").show()
                    } else if (this.id == 'his_OTHER') {
                        $("#SOTHER").show()
                    } else if (this.id == 'his_C78') {
                        $("#SC78").show()
                    }
                });

                $("#p1").change(function() {
                    calculateTotal()
                });

                $("#p2").change(function() {
                    calculateTotal()
                });

                $("#p3").change(function() {
                    calculateTotal()
                });

                $("#p4").change(function() {
                    calculateTotal()
                    var pct = document.getElementById("p4").value
                });
                
                $("#w6").hide()
                
                $("#g1").change(function() {
                    $("#who").show()
                    $("#t2t").hide()
                    $("#w6").attr('checked', true)
                });  

                $("#g2").change(function() {
                    $("#who").hide()
                    $("#t2t").show()
                    $("#w6").attr('checked', true)
                });

                $("#m1").change(function() {
                    document.getElementById("re").value = ''
                    $("#f").hide()    
                });  

                $("#m2").change(function() {   
                    $("#f").show()    
                });  

                $("#c1").change(function() {
                    document.getElementById("re2").value = ''
                    $("#f2").hide()    
                });  

                $("#c2").change(function() {
                    $("#f2").show()   
                });  

                $(":input").change(function() {
                    document.getElementById("changed").value = "Y"
                });

                $("#sa1").change(function() {
                    document.getElementById("sarcomatoidDesc").value = ''
                    $("#sad").hide()
                });  

                $("#sa2").change(function() {
                    $("#sad").show()
                });      

                $("#sub").click(function() {
                    var val = document.getElementById("tumorDimension").value
                    val = val.replace(/,/g, '')
                    if (val != null && isNaN(val)) {
                        alert("The greatest tumor dimension on slidet must be a number")
                        document.getElementById("tumorDimension").focus()
                        return false
                    }

                    val = document.getElementById("pctTumorArea").value
                    val = val.replace(/,/g, '')
                    if (val != null && isNaN(val)) {
                        alert("The percent of cross-sectional surface area composed of tumor focus must be a number")
                        document.getElementById("pctTumorArea").focus()
                        return false
                    }
                    if (val < 0 || val > 100) {
                        alert("The percent of cross-sectional surface area composed of tumor focus must be between 0 and 100")
                        document.getElementById("pctTumorArea").focus()
                        return false
                    }

                    val = document.getElementById("pctTumorCellularity").value
                    val = val.replace(/,/g, '')
                    if (val != null && isNaN(val)) {
                        alert("The percent of tumor cellularity by cell count of the entire slide must be a number")
                        document.getElementById("pctTumorCellularity").focus()
                        return false
                    }
                    if (val < 0 || val > 100) {
                        alert("The percent of tumor cellularity by cell count of the entire slide must be between 0 and 100")
                        document.getElementById("pctTumorCellularity").focus()
                        return false
                    }

                    val = document.getElementById("p1").value
                    val = val.replace(/,/g, '')
                    if (val != null && isNaN(val)) {
                        alert("The percent viable tumor by surface area must be a number")
                        document.getElementById("p1").focus()
                        return false
                    }
                    if (val < 0 || val > 100) {
                        alert("The percent viable tumor by surface area must be between 0 and 100")
                        document.getElementById("p1").focus()
                        return false
                    }

                    val = document.getElementById("p2").value
                    val = val.replace(/,/g, '')
                    if (val != null && isNaN(val)) {
                        alert("The percent necrotic tumor by surface area must be a number")
                        document.getElementById("p2").focus()
                        return false
                    }
                    if (val < 0 || val > 100) {
                        alert("The percent necrotic tumor by surface area must be between 0 and 100")
                        document.getElementById("p2").focus()
                        return false
                    }

                    val = document.getElementById("p3").value
                    val = val.replace(/,/g, '')
                    if (val != null && isNaN(val)) {
                        alert("The percent viable non-tumor tissue by surface area must be a number")
                        document.getElementById("p3").focus()
                        return false
                    }
                    if (val < 0 || val > 100) {
                        alert("The percent viable non-tumor tissue by surface area must be between 0 and 100")
                        document.getElementById("p3").focus()
                        return false
                    }

                    val = document.getElementById("p4").value
                    val = val.replace(/,/g, '')
                    if (val != null && isNaN(val)) {
                        alert("The percent viable non-tumor tissue by surface area must be a number")
                        document.getElementById("p4").focus()
                        return false
                    }
                    if (val < 0 || val > 100) {
                        alert("The percent viable non-tumor tissue by surface area must be between 0 and 100")
                        document.getElementById("p4").focus()
                        return false
                    }
                });    
            });

            function calculateTotal() {
                var pct1 = document.getElementById("p1").value
                var pct2 = document.getElementById("p2").value
                var pct3 = document.getElementById("p3").value
                var pct4 = document.getElementById("p4").value

                if (pct1 == '')
                    pct1=0
                if (pct2 == '')
                    pct2 = 0
                if (pct3 == '')
                    pct3 = 0
                if(pct4 == '')
                    pct4 = 0

                var hisTotal
                if (!isNaN(pct1) && !isNaN(pct2) && !isNaN(pct3) && !isNaN(pct4)) {
                    hisTotal = pct1*1 + pct2*1 + pct3*1 + pct4*1
                    document.getElementById("t").value = hisTotal    
                }
            }

            function rev() {
                var changed = document.getElementById("changed").value
                if(changed == "Y") {
                    alert("Please save the change!")
                    return false
                }
            }
            
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
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </div>
      </div>
      <div id="container" class="clearfix">
            <h1><g:message code="default.edit.label.with.slide.id" args="[entityName,slideId]" /></h1>
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
                <g:if test="${bpvPrcPathReviewInstance?.category == 'Kidney'}">
                    <g:hiddenField name="gradingSystem" value="Fuhrman Nuclear Grading System" />
                </g:if>
                <g:if test="${bpvPrcPathReviewInstance?.category == 'Colon' || bpvPrcPathReviewInstance?.category == 'Lung'}">
                    <g:hiddenField name="gradingSystem" value="AJCC 7th Edition Grading System" />
                </g:if>
                <input type="hidden" name="changed" value="N" id="changed"/>
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
                                    <td colspan="2" class="name ${errorMap.get('organOrigin')}" >
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
                                            <span id="oo" style="display: block" class="value ${errorMap.get('otherOrganOrigin')}"><br /><span class="subentry">Specify other organ of origin:</span> <g:textField id="otherOrganOrigin" name="otherOrganOrigin" value="${bpvPrcPathReviewInstance?.otherOrganOrigin}" /></span>  
                                        </g:if>
                                        <g:else>
                                            <span id="oo" style="display: none"  class="value ${errorMap.get('otherOrganOrigin')}"><br /><span class="subentry">Specify other organ of origin:</span> <g:textField id="otherOrganOrigin" name="otherOrganOrigin" value="${bpvPrcPathReviewInstance?.otherOrganOrigin}" /></span>  
                                        </g:else>
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td colspan="2" class="name ${errorMap.get('needHis')}" >
                                        ${labelNumber++}. Histologic type:
                                            <span data-msg="If <b>Other</b> was selected, record other histologic type" class="cahub-tooltip"></span>
                                        <g:if test="${bpvPrcPathReviewInstance?.category=='Ovary'||bpvPrcPathReviewInstance?.category=='Lung'}">
                                            <span data-msg="Specify histologic type details if the field pops up" class="cahub-tooltip"></span>
                                        </g:if>
                                        <div class="subentry value formvaluediv">
                                            <g:each in="${hisList}" status="i" var="his">
                                                <g:radio class="hisRadio" name="histologicType.id" value="${his.id}" id="his_${his.code}" checked="${bpvPrcPathReviewInstance?.histologicType?.id == his.id}"/>
                                                <label for="his_${his.code}">  ${his.name}
                                                    <g:if test="${his.who_code}"> (WHO code: ${his.who_code}) </g:if>
                                                </label><br/>
                                                
                                                <g:if test="${his.code == 'C7' && bpvPrcPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_C7')} hisDetail"  id="SC7"><br/>Specify types and %:<br/><g:textArea name="detail_C7" cols="40" rows="5" value="${bpvPrcPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code =='C7' && bpvPrcPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SC7" style="display:none"><br/>Specify types and %:<br/><g:textArea name="detail_C7" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>

                                                <g:if test="${his.code == 'C8' && bpvPrcPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_C8')} hisDetail" id="SC8"><br/>Specify types and %:<br/><g:textArea name="detail_C8" cols="40" rows="5" value="${bpvPrcPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code == 'C8' && bpvPrcPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SC8" style="display:none"><br/>Specify types and %:<br/><g:textArea name="detail_C8" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>

                                                <g:if test="${his.code == 'C9' && bpvPrcPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_C9')} hisDetail" id="SC9"><br/>Specify types and %:<br/><g:textArea name="detail_C9" cols="40" rows="5" value="${bpvPrcPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code == 'C9' && bpvPrcPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SC9" style="display:none"><br/>Specify types and %:<br/><g:textArea name="detail_C9" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>

                                                <g:if test="${his.code == 'C20' && bpvPrcPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_C20')} hisDetail"  id="SC20"><br/>Specify type:<br/><g:textArea name="detail_C20" cols="40" rows="5" value="${bpvPrcPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code == 'C20' && bpvPrcPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SC20" style="display:none"><br/>Specify type:<br/><g:textArea name="detail_C20" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>

                                                <g:if test="${his.code == 'OTHER' && bpvPrcPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_other')} hisDetail" id="SOTHER"><br/>Specify type:<br/><g:textArea name="detail_other" cols="40" rows="5" value="${bpvPrcPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code == 'OTHER' && bpvPrcPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SOTHER" style="display:none"><br/>Specify type:<br/><g:textArea name="detail_other" cols="40" rows="5" value="" /><br/><br/></span>
                                                </g:if>
                                                
                                                <g:if test="${his.code == 'C78' && bpvPrcPathReviewInstance.histologicType?.id == his.id}">
                                                    <span class="value ${errorMap.get('detail_C78')} hisDetail"  id="SC78"><br/>Specify types and %:<br/><g:textArea name="detail_C78" cols="40" rows="5" value="${bpvPrcPathReviewInstance.otherHistologicType}" /><br/><br/></span>
                                                </g:if>
                                                <g:if test="${his.code == 'C78' && bpvPrcPathReviewInstance.histologicType?.id != his.id}">
                                                    <span class="hisDetail" id="SC78" style="display:none"><br/>Specify types and %:<br/><g:textArea name="detail_C78" cols="40" rows="5" value="" /><br/><br/></span>
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
                                        <td class="value ${errorMap.get('sarcomatoid')}" >
                                            <div>
                                                <g:radio name="sarcomatoid" id='sa1' value="Not identified" checked="${bpvPrcPathReviewInstance?.sarcomatoid =='Not identified'}"/>&nbsp;<label for="sa1">Not identified</label><br/>
                                                <g:radio name="sarcomatoid" id='sa2' value="Present" checked="${bpvPrcPathReviewInstance?.sarcomatoid =='Present'}"/>&nbsp;<label for="sa2">Present</label>
                                            </div>
                                        </td>
                                    </tr>

                                    <tr class="prop">
                                        <td colspan="2" class="name" >
                                            <g:if test="${bpvPrcPathReviewInstance?.sarcomatoid == 'Present'}">
                                              <span id="sad" style="display: block" class="value ${errorMap.get('sarcomatoidDesc')}"><span class="subentry">Describe Sarcomatoid features:</span><br /><g:textArea class="textwide" id="sarcomatoidDesc" name="sarcomatoidDesc" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.sarcomatoidDesc}" /></span>  
                                            </g:if>
                                            <g:else>
                                                <span id="sad" style="display: none"  class="value ${errorMap.get('sarcomatoidDesc')}"><span class="subentry">Describe Sarcomatoid features:</span><br /><g:textArea class="textwide" id="sarcomatoidDesc" name="sarcomatoidDesc" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.sarcomatoidDesc}" /></span>  
                                            </g:else>
                                        </td>
                                    </tr>
                                </g:if>

                                <tr class="prop">
                                    <td class="name" >
                                        ${labelNumber++}. Greatest tumor dimension on slide:
                                    </td>
                                    <td class="value ${errorMap.get('tumorDimension')}" ><g:textField id="tumorDimension" name="tumorDimension" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'tumorDimension')}" /> (mm)</td>
                                </tr>  

                                <tr class="prop">
                                    <td class="name" >
                                        ${labelNumber++}. 
                                        <g:if test="${ (isLater5_3 >= 0) }">Percent of cross-sectional surface area of entire slide composed of tumor focus (Includes necrotic tumor) :</g:if>
                                        <g:else>Percent of cross-sectional surface area composed of tumor focus:</g:else>
                                    </td>
                                    <td class="value ${errorMap.get('pctTumorArea')}" ><g:textField id="pctTumorArea" name="pctTumorArea" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctTumorArea')}" /> %</td>
                                </tr>

                                <tr class="prop">
                                    <td class="name" >
                                        ${labelNumber++}. 
                                        <g:if test="${ (isLater5_3 >= 0) }">Percent of tumor nuclei by cell count of the entire slide (number of tumor epithelial cell nuclei as compared to all cell nuclei) :</g:if>
                                        <g:else>Percent of tumor cellularity by cell count of the entire slide:</g:else>
                                    </td>
                                    <td class="value ${errorMap.get('pctTumorCellularity')}" ><g:textField id="pctTumorCellularity" name="pctTumorCellularity" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctTumorCellularity')}" /> %</td>
                                </tr>
                                
                                <g:if test="${ (isLater5_3 >= 0) }">
                                    <tr class="prop">
                                        <td class="name" >
                                            ${labelNumber++}. Percent of cross-sectional surface area of entire slide composed of necrotic tissue:
                                        </td>
                                        <td class="value ${errorMap.get('pctNecroticTissue')}" ><g:textField id="pctNecroticTissue" name="pctNecroticTissue" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctNecroticTissue')}" /> %</td>
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
                                    <td colspan="2" class="name" >
                                        ${labelNumber++}. Histologic Profile Quantitative Assessment:
                                        <span data-msg="If present, describe Non-Cellular component" class="cahub-tooltip"></span><br />
                                        <table class="formvaluediv">
                                            <tr>
                                                <td >Percent Viable Tumor by surface area<g:if test="${ (isLater5_3 >= 0) }"> (not including stroma)</g:if></td>
                                                <td class="value ${errorMap.get('pctViablTumor')}"><g:textField id="p1" name="pctViablTumor" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctViablTumor')}" SIZE="10" /> %</td>
                                            </tr>
                                            <tr>
                                                <td >Percent Necrotic Tumor by surface area</td>
                                                <td class="value ${errorMap.get('pctNecroticTumor')}" ><g:textField id="p2" name="pctNecroticTumor" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctNecroticTumor')}" SIZE="10" /> %</td>
                                            </tr> 
                                            <tr>
                                                <td>
                                                    <g:if test="${ (isLater5_3 >= 0) }">Percent Tumor Stroma by surface area</g:if>
                                                    <g:else>Percent Viable Non-Tumor Tissue by surface area</g:else>
                                                </td>
                                                <td class="value ${errorMap.get('pctViableNonTumor')}" ><g:textField id="p3" name="pctViableNonTumor" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctViableNonTumor')}" SIZE="10" /> %</td>
                                            </tr>
                                            <tr>
                                                <td>Percent Non-Cellular Component by surface area (i.e., mucin, hemorrhage, blood clot, etc.)</td>
                                                <td class="value ${errorMap.get('pctNonCellular')}"><g:textField id="p4" name="pctNonCellular" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'pctNonCellular')}"  SIZE="10" /> %</td>
                                            </tr>
                                            <tr>
                                                <td class="formheader">Histologic Profile Total % (should equal 100%)</td>
                                                <td class="formheader ${errorMap.get('hisTotal')}"><g:textField readonly="readonly" id="t" name="hisTotal" value="${fieldValue(bean: bpvPrcPathReviewInstance, field: 'hisTotal')}" SIZE="10" /> %</td>
                                            </tr> 
                                        </table>
                                    </td>
                                </tr>
                                
                                <g:if test="${bpvPrcPathReviewInstance?.category=='Ovary'}">
                                    <tr class="prop clearborder">
                                        <td colspan="2" class="name ${errorMap.get('gradingSystem')}" >${labelNumber++}. What histologic grading system was applied?
                                            <div class="value formvaluediv subentry">
                                                <g:radio name="gradingSystem" id='g1' value="WHO Grading System" checked="${bpvPrcPathReviewInstance?.gradingSystem =='WHO Grading System'}"/>&nbsp;<label for="g1">WHO Grading System</label><br/>
                                                <g:radio name="gradingSystem" id='g2' value="Two-Tier Grading System" checked="${bpvPrcPathReviewInstance?.gradingSystem =='Two-Tier Grading System'}"/>&nbsp;<label for="g2">Two-Tier Grading System</label><br/>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr class="subentry prop" id="who" style="display:${bpvPrcPathReviewInstance?.gradingSystem == 'WHO Grading System' ? 'display' : 'none'}">
                                        <td colspan="2" class="name ${errorMap.get('grade')}" >Histologic Grade (WHO Grading System):
                                            <span data-msg="WHO Grading applies to all carcinomas, including serous carcinomas" class="cahub-tooltip"></span>
                                            <div class="value formvaluediv">
                                                <g:radio name="grade" id='w1' value="G1: Well Differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G1: Well Differentiated'}"/>&nbsp;<label for="w1">G1: Well Differentiated</label><br/>
                                                <g:radio name="grade" id='w2' value="G2: Moderately differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G2: Moderately differentiated'}"/>&nbsp;<label for="w2">G2: Moderately differentiated</label><br/>
                                                <g:radio name="grade" id='w3' value="G3: Poorly differentiated" checked="${bpvPrcPathReviewInstance?.grade =='G3: Poorly differentiated'}"/>&nbsp;<label for="w3">G3: Poorly differentiated</label><br/>
                                                <g:radio name="grade" id='w4' value="G4: Undifferentiated" checked="${bpvPrcPathReviewInstance?.grade =='G4: Undifferentiated'}"/>&nbsp;<label for="w4">G4: Undifferentiated</label><br/>
                                                <g:radio name="grade" id='w5' value="GX: Cannot be assessed" checked="${bpvPrcPathReviewInstance?.grade =='GX: Cannot be assessed'}"/>&nbsp;<label for="w5">GX: Cannot be assessed</label><br/>
                                                <g:radio name="grade" id='w6' value="" checked="${bpvPrcPathReviewInstance?.grade ==''}"/>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr class="subentry prop" id="t2t" style="display:${bpvPrcPathReviewInstance?.gradingSystem == 'Two-Tier Grading System' ? 'display' : 'none'}">
                                        <td colspan="2" class="name ${errorMap.get('grade')}" >Two-Tier Grading System Grade:
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
                                        <td colspan="2" class="name ${errorMap.get('grade')}" >${labelNumber++}. Histologic Grade (Fuhrman Nuclear Grading System):<br />
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
                                        <td colspan="2" class="name ${errorMap.get('grade')}" >${labelNumber++}. AJCC 7th Edition Histologic Grade:<br />
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
                                        <td colspan="2" class="name ${errorMap.get('grade')}" >${labelNumber++}. AJCC 7th Edition Histologic Grade:<br />
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
                                    <td class="value ${errorMap.get('histoEligible')}">
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
                                    <td class="value ${errorMap.get('meetsCriteria')}">
                                        <div>
                                            <g:radio name="meetsCriteria" id='m1' value="Yes" checked="${bpvPrcPathReviewInstance?.meetsCriteria =='Yes'}"/>&nbsp;<label for="m1">Yes</label><br/>
                                            <g:radio name="meetsCriteria" id='m2' value="No" checked="${bpvPrcPathReviewInstance?.meetsCriteria =='No'}"/>&nbsp;<label for="m2">No</label><br/>
                                        </div> 
                                    </td>
                                </tr>

                                <tr class="prop">
                                  <td colspan="2" class="name">
                                        <g:if test="${bpvPrcPathReviewInstance?.meetsCriteria == 'No'}">
                                            <span class="value ${errorMap.get('reasonNotMeet')}" id="f" style="display:display"><span class="subentry">Specify findings:</span><br /><g:textArea class="textwide" name="reasonNotMeet" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.reasonNotMeet}" id="re" /></span>
                                        </g:if>
                                        <g:else>
                                            <span id="f" style="display:none"><span class="subentry">Specify findings:</span><br /><g:textArea class="textwide" name="reasonNotMeet" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.reasonNotMeet}" id="re" /></span>
                                        </g:else>
                                    </td>
                                </tr>

                                <tr class="prop">
                                    <td colspan="2" class="name" >
                                            ${labelNumber++}. Pathology review comments:<br />
                                        <g:textArea class="textwide" name="pathologyComments" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.pathologyComments}" />
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
                                    <td class="value ${errorMap.get('consistentLocalPrc')}" >
                                        <div>
                                            <g:radio name="consistentLocalPrc" id='c1' value="Yes" checked="${bpvPrcPathReviewInstance?.consistentLocalPrc =='Yes'}"/>&nbsp;<label for="c1">Yes</label><br/>
                                            <g:radio name="consistentLocalPrc" id='c2' value="No" checked="${bpvPrcPathReviewInstance?.consistentLocalPrc =='No'}"/>&nbsp;<label for="c2">No</label><br/>
                                        </div>                                    
                                    </td>
                                </tr>

                                <tr class="prop">
                                  <td colspan="2" class="name">
                                        <g:if test="${bpvPrcPathReviewInstance?.consistentLocalPrc == 'No'}">
                                            <span  class="subentry value ${errorMap.get('reasonNotCons')}" id="f2" style="display:display">Specify findings: <br/><g:textArea class="textwide" name="reasonNotCons" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.reasonNotCons}" id="re2" /></span>
                                        </g:if>
                                        <g:else>
                                            <span class="subentry" id="f2" style="display:none">Specify findings:<br/><g:textArea class="textwide" name="reasonNotCons" cols="40" rows="5" value="${bpvPrcPathReviewInstance?.reasonNotCons}" id="re2" /></span>
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
                                        <td class="value ${errorMap.get('sameOriginType_' + bpvPrcExpSampleReview.id)}" >
                                            <div>
                                                <g:radio name="sameOriginType_${bpvPrcExpSampleReview.id}" id="sameOriginType_yes_${bpvPrcExpSampleReview.id}" value="Yes" checked="${bpvPrcExpSampleReview?.sameOriginType == 'Yes'}"/>&nbsp;<label for="sameOriginType_yes_${bpvPrcExpSampleReview.id}">Yes</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <g:radio name="sameOriginType_${bpvPrcExpSampleReview.id}" id="sameOriginType_no_${bpvPrcExpSampleReview.id}" value="No" checked="${bpvPrcExpSampleReview?.sameOriginType == 'No'}"/>&nbsp;<label for="sameOriginType_no_${bpvPrcExpSampleReview.id}">No</label>
                                            </div>
                                        </td>
                                    </tr>

                                    <tr class="prop clearborder">
                                        <td class="name" >
                                            This slide meets the microscopic analysis criteria for the BPV project of <20% necrosis and tumor content of &ge;50%  <g:if test="${ (isLater5_3 >= 0) }">tumor nuclei</g:if><g:else>of cells by surface area</g:else>:
                                        </td>
                                        <td class="value ${errorMap.get('meetsCriteria_' + bpvPrcExpSampleReview.id)}" >
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
                                         <td class="value ${errorMap.get('percTumor_' + bpvPrcExpSampleReview.id)}" >
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
                                            <span  class="value ${errorMap.get('reasonNotMeet_' + bpvPrcExpSampleReview.id)}">
                                                Comments:<br /><g:textArea class="textwide" name="reasonNotMeet_${bpvPrcExpSampleReview.id}" cols="40" rows="5" value="${bpvPrcExpSampleReview?.reasonNotMeet}" />
                                            </span>
                                        </td>
                                    </tr>
                                    
                                </tbody>
                            </table>
                        </g:each>
              
                    </div>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Save" id="sub" /></span>
                    <g:if test="${canReview}">
                        <span class="button"><g:actionSubmit class="save" action="review" value="Submit" onclick="return rev()" /></span>
                    </g:if>  
                    <span class="button"><input class="delete" type="button" value="Cancel" onclick="if(confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Discard unsaved data?')}'))window.location.href='${createLink(uri: '/')}${params.controller}/edit/${params.id}';"></input></span>
                </div>
            </g:form>
        </div>
         <g:if test="${AppSetting.findByCode('PRC_DISCLAIMER')?.bigValue}">
                   <br/>
                  <p>${AppSetting.findByCode('PRC_DISCLAIMER').bigValue}</p>
                </g:if>
    </body>
</html>
