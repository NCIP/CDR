package nci.obbr.cahub

import nci.obbr.cahub.staticmembers.*;
import nci.obbr.cahub.util.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import nci.obbr.cahub.forms.gtex.*;
import nci.obbr.cahub.datarecords.*;
import java.text.SimpleDateFormat;
import nci.obbr.cahub.datarecords.*;
import nci.obbr.cahub.ldacc.*;




class MbbService {

    static transactional = true
    def bpvWorkSheetService
    def ldaccService
    def prcReportService

    def saveFile(params, request) throws Exception{
        def uploadedFile = request.getFile("fileName")
        def originalFileName = uploadedFile.originalFilename.replace(' ', '_')
        def strippedFileName = originalFileName.substring(0,originalFileName.lastIndexOf('.'))                    
        def fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size())                           
        def newFileName = strippedFileName + "-" + Date.parse((new Date()).getDateTimeString()) + "." + fileExtension
        //println("file name" + originalFileName )
        def pathUploads = AppSetting.findByCode("BB_REPORTS_DIR").value
        File dir = new File(pathUploads)
        if(!dir.exists()){
            dir.mkdirs()
        }
        uploadedFile.transferTo( new File(pathUploads, newFileName) )
        def posMap = parseFile(pathUploads + File.separator + newFileName)
       
        def cid = params.get("caseRecord.id")
        def caseRecord = CaseRecord.findById(cid)
        def caseId = caseRecord.caseId
        def tissueRecoveryBrain = caseRecord.tissueRecoveryBrain
        def brainTissues
        if(!tissueRecoveryBrain){
            tissueRecoveryBrain = new TissueRecoveryBrain()
            tissueRecoveryBrain.caseRecord = caseRecord
            tissueRecoveryBrain.uploadedFilename_bt = originalFileName
            tissueRecoveryBrain.save(failOnError:true)
        }else{
            tissueRecoveryBrain.uploadedFilename_bt = originalFileName
            tissueRecoveryBrain.save(failOnError:true)
            
            brainTissues = tissueRecoveryBrain.brainTissues 
        }
        
