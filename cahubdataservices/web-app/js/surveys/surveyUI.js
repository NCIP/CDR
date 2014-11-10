var notAnswered = new Array();
var answered = new Array();
var IDK = new Array();
var partsListHtml = "";
var survey;
var resubmitCounter;
var accessDeniedHTML = "<div class=\"errors\"><ul><li>Sorry, you're not authorized to view this page or perform this operation. Redirecting to your home page in 5 seconds...</li></ul>";
var notFoundHTML = "<div class=\"errors\"><ul><li>Interview Not Found. Redirecting to the available Interview Record List in 5 seconds...</li></ul>";


$(document).ready(function() {
    clearInterval("fetchActivities()");
    getSurvey("/cahubdataservices/surveyRecord/getSurvey/"+recordid + "?pagetype=" + pagetype + "&callback=?");
});

function getSurvey(src) {
    $.ajax({
        type: 'GET',
        dataType: 'jsonp', 
        url: src
     }).done(function(data) {
        if(data.survey){
            survey = data.survey;
            createSurvey("survey");
        } else if(data.status) {
               if (data.status === "denied") { 
                    $("#survey").html(accessDeniedHTML);
                    setTimeout("window.location.href='/cahubdataservices';",5000);
               } else if (data.status === "not found") {
                    $("#survey").html(notFoundHTML);
                    setTimeout("window.location.href='/cahubdataservices/interviewRecord/list';",5000);
               }
        }
     });
}

function createSurvey(surveypage){
    var surveyHTML = "";
    $("body").addClass(pagetype.replace("show","view"));
    if(survey.title) {
        $("h1").html(survey.title);
        $("h1").show();
    }
    
    if(survey.resume_editing && survey.date_submitted.length > 0) {
        $("#survey").addClass("resumeEdit");
    }
    
    surveyHTML += "<table id=\"details\"><tbody><tr class= \"prop \"><td colspan= \"8 \"><h2>Interview Details</h2></td></tr><tr class= \"prop \"><td><div class= \"clearfix \"><dl class= \"formdetails left \"><dt>Interview ID:</dt><dd><a href= \"/cahubdataservices/interviewRecord/show/" + survey.interview_record_id + "\">" + survey.interview_id + "</a></dd></dl><dl class= \"formdetails left \"></dl><dl class= \"formdetails left \"><dt>Organization:</dt><dd>" + survey.organization+ "</dd></dl><dl class= \"formdetails left \"><dt>Survey Type:</dt><dd>" + survey.typ+ "</dd></dl><dl class= \"formdetails left \"><dt>Interview Status:</dt><dd>" + survey.interview_status+ "</dd></dl></div></td></tr></tbody></table>"
    
    if(survey.desc) {
        surveyHTML += "<div id=\"survey-desc\">" + survey.desc + "</div>";
    }
    if(survey.sections.length > 0) {
        var tabsList = "";
        var tabsContent = "";
        surveyHTML += "<div id=\"survey-tabs\">";
        tabsList += "<ul id=\"tabs-list\" class=\"tab-list\">";
        for (var i=0; i < survey.sections.length; i++){
            tabsList += "<li><a href=\"#survey-tabs-" + i + "\">" + survey.sect_name + " " + getOrd(i, survey.sect_numbering) + "</a></li>";
            partsListHtml += "<li class=\"jump-to\" data-goto=\"survey-tabs-" + i + "\"><a href=\"#\">" + survey.sect_name + " " + getOrd(i, survey.sect_numbering) + "</a></li>"
            tabsContent += createSurveySection(i, survey.sect_name.toUpperCase() + " " + getOrd(i, survey.sect_numbering), survey.sections[i], i);
        }
        tabsList += "<input class=\"button save ui-button ui-widget ui-state-default ui-corner-all sendSurvey right\" readonly=readonly /><div class=\"clear\"></div></ul>";
        surveyHTML += tabsList;
        surveyHTML += tabsContent;
        surveyHTML += "</div><div class=\"clear\"></div><div class=\"sendtotop\"><a href=\"javascript:void(0)\" class=\"back-to-top\">Back to Top</a></div>";
    }
    $("#"+surveypage).html(surveyHTML);
    markAllNotAnswered();
    $( "#footer" ).show();
    
    if(pagetype == "edit") {
        $( "#survey-tabs" ).tabs();
        $( ".sendSurvey" ).val("Submit");
    } else {
        $( "#survey-tabs" ).addClass("ui-tabs ui-widget ui-widget-content ui-corner-all");
        $( "#survey-tabs ul" ).addClass("tab-list ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all");
        $( ".sendSurvey" ).val("Resume");
    }

    $(".view .dropdown").each(function () {
        var dropdownspace = $(this).width() + 28;
        $(this).html("<ul class=\"dropit\"><li class=\"dropit-trigger dropit-close\"><a class=\"dropit-triggerlbl clearfix\" href=\"#\"><div class=\"ui-state-default left\"><span class=\"ui-icon ui-icon-circle-triangle-s\"></span></div>" + $(this).html() +"</a><ul class=\"dropit-submenu\">" + partsListHtml.replace(">" + capitalize($(this).html().toLowerCase()), " class=\"selected\">" + capitalize($(this).html().toLowerCase())) + "</ul></li></ul>");
        $(this).parent().children(".sectionTitle").css("margin-left", dropdownspace + "px");
    });

    addPrintLink();
    applyGlobalRules();
    createSurveyInteractions();
}

