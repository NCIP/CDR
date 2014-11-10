package cahubdataservices

class MedicationAdminTagLib {
    def medAdmin = {attrs, body ->
        def out = out
        def name = attrs.name //The name attribute is required for the tag to work seamlessly with grails
        def id = attrs.id ?: name

        def fieldVal = attrs.namevalue ?: ""
        def doseVal = attrs.dosevalue ?: ""
        def unitVal = attrs.unitvalue ?: ""
        def timeVal = attrs.timevalue ?: ""

        def drugName = attrs.dlist ?: ""
        def doseerror = attrs.doseerror ?: ""
        def uniterror = attrs.uniterror ?: ""
        def timeerror = attrs.timeerror ?: ""
        def nameerror = attrs.nameerror ?: ""

        out.println "<tr><td nowrap=\"nowrap\" valign=\"top\" class=\"${nameerror}\">"
        if(!drugName) {
            out.println "<input type=\"text\" name=\"${name}Name\" id=\"${name}Name\" value=\"${fieldVal}\""
            out.println "onkeyup=\"setOtherMedAdmin(this,'${name}Dose','${name}Unit','${name}Time')\"/>"
        } else if("Insulin".equals(drugName) || "Steroid".equals(drugName) || "Antibiotic".equals(drugName) ||
            "Other Medication".equals(drugName) || drugName.indexOf("specify") > -1 || "Additional Pre-Operative Medication".equals(drugName) ||
                 "Additional Anesthesia Agent".equals(drugName) || "Additional Intra-operative Medication".equals(drugName)) {
            out.println "${drugName}:<span id=\"bpvsurgeryanesthesiaform.${id}\" class=\"vocab-tooltip\"></span><br /><input type=\"text\" name=\"${name}Name\" id=\"${name}Name\" value=\"${fieldVal}\""
            out.println "onkeyup=\"setOtherMedAdmin(this,'${name}Dose','${name}Unit','${name}Time')\"/>"
        } else {
            out.println "<input type=\"hidden\" name=\"${name}Name\" id=\"${name}Name\" value=\"${fieldVal?drugName:''}\"/>"
            out.println "<input type=\"checkbox\" ${fieldVal?'checked':''} name=\"_${name}Name\" id=\"_${name}Name\""
            out.println "onChange=\"setMedAdmin(this,'${name}Name','${drugName}','${name}Dose','${name}Unit','${name}Time')\"/>"
            out.println "&nbsp;<label for=\"${name}Name\" >${drugName}</label>"
        }
        out.println "</td>"

        out.println "<td class=\"${doseerror}\"><span id=\"_${name}Dose\" class=\"${fieldVal?'':'hide'}\"><label for=\"${name}Dose\">Dose</label>"
        out.println "&nbsp; <input type=\"text\" name=\"${name}Dose\" size=\"4\" id=\"${id}Dose\" value=\"${doseVal}\" onkeyup=\"isNumericValidation(this)\"/></span></td>"

        out.println "<td class=\"${uniterror}\"><span id=\"_${name}Unit\" class=\"${fieldVal?'':'hide'}\"><label for=\"${name}Unit\">Unit</label>"
        out.println "&nbsp; <input type=\"text\" name=\"${name}Unit\" size=\"4\" id=\"${id}Unit\" value=\"${unitVal}\"/></span></td>"

        out.println "<td class=\"${timeerror}\"><span id=\"_${name}Time\" class=\"${fieldVal?'':'hide'}\"><label for=\"${name}Time\">Time</label>"
        out.println "&nbsp; <input type=\"text\" name=\"${name}Time\" id=\"${id}Time\" class=\"timeEntry\" value=\"${timeVal}\"/></span></td>"
        out.println "</tr>"
    }
}
