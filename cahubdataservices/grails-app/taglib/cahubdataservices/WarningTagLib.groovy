package cahubdataservices

class WarningTagLib {
    def warnings = { attrs, body ->
        if (attrs.warningmap?.size() > 0) {
            out.println "<div class=\"warnings\"><ul>"
            attrs.warningmap.each{ key, warning -> out.println "<li class=\"warning_" + key + "\"><span class=\"cdr-icon\"></span>" + warning + "</li>"}
            out.println "</ul></div>"
        }
    }
}
