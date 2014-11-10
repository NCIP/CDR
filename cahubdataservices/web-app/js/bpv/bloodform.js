
var topClassStrActive = "ui-state-default ui-corner-top ui-tabs-selected ui-state-active";
var topClassStrInActive = "ui-state-default ui-corner-top";
var tabsDivClass = "hide ui-tabs ui-widget ui-corner-all";
var tabsUlClass = "ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all";
var availableTissueTypes = ["Main","DNA","RNA","Plasma","Serum"];
var tabsListStr = "";
var activeTabContent = null;
var userSetTab = false;
var activeType = "";
var ptTableRows = 0;
var bpvErrorDict = [];
var originalctId; 
var origAddParentHtml; 
var needSaveNow = false;
var currentSpecSelect = "";

bpvErrorDict[ "bpvErr2"] = "<a href = \"#bloodMinimum_yes\" class=\"tabs-main\" title=\"Go to this question\">Please select an option for question #2</a>";
bpvErrorDict[ "bpvErr3"] = "<a href = \"#bloodDrawType\" class=\"tabs-main\" title=\"Go to this question\">Please select an option from the list for question #3</a>";
bpvErrorDict[ "bpvErr4"] = "<a href = \"#bloodDrawType\" class=\"tabs-main\" title=\"Go to this question\">Please specify the answer in the Other box of question #3</a>";
bpvErrorDict[ "bpvErr5"] = "<a href = \"#dateTimeBloodDraw\" class=\"tabs-main\" title=\"Go to this question\">Please enter a date for question #4</a>";
bpvErrorDict[ "bpvErr6"] = "<a href = \"#bloodDrawNurse\" class=\"tabs-main\" title=\"Go to this question\">Please select an option from the list for question #5</a>";
bpvErrorDict[ "bpvErr7"] = "<a href = \"#bloodDrawNurse\" class=\"tabs-main\" title=\"Go to this question\">Please specify the answer in the Other box of question #5</a>";
bpvErrorDict[ "bpvErr8"] = "<a href = \"#bloodDrawNurseName\" class=\"tabs-main\" title=\"Go to this question\">Please enter the name of the person who collected the blood sample</a>";
bpvErrorDict[ "bpvErr9"] = "<a href = \"#ParentTubes\" class=\"tabs-main\" title=\"Go to this question\">You have not added the Parent blood tubes yet</a>";
bpvErrorDict[ "bpvErr10"] = "<a href = \"#ParentTubes\" class=\"tabs-main\" title=\"Go to this question\">Please add the required RNA PAXgene tube</a>";
bpvErrorDict[ "bpvErr11"] = "<a href = \"#ParentTubes\" class=\"tabs-main\" title=\"Go to this question\">Please add the required DNA PAXgene tube</a>";
bpvErrorDict[ "bpvErr12"] = "<a href = \"#bloodSourceOs\" class=\"tabs-main\" title=\"Go to this question\">Please specify the answer in the Other box of question #6</a>";
bpvErrorDict[ "bpvErr13"] = "<a href = \"#dateTimeBloodReceived\" class=\"tabs-main\" title=\"Go to this question\">Please enter a date/time for question #8</a>";
bpvErrorDict[ "bpvErr14"] = "<a href = \"#dnaPaxFrozen\" class=\"tabs-dna\" title=\"Go to this question\">Please enter a date/time for question #13</a>";
bpvErrorDict[ "bpvErr15"] = "<a href = \"#dnaPaxStorage\" class=\"tabs-dna\" title=\"Go to this question\">Please enter a date/time for question #14</a>";
bpvErrorDict[ "bpvErr16"] = "<a href = \"#dnaPaxProcTech\" class=\"tabs-dna\" title=\"Go to this question\">Please select an option for question #15</a>";
bpvErrorDict[ "bpvErr17"] = "<a href = \"#dnaPaxProcSopDev_yes\" class=\"tabs-dna\" title=\"Go to this question\">Please select an option for question #16</a>";
bpvErrorDict[ "bpvErr18"] = "<a href = \"#dnaPaxProcComments\" class=\"tabs-dna\" title=\"Go to this question\">DNA processing comments is required";
bpvErrorDict[ "bpvErr19"] = "<a href = \"#rnaPaxFrozen\" class=\"tabs-rna\" title=\"Go to this question\">Please enter a date/time for question #20</a>";
bpvErrorDict[ "bpvErr20"] = "<a href = \"#rnaPaxStorage\" class=\"tabs-rna\" title=\"Go to this question\">Please enter a date/time for question #21</a>";
bpvErrorDict[ "bpvErr21"] = "<a href = \"#rnaPaxProcTech\" class=\"tabs-rna\" title=\"Go to this question\">Please select an option for question #22</a>";
bpvErrorDict[ "bpvErr22"] = "<a href = \"#rnaPaxProcSopDev_yes\" class=\"tabs-rna\" title=\"Go to this question\">Please select an option for question #23</a>";
bpvErrorDict[ "bpvErr23"] = "<a href = \"#rnaPaxProcComments\" class=\"tabs-rna\" title=\"Go to this question\">RNA processing comments is required</a>";
bpvErrorDict[ "bpvErr24"] = "<a href = \"#PlasmaTubes\" class=\"tabs-plasma\" title=\"Go to this question\">Please add at least one Plasma aliquot since you have added a Plasma blood Specimen collected in a Lavender EDTA Tube</a>";
bpvErrorDict[ "bpvErr25"] = "<a href = \"#plasmaProcSopDev_yes\" class=\"tabs-plasma\" title=\"Go to this question\">Please select an option for question #34</a>";
bpvErrorDict[ "bpvErr26"] = "<a href = \"#plasmaProcComments\" class=\"tabs-plasma\" title=\"Go to this question\">Plasma processing comments is required</a>";
bpvErrorDict[ "bpvErr27"] = "<a href = \"#SerumTubes\" class=\"tabs-serum\" title=\"Go to this question\">Please add at least one Serum aliquot since you have added a Serum blood Specimen collected in a Serum Separator Tube</a>";
bpvErrorDict[ "bpvErr28"] = "<a href = \"#serumProcSopDev_yes\" class=\"tabs-serum\" title=\"Go to this question\">Please select an option for question #44</a>";
bpvErrorDict[ "bpvErr29"] = "<a href = \"#serumProcComments\" class=\"tabs-serum\" title=\"Go to this question\">Serum processing comments is required</a>";
bpvErrorDict[ "bpvErr30"] = "<a href = \"#dnaPaxStorage\" class=\"tabs-dna\" title=\"Go to this question\">Time when DNA PAXgene tube was transferred to storage (question #14) cannot precede the time it was frozen (question #13)</a>";
bpvErrorDict[ "bpvErr31"] = "<a href = \"#rnaPaxStorage\" class=\"tabs-rna\" title=\"Go to this question\">Time when RNA PAXgene tube was transferred to storage (question #21) cannot precede the time it was frozen (question #20)</a>";
bpvErrorDict[ "bpvErr32"] = "<a href = \"#addPlasmaCTBtn\" class=\"tabs-plasma\" title=\"Go to this question\">Please add the Conical Centrifuge tube that was derived from the plasma parent specimen</a>";
bpvErrorDict[ "bpvErr33"] = "<a href = \"#dateTimeBloodDraw\" class=\"tabs-main\" title=\"Go to this question\">Date and Time Blood was Drawn cannot be in the future</a>";
bpvErrorDict[ "bpvErr34"] = "<a href = \"#dateTimeBloodReceived\" class=\"tabs-main\" title=\"Go to this question\">Date and Time blood received in the lab cannot precede the Date and Time Blood was Drawn</a>";
bpvErrorDict[ "bpvErr35"] = "<a href = \"#dateTimeBloodReceived\" class=\"tabs-main\" title=\"Go to this question\">Date and Time blood received in the lab cannot be in the future</a>";
bpvErrorDict[ "bpvErr36"] = "<a href = \"#rnaPaxFrozen\" class=\"tabs-rna\" title=\"Go to this question\">Time RNA PAXgene tube was frozen cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr37"] = "<a href = \"#rnaPaxFrozen\" class=\"tabs-rna\" title=\"Go to this question\">Time RNA PAXgene tube was frozen cannot be in the future</a>";
bpvErrorDict[ "bpvErr38"] = "<a href = \"#rnaPaxStorage\" class=\"tabs-rna\" title=\"Go to this question\">Time RNA PAXgene tube was transferred to storage cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr39"] = "<a href = \"#rnaPaxStorage\" class=\"tabs-rna\" title=\"Go to this question\">Time RNA PAXgene tube was transferred to storage cannot be in the future</a>";
bpvErrorDict[ "bpvErr40"] = "<a href = \"#dnaPaxFrozen\" class=\"tabs-dna\" title=\"Go to this question\">Time DNA PAXgene tube was frozen cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr41"] = "<a href = \"#dnaPaxFrozen\" class=\"tabs-dna\" title=\"Go to this question\">Time DNA PAXgene tube was frozen cannot be in the future</a>";
bpvErrorDict[ "bpvErr42"] = "<a href = \"#dnaPaxStorage\" class=\"tabs-dna\" title=\"Go to this question\">Time DNA PAXgene tube was transferred to storage cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr43"] = "<a href = \"#dnaPaxStorage\" class=\"tabs-dna\" title=\"Go to this question\">Time DNA PAXgene tube was transferred to storage cannot be in the future</a>";
bpvErrorDict[ "bpvErr44"] = "<a href = \"#plasmaProcStart\" class=\"tabs-plasma\" title=\"Go to this question\">Time Plasma processing began cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr45"] = "<a href = \"#plasmaProcStart\" class=\"tabs-plasma\" title=\"Go to this question\">Time Plasma processing began cannot be in the future</a>";
bpvErrorDict[ "bpvErr46"] = "<a href = \"#plasmaProcEnd\" class=\"tabs-plasma\" title=\"Go to this question\">Time Plasma aliquot processing was completed cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr47"] = "<a href = \"#plasmaProcEnd\" class=\"tabs-plasma\" title=\"Go to this question\">Time Plasma aliquot processing was completed cannot be in the future</a>";
bpvErrorDict[ "bpvErr48"] = "<a href = \"#plasmaProcFrozen\" class=\"tabs-plasma\" title=\"Go to this question\">Time Plasma aliquots were frozen cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr49"] = "<a href = \"#plasmaProcFrozen\" class=\"tabs-plasma\" title=\"Go to this question\">Time Plasma aliquots were frozen cannot be in the future</a>";
bpvErrorDict[ "bpvErr50"] = "<a href = \"#plasmaProcStorage\" class=\"tabs-plasma\" title=\"Go to this question\">Time Plasma aliquots were transferred to storage cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr51"] = "<a href = \"#plasmaProcStorage\" class=\"tabs-plasma\" title=\"Go to this question\">Time Plasma aliquots were transferred to storage cannot be in the future</a>";
bpvErrorDict[ "bpvErr52"] = "<a href = \"#serumProcStart\" class=\"tabs-serum\" title=\"Go to this question\">Time Serum processing began cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr53"] = "<a href = \"#serumProcStart\" class=\"tabs-serum\" title=\"Go to this question\">Time Serum processing began cannot be in the future</a>";
bpvErrorDict[ "bpvErr54"] = "<a href = \"#serumProcEnd\" class=\"tabs-serum\" title=\"Go to this question\">Time Serum aliquot processing was completed cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr55"] = "<a href = \"#serumProcEnd\" class=\"tabs-serum\" title=\"Go to this question\">Time Serum aliquot processing was completed cannot be in the future</a>";
bpvErrorDict[ "bpvErr56"] = "<a href = \"#serumProcFrozen\" class=\"tabs-serum\" title=\"Go to this question\">Time Serum aliquots were frozen cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr57"] = "<a href = \"#serumProcFrozen\" class=\"tabs-serum\" title=\"Go to this question\">Time Serum aliquots were frozen cannot be in the future</a>";
bpvErrorDict[ "bpvErr58"] = "<a href = \"#serumProcStorage\" class=\"tabs-serum\" title=\"Go to this question\">Time Serum aliquots were transferred to storage cannot precede the Date and Time blood was received in the lab</a>";
bpvErrorDict[ "bpvErr59"] = "<a href = \"#serumProcStorage\" class=\"tabs-serum\" title=\"Go to this question\">Time Serum aliquots were transferred to storage cannot be in the future</a>";
bpvErrorDict[ "bpvErr60"] = "<a href = \"#dateTimeBloodDraw2\" class=\"tabs-main\" title=\"Go to this question\">Second Date and Time Blood was Drawn cannot be in the future</a>";
bpvErrorDict[ "bpvErr61"] = "<a href = \"#dateTimeBloodReceived\" class=\"tabs-main\" title=\"Go to this question\">Date and Time blood received in the lab cannot precede the Second Date and Time Blood was Drawn</a>";
bpvErrorDict[ "bpvErr62"] = "<a href = \"#dateTimeBloodDraw2\" class=\"tabs-main\" title=\"Go to this question\">Second Date and Time Blood was Drawn cannot precede the (First) Date and Time Blood was Drawn</a>";
bpvErrorDict[ "bpvErr63"] = "<a href = \"#bloodDrawNurse2\" class=\"tabs-main\" title=\"Go to this question\">Please select an option from the list for the person that performed the second blood draw</a>";
bpvErrorDict[ "bpvErr64"] = "<a href = \"#bloodDrawNurse2\" class=\"tabs-main\" title=\"Go to this question\">Please specify the answer in the Other box for the person that performed the second blood draw</a>";
bpvErrorDict[ "bpvErr65"] = "<a href = \"#bloodDrawNurseName2\" class=\"tabs-main\" title=\"Go to this question\">Please enter the name of the person who collected the blood sample for the second draw</a>";
bpvErrorDict[ "bpvErr66"] = "<a href = \"#dateTimeBloodDraw2\" class=\"tabs-main\" title=\"Go to this question\">Please enter a date for the second blood draw</a>";
bpvErrorDict[ "bpvErr67"] = "<a href = \"#dnaParBarCodeSelect\" class=\"tabs-dna\" title=\"Go to this question\">Please select the DNA PAXgene tube Specimen barcode ID</a>";
bpvErrorDict[ "bpvErr68"] = "<a href = \"#rnaParBarCodeSelect\" class=\"tabs-rna\" title=\"Go to this question\">Please select the RNA PAXgene tube Specimen barcode ID</a>";
bpvErrorDict[ "bpvErr69"] = "<a href = \"#dateTimeBloodReceived2\" class=\"tabs-main\" title=\"Go to this question\">Please enter a date/time for the Date and time second blood draw was received in lab</a>";
bpvErrorDict[ "bpvErr70"] = "<a href = \"#dateTimeBloodReceived2\" class=\"tabs-main\" title=\"Go to this question\">Date and time second blood draw was received in lab cannot precede the Second date and Time Blood was Drawn</a>";
bpvErrorDict[ "bpvErr71"] = "<a href = \"#dateTimeBloodReceived2\" class=\"tabs-main\" title=\"Go to this question\">Date and time second blood draw was received in lab cannot be in the future</a>";


