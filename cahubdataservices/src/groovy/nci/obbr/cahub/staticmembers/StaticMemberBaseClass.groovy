package nci.obbr.cahub.staticmembers

abstract class StaticMemberBaseClass {

    String name
    String code
    String description

    String toString(){"$name"}

    static auditable = true
    
    static constraints = {

        name(blank:false,nullable:false,unique:true)
        code(blank:true,nullable:true,unique:true)
        description(blank:true,nullable:true,widget:'textarea',maxSize:4000)

    }    

}
