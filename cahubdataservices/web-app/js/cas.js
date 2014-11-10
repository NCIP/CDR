$(document).ready(function() {
    //focus username field
    $("input:visible:enabled:first").focus();
    if($("#msg").html() !=undefined ) {
        $("#message").html(authErrMessage);
        $("input:visible:enabled:first").focus();
    }

    fetchBulletinMessage();
    fetchAppVersion();
    fetchCBRIMSInfo();
    getFooter();
});

function validateForm(){
    var usr=document.getElementById("username").value;
    var pwd=document.getElementById("password").value;
    if (usr==null || usr.replace(/^\s+|\s+$/g, "")=="" || pwd==null || pwd.replace(/^\s+|\s+$/g, "")=="" ) {
        $("#message").html(authErrMessage);
        $("input:visible:enabled:first").focus();
        return false;
    }else{
         document.getElementById("username").value=usr.toLowerCase()
    }
}

function fetchBulletinMessage() {
    $.ajax({
        type: 'GET',
        dataType: 'json', 
        url: serverUrl+ '/dmzUtils/getRemoteLoginBulletin?callback=?'
    }).done(function(data) {
        if(data.length > 0) {
            var message = data[0].loginBulletin;
            if(message != null && message != 'bulletin app setting not found') {
               $("#bulletinmessage").html("<div class=\"ui-state-highlight ui-corner-all auto_center\"><span class=\"ui-icon ui-icon-info\"></span><div class=\"infobox\">" + message + "</div></div>")
            }
        }
    });
}

function fetchAppVersion() {
    $.ajax({
        type: 'GET',
        dataType: 'json', 
        url: serverUrl+ '/dmzUtils/getRemoteClientAppVersion?callback=?'
    }).done(function(data) {
        var version
        var milestone

        if(data.length > 0) {
            version = data[0].version;
            milestone = data[0].milestone;
        }

        if(version != null && $.trim(version)) {
            if(milestone != null && $.trim(milestone) != "") {
                milestone = "." +milestone
            }
            $("#verstext").html("caHUB CDR " +clientInitial+ " v" +version+ milestone);
        } else {
            $("#verstext").html("caHUB CDR " +clientInitial);
        }
    });
}

function fetchCBRIMSInfo() {
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: serverUrl+ '/dmzUtils/getRemoteClientCBRIMSInfo?callback=?'
    }).done(function(data) {
        var cbrIMSHost
        var cbrIMSName

        if(data.length > 0) {
            cbrIMSHost = data[0].cbrIMSHost;
            cbrIMSName = data[0].cbrIMSName;
        }

        if(cbrIMSHost != null && $.trim(cbrIMSHost) != "" &&
            cbrIMSName != null && $.trim(cbrIMSName) != "") {
            $("#cbrIMSInfoId").html("<a target='_blank' href='" +cbrIMSHost+ "'>Go to " +cbrIMSName+ " website</a>");
        } else {
            $("#cbrIMSInfoId").hide()
        }
    });
}

function getFooter(){
    var footerStr = "";
    footerStr += "<li class=\"footer_cahub\"><a target=\"_blank\" href=\"http://cahub.cancer.gov\" title=\"caHUB\" >caHUB</a></li>";
    footerStr += "<li class=\"footer_nci\"><a target=\"_blank\" href=\"http://www.cancer.gov/\" title=\"NCI - National Cancer Institute\">National Cancer Institute</a></li>";
    footerStr += "<li class=\"footer_leidos\"><a target=\"_blank\" href=\"http://www.leidos.com/about/companies/leidos-biomedical-research\" title=\"Leidos Biomedical Research Inc.\">Leidos Biomedical Research Inc.</a></li>";        
    footerStr += "<li class=\"footer_nih\"><a target=\"_blank\" href=\"http://www.nih.gov/\" title=\"NIH - National Institutes of Health\">National Institutes of Health</a></li>";
    footerStr += "<li class=\"footer_dop\"><a target=\"_blank\" href=\"http://www.hhs.gov/\" title=\"HHS - U.S. Department of Health &amp; Human Services\">U.S. Department of Health &amp; Human Services</a></li>";
    footerStr += "<li class=\"footer_usagov\"><a target=\"_blank\" href=\"http://www.usa.gov/\" title=\"USA.gov\">USA.gov</a></li>";
    $("#footer .footerlogos").html(footerStr);
}