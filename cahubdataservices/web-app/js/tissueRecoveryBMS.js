          var date1Tip = "Date tissue sample is removed from body.";
          var time1Tip = "Time tissue sample is removed from body.";
          var date2Tip = "Date aliquot is removed from tissue sample.";
          var time2Tip = "Time aliquot is removed from tissue sample.";
          var dateFixativeTip = "Date aliquot placed in PAXgene, LN2 or on dry ice.";
          var timeFixativeTip = "Time aliquot placed in PAXgene, LN2 or on dry ice.";
          var delayTip = "Fixation Start Time  - Cardiac Cessation Time";
          
          function showhide(id){
            var value=  document.getElementById("tissueLocation_" + id).value; 
            if(value=='28'){
              document.getElementById("otl_" + id).style.display = 'block';
            }else{
              document.getElementById("otl_" + id).style.display = 'none';
              document.getElementById("otherTissueLocation_" + id).value = ''; 
            }
          }

          function setDate(i){
            var contents = '';
            if(ccString ){
              var ccTime= Date.parse(ccString);
              var date;
              if (document.getElementById("date2_" +i +"_0").checked) {
                  date=document.getElementById("date2_" +i +"_0").value;
              }
              if (document.getElementById("date2_" +i +"_1").checked) {
                  date=document.getElementById("date2_" +i +"_1").value;
              }

              var time = document.getElementById("time2_" +i).value;
              if(date && time){
                 var d=Date.parse(date+ " " + time);
                 var millsec = d - ccTime;
                 var posMillsec;
                 if(millsec < 0){
                   posMillsec= (-1)*millsec;
                 } else {
                   posMillsec = millsec;
                 }

                  var hour = Math.floor(posMillsec/(60*60*1000));
                  var min = Math.floor(posMillsec/(60*1000) -hour*60);
                  var str= "";
                  if (hour < 10 && hour >= 0){
                    str='0' + hour;
                  } else {
                    str = str + hour;
                  }

                  var str2= "";
                  if(min < 10){
                    str2='0' + min;
                  } else {
                    str2 = str2 + min;
                  }

                  if(millsec < 0){
                   contents = "-" + str + ":" + str2;
                  } else {
                   contents = str + ":" + str2;
                  }
              }
              document.getElementById("delay_" +i).innerHTML=contents;
          }
    
       }
  
            $(document).ready(function(){
                $("#batch_date1").change(function(){
                    var value = document.getElementById("batch_date1").value;
                    for(var i = 0; i < specimenSize; i++){
                        var value2 = document.getElementById("date1_" +i +"_0").value;
                        if(value2 == value){
                           document.getElementById("date1_" +i +"_0").checked = true;
                        }else{
                           document.getElementById("date1_" +i +"_0").checked = false;
                        }
                        var value3 = document.getElementById("date1_" +i +"_1").value;
                        if(value3 == value){
                           document.getElementById("date1_" +i +"_1").checked = true;
                        }else{
                           document.getElementById("date1_" +i +"_1").checked = false;
                        }
                    }
                   });
                   
                    $("#batch_date2").change(function(){
                    var value = document.getElementById("batch_date2").value; 
                    for(var i = 0; i < specimenSize; i++){
                        var value2 = document.getElementById("date2_" +i +"_0").value;
                        if(value2 == value){
                           document.getElementById("date2_" +i +"_0").checked = true;
                        }else{
                           document.getElementById("date2_" +i +"_0").checked = false;
                        }
                        var value3 = document.getElementById("date2_" +i +"_1").value;
                         if(value3 == value){
                           document.getElementById("date2_" +i +"_1").checked = true;
                        }else{
                           document.getElementById("date2_" +i +"_1").checked = false;
                        }
                    }
                   });
                   
                    $("#batch_date3").change(function(){
                    var value = document.getElementById("batch_date3").value; 
                    for(var i = 0; i < specimenSize; i++){
                        var value2 = document.getElementById("date3_" +i +"_0").value;
                        if(value2 == value){
                           document.getElementById("date3_" +i +"_0").checked = true;
                        }else{
                           document.getElementById("date3_" +i +"_0").checked = false;
                        }
                        var value3 = document.getElementById("date3_" +i +"_1").value;
                         if(value3 == value){
                           document.getElementById("date3_" +i +"_1").checked = true;
                        }else{
                           document.getElementById("date3_" +i +"_1").checked = false;
                        }
                    }
                   });
                    
                    $("#batch_cons").change(function(){
                     var value = document.getElementById("batch_cons").value;
                     for(var i = 0; i < specimenSize; i++){
                      document.getElementById("tissue_cons_" +i).value = value;
                     }
                    });
            });

