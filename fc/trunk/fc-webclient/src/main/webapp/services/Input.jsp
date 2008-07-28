<!--
 Copyright (C) 2007 ETH Zurich

 This file is part of Fosstrak (www.fosstrak.org).

 Fosstrak is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License version 2.1, as published by the Free Software Foundation.

 Fosstrak is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with Fosstrak; if not, write to the Free
 Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 Boston, MA  02110-1301  USA
-->
<%@page contentType="text/html;charset=UTF-8"%>
<HTML>
<HEAD>
<TITLE>Inputs</TITLE>
<STYLE TYPE="text/css">
	body {
		font-family: Helvetica, Arial, sans-serif;
		font-size: small
	}
</STYLE>
</HEAD>
<BODY>
<H1>Inputs</H1>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

boolean valid = true;

if(methodID != -1) methodID = Integer.parseInt(method);
switch (methodID){ 
case 2:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 5:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">endpoint:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="endpoint" style="width:450px;"><br/>
example: http://localhost:8080/fc-server-0.3.0-SNAPSHOT/services/ALEService</TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 10:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 14:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">parms:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specName:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName" SIZE=50></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specFilePath:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specFilePath" SIZE=50></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 50:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">parms:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specName:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName55" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 57:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">parms:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specName:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName102" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 104:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">parms:</TD>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 109:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">parms:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">notificationURI:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="notificationURI114" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specName:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName116" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 118:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">parms:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">notificationURI:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="notificationURI123" SIZE=20></TD>
</TR>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specName:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName125" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 127:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">parms:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specName:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName192" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 194:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">parms:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specFilePath:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specFilePath" SIZE=50></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 289:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">parms:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specName:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName294" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 296:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">parms:</TD>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 301:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">parms:</TD>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;


// sawielan
case 317:
	valid = false;
	%>
	<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
	<INPUT TYPE="HIDDEN" NAME="lrmethod" VALUE="<%=method%>">
	<TABLE>
	<TR>
	<TD COLSPAN="1" ALIGN="LEFT">endpoint:</TD>
	<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="lrendpoint" style="width:450px;"><br/>
	example: http://localhost:8080/fc-server-0.3.0-SNAPSHOT/services/ALELRService
	</TD>
	</TR>
	</TABLE>
	<BR>
	<INPUT TYPE="SUBMIT" VALUE="Invoke">
	<INPUT TYPE="RESET" VALUE="Clear">
	</FORM>
	<%
	break;

// define(String name, LRSpec spec)
// update(String name, LRSpec spec)
case 305:
case 307:
valid = false;
%>
<form method="post" action="Result.jsp" target="result">
<input type="hidden" name="lrmethod" value="<%=method%>">
<table border="0">
	<tr>
		<td colspan="3" align="left">params:</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td colspan="2" align="left">readerName:</td>
		<td align="left"><input type="text" name="readerName" size="50"></td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td colspan="2" align="left">specFilePath:</td>
		<td align="left"><input type="text" name="specFilePath" size="50"></td>
	</tr>
</table>
<br>
<input type="submit" value="Invoke">
<input type="reset" value="Clear">
</form>
<%
break;

// undefine(String name)
// getLRSpec (String name)
case 306:
case 309:
valid = false;
%>
<form method="post" action="Result.jsp" target="result">
<input type="hidden" name="lrmethod" value="<%=method%>">
<table border="0">
	<tr>
		<td colspan="3" align="left">params:</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td colspan="2" align="left">readerName:</td>
		<td align="left"><input type="text" name="readerName" size="50"></td>
	</tr>
</table>
<br>
<input type="submit" value="Invoke">
<input type="reset" value="Clear">
</form>
<%
break;

// getLogicalReaderNames()
case 308:
valid = false;
%>
<form method="post" action="Result.jsp" target="result">
<input type="hidden" name="lrmethod" value="<%=method%>">
<input type="submit" value="Invoke">
<input type="reset" value="Clear">
</form>
<%

break;

case 310:
case 311:
case 312:
valid = false;
%>
<form method="post" action="Result.jsp" target="result">
<input type="hidden" name="lrmethod" value="<%=method%>">
<table border="0">
	<tr>
		<td colspan="3" align="left">params:</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td colspan="2" align="left">readerName:</td>
		<td align="left"><input type="text" name="readerName" size="50"></td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td colspan="2" align="left">readersArray filePath:</td>
		<td align="left"><input type="text" name="filePath" size="50"></td>
	</tr>
</table>
<br>
<input type="submit" value="Invoke">
<input type="reset" value="Clear">
</form>
<%
break;


case 313:
valid = false;
%>
<form method="post" action="Result.jsp" target="result">
<input type="hidden" name="lrmethod" value="<%=method%>">
<table border="0">
	<tr>
		<td colspan="3" align="left">params:</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td colspan="2" align="left">readerName:</td>
		<td align="left"><input type="text" name="readerName" size="50"></td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td colspan="2" align="left">LRPropertiesArray filePath:</td>
		<td align="left"><input type="text" name="filePath" size="50"></td>
	</tr>
</table>
<br>
<input type="submit" value="Invoke">
<input type="reset" value="Clear">
</form>
<%
break;

case 314:
valid = false;
%>
<form method="post" action="Result.jsp" target="result">
<input type="hidden" name="lrmethod" value="<%=method%>">
<table border="0">
	<tr>
		<td colspan="3" align="left">params:</td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td colspan="2" align="left">readerName:</td>
		<td align="left"><input type="text" name="readerName" size="50"></td>
	</tr>
	<tr>
		<td width="5%"></td>
		<td colspan="2" align="left">propertyName:</td>
		<td align="left"><input type="text" name="propertyName" size="50"></td>
	</tr>
</table>
<br>
<input type="submit" value="Invoke">
<input type="reset" value="Clear">
</form>
<%
break;

case 315:
	valid = false;
	%>
	<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
	<INPUT TYPE="HIDDEN" NAME="lrmethod" VALUE="<%=method%>">
	<TABLE>
	<TR>
	<TD COLSPAN="1" ALIGN="LEFT">parms:</TD>
	</TABLE>
	<BR>
	<INPUT TYPE="SUBMIT" VALUE="Invoke">
	<INPUT TYPE="RESET" VALUE="Clear">
	</FORM>
	<%
	break;
	case 316:
	valid = false;
	%>
	<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
	<INPUT TYPE="HIDDEN" NAME="lrmethod" VALUE="<%=method%>">
	<TABLE>
	<TR>
	<TD COLSPAN="1" ALIGN="LEFT">parms:</TD>
	</TABLE>
	<BR>
	<INPUT TYPE="SUBMIT" VALUE="Invoke">
	<INPUT TYPE="RESET" VALUE="Clear">
	</FORM>
	<%
	break;
	case 318:
		valid = false;
		%>
		<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
		<INPUT TYPE="HIDDEN" NAME="lrmethod" VALUE="<%=method%>">
		<TABLE>
		<TR>
		<TD COLSPAN="1" ALIGN="LEFT">parms:</TD>
		</TABLE>
		<BR>
		<INPUT TYPE="SUBMIT" VALUE="Invoke">
		<INPUT TYPE="RESET" VALUE="Clear">
		</FORM>
		<%
		break;



case 1111111111:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<TABLE>
<TR>
<TD COLSPAN="1" ALIGN="LEFT">URLString:</TD>
<TD ALIGN="left"><INPUT TYPE="TEXT" NAME="url1111111111" SIZE=20></TD>
</TR>
</TABLE>
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
case 1111111112:
valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
<BR>
<INPUT TYPE="SUBMIT" VALUE="Invoke">
<INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
break;
}
if (valid) {
%>
Select a method to test.
<%
    return;
}
%>

</BODY>
</HTML>
