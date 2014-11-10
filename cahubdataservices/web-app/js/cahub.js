/* Please do not check in commented out code like alert(flags) , partial code etc... */
 
var activityFeedJob;
var recentActivityTimeInt = 1800000;
var deidentified = false;
var vocabObj = {};
var codVocabObj = {};
var rxVocabObj = {};
var labelSelectedDefault = "Choose...";
var setCountDown = true;
var resetLogOutTime;
var logOutMsg = "<p>Your application will time out in <b class=\"redtext\"><span class=\"minutesleft\"></span> minutes and <span class=\"secondsleft\"></span> seconds</b>. For your safety and protection your online session is about to be timed out and redirected to the home page.</p><p>If you are still working in your online session simply click <b>Continue</b> to refresh your session.</p>";
var validSpecimen;


//ONLY put function calls here... set global behaviour rules in applyGlobalRules() function underneath
$(document).ready(function() {
    activateCahubDatePickers();
    disableShowOrViewElements();
    startVocab();
    createTooltips();
    activateExpandIssue();
    addPrintLink();
    disableBackSpaceNav();
    applyGlobalRules();  //miscellaneous stuff after body loads
    warnSpecimenIdMissMatch();
    activateTimedLogout();
    if($("#activityEvent").length != 0){
        fetchActivities();
        activityFeedJob = setInterval("fetchActivities()", recentActivityTimeInt);
    }
});

//miscellaneous stuff after body loads
function applyGlobalRules(){
    changeToInfoBox();
    addDependentTextareas();
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

    // identify location of error on page and tie to user onclick
    $(".errors ul li a").click(function() {
        scrollTo($(this).attr("href"),"error");
    });
    
    if ($("table th a:contains('FT')").length>0) {
        $(".fasttrack").show();
    }
    
    if ($("table th:contains('QT')").length>0) {
        $(".querytracker").show();
    }

    $(".loginpage :input:visible:enabled:first").focus();  //focus set to first form input in login form
    $(".search #query").focus(); 
    
    $(".loginpage #cancel").click(function() {
        window.location = "../";
    });
    
    $("#prevCancerAddBtn").click(function() {
        $("prevCancerDiagDesc").focus();
    });

    $(".cprint").click(function() {
        window.print();
    });

    $("input[type=submit]").each(function(){
        $(this).addClass("button ui-button ui-widget ui-state-default ui-corner-all");
    });

    $("input[type=button]").each(function(){
        $(this).addClass("button ui-button ui-widget ui-state-default ui-corner-all");
    });

    $(".uibutton").each(function(){
        $(this).addClass("button ui-button ui-widget ui-state-default ui-corner-all");
        $(this).children(".ui-icon").html("&nbsp;");
        $(this).css('visibility', 'visible');
    });

    $(':input').change(function() {
        $('#changed').val('Y');
    });

    $("select option").each(function() {
        if( ($(this).text() === "Other" && $(this).parent("select").children("option:last-child").text() !== "Other") || ($(this).text() === "Other, specify" && $(this).parent("select").children("option:last-child").text() !== "Other, specify")) { 
            $(this).insertAfter($(this).parent("select").children("option:last-child"));
        }
    }); 

    $(".jump-to").click(function() {
        if( $(this).data("goto") && $(this).data("goto").length > 0) { 
            scrollTo( "#"+$(this).data("goto") );
        }
    }); 
    
    $("textarea[maxlength]").bind('input propertychange', function() {  
        var maxLength = $(this).attr('maxlength');  
        if ($(this).val().length > maxLength) {  
            $(this).val($(this).val().substring(0, maxLength));  
        }  
    });
    
    $('.vocDefDC').click(function() {
        createVocabDialog($(this).data("vocid"), $(this).data("title"));
        
    });
    
    //dropdown menus
    $('.dropit').dropit();
    if($(".checkboxtree").length > 0){
        $(".checkboxtree").checkboxTree();
    }
    
    //dynamic errors
    $(".borderlesserrors input").each( function(){ 
        if($(this).val().length == 0){
            $(this).addClass("errors");
        }
    });
    
    $(':input').change(function(e) {
        if (e.keyCode == 13){
            $(this).form.submit();
        }
    });
    
}
function warnSpecimenIdMissMatch(){
    validSpecimen = "^" + $("#caseRecordID").html() + "-[0-9][0-9]\$";
    $(".specimenid").each(function(){
        validateSpecimenRecordWithWarning( validSpecimen, $(this));
    });
    if($(".specimenid.warnings").length > 0 || $(".warnings a.specimenid").length > 0){
        $( "h1" ).after( "<div class=\"warning\"><span class=\"cdr-icon\"></span>IMPORTANT: Please review Specimen IDs to ensure they are of the proper format and belonging to the correct Case ID "+ $("#caseRecordID").html()+".</div>" );
    }
}
function addDependentTextareas(){
    $(".addotherfield input[type=checkbox]").each(function(){
        if($(this).val().toLowerCase() === "other") {
            $(this).addClass("othertrigger");
            $(this).next("label").after("<input id=\"" + $(this).attr("id") + "_other\" type=\"text\" class=\"othertxt depends-on dependent-focus\" data-id=\"" + $(this).attr("id") + "\" value=\"" + ($(this).data("otherval") != undefined? $(this).data("otherval"):"") + "\" />");
        }
    });

    $(".addposttextfield input[type=radio]").each(function(){
        if(!$(this).parent(".addposttextfield").hasClass("textfieldbut" + $(this).data("radiopos"))) {
            $(this).addClass("othertrigger");
            $(this).next("label").after("<input data-questionid=\"" + $(this).data("questionid") + "\" type=\"text\" class=\"fieldtxt depends-on dependent-focus\" data-id=\"" + $(this).attr("id") + "\" value=\"" + ($(this).data("otherval") != undefined && $(this).prop("checked") == true? $(this).data("otherval"):"") + "\" />");
        }
    });

    $(".addpretextfield input[type=radio]").each(function(){
        if(!$(this).parent(".addpretextfield").hasClass("textfieldbut" + $(this).data("radiopos"))) {
            $(this).addClass("othertrigger");
            $(this).next("label").before("<input data-questionid=\"" + $(this).data("questionid") + "\" type=\"text\" class=\"fieldtxt depends-on dependent-focus\" data-id=\"" + $(this).attr("id") + "\" value=\"" + ($(this).data("otherval") != undefined && $(this).prop("checked") == true? $(this).data("otherval"):"") + "\" />");
        }
    });
}

function rundependencies() {
    $(".depends-on").each( function(){ 
        createDependentElements($(this));
    });
}

function activateTimedLogout() {
    if(!$("body.loginpage").length > 0 && $("#countdown").length > 0) {
        //logout dialog
        $( "#login-dialog" ).dialog({
            autoOpen: false,
            resizable: false,
            height:210,
            width:400,
            modal: true,
            buttons: {
                "Continue...": function() {
                    localStorage.setItem("timetologout", resetLogOutTime);
                    $("#login-dialog").dialog( "close" );
                }
            }
        });
        resetLogOutTime = timeOutMilliSecs;
        localStorage.setItem("timetologout", timeOutMilliSecs);
        setInterval("updateTimeToLogout()", 1000);
        
        $("#login-dialog").dialog("option", "title","Time Out Warning");
        $("#login-dialog").html(logOutMsg);
    }
}

function updateTimeToLogout() {
    if (localStorage.getItem("timetologout") == timeOutMilliSecs || localStorage.getItem("timetologout") > timeOutMilliSecs){
        localStorage.setItem("timetologout", localStorage.getItem("timetologout")-1000);
    }
    timeOutMilliSecs = localStorage.getItem("timetologout");
    secondsToTime(localStorage.getItem("timetologout"));
    $("#countdown").css('visibility', 'visible');
    if (timeOutMilliSecs < 1000) {
        $("#countdown").hide();
        redirectToLogin();
    } else if(timeOutMilliSecs < 300000){
        $("#countdown").addClass("redtext"); 
        if(timeOutMilliSecs < 121000){
            $("#login-dialog").dialog("open");
        } else {
            $("#login-dialog").dialog( "close" );
        }
    } else {
        $("#countdown").removeClass("redtext");
        $("#login-dialog").dialog( "close" );
    }
}

function secondsToTime(s){
  var ms = s % 1000;
  s = (s - ms) / 1000;
  var secs = s % 60;
  s = (s - secs) / 60;
  var mins = s % 60;
  var hrs = (s - mins) / 60;
  var secslabel = secs > 9 ? secs: "0" + secs;
  var minslabel = mins > 9 ? mins: "0" + mins;
  
  $(".minutesleft").html(minslabel);
  $(".secondsleft").html(secslabel);
}

function redirectToLogin(){
    window.location = '/cahubdataservices/logout?url='+escape(document.URL);
}
 
function activateCahubDatePickers( datefield ){
    $("input.cahubDate").datepicker({
        onSelect: function(dateText, inst) {
            $("#"+ this.id + "_month").attr("value",new Date(dateText).getMonth() + 1);
            $("#"+ this.id + "_day").attr("value",new Date(dateText).getDate());
            $("#"+ this.id + "_year").attr("value",new Date(dateText).getFullYear());
            $("#"+ this.id + "_changeTime").val(Date.now()).trigger('change');
        },
        changeMonth: true,
        changeYear: true, 
        yearRange: '1900:2050', 
        showButtonPanel: true, 
        showOn: 'both', 
        buttonImage: '/cahubdataservices/images/datepicker.gif', 
        buttonText: 'Date', 
        buttonImageOnly: true, 
        closeText: 'Close', 
        showAnim: ''
    }); 
    $("input.cahubDateTime").datetimepicker({
        onSelect: function(dateText, inst) {
            $("#"+ this.id + "_month").attr("value",new Date(dateText).getMonth() + 1);
            $("#"+ this.id + "_day").attr("value",new Date(dateText).getDate());
            $("#"+ this.id + "_year").attr("value",new Date(dateText).getFullYear());
            $("#"+ this.id + "_hour").attr("value",new Date(dateText).getHours());
            $("#"+ this.id + "_minute").attr("value",new Date(dateText).getMinutes());
            $("#"+ this.id + "_changeTime").val(Date.now()).trigger('change');
        },
        changeMonth: true,
        changeYear: true, 
        yearRange: '1900:2050', 
        showButtonPanel: true, 
        showOn: 'both', 
        buttonImage: '/cahubdataservices/images/datepicker.gif', 
        buttonText: 'Date', 
        buttonImageOnly: true, 
        closeText: 'Close', 
        showAnim: ''
    }); 
    
    //adds clear button after every date picker and hooks functionality to corresponding field
    $('img.ui-datepicker-trigger').after(function() { 
        if( datefield == null || $(this).prevAll("input.hasDatepicker:first").attr("id") == datefield ){
            return "<input class=\"button cleardate\"  readonly=\"readonly\" value=\"Clear\" id=\"" + $(this).prevAll("input.hasDatepicker:first").attr("id") + "_cleardate\" />";
        }
    });

    var _gotoToday = $.datepicker._gotoToday;
    $.datepicker._gotoToday = function(id) {
        if( datefield == null || id == datefield ){
            var dateToday = new Date();
            var currentMonth =  dateToday.getMonth() + 1;
            var currentDay = dateToday.getDate();
            var currentYear = dateToday.getFullYear();
            var currentHour = dateToday.getHours();
            var currentMinute = dateToday.getMinutes();
            currentDay = (currentDay < 10) ? '0' + currentDay : currentDay;
            currentMonth = (currentMonth < 10) ? '0' + currentMonth : currentMonth;
            currentHour = (currentHour < 10) ? '0' + currentHour : currentHour;
            currentMinute = (currentMinute < 10) ? '0' + currentMinute : currentMinute;

            $(id).datetimepicker('setDate', new Date() );
            $(id + "_month").attr("value", currentMonth);
            $(id + "_day").attr("value", currentDay);
            $(id + "_year").attr("value", currentYear);
            $(id + "_hour").attr("value", currentHour);
            $(id + "_minute").attr("value", currentMinute);
            $(id + "_changeTime").val(Date.now()).trigger('change');
            $(id).datepicker('hide');
            $(id).blur();
        }
    };

    $("input.cleardate").each(function (index, domEle) {
        var calndrFieldId = this.id.split("_")[0];
        if( datefield == null || calndrFieldId == datefield ){
            var calndrParent = $('#' + calndrFieldId).parent();
            if($(this).css('display') != 'none'){ 
                $(this).button();
                $(this).click(function () { 
                    $('#' + calndrFieldId).datepicker("setDate", null );
                    $(".ui-timepicker-select").val("0").change();
                    $('#' + calndrFieldId).val("Select Date");
                    $('#' + calndrFieldId + "_day").val("");
                    $('#' + calndrFieldId + "_month").val("");
                    $('#' + calndrFieldId + "_year").val("");
                    $('#' + calndrFieldId + "_hour").val("");
                    $('#' + calndrFieldId + "_minute").val("");
                    $("#" + calndrFieldId + "_changeTime").val(Date.now()).trigger('change');
                    $(this).blur();
                });
            }
        }
    });
}
 
