
    $(document).ready(function() {
        $(".addtlRows").hide();

        $("#isAscitesFldColl_yes").click(function() {
            $("#collAscFluidRow").show()
            $("#collAscFluid").focus()
        });

        $("#isAscitesFldColl_no").click(function() {
            $("#collAscFluidRow").hide()
            $("#collAscFluid").val('')
        });

        $("#isPelvicWashColl_yes").click(function() {
            $("#collPelvicWashRow").show()
            $("#collPelvicWash").focus()
        });

        $("#isPelvicWashColl_no").click(function() {
            $("#collPelvicWashRow").hide()
            $("#collPelvicWash").val('')
        });

        $("#insulinAdmin_yes").click(function() {
            $("#InsulinRow1").show()
            $("#InsulinRow2").show()
            $("#insul1Name").focus()
        });

        $("#insulinAdmin_no").click(function() {
            $("#InsulinRow1").hide()
            $("#InsulinRow2").hide()
            $("#insul1Name").val('')
            $("#insul1Dose").val('')
            $("#insul1Unit").val('')
            $("#insul1Time").val('')

            $("#insul2Name").val('')
            $("#insul2Dose").val('')
            $("#insul2Unit").val('')
            $("#insul2Time").val('')
        });

        $("#steroidAdmin_yes").click(function() {
            $("#SteroidRow1").show()
            $("#SteroidRow2").show()
            $("#steroid1Name").focus()
        });

        $("#steroidAdmin_no").click(function() {
            $("#SteroidRow1").hide()
            $("#SteroidRow2").hide()
            $("#steroid1Name").val('')
            $("#steroid1Dose").val('')
            $("#steroid1Unit").val('')
            $("#steroid1Time").val('')

          $("#steroid2Name").val('')
          $("#steroid2Dose").val('')
          $("#steroid2Unit").val('')          
          $("#steroid2Time").val('')
        });

        $("#antibioAdmin_yes").click(function() {
            $("#AntibioRow1").show()
            $("#AntibioRow2").show()
            $("#antibio1Name").focus()
        });

        $("#antibioAdmin_no").click(function() {
            $("#AntibioRow1").hide()
            $("#AntibioRow2").hide()
            $("#antibio1Name").val('')
            $("#antibio1Dose").val('')
            $("#antibio1Unit").val('')
            $("#antibio1Time").val('')

            $("#antibio2Name").val('')
            $("#antibio2Dose").val('')
            $("#antibio2Unit").val('')            
            $("#antibio2Time").val('')
        });

        $("#othMedAdmin_yes").click(function() {
            $("#OthMedRow1").show()
            $("#OthMedRow2").show()
            $("#med1Name").focus()
        });

        $("#othMedAdmin_no").click(function() {
            $("#OthMedRow1").hide()
            $("#OthMedRow2").hide()
            $("#med1Name").val('')
            $("#med1Dose").val('')
            $("#med1Unit").val('')
            $("#med1Time").val('')

            $("#med2Name").val('')
            $("#med2Dose").val('')
            $("#med2Unit").val('')            
            $("#med2Time").val('')
        });

        $("#surgicalProc").change(function() {
            if($("#surgicalProc").val() == 'Other-specify') {
                $("#otherSurgicalProcRow").show()
            } else {
                $("#otherSurgicalProc").val('')
                $("#otherSurgicalProcRow").hide()
            }
        });

        $("#surgicalMethod").change(function() {
            if($("#surgicalMethod").val() == 'Other-specify') {
                $("#otherSurgicalMethodRow").show()
            } else {
                $("#otherSurgicalMethod").val('')
                $("#otherSurgicalMethodRow").hide()
            }
        });
        
        $("#co2LevelUnit").change(function() 
        { 
            //alert('CCCCC:' + $("#co2LevelUnit").val())
            if ($("#co2LevelUnit").val() == 'Other, Specify' || $("#co2LevelUnit").val() == 'OTH') {
                $("#co2LevelUnitOtherRow").show()
            } else {
                $("#co2LevelUnitOtherRow").hide()
                $("#co2LevelUnitOther").val('')
            }
        });
        
        
        $("#poSedDiv_yes").click(function() {
            $("#poSedDivRow").show()
        });

        $("#poSedDiv_no").click(function() {
            $("#poSedDivRow").hide()
            if ($("#_poSed1Name").attr('checked')) {
              $("#_poSed1Name").trigger("click")
            }
            if ($("#_poSed2Name").attr('checked')) {
              $("#_poSed2Name").trigger("click")
            }
            if ($("#_poSed3Name").attr('checked')) {
              $("#_poSed3Name").trigger("click")
            }
            $("#poSed4Name").val('')
            $("#poSed4Name").trigger('keyup')
        });
        
        $("#poOpDiv_yes").click(function() {
            $("#poOpDivRow").show()
        });

        $("#poOpDiv_no").click(function() {
            $("#poOpDivRow").hide()
            if ($("#_poOp1Name").attr('checked')) {
              $("#_poOp1Name").trigger("click")
            }
            if ($("#_poOp2Name").attr('checked')) {
              $("#_poOp2Name").trigger("click")
            }
            if ($("#_poOp3Name").attr('checked')) {
              $("#_poOp3Name").trigger("click")
            }
            if ($("#_poOp4Name").attr('checked')) {
              $("#_poOp4Name").trigger("click")
            }
            $("#poOp5Name").val('')
            $("#poOp5Name").trigger('keyup')
        });

        $("#poAntiemDiv_yes").click(function() {
            $("#poAntiemDivRow").show()
        });

        $("#poAntiemDiv_no").click(function() {
            $("#poAntiemDivRow").hide()
            if ($("#_poAntiem1Name").attr('checked')) {
              $("#_poAntiem1Name").trigger("click")
            }
            if ($("#_poAntiem2Name").attr('checked')) {
              $("#_poAntiem2Name").trigger("click")
            }
            $("#poAntiem3Name").val('')
            $("#poAntiem3Name").trigger('keyup')            
        });

        $("#poAntiAcDiv_yes").click(function() {
            $("#poAntiAcDivRow").show()
        });

        $("#poAntiAcDiv_no").click(function() {
            $("#poAntiAcDivRow").hide()
            if ($("#_poAntiAc1Name").attr('checked')) {
              $("#_poAntiAc1Name").trigger("click")
            }
            $("#poAntiAc2Name").val('')
            $("#poAntiAc2Name").trigger('keyup')
        });

        $("#poMedDiv_yes").click(function() {
            $("#poMedDivRow").show()
        });

        $("#poMedDiv_no").click(function() {
            $("#poMedDivRow").hide()
            $("#poMed1Name").val('')
            $("#poMed1Name").trigger('keyup')            
            $("#poMed2Name").val('')
            $("#poMed2Name").trigger('keyup')            
            $("#poMed3Name").val('')
            $("#poMed3Name").trigger('keyup')
        });

        $("#localAnesDiv_yes").click(function() {
            $("#localAnesDivRow").show()
        });

        $("#localAnesDiv_no").click(function() {
            $("#localAnesDivRow").hide()
            if ($("#_localAnes1Name").attr('checked')) {
              $("#_localAnes1Name").trigger("click")
            }
            if ($("#_localAnes2Name").attr('checked')) {
              $("#_localAnes2Name").trigger("click")
            }
            $("#localAnes3Name").val('')
            $("#localAnes3Name").trigger('keyup')
        });

        $("#regAnesDiv_yes").click(function() {
            $("#regAnesDivRow").show()
        });

        $("#regAnesDiv_no").click(function() {
            $("#regAnesDivRow").hide()
            if ($("#_regAnes1Name").attr('checked')) {
              $("#_regAnes1Name").trigger("click")
            }
            if ($("#_regAnes2Name").attr('checked')) {
              $("#_regAnes2Name").trigger("click")
            }
            $("#regAnes3Name").val('')
            $("#regAnes3Name").trigger('keyup')
        });

        $("#anesDiv_yes").click(function() {
            $("#anesDivRow").show()
        });

        $("#anesDiv_no").click(function() {
            $("#anesDivRow").hide()
            if ($("#_anes1Name").attr('checked')) {
              $("#_anes1Name").trigger("click")
            }
            if ($("#_anes2Name").attr('checked')) {
              $("#_anes2Name").trigger("click")
            }
            if ($("#_anes3Name").attr('checked')) {
              $("#_anes3Name").trigger("click")
            }
            if ($("#_anes4Name").attr('checked')) {
              $("#_anes4Name").trigger("click")
            }
            if ($("#_anes5Name").attr('checked')) {
              $("#_anes5Name").trigger("click")
            }            
            $("#anes6Name").val('')
            $("#anes6Name").trigger('keyup')
        });

        $("#narcOpDiv_yes").click(function() {
            $("#narcOpDivRow").show()
        });

        $("#narcOpDiv_no").click(function() {
            $("#narcOpDivRow").hide()
            if ($("#_narcOp1Name").attr('checked')) {
              $("#_narcOp1Name").trigger("click")
            }
            if ($("#_narcOp2Name").attr('checked')) {
              $("#_narcOp2Name").trigger("click")
            }
            if ($("#_narcOp3Name").attr('checked')) {
              $("#_narcOp3Name").trigger("click")
            }
            if ($("#_narcOp4Name").attr('checked')) {
              $("#_narcOp4Name").trigger("click")
            }
            $("#narcOp5Name").val('')
            $("#narcOp5Name").trigger('keyup')
        });

        $("#musRelaxDiv_yes").click(function() {
            $("#musRelaxDivRow").show()
        });

        $("#musRelaxDiv_no").click(function() {
            $("#musRelaxDivRow").hide()
            if ($("#_musRelax1Name").attr('checked')) {
              $("#_musRelax1Name").trigger("click")
            }
            if ($("#_musRelax2Name").attr('checked')) {
              $("#_musRelax2Name").trigger("click")
            }
            if ($("#_musRelax3Name").attr('checked')) {
              $("#_musRelax3Name").trigger("click")
            }
            $("#musRelax4Name").val('')
            $("#musRelax4Name").trigger('keyup')
        });

        $("#inhalAnesDiv_yes").click(function() {
            $("#inhalAnesDivRow").show()
        });

        $("#inhalAnesDiv_no").click(function() {
            $("#inhalAnesDivRow").hide()
            if ($("#_inhalAnes1Name").attr('checked')) {
              $("#_inhalAnes1Name").trigger("click")
            }
            if ($("#_inhalAnes2Name").attr('checked')) {
              $("#_inhalAnes2Name").trigger("click")
            }
            $("#inhalAnes3Name").val('')
            $("#inhalAnes3Name").trigger('keyup')            
        });

        $("#addtlAnesDiv_yes").click(function() {
            $("#addtlAnesDivRow").show()
        });

        $("#addtlAnesDiv_no").click(function() {
            $("#addtlAnesDivRow").hide()
            $("#addtlAnes1Name").val('')
            $("#addtlAnes1Name").trigger('keyup')            
            $("#addtlAnes2Name").val('')
            $("#addtlAnes2Name").trigger('keyup')            
            $("#addtlAnes3Name").val('')
            $("#addtlAnes3Name").trigger('keyup')
        });
        
            
        
    });
    
    function setMedAdmin(medCheck, medNameId, medNameValue, medDoseId, medUnitId, medTimeId) {
        showHideMeasurements(medCheck.checked, medDoseId, medUnitId, medTimeId)
        document.getElementById(medNameId).value = medCheck.checked ? medNameValue : ''
    }

    function setOtherMedAdmin(medCheck, medDoseId, medUnitId, medTimeId) {
        if($.trim(medCheck.value).length > 0) {
            showHideMeasurements(true, medDoseId, medUnitId, medTimeId)
        } else {
            showHideMeasurements(false, medDoseId, medUnitId, medTimeId)
        }
    }

    function showHideMeasurements(isDisplayed, medDoseId, medUnitId, medTimeId) {
        if(isDisplayed == false) {
            document.getElementById(medDoseId).value = '';
            document.getElementById(medUnitId).value = '';
            document.getElementById(medTimeId).value = '';
        }
        $('#_'+medDoseId).attr('class', isDisplayed ? '' : 'hide');
        $('#_'+medUnitId).attr('class', isDisplayed ? '' : 'hide');
        $('#_'+medTimeId).attr('class', isDisplayed ? '' : 'hide');
    }

    function rowDispfn(tbId, addBtn, nxtAddBtn) {
        document.getElementById(tbId).style.display = 'table-row';
        document.getElementById(addBtn).style.display = 'none';
        document.getElementById(nxtAddBtn).style.display = 'table-row';
    }
    
    function getSecondClampTimeEntry(side)
    {
        var time1
        var elementId
        
        if (side=="Left") {
            elementId = "secondClampTimeLeft"
        } else {
            elementId = "secondClampTimeRight"
        }
        
        time1 = document.getElementById(elementId).value
                
        if (time1 == null) time1 = ''
        else time1 = time1.replace(/^\s+|\s+$/g,'');
        
        var val = getValidTimeFormatData(time1)
        var hasAlerted = false
        if ((val != time1)&&(val == ''))
        {
            alert('Invalid Time data (HH:MM) : ' + time1)   
            hasAlerted = true
        }
        
        document.getElementById(elementId).value = val
        
        if (hasAlerted) document.getElementById(elementId).focus()
    }
    function getTimeDifference(side, fromEle) {
        
        var timeInVivo
        var time1
        var time2 
        
        if (side=="Left") {
            time2 = getValidTimeFormatData(document.getElementById("organResecTimeLeft").value)
            time1 = getValidTimeFormatData(document.getElementById("firstClampTimeLeft").value)
            timeInVivo = document.getElementById("inVivoIntOpIschemPdLeft").value
        } else {
            time2 = getValidTimeFormatData(document.getElementById("organResecTimeRight").value)
            time1 = getValidTimeFormatData(document.getElementById("firstClampTimeRight").value)
            timeInVivo = document.getElementById("inVivoIntOpIschemPdRight").value
        }
        
        if (timeInVivo == null) timeInVivo = ''
        else timeInVivo = timeInVivo.replace(/^\s+|\s+$/g,'');
        
        var val
        var timeDiff = 0
        if ((time1 == '')|| (time2 == '')) val = '';
        else
        {
            if ((parseFloat(time2.split(':')[0])) < (parseFloat(time1.split(':')[0]))) {
                timeDiff = ((parseFloat(time2.split(':')[0])+24)*3600 + parseFloat(time2.split(':')[1])*60) - (parseFloat(time1.split(':')[0])*3600 + parseFloat(time1.split(':')[1])*60)
            } else {
                timeDiff = (parseFloat(time2.split(':')[0])*3600 + parseFloat(time2.split(':')[1])*60) - (parseFloat(time1.split(':')[0])*3600 + parseFloat(time1.split(':')[1])*60)
            }
            
            if (isNaN(timeDiff)) val = '';
            else val = '' + timeDiff/60
        }                  
        
        if (fromEle == 'InVivo')
        {
            if (val != timeInVivo)
            {
                alert('In Vivo Intra-operative Ischemic Period: Any manual input is not allowed at this item.')  
            }
        }
        
        if (side=="Left") 
        {
            document.getElementById("inVivoIntOpIschemPdLeft").value = val
        } else {
            document.getElementById("inVivoIntOpIschemPdRight").value = val
        }
    }

    function addRowsFn(tbId, count) {
        count = parseInt(count);
        if (document.getElementById(tbId+'1Name').value!='') {
            for (i=1;i<count+1;i++) {
                if ((document.getElementById(tbId+i+'_tb').style.display == 'table-row') || (document.getElementById(tbId+i+'Name').value!='')) {
                    continue;
                } else {
                    document.getElementById(tbId+i+'_tb').style.display = 'table-row';
                    if (i==count) {
                        document.getElementById(tbId+'_addBtn').style.display = 'none';
                    }
                    break;
                }
            }
        } else {
            alert ('Please choose a medication before adding another row.');
        }
    }
    