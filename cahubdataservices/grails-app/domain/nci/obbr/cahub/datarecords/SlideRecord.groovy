package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.forms.*
import nci.obbr.cahub.prctumor.*
import nci.obbr.cahub.forms.bpv.BpvLocalPathReview
import nci.obbr.cahub.prc.bpv.BpvPrcPathReview
import nci.obbr.cahub.prc.bpv.BpvPrcExpSampleReview

class SlideRecord extends DataRecordBaseClass {
    
    SpecimenRecord specimenRecord
    String slideId
    boolean requestReorder
    ReorderType reorderType
    ReorderReason reorderReason
    String reorderOtherComment
    Date reorderRequestDate
    Date dateCreated
    Date lastUpdated
    Organization slideLocation
    String finalSurgicalReview 
    String localPathologyReview 
    String caseId
    String createdBy
    Module module
 
    String toString(){"$slideId"}

    static hasOne = [imageRecord:ImageRecord, prcForm:PrcForm, bpvLocalPathReview:BpvLocalPathReview, bpvPrcPathReview:BpvPrcPathReview]
    static hasMany = [shipEvents:ShippingEvent, bpvPrcExpSampleReviews:BpvPrcExpSampleReview, processEvents:ProcessingEvent]
    
    def static searchable = {
         only= ['id', 'slideId']
         root false   
     }
    
    static constraints = {
        specimenRecord(blank:true,nullable:true)
        slideId(blank:false,nullable:false,unique:true)
        requestReorder(blank:true,nullable:true)
        reorderType(blank:true,nullable:true)
        reorderReason(blank:true,nullable:true)
        reorderOtherComment(blank:true,nullable:true,maxSize:4000)
        reorderRequestDate(blank:true,nullable:true)
        slideLocation(blank:false,nullable:false)
        shipEvents(blank:true,nullable:true)
        finalSurgicalReview(blank:true,nullable:true)
        localPathologyReview(blank:true,nullable:true)
        caseId(blank:true,nullable:true)
        imageRecord(blank:true,nullable:true)
        prcForm(blank:true,nullable:true)
        bpvLocalPathReview(blank:true,nullable:true)
        bpvPrcPathReview(blank:true,nullable:true)
        createdBy(blank:true,nullable:true)
        processEvents(blank:true,nullable:true)
        module(blank:true,nullable:true)
    }
    
    enum ReorderType {

        Recut("Recut"),
        Reembed("Re-embed"),
        Relabel("Re-label"),
        Refix("Re-Fix"),
        Other("Other, specify")

        final String value;

        ReorderType(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Recut, Reembed, Relabel, Refix, Other]
        }
    }        
    
    enum ReorderReason {
      
        Mislabel("Mislabeled"),
        Broken("Slide broken/cracked"),
        Stain("Stain is too dark/light"),
        Other("Other, specify")

        final String value;

        ReorderReason(String value) {
            this.value = value;
        }
        String toString(){
            value;
        }
        String getKey(){
            name()
        }
        static list() {
            [Mislabel, Broken, Stain, Other]
        }
    } 
    
    
    static mapping = {
        table 'dr_slide'
        id generator:'sequence', params:[sequence:'dr_slide_pk']
    }
    
}
