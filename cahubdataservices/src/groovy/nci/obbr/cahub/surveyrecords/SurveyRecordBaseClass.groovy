/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nci.obbr.cahub.surveyrecords

/**
 *
 * @author shivece
 */
abstract class SurveyRecordBaseClass {

    String internalGUID = java.util.UUID.randomUUID().toString()

    Date dateSubmitted
    String submittedBy    
    String comments
    Date dateCreated
    Date lastUpdated
    
    static auditable = true
    
    static constraints = {

        dateSubmitted(blank:true,nullable:true)
        submittedBy(blank:true,nullable:true)        
        comments(blank:true,nullable:true,widget:'textarea',maxSize:4000)
    }    
    
	
}

