package nci.obbr.cahub.staticmembers

import nci.obbr.cahub.staticmembers.*

class Goal extends StaticMemberBaseClass {

    BSS bss
    Study study
    Date beginDate
    Date endDate
    Integer numPeriods
    String milestone
    String criteria
    Integer total
    
    static mapping = {
        table 'st_goal'
        id generator:'sequence', params:[sequence:'st_goal_pk']
    }
    
    static constraints = {
        study(nullable:false, blank:false)
        bss(nullable:true, blank:true)
        beginDate(nullable:true, blank:true)
        endDate(nullable:true, blank:true)
        numPeriods(nullable:true, blank:true)
        milestone(nullable:true, blank:true)
        criteria(nullable:true, blank:true)
        total(nullable:true, blank:true)
        name(nullable:false, blank:false, unique:true)
//        milestone(unique: 'criteria')  // define a two-column unique constraint: criteria must be unique /within/ each milestone. (doesn't seem to work)

    }
}
