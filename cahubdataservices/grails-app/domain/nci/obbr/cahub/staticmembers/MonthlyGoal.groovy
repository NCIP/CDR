package nci.obbr.cahub.staticmembers

class MonthlyGoal extends StaticMemberBaseClass {
    String month
    Date beginDate
    Date endDate
    Integer numPeriods
    String milestone
    String criteria
    Integer total
    
    String toString(){
        if(description)
            return name + " - " + description
        else 
            return name
    }
    static mapping = {
      table 'st_monthly_goal'
      id generator:'sequence', params:[sequence:'st_monthly_goal_pk']
    }
    static constraints = {
        month(nullable:false, blank:false)
        beginDate(nullable:true, blank:true)
        endDate(nullable:true, blank:true)
        numPeriods(nullable:true, blank:true)
        milestone(nullable:true, blank:true)
        criteria(nullable:true, blank:true)
        total(nullable:true, blank:true)
        name(nullable:false, blank:false, unique:true)
        
//        criteria(unique: 'month')  // define a two-column unique constraint: month must be unique /within/ each criteria. (doesn't seem to work)
    }
}