$(document).ready(function(){
    originalctId = $("#ctIdHTML").html();
    origAddParentHtml = $( "#add-parent" ).html();
    bloodformPopUps();
    showErrors();
    updateParBarCodeDisp();
    syncDrawDateKeys();
    
    ptTableRows = 0;
    if (document.getElementById("PlasmaTubes") != null){
        ptTableRows = document.getElementById("PlasmaTubes").getElementsByTagName("tr").length;
    }
    
    $("#bloodMinimum_no").click(function() {
        $('#tabs-main tr.bottomsection').hide(); 
        $('#tabs-main table.islung #blooddrawcomments').show();
        $('#tabs-list').hide(); 
        $('#bloodform-tabs').removeClass('active');
    });
    $("#bloodMinimum_yes").click(function() {
         $('#tabs-main tr.bottomsection').show();
         showhideOthDT(); 
         showhideOthNurse();
         showhideOthSource();
         showhideBloodNurseName();  
         hidedraw2();
        $('#tabs-list').show(); 
         buildTabsOnTable();  //activate tabbed experience 
         $('#bloodform-tabs').addClass('active');             
    });
    $(".edit #bloodMinimum_yes").after(function() {
       if( $(this).attr("checked")) { 
         $('#tabs-main tr.bottomsection').show(); 
         showhideOthDT(); 
         showhideOthNurse();
         showhideOthSource();
         showhideBloodNurseName();
         hidedraw2();
         buildTabsOnTable();  //activate tabbed experience
         $('#bloodform-tabs').addClass('active');
       } else {
         $('#bloodform-tabs').removeClass('active');
         $('#tabs-main tr.bottomsection').hide(); 
         $('#tabs-main table.islung #blooddrawcomments').show();
       }
       $("body.bpvbloodform").show();
    });
    
    $(".show #bloodMinimum_yes").after(function() {
       if( $(this).attr("checked")) {
         $('#tabs-main tr.bottomsection').show(); 
         showhideOthDT(); 
         showhideOthNurse();
         showhideOthSource();
         showhideBloodNurseName();
         hidedraw2();
         $('#bloodform-tabs').addClass('active');
       } else {
         $('#bloodform-tabs').removeClass('active');
         $('tr.bottomsection').hide(); 
         $('#tabs-main table.islung #blooddrawcomments').show();
       }
       $("body.bpvbloodform").show();
    });

    $('#addPlasmaBtn').after(function() {
        showAddPlasmaGroup();
    });
    
    $( "#dialog-confirm" ).dialog({
        resizable: false,
        height:160,
        width:400,
        modal: true,
        buttons: {
            "Clear related entries": function() {
                $( this ).dialog( "close" );
                clearData (currentSpecSelect);
                currentSpecSelect = "";
            },
            "Don't clear entries": function() {
                $( this ).dialog( "close" );
                currentSpecSelect = "";
            },
            Cancel: function() {
                $( this ).dialog( "close" );
                var selectDropDown = $("#" + currentSpecSelect + "ParBarCodeSelect");
                var originalVal = $("#pageload" + currentSpecSelect + "ParBarCode").val();
                selectDropDown.val(originalVal);
                $("#" + currentSpecSelect + "ParBarCode").val(originalVal);
                currentSpecSelect = "";
                selectDropDown.closest('td').removeClass("D2-draw");
                selectDropDown.closest('td').addClass($("option:selected", selectDropDown).attr("class"));
            }
        }
    });

    $(".saveNContinue").click(function() {
        if($("#loc").val().length === 0) {
            $("#loc").val("tabs-plasma");  
        }
    });

     //adds reset button after plasmaCTBarcode field
    $('#plasmaCTBarcode').after(function() {
        return "<input class=\"button reset ui-button ui-widget ui-state-default ui-corner-all\"  readonly=\"readonly\" value=\"Reset\" id=\"plasmaCTBarcodeReset\" />";
    });
    
    $('.bpvbloodform #container .reset').click(function() {
        $('#plasmaCTBarcode').val($('#origPlasmaCTBarcode').val());
        $('#plasmaCTVol').val($('#origPlasmaCTVol').val());
        $(this).hide();
        showPlasmaContinue();
    });
    
    $("#bloodDraw2").change(function() {
        $("#bloodDrawNurseOsRow2").hide();
        if (!$(this).is(":checked") && ($("#dateTimeBloodDraw2_day").val().length != 0 || $("#bloodDrawNurse2").val().length != 0 || $("#bloodDrawNurseOs2").val().length != 0 || $("#bloodDrawNurseName2").val().length != 0)) { 
            if(!confirm("You are about to clear all information entered for the second draw.")){
                $(this).prop('checked', true);
                $(".D2-draw").show();
                if( $("#bloodDrawNurse2").val() !== "OtherSpecify") {
                    $("#bloodDrawNurseOsRow2").hide();
                }
            } else {
                $(".D2-draw input:text").not(".D2-draw input.cleardate:text").val("");
                $(".D2-draw select").val("");
                $(".D2-draw .cleardate").trigger("click");
                $("#loc").val("bloodDraw2");
                buildTabsOnTable();
                
            }
        }
    });
    
    if(previousLoc.length > 0 && $("#bloodform-"+previousLoc).attr("id") != undefined && $("#"+previousLoc).attr("id") != undefined ){
        setBloodTab($("#"+previousLoc));
        scrollTo("#bloodform-"+previousLoc);
    }
    if(previousLoc === "ParentTubes"){
        scrollTo("#ParentTubes");
    }

    $("#dateTimeBloodDraw_changeTime").change(function() {
        $("#dateTimeBloodDrawRow").removeClass("warnings redtext");
        syncDrawDateKeys();
    });
    $("#dateTimeBloodDraw2_changeTime").change(function() {
        if($("#dateTimeBloodDraw2_day").val().length != 0) {
            $("#drawTimesKey-d2").html("D2 - <span>" + $("#dateTimeBloodDraw2").val() + "</span>");
        } else {
            $("#drawTimesKey-d2").html("");
        }
        buildTabsOnTable();
    });
    
    $("#bloodReceiptTemp").AllowNumericOnlyWithDecimal();
    $("#bloodReceiptHumidity").AllowNumericOnlyWithDecimal();
    $("#volume").AllowNumericOnlyWithDecimal();
    $("#volumeEd").AllowNumericOnlyWithDecimal();
    $("#volumePl").AllowNumericOnlyWithDecimal();
    $("#volumePlEd").AllowNumericOnlyWithDecimal();
    $("#volumeCT").AllowNumericOnlyWithDecimal();
    $("#volumeCTEd").AllowNumericOnlyWithDecimal();
    $("#volumeSrm").AllowNumericOnlyWithDecimal();
    $("#volumeSrmEd").AllowNumericOnlyWithDecimal();

    seekPermissionToDelPlasmaG3();
});
    
