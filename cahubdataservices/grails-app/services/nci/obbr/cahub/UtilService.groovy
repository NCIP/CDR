package nci.obbr.cahub

import groovy.xml.XmlUtil
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException

import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.XML
import static groovyx.net.http.ContentType.TEXT

import nci.obbr.cahub.util.AppSetting
import org.codehaus.groovy.grails.web.util.WebUtils


class UtilService {

    static transactional = false

    def grailsApplication
    def springSecurityService

    def getServerUrl() {
        def serverUrl
        def request = WebUtils?.retrieveGrailsWebRequest()?.currentRequest

        if(request) {
            String port = request.serverPort?":${request.serverPort}":""
            serverUrl = "${request.scheme}://${request.serverName}${port}"
        }

        if(!serverUrl) {
            serverUrl = "http://localhost:8080"
        }

        return serverUrl
    }

    def getApplicationUrl() {
        def appUrl
        def request = WebUtils?.retrieveGrailsWebRequest()?.currentRequest

        if(request) {
            appUrl = getServerUrl(request)+"${request.contextPath}"
        }

        if(!appUrl) {
            appUrl = "http://localhost:8080/${grailsApplication.metadata.'app.name'}"
        }

        return appUrl
    }

    def getContextPath() {
        def contextPath = WebUtils?.retrieveGrailsWebRequest()?.currentRequest?.contextPath
        if(!contextPath) {
            contextPath = "/${grailsApplication.metadata.'app.name'}"
        }

        return contextPath
    }

    def getSession() {
        return WebUtils?.retrieveGrailsWebRequest()?.session
    }

    def getCurrentUsername() {
        return WebUtils?.retrieveGrailsWebRequest()?.session.SPRING_SECURITY_CONTEXT?.authentication?.principal?.getUsername()
    }

    def getShippingEventTrackingUrls() {
        def trackingUrls = [:]
        AppSetting.findAllByCodeLike('CDR_SHIPPINGEVENTS_TRACKING_URL_%').each{trackingUrls[it.name] = it.value}
        return trackingUrls
    }

    def invokeWebService(url, path, query, authUsername = "${AppSetting.findByCode('CDR_SERVICE_NAME')?.value}", authPassword = "${AppSetting.findByCode('CDR_SERVICE_PASSWORD')?.value}") {
        if(!url) {
            url = getServerUrl()
        }

        def service = new HTTPBuilder(url)
        path = "${getContextPath()}$path"

        def cdf
        int n = 0
        Exception exception
        def dataS
        while(true)
        {
            n++
            //println 'n='+ n + ', password=' + authPassword
            if (n > 2) break
            if (n == 2)
            {
                if (authPassword.toString().equals('Welcome01'))
                {
                    authPassword = 'W3lcome0!'
                }
                else if (authPassword.toString().equals('W3lcome0!'))
                {
                    authPassword = 'Welcome01'
                }
                else
                {
                    break
                }
            }
            try {
                
                service.auth.basic "$authUsername", "$authPassword"
                service.request(GET, TEXT) {
                    
                    uri.path = path
                    uri.query = query
                    
                    response.success = { resp, data ->
                        dataS = data.getText().trim()
                        
                        int idx = dataS.toLowerCase().indexOf('<html ')
                        if (idx > 0)
                        {
                            cdf = null
                            exception = new Exception('An XML document is not generated but HTML. It may be something wrong with password or user ID.')
                        }
                        else
                        {
                            cdf = XmlUtil.serialize(dataS)
                            exception = null
                        }
                    }
                }
            } catch(Exception hre) {
                println 'f' + n + ', message=' + hre.getMessage()
                exception = hre
                continue
            }
            
            if (cdf) break
        }
        if (!cdf)
        {
            if (exception)
            {
                String message = hre.getMessage()
                if (!message) message = 'NullPointerError'
                int idx = message.toLowerCase().indexOf('unauthorized')
                println 'Exception message1=' + message + ', XML Data='+dataS
                if(idx >= 0) {
                    log.error("Error requesting the caserecord service:")
                    log.error("Incorrect username/password value.\n")
                }
                else
                {
                    log.error("Error requesting the caserecord service:")
                    log.error(message + "\n")
                }
                hre.printStackTrace()
                throw exception
            }
            else 
            {
                String message = 'Null Output XmlUtil.serialize()'
                println 'Exception message2=' + message + ', XML Data='+dataS
                log.error(message)
                log.error("XML Data="+dataS+"\n")
                throw new Exception(message)
            }
        }
        
        return cdf
    }
    
    
    def compareCDRVersion(String v1, String v2) throws Exception{
        if(!v1 || ! v2)
           throw new Exception("Wrong CDR version format")
        int result=100
        def a1 = v1.split("\\.")
        def a2 = v2.split("\\.")
       
        
        def len1 = a1.size()
        def len2 = a2.size()
        
        def len
        if(len1 <= len2)
          len = len1
        else
          len = len2
       
        
       try{
        for(int i = 0; i < len; i++){
           int i1 = Integer.parseInt(a1[i])
           int i2 = Integer.parseInt(a2[i])
           if(i1 > i2){
             result = 1
           }else if (i1 == i2){
             result = 0
           }else{
              result = -1
           }  
           if(result == 1 || result == -1)
           break
        }
       }catch(Exception e){
            throw new Exception("Wrong CDR version format")
       } 
       
       if(result == 0){
           if(len1 < len2)
             result = -1
           else if(len1 > len2)
             result = 1
           else
             result = 0
       }
       
       return result
        
    }
}
