package nci.obbr.cahub

import nci.obbr.cahub.datarecords.ChpTissueRecord
import java.sql.Timestamp

class ChpService {

    static transactional = true

    def getChildren (chpBloodKeep, chpBloodChildren, chpBloodRecord) {
        
        //def localList = chpBloodKeep.specimenRecord.findAllByParentSpecimen(chpBloodRecord.specimenRecord)
        def localList = chpBloodKeep.findAll{ it.specimenRecord.parentSpecimen?.specimenId == chpBloodRecord.specimenRecord.specimenId }
        localList.each() {
            it.generation = chpBloodRecord.generation + 1
            chpBloodChildren.add(it)
            //println "Calling getChildren from chpService: " 
            //println "it.parentSpecimen: " +it.specimenRecord.parentSpecimen
            //println "it.specimenRecord: " +it.specimenRecord
            //println "it.generation:     " +it.generation
            
            getChildren (chpBloodKeep, chpBloodChildren, it)
        }
    }
    
    def daysDiff (tzDate, refDate) {
        /* Accept two dates and return the days difference as "" (0D) 1D, 2D, etc. */
        if (tzDate && refDate) {
            def diff = refDate - tzDate
            String sDiff
            if ( diff != 0 ) {
                sDiff = String.format('%02d',diff)+"D "
                return sDiff
            }
        }
    }
def timeDiff (chpRecord, type, bss) {  // change from dissecStartTime to tissDissecTime per Erin Gover 11-April-2012 - DT
                                      // Make timeZero calculation conditional based on BSS. per Erin Gover 21-June-2012 - DT
                                      // if BSS == VUMC, use tissDissecTime as "Time Zero"
                                      // if BSS == UNM,  use resectTime as "Time Zero"
        long difference = 0
        if (type == "TIF") {
            if (chpRecord.procTimeEnd && chpRecord.timeInFix) {
                difference = ((chpRecord.procTimeEnd.time - (7*60*60*1000)) - chpRecord.timeInFix.time)
            } else {
                return ""
            }
        } else if (type == "DTF") {
            if (bss.code == "VUMC") { /* not needed: chpRecord.specimenRecord.parentSpecimen.bpvTissueForm.tissDissecTime */
                if (chpRecord.timeInFix && chpRecord.specimenRecord.parentSpecimen.bpvTissueForm.tissDissecTime ) { // tissDissecTime could be better named.  It's "Time Received in tissue bank."
                    difference = chpRecord.timeInFix.time - chpRecord.specimenRecord.parentSpecimen.bpvTissueForm.tissDissecTime.time
                } else {
                    return ""
                }
            } else if (bss.code == "UNM") { /* not needed: chpRecord.specimenRecord.parentSpecimen.bpvTissueForm.resectTime */
                    if (chpRecord.timeInFix && chpRecord.specimenRecord.parentSpecimen.bpvTissueForm.resectTime) { // The two Hospitals, UNM and VUMC, are operating differently.
                        difference = chpRecord.timeInFix.time - chpRecord.specimenRecord.parentSpecimen.bpvTissueForm.resectTime.time
                    } else {
                        return ""
                    }
            } else {
                return "Illegal function call.  Either VUMC or UNM must be specified."
            }
        } 
        if (difference == 0) {
            //println "type: " + type
            //println "chpRecord.timeInFix.class.name: "+ chpRecord.timeInFix.class.name
            //println "chpRecord.timeInFix.toString(): "+ chpRecord.timeInFix.toString()
            //println "chpRecord.tissDissecTime.toString(): "+ chpRecord.tissDissecTime.toString()
        }
        
        String sign = ""
        if (difference < 0) {
            difference = 0 - difference
            sign = "-"
        }
        // println "There were $difference milliseconds between tissDissecTime and timeInFix"

        long seconds = difference / 1000
        int minutes = 0
        int hours   = 0
        int days    = 0
        int weeks   = 0
        String sSeconds, sMinutes, sHours, sDays, sWeeks, sRet
        if (seconds > 60) {
            minutes = seconds / 60
            seconds = seconds - (minutes * 60)
            //println "minutes: " + minutes.toString()
            //println "seconds: " + seconds.toString()
            if (minutes > 60) {
                hours = minutes / 60
                minutes = minutes - (hours * 60)
                //println "hours: " + hours.toString()
                //println "minutes: " + minutes.toString()
                if (hours > 24) {
                    days = hours / 24
                    hours = hours - (days * 24)
                    if (days > 7) {
                        weeks = days / 7
                        days = days - (weeks * 7)
                    }
                }
            }
        }
        sSeconds = String.format('%02d',seconds)
        sRet = sSeconds
        sMinutes = String.format('%02d',minutes)
        sRet = sMinutes +":"+sRet
        sHours = String.format('%02d',hours)
        sRet = sHours +":"+sRet
        if ( days == 0) {
                sDays = ""
        } else {
                sDays = String.format('%02d',days)
                sRet = sDays +"D "+sRet
        }
        if ( weeks == 0) {
                sWeeks = ""
        } else {
                sWeeks = String.format('%02d',weeks)
                sRet = sWeeks +"W "+sRet
        }
        return sign+sRet
    }    