function disableShowOrViewElements(){
    if($("body").hasClass("show") || $("body").hasClass("view")){
        $(".addtlRows").hide();
        $(".addtb").hide();
        $(".photoLink").hide();
        $(".Btn").hide();
        $(".editOnly").hide();
        $("#show :input").attr("disabled", true);
        $("#view :input").attr("disabled", true);
        $("img[alt=AddBtn]").css("display","none");
        $(".ui-datepicker-trigger").hide();
        $("input.cleardate").hide();
        $(".cahubDate").hover( function(){
            $(this).css("cursor","none");
        });
        $(".cahubDateTime").hover( function(){
            $(this).css("cursor","none");
        });
        $(".bpvslideprep #a").hide();
        $("#show .uibutton").hide();
        $("#show .ui-button").hide();
        $("#show textarea").each( function(){ 
            if($(this).css("display") != "none") {
                $(this).after("<div id=\"" + $(this).attr("id") + "\" class=\"show-textarea\">" + $('<div />').text($(this).val().replace(/\n/g, "*br*").replace(/\r/g, "*br*")).html().replace(/\*br\*/g, "<br>") + "</div>");
                $(this).remove();
            }
        });
        $("#view textarea").each( function(){
            if($(this).css("display") != "none") {
                $(this).after("<div id=\"" + $(this).attr("id") + "\" class=\"show-textarea\">" + $('<div />').text($(this).val().replace(/\n/g, "*br*").replace(/\r/g, "*br*")).html().replace(/\*br\*/g, "<br>") + "</div>");
                $(this).remove();
            }
        });

        $("#show input").each( function(){
            if($(this).css("display") != "none" && $(this).attr("type")=="text") {
                if($(this).val() != "Select Date") {
                    $(this).after("<span id=\"" + $(this).attr("id") + "\" class=\"show-input\">" + $('<div />').text($(this).val()).html() + "</span>");
                }
                $(this).remove();
            }
        });
        
        $("#view input").each( function(){
            if($(this).css("display") != "none" && $(this).attr("type")=="text") {
                $(this).after("<span id=\"" + $(this).attr("id") + "\" class=\"show-input\">" + $('<div />').text($(this).val()).html() + "</span>");
                $(this).remove();
            }
        });
        
        $("#show select").each( function(){
            var displayStr = "";
            displayStr = $(this).find("option:selected").text();
            $(this).after("<span id=\"" + $(this).attr("id") + "_selected\" class=\"show-select\">" + displayStr + "</span>");
            $(this).hide();
        });
        
        $("#view select").each( function(){
            var displayStr = "";
            displayStr = $(this).find("option:selected").text();
            $(this).after("<span id=\"" + $(this).attr("id") + "_selected\" class=\"show-select\">" + displayStr + "</span>");
            $(this).hide();
        });
        $("body").css("display","block");
    }
}

function isNumericValidation(ele) {
    if(isNaN(ele.value)) {
        ele.value = "";
    }
}

function checkModification() {
    var changed = $('#changed').val();
    if(changed == 'Y') {
        alert("Please save the change!");
        return false;
    }
    return true;
}

function parseISO8601(dateStringInRange) {
    var isoExp = /^\s*(\d{4})-(\d\d)-(\d\d)T(\d\d):(\d\d):(\d\d).*Z\s*$/,
        date = new Date(NaN), month,
        parts = isoExp.exec(dateStringInRange);
    if (parts) {
        month = +parts[2];
        date.setUTCFullYear(parts[1], month - 1, parts[3]);
        date.setUTCHours(parts[4]);
        date.setUTCMinutes(parts[5]);
        date.setUTCSeconds(parts[6]);
        if (month != date.getMonth() + 1) {
            date.setTime(NaN);
        }
    }
    return date;
}

function getTimeStamp(jsdateobject){
    var dateObj;
    if (navigator.appName == "Microsoft Internet Explorer") {
        dateObj = parseISO8601(jsdateobject);
    } else {
        dateObj = new Date(jsdateobject);
    }
    var timeOffSet = dateObj.getTimezoneOffset()/60;
    var currentTime = new Date();
    var timeDiff = Math.round((currentTime - dateObj)/1000/60/60); //time diff in hours
    var timeStamp = "";
    if(timeDiff == 0){
        var timeStampMins = Math.round((currentTime - dateObj)/1000/60);
        if (timeStampMins > 1){
            timeStamp = timeStampMins + " minutes ago";
        } else {
            timeStamp = timeStampMins + " minute ago"; 
        }
    } else if(timeDiff < 24){
        if (timeDiff > 1){
            timeStamp = timeDiff + " hours ago";
        } else {
            timeStamp = timeDiff + " hour ago"; 
        }
    } else {
        timeStamp = (dateObj.getMonth()+1) + "/" + dateObj.getDate() + "/" + dateObj.getFullYear(); 
    }   
    return timeStamp;
}

function fetchActivities(){
    var numResults;
    var numHeader = 3;
    if($("body").hasClass("recentactivity")) {
        numResults = 250;      
    } else {
        numResults = numHeader;
    }
    $.ajax({
        type: 'GET',
        dataType: 'json', 
        url: '/cahubdataservices/activityEvent/retrieveEvent?max=' + numResults + '&callback=?'
    }).done(function(data) {
        if(data.length > 0){
            var message = "";
            var linkText;
            if (sessionOrg.toLowerCase() == "obbr" && sessionStudy.length > 0){
                message = "<h6>Most recent CDR activity for " + sessionStudy + "</h6><ul>";
            } else {
                message = "<h6>Most recent CDR activity</h6><ul>";
            }
            
            var isLink = sessionAuthDM||sessionAuthSuper||sessionAuthLDS||sessionAuthAdmin||sessionDM;
                        
            for(var i=0; i < numHeader; i++){
                linkText = "";
                if(data[i] == null){
                    break;
                }   
                                
                if ((deidentified == null)||(deidentified == false)) {
                    
                    if(data[i].activityType.code == 'SHIP' || data[i].activityType.code == 'SHIPPINGRECEIPT' || data[i].activityType.code == 'SHIPINSP' || data[i].activityType.code == 'SHIPRECPTDISC'){
                        if (data[i].caseId == 'N/A' || data[i].caseId == 'n/a') {
                            linkText = "<a href=\"/cahubdataservices/shippingEvent/showByCbrId/" + data[i].restEventId + "\">" + data[i].restEventId + "</a>";
                        } else {
                            linkText = "<a href=\"/cahubdataservices/shippingEvent/showByCbrIdAndCase/" + data[i].restEventId + "/" + data[i].caseId + "\">" + data[i].restEventId + "</a>";
                        }
                    }else if(data[i].activityType.code == 'UNUSEDKIT'){
                        linkText = "<a href=\"/cahubdataservices/shippingEvent/showByCbrId/" + data[i].restEventId + "\">" + data[i].restEventId + "</a>";
                    }else if(data[i].activityType.code == 'PROCESSEVT'){
                        linkText = "<a href=\"/cahubdataservices/processingEvent/showByCbrId/" + data[i].restEventId + "\">" + data[i].restEventId + "</a>";
                    }else if(data[i].activityType.code == 'CANDIDATE' || data[i].activityType.code == 'CASELINK'){
                        linkText = "<a href=\"/cahubdataservices/candidateRecord/view/" + data[i].additionalInfo2 + "\">" + data[i].additionalInfo1 + "</a>";
                    }else if(data[i].activityType.code == 'FILEUPLOAD' && !data[i].caseId && data[i].study){
                        linkText = data[i].study.code;
                    }else if(data[i].activityType.code == 'QUERY'){
                        linkText = "<a href=\"/cahubdataservices/query/show/" + data[i].additionalInfo1 + "\">" + data[i].additionalInfo1 + "</a>";
                    }else if(data[i].activityType.code == 'INTERVIEW'){
                        linkText = "<a href=\"/cahubdataservices/interviewRecord/show/" + data[i].additionalInfo2 + "\">" + data[i].additionalInfo1 + "</a>";
                    }else if(data[i].activityType.code == 'INTERVIEWSTATUS'){
                        linkText = "<a href=\"/cahubdataservices/interviewRecord/show/" + data[i].additionalInfo1.split(',')[1] + "\">" + data[i].additionalInfo1.split(',')[0] + "</a>";
                    }else if(data[i].activityType.code == 'PROCESSEVT'){
                        if (((sessionStudy == 'BPV')||(sessionStudy == ''))&&(!isLink)) linkText = "<b>" + data[i].caseId +  "</b>";  
                        else linkText = "<a href=\"/cahubdataservices/caseRecord/display/" + data[i].caseId + "\">" + data[i].caseId + "</a>";
                    }else{
                        if (((sessionStudy == 'BPV')||(sessionStudy == ''))&&(!isLink)) linkText = "<b>" + data[i].caseId +  "</b>";  
                        else linkText = "<a href=\"/cahubdataservices/caseRecord/display/" + data[i].caseId + "\">" + data[i].caseId + "</a>";
                    }  
                } else {
                
                    if(data[i].activityType.code == 'SHIP' || data[i].activityType.code == 'UNUSEDKIT' || data[i].activityType.code == 'SHIPPINGRECEIPT' || data[i].activityType.code == 'SHIPINSP' || data[i].activityType.code == 'SHIPRECPTDISC' || data[i].activityType.code == 'PROCESSEVT'){
                        linkText = "<b>" + data[i].restEventId + "</b>";
                    }else if(data[i].activityType.code == 'CANDIDATE' || data[i].activityType.code == 'CASELINK'){
                        linkText = "<b>" + data[i].additionalInfo1 + "</b>";
                    }else if(data[i].activityType.code == 'FILEUPLOAD' && !data[i].caseId && data[i].study){
                        linkText = data[i].study.code;
                    }else if(data[i].activityType.code == 'QUERY'){
                        linkText = "<a href=\"/cahubdataservices/query/show/" + data[i].additionalInfo1 + "\">" + data[i].additionalInfo1 + "</a>";
                    }else if(data[i].activityType.code == 'INTERVIEW'){
                        linkText = "<b>" + data[i].additionalInfo1 + "</b>";
                    }else if(data[i].activityType.code == 'INTERVIEWSTATUS'){
                        linkText = "<b>" + data[i].additionalInfo1.split(',')[0] + "</b>";
                    }else{
                        if (((sessionStudy == 'BPV')||(sessionStudy == ''))&&(!isLink)) linkText = "<b>" + data[i].caseId +  "</b>";  
                        else linkText = "<a href=\"/cahubdataservices/caseRecord/display/" + data[i].caseId + "\">" + data[i].caseId + "</a>";
                    }
                }
                message += "<li>" + data[i].activityType.name + ": " + linkText + "<span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";  
            }
            if($("#recentactivity").length != 0){
                $("#activityEvent").html(message + "</ul>");
                createActivitiesPge(data);
            } else {
                $("#activityEvent").html(message + "</ul><a href=\"/cahubdataservices/activitycenter\" title=\"View more in Activity Center\">more ...</a>");
            }
            $("#activityEvent").addClass("display");
        }
    }); 
}
 
