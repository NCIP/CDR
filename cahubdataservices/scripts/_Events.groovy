import org.apache.catalina.*
import org.apache.catalina.connector.*
import org.apache.catalina.loader.WebappLoader
import org.codehaus.groovy.grails.commons.ConfigurationHolder

includeTargets << grailsScript("_GrailsRun")

eventCompileStart = { kind ->
  
//    Ant.copy(toDir: "/var/storage/conf/cahubdataservices", filtering: false, overwrite: true) {
//      fileset(file: "grails-app/conf/Config.external.groovy")
//    }
//    Ant.move(tofile: "/var/storage/conf/cahubdataservices/Config.groovy", filtering: false, overwrite: true) {
//      fileset(file: "/var/storage/conf/cahubdataservices/Config.external.groovy")
//    }
}

eventWarStart = {warName ->
  
//    Ant.copy(toDir: "/var/storage/conf/cahubdataservices", filtering: false, overwrite: true) {
//      fileset(file: "grails-app/conf/cahubdataservices/Config.external.groovy")
//    }
//    Ant.move(tofile: "/var/storage/conf/cahubdataservices/Config.groovy", filtering: false, overwrite: true) {
//      fileset(file: "/var/storage/conf/cahubdataservices/Config.external.groovy")
//    }
}

eventCreatedArtefact = { type, name ->
   println "eventCreatedArtefact Created $type $name"
}

eventStatusUpdate = { msg -> println "eventStatusUpdate: " + msg }

// eventStatusFinal = { msg -> println "eventStatusFinal: " + msg }

eventConfigureTomcat = {tomcat ->
println "### Starting load of custom application"
//def contextRoot = ConfigurationHolder.config.grails.myproject.contextRoot
//def buildroot= ConfigurationHolder.config.grails.myproject.build.path
//def webroot  = ConfigurationHolder.config.grails.myproject.web.root
 
//File appDir = new File(webroot);
//context = tomcat.addWebapp(contextRoot, appDir.getAbsolutePath());
//context.reloadable = true
 
//WebappLoader loader = new WebappLoader(tomcat.class.classLoader)
 
//loader.addRepository(new File(buildroot).toURI().toURL().toString());
//context.loader = loader
//loader.container = context



//enable AJP to allow apache to front tomcat
//def ajpConnector = new Connector("org.apache.jk.server.JkCoyoteHandler")
def ajpConnector = new Connector("org.apache.coyote.ajp.AjpProtocol")
ajpConnector.port = 8009
ajpConnector.setProperty("redirectPort", "8443")
ajpConnector.setProperty("protocol", "AJP/1.3")
ajpConnector.setProperty("enableLookups", "false")
	 
tomcat.service.addConnector ajpConnector


println "### Ending load of custom application"
}