    def flagOrder(Long a, Long b) {  /* args as millis */
        if ( ! (a && b)) {
            return ""
        } else {
            long difference = (b - a)
            if (difference < 0) {
                return "incomplete"
            } else {
                return ""
            }
        }
    }

    def flagDiff(chpRecord, type, bss) {
                                      // Make timeZero calculation conditional based on BSS. per Erin Gover 21-June-2012 - DT
                                      // if BSS == VUMC, use tissDissecTime as "Time Zero"
                                      // if BSS == UNM,  use resectTime as "Time Zero"

        def difference = null
        if (type == "TIF") {
            if ( !(chpRecord.procTimeEnd && chpRecord.timeInFix)){
                    return ""
            }
            difference = ((chpRecord.procTimeEnd.time - (7*60*60*1000)) - chpRecord.timeInFix.time)
            
            if (difference < 0) {
                return "incomplete"
            }
            /*
            println "chpRecord.protocol.timeInFixative: " + chpRecord.protocol.timeInFixative
            println "-----------------"
            println "(12*60*60*1000): " + (12*60*60*1000)
            println "difference     : " + difference.toString()
            println "(24*60*60*1000): " + (24*60*60*1000)
            */
            switch (chpRecord.protocol.timeInFixative) {
                case "<6 hours in fixative":
                if ( (difference > (6*60*60*1000))) {
                    return "incomplete"
                } else {
                    return ""
                }   
                case "6-12 hours in fixative":
                if ((difference < (6*60*60*1000)) || (difference > (12*60*60*1000))) {
                    return "incomplete"
                } else {
                    return ""
                }   
                case "12-24 hours in fixative":
                if ((difference < (12*60*60*1000)) || (difference > (24*60*60*1000))) {
                    return "incomplete"
                } else {
                    return ""
                }   
                case "72 hours in fixative": /* 72 hours +/- 6 hours */
                if ((difference < (66*60*60*1000)) || (difference > (78*60*60*1000)) ) {
                    return "incomplete"
                } else {
                    return ""
                }   
            }
        } else if (type == "DTF") {
            if (bss.code == "VUMC") {
                if ( !(chpRecord.timeInFix && chpRecord.tissDissecTime)){
                    return ""
                }
                difference = (chpRecord.timeInFix.time  - chpRecord.tissDissecTime.time)
                if (difference < 0) {
                    return "incomplete"
                }
            } else if (bss.code == "UNM") {
                    if ( !(chpRecord.timeInFix && chpRecord.resectTime)){
                        return ""
                    }
                    difference = (chpRecord.timeInFix.time  - chpRecord.resectTime.time)
                    if (difference < 0) {
                        return "incomplete"
                    }
            } else {
                println "Illegal function call.  Either VUMC or UNM must be specified: " + bss.code
                return "Illegal function call.  Either VUMC or UNM must be specified."
            }
            switch (chpRecord.protocol.delayToFixation) {
                case "0-30 minutes":
                if ((difference > (30*60*1000))) {
                    return "incomplete"
                } else {
                    return ""
                }
                case "30-60 minutes":
                if ((difference < (30*60*1000)) || (difference > (60*60*1000))) {
                    return "incomplete"
                } else {
                    return ""
                }
                case "2 hours": /*  2 hours +/- 15 minutes */
                if ((difference < (1.75*60*60*1000)) || (difference > (2.25*60*60*1000))) {
                    return "incomplete"
                } else {
                    return ""
                }
                case "12+ hours": /* 12 hours +/- 2 hours */
                if ((difference < (10*60*60*1000)) || (difference > (14*60*60*1000))) {
                    return "incomplete"
                } else {
                    return ""
                }
            }
        } else if (type == "TimeRemov") {
           if ( !(chpRecord.procTimeEnd && chpRecord.procTimeRemov)){
                return ""
            }
            difference = (chpRecord.procTimeRemov.time  - chpRecord.procTimeEnd.time)
            /*
            println "chpRecord.procTimeEnd:   " + chpRecord.procTimeEnd
            println "chpRecord.procTimeRemov: " + chpRecord.procTimeRemov
            println "-----------------"
            println "(60*60*1000):            " + (60*60*1000)
            println "difference:              " + difference.toString()
            */
            if (difference < 0 || (difference > (60*60*1000)) ) {
                return "incomplete"
            } 
        }
    }
}