function createActivitiesPge(data){
    var message = "<ul>";
    var splits1 = [];
    var splits2 = [];
    var caseText, eventText, candidateText, queryText, interviewText;
    var isLink = sessionAuthDM||sessionAuthSuper||sessionAuthLDS||sessionAuthAdmin||sessionDM;
    for(var i=0; i < data.length; i++){
        caseText = "";
        eventText = "";
        candidateText = "";
        queryText = "";
        interviewText = "";
        if(data[i] == null){
            break;
        }
        //if(!deidentified)
        if ((deidentified == null)||(deidentified == false))
        {
            if(data[i].activityType.code == 'SHIP' || data[i].activityType.code == 'IMAGEREADY' || data[i].activityType.code == 'SHIPPINGRECEIPT' || data[i].activityType.code == 'SHIPRECPTDISC' || data[i].activityType.code == 'SHIPINSP'){
                if (data[i].caseId == 'N/A' || data[i].caseId == 'n/a') {
                    eventText = "<a href=\"/cahubdataservices/shippingEvent/showByCbrId/" + data[i].restEventId + "\">" + data[i].restEventId + "</a>";
                } else {
                    eventText = "<a href=\"/cahubdataservices/shippingEvent/showByCbrIdAndCase/" + data[i].restEventId + "/" + data[i].caseId + "\">" + data[i].restEventId + "</a>";
                }
            }
            if(data[i].activityType.code == 'UNUSEDKIT'){
                eventText = "<a href=\"/cahubdataservices/shippingEvent/showByCbrId/" + data[i].restEventId + "\">" + data[i].restEventId + "</a>";
            }            
            if(data[i].activityType.code == 'PROCESSEVT'){
                eventText = "<a href=\"/cahubdataservices/processingEvent/showByCbrId/" + data[i].restEventId + "\">" + data[i].restEventId + "</a>";
            }            
            if(data[i].activityType.code == 'CANDIDATE' || data[i].activityType.code == 'CASELINK'){
                candidateText = "<a href=\"/cahubdataservices/candidateRecord/view/" + data[i].additionalInfo2 + "\">" + data[i].additionalInfo1 + "</a>";
            }
            if(data[i].activityType.code == 'QUERY'){
                queryText = "<a href=\"/cahubdataservices/query/show/" + data[i].additionalInfo1 + "\">" + data[i].additionalInfo1 + "</a>";
            }
            if(data[i].activityType.code == 'INTERVIEW'){
                interviewText = "<a href=\"/cahubdataservices/interviewRecord/show/" + data[i].additionalInfo2 + "\">" + data[i].additionalInfo1 + "</a>";
            }
            if(data[i].activityType.code == 'INTERVIEWSTATUS'){
                interviewText = "<a href=\"/cahubdataservices/interviewRecord/show/" + data[i].additionalInfo1.split(',')[1] + "\">" + data[i].additionalInfo1.split(',')[0] + "</a>";
            }
            if(data[i].activityType.code == 'FILEUPLOAD' && !data[i].caseId && data[i].study){
                caseText = "<b>" + data[i].study.code + "</b>";
            }else{
                if (((sessionStudy == 'BPV')||(sessionStudy == ''))&&(!isLink)) caseText = "<b>" + data[i].caseId +  "</b>";  
                else caseText = "<a href=\"/cahubdataservices/caseRecord/display/" + data[i].caseId + "\">" + data[i].caseId + "</a>";
            }
        } else {
            if(data[i].activityType.code == 'SHIP' || data[i].activityType.code == 'IMAGEREADY' || data[i].activityType.code == 'UNUSEDKIT' || data[i].activityType.code == 'SHIPPINGRECEIPT' || data[i].activityType.code == 'SHIPRECPTDISC' || data[i].activityType.code == 'PROCESSEVT'){
                eventText = "<b>" + data[i].restEventId + "</b>";
            }
            if(data[i].activityType.code == 'CANDIDATE' || data[i].activityType.code == 'CASELINK'){
                candidateText = "<b>" + data[i].additionalInfo1 + "</b>";
            }
            if(data[i].activityType.code == 'QUERY'){
                queryText = "<a href=\"/cahubdataservices/query/show/" + data[i].additionalInfo1 + "\">" + data[i].additionalInfo1 + "</a>";
            }
            if(data[i].activityType.code == 'INTERVIEW'){
                interviewText = "<b>" + data[i].additionalInfo1 + "</b>";
            }
            if(data[i].activityType.code == 'INTERVIEWSTATUS'){
                interviewText = "<b>" + data[i].additionalInfo1.split(',')[0] + "</b>";
            }
            if(data[i].activityType.code == 'FILEUPLOAD' && !data[i].caseId && data[i].study){
                caseText = "<b>" + data[i].study.code + "</b>";
            }else{
                if (((sessionStudy == 'BPV')||(sessionStudy == ''))&&(!isLink)) caseText = "<b>" + data[i].caseId +  "</b>";  
                else caseText = "<a href=\"/cahubdataservices/caseRecord/display/" + data[i].caseId + "\">" + data[i].caseId + "</a>";
            }
        }
        if(data[i].activityType.code == 'COLLECT'){
            eventText = "<b>" + data[i].restEventId + "</b>";
        }
        switch(data[i].activityType.code){
            case 'PRCCOMP':
                message += "<li>PRC report for " + caseText + " was submitted by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
             case 'PRCFZNCOMP':
                message += "<li>PRC report FZN for " + caseText + " was submitted by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'PFFCOMP':
                message += "<li>Procurement feedback form  for " + caseText + " was submitted by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'PFFFZNCOMP':
                message += "<li>Procurement feedback form FZN for " + caseText + " was submitted by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'SHIP':
                if(data[i].additionalInfo1 != null && data[i].additionalInfo1.indexOf(',') > -1){
                    splits1 = data[i].additionalInfo1.split(',');
                    splits2 = data[i].additionalInfo2.split(',');
                }else{
                    splits1[0] = data[i].additionalInfo1;
                    splits1[1] = data[i].additionalInfo2;
                    splits2[0] = '';
                    splits2[1] = '';
                }
                message += "<li>Specimens for " + caseText + " were shipped by <b>" + splits1[0] + "</b> to <b>" + splits1[1] + "</b>. CBR event ID: " + eventText + ". " + splits2[0] + " tracking #: <b>" + splits2[1] + "</b> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'UNUSEDKIT':
                splits1 = data[i].additionalInfo1.split(',');
                splits2 = data[i].additionalInfo2.split(',');
                message += "<li>New " + data[i].study.name + " kit(s) were shipped by <b>" + splits1[0] + "</b> to <b>" + splits1[1] + "</b>. CBR event ID: " + eventText + ". " + splits2[0] + " tracking #: <b>" + splits2[1] + "</b> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'STATUSCHG':
                message += "<li>Case status for " + caseText + " was changed by <span class=\"activityInitiator\">" + data[i].initiator + "</span> to <b>" + data[i].additionalInfo2 +  "</b>. Previous status: <b>" + data[i].additionalInfo1 + "</b> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'NEWRIN':
                message += "<li>New RIN data for " + caseText + " are now available <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'FASTTRACK':
                message += "<li>FastTrack was requested for " + caseText + " by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'IMAGEREADY':
                message += "<li>Aperio images for " + caseText + " are now available. CBR event ID: " + eventText + " <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'COLLECT':
                message += "<li>" + caseText + " collection has occurred at <b>" + data[i].additionalInfo1 + "</b>. CBR event ID: " + eventText + " <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'FILEUPLOAD':
                message += "<li>New file <b>" + data[i].additionalInfo1 + "</b> was uploaded for " + caseText + " by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'EMAIL':
                message += "<li>" + caseText + " specimen data was transmitted to VARI by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'CANDIDATE':
                message += "<li>New candidate " + candidateText + " was created by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'CASELINK':
                message += "<li>Candidate " + candidateText + " was linked to " + caseText + " by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'BPVCASE':
                message += "<li>New BPV case " + caseText + " was created at <b>" + data[i].bssCode + "</b> by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'PRCAVAILABLE':
                message += "<li>GTEx PRC report for " + caseText + " was reviewed by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'PROCFEEDAVAI':
                message += "<li>GTEx procurement feedback for " + caseText + " was reviewed by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'PROCFEEDFZNAVAI':
                message += "<li>GTEx procurement feedback FZN for " + caseText + " was reviewed by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'PRCFORQC':
                message += "<li>" + caseText + " PRC report was flagged for QC review. The report was submitted by <span class=\"activityInitiator\">" + data[i].additionalInfo1 + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'SHIPPINGRECEIPT':
                if (data[i].caseId == 'N/A' || data[i].caseId == 'n/a') {
                    message += "<li>"+data[i].study.code + " shipment receipt for " + eventText + " was received.<span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                } else {
                    message += "<li>"+data[i].study.code + " shipment receipt for " + eventText + " was received. Case ID: " + caseText + "<span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                }
                break;
            case 'SHIPRECPTDISC':
                if (data[i].caseId == 'N/A' || data[i].caseId == 'n/a') {
                    message += "<li>"+data[i].study.code + " shipment receipt for " + eventText + " was received with discrepancies.<span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                } else {
                    message += "<li>"+data[i].study.code + " shipment receipt for " + eventText + " was received with discrepancies. Case ID: " + caseText + "<span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                }
                break;                
            case 'QUERY':
                if(data[i].caseId){
                    message += "<li>New Query Tracker " + queryText + " for " + caseText + " was created by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                }else{
                    message += "<li>New Query Tracker " + queryText + " was created by <span class=\"activityInitiator\">" + data[i].initiator + "</span> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                }
                break;
            case 'INTERVIEW':
                message += "<li>New BPV ELSI Interview was created at " + data[i].bssCode + " by <span class=\"activityInitiator\">" + data[i].initiator + "</span>. Interview ID: " + interviewText + "<span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'INTERVIEWSTATUS':
                splits1 = data[i].additionalInfo1.split(',');
                splits2 = data[i].additionalInfo2.split(',');
                message += "<li>Interview status for " + interviewText + " was changed by <span class=\"activityInitiator\">" + data[i].initiator + "</span> to <b>" + splits2[1] +  "</b>. Previous status: <b>" + splits2[0] + "</b> <span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;
            case 'PROCESSEVT':
                splits1 = data[i].additionalInfo1.split(',');
                //splits2 = data[i].additionalInfo2.split(',');                
                message += "<li><b>"+ splits1[0] +"</b> were added to the case "+caseText+". CBR event ID: <b>" + eventText + "</b><span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;                
            case 'SHIPINSP':
                message += "<li>Discrepant specimens were reported - Inspection event "+ eventText +". Case ID: " + caseText + "<span class=\"activityTime\">" + getTimeStamp(data[i].dateCreated) + "</span></li>";
                break;                                
            default:
                message += "<li>DEFAULT</li>";
        }
    }
    $("#recentactivity").html(message + "</ul>");
    $("#recentactivity").slideDown();
}

function getLabel(elem, findBy){
   var label = $("label[for='"+elem.attr(findBy)+"']")
   if (label.length > 0) {
     return label;
   } else {
     return null;
   } 
}

function scrollTo( elem , scrollType) { 
    var domEl;
    var scrollLoc;
    var labelElem = null;
   
    $(".currentErr").removeClass("currentErr");
    if($(elem).length > 0){
        domEl = $(elem);
        labelElem = getLabel(domEl, "id");
        if(labelElem != null){
            scrollLoc = labelElem;
        } else {
            scrollLoc = domEl;
        }
    } else if($('[name="' + elem.replace("#","") + '"]').length > 0){
        domEl = $('[name="' + elem.replace("#","") + '"]');
        labelElem = getLabel(domEl, "name");
        if(labelElem != null){
            scrollLoc = labelElem;
        } else {
            scrollLoc = domEl;
        }
    }
    if(domEl != undefined){
      $('html,body').animate({
        scrollTop: scrollLoc.offset().top
      }, "slow"); 
      if(scrollType == "error"){
        if(domEl.closest(".errors").length > 0){
          domEl.closest(".errors").addClass("currentErr");
        } else {
          domEl.parent().addClass("currentErr");
        }
        $(".currentErr").removeClass( "currentErr", 8000, "" );
      }
    }
}

function fetchVocab(vocabGrp){
    if(vocabJsnLoc.length != 0) {
        vocabObj[vocabGrp] = {};
        $.ajax({
            type: 'GET',
            dataType: 'jsonp', 
            jsonp: 'json.wrf',
            url: vocabJsnLoc + '/solr/cde?fl=*&wt=json&rows=500&q=colName:' + vocabGrp + '.*'
        }).done(function(data) {
            if(data.response.docs[0]){
                var itemsArr = data.response.docs;
                var vocabArr = new Array();
                for (var i=0; i<itemsArr.length; i++) {
                    var srcDefArr = itemsArr[i].srcDef;
                    var srcDefStr = "";
                    if( srcDefArr != undefined) {
                        for (var j=0; j<srcDefArr.length; j++) {
                            srcDefStr += "<div>" + srcDefArr[j] + "</div>";
                        }
                        for (var k=0; k<itemsArr[i].colName.length; k++) {
                            vocabObj[vocabGrp][itemsArr[i].colName[k]] = srcDefStr;
                        }
                    }
                }
            }
            createVocabTooltips(); 
        });
    }
}

function createVocabTooltips(){
    $('.vocab-tooltip').each(function() { 
        if (vocabObj[$(this).attr("id").split(".")[0]][$(this).attr("id")] != undefined) {
            $(this).after("<span id = \"" + $(this).attr("id") + "\" class=\"ui-vocab-tooltip ui-state-highlight ca-tooltip\"><span class=\"ui-icon ui-icon-info\">&nbsp;</span></span>");
            $(this).remove();   
        }
    });
    $('.ui-vocab-tooltip').mouseenter(function() { 
        tooltip.show(vocabObj[$(this).attr("id").split(".")[0]][$(this).attr("id")]);
    });
    $('.ui-vocab-tooltip').mouseleave(function() {
        tooltip.hide();
    });
}

function createTooltips(){
    //no background tooltips
    $('.ca-tooltip-nobg').mouseenter(function() { 
        if ($(this).data("msg") != undefined) {
            tooltip.show(unescape($(this).data("msg")));
        }
    });
    $('.ca-tooltip-nobg').mouseleave(function() {
        tooltip.hide();
    });
    
    // info icon tooltips
    $('.cahub-tooltip').each(function() { 
        var idStr = "";
        if ($(this).attr("id") != undefined) {
            idStr = "id = \"" + $(this).attr("id") + "\" ";
        }
        $(this).after("<span data-msg = \"" + escape($(this).data("msg")) + "\" " + idStr + "class=\"ui-ca-tooltip ui-state-highlight ca-tooltip\"><span class=\"ui-icon ui-icon-info\">&nbsp;</span></span>");
        $(this).remove();  
    });
    $('.ui-ca-tooltip').mouseenter(function() {  
        if ($(this).data("msg") != undefined && $(this).data("msg") != "undefined") {
            tooltip.show(unescape($(this).data("msg")));
        }
    });
    $('.ui-ca-tooltip').mouseleave(function() {
        tooltip.hide();
    });

    // bubble tooltips
    $('.ca-bubble').mouseenter(function() { 
        if ($(this).data("msg") != undefined) {
            tooltip.show(unescape($(this).data("msg")));
        }
    });
    $('.ca-bubble').mouseleave(function() {
        tooltip.hide();
    });
}

function startVocab() {
    var currentPage = document.URL.replace(/^(?:\/\/|[^\/]+)*\//, "").split("/")[1];
    $("label").each(function(){
        var vers = "";
        if ($(this).data("version") != undefined) {
            vers = $(this).data("version");
        }
        if($(this).attr('for') && $(this).attr('for').length > 0) {
            var vocabObjStr = currentPage;
            vocabObjStr = vocabObjStr + "." + $(this).attr('for');
            if(document.getElementById(vocabObjStr) == null){
                $(this).after("<span id=\"" + vocabObjStr.toLowerCase() + vers + "\" class=\"vocab-tooltip\"></span>");
            }
        }
    });
    
    $('.vocab-tooltip').each(function() {
        var vocabGrp = $(this).attr("id").split(".")[0];
        if (vocabObj[vocabGrp] == undefined) {
            fetchVocab(vocabGrp);
        }
    });
}

//vocab assist functions
function log( message, fieldName, typ ) {
    $("#" + fieldName + "-" + typ + "-results").html(message + "<br />" + $("#" + fieldName + "-" + typ + "-results").html());
    $("#" + fieldName + "-" + typ + "-results a").attr("target","_blank");
    $("#" + fieldName + "-" + typ + "-results-container").show();
}
function refreshlog(fieldName, presetVal, typ) {
    $( "#" + fieldName + "-" + typ + "-results" ).html(""); 
    $( "#" + fieldName + "-" + typ + "-results-container" ).hide();
    $( "#" + fieldName + "-dialog" ).dialog("open");
    if (presetVal) {
        presetLog(fieldName, typ);
    }
}
function presetLog(fieldName, typ) {
    $( "#" + fieldName + "-" + typ + "-label").html().toLowerCase() == labelSelectedDefault.toLowerCase() ? $( "#" + fieldName + "-" + typ).attr("value", "") : $( "#" + fieldName + "-" + typ).attr("value",$( "#" + fieldName + "-" + typ + "-label").html());

    if($( "#" + fieldName + "-" + typ ).val().length > 0) {
        presetLogWithData2(typ, fieldName)
    /**  $.ajax({
                url: vocabJsnLoc + "/solr/" + typ,
                dataType: "jsonp",
                jsonp: 'json.wrf',
                data: {
                    fl: "*,score",
                    wt: "json",
                    q: "id:" + $( "#" + fieldName + "-" + typ + "-id" ).val(),
                    rows: "1"
                },
                success: function( data ) {
                    presetLogWithData(data, typ, fieldName);
                    $( "#" + fieldName + "-" + typ + "-results-container").show();
                }
            });**/
    }
}
function presetLogWithData(data, typ, fieldName){
    if (typ === "cod") {
        if(data.response.docs[0].srcDef != undefined){
            var srcDefArrToStr = "";
            for (var i=0; i<data.response.docs[0].srcDef.length; i++) {
                srcDefArrToStr += "<div>" + data.response.docs[0].srcDef[i] + "</div>";
            }
            log("<h3>Definition</h3>" + srcDefArrToStr, fieldName, "cod");
        }
        if(data.response.docs[0].ICDcd != undefined){
            log("<b>ICDcd:</b> " + data.response.docs[0].ICDcd, fieldName, "cod");
        }
        if(data.response.docs[0].cui != undefined){
            log("<b>CUI:</b> " + data.response.docs[0].cui, fieldName, "cod");
        }
        if(data.response.docs[0].typ != undefined){
            log("<b>TYP:</b> " + data.response.docs[0].typ, fieldName, "cod");
        }
        if(data.response.docs[0].cod != undefined){
            log("<b>" + data.response.docs[0].cod + "</b>", fieldName, "cod");
        }
    } else if (typ === "rx") {
        if(data.response.docs[0].genName != undefined){
            var genNameArrToStr = "";
            for (var i=0; i<data.response.docs[0].genName.length; i++) {
                genNameArrToStr += "<div>" + data.response.docs[0].genName[i] + "</div>";
            }
            log("<h3>Generic Names</h3>" + genNameArrToStr, fieldName, "rx");
        }
        if(data.response.docs[0].rx != undefined){
            log("<b>RX:</b> " + data.response.docs[0].rx, fieldName, "rx");
        }
        if(data.response.docs[0].typ != undefined){
            log("<b>TYP:</b> " + data.response.docs[0].typ, fieldName, "rx");
        }
    } else if (typ === "cde") {
        if(data.response.docs[0].srcDef != undefined){
            var srcDefArrToStr = "";
            for (var i=0; i<data.response.docs[0].srcDef.length; i++) {
                srcDefArrToStr += "<div>" + data.response.docs[0].srcDef[i] + "</div>";
            }
            log("<h3>Definition</h3>" + srcDefArrToStr, fieldName, "cde");
        }
        if(data.response.docs[0].cde != undefined){
            log("<b>CDE:</b> " + data.response.docs[0].cde, fieldName, "cde");
        }
        if(data.response.docs[0].typ != undefined){
            log("<b>TYP:</b> " + data.response.docs[0].typ, fieldName, "cde");
        }
    }
}


function presetLogWithData2(typ, fieldName){
    if (typ === "cod") {
        
        if( $("#" + fieldName + "-cod-srcDef").val()!=null &&  $("#" + fieldName + "-cod-srcDef").val().length > 0){
            // log("<h3>Definition</h3>" + decodeURIComponent($("#" + fieldName + "-cod-srcDef").val()), fieldName, "cod"); 
            log("<h3>Definition</h3>" + $("#" + fieldName + "-cod-srcDef").val(), fieldName, "cod"); 
        }else if(fieldName=='immediateCause' && immediateCause_cod_srcDef_str.length > 0){
            log("<h3>Definition</h3>" + immediateCause_cod_srcDef_str, fieldName, "cod"); 
        }else if(fieldName=='firstCause' && firstCause_cod_srcDef_str.length > 0){
            log("<h3>Definition</h3>" + firstCause_cod_srcDef_str, fieldName, "cod"); 
        }else if(fieldName=='lastCause' && lastCause_cod_srcDef_str.length > 0){
            log("<h3>Definition</h3>" + lastCause_cod_srcDef_str, fieldName, "cod"); 
        }else if(fieldName=='deathCertificateCause' && deathCertificateCause_cod_srcDef_str.length > 0){
            log("<h3>Definition</h3>" + deathCertificateCause_cod_srcDef_str, fieldName, "cod"); 
        }else if(fieldName=='medicalCondition' && medicalCondition_cod_srcDef_str.length > 0){
            log("<h3>Definition</h3>" + medicalCondition_cod_srcDef_str, fieldName, "cod"); 
        }else{
            
        }
        
        if( $("#" + fieldName + "-cod-ICDcd").val() != null && $("#" + fieldName + "-cod-ICDcd").val().length > 0 ){
            log("<b>ICDcd:</b> " + $("#" + fieldName + "-cod-ICDcd").val(), fieldName, "cod");
        }
        
        if( $("#" + fieldName + "-cod-cui").val()!=null && $("#" + fieldName + "-cod-cui").val().length > 0){
            log("<b>CUI:</b> " + $("#" + fieldName + "-cod-cui").val(), fieldName, "cod");
        }
        
        if($("#" + fieldName + "-cod-typ").val() != undefined && $("#" + fieldName + "-cod-typ").val().length > 0 && $("#" + fieldName + "-cod-typ").val() != "MEDCON"){
            log("<b>TYP:</b> " + $("#" + fieldName + "-cod-typ").val(), fieldName, "cod");
        }
        
        if($("#" + fieldName + "-cod-cod").val()!=null && $("#" + fieldName + "-cod-cod").val().length > 0 && $("#" + fieldName + "-cod-typ").val() != "MEDCON"){
            log("<b>" + $("#" + fieldName + "-cod-cod").val() + "</b>", fieldName, "cod");
        }
        
    }else if (typ === "rx") {
        
        if( $("#" + fieldName + "-rx-genName").val()!=null &&  $("#" + fieldName + "-rx-genName").val().length > 0){
            log("<h3>Generic Names</h3>" + decodeURIComponent($("#" + fieldName + "-rx-genName").val()), fieldName, "rx"); 
        }else if(fieldName=='medicationName' && medicationName_rx_genName_str.length > 0){
            log("<h3>Generic Names</h3>" + medicationName_rx_genName_str, fieldName, "rx"); 
        }else{
            
        }
        
        if($("#" + fieldName + "-rx-rx").val()!=null && $("#" + fieldName + "-rx-rx").val().length > 0){
            log("<b>RX:</b> " + $("#" + fieldName + "-rx-rx").val(), fieldName, "rx");
        }
        
        if($("#" + fieldName + "-rx-typ").val() != undefined && $("#" + fieldName + "-rx-typ").val().length > 0){
            log("<b>TYP:</b> " + $("#" + fieldName + "-rx-typ").val(), fieldName, "rx");
        }
        
      
    }else{
        
    }
    
}


function createVocabSelectHTML(typ, vocabSrc, dialogTitle){
    var labelSelected = labelSelectedDefault;
    var vocabSelectHTML = "";
    var fieldName = vocabSrc.attr("id"); 
    var isMedCon = false;
    if(dialogTitle == 'Medical History'){
        isMedCon=true;
    }
    vocabSelectHTML += "<div id=\"" + fieldName + "-dialog\" class=\"" + typ + "-dialog hide\" title=\"" + dialogTitle + "\">";
    vocabSelectHTML += "<p>Type in at least three characters to filter through results.</p>";
    vocabSelectHTML += "<input id=\"" + fieldName + "-" + typ + "\" class=\"allow-exceed-limit " + typ + "-user-input-srch\" type=\"text\" />";
    vocabSelectHTML += "<div id=\"" + fieldName + "-" + typ + "-results-container\" class=\"vocab-" + typ + "-results-container\">";
    vocabSelectHTML += "<h3>Selected Result:</h3>";
    vocabSelectHTML += "<div id=\"" + fieldName + "-" + typ + "-results\" class=\"vocab-" + typ + "-results\"></div>";
    vocabSelectHTML += "<div class=\"buttons-div button ui-corner-all clearfix\"><input type=\"submit\" id=\"" + fieldName + "-" + typ + "-submit\" disabled=disabled value=\"Confirm\" class=\"left ui-corner-all\" />";
    vocabSelectHTML += "<input type=\"submit\" id=\"" + fieldName + "-" + typ + "-cancel\" value=\"Cancel\" class=\"left button ui-button ui-widget ui-state-default ui-corner-all\"/></div>";
    vocabSelectHTML += "</div></div>";
    if($("#"+ fieldName + "-" + typ + "-" + typ).val().length > 0) {
        labelSelected = $("#"+ fieldName + "-" + typ + "-" + typ).val();
    }
    vocabSelectHTML += "<a title=\"Select from list in Vocab\" id=\"" + fieldName + "-zoomin\" class=\"ca-ui-zoomin ca-tooltip\"><span class=\"ui-icon-zoomin\" id=\"" + fieldName + "-" + typ + "-label\">" + labelSelected + "</span></a>";
    if(vocabSrc.hasClass("with-manual-override")){
        vocabSelectHTML = "<input id=\"" + fieldName + "_vocab_radio\" type=\"radio\" name=\"" + fieldName + "R\" class=\"checkBxLabelRight\" value=\"vocab\"/>" + vocabSelectHTML;
        vocabSelectHTML += "<br /><input id=\"" + fieldName + "_manual_radio\" type=\"radio\" name=\"" + fieldName + "R\" class=\"checkBxLabelRight\" value=\"manual\" /> Other";
    }
    vocabSrc.html(vocabSelectHTML);
        
    if(vocabSrc.hasClass("with-manual-override")){
        if($("#"+fieldName + "_manual").val().length > 0){
            $( "#" + fieldName + "_manual_radio").prop("checked", true);
        } else {
            $( "#" + fieldName + "_vocab_radio").prop("checked", true);
        }
        createDependentElements($("#"+fieldName + "_manual"));
    }
    selectByVocab(fieldName,typ, isMedCon);
    
}

function selectByVocab(fieldName, typ, isMedCon) {
    if(typ==="cod"){
        selectCODByVocab(fieldName, isMedCon);
    } else if(typ==="rx"){
        selectRXByVocab(fieldName);
    }
}

function activateVocabSubmitButton(fieldName,typ) {
    if( !$("#" + fieldName + "-" + typ + "-submit").hasClass("button") ) {
        $("#" + fieldName + "-" + typ + "-submit").attr("disabled",false);
        $("#" + fieldName + "-" + typ + "-submit").addClass("button");
        $("#" + fieldName + "-" + typ + "-submit").addClass("ui-button");
        $("#" + fieldName + "-" + typ + "-submit").addClass("ui-widget");
        $("#" + fieldName + "-" + typ + "-submit").addClass("ui-state-default");
    }
}

function deactivateVocabSubmitButton(fieldName,typ) {
    $("#" + fieldName + "-" + typ + "-submit").attr("disabled","disabled");
    $("#" + fieldName + "-" + typ + "-submit").removeClass("button");
    $("#" + fieldName + "-" + typ + "-submit").removeClass("ui-button");
    $("#" + fieldName + "-" + typ + "-submit").removeClass("ui-widget");
    $("#" + fieldName + "-" + typ + "-submit").removeClass("ui-state-default");
}

function selectCODByVocab(fieldName, isMedCon) {
    var top_margin = 150;
    var srcDefArrToStr = "";
    var solrType = "";
    
    if (isMedCon) {
        solrType = "codcpt";
    } else {
        solrType = "cod";
    }
    if ($("body").hasClass("deathcircums") == true) {
        top_margin = 20;
    }
    $( "#" + fieldName + "-dialog" ).dialog({
        autoOpen: false,
        position: ['center',top_margin],
        width: 800,
        modal: true,
        buttons: {},
        close: function(){
            deactivateVocabSubmitButton(fieldName,"cod");
            $(".ui-autocomplete").hide();
        }
    });
    $( "#" + fieldName + "-zoomin").click(function(){ 
        $( "#" + fieldName + "_vocab_radio").prop("checked",true);
        $( "#" + fieldName + "_manual").hide();
        refreshlog(fieldName, true, "cod");
    });
    $( "#" + fieldName + "-cod" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: vocabJsnLoc + "/solr/" + solrType + "?fl=label:cpt,label:cod,*",
                dataType: "jsonp",
                jsonp: 'json.wrf',
                data: {
                    wt: "json",
                    q: request.term,
                    rows: "25"
                },
                success: function( data ) {
                    response( $.map( data.response.docs, function( item ) {
                        return {
                            id: item.id,
                            typ: item.typ,
                            cod: item.label,
                            cui: item.cui,
                            ICDcd: item.ICDcd,
                            cvocabVer: item.cvocabVer,
                            srcDef: item.srcDef,
                            _version_: item._version_,
                            score: item.score,
                            value: item.label
                        }
                    }));
                }
            });
        },
        minLength: 2,
        select: function( event, ui ) {
            refreshlog(fieldName, false, "cod");
            codVocabObj = ui;
            srcDefArrToStr = "";
            if(ui.item.srcDef != undefined){
                // var srcDefArrToStr = "";
                for (var i=0; i<ui.item.srcDef.length; i++) {
                    srcDefArrToStr += "<div>" + ui.item.srcDef[i] + "</div>";
                }
                log("<h3>Definition</h3>" + srcDefArrToStr, fieldName, "cod");
            }
            if(ui.item.ICDcd != undefined){
                log("<b>ICDcd:</b> " + ui.item.ICDcd.toString(), fieldName, "cod");
            }
            if(ui.item.cui != undefined){
                log("<b>CUI:</b> " + ui.item.cui.toString(), fieldName, "cod");
            }
            if(ui.item.typ != undefined && !isMedCon){
                log("<b>TYP:</b> " + ui.item.typ.toUpperCase(), fieldName, "cod");
            }
            if(ui.item.cod != undefined){
                log("<b>" + ui.item.cod + "</b>", fieldName, "cod");
            }
            activateVocabSubmitButton(fieldName,"cod");
        }
    });		
    $("#" + fieldName + "-cod-submit").click(function(){      
        if ($("#" + fieldName + "-cod").val().length > 0) {
            if(codVocabObj.item != undefined) {
                if(codVocabObj.item.cui != undefined){
                    $("#" + fieldName + "-cod-cui").val(codVocabObj.item.cui);
                } else {
                    $("#" + fieldName + "-cod-cui").val("");
                }
                if(codVocabObj.item.cod != undefined){
                    $("#" + fieldName + "-cod-cod").val(codVocabObj.item.cod);
                    $("#" + fieldName + "-cod-label").html(codVocabObj.item.cod);
                    $("#" + fieldName + "-cod-label").parents('td').last().removeClass('errors');
                } else {
                    $("#" + fieldName + "-cod-cod").val("");
                }
                if(codVocabObj.item.typ != undefined && !isMedCon){
                    $("#" + fieldName + "-cod-typ").val(codVocabObj.item.typ.toUpperCase());
                } else if(isMedCon){
                    $("#" + fieldName + "-cod-typ").val('MEDCON');
                }else {
                    $("#" + fieldName + "-cod-typ").val("");
                }
                if(codVocabObj.item.id != undefined){
                    $("#" + fieldName + "-cod-id").val(codVocabObj.item.id);
                } else {
                    $("#" + fieldName + "-cod-id").val("");
                }
                if(codVocabObj.item.ICDcd != undefined){
                    $("#" + fieldName + "-cod-ICDcd").val(codVocabObj.item.ICDcd);
                } else {
                    $("#" + fieldName + "-cod-ICDcd").val("");
                }
                if(codVocabObj.item.cvocabVer != undefined){
                    $("#" + fieldName + "-cod-cvocabVer").val(codVocabObj.item.cvocabVer);
                } else {
                    $("#" + fieldName + "-cod-cvocabVer").val("");
                }
          
                $("#" + fieldName + "-cod-srcDef").val(srcDefArrToStr);

                if(fieldName=='immediateCause' && immediateCause_cod_srcDef_str.length > 0){
                    immediateCause_cod_srcDef_str=""
                }else if(fieldName=='firstCause' && firstCause_cod_srcDef_str.length > 0){
                    firstCause_cod_srcDef_str=""
                }else if(fieldName=='lastCause' && lastCause_cod_srcDef_str.length > 0){
                    lastCause_cod_srcDef_str=""
                }else if(fieldName=='deathCertificateCause' && deathCertificateCause_cod_srcDef_str.length > 0){
                    deathCertificateCause_cod_srcDef_str = ""
                }else if(fieldName=='medicalCondition' && medicalCondition_cod_srcDef_str.length > 0){
                    medicalCondition_cod_srcDef_str=""
                }else{

                }
            }      
            $("#" + fieldName + "-dialog").dialog("close"); 
        } else {
            $("#" + fieldName + "-dialog").addClass("disabledsave");
            $("#" + fieldName + "-cod-results").html("<span class=\"redtext\">Please enter valid value or cancel.</span>");
            $("#" + fieldName + "-cod-results-container h3").hide();
        }
      
    });  		
    $("#" + fieldName + "-cod-cancel").click(function(){
        $("#" + fieldName + "-dialog").dialog("close");
    });     
}

