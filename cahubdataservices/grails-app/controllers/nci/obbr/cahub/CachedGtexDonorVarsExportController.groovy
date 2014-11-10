package nci.obbr.cahub

import groovy.xml.MarkupBuilder
import javax.xml.parsers.DocumentBuilderFactory

import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.staticmembers.CaseStatus
import nci.obbr.cahub.util.CachedGtexDonorVarsExport


class CachedGtexDonorVarsExportController {

    def gtexDonorVarsExportService
    def cachedGtexDonorVarsExportService

    def getGTEXDonorVars = {
        def document
        def caseId = params.caseId
        def version = params.version

        if(caseId) {
            def caseRecordInstance = CaseRecord.findByCaseId(caseId)
            if(caseRecordInstance && ("GTEX".equalsIgnoreCase(caseRecordInstance?.study?.code) || "BMS".equalsIgnoreCase(caseRecordInstance?.study?.code))) {
                if(caseRecordInstance?.caseStatus?.code == "RELE") {
                    def cachedCases = CachedGtexDonorVarsExport.findAllByCaseId(caseId, [sort:"cachedVersion", order:"desc"])
                    if(!cachedCases) {
                        // cache RELEASED case -- for the first time, if it's not already stored in the database
                        cachedGtexDonorVarsExportService.cacheReleasedCase(caseRecordInstance, null, null)
                        cachedCases = CachedGtexDonorVarsExport.findAllByCaseId(caseId, [sort:"cachedVersion", order:"desc"])
                    }

                    if(version == "*") {
                        def xml
                        def writer
                        def root = new XmlSlurper().parseText("<caseSummary id='$caseId'></caseSummary>")
                        for(cachedCase in cachedCases) {
                            writer = new StringWriter()
                            xml = new MarkupBuilder(writer)

                            xml.version(reason:cachedCase.reason, appVersion:cachedCase.appVersion, releaseDate:cachedCase.releaseDate, number:cachedCase.cachedVersion)
                            root.appendNode(new XmlSlurper().parseText(writer.toString()))
                        }

                        document = groovy.xml.XmlUtil.serialize(root)
                    } else if(version) {
                       def cachedCase = cachedCases.find {version == it.cachedVersion.toString()}
                        if(cachedCase) {
                            document = generateXMLDocument(cachedCase)
                        } else {
                            document = "<GTEx_de_identified_donor_variables><case id='$caseId' collection_type='${caseRecordInstance?.caseCollectionType}'><case_status>Case copy version [$version] not available</case_status></case></GTEx_de_identified_donor_variables>\n"
                        }
                    } else {
                        document = generateXMLDocument(cachedCases[0])
                    }
                } else {
                    document = "<GTEx_de_identified_donor_variables><case id='$caseId' collection_type='${caseRecordInstance?.caseCollectionType}'><case_status>Case not released</case_status></case></GTEx_de_identified_donor_variables>\n"
                }
            } else {
                document = "<GTEx_de_identified_donor_variables><message code='1'>Invalid caseId</message></GTEx_de_identified_donor_variables>\n"
            }
        } else {
            document = "<GTEx_de_identified_donor_variables><message code='1'>Null caseId</message></GTEx_de_identified_donor_variables>\n"
        }

        render contentType: "text/xml", encoding: "UTF-8", text: document
    }

    def generateXMLDocument(cachedCase) {
        def document
        if(cachedCase) {
            def root = new XmlSlurper().parseText(cachedCase.xmlDocument)
            def writer = new StringWriter()
            def xml = new MarkupBuilder(writer)

            xml.version(reason:cachedCase.reason, appVersion:cachedCase.appVersion, releaseDate:cachedCase.releaseDate, number:cachedCase.cachedVersion)
            root.appendNode(new XmlSlurper().parseText(writer.toString()))
            document = groovy.xml.XmlUtil.serialize(root)
        }

        return document
    }
}
