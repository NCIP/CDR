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
import nci.obbr.cahub.staticmembers.TumorStatus
import nci.obbr.cahub.staticmembers.Fixative
import nci.obbr.cahub.staticmembers.Protocol

class ChpTissueMashup {
    long     id
    SpecimenRecord specimenRecord
    Date     surgDate
    Date     firstIncis
    Date     clamp1Time
    Date     clamp2Time
    Date     resectTime
    Date     grossTimeIn
    String   grossDiagx
    Date     grossTimeOut
    String   tissSop
    Date     tissDissecTime
    String   tissComment
    String   tissGrossId
    AcquisitionType acquisType    /* tissSource */
    TumorStatus     tumorStatus   /* grossDiag  */
    Date     dissecStartTime
    Date     dissecEndTime
    Fixative fixative
    Protocol protocol
    String   protoDelayToFix
    String   protoTimeInFix
    Date     timeInCass
    Date     timeInFix
    Date     timeInProcessor
    Date     procTimeEnd
    Date     procTimeRemov
    Date     timeEmbedded
    String   calcDelayToFix
    String   calcTimeInFix
 
}

