var caseIDNotFound = "This Case Record does not exist."
var caseValidationUrl= "/cahubdataservices/caseRecord/getcaseid";
$(document).ready(function(){ 
    $( "#ca-dialog" ).dialog({
	autoOpen: false,
        resizable: true,
        height:135,
        width:400,
        modal: true,
        buttons: {
            "Confirm and Change": function() {
                var idToValidate = $("#ca-dialog input").val();
                $.ajax({
                    type: 'POST',
                    dataType: 'jsonp',
                    data: {
                        id: idToValidate
                    },
                    url: caseValidationUrl
                 }).done(function(data) {
                     if(data.casesysid != ""){
                        $("#caseRecordID").val( data.casesysid );
                        $("#caseid").html( idToValidate );
                        $("#ca-dialog .redtext").html("");
                        $("#ca-dialog").dialog( "close" );
                     } else {
                        $("#ca-dialog .redtext").html(caseIDNotFound);
                     }
                 });
            },
            Cancel: function() {
                $("#ca-dialog .redtext").html("");
                $( this ).dialog( "close" );
            }
        }
    });

    $("#changeRecordID").click(function() {
        $("#ca-dialog").dialog("option", "title","Change Case Record ID");
        $("#ca-dialog").html("<input type='text' value='' /> <span class='redtext'></span>");
        $("#ca-dialog").dialog("open");
    });
 });