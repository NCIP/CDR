package nci.obbr.cahub.datarecords

import nci.obbr.cahub.staticmembers.*
import nci.obbr.cahub.forms.*

class ImageRecord extends DataRecordBaseClass{

    SlideRecord slideRecord
    String imageId

    String toString(){"$imageId"}


    static constraints = {

        imageId(blank:false,nullable:false,unique:true)
    }
    
    
    static mapping = {
      table 'dr_image'
      id generator:'sequence', params:[sequence:'dr_image_pk']
    }
}