$('#serumParBarcodeDisp').change(function() { 
    var barCode = $('#serumParBarcodeDisp').val();
    $('#serumParBarcode').val(barCode);
});              
           
/*
 * Set active tab: takes in dom element of the content that should be active, first resets everything visually,
 *  then displays correct content and activates correct tab.
 */
function setBloodTab( bTypeDom ) {
    var ttype;
    for(var i=0; i < availableTissueTypes.length; i++){
      var ttype = availableTissueTypes[i].toLowerCase();
      $( "#tabs-" + ttype).attr("class","hide");
      if($( "#bloodform-tabs-" + ttype)){
        $( "#bloodform-tabs-" + ttype).attr("class",topClassStrInActive);
      }
    }
    if( bTypeDom != null){
        $(bTypeDom.attr("id").replace("tabs-","#tabs-")).removeClass("hide");  
        $(bTypeDom.attr("id").replace("tabs-","#bloodform-tabs-")).attr("class",topClassStrActive); 
    } else {
        $("#tabs-main").removeClass("hide");  
        $("#bloodform-tabs-main").attr("class",topClassStrActive); 
    }
}


/*
 * Build tabs based on parent table content: Goes through the table looking for tissue types, then goes through
 * possible available types and creates mark up for tab for each that exist in the table. Also decides on which tab should be active 
 * and turns correct tab on.
 */
