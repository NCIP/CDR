package nci.obbr.cahub

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException

import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.XML
import static groovyx.net.http.ContentType.TEXT

import nci.obbr.cahub.ldacc.*
import nci.obbr.cahub.datarecords.*
import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.util.*
import java.text.SimpleDateFormat
//import org.hibernate.lob.ClobImpl
//import java.sql.Clob


class RpcService {

    static transactional = false

    def sendMailService

    def ldaccApiCall(cid, remoteAPI) {
          StringBuffer sb = new StringBuffer()
          sb.append("import " + cid + "...\n\r")
          String pattern = "MM/dd/yyyy";
          SimpleDateFormat format = new SimpleDateFormat(pattern);
        
          remoteAPI.request(GET, TEXT) {
          uri.path = AppSetting.findByCode('LDACC_PATH').value

          uri.query = [ donor_name: cid ]
                 
            //if success
          response.success = { resp, reader ->
               def caseRecord = CaseRecord.findByCaseId(cid)
               def donorFromDb = Donor.findByCaseRecord(caseRecord)
               
               //   println resp.status
                
                def rawData = reader.text
                
                //println("raw data:" + rawData)
                
                if(rawData.size() < 1000){
                    sb.append("raw data size : " + rawData.size() + ". not processed\n\r")
                    retyrb sb.toString();
                }
           
                
               if(donorFromDb){ 
                  donorFromDb.delete(failOnError:true, flush:true)
               }

      
              
                       
                def xml = new XmlSlurper().parseText(rawData)
              
                def stockQc=[:]
                def stockMap=[:]
                
                def caseId = xml.donors.donor.@privateDonorID.text()
       
                def donorId = xml.donors.donor.@donorID
              
              
               Donor.withTransaction { 
                
                def donor = new Donor()
                donor.caseRecord = caseRecord
                donor.publicId = donorId
                //
                donor.rawData = rawData
                donor.privateId = cid
                donor.lsid = xml.donors.donor.lsid.text()
                donor.save(failOnError:true)
                
              
                
                xml.donors.donor.specimens.specimen.each { 
                    def specimenId = it.@privateSpecimenID.text()
                    if(specimenId.indexOf(' ') > -1){
                       // println("specimen id with space: " + specimenId)
                        sb.append("specimen id with space: " + specimenId + "\n\r")
                        specimenId = specimenId.replaceAll(' ', '-')
                    }
                  //  println("specimenId: " + specimenId)
                    def specimenRecord = SpecimenRecord.findBySpecimenId(specimenId)
                    
                    if(!specimenRecord){
                        if(specimenId.startsWith(caseId + "-0011")){
                            specimenRecord=SpecimenRecord.findBySpecimenId(caseId + "-0011")
                            //if(specimenRecord){
                             //   println("found...")
                           // }
                        }
                    }
                    
                    if(!specimenRecord){
                      // println("case id: " + caseId + " specimenRecord " + specimenId + " not found in CDR !!!!  public id is: " + it.@specimenID.text() )
                       sb.append("specimenRecord " + specimenId + "  not found in CDR !!!!  public id is: " + it.@specimenID.text()+"\n\r")
                       
                    }else{
                    def specimen = new Specimen()
                    specimen.specimenRecord = specimenRecord
                    // println("aftrer set specimenRecord...")
                    specimen.donor = donor
                    specimen.lsid =it.lsid.text()
                    specimen.publicId = it.@specimenID.text()
                    specimen.privateId = specimenId
                   
                    specimen.materialType = it.materialType.text()
                    if(it.receiptDate.text()){
                    specimen.receiptDate = format.parse(it.receiptDate.text())
                    }
                    specimen.receiptNote = it.receiptNote.text()
                    specimen.tissueSite = it.tissueSite.text()
                    specimen.tissueSiteDetail=it.tissueSiteDetail.text()
                    specimen.save(failOnError:true)
                   // println("after save specimen....")
                    it.qcResultSummaries.qcResultSummary.each{
                        def qc = new Qc()
                        qc.specimen = specimen
                        //qc.barcode = it.barcode.text()
                        qc.lsid = it.lsid.text()
                        qc.protocolName = it.protocol.protocolName.text()
                        if(it.dateRun.text())
                             qc.dateRun= format.parse(it.dateRun.text())
                        qc.save(failOnError:true)
                        
                        def list = stockQc.get(it.lsid.text())
                        
                        if(!list){
                            list = []
                            stockQc.put(it.lsid.text(), list)
                        }
                         list.add(qc)
                       
                        
                        it.results.result.each{
                            def qcResult = new QcResult()
                            qcResult.qc = qc
                            qcResult.attribute = it.@attribute.text()
                            qcResult.value = it.text()
                            if(it.text()){
                                qcResult.save(failOnError:true)
                            }
                        }
                        
                    }//qc loop
                    
                    it.extractions.extraction.each{
                        def extraction = new Extraction()
                        if(it.dateRun.text())
                           extraction.dateRun = format.parse(it.dateRun.text())
                        extraction.protocolName = it.protocol.protocolName
                        extraction.specimen = specimen
                        extraction.save(failOnError:true)
                        it.stocks.stock.each{
                            def stock = new Stock()
                            stock.extraction = extraction
                            stock.lsid = it.lsid.text()
                           
                            stock.materialType = it.materialType
                            stock.volumeUnits = it.volume.@units.text()
                            if(it.volume.text())
                                stock.volume = Float.parseFloat(it.volume.text())
                            stock.concentrationUnits = it.concentration.@units.text()
                            
                            if(it.concentration.text())
                                stock.concentration = Float.parseFloat(it.concentration.text())
                            stock.yield = it.yield
                            
                            if(it.storageTemperature.text()){
                                // println("temprature: " +it.storageTemperature.text() )
                                 stock.storageTemperature =Float.parseFloat(it.storageTemperature.text())
                                 stock.storageTemperatureUnit =it.storageTemperature.@units.text()
                            }
                            stock.save(failOnError:true)
                            stockMap.put(it.lsid.text(), stock)
                         /**   def list = stockQc.get(it.lsid.text())
                            list.each{
                                it.stock = stock
                                it.save(failOnError:true)
                            }**/
                          
                        }//stock loop
                        
                    }//extraction loop
                    
                    it.expressionResultSummaries.expressionResultSummary.each{
                        
                        def expression = new Expression()
                        expression.stockLsid = it.stockLsid.text()
                        expression.lsid = it.lsid.text()
                        expression.chipTypeName = it.chipTypeName.text()
                        
                        expression.specimen = specimen
                        
                        def stock = stockMap.get(it.stockLsid.text())
                        expression.stock = stock
                                
                        expression.save(failOnError:true)
                        
                         it.results.result.each{
                            def expressionResult = new ExpressionResult()
                            expressionResult.expression = expression
                            expressionResult.attribute = it.@attribute.text()
                            expressionResult.value = it.text()
                            expressionResult.save(failOnError:true)
                        }        
                    }
                        
                        
                        
                        
                    }//else

                }//specimen loop 
                }
                return sb.toString()

            }
            //if failure2
            response.failure = { resp ->
                sb.append("failed, response status: " + resp.status + '\n\r')
                return sb.toString()
            }
        
        }
        

    }
    
    
    def getRawData(caseId) {
        if(!caseId)
           return ''
        def caseRecord = CaseRecord.findByCaseId(caseId.toUpperCase())
        if(!caseRecord)
          return ''
        def donor = Donor.findByCaseRecord(caseRecord)
        if(!donor){
           return ''
        }
        def rawData = donor.rawData
       // int clobLength = (int) rawData.length();
       // def str = rawData.getSubString(1, clobLength);
        
       // return str
       
        return rawData
        
    }
    

    
    def importLdaccData(){
        def caseList = CaseRecord.findAllByStudy(Study.findByCode('GTEX'))
        //def caseList = []  //for testing
       // def gtexCase = CaseRecord.findByCaseId('GTEX-000007')
       // caseList.add(gtexCase)
        
        StringBuffer sb = new StringBuffer()
        
        def ldaccHost = AppSetting.findByCode('LDACC_HOST').value
        
        //set up and authenticate with API
        def remoteAPI = new HTTPBuilder(ldaccHost)
        //remoteAPI.auth.basic 'charles.shive@nih.gov', 'Welcome01' 
        
        remoteAPI.auth.basic "${AppSetting.findByCode('LDACC_USERNAME').value}", "${AppSetting.findByCode('LDACC_PASSWORD').value}"
    
        
        //loop through the list of cases
        caseList.each{
          def cid = it.caseId
          try{
            //Broad hasn't scrubbed and replaced spaces with dashes yet
            
            //println("cid: " + cid)
            //def cid = it.tr('-', ' ') //for testing
            
            //Call method to make actual GET request, get result message, add to array
            def result = ldaccApiCall(cid, remoteAPI)
            sb.append(result)
          }catch(Exception e){
              sb.append("process " + cid + " failed.\n\r" )
              sb.append(e.toString() + "\n\r")
              
          }
        }
     
       sendMailService.sendLDACCImportEmail(sb.toString())
    }
}
