$(function() {
   // $( "#bbf-tabs" ).tabs();
    activateDeletes();
    emptyGlobalDialogOnClose();
    $("#msnewadd").hide() // dialog box for adding a missing structure
    $("#adddamage").hide() // dialog box for adding a damage observation
       
    $("#procImmedYes").hide() //  this is the dialog box to show  for Q 7 ( ans:Yes)
    var brainProcImmed = $('input:radio[name=wasBrainProcImmed]:checked').val();              
        // $("#procImmedNo").hide();
        if(brainProcImmed =="No"){
            $("#procImmedNo").show();
        }
        else{
            $("#procImmedNo").hide() //  this is the gross evaluation not acceptbal reasons table
        }
        
        var grossacceptable = $('input:radio[name=acceptForFurtherProc]:checked').val();              
        // $("#procImmedNo").hide();
        if(grossacceptable =="No"){
            $("#grossProcNo").show();
        }
        else{
            $("#grossProcNo").hide() // 
        }
      
    //add1 is add button for new structures
    $("#add1").click(function(){
        $("#ca-dialog").html("<label for=\"structName\">Enter other missing structure: </label><input size=\"25\" name=\"structNameTemp\" value=\"\" id=\"structNameTemp\" type=\"text\">");
        inputCharLimit();
        $("#ca-dialog").dialog({
            title: "Add other missing structure",
            autoOpen: false,
            modal: true,
            height:150,
            width:400,
            buttons : {
                    "Save" : function() {
                        $("#ca-dialog .redtext").remove();
                    if($("#structNameTemp").val().length == 0){
                        $("#ca-dialog").append("<span class=\"redtext\">Please specify name  of the missing Brain structure to be added</span>");
                        $( "#structNameTemp").focus();
                    } else {
                        $("#structName").val($("#structNameTemp").val());
                        $.ajax({
                         url: '/cahubdataservices/brainBankFeedback/save?vessel=json',
                         type: 'POST',
                         data: $("form.bbfform").serialize(),
                         success:function(data){
                            var newEntry = data.success;
                            var newEntryStr = "";
                            $(".new-entry").removeClass("new-entry");
                            if(newEntry != null && newEntry != undefined) {
                                var newEntryid = newEntry[1].id;
                                var newEntrystructName = newEntry[1].structName;
                                newEntryStr = "<li class=\"\" id=\"row" + newEntryid + "\">";
                                newEntryStr += "<input size=\"30\" name=\"editstructName_" + newEntryid + "\"  value=\"" + newEntrystructName + "\"/>";
                                newEntryStr += "&nbsp;<a class=\"delete-struct ui-button ui-state-default ui-corner-all removepadding\" data-deleteid=\"" + newEntryid + "\" title=\"delete\"><span class=\"ui-icon ui-icon-trash\">Remove</span></a>";
                                newEntryStr += "</li>";
                                $("#editstructlist").append(newEntryStr);
                                $("#structName").val("");
                                activateDeletes();
                                $( "#row" + newEntryid ).addClass("new-entry");
                                inputCharLimit();
                            }
                         }
                        });   
                        $("#ca-dialog").dialog("close");
                    }
                },
                    "Cancel" : function() {
                        $(this).dialog("close");
                    }
            }
        });
        $("#ca-dialog").dialog("open");
    }); 
    //add2 is button for damages
    $("#add2").click(function(){
        $("#ca-dialog").html("<label for=\"damageRegionTemp\">Region of the brain: </label><br /><input type=\"text\" id=\"damageRegionTemp\" name=\"damageRegionTemp\"  /><br /><label for=\"damageObservationTemp\">Observation: </label><br /><textarea id=\"damageObservationTemp\" name=\"damageObservationTemp\"  />");
        inputCharLimit();
        $("#ca-dialog").dialog({
            title: "Add a damage type",
            autoOpen: false,
            modal: true,
            height:300,
            width:400,
            buttons : {
                "Save" : function() {
                    $("#ca-dialog .redtext").remove();
                    if($("#damageRegionTemp").val().length == 0){
                        $("#ca-dialog").append("<span class=\"redtext\">Please specify damage region upon gross inspection</span>");
                        $( "#damageRegionTemp").focus();
                    } else if($("#damageObservationTemp").val().length == 0){
                        $("#ca-dialog").append("<span class=\"redtext\">Please specify observation of damage done upon gross inspection</span>");
                        $( "#damageObservationTemp").focus();
                    } else {
                        $("#damageRegion").val($("#damageRegionTemp").val());
                        $("#damageObservation").val($("#damageObservationTemp").val());
                        $.ajax({
                         url: '/cahubdataservices/brainBankFeedback/save?vessel=json',
                         type: 'POST',
                         data: $("form.bbfform").serialize(),
                         success:function(data){
                            var newEntry = data.success;
                            var newEntryStr = "";
                            $(".new-entry").removeClass("new-entry");
                            if(newEntry != null && newEntry != undefined) {
                                var newEntryid = newEntry[0].id;
                                var newEntryregion = newEntry[0].region;
                                var newEntryobservation = newEntry[0].observation;                                
                                if($("#damageRegionTable tr").length == 0 ){
                                    newEntryStr = "<thead><tr class=\"prop\" ><th class=\"label\">Region of Brain</th><th>Observations</th><th class=\"action\"></th></tr></thead><tbody></tbody>";
                                    $("#damageRegionTable").html(newEntryStr);
                                }
                                newEntryStr = "<tr class=\"prop\" id=\"row" + newEntryid + "\">";
                                newEntryStr += "<td><input size=\"30\" name=\"editDamRegion_" + newEntryid + "\"  value=\"" + newEntryregion + "\"/></td>";
                                newEntryStr += "<td><textarea name=\"editDamObservation_" + newEntryid + "\" cols=\"30\" rows=\"2\">" + newEntryobservation + "</textarea></td>";
                                newEntryStr += "<td><a class=\"delete-damage ui-button ui-state-default ui-corner-all removepadding\" data-deleteid=\"" + newEntryid + "\" title=\"delete\"  ><span class=\"ui-icon ui-icon-trash\">Remove</span></a></td>";
                                $("#damageRegionTable tbody").append(newEntryStr);
                                $("#damageRegion").val("");
                                $("#damageObservation").val("");
                                activateDeletes();
                                $( "#row" + newEntryid ).addClass("new-entry");
                                inputCharLimit();
                            }
                         }
                        });   
                        $("#ca-dialog").dialog("close");
                    }
                },
                "Cancel" : function() {
                    $(this).dialog("close");
                }
            }
        });
        $("#ca-dialog").dialog("open");                 
    }); 
      
    // add3 is button for abnormalities
    $("#add3").click(function(){
        $("#ca-dialog").html("<label for=\"hpRegionTemp\">Region: </label><br /><input type=\"text\" id=\"hpRegionTemp\" name=\"hpRegionTemp\"  /><br /><label for=\"hpObservationTemp\">Observation: </label><br /><textarea id=\"hpObservationTemp\" name=\"hpObservationTemp\"  />");
        inputCharLimit();
        $("#ca-dialog").dialog({
            title: "Add a new Histopathological evaluation observation",
            autoOpen: false,
            modal: true,
            height:300,
            width:400,
            buttons : {
               "Save" : function() {
                    $("#ca-dialog .redtext").remove();
                    if($("#hpRegionTemp").val().length == 0){
                        $("#ca-dialog").append("<span class=\"redtext\">Please specify damage region upon histopathological evaluation</span>");
                        $( "#hpRegionTemp").focus();
                    } else if($("#hpObservationTemp").val().length == 0){
                        $("#ca-dialog").append("<span class=\"redtext\">Please specify observation of damage done upon histopathological evaluation</span>");
                        $( "#hpObservationTemp").focus();
                    } else {
                        $("#hpRegion").val($("#hpRegionTemp").val());
                        $("#hpObservation").val($("#hpObservationTemp").val());
                        $.ajax({
                         url: '/cahubdataservices/brainBankFeedback/save?vessel=json',
                         type: 'POST',
                         data: $("form.bbfform").serialize(),
                         success:function(data){
                            var newEntry = data.success;
                            var newEntryStr = "";
                            $(".new-entry").removeClass("new-entry");
                            if(newEntry != null && newEntry != undefined) {
                                var newEntryid = newEntry[0].id;
                                var newEntryregion = newEntry[0].region;
                                var newEntryobservation = newEntry[0].observation;                      
                                if($("#hpRegionTable tr").length == 0 ){
                                    newEntryStr = "<thead><tr class=\"prop\" ><th class=\"label\">Region of Brain</th><th>Observations</th><th class=\"action\"></th></tr></thead><tbody></tbody>";
                                    $("#hpRegionTable").html(newEntryStr);
                                }
                                newEntryStr = "<tr class=\"prop\" id=\"row" + newEntryid + "\">";
                                newEntryStr += "<td><input size=\"30\" name=\"editHpRegion_" + newEntryid + "\"  value=\"" + newEntryregion + "\"/></td>";
                                newEntryStr += "<td><textarea name=\"editHpObservation_" + newEntryid + "\" cols=\"30\" rows=\"2\">" + newEntryobservation + "</textarea></td>";
                                newEntryStr += "<td><a class=\"delete-damage ui-button ui-state-default ui-corner-all removepadding\" data-deleteid=\"" + newEntryid + "\" title=\"delete\"  ><span class=\"ui-icon ui-icon-trash\">Remove</span></a></td>";
                                $("#hpRegionTable tbody").append(newEntryStr);
                                $("#hpRegion").val("");
                                $("#hpObservation").val("");
                                activateDeletes();
                                $( "#row" + newEntryid ).addClass("new-entry");
                                inputCharLimit();
                            }
                         }
                        });   
                        $("#ca-dialog").dialog("close");
                    }
                },
                "Cancel" : function() {
                    $(this).dialog("close");
                }
            }
        });
        $("#ca-dialog").dialog("open");                  
    }); 
        
    
    // id s1 c1 is adding/cancelling new structures
    $("#s1").click(function(){
        var addnewStruct = $('input:radio[name=missingStructExists]:checked').val();
        var sname = document.getElementById("structName").value
        
        if(addnewStruct == "Yes"){
            if((sname==null || sname.length==0) ){
                alert("Please specify name  of the missing Brain structure to be added")
                $("#addstructure").hide()
                return false;
            }
            
        }
  
    });
      
    $("#c1").click(function(){
                
        var addnewStruct = $('input:radio[name=missingStructExists]:checked').val();
        document.getElementById("structName").value='';
       
        // document.getElementById("d1").style.display = 'none';
        if(addnewStruct == "Yes"){
            $("#msnewadd").hide()
            $("#addstructure").show()
            $("#add1td").show()
        }
        else{
            $("#msnewadd").show()
            $("#addstructure").hide() // addstructure id is for selecting preselects
            $("#add1td").hide()
        }
                    
        return false;
                  
                  
               
    });    
      
    // id s2 c2 is adding/cancelling new damages
    $("#s2").click(function(){
       
        var dregion = document.getElementById("damageRegion").value
        var ddesc = document.getElementById("damageObservation").value
       
        
            if((dregion==null || dregion.length==0) ){
                alert("Please specify damage region upon gross inspection")
                return false;
            }
            if((ddesc== null || ddesc.length == 0)){
                alert("Please specify observation of damage done upon gross inspection")
                return false;
            }
      
           
        $("#adddamage").hide()
    });
      
    $("#c2").click(function(){
                
                    
        document.getElementById("damageRegion").value='';
        document.getElementById("damageObservation").value=''
        
        // document.getElementById("d1").style.display = 'none';
        $("#adddamage").hide()
        $("#add2").show()                   
        return false;
               
    });   
      
    // id s3 c3 is adding/cancelling reasons why structures not acceptable upon gross inspect
    $("#s3").click(function(){
        var acceptFurtherProc = $('input:radio[name=acceptForFurtherProc]:checked').val();
        var leftNoReason = document.getElementById("noLeftHemisProcReason").value          
        var rightNoReason = document.getElementById("noRightHemisProcReason").value        
        var wbNoReason = document.getElementById("wholeBrainNoProcReason").value
              
         
         
        if(acceptFurtherProc =="No"){
            if(((leftNoReason== null || leftNoReason.length == 0) &&(rightNoReason== null || rightNoReason.length == 0)&&(wbNoReason== null || wbNoReason.length == 0))){
                alert("Please specify reason why partial/whole Brain  not acceptable for processing")
                $("#grossProcNo").show()
                return false;
            }
        }
        $("#grossProcNo").hide()
         
  
    });
      
    $("#c3").click(function(){                                  
         
        var grossProc = $('input:radio[name=acceptForFurtherProc]:checked').val();                   
        if(grossProc == "Yes"){
            $("#grossProcNo").hide();                  
        }
        else{
            $("#grossProcNo").show(); 
        }
                    
        return false;      
              
    });    
      
    // id s4 c4 is adding/cancelling reasons if brain not processed immed. and storage info.
    $("#s4").click(function(){
      
        var storeImmed =$('input:checkbox[name=storedImmed]:checked').val();
        var storeType = $('input:radio[name=storedImmedType]:checked').val();         
        var storedComments = document.getElementById("storedInComments").value        
                      
        if(storeImmed!=null  && storeType== null ){
            alert("Please specify how Brain and Brain contents were stored")
            return false;
        }
        if(((storeType == 'formalin' || storeType == 'other') &&(storedComments== null || storedComments.length == 0))){
            alert("Please explain how Brain and Brain contents were stored")
            return false;
        }
         
  
    });
      
    $("#c4").click(function(){
        var wasBrainProcImmed = $('input:radio[name=wasBrainProcImmed]:checked').val();              
        // $("#procImmedNo").hide();
        if(wasBrainProcImmed =="No"){
            $("#procImmedNo").show();
        }
        else if(wasBrainProcImmed =="Yes"){
            $("#procImmedYes").show();
        }
        else{                                  
            $("#procImmedYes").hide();
            $("#procImmedNo").hide();                  
        }
                  
        return false;
               
    });    
       
    // id s5 c5 is adding/cancelling reasons if brain processed immed. and if so by who
      
    $("#s5").click(function(){
          
        var procBy=document.getElementById("processedBy").value
        var procDt =document.getElementById("processDate").value
        if((procBy == null || procBy.length ==0 ) || (procDt == null || procDate.length ==0) ){
            alert("Please enter Brain  Processed by and process date information")
             
            $("#procImmedYes").show();
            return false;
        }
          
        $("#grossProcNo").hide();
        $("#showgrossNoReasons").show();
         
    });     
               
    $("#c5").click(function(){
              
        var wasBrainProcImmed = $('input:radio[name=wasBrainProcImmed]:checked').val(); 
        if(wasBrainProcImmed =="Yes"){
            $("#procImmedYes").show();
        }
        else if(wasBrainProcImmed =="No"){
            $("#procImmedNo").show();
        }
        else{
                                  
            $("#procImmedYes").hide();
            $("#procImmedNo").hide();
                  
        }
                                   
        //  $("#grossProcNo").hide();
        //  $("#showgrossNoReasons").show();
                 
        return false;                  
                 
               
    });  
      
    // id s6 c6 is adding/cancelling abnormalities
               
    $("#s6").click(function(){
        var hpregion = document.getElementById("hpRegion").value
        var hpdesc = document.getElementById("hpObservation").value
        
              
             
        if((hpregion==null || hpregion.length==0) ){
            alert("Please specify damage region upon histopathological evaluation")
            return false;
        }
        if((hpdesc== null || hpdesc.length == 0)){
            alert("Please specify observation of damage done upon histopathological evaluation")
            return false;
        }
        
        
          
        $("#addHistoPatho").hide()
    });     
    $("#c6").click(function(){
                                  
        document.getElementById("hpRegion").value='';
        document.getElementById("hpObservation").value=''
        
        // document.getElementById("d1").style.display = 'none';
        $("#addHistoPatho").hide()
        $("#add3").show()
                    
        return false;
                  
                                                    
               
    });  
    
    $("input[type=radio]").change(function()
    {
        var divId = $(this).attr("id");
                         
        if(divId =='siNo'){
            $(store2).prop('checked', false);
            $(store3).prop('checked', false);
            $(store4).prop('checked', false);
        }
    
                 
    });
    
    $(".delete-file").click(function(){
        $("#ca-dialog").html("Are you sure you want to remove the file?");
        $("#ca-dialog").dialog({
           title: "Delete File Warning",
           autoOpen: false,
           modal: true,
           buttons : {
                "Yes" : function() {
                    $.ajax({
                        type: 'POST',
                        dataType: 'jsonp',
                        url: '/cahubdataservices/brainBankFeedback/removeFile/' + $(".delete-file").data("deleteid")
                     }).done(function(data) {
                            if(data.success == "yes"){
                                $(".delete-file").parent("div").html("<input type =\"file\" name=\"fileName\" id=\"fileName\" size=\"25\"  value=\"\"/>");
                                $("#ca-dialog").dialog("close");
                            }
                     });
                },
                "Cancel" : function() {
                  $(this).dialog("close");
                }
           }
         });
        $("#ca-dialog").dialog("open");
    });
 
})