function buildTabsOnTable(){  
    var activeTissueTypes = [];
    var currentTissue;
    var tableRowCount = 1;
    var rnaCnt = 0;
    var dnaCnt = 0;
    var plasmaCnt = 0;
    var serumCnt = 0; 
       
    $("#ctIdHTML").html(originalctId); //reset menu
    $("#code").after(function() {
        $(this).attr("disabled","disabled");               
    });
    
    activeTabContent = null;
    $( "#bloodform-tabs" ).hide();
    $("#tabs-list").html("");

    $(".tissueType").each(function() {
       
      tableRowCount++;
      currentTissue = $(this).html().toLowerCase();

      //only allow 1 plasma and 1 serum and 2 of the rest
      if( currentTissue === "plasma" ) {
        plasmaCnt++;
        $("#ctId").option("LAVEDTA").remove();
      } else if( currentTissue === "serum" ) {
        serumCnt++;
        $("#ctId").option("SST").remove();
      }
      
      if( $(".gen1.D2-draw").length > 0 ){
        disable2drawChkbx();
      }
      
      activeTissueTypes[$(this).html().toLowerCase()]==undefined?activeTissueTypes[currentTissue]=1:activeTissueTypes[currentTissue]++;
    });
    
    setChangeCtId();     

    if (tableRowCount > 0) {    
        tabsListStr = "<li id=\"bloodform-tabs-main\"><a href=\"#tabs-main\" name=\"tabs-main\">Main</a></li>";
        for(var i=0; i < availableTissueTypes.length; i++){
           currentTissue = availableTissueTypes[i].toLowerCase();
           $( "#tabs-" + currentTissue ).attr( "class", "hide"); 
           if(activeTissueTypes[currentTissue]!=undefined){
              if((activeTabContent == null && userSetTab == false) || (activeTabContent == null && activeType === "")){
                activeTabContent = $("#tabs-main");
                userSetTab = true;
              } else if(activeTabContent == null && userSetTab == true){
                activeTabContent = $("#tabs-" + activeType); 
              }
              tabsListStr += ("<li id=\"bloodform-tabs-" + currentTissue +"\"><a href=\"#tabs-" + currentTissue +"\" name=\"tabs-" + currentTissue +"\">" + availableTissueTypes[i] +"</a></li>"); 
           } 
         }
         if(document.getElementById("ParentTubes") != null) {
            tableRows = document.getElementById("ParentTubes").getElementsByTagName("tr");
            var className = "";
            for(var y=1; y < tableRows.length; y++){ 
               if(tableRows[y].id && tableRows[y].id.indexOf("gen1row") > -1) {
                   if( className === "" ){
                      className = "odd";
                      tableRows[y].className = tableRows[y].className + " " + className;
                   } else {
                      className = ""; 
                   }
               }
            }
         }
         
         $( "#tabs-list").html(tabsListStr);
         $( "#bloodform-tabs" ).show();
         $( "#bloodform-tabs" ).attr("class", tabsDivClass);
         $( "#bloodform-tabs #tabs-list" ).attr("class", tabsUlClass);

         $( "#bloodform-tabs" ).show();
         setBloodTab(activeTabContent);
         activeType = "";

        $("#tabs-list a").click(function() {
            $("#ParentTubes .active").removeClass("active");
            closeGen1Gen2();
            setBloodTab($(($(this).closest("li").attr('id')).replace("bloodform-tabs-","#tabs-"))); 
            return false;
        });
        
        $("#plasmaCTBarcodeDel").click(function() {
            if(ptTableRows > 1){
                $("#PlasmaTubes .redtext").removeClass("hide");
                scrollTo("#PlasmaTubes");
            }
        });
        
    }
    addParentTableTrListenner();
}

function plasmaCTBarcodeDel(confirm) {
   clearData ("plasma");
   if( confirm == "confirm") {
       $( "#dialog-confirm" ).dialog( "open" );
       $("#plasmaCTBarcode").val("");
       $( "#plasmaCTVol" ).val( "0.0" );
       $(".plasmaBarcodeGroup").addClass("hide");
       $(".tabs-plasma .tab-table").hide();
       $(".saveNContinue .button input.save").trigger("click");
   } else {
       $("#plasmaCTBarcode").val("");
       $( "#plasmaCTVol" ).val( "0.0" );
       $(".plasmaBarcodeGroup").addClass("hide");
       $(".tabs-plasma .tab-table").hide();
       $(".saveNContinue .button input.save").trigger("click");
   }
}

function permissionToAllowParentsDel() {
    if( $(".edit .gen1-plasma").length < 2 && (($(".edit .gen3-plasma-parent").length == 0 && $(".edit .gen2-plasma-parent").length == 0)) ){
        $("#ParentTubes tr.gen1-plasma .deleteOnly").css("visibility","visible");
    } else {
        $("#ParentTubes tr.gen1-plasma .deleteOnly").css("visibility","hidden");
    }
    if( $(".edit .gen1-plasma").length < 2 && (($(".edit .gen3-plasma-parent").length == 0 && $(".edit .gen2-plasma-parent").length == 1)) ){
        $("#ParentTubes tr.gen2-plasma-parent .deleteOnly").css("visibility","visible");
    } else {
        $("#ParentTubes tr.gen2-plasma-parent .deleteOnly").css("visibility","hidden");
    }
    if( $(".edit .gen1-serum").length < 2 && $(".edit .gen2-serum-parent").length == 0){
        $("#ParentTubes tr.gen1-serum .deleteOnly").css("visibility","visible");
    } else {
        $("#ParentTubes tr.gen1-serum .deleteOnly").css("visibility","hidden");
    }
}

/*
 * Controls what happens when you click on a parent table tr
 */
function addParentTableTrListenner(){
    $(".edit #ParentTubes tr.gen1").click(function() {
        $("#ParentTubes table tr.active").removeClass("active");
        $(this).addClass("active");
        closeGen1Gen2();
        $("."+$(this).attr("id")).show();
    });
    
    $("#ca-dialog").hide();
    permissionToAllowParentsDel();
    updateParBarCodeDisp();
}

function closeGen1Gen2(){
    $(" #ParentTubes .gen2").hide();
    $(" #ParentTubes .gen3").hide();
}

function addParentTableWithConicalTube(){
    var tubeBarCode = document.getElementById("plasmaCTBarcode").value;
    var tubeVolume = document.getElementById("plasmaCTVol").value;
    if( tubeBarCode.length > 0 && $(".gen2-plasma-parent").length === 0){
        var lastParentRow;
        var rowHTML;
        lastParentRow = $('.gen1-plasma').eq(0);
        
        rowHTML = '<tr class="gen2 ' + $(lastParentRow).attr("id") + ' gen2-plasma-parent" id="';
        rowHTML += 'gen2-tube-parent"><td></td><td class="ui-state-default"><span class="ui-icon ui-icon-arrowreturnthick-1-e"></span>';
        rowHTML += tubeBarCode + '</td><td>Conical Tube</td><td class="tissueType">Plasma</td>';
        rowHTML += '<td class="textcenter">' + tubeVolume + ' ml</td>';
        rowHTML += '<td class="textcenter editOnly">';
        rowHTML += '<a title="Edit" href="" onClick="return false;" class="editOnly button ui-button  ui-state-default ui-corner-all removepadding">';
        rowHTML += '<span class="ui-icon ui-icon-pencil">Edit</span></a>';
        rowHTML += '</td></tr>';
        $(rowHTML).insertAfter(lastParentRow);
        $('.plasmaCTBarcodeDel').appendTo('#gen2-tube-parent td.editOnly');
    }
} 
/*
 * When aliquots are added to plasma or serum, after ajaxing is done update Parent Table
 */
function addParentTableWithAliquot(aType, gen){
    if ($("#" + capitalize(aType) + "Tubes .errors").length === 0) {
        var lastParentRow;
        var aSiblingsCnt;
        var aparentSiblingsCnt;

        var lastParentRowClass;
        var lastParentRowStyle;

        aSiblingsCnt = $('.gen' + gen + '-' + aType + '-parent').size();
        //grab the last type gen row in parent table
        if ( aSiblingsCnt > 0 ) {
            lastParentRow = $('.gen' + gen + '-' + aType + '-parent').eq(aSiblingsCnt-1);
            lastParentRowClass = $(lastParentRow).attr("class");
            lastParentRowStyle = $(lastParentRow).attr("style");
        } else {
            aParentSiblingsCnt = $('.gen' + (gen - 1) + '-' + aType + '-parent' ).size();
            if ( aParentSiblingsCnt > 0) {
                lastParentRow = $('.gen' + (gen - 1) + '-' + aType + '-parent' ).eq(aParentSiblingsCnt-1);
                var re = new RegExp("gen" + (gen - 1),"g");
                lastParentRowClass = $(lastParentRow).attr("class").replace(re, (' gen' + gen));
                lastParentRowStyle = $(lastParentRow).attr("style");
            } else {
                lastParentRow = $('#ParentTubes table tr.gen1-' + aType);
                lastParentRowClass = "gen" + gen + " " + lastParentRow.attr("id") + " gen" + gen + "-" + aType + "-parent";
            }
        }

        //get content from tabbed table
        var addedRowID = $('.gen' + gen + '-' + aType ).eq($('.gen' + gen + '-' + aType ).size()-1).attr("id");
        var specimenIdAl = $('#'+addedRowID+' span.specimenIdAl').html();
        var volumeAl = $('#'+addedRowID+' td.volumeAl').html().replace(" ml","");
        var actionContent = $('#'+addedRowID+' td.editOnly').html();

        //add new row to Parent Table
        var rowHTML = '<tr id="' + addedRowID +'-parent" class="' + lastParentRowClass + '" style="' + lastParentRowStyle + '"><td></td><td class="ui-state-default"><span class="ui-icon ui-icon-arrowreturnthick-1-e"></span><span class="specimenIdAl">' + specimenIdAl + '</span></td><td>Cryovial</td><td class="tissueType">' + aType + '</td><td class="volumeAl textcenter">' + volumeAl + ' ml</td><td class="textcenter editOnly">' + actionContent + '</td></tr>';
        $(rowHTML).insertAfter(lastParentRow);

        if (aType == "plasma" && gen == 3) {
           ptTableRows++; 
           if ( ptTableRows === 3) {
                $("#ParentTubes .gen2-plasma-parent .deleteOnly").css("visibility","hidden");
                $("#plasmaCTBarcodeDel").removeClass("hide");
                $(".plasmaCTBarcodeDel").addClass("hide");
           }
        }
        if( $(lastParentRow).hasClass("active") ) {
            $("#" + addedRowID + "-parent").show();
        }
        seekPermissionToDelPlasmaG3();
        permissionToAllowParentsDel();
    }
    $("#ca-dialog").hide();
}

