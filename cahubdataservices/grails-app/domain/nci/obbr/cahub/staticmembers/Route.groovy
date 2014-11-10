package nci.obbr.cahub.staticmembers

class Route extends StaticMemberBaseClass{

    String toString(){
        if(description)
          return name + " - " + description
        else 
          return name
    }
    
     static mapping = {
      table 'st_route'
      id generator:'sequence', params:[sequence:'st_route_pk']
    }
    
   
}
