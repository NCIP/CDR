<%@ page import="nci.obbr.cahub.forms.bpv.worksheet.Module1Sheet" %>
<g:set var="bodyclass" value="bpvworksheet editm4 bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'module4Sheet.label', default: 'Module4Sheet')}" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
  
   <g:javascript>
    $(document).ready(function(){
    
    $("#f1").change(function(){
      
        $("#r").hide()
        
    });
    
    
     $("#f2").change(function(){
      
        $("#r").show()
        
    });
    
    $(".w").change(function(){
  
    var w= $(this).val()
    
    
    if(isNaN(w)){
    alert("The weight must be a number")
     //document.getElementById("w1").focus()
     return false
   
    }
   
    });  
    
    
     $(".recorddate").change(function(){
     
        // alert("heeee")
     
        var now = new Date();
        var dd= now.getDate();
        var MM= now.getMonth() + 1;
        var yyyy=now.getFullYear()
        var HH=now.getHours()
        var mm=now.getMinutes()

        var now_str = ''+MM+'/'+dd+'/'+yyyy+' ' +HH+":"+ mm
        
        mid =document.getElementById('mid').value

      
        $.ajax({
        type:'POST',
        dataType: "text", 
        data: ({value:this.value, field:this.name, now:now_str, mid:mid}),
        url:'/cahubdataservices/bpvWorkSheet/update_time',
        success:function(data,textStatus){displayDate(data,textStatus);},
        error:function(XMLHttpRequest,textStatus,errorThrown){}
        });

     
     
    
     });  
 
    
    
    
     $("#sub").click(function(){
         var w1= document.getElementById("w1").value
          if(w1 != null && isNaN(w1)){
                alert("The weight must be a number")
                document.getElementById("w1").focus()
             return false;
          }
           var w2= document.getElementById("w2").value
          if(w2!=null && isNaN(w2)){
                alert("The weight must be a number")
                document.getElementById("w2").focus()
             return false;
          }
          var w3= document.getElementById("w3").value
          if(w3!=null && isNaN(w3)){
                alert("The weight must be a number")
                document.getElementById("w3").focus()
             return false;
          }
          var w4= document.getElementById("w4").value
          if(w4 != null && isNaN(w4)){
                alert("The weight must be a number")
                document.getElementById("w4").focus()
             return false;
          }
           var w5= document.getElementById("w5").value
          if(w5 != null && isNaN(w5)){
                alert("The weight must be a number")
                document.getElementById("w5").focus()
             return false;
          }
          var w6= document.getElementById("w6").value
          if(w6 != null && isNaN(w6)){
                alert("The weight must be a number")
                document.getElementById("w6").focus()
             return false;
          }
         
       }); 
  }); 
  
  
  function displayDate(data, textStatus){
        var obj= jQuery.parseJSON(data)

        // alert("Hello " +obj.thedate  )

        if(obj.fieldid != null  ){
           document.getElementById(obj.fieldid).value = obj.thedate
        }
     }
  
  </g:javascript>