function seekPermissionToDelPlasmaG3() {
   if (ptTableRows === 1) {
      $("#ParentTubes .gen2-plasma-parent .deleteOnly").css("visibility","visible");
       $(".plasmaCTBarcodeDel").removeClass("hide");
       $("#plasmaCTBarcodeDel").addClass("hide");
   } else {
      $("#ParentTubes .gen2-plasma-parent .deleteOnly").css("visibility","hidden");
       $(".plasmaCTBarcodeDel").addClass("hide");
       $("#plasmaCTBarcodeDel").removeClass("hide");  
   }
}

function uiDelAliquot(rowID, gen, activeateRows){
  var parentTableRowId = "gen" + gen + "-" + rowID + "-parent";
  var delRow = document.getElementById(parentTableRowId);
  var plasmaClass = "gen3-plasma-parent";
  if ( $("#" + parentTableRowId).hasClass(plasmaClass) ) {
      ptTableRows--; 
      seekPermissionToDelPlasmaG3();
      delRow.parentNode.removeChild(delRow);
  } else {
      delRow.parentNode.removeChild(delRow);
  }
  
  if (activeateRows != null) {
      $("#"+activeateRows).addClass("active");
      $("."+activeateRows).show();
  }
  permissionToAllowParentsDel();
  $("#ca-dialog").hide();
}

function uiEditAliquot(gen){
  var parentTableRowId;
  if(gen == 2) {
    parentTableRowId = "gen" + gen + "-" + $("#specSerumId").val() + "-parent";
    $("#"+parentTableRowId+" .volumeP").html($("#volumeSrmEd" ).val() + " ml");
  } else if(gen == 3) {
    parentTableRowId = "gen" + gen + "-" + $("#specPlasmaId").val() + "-parent";
    $("#"+parentTableRowId+" .volumeP").html($("#volumePlEd" ).val() + " ml");
  }
}

function showhideOthDT() {
    var selectedValue = $("#bloodDrawType").val();
    if (selectedValue == 'Other (specify)') {
        $('#bloodDrawTypeOsRow').show();
    } else {
        $('#bloodDrawTypeOs').val('')
        $('#bloodDrawTypeOsRow').hide();
    }
}

function showhideOthNurse() {
    var selectedValue = $("#bloodDrawNurse").val();
    if (selectedValue == 'OtherSpecify') {
        $('#bloodDrawNurseOsRow').show();
    } else {
        $('#bloodDrawNurseOs').val('')
        $('#bloodDrawNurseOsRow').hide();
    }
}

function showhideOthSource() {
    var selectedValue = $("#bloodSource").val();
    if (selectedValue == 'Other (specify)') {
        $('#bloodSourceOsRow').show();
    } else {
        $('#bloodSourceOs').val('')
        $('#bloodSourceOsRow').hide();
    }
}
                   
function showhideBloodNurseName() {
    var selectedValue = $("#bloodDrawNurse").val();
    if (selectedValue == 'Unknown') {
        $('#bloodDrawNurseNameRow').hide();
        $('#bloodDrawNurseName').val('')
    } else {
        $('#bloodDrawNurseNameRow').show();
    }
}
              
function isVolumeValid(ele) {
    if (isNaN(ele.value)) {
        ele.value="0.0";
    }
}
                   
function updateParentSpecimens(cid) {
    if(needSaveNow) {
        $("#loc").val("ParentTubes");
        $(".saveNContinue .button input.save").trigger("click");   
    } else {
        userSetTab = false; //otherwise update will pick up which tissue type you are working with and preselect the appropriate tab
        buildTabsOnTable();
        $('.gen1').eq($('.gen1').size()-1).addClass("active");
    }
}