function activateDeletes() {
     $(".delete-struct").click(function(){
         var thisid = $(this).data("deleteid");
        $("#ca-dialog").html("Are you sure you want to remove the structure?");
        $("#ca-dialog").dialog({
            title: "Delete Missing Structure Warning",
            autoOpen: false,
            modal: true,
            height:120,
            width:300,
            buttons : {
                    "Yes" : function() {
                        $.ajax({
                            type: 'POST',
                            dataType: 'jsonp',
                            url: '/cahubdataservices/brainBankFeedback/removeStructure/' + thisid
                        }).done(function(data) {
                                if(data.structDel == "yes"){

                                    $("#row"+data.delid).remove();
                                    $("#ca-dialog").dialog("close");
                                }
                        });
                    },
                    "Cancel" : function() {
                    $(this).dialog("close");
                    }
            }
        });
        $("#ca-dialog").dialog("open");
    });
     
     
     $(".delete-damage").click(function(){
        var thisid = $(this).data("deleteid");
        $("#ca-dialog").html("Are you sure you want to remove the brain damage added?");
        $("#ca-dialog").dialog({
            title: "Delete Damage Warning",
            autoOpen: false,
            modal: true,
            height:120,
            width:250,
            buttons : {
                    "Yes" : function() {
                        $.ajax({
                            type: 'POST',
                            dataType: 'jsonp',
                            url: '/cahubdataservices/brainBankFeedback/removeDamage/' + thisid
                        }).done(function(data) {
                                if(data.damDel == "yes"){

                                    $("#row"+data.delid).remove();
                                    $("#ca-dialog").dialog("close");
                                }
                        });
                    },
                    "Cancel" : function() {
                    $(this).dialog("close");
                    }
            }
        });
        $("#ca-dialog").dialog("open");
    });
}