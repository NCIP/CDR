/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nci.obbr.cahub.datarecords

/**
 *
 * @author taborde
 */

import nci.obbr.cahub.staticmembers.AcquisitionType
import nci.obbr.cahub.staticmembers.ContainerType
import nci.obbr.cahub.staticmembers.Fixative

class ChpBloodMashup {
    long           id
    SpecimenRecord specimenRecord
    short          generation           
    Date           surgDate
    String         bloodSource
    Date           dateTimeBloodDraw
    float          volume
    String         volUnits
    ContainerType  containerType
    Fixative       fixative
    AcquisitionType acquisType    /* tissSource */
    Date           dateTimeBloodReceived
    Date           bloodProcStart
    Date           bloodProcEnd
    String         hemolysis
    Date           bloodFrozen
    Date           bloodStorage
    String         bloodProcComment
    String         bloodStorageComment
    
}

