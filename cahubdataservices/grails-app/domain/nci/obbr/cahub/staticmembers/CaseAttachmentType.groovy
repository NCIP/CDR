package nci.obbr.cahub.staticmembers

class CaseAttachmentType extends StaticMemberBaseClass{
    
    
    static mapping = {
      table 'st_caseAttachment_type'
      id generator:'sequence', params:[sequence:'st_caseAttachment_type_pk']
    }

     static searchable ={
        only= ['name', 'code']
        'name' name:'fileTypeName'
        'code' name:'fileTypeCode'
        root false
    }
    
    static constraints = {
    }
}
