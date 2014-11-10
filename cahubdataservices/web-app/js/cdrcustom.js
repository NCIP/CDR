
//@author shivece

//ajax call to return specimen count. return type from controller is jsonp
function getSpecimenCount(cid){
                  
    $.ajax({
        type: "GET",
        url: "/cahubdataservices/caseRecord/getSpecimenCount/" + cid,
        dataType: "jsonp",
        crossDomain: true,
        success: function(data) {
            //alert(data[0].count)
            $("#scount" + cid).html(data[0].count)
        }
    });
}
