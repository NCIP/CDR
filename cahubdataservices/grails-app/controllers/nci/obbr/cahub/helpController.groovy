package nci.obbr.cahub

import nci.obbr.cahub.util.AppSetting
import nci.obbr.cahub.util.HelpFileUpload

class helpController {

    def helphome = { 
    
        //def helpFileUploadInstanceList = HelpFileUpload?.list()
        //def helpFileUploadInstanceList = HelpFileUpload?.list(sort:"studyCode")
        def helpFileUploadInstanceList = HelpFileUpload?.list(params)
        return [helpFileUploadInstanceList: helpFileUploadInstanceList]
    }
    
    def download = {
       
        def helpFileUploadInstance = HelpFileUpload.get(params.id)
        if (!helpFileUploadInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpFileUpload.label', default: 'helpFileUpload'), params.id])}"
            redirect(action: "helphome")
        }
        else {   
            def convertedFilePath = helpFileUploadInstance.filePath +"/"+helpFileUploadInstance.fileName
            def file = new File(convertedFilePath)
            if (file.exists()) {
                def inputStream = new FileInputStream(file)
                response.setContentType("application/octet-stream")
                response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
                response.outputStream << inputStream // Performing a binary stream copy
                inputStream.close()
                response.outputStream.close()
            } else {
                flash.message = "File not found, please remove: " + file.getName()
                redirect(controller: 'help', action: "helphome")
            }
        }
   
        
    }
    
    def downloaddcf = {
        
        def convertedFilePath = AppSetting.findByCode('FILE_DOWNLOAD_PATH').value +"/"+AppSetting.findByCode('DCF_FILE_NAME').value
        def file = new File(convertedFilePath)
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
        response.outputStream << file.newInputStream() // Performing a binary stream copy
        
    }    
    
    
    def remove = {
       
        def helpFileUploadInstance = HelpFileUpload.get(params.id)
        def convertedFilePath = helpFileUploadInstance.filePath + File.separator + helpFileUploadInstance.fileName
        def file = new File(convertedFilePath)
        try {
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException("Failed to delete the file")
                }
            }
            helpFileUploadInstance.delete()
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.toString())
        }
        redirect(controller: 'help', action: "helphome")
    }
    
    
    

    
}
