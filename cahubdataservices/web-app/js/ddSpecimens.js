
//@author santhanamv

//ajax call to return just the parent specimens of the case record. return type from controller is jsonp
function getParentSpecimens(cid){
    getPlasmaParentSpecimens(cid)
    getSerumParentSpecimens(cid)
    getDnaParentSpecimens(cid)
    getRnaParentSpecimens(cid)
}

function getPlasmaParentSpecimens(cid){
                  
    $.ajax({
        type: "GET",
        url: "/cahubdataservices/bpvBloodForm/getPlasmaParentSpecimens/" + cid,
        dataType: "jsonp",
        crossDomain: true,
        success: function(data) {
            //$("#plasmaParBarcodeDisp").val(data[0].psList)
            $("#plasmaParBarcodeDisp").html(data[0].psList)
            $("#plasmaParBarcode").val(data[0].psList)
            showAddPlasmaGroup()
        }
    });
}

function getSerumParentSpecimens(cid){
                  
    $.ajax({
        type: "GET",
        url: "/cahubdataservices/bpvBloodForm/getSerumParentSpecimens/" + cid,
        dataType: "jsonp",
        crossDomain: true,
        success: function(data) {
            $("#serumParBarcodeDisp").html(data[0].psList)
            $("#serumParBarcode").val(data[0].psList)
            showAddSerumGroup()
        }
    });
}

function getDnaParentSpecimens(cid){
                  
    $.ajax({
        type: "GET",
        url: "/cahubdataservices/bpvBloodForm/getDnaParentSpecimens/" + cid,
        dataType: "jsonp",
        crossDomain: true,
        success: function(data) {
            $("#dnaParBarCodeDisp").html(data[0].psList)
            $("#dnaParBarCode").val(data[0].psList)
        }
    });
}

function getRnaParentSpecimens(cid){
                  
    $.ajax({
        type: "GET",
        url: "/cahubdataservices/bpvBloodForm/getRnaParentSpecimens/" + cid,
        dataType: "jsonp",
        crossDomain: true,
        success: function(data) {
            $("#rnaParBarCodeDisp").html(data[0].psList)
            $("#rnaParBarCode").val(data[0].psList)
        }
    });
}



