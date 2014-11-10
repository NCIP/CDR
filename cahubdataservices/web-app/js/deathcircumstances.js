 /* Please do not check in commented out code like alert(flags) , partial code etc... */

$(document).ready(function(){
    $(".placeOfDeath").click(function(){
        document.getElementById("otherPlaceOfDeath").value='';
        document.getElementById("opod").style.display = 'none';
    });
    $("#pl8").click(function(){
        $("#opod").show();
        $("#otherPlaceOfDeath").focus();
    });

    $(".personDeterminedDeath").click(function(){
        document.getElementById("otherPersonDeterminedDeath").value='';
        document.getElementById("op").style.display = 'none';
    });
    $("#pe3").click(function(){
        $("#op").show();
        $("#otherPersonDeterminedDeath").focus();
    });
                
    $("#v1").click(function(){
        $("#du").show();
        $("#ventilatorDuration").focus();
    });
    $(".onVentilator").click(function(){
        document.getElementById("ventilatorDuration").value='';
        document.getElementById("du").style.display = 'none';
    });
                
    $("#re1").click(function(){
         $("#eh").show();
        $("#estimatedHours").focus();
    });           
    $(".wasRefrigerated").click(function(){
        document.getElementById("eh").style.display = 'none';
        document.getElementById("estimatedHours").value = ''
    });             
                
    $("#immediateCause").change(function(){
        var g = document.getElementById('immediateCause').value;
        if(g == 'Other'){
            $("#oi").show();
            $("#otherImmediate").focus();
        } else  {
            document.getElementById("otherImmediate").value='';
            document.getElementById("oi").style.display = 'none';
        }
     });
                
     $("#firstCause").change(function(){
         var g = document.getElementById('firstCause').value;
         if(g == 'Other'){
            $("#of").show();
            $("#otherFirstCause").focus();
         } else {
            document.getElementById("otherFirstCause").value='';
            document.getElementById("of").style.display = 'none';
         }
      });
                
      $("#lastCause").change(function(){
          var g = document.getElementById('lastCause').value;
          if(g == 'Other'){
             $("#ol").show();
             $("#otherLastCause").focus();
          } else {
             document.getElementById("otherLastCause").value='';
             document.getElementById("ol").style.display = 'none';
          }
      });
      
    $('.select-cod-by-vocab').each(function() { 
        createVocabSelectHTML("cod", $(this), "Cause of Death");
    });
    
});


