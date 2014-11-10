package nci

class ErrorsController {

    //this needs to be made more elegant-- much more
    
    def error500 = {
        render(view: '/errors/error500')
    }
    
    def error403 = {
        render(view: '/errors/errors')
    }
    
    def error401 = {
        render(view: '/errors/errors')
    }  
    
    def error404 = {
        render(view: '/errors/error404')
    }   
}


