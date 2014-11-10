<%@ page import="nci.obbr.cahub.forms.bpv.worksheet.Module1Sheet" %>
<g:set var="bodyclass" value="bpvworksheet editm1 xtrawide bpv-study" scope="request"/>
<html>
    <head>
        <meta name="layout" content="cahubTemplate"/>
  <g:set var="entityName" value="${message(code: 'module3NSheet.label', default: 'Module3NSheet')}" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
  
  
  <g:javascript>
    $(document).ready(function(){



    $(".recorddate").change(function(){
    // alert("heeee")
    var now = new Date();
    var dd= now.getDate();
    var MM= now.getMonth() + 1;
    var yyyy=now.getFullYear()
    var HH=now.getHours()
    var mm=now.getMinutes()

    var now_str = ''+MM+'/'+dd+'/'+yyyy+' ' +HH+":"+ mm

    var name=this.name
    var index = name.indexOf("_")
    id=name.substring(index+ 1)
    var prefix = name.substring(0, index)

    //alert("prefix: " + prefix)

    var t1,t2,t3, t4, mid,side


    //alert ("name:" + name + "  index: " + index + "id: " + id)

    if(prefix != 'sampleId4Frozen' && prefix != 'sampleId4Trans'){
    t1= document.getElementById('dateSampleInFixativeStr_' +id).value
    t2= document.getElementById('dateSampleProcEndStr_' + id).value
    t3= document.getElementById('dateSampleEmbStartedStr_' + id).value
    t4= document.getElementById('dateSampleInProcStr_' + id).value
    mid =document.getElementById('mid').value
    side = $("input[name='whichOvary']:checked").val();
    //alert("t1:" + t1 + "  t2: " + t2 + " t3: " + t3) 
    }





    // alert(this.id + this.value  + " dd:" + dd + " MM:" + MM + " yyyy:" + yyyy + " HH:" + HH + " mm:" + mm + " nowstr: "+ now_str)


    $.ajax({
    type:'POST',
    dataType: "text", 
    data: ({value:this.value, field:this.name, now:now_str, t1:t1, t2:t2, t3:t3, t4:t4, module:'4n', mid:mid, side:side}),
    url:'/cahubdataservices/bpvWorkSheet/update_time',
    success:function(data,textStatus){displayDate(data,textStatus);},
    error:function(XMLHttpRequest,textStatus,errorThrown){}
    });




    });


    $(".recordinterval").change(function(){
    //alert ("changed")
    var name=this.name
    var index = name.indexOf("_")
    id=name.substring(index+ 1)

    //alert ("name:" + name + "  index: " + index + "id: " + id)
    var t1= document.getElementById('dateSampleInFixativeStr_' +id).value
    var t2= document.getElementById('dateSampleProcEndStr_' + id).value
    var t3= document.getElementById('dateSampleEmbStartedStr_' + id).value
    var t4= document.getElementById('dateSampleInProcStr_' + id).value
    var mid =document.getElementById('mid').value
    var side = $("input[name='whichOvary']:checked").val();

    $.ajax({
    type:'POST',
    dataType: "text", 
    data: ({value:this.value, field:this.name,  t1:t1, t2:t2, t3:t3, t4:t4, module:'4n', mid:mid, side:side}),
    url:'/cahubdataservices/bpvWorkSheet/get_interval',
    success:function(data,textStatus){displayInterval(data,textStatus);},
    error:function(XMLHttpRequest,textStatus,errorThrown){}
    });


    });


    
     $(".weight").change(function(){
  
    var w= $(this).val()
    
    
    if(isNaN(w)){
    alert("The weight must be a number")
    
   
    }
    
    });

     $("#sub").click(function(){
        /** var w= document.getElementById("sampleFrWeight").value
          if(isNaN(w)){
                alert("The weight must be a number")
                document.getElementById("sampleFrWeight").focus()
             return false;
          }**/
          var found=false;
          
          $(".weight").each(function(){
            if(!found){
              var w = $(this).val();
              if(isNaN(w)){
                   alert("The weight must be a number");
                   $(this).focus();
                   found=true
              }
            }
         });
         
         if(found){
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



    if(obj.in1id !=null){
    document.getElementById(obj.in1id).innerHTML = obj.interval1
    }

    if(obj.in2id !=null){
    document.getElementById(obj.in2id).innerHTML = obj.interval2
    }

    if(obj.in3id !=null){
    document.getElementById(obj.in3id).innerHTML = obj.interval3
    }
    }

    function displayInterval(data, textStatus){
    var obj= jQuery.parseJSON(data)

    //alert("Hello " + " interval1: " + obj.interval1 + " in1id:" + obj.in1id )

    if(obj.in1id !=null){
    document.getElementById(obj.in1id).innerHTML = obj.interval1
    }

    if(obj.in2id !=null){
    document.getElementById(obj.in2id).innerHTML = obj.interval2
    }

    if(obj.in3id !=null){
    document.getElementById(obj.in3id).innerHTML = obj.interval3
    }
    }

  </g:javascript>

</head>
<body>
      <div id="nav" class="clearfix">
          <div id="navlist">
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
            <g:link class="edit" action="edit" id="${module4NSheetInstance.bpvWorkSheet.id}">FFPE Worksheet Main</g:link>
        </div>
      </div>
      <div id="container" class="clearfix">
    <h1>Edit Module IV Of Tissue Processing Worksheet</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${module4NSheetInstance}">
      <div class="errors">
        <g:renderErrors bean="${module4NSheetInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:warnings warningmap="${warningMap}" />
    
   <g:render template="/caseRecord/caseDetails" bean="${bpvWorkSheetInstance.caseRecord}" var="caseRecord" /> 
    <br></br>
    <g:form method="post" >
      <g:hiddenField id="mid" name="id" value="${module4NSheetInstance?.id}" />
      <g:hiddenField name="version" value="${module4NSheetInstance?.version}" />
     
      <table>
         <tr><td class="name">Select Priority<span id="bpvworksheet.priority" class="vocab-tooltip"></span></td><td class="value ${hasErrors(bean: module4NSheetInstance, field: 'priority', 'errors')}"><div><g:radio id ="p1" name="priority"  value="1" checked="${module4NSheetInstance.priority ==1}"  />&nbsp;<label for="p1">Priority I</label><br /><g:radio id ="p2" name="priority"  value="2" checked="${module4NSheetInstance.priority ==2}"  />&nbsp;<label for="p2">Priority II</label></div></td></tr>
       </table>
      <br></br>
      
      <g:if test ="${bpvWorkSheetInstance.caseRecord.primaryTissueType.code=='OVARY'}">
       <table>
         <tr><td class="name">Module IV tissue was collected from:<span id="bpvworksheet.moduletissuefrom" class="vocab-tooltip"></span></td><td class="value ${hasErrors(bean: module4NSheetInstance, field: 'whichOvary', 'errors')}"><div><g:radio id ="l" name="whichOvary"  value="L" checked="${module4NSheetInstance.whichOvary =='L'}"  />&nbsp;<label for="l">Left Ovary</label><br /><g:radio id ="r" name="whichOvary"  value="R" checked="${module4NSheetInstance.whichOvary =='R'}"  />&nbsp;<label for="r">Right Ovary</label></div></td></tr>
       </table>
      </g:if>
     
      <div class="line">
        
        
         <table class="bpvws">
          <tbody>
            <tr><th colspan="2" > Frozen Control Tissue Sample Information, Module IV</th></tr>
            <tr class="prop" >
              <td  valign="top" class="name" style="width:30%" >Barcode ID of frozen control tissue cryosette:<span id="bpvworksheet.barcodeidcryosette" class="vocab-tooltip"></span></td>
              <td class='${errorMap.get("sampleId4Frozen_" +module4NSheetInstance?.sampleFr?.id)} ${warningMap.get("sampleId_" +module4NSheetInstance?.sampleFr?.id)? "warnings" : ""}'><g:textField class="recorddate" name="sampleId4Frozen_${module4NSheetInstance?.sampleFr?.id}" value="${module4NSheetInstance?.sampleFr?.sampleId4Frozen}" /></td>
          </tr>
          <tr class="prop">
            <td  valign="top" class="name" >Date/Time the tissue sample was frozen in liquid nitrogen:<span id="bpvworksheet.datefrozenliquidnitrogen" class="vocab-tooltip"></span></td>
            <td class="${errorMap.get("dateSampleFrozenStr_" +module4NSheetInstance?.sampleFr?.id)}"><g:textField  name="dateSampleFrozenStr_${module4NSheetInstance?.sampleFr?.id}" value="${module4NSheetInstance?.sampleFr?.dateSampleFrozenStr}" /> </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="sampleFrWeight">Weight of frozen control block:</label>
            </td>
            <td valign="top" class="value ${errorMap.get("weight_" +module4NSheetInstance?.sampleFr?.id)}">
          <g:textField class="weight" id="sampleFrWeight" name="weight_${module4NSheetInstance?.sampleFr?.id}" value="${module4NSheetInstance?.sampleFr?.weight}" /> mg
          </td>
          </tr>

          </tbody>
        </table>
        <br></br>
      <table class="bpvws">
        <tr><th colspan="7" class="bpvws">Module IV: QC FFPE Section</th></tr>
        <tr>
          <th>Protocol</th>
          <th>Planned Delay to Fixation Time</th>
          <th>Scanned ID of cassette: Record first scan</th>
          <th>Date/Time That Cassette Was First Scanned Or Recorded</th>
          <th>Scanned ID of cassette: Record when placed in fixative</th>
          <th>Date/Time That Cassette Was Placed In Fixative</th>
          <th>Delay to Fixation Time (HH:MM)</th>
        </tr>
        <g:each in="[module4NSheetInstance.sampleQc]" status="i" var="s">
          <tr id="${s.protocolId}">
            <td>${s.protocol}</td>
            <td>${s.plannedDelay}</td>
            <td class='${errorMap.get("sampleId4Record_" +s.id)} ${warningMap.get("sampleId_" +s.id)? "warnings" : ""}'><g:textField class="recorddate" name="sampleId4Record_${s.id}" value="${s.sampleId4Record}" size="15" /></td>
          <td class="${errorMap.get("dateSampleRecordedStr_" +s.id)}" nowrap="nowrap"><g:textField  name="dateSampleRecordedStr_${s.id}" value="${s.dateSampleRecordedStr}" size="16" /></td>
          <td class="${errorMap.get("sampleId4Fixative_" +s.id)}"><g:textField class="recorddate"  name="sampleId4Fixative_${s.id}" value="${s.sampleId4Fixative}" size="16" /></td>
          <td class="${errorMap.get("dateSampleInFixativeStr_" +s.id)}" nowrap="nowrap"><g:textField class="recordinterval" id="dateSampleInFixativeStr_${s.id}" name="dateSampleInFixativeStr_${s.id}" value="${s.dateSampleInFixativeStr}" size="16" /></td>
          <td class="${warningMap?.get('interval1_' + s.id) ? 'warnings' : ''}"><span id="in1_${s.id}">${intervalMap.get('interval1_' + s.id)}</span></td>
          </tr>
        </g:each>
      </table>
      <br></br>

      <table class="bpvws">
        <tr><th colspan="10" class="bpvws">Module IV: QC FFPE Section<br />Continue entering data for QC FFPE Section</th></tr>
        <tr>
          <th>Protocol</th>
          <th>Scanned ID of cassette: Record when placed in tissue processor</th>
          <th>Date/Time Cassette Was Placed in Processor</th>                      
          <th>Date/Time Tissue Processor Cycle Ended</th>
          <th>Actual Time in Fixative (HH:MM)</th>
          <th>Scanned ID of cassette: Record when removed from tissue processor</th>
          <th>Date/Time Tissue Cassettes Were Removed From Tissue Processor</th>
          <th>Scanned ID of cassette: Record when tissue embedding was started</th>
          <th>Date/Time Tissue Embedding Was Started</th>
          <th>Time between Tissue Processor Cycle Completion and Embedding (HH:MM)</th>
        </tr>
        <g:each in="[module4NSheetInstance.sampleQc]" status="i" var="s">
          <tr id="${s.protocolId}">
            <td>${s.protocol}</td>
            <td class="${errorMap.get("sampleId4Proc_" +s.id)}"><g:textField class="recorddate" name="sampleId4Proc_${s.id}" value="${s.sampleId4Proc}" size="15" /></td>
          <td class="${errorMap.get("dateSampleInProcStr_" +s.id)}" nowrap="nowrap"><g:textField class="recordinterval" name="dateSampleInProcStr_${s.id}" value="${s.dateSampleInProcStr}" size="16" /></td>
          <td class="${errorMap.get("dateSampleProcEndStr_" +s.id)}" nowrap="nowrap"><g:textField class="recordinterval" id="dateSampleProcEndStr_${s.id}" name="dateSampleProcEndStr_${s.id}" value="${s.dateSampleProcEndStr}" size="16" /></td>
          <td class="${warningMap?.get('interval2_' + s.id) ? 'warnings' : ''}"><span id="in2_${s.id}">${intervalMap.get('interval2_' + s.id)}</span></td>
          <td class='${errorMap.get("sampleId4Removal_" +s.id)}'><g:textField class="recorddate" name="sampleId4Removal_${s.id}" value="${s.sampleId4Removal}" size="16" /></td>
          <td class="${errorMap.get("dateSampleRemovedStr_" +s.id)}" nowrap="nowrap"><g:textField  name="dateSampleRemovedStr_${s.id}" value="${s.dateSampleRemovedStr}" size="16" /></td>
          <td class='${errorMap.get("sampleId4Embedding_" +s.id)}'><g:textField class="recorddate" name="sampleId4Embedding_${s.id}" value="${s.sampleId4Embedding}" size="16" /></td>
          <td class="${errorMap.get("dateSampleEmbStartedStr_" +s.id)}" nowrap="nowrap"><g:textField class="recordinterval" id="dateSampleEmbStartedStr_${s.id}" name="dateSampleEmbStartedStr_${s.id}" value="${s.dateSampleEmbStartedStr}" size="16" /></td>
          <td><span id="in3_${s.id}">${intervalMap.get('interval3_' + s.id)}</span></td>


          </tr>
        </g:each>

      </table>
       </div>
      <div class="list">
      <br></br>
      <table class="bpvws">
        <tr><th colspan="8" class="bpvws">Module IV:</th></tr>
        <tr>
          <th>Protocol</th><th>Scanned ID of Cryosette: <br/> Record when frozen</th><th>Time Frozen</th> <th>Method of Freezing</th><th>Scanned ID of Cryosette: <br/> Record when transferred </th><th>Time Transferred</th><th>Transferred to</th><th>Weight (mg)</th>
        </tr>
         <g:each in="[module4NSheetInstance.sampleO, module4NSheetInstance.sampleP, module4NSheetInstance.sampleQ, module4NSheetInstance.sampleR, module4NSheetInstance.sampleS, module4NSheetInstance.sampleT]" status="i" var="s">
           <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
              <td>${s.protocol}</td>
              <td class='${errorMap.get("sampleId4Frozen_" +s.id)} ${warningMap.get("sampleId_" +s.id)? "warnings" : ""}'><g:textField class="recorddate" name="sampleId4Frozen_${s.id}" value="${s.sampleId4Frozen}" size="15" /></td>
              <td class="${errorMap.get("dateSampleFrozenStr_" +s.id)}"><g:textField  name="dateSampleFrozenStr_${s.id}" value="${s.dateSampleFrozenStr}" /></td>
              <td>${s.freezeMethod}</td>
              <td class="${errorMap.get("sampleId4Trans_" +s.id)}"><g:textField class="recorddate" name="sampleId4Trans_${s.id}" value="${s.sampleId4Trans}" size="15" /></td>
              <td class="${errorMap.get("dateSampleTransStr_" +s.id)}"><g:textField  name="dateSampleTransStr_${s.id}" value="${s.dateSampleTransStr}" /></td>
              <td>${s.transTo}</td>
              <td class="${errorMap.get("weight_" +s.id)}"><g:textField class="weight" name="weight_${s.id}" value="${s.weight}" size="15" /></td>
           </tr>
         </g:each>
      </table>
      </div>
      <div class="buttons">
        <span class="button"><g:actionSubmit id="sub" class="save" action="updatem4n" value="Save" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
