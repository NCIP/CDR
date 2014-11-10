package nci.obbr.cahub
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.ldacc.*
import java.text.SimpleDateFormat
import java.text.DecimalFormat
import groovy.xml.StreamingMarkupBuilder



class LdaccService {
    static transactional = true
    def green = ['0011','0014']
    def yellow = ['0001','0002','0003','0004','0005','0006', '0008', '0009', '0007', '0010']
    
    
    def calculateAge(Date deathDate, Date birthDate){
        if(!deathDate || !birthDate)
        return ''
        Calendar death = Calendar.getInstance()
        Calendar birth = Calendar.getInstance()
        
        death.setTime(deathDate)
        birth.setTime(birthDate)
        
        int year1 = death.get(Calendar.YEAR)
        int year2 = birth.get(Calendar.YEAR)
        
        int age = year1 - year2
        
        int month1 = death.get(Calendar.MONTH)
        int month2 = birth.get(Calendar.MONTH)
        
        if(month2 > month1){
            age--
        }else if(month1 == month2){
            int day1 = death.get(Calendar.DAY_OF_MONTH)
            int day2 = birth.get(Calendar.DAY_OF_MONTH)
            
            if(day2 > day1){
                age--
            }
        }
        
        return age
        
    }
    
    
    def calculateDecimalYear(Date deathDate, Date birthDate){
        if(!deathDate || !birthDate)
        return ''
        Calendar death = Calendar.getInstance()
        Calendar birth = Calendar.getInstance()
        
        death.setTime(deathDate)
        birth.setTime(birthDate)
        
        int year1 = death.get(Calendar.YEAR)
        int year2 = birth.get(Calendar.YEAR)
        
        float years
        if(year1 != year2){
            
            
            def days2 = birth.get(Calendar.DAY_OF_YEAR)
            def total2= getTotalDays(year2)
            def firstYears = (total2-days2 + 1)/total2
            
            
            def days1 = death.get(Calendar.DAY_OF_YEAR)
            def total1 = getTotalDays(year1)
            def lastYears = (days1-1)/total1
            
            
            years = year1-year2-1 + firstYears + lastYears
            
           
             
            
        }else{
            def days2 = birth.get(Calendar.DAY_OF_YEAR)
            def days1 = death.get(Calendar.DAY_OF_YEAR)
            def total = getTotalDays(year1)
            
           
            years = (days1 - days2)/total
            
        }
        
        DecimalFormat myFormatter = new DecimalFormat("##.##");
        String result = myFormatter.format(years);
        return result
        
       
        
    }
    
    
    
    
    
    def calculateDecimalYearFromMonth(Date deathDate, Date birthDate){
        
        if(!deathDate || !birthDate)
        return ''
        Calendar death = Calendar.getInstance()
        Calendar birth = Calendar.getInstance()
        
        death.setTime(deathDate)
        birth.setTime(birthDate)
        
        int year1 = death.get(Calendar.YEAR)
        def month1 = death.get(Calendar.MONTH) + 1
        int year2 = birth.get(Calendar.YEAR)
        
        SimpleDateFormat  df = new SimpleDateFormat("MM/dd/yyyy");
        String deathDateStr = '' + month1 +"/01" +"/"+ year1

        
        def newDeathDate = df.parse(deathDateStr)
        
        return calculateDecimalYear(newDeathDate, birthDate)
        
        
    }
    
    
    
    def calculateDecimalYearFromYear(Date deathDate, Date birthDate){
        
        if(!deathDate || !birthDate)
        return ''
        Calendar death = Calendar.getInstance()
        Calendar birth = Calendar.getInstance()
        
        death.setTime(deathDate)
        birth.setTime(birthDate)
        
        int year1 = death.get(Calendar.YEAR)
        int year2 = birth.get(Calendar.YEAR)
        
        SimpleDateFormat  df = new SimpleDateFormat("MM/dd/yyyy");
        String deathDateStr = "01/01/"+ year1
        
        def newDeathDate = df.parse(deathDateStr)
        
        return calculateDecimalYear(newDeathDate, birthDate)
        
        
    }
    
    
    def getTotalDays(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 1, 1);
 
