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
<TITLE>Web Services Test Client</TITLE>
</HEAD>
<FRAMESET  COLS="400,*">
<FRAME SRC="Method.jsp" NAME="methods" MARGINWIDTH="1" MARGINHEIGHT="1" SCROLLING="yes" FRAMEBORDER="1">
<FRAMESET  ROWS="50%,50%">
<FRAME SRC="Input.jsp" NAME="inputs"  MARGINWIDTH="1" MARGINHEIGHT="1" SCROLLING="yes" FRAMEBORDER="1">
<%
StringBuffer resultJSP = new StringBuffer("Result.jsp");
resultJSP.append("?");
java.util.Enumeration resultEnum = request.getParameterNames();while (resultEnum.hasMoreElements()) {
Object resultObj = resultEnum.nextElement();
resultJSP.append(resultObj.toString()).append("=").append(request.getParameter(resultObj.toString())).append("&");
}
%>
<FRAME SRC="<%=resultJSP.toString()%>" NAME="result"  MARGINWIDTH="1" MARGINHEIGHT="1" SCROLLING="yes" FRAMEBORDER="1">
</FRAMESET>
<NOFRAMES>
<BODY>
The Web Services Test Client requires a browser that supports frames.
</BODY>
</NOFRAMES>
</FRAMESET>
</HTML>