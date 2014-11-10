package nci.obbr.cahub.staticmembers

class TransactionType extends StaticMemberBaseClass{
    static mapping = {
      table 'st_transaction_type'
      id generator:'sequence', params:[sequence:'st_transaction_type_pk']
    }
    
     static searchable ={
        only= ['name', 'code']
        'name' name:'studyName'
        'code' name:'studyCode'
        root false
    }
}
