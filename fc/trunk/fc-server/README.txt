Accada Filtering and Collection Server
======================================

The objective of the Accada Filtering and Collection Server module is to provide an implementation
of the EPCglobal Application Level Event (ALE) specification. In the current release ALE 1.0 is 
implemented.


How to use the Filtering and Collection Server
==============================================

Starting Filtering and Collection Server:

1. Deploy the Webservice archive file fc-server-VERSION.war of the server.
   E.g. for an Apache Tomcat installation, copy the file to the the directory
   TOMCATDIR\webapps.

2. Start the server (e.g. Tomcat).

The Filtering and Collection Server can then be accessed using the Filtering and Collection Client
or Webclient module.

3. The configuration of the server can be changed in the property files InputGenerators.properties
and LogicalReaders.xml.

For more information,  please see http://www.accada.org/fc/