function selectRXByVocab(fieldName, dataType) {
    var genNameArrToStr = "";
    $( "#" + fieldName + "-dialog" ).dialog({
        autoOpen: false,
        position: ['center',150],
        width: 800,
        modal: true,
        buttons: {},
        close: function(){
            deactivateVocabSubmitButton(fieldName,"rx");
            $(".ui-autocomplete").hide();
        }
    });
    $( "#" + fieldName + "-zoomin").click(function(){ 
        $( "#" + fieldName + "_vocab_radio").prop("checked",true);
        $( "#" + fieldName + "_manual").hide();
        refreshlog(fieldName, true, "rx");
    });
    $( "#" + fieldName + "-rx" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: vocabJsnLoc + "/solr/rx",
                dataType: "jsonp",
                jsonp: 'json.wrf',
                data: {
                    fl: "*,score",
                    wt: "json",
                    q: request.term,
                    rows: "25"
                },
                success: function( data ) {
                    response( $.map( data.response.docs, function( item ) {
                        return {
                            id: item.id,
                            typ: item.typ,
                            cvocabVer: item.cvocabVer,
                            rx: item.rx,
                            genName: item.genName,
                            _version_: item._version_,
                            score: item.score,
                            value: item.rx
                        }
                    }));
                }
            });
        },
        minLength: 2,
        select: function( event, ui ) {
            refreshlog(fieldName, false, "rx");
            rxVocabObj = ui;
            genNameArrToStr = "";
            if(ui.item.genName != undefined){
                // var genNameArrToStr = "";
                for (var i=0; i<ui.item.genName.length; i++) {
                    genNameArrToStr += "<div>" + ui.item.genName[i] + "</div>";
                }
                log("<h3>Generic Names</h3>" + genNameArrToStr, fieldName, "rx");
            }
            if(ui.item.rx != undefined){
                log("<b>RX:</b> " + ui.item.rx, fieldName, "rx");
            }
            if(ui.item.typ != undefined){
                log("<b>TYP:</b> " + ui.item.typ.toUpperCase(), fieldName, "rx");
            }
            activateVocabSubmitButton(fieldName,"rx");
        }
    });		
    $("#" + fieldName + "-rx-submit").click(function(){
        if ($("#" + fieldName + "-rx").val().length > 0) {
            if(rxVocabObj.item != undefined) {
                if(rxVocabObj.item.rx != undefined){
                    $("#" + fieldName + "-rx-rx").val(rxVocabObj.item.rx);
                    $("#" + fieldName + "-rx-label").html(rxVocabObj.item.rx);
                    $("#" + fieldName + "-rx-label").parents('td').last().removeClass('errors');
                } else {
                    $("#" + fieldName + "-rx-rx").val("");
                }
                if(rxVocabObj.item.typ != undefined){
                    $("#" + fieldName + "-rx-typ").val(rxVocabObj.item.typ.toUpperCase());
                } else {
                    $("#" + fieldName + "-rx-typ").val("");
                }
                if(rxVocabObj.item.id != undefined){
                    $("#" + fieldName + "-rx-id").val(rxVocabObj.item.id);
                } else {
                    $("#" + fieldName + "-rx-id").val("");
                }
                if(rxVocabObj.item.cvocabVer != undefined){
                    $("#" + fieldName + "-rx-cvocabVer").val(rxVocabObj.item.cvocabVer);
                } else {
                    $("#" + fieldName + "-rx-cvocabVer").val("");
                }
          
                $("#" + fieldName + "-rx-genName").val(genNameArrToStr);
                if(fieldName=='medicationName' && medicationName_rx_genName_str.length > 0){
                    medicationName_rx_genName_str=""
                }
            } 
            $("#" + fieldName + "-dialog").dialog("close");
        } else {
            $("#" + fieldName + "-dialog").addClass("disabledsave");
            $("#" + fieldName + "-rx-results").html("<span class=\"redtext\">Please enter valid value or cancel.</span>");
            $("#" + fieldName + "-rx-results-container h3").hide();
        }
    });  		
    $("#" + fieldName + "-rx-cancel").click(function(){
        $("#" + fieldName + "-dialog").dialog("close");
    });     
}