function createSurveySection(sectOrd, sectName, surveySection, jsonSectIndex) {
    var sectionHTML = "";
    sectionHTML += "<div id=\"survey-tabs-" + sectOrd + "\" class=\"survey-divs\">";
    sectionHTML += "<h3><span class=\"dropdown\">" + sectName + "</span> <span class=\"sectionTitle\">- " + surveySection.title + "</span></h3>";
    if(surveySection.desc) {
        sectionHTML += "<div class=\"section-desc\">" + surveySection.desc + "</div>";
    }
    for (var i=0; i < surveySection.questions.length; i++){
        sectionHTML += createSurveyQuestion(surveySection.questions_name + getOrd(i, surveySection.questions_numbering) , surveySection.questions[i] , i, jsonSectIndex);
    }
    sectionHTML += "</div>";
    return sectionHTML;
}

function getSurveyQuestionAnswer(surveyQuestion) {
    var quesAnswer = "";
    if(surveyQuestion.answer != undefined) {
        if(type(surveyQuestion.answer) == "object" ) {
            quesAnswer = surveyQuestion.answer.response;
        }
    }
    if(quesAnswer === "-1" && jQuery.inArray("quest" + surveyQuestion.id, notAnswered) == -1) {
        notAnswered.push("quest" + surveyQuestion.id);
    }
    if(quesAnswer === "idk" && jQuery.inArray("quest" + surveyQuestion.id, IDK) == -1) { 
        IDK.push("quest" + surveyQuestion.id);
    }
    
    if( quesAnswer.length > 0 && jQuery.inArray("quest" + surveyQuestion.id, answered) == -1) {
        answered.push("quest" + surveyQuestion.id);
    }
    
    if(quesAnswer === "idk" || quesAnswer === "-1"){
        return "";
    } else {
        return quesAnswer;
    }
}  

function checkAllAnsweredForParent(parentid) {
   var parent = $("#" + parentid);
   if((parent.data("sectinjson") != undefined && $("#" + parentid).hasClass("questionDivtabled")) && $("#" + parentid + " .answered-question").length == survey.sections[parent.data("sectinjson")].questions[parent.data("quesinjson")].options_questions.length) {
        parent.removeClass("unanswered-question");
        parent.addClass("answered-question");
   }
}

function markAllNotAnswered() {
    for(var i=0; i<notAnswered.length; i++) {
        $("#"+notAnswered[i].replace("quest","nalbl")).html("Chosen not to answer.");
        $("#"+notAnswered[i] + " .noAnswer").hide();
        $("#"+notAnswered[i] + " .notanswered").show();
            
        var question = $("#"+notAnswered[i]);
        if(question.data("sectinjson") !== "sub" && question.data("sectinjson") != undefined ) {
            var subquestions = survey.sections[question.data("sectinjson")].questions[question.data("quesinjson")].questions;
            if ( subquestions != undefined ) {
                for (var x = 0; x < subquestions.length; x++) {
                    $('input:radio[name=quest' + subquestions[x].id + '_scale]').prop('disabled', true);
                }
            }
        }
    }
    for(var i=0; i<IDK.length; i++) {
        $("#"+IDK[i].replace("quest","nalbl")).html("IDK.");
        $("#"+IDK[i] + " .noAnswer").hide();
        $("#"+IDK[i] + " .notanswered").show();
            
        var question = $("#"+IDK[i]);
        if(question.data("sectinjson") !== "sub" && question.data("sectinjson") != undefined ) {
            var subquestions = survey.sections[question.data("sectinjson")].questions[question.data("quesinjson")].questions;
            if ( subquestions != undefined ) {
                for (var y = 0; y < subquestions.length; y++) {
                    $('input:radio[name=quest' + subquestions[y].id + '_scale]').prop('disabled', true);
                }
            }
        }
    }
    for(var i=0; i<answered.length; i++) {
        $("#"+answered[i]).removeClass("unanswered-question");
        $("#"+answered[i]).addClass("answered-question");
        if($("#"+answered[i]).data("parentid") != null && $("#"+answered[i]).data("parentid").length > 0) {
           var parentid = $("#"+answered[i]).data("parentid");
           checkAllAnsweredForParent(parentid);
        }
    }
}

function markNotAnswered( questionid, answer ) {
    if( answer === "-1") {
        $("#nalbl"+questionid).html("Chosen not to answer.");
    } else if ( answer === "idk") {
        $("#nalbl"+questionid).html("IDK.");
    }
    $("#quest"+questionid + " .noAnswer").hide();
    $("#quest"+questionid + " .notanswered").show();
    $("#quest"+questionid + " .surveyTextQues").attr("value","");
    $("#quest"+questionid + " .surveyFieldQues").attr("value","");
    $("#quest"+questionid + " .surveyRadioQues").each(function(){
        $(this).prop('checked', false);
    });
    $("#quest"+questionid + " .surveyCheckBoxQues").each(function(){
        $(this).prop('checked', false);
    });
}

function markAnswered( questionid ) {
    $("#nalbl" + questionid).html("");
    $("#quest" + questionid + " .notanswered").hide();
    $("#quest" + questionid + " .noAnswer").show();
}

