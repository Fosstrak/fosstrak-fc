<%@page contentType="text/html;charset=UTF-8"%><HTML>
<HEAD>
<TITLE>Methods</TITLE>
<STYLE TYPE="text/css">
	body {
		font-family: Helvetica, Arial, sans-serif;
		font-size: small
	}
</STYLE>
</HEAD>
<BODY>
<H1>Methods</H1>
<UL>
<LI><A HREF="Input.jsp?method=2" TARGET="inputs"> getEndpoint()</A></LI>
<LI><A HREF="Input.jsp?method=5" TARGET="inputs"> setEndpoint(String endPointName)</A></LI>
<LI><A HREF="Input.jsp?method=10" TARGET="inputs"> getALEServicePortType()</A></LI>
<LI><A HREF="Input.jsp?method=14" TARGET="inputs"> define(String specName, String specFilePath)</A></LI>
<LI><A HREF="Input.jsp?method=50" TARGET="inputs"> undefine(String specName)</A></LI>
<LI><A HREF="Input.jsp?method=57" TARGET="inputs"> getECSpec(String specName)</A></LI>
<LI><A HREF="Input.jsp?method=104" TARGET="inputs"> getECSpecNames()</A></LI>
<LI><A HREF="Input.jsp?method=109" TARGET="inputs"> subscribe(String specName, String notificationUri)</A></LI>
<LI><A HREF="Input.jsp?method=118" TARGET="inputs"> unsubscribe(String specName, String notificationUri)</A></LI>
<LI><A HREF="Input.jsp?method=127" TARGET="inputs"> poll(String specName)</A></LI>
<LI><A HREF="Input.jsp?method=194" TARGET="inputs"> immediate(String specFilePath)</A></LI>
<LI><A HREF="Input.jsp?method=289" TARGET="inputs"> getSubscribers(String specName)</A></LI>
<LI><A HREF="Input.jsp?method=296" TARGET="inputs"> getStandardVersion()</A></LI>
<LI><A HREF="Input.jsp?method=301" TARGET="inputs"> getVendorVersion()</A></LI>
</UL>
</BODY>
</HTML>