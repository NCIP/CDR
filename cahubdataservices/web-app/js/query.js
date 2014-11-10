/* Please do not check in commented out code like alert(flags), partial code etc. */

function adjustFormList() {
    $.ajax({
        type: 'GET',
        dataType: 'text', 
        url: '/cahubdataservices/query/getStudyCodeForFormList?caseId=' + $("#caseId").val() + (!isNaN($("#organization\\.id").val()) ? '&organization.id=' + $("#organization\\.id").val() : '')
    }).done(function(data) {
        if (data == 'GTEX') {
            $(".gtexFormCheckBox").show();
            $(".gtexCandidateFormCheckBox").hide();
            $(".gtexCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bmsFormCheckBox").hide();
            $(".bmsFormCheckBox input:checkbox").attr('checked', false);
            $(".bpvCandidateFormCheckBox").hide();
            $(".bpvCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bpvFormCheckBox").hide();
            $(".bpvFormCheckBox input:checkbox").attr('checked', false);
        } else if (data == 'BMS') {
            $(".gtexFormCheckBox").hide();
            $(".gtexFormCheckBox input:checkbox").attr('checked', false);
            $(".gtexCandidateFormCheckBox").hide();
            $(".gtexCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bmsFormCheckBox").show();
            $(".bpvCandidateFormCheckBox").hide();
            $(".bpvCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bpvFormCheckBox").hide();
            $(".bpvFormCheckBox input:checkbox").attr('checked', false);
        } else if (data == 'BPV') {
            $(".gtexFormCheckBox").hide();
            $(".gtexFormCheckBox input:checkbox").attr('checked', false);
            $(".gtexCandidateFormCheckBox").hide();
            $(".gtexCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bmsFormCheckBox").hide();
            $(".bmsFormCheckBox input:checkbox").attr('checked', false);
            $(".bpvCandidateFormCheckBox").show();
            $(".bpvFormCheckBox").show();
        } else if (data == 'BRN') {
            hideFormList();
        } else if (data == 'NOCASE') {
            hideFormList();
            $("#noCaseText").text("This Case does not exist");
        } else if (data == 'HIDE') {
            hideFormList();
        }
    });
}

function adjustCandidateFormList() {
    $.ajax({
        type: 'GET',
        dataType: 'text', 
        url: '/cahubdataservices/query/getStudyCodeForCandidateFormList?candidateId=' + $("#candidateId").val() + (!isNaN($("#organization\\.id").val()) ? '&organization.id=' + $("#organization\\.id").val() : '')
    }).done(function(data) {
        if (data == 'GTEX') {
            $(".gtexFormCheckBox").hide();
            $(".gtexFormCheckBox input:checkbox").attr('checked', false);
            $(".gtexCandidateFormCheckBox").show();
            $(".bmsFormCheckBox").hide();
            $(".bmsFormCheckBox input:checkbox").attr('checked', false);
            $(".bpvCandidateFormCheckBox").hide();
            $(".bpvCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bpvFormCheckBox").hide();
            $(".bpvFormCheckBox input:checkbox").attr('checked', false);
        } else if (data == 'BMS') {
            $(".gtexFormCheckBox").hide();
            $(".gtexFormCheckBox input:checkbox").attr('checked', false);
            $(".gtexCandidateFormCheckBox").hide();
            $(".gtexCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bmsFormCheckBox").show();
            $(".bpvCandidateFormCheckBox").hide();
            $(".bpvCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bpvFormCheckBox").hide();
            $(".bpvFormCheckBox input:checkbox").attr('checked', false);
        } else if (data == 'BPV') {
            $(".gtexFormCheckBox").hide();
            $(".gtexFormCheckBox input:checkbox").attr('checked', false);
            $(".gtexCandidateFormCheckBox").hide();
            $(".gtexCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bmsFormCheckBox").hide();
            $(".bmsFormCheckBox input:checkbox").attr('checked', false);
            $(".bpvCandidateFormCheckBox").show();
            $(".bpvFormCheckBox").hide();
            $(".bpvFormCheckBox input:checkbox").attr('checked', false);
        } else if (data == 'BRN') {
            hideFormList();
        } else if (data == 'NOCANDIDATE') {
            hideFormList();
            $("#noCandidateText").text("This Candidate does not exist");
        } else if (data == 'HIDE') {
            hideFormList();
        }
    });
}

