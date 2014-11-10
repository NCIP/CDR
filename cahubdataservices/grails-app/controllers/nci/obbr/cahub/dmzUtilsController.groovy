package nci.obbr.cahub

import grails.converters.JSON
import nci.obbr.cahub.util.AppSetting

import nci.obbr.cahub.ldacc.Specimen
import nci.obbr.cahub.datarecords.SpecimenRecord
import nci.obbr.cahub.datarecords.SlideRecord
import nci.obbr.cahub.datarecords.ImageRecord
import groovyx.net.http.*
import static groovyx.net.http.Method.HEAD

class dmzUtilsController {
    def grailsApplication

    def getRemoteLoginBulletin = {
        def bulletin = [:]
        def appSettingInstance = AppSetting.findByCode('LOGIN_BULLETIN')

        if(!appSettingInstance?.bigValue) {
            bulletin.put("loginBulletin", "bulletin app setting not found")
        } else {
            bulletin.put("loginBulletin", appSettingInstance.bigValue)
        }

        if(params.callback) {
            render "${params.callback.encodeAsURL()}([${bulletin as JSON}])"
        } else {
            render bulletin as JSON
        }
    }

    def getRemoteClientAppVersion = {
        def milestone = AppSetting.findByCode('APP_RELEASE_MILESTONE')?.bigValue
        def appVersion = ["version":"${grailsApplication.metadata.'app.version'}","milestone":"${milestone?"$milestone":""}"]
        if(params.callback) {
            render "${params.callback.encodeAsURL()}([${appVersion as JSON}])"
        } else {
            render appVersion as JSON
        }
    }

    def getRemoteClientCBRIMSInfo = {
        def cbrIMSInfo = ["cbrIMSHost":"${AppSetting.findByCode('CBR_IMS_HOST')?.value}","cbrIMSName":"${AppSetting.findByCode('CBR_IMS_NAME')?.value}"]
        if(params.callback) {
            render "${params.callback.encodeAsURL()}([${cbrIMSInfo as JSON}])"
        } else {
            render cbrIMSInfo as JSON
        }
    }
    
    def setMilestone = {
        
        def m = AppSetting.findByCode('APP_RELEASE_MILESTONE')
        m.bigValue = params.m
        m.save(failOnError: false, flush: true)
        redirect(uri:"/")
        
    }
    
    def getSpecimenImageID = {
        def webServer = AppSetting.findByCode("APERIO_WEB_SERVER").value
        def ldaccSpecimen = Specimen.findByPublicId(params.specimenid)
        def specimenRecord
        def slideId
        def preferredSlideId = "0"
        def imageId
        def caseId
        def slides
        def privateId
        def otherSpecimen
        def httpGet
        def found = false
        def slideData = [:]
        if (ldaccSpecimen) {
            privateId = ldaccSpecimen.privateId
            caseId = ldaccSpecimen.donor.privateId
            if (privateId[14..15].equals("26")) {
                /* The most likely case is "26" */  
                otherSpecimen = privateId[0..13]+"25"
            } else {
                if (privateId[14..15].equals("25")) {
                    otherSpecimen = privateId[0..13]+"26"
                } else {
                    log.error ("Bad public specimen ID!  Must end with either 26 or 25.")
                }
            }
            specimenRecord = SpecimenRecord.findBySpecimenId(otherSpecimen)
            if (specimenRecord) {
                slides = specimenRecord.slides
//              take the highest slide number associated with a specimenRecord
                slides.each() {
                    slideId = it.slideId
                    if (slideId.toInteger() > preferredSlideId.toInteger()) {
                        preferredSlideId = slideId
                    }
                }
                slideId = preferredSlideId
//                    println "slideId: " + slideId
//                    println "URL: " + webServer + 'images/'+caseId+"/"+slideId+".dzi"
                httpGet = new HTTPBuilder(webServer)
//                httpGet.ignoreSSLIssues() this should work in the rest client 0.7.1 or higher, but apparently, it does not.
                try {
                    httpGet.get (path : 'images/'+caseId+"/"+slideId+".dzi") { resp, reader ->
//                            println "response status:      ${resp.statusLine}"
//                            println "response status code: ${resp.statusLine.statusCode}"
//                            println 'Response data: -----'
//                            System.out << reader
//                            println '\n--------------------'

                        if (resp.statusLine.statusCode.equals(200)) {
                            found = true
                            slideData = image(slideData, slideId, null)
//                                render (action: image, params: params)
                            if (slideData) {
                                render(view: "../generic", model: [controllerData: slideData as JSON])
                            } else {
                                log.error ("Error! no slideData!")
                            }
                        } else {
                            log.info("response status:      ${resp.statusLine}")
                        }
                    }
                }
                catch (HttpResponseException hre) {
//                        println "Exception!"
                    slides.each() {
                        imageId = it.imageRecord.imageId
                    }
                    try {
                        httpGet.get (path : 'images/'+caseId+"/"+imageId+".dzi") { resp, reader ->
                            if (resp.statusLine.statusCode.equals(200)) {
                                slideData = image(slideData, null, imageId)
                                if (slideData) {
                                    render(view: "../generic", model: [controllerData: slideData as JSON])
                                } else {
                                    log.error ("Error! no slideData!")
                                }
                            } else {
                                log.info("response status:      ${resp.statusLine}")
                            }
                        }
                    }
                    catch (HttpResponseException her) {
                        println "Second Exception!"
                        log.error("Lookup by Slide ID AND Image ID Failed! Case ID: "+caseId+" Private specimen ID: "+params.specimenid)
                        redirect(url: webServer + "/imagedatanotavailable.html") 
                    }
                } 

            } else {
                redirect(url: webServer + "/imagedatanotavailable.html") 
            }
        } else {
            redirect(url: webServer + "/imagedatanotavailable.html") 
        }
    }
    
    def image (slideData, slideId, imageId) {
       def slideRecordInstance 
       if (slideId) {
           slideRecordInstance = SlideRecord.findBySlideId(slideId)
       }
       if (imageId) {
           slideRecordInstance = ImageRecord.findByImageId(imageId).slideRecord
       }
       if(slideRecordInstance) {
           slideData.put("caseid", slideRecordInstance.specimenRecord?.caseRecord?.caseId?:"")
           /*
           slideData.put("casenumid", slideRecordInstance.specimenRecord?.caseRecord?.id?:"")
           slideData.put("specimenid", slideRecordInstance.specimenRecord?.specimenId?:"")
           slideData.put("specimennumid", slideRecordInstance.specimenRecord?.id?:"")
           */
           slideData.put("tissuetype", slideRecordInstance.specimenRecord?.tissueType?.name?:"")
           slideData.put("tissueloc", slideRecordInstance.specimenRecord?.tissueLocation?:"")
           slideData.put("imageid", slideRecordInstance.imageRecord?.imageId?:"")
           if (slideId) {
               slideData.put("slideid", slideRecordInstance.slideId?:"")
           }
           if (imageId) {
               slideData.put("slideid", slideRecordInstance.imageRecord.imageId?:"")
           }
           return slideData
        } else {
//            response.sendError(404)
//            redirect (action: getSpecimenImageID )
           return null
        }
    }
}
