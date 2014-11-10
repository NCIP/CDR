/* Please do not check in commented out code like alert(flags) , partial code etc... */
 
$(document).ready(function() {
    //alert("hello world")
    $("input[name='collectIn8afterDeath']").change(function(){
        $("input[name='collectAllIn24afterDeath']").prop('checked', false);
    }); 
     $("input[name='collectAllIn24afterDeath']").change(function(){
        $("input[name='collectIn8afterDeath']").prop('checked', false);
       
    });  
    
    
});
 
