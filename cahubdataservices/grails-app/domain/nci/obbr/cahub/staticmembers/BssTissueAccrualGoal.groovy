package nci.obbr.cahub.staticmembers

class BssTissueAccrualGoal extends StaticMemberBaseClass{

    String bss
    Date expectedByDate
    Float  ovary
    Float  kidney
    Float lung
    Float colon
    Float Total
    String month
    
    String toString(){
        if(description)
        return name + " - " + description
        else 
        return name
    }
    static mapping = {
        table 'st_bss_Tissue_Accrual_goal'
        id generator:'sequence', params:[sequence:'st_bss_Tissue_Accrual_goal_pk']
    }
    static constraints = {
        bss(nullable:false, blank:false)
        expectedByDate(nullable:false, blank:false)
        ovary(nullable:false, blank:false)
        kidney(nullable:false, blank:false)
        lung(nullable:false, blank:false)
        colon(nullable:false, blank:false)
        name(nullable:false, blank:false, unique:true)
        
      
    }
}
