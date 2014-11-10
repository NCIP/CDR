package nci.obbr.cahub

import nci.obbr.cahub.forms.bpv.*;
import nci.obbr.cahub.util.*;
import nci.obbr.cahub.datarecords.PhotoRecord

class BpvTissueGrossEvaluationService {

    static transactional = true
   
    def upload(params, uploadedFile) {   
        try {
            def bpvTissueGrossEvaluationInstance = BpvTissueGrossEvaluation.get(params.id)
            def originalFileName = uploadedFile.originalFilename.replace(' ', '_') //replace whitespace with underscores
            def strippedFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'))                    
            def fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.toString().size())                         
            def current_time = (new Date()).getTime()           
            def newFileName = strippedFileName + "-" + current_time + "." + fileExtension
            def pathUploads = File.separator + 'var' + File.separator + 'storage' + File.separator + 'cdrds-filestore' + File.separator + 'BPV' + File.separator + bpvTissueGrossEvaluationInstance.caseRecord.caseId + File.separator + 'tissue_photos'
            File dir = new File(pathUploads)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            uploadedFile.transferTo(new File(pathUploads, newFileName))
            
            def photoRecordInstance = new PhotoRecord(fileName:newFileName, filePath:pathUploads, bpvTissueGrossEvaluation:bpvTissueGrossEvaluationInstance)
            bpvTissueGrossEvaluationInstance.addToPhotos(photoRecordInstance)
            bpvTissueGrossEvaluationInstance.photoTaken = 'Yes'
            bpvTissueGrossEvaluationInstance.reasonNoPhoto = null
            bpvTissueGrossEvaluationInstance.save(failOnError: true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
    
    def remove(params) {
        try {
            def photoRecordInstance = PhotoRecord.get(params.photoId)
            def path = photoRecordInstance.filePath + File.separator + photoRecordInstance.fileName
            def file = new File(path)
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException("Failed to delete the tissue photo file")
                }
            }
            photoRecordInstance.delete(flush: true)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
    }
}