        def days = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        return days
    }
    
   
    
    /* def calculateHours(Date startDate, Date endDate){
    if(startDate && endDate){
    def interval = (endDate.time - startDate.time)/(60*60*1000)
    int hour = (endDate.time - startDate.time)/(60*60*1000)
            
    int min = (interval - hour)*60
    String sMin = min.toString();
    if(sMin.length() == 1)
    sMin = "0"+sMin
    return hour.toString() + ":" + sMin
                
    }else{
    return ""
    }
        
    }*/
    
    
    def calculateInterval(startDate, endDate){
        def result = []
        if(startDate && endDate){
            
            int hour = (endDate.time - startDate.time)/(60*60*1000)
            int min = (endDate.time - startDate.time)/(60*1000) -hour*60
            result<<""+ hour +" hour(s), " + min +" minute(s)"
            int millisecond = endDate.time - startDate.time
            result<<millisecond
            return result
            
            /* DecimalFormat myFormatter = new DecimalFormat("##.#");
            String result = myFormatter.format(hour);
            return result*/

                
        }else{
            //println("in else....")
            result = ['','']
            return result
        }
        
    }
    
    
    def calculateHourMi(Date startDate, Date endDate){
        if(startDate && endDate){
            
            int min = (endDate.time - startDate.time)/(60*1000)
            
            int hour = min/60
            int min2 = min-hour*60
            
            return ""+ hour +" hour(s), " + min2 +" minute(s)"

                
        }else{
            return ""
        }
        
    }
    
    def calculateDateWithTime(Date date, String time){
        
        if(!date || !time)
        return null
        
        SimpleDateFormat  df = new SimpleDateFormat("MM/dd/yyyy");
        String dateStr = df.format(date)
        String dateWithTime = dateStr + ' ' + time
        
        SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return df2.parse(dateWithTime)
     
    }
    
    
    def getDateTimeString(Date date){
        if(!date)
        return null
           
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String dateStr = df.format(date)
        int index = dateStr.indexOf(' ')
        def result=[]
        result[0]=dateStr.substring(0, index)
        result[1]=dateStr.substring(index+1)
        return result
    }
    
    def getDateTimeAfter(Date startingDate, String time){
        Date result = null
        if(!startingDate || !time)
        return result
        // println("in getDateTimeAfter startingDate ${startingDate.toString()}")
        def start = retriveTime(startingDate)
        def startMi = convertTimeToMi(start)
        def endMi = convertTimeToMi(time)
        if(startMi > endMi){
            def nextDate = getNextDay(startingDate)
            return calculateDateWithTime(nextDate, time)
        }else{
            return calculateDateWithTime(startingDate, time)
        }
        
    }
    
    def getDateTimeAfter4To28(Date startingDate, String time){
        if(!startingDate || !time)
        return ''
        // println("startDate: "  + startingDate.toString() + "time: " + time)
        Date date = getDateTimeAfter(startingDate, time)
        def mseconds = calculateInterval(startingDate, date)[1]
        // println("mseconds is: " + mseconds)
        if(mseconds < 4*60*60*1000)
        return getNextDay(date)
        else
        return date
        
      
    }
    
    
    def getDateTimeBefore(Date startingDate, String time){
        Date result = null
        if(!startingDate || !time)
        return result
        def start = retriveTime(startingDate)
        def startMi = convertTimeToMi(start)
        def beforeMi = convertTimeToMi(time)
        if(beforeMi > startMi){
            def previousDate = getPreviousDay(startingDate)
            return calculateDateWithTime(previousDate, time)
        }else{
            return calculateDateWithTime(startingDate, time)
        }
        
    }
    
    def getNextDay(Date date){
        return date + 1
    }
    
    def getPreviousDay(Date date){
        return date - 1
    }
    
    def getDateTimeComp(Date compDate, String time){
        Date result = null;
        if(!compDate || !time)
        return result
        def compTime = retriveTime(compDate)
        def list = [compTime, time]
        def startTime=getStartTime(list)
        if(startTime == compTime)
        return   getDateTimeAfter(compDate, time)      
        else
        return getDateTimeBefore(compDate, time)
    }
    
    def retriveTime(Date date){
        SimpleDateFormat  df = new SimpleDateFormat("HH:mm");
        return df.format(date)
        
    }
    
    /*def calculateIntervalWithin24h(String startTime, String endTime){
    int start = convertTimeToMi(startTime)
    // System.out.println("start time?????????????????: ${start}")
    int end = convertTimeToMi(endTime)
    int interval =0
    if(end > start){
    interval = end-start
    }else{
    interval= end - start + 24*60
    }
        
    int hour = interval/60
    int min = interval - (hour*60)
        
    def result = []
        
    result<<""+ hour +" hour(s), " + min +" minute(s)"
    def millisecond = (hour*3600 + min*60)*1000
    result << millisecond
    // return ""+ hour +" hour(s), " + min +" minute(s)"
       
    return result
          
    }*/
    
    
    /* def calculateInterval(Date date, String endTime){
    if(!date || !endTime){
    def result = ['','']
    return result
    }
         
    String startTime = retriveTime(date)
    return calculateIntervalWithin24h(startTime, endTime)
    }*/
    
    def calculateInterval(Date death, Date collectionStart, String time){
        
        if(!death || !collectionStart ||!time){
            def result = ['','']
            return result
        }

        Date date =getDateTimeAfter(collectionStart, time)
        return calculateInterval(death, date)
    }
    
    def convertTimeToMi(String time){
        String hour = time.substring(0,2)
        String min = time.substring(3)
        if(hour.startsWith('0')){
            hour = hour.substring(1)
        }
        
        if(min.startsWith('0')){
            min = min.substring(1)
        }
        
        int hourI = Integer.parseInt(hour)
        int minI = Integer.parseInt(min)
       
        
        return hourI *60 + minI
    }
    
  
    
    
    def verifyFristTissueRemovedTime(Date deathDate, Date collectionStartTime, List aquaList){
        SpecimenRecord firstTissue = null
        for(s in aquaList){
            def aliquotTimeRemovedInterval =calculateInterval(deathDate,collectionStartTime,s.aliquotTimeRemoved)[0]
            def aliquotTimeRemovedIntervalM =calculateInterval(deathDate,collectionStartTime,s.aliquotTimeRemoved)[1]
            //println("in verifyFristTissueRemovedTime aliquotTimeRemovedIntervalM: ${aliquotTimeRemovedIntervalM}")
            if(aliquotTimeRemovedIntervalM && !firstTissue){
                firstTissue = s
            }else if(aliquotTimeRemovedIntervalM && firstTissue){
                if(aliquotTimeRemovedIntervalM < calculateInterval(deathDate,collectionStartTime,firstTissue.aliquotTimeRemoved)[1]){
                    firstTissue = s
                }
            }else{
                 
            }
            
        }
        
        if(!firstTissue){
            return "not avaliable"
        }else{
            def aliquotTimeRemovedInterval =calculateInterval(deathDate,collectionStartTime,firstTissue.aliquotTimeRemoved)[0]
            def aliquotTimeRemovedIntervalM =calculateInterval(deathDate,collectionStartTime,firstTissue.aliquotTimeRemoved)[1]
             
            if(aliquotTimeRemovedIntervalM > 4*3600*1000){
                return aliquotTimeRemovedInterval
            }else{
                return ""
            }
               
            
        }
        
        
    }
    
    def getStartTime(list, type){
        def result=[]
        if(type =='bloodTimeDraw'){
            for(s in list){
                if(s.bloodTimeDraw)
                result.add(s.bloodTimeDraw)
            }
        }else if(type=='aliquotTimeRemoved'){
            for(s in list){
                if(s.aliquotTimeRemoved){
                    result.add(s.aliquotTimeRemoved)
                }
            }
        }else{
            
        }
        
        return getStartTime(result)
            
   
    }
    //need change anyway
    def getStartTime(list){
        if(!list)
        return ''
          
        if(list.size() ==1)
        return result.get(0)
        
        def earliest = ''
        for(t in list){
            if(!earliest){
                earliest = t
            }else{
                if(convertTimeToMi(earliest) <= convertTimeToMi(t)){
                    def duration1 =Math.abs(convertTimeToMi(t)-convertTimeToMi(earliest))
                    def duration2 = Math.abs(convertTimeToMi(earliest)+24*60 -convertTimeToMi(t))
                    if(duration2 < duration1){
                        earliest = t
                    }
                        
                    
                }else{
                    def duration1 =Math.abs(convertTimeToMi(earliest) -convertTimeToMi(t))
                    def duration2 = Math.abs(convertTimeToMi(t) + 24*60 -convertTimeToMi(earliest) )
                    if(duration2 > duration1){
                        earliest = t
                    }
                }
            }
        }
        
        return earliest
    }
   
    //returns LDACC public donor ID by case ID
    def getPublicDonorId(caseId){
        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"

        def xmlDocument = builder.bind {
            mkp.xmlDeclaration()
            if(caseId) {
                def d = Donor.findByCaseRecord(CaseRecord.findByCaseId(caseId.toUpperCase()))
                if(d) {
                    ldaccDonor{
                        publicDonorId(d.publicId)
                        privateDonorId(d.caseRecord.caseId)
                    }
                }   
                else{
                    ldaccDonor {
                        error(message: 'Donor not found. ')
                    }                    
                }

            } else {
                ldaccDonor {
                    error(message: 'Case ID not provided.')
                }
            }
        }

        return xmlDocument.toString()        
        
    }
  
    def donorInstantiation(payload) {
        /*   String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        def stockMap =[:]
        def errors
        def donorList = payload.Donors.children()
        donorList.each(){
        def donorId = it.ID.text()
  
        def donorPrivateId = it.PrivateID.text()
        if(!donorPrivateId)
        throw new RuntimeException("donor priave id can not be null")
                      
        CaseRecord caseRecord = CaseRecord.findByCaseId(donorPrivateId.trim().toUpperCase().replace(' ', '-'))
        if(!caseRecord){
        throw new RuntimeException("no such case id in database: $donorPrivateId")
        }
        Donor donor = Donor.findByCaseRecord(caseRecord)
        if(!donor){
        donor = new Donor(caseRecord:caseRecord)
        }
        donor.publicId=donorId
        if(!donor.save()){
        errors=donor.errors
        throw new RuntimeException(errors.toString())
        }
        def ss  = it.Specimens
               
        def specimenList = it.Specimens.children()
        specimenList.each(){
        def specimenId = it.ID.text()
        def specimenPrivateID = it.PrivateID.text()
        if(!specimenPrivateID){
        throw new RuntimeException("specimen priave id can not be null")
        }
        def materialType = it.MaterialType.text()
        def receiptDate = it.ReceiptDate.text()
        Date rdate = null
        if(receiptDate){
        rdate = format.parse(receiptDate)
        }
        def tissueSite = it.TissueSite.text()
        def massRemainingUnits = it.MassRemaining.@units.text()
        def massRemaining = it.MassRemaining.text()
                    
        SpecimenRecord specimenRecord = SpecimenRecord.findBySpecimenId(specimenPrivateID.trim().toUpperCase().replace(' ', '-'))
        if(!specimenRecord){
        throw new RuntimeException("no such specimen id in database: $specimenPrivateID")
        }    
                
        Specimen specimen = Specimen.findBySpecimenRecord(specimenRecord)
        if(!specimen){
        specimen = new Specimen(specimenRecord:specimenRecord);
        }
        specimen.donor=donor  
        specimen.publicId=specimenId
        specimen.materialType=materialType
        specimen.receiptDate=rdate
        specimen.tissueSite=tissueSite
        specimen.massRemainingUnits=massRemainingUnits
        specimen.massRemaining=massRemaining
                
                          
        if(!specimen.save()){
        errors = specimen.errors
        throw new RuntimeException(errors.toString())
        }
                    
        def extractionList = it.Extractions.children()
        extractionList.each(){
        def extractionDateRun = it.DateRun.text()
        Date edate = null;
        if(extractionDateRun){
        edate=format.parse(extractionDateRun)
        }
        def inputAmountUnits = it.InputAmount.@units.text()
        def inputAmount = it.InputAmount.text()
        def extractionProtocolName = it.Protocol.Name.text()
        def extractionProtocolVersion = it.Protocol.Version.text()
                  
        Extraction extraction=null;
        def stockList = it.ExtractedStocks.children()
                        
        //extraction do not have id, try to find it from stock barcode
        stockList.each(){
        def stockBarcode = it.Barcode.text()
        extraction = Extraction.find("from Extraction e where exists(from Stock s where  s.extraction = e and s.barcode=?)", [stockBarcode])
                                                         
        }
                        
        if(!extraction){
        extraction = new Extraction()
        }
                        
        extraction.specimen=specimen
        extraction.dateRun=edate
        extraction.inputAmountUnits=inputAmountUnits
        extraction.inputAmount=inputAmount
        extraction.protocolName=extractionProtocolName
        extraction.protocolVersion=extractionProtocolVersion
                        
        if(!extraction.save()){
        errors = extraction.errors
        throw new RuntimeException(errors.toString())
        }
                       
        stockList.each(){
        def stockBarcode = it.Barcode.text()
        def stockMaterialType = it.MaterialType.text()
        def stockVolumeUnits = it.Volume.@units.text()
        def stockVolume = it.Volume.text()
        def stockConcentrationUnits = it.Concentration.@units.text()
        def stockConcentration = it.Concentration.text()
        def yield = it.yield.text()
                            
                            
        Stock stock = Stock.findByBarcode(stockBarcode)
        if(!stock){
        stock = new Stock(barcode:stockBarcode)
        }
        stock.extraction=extraction
        stock.materialType=stockMaterialType
        stock.volumeUnits=stockVolumeUnits
        stock.volume=stockVolume
        stock.concentrationUnits=stockConcentrationUnits
        stock.concentration=stockConcentration
        stock.yield=yield
                            
        if(!stock.save()){
        errors = stock.errors
        throw new RuntimeException(errors.toString())
        }
        stockMap.put(stockBarcode, stock)
        }
        }//extractionList
        def qcList = it.QCResultSummaries.children()
        qcList.each(){
        def qcDateRun = it.DateRun.text()
        Date qd = null
        if(qcDateRun){
        qd = format.parse(qcDateRun)
        }
        def qcBarcode = it.Barcode.text()
        def qcStockBarcode = it.StockBarcode.text()
        def qcProtocolName = it.Protocol.Name.text()
        def qcProtocolVersion = it.Protocol.Version.text()
                         
        Stock st = stockMap.get(qcStockBarcode)
                         
        QC qc = QC.findByBarcode(qcBarcode)
        if(!qc){
        qc = new QC(barcode:qcBarcode)
        }
        qc.stock=st
        qc.protocolName=qcProtocolName
        qc.protocolVersion=qcProtocolVersion
        qc.dateRun=qd
                         
        if(!qc.save()){
        errors = qc.errors
        throw new RuntimeException(errors.toString())
        }
                          
        def qcResultList = it.Results.children()
        qcResultList.each(){
        def qcResultAttr = it.@attribute.text()
        def qcValue = it.text()
                            
                     
        QCResult qcResult = QCResult.findByQcAndAttribute(qc,qcResultAttr)
        if(!qcResult){
        qcResult = new QCResult(qc:qc, attribute:qcResultAttr)
        }
        qcResult.value=qcValue
                            
        //QCResult qcResult = new QCResult(qc:qc, attribute:qcResultAttr, value:qcValue)
        if(!qcResult.save()){
        errors = qcResult.errors
        throw new RuntimeException(errors.toString())
        }
                            
        }//qcResultList
                        
        }//qcList
        def expressionList = it.ExpressionResultSummaries.children()
        expressionList.each(){
        def expressionDateRun = it.DateRun.text()
        Date exd = null
        if(expressionDateRun){
        exd = format.parse(expressionDateRun)
        }
        def expressionBarcode = it.Barcode.text()
        def expressionStockBarcode = it.StockBarcode.text()
        def expressionResultList = it.Results.children()
        Stock st = stockMap.get(expressionStockBarcode)
        // Expression expression = new Expression(specimen:specimen, stock:st, dateRun:exd, barcode:expressionBarcode)   
        Expression expression = Expression.findByBarcode(expressionBarcode)
        if(!expression){
        expression = new Expression(barcode:expressionBarcode)
        }
        expression.stock = st
        expression.dateRun=exd
        if(!expression.save()){
        errors = expression.errors
        throw new RuntimeException(errors.toString())
        }
        expressionResultList.each(){
        def expressionResultAttr = it.@attribute.text()
        def expressionResultVal = it.text()
                           
        ExpressionResult expressionResult =  ExpressionResult.findByExpressionAndAttribute(expression,expressionResultAttr)
                            
        if(!expressionResult){
        expressionResult = new ExpressionResult(expression:expression, attribute:expressionResultAttr)
        }
        expressionResult.value=expressionResultVal 
        if(!expressionResult.save()){
        errors = expressionResult.errors
        throw new RuntimeException(errors.toString())
        }                                    
                        
        }//expressionResultList
        }//expressionList
        def cDNASeqList = it.cDNASeqResultSummaries.children()
        cDNASeqList.each(){
        def cDNASeqRunDate = it.DateRun.text()
        Date cd = null
        if(cDNASeqRunDate){
        cd = format.parse(cDNASeqRunDate)
        }
        def cDNASeqBarcode = it.Barcode.text()
        def cDNASeqStockBarcode = it.StockBarcode.text()
        def cDNASeqProtocolName = it.Protocol.Name.text()
        def cDNASeqProtocolVersion = it.Protocol.Version.text()
        def cDNASeqInputAmountUnits = it.InputAmount.@units.text()
        def cDNASeqInputAmount = it.InputAmount.text()
                        
        Stock st = stockMap.get(cDNASeqStockBarcode)
                      
        CDNASeq cDNASeq = CDNASeq.findByBarcode(cDNASeqBarcode)
        if(!cDNASeq){
        cDNASeq = new CDNASeq(barcode:cDNASeqBarcode)
        }
        cDNASeq.stock=st
        cDNASeq.dateRun=cd
        cDNASeq.protocolName=cDNASeqProtocolName
        cDNASeq.protocolVersion=cDNASeqProtocolVersion
        cDNASeq.inputAmountUnits=cDNASeqInputAmountUnits
        cDNASeq.inputAmount=cDNASeqInputAmount
                        
        if(!cDNASeq.save()){
        errors = cDNASeq.errors
        throw new RuntimeException(errors.toString())
        }
        def cDNASeqResultList = it.Results.children()
        cDNASeqResultList.each(){
        def cDNASeqResultAttr = it.@attribute.text()
        def cDNASeqResultVal = it.text()
                          
        CDNASeqResult cDNASeqResult =  CDNASeqResult.findByCDNASeqAndAttribute(cDNASeq,cDNASeqResultAttr)
                            
        if(!cDNASeqResult){
        cDNASeqResult = new CDNASeqResult(cDNASeq:cDNASeq, attribute:cDNASeqResultAttr)
        }
        cDNASeqResult.value= cDNASeqResultVal
        if(!cDNASeqResult.save()){
        errors = cDNASeqResult.errors
        throw new RuntimeException(errors.toString())
        }                        
                            
        }//cDNASeqResultList
                        
                        
        }//cDNASeqList
        }//specimenList
        }//donorList */
       
    }//end of method

    
}

