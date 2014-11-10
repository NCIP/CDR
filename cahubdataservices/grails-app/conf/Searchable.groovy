/**
 * This {@link groovy.util.ConfigObject} script provides Grails Searchable Plugin configuration.
 *
 * You can use the "environments" section at the end of the file to define per-environment
 * configuration.
 *
 * Note it is NOT required to add a reference to this file in Config.groovy; it is loaded by
 * the plugin itself.
 *
 * Available properties in the binding are:
 *
 * @param userHome The current user's home directory.
 *                 Same as System.properties['user.home']
 * @param appName The Grails environment (ie, "development", "test", "production").
 *                Same as System.properties['grails.env']
 * @param appVersion The version of your application
 * @param grailsEnv The Grails environment (ie, "development", "test", "production").
 *                  Same as System.properties['grails.env']
 *
 * You can also use System.properties to refer to other JVM properties.
 *
 * This file is created by "grails install-searchable-config", and replaces
 * the previous "SearchableConfiguration.groovy"
 */
searchable {
   
    
    bulkIndexOnStartup = false
    
    compassSettings = [ 'compass.engine.mergeFactor':'3', 'compass.engine.maxBufferedDocs':'3']

    
    
  environments {
    development {
        searchable {
           compassConnection = new File(
              "${userHome}/.grails/projects/${appName}/searchable-index"
           ).absolutePath

        }
    }

   ec2dev{
          searchable {
          
           compassConnection = new File(
                  "/var/storage/cdrds-index"
            ).absolutePath
        }
                        
         
    }    
    
    ec2cdr{
        searchable {
          
           compassConnection = new File(
                  "/var/storage/cdrds-index"
            ).absolutePath
        }
    }
                    
   abccdev {
        searchable {
          
           compassConnection = new File(
                  "/var/storage/cdrds-index"
            ).absolutePath
        }
    }
    
     qa {
        searchable {
          
           compassConnection = new File(
                  "/var/storage/cdrds-index"
            ).absolutePath
        }
    }
        
     stage {
        searchable {
          
           compassConnection = new File(
                  "/var/storage/cdrds-index"
            ).absolutePath
        }
    }

    production {
        searchable {
            compassConnection = new File(
                  "/var/storage/cdrds-index"
            ).absolutePath
        }
    }
   
   }
     
}
