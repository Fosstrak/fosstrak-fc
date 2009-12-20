Fosstrak Filtering and Collection Client
======================================

The objective of the Fosstrak Filtering and Collection Client module is to provide a 
Java Swing based test client to execute Application Level Event specification (ALE)
commands on a reader or component that implements the ALE specification.


How to use the Filtering and Collection Client
==============================================

Starting Filtering and Collection Client:

1. Unpack fc-client-VERSION-bin-with-deps.zip.

2. Optionally change the properties files client.properties
   to set the endpoint of the ALE webservice to which you want to connect
   and the log4j.properties.

3. Start the client: java -jar fc-client-VERSION.jar

4. Start the reader client EventSinkUI to receive and display reports:
java -cp reader-rp-client-VERSION.jar org.fosstrak.reader.rp.client.EventSinkUI [port]


For more information,  please see http://www.fosstrak.org/fc