        if(brainTissues){
            brainTissues.each{
                def position=it.position
                def sampleId = posMap.get(position)
                it.sampleId = sampleId
                it.save(failOnError:true)
            }
        }else{
            def parentSample = getParentSample(caseRecord)
            def parentSampleId = parentSample.specimenId
            posMap.each{key, value->
               
                if(key=='A01'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='A01'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('HPCMP')
                    brainTissue.collSampleId= parentSampleId  + " R1a"
                    brainTissue.collSampleId2= "HIPP-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='A02'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='A02'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('HPCMP')
                    brainTissue.collSampleId= parentSampleId  +" R1b"
                    brainTissue.collSampleId2= "HIPP-b"
                    brainTissue.save(failOnError:true)
                 
                }else if(key=='A03'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='A03'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('SBNG')
                    brainTissue.collSampleId= parentSampleId  + " R2a"
                    brainTissue.collSampleId2= "SN-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='A04'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='A04'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('SBNG')
                    brainTissue.collSampleId= parentSampleId + " R2b"
                    brainTissue.collSampleId2= "SN-b"
                    brainTissue.save(failOnError:true)
                }else if(key=='A05'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='A05'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('ACCOR')
                    brainTissue.collSampleId= parentSampleId +" R3a"
                    brainTissue.collSampleId2= "Cing24-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='A06'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='A06'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('ACCOR')
                    brainTissue.collSampleId= parentSampleId + " R3b"
                    brainTissue.collSampleId2= "Cing24-b"
                    brainTissue.save(failOnError:true)
                }else if(key=='A07'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='A07'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('AMGD')
                    brainTissue.collSampleId= parentSampleId + " R4a"
                    brainTissue.collSampleId2= "Amg-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='A08'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='A08'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('AMGD')
                    brainTissue.collSampleId= parentSampleId + " R4b"
                    brainTissue.collSampleId2= "Amg-b"
                    brainTissue.save(failOnError:true)
                }else if(key=='A09'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='A09'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('CADT')
                    brainTissue.collSampleId= parentSampleId +  " R5a"
                    brainTissue.collSampleId2= "Cd-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='B01'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='B01'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('CADT')
                    brainTissue.collSampleId= parentSampleId + " R5b"
                    brainTissue.collSampleId2= "Cd-b"
                    brainTissue.save(failOnError:true)
                }else if(key=='B02'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='B02'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('NCLA')
                    brainTissue.collSampleId= parentSampleId + " R6a"
                    brainTissue.collSampleId2= "NA-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='B03'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='B03'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('NCLA')
                    brainTissue.collSampleId= parentSampleId + " R6b"
                    brainTissue.collSampleId2= "NA-b"
                    brainTissue.save(failOnError:true)
                }else if(key=='B04'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='B04'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('PTMN')
                    brainTissue.collSampleId= parentSampleId + " R7a"
                    brainTissue.collSampleId2= "Pt-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='B05'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='B05'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('PTMN')
                    brainTissue.collSampleId= parentSampleId  + " R7b"
                    brainTissue.collSampleId2= "Pt-b"
                    brainTissue.save(failOnError:true)
                }else if(key=='B06'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='B06'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('HPTLM')
                    brainTissue.collSampleId= parentSampleId  + " R8a"
                    brainTissue.collSampleId2= "Hyp-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='B07'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='B07'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('HPTLM')
                    brainTissue.collSampleId= parentSampleId  + " R8b"
                    brainTissue.collSampleId2= "Hyp-b"
                    brainTissue.save(failOnError:true)
                }else if(key=='B08'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='B08'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('SPNC')
                    brainTissue.collSampleId= parentSampleId  + " R9a"
                    brainTissue.collSampleId2= "SC-C1-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='B09'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='B09'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('SPNC')
                    brainTissue.collSampleId= parentSampleId  + " R9b"
                    brainTissue.collSampleId2= "SC-C1-b"
                    brainTissue.save(failOnError:true)
                }else if(key=='C01'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='C01'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('FRCOR')
                    brainTissue.collSampleId= parentSampleId  + " R10a"
                    brainTissue.collSampleId2= "FC-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='C02'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='C02'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('FRCOR')
                    brainTissue.collSampleId= parentSampleId  + " R10b"
                    brainTissue.collSampleId2= "FC-b"
                    brainTissue.save(failOnError:true)
                }else if(key=='C03'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='C03'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('CRBHS')
                    brainTissue.collSampleId= parentSampleId  + " R11a"
                    brainTissue.collSampleId2= "CBH-a"
                    brainTissue.save(failOnError:true)
                }else if(key=='C04'){
                    def brainTissue= new BrainTissue()
                    brainTissue.tissueRecoveryBrain=tissueRecoveryBrain
                    brainTissue.position='C04'
                    brainTissue.sampleId = value
                    brainTissue.tissueType= AcquisitionType.findByCode('CRBHS')
                    brainTissue.collSampleId= parentSampleId  + " R11b"
                    brainTissue.collSampleId2= "CBH-b"
                    brainTissue.save(failOnError:true)
                }else{
                 
                }
             
               
            }   
        
        
        
        }
       
       
        
    }
   
    
    Map parseFile(String file) throws Exception{
        def result=[:]
        try{
            InputStream myxls = new FileInputStream(file);
            HSSFWorkbook wb     = new HSSFWorkbook(myxls);
            HSSFSheet sheet = wb.getSheet("Sample Info"); 
            for(int i = 2; i <=23; i++){
         
                HSSFRow row     = sheet.getRow(i);
                HSSFCell cellA   = row.getCell((short)0);
                HSSFCell cellB   = row.getCell((short)1);

             

                if(cellA.getCellType() == HSSFCell.CELL_TYPE_STRING ){
                    result.put(cellA.getStringCellValue(), cellB.getStringCellValue())
                }

            }
        }catch(Exception e){
            throw new Exception("Wrong file format")
        }
        if (result.size() == 0){
            throw new Exception("Wrong file format")
        }
       
        return result
    }  
   
    
    
    def updateForm(tissueRecoveryBrainInstance, params){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        tissueRecoveryBrainInstance.started = true
        tissueRecoveryBrainInstance.save(failOnError:true)
         
        params.each{key,value->
            if(value == 'is_id'){
                def brainTissue = BrainTissue.get(key)
                //brainTissue.mass = params.get("mass_" + key)
                def mass=params.get("mass_" + key)
                //println("mass: " + mass)
                if(mass)
                brainTissue.mass = Float.parseFloat(mass)
                else
                brainTissue.mass = null
               // println("collected" + params.get("collected_" + key))
                if(params.get("collected_" + key) || brainTissue.specimenRecord){
                   brainTissue.collected = true
                }else{
                     brainTissue.collected = false
                }
                brainTissue.collectionDateStr=params.get("date_" + key)
                brainTissue.collectionTimeStr=params.get("time_" + key)
                brainTissue.notes = params.get("notes_" + key)
                if(brainTissue.collectionDateStr && isDate(brainTissue.collectionDateStr) && brainTissue.collectionTimeStr){
                    brainTissue.collectionDate=df.parse(brainTissue.collectionDateStr + " " + brainTissue.collectionTimeStr )
                }else{
                    brainTissue.collectionDate=null
                }
                brainTissue.save(failOnError:true)
            }
             
        }
         
    }
    def submitForm(tissueRecoveryBrainInstance){
        def caseRecord = tissueRecoveryBrainInstance.caseRecord
        def parentSample = getParentSample(caseRecord)
         
        // println("parentSample id: " + parentSample.specimenId)
        tissueRecoveryBrainInstance.dateSubmitted= new Date()
        tissueRecoveryBrainInstance.save(failOnError:true)
        def brainTissues =  tissueRecoveryBrainInstance.brainTissues
        brainTissues.each{
            if(!it.specimenRecord && it.collected){
                def specimenRecord = new SpecimenRecord()
                specimenRecord.caseRecord = caseRecord
                specimenRecord.parentSpecimen=parentSample
                def fixative = Fixative.findByCode('FROZEN')
                specimenRecord.fixative = fixative
                specimenRecord.specimenId=it.collSampleId.replace(' ', '-').toUpperCase()
                specimenRecord.provisionalTissueType = it.tissueType
                specimenRecord.tissueType = it.tissueType
                specimenRecord.save(failOnError:true)
                it.specimenRecord=specimenRecord
                it.save(failOnError:true)
            }
        }
        
    }
    
    def resumeEditing(tissueRecoveryBrainInstance){
        tissueRecoveryBrainInstance.dateSubmitted= null
        tissueRecoveryBrainInstance.save(failOnError:true)
    }
    
    static boolean isDate(dateStr){
        boolean result=false
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        df.setLenient(false);
        try{
        
            def date = df.parse(dateStr)
            result=true
        }catch (Exception e){
             
        }
          
        return result
        
        
    }
    
    def brainRemovedDateTime(caseRecord){
        def result = null
        def firstTissueRemovedDate = caseRecord.tissueRecoveryGtex?.firstTissueRemovedDate
        def firstTissueRemovedTime = caseRecord.tissueRecoveryGtex?.firstTissueRemovedTime
        def firstTissueRemovedDateTime = ldaccService.calculateDateWithTime(firstTissueRemovedDate,firstTissueRemovedTime)
        
                               
         
         
        def specimen_id = caseRecord.caseId + "-0011"
        def specimen = SpecimenRecord.findBySpecimenId(specimen_id)
        if(specimen){
            def removedTime = specimen.brainTimeStartRemoval
            result = ldaccService.getDateTimeAfter(firstTissueRemovedDateTime, removedTime)
        }
        return result
    }
    
    
    def getRins(caseRecord){
        def result=[:]
        def rinResult = QcResult.executeQuery("select  s.privateId,  r.value  from Donor d inner join d.specimens s inner join s.qcs q inner join q.qcResults r, CaseRecord cd where d.caseRecord =cd and r.attribute='RIN Number' and cd.id=? ", [caseRecord.id])
        rinResult.each{
            def specimenId = it[0].toUpperCase()
            def value
            if(result.get(specimenId)){
                value=result.get(specimenId) + "," + prcReportService.formatRin(it[1])
            }else{
                value=prcReportService.formatRin(it[1])
            }
            result.put(specimenId, value)
        }
        return result
    }
    
    def getBrainTissues(tissueRecoveryBrainInstance){
        def brainTissues = tissueRecoveryBrainInstance.brainTissues.sort{it.position}
        def removedTime=brainRemovedDateTime(tissueRecoveryBrainInstance.caseRecord)
        def rinMap = getRins(tissueRecoveryBrainInstance.caseRecord)
        // println("size: " + rinMap.size())
        brainTissues.each{
            def collectionDate =it.collectionDate
            def interval = bpvWorkSheetService.calculateInterval(removedTime, collectionDate)
            it.interval = interval
            if(!rinMap && it.collected){
                it.rin='Pending'
            }else if(rinMap && it.collected){
                def specimenId = it.collSampleId.replace(' ', '-').toUpperCase()
                def rin = rinMap.get(specimenId)
                if(!rin)
                rin='NA'
                it.rin = rin 
                       
            }else{
                it.rin=""
            }
        }
        return brainTissues
    }
    
    
    def exportForm(tissueRecoveryBrainInstance, out){
        //  def caseRecord = tissueRecoveryBrainInstance.caseRecord
      
        Workbook wb = new HSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);

        Sheet sheet = wb.createSheet("Sample Info");
            
        Row row0 = sheet.createRow((short)0);
        Cell cell0_0 = row0.createCell(0);
        cell0_0.setCellValue("Position");
        Cell cell0_1 = row0.createCell(1);
        cell0_1.setCellValue("Sample ID");
        Cell cell0_2 = row0.createCell(2);

        cell0_2.setCellValue("Alias");
        Cell cell0_3 = row0.createCell(3);
        cell0_3.setCellValue("Alias");
        Cell cell0_4 = row0.createCell(4);
        cell0_4.setCellValue("Alias");
        Cell cell0_5 = row0.createCell(5);
        cell0_5.setCellValue("Mass");
        Cell cell0_6 = row0.createCell(6);
        cell0_6.setCellValue("Date of Collection");
        Cell cell0_7 = row0.createCell(7);
        cell0_7.setCellValue("Time of Collection");
        Cell cell0_8 = row0.createCell(8);
        cell0_8.setCellValue("Tissue Site");
        Cell cell0_9 = row0.createCell(9);
        cell0_9.setCellValue("Notes");

        Row row1 = sheet.createRow((short)1);
        Cell cell1_0 = row1.createCell(0);
        cell1_0.setCellStyle(cellStyle);
        Cell cell1_1 = row1.createCell(1);
        cell1_1.setCellStyle(cellStyle);


        Cell cell1_2 = row1.createCell(2);
        cell1_2.setCellValue("Collaborator Participant ID");
        cell1_2.setCellStyle(cellStyle);

        Cell cell1_3 = row1.createCell(3);
        cell1_3.setCellValue("Collaborator Sample ID");
        cell1_3.setCellStyle(cellStyle);

        Cell cell1_4 = row1.createCell(4);
        cell1_4.setCellValue("Collaborator Sample ID_2");
        cell1_4.setCellStyle(cellStyle);

        Cell cell1_5 = row1.createCell(5);
        cell1_5.setCellValue("mg");
        cell1_5.setCellStyle(cellStyle);
        Cell cell1_6 = row1.createCell(6);
        cell1_6.setCellValue("(Date specific sample underwent dissection)");
        cell1_6.setCellStyle(cellStyle);

        Cell cell1_7 = row1.createCell(7);
        cell1_7.setCellValue("(Time specific sample underwent dissection)");
        cell1_7.setCellStyle(cellStyle);

        Cell cell1_8 = row1.createCell(8);
        cell1_8.setCellValue("(Specific brain subregion of tissue sample)");
        cell1_8.setCellStyle(cellStyle);
        Cell cell1_9 = row1.createCell(9);
        cell1_9.setCellStyle(cellStyle);

        
        def brainTissues=getBrainTissues(tissueRecoveryBrainInstance)
        int i = 0;
        brainTissues.each{
            Row row = sheet.createRow((short)i +2);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(it.position)    
            Cell cell1 =row.createCell(1)
            cell1.setCellValue(it.sampleId)   
            Cell cell2 =row.createCell(2)
            cell2.setCellValue(tissueRecoveryBrainInstance.caseRecord.caseId)  
            Cell cell3 =row.createCell(3)
            cell3.setCellValue(it.collSampleId)  
            Cell cell4 =row.createCell(4)
            cell4.setCellValue(it.collSampleId2) 
            Cell cell5 =row.createCell(5)
            cell5.setCellValue(it.mass)
            Cell cell6 =row.createCell(6)
            cell6.setCellValue(it.collectionDateStr)
            Cell cell7 =row.createCell(7)
            cell7.setCellValue(it.collectionTimeStr)
            Cell cell8 =row.createCell(8)
            cell8.setCellValue(it.tissueType.name)
            Cell cell9 =row.createCell(9)
            cell9.setCellValue(it.notes)
            i++;
        }
        

        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 4800);
        sheet.setColumnWidth(3, 5700);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 8000);
        sheet.setColumnWidth(9, 3000);


       
        wb.write(out)
        
        out.close()

      
        
        
    }
    
    
    def getParentSample(caseRecord){
        def tissueType = AcquisitionType.findByCode('BRAIN')
        def specimen = SpecimenRecord.findByCaseRecordAndTissueType(caseRecord, tissueType)
        return specimen
    }
    
    
    
    def feedbackStructCreate(brainBankFeedbackInstance){
        
        try{
            
            def brainStructureInstance
       
            brainStructureInstance = new BrainStructures()
            brainStructureInstance.brainbankfeedback=brainBankFeedbackInstance
            brainStructureInstance.structName = "Spinal Cord"
            brainStructureInstance.description = "Spinal Cord"
            brainStructureInstance.save(failOnError:true)
                
            brainStructureInstance = new BrainStructures()
            brainStructureInstance.brainbankfeedback=brainBankFeedbackInstance
            brainStructureInstance.structName = "C1-Spinal cord short"
            brainStructureInstance.description = "C1-Spinal cord short"
            brainStructureInstance.save(failOnError:true)
                
            brainStructureInstance = new BrainStructures()
            brainStructureInstance.brainbankfeedback=brainBankFeedbackInstance
            brainStructureInstance.structName = "Olfactory Bulbs"
            brainStructureInstance.description = "Olfactory Bulbs"
            brainStructureInstance.save(failOnError:true)
                
            brainStructureInstance = new BrainStructures()
            brainStructureInstance.brainbankfeedback=brainBankFeedbackInstance
            brainStructureInstance.structName = "Brain Stem"
            brainStructureInstance.description = "Brain Stem"
            brainStructureInstance.save(failOnError:true)
            /*
            brainStructureInstance = new BrainStructures()
            brainStructureInstance.brainbankfeedback=brainBankFeedbackInstance
            brainStructureInstance.structName = "Other"
            brainStructureInstance.description = "Other"
            brainStructureInstance.save(failOnError:true)
             */
                
                          
        }
        catch(Exception e){
            throw new RuntimeException(e.toString())
        }
    }
    
    
    def feedbackDamageCreate(brainBankFeedbackInstance){
        
        try{
            
              
            def brainDamageInstance
       
            brainDamageInstance = new BrainDamage()
            brainDamageInstance.brainbankfeedback=brainBankFeedbackInstance
            //brainDamageInstance.bstruct=BrainStructures.findById(1)
            brainDamageInstance.region = "Spinal Cord"
            brainDamageInstance.observation = "No spinal Cord/C1-SC short"
            brainDamageInstance.studyType = "gross inspection"
            brainDamageInstance.save(failOnError:true)
                
            brainDamageInstance = new BrainDamage()
            brainDamageInstance.brainbankfeedback=brainBankFeedbackInstance
            //brainDamageInstance.bstruct=BrainStructures.findById(2)
            brainDamageInstance.region = "Spinal Cord"
            brainDamageInstance.observation = "Deep/Shallow Cuts"
            brainDamageInstance.studyType = "gross inspection"
            brainDamageInstance.save(failOnError:true)
            
            brainDamageInstance = new BrainDamage()
            brainDamageInstance.brainbankfeedback=brainBankFeedbackInstance
            //brainDamageInstance.bstruct=BrainStructures.findById(3)
            brainDamageInstance.region = "Spinal Cord"
            brainDamageInstance.observation = "Damage Temporal/Parietal Lobe"
            brainDamageInstance.studyType = "gross inspection"
            brainDamageInstance.save(failOnError:true)
            
            brainDamageInstance = new BrainDamage()
            brainDamageInstance.brainbankfeedback=brainBankFeedbackInstance
            //brainDamageInstance.bstruct=BrainStructures.findById(3)
            brainDamageInstance.region = "Spinal Cord"
            brainDamageInstance.observation = "No olfactory bulb/ Brainstem"
            brainDamageInstance.studyType = "gross inspection"
            brainDamageInstance.save(failOnError:true)
            
            brainDamageInstance = new BrainDamage()
            brainDamageInstance.brainbankfeedback=brainBankFeedbackInstance
            //brainDamageInstance.bstruct=BrainStructures.findById(3)
            brainDamageInstance.region = "Spinal Cord"
            brainDamageInstance.observation = "Bloody/Hemorrhage"
            brainDamageInstance.studyType = "gross inspection"
            brainDamageInstance.save(failOnError:true)
           
            
                
                          
        }
        catch(Exception e){
            throw new RuntimeException(e.toString())
        }
    }
    
    def feedbackStructUpdate(brainBankFeedbackInstance,params){
        def updateStatus
        // prepopulated list is pslist          
        def pslist=BrainStructures.findAllByBrainbankfeedbackAndUserInput(brainBankFeedbackInstance,false)
       
        // masterlist
        def masterlist=BrainStructures.findAllByBrainbankfeedback(brainBankFeedbackInstance)
        
        try{
            def brainStructureInstance = new BrainStructures()
            if(params.structName ){
                
                updateStatus="savingStruct"
                brainStructureInstance.brainbankfeedback=brainBankFeedbackInstance
                brainStructureInstance.structName = params.structName
                //brainStructureInstance.description = params.description
                brainStructureInstance.userInput=true 
                brainStructureInstance.save(failOnError:true)
            }
            
                
            
            pslist.each(){it->
              
                if(params.get("stchkbox_"+it.id) && params.get("missingStructExists").equals("Yes")){
                    //println "stchkbox_"+it2.id+" saved"
                    it.preSelected=true
                }
                else{
                    it.preSelected=false
                }
                it.save(failOnError:true)
            }
            
            
            masterlist.each(){it1->
              
                if(params.get("editstructName_"+it1.id) && params.get("missingStructExists").equals("Yes")){
                    //println "stchkbox_"+it2.id+" saved"
                    it1.structName=params.get("editstructName_"+it1.id)
                    it1.save(failOnError:true)
                }
                if(params.get("editstructName_"+it1.id) && params.get("missingStructExists").equals("No")){
                    //println "stchkbox_"+it2.id+" saved"
                    //it1.structName=params.get("editstructName_"+it1.id)
                    //it1.userInput=false
                     it1?.delete()
                    //it1.save(failOnError:true)
                }
                
            }
            
                        
        }
        catch(Exception e){
            throw new RuntimeException(e.toString())
        }
       
        return updateStatus
    }
    
    def feedbackDamageUpdate(brainBankFeedbackInstance,params){
        def updateStatus
        
        // all 
        def masterlist=BrainDamage.findAllByBrainbankfeedback(brainBankFeedbackInstance)
        try{
            if(params.damageRegion && params.damageObservation){
                updateStatus="savingDamage"
                def brainDamageInstance = new BrainDamage()
                brainDamageInstance.brainbankfeedback=brainBankFeedbackInstance
                brainDamageInstance.bstruct=BrainStructures.findById(params.damageStructure)
                brainDamageInstance.region = params.damageRegion
                brainDamageInstance.observation = params.damageObservation
                brainDamageInstance.studyType = "gross inspection"
                brainDamageInstance.userInput=true
                brainDamageInstance.save(failOnError:true)
            }
                        
            if(params.hpRegion && params.hpObservation){
                updateStatus="savingDamage"
                def brainDamageInstance = new BrainDamage()
                brainDamageInstance.brainbankfeedback=brainBankFeedbackInstance
                brainDamageInstance.bstruct=BrainStructures.findById(params.damageStructure)
                brainDamageInstance.region = params.hpRegion
                brainDamageInstance.observation = params.hpObservation
                brainDamageInstance.studyType =  "histopathological evaluation"
                brainDamageInstance.userInput=true
                brainDamageInstance.save(failOnError:true)
            }
            
            
            masterlist.each(){it->
                
                
                if(params.get("editDamRegion_"+it.id)){
                    
                    if(it.observation ){
                        it.region=params.get("editDamRegion_"+it.id)
                        it.save(failOnError:true)
                    }
                    
                }               
                
                   
                if( it.observation && !params.get("editDamRegion_"+it.id) && it.studyType.equals("gross inspection")){
                    it.region=null
                    it.save(failOnError:true)
                   
                }
               
                                
                if(params.get("editDamObservation_"+it.id)){
                   
                    if(it.region ){
                        it.observation=params.get("editDamObservation_"+it.id)
                        it.save(failOnError:true)
                    }
                    
                }
                
                if(it.region && !params.get("editDamObservation_"+it.id)&& it.studyType.equals("gross inspection")){
                    it.observation=null
                    it.save(failOnError:true)
                   
                }
               
                
                if(params.get("editHpRegion_"+it.id)){
                    
                    if(it.observation){
                        it.region=params.get("editHpRegion_"+it.id)
                        it.save(failOnError:true)
                    }
                   
                }               
                
                   
                if( it.observation && !params.get("editHpRegion_"+it.id) && it.studyType.equals("histopathological evaluation")){
                    it.region=null
                    it.save(failOnError:true)
                 
                }
               
                                
                if(params.get("editHpObservation_"+it.id)){
                    //println "stchkbox_"+it2.id+" saved"
                    if(it.region){
                        it.observation=params.get("editHpObservation_"+it.id)
                        it.save(failOnError:true)
                    }
                    
                }
                
                if(it.region && !params.get("editHpObservation_"+it.id)&& it.studyType.equals("histopathological evaluation")){
                    it.observation=null
                    it.save(failOnError:true)
                
                }
               
            }
            
            
        }
        catch(Exception e){
            throw new RuntimeException(e.toString())
        }
        return updateStatus
    }
    
    
    def uploadNeuroReport(brainBankFeedbackInstance,request){
        def newFileName
        def fileId
        if(request){
                
            def uploadedFile = request.getFile("fileName")
         
            if(uploadedFile){
                def originalFileName = uploadedFile.originalFilename.replace(' ', '_')
                    
                if(originalFileName){
                    def current_time = (new Date()).getTime()
                    def caseRecord = brainBankFeedbackInstance.caseRecord
                    def studyCode = caseRecord.study.code
                    def pathUploads = AppSetting.findByCode("FILE_STORAGE").value
                    //pathUploads=pathUploads+File.separator +studyCode+File.separator + caseRecord.caseId+File.separator +authority+ File.separator
                    pathUploads=pathUploads+File.separator +studyCode+File.separator + caseRecord.caseId+File.separator +'attachments'+ File.separator
                    def strippedFileName = originalFileName?.substring(0,originalFileName?.lastIndexOf('.'))                    
                    def fileExtension = originalFileName?.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size()) 
               
                    //def newFileName = strippedFileName + "-" + current_time + "." + fileExtension
                    newFileName = strippedFileName + "." + fileExtension
           
                    File dir = new File(pathUploads)
            
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }           
       
                    uploadedFile.transferTo( new File(pathUploads, newFileName) )
            
                    def fileUpload = new FileUpload()
                    fileUpload.caseRecord = caseRecord
                    fileUpload.fileName= newFileName
                    fileUpload.filePath=pathUploads
                    fileUpload.uploadTime = new Date().getDateTimeString()
                    fileUpload.category =CaseAttachmentType.findByCode('BRAINBANK')
                    fileUpload.caseId=caseRecord.caseId
                    fileUpload.bssCode='MBB'
                    //fileUpload?.caseRecord.id=recallInstance.caseRecord.id
               
                    fileUpload.save(failOnError:true)
                    fileId=fileUpload.id
                    
                      
                }           
          
            
            }        
        
        }    
        //return newFileName
        return fileId
    }    
    
}
