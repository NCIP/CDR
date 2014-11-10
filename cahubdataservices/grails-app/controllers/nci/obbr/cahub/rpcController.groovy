package nci.obbr.cahub

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.XML

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.Study



class rpcController {
    def rpcService
    def sendMailService
    
    def ldaccGtexMolecularImport = {
     
        def result = rpcService.importLdaccData()
     
        render("LDACC import is completed")         
    }
    
   
    def ldaccGtexMolecularData = {
         if(params.code){
                def  c = CaseRecord.findByCaseId(params.code.toUpperCase())
                if(!c){
                 render text: '<?xml version="1.0"?><prcReport><status>1</status><message><error><error message="no such case id: ' + params.code +'" /></error></message></prcReport>\n', contentType:"text/xml", encoding:"UTF-8"
                } else{
                def xml = rpcService.getRawData(params.code)
                if(!xml){
                   render text: '<?xml version="1.0"?><prcReport><status>1</status><message><error><error message="the donor data is not available" /></error></message></prcReport>\n', contentType:"text/xml", encoding:"UTF-8"
                 }else{
                     render(text:xml, contentType:"text/xml",encoding:"UTF-8")
                 }
                }
         }else{
              render text: '<?xml version="1.0"?><prcReport><status>1</status><message><error><error message="please provide case id" /></error></message></prcReport>\n', contentType:"text/xml", encoding:"UTF-8"
         }
           
    }
    
    def ldaccApiCall(cid, remoteAPI) {
        
        //Actual GET request
        remoteAPI.request(GET, XML) {
            uri.path = '/portal/GTEx/ws/portals/private/get_gtex_donor_data'

            uri.query = [ donor_name: cid ]
            println uri
            
            //if success
            response.success = { resp, xml ->

                //println resp.statusLine.statusCode
                println resp.status
                //println xml
                xml.donors.donor.specimens.specimen.each {  
                    println it.'@specimenID'.text()

                }
                return [caseId:cid,mssg:"SUCCESS",resp:resp.status]

            }
            //if failure
            response.failure = { resp ->
                return [caseId:cid,mssg:"FAIL",resp:resp.status]    
            }
        
        }
    }    
    
}
