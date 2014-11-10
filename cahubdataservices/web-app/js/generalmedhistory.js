 /* Please do not check in commented out code like alert(flags) , partial code etc... */

$(document).ready(function(){
    $("#sub").click(function(){
        var site;
        if(show45VersionUpdates) {
            site = document.getElementById("medicalCondition-cod-id").value;
        } else {
            site = document.getElementById("medicalCondition").value;
        }
        
        if(site==null ||site.length==0){
            alert("The medical history is required");
            document.getElementById("medicalCondition").focus();
            return false;
        }
                     
        var yearOfOnset =document.getElementById("yearOfOnset").value;
        if(yearOfOnset != null && yearOfOnset.length > 0){
            for (i = 0 ; i < yearOfOnset.length ; i++) { 
                if ((yearOfOnset.charAt(i) < '0') || (yearOfOnset.charAt(i) > '9')){
                    alert("The year of onset must be an integer");
                    document.getElementById("yearOfOnset").focus();
                    return false;
                }
            } 
        }
     });   
     
     $("#sub2").click(function(){
        document.getElementById("f").style.display = "none";
        $("#a").show();
        return false;
     });    
           
     $("#a").click(function(){
         document.getElementById("f").style.display = "block";
         $(this).hide();
      });
      
     $('.select-cod-by-vocab').each(function() { 
         createVocabSelectHTML("cod", $(this), "Medical History");
     });
     
     $(".yearOfOnsetUnknown").change(function() {
         var historyId = $(this).attr("id").split("Unknown")[1];
         $("#yearOfOnset" + historyId).val("1900");
     });
     
     $(".yearOfOnset").change(function() {
         var historyId = $(this).attr("id").split("Onset")[1];
         $("#yearOfOnsetUnknown" + historyId).attr("checked", false);
     });
     
     $("#yearOfOnsetUnknown").change(function() {
         $("#yearOfOnset").val("1900"); 
     });
     
     $("#yearOfOnset").change(function() {
         $("#yearOfOnsetUnknown").attr("checked", false); 
     });
});
         