</head>
<body>
        <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="edit" action="edit" id="${module4SheetInstance.bpvWorkSheet.id}">FFPE Worksheet Main</g:link>
        </div>
      </div>
 <div id="container" class="clearfix">
    <h1>Edit Additional Tumor Tissue Processing Worksheet</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${module4SheetInstance}">
      <div class="errors">
        <g:renderErrors bean="${module4SheetInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:warnings warningmap="${warningMap}" />
    <g:render template="/caseRecord/caseDetails" bean="${bpvWorkSheetInstance.caseRecord}" var="caseRecord" /> 
    
    <g:form method="post" >
      <g:hiddenField id="mid" name="id" value="${module4SheetInstance?.id}" />
      <g:hiddenField name="version" value="${module4SheetInstance?.version}" />
        <div class="dialog line">
          <table>
           <tbody>
              
                  <tr class="prop">
                    <td valign="top" class="name">
                     <label for="ttDisecPerformedBy">Tissue dissection performed by:</label>
                       </td>
                        <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttDisecPerformedBy', 'errors')}">
                          <g:textField name="ttDisecPerformedBy" value="${module4SheetInstance?.ttDisecPerformedBy}" />
                        </td>
                   </tr>
                   
                     <tr class="prop">
                    <td valign="top" class="name">
                     <label for="fixativeTimeInRange">Were the additional tumor tissue FFPE specimens processed with <1 hour delay to fixation and  23 hour time in fixative?</label>
                       </td>
                        <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFixativeTimeInRange', 'errors')}">
                          <div>
                            <g:radio name="ttFixativeTimeInRange" id='f1' value="Yes" checked="${module4SheetInstance.ttFixativeTimeInRange =='Yes'}"  />&nbsp;<label for="f1">Yes</label><br />
                            <g:radio name="ttFixativeTimeInRange" id='f2' value="No" checked="${module4SheetInstance.ttFixativeTimeInRange =='No'}"  />&nbsp;<label for="f2">No</label>
                          </div>
                        </td>
                   </tr>
                   
                    <tr id="r"  style="display:${module4SheetInstance.ttFixativeTimeInRange =='No'?'display':'none'}">
                       <td valign="top" class="name">
                      <label for="ttFixativeTimeInRange">If No, please specify:</label>
                      </td>
                     <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttReasonNotInRange', 'errors')}">
                             <g:textArea style="height:38px;width:200px;" name="ttReasonNotInRange" value="${module4SheetInstance?.ttReasonNotInRange}" />
                        </td>
                </tr>
                   
                <tr class="prop">
                      <td valign="top" class="name">
                      <label for="ttComment">Comments/issues with tissue receipt of deviations from SOP:</label>
                      </td>
                        <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttComment', 'errors')}">
                             <g:textArea style="height:38px;width:200px;" name="ttComment" value="${module4SheetInstance?.ttComment}" />
                        </td>
                </tr>
                        
                              
            </tbody>
            </table>
           </div>
          <br></br>
               <div class="list">
              <table>
                <th>&nbsp;</th><th>Barcode ID</th><th>Date/Time of Preservation (fixed or frozen)</th><th>Weight(mg)</th>
                <tr class="odd">
                <td>FFPE tissue 1</td>  
                <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeId1', 'errors')} ${warningMap.get("ttFfpeId1")? "warnings" : ""}">
                                    <g:textField class="recorddate" name="ttFfpeId1" value="${module4SheetInstance?.ttFfpeId1}" />
                </td>
                 <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeTimeStr1', 'errors')}">
                                    <g:textField name="ttFfpeTimeStr1" value="${module4SheetInstance?.ttFfpeTimeStr1}" />
                </td>
                 <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeWeight1', 'errors')}">
                                    <g:textField class="w" id="w1"  name="ttFfpeWeight1" value="${fieldValue(bean: module4SheetInstance, field: 'ttFfpeWeight1')}" />
                 </td>
                 </tr>
                  <tr class="even">
                <td>FFPE tissue 2</td>  
                <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeId2', 'errors')} ${warningMap.get("ttFfpeId2")? "warnings" : ""}">
                       <g:textField class="recorddate" name="ttFfpeId2" value="${module4SheetInstance?.ttFfpeId2}" />
                </td>
                 <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeTimeStr2', 'errors')}">
                                    <g:textField name="ttFfpeTimeStr2" value="${module4SheetInstance?.ttFfpeTimeStr2}" />
                </td>
                 <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeWeight2', 'errors')}">
                          <g:textField class="w" id="w2" name="ttFfpeWeight2" value="${fieldValue(bean: module4SheetInstance, field: 'ttFfpeWeight2')}" />
                 </td>
                 </tr>
                 
                  <tr class="odd">
                <td>FFPE tissue 3</td>  
                <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeId3', 'errors')} ${warningMap.get("ttFfpeId3")? "warnings" : ""}">
                       <g:textField class="recorddate"  name="ttFfpeId3" value="${module4SheetInstance?.ttFfpeId3}" />
                </td>
                  <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeTimeStr3', 'errors')}">
                                    <g:textField name="ttFfpeTimeStr3" value="${module4SheetInstance?.ttFfpeTimeStr3}" />
                </td>
                 <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFfpeWeight3', 'errors')}">
                          <g:textField class="w" id="w3" name="ttFfpeWeight3" value="${fieldValue(bean: module4SheetInstance, field: 'ttFfpeWeight3')}" />
                 </td>
                 </tr>
                  <tr class="even">
                    <td>Frozen tissues 1</td>
                   <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenId1', 'errors')} ${warningMap.get("ttFrozenId1")? "warnings" : ""}">
                                    <g:textField class="recorddate" name="ttFrozenId1" value="${module4SheetInstance?.ttFrozenId1}" />
                   </td>
                    <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenTimeStr1', 'errors')}">
                                    <g:textField name="ttFrozenTimeStr1" value="${module4SheetInstance?.ttFrozenTimeStr1}" />
                </td>
                   <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenWeight1', 'errors')}">
                                    <g:textField class="w" id="w4" name="ttFrozenWeight1" value="${fieldValue(bean: module4SheetInstance, field: 'ttFrozenWeight1')}" />
                   </td>
                  </tr>
                   <tr class="odd">
                    <td>Frozen tissues 2</td>
                   <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenId2', 'errors')} ${warningMap.get("ttFrozenId2")? "warnings" : ""}">
                                    <g:textField class="recorddate" name="ttFrozenId2" value="${module4SheetInstance?.ttFrozenId2}" />
                   </td>
                      <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenTimeStr2', 'errors')}">
                                    <g:textField name="ttFrozenTimeStr2" value="${module4SheetInstance?.ttFrozenTimeStr2}" />
                </td>
                   <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenWeight2', 'errors')}">
                                    <g:textField class="w" id="w5" name="ttFrozenWeight2" value="${fieldValue(bean: module4SheetInstance, field: 'ttFrozenWeight2')}" />
                   </td>
                  </tr>
                   <tr class="even">
                    <td>Frozen tissues 3</td>
                   <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenId3', 'errors')} ${warningMap.get("ttFrozenId3")? "warnings" : ""}">
                                    <g:textField class="recorddate" name="ttFrozenId3" value="${module4SheetInstance?.ttFrozenId3}" />
                   </td>
                      <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenTimeStr3', 'errors')}">
                                    <g:textField name="ttFrozenTimeStr3" value="${module4SheetInstance?.ttFrozenTimeStr3}" />
                </td>
                   <td valign="top" class="value ${hasErrors(bean: module4SheetInstance, field: 'ttFrozenWeight3', 'errors')}">
                                    <g:textField class="w" id="w6" name="ttFrozenWeight3" value="${fieldValue(bean: module4SheetInstance, field: 'ttFrozenWeight3')}" />
                   </td>
                  </tr>
                  
               </table>
                
               </div>
        
          <br></br>
         
          
     

     


      <div class="buttons">
        <span class="button"><g:actionSubmit id="sub" class="save" action="updatem4" value="Save" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