var tooltip=function(){
    var id = 'tt';
    var top = 3;
    var left = 3;
    var maxw = 300;
    var speed = 10;
    var timer = 20;
    var endalpha = 95;
    var alpha = 0;
    var tt,t,c,b,h;
    var ie = document.all ? true : false;
    return{
        show:function(v,w){
            document.body.style.cursor = 'help';
            if(tt == null){
                tt = document.createElement('div');
                tt.setAttribute('id',id);
                tt.setAttribute('class','ui-corner-all shadow');
                t = document.createElement('div');
                t.setAttribute('id',id + 'top');
                c = document.createElement('div');
                c.setAttribute('id',id + 'cont');
                b = document.createElement('div');
                b.setAttribute('id',id + 'bot');
                tt.appendChild(t);
                tt.appendChild(c);
                tt.appendChild(b);
                document.body.appendChild(tt);
                tt.style.opacity = 0;
                tt.style.filter = 'alpha(opacity=0)';
                document.onmousemove = this.pos;
            }
            tt.style.display = 'block';
            c.innerHTML = v;
            tt.style.width = w ? w + 'px' : 'auto';
            if(!w && ie){
                t.style.display = 'none';
                b.style.display = 'none';
                tt.style.width = tt.offsetWidth;
                t.style.display = 'block';
                b.style.display = 'block';
            }
            if(tt.offsetWidth > maxw){
                tt.style.width = maxw + 'px'
            }
            h = parseInt(tt.offsetHeight) + top;
            clearInterval(tt.timer);
            tt.timer = setInterval(function(){
                tooltip.fade(1)
            },timer);
        },
        pos:function(e){
            var u = ie ? event.clientY + document.documentElement.scrollTop : e.pageY;
            var l = ie ? event.clientX + document.documentElement.scrollLeft : e.pageX;
            tt.style.top = (u - h) + 'px';
            tt.style.left = (l + left) + 'px';
        },
        fade:function(d){
            var a = alpha;
            if((a != endalpha && d == 1) || (a != 0 && d == -1)){
                var i = speed;
                if(endalpha - a < speed && d == 1){
                    i = endalpha - a;
                }else if(alpha < speed && d == -1){
                    i = a;
                }
                alpha = a + (i * d);
                tt.style.opacity = alpha * .01;
                tt.style.filter = 'alpha(opacity=' + alpha + ')';
            }else{
                clearInterval(tt.timer);
                if(d == -1){
                    tt.style.display = 'none'
                }
            }
        },
        hide:function(){
            document.body.style.cursor = 'default';
            clearInterval(tt.timer);
            tt.timer = setInterval(function(){
                tooltip.fade(-1)
            },timer);
        }
    };
}();

