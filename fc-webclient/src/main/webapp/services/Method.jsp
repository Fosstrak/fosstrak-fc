<!--
 Copyright (C) 2007 ETH Zurich

 This file is part of Accada (www.accada.org).

 Accada is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License version 2.1, as published by the Free Software Foundation.

 Accada is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with Accada; if not, write to the Free
 Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 Boston, MA  02110-1301  USA
-->
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
<BR/>
<BR/>
LogicalReader API<BR/>
<UL>
<LI><A HREF="Input.jsp?method=305" TARGET="inputs"> define(String readerName, LRSpec spec)</A></LI>
<LI><A HREF="Input.jsp?method=306" TARGET="inputs"> undefine(String readerName)</A></LI>
<LI><A HREF="Input.jsp?method=307" TARGET="inputs"> update(String readerName, LRSpec spec)</A></LI>
<LI><A HREF="Input.jsp?method=308" TARGET="inputs"> getLogicalReaderNames()</A></LI>
<LI><A HREF="Input.jsp?method=309" TARGET="inputs"> getLRSpec(String readerName)</A></LI>
<LI><A HREF="Input.jsp?method=310" TARGET="inputs"> addReaders(String readerName, String[] readers)</A></LI>
<LI><A HREF="Input.jsp?method=311" TARGET="inputs"> setReaders(String readerName, String[] readers)</A></LI>
<LI><A HREF="Input.jsp?method=312" TARGET="inputs"> removeReaders(String readerName, String[] readers)</A></LI>
<LI><A HREF="Input.jsp?method=313" TARGET="inputs"> setProperties(String readerName, LRProperty[] properties</A></LI>
<LI><A HREF="Input.jsp?method=314" TARGET="inputs"> getPropertyValue(String readerName, String propertyName)</A></LI>
</UL>
</BODY>
</HTML>