function createSurveyQuestion(questionLabel, surveyQuestion, jsonQuesIndex, jsonSectIndex) {
    var questionsHTML = "";
    var quesAnswer = getSurveyQuestionAnswer(surveyQuestion);

    questionsHTML += "<div data-sectinjson=\"" + jsonSectIndex + "\" data-quesinjson=\"" + jsonQuesIndex + "\" id=\"quest" + surveyQuestion.id + "\" class=\"questionDiv questionDiv" + questionLabel + " questionDiv" + surveyQuestion.typ + " " + surveyQuestion.ui_class + " unanswered-question\">";
    questionsHTML += "<div class=\"questitle\"><span class=\"questionlbl left\">" + questionLabel + (questionLabel.length > 0 ? ". ":"") + surveyQuestion.title;
    if(surveyQuestion.typ != "empty" && surveyQuestion.typ.length > 0 && surveyQuestion.typ.indexOf("tabled") == -1){
        questionsHTML += "</span> <span id=\"nalbl" + surveyQuestion.id + "\" class=\"notanswered right\"></span><div class=\"answerBtns right\"><input id=\"idkbuttn" + surveyQuestion.id + "\"  data-questionid=\"" + surveyQuestion.id + "\" class=\"button ui-button ui-widget ui-state-default ui-corner-all noAnswer left\" data-answer=\"IDK\" value=\"IDK\" readonly=readonly />";
        questionsHTML += "<input id=\"nabuttn" + surveyQuestion.id + "\"  data-questionid=\"" + surveyQuestion.id + "\" class=\"button ui-button ui-widget ui-state-default ui-corner-all noAnswer right\" data-answer=\"-1\" value=\"No Ans\" readonly=readonly /></div></div><div class=\"clear\"></div>";
    } else {
        questionsHTML += "</span></div><div class=\"clear\"></div>";
    }
    questionsHTML += "<div><div class=\"message\">" + surveyQuestion.tip + "</div></div>";
    if(surveyQuestion.typ == "field") {
        if(pagetype == "edit") {
            questionsHTML += "<div class=\"surveyFieldWrap\"><input id=\"surveyFieldQues" + surveyQuestion.id + "\"  class=\"successMrk surveyFieldQues\" data-questionid=\""+surveyQuestion.id+"\" value=\"" + getSurveyQuestionAnswer(surveyQuestion) + "\"/></div><div id =\"successMsg" + surveyQuestion.id + "\" class=\"successMsg right\">Answer will be saved when you leave the question</div><div class=\"clear\"></div>";
        } else {
            questionsHTML += "<div class=\"showValue\">" + getSurveyQuestionAnswer(surveyQuestion) + "</div>";
        }
    } else if(surveyQuestion.typ == "text") {
        if(pagetype == "edit") {
            questionsHTML += "<textarea id=\"surveyTextQues" + surveyQuestion.id + "\" rows=\"4\" cols=\"40\" class=\"textwide surveyTextQues successMrk\" data-questionid=\""+surveyQuestion.id+"\"/ >" + quesAnswer + "</textarea><div id =\"successMsg" + surveyQuestion.id + "\" class=\"successMsg right\">Answer will be saved when you leave the question</div><div class=\"clear\"></div>"; 
        } else {
            questionsHTML += "<div class=\"showValue\">" + getSurveyQuestionAnswer(surveyQuestion) + "</div>";
        }
    }
    if(surveyQuestion.typ == "checkbox") {
        var checkboxOptions = surveyQuestion.options;
        var checkboxStr = "";
        var otherVal = "";
        if(checkboxOptions.length > 0) {
            checkboxStr += "<div class=\"surveyCheckBoxGroup\">";
            if(pagetype == "edit") {
                if (getSurveyQuestionAnswer(surveyQuestion).indexOf("Other:") > -1) {
                   otherVal = " data-otherval = \"" + getSurveyQuestionAnswer(surveyQuestion).split("Other:")[1].split(",")[0] + "\" ";
                }
                for(var i = 0; i < checkboxOptions.length; i++) {
                    checkboxStr += "<input " + (getSurveyQuestionAnswer(surveyQuestion).indexOf("Other:") > -1?otherVal:"") + " " + (getSurveyQuestionAnswer(surveyQuestion).indexOf(checkboxOptions[i].title) > -1?"checked":"") + " id=\"checkbox_" + i + "_" + surveyQuestion.id + "\" class=\"surveyCheckBoxQues\" data-questionid=\"" + surveyQuestion.id + "\" type=\"checkbox\" value=\"" + checkboxOptions[i].title + "\" name=\"" + "quest" + surveyQuestion.id + "\" />&nbsp;<label for=\"checkbox_" + i + "_" + surveyQuestion.id + "\">" + checkboxOptions[i].title + "</label><br />";
                }
            } else {
                checkboxStr += getSurveyQuestionAnswer(surveyQuestion);
            }
            //remove trailing  ",  "
            checkboxStr = checkboxStr.replace(/^\s\s*/, '').replace(/\s\s*$/, '').replace(/,$/,'');
            checkboxStr = checkboxStr.replace("surveyCheckBoxGroup","showValue");
            checkboxStr += "</div>";
            questionsHTML += checkboxStr + "<div id =\"successMsg" + surveyQuestion.id + "\" class=\"successMsg right\">Answer will be saved when you make a selection</div><div class=\"clear\"></div>";
        }
    } else if(surveyQuestion.typ == "radio") {
        if(pagetype == "edit") {
            var radioOptions = surveyQuestion.options;
            var radioStr = "";
            var otherVal = "";
            var newInitVal ="";
            var initValArr = getSurveyQuestionAnswer(surveyQuestion).split(" _ ");
            var initVal = initValArr[0];
            var origInitVal = initVal;
            if(radioOptions.length > 0) {
                if (surveyQuestion.ui_class.indexOf("addpretextfield") > -1) {
                   otherVal = " data-otherval = \"" + initValArr[0] + "\" ";
                   newInitVal = initValArr[1];
                } else if (surveyQuestion.ui_class.indexOf("addposttextfield") > -1){
                   otherVal = " data-otherval = \"" + initValArr[1] + "\" ";
                   newInitVal = initValArr[0];
                }
                for(var i = 0; i < radioOptions.length; i++) {
                    if(surveyQuestion.ui_class.indexOf("textfieldbut" + i) > 0){
                        initVal = origInitVal;
                    } else if (surveyQuestion.ui_class.indexOf("addpretextfield") > -1 || surveyQuestion.ui_class.indexOf("addposttextfield") > -1){
                        initVal = newInitVal;
                    }
                    radioStr += "<input " + (getSurveyQuestionAnswer(surveyQuestion).indexOf(" _ ") > -1?otherVal:"") + " " + (initVal == radioOptions[i].title?"checked":"") +" class=\"surveyRadioQues\" data-radiopos = \"" + i + "\" id=\"radio_" + i + "_" + surveyQuestion.id + "\" data-questionid=\"" + surveyQuestion.id + "\" type=\"radio\" value=\"" + radioOptions[i].title + "\" name=\"" + "questr" + surveyQuestion.id + "\" />&nbsp;<label for=\"radio_" + i + "_" + surveyQuestion.id + "\">" + radioOptions[i].title + "</label><br />";
                }
                questionsHTML += radioStr + "<div id =\"successMsg" + surveyQuestion.id + "\" class=\"successMsg right\">Answer will be saved when you select an option</div><div class=\"clear\"></div>";
            }
        } else {
            questionsHTML += "<div class=\"showValue\">" + getSurveyQuestionAnswer(surveyQuestion).replace(" _ ", " ") + "</div>";
        }
    } else if(surveyQuestion.typ == "tabled radio") {
        var tableQuestions = surveyQuestion.options_questions;
        var radioOptions = surveyQuestion.options;
        var tableStr = "";
        if(tableQuestions.length > 0 && radioOptions.length > 0) {
            if(pagetype == "edit") {
                tableStr += "<table><thead><th>&nbsp;</th>";
                for(var i = 0; i < radioOptions.length; i++) {
                    tableStr += "<th class=\"surv_radio_opt\">" + radioOptions[i].title + "</th>";
                }
                tableStr += "</thead><tbody>";
                for(var i = 0; i < tableQuestions.length; i++) {
                    tableStr += "<tr id=\"quest" + tableQuestions[i].id + "\" data-parentid=\"quest" + surveyQuestion.id + "\" class=\"unanswered-question\"><td>" + getOrd(i, surveyQuestion.options_ques_numbering) + (surveyQuestion.options_ques_numbering.length > 0 ? ". ":"") + tableQuestions[i].title + " <span id=\"nalbl" + tableQuestions[i].id + "\" class=\"notanswered right\"></span>";
                    tableStr += "<div class=\"answerBtns right\"><input id=\"idkbuttn" + tableQuestions[i].id + "\"  data-questionid=\"" + tableQuestions[i].id + "\" class=\"button ui-button ui-widget ui-state-default ui-corner-all noAnswer left\" data-parentid=\"" + surveyQuestion.id + "\" data-answer=\"IDK\" value=\"IDK\" readonly=readonly />\n\
                                 <input id=\"nabuttn" + tableQuestions[i].id + "\"  data-questionid=\"" + tableQuestions[i].id + "\" class=\"button ui-button ui-widget ui-state-default ui-corner-all noAnswer right\" data-parentid=\"" + surveyQuestion.id + "\" data-answer=\"-1\" value=\"No Ans\" readonly=readonly /></div><div class=\"clear\"></div></td>";
                    for(var j = 0; j < radioOptions.length; j++) {
                        tableStr += "<td><div><input " + (getSurveyQuestionAnswer(tableQuestions[i]) == radioOptions[j].title?"checked":"") + " class=\"surveyRadioQues\" id=\"tabledradio_" + surveyQuestion.id + "_" + tableQuestions[i].id + "_" + j + "\" data-questionid=\"" + tableQuestions[i].id + "\" type=\"radio\" value=\"" + radioOptions[j].title + "\" name=\"" + "q_" + surveyQuestion.id + "_" + tableQuestions[i].id + "\" data-parentid=\"" + surveyQuestion.id + "\"/></div></td>";
                    }
                    tableStr += "</tr>";
                }
                tableStr += "</tbody></table>";
            } else {
                for(var i = 0; i < tableQuestions.length; i++) {
                    tableStr += "<div id=\"quest" + tableQuestions[i].id +"\" data-parentid=\"quest" + surveyQuestion.id + "\" class=\"unanswered-question subanswer\">";
                    tableStr += "<span class=\"questitle\">" + getOrd(i, surveyQuestion.options_ques_numbering) + (surveyQuestion.options_ques_numbering.length > 0 ? ". ":"")+ tableQuestions[i].title + " <span id=\"nalbl" + tableQuestions[i].id + "\" class=\"notanswered\"></span></span><span class=\"showValue\">" + getSurveyQuestionAnswer(tableQuestions[i]) + "</span>";
                    tableStr += "</div>";
                }
            }
        } 
        questionsHTML += tableStr + "<div id =\"successMsg" + surveyQuestion.id + "\" class=\"successMsg right\">Answer will be saved when you select an option</div><div class=\"clear\"></div>";
    } else if(surveyQuestion.typ == "tabled scale") {
        var tableQuestions = surveyQuestion.options_questions;
        var radioOptions = surveyQuestion.options;
        var tableStr = "";
        if(tableQuestions.length > 0 && radioOptions.length > 0) {
            if(pagetype == "edit") {
                tableStr += "<div class=\"successMrk\"><table><thead><th>&nbsp;</th>";
                for(var i = 0; i < radioOptions.length; i++) {
                    tableStr += "<th class=\"surv_scale_opt textcenter\">" + radioOptions[i].title + "<br />" + radioOptions[i].desc + "</th>";
                }
                tableStr += "</thead><tbody>";
                for(var i = 0; i < tableQuestions.length; i++) {
                    tableStr += "<tr id=\"" + "quest" + tableQuestions[i].id + "\" data-parentid=\"quest" + surveyQuestion.id + "\" class=\"unanswered-question\"><td>" + getOrd(i, surveyQuestion.options_ques_numbering) + (surveyQuestion.options_ques_numbering.length > 0 ? ". ":"") + tableQuestions[i].title + " <span id=\"nalbl" + tableQuestions[i].id + "\" class=\"notanswered right\"></span><div class=\"answerBtns right\"><input id=\"idkbuttn" + tableQuestions[i].id + "\"  data-questionid=\"" + tableQuestions[i].id + "\" class=\"button ui-button ui-widget ui-state-default ui-corner-all noAnswer left\" data-parentid=\"" + surveyQuestion.id + "\" data-answer=\"IDK\" value=\"IDK\" readonly=readonly />\n\
                    <input id=\"nabuttn" + tableQuestions[i].id + "\"  data-questionid=\"" + tableQuestions[i].id + "\"  data-parentid=\"" + surveyQuestion.id + "\" class=\"button ui-button ui-widget ui-state-default ui-corner-all noAnswer right\" data-answer=\"-1\" value=\"No Ans\" readonly=readonly /></div><div class=\"clear\"></div></td>";
                    for(var j = 0; j < radioOptions.length; j++) {
                        tableStr += "<td class=\"textcenter\"><input " + (getSurveyQuestionAnswer(tableQuestions[i]) == radioOptions[j].title?"checked":"") + " class=\"surveyRadioQues\" data-parentid=\"" + surveyQuestion.id + "\" data-questionid=\"" + tableQuestions[i].id + "\" type=\"radio\" value=\"" + radioOptions[j].title + "\" name=\"" + "quest" + tableQuestions[i].id + "_" + i + "\" /></td>";
                    }
                    tableStr += "</tr>";
                }
                tableStr += "</tbody></table>";
            } else {
                tableStr += "<div class=\"successMrk\"><table><thead><th>&nbsp;</th>";
                for(var i = 0; i < radioOptions.length; i++) {
                    tableStr += "<th class=\"surv_scale_opt textcenter\">" + radioOptions[i].desc + "</th>";
                }
                tableStr += "</thead><tbody>";
                for(var i = 0; i < tableQuestions.length; i++) {
                    tableStr += "<tr id=\"" + "quest" + tableQuestions[i].id + "\" data-parentid=\"quest" + surveyQuestion.id + "\" class=\"unanswered-question\"><td>" + getOrd(i, surveyQuestion.options_ques_numbering) + (surveyQuestion.options_ques_numbering.length > 0 ? ". ":"") + tableQuestions[i].title + " <span id=\"nalbl" + tableQuestions[i].id + "\" class=\"notanswered\"></span></td>";
                    for(var j = 0; j < radioOptions.length; j++) {
                        tableStr += "<td class=\"textcenter surv_radio_label\"><label for=\"radio_" + j + "_" + tableQuestions[i].id + "\">" + radioOptions[j].title + "</label></td>";        
                        if(pagetype != "edit") {
                            tableStr = tableStr.replace("\"radio_" + getSurveyQuestionAnswer(tableQuestions[i]) + "_" + tableQuestions[i].id + "\"", "\"radio_" + getSurveyQuestionAnswer(surveyQuestion) + "_" + surveyQuestion.id + "\" class=\"answer\"");
                        }
                    }
                    tableStr += "</tr>";
                }
                tableStr += "</tbody></table>";
            }
            questionsHTML += tableStr + "<div id =\"successMsg" + surveyQuestion.id + "\" class=\"successMsg right\">Answer will be saved when you select an option</div><div class=\"clear\"></div></div>";
        }
    } else if(surveyQuestion.typ == "scale") {
        var radioOptions = surveyQuestion.options;
        var tableStr = "";
        var tableStr1 = "";
        var tableStr2 = "";
        var tableStr3 = "";
        if(radioOptions.length > 0) {
            tableStr += "<table><tbody>";
            for(var i = 0; i < radioOptions.length; i++) {
                tableStr1 += "<td width=\"" + (980/radioOptions.length) + "\" class=\"surv_radio_label borderless textcenter\"><label for=\"radio_" + i + "_" + surveyQuestion.id + "\">" + radioOptions[i].title + "</label></td>";
                tableStr2 += "<td class=\"surv_radio_opt borderless textcenter\"><input " + (getSurveyQuestionAnswer(surveyQuestion) == radioOptions[i].title?"checked":"") + " class=\"surveyRadioQues\" id=\"radio_" + i + "_" + surveyQuestion.id + "\" data-questionid=\"" + surveyQuestion.id + "\" type=\"radio\" value=\"" + radioOptions[i].title + "\" name=\"" + "quest" + surveyQuestion.id + "_scale\" /></td>";
                tableStr3 += "<td class=\"surv_radio_desc borderless textcenter\"><label for=\"radio_" + i + "_" + surveyQuestion.id + "\">" + radioOptions[i].desc + "</label></td>";
            }
            tableStr += "<tr>" + tableStr1 + "</tr>";
            if(pagetype == "edit") {
                tableStr += "<tr>" + tableStr2 + "</tr>";
            }
            tableStr += "<tr>" + tableStr3 + "</tr>";
            tableStr += "</tbody></table>";
            
            if(pagetype != "edit") {
                tableStr = tableStr.replace("\"radio_" + getSurveyQuestionAnswer(surveyQuestion) + "_" + surveyQuestion.id + "\"", "\"radio_" + getSurveyQuestionAnswer(surveyQuestion) + "_" + surveyQuestion.id + "\" class=\"answer\"");
            }
            questionsHTML += tableStr + "<div id =\"successMsg" + surveyQuestion.id + "\" class=\"successMsg right\">Answer will be saved when you select an option</div><div class=\"clear\"></div>";
        }
    } else if( surveyQuestion.typ == "empty"){
        answered.push("quest" + surveyQuestion.id);
    } else if(surveyQuestion.typ != "field" && surveyQuestion.typ != "text" && surveyQuestion.typ.length > 0){
        alert("Survey has no Layouts for type " + surveyQuestion.typ + ".")
    }
    questionsHTML += "</div>";
    if (surveyQuestion.questions.length > 0) {
        for (var i=0; i < surveyQuestion.questions.length; i++){
            questionsHTML += createSurveyQuestion(surveyQuestion.questions_name + getOrd(i, surveyQuestion.questions_numbering), surveyQuestion.questions[i], i, "sub");
        }
    }
    return questionsHTML;
}

