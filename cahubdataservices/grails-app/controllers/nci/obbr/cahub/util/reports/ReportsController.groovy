package nci.obbr.cahub.util.reports
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import nci.obbr.cahub.util.appaccess.*
import nci.obbr.cahub.staticmembers.Organization
import groovy.time.* 

class ReportsController {
    
    // Export service provided by Export plugin	
    def exportService
    
    def index = { 
        
        redirect(action:"splitString")
    }
    
    
    def appUser = {
        /**
        Creates report (CSV, EXCEL or PDF format)for App Users.
        If params.org ID exists, then create report for users of the given Org.
        If not, create report containing all users.
        If training and receivealert details is requested, then the report will contain them. Else not.
         **/
        
        
       
        def dateFormatter = { domain, value ->
            String S = String.format("%1\$TD", value)
            if (S.equals ('NULL')){
                S=''
            }
            return S
        } 
        def allAppUsers
        def appUserStatus
        def appUserMaster = []
        
        // FIELDS ARE CASE SENSITIVE. MUST MATCH WHAT YOU HAVE IN THE POGO
        List   fields = ["firstname", "lastname","organization", "title","phone", "email", 'hasAperio', 'aperioActiveDate', 'aperioDeacDate', 'hasBRIMS','BRIMSActiveDate','BRIMSDeacDate', 'hasCDR','cdrActiveDate','cdrDeacDate','hasMC','mcActiveDate','mcDeacDate']
        Map labels = ["firstname": "FIRSTNAME", "lastname": "LASTNAME","organization": "ORGANIZATION","title":"TITLE","phone":"PHONE", "email":"EMAIL", "hasAperio":"Aperio","aperioActiveDate":"Aperio Active Date","aperioDeacDate":"Aperio Deac Date","hasBRIMS":"BRIMS","BRIMSActiveDate":"BRIMS Active Date","BRIMSDeacDate":"BRIMS Deac Date","hasCDR": "CDR","cdrActiveDate":"CDR Active date","cdrDeacDate":"CDR Deac Date","hasMC": "Master Control","mcActiveDate":"MC Active date","mcDeacDate":"MC Deac Date"]  
       
        if(params.receiveAlerts){
                       
            fields.add("receiveAlerts")
            labels.put('receiveAlerts','RECEIVE ALERTS')
        }
        
        
        // if Organization ID is NOT given, find all
        if(!params.orgid || params.orgid.equals("ALL") ){
            //allAppUsers =AppUsers.findAll(sort:"lastname")
            allAppUsers =AppUsers.findAll("from AppUsers order by organization.name, lastname")
        }
        else{
            //search by given org ID
            allAppUsers=AppUsers.findAllByOrganization(Organization.findById(params.orgid),[sort:"lastname"])
        }
        // iterate the appuser list and populate the AppUsersReport pogo
        allAppUsers.each{
            if (it) {
                   
                def appUserFields = new AppUsersReport (
                    
                    firstname: it.firstname,
                    lastname: it.lastname,
                    organization:  it.organization?.name,
                    title: it.title,
                    phone:  it.phone,
                    email: it.email,
                    //hipaaTraining: it.hipaaTraining,
                    //hipaaTrainingDt:it.hipaaTrainingDt,
                    //nihSecurityTraining: it.nihSecurityTraining,
                    //nihTrainingDt:it.nihTrainingDt,
                    receiveAlerts:it.receiveAlerts,
                    hasAperio:'NO',
                    hasBRIMS:'NO',
                    hasCDR:'NO',
                    hasMC:'NO'
                    //hasOpenClinica:'NO'
                        
                )
                appUserStatus = AccountStatus.findByApplicationListingAndAppUser(ApplicationListing.findByName('Aperio'),it)
                if(appUserStatus){
                    if(appUserStatus.dateCreated2){
                        appUserFields.hasAperio='YES'
                        appUserFields.aperioActiveDate=appUserStatus.dateCreated2
                    }
                    if(appUserStatus.dateDeactivated){
                        appUserFields.aperioDeacDate=appUserStatus.dateDeactivated
                    }
                            
                }
                appUserStatus = AccountStatus.findByApplicationListingAndAppUser(ApplicationListing.findByName('BRIMS'),it)
                if(appUserStatus){
                    if(appUserStatus.dateCreated2){
                        appUserFields.hasBRIMS='YES'
                        appUserFields.BRIMSActiveDate=appUserStatus.dateCreated2
                    }
                    if(appUserStatus.dateDeactivated){
                        appUserFields.BRIMSDeacDate=appUserStatus.dateDeactivated
                    }
                    
                            
                }
                appUserStatus = AccountStatus.findByApplicationListingAndAppUser(ApplicationListing.findByName('CDR'),it)
                if(appUserStatus){
                    if(appUserStatus.dateCreated2){
                        appUserFields.hasCDR='YES'
                        appUserFields.cdrActiveDate=appUserStatus.dateCreated2
                    }
                    if(appUserStatus.dateDeactivated){
                        appUserFields.cdrDeacDate=appUserStatus.dateDeactivated
                    }
                }
                
                appUserStatus = AccountStatus.findByApplicationListingAndAppUser(ApplicationListing.findByName('MasterControl'),it)
                if(appUserStatus){
                    if(appUserStatus.dateCreated2){
                        appUserFields.hasMC='YES'
                        appUserFields.mcActiveDate=appUserStatus.dateCreated2
                    }
                    if(appUserStatus.dateDeactivated){
                        appUserFields.mcDeacDate=appUserStatus.dateDeactivated
                    }   
                }
                
                    
                    
                appUserMaster.add(appUserFields)
            }
        }
                
        // report type choice is csv, excel or pdf as given in the .gsp
        if(params?.format && params.format != "html"){
         
            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=APPUSERRPT.${params.format}")
            // Map formatters = [hipaaTrainingDt: dateFormatter,nihTrainingDt:dateFormatter,aperioActiveDate:dateFormatter,aperioDeacDate:dateFormatter,bio4dActiveDate:dateFormatter,bio4dDeacDate:dateFormatter,cdrActiveDate:dateFormatter,cdrDeacDate:dateFormatter,openclinicaActiveDate:dateFormatter,openclinicaDeacDate:dateFormatter]
            Map formatters = [aperioActiveDate:dateFormatter,aperioDeacDate:dateFormatter,BRIMSActiveDate:dateFormatter,BRIMSDeacDate:dateFormatter,cdrActiveDate:dateFormatter,cdrDeacDate:dateFormatter,mcActiveDate:dateFormatter,mcDeacDate:dateFormatter]
            //exportService.export(params.format, response.outputStream, myrpt, fields, labels, formatters, [:])
            exportService.export(params.format, response.outputStream, appUserMaster, fields, labels, formatters, [:])
          
        }       
        
    }
    
    
    def splitString = {
     
        // From production I ran the query "select distinct organs_donated from gtex_tissue_recovery where upper(organs_donated) is not null" and exported the output to a .csv file
        def rtrnlist=[]
        def myrow = []
        File file = new File('c:\\tmp\\organs_051313.csv')
        if(file.exists()) {
         
            file.eachLine {line ->
              
                myrow = line.split(',');
                def tmpstr
                myrow.each{
                           
                    //  println it-'"'
                    // this removes the first and last trailing double quotes from the substring
                    tmpstr=it - '"'
                    //trim and convert to lowercase to eliminate case sensitivity
                    tmpstr=tmpstr.trim().toLowerCase()
                    
                    if(! rtrnlist.contains(tmpstr)){
                        
                        rtrnlist.add(tmpstr)
                    }
                    
                }
                
               
            }
         
          
            File writeToFile = new File('c:\\tmp\\organsDonatedUnique051313.csv')
            writeToFile.withWriter{ out ->
                //rtrnlist.unique().each {out.println it}
                rtrnlist.each {out.println it}
            }
            
    
        }
        else{
            println "file does not exist"
        }
        
        
       
    }
    
}