function adjustInterviewFormList() {
    $.ajax({
        type: 'GET',
        dataType: 'text', 
        url: '/cahubdataservices/query/getStudyCodeForInterviewFormList?interviewId=' + $("#interviewId").val()
    }).done(function(data) {
        if (data == 'BPVELSI') {
            $(".gtexFormCheckBox").hide();
            $(".gtexFormCheckBox input:checkbox").attr('checked', false);
            $(".gtexCandidateFormCheckBox").hide();
            $(".gtexCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bmsFormCheckBox").hide();
            $(".bmsFormCheckBox input:checkbox").attr('checked', false);
            $(".bpvCandidateFormCheckBox").hide();
            $(".bpvCandidateFormCheckBox input:checkbox").attr('checked', false);
            $(".bpvFormCheckBox").hide();
            $(".bpvFormCheckBox input:checkbox").attr('checked', false);
            $(".elsiFormCheckBox").show();
        } else if (data == 'NOCANDIDATE') {
            hideFormList();
            $("#noInterviewText").text("This Interview does not exist");
        }
    });
}

function hideFormList() {
    $(".gtexFormCheckBox").hide();
    $(".gtexFormCheckBox input:checkbox").attr('checked', false);
    $(".gtexCandidateFormCheckBox").hide();
    $(".gtexCandidateFormCheckBox input:checkbox").attr('checked', false);
    $(".bmsFormCheckBox").hide();
    $(".bmsFormCheckBox input:checkbox").attr('checked', false);
    $(".bpvCandidateFormCheckBox").hide();
    $(".bpvCandidateFormCheckBox input:checkbox").attr('checked', false);
    $(".bpvFormCheckBox").hide();
    $(".bpvFormCheckBox input:checkbox").attr('checked', false);
    $(".elsiFormCheckBox").hide();
    $(".elsiFormCheckBox input:checkbox").attr('checked', false);
}

