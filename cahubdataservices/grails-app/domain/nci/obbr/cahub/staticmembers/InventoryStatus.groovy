package nci.obbr.cahub.staticmembers

class InventoryStatus extends StaticMemberBaseClass{
    
    
    static mapping = {
      table 'st_inventory_status'
      id generator:'sequence', params:[sequence:'st_inventory_status_pk']
    }

    static constraints = {
    }
}