function bloodformPopUps() { 
    
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    
    $( "#add-parent" ).dialog({
        autoOpen: false,
	height: 115,
	width: 830,
	modal: true,
	buttons: {},
	close: function(){}
    });

    $( "#edit-parent" ).dialog({
	autoOpen: false,
	height: 115,
	width: 830,
	modal: true,
	buttons: {},
	close: function() {}
    });

    $( "#add-plasma" ).dialog({
	autoOpen: false,
	height: 125,
	width: 450,
	modal: true,
	buttons: {},
	close: function() {}
    });
                
    $( "#edit-plasma" ).dialog({
	autoOpen: false,
	height: 125,
	width: 450,
	modal: true,
	buttons: {},
	close: function(){}
    });

    $( "#add-plasmaCT" ).dialog({
	autoOpen: false,
	height: 115,
	width: 450,
	modal: true,
	buttons: {},
	close: function() {}
    });
                
    $( "#edit-plasmaCT" ).dialog({
	autoOpen: false,
	height: 115,
	width: 450,
	modal: true,
	buttons: {},
	close: function(){}
    });


    $( "#add-serum" ).dialog({
	autoOpen: false,
	height: 115,
	width: 450,
	modal: true,
	buttons: {},
	close: function(){}
    });
                
    $( "#edit-serum" ).dialog({
	autoOpen: false,
	height: 115,
	width: 450,
	modal: true,
	buttons: {},
	close: function() {}
    });                

    $( "#dialog-confirm" ).dialog({
	autoOpen: false,
        resizable: false,
        height:140,
        modal: true,
        buttons: {
                "Save": function() {
                    $(this).dialog( "close" );
                },
                Cancel: function() {
                    $(this).dialog( "close" );
                    return false;
                }
            }
        });
        
    $( "#addParentBtn" ).button().click(function() {
        $( "#add-parent" ).removeClass("D1-draw");
        $( "#add-parent" ).removeClass("D2-draw");
        $( "#drawTimeLbl" ).html("");
        $( "#drawTimeLbl" ).hide();
        $( "#drawTime" ).remove();
        $( "#add-parent" ).dialog( "open" );
        $( "#specimenId" ).val( "" );
        $( "#ctId" ).val( "" );
        $( "#code" ).val( "" );
        $( "#volume" ).val( "0.0" );
        if($('#bloodDraw2').attr('checked')){
            $( "#drawTimeLbl" ).after("<select name=\"drawtime\" id=\"drawTime\"><option value=\"\"></option><option value=\"D1\">First Draw</option><option value=\"D2\">Second Draw</option></select>");
        } else {
            $( "#drawTimeLbl" ).after("<select name=\"drawtime\" id=\"drawTime\"><option value=\"\"></option><option value=\"D1\">First Draw</option></select>");
        }  
        $("#add-parent .errors li a").click(function(){
            $( "#add-parent" ).dialog( "close" );
            $( "#second-blood-draw" ).addClass( "redtext" );
        });  
        $("#drawTime").change( function(){
            if($( "#drawTime" ).val() == "D2") {
                $( "#add-parent" ).addClass("D2-draw");
            } else {
                $( "#add-parent" ).removeClass("D2-draw");
            }
        });
        return false;
    });

    
    $( "#addPlasmaBtn" ).button().click(function() {
            $( "#add-plasma" ).dialog( "open" );
            $( "#specimenIdPl" ).val( "" );
            $( "#volumePl" ).val( "0.0" );
            
        return false;
    });

    $( "#addSerumBtn" ).button().click(function() {
	$( "#add-serum" ).dialog( "open" );
        $( "#specimenIdSrm" ).val( "" );
        $( "#volumeSrm" ).val( "0.0" );                                
        var parentBarCode = document.getElementById('serumParBarCode');
        document.getElementById('parSrmBarCd').value = parentBarCode.value; 
        return false;
    });
    
    $( "#addPlasmaCTBtn" ).button().click(function() {
	$( "#add-plasmaCT" ).dialog( "open" );
        $( "#specimenIdCT" ).val( "" );
        $( "#volumeCT" ).val( "0.0" );
        var parentBarCode = document.getElementById('plasmaParBarCode');
        return false;
    });

    $("#saveBtn2").click(function(){
        if (volValid('volumePl')) {
            $( "#specimenIdPl" ).select();
            showWait("Adding specimen");
        } else {
            return false;
        }
    });

    $("#saveBtn3").click(function(){
        if (volValid('volumeSrm')) {
             $( "#specimenIdSrm" ).select();
             showWait("Adding specimen");
        } else {
            return false;
        }        
    });               
    
    $("#ctIdEd").change(function() { 
        var ctIdDEdVal = document.getElementById('ctIdEd').value;
        
        if (ctIdDEdVal == 'DNAPAX') $("#codeEd").val('BLOODDNA');
        else if (ctIdDEdVal == 'RNAPAX') $("#codeEd").val('BLOODRNA');
        else if (ctIdDEdVal == 'LAVEDTA') $("#codeEd").val('BLOODPLAS');
        else if (ctIdDEdVal == 'SST') $("#codeEd").val('BLOODSRM');
        else $("#codeEd").val('null');
    });
    $("#saveBtn4").click(function() {
        var specimenIDEdVal = document.getElementById('specimenIdEd').value;
        var ctIdDEdVal = document.getElementById('ctIdEd').value;
        var codeEdVal = document.getElementById('codeEd').value;
        
        if (specimenIDEdVal == null) specimenIDEdVal = '';
        else {
            specimenIDEdVal = specimenIDEdVal.replace(/^\s+|\s+$/g,'');  //specimenIDEdVal = specimenIDEdVal.trim(); trim() dosen't work in IE lower then 8.x
            if (specimenIDEdVal != document.getElementById('specimenIdEd').value) $("#specimenIdEd").val(specimenIDEdVal);
        }
        
        if (specimenIDEdVal == "") {
            alert("The value of 'Specimen Barcode ID' item is null.");
            return false;
        }

        if (!volValid('volumeEd')) {
            return false;
        }
        
        if (ctIdDEdVal == "null" || ctIdDEdVal == null || ctIdDEdVal == "") {
           alert("The value of 'Specimen Tube type' item is null.");
           return false;
        }
        
        if (codeEdVal == "null" || codeEdVal == null || codeEdVal == "") {
           alert("The value of 'Processed for' item is null.");
           return false;
        }
        
        if ((ctIdDEdVal == "DNAPAX")&&(codeEdVal != "BLOODDNA")) {
           alert("When tube is 'PAXgene DNA', 'Processed for' item should be 'Blood, DNA'");
           return false;
        }
        else if ((ctIdDEdVal == "RNAPAX")&&(codeEdVal != "BLOODRNA")) {
            alert("When the tube is 'PAXgene RNA', the 'Processed for' item should be 'Blood, RNA'");
            return false;
        }
        else if ((ctIdDEdVal == "SST")&&(codeEdVal != "BLOODSRM")) {
            alert("When the tube is 'SST Vacutainer', the 'Processed for' item should be 'Blood, Serum'");
            return false;
        }
        else if ((ctIdDEdVal == "LAVEDTA")&&(codeEdVal != "BLOODPLAS")) {
            alert("When the tube is 'Lavender EDTA', the 'Processed for' item should be 'Blood, Plasma'");
            return false;
        }
        
        $( "#edit-parent" ).dialog( "close" );
    });


    $("#saveBtn5").click(function(){
        if (volValid('volumePlEd')) {
            $( "#edit-plasma" ).dialog( "close" );
        } else {
            return false;
        }        
    });
    $("#saveBtn6").click(function(){
        if (volValid('volumeSrmEd')) {
            $( "#edit-serum" ).dialog( "close" );
        } else {
            return false;
        }
    });
    
    $("#saveBtn7").click(function(){
        var ctIdVal = document.getElementById('volumeCT').value;
        if(document.getElementById('specimenIdCT').value == ""){
              alert("Please enter conical centrifuge tube code");
              document.getElementById('specimenIdCT').focus();
        } else if (ctIdVal == "0.0" || ctIdVal == null || ctIdVal == "" || ctIdVal == 0) {
              alert("Conical Tube volume is either blank or not valid");
              document.getElementById('volumeCT').focus();
              return false;
        } else {
            $.ajax({
                type: 'GET',
                dataType: 'text', 
                url: '/cahubdataservices/bpvBloodForm/validateSpecimen?specimenId=' + $("#specimenIdCT").val(),
                success: function(data) {
                    ctIdVal = ctIdVal.replace(/^\s+|\s+$/g,'');
                    if (data == 'false') {
                        alert("This conical centrifuge tube code already exists, duplicate IDs are not allowed");
                        document.getElementById('specimenIdCT').focus();
                        return false;
                    } else {
                        $("#add-plasmaCT").dialog( "close" );
                        showWait('Adding conical centrifuge tube');
                    }
                }
            });
        }
    });
    
    $("#saveBtn8").click(function(){
        var ctIdVal = document.getElementById('volumeCTEd').value;
        ctIdVal = ctIdVal.replace(/^\s+|\s+$/g,'');
        if (ctIdVal == "0.0" || ctIdVal == null || ctIdVal == "" || ctIdVal == 0) {
            alert("Conical Tube volume is either blank or not valid");
            document.getElementById('volumeCTEd').focus();
           return false;
        } else {
            $( "#edit-plasmaCT" ).dialog( "close" );
        }       
    });    
    
    $("#cancelButton2").click(function(){
        $( "#add-plasma" ).dialog( "close" );
        return false;
    });

    $("#cancelButton3").click(function(){
        $( "#add-serum" ).dialog( "close" );
        return false;
    }); 
    
    $("#cancelButton4").click(function(){
        $( "#edit-parent" ).dialog( "close" );
        return false;
    });                        
                        
    $("#cancelButton5").click(function(){
        $( "#edit-plasma" ).dialog( "close" );
        return false;
    });                        
                        
    $("#cancelButton6").click(function(){
        $( "#edit-serum" ).dialog( "close" );
        return false;
    });
    
    $("#cancelButton7").click(function(){
        $( "#add-plasmaCT" ).dialog( "close" );
        return false;
    });    
    
    $("#cancelButton8").click(function(){
        $( "#edit-plasmaCT" ).dialog( "close" );
        return false;
    });        

    $("#saveBtn1").click(function() {
        var specimenIDVal = document.getElementById('specimenId').value;
        var ctIdDVal = document.getElementById('ctId').value;
        var codeVal = document.getElementById('code').value;
        var drawTimeVal = document.getElementById('drawTime').value;
        
        if (specimenIDVal == null) specimenIDVal = '';
        else {
            specimenIDVal = specimenIDVal.replace(/^\s+|\s+$/g,'');  //specimenIDVal = specimenIDVal.trim(); trim() dosen't work in IE lower then 8.x
            if (specimenIDVal != document.getElementById('specimenId').value) $("#specimenId").val(specimenIDVal);
        }
        
        if (specimenIDVal == "") {
            alert("The value of 'Specimen Barcode ID' item is null.");
            return false;
        }
        
        if (drawTimeVal == "") {
            alert("Please select the Blood Draw.");
            return false;
        }
        
        if (!volValid('volume')) {
            return false;
        }
        
        if (ctIdDVal == "null" || ctIdDVal == null || ctIdDVal == "") {
            alert("The value of 'Specimen Tube type' item is null.");
            return false;
        }
        
        if (codeVal == "null" || codeVal == null || codeVal == "") {
            alert("The value of 'Processed for' item is null.");
            return false;
        }
        
        if ((ctIdDVal == "DNAPAX")&&(codeVal != "BLOODDNA")) {
            alert("When tube is 'PAXgene DNA', 'Processed for' item should be 'Blood, DNA'");
            return false;
        }
        else if ((ctIdDVal == "RNAPAX")&&(codeVal != "BLOODRNA")) {
            alert("When the tube is 'PAXgene RNA', the 'Processed for' item should be 'Blood, RNA'");
            return false;
        }
        else if ((ctIdDVal == "SST")&&(codeVal != "BLOODSRM")) {
            alert("When the tube is 'SST Vacutainer', the 'Processed for' item should be 'Blood, Serum'");
            return false;
        }
        else if ((ctIdDVal == "LAVEDTA")&&(codeVal != "BLOODPLAS")) {
            alert("When the tube is 'Lavender EDTA', the 'Processed for' item should be 'Blood, Plasma'");
            return false;
        }
                
        $( "#add-parent" ).dialog( "close" );
        
        showWait("Adding specimen");
        
        var codeSelect = document.getElementById("code");
        activeType = codeSelect.options[codeSelect.selectedIndex].text.toLowerCase().replace("blood, ","");
        
        $('#code').removeAttr('disabled');
        return true;
    });

    $("#cancelButton1").click(function(){
        $( "#add-parent" ).dialog( "close" );
        return false;
    });
}

