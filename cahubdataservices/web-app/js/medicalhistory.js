/* Please do not check in commented out code like alert(flags) , partial code etc... */

$(document).ready(function(){
    
    $("#sub").click(function(){
        if(!document.getElementById("s2").checked && !document.getElementById("s3").checked){
            alert("The primary history source is a required field");
            return false;
        }
        if(!document.getElementById("n1").checked && !document.getElementById("n2").checked && !document.getElementById("n3").checked){
            alert("Please specify if there is a history of non-metastatic cancer");
            return false;
        }
    });   
    
    
    $("#s2").change(function(){
        document.getElementById("primary").value="";
        document.getElementById("c").style.display = "none";
        document.getElementById("otherPrimary").value="";
        document.getElementById("o").style.display = "none";
    });
    
    $("#s3").change(function(){
        $("#c").show();
    });
                
    $("#primary").change(function(){
        var g = document.getElementById("primary").value;
        if(g == 'Other'){
            $("#o").show();
        } else {
            document.getElementById("otherPrimary").value="";
            document.getElementById("o").style.display = "none";
        }
    });
                
    $("#n1").change(function(){ 
        $("#ch").show();
    });
                 
    $("#n2").change(function(){ 
        document.getElementById("ch").style.display = "none";
    });
                 
    $("#n3").change(function(){ 
        document.getElementById("ch").style.display = "none";
    });
                  
    $("#nonMetastaticCancer").change(function(){
        var g = document.getElementById("nonMetastaticCancer").value;
        if (g == 'Yes'){
            $("#ch").show();
        } else {
            document.getElementById("ch").style.display = "none";
        }
    });
});


