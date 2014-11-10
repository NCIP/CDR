$(document).ready(function() {
    $(".icon-collection .ui-state-default").click(function() { 
        var classAct = $.grep(($(this).children("span").attr("class")).split(" "), function(v, i){
            return v.indexOf('ui-icon-') === 0;
        }).join();
        var classCurr = $("#code-instructions").data("classcurr");
        $("#code-instructions").attr("data-classcurr",classAct);
        $("#code-instructions").html($("#code-instructions").html().replace(RegExp(classCurr, "igm"), classAct));
        $("#code-instructions").html($("#code-instructions").html().replace(RegExp($("#button-text").html(), "igm"), classAct.replace("ui-icon-","")));
    });
});