function getOrd(ArrIndex, questions_numbering) {
    if(questions_numbering == "numeric"){
        return ArrIndex + 1;
    } else if(questions_numbering == "alpha"){
        return String.fromCharCode('a'.charCodeAt() + ArrIndex);
    } else {
        return "";
    }
}

function greyOutAnswered(){
    $(".surveyTextQues").addClass("answeredTxt"); 
    $(".surveyFieldQues").addClass("answeredTxt");
    $(".othertxt").addClass("answeredTxt");
    $(".fieldtxt").addClass("answeredTxt");
}
function createSurveyInteractions() {
    var originalVal;   
    greyOutAnswered();
    $(".surveyTextQues").focus(function () { 
        originalVal = $(this).val();
        $(this).removeClass("answeredTxt");
    });
    $(".surveyFieldQues").focus(function () { 
        originalVal = $(this).val();
        $(this).removeClass("answeredTxt");
    });
    
    $(".surveyTextQues").blur(function () { 
        if($(this).val().length < 4001) {
            resubmitCounter = 0;
            if(originalVal != $(this).val()) {
                sendSurveyAns(recordid, $(this).data("questionid"), $(this).val());
            } else if($(this).val().length > 0) {
                $(this).addClass("answeredTxt");
            } else {
                $(this).removeClass("answeredTxt");
            }
        }
    });
    
    $(".surveyFieldQues").blur(function () { 
        if($(this).val().length < 256) {
            resubmitCounter = 0;
            if(originalVal != $(this).val()) {
                sendSurveyAns(recordid, $(this).data("questionid"), $(this).val());
            } else if($(this).val().length > 0) {
                $(this).addClass("answeredTxt");
            } else {
                $(this).removeClass("answeredTxt");
            }
        }
    });

    $(".surveyRadioQues").change(function () {
        resubmitCounter = 0;
        if( !$(this).hasClass("othertrigger") ) {
            var parentid = String($(this).data("parentid"));
            if (parentid != null && parentid != "undefined" && parentid.length > 0){ 
                sleep(300);
                sendSurveyAns(recordid, $(this).data("questionid"), $(this).val(), parentid);  
            } else {
                sleep(300);
                sendSurveyAns(recordid, $(this).data("questionid"), $(this).val()); 
            }

            $("#quest" + $(this).data("questionid") + " .fieldtxt").each(function () {
                if(!($(this).prev("input")).prop("checked")){
                    $(this).val("");
                }
            });
        }
    });
    
    $(".surveyCheckBoxQues").change(function () {
        resubmitCounter = 0;
        if( !$(this).hasClass("othertrigger") ) {
            sendSurveyAns(recordid, $(this).data("questionid"), getCheckBoxValues($(this).attr("name")));
        } else if($(this).prop("checked")== false ){
            $("#" + $(this).attr("id") + "_other").val("");
            sendSurveyAns(recordid, $(this).data("questionid"), getCheckBoxValues($(this).attr("name")));
        }
    });
    
    $(".othertxt").blur(function () {
        if(originalVal != $(this).val()) {
            var parentElement = $("#" + $(this).attr("id").replace("_other",""));
            resubmitCounter = 0;
            sendSurveyAns(recordid, parentElement.data("questionid"), getCheckBoxValues(parentElement.attr("name")));
        }
        $(this).addClass("answeredTxt");
    });
    
    $(".othertxt").focus(function () {
        originalVal = $(this).val();
        $(this).removeClass("answeredTxt");
    });
    
    $(".fieldtxt").blur(function () {
        if(originalVal != $(this).val()) {
            var newVal; 
            var parentElement = $("#quest" + $(this).data("questionid"));
            resubmitCounter = 0;
            if(parentElement.hasClass("addposttextfield")){
                newVal = $(this).prev("input").val() + " _ " + $(this).val();
            } else {
                newVal = $(this).val() + " _ " + $(this).prev("input").val();
            }
            sendSurveyAns(recordid, $(this).data("questionid"), newVal);
            $("#quest" + $(this).data("questionid") + " .fieldtxt").each(function () {
                if(!($(this).prev("input")).prop("checked")){
                    $(this).val("");
                    $(this).hide();
                }
            });
        }
        $(this).addClass("answeredTxt");
    });
    
    $(".fieldtxt").focus(function () {
        originalVal = $(this).val();
        $(this).removeClass("answeredTxt");
    });
    
    $(".edit .sendSurvey").click(function () {
        submitRecord(recordid);
    });
    
    $(".view .sendSurvey").click(function () {
        resubmitCounter = 0;
        resumeEditing(recordid);
    });
    
    $("div.questionDiv.depends-on").each(function () {
        var dependenciesArr;
        var dependinfoArr;
        var groupName = "";
        var ans = "";
        var destElem = $(this);
        var fieldWithValElem;
        dependenciesArr = destElem.attr("class").split("depends-on-");
        if(dependenciesArr.length > 1){
            dependinfoArr = dependenciesArr[1].split(" ")[0].split("-");
            ans = dependinfoArr[0];
            groupName = "questr"+ dependinfoArr[1];
            if( !$("body").hasClass("view") ){
                if($("input[name='" + groupName + "']:checked").val() != ans) {
                    destElem.removeClass("unanswered-question");
                } else {
                    destElem.addClass("dependency-on");
                }
            } else {
                if($("#quest" + dependinfoArr[1] + " .showValue").html() != ans) {
                    destElem.removeClass("unanswered-question");
                }
            }
            $("input[name='" + groupName + "']").change(function() {
                 fieldWithValElem = $("#" + destElem.attr("id").replace("quest","surveyTextQues"));
                 if (fieldWithValElem.val() == undefined) {
                     fieldWithValElem = $("#" + destElem.attr("id").replace("quest","surveyFieldQues"));
                     if (fieldWithValElem.val() == undefined) {
                        fieldWithValElem = $("input[name='" + destElem.attr("id") + "_scale']:checked");
                     }
                 }
                 if( $(this).val() == ans && (fieldWithValElem.val() == undefined || !fieldWithValElem.val().length > 0) ) {
                    destElem.removeClass("answered-question");
                    destElem.addClass("unanswered-question");
                    destElem.addClass("dependency-on");
                 } else {
                    destElem.addClass("answered-question");
                    destElem.removeClass("unanswered-question");
                    destElem.removeClass("dependency-on");
                 }
            });
        }
    });
    
    $(".noAnswer").click(function () {
        var ansToSend = String($(this).data("answer")).toLowerCase();
        var questionid = $(this).data("questionid");
        var groupName = "";
        resubmitCounter = 0; 
        sendSurveyAns(recordid, questionid, ansToSend, String($(this).data("parentid")));
        $(this).hide();
        $("#"+$(this).attr("id").replace("nabuttn","nalbl")).show();
        $("#quest" + questionid + " .othertxt").val("");
        $("#quest" + questionid + " .othertxt").hide();
        $("#quest" + questionid + " .fieldtxt").val("");
        $("#quest" + questionid + " .fieldtxt").hide();
        $("#quest" + questionid + " .limiterr").hide();
        var question = $("#quest" + questionid);
        if (question.data("sectinjson") != "sub" && question.data("sectinjson") != undefined ){
            var subquestions = survey.sections[question.data("sectinjson")].questions[question.data("quesinjson")].questions;
            if ( subquestions != undefined ) {
                for (var i = 0; i < subquestions.length; i++) {
                    var defaultClassArr;
                    defaultClassArr = subquestions[i].ui_class.split("default-val-");
                    if(defaultClassArr.length > 1){
                        ansToSend = defaultClassArr[1].split(" ")[0];
                        groupName = "quest"+ subquestions[i].id + "_scale";
                        $('input:radio[name=' + groupName + ']').filter('[value=' + ansToSend + ']').prop('checked', true);
                        $('input:radio[name=' + groupName + ']').prop('disabled', true);
                    }
                    sendSurveyAns(recordid, subquestions[i].id, ansToSend, questionid);
                    sleep(300);
                }
            }
        }
    });
    
    $(".back-to-top").click(function () {
        scrollTo("#survey-tabs");
    });
        
}

