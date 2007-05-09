Accada Filtering and Collection Project
=======================================

This directory contains the modules of the Accada Filtering and Collection project. The objective of the
project is to implement the Application Level Event (ALE) specification and to develop the appropriate
tools that facilitate communication with the filtering and collection instance.

This version of the Accada Filtering and Collection implements the EPCglobal ALE Specification 1.0 and
provides a Java Swing-based and a Web-based test client.

The Accada Filtering and Collection is part of the Accada Open Source RFID prototyping platform that 
implements the EPC Network specifications. At www.accada.org, there is also software available that 
implements the ALE, TDT, and EPCIS specification of EPCglobal.

For more information,  please see the README.txt in the subdirectories of the modules and the information at http://www.accada.org/fc/


Development using Eclipse
=========================

In order to work on the Accada Filtering and Collection (fc) module
using Eclipse, please follow these instructions:


1. Eclipse needs to know the path to the local maven repository. Execute
   the following command to automatically set up the corresponding
   classpath variable (M2_REPO) accordingly:

      mvn -Declipse.workspace=<path-to-eclipse-workspace> eclipse:add-maven-repo


2. In the "fc" directory, run the following command:

      mvn -Dwtpversion=1.0 eclipse:eclipse

   Please indicate the version of the Web Tools Project (WTP) you use in your
   Eclipse installation. Please note that maven does not currently support
   wtpversion=1.5. However, wtpversion=1.0 will work fine with WTP 1.5.x.


3. Import the four modules fc-commons, fc-server, fc-client, fc-webclient in
   Eclipse (from the menu bar, select File > Import > Existing Projects into Workspace).
