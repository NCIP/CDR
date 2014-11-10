 /* Please do not check in commented out code like alert(flags) , partial code etc... */
 
 $(document).ready(function() {
    tissueRecoveryLoadJs();
 });
function refreshJsFunctionsFromMain(){
    changeToInfoBox();
    rundependencies();
    inputCharLimit();
    $(".numFloat").AllowNumericOnlyWithDecimal();
    $(".numNegFloat").AllowNegNumericWithDecimal();    
    $(".numInt").AllowNumericOnly();
    $(".numNegInt").AllowNegativeNumeric();    
    $(".numFloat input[type=text]").AllowNumericOnlyWithDecimal();
    $(".numNegFloat input[type=text]").AllowNegNumericWithDecimal();
    $(".numInt input[type=text]").AllowNumericOnly();
    $(".numNegInt input[type=text]").AllowNegativeNumeric(); 
    $('.timeEntry').timeEntry({
        spinnerImage: '', 
        useMouseWheel: false, 
        show24Hours: true, 
        defaultTime: '00:00'
    });
    $(".uibutton").each(function(){
        $(this).addClass("button ui-button ui-widget ui-state-default ui-corner-all");
        $(this).children(".ui-icon").html("&nbsp;");
        $(this).css('visibility', 'visible');
    });
    $("select option").each(function() {
        if( ($(this).text() === "Other" && $(this).parent("select").children("option:last-child").text() !== "Other") || ($(this).text() === "Other, specify" && $(this).parent("select").children("option:last-child").text() !== "Other, specify")) { 
            $(this).insertAfter($(this).parent("select").children("option:last-child"));
        }
    });
    createTooltips();
}

function aquaRefreshJs(){
    //aquaKit
    refreshJsFunctionsFromMain();
    $("#aquaKit .td_tissueLocation select").focus(function () {
        previousSelVal = this.value;
    }).change(function() {
        var currentId = $(this).data("currentid"); 
        if(parseInt($(this).val()) > 0) {
            updateTRFEntry( $(this), currentId, $(this).val(), 'tissueLocation.id'); 
        } else {
            updateTRFEntry( $(this), currentId, null, 'tissueLocation.id'); 
        }
    });

    $("a.aquarefresh").click(function(){
        $(this).removeAttr( "onclick");
        $(this).removeAttr( "href");
        $(this).removeClass("ui-state-default");
        $(this).removeClass("uibutton");
        $(this).html("<span class=\"ui-icon ui-icon-refresh left\">&nbsp;</span><i>Refreshing Aqua Kit...</i>");
        $(".aqua-row").animate({ opacity: '.5' }, "slow");
    });
    $("#aquaKit .otherTissueLocation").change(function(){
        updateTRFEntry( $(this), $(this).data("currentid"), $(this).val(), 'otherTissueLocation');
    });
    $(".aqua-row").each(function () {
       fixativeValidate(($(this).attr("id")).split("-")[1], true);
     }); 
    $(".aqua-row .timeEntry").blur(function(){
        fixativeValidate(($(this).closest("tr").attr("id")).split("-")[1], false);
    });
        
    $('#aquaKit .gtexQuickSave').blur(function() {
        updateTRFEntry( $(this), $(this).data("currentid"), $(this).val(), $(this).data("param"));
    });
    
    //needed for otherTissueLocation clean up when user selects entry - need to do another json to clean up database
    $('#aquaKit .td_tissueLocation select').blur(function() {
        if($(this).val() != 28 && $("#otherTissueLocation_" + $(this).data("currentid") ).val().length > 0) {
            updateTRFEntry( $(this), $(this).data("currentid"), "", 'otherTissueLocation');
            $("#otherTissueLocation_" + $(this).data("currentid") ).val("");
        }
    });      
}

