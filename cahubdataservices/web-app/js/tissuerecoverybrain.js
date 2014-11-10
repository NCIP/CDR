/* Please do not check in commented out code like alert(flags) , partial code etc... */

$(document).ready(function(){
    $("#batch_date_changeTime").change(function(){
        var id = document.getElementById("id").value;
        var d = new Date($("#batch_date").val());
        var year = d.getFullYear();
        var month = d.getMonth() + 1;
        var month_str;
        if (month < 10) {
            month_str = '0' +'' +month;
        } else {
            month_str = month;
        }            
                   
        var day = d.getDate();
        var day_str;
        if(day < 10) {
            day_str = '0' + '' + day;
        } else {
            day_str =  day;
        }
        
        var date_str = month_str + "/" +day_str + "/" + year;
        for(var i = 0; i < 22; i++){
            document.getElementById("date_" +i).value = date_str;
            var t = document.getElementById("time_" +i).value;
            $.ajax({
                type:'POST',
                dataType: "text", 
                data: ({id:id, d:date_str, t:t, i:i}),
                url:'/cahubdataservices/tissueRecoveryBrain/get_interval',
                success:function(data,textStatus){displayInterval(data,textStatus);},
                error:function(XMLHttpRequest,textStatus,errorThrown){}
            });
        }
    });
              
    $("#batch_time").change(function(){
        var id = document.getElementById("id").value;
        var t = document.getElementById("batch_time").value;
        for(var i = 0; i < 22; i++){
            document.getElementById("time_" +i).value = t;
            var d = document.getElementById("date_" +i).value
            $.ajax({
                type:'POST',
                dataType: "text", 
                data: ({id:id, d:d, t:t, i:i}),
                url:'/cahubdataservices/tissueRecoveryBrain/get_interval',
                success:function(data,textStatus){displayInterval(data,textStatus);},
                error:function(XMLHttpRequest,textStatus,errorThrown){}
             });
                      
        }
    });
    
    $("#sub").click(function(){
        for(var i = 0; i < 22; i++){
            var mass = document.getElementById("mass_" +i).value;
            if(isNaN(mass)){
               document.getElementById("mass_" +i).focus();
               document.getElementById("mass_" +i).style.color='#FF0000'; 
               alert("The mass must be a number");
               return false;
            }
         }
    });
             
    $(".date-time-clear").click(function(){
        var theid= $(this).attr('id').split("_")[1];
        $("#time_" + theid).val("");
        $("#date_" + theid).val("");
        return false;
    });
            
    $(".recordinterval").change(function(){
        var name=$(this).attr('id');
        var index = name.indexOf("_");
        var i=name.substring(index+ 1);
        var d = document.getElementById("date_" +i).value;
        var t = document.getElementById("time_" +i).value;
        var id = document.getElementById("id").value;
        $.ajax({
            type:'POST',
            dataType: "text", 
            data: ({id:id, d:d, t:t, i:i}),
            url:'/cahubdataservices/tissueRecoveryBrain/get_interval',
            success:function(data,textStatus){displayInterval(data,textStatus);},
            error:function(XMLHttpRequest,textStatus,errorThrown){}
        });
    });
             
    $(":input").change(function(){
        document.getElementById("changed").value = "Y";
     });
 });
           

 function displayInterval(data, textStatus){
    var obj= jQuery.parseJSON(data);
    document.getElementById(obj.in_id).innerHTML = obj.interval;
 }

 function check(){
    var changed = document.getElementById("changed").value;
    if(changed == "Y"){
        alert("Please save the change!");
        return false;
    }
 }
          


