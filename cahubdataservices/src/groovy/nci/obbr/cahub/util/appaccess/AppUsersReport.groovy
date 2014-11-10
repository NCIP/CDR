/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nci.obbr.cahub.util.appaccess
import nci.obbr.cahub.util.appaccess.AppUsers

/**
 *
 * @author hariharanp
 * 02/01/2013
 * This POGO is used to create Application Users Report
 */
class AppUsersReport {
	
    String firstname
    String lastname
    String title
    String email
    String phone
    String organization
    
    String receiveAlerts
    
    //application access status
    String hasAperio
    Date aperioActiveDate
    Date aperioDeacDate
    //pmh: bio4D has been replaced by BRIMS 07/13
    String hasBRIMS
    Date BRIMSActiveDate
    Date BRIMSDeacDate
    
    String hasCDR
    Date cdrActiveDate
    Date cdrDeacDate
    
    
    // master control
    String hasMC
    Date mcActiveDate
    Date mcDeacDate
    
    // following deprecated or replaced as of CDR Release 5.0 07/13 
    String hasOpenClinica
    Date openclinicaActiveDate
    Date openclinicaDeacDate
    String nihSecurityTraining
    Date nihTrainingDt
    String hipaaTraining
    Date hipaaTrainingDt
   //  String hasBio4D
   // Date bio4dActiveDate
   // Date bio4dDeacDate
}

