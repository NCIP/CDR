/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nci.obbr.cahub.util.pogo

/**
 *
 * @author umkis
 */
class AuditLogEventUnitRecord {
	
  int recordID
  Date dateCreated
  Date lastUpdated

  String actor
  String uri
  String className
  String persistedObjectId
  
  String eventName
  String propertyName
  String oldValue
  String newValue
  String note
  String link
}

