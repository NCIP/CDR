package nci.obbr.cahub

import grails.plugins.springsecurity.Secured

class backofficeController {

    def springSecurityService
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def index = { }
    
    def controllers = { }
    
    def restservices = { }
    
    def cbrinvfeed = { }
    
    @Secured(['ROLE_NCI-FREDERICK_CAHUB_SUPER','ROLE_ADMIN'])
    def viewapplog = {
        
        def download = params.download
        
        def catalinaBase = System.properties.getProperty('catalina.base')
        if (!catalinaBase) catalinaBase = '.'   // just in case
        def logDirectory = "${catalinaBase}/logs"        
                       
        def logFile = logDirectory + '/' + grailsApplication.metadata['app.name'] + '.log'
        def file = new File(logFile)
        
        String message
        if ((!file.exists())||(!file.isFile())) message = "Log file has not been generated yet."
        else if (!file.getText()) message = "Log file reading failure." 
        else if (file.getText().trim().equals("")) message = "Log file is empty."
        
        if (message)
        {
            flash.error = message + ' : ' + file.getAbsolutePath()
            return
        }
               
        if ((!download)||(!download.equalsIgnoreCase('yes')))
        {
            return [filePath:file ]
        }
        else
        {
            try
            {
                response.setContentType("application/octet-stream")
                response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
                response.outputStream << file.newInputStream() // Performing a binary stream copy    
            }
            catch(Exception ee)
            {
                flash.error = "Exception : " + ee.getMessage()
                return
            }
        }    
    }
}