function tissueRecoveryLoadJs(){
    refreshJsFunctionsFromMain();
    $(".td_tissueLocation select").focus(function () {
        previousSelVal = this.value;
    }).change(function() {
        var currentId = $(this).data("currentid"); 
        if(parseInt($(this).val()) > 0) {
            updateTRFEntry( $(this), currentId, $(this).val(), 'tissueLocation.id'); 
        } else {
            updateTRFEntry( $(this), currentId, null, 'tissueLocation.id'); 
        }
    });

    $("a.aquarefresh").click(function(){
        $(this).removeAttr( "onclick");
        $(this).removeAttr( "href");
        $(this).removeClass("ui-state-default");
        $(this).removeClass("uibutton");
        $(this).html("<span class=\"ui-icon ui-icon-refresh left\">&nbsp;</span><i>Refreshing Aqua Kit...</i>");
        $(this).closest("tbody").children(".aqua-row").animate({ opacity: '.5' }, "slow");
    });
    $(".otherTissueLocation").change(function(){
        updateTRFEntry( $(this), $(this).data("currentid"), $(this).val(), 'otherTissueLocation');
    });
    $(".aqua-row").each(function () {
       fixativeValidate(($(this).attr("id")).split("-")[1], true);
     }); 
    $(".aqua-row .timeEntry").blur(function(){
        fixativeValidate(($(this).closest("tr").attr("id")).split("-")[1], false);
    });
        
    $('.gtexQuickSave').blur(function() {
        updateTRFEntry( $(this), $(this).data("currentid"), $(this).val(), $(this).data("param"));
    });
    
    $('#coreBodyTempStr').blur(function() {
        setTemp($('#coreBodyTempStr').val());
    });
    
    //needed for otherTissueLocation clean up when user selects entry - need to do another json to clean up database
    $('.td_tissueLocation select').blur(function() {
        if($(this).val() != 28 && $("#otherTissueLocation_" + $(this).data("currentid") ).val().length > 0) {
            updateTRFEntry( $(this), $(this).data("currentid"), "", 'otherTissueLocation');
            $("#otherTissueLocation_" + $(this).data("currentid") ).val("");
        }
    });        

    $(".edit #firstTissueRemoved option:first-child").remove(); 

    $("#coreBodyTempLocHelper").change(function () {
       if ($("#coreBodyTempLocHelper").attr("value") !== "Other" && $("#coreBodyTempLocHelper").attr("value") !== "Select") {
            $('#coreBodyTempLoc').val($("#coreBodyTempLocHelper").attr("value"));
        } else {
            $('#coreBodyTempLoc').val("");
        }
     });  
     
    $(".errors #coreBodyTempLocHelper").each(function () {
       if($.urlParam('tl') == "o") {
            $("#coreBodyTempLocHelper").attr("value","Other");
            $("#coreBodyTempLocParent").show();
       }
     }); 
    
    if( $("#coreBodyTempLoc").length > 0 && $("#coreBodyTempLoc").val().length > 0) {
        var existsInDropDown = false;
        $('#coreBodyTempLocHelper option').each(function(){
           if (this.value == $("#coreBodyTempLoc").val()) {
               existsInDropDown = true;
               return false;
           }
        });
        if(existsInDropDown == false) {
           $("#coreBodyTempLocHelper").val("Other");
           createDependentElements($("#coreBodyTempLoc").parent("div"));
        }
   }
   if ( $("body").hasClass("view") && $("#coreBodyTempLocHelperParent .show-select").html().length === 0) {
       $("#coreBodyTempLocParent").show();
   }
}
          
function setTemp(temp) {
    if(!temp){
       $('#coreBodyTemp').val(-1000.0);                
    } else {
       $('#coreBodyTemp').val(temp);
    }
}

function updateTRFEntry( elem, currentId, currentVal, dataEntry) {
    elem.closest("td").removeClass("errors");
    (elem.closest("td")).children(".errmsg").remove();
    $.ajax({
        type: 'POST',
        dataType: 'jsonp',
        data: dataEntry + '=' + currentVal + '&id=' + currentId + '&viajson=yes', 
        url: '/cahubdataservices/specimenRecord/update'
     }).always(function(data) {
         if(data.results != "success"){
             showUpdateError(elem);
         }
     });
}

