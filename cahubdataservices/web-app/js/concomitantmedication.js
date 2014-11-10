 /* Please do not check in commented out code like alert(flags) , partial code etc... */

function getCurrentDate() {
  var current = new Date();
  var day = current.getDate()-1;
  var month = current.getMonth();
  var year = current.getFullYear()-1900;
  document.getElementById("dateofLastAdministration_day").options[day].selected = true;
  document.getElementById("dateofLastAdministration_month").options[month].selected = true;
  document.getElementById("dateofLastAdministration_year").options[year].selected = true;
}

function getCurrentDate2() {
  var current = new Date();
  var day = current.getDate();
  var month = current.getMonth() + 1;
  var year = current.getFullYear() - 1899;
  document.getElementById("dateofLastAdministration_day").options[day].selected = true;
  document.getElementById("dateofLastAdministration_month").options[month].selected = true;
  document.getElementById("dateofLastAdministration_year").options[year].selected = true;
}

function getDefaultDate() {
  document.getElementById("dateofLastAdministration_day").options[0].selected = true;
  document.getElementById("dateofLastAdministration_month").options[0].selected = true;
  document.getElementById("dateofLastAdministration_year").options[0].selected = true;
}

$(document).ready(function(){
    var day=document.getElementById("dateofLastAdministration_day").options[0].selected;
    var month=document.getElementById("dateofLastAdministration_month").options[0].selected;
    var year=document.getElementById("dateofLastAdministration_year").options[0].selected;
    if (day && month && year) {
      document.getElementById("d2").checked=true;
    }
    $("#sub").click(function(){
        var site= document.getElementById("medicationName").value;
        if(site==null ||site.length==0){
            site= document.getElementById("medicationName-rx-id").value;
        } 
        if(site==null ||site.length==0){
            alert("The medication name is required");
            //document.getElementById("medicationName").focus();
            return false;
        }
                     
        var amount = document.getElementById("amount").value;
        if(amount != null && isNaN(amount)){
           alert("The amount must be a number");
           return false;
        }
    });  
    $("#sub2").click(function(){
        document.getElementById("f").style.display = 'none';
        $("#a").show();
        return false;
    });    

    $("#a").click(function(){
        document.getElementById("f").style.display = 'block';
        $(this).hide();
    });
    $('.select-rx-by-vocab').each(function() { 
        createVocabSelectHTML("rx", $(this), "Medication Name/Vitamins/Supplements");
    });
});
        
    