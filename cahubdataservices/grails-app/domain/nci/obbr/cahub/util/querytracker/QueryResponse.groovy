package nci.obbr.cahub.util.querytracker

import nci.obbr.cahub.CDRBaseClass

class QueryResponse extends CDRBaseClass {

    String response
    String responder
    
    static belongsTo = [query:Query, deviation:Deviation]
    
    static constraints = {
        response(nullable:true, blank:true, widget:'textarea', maxSize:4000)
        responder(nullable:true, blank:true)
        query(nullable:true, blank:true)
        deviation(nullable:true, blank:true)
    }
    
    static mapping = {
        table 'query_response'
        id generator:'sequence', params:[sequence:'query_response_pk']
    } 
}
