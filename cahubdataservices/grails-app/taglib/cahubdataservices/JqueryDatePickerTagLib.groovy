package cahubdataservices


class JqueryDatePickerTagLib {

    def jqDatePicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name
        if ( attrs.LDSOverlay?.find("create").equals("create") || attrs.LDSOverlay?.find("edit").equals("edit") || (!attrs.LDSOverlay.equals("false") && session.LDS == true) || (attrs.LDSOverlay.equals("false") || attrs.LDSOverlay == null) ) {
            def value = attrs.value
            def dateStrArr = []
            def thisDay = ""
            def thisMonth = ""
            def thisYear = ""
            if (value != null) {
                value = value.format("MM/dd/yyyy")
                dateStrArr = value.split("/")//split string into array
                thisDay = dateStrArr[1]
                thisMonth = dateStrArr[0]
                thisYear = dateStrArr[2]
            } else {
                value = "Select Date"
            }

            //Create date text field 
            out.println "<input type=\"text\" name=\"${name}\" id=\"${id}\" class=\"dateField cahubDate\" readonly=\"readonly\" value=\"${value}\" />"
            out.println "<input type=\"hidden\" name=\"${name}_day\" id=\"${id}_day\" value=\"${thisDay}\" />";
            out.println "<input type=\"hidden\" name=\"${name}_month\" id=\"${id}_month\" value=\"${thisMonth}\" />";
            out.println "<input type=\"hidden\" name=\"${name}_year\" id=\"${id}_year\" value=\"${thisYear}\" />";
            out.println "<input type=\"hidden\" class=\"datePickerChangeTime\" name=\"${name}_changeTime\" id=\"${id}_changeTime\" />";
        } else if (session.LDS == false || session.LDS == null) {
            out.println "<span id=\"${id}\" class=\"redactedMsg\">REDACTED (No LDS privilege)</span>";
        }
    }


    def jqDateTimePicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name
        if ( attrs.LDSOverlay?.find("create").equals("create") || attrs.LDSOverlay?.find("edit").equals("edit") || (!attrs.LDSOverlay.equals("false") && session.LDS == true) || (attrs.LDSOverlay.equals("false") || attrs.LDSOverlay == null) ) {
            def value = attrs.value
            def currenTime = []
            def dateStrArr = []
            def timeStrArr = []
            def thisDay = ""
            def thisMonth = ""
            def thisYear = ""
            def thisHour = ""
            def thisMinute = ""

            if (value != null) {
                value = value.format("MM/dd/yyyy HH:mm")
                currenTime = value.split(" ")
                dateStrArr = currenTime[0].split("/")
                timeStrArr = currenTime[1].split(":")
                thisDay = dateStrArr[1]
                thisMonth = dateStrArr[0]
                thisYear = dateStrArr[2]
                thisHour = timeStrArr[0]
                thisMinute = timeStrArr[1]
            } else {
                value = "Select Date"
            }    
            //Create date text field 
            out.println "<input type=\"text\" name=\"${name}\" id=\"${id}\" class=\"dateField cahubDateTime\" readonly=\"readonly\" value=\"${value}\" />"
            out.println "<input type=\"hidden\" name=\"${name}_day\" id=\"${id}_day\" value=\"${thisDay}\" />";
            out.println "<input type=\"hidden\" name=\"${name}_month\" id=\"${id}_month\" value=\"${thisMonth}\" />";
            out.println "<input type=\"hidden\" name=\"${name}_year\" id=\"${id}_year\" value=\"${thisYear}\" />";
            out.println "<input type=\"hidden\" name=\"${name}_hour\" id=\"${id}_hour\" value=\"${thisHour}\" />";
            out.println "<input type=\"hidden\" name=\"${name}_minute\" id=\"${id}_minute\" value=\"${thisMinute}\" />";
            out.println "<input type=\"hidden\" class=\"dateTimePickerChangeTime\" name=\"${name}_changeTime\" id=\"${id}_changeTime\" />";
        } else if (session.LDS == false || session.LDS == null) {
            out.println "<span id=\"${id}\" class=\"redactedMsg\">REDACTED (No LDS privilege)</span>";
        }
    }


    
    def jqMonthPicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name
        //def minDate = attrs.minDate
        //def showDay = attrs.showDay
        def value = attrs.value
       
        def newValue
        
        if (value != null) {
            value = value.format("yyyy,MM,dd")
            println "Value : " + value
            def dateStrArr = value.split(",")//split string into array
            dateStrArr[1] = dateStrArr[1].toInteger() - 1 //manipulate array
            newValue = dateStrArr.join(",") //create new string
            println " new date 1:" + newValue
        }
        
        
        //Create date text field and supporting hidden text fields need by grails
        out.println "<input type=\"text\" name=\"${name}\" id=\"${id}\" />"
        //out.println "<input type=\"hidden\" name=\"${name}_day\" id=\"${id}_day\" />"
        out.println "<input type=\"hidden\" name=\"${name}_month\" id=\"${id}_month\" />"
        out.println "<input type=\"hidden\" name=\"${name}_year\" id=\"${id}_year\" />"
        out.println "<input class=\"button cleardate\" value=\"Clear\"id=\"${id}_cleardate\" />"
        
        //Code to parse selected date into hidden fields required by grails
        out.println "<script type=\"text/javascript\"> \$(document).ready(function(){"
        out.println "\$(\"#${name}\").datepicker({"
        out.println "onClose: function(dateText, inst) {"
        out.println "\$(\"#${name}_month\").attr(\"value\",new Date(dateText).getMonth() +1);"
        //out.println "\$(\"#${name}_day\").attr(\"value\",new Date(dateText).getDate());"
        out.println "\$(\"#${name}_year\").attr(\"value\",new Date(dateText).getFullYear());"
        out.println "}"
        
        out.println "});"
        out.println "})"
        
        //Paint this function on the page to set date in datetime picker from the object.
        //Note the value has been manipulated above.
        if (newValue != null) {
            println " new date 2:" + newValue
            out.println "\$(function() {"
            out.println "\$('#${name}').datepicker(\"option\", \"dateFormat\", \'mm/yy\');"
            out.println "\$('#${name}').datepicker(\"setDate\", new Date(${newValue}));"
            out.println "});"
        }
        
        
        out.println "</script>"
    }
    
        