function changeToInfoBox() {
    $("#container .message").each(function() {
        if ($(this).html() != "") {
            $(this).removeClass("message");
            $(this).addClass("cahubmsg ui-widget");
            $(this).html("<div class=\"ui-state-highlight ui-corner-all auto_center\"><span class=\"ui-icon ui-icon-info\"></span><div class=\"infobox\">" + $(this).html() + "</div></div>");
            $(this).show();
        }
    });
}

function capitalize(str) {
    strVal = '';
    str = str.split(' ');
    for (var chr = 0; chr < str.length; chr++) {
        strVal += str[chr].substring(0, 1).toUpperCase() + str[chr].substring(1, str[chr].length) + ' '
    }
    return strVal.replace(/(^[\s]+|[\s]+$)/g, '');
}

//onchange needed in certain browsers for copy-and-paste
function inputCharLimit() {
    $("textarea:not(.nolimit)").keyup(function() {
        var textareaId = $(this).attr("id");
        var textareaCount = $(this).val().length;
        
        if($('#' + textareaId + 'LimitErr').length > 0) {
            $('#' + textareaId + 'LimitErr').remove();
            $(this).removeClass("errors");
        }
        if(textareaCount > 4000) {
            $('<div id="' + textareaId + 'LimitErr" class="limiterr redtext">The character limit of 4000 has been exceeded by ' + (textareaCount - 4000) + '.</div>').insertAfter($(this));
            $(this).addClass("errors");
        }
        permissionToSave();
    });       
    $("input:not(.nolimit)").keyup(function() {
        var inputId = $(this).attr("id");
        var inputCount = $(this).val().length;
        
        if($('#' + inputId + 'LimitErr').length > 0) {
            $('#' + inputId + 'LimitErr').remove();
            $(this).removeClass("errors");
        }
        if(inputCount > 255) {
            $('<div id="' + inputId + 'LimitErr" class="limiterr redtext">The character limit of 255 has been exceeded by ' + (inputCount - 255) + '.</div>').insertAfter($(this));
            $(this).addClass("errors");
        }
        permissionToSave();
    });
    $("textarea:not(.nolimit)").change(function() {
        var textareaId = $(this).attr("id");
        var textareaCount = $(this).val().length;
        
        if($('#' + textareaId + 'LimitErr').length > 0) {
            $('#' + textareaId + 'LimitErr').remove();
        }
        if(textareaCount > 4000) {
            $('<div id="' + textareaId + 'LimitErr" class="limiterr redtext">The character limit of 4000 has been exceeded by ' + (textareaCount - 4000) + '.</div>').insertAfter($(this));
        }
        permissionToSave();
    });       
    $("input").change(function() {
        var inputId = $(this).attr("id");
        var inputCount = $(this).val().length;
        
        if($('#' + inputId + 'LimitErr').length > 0) {
            $('#' + inputId + 'LimitErr').remove();
        }
        if(inputCount > 255) {
            $('<div id="' + inputId + 'LimitErr" class="limiterr redtext">The character limit of 255 has been exceeded by ' + (inputCount - 255) + '.</div>').insertAfter($(this));
        }
        permissionToSave();
    });
}