function showUpdateError(elem){
    elem.closest("td").addClass("errors"); 
    if((elem.closest("td")).children(".errmsg").length == 0){
        var errMsg = '<a data-msg="Oops! This value was not saved. Please try refreshing this page before continuing to try and resolve this problem." class="errmsg removepadding borderless ca-tooltip-nobg ui-button ui-widget ui-state-error ui-corner-all" style="visibility: visible;"><span class="ui-icon ui-icon-alert">&nbsp;</span></a>';
        elem.closest("td").append(errMsg);
        createTooltips();
    }
}
function fixativeValidate(rowID, ordered){
    var currentDate = "";
    var nextDate = "";
    var previousDate = "";
    var fixativeDate = "";
    var stabilizerDate = "";
    if($("body.view").length > 0){ 
        currentDate = $("#aqua-" + rowID + " .td_aliquotTimeRemoved .timeEntry").html();
        nextDate = $("#aqua-" + eval(rowID + 1) + " .td_aliquotTimeRemoved .timeEntry").html();
        previousDate = $("#aqua-" + eval(rowID - 1) + " .td_aliquotTimeRemoved .timeEntry").html();
        fixativeDate = $("#aqua-" + rowID + " .td_aliquotTimeFixed .timeEntry").html();
        stabilizerDate = $("#aqua-" + rowID + " .td_aliquotTimeStabilized .timeEntry").html();
    } else {
        currentDate = $("#aqua-" + rowID + " .td_aliquotTimeRemoved .timeEntry").val();
        nextDate = $("#aqua-" + eval(rowID + 1) + " .td_aliquotTimeRemoved .timeEntry").val();
        previousDate = $("#aqua-" + eval(rowID - 1) + " .td_aliquotTimeRemoved .timeEntry").val();
        fixativeDate = $("#aqua-" + rowID + " .td_aliquotTimeFixed .timeEntry").val();
        stabilizerDate = $("#aqua-" + rowID + " .td_aliquotTimeStabilized .timeEntry").val();
    }
    if(nextDate != undefined && nextDate.length > 0 && ordered){
        if(minuteDiff(currentDate, nextDate) > 20){
            $("#aqua-" + eval(rowID + 1) + " .td_aliquotTimeRemoved").addClass("warning");
        } else {
            $("#aqua-" + eval(rowID + 1) + " .td_aliquotTimeRemoved").removeClass("warning");
        }
    }
    if(previousDate != undefined && previousDate.length > 0 && ordered){
        if(minuteDiff(previousDate, currentDate) > 20){
            $("#aqua-" + rowID + " .td_aliquotTimeRemoved").addClass("warning");
        } else {
            $("#aqua-" + rowID + " .td_aliquotTimeRemoved").removeClass("warning");
        }
    }
    if(fixativeDate != undefined && fixativeDate.length > 0){
        if(minuteDiff(currentDate, fixativeDate) > 20){
            $("#aqua-" + rowID + " .td_aliquotTimeFixed").addClass("warning");
        } else {
            $("#aqua-" + rowID + " .td_aliquotTimeFixed").removeClass("warning");
        }
    }
    if(stabilizerDate != undefined && stabilizerDate.length > 0){
        if(minuteDiff(fixativeDate, stabilizerDate) < 360){
            $("#aqua-" + rowID + " .td_aliquotTimeStabilized").addClass("warning");
        } else {
            $("#aqua-" + rowID + " .td_aliquotTimeStabilized").removeClass("warning");
        }
    }
}

function minuteDiff(time1,time2){
    var time1Split = time1.split(":");
    var time2Split = time2.split(":");
    var h1 = time1Split[0];
    var m1 = time1Split[1];
    var h2 = time2Split[0];
    var m2 = time2Split[1];
    var date1 = new Date(2000, 0, 1,  eval(h1), eval(m1));
    var date2 = new Date(2000, 0, 1, eval(h2), eval(m2));
    if (date2 < date1) {
        date2.setDate(date2.getDate() + 1);
    }
    var diff = date2 - date1;
    return eval(diff / 1000 / 60)
}