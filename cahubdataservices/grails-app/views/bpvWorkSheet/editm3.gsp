<%@ page import="nci.obbr.cahub.forms.bpv.worksheet.Module1Sheet" %>
<g:set var="bodyclass" value="bpvworksheet editm3 bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'module3Sheet.label', default: 'Module3Sheet')}" />
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
    
    
    if(w!=null && isNaN(w)){
    alert("The weight must be a number")
     //document.getElementById("w1").focus()
     return false
     }
   
      });  
    
    
     $(".recorddate").change(function(){
     
    //     alert("heeee")
     
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
       
         
        
           var w7= document.getElementById("w7").value
          if(w7!= null && isNaN(w7)){
                alert("The weight must be a number")
                document.getElementById("w7").focus()
             return false;
          }
           var w8= document.getElementById("w8").value
          if(w8 != null && isNaN(w8)){
                alert("The weight must be a number")
                document.getElementById("w8").focus()
             return false;
          }
          var w9= document.getElementById("w9").value
          if(w9 != null && isNaN(w9)){
                alert("The weight must be a number")
                document.getElementById("w9").focus()
             return false;
          }
          var w10= document.getElementById("w10").value
          if(w10 != null && isNaN(w10)){
                alert("The weight must be a number")
                document.getElementById("w10").focus()
             return false;
          }
           var w11= document.getElementById("w11").value
          if(w11 !=null  &&  isNaN(w11)){
                alert("The weight must be a number")
                document.getElementById("w11").focus()
             return false;
          }
          var w12= document.getElementById("w12").value
          if(w12 != null && isNaN(w12)){
                alert("The weight must be a number")
                document.getElementById("w12").focus()
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
            <g:link class="edit" action="edit" id="${module3SheetInstance.bpvWorkSheet.id}">FFPE Worksheet Main</g:link>
          </div>
      </div>
      <div id="container" class="clearfix">
    <h1>Edit Normal Adjacent Tissue Processing Worksheet</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${module3SheetInstance}">
      <div class="errors">
        <g:renderErrors bean="${module3SheetInstance}" as="list" />
      </div>
    </g:hasErrors>
     <g:warnings warningmap="${warningMap}" />
    
    <g:render template="/caseRecord/caseDetails" bean="${bpvWorkSheetInstance.caseRecord}" var="caseRecord" /> 
    
    <g:form method="post" >
      <g:hiddenField id="mid" name="id" value="${module3SheetInstance?.id}" />
      <g:hiddenField name="version" value="${module3SheetInstance?.version}" />
      
         
           <div class="dialog line">
              
          <table>
           <tbody>
               
                  <tr class="prop">
                    <td valign="top" class="name">
                     <label for="ntDisecPerformedBy">Tissue dissection performed by:</label>
                       </td>
                        <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntDisecPerformedBy', 'errors')}">
                          <g:textField name="ntDisecPerformedBy" value="${module3SheetInstance?.ntDisecPerformedBy}" />
                        </td>
                   </tr>
                   
                    <tr class="prop">
                    <td valign="top" class="name">
                     <label for="fixativeTimeInRange">Were the normal adjacent tissue FFPE specimens processed with <1 hour delay to fixation and  23 hour time in fixative?</label>
                       </td>
                        <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFixativeTimeInRange', 'errors')}">
                          <div>
                            <g:radio name="ntFixativeTimeInRange" id='f1' value="Yes" checked="${module3SheetInstance.ntFixativeTimeInRange =='Yes'}"  />&nbsp;<label for="f1">Yes</label><br />
                            <g:radio name="ntFixativeTimeInRange" id='f2' value="No" checked="${module3SheetInstance.ntFixativeTimeInRange =='No'}"  />&nbsp;<label for="f2">No</label>
                          </div>
                        </td>
                   </tr>
                   
                    <tr id="r"  style="display:${module3SheetInstance.ntFixativeTimeInRange =='No'?'display':'none'}">
                       <td valign="top" class="name">
                      <label for="ntFixativeTimeInRange">If No, please specify:</label>
                      </td>
                     <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntReasonNotInRange', 'errors')}">
                             <g:textArea style="height:38px;width:200px;" name="ntReasonNotInRange" value="${module3SheetInstance?.ntReasonNotInRange}" />
                        </td>
                </tr>
                   
                <tr class="prop">
                      <td valign="top" class="name">
                      <label for="ntComment">Comments/issues with tissue receipt of deviations from SOP:</label>
                      </td>
                        <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntComment', 'errors')}">
                             <g:textArea style="height:38px;width:200px;" name="ntComment" value="${module3SheetInstance?.ntComment}" />
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
                <td valign="top" class='value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeId1', 'errors')} ${warningMap.get("ntFfpeId1")? "warnings" : ""}'>
                                    <g:textField class="recorddate" name="ntFfpeId1" value="${module3SheetInstance?.ntFfpeId1}" />
                </td>
                 <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeTimeStr1', 'errors')}">
                                    <g:textField name="ntFfpeTimeStr1" value="${module3SheetInstance?.ntFfpeTimeStr1}" />
                </td>
                 <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeWeight1', 'errors')}">
                                    <g:textField class="w" id="w7" name="ntFfpeWeight1" value="${fieldValue(bean: module3SheetInstance, field: 'ntFfpeWeight1')}" />
                 </td>
                 </tr>
                  <tr class="even">
                <td>FFPE tissue 2</td>  
                <td valign="top" class='value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeId2', 'errors')} ${warningMap.get("ntFfpeId2")? "warnings" : ""}'>
                       <g:textField class="recorddate" name="ntFfpeId2" value="${module3SheetInstance?.ntFfpeId2}" />
                </td>
                <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeTimeStr2', 'errors')}">
                                    <g:textField name="ntFfpeTimeStr2" value="${module3SheetInstance?.ntFfpeTimeStr2}" />
                </td>
                 <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeWeight2', 'errors')}">
                          <g:textField class="w" id="w8" name="ntFfpeWeight2" value="${fieldValue(bean: module3SheetInstance, field: 'ntFfpeWeight2')}" />
                 </td>
                 </tr>
                 
                  <tr class="odd">
                <td>FFPE tissue 3</td>  
                <td valign="top" class='value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeId3', 'errors')} ${warningMap.get("ntFfpeId3")? "warnings" : ""}'>
                       <g:textField class="recorddate" name="ntFfpeId3" value="${module3SheetInstance?.ntFfpeId3}" />
                </td>
                 <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeTimeStr3', 'errors')}">
                                    <g:textField name="ntFfpeTimeStr3" value="${module3SheetInstance?.ntFfpeTimeStr3}" />
                </td>
                 <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFfpeWeight3', 'errors')}">
                          <g:textField class="w" id="w9" name="ntFfpeWeight3" value="${fieldValue(bean: module3SheetInstance, field: 'ntFfpeWeight3')}" />
                 </td>
                 </tr>
                  <tr class="even">
                    <td>Frozen tissues 1</td>
                   <td valign="top" class='value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenId1', 'errors')} ${warningMap.get("ntFrozenId1")? "warnings" : ""}'>
                                    <g:textField class="recorddate" name="ntFrozenId1" value="${module3SheetInstance?.ntFrozenId1}" />
                   </td>
                    <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenTimeStr1', 'errors')}">
                                    <g:textField name="ntFrozenTimeStr1" value="${module3SheetInstance?.ntFrozenTimeStr1}" />
                </td>
                   <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenWeight1', 'errors')}">
                                    <g:textField class="w" id="w10" name="ntFrozenWeight1" value="${fieldValue(bean: module3SheetInstance, field: 'ntFrozenWeight1')}" />
                   </td>
                  </tr>
                   <tr class="odd">
                    <td>Frozen tissues 2</td>
                   <td valign="top" class='value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenId2', 'errors')} ${warningMap.get("ntFrozenId2")? "warnings" : ""}'>
                                    <g:textField class="recorddate" name="ntFrozenId2" value="${module3SheetInstance?.ntFrozenId2}" />
                   </td>
                    <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenTimeStr2', 'errors')}">
                                    <g:textField name="ntFrozenTimeStr2" value="${module3SheetInstance?.ntFrozenTimeStr2}" />
                </td>
                   <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenWeight2', 'errors')}">
                                    <g:textField class="w" id="w11" name="ntFrozenWeight2" value="${fieldValue(bean: module3SheetInstance, field: 'ntFrozenWeight2')}" />
                   </td>
                  </tr>
                   <tr class="even">
                    <td>Frozen tissues 3</td>
                   <td valign="top" class='value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenId3', 'errors')} ${warningMap.get("ntFrozenId3")? "warnings" : ""}'>
                                    <g:textField class="recorddate" name="ntFrozenId3" value="${module3SheetInstance?.ntFrozenId3}" />
                   </td>
                      <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenTimeStr3', 'errors')}">
                                    <g:textField name="ntFrozenTimeStr3" value="${module3SheetInstance?.ntFrozenTimeStr3}" />
                 </td>
                   <td valign="top" class="value ${hasErrors(bean: module3SheetInstance, field: 'ntFrozenWeight3', 'errors')}">
                                    <g:textField class="w" id="w12" name="ntFrozenWeight3" value="${fieldValue(bean: module3SheetInstance, field: 'ntFrozenWeight3')}" />
                   </td>
                  </tr>
                  
               </table>
                
               </div>
          
     

     


      <div class="buttons">
        <span class="button"><g:actionSubmit id="sub" class="save" action="updatem3" value="Save" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
