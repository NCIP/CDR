package nci.obbr.cahub.forms.bpv

import nci.obbr.cahub.CDRBaseClass
import nci.obbr.cahub.datarecords.CaseRecord
import nci.obbr.cahub.datarecords.SOPRecord

class BpvTissueProcessEmbed extends BpvFormBaseClass {
    
    CaseRecord caseRecord
    String expKeyBarcodeId
    SOPRecord processingSOP
    String tissProcessorMdl
    String othTissProcessorMdl
    String procMaintenance
    String othProcMaintenance
    String alcoholType
    String othAlcoholType
    String clearingAgt
    String othClearingAgt
    String alcoholStgDur
    String othAlcoholStgDur
    String dehydrationProcDur
    String othDehydrationProcDur
    String temperatureDehyd
    String othTemperatureDehyd
    String numStages
    String othNumStages
    String clearingAgtDur
    String othClearingAgtDur
    String temperatureClearingAgt
    String othTemperatureClearingAgt
    String paraffImpreg
    String othParaffImpreg
    String tempParaffinProc
    String othTempParaffinProc
    String addtlCommtsProc
    SOPRecord embeddingSOP
    String typeParaffin
    String paraffinManufacturer
    String otherParaffinManufacturer
    String paraffinProductNum
    String paraffinLotNum
    Double tempParaffinEmbed
    String tempParaffinEmbedUnit
    String paraffinUsage
    String otherParaffinUsage
    String waxAge
    String totalTimeBlocksCooled
    String storedFfpeBlocksPerSop
    String othStoredFfpeBlocksPerSop
    String addtlCommentsEmbed
    String siteSOPProcEmbed

    static belongsTo = CaseRecord

    String toString(){"$caseRecord.caseId"}

    static constraints = {
        caseRecord(blank:false, nullable:false)
        expKeyBarcodeId(blank:true, nullable:true)
        processingSOP(blank:true, nullable:true)
        tissProcessorMdl(blank:true, nullable:true)
        othTissProcessorMdl(blank:true, nullable:true)
        procMaintenance(blank:true, nullable:true)
        othProcMaintenance(blank:true, nullable:true)
        alcoholType(blank:true, nullable:true)
        othAlcoholType(blank:true, nullable:true)
        clearingAgt(blank:true, nullable:true)
        othClearingAgt(blank:true, nullable:true)
        alcoholStgDur(blank:true, nullable:true)
        othAlcoholStgDur(blank:true, nullable:true)
        dehydrationProcDur(blank:true, nullable:true)
        othDehydrationProcDur(blank:true, nullable:true)
        temperatureDehyd(blank:true, nullable:true)
        othTemperatureDehyd(blank:true, nullable:true)
        numStages(blank:true, nullable:true)
        othNumStages(blank:true, nullable:true)
        clearingAgtDur(blank:true, nullable:true)
        othClearingAgtDur(blank:true, nullable:true)
        temperatureClearingAgt(blank:true, nullable:true)
        othTemperatureClearingAgt(blank:true, nullable:true)
        paraffImpreg(blank:true, nullable:true)
        othParaffImpreg(blank:true, nullable:true)
        tempParaffinProc(blank:true, nullable:true)
        othTempParaffinProc(blank:true, nullable:true)
        addtlCommtsProc(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        embeddingSOP(blank:true, nullable:true)
        typeParaffin(blank:true, nullable:true)
        paraffinManufacturer(blank:true, nullable:true)
        otherParaffinManufacturer(blank:true, nullable:true)
        paraffinProductNum(blank:true, nullable:true)
        paraffinLotNum(blank:true, nullable:true)
        tempParaffinEmbed(blank:true, nullable:true)
        tempParaffinEmbedUnit(blank:true, nullable:true)
        paraffinUsage(blank:true, nullable:true)
        otherParaffinUsage(blank:true, nullable:true)
        waxAge(blank:true, nullable:true)
        totalTimeBlocksCooled(blank:true, nullable:true)
        storedFfpeBlocksPerSop(blank:true, nullable:true)
        othStoredFfpeBlocksPerSop(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        addtlCommentsEmbed(blank:true, nullable:true, widget:'textarea', maxSize:4000)
        siteSOPProcEmbed(blank:true, nullable:true)
    }

    enum YesNo {
        No("No"),
        Yes("Yes")

        final String value;

        YesNo(String value) {
            this.value = value;
        }

        String toString() {
            value;
        }

        String getKey() {
            name()
        }

        static list() {
            [Yes, No]
        }
    }

    enum TissProcMdl {
        LeicaPeloris("Leica Peloris Rapid Tissue Processor"),
        OTH("Other, Specify.")

        final String value;

        TissProcMdl(String value) {
            this.value = value;
        }

        String toString() {
            value;
        }

        String getKey() {
            name()
        }

        static list() {
            [LeicaPeloris,OTH]
        }
    }

    enum AlcoholType {
        AbsAlcohol("Absolute Alcohol (100%)"),
        OTH("Other, Specify.")

        final String value;

        AlcoholType(String value) {
            this.value = value;
        }

        String toString() {
            value;
        }

        String getKey() {
            name()
        }

        static list() {
            [AbsAlcohol,OTH]
        }
    }

    enum ClearingAgtType {
        XYLENE("Xylene"),
        OTH("Other, Specify.")

        final String value;

        ClearingAgtType(String value) {
            this.value = value;
        }

        String toString() {
            value;
        }

        String getKey() {
            name()
        }

        static list() {
            [XYLENE,OTH]
        }
    }

    enum ParaffinImpregnation {
        MANUAL("Manual"),
        MACHINE_BASED("Machine-Based")

        final String value;

        ParaffinImpregnation(String value) {
            this.value = value;
        }

        String toString() {
            value;
        }

        String getKey() {
            name()
        }

        static list() {
            [MANUAL,MACHINE_BASED]
        }
    }

    enum ParaffinManufacturer {
        FISHER("Fisher"),
        OTH("Other, Specify.")

        final String value;

        ParaffinManufacturer(String value) {
            this.value = value;
        }

        String toString() {
            value;
        }

        String getKey() {
            name()
        }

        static list() {
            [FISHER,OTH]
        }
    }

    enum ParaffinUsage {
        FRESH("Fresh wax"),
        OTH("Other, Specify.")

        final String value;

        ParaffinUsage(String value) {
            this.value = value;
        }

        String toString() {
            value;
        }

        String getKey() {
            name()
        }

        static list() {
            [FRESH,OTH]
        }
    }

    static mapping = {
        table 'bpv_tissue_process_embed'
        id generator:'sequence', params:[sequence:'bpv_tissue_process_embed_pk']
    }
}
