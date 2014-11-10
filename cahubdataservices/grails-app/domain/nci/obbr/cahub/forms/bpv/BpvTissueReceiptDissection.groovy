package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SOPRecord

class BpvTissueReceiptDissection extends BpvFormBaseClass {

    CaseRecord caseRecord
    String bloodSamplesCollected
    boolean bloodNotReceived
    boolean bloodNotDrawn
    boolean aliquotsNotBanked
    boolean hemolyzedUnusableBlood
    boolean otherReason
    String specifiedOtherReason

    SOPRecord receiptDissectSOP
    Date dateTimeTissueReceived
    String tissueRecipient
    String tissueReceiptComment

    String experimentalKeyId
    String parentTissueDissectedBy
    String parentTissueDissectBegan
    String parentTissueDissectEnded
    String grossAppearance
    String otherGrossAppearance
    String tumorSource
    String collectionProcedure
    String otherCollectionProcedure

    String fixativeType
    String otherFixativeType
    String fixativeFormula
    String fixativePH
    String fixativeManufacturer
    String fixativeLotNum
    Date dateFixativeLotNumExpired
    String fixativeProductNum
    String fixativeProductType
    String otherFixativeProductType
    String formalinFreshRecycled
    String parentTissueSopComment
    Integer formVersion=2

    static belongsTo = CaseRecord

    String toString(){"$caseRecord.caseId"}

    static constraints = {
        caseRecord(blank:false, nullable:false)
        bloodSamplesCollected(blank:true, nullable:true, 
            validator: {val, obj -> return (val == "Yes" || val == "No" || val == null)}
        )
        bloodNotReceived(blank:true, nullable:true)
        bloodNotDrawn(blank:true, nullable:true)
        aliquotsNotBanked(blank:true, nullable:true)
        hemolyzedUnusableBlood(blank:true, nullable:true)
        otherReason(blank:true, nullable:true)
        specifiedOtherReason(blank:true, nullable:true, widget:'textarea', maxSize:4000)

        receiptDissectSOP(blank:true, nullable:true)
        dateTimeTissueReceived(blank:true, nullable:true)
        tissueRecipient(blank:true, nullable:true)
        tissueReceiptComment(blank:true, nullable:true, widget:'textarea', maxSize:4000)

        experimentalKeyId(blank:true, nullable:true)
        parentTissueDissectedBy(blank:true, nullable:true)
        parentTissueDissectBegan(blank:true, nullable:true)
        
        // The Validater has been moved to the controller in accordance with JIRA CDRQA-1322. 09/03/2014 umkis  
        parentTissueDissectEnded(blank:true, nullable:true)
        //parentTissueDissectEnded(blank:true, nullable:true,
        //    validator: {val, obj -> if(obj.parentTissueDissectBegan) return (val >= obj.parentTissueDissectBegan)}
        //)
        otherGrossAppearance(blank:true, nullable:true)
        grossAppearance(blank:true, nullable:true)
        tumorSource(blank:true, nullable:true)
        collectionProcedure(blank:true, nullable:true)
        otherCollectionProcedure(blank:true, nullable:true)

        fixativeType(blank:true, nullable:true)
        otherFixativeType(blank:true, nullable:true)
        fixativeFormula(blank:true, nullable:true)
        fixativePH(blank:true, nullable:true)
        fixativeManufacturer(blank:true, nullable:true)
        fixativeLotNum(blank:true, nullable:true)
        dateFixativeLotNumExpired(blank:true, nullable:true)
        fixativeProductNum(blank:true, nullable:true)
        fixativeProductType(blank:true, nullable:true)
        otherFixativeProductType(blank:true, nullable:true)
        formalinFreshRecycled(blank:true, nullable:true)
        parentTissueSopComment(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        formVersion(blank:true, nullable:true)
    }

    static mapping = {
        table 'bpv_tissue_receipt_dissect'
        id generator:'sequence', params:[sequence:'bpv_tissue_receipt_dissect_pk']
    }
}
