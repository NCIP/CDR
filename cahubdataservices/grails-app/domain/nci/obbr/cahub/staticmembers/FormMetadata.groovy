/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nci.obbr.cahub.staticmembers

/**
 *
 * @author shivece
 */
class FormMetadata extends StaticMemberBaseClass {
    
    Study study
    String paperFormName
    String formNumber //can be alpha numeric, should be in gaps of 10 if numeric
    String cdrFormName //outward-facing form title, needs to match SOP and work instruction
    String timeConstraintLabel 
    int timeConstraintVal //time to completion, calculate against date created to compare
    String domId //label to put on DOM for automated testing software to easily identify links to forms
    String comments
    
    List sops
    static hasMany = [sops:SOP]
    
    String toString(){"$name"}
    
    static mapping = {
        table 'st_form_metadata'
        id generator:'sequence', params:[sequence:'st_form_metadata_pk']
        //sops sort:'sopNumber'
    }

    static constraints = {
        study(nullable:false,blank:false)
        paperFormName(nullable:true,blank:true)
        formNumber(nullable:true,blank:true)
        cdrFormName(nullable:true,blank:true)
        timeConstraintLabel(nullable:true,blank:true)
        timeConstraintVal(nullable:true,blank:true)
        domId(nullable:true,blank:true,unique:true)
        comments(blank:true,nullable:true,widget:'textarea',maxSize:4000)  
    }    
    
}
