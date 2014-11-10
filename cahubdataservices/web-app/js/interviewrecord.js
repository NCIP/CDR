/* Please do not check in commented out code like alert(flags) , partial code etc... */
 
$(document).ready(function(){
    
    $("#noconsent").click(function(){
        $("#stNone").attr("checked", "checked");

    });

    $("#declined").click(function(){
        $("#stNone").attr("checked", "checked");
    });    

    $("#consented").click(function(){
        $("#stNone").attr("checked", false);

    });

    $("#stNone").click(function(){
        $("#consented").attr("checked", false);
    });     
    
    $("#stDonor").click(function(){
        $("#declined").attr("checked", false);
        $("#noconsent").attr("checked", false);
    });     
    $("#stNonDonor").click(function(){
        $("#declined").attr("checked", false);
        $("#noconsent").attr("checked", false);
    });     
    
    
    $("#interviewCreate").click(function(){
    if (!document.getElementById("declined").checked &&
        !document.getElementById("noconsent").checked &&
        !document.getElementById("consented").checked) {
            alert ("Please choose the appropriate consent option.");
            return false;
        }        

    if (!document.getElementById("stDonor").checked &&
        !document.getElementById("stNonDonor").checked &&
        !document.getElementById("stNone").checked){
            alert ("Please choose a survey type.");
            return false;
        }        
    });
    
    
    $("#interviewCancel").click(function(){
        history.back(1);
    });

});    