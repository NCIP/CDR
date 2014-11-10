var caseIDNotFound = "This Case Record does not exist."
$(document).ready(function() {
    getSpecimenInventStatus("/cahubdataservices/caseRecord/getcaseinventoryfeed/?caseId="+$("#caseRecordID").html()+ "&callback=?");
});

function getSpecimenInventStatus(src) {
    $.ajax({
        type: 'GET',
        dataType: 'jsonp', 
        url: src
     }).done(function(data) {
        if(data.specimens){
            specimens = data.specimens;
            displayInventStatus(specimens);
        } 
     });
}

function displayInventStatus(specimenList){
    if (specimenList) {
        for (var i=0; i<specimenList.length; i++) {
         $("#"+specimenList[i].id+" .sp_status").addClass("sp-status-"+(specimenList[i].invent_status).toLowerCase()).attr("data-msg", "CBR Inventory Status: "+specimenList[i].invent_status).addClass("ui-ca-tooltip").html(specimenList[i].invent_status.toProperCase());
        }
        createTooltips();
    }
}