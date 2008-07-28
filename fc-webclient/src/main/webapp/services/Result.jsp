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
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
<STYLE TYPE="text/css">
	body {
		font-family: Helvetica, Arial, sans-serif;
		font-size: small
	}
</STYLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="factory" scope="session" class="org.apache.cxf.jaxws.JaxWsProxyFactoryBean" />
<%
java.lang.String method = request.getParameter("method");
int methodID = 0;
if (method == null) { 
	methodID = -1;
}

org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType service = null;
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0) {
	factory.setServiceClass(org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType.class);
	factory.setAddress(request.getParameter("endpoint"));
	service = (org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType) factory.create();
} else if (factory.getAddress() != null) {
	service = (org.fosstrak.ale.wsdl.ale.epcglobal.ALEServicePortType) factory.create();
} else {
	methodID = -1;
}


boolean gotMethod = false;
if (methodID != -1) {
	methodID = Integer.parseInt(method);
}

try {
switch (methodID) {
// getEndpoint()
case 2:
	java.lang.String fenp = factory.getEndpointName().toString();
	java.lang.String fenpa = factory.getAddress();
	%>
	EndPointAddress: <%=fenpa %><br/>
	EndpointName: <%=fenp %>
	<%
	break;
    
// define(String specName, String specFilePath)
case 14:
	gotMethod = true;

%>
    <jsp:useBean id="Define14" scope="session" class="org.fosstrak.ale.wsdl.ale.epcglobal.Define" />
<%
	Define14.setSpec(org.fosstrak.ale.util.DeserializerUtil.deserializeECSpec(request.getParameter("specFilePath")));
	Define14.setSpecName(request.getParameter("specName"));
	org.fosstrak.ale.wsdl.ale.epcglobal.VoidHolder define14 = service.define(Define14);
%>
	<%=define14%>
<%
	
break;


// undefine(String specName)
case 50:
	gotMethod = true;
	
	org.fosstrak.ale.wsdl.ale.epcglobal.Undefine undefineSpec = new org.fosstrak.ale.wsdl.ale.epcglobal.Undefine();
	undefineSpec.setSpecName(request.getParameter("specName55"));
	org.fosstrak.ale.wsdl.ale.epcglobal.VoidHolder undefSpecRes = service.undefine(undefineSpec);
	%>
	<%=undefSpecRes %>
	<%
	break;
	     		
          

//------------------------------------------------------------------------------------------------------------------------------
// getECSpec(String)
case 57:
    gotMethod = true;
    String specName_21id=  request.getParameter("specName102");
    java.lang.String specName_21idTemp  = specName_21id;
	%>
	<jsp:useBean id="_11wsdl1ale1epcglobal1GetECSpec_20id" scope="session" class="org.fosstrak.ale.wsdl.ale.epcglobal.GetECSpec" />
	<%
	    _11wsdl1ale1epcglobal1GetECSpec_20id.setSpecName(specName_21idTemp);
	    org.fosstrak.ale.xsd.ale.epcglobal.ECSpec getECSpec57mtemp = service.getECSpec(_11wsdl1ale1epcglobal1GetECSpec_20id);
	if(getECSpec57mtemp == null){
	%>
	<%=getECSpec57mtemp%>
	<%
	}else{
	%>
	<TABLE>
	<TR>
	<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="2" ALIGN="LEFT">logicalReaders:</TD>
	<TD>
	<%
	if(getECSpec57mtemp != null){
	java.util.List<java.lang.String> typelogicalReaders60 = getECSpec57mtemp.getLogicalReaders().getLogicalReader();
	java.util.List listlogicalReaders60= java.util.Arrays.asList(typelogicalReaders60);
	String templogicalReaders60 = listlogicalReaders60.toString();
	%>
	<%=templogicalReaders60%>
	<%
	}
	%>
	</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="2" ALIGN="LEFT">boundarySpec:</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="1" ALIGN="LEFT">repeatPeriod:</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="0" ALIGN="LEFT">_value:</TD>
	<TD>
	<%
	if(getECSpec57mtemp != null){
	org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
	if(tebece0 != null){
	org.fosstrak.ale.xsd.ale.epcglobal.ECTime tebece1=tebece0.getRepeatPeriod();
	if(tebece1 != null){
	%>
	<%=tebece1.getValue()%><%
	}}}
	%>
	</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="1" ALIGN="LEFT">startTrigger:</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="0" ALIGN="LEFT">_value:</TD>
	<TD>
	<%
	if(getECSpec57mtemp != null){
	org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
	if(tebece0 != null){
	java.lang.String tebece1=tebece0.getStartTrigger();
	%>
	<%=tebece1%>
	<%
	}}
	%>
	</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="1" ALIGN="LEFT">stopTrigger:</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="0" ALIGN="LEFT">_value:</TD>
	<TD>
	<%
	if(getECSpec57mtemp != null){
	org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
	if(tebece0 != null){
	java.lang.String tebece1=tebece0.getStopTrigger();
	%>
	<%=tebece1%>
	<%
	}}
	%>
	</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="1" ALIGN="LEFT">duration:</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="0" ALIGN="LEFT">_value:</TD>
	<TD>
	<%
	if(getECSpec57mtemp != null){
	org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
	if(tebece0 != null){
	org.fosstrak.ale.xsd.ale.epcglobal.ECTime tebece1=tebece0.getDuration();
	if(tebece1 != null){
	%>
	<%=tebece1.getValue()%><%
	}}}
	%>
	</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="1" ALIGN="LEFT">stableSetInterval:</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="0" ALIGN="LEFT">_value:</TD>
	<TD>
	<%
	if(getECSpec57mtemp != null){
	org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
	if(tebece0 != null){
	org.fosstrak.ale.xsd.ale.epcglobal.ECTime tebece1=tebece0.getStableSetInterval();
	if(tebece1 != null){
	%>
	<%=tebece1.getValue()%><%
	}}}
	%>
	</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="2" ALIGN="LEFT">includeSpecInReports:</TD>
	<TD>
	<%
	if(getECSpec57mtemp != null){
	%>
	<%=getECSpec57mtemp.isIncludeSpecInReports()%><%
	}
	%>
	</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="2" ALIGN="LEFT">reportSpecs:</TD>
	<TD>
	<%
	if(getECSpec57mtemp != null){
	java.util.List<org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec> typereportSpecs92 = getECSpec57mtemp.getReportSpecs().getReportSpec();
	java.util.List listreportSpecs92= java.util.Arrays.asList(typereportSpecs92);
	String tempreportSpecs92 = listreportSpecs92.toString();
	%>
	<%=tempreportSpecs92%>
	<%
	}
	%>
	</TD>
	<TR>
	<TD WIDTH="5%"></TD>
	<TD COLSPAN="2" ALIGN="LEFT">extension:</TD>
	</TABLE>
	<%
	}
break;
//------------------------------------------------------------------------------------------------------------------------------
// getECSpecNames
case 104:
    gotMethod = true;
%>
    <jsp:useBean id="_11wsdl1ale1epcglobal1EmptyParms_22id" scope="session" class="org.fosstrak.ale.wsdl.ale.epcglobal.EmptyParms" />
    <%
         org.fosstrak.ale.wsdl.ale.epcglobal.ArrayOfString getECSpecNames104mtemp = service.getECSpecNames(_11wsdl1ale1epcglobal1EmptyParms_22id);
     		if (getECSpecNames104mtemp == null) {
     			%>
     		<%=getECSpecNames104mtemp%>
     		<%
     			} else {
     				String specs = getECSpecNames104mtemp.getString().toString();
     				%>
     		<%=specs%>
     		<%
     			}
     		break;
// subscribe(String specName, String url)
case 109:
	org.fosstrak.ale.wsdl.ale.epcglobal.Subscribe subscribe = new org.fosstrak.ale.wsdl.ale.epcglobal.Subscribe();
	subscribe.setNotificationURI(request.getParameter("notificationURI114"));
	subscribe.setSpecName(request.getParameter("specName116"));
	org.fosstrak.ale.wsdl.ale.epcglobal.VoidHolder subscribeRes = service.subscribe(subscribe);
	%>
	<%=subscribeRes %>
	<%
	break;
	
	
// unsubscribe(String specName, String url)
case 118:
	gotMethod = true;
	org.fosstrak.ale.wsdl.ale.epcglobal.Unsubscribe unsubscribe = new org.fosstrak.ale.wsdl.ale.epcglobal.Unsubscribe();
	unsubscribe.setNotificationURI(request.getParameter("notificationURI123"));
	unsubscribe.setSpecName(request.getParameter("specName125"));
	org.fosstrak.ale.wsdl.ale.epcglobal.VoidHolder unsubscribeRes = service.unsubscribe(unsubscribe);
	%>
	<%=unsubscribeRes %>
	<%
	break;
     		
     		
// poll(String specName)
case 127:
	gotMethod = true;
	org.fosstrak.ale.wsdl.ale.epcglobal.Poll poll = new org.fosstrak.ale.wsdl.ale.epcglobal.Poll();
	poll.setSpecName(request.getParameter("specName192"));
	org.fosstrak.ale.xsd.ale.epcglobal.ECReports pollResult = service.poll(poll);
	
	if (pollResult != null) {
		java.io.CharArrayWriter pollwriter = new java.io.CharArrayWriter();
		org.fosstrak.ale.util.SerializerUtil.serializeECReports(pollResult, pollwriter);
		java.lang.String pollResultStr = pollwriter.toString();
		%>
 		<%=pollResultStr%>
 		<%				
	} else {
		%>
 		<%=pollResult%>
 		<%		
	}
	break;
	     		
     		
// immediate(String specFilePath);
case 194:
	gotMethod = true;
	org.fosstrak.ale.wsdl.ale.epcglobal.Immediate immediate = new org.fosstrak.ale.wsdl.ale.epcglobal.Immediate();
	immediate.setSpec(org.fosstrak.ale.util.DeserializerUtil.deserializeECSpec(request.getParameter("specFilePath")));
	org.fosstrak.ale.xsd.ale.epcglobal.ECReports immediateResult = service.immediate(immediate);
	
	if (immediateResult != null) {
		java.io.CharArrayWriter writer = new java.io.CharArrayWriter();
		org.fosstrak.ale.util.SerializerUtil.serializeECReports(immediateResult, writer);
		java.lang.String immediateResultStr = writer.toString();
		%>
 		<%=immediateResultStr%>
 		<%				
	} else {
		%>
 		<%=immediateResult%>
 		<%		
	}
	break;
	     		
     		
// getSubscribers(String specName)
case 289:
	gotMethod = true;
	org.fosstrak.ale.wsdl.ale.epcglobal.GetSubscribers getSubscribers = new org.fosstrak.ale.wsdl.ale.epcglobal.GetSubscribers();
	getSubscribers.setSpecName(request.getParameter("specName294"));
	org.fosstrak.ale.wsdl.ale.epcglobal.ArrayOfString getSubscribersRes = service.getSubscribers(getSubscribers);
	if (getSubscribersRes == null) {
	%>
	<%=getSubscribersRes%>
	<%
	} else {
		String theSubscribers = getSubscribersRes.getString().toString();
		%>
 		<%=theSubscribers%>
 		<%
	}
	break;
	
//------------------------------------------------------------------------------------------------------------------------------
// getStandardVersion    
case 296:
    gotMethod = true;
%>
<jsp:useBean id="_11wsdl1ale1epcglobal1EmptyParms_49id" scope="session" class="org.fosstrak.ale.wsdl.ale.epcglobal.EmptyParms" />
<%
java.lang.String getStandardVersion296mtemp = service.getStandardVersion(_11wsdl1ale1epcglobal1EmptyParms_49id);
if(getStandardVersion296mtemp == null){
%>
<%=getStandardVersion296mtemp%>
<%
}else{
String tempResultreturnp297 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getStandardVersion296mtemp));
%>
<%=tempResultreturnp297%>
<%
}
break;

// getVendorVersion
case 301:
    gotMethod = true;
%>
<jsp:useBean id="_11wsdl1ale1epcglobal1EmptyParms_50id" scope="session" class="org.fosstrak.ale.wsdl.ale.epcglobal.EmptyParms" />
<%
java.lang.String getVendorVersion301mtemp = service.getVendorVersion(_11wsdl1ale1epcglobal1EmptyParms_50id);
if(getVendorVersion301mtemp == null){
%>
<%=getVendorVersion301mtemp %>
<%
}else{
String tempResultreturnp302 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getVendorVersion301mtemp));
%>
<%= tempResultreturnp302 %>
<%
}
break;
	
}
} catch (Exception e) {
%>
exception: <%=e%>
<%
return;
}

if (!gotMethod) {
%>
<%
}
%>
<%@include file="lrResult.jsp" %>
</BODY>
</HTML>