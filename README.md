CDR - caHUB Comprehensive Data Resource
===
Welcome to the caHUB Comprehensive Data Resource (CDR). This Grails application is the original software used to run the NIH-funded GTEx study and the NCI BBRB-funded Biospecimen Preanalytical Variables study. It was developed by Leidos Biomedical Research, contracted by the NCI's Biospecimen and Biorepositories Branch (BBRB). For more information, visit:

http://commonfund.nih.gov/GTEx/index

http://biospecimens.cancer.gov/programs/bpv/

http://biospecimens.cancer.gov/programs/cahub/

http://www.ncbi.nlm.nih.gov/pubmed/23715323

The CDR is a Grails application. To get this standalone version up and running, please follow the steps below.
Prerequisites:

Install the latest JDK 7 per instructions for your environment. JDK 8 is not supported at this time.

Install Grails 2.2.4 per instructions for your environment. Later versions of Grails are not supported at this time.

See here for more Grails installation information: http://grails.org/doc/latest/guide/gettingStarted.html

After cloning from GitHub, open a console window, navigate to the cahubdataservices folder, run the following commands:

  0) set JAVA_OPTS=-XX:MaxPermSize=128m -XX:PermSize=512m -Xms1024m -Xmx2048m (This is a generous estimate. Default memory settings are not enough.)

1) grails RunApp 

This will download all project plug-ins, resolve dependencies and determine if your environment is set up properly. If all goes well, you should see the following message: Server running. Browse to http://localhost:8080/cahubdataservices

2) Open a browser if your choice and enter http://localhost:8080/cahubdataservices

3) You may log in with the following test accounts:

testerndri/Welcome01 (GTEx study user)

testerunm/Welcome01 (BPV study user)

hubadmin/Welcome01 (caHUB admin user)
	
If you run into errors, try the following:

1) Grails clean

2) Grails upgrade (say yes)

3) Grails RunApp 

The CDR was originally developed in the NetBeans IDE against Oracle XE. This version is using the Grails in-memory H2 database. 
