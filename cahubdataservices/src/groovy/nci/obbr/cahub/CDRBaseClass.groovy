package nci.obbr.cahub

abstract class CDRBaseClass {

    String internalGUID = java.util.UUID.randomUUID().toString()
    String internalComments
    String publicComments
    double publicVersion
    Date dateCreated
    Date lastUpdated
    
    static auditable = true

    
    static constraints = {
        internalComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
        publicComments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
    }
}
