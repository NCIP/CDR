class UrlMappings {
    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        //"500"(view:"/error")
        "500"(controller: 'errors', action: 'error500')
        "403"(controller: 'errors', action: 'error500')
        "401"(controller: 'errors', action: 'error500')
        "404"(controller: 'errors', action: 'error404')

        //Landing page URLs
        "/"(controller:"home",action:"homedispatcher")
        "/home"(controller:"home",action:"homedispatcher")

        //REST URLs
        "/rest/caserecord/$caseid?"(controller:"rest",action:"caseRecordRestActions",parseRequest:true)
        "/rest/specimenrecord/$specimenid?"(controller:"rest",action:"specimenRestActions",parseRequest:true)
        "/rest/sliderecord/$slideid?"(controller:"rest",action:"slideAndImageRestActions",parseRequest:true)
        "/rest/shippingevent/"(controller:"rest",action:"shippingEventRestActions")
        "/rest/collectionevent/"(controller:"rest",action:"collectionEventRestActions")
        "/rest/chpevent/"(controller:"rest",action:"chpEventRestActions")
        "/rest/processingevent/"(controller:"rest",action:"processingEventRestActions")
        "/rest/getcbrapievent/$id?/$cdrid?"(controller:"rest",action:"cbrApiEventRestActions")
        "/rest/postcasetobrims/$caseid?"(controller:"rest",action:"postCaseRecordToBrims")
        "/rest/getldaccdonorid/$caseid?"(controller:"rest",action:"getLDACCPublicDonorId")

        //Controlled vocabulary URLs
        "/rest/bss/$code?"(controller:"rest",action:"getValidBSS",parseRequest:true)
        "/rest/acquisitiontype/$code?"(controller:"rest",action:"getValidAcquisitionType",parseRequest:true)
        "/rest/acquisitionlocation/$code?"(controller:"rest",action:"getValidAcquisitionLocation",parseRequest:true)
        "/rest/fixative/$code?"(controller:"rest",action:"getValidFixative",parseRequest:true)
        "/rest/containertype/$code?"(controller:"rest",action:"getValidContainerType",parseRequest:true)
        "/rest/kittype/$code?"(controller:"rest",action:"getValidKit",parseRequest:true)
        "/rest/shippingcontenttype/$code?"(controller:"rest",action:"getValidShippingContentType",parseRequest:true)
        "/rest/organization/$code?"(controller:"rest",action:"getValidOrganization",parseRequest:true)

        "/rest/gtexdonorvars/$caseId?/$version?"(controller:"cachedGtexDonorVarsExport",action:"getGTEXDonorVars",parseRequest:true)
        "/rest/gtexpartialdonorvars/$code?"(controller:"gtexDonorVarsExport",action:"gtexPartialDonorVarsExport",parseRequest:true)
        "/rest/previewgtexdonorvars/$code?"(controller:"gtexDonorVarsExport",action:"previewGTEXDonorVars",parseRequest:true)

        "/rest/prcspecimenreport/$specimenId?"(controller:"rest",action:"getPrcSpecimenReport",parseRequest:true)
        "/rest/prcsummaryreport/$caseId?"(controller:"rest",action:"getPrcSummaryReport",parseRequest:true)
        "/rest/ldaccdonorimport/"(controller:"rest",action:"importLDACCDonor")
        "/rest/gtexprcreport/$code?"(controller:"rest",action:"getPrcReport",parseRequest:true)

        "/rpc/ldaccGtexMolecularImport/"(controller:"rpc",action:"ldaccGtexMolecularImport")
        "/rpc/ldaccGtexMolecularData/$code?"(controller:"rpc",action:"ldaccGtexMolecularData")

        "/search_admin/index_all/"(controller:"textSearch",action:"index_all")
        "/search_admin/index_obj/$caseId?"(controller:"textSearch",action:"index_all")

        "/register/expiredPassword"(controller:"register",action:"expiredPassword")

        "/activitycenter"(view: "generic")  {
            title = "Activity Center"
            bodyclass = "recentactivity"
            navigation = "/cahubdataservices;home;Home"
            divs = "recentactivity"
            h1studysession = true
        }
        
        "/survey/$pagetype/$recordid"(view: "generic")  {
            title = "Survey"
            urlparams = "pagetype,recordid"
            bodyclass = "cdr_surveys"
            navigation = "/cahubdataservices;home;Home"
            divs = "survey"
            jsvalues = "/surveys/surveyUI.js"
            h1studysession = false
        }
        
        "/specimen/$specimenid"(controller:"dmzUtils",action:"getSpecimenImageID")  {
            bodyclass = "aperio"
            navigation = "/cahubdataservices;home;Home"
            jsvalues = "openseadragon-bin-1.1.1/openseadragon.js,slides.js"
            appsettingused = "APERIOIMGLOC"
            divs = "slidedata,openseadragon"
            h1studysession = false
        }      
        
        "/home/vocabhome/cvocab"(view: "generic")  {
            title = "Controlled Vocab"
            bodyclass = "vocab_status"
            navigation = "/cahubdataservices;home;Home"
            jsvalues = "ext/vocab/vocabstatus.js"
            h1studysession = false
            extjs = true
        }
        
        "/iconbuilder"(view: "generic")  {
            title = "Icon Builder"
            bodyclass = "iconbuilder"
            navigation = "/cahubdataservices;home;Home"
            divs = "iconbuilder"
            includes = "iconslist"
            jsvalues = "/iconbuilder.js"
            h1studysession = false
        }
        
        "/shippingEvent/showByCbrIdAndCase/$id?/$caseId?"(controller:"shippingEvent",action:"showByCbrIdAndCase")        
    }
}
