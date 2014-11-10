set JAVA_OPTS=-XX:MaxPermSize=512m -XX:PermSize=768m -Xms1024m -Xmx1536m
if %1. ==. goto noparams 
grails clean && grails -Dgrails.env=%1 war 
:noparams
grails clean && grails -Dgrails.env=ec2dev war && grails -Dgrails.env=ec2cdr war && grails -Dgrails.env=abccdev war && grails -Dgrails.env=stage war && grails -Dgrails.env=qa war && grails -Dgrails.env=production war