function permissionToSave() {
    var permitSave = true;
    var exceededFields = 0;
    $('.saveLimitErr').remove();
    $("textarea:not(.nolimit)").each(function() {
        var textareaCount = $(this).val().length;
        if(textareaCount > 4000 && !$(this).hasClass("allow-exceed-limit")) {
            exceededFields++;
            if(permitSave) {
                permitSave = false;
            }
        }
    });       
    $("input:not(.nolimit)").each(function() {
        var type;
        $(this).attr("type") == undefined ? type = "text" : type = $(this).attr("type") ;
        var inputCount = $(this).val().length;
        if(inputCount > 255 && type!='hidden' && !$(this).hasClass("allow-exceed-limit")) {
            exceededFields++;
            if(permitSave) {
                permitSave = false;
            }
        }
    });
    if(!permitSave){
        $(".buttons .save").prop("disabled", true);
        $(".button.save").prop("disabled", true);
        $(".modulecombination .button").prop("disabled", true);
        $("input.show").prop("disabled", true);
        $(".ui-dialog-buttonpane .ui-button").prop("disabled", true);
        $(".ui-dialog-buttonpane .ui-button").addClass("disabledsave");
        $(".buttons .save").addClass("disabledsave");
        $(".button.save").addClass("disabledsave");
        $(".modulecombination .button").addClass("disabledsave");
        $("input.show").addClass("disabledsave");
        if(exceededFields === 1){
            $('<div class="saveLimitErr redtext">1 field has exceeded its character limit. Please correct to save.</div>').insertBefore(".buttons");
        } else {
            $('<div class="saveLimitErr redtext">' + exceededFields + ' fields have exceeded their character limit. Please correct to save.</div>').insertBefore(".buttons");
        }
    }
    if(permitSave){
        $(".buttons .save").prop("disabled", false);
        $(".button.save").prop("disabled", false);
        $(".modulecombination .button").prop("disabled", false);
        $("input.show").prop("disabled", false);
        $(".ui-dialog-buttonpane .ui-button").prop("disabled", false);
        $(".ui-dialog-buttonpane .ui-button").removeClass("disabledsave");
        $(".buttons .save").removeClass("disabledsave");
        $(".button.save").removeClass("disabledsave");
        $(".modulecombination .button").removeClass("disabledsave");
        $("input.show").removeClass("disabledsave");
    }
}

function clearErrorDiv(){
    document.getElementById('ErrorDiv').innerHTML ="";
}

function addPrintLink(){
    if( $("body").hasClass("view") || $("body").hasClass("show") || $("body").hasClass("edit") || $("body").hasClass("create") || $("body").hasClass("display") || $("body").hasClass("report")){
        $("#navlist").append('<span class="list cprint">Print</span>');
        
        //The following part is inserted by umkis on 09/12/2013 for adding 'Audit Log trail' link to the 'navlist'
        var path = window.location.pathname;
        var separator = "-";
        while (path != null)
        {
            if ((sessionDM == null)|| (sessionDM == false) || (sessionOrg == null))
            {
                break;    
            }   
            if ((sessionDM)||(sessionAuthDM))//||(sessionAuthSuper)||(sessionAuthAdmin))
            {
                if ((sessionOrg == "OBBR")||(sessionOrg == "BBRB")) {}
                else  { break }
            }
            else
            {
                break;
            }
   
            var i = 0;
            var seq = 0;
            var service = '';
            var form = '';
            var action = '';
            var id = '';
            var buff = '';
            var paramet = ''
            var startParam = false;
            
            for(i=0;i<path.length;i++)
            {
                var achar = path.substring(i, i+1);
                
                if (achar == '/')
                {
                    if (i > 0) 
                    {
                        if (buff != '')
                        {
                            if (seq == 0) service = buff;
                            else if (seq == 1) form = buff;
                            else if (seq == 2) action = buff;
                            else if (seq == 3) id = buff;
                            
                            seq++; 
                        }
                    }
                    buff = '';
                }
                else 
                {
                    if (achar == '?')
                    {    
                        startParam = true;
                        if ((id == '')&&(buff != ''))
                        {
                            id = buff ;
                            buff = '';
                        }
                    }
                    else
                    {
                        buff = buff + achar;
                    }
                }
            }
            
            if (buff != '')
            {
                if (startParam == true) paramet = buff;
                else if (id == '') id = buff;   
            }
            
            if (seq < 0) break;
            
            if (service.toLowerCase() != 'cahubdataservices') break;
            
            if (action.toLowerCase().indexOf('view') == 0) {}
            else if (action.toLowerCase().indexOf('edit') >= 0) {}
            else if (action.toLowerCase() == 'display') {}
            else if (action.toLowerCase().indexOf('show') >= 0) {}
            else if ((form.toLowerCase() == 'caseqmsignature')&&(action.toLowerCase() == 'sign')) {}
            else break;
                      
            var valueF = parseInt(id)
            if ((id == '')||(isNaN(valueF)))
            {
                break;
            }
            
            var url = '../../trailAuditLog/trailLog/' + form + separator + action + separator + id;
            if (paramet != '') url = url + separator + paramet;
            $("#navlist").append('<a class="list" href="'+url+'">View Audit Trail</a>');   
            
            break;
        }
    }
}

function activateExpandIssue() {
    $(".query-more").click(function(){ 
        var queryId = $(this).attr("id").split("_")[1];
        expandIssue(queryId);
    });
}

function activateCollapseIssue() {
    $(".query-less").click(function(){ 
        var queryId = $(this).attr("id").split("_")[1];
        collapseIssue(queryId);
    });
}
 
function createDependentElements(dependentElem){
    var initiatorElem;
    if(dependentElem.data("id") != undefined && dependentElem.data("id").length > 0 ) {
        var initiatorElementsArr = dependentElem.data("id").split(",");
        var setToShow = false;
        for (var i = 0; i < initiatorElementsArr.length; i++) {
            if(setToShow == false){
                initiatorElem = $("#"+initiatorElementsArr[i]);
                if( initiatorElem.prop('checked') || dependentElem.data("select") == initiatorElem.attr("value")) {
                    dependentElem.show();
                    setToShow = true;
                } else {
                    dependentElem.hide();
                }
            }
        }

        for (var j = 0; j < initiatorElementsArr.length; j++) {
            initiatorElem = $("#"+initiatorElementsArr[j]);
            $(initiatorElem).change( function(){
                if($(this).prop('checked') || dependentElem.data("select") == initiatorElem.attr("value")) {
                    dependentElem.show();
                    setToShow = true;
                    dependentElem.children(".dependent-focus").focus();
                } else {
                    //Should change this at some point to do a for loop with the array
                    //then check if any checkboxes checked are in dependentElem.data("id")
                    //if exists show else hide. Until the fix code will only work with one
                    //check box id
                    dependentElem.find(".dependent-clear").val("");
                    dependentElem.hide();
                }
            });
        }
            
        for (var k = 0; k < initiatorElementsArr.length; k++) {
            initiatorElem = $("#"+initiatorElementsArr[k]);
            if (initiatorElem.length > 0 && !(initiatorElem.hasClass("othertrigger") && initiatorElem.attr('type') != undefined && initiatorElem.attr('type').toLowerCase() === "checkbox")) {
                $("input[name='" + initiatorElem.attr('name') + "']").change(function(){
                    var currentId = $("input[name=" + initiatorElem.attr('name') + "]:checked").attr("id");
                    var idCompareStr = ","+dependentElem.data("id")+",";
                    if(idCompareStr.indexOf(","+currentId+",") !== -1) {
                        dependentElem.show();
                        dependentElem.children(".dependent-focus").focus();
                        if(dependentElem.hasClass("dependent-focus")) {
                            dependentElem.focus();
                        }
                    } else {
                        dependentElem.children(".dependent-clear").val("");
                        if(dependentElem.hasClass("dependent-clear")) {
                            dependentElem.val("");
                        }
                        dependentElem.hide();
                    }
                });
            }
        }
    }
}

function expandIssue(queryId) {
    var desc = $("#queryDesc_" + queryId).html();
    var message = "Issue " + queryId + ": " + desc + "&nbsp;";
    var less = "<span id=\"query-less_" + queryId + "\" class=\"query-less\">less</span>";
    $("#queryWarning_" + queryId).replaceWith("<div id=\"queryWarning_" + queryId + "\" class=\"warning\">" + message + less + "</div>");
    activateCollapseIssue();
}

function collapseIssue(queryId) {
    var desc = $("#queryDesc_" + queryId).text().substring(0, 100);
    var message = "Issue " + queryId + ": " + desc + "&nbsp;&hellip;&nbsp;";
    var more = "<span id=\"query-more_" + queryId + "\" class=\"query-more\">more</span>";
    $("#queryWarning_" + queryId).replaceWith("<div id=\"queryWarning_" + queryId + "\" class=\"warning\">" + message + more + "</div>");
    activateExpandIssue();
}

var type = (function(global) {
    var cache = {};
    return function(obj) {
        var key;
        return obj === null ? 'null' // null
        : obj === global ? 'global' // window in browser or global in nodejs
        : (key = typeof obj) !== 'object' ? key // basic: string, boolean, number, undefined, function
        : obj.nodeType ? 'object' // DOM element
        : cache[key = ({}).toString.call(obj)] // cached. date, regexp, error, object, array, math
        || (cache[key] = key.slice(8, -1).toLowerCase()); // get XXXX from [object XXXX], and cache it
    };
}(this));
 
//plugin to remove an option from select
//Example: $("#ctId").option("SST").remove();
(function($) {
    $.fn.option=function(value) {
        return this.children().filter(function(index, option) {
            return option.value===value;
        });
    };
})(jQuery);

// For IE8 and earlier version.
if (!Date.now) {
    Date.now = function() {
        return new Date().valueOf();
    }
}

$.urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results[1] || 0;
}

jQuery.fn.AllowNumericOnly = function () {

    return this.each(function () {
        $(this).keydown(function (e) {
            var key = e.which || e.keyCode;

            if (!e.shiftKey && !e.altKey && !e.ctrlKey &&
                // numbers   
                key >= 48 && key <= 57 ||
                // Numeric keypad
                key >= 96 && key <= 105 ||
                // comma, period and minus, . on keypad
                //key == 190 || key == 188 || key == 109 || key == 110 ||
                // Backspace and Tab and Enter
                key == 8 || key == 9 || key == 13 ||
                // Home and End
                key == 35 || key == 36 ||
                // left and right arrows
                key == 37 || key == 39 ||
                // Del and Ins
                key == 46 || key == 45)
                return true;

            return false;
        });
    });
}

jQuery.fn.AllowNumericOnlyWithDecimal = function () {

    return this.each(function () {
        $(this).keydown(function (e) {
            var key = e.which || e.keyCode;

            if (!e.shiftKey && !e.altKey && !e.ctrlKey &&
                // numbers   
                key >= 48 && key <= 57 ||
                // Numeric keypad
                key >= 96 && key <= 105 ||
                // period
                (key == 190 || key == 110) && !($(this).val().indexOf(".") > -1) ||
                // Backspace and Tab and Enter
                key == 8 || key == 9 || key == 13 ||
                // Home and End
                key == 35 || key == 36 ||
                // left and right arrows
                key == 37 || key == 39 ||
                // Del and Ins
                key == 46 || key == 45)
                return true;

            return false;
        });
    });
}

jQuery.fn.AllowNegNumericWithDecimal = function () {

    return this.each(function () {
        $(this).keydown(function (e) {
            var key = e.which || e.keyCode;

            if (!e.shiftKey && !e.altKey && !e.ctrlKey &&
                // numbers   
                key >= 48 && key <= 57 ||
                // Numeric keypad
                key >= 96 && key <= 105 ||
                // period
                (key == 190 || key == 110) && !($(this).val().indexOf(".") > -1) ||
                // minus        
                key == 109 ||key == 189 || key == 173 ||                         
                // Backspace and Tab and Enter
                key == 8 || key == 9 || key == 13 ||
                // Home and End
                key == 35 || key == 36 ||
                // left and right arrows
                key == 37 || key == 39 ||
                // Del and Ins
                key == 46 || key == 45)
                return true;

            return false;
        });
    });
}

