//check external configuration as described in Config.groovy

dataSource {
    pooled = true
    
    // For H2 Java virtural database
     driverClassName = "org.h2.Driver"
     username = "sa"
     password = ""
    
    // For MySQL
    //%%MySQL// driverClassName = "com.mysql.jdbc.Driver"
    //%%MySQL// dialect = "org.hibernate.dialect.MySQL5Dialect"
    
    // For ORACLE
    //%%Oracle// driverClassName = "oracle.jdbc.driver.OracleDriver"
    //%%Oracle// dialect = "org.hibernate.dialect.Oracle10gDialect"
}
hibernate {
    
    cache.use_second_level_cache = true
     cache.use_query_cache = false // Set 'false' if H2 Java virtural database or 'true' in any else cases
    //%%MySQL// cache.use_query_cache = true
    //%%Oracle// cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
    
}
// environment specific settings
environments {
    development {
        dataSource {
            // For H2 Java virtural database
             dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
             url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            // End of For H2
            
            // For MySQL
            //%%MySQL// username = "cdrds"
	    //%%MySQL// password = "admin"
	    //%%MySQL// dbCreate = "create"
	    //%%MySQL// url = "jdbc:mysql://127.0.0.1:3306/umkis2?autoreconnect=true"
            // End of For MySQL
    	    
    	    // For ORACLE
	    //%%Oracle// username = "cdrgit"
	    //%%Oracle// password = "admin2"
	    //%%Oracle// dbCreate = "create"
            //%%Oracle// url = "jdbc:oracle:thin:@localhost:1521:umkis2"
            // End of For ORACLE
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            pooled = true
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }
    }
}