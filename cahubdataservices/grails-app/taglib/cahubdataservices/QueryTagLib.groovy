package cahubdataservices

import nci.obbr.cahub.util.querytracker.Query

class QueryTagLib {
    def queryDesc = {attrs, body ->
        def message = ""
        def queryInstanceList = Query.createCriteria().list {
            or {
                eq("caseRecord", attrs.caserecord)
                eq("candidateRecord", attrs.candidaterecord)
                eq("interviewRecord", attrs.interviewrecord)
            }
            eq(attrs.form, true)
        }
        
        for (queryInstance in queryInstanceList) {
            if (queryInstance.queryStatus?.code == 'ACTIVE') {
                if (queryInstance.description?.size() <= 100) {
                    message = "Query " + queryInstance.id + ": " + queryInstance.description
                    out.println "<div id=\"queryWarning_${queryInstance.id}\" class=\"warning\"><span class=\"cdr-icon\"></span>${message}</div>"
                } else {
                    message = "Query " + queryInstance.id + ": " + queryInstance.description?.substring(0, 100) + "&nbsp;&hellip;&nbsp;"
                    def more = "<span id=\"query-more_${queryInstance.id}\" class=\"query-more\">more</span>"
                    out.println "<div id=\"queryDesc_${queryInstance.id}\" class=\"hide\">" + queryInstance.description?.replace('\"', '\'').replace("\n", "<br />") + "</div>"
                    out.println "<div id=\"queryWarning_${queryInstance.id}\" class=\"warning\"><span class=\"cdr-icon\"></span>${message}${more}</div>"
                }
            }
        }
    }
}