function sendSurveyAns( recId, quesId, ans, parentid) {
  $.ajax({ 
        type: "POST",
        url: '/cahubdataservices/surveyRecord/updateSurveyJSON/' + recId + '?callback=?',
        dataType: "jsonp",
        data: {
            id: recId,
            question: quesId,
            answer: ans
        },
        
        success: function( data ) {
            if(data.status == "success") {
                painTabledRowStatus(quesId, parentid, "success");
                if(ans === "-1" || ans === "idk"){
                    markNotAnswered( quesId, ans );
                } else {
                    markAnswered( quesId );
                    var question = $("#quest"+quesId);
                    if(question.data("sectinjson") !== "sub" && question.data("sectinjson") != undefined ) {
                        var subquestions = survey.sections[question.data("sectinjson")].questions[question.data("quesinjson")].questions;
                        if ( subquestions != undefined ) {
                            for (var i = 0; i < subquestions.length; i++) {
                                $('input:radio[name=quest' + subquestions[i].id + '_scale]').prop('disabled', false);
                            }
                        }
                    }
                }
                if( ans.length > 0) {
                    if ( $("#quest" + quesId).hasClass("unanswered-question") ) {
                        $("#quest" + quesId).removeClass("unanswered-question");
                        $("#quest" + quesId).addClass("answered-question");
                    }
                } else if ($("#quest" + quesId).hasClass("dependency-on")) {
                    $("#quest" + quesId).addClass("unanswered-question"); 
                    $("#quest" + quesId).removeClass("answered-question"); 
                }
                
                quesId = parentid != null && parentid != "undefined" && parentid.length > 0 ? parentid: quesId;
                $("#successMsg" + quesId).html("Successfully saved this answer.");
                paintAnswered(quesId, ans, "success");
                if( data.surveyComplete ) {
                    $(".sendSurvey").show();
                } else { 
                    $(".sendSurvey").hide();
                }
            } else {
                resubmitCounter++;
                if (resubmitCounter < 3) {
                    sleep(800);
                    sendSurveyAns( recId, quesId, ans, parentid);
                } else {
                    painTabledRowStatus(quesId, parentid, "fail");
                    quesId = parentid != null && parentid != undefined && parentid.length > 0 ? parentid: quesId;
                    $("#successMsg" + quesId).html("Did not save answer. <b>Please </b><input class=\"button ui-button ui-widget ui-state-default ui-corner-all\" value=\"Resubmit\" readonly=readonly />");
                    paintAnswered(quesId, ans, "fail");
                    $("#quest" + quesId + " .button").click(function () { 
                        sendSurveyAns(recId, quesId, ans);
                    });
                }
            }
        },
        error: function() {
            resubmitCounter++;
            if (resubmitCounter < 3) {
                sleep(800);
                sendSurveyAns( recId, quesId, ans, parentid);
            } else {
                painTabledRowStatus(quesId, parentid, "fail");
                quesId = parentid != null && parentid != undefined && parentid.length > 0 ? parentid: quesId;
                $("#successMsg" + quesId).html("Did not save answer. <b>Please </b><input class=\"button ui-button ui-widget ui-state-default ui-corner-all\" value=\"Resubmit\" readonly=readonly />");
                paintAnswered(quesId, ans, "fail");
                $("#quest" + quesId + " .button").click(function () { 
                    sendSurveyAns(recId, quesId, ans);
                });
            }
        }
    }); 
}