/*    def jqDateTimePicker = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def value = attrs.value
        def newValue
        
        //Need to remove a month due to ECMA/Javascript months starting at 0
        //Incoming value is dependent on format of date passed. If you are getting wierd results,
        //check the formatdate method on the calling tag.  For example, the code below is set to work wih receive:
        //<g:jqDateTimePicker name="dateOfBirth"  value="${CRFCollectGTEXInstance?.dateOfBirth.format('yyyy, MM, dd, HH, mm')}"/>
        
        
        if (value != null) {
            def dateStrArr = value.split(",")//split string into array
            dateStrArr[1] = dateStrArr[1].toInteger() - 1//manipulate array
            newValue = dateStrArr.join(",") //create new string
        }
        
        //end shenanigans 
        
        def id = attrs.id ?: name
        def minDate = attrs.minDate
        def showDay = attrs.showDay
        
        //Create date text field and supporting hidden text fields need by grails
        out.println "<input type=\"text\" name=\"${name}\" id=\"${id}\" />"
        out.println "<input type=\"hidden\" name=\"${name}_day\" id=\"${id}_day\" />"
        out.println "<input type=\"hidden\" name=\"${name}_month\" id=\"${id}_month\" />"
        out.println "<input type=\"hidden\" name=\"${name}_year\" id=\"${id}_year\" />"
        out.println "<input type=\"hidden\" name=\"${name}_hour\" id=\"${id}_hour\" />"
        out.println "<input type=\"hidden\" name=\"${name}_minute\" id=\"${id}_minute\" />"
        
        
        //Code to parse selected date into hidden fields required by grails
        out.println "<script type=\"text/javascript\"> \$(document).ready(function(){"
        out.println "\$(\"#${name}\").datetimepicker({"
        out.println "onClose: function(dateText, inst) {"
        out.println "\$(\"#${name}_month\").attr(\"value\",new Date(dateText).getMonth() +1);"
        out.println "\$(\"#${name}_day\").attr(\"value\",new Date(dateText).getDate());"
        out.println "\$(\"#${name}_year\").attr(\"value\",new Date(dateText).getFullYear());"
        out.println "\$(\"#${name}_hour\").attr(\"value\",new Date(dateText).getHours());"
        out.println "\$(\"#${name}_minute\").attr(\"value\",new Date(dateText).getMinutes());"
        out.println "}"
        
        out.println "});"
        out.println "})"
        //Paint this function on the page to set date in datetime picker from the object.
        //Note the value has been manipulated above.
        
                
        if (newValue != null) {
            out.println "\$(function() {"
            out.println "\$('#${name}').datetimepicker(\"setDate\", new Date(${newValue}) );"
            out.println "});"
        }
        
        out.println "</script>"
        
    } */
    
}
