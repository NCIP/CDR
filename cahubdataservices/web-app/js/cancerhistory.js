 /* Please do not check in commented out code like alert(flags) , partial code etc... */
 
  $(document).ready(function(){
     
       var top_margin = 20;
          var codVocabObj = {};
           var srcDefArrToStr = "";
         // console.log("path: " + vocabJsnLoc)

        $( "#primaryTumorSite-dialog").dialog({
            autoOpen: false,
            position: ['center',top_margin],
            width: 800,
            modal: true,
            buttons: {},
            close: function(){
                $(".ui-autocomplete").hide();
            }
        });


        $( "#primaryTumorSite-zoomin").click(function(){ 
           
            refreshlog('primaryTumorSite', true, "pts");
        });
        
        //code for autocomplete, start
        $( "#primaryTumorSite-pts" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: vocabJsnLoc + "/solr/pct",
                dataType: "jsonp",
                jsonp: 'json.wrf',
                data: {
                    fl: "*,score",
                    wt: "json",
                    q: request.term,
                    rows: "25"
                },
                success: function( data ) {
                    response( $.map( data.response.docs, function( item ) {
                        //console.log("ICDcd: " + item.ICDcd)
                        return {
                            id: item.id,
                            typ: item.typ,
                            pct: item.pct,
                            cui: item.cui,
                            ICDcd: item.ICDcd,
                            cvocabVer: item.cvocabVer,
                            srcDef: item.srcDef,
                            _version_: item._version_,
                            score: item.score,
                            value: item.pct,
                            PDQcd: item.PDQcd
                            
                        }
                    }));
                }
            });
        },
        minLength: 2,
        select: function( event, ui ) {
            refreshlog('primaryTumorSite', false, "pts");
            codVocabObj = ui;
            srcDefArrToStr=""
            if(ui.item.srcDef != undefined){
              //  var srcDefArrToStr = "";
                for (var i=0; i<ui.item.srcDef.length; i++) {
                   srcDefArrToStr += "<div>" + ui.item.srcDef[i] + "</div>";
                }
               log("<h3>Definition</h3>" + srcDefArrToStr, "primaryTumorSite", "pts");
            }
            if(ui.item.ICDcd != undefined){
               log("<b>ICDcd:</b> " + ui.item.ICDcd.toString(), "primaryTumorSite", "pts");
            }
            if(ui.item.cui != undefined){
               log("<b>CUI:</b> " + ui.item.cui.toString(), "primaryTumorSite", "pts");
            }
             if(ui.item.PDQcd != undefined){
               log("<b>PDQcd:</b> " + ui.item.PDQcd.toString(), "primaryTumorSite", "pts");
            }
           
            if(ui.item.pct != undefined){
               log("<b>PCT:</b> " + ui.item.pct, "primaryTumorSite", "pts");
            }
        }
    });		
        
     //code for autocomplete, end
     
     //code for submit, start 
      $("#primaryTumorSite-pts-submit").click(function(){
      if(codVocabObj.item != undefined) {
           $("#pts-cui").val("");
        if(codVocabObj.item.cui != undefined){
            $("#pts-cui").val(codVocabObj.item.cui.toString());
        }
        // console.log("what happen?" + codVocabObj.item.cui.toString())
        $("#pts-pct").val("");
        if(codVocabObj.item.pct != undefined){
            $("#pts-pct").val(codVocabObj.item.pct);
            $("#pts-label").html(codVocabObj.item.pct);
            $("#pts-label").parents('td').last().removeClass('errors');
        }
        // console.log("what happen2?" + codVocabObj.item.pct)
        $("#pts-typ").val("");
        if(codVocabObj.item.typ != undefined){
            $("#pts-typ").val('PCT');
        }
        
        $("#pts-id").val("");
        if(codVocabObj.item.id != undefined){
            $("#pts-id").val(codVocabObj.item.id);
        }
        
        $("#pts-ICDcd").val("");
        if(codVocabObj.item.ICDcd != undefined){
            $("#pts-ICDcd").val(codVocabObj.item.ICDcd.toString());
        }
        
        $("#pts-cvocabVer").val("");
        if(codVocabObj.item.cvocabVer != undefined){
            $("#pts-cvocabVer").val(codVocabObj.item.cvocabVer);
        }
        
         $("#pts-PDQcd").val("");
         if(codVocabObj.item.PDQcd != undefined){
            $("#pts-PDQcd").val(codVocabObj.item.PDQcd.toString());
        }
        
        $("#pts-srcDef").val("");
         if(codVocabObj.item.srcDef != undefined){
            $("#pts-srcDef").val(srcDefArrToStr);
        }
      } 
      //console.log("what happen3?")
      $("#primaryTumorSite-dialog").dialog("close");
    });  
    
    //code for submit, end
    
    //code for cancel
    $("#primaryTumorSite-pts-cancel").click(function(){
      $("#primaryTumorSite-dialog").dialog("close");
    });     
     
     
});
           

  function presetLog(fieldName, typ) {
     
        if($("#pts-pct").val() != null && $("#pts-pct").val().length > 0){
            $( "#primaryTumorSite-pts").attr("value", $("#pts-pct").val())
          presetLogWithData2(typ, fieldName)
        }
     
     
        
  }

 
 
function presetLogWithData2(typ, fieldName){
    //console.log("srcDef:" + $("#pts-srcDef").val())
    if($("#pts-srcDef").val() != null && $("#pts-srcDef").val().length > 0){
        log("<h3>Definition</h3>" +  $("#pts-srcDef").val(), fieldName, "pts");
    }else if( pct_srcDef_str.length > 0){
          log("<h3>Definition</h3>" + pct_srcDef_str, fieldName, "pts" );
    }else{
        
    }
       if($("#pts-ICDcd").val() != null && $("#pts-ICDcd").val().length > 0){
               log("<b>ICDcd:</b> " + $("#pts-ICDcd").val(), fieldName, "pts");
      }
      
      if($("#pts-cui").val() != null && $("#pts-cui").val().length > 0){
               log("<b>CUI:</b> " + $("#pts-cui").val(), fieldName, "pts");
      }
    
       if($("#pts-PDQcd").val() != null && $("#pts-PDQcd").val().length > 0){
               log("<b>PDQcd:</b> " + $("#pts-PDQcd").val(), fieldName, "pts");
      }
       if($("#pts-pct").val() != null && $("#pts-pct").val().length > 0){
               log("<b>PCT:</b> " + $("#pts-pct").val(), fieldName, "pts");
      }
}
  
  