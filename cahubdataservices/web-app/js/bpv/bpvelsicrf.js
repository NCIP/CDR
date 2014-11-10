

    $(".contactResultC2").change(function() {
        var checkflag1 = 0;
        var checkflag2 = 0;
        if( $(this).prop('checked') && !$("#contactResultC2").prop('checked')) {
            $("#contactResultC2").prop('checked',true);
            $("#contactResultC").prop('checked',true);
        } else if( !$(this).prop('checked')){
            $(".contactResultC2").each(function () {
                if( $(this).prop('checked')) {
                    checkflag1 = 1;
                }
            });
            if( checkflag1 == 0 ) {
                $("#contactResultC2").prop('checked',false);
                $(".contactResultC").each(function () {
                    if( $(this).prop('checked')) {
                        checkflag2 = 1;
                    }
                });
                if( checkflag2 == 0 ) {
                    $("#contactResultC").prop('checked',false)
                }
            }
        }
    });


    $(".contactResultC").change(function() {
        var checkflag = 0;
        if( $(this).prop('checked') && !$("#contactResultC").prop('checked')) {
            $("#contactResultC").prop('checked',true);
        } else if( !$(this).prop('checked')){
            $(".contactResultC").each(function () {
                if( $(this).prop('checked')) {
                    checkflag = 1;
                }
            });
            if( checkflag == 0 ) {
                $("#contactResultC").prop('checked',false);
            }
            if( $(this).attr("id") == "contactResultC2") {
                $(".contactResultC2").prop('checked',false);
            }
        }
    });


    $(".contactResultB").change(function() {
        var checkflag = 0;
        if( $(this).prop('checked') && !$("#contactResultB").prop('checked')) {
            $("#contactResultB").prop('checked',true);
        } else if( !$(this).prop('checked')){
            $(".contactResultB").each(function () {
                if( $(this).prop('checked')) {
                    checkflag = 1;
                }
            });
            if( checkflag == 0 ) {
                $("#contactResultB").prop('checked',false);
            }
        }
    });
    
    $("#contactResultC").change(function() {
        if( !$(this).prop('checked')){
            $(".contactResultC").prop('checked',false);
            $(".contactResultC2").prop('checked',false);
        }
    });
    
    $("#contactResultB").change(function() {
        if( !$(this).prop('checked')){
            $(".contactResultB").prop('checked',false);
        }
    });