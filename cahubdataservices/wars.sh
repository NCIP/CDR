#! /bin/sh

echo "******** Clean *************"
grails clean 
echo "******** Clean Complete **********"

echo "******** Build Test Wars **********"
#grails -Dgrails.env=ec2dev war 
grails -Dgrails.env=ec2cdr war 
grails -Dgrails.env=abccdev war 
grails -Dgrails.env=qa war 
grails -Dgrails.env=stage war 
echo "**** Build Test Wars Complete *****"

echo "******** Build Production War **********"
grails -Dgrails.env=production war
echo "**** Build Production War Complete *****"