    $(document).ready(function() {
/*      $("#irradSaveBtn").hide();
      $("#chemoSaveBtn").hide();
      $("#immSaveBtn").hide();
      $("#hormSaveBtn").hide();
      $("#hbcSaveBtn").hide();
      $("#hrtSaveBtn").hide();
      $("#prevCancerSave").hide(); */
      
        $("#karnofsky").click(function() {
            if(this.checked) {
                $("#karnofskyRow").show()
                $("#ecogRow").hide()
                //$("#ecogStatus").val('')
                //$("#ecogStatus").checked = false;
                $("#ecogStatus").prop('checked', false);
            }
        });

        $("#ecog").click(function() {
            if(this.checked) {
                $("#ecogRow").show()
                $("#karnofskyRow").hide()
                //$("#karnofskyScore").val('')
                //$("#karnofskyScore").checked = false;
                $("#karnofskyScore").prop('checked', false);
            }
        });
        
        $("#notRecorded").click(function() {
            
            if(this.checked) {
                
                $("#ecogRow").hide()
                $("#karnofskyRow").hide()
                $("#karnofskyScore").prop('checked', false);
                $("#ecogStatus").prop('checked', false);  
                
            }
        });
        
        
        
        
        $("#karnofsky60").click(function() {
            if(this.checked) {
                $("#karnofskyRow").show()
                $("#ecogRow").hide()
                //$("#ecogStatus").val('')
                //$("#ecogStatus").checked = false;
                $("#ecogStatus").prop('checked', false);
                $("#timingOfScoreTR").show()
            }
        });

        $("#ecog60").click(function() {
            if(this.checked) {
                $("#ecogRow").show()
                $("#karnofskyRow").hide()
                //$("#karnofskyScore").val('')
                //$("#karnofskyScore").checked = false;
                $("#karnofskyScore").prop('checked', false);
                $("#timingOfScoreTR").show()
            }
        });
        
        $("#notRecorded60").click(function() {
            
            if(this.checked) {
                
                $("#ecogRow").hide()
                $("#karnofskyRow").hide()
                $("#karnofskyScore").prop('checked', false);
                $("#ecogStatus").prop('checked', false);  
                                
                $("#timingOfScoreTR").hide()
                $("#Preoperative").prop('checked', false)
                $("#Preadjuvant").prop('checked', false)
                $("#Postadjuvant").prop('checked', false)
                $("#tosUnknown").prop('checked', false)
                $("#othTimScore").prop('checked', false)
                $("#timingOfScoreOs").val('')
                $("#timingOfScoreOsRow").hide()
            }
        });
        
        
        
        
        $("#addtlColoRisk6").click(function() {
          if(this.checked) {
            $("#otherColRiskRow").show()
            $("#otherAddColRisk").val('')
            $("#otherAddColRisk").focus()
          } else {
            $("#otherColRiskRow").hide()
            $("#otherAddColRisk").val('')
          }
        });
        

        $("#bloodRelCancer1").click(function() {
            if(this.checked) {
                $("#relCancerType1").show()
                $("#relCancerType1").focus()
            } else {
                $("#relCancerType1").hide()
                $("#relCancerType1").val('')
            }
        });

        $("#bloodRelCancer2").click(function() {
            if(this.checked) {
                $("#relCancerType2").show()
                $("#relCancerType2").focus()
            } else {
                $("#relCancerType2").hide()
                $("#relCancerType2").val('')
            }
        });

        $("#bloodRelCancer3").click(function() {
            if(this.checked) {
                $("#relCancerType3").show()
                $("#relCancerType3").focus()
            } else {
                $("#relCancerType3").hide()
                $("#relCancerType3").val('')
            }
        });

        $("#bloodRelCancer4").click(function() {
            if(this.checked) {
                $("#relCancerType4").show()
                $("#relCancerType4").focus()
            } else {
                $("#relCancerType4").hide()
                $("#relCancerType4").val('')
            }
        });

        $("#bloodRelCancer5").click(function() {
            if(this.checked) {
                $("#relCancerType5").show()
                $("#relCancerType5").focus()
            } else {
                $("#relCancerType5").hide()
                $("#relCancerType5").val('')
            }
        });

        $("#bloodRelCancer6").click(function() {
            if(this.checked) {
                $("#relCancerType6").show()
                $("#relCancerType6").focus()
            } else {
                $("#relCancerType6").hide()
                $("#relCancerType6").val('')
            }
        });

        $("#bloodRelCancer7").click(function() {
            if(this.checked) {
                $("#relCancerType7").show()
                $("#relCancerType7").focus()
            } else {
                $("#relCancerType7").hide()
                $("#relCancerType7").val('')
            }
        });

        $("#bloodRelCancer8").click(function() {
            if(this.checked) {
                $("#relCancerType8").show()
                $("#relCancerType8").focus()
            } else {
                $("#relCancerType8").hide()
                $("#relCancerType8").val('')
            }
        });

        $("#bloodRelCancer9").click(function() {
            if(this.checked) {
                $("#relCancerType9").show()
                $("#relCancerType9").focus()
            } else {
                $("#relCancerType9").hide()
                $("#relCancerType9").val('')
            }
        });

        $("#bloodRelCancer10").click(function() {
            if(this.checked) {
                $("#relCancerType10").show()
                $("#relCancerType10").focus()
            } else {
                $("#relCancerType10").hide()
                $("#relCancerType10").val('')
            }
        });

        $("#bloodRelCancer11").click(function() {
            if(this.checked) {
                $("#relCancerType11").show()
                $("#relCancerType11").focus()
            } else {
                $("#relCancerType11").hide()
                $("#relCancerType11").val('')
            }
        });

        $("#bloodRelCancer12").click(function() {
            if(this.checked) {
                $("#relCancerType12").show()
                $("#relCancerType12").focus()
            } else {
                $("#relCancerType12").hide()
                $("#relCancerType12").val('')
            }
        });

        $("#bloodRelCancer13").click(function() {
            if(this.checked) {
                $("#othbloodRel").show()
                $("#othbloodRel").focus()              
                $("#relCancerType13").show()
            } else {
                $("#othbloodRel").hide()
                $("#othbloodRel").val('')
                $("#relCancerType13").hide()
                $("#relCancerType13").val('')
            }
        });

        $("#immunoSuppStatus4").click(function() {
            if(this.checked) {
                $("#othImmStat").show()
                $("#otherImmunoSuppStatus").focus()
            } else {
                $("#othImmStat").hide()
                $("#otherImmunoSuppStatus").val('')
            }
        });
        
        $("#isImmunoSupp_yes").click(function() {
          $("#immunoSuppRow").show()
        });        
        
        $("#isImmunoSupp_no").click(function() {
          $("#immunoSuppRow").hide()
          $("#immunoSuppStatus1").prop('checked', false)
          $("#immunoSuppStatus2").prop('checked', false)
          $("#immunoSuppStatus3").prop('checked', false)
          $("#immunoSuppStatus4").prop('checked', false)
          $("#othImmStat").hide()
          $("#otherImmunoSuppStatus").val('')
        });
        
        $("#isImmunoSupp_unknown").click(function() {
          $("#immunoSuppRow").hide()
          $("#immunoSuppStatus1").prop('checked', false)
          $("#immunoSuppStatus2").prop('checked', false)
          $("#immunoSuppStatus3").prop('checked', false)
          $("#immunoSuppStatus4").prop('checked', false)
          $("#othImmStat").hide()
          $("#otherImmunoSuppStatus").val('')
        });        
        
        $("#isEnvCarc_yes").click(function() {
          $("#envCarcRow").show()
          $("#envCarcDescRow").show()
        });        
        
        
        $("#isEnvCarc_no").click(function() {
          $("#envCarcRow").hide()
          $("#envCarcDescRow").hide()
          $("#envCarc1").prop('checked', false)
          $("#envCarc2").prop('checked', false)
          $("#envCarc3").prop('checked', false)
          $("#envCarc4").prop('checked', false)
          $("#envCarc5").prop('checked', false)
          $("#envCarcExpDesc").val('')
        });        
        
        $("#isEnvCarc_unknown").click(function() {
          $("#envCarcRow").hide()
          $("#envCarcDescRow").hide()
          $("#envCarc1").prop('checked', false)
          $("#envCarc2").prop('checked', false)
          $("#envCarc3").prop('checked', false)
          $("#envCarc4").prop('checked', false)
          $("#envCarc5").prop('checked', false)          
          $("#envCarcExpDesc").val('')
        });        
        
        $("#isAddtlColoRisk_yes").click(function() {
          $("#addtlColoRiskRow").show()
          //$("#otherColRiskRow").show()
        });        
        
        
        $("#isAddtlColoRisk_no").click(function() {
          $("#addtlColoRiskRow").hide()
          $("#otherColRiskRow").hide()
          $("#addtlColoRisk1").prop('checked', false)
          $("#addtlColoRisk2").prop('checked', false)
          $("#addtlColoRisk3").prop('checked', false)
          $("#addtlColoRisk4").prop('checked', false)
          $("#addtlColoRisk5").prop('checked', false)
          $("#addtlColoRisk6").prop('checked', false)
          $("#otherAddColRisk").val('')
        });        
        
        $("#isAddtlColoRisk_unknown").click(function() {
          $("#addtlColoRiskRow").hide()
          $("#otherColRiskRow").hide()
          $("#addtlColoRisk1").prop('checked', false)
          $("#addtlColoRisk2").prop('checked', false)
          $("#addtlColoRisk3").prop('checked', false)
          $("#addtlColoRisk4").prop('checked', false)
          $("#addtlColoRisk5").prop('checked', false)
          $("#addtlColoRisk6").prop('checked', false)
          $("#otherAddColRisk").val('')
        });        

        $("#irradTherb4Surg_yes").click(function() {
          $("#irradSaveRow").show()
          $("#IrradTable").show()
          $("#irradSaveBtn").hide()
          $("#irradCancelBtn").hide()
          $("#irradAddBtn").show()
        });

        $("#irradTherb4Surg_no").click(function() {
          $("#irradTherb4SurgDescRow").hide()
          $("#irradTherb4SurgDesc").val('')
          $("#irradTherb4SurgDtRow").hide()
          $("#irradTherb4SurgDt").val('Select Date')
          $("#irradTherb4SurgEst").val('')
          $("#irradTherb4SurgDt_day").val('')
          $("#irradTherb4SurgDt_month").val('')
          $("#irradTherb4SurgDt_year").val('')
          $("#irradSaveRow").hide()
          $("#IrradTable").hide()          
        });

        $("#irradTherb4Surg_unknown").click(function() {
          $("#irradTherb4SurgDescRow").hide()
          $("#irradTherb4SurgDesc").val('')
          $("#irradTherb4SurgDtRow").hide()
          $("#irradTherb4SurgDt").val('Select Date')
          $("#irradTherb4SurgEst").val('')
          $("#irradTherb4SurgDt_day").val('')
          $("#irradTherb4SurgDt_month").val('')
          $("#irradTherb4SurgDt_year").val('')
          $("#irradSaveRow").hide()
          $("#IrradTable").hide()          
        });


        $("#chemoTherb4Surg_yes").click(function() {
          $("#ChemoTable").show()
          $("#chemoSaveRow").show()
          $("#chemoSaveBtn").hide()
          $("#chemoCancelBtn").hide()
          $("#chemoAddBtn").show()
        });

        $("#chemoTherb4Surg_no").click(function() {
          $("#chemoTherb4SurgDescRow").hide()
          $("#chemoTherb4SurgDesc").val('')
          $("#chemoTherb4SurgDtRow").hide()
          $("#chemoTherb4SurgDt").val('Select Date')
          $("#chemoTherb4SurgEst").val('')          
          $("#chemoTherb4SurgDt_day").val('')
          $("#chemoTherb4SurgDt_month").val('')
          $("#chemoTherb4SurgDt_year").val('')
          $("#chemoSaveRow").hide()
          $("#ChemoTable").hide()
        });

        $("#chemoTherb4Surg_unknown").click(function() {
          $("#chemoTherb4SurgDescRow").hide()
          $("#chemoTherb4SurgDesc").val('')
          $("#chemoTherb4SurgDtRow").hide()
          $("#chemoTherb4SurgDt").val('Select Date')
          $("#chemoTherb4SurgEst").val('')          
          $("#chemoTherb4SurgDt_day").val('')
          $("#chemoTherb4SurgDt_month").val('')
          $("#chemoTherb4SurgDt_year").val('')
          $("#chemoSaveRow").hide()
          $("#ChemoTable").hide()          
        });



        $("#immTherb4Surg_yes").click(function() {
          $("#ImmunoTable").show()
          $("#immSaveRow").show()
          $("#immSaveBtn").hide()
          $("#immCanelBtn").hide()
          $("#immAddBtn").show()                              
        });

        $("#immTherb4Surg_no").click(function() {
          $("#immTherb4SurgDescRow").hide()
          $("#immTherb4SurgDesc").val('')
          $("#immTherb4SurgDtRow").hide()
          $("#immTherb4SurgDt").val('Select Date')
          $("#immTherb4SurgEst").val('')          
          $("#immTherb4SurgDt_day").val('')
          $("#immTherb4SurgDt_month").val('')
          $("#immTherb4SurgDt_year").val('')
          $("#ImmunoTable").hide()
          $("#immSaveRow").hide()
          
        });

        $("#immTherb4Surg_unknown").click(function() {
          $("#immTherb4SurgDescRow").hide()
          $("#immTherb4SurgDesc").val('')
          $("#immTherb4SurgDtRow").hide()
          $("#immTherb4SurgDt").val('Select Date')
          $("#immTherb4SurgEst").val('')
          $("#immTherb4SurgDt_day").val('')
          $("#immTherb4SurgDt_month").val('')
          $("#immTherb4SurgDt_year").val('')
          $("#ImmunoTable").hide()
          $("#immSaveRow").hide()          
        });

        $("#hormTherb4Surg_yes").click(function() {
          $("#HormTable").show()
          $("#hormSaveRow").show()
          $("#hormSaveBtn").hide()
          $("#hormCancelBtn").hide()
          $("#hormAddBtn").show()          
        });

        $("#hormTherb4Surg_no").click(function() {
          $("#hormTherb4SurgDescRow").hide()
          $("#hormTherb4SurgDesc").val('')
          $("#hormTherb4SurgDtRow").hide()
          $("#hormTherb4SurgDt").val('Select Date')
          $("#hormTherb4SurgEst").val('')          
          $("#hormTherb4SurgDt_day").val('')
          $("#hormTherb4SurgDt_month").val('')
          $("#hormTherb4SurgDt_year").val('')
          $("#HormTable").hide()
          $("#hormSaveRow").hide()
        });

        $("#hormTherb4Surg_unknown").click(function() {
          $("#hormTherb4SurgDescRow").hide()
          $("#hormTherb4SurgDesc").val('')
          $("#hormTherb4SurgDtRow").hide()
          $("#hormTherb4SurgDt").val('Select Date')
          $("#hormTherb4SurgEst").val('')
          $("#hormTherb4SurgDt_day").val('')
          $("#hormTherb4SurgDt_month").val('')
          $("#hormTherb4SurgDt_year").val('')          
          $("#HormTable").hide()
          $("#hormSaveRow").hide()          
        });

        $("#othTimScore").click(function() {
          $("#timingOfScoreOsRow").show()
          $("#timingOfScoreOs").focus()
        });
        
        $("#Preoperative").click(function() {
                $("#timingOfScoreOsRow").hide()
                $("#timingOfScoreOs").val('')
        });
        
        $("#Preadjuvant").click(function() {
                $("#timingOfScoreOsRow").hide()
                $("#timingOfScoreOs").val('')
        });
        
        $("#Postadjuvant").click(function() {
                $("#timingOfScoreOsRow").hide()
                $("#timingOfScoreOs").val('')
        });
        
        $("#tosUnknown").click(function() {
                $("#timingOfScoreOsRow").hide()
                $("#timingOfScoreOs").val('')
        });
        
        $("#prevMalignancy_yes").click(function() {
          $("#PrevCancerTable").show()
          $("#prevCancerSave").show()
          $("#prevCancerSaveBtn").hide()
          $("#prevCancerCancelBtn").hide()
          $("#prevCancerSave2").hide()
          $("#prevCancerAddBtn").show()
          
        });

        $("#prevMalignancy_no").click(function() {
          $("#PrevCancerTable").hide()
          $("#prevCancerSave").hide()
          $("#prevCancerDiagDt").val('Select Date')
          $("#prevCancerDiagEst").val('')          
          $("#prevCancerDiagDt_day").val('')
          $("#prevCancerDiagDt_month").val('')
          $("#prevCancerDiagDt_year").val('')
          $("#prevCancerDiagEst").val('')
        });

        $("#prevMalignancy_unknown").click(function() {
          $("#PrevCancerTable").hide()
          $("#prevCancerSave").hide()
          $("#prevCancerDiagDt").val('Select Date')
          $("#prevCancerDiagEst").val('')
          $("#prevCancerDiagDt_day").val('')          
          $("#prevCancerDiagDt_month").val('')
          $("#prevCancerDiagDt_year").val('')
          $("#prevCancerDiagEst").val('')          
        });
        
/*        $("#formHorBirthControl").change(function() {
          var selectedVal = $("#formHorBirthControl").val()
          if (selectedVal=='OTH') {
            $("#othFormHBCRow").show()
            $("#othFormHBCDescRow").show()
            $("#othFormHBC").focus()
          } else {
            $("#othFormHBCRow").hide()
            $("#othFormHBCDescRow").hide()
            $("#othFormHBC").val('')
            $("#othHorBC").val('')
          }
        });*/
        
        
        
/*        $("#formHorReplaceTher").change(function() {
          var selectedVal = $("#formHorReplaceTher").val()
          if (selectedVal=='OTH' && ) {
            $("#othFormHRTRow").show()
            $("#othFormHRTDescRow").show()
            $("#othFormHRT").focus()
          } else {
            $("#othFormHRTRow").hide()
            $("#othFormHRTDescRow").hide()
            $("#othFormHRT").val('')
            $("#othHorRT").val('')
          }
        }); */
        
        
        
        $("#formHBC6").click(function() {
          $("#othFormHBCRow").show()
          $("#othFormHBCDescRow").show()
          $("#othFormHBC").focus()
        });
        $("#formHBC5").click(function() {
          $("#othFormHBCRow").hide()
          $("#othFormHBCDescRow").hide()
          $("#othFormHBC").val('')
          $("#othHorBC").val('')
        });
        $("#formHBC4").click(function() {
          $("#othFormHBCRow").hide()
          $("#othFormHBCDescRow").hide()          
          $("#othFormHBC").val('')
          $("#othHorBC").val('')          
        });
        $("#formHBC3").click(function() {
          $("#othFormHBCRow").hide()
          $("#othFormHBCDescRow").hide()          
          $("#othFormHBC").val('')
          $("#othHorBC").val('')          
        });
        $("#formHBC2").click(function() {
          $("#othFormHBCRow").hide()
          $("#othFormHBCDescRow").hide()          
          $("#othFormHBC").val('')
          $("#othHorBC").val('')          
        });
        $("#formHBC1").click(function() {
          $("#othFormHBCRow").hide()
          $("#othFormHBCDescRow").hide()          
          $("#othFormHBC").val('')
          $("#othHorBC").val('')          
        });
        
        $("#hbcStat1").click(function() {
          $("#HBCTable").show()
          $("#hbcSaveRow").show()
          if (($("#othHorBC").val()) != '') {
            $("#othFormHBCDescRow").show()
          }
          $("#hbcAddBtn").show()
          $("#hbcSaveBtn").hide()
          $("#hbcCancelBtn").hide()
          $("#hbcAddNew").hide()
        });        
        
        $("#hbcStat2").click(function() {
          $("#HBCTable").show()
          $("#hbcSaveRow").show()
          $("#hbcAddBtn").show()
          if (($("#othHorBC").val()) != '') {
            $("#othFormHBCDescRow").show()
          }
          $("#hbcSaveBtn").hide()
          $("#hbcCancelBtn").hide()          
          $("#hbcAddNew").hide()
        });
        
        $("#hbcStat3").click(function() {
          $("#HBCTable").hide()
          $("#hbcSaveRow").hide()
          $("#othFormHBCDescRow").hide()          
        });        
        
        $("#hbcStat4").click(function() {
          $("#HBCTable").hide()
          $("#hbcSaveRow").hide()
          $("#othFormHBCDescRow").hide()
        });
        
        $("#usedHorReplaceTher_yes").click(function() {
          $("#HRTTable").show()
          $("#hrtSaveRow").show()
          if (($("#othHorRT").val()) != '') {
            $("#othFormHRTDescRow").show()
          }
          $("#hrtAddBtn").show()
          $("#hrtSaveBtn").hide()
          $("#hrtCancelBtn").hide()
          $("#hrtAddNew").hide()
        });        
        
        $("#usedHorReplaceTher_no").click(function() {
          $("#HRTTable").hide()
          $("#hrtSaveRow").hide()
          $("#othFormHRTDescRow").hide()
        });        

        $("#usedHorReplaceTher_unknown").click(function() {
          $("#HRTTable").hide()
          $("#hrtSaveRow").hide()
          $("#othFormHRTDescRow").hide()
        });
        
        $("#formHRT5").click(function() {
          $("#othFormHRTRow").show()
          $("#othFormHRTDescRow").show()
          $("#othFormHRT").focus()
        });
        $("#formHRT4").click(function() {
          $("#othFormHRTRow").hide()
          $("#othFormHRTDescRow").hide()          
          $("#othFormHRT").val('')
          $("#othHorRT").val('')          
        });
        $("#formHRT3").click(function() {
          $("#othFormHRTRow").hide()
          $("#othFormHRTDescRow").hide()          
          $("#othFormHRT").val('')
          $("#othHorRT").val('')          
        });
        $("#formHRT2").click(function() {
          $("#othFormHRTRow").hide()
          $("#othFormHRTDescRow").hide()          
          $("#othFormHRT").val('')
          $("#othHorRT").val('')          
        });
        $("#formHRT1").click(function() {
          $("#othFormHRTRow").hide()
          $("#othFormHRTDescRow").hide()          
          $("#othFormHRT").val('')
          $("#othHorRT").val('')          
        });

        $("#wasPregnant_yes").click(function() {
          $("#totPregRow").show()
          $("#totPregnancies").focus()
          $("#totLBRow").show()
          $("#firstChildRow").show()
        });

        $("#wasPregnant_no").click(function() {
          $("#totPregRow").hide()
          $("#totPregnancies").val('')
          $("#totLBRow").hide()
          $("#totLiveBirths").val('')
          $("#firstChildRow").hide()
          $("#ageAt1stChild").val('')
        });

        $("#wasPregnant_unknown").click(function() {
          $("#totPregRow").hide()
          $("#totPregnancies").val('')
          $("#totLBRow").hide()
          $("#totLiveBirths").val('')
          $("#firstChildRow").hide()
          $("#ageAt1stChild").val('')
        });


        $("#sm4").click(function() {
          $("#smkStartRow").show()
          $("#smokeAgeStart").focus()
          $("#smkStopRow").hide()
          $("#cigsPDRow").show()
          $("#numPkYrsRow").show()
        });

          $("#sm3").click(function() {
          $("#smkStartRow").show()
          $("#smokeAgeStart").focus()
          $("#smkStopRow").show()
          $("#cigsPDRow").show()
          $("#numPkYrsRow").show()
        });

          $("#sm2").click(function() {
          $("#smkStartRow").show()
          $("#smokeAgeStart").focus()
          $("#smkStopRow").show()
          $("#cigsPDRow").show()
          $("#numPkYrsRow").show()
        });

          $("#sm5").click(function() {
          $("#smkStartRow").hide()
          $("#smokeAgeStart").val('')
          $("#smkStopRow").hide()
          $("#smokeAgeStop").val('')
          $("#cigsPDRow").hide()
          $("#cigsPerDay").val('')
          $("#numPkYrsRow").hide()
          $("#numPackYearsSm").val('')
        });

          $("#sm1").click(function() {
          $("#smkStartRow").hide()
          $("#smokeAgeStart").val('')
          $("#smkStopRow").hide()
          $("#smokeAgeStop").val('')
          $("#cigsPDRow").hide()
          $("#cigsPerDay").val('')
          $("#numPkYrsRow").hide()
          $("#numPackYearsSm").val('')
        });
        
        
        
      $( "#prevCancerAddBtn" ).button().click(function() {
                      $("#prevCancerAddBtn").hide();
                      $("#prevCancerSaveBtn").show();
                      $("#prevCancerCancelBtn").show();
                      $("#prevCancerSave2").show();
                      $("#prevCancerDiagDesc").val('');
                      $("#prevCancerDiagDesc").focus();
                      $("#prevCancerDiagDt").val('Select Date');
                      $("#prevCancerDiagDt_day").val('');
                      $("#prevCancerDiagDt_month").val('');
                      $("#prevCancerDiagDt_year").val('');
                      $("#prevCancerDiagEst").val('');
                      return false;
              });
              
      $( "#prevCancerSaveBtn" ).button().click(function() {
                if ((($( "#prevCancerDiagDesc" ).val()) == '') && (($( "#prevCancerDiagDt" ).val()) == 'Select Date') && (($( "#prevCancerDiagEst" ).val()) == '')) {
                  alert ('Please enter the previous cancer and the date/how long ago that was diagnosed');
                  $("#prevCancerDiagDesc").focus();
                  return false;
                }else if (($( "#prevCancerDiagDesc" ).val()) == '') {
                  alert ('Please enter the previous cancer that was diagnosed');
                  $("#prevCancerDiagDesc").focus();                  
                  return false;
                }else if ((($( "#prevCancerDiagDt" ).val()) == 'Select Date') && (($( "#prevCancerDiagEst" ).val()) == '')) {
                  alert ('Please enter the date when/how long ago the cancer was diagnosed');
                  return false;
                  //$("#prevCancerDiagEst").focus();
                }else if ((($( "#prevCancerDiagDt" ).val()) != 'Select Date') && (($( "#prevCancerDiagEst" ).val()) != '')) {
                  alert ('Please enter only one of the following for the previous cancer diagnosis : Date when diagnosed or how long ago it was diagnosed');
                  return false;                  
                } else {
                      $("#prevCancerSaveBtn").hide();
                      $("#prevCancerCancelBtn").hide();
                }
              });              
              
      $( "#prevCancerCancelBtn" ).button().click(function() {
                      $("#prevCancerAddBtn").show();
                      $("#prevCancerSaveBtn").hide();
                      $("#prevCancerCancelBtn").hide();
                      $("#prevCancerSave2").hide();
                      return false;
              });
              
              
      $( "#irradAddBtn" ).button().click(function() {
                      $("#irradAddBtn").hide();
                      $("#irradSaveBtn").show();
                      $("#irradCancelBtn").show();
                      $("#irradTherb4SurgDescRow").show()
                      $("#irradTherb4SurgDtRow").show()
                      $("#irradTherb4SurgDesc").focus()
                      return false;
              });
              
      $( "#irradSaveBtn" ).button().click(function() {
                if ((($( "#irradTherb4SurgDesc" ).val()) == '') && (($( "#irradTherb4SurgDt" ).val()) == 'Select Date') && (($( "#irradTherb4SurgEst" ).val()) == '')) {
                  alert ('Please describe the radiation therapy and the date/how long ago that was done');
                  $("#irradTherb4SurgDesc").focus();
                  return false;
                }else if (($( "#irradTherb4SurgDesc" ).val()) == '') {
                  alert ('Please describe the radiation therapy');
                  $("#irradTherb4SurgDesc").focus();                  
                  return false;
                }else if ((($( "#irradTherb4SurgDt" ).val()) == 'Select Date') && (($( "#irradTherb4SurgEst" ).val()) == '')) {
                  alert ('Please enter the date when/how long ago the radiation therapy was done');
                  return false;
                  //$("#irradTherb4SurgEst").focus();
                }else if ((($( "#irradTherb4SurgDt" ).val()) != 'Select Date') && (($( "#irradTherb4SurgEst" ).val()) != '')) {
                  alert ('Please enter only one of the following for radiation therapy : Date of treatment or how long ago it was done');
                  return false;                  
                } else {
                      $("#irradSaveBtn").hide();
                      $("#irradCancelBtn").hide();
                }
              });              
    
              
      $( "#irradCancelBtn" ).button().click(function() {
                      $("#irradAddBtn").show();
                      $("#irradCancelBtn").hide();
                      $("#irradSaveBtn").hide();
                      $("#irradTherb4SurgDescRow").hide()
                      $("#irradTherb4SurgDesc").val('')
                      $("#irradTherb4SurgDtRow").hide()
                      $("#irradTherb4SurgDt").val('Select Date')
                      $("#irradTherb4SurgDt_day").val('')
                      $("#irradTherb4SurgDt_month").val('')
                      $("#irradTherb4SurgDt_year").val('')
                      $("#irradTherb4SurgEst").val('')
                      return false;
              });              
              
      $( "#chemoAddBtn" ).button().click(function() {
                      $("#chemoAddBtn").hide();
                      $("#chemoSaveBtn").show();
                      $("#chemoCancelBtn").show();
                      $("#chemoTherb4SurgDescRow").show()
                      $("#chemoTherb4SurgDtRow").show()          
                      $("#chemoTherb4SurgDesc").focus()
                      return false;
              });
              
      $( "#chemoSaveBtn" ).button().click(function() {
                if ((($( "#chemoTherb4SurgDesc" ).val()) == '') && (($( "#chemoTherb4SurgDt" ).val()) == 'Select Date') && (($( "#chemoTherb4SurgEst" ).val()) == '')) {
                  alert ('Please describe the chemotherapy and the date/how long ago that was done');
                  $("#chemoTherb4SurgDesc").focus();
                  return false;
                }else if (($( "#chemoTherb4SurgDesc" ).val()) == '') {
                  alert ('Please describe the chemotherapy');
                  $("#chemoTherb4SurgDesc").focus();                  
                  return false;
                }else if ((($( "#chemoTherb4SurgDt" ).val()) == 'Select Date') && (($( "#chemoTherb4SurgEst" ).val()) == '')) {
                  alert ('Please enter the date when/how long ago the chemotherapy was done');
                  return false;
                  //$("#chemoTherb4SurgEst").focus();
                }else if ((($( "#chemoTherb4SurgDt" ).val()) != 'Select Date') && (($( "#chemoTherb4SurgEst" ).val()) != '')) {
                  alert ('Please enter only one of the following for chemotherapy : Date of treatment or how long ago it was done');
                  return false;                  
                } else {
                      $("#chemoSaveBtn").hide();
                      $("#chemoCancelBtn").hide();
                }
              });

      $( "#chemoCancelBtn" ).button().click(function() {
                      $("#chemoAddBtn").show();
                      $("#chemoSaveBtn").hide();
                      $("#chemoCancelBtn").hide();
                      $("#chemoTherb4SurgDescRow").hide()
                      $("#chemoTherb4SurgDesc").val('')
                      $("#chemoTherb4SurgDtRow").hide()
                      $("#chemoTherb4SurgDt").val('Select Date')
                      $("#chemoTherb4SurgDt_day").val('')
                      $("#chemoTherb4SurgDt_month").val('')
                      $("#chemoTherb4SurgDt_year").val('')
                      $("#chemoTherb4SurgEst").val('')
                      return false;
              });

              
      $( "#immAddBtn" ).button().click(function() {
                      $("#immAddBtn").hide();
                      $("#immSaveBtn").show();
                      $("#immCancelBtn").show();
                      $("#immTherb4SurgDescRow").show()
                      $("#immTherb4SurgDtRow").show()
                      $("#immTherb4SurgDesc").focus()
                      return false;
              });
              
      $( "#immSaveBtn" ).button().click(function() {
                if ((($( "#immTherb4SurgDesc" ).val()) == '') && (($( "#immTherb4SurgDt" ).val()) == 'Select Date') && (($( "#immTherb4SurgEst" ).val()) == '')) {
                  alert ('Please describe the immuno therapy and the date/how long ago that was done');
                  $("#immTherb4SurgDesc").focus();
                  return false;
                }else if (($( "#immTherb4SurgDesc" ).val()) == '') {
                  alert ('Please describe the immuno therapy');
                  $("#immTherb4SurgDesc").focus();                  
                  return false;
                }else if ((($( "#immTherb4SurgDt" ).val()) == 'Select Date') && (($( "#immTherb4SurgEst" ).val()) == '')) {
                  alert ('Please enter the date when/how long ago the immuno therapy was done');
                  return false;
                  //$("#immTherb4SurgEst").focus();
                }else if ((($( "#immTherb4SurgDt" ).val()) != 'Select Date') && (($( "#immTherb4SurgEst" ).val()) != '')) {
                  alert ('Please enter only one of the following for immuno therapy : Date of treatment or how long ago it was done');
                  return false;                  
                } else {
                      $("#immSaveBtn").hide();
                      $("#immCancelBtn").hide();
                }
              });              
              
      $( "#immCancelBtn" ).button().click(function() {
                      $("#immAddBtn").show();
                      $("#immSaveBtn").hide();
                      $("#immCancelBtn").hide();
                      $("#immTherb4SurgDescRow").hide()
                      $("#immTherb4SurgDesc").val('')
                      $("#immTherb4SurgDtRow").hide()
                      $("#immTherb4SurgDt").val('Select Date')
                      $("#immTherb4SurgDt_day").val('')
                      $("#immTherb4SurgDt_month").val('')
                      $("#immTherb4SurgDt_year").val('')
                      $("#immTherb4SurgEst").val('')
                      return false;
              });
              
      $( "#hormAddBtn" ).button().click(function() {
                      $("#hormAddBtn").hide();
                      $("#hormSaveBtn").show();
                      $("#hormCancelBtn").show();
                      $("#hormTherb4SurgDescRow").show()
                      $("#hormTherb4SurgDtRow").show()          
                      $("#hormTherb4SurgDesc").focus()
                      return false;
              });
              
      $( "#hormSaveBtn" ).button().click(function() {
                if ((($( "#hormTherb4SurgDesc" ).val()) == '') && (($( "#hormTherb4SurgDt" ).val()) == 'Select Date') && (($( "#hormTherb4SurgEst" ).val()) == '')) {
                  alert ('Please describe the hormonal therapy and the date/how long ago that was done');
                  $("#hormTherb4SurgDesc").focus();
                  return false;
                }else if (($( "#hormTherb4SurgDesc" ).val()) == '') {
                  alert ('Please describe the hormonal therapy');
                  $("#hormTherb4SurgDesc").focus();                  
                  return false;
                }else if ((($( "#hormTherb4SurgDt" ).val()) == 'Select Date') && (($( "#hormTherb4SurgEst" ).val()) == '')) {
                  alert ('Please enter the date when/how long ago the hormonal therapy was done');
                  return false;
                  //$("#hormTherb4SurgEst").focus();
                }else if ((($( "#hormTherb4SurgDt" ).val()) != 'Select Date') && (($( "#hormTherb4SurgEst" ).val()) != '')) {
                  alert ('Please enter only one of the following for hormonal therapy : Date of treatment or how long ago it was done');
                  return false;                  
                } else {
                      $("#hormSaveBtn").hide();
                      $("#hormCancelBtn").hide();
                }
              });                            
              
      $( "#hormCancelBtn" ).button().click(function() {
                      $("#hormAddBtn").show();
                      $("#hormSaveBtn").hide();
                      $("#hormCancelBtn").hide();
                      $("#hormTherb4SurgDescRow").hide()
                      $("#hormTherb4SurgDesc").val('')
                      $("#hormTherb4SurgDtRow").hide()
                      $("#hormTherb4SurgDt").val('Select Date')
                      $("#hormTherb4SurgDt_day").val('')
                      $("#hormTherb4SurgDt_month").val('')
                      $("#hormTherb4SurgDt_year").val('')
                      $("#hormTherb4SurgEst").val('')
                      return false;
              });
              
              
      $( "#hbcAddBtn" ).button().click(function() {
                      $("#hbcAddBtn").hide();
                      $("#hbcSaveBtn").show();
                      $("#hbcCancelBtn").show();
                      $("#hbcAddNew").show()
                      $("#formHorBirthControl").val('')
                      $("#hormBCDur").val('')
                      $("#hormBCLast").val('')
                      return false;
              });
              
      $( "#hbcSaveBtn" ).button().click(function() {
                      var form = $("#formHorBirthControl").val();
                      var other = $("#formHBCOtherSpecification").val();
                      var dur = $("#hormBCDur").val();
                      var last = $("#hormBCLast").val();
                      
                      if (form == null) form = '';
                      else form = form.replace(/^\s+|\s+$/g,'');  
                      
                      if (other == null) other = '';
                      else other = other.replace(/^\s+|\s+$/g,'');  
                      
                      if (dur == null) dur = '';
                      else dur = dur.replace(/^\s+|\s+$/g,'');  
                      
                      if (last == null) last = '';
                      else last = last.replace(/^\s+|\s+$/g,'');  
                                           
                      if (other.toUpperCase() == 'N/A') other = '';
                      if (other.toLowerCase() == 'not applicable') other = '';                    
                      $("#formHBCOtherSpecification").val(other)
                      
                      if (form == ''){
                          alert("Any value of 'Form' item is not selected.");
                          return false ;
                      }
                      
                      if ((form == 'OTH')&&(other == '')) {
                          alert("The value of 'Form' item is 'Other', it should be specified.");
                          return false ;
                      }
                      if ((form != 'OTH')&&(other.length > 0)) {
                          alert("The value of 'Form' item is '" + form + "', it is not needed to be specified more.");
                          return false;
                      }
                      
                      var i = 0
                      for(i=0;i<2;i++)
                      {
                          var item = dur
                          var itemName = "Duration"
                          var eleId = "hormBCDur"
                          if (i != 0)
                          {
                              item = last
                              itemName = "Time since last usage"
                              eleId = "hormBCLast"    
                          }
                          
                          if (item == '')
                          {
                              alert("The value of '" + itemName + "' is null." );
                              return false;
                          }
                          else
                          {
                              var value = parseFloat(item)  
                              if (isNaN(value))
                              {
                                  alert("The value of '" + itemName + "' is not numeric." );
                                  return false;   
                              }
                              if (value < 0)
                              {
                                  alert("The value of '" + itemName + "' is less than 0." );
                                  return false;   
                              }
                              if (value == 0)
                              {
                                  var answer = confirm("The value of '" + itemName + "' is 0. Are you sure?" )   
                                  if (answer != true)
                                  {
                                      $("#" + eleId).val('');
                                      return false
                                  }   
                              }
                          }
                      }
                                
                      $("#hbcSaveBtn").hide();
                      $("#hbcCancelBtn").hide();
                      return true;
              });              
              
      $( "#hbcCancelBtn" ).button().click(function() {
                      $("#hbcAddBtn").show();
                      $("#hbcSaveBtn").hide();
                      $("#hbcCancelBtn").hide();
                      $("#hbcAddNew").hide()
                      $("#formHorBirthControl").val('')
                      $("#hormBCDur").val('')
                      $("#hormBCLast").val('')
                      return false;
              });
              
              
      $( "#hrtAddBtn" ).button().click(function() {
                      $("#hrtAddBtn").hide();
                      $("#hrtSaveBtn").show();
                      $("#hrtCancelBtn").show();
                      $("#hrtAddNew").show()
                      $("#formHorReplaceTher").val('')
                      $("#typeHorReplaceTher").val('')
                      $("#hormRTDur").val('')
                      $("#hormRTLast").val('')
                      return false;
              });
              
       $( "#hrtSaveBtn" ).button().click(function() {
                      var form = $("#formHorReplaceTher").val();
                      var other = $("#formHRTOtherSpecification").val();
                      var type = $("#typeHorReplaceTher").val();
                      var dur = $("#hormRTDur").val();
                      var last = $("#hormRTLast").val();
                      
                      if (form == null) form = '';
                      else form = form.replace(/^\s+|\s+$/g,'');  
                      
                      if (other == null) other = '';
                      else other = other.replace(/^\s+|\s+$/g,'');  
                      
                      if (type == null) type = '';
                      else type = type.replace(/^\s+|\s+$/g,'');  
                      
                      if (dur == null) dur = '';
                      else dur = dur.replace(/^\s+|\s+$/g,'');  
                      
                      if (last == null) last = '';
                      else last = last.replace(/^\s+|\s+$/g,'');  
                                            
                      if (other.toUpperCase() == 'N/A') other = '';
                      if (other.toLowerCase() == 'not applicable') other = '';
                      $("#formHRTOtherSpecification").val(other)
                      
                      if (form == '')
                      {
                          alert("Any value of 'Form' item is not selected.");
                          return false ;
                      }
                      if (type == '')
                      {
                          alert("Any value of 'Type' item is not selected.");
                          return false ;
                      }
                      
                      if ((form == 'OTH')&&(other == ''))
                      {
                          alert("The value of 'Form' item is 'Other', it should be specified.");
                          return false ;
                      }
                      if ((form != 'OTH')&&(other.length > 0))
                      {
                          alert("The value of 'Form' item is '" + form + "', it is not needed to be specified more.");
                          return false;
                      }
                      
                      var i = 0
                      for(i=0;i<2;i++)
                      {
                          var item = dur
                          var itemName = "Duration"
                          var eleId = "hormRTDur"
                          if (i != 0)
                          {
                              item = last
                              itemName = "Time since last usage"
                              eleId = "hormRTLast"    
                          }
                          
                          if (item == '')
                          {
                              alert("The value of '" + itemName + "' is null." );
                              return false;
                          }
                          else
                          {
                              var value = parseFloat(item)  
                              if (isNaN(value))
                              {
                                  alert("The value of '" + itemName + "' is not numeric." );
                                  return false;   
                              }
                              if (value < 0)
                              {
                                  alert("The value of '" + itemName + "' is less than 0." );
                                  return false;   
                              }
                              if (value == 0)
                              {
                                  var answer = confirm("The value of '" + itemName + "' is 0. Are you sure?" )   
                                  if (answer != true)
                                  {
                                      $("#" + eleId).val('');
                                      return false
                                  }   
                              }
                          }
                      }
                      
                      $("#hrtSaveBtn").hide();
                      $("#hrtCancelBtn").hide();
                      return true;
              });              
              
      $( "#hrtCancelBtn" ).button().click(function() {
                      $("#hrtAddBtn").show();
                      $("#hrtSaveBtn").hide();
                      $("#hrtCancelBtn").hide();
                      $("#hrtAddNew").hide()
                      $("#formHorReplaceTher").val('')
                      $("#typeHorReplaceTher").val('')
                      $("#hormRTDur").val('')
                      $("#hormRTLast").val('')
                      return false;
              });
              
        $( "#secHandSmHist2" ).click(function() {
          $("#secHandSmokeHistYesDiv").show();
        });
        
        $( "#secHandSmHist1" ).click(function() {
          $("#secHandSmokeHistYesDiv").hide();
          $("#secHandSmHist3").prop('checked', false);
          $("#secHandSmHist4").prop('checked', false);
        });
        
        $( "#secHandSmHist5" ).click(function() {
          $("#secHandSmokeHistYesDiv").hide();
          $("#secHandSmHist3").prop('checked', false);
          $("#secHandSmHist4").prop('checked', false);
        });        
        
        $( "#smokeAgeStop" ).blur(function() {
            calcPkYrs();
        });
        
        $( "#smokeAgeStart" ).blur(function() {
            calcPkYrs();
        });

        $( "#cigsPerDay" ).blur(function() {
            calcPkYrs();
        });
        
        function calcPkYrs() {
            var stopVal;
            var startVal;
            var cigsPerDay;
            var errMsg1 = 'Age at which the participant started smoking cannot be more than when they quit smoking';
            if ($( "#smokeAgeStart" ).val()!="") {
                startVal = parseFloat($( "#smokeAgeStart" ).val());
            }

            if ($('#sm4').is(':checked')) {
                    if ($( "#ageAsOfConsentedDate" ).val()!="") {
                    stopVal = parseFloat($( "#ageAsOfConsentedDate" ).val());
                    errMsg1 = 'Age at which the participant started smoking cannot be more than their current age';
                }
            } else {
                if ($( "#smokeAgeStop" ).val()!="") {
                    stopVal = parseFloat($( "#smokeAgeStop" ).val());
                }
            }
                
            


            if ($( "#cigsPerDay" ).val()!="") {
                cigsPerDay = parseFloat($( "#cigsPerDay" ).val());
                
            }            
            
                if (isNaN(stopVal) || isNaN(startVal) || isNaN(cigsPerDay)) {
                        $( "#numPackYearsSm" ).val('');
                        $( "#numPackYearsSmLbl" ).html('');
                        
                } else {
                    if (stopVal < startVal) {
                        alert (errMsg1);
                    } else {
                            $( "#numPackYearsSm" ).val(((((stopVal - startVal)*cigsPerDay)/20)).toFixed(1));
                            $( "#numPackYearsSmLbl" ).html(((((stopVal - startVal)*cigsPerDay)/20)).toFixed(1));
                    }                    
                }            
        }
        
   
$("#smokeAgeStart").AllowNumericOnly();
$("#smokeAgeStop").AllowNumericOnly();
$("#cigsPerDay").AllowNumericOnly();
        
    });
    
    function isNumericValidation(ele)
    {
      if (isNaN(ele.value)) {
         ele.value=""
      } else {
        //alert ('is a number', ele.value);
      }
    }
                   
    function checkRelatives(theform, itemNum)
    {
        var checked = false
        if (theform.bloodRelCancer14.checked == true)
        {
            if (theform.bloodRelCancer1.checked == true) checked = true
            if (theform.bloodRelCancer2.checked == true) checked = true
            if (theform.bloodRelCancer3.checked == true) checked = true
            if (theform.bloodRelCancer4.checked == true) checked = true
            if (theform.bloodRelCancer5.checked == true) checked = true
            if (theform.bloodRelCancer6.checked == true) checked = true
            if (theform.bloodRelCancer7.checked == true) checked = true
            if (theform.bloodRelCancer8.checked == true) checked = true
            if (theform.bloodRelCancer9.checked == true) checked = true
            if (theform.bloodRelCancer10.checked == true) checked = true
            if (theform.bloodRelCancer11.checked == true) checked = true
            if (theform.bloodRelCancer12.checked == true) checked = true
            if (theform.bloodRelCancer13.checked == true) checked = true
        }
        if (checked)
        {
            if (itemNum == 14)
            {
                alert('Please uncheck the other relatives before checking \'None\'.')
            }
            theform.bloodRelCancer14.checked = false;
        }
    }
                   

                   
