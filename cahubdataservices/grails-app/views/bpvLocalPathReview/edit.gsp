<%@ page import="nci.obbr.cahub.forms.bpv.BpvLocalPathReview" %>
<g:set var="bodyclass" value="bpvlocalpathreview edit bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
        <g:set var="entityName" value="${bpvLocalPathReviewInstance?.formMetadata?.cdrFormName}" />
        <g:set var="slideId" value="${bpvLocalPathReviewInstance?.slideRecord?.slideId}" />
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
            <g:hasErrors bean="${bpvLocalPathReviewInstance}">
                <div class="errors">
                    <g:renderErrors bean="${bpvLocalPathReviewInstance}" as="list" />
                </div>
            </g:hasErrors>
            <g:queryDesc caserecord="${bpvLocalPathReviewInstance?.caseRecord}" form="bpvLocalPath" />
            <g:form method="post" >
                <g:hiddenField name="id" value="${bpvLocalPathReviewInstance?.id}" />
                <g:hiddenField name="version" value="${bpvLocalPathReviewInstance?.version}" />
                <g:if test="${bpvLocalPathReviewInstance?.category=='Kidney'}">
                    <g:hiddenField name="gradingSystem" value="Fuhrman Nuclear Grading System" />
                </g:if>
                <g:if test="${bpvLocalPathReviewInstance?.category=='Colon'||bpvLocalPathReviewInstance?.category=='Lung'}">
                    <g:hiddenField name="gradingSystem" value="AJCC 7th Edition Grading System" />
                </g:if>
                <input type="hidden" name="changed" value="N" id="changed"/>
                <div class="dialog">
                    <g:render template="/formMetadata/timeConstraint" bean="${bpvLocalPathReviewInstance.formMetadata}" var="formMetadata"/>
                    <g:render template="/caseRecord/caseDetails" bean="${bpvLocalPathReviewInstance.caseRecord}" var="caseRecord" />
                    
                    <div class="list">
                        <table class="tdwrap">
                            <tbody>
                                <g:set var="isLater5_3" value="${ nci.obbr.cahub.util.ComputeMethods.compareCDRVersion(bpvLocalPathReviewInstance.caseRecord.cdrVer, '5.3') }" />
                                                                
                               
                        
                                <g:if test="${ (isLater5_3 < 0)}">
                                    <g:render template="edit1" bean="${bpvLocalPathReviewInstance}" />
                                    <g:render template="edit2" bean="${bpvLocalPathReviewInstance}" />
                                </g:if>
                                <g:else>
                                    <g:set var="seq" value="${1}" />
                                    <g:render template="formFieldInc_ver53_1" bean="${bpvLocalPathReviewInstance}" />
                                    <g:render template="formFieldInc_ver53_2" bean="${bpvLocalPathReviewInstance}" />
                                    <g:render template="formFieldInc_ver53_3" bean="${bpvLocalPathReviewInstance}" />
                                </g:else>
                                
                            </tbody>
                        </table>     
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
    </body>
</html>