function editParent(id, specimenId, ctId, code, volume, drawTimeId) {
   $( "#edit-parent" ).removeClass("D1-draw");
   $( "#edit-parent" ).removeClass("D2-draw");
    
    if(drawTimeId == "D2") {
        $("#drawTimeEdLbl").html($("#dateTimeBloodDraw2").val().replace("Select Date", "<span class=\"redtext\">Not Yet Entered</span>"));
    } else {
        $("#drawTimeEdLbl").html($("#dateTimeBloodDraw").val().replace("Select Date", "<span class=\"redtext\">Not Yet Entered</span>"));
    }
    
    $( "#edit-parent" ).dialog( "open" );
    $( "#edit-parent" ).addClass(drawTimeId + "-draw");
    $( "#specimenIdEd" ).val(specimenId);
    $( "#specId" ).val(specimenId);                                
    $( "#ctIdEd" ).val(ctId);
    $( "#codeEd" ).val(code);
    $( "#volumeEd" ).val(volume);
    $("#specParentId").val(id);
    $("#drawTimeEd").val(drawTimeId);
    return false;
}
                        
function editPlasma(id, specimenId, volume) {
    $( "#edit-plasma" ).dialog( "open" );
    $( "#specimenIdPlEd" ).val(specimenId);
    $( "#specPlId" ).val(specimenId);
    $( "#volumePlEd" ).val(volume);
    $("#specPlasmaId").val(id)
    return false;
}

function editPlasmaCT(id, specimenId, volume) {
    $( "#edit-plasmaCT" ).dialog( "open" );
    $( "#specimenIdCTEd" ).val(specimenId);
    $( "#specCTId" ).val(specimenId);
    $( "#volumeCTEd" ).val(volume);
    $("#specPlasmaCTId").val(id)
    return false;
}


function editSerum(id, specimenId, volume) {
    $( "#edit-serum" ).dialog( "open" );
    $( "#specimenIdSrmEd" ).val(specimenId);
    $( "#specSrmId" ).val(specimenId);                     
    $( "#volumeSrmEd" ).val(volume);
    $("#specSerumId").val(id)
    return false;
}
                        
function clearCT() {
    $( "#plasmaCTBarcode" ).val( "" );
    $( "#plasmaCTVol" ).val( "0.0" );
    $( "#imgPlCTDel" ).html("");
    return false;
}                

function volValid(volumeEle) {
        var vol = document.getElementById(volumeEle).value;
        if (vol == null) vol = "0.0";
        vol = vol.replace(/^\s+|\s+$/g,'');
        if (vol == "0.0" || vol == "0" || vol == "0." || vol == "" || vol == 0) {
            alert("Volume is either blank or not valid");
            document.getElementById(volumeEle).focus();
           return false;
        } else {
            return true;
        }
}


 function showPlasmaContinue(){
     if ($.trim($('#plasmaCTBarcode').val()) == $("#origPlasmaCTBarcode").val() && $("#origPlasmaCTBarcode").val().length > 0){
       $('.plasmaBarcodeGroup').removeClass("hide");
       $('.bpvbloodform #container .reset').hide();
       $('.saveNContinue').hide();
     } else if( $.trim($('#plasmaCTBarcode').val()).length > 0){
       $('.saveNContinue').show();
       $('.plasmaBarcodeGroup').addClass("hide");
       $('.bpvbloodform #container .reset').show();
     } else if( $.trim($('#plasmaCTBarcode').val()).length == 0){
       $('#plasmaCTVol').val("0.00");
       $('.saveNContinue').show();
       $('.plasmaBarcodeGroup').addClass("hide");
       if($("#origPlasmaCTBarcode").val().length === 0){
           $('.bpvbloodform #container .reset').hide();
           $('.saveNContinue').hide();
       } else {
           $('.bpvbloodform #container .reset').show();
       }
     }
    
     if ($("#origPlasmaCTBarcode").val().length > 0){
        $('.plasmaBarcodeGroup').removeClass("hide");
     }

 }
 
 function showAddPlasmaGroup(){
     if( $.trim($('#plasmaCTBarcode').val()).length > 0 ){
         $('.edit .plasmaBarcodeGroup').removeClass("hide");
     } else {
         $('.show .plasma-tubes-section').addClass("hide");
         $('.edit .plasmaBarcodeGroup').addClass("hide");
     }
 }

 function showAddSerumGroup(){ 
     if( $.trim($('#serumParBarcodeDisp').html()).length > 0){
         $('.serumBarcodeGroup').show();
     } else {
         $('.serumBarcodeGroup').hide();
     }
 }

function showWait(msg){
    $("#ca-dialog").html("<div id=\"blocker\"><div>" + msg + "...<br /><img src=\"/cahubdataservices/images/loading.gif\" /></div></div>");
    $("#ca-dialog").show();
}

function validateSpecDelete(rowID,tissueType){
    tissueType = tissueType.toLowerCase();
    if(tissueType == "dna" || tissueType == "rna"){
        if($("tr.gen1-"+tissueType).length > 0 && $("#"+rowID+" .specimenIdName").html() == $("#"+tissueType+"ParBarCodeSelect").val()){
            if(confirm("Since this was the processed specimen this action will clear the related " + tissueType.toUpperCase() + " processing entries.")){
                clearData(tissueType);
                showWait("Deleting specimen");
                return true;
            } else {
                return false;
            }
            
        } else if($("tr.gen1-"+tissueType).length == 1){
            if(confirm("Since this was the remaining " + tissueType.toUpperCase() + " specimen this action will clear any " + tissueType.toUpperCase() + " processing entries.")){
                clearData(tissueType);
                showWait("Deleting specimen");
                return true;
            } else {
                return false;
            }
        }
        else {
            showWait("Deleting Specimen");
            return true;
        }
    } else {
        showWait("Deleting Specimen");
        return true;
    }
}

function deleteParentSpecimens(rowID, caseRecordId, tissueType){
    tissueType = tissueType.toLowerCase();
    if(((tissueType == "rna" || tissueType == "dna") && $("#"+rowID+" .specimenIdName").html() == $("#"+tissueType+"ParBarCodeSelect").val()) || !(tissueType == "rna" || tissueType == "dna")){
        clearData(tissueType);
    }
    
    $("#loc").val("ParentTubes");
    $("#formsave").trigger("click");
}

function clearData (tissueType){
    var tissueId = tissueType.replace("rna","rnaPax").replace("dna","dnaPax");
    $("#" + tissueId + "Frozen_cleardate").trigger("click");
    $("#" + tissueId + "Storage_cleardate").trigger("click");
    $("#" + tissueId + "ProcTech").val("");
    $("input:radio[name='" + tissueId + "ProcSopDev']").prop('checked', false);
    $("#" + tissueId + "ProcSopDev_yes").val("");
    $("#" + tissueId + "ProcSopDev_no").val("");
    $("#" + tissueId + "ProcComments").val("");
    $("#" + tissueId + "StorageIssues").val("");
    if( tissueType.toLowerCase() === "plasma") {
        $("#plasmaProcStart_cleardate").trigger("click");
        $("#plasmaProcEnd_cleardate").trigger("click");
        $("#plasmaProcFrozen_cleardate").trigger("click");
        $("#plasmaProcStorage_cleardate").trigger("click");
        $("input:radio[name='" + tissueId + "plasmaHemolysis']").prop('checked', false);
        $("#plasmaHemolysis_yes").val("");
        $("#plasmaHemolysis_no").val("");
    } else if( tissueType.toLowerCase() === "serum") {
        $("#serumProcStart_cleardate").trigger("click");
        $("#serumProcEnd_cleardate").trigger("click");
        $("#serumProcFrozen_cleardate").trigger("click");
        $("#serumProcStorage_cleardate").trigger("click");
    }
}

