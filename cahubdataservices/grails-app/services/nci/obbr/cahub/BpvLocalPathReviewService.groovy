package nci.obbr.cahub

import nci.obbr.cahub.forms.bpv.*;
import nci.obbr.cahub.datarecords.*;
import nci.obbr.cahub.staticmembers.*;
import nci.obbr.cahub.util.*;

class BpvLocalPathReviewService {

    static transactional = true
    
    def createForm(bpvLocalPathReviewInstance) { 
        try {
            bpvLocalPathReviewInstance.status = 'Editing'
            def tissue = bpvLocalPathReviewInstance.slideRecord.specimenRecord.tissueType.code
            
            if (tissue == 'OVARY') {
                bpvLocalPathReviewInstance.category = "Ovary"
            }
           
            if (tissue == 'KIDNEY') {
                bpvLocalPathReviewInstance.category = "Kidney"
            }
            
            if (tissue == 'COLON') {
                bpvLocalPathReviewInstance.category = "Colon"
            }
            
            if (tissue == 'LUNG') {
                bpvLocalPathReviewInstance.category = "Lung"
            }
            
            def caseRecord=bpvLocalPathReviewInstance.slideRecord.specimenRecord?.caseRecord
            if(caseRecord){
                caseRecord.hasLocalPathReview = true
                caseRecord.save(failOnError:true)
            }
            
            bpvLocalPathReviewInstance.save(failOnError:true)
        } catch (Exception e) {
            throw new RuntimeException(e.toString())
        }
    }
    
    def retrieveHisList(bpvLocalPathReviewInstance) {
        try {
            def tissue = bpvLocalPathReviewInstance.slideRecord.specimenRecord.tissueType.code
            def hisList0
            def hisList1 = []
            def c20
            def other
            
            if (tissue == 'OVARY') {
                def ovary_str = (AppSetting.findByCode("HISTO_OVARY")).bigValue
                def list = "'" + ovary_str.replace(",", "','") + "'"
                hisList0 = HistologicType.executeQuery("select h from HistologicType h where h.code in (" + list + ") order by h.name")
            }
           
            if (tissue == 'KIDNEY') {
                def kidney_str = (AppSetting.findByCode("HISTO_KIDNEY")).bigValue
                def list = "'" + kidney_str.replace(",", "','") + "'"
                hisList0 = HistologicType.executeQuery("select h from HistologicType h where h.code in (" + list + ") order by h.name")
            }
            
            if (tissue == 'COLON') {
                def colon_str = (AppSetting.findByCode("HISTO_COLON")).bigValue
                def list = "'" + colon_str.replace(",", "','") + "'"
                hisList0 = HistologicType.executeQuery("select h from HistologicType h where h.code in (" + list + ") order by h.name")
            }
            
            if (tissue == 'LUNG') {
                def lung_str = (AppSetting.findByCode("HISTO_LUNG")).bigValue
                def list = "'" + lung_str.replace(",", "','") + "'"
                hisList0 = HistologicType.executeQuery("select h from HistologicType h where h.code in (" + list + ") order by h.name")
            }
            
            // Put "other" options at the end
            hisList0.each() {
                if (it.code == 'C20') {
                    c20 = it
                } else if (it.code == 'OTHER') {
                    other = it
                } else {
                    hisList1.add(it)
                }
            }
            if (c20 != null) {
                hisList1.add(c20)
            }
            if (other != null) {
                hisList1.add(other)
            }
            
            return hisList1
        } catch (Exception e) {
            throw new RuntimeException(e.toString())
        }
    }
    
    def saveForm(params) {
        def bpvLocalPathReviewInstance = BpvLocalPathReview.get(params.id)
        bpvLocalPathReviewInstance.properties = params
        
        if (params.histologicType?.id) {
            def histologicType = HistologicType.findById(params.histologicType.id)
            if (histologicType.code == 'C7') {
                bpvLocalPathReviewInstance.otherHistologicType = params.detail_C7  
            } else if (histologicType.code == 'C8') {
                bpvLocalPathReviewInstance.otherHistologicType = params.detail_C8   
            } else if (histologicType.code == 'C9') {
                bpvLocalPathReviewInstance.otherHistologicType = params.detail_C9
            } else if (histologicType.code == 'C20') {
                bpvLocalPathReviewInstance.otherHistologicType = params.detail_C20
            } else if (histologicType.code == 'OTHER') {
                bpvLocalPathReviewInstance.otherHistologicType = params.detail_other
            } else if (histologicType.code == 'C78') {
                bpvLocalPathReviewInstance.otherHistologicType = params.detail_C78
            } else {
                bpvLocalPathReviewInstance.otherHistologicType = null
            }
        }
        
        bpvLocalPathReviewInstance.save(failOnError:true)  
    }
     
    def reviewForm(bpvLocalPathReviewInstance, username) {
        try {
            bpvLocalPathReviewInstance.status = "Reviewed"
            bpvLocalPathReviewInstance.reviewedBy = username
            bpvLocalPathReviewInstance.reviewDate = new Date()
            bpvLocalPathReviewInstance.save(failOnError: true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
    
    def reEdit(bpvLocalPathReviewInstance) {
        try {
            bpvLocalPathReviewInstance.status = "Editing"
            bpvLocalPathReviewInstance.reviewedBy=null
            bpvLocalPathReviewInstance.reviewDate=null
            bpvLocalPathReviewInstance.save(failOnError: true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
   
    def upload(params, uploadedFile, username) {   
        try {
            def caseRecordInstance = CaseRecord.get(params.id)
            def originalFileName = uploadedFile.originalFilename.replace(' ', '_') //replace whitespace with underscores
            def strippedFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'))                    
            def fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size())                         
            def current_time = (new Date()).getTime()           
            def newFileName = strippedFileName + "-" + current_time + "." + fileExtension
            def dir_name = 'surgical_path_report'
            def pathUploads = File.separator + 'var' + File.separator + 'storage' + File.separator + 'cdrds-filestore' + File.separator + 'BPV' + File.separator + caseRecordInstance.caseId + File.separator + dir_name
            File dir = new File(pathUploads)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            uploadedFile.transferTo(new File(pathUploads, newFileName))
            caseRecordInstance.finalSurgicalPath = pathUploads + File.separator + newFileName
            caseRecordInstance.dateFspUploaded = new Date()
            caseRecordInstance.fspUploadedBy = username
            caseRecordInstance.save(failOnError: true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
    
    def remove(params) {
        try {
            def caseRecordInstance = CaseRecord.get(params.id)
            def convertedFilePath = caseRecordInstance.finalSurgicalPath
            def file = new File(convertedFilePath)
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException("Failed to delete the final surgical pathology report")
                }
            }
            caseRecordInstance.finalSurgicalPath = null
            caseRecordInstance.dateFspUploaded = null
            caseRecordInstance.fspUploadedBy = null
            caseRecordInstance.save(failOnError:true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
}
