package nci.obbr.cahub

import nci.obbr.cahub.staticmembers.CaseStatus
import nci.obbr.cahub.util.CachedGtexDonorVarsExport

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware


class CachedGtexDonorVarsExportService implements ApplicationContextAware {

    static transactional = true
    ApplicationContext applicationContext

    def grailsApplication
    // bean load created to avoid circular service dependency injection error with
    // gtexDonorVarsExportService reference to prcReportService and prcReportService reference to this service
    def gtexDonorVarsExportServiceBean

    boolean cacheReleasedCase(caseRecordInstance, releaseDate, reason) {
        def caseId = caseRecordInstance?.caseId
        def caseStatus = caseRecordInstance?.caseStatus?.code
        def caseStudy = caseRecordInstance?.study?.code

        boolean stored = false
        gtexDonorVarsExportServiceBean = applicationContext.getBean("gtexDonorVarsExportService")

        if("RELE".equalsIgnoreCase(caseStatus)) {
            def xmlDocument
            if("GTEX".equalsIgnoreCase(caseStudy)) {
                xmlDocument = gtexDonorVarsExportServiceBean.gtexDonorVarsExport(caseId)
            } else if("BMS".equalsIgnoreCase(caseStudy)) {
                xmlDocument = gtexDonorVarsExportServiceBean.bmsDonorVarsExport(caseId)
            }

            if(xmlDocument) {
                def cachedVersion = CachedGtexDonorVarsExport?.createCriteria().get {
                    eq("caseId", caseId)
                    projections {max "cachedVersion"}
                }

                def cachedExport = new CachedGtexDonorVarsExport()
                cachedExport.caseId = caseId
                cachedExport.reason = reason ?: "Case status changed to ${CaseStatus.get(caseRecordInstance?.caseStatus?.id)?.name}"
                cachedExport.releaseDate = releaseDate ?: caseRecordInstance?.lastUpdated
                cachedExport.xmlDocument = xmlDocument
                cachedExport.cachedVersion = cachedVersion ? ++cachedVersion : 1
                cachedExport.appVersion = grailsApplication.metadata.'app.version'
                cachedExport.save(failOnError:true, flush:true)

                stored = true
            }
        }

        return stored
    }
}