jQuery.fn.AllowNegativeNumeric = function () {

    return this.each(function () {
        $(this).keydown(function (e) {
            var key = e.which || e.keyCode;

            if (!e.shiftKey && !e.altKey && !e.ctrlKey &&
                // numbers   
                key >= 48 && key <= 57 ||
                // Numeric keypad
                key >= 96 && key <= 105 ||
                // comma, period and minus, . on keypad
                 //key == 190 || key == 188 || key == 109 || key == 110 ||
                // minus
                key == 109 ||key == 189 || key == 173 || 
                // Backspace and Tab and Enter
                key == 8 || key == 9 || key == 13 ||
                // Home and End
                key == 35 || key == 36 ||
                // left and right arrows
                key == 37 || key == 39 ||
                // Del and Ins
                key == 46 || key == 45)
                return true;

            return false;
        });
    });
}

function disableBackSpaceNav() {
    if( $("body").hasClass("edit") ) {
        // Prevent the backspace key from navigating back.
        $(document).unbind('keydown').bind('keydown', function (event) {
            var doPrevent = false;
            if (event.keyCode === 8) {
                var d = event.srcElement || event.target;
                if ((d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT' || d.type.toUpperCase() === 'PASSWORD' || d.type.toUpperCase() === 'FILE')) 
                     || d.tagName.toUpperCase() === 'TEXTAREA') {
                    doPrevent = d.readOnly || d.disabled;
                }
                else {
                    doPrevent = true;
                }
            }

            if (doPrevent) {
                event.preventDefault();
            }
        });
    }
}

// inserted by umkis on 03/06/2013
function getValidTimeFormatData(time1) 
{
    if (time1 == null) time1 = ''
    else time1 = time1.replace(/^\s+|\s+$/g,'');

    if (time1 == '') return ''

    if (time1.indexOf(':', 0) <= 0) return ''

    var val = ''
    var i
    for (i=0;i<2;i++)
    {    
        var timeS = time1.split(':')[i]
        timeS = timeS.replace(/^\s+|\s+$/g,'')
        if (timeS == '') return ''

        while(timeS.substring(0, 1) == '0')
        {
            if (timeS.length == 1) break
            timeS = timeS.substring(1)    
        }

        var timeI = parseInt(timeS)

        if (isNaN(timeI)) return ''

        if (timeI < 0) return ''
        if ((i == 0)&&(timeI > 23)) return ''
        if ((i == 1)&&(timeI > 59)) return ''

        if (timeI < 10) val = val + '0' + timeI
        else val = val + timeI

        if (i == 0) val = val + ':'
    }
    return val
}
// inserted by umkis on 03/06/2013
function displayWarningForNumberValue(id, lowValue, highValue, fieldName) 
{
    if (id == null) id = ''
    else id = id.replace(/^\s+|\s+$/g,'')
    if (id == '') return
    
    var value = document.getElementById(id).value
    
    if (value == null) value = ''
    else value = value.replace(/^\s+|\s+$/g,'')
    if (value == '') return
            
    if (fieldName == null) fieldName = ''
    else fieldName = fieldName.replace(/^\s+|\s+$/g,'')
    if (fieldName == '') fieldName = id        
            
    var valueF = parseFloat(value)
    var confirmMessage = ''
    var resetFocus = false
    var valueS 
    if (isNaN(valueF))
    {
        alert(fieldName + ' value is not numeric. : ' + value)
        resetFocus = true
        valueS = ''
    }
    else
    {
        valueS = '' + valueF

        var lowValueF = parseFloat(lowValue)
        var highValueF = parseFloat(highValue)

        if (isNaN(lowValueF) && isNaN(highValueF))
        {
            
        }
        else if (isNaN(lowValueF)) 
        {
            if (valueF > highValueF)
            {
                confirmMessage = fieldName + ' value ('+valueF+') is greater than ' + highValueF + '.'
            }
        }
        else if (isNaN(highValueF)) 
        {
            if (valueF < lowValueF)
            {
                confirmMessage = fieldName + ' value ('+valueF+') is less than ' + lowValueF + '.'
            }
        }
        else 
        {
            if (lowValueF > highValueF)
            {
                var tempVar = highValueF
                highValueF = lowValueF
                lowValueF = tempVar
            }
            if ((highValueF == lowValueF)&&(valueF != highValueF))
            {
                confirmMessage = fieldName + ' value ('+valueF+') is not the same as ' + lowValueF + '.'
            }
            else if ((valueF < lowValueF)||(valueF > highValueF))
            {
                confirmMessage = fieldName + ' value ('+valueF+') is not in between ' + lowValueF + ' and ' + highValueF + '.' 
            }
        }
        
        if (confirmMessage.length > 0 )
        {
            var answer = confirm(confirmMessage + ' Are you sure?' )   
            
            if (answer != true)
            {
                valueS = ''
                resetFocus = true
            }
        }
    }
    document.getElementById(id).value = valueS
    if (resetFocus) document.getElementById(id).focus()
}

function createVocabDialog(vocid,title){
    var dialogHtml = "";
    $("#ca-dialog").dialog({
        title: title,
        autoOpen: false,
        modal: true,
        width:500
    });
    $.ajax({
     url: '/cahubdataservices/deathCircumstances/vocabdetails?vocid=' + vocid,
     success:function(data){
        var vocabRec = data.vocabRec;
        var typ = data.typ;
        var cuiList = data.cuiList;
        if (vocabRec != null){
            var cVocabUserSelection = vocabRec.cVocabUserSelection;
            var icdCd = vocabRec.icdCd;
            var pdqCd = vocabRec.pdqCd;
            var srcDef = vocabRec.srcDef;
            var genName = vocabRec.genName;
            dialogHtml += "<h3>Selected Result:</h3>";
            dialogHtml += "<ul>";
            if(typ == "PCT"){
                dialogHtml += "<li><b>PCT:</b> " + cVocabUserSelection +"</li>";
            } else if(typ == "RX"){
                dialogHtml += "<li><b>TYP:</b> " + typ +"</li>";
                dialogHtml += "<li><b>RX:</b> " + cVocabUserSelection +"</li>";
            } else {
                dialogHtml += "<li><b>" + cVocabUserSelection +"</b></li>";
            }
            if( pdqCd != null && pdqCd.length > 0){
                dialogHtml += "<li><b>PDQcd:</b> " + pdqCd +"</li>";
            }
            if( cuiList != null && cuiList.length > 0){
                dialogHtml += "<li><b>CUI:</b> " + cuiList +"</li>";
            }
            if( icdCd != null && icdCd.length > 0){
                dialogHtml += "<li><b>ICDcd:</b> " + icdCd +"</li>";
            }
            dialogHtml += "</ul>";
            if( srcDef != null && srcDef.length > 0){
                dialogHtml += "<h3>Definition:</h3><div class=\"vocabdef\">" + srcDef +"</div>";
            } else if(genName != null && genName.length > 0){
                dialogHtml += "<h3>Generic Names:</h3><div class=\"vocabdef\">" + genName +"</div>";
            }
            
            $("#ca-dialog").html(dialogHtml);
            $("#ca-dialog a").attr("target","_blank");
            $("#ca-dialog").dialog("open");
        }
     }
    });   
}

//returns comma delimited values of a group of checkboxes
function getCheckBoxValues(checkbox){
    var checkedVals = "";
    var resultsArr = document.getElementsByName(checkbox);
    for(var i = 0; i < resultsArr.length; i++) {
        if( (resultsArr[i].checked ? 'checked' : 'unchecked') == "checked") {
            if(resultsArr[i].getAttribute("value").toLowerCase() === "other" && $("#" + resultsArr[i].getAttribute("id") + "_other").length > 0) {
                checkedVals += resultsArr[i].getAttribute("value") + ":" + $("#" + resultsArr[i].getAttribute("id") + "_other").val().replace(/,/g , "; ") + ",";
            } else {
                checkedVals += resultsArr[i].getAttribute("value") + ",";
            }
        }
    }
    return checkedVals.replace(/,+$/, "");
}

/*
 * Dropit v1.0
 * http://dev7studios.com/dropit
 *
 * Copyright 2012, Dev7studios
 * Free to use and abuse under the MIT license.
 * http://www.opensource.org/licenses/mit-license.php
 */

;
(function($) {

    $.fn.dropit = function(method) {

        var methods = {

            init : function(options) {
                this.dropit.settings = $.extend({}, this.dropit.defaults, options);
                return this.each(function() {
                    var $el = $(this),
                    el = this,
                    settings = $.fn.dropit.settings;
                    
                    // Hide initial submenus     
                    $el.addClass('dropit')
                    .find('>'+ settings.triggerParentEl +':has('+ settings.submenuEl +')').addClass('dropit-trigger')
                    .find(settings.submenuEl).addClass('dropit-submenu').hide();
                    
                    // Open on click
                    $el.on(settings.action, settings.triggerParentEl +':has('+ settings.submenuEl +') > '+ settings.triggerEl +'', function(){
                        if($(this).parents(settings.triggerParentEl).hasClass('dropit-open')) return false;
                        settings.beforeHide.call(this);
                        $('.dropit-open').removeClass('dropit-open').find('.dropit-submenu').hide();
                        settings.afterHide.call(this);
                        settings.beforeShow.call(this);
                        $(this).parents(settings.triggerParentEl).addClass('dropit-open').find(settings.submenuEl).show();
                        settings.afterShow.call(this);
                        return false;
                    });
                    
                    // Close if outside click
                    $(document).on('click', function(){
                        settings.beforeHide.call(this);
                        $('.dropit-open').removeClass('dropit-open').find('.dropit-submenu').hide();
                        settings.afterHide.call(this);
                    });
                    
                    settings.afterLoad.call(this);
                });
            }
            
        }

        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error( 'Method "' +  method + '" does not exist in dropit plugin!');
        }

    }

    $.fn.dropit.defaults = {
        action: 'click', // The open action for the trigger
        submenuEl: 'ul', // The submenu element
        triggerEl: 'a', // The trigger element
        triggerParentEl: 'li', // The trigger parent element
        afterLoad: function(){}, // Triggers when plugin has loaded
        beforeShow: function(){}, // Triggers before submenu is shown
        afterShow: function(){}, // Triggers after submenu is shown
        beforeHide: function(){}, // Triggers before submenu is hidden
        afterHide: function(){} // Triggers before submenu is hidden
    }

    $.fn.dropit.settings = {}

})(jQuery);

//helps post data without needing to but form on page.... i.e does it for you
function postData(actionUrl, method, data) {
    var mapForm = $('<form id="mapform" action="' + actionUrl + '" method="' + method.toLowerCase() + '"></form>');
    for (var key in data) {
        if (data.hasOwnProperty(key)) {
            mapForm.append('<input type="hidden" name="' + key + '" id="' + key + '" value="' + data[key] + '" />');
        }
    }
    $('body').append(mapForm);
    mapForm.submit();
}

function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}

function emptyGlobalDialogOnClose(){
    $("#ca-dialog").dialog({
        autoOpen: false,
        close: function(event, ui){
            $(this).html("");
            permissionToSave();
        }  
    });
}

function validateSpecimenRecordWithWarning (caseSpecimenPattern, specimenIDElem){
    if(!(specimenIDElem.html()).match(caseSpecimenPattern)){
        if(specimenIDElem.prop("tagName")=="A"){
            specimenIDElem.parent().addClass("warnings");
        } else {
            specimenIDElem.addClass("warnings");
        }
    }
}

String.prototype.toProperCase = function () {
    return this.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
};