
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


   