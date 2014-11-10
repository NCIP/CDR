package nci.obbr.cahub.util


class CachedGtexDonorVarsExport {

    String caseId
    String reason
    Date releaseDate
    String appVersion
    String xmlDocument
    Integer cachedVersion

    static constraints = {
        reason(nullable:true, blank:true)
        caseId(blank:false, nullable:false)
        appVersion(nullable:false, blank:false)
        xmlDocument(nullable:false, blank:false)
        releaseDate(nullable:false, blank:false)
        cachedVersion(nullable:false, blank:false)
    }

    static mapping = {
        table 'cached_gtex_donor_vars_export'
        id generator: 'sequence', params:[sequence:'cached_gtex_donor_vars_expt_pk']
        xmlDocument sqlType: 'clob'
    }
}