$(document).ready(function() {
    $("#organization\\.id").change(function() {
        if ($("#caseId").val().length) {
            adjustFormList();
        }
    });
    
    $("#caseId").change(function() {
        $("#noCaseText").text("");
        if ($("#caseId").val().length) {
            adjustFormList();
        } else {
            hideFormList();
        }
    });
    
    $("#candidateId").change(function() {
        $("#noCandidateText").text("");
        if ($("#candidateId").val().length) {
            adjustCandidateFormList();
        } else {
            hideFormList();
        }
    });
    
    $("#interviewId").change(function() {
        $("#noInterviewText").text("");
        if ($("#interviewId").val().length) {
            adjustInterviewFormList();
        } else {
            hideFormList();
        }
    });
    
    $("#association_case").change(function() {
       $("#caseRow").show();
       $("#candidateRow").hide();
       $("#candidateId").val("");
       $("#noCandidateText").text("");
       $("#interviewRow").hide();
       $("#interviewId").val("");
       $("#noInterviewText").text("");
       $("#otherRow").hide();
       $("#other").val("");
    });
    
    $("#association_candidate").change(function() {
       $("#candidateRow").show();
       $("#caseRow").hide();
       $("#caseId").val("");
       $("#noCaseText").text("");
       $("#interviewRow").hide();
       $("#interviewId").val("");
       $("#noInterviewText").text("");
       $("#otherRow").hide();
       $("#other").val("");
    });
    
    $("#association_interview").change(function() {
       $("#interviewRow").show();
       $("#caseRow").hide();
       $("#caseId").val("");
       $("#noCaseText").text("");
       $("#candidateRow").hide();
       $("#candidateId").val("");
       $("#noCandidateText").text("");
       $("#otherRow").hide();
       $("#other").val("");
    });
    
    $("#association_other").change(function() {
       $("#otherRow").show();
       $("#caseRow").hide();
       $("#caseId").val("");
       $("#noCaseText").text("");
       $("#candidateRow").hide();
       $("#candidateId").val("");
       $("#noCandidateText").text("");
       $("#interviewRow").hide();
       $("#interviewId").val("");
       $("#noInterviewText").text("");
    });
    
    $("#isDcf_yes").change(function() {
        $("#queryType\\.id").val(2);
    });
    
    $("#isDcf_no").change(function() {
        $("#dcfId").val("");
        $("#jira").val("");
    });
    
    $("#isPr2_yes").change(function() {
        $("#organization\\.id option:contains('VARI')").prop('selected', true);
        if ($("#caseId").val().length) {
            adjustFormList();
        }
    });
    
    $("#isPr2_no").change(function() {
        $("#pr2Id").val("");
        $("#pr2Jira").val("");
        $("#pr2Dcf").val("");
    });
    
    $("#activateBtn").click(function() {
        return confirm("Are you sure you want to activate this Query?");
    });
    
    $("#addressBtn").click(function() {
        return confirm("Are you sure you want to mark this Query as Addressed?");
    });
    
    $("#resolveBtn").click(function() {
        return confirm("Are you sure you want to mark this Query as Resolved?");
    });
    
     $("#unresolveBtn").click(function() {
        return confirm("Are you sure you want to mark this Query as Unresolved?");
    });
    
    $("#reactivateBtn").click(function() {
        return confirm("Are you sure you want to reactivate this Query?");
    });
    
    $("#closeBtn").click(function() {
        return confirm("Are you sure you want to close this Query?");
    });
    
    $("#addResponseBtn").button().click(function() {
        $("#responseDialog").dialog("open");
        $("#response").val("");
    });

    $("#saveResponseBtn").click(function() {
        $("#responseDialog").dialog("close");
    });
    
    $("#cancelResponseBtn").click(function() {
        $("#responseDialog").dialog("close");
    });

    $("#responseDialog").dialog({
        autoOpen: false,
        height: 150,
        width: 930,
        modal: true,
        buttons: {},
        close: function() {}
    });
    
    $("#overDue").change(function() {
        if ($("#overDue").val() != "") {
            $("#status").val("Active");
        }
    });
    
    $('.dueDateHoverWrap').hover(function () {
        var queryId = $(this).attr("id").split("_")[1];
        $("#dueDateEdit_" + queryId).show();
    },function () {
        var queryId = $(this).attr("id").split("_")[1];
        $("#dueDateEdit_" + queryId).hide();
    });
    
    $(".dueDateEdit").click(function() {
        var queryId = $(this).attr("id").split("_")[1];
        $("#dueDatePicker_" + queryId).show();
        $("#dueDateHoverWrap_" + queryId).hide();
        $("#dueDate_" + queryId).focus();
    });
    
    $(".dateField").blur(function() {
        var queryId = $(this).attr("id").split("_")[1];
        $("#dueDatePicker_" + queryId).hide();
        $("#dueDateHoverWrap_" + queryId).show();
    });
    
    $(".dueDatePicker .datePickerChangeTime").change(function() {
        var queryId = $(this).attr("id").split("_")[1];
        var newDay = $("#dueDate_" + queryId + "_day").val();
        var newMonth = $("#dueDate_" + queryId + "_month").val();
        var newYear = $("#dueDate_" + queryId + "_year").val();
        $.ajax({
            type: 'POST',
            dataType: 'text', 
            url: '/cahubdataservices/query/updateDueDate?queryId=' + queryId + '&newDay=' + newDay + '&newMonth=' + newMonth + '&newYear=' + newYear
        }).done(function(data) {
            if (data != "ERROR") {
                $("#dueDateDisplay_" + queryId).text(data)
            }
        });
    });
});
