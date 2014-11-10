/* Please do not check in commented out code like alert(flags) , partial code etc... */
 
$(document).ready(function() {
    
    $("#sub").click(function(){
        var h = document.getElementById("height").value;
        if(isNaN(h)){
            alert("The height must be a number");
            document.getElementById("height").focus();
            return false;
        }
        var w = document.getElementById("weight").value;
        if(isNaN(w)){
            alert("The weight must be a number");
            document.getElementById("weight").focus();
            return false;
        }
    });    

    $("#g1").change(function(){
        document.getElementById("otherGender").value='';
        document.getElementById("other").style.display = 'none';
    });  
    
    $("#g2").change(function(){
        document.getElementById("otherGender").value='';
        document.getElementById("other").style.display = 'none';
    }); 
    
    $("#g3").change(function(){
        document.getElementById("otherGender").value='';
        document.getElementById("other").style.display = 'none';
    });
    
    $("#g4").change(function(){
        $("#other").show();
    });  

    $("#height").change(function(){
        var h = document.getElementById("height").value;
        var w = document.getElementById("weight").value;
        var bmi;
        if(!isNaN(h) && !isNaN(w) && h!= 0 && w !=0){
            bmi=(703*w)/(h*h);
            bmi=bmi.toFixed(2); 
            document.getElementById("BMI").value=bmi;
            $("#bmi").html(bmi);
        }
    });

    $("#weight").change(function(){
        var h = document.getElementById("height").value;
        var w = document.getElementById("weight").value;
        var bmi;
        if(!isNaN(h) && !isNaN(w) && h != 0 && w !=0){
            bmi=(703*w)/(h*h);
            bmi=bmi.toFixed(2); 
            document.getElementById("BMI").value=bmi;
            $("#bmi").html(bmi);
        }
    });
});
 