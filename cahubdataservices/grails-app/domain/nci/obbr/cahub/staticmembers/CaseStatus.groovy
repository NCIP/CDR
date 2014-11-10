package nci.obbr.cahub.staticmembers

class CaseStatus extends StaticMemberBaseClass{

    static searchable = {
        only = ['name', 'code', 'description']
        'name' name:'statusName'
        'code' name:'statusCode'
        'description' name:'statusDescription'
        root false
    }

     static mapping = {
      table 'st_case_status'
      id generator:'sequence', params:[sequence:'st_case_status_pk']
    }
}
