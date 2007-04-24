Accada Filtering and Collection Webclient
=========================================

The objective of the Accada Filtering and Collection Webclient module is to provide 
a Web-based test client to execute Application Level Event specification (ALE)
commands on a reader or component that implements the ALE specification.


How to use the Filtering and Collection Webclient
=================================================

1. Deploy the Webservice archive file fc-webclient-VERSION.war of the Webclient.
   E.g. for an Apache Tomcat installation, copy the file to the the directory
   TOMCATDIR\webapps.

2. Start the server (e.g. Tomcat).

3. Open the Webclient using the Webclient's endpoint
   (e.g. http://localhost:8080/fc-webclient-VERSION/services/ALEWebClient.jsp)

4. Use the command SetEndPoint to set the address of the ALE
   (e.g. http://localhost:8080/fc-server-VERSION/services/ALEServicePort)


For more information,  please see http://www.accada.org/fc
