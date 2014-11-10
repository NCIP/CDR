<%@ page import="nci.obbr.cahub.forms.bpv.worksheet.BpvWorkSheet" %>
<g:set var="bodyclass" value="bpvworksheet edit bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${bpvWorkSheetInstance?.formMetadata?.cdrFormName}" />
  <g:set var="caseId" value="${bpvWorkSheetInstance?.caseRecord?.caseId}"/>
  <title><g:message code="default.edit.label" args="[entityName]" /></title>

  <g:javascript>
    var formVersion=${bpvWorkSheetInstance.formVersion}
    var pro3a = "Normal Adjacent Tissue.";
    var pro3b= "Excess Tumor Tissue."
    
    $(document).ready(function(){
    $("#MODULE1").change(function(){
    if(document.getElementById("MODULE1").checked)
    document.getElementById("div_MODULE1").style.display='block'
    else
    document.getElementById("div_MODULE1").style.display='none'
    });  

    $("#MODULE2").change(function(){
    if(document.getElementById("MODULE2").checked)
    document.getElementById("div_MODULE2").style.display='block'
    else
    document.getElementById("div_MODULE2").style.display='none'
    });  


    $(".recorddate").change(function(){
    //alert("heeee")
    var now = new Date();
    var dd= now.getDate();
    var MM= now.getMonth() + 1;
    var yyyy=now.getFullYear()
    var HH=now.getHours()
    var mm=now.getMinutes()

    var now_str = ''+MM+'/'+dd+'/'+yyyy+' ' +HH+":"+ mm





    // alert(this.id + this.value  + " dd:" + dd + " MM:" + MM + " yyyy:" + yyyy + " HH:" + HH + " mm:" + mm + " nowstr: "+ now_str)


    $.ajax({
    type:'POST',
    dataType: "text", 
    data: ({value:this.value, field:this.name, now:now_str}),
    url:'/cahubdataservices/bpvWorkSheet/update_time',
    success:function(data,textStatus){displayDate(data,textStatus);},
    error:function(XMLHttpRequest,textStatus,errorThrown){}
    });




    });


    $(":input").change(function(){
    document.getElementById("changed").value = "Y"
    //alert("Changed!")
    });


    $('#m1').click(function () {
      if($("#m1").is(':checked')){
          if(formVersion < 2){
         //  alert("what???")
            if(($("#m2").is(':checked') && $("#m3").is(':checked')) || ($("#m2").is(':checked') && $("#m4").is(':checked')) || ($("#m4").is(':checked') && $("#m3").is(':checked'))){
               alert('You can only select up to two modules!')
               $("#m1").prop('checked', false);
            }else{
              $("#em1").show();  // checked
            }
          }else{
                if(($("#m2").is(':checked') && $("#m5").is(':checked')) ){
                alert('You can only select up to two modules!')
                $("#m1").prop('checked', false);
                 }else{
                   $("#em1").show();  // checked
            }


         }
     }else
           $("#em1").hide(); 
     });
     
     $('#m2').click(function () {
      if($("#m2").is(':checked'))
          if(formVersion < 2){
              if(($("#m1").is(':checked') && $("#m3").is(':checked')) || ($("#m3").is(':checked') && $("#m4").is(':checked')) || ($("#m4").is(':checked') && $("#m1").is(':checked'))){
                 alert('You can only select up to two modules!')
                 $("#m2").prop('checked', false);
              }else{
                 $("#em2").show();  // checked
              }
          }else{
             if($("#m1").is(':checked') && $("#m5").is(':checked') ){
                 alert('You can only select up to two modules!')
                 $("#m2").prop('checked', false);
              }else{
                 $("#em2").show();  // checked
              }
          }
      else
         $("#em2").hide(); 
     });
     
     $('#m3').click(function () {
      if($("#m3").is(':checked'))
          if(($("#m1").is(':checked') && $("#m2").is(':checked')) || ($("#m2").is(':checked') && $("#m4").is(':checked')) || ($("#m4").is(':checked') && $("#m1").is(':checked'))){
             alert('You can only select up to two modules!')
             $("#m3").prop('checked', false);
          }else{
              $("#em3").show();  // checked
          }
         else
           $("#em3").hide(); 
     });
     
      $('#m4').click(function () {
      if($("#m4").is(':checked'))
          if(($("#m1").is(':checked') && $("#m2").is(':checked')) || ($("#m2").is(':checked') && $("#m3").is(':checked')) || ($("#m3").is(':checked') && $("#m1").is(':checked'))){
             alert('You can only select up to two modules!')
             $("#m4").prop('checked', false);
          }else{
             $("#em4").show();  // checked
          }
         else
           $("#em4").hide(); 
     });
     
     
      $('#m5').click(function () {
     // alert("clicked....")
      if($("#m5").is(':checked'))
          if(($("#m1").is(':checked') && $("#m2").is(':checked'))) {
             alert('You can only select up to two modules!')
             $("#m5").prop('checked', false);
          }else{
             $("#em5").show();  // checked
          }
         else
           $("#em5").hide(); 
     });
     
      $('#nat').click(function () {
      if($("#nat").is(':checked'))
          $("#enat").show();  // checked
         else
           $("#enat").hide(); 
     });
     
      $('#ett').click(function () {
      if($("#ett").is(':checked'))
          $("#eett").show();  // checked
         else
           $("#eett").hide(); 
     });
   
    });

    function setModule(module){
    //alert("in time delay func... hour:" + module)
    document.getElementById("editing_module").value=module
    }


    function displayDate(data, textStatus){
    var obj= jQuery.parseJSON(data)

    // alert("Hello " +obj.thedate + "  " + obj.fieldid)
    if(obj.fieldid != null  ){
    document.getElementById(obj.fieldid).value = obj.thedate
    }
    }


    function sub(){
    var changed = document.getElementById("changed").value
    if(changed == "Y"){
    alert("Please save the change!")
    return false
    }

    }

    function before_save(){
     var i = 0
    if(document.getElementById('m1').checked){
     i++;
    }
     if(document.getElementById('m2').checked){
     i++;
    }
     if(document.getElementById('m3').checked){
     i++;
    }
     if(document.getElementById('m4').checked){
     i++;
    }
    
     if(document.getElementById('m5').checked){
     i++;
    }
     
    if(i > 2){
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
    <h1><g:message code="default.edit.label.with.case.id" args="[entityName,caseId]"/></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${bpvWorkSheetInstance}">
      <div class="errors">
        <g:renderErrors bean="${bpvWorkSheetInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:queryDesc caserecord="${bpvWorkSheetInstance?.caseRecord}" form="bpvWorksheet" />
    
    <g:render template="/formMetadata/timeConstraint" bean="${bpvWorkSheetInstance.formMetadata}" var="formMetadata"/>
    <g:render template="/caseRecord/caseDetails" bean="${bpvWorkSheetInstance.caseRecord}" var="caseRecord" /> 
    
    
    <g:form method="post" >
      <g:hiddenField name="id" value="${bpvWorkSheetInstance?.id}" />
      <g:hiddenField name="version" value="${bpvWorkSheetInstance?.version}" />
      <g:hiddenField name="editing_module" id="editing_module" />
      <input type="hidden" name="changed" value="N" id="changed"/>
     <table class="tdtop">
  <tbody>
   
<tr class="prop">
  <td valign="top" class="name">
    <label for="parentSampleId">1. Parent tissue specimen ID:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: bpvWorkSheetInstance, field: 'parentSampleId', 'errors')}">
