package nci.obbr.cahub

import nci.obbr.cahub.datarecords.CaseRecord


class GtexDonorVarsExportController {

    def gtexDonorVarsExportService


    def gtexPartialDonorVarsExport = {
        switch(request.method) {
            case "GET":
                def document = gtexDonorVarsExportService.gtexPartialDonorVarsExport(params.code)
                renderXmlDocument(document)
            break
        }
    }

    def previewGTEXDonorVars = {
        if(params.code) {
            def c = CaseRecord.findByCaseId(params.code)
            if(!c) {
                renderXmlDocument("<GTEx_de_identified_donor_variables><message code='1'>Invalid caseId</message></GTEx_de_identified_donor_variables>\n")
            } else {
                if(session.org?.code == 'OBBR' && (c.caseStatus?.code == 'RELE' || c.caseStatus?.code == 'COMP')) {
                    if(c.study.code=='GTEX') {
                        gtexDonorVarsExport()
                    } else {
                        bmsDonorVarsExport()
                    }
                } else {
                    renderXmlDocument("<GTEx_de_identified_donor_variables><case id='$c.caseId' collection_type='$c.caseCollectionType'><case_status>Not released or not complete</case_status></case></GTEx_de_identified_donor_variables>\n")
                }
            }
        } else {
            renderXmlDocument("<GTEx_de_identified_donor_variables><message code='1'>Null caseId</message></GTEx_de_identified_donor_variables>\n")
        }
    }

    def getGTEXDonorVars = {
        if(params.code) {
            def c = CaseRecord.findByCaseId(params.code.toUpperCase())
            if(!c) {
                renderXmlDocument("<GTEx_de_identified_donor_variables><message code='1'>Invalid caseId</message></GTEx_de_identified_donor_variables>\n")
            } else {
                if(c.caseStatus?.code == 'RELE') {
                    if(c.study.code == 'GTEX') {
                        gtexDonorVarsExport()
                    } else {
                        bmsDonorVarsExport()
                    }
                } else {
                    renderXmlDocument("<GTEx_de_identified_donor_variables><case id='$c.caseId' collection_type='$c.caseCollectionType'><case_status>Not released</case_status></case></GTEx_de_identified_donor_variables>\n")
                }
            }
        } else {
            renderXmlDocument("<GTEx_de_identified_donor_variables><message code='1'>Null caseId</message></GTEx_de_identified_donor_variables>\n")
        }
    }

    def gtexDonorVarsExport = {
         switch(request.method) {
            case "GET":
                def document = gtexDonorVarsExportService.gtexDonorVarsExport(params.code)
                renderXmlDocument(document)
            break
        }
    }

    def bmsDonorVarsExport = {
        switch(request.method) {
            case "GET":
                def document = gtexDonorVarsExportService.bmsDonorVarsExport(params.code)
                renderXmlDocument(document)
            break
        }
    }

    def getPrcReport = {
        switch(request.method) {
            case "GET":
                def document = gtexDonorVarsExportService.getPrcReport(params.code)
                renderXmlDocument(document)
            break
        }
    }

    def renderXmlDocument(document) {
        render contentType: "text/xml", encoding: "UTF-8", text: document
    }
}
