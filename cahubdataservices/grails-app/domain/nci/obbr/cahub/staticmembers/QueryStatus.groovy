package nci.obbr.cahub.staticmembers

class QueryStatus extends StaticMemberBaseClass {

     static searchable = {
        only = ['name', 'code']
        'name' name:'qtStatusName'
        'code' name:'qtStatusCode'
        root false
    }

    
    static mapping = {
        table 'st_query_status'
        id generator:'sequence', params:[sequence:'st_query_status_pk']
    }
}
