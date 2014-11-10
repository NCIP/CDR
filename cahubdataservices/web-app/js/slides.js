	
$(document).ready(function() {
    var dataHTMLStr = "";
    var title = "Aperio Slide ID " + controllerData.slideid;
    /*
    dataHTMLStr += '<div class="row clearfix"><div class="label">Case ID:</div><div class="value"><a href="/cahubdataservices/caseRecord/show/' + controllerData.casenumid + '" title="View Case Record">' + controllerData.caseid + '</a></div></div>';
    dataHTMLStr += '<div class="row clearfix"><div class="label">Specimen ID:</div><div class="value"><a href="/cahubdataservices/specimenRecord/show/' + controllerData.specimennumid + '" title="View Specimen Record">' + controllerData.specimenid + '</a></div></div>';
    */
    dataHTMLStr += '<div class="row clearfix"><div class="label">Tissue Type:</div><div class="value">' + controllerData.tissuetype + '</div></div>';
    dataHTMLStr += '<div class="row clearfix"><div class="label">Tissue Location:</div><div class="value">' + controllerData.tissueloc + '</div></div>';
    dataHTMLStr += '<div class="row clearfix"><div class="label">Slide ID:</div><div class="value">' + controllerData.slideid + '</div></div>';
    dataHTMLStr += '<div class="row clearfix"><div class="label">Image ID:</div><div class="value">' + controllerData.imageid + '</div></div>';
    $("h1").html(title);
    $("#slidedata").html(dataHTMLStr);
    var viewer = OpenSeadragon({
        id: "openseadragon",
        prefixUrl: "../js/openseadragon-bin-1.1.1/images/",
        tileSources: APERIOIMGLOC + "/" + controllerData.caseid + "/" + controllerData.slideid + ".dzi"
    });
});