function callSaveNContinue (){
    $(".saveNContinue .button input.save").trigger("click");
}

function updateParBarCodeDisp(){
    for(var i=0; i < availableTissueTypes.length; i++){
        currentTissueSearchIndex =  0;
        currentTissueLabelStr = "";
        currentTissue = availableTissueTypes[i].toLowerCase();
        currentTissueCount = $(".gen1-" + currentTissue + " .specimenIdName").length;
        currentVal = $("#" + currentTissue + "ParBarCode").val();
        selectStr = "";
        selectStr = "<select id=\"" + currentTissue + "ParBarCodeSelect\">";
        if(currentTissue == "rna" || currentTissue == "dna"){
            selectStr += "<option value=\"\">Select Specimen Barcode ID</option>";
        }
        
        $("#" + currentTissue + "ParBarcodeDisp").html("");
        
        $(".gen1-" + currentTissue ).each(function() { 
            selectVal = $("#" + $(this).attr("id") + " .specimenIdName").html();
            if($("#" + currentTissue + "ParBarcodeDisp").html().length == 0) {
               selectStr += "<option value=\"" + selectVal  + "\"" + ($(this).hasClass("D2-draw")? " class=\"D2-draw\"" : "") + ">" + selectVal + "</option>";
            }
            if ( (currentTissue == "rna" || currentTissue == "dna") && currentVal == selectVal) { 
                selectStr = selectStr.replace(">"+selectVal," selected>"+selectVal);                 
            }
        });
        if(currentTissueCount > 0){
            selectStr += "</select>";
        }
        $("#" + currentTissue + "ParBarcodeDisp").html(selectStr);
        if(currentTissue == "plasma" || currentTissue == "serum"){
            $("#" + currentTissue + "ParBarcodeDisp select").attr("disabled", true);
        }
        $("#" + currentTissue + "ParBarCodeSelect").change(function() {
            $("#" + $(this).attr("id").replace("Select","")).val($(this).val());
            $(this).closest('td').removeClass("D2-draw");
            $(this).closest('td').addClass($("option:selected", this).attr("class"));
            if ( $(this).attr("value") == ""){
                $(this).addClass("errors");
            } else {
                $(this).closest( "td" ).removeClass("errors")
                $(this).removeClass("errors");
            }
        });
        $("#" + currentTissue + "ParBarCode").val($("#" + currentTissue + "ParBarCodeSelect").val());
        $("#" + currentTissue + "ParBarCodeSelect").removeClass("D2-draw");
        $("#" + currentTissue + "ParBarCodeSelect").closest('td').addClass($("option:selected", $("#" + currentTissue + "ParBarCodeSelect")).attr("class"));
        if ( $("#" + currentTissue + "ParBarCodeSelect").attr("value") == ""){
            $("#" + currentTissue + "ParBarCodeSelect").addClass("errors");
            $("#" + currentTissue + "ParBarCodeSelect").closest('table').addClass("notfilled");
        } else {
            $("#" + currentTissue + "ParBarCodeSelect").closest( "td" ).removeClass("errors")
            $("#" + currentTissue + "ParBarCodeSelect").removeClass("errors");
        }
     }

    $("#dnaParBarCodeSelect").change(function() {
        if (($("#dnaPaxProcTech").val() + $("#dnaPaxProcComments").val() + $("#dnaPaxStorageIssues").val() + $("#dnaPaxFrozen_day").val() + $("#dnaPaxStorage_day").val()).length > 0 || $("input:radio[name='dnaPaxProcSopDev']").is(":checked")) {
            currentSpecSelect = "dna";
            $("#dialog-confirm").dialog("open");
        } 
    });

    $("#rnaParBarCodeSelect").change(function() {
        if (($("#rnaPaxProcTech").val() + $("#rnaPaxProcComments").val() + $("#rnaPaxStorageIssues").val() + $("#rnaPaxFrozen_day").val() + $("#rnaPaxStorage_day").val()).length > 0 || $("input:radio[name='rnaPaxProcSopDev']").is(":checked")) {
            currentSpecSelect = "rna";
            $("#dialog-confirm").dialog("open");
        }   
    });
    
}

// Only Error codes are commun icated from backend.
// Front end has actual error content in javascript to enable the matching of errors to elements on the page.
function showErrors(){
    
    // match error codes to links and show links
    $("#pageErrs ul li").each(function() {
        $(this).html(bpvErrorDict[$(this).html()]);
        $("#pageErrs").show();
    });
    
    // identify location of error on page and tie to user onclick
    $("#pageErrs ul li a").click(function() {
        var dest = $(this).attr("href");
        if($(dest).css("display").toLowerCase() != "none") {
            if($(this).attr("class")){
                if($("#" + $(this).attr("class")).css("display").toLowerCase() != "none") {
                    if($(dest).closest("tr").css("display") == "none" && $(dest).closest("tr").hasClass("plasmaBarcodeGroup")) {
                        setBloodTab($("#" + $(this).attr("class")));
                        scrollTo("#" + $(this).attr("class"),"error");
                    } else if ($("#" + "bloodform-" + $(this).attr("class")).length > 0) {
                        setBloodTab($("#" + $(this).attr("class")));
                        scrollTo(dest,"error");
                    } else {
                        scrollTo(dest,"error");
                    }
                } else {
                    if ($("#" + "bloodform-" + $(this).attr("class")).length > 0) {
                      setBloodTab($("#" + $(this).attr("class")));
                      scrollTo(dest,"error");
                    }
                }
            } else {
                scrollTo(dest,"error");
            }
            return false;
        }
    });
}

function disable2drawChkbx(){
    $("#bloodDraw2").attr("checked",true); 
    $(".D2-draw").show();
    if( $("#bloodDrawNurse2").val() !== "OtherSpecify") {
        $("#bloodDrawNurseOsRow2").hide();
    }
    $("#bloodDraw2").attr("disabled","disabled"); 
    $("#checkboxinstr").html(" <a href=\"#ParentTubes\"><u>To uncheck, please first remove specimens added for the second draw.</u></a>");
    $("#second-blood-draw td label unboldtext").click(function() {
        scrollTo("#ParentTubes");
    })
}

function hidedraw2(){
    if($("#bloodDraw2").attr("checked") == undefined) {
       $("#tabs-main tr.bottomsection.D2-draw").hide();
       $("#second-blood-draw").show();
    }
}

function  setChangeCtId() {                   
    $("#ctId").change(function() { 
        var ctIdDVal = document.getElementById('ctId').value;
        if (ctIdDVal == 'DNAPAX') {
            $("#code").val('BLOODDNA');
        } else if (ctIdDVal == 'RNAPAX') {
            $("#code").val('BLOODRNA');
        } else if (ctIdDVal == 'LAVEDTA') {
            $("#code").val('BLOODPLAS');
        } else if (ctIdDVal == 'SST') {
            $("#code").val('BLOODSRM');
        } else {
            $("#code").val('null');
        }
    });    
}   

function syncDrawDateKeys() { 
    if (!($("#bloodMinimum_no").attr("checked") == "checked" && $("body").hasClass("show"))){
        $("#drawTimesKey-d1 span").html($("#dateTimeBloodDraw").val()==""?$("#dateTimeBloodDraw").closest('td').html():$("#dateTimeBloodDraw").val().replace("Select Date", "<span class=\"redtext\" href=\"\">Select Date</span>"));
        if($("#dateTimeBloodDraw2_day").val() != undefined && $("#dateTimeBloodDraw2_day").val().length != 0 && $("#dateTimeBloodDraw2").val().length != 0) {
            $("#drawTimesKey-d2").html("D2 - <span>" + $("#dateTimeBloodDraw2").val() + "</span>");
        } else if ($("#bloodDraw2").attr("checked") && $("#dateTimeBloodDraw2").html().length != 0) {
            $("#drawTimesKey-d2").html("D2 - <span>" + $("#dateTimeBloodDraw2").closest('td').html() + "</span>");
        }

        $("#drawTimesKey-d1 span.redtext").click(function() {
            scrollTo("#dateTimeBloodDraw");
            $("#dateTimeBloodDrawRow").addClass("warnings redtext");
        });
    }
}