<!--<g:textField name="parentSampleId" value="${bpvWorkSheetInstance?.parentSampleId}" />-->
    ${bpvWorkSheetInstance?.parentSampleId}
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="experimentId">2. Experimental key ID:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: bpvWorkSheetInstance, field: 'experimentId', 'errors')}">
<g:textField class="recorddate" name="experimentId_${bpvWorkSheetInstance?.id}" value="${bpvWorkSheetInstance?.experimentId}" />
</td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="dateEidRecorded">3. Date & time experimental key barcode ID was recorded:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: bpvWorkSheetInstance, field: 'dateEidRecordedStr', 'errors')}">
<g:textField  name="dateEidRecordedStr" value="${bpvWorkSheetInstance?.dateEidRecordedStr}" />
</td>
</tr>



<tr class="prop">
  <td valign="top" class="name">
    <label for="editmodules">4. Edit modules:</label>
  </td>
  <td>
      <table class="modulecombination">
        
          <tr >
            <td>
          <g:checkBox name="m1" id="m1" value="${bpvWorkSheetInstance?.m1}" disabled="${bpvWorkSheetInstance?.sm1}"  />&nbsp;Module I&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class="${errorMap.get("module1Sheet")}" style="display:${bpvWorkSheetInstance?.m1?'display':'none'}" id="em1"><g:actionSubmit  action="editm" value="Edit" onclick="setModule('MODULE1')" id="${bpvWorkSheetInstance?.id}"/></span></td>
          </tr>
          <tr  >
            <td><g:checkBox name="m2" id="m2" value="${bpvWorkSheetInstance?.m2}" disabled="${bpvWorkSheetInstance?.sm2}" />&nbsp;Module II&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span  class="${errorMap.get("module2Sheet")}" style="display:${bpvWorkSheetInstance?.m2?'display':'none'}" id="em2"> <g:actionSubmit  action="editm" value="Edit" onclick="setModule('MODULE2')" id="${bpvWorkSheetInstance?.id}"/></span></td>
          </tr>
          <g:if test="${!bpvWorkSheetInstance.formVersion || bpvWorkSheetInstance.formVersion <2 }">
           <tr>
            <td><g:checkBox name="m3" id="m3" value="${bpvWorkSheetInstance?.m3}" disabled="${bpvWorkSheetInstance?.sm3}" />&nbsp;Module III&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class="${errorMap.get("module3NSheet")}" style="display:${bpvWorkSheetInstance?.m3?'display':'none'}" id="em3"> <g:actionSubmit  action="editm" value="Edit" onclick="setModule('MODULE3N')" id="${bpvWorkSheetInstance?.id}"/></span></td>
          </tr>
           <tr>
            <td><g:checkBox name="m4" id="m4" value="${bpvWorkSheetInstance?.m4}" disabled="${bpvWorkSheetInstance?.sm4}" />&nbsp;Module IV&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="${errorMap.get("module4NSheet")}" style="display:${bpvWorkSheetInstance?.m4?'display':'none'}" id="em4"> <g:actionSubmit  action="editm" value="Edit" onclick="setModule('MODULE4N')" id="${bpvWorkSheetInstance?.id}"/></span></td>
          </tr>
          </g:if>
            <g:else>
              <tr>
               <td><g:checkBox name="m5" id="m5" value="${bpvWorkSheetInstance?.m5}" disabled="${bpvWorkSheetInstance?.sm5}" />&nbsp;Module V&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class="${errorMap.get("module5Sheet")}" style="display:${bpvWorkSheetInstance?.m5?'display':'none'}" id="em5"> <g:actionSubmit  action="editm" value="Edit" onclick="setModule('MODULE5')" id="${bpvWorkSheetInstance?.id}"/></span></td>
          </tr>
            </g:else>
          <tr  >
             <td><g:checkBox name="nat" id="nat" value="${bpvWorkSheetInstance?.nat}" disabled="${bpvWorkSheetInstance?.snat}" />&nbsp;Normal Adjacent Tissue&nbsp;&nbsp;&nbsp;&nbsp;<span  class="${errorMap.get("module3Sheet")}" style="display:${bpvWorkSheetInstance?.nat?'display':'none'}" id="enat"> <g:actionSubmit  action="editm" value="Edit" onclick="setModule('MODULE3')" id="${bpvWorkSheetInstance?.id}"/></span></td>
          </tr>
           <tr  >
             <td><g:checkBox name="ett" id="ett" value="${bpvWorkSheetInstance?.ett}" disabled="${bpvWorkSheetInstance?.sett}" />&nbsp;Additional Tumor Tissue&nbsp;&nbsp;&nbsp;&nbsp;<span  class="${errorMap.get("module4Sheet")}" style="display:${bpvWorkSheetInstance?.ett?'display':'none'}" id="eett"><g:actionSubmit  action="editm" value="Edit" onclick="setModule('MODULE4')" id="${bpvWorkSheetInstance?.id}"/></div></td>
          </tr>
        
      </table>
  </td>
</tr>

<tr class="prop">
  <td valign="top" class="name">
    <label for="comments">5. Comments:</label>
  </td>
  <td valign="top" class="value ${hasErrors(bean: bpvWorkSheetInstance, field: 'comments', 'errors')}">
    <g:textArea style="height:58px;width:350px;" name="comments" value="${bpvWorkSheetInstance?.comments}" />
</td>
</tr>


</tbody>
</table>
      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update" value="Save" onclick="return before_save()"/></span>
        <g:if test="${canSubmit == 'Yes'}">
          <span class="button"><g:actionSubmit class="save" action="submit" value="Submit" onclick="return sub()" /></span>
        </g:if>
      </div>
    </g:form>
  </div>
</body>
</html>