function submitRecord( recId ) {
    var data= { "id": recId };
    postData("/cahubdataservices/surveyRecord/submitSurveyJSON/" + recId , 'post', data);
}

function resumeEditing( recId ) {
    var redir;
    $.ajax({
        type: 'GET',
        dataType: 'jsonp', 
        url: "/cahubdataservices/surveyRecord/resumeEditingJSON/" + recId,
        success: function( data ) {
            if(data.status == "success") { 
                   redir = "window.location.href='/cahubdataservices/survey/edit/" +  + recId + "';"
                   setTimeout(redir,0);
            } else {
                resubmitCounter++;
                if (resubmitCounter < 3) {
                    sleep(800);
                    resumeEditing( recId );
                } else {
                    $("ul.tab-list").append("<b class=\"redtext textright\">Please check Interview Status or try again later. </b>");
                }
            }
        },
        error: function() {
            resubmitCounter++;
            if (resubmitCounter < 3) {
                sleep(800);
                resumeEditing( recId );
            } else {
                $("ul.tab-list").append("<b class=\"redtext textright\">Please check Interview Status or try again later. </b>");
            }
        }
    }); 
}

function paintAnswered( quesId, answer, status ){
    var statuscolor = "";
    checkAllAnsweredForParent("quest" + quesId);
    permissionToSave();
    $("#quest" + quesId + " input.successMrk").on("focus",function(){
        $("#quest" + quesId + " input.successMrk").removeClass("answeredTxt");
    });
    $("#quest" + quesId + " textarea.successMrk").on("focus",function(){
        $("#quest" + quesId + " textarea.successMrk").removeClass("answeredTxt");
    });
    $("#successMsg" + quesId).removeClass("successMsg");
    $("#successMsg" + quesId).removeClass("failMsg");
    if(status === "success") {
        if (answer.length > 0){
            $("#quest" + quesId + " input.successMrk").addClass("answeredTxt");
            $("#quest" + quesId + " textarea.successMrk").addClass("answeredTxt");
        }
        statuscolor = "#66FF00";
        textcolor = "#669900";
        $("#successMsg" + quesId).addClass("successMsg");
    } else {
        statuscolor = "#FF9999";
        textcolor = "#FF3333";
        $("#successMsg" + quesId).addClass("failMsg");
    }
    $("#quest" + quesId + " .successMrk").animate({
       borderBottomColor: statuscolor,
       borderLeftColor: statuscolor,
       borderTopColor: statuscolor,
       borderRightColor: statuscolor,
       borderBottomWidth: "2px",
       borderLeftWidth: "2px",
       borderTopWidth: "#2px",
       borderRightWidth: "#2px"
    },0);
    $("#quest" + quesId + " .successMsg").show();
    $("#successMsg" + quesId ).animate({color:textcolor},0);
    if(status === "success") {
        $("#quest" + quesId + " .successMrk").animate({
           borderBottomColor: "#eee",
           borderLeftColor: "#eee",
           borderTopColor: "#eee",
           borderRightColor: "#eee",
           borderBottomWidth: "1px",
           borderLeftWidth: "1px",
           borderTopWidth: "#1px",
           borderRightWidth: "#1px"
        }, 7000);
        $("#quest" + quesId + " .successMsg").animate({color: "#fff"},7000);
        $("#quest" + quesId + " .successMrk").removeClass("successBorder");
    }
}

function painTabledRowStatus( quesId, parentid, status ){
   if (status == "fail"){
        $("#tr_" + parentid + "_" + quesId ).addClass("errors");
        $("#tr_" + parentid + "_" + quesId + " td:first-child").addClass("redtext");
   } else {
        $("#tr_" + parentid + "_" + quesId ).removeClass("errors");
        $("#tr_" + parentid + "_" + quesId + " td:first-child").removeClass("redtext");
   }
}

function jsAjax(urlCallback)
{
	var headID=document.getElementsByTagName("head")[0];
	var newScript=document.createElement('script');
	newScript.type='text/javascript';
	newScript.src=urlCallback;
	headID.appendChild(newScript);
}
