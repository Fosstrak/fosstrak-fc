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

<jsp:useBean id="sampleALEServicePortTypeProxyid" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.ALEServicePortTypeProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
sampleALEServicePortTypeProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = sampleALEServicePortTypeProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp%>
<%
        }else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
%>
        <%=tempResultreturnp3%>
        <%
                }
                break;
                case 5:
                        gotMethod = true;
                        String endpoint_0id=  request.getParameter("endpoint8");
                        java.lang.String endpoint_0idTemp  = endpoint_0id;
                        sampleALEServicePortTypeProxyid.setEndpoint(endpoint_0idTemp);
                break;
                case 10:
                        gotMethod = true;
                        org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType getALEServicePortType10mtemp = sampleALEServicePortTypeProxyid.getALEServicePortType();
                if(getALEServicePortType10mtemp == null){
        %>
<%=getALEServicePortType10mtemp%>
<%
        }else{
        if(getALEServicePortType10mtemp!= null){
        String tempreturnp11 = getALEServicePortType10mtemp.toString();
%>
        <%=tempreturnp11%>
        <%
                }}
                break;
                case 13:
                        gotMethod = true;
                        String _value_5id=  request.getParameter("_value24");
                        long _value_5idTemp  = Long.parseLong(_value_5id);
        %>
        <jsp:useBean id="_11xsd1ale1epcglobal1ECTime_4id" scope="session" class="org.accada.ale.xsd.ale.epcglobal.ECTime" />
        <%
                                                _11xsd1ale1epcglobal1ECTime_4id.set_value(_value_5idTemp);
                                                String _value_7id=  request.getParameter("_value28");
                                                java.lang.String _value_7idTemp  = _value_7id;
        %>
        <jsp:useBean id="_11xsd1ale1epcglobal1ECTrigger_6id" scope="session" class="org.accada.ale.xsd.ale.epcglobal.ECTrigger" />
        <%
                                                _11xsd1ale1epcglobal1ECTrigger_6id.set_value(_value_7idTemp);
                                                String _value_9id=  request.getParameter("_value32");
                                                java.lang.String _value_9idTemp  = _value_9id;
        %>
        <jsp:useBean id="_11xsd1ale1epcglobal1ECTrigger_8id" scope="session" class="org.accada.ale.xsd.ale.epcglobal.ECTrigger" />
        <%
                                                _11xsd1ale1epcglobal1ECTrigger_8id.set_value(_value_9idTemp);
                                                String _value_11id=  request.getParameter("_value36");
                                                long _value_11idTemp  = Long.parseLong(_value_11id);
        %>
        <jsp:useBean id="_11xsd1ale1epcglobal1ECTime_10id" scope="session" class="org.accada.ale.xsd.ale.epcglobal.ECTime" />
        <%
        _11xsd1ale1epcglobal1ECTime_10id.set_value(_value_11idTemp);
        %>
        <jsp:useBean id="_11xsd1ale1epcglobal1ECBoundarySpecExtension_12id" scope="session" class="org.accada.ale.xsd.ale.epcglobal.ECBoundarySpecExtension" />
        <%
                                                String _value_14id=  request.getParameter("_value42");
                                                long _value_14idTemp  = Long.parseLong(_value_14id);
        %>
        <jsp:useBean id="_11xsd1ale1epcglobal1ECTime_13id" scope="session" class="org.accada.ale.xsd.ale.epcglobal.ECTime" />
        <%
        _11xsd1ale1epcglobal1ECTime_13id.set_value(_value_14idTemp);
        %>
        <jsp:useBean id="_11xsd1ale1epcglobal1ECBoundarySpec_3id" scope="session" class="org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec" />
        <%
                                                _11xsd1ale1epcglobal1ECBoundarySpec_3id.setRepeatPeriod(_11xsd1ale1epcglobal1ECTime_4id);
                                                _11xsd1ale1epcglobal1ECBoundarySpec_3id.setStartTrigger(_11xsd1ale1epcglobal1ECTrigger_6id);
                                                _11xsd1ale1epcglobal1ECBoundarySpec_3id.setStopTrigger(_11xsd1ale1epcglobal1ECTrigger_8id);
                                                _11xsd1ale1epcglobal1ECBoundarySpec_3id.setDuration(_11xsd1ale1epcglobal1ECTime_10id);
                                                _11xsd1ale1epcglobal1ECBoundarySpec_3id.setExtension(_11xsd1ale1epcglobal1ECBoundarySpecExtension_12id);
                                                _11xsd1ale1epcglobal1ECBoundarySpec_3id.setStableSetInterval(_11xsd1ale1epcglobal1ECTime_13id);
                                                String includeSpecInReports_15id=  request.getParameter("includeSpecInReports44");
                                                boolean includeSpecInReports_15idTemp  = Boolean.valueOf(includeSpecInReports_15id).booleanValue();
        %>
        <jsp:useBean id="_11xsd1ale1epcglobal1ECSpecExtension_16id" scope="session" class="org.accada.ale.xsd.ale.epcglobal.ECSpecExtension" />
        <%

        %>
        <jsp:useBean id="_11xsd1ale1epcglobal1ECSpec_2id" scope="session" class="org.accada.ale.xsd.ale.epcglobal.ECSpec" />
        <%
                                                _11xsd1ale1epcglobal1ECSpec_2id.setBoundarySpec(_11xsd1ale1epcglobal1ECBoundarySpec_3id);
                                                _11xsd1ale1epcglobal1ECSpec_2id.setIncludeSpecInReports(includeSpecInReports_15idTemp);
                                                _11xsd1ale1epcglobal1ECSpec_2id.setExtension(_11xsd1ale1epcglobal1ECSpecExtension_16id);
                                                String specName_17id=  request.getParameter("specName48");
                                                java.lang.String specName_17idTemp  = specName_17id;
        %>
        <jsp:useBean id="_11wsdl1ale1epcglobal1Define_1id" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.Define" />
        <%
                                                _11wsdl1ale1epcglobal1Define_1id.setSpec(_11xsd1ale1epcglobal1ECSpec_2id);
                                                _11wsdl1ale1epcglobal1Define_1id.setSpecName(specName_17idTemp);
                                                org.accada.ale.wsdl.ale.epcglobal.VoidHolder define13mtemp = sampleALEServicePortTypeProxyid.define(_11wsdl1ale1epcglobal1Define_1id);
                                        if(define13mtemp == null){
        %>
<%=define13mtemp%>
<%
        }else{
        if(define13mtemp!= null){
        String tempreturnp14 = define13mtemp.toString();
%>
        <%=tempreturnp14%>
        <%
                                        }}
                                        break;
//
// case 14 modified by regli
//

case 14:
	gotMethod = true;
	String specName =  request.getParameter("specName");
	String specFilePath =  request.getParameter("specFilePath");
%>
    <jsp:useBean id="Define14" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.Define" />
<%
	Define14.setSpecName(specName);
	Define14.setSpec(org.accada.ale.util.DeserializerUtil.deserializeECSpec(specFilePath));
	org.accada.ale.wsdl.ale.epcglobal.VoidHolder define14 = sampleALEServicePortTypeProxyid.define(Define14);
	if(define14 == null){
%>
		<%=define14%>
<%
	}else{
		if(define14!= null){
		    String tempreturnp14 = define14.toString();
%>
    		<%=tempreturnp14%>
<%
		}
	}
break;
                   	
                    case 50:
                            gotMethod = true;
                            String specName_19id=  request.getParameter("specName55");
                            java.lang.String specName_19idTemp  = specName_19id;
    %>
        <jsp:useBean id="_11wsdl1ale1epcglobal1Undefine_18id" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.Undefine" />
        <%
                                                _11wsdl1ale1epcglobal1Undefine_18id.setSpecName(specName_19idTemp);
                                                org.accada.ale.wsdl.ale.epcglobal.VoidHolder undefine50mtemp = sampleALEServicePortTypeProxyid.undefine(_11wsdl1ale1epcglobal1Undefine_18id);
                                        if(undefine50mtemp == null){
        %>
<%=undefine50mtemp%>
<%
        }else{
        if(undefine50mtemp!= null){
        String tempreturnp51 = undefine50mtemp.toString();
%>
        <%=tempreturnp51%>
        <%
                                        }}
                                        break;
                                        case 57:
                                                gotMethod = true;
                                                String specName_21id=  request.getParameter("specName102");
                                                java.lang.String specName_21idTemp  = specName_21id;
        %>
        <jsp:useBean id="_11wsdl1ale1epcglobal1GetECSpec_20id" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.GetECSpec" />
        <%
                                                _11wsdl1ale1epcglobal1GetECSpec_20id.setSpecName(specName_21idTemp);
                                                org.accada.ale.xsd.ale.epcglobal.ECSpec getECSpec57mtemp = sampleALEServicePortTypeProxyid.getECSpec(_11wsdl1ale1epcglobal1GetECSpec_20id);
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
java.lang.String[] typelogicalReaders60 = getECSpec57mtemp.getLogicalReaders();
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
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECTime tebece1=tebece0.getRepeatPeriod();
if(tebece1 != null){
%>
<%=tebece1.get_value()%><%
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
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECTrigger tebece1=tebece0.getStartTrigger();
if(tebece1 != null){
java.lang.String type_value70 = tebece1.get_value();
        String tempResult_value70 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(type_value70));
%>
        <%=tempResult_value70%>
        <%
        }}}
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
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECTrigger tebece1=tebece0.getStopTrigger();
if(tebece1 != null){
java.lang.String type_value74 = tebece1.get_value();
        String tempResult_value74 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(type_value74));
%>
        <%=tempResult_value74%>
        <%
        }}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(getECSpec57mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
if(tebece0 != null){
org.apache.axis.message.MessageElement[] type_any76 = tebece0.get_any();
        java.util.List list_any76= java.util.Arrays.asList(type_any76);
        String temp_any76 = list_any76.toString();
%>
        <%=temp_any76%>
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
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECTime tebece1=tebece0.getDuration();
if(tebece1 != null){
%>
<%=tebece1.get_value()%><%
}}}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">extension:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(getECSpec57mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpecExtension tebece1=tebece0.getExtension();
if(tebece1 != null){
org.apache.axis.message.MessageElement[] type_any84 = tebece1.get_any();
        java.util.List list_any84= java.util.Arrays.asList(type_any84);
        String temp_any84 = list_any84.toString();
%>
        <%=temp_any84%>
        <%
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
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece0=getECSpec57mtemp.getBoundarySpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECTime tebece1=tebece0.getStableSetInterval();
if(tebece1 != null){
%>
<%=tebece1.get_value()%><%
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
org.accada.ale.xsd.ale.epcglobal.ECReportSpec[] typereportSpecs92 = getECSpec57mtemp.getReportSpecs();
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
<TD COLSPAN="2" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(getECSpec57mtemp != null){
org.apache.axis.message.MessageElement[] type_any94 = getECSpec57mtemp.get_any();
        java.util.List list_any94= java.util.Arrays.asList(type_any94);
        String temp_any94 = list_any94.toString();
%>
        <%=temp_any94%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">extension:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(getECSpec57mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpecExtension tebece0=getECSpec57mtemp.getExtension();
if(tebece0 != null){
org.apache.axis.message.MessageElement[] type_any98 = tebece0.get_any();
        java.util.List list_any98= java.util.Arrays.asList(type_any98);
        String temp_any98 = list_any98.toString();
%>
        <%=temp_any98%>
        <%
        }}
        %>
</TD>
</TABLE>
<%
}
break;
case 104:
        gotMethod = true;
%>
        <jsp:useBean id="_11wsdl1ale1epcglobal1EmptyParms_22id" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.EmptyParms" />
        <%
                                        java.lang.String[] getECSpecNames104mtemp = sampleALEServicePortTypeProxyid.getECSpecNames(_11wsdl1ale1epcglobal1EmptyParms_22id);
                                        if(getECSpecNames104mtemp == null){
        %>
<%=getECSpecNames104mtemp%>
<%
        }else{
        java.util.List listreturnp105= java.util.Arrays.asList(getECSpecNames104mtemp);
        String tempreturnp105 = listreturnp105.toString();
%>
        <%=tempreturnp105%>
        <%
                                        }
                                        break;
                                        case 109:
                                                gotMethod = true;
                                                String notificationURI_24id=  request.getParameter("notificationURI114");
                                                java.lang.String notificationURI_24idTemp  = notificationURI_24id;
                                                String specName_25id=  request.getParameter("specName116");
                                                java.lang.String specName_25idTemp  = specName_25id;
        %>
        <jsp:useBean id="_11wsdl1ale1epcglobal1Subscribe_23id" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.Subscribe" />
        <%
                                                _11wsdl1ale1epcglobal1Subscribe_23id.setNotificationURI(notificationURI_24idTemp);
                                                _11wsdl1ale1epcglobal1Subscribe_23id.setSpecName(specName_25idTemp);
                                                org.accada.ale.wsdl.ale.epcglobal.VoidHolder subscribe109mtemp = sampleALEServicePortTypeProxyid.subscribe(_11wsdl1ale1epcglobal1Subscribe_23id);
                                        if(subscribe109mtemp == null){
        %>
<%=subscribe109mtemp%>
<%
        }else{
        if(subscribe109mtemp!= null){
        String tempreturnp110 = subscribe109mtemp.toString();
%>
        <%=tempreturnp110%>
        <%
                                        }}
                                        break;
                                        case 118:
                                                gotMethod = true;
                                                String notificationURI_27id=  request.getParameter("notificationURI123");
                                                java.lang.String notificationURI_27idTemp  = notificationURI_27id;
                                                String specName_28id=  request.getParameter("specName125");
                                                java.lang.String specName_28idTemp  = specName_28id;
        %>
        <jsp:useBean id="_11wsdl1ale1epcglobal1Unsubscribe_26id" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.Unsubscribe" />
        <%
                                                _11wsdl1ale1epcglobal1Unsubscribe_26id.setNotificationURI(notificationURI_27idTemp);
                                                _11wsdl1ale1epcglobal1Unsubscribe_26id.setSpecName(specName_28idTemp);
                                                org.accada.ale.wsdl.ale.epcglobal.VoidHolder unsubscribe118mtemp = sampleALEServicePortTypeProxyid.unsubscribe(_11wsdl1ale1epcglobal1Unsubscribe_26id);
                                        if(unsubscribe118mtemp == null){
        %>
<%=unsubscribe118mtemp%>
<%
        }else{
        if(unsubscribe118mtemp!= null){
        String tempreturnp119 = unsubscribe118mtemp.toString();
%>
        <%=tempreturnp119%>
        <%
                                        }}
                                        break;
                                        case 127:
                                                gotMethod = true;
                                                String specName_30id=  request.getParameter("specName192");
                                                java.lang.String specName_30idTemp  = specName_30id;
        %>
        <jsp:useBean id="_11wsdl1ale1epcglobal1Poll_29id" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.Poll" />
        <%
                                                _11wsdl1ale1epcglobal1Poll_29id.setSpecName(specName_30idTemp);
                                                org.accada.ale.xsd.ale.epcglobal.ECReports poll127mtemp = sampleALEServicePortTypeProxyid.poll(_11wsdl1ale1epcglobal1Poll_29id);
                                        if(poll127mtemp == null){
        %>
<%=poll127mtemp%>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specName:</TD>
<TD>
<%
if(poll127mtemp != null){
java.lang.String typespecName130 = poll127mtemp.getSpecName();
        String tempResultspecName130 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typespecName130));
%>
        <%=tempResultspecName130%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">schemaURL:</TD>
<TD>
<%
if(poll127mtemp != null){
java.lang.String typeschemaURL132 = poll127mtemp.getSchemaURL();
        String tempResultschemaURL132 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeschemaURL132));
%>
        <%=tempResultschemaURL132%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">date:</TD>
<TD>
<%
if(poll127mtemp != null){
java.util.Calendar typedate134 = poll127mtemp.getDate();
        java.text.DateFormat dateFormatdate134 = java.text.DateFormat.getDateInstance();
        java.util.Date datedate134 = typedate134.getTime();
        String tempResultdate134 = org.eclipse.jst.ws.util.JspUtils.markup(dateFormatdate134.format(datedate134));
%>
        <%=tempResultdate134%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">aLEID:</TD>
<TD>
<%
if(poll127mtemp != null){
java.lang.String typeaLEID136 = poll127mtemp.getALEID();
        String tempResultaLEID136 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeaLEID136));
%>
        <%=tempResultaLEID136%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">eCSpec:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">logicalReaders:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
java.lang.String[] typelogicalReaders140 = tebece0.getLogicalReaders();
        java.util.List listlogicalReaders140= java.util.Arrays.asList(typelogicalReaders140);
        String templogicalReaders140 = listlogicalReaders140.toString();
%>
        <%=templogicalReaders140%>
        <%
        }}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">boundarySpec:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">repeatPeriod:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_value:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECTime tebece2=tebece1.getRepeatPeriod();
if(tebece2 != null){
%>
<%=tebece2.get_value()%><%
}}}}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">startTrigger:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_value:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECTrigger tebece2=tebece1.getStartTrigger();
if(tebece2 != null){
java.lang.String type_value150 = tebece2.get_value();
        String tempResult_value150 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(type_value150));
%>
        <%=tempResult_value150%>
        <%
        }}}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">stopTrigger:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_value:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECTrigger tebece2=tebece1.getStopTrigger();
if(tebece2 != null){
java.lang.String type_value154 = tebece2.get_value();
        String tempResult_value154 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(type_value154));
%>
        <%=tempResult_value154%>
        <%
        }}}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.apache.axis.message.MessageElement[] type_any156 = tebece1.get_any();
        java.util.List list_any156= java.util.Arrays.asList(type_any156);
        String temp_any156 = list_any156.toString();
%>
        <%=temp_any156%>
        <%
        }}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">duration:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_value:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECTime tebece2=tebece1.getDuration();
if(tebece2 != null){
%>
<%=tebece2.get_value()%><%
}}}}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">extension:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpecExtension tebece2=tebece1.getExtension();
if(tebece2 != null){
org.apache.axis.message.MessageElement[] type_any164 = tebece2.get_any();
        java.util.List list_any164= java.util.Arrays.asList(type_any164);
        String temp_any164 = list_any164.toString();
%>
        <%=temp_any164%>
        <%
        }}}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">stableSetInterval:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_value:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECTime tebece2=tebece1.getStableSetInterval();
if(tebece2 != null){
%>
<%=tebece2.get_value()%><%
}}}}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">includeSpecInReports:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
%>
<%=tebece0.isIncludeSpecInReports()%><%
}}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">reportSpecs:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECReportSpec[] typereportSpecs172 = tebece0.getReportSpecs();
        java.util.List listreportSpecs172= java.util.Arrays.asList(typereportSpecs172);
        String tempreportSpecs172 = listreportSpecs172.toString();
%>
        <%=tempreportSpecs172%>
        <%
        }}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
org.apache.axis.message.MessageElement[] type_any174 = tebece0.get_any();
        java.util.List list_any174= java.util.Arrays.asList(type_any174);
        String temp_any174 = list_any174.toString();
%>
        <%=temp_any174%>
        <%
        }}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">extension:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=poll127mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECSpecExtension tebece1=tebece0.getExtension();
if(tebece1 != null){
org.apache.axis.message.MessageElement[] type_any178 = tebece1.get_any();
        java.util.List list_any178= java.util.Arrays.asList(type_any178);
        String temp_any178 = list_any178.toString();
%>
        <%=temp_any178%>
        <%
        }}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">reports:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECReport[] typereports180 = poll127mtemp.getReports();
        java.util.List listreports180= java.util.Arrays.asList(typereports180);
        String tempreports180 = listreports180.toString();
%>
        <%=tempreports180%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">totalMilliseconds:</TD>
<TD>
<%
if(poll127mtemp != null){
%>
<%=poll127mtemp.getTotalMilliseconds()%><%
}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(poll127mtemp != null){
org.apache.axis.message.MessageElement[] type_any184 = poll127mtemp.get_any();
        java.util.List list_any184= java.util.Arrays.asList(type_any184);
        String temp_any184 = list_any184.toString();
%>
        <%=temp_any184%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">extension:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(poll127mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECReportsExtension tebece0=poll127mtemp.getExtension();
if(tebece0 != null){
org.apache.axis.message.MessageElement[] type_any188 = tebece0.get_any();
        java.util.List list_any188= java.util.Arrays.asList(type_any188);
        String temp_any188 = list_any188.toString();
%>
        <%=temp_any188%>
        <%
        }}
        %>
</TD>
</TABLE>
<%
}
break;

//
// case 194 modified by regli
//

case 194:
	gotMethod = true;
    specFilePath =  request.getParameter("specFilePath");
%>
	<jsp:useBean id="Immediate194" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.Immediate" />
<%
	Immediate194.setSpec(org.accada.ale.util.DeserializerUtil.deserializeECSpec(specFilePath));
	org.accada.ale.xsd.ale.epcglobal.ECReports immediate194mtemp = sampleALEServicePortTypeProxyid.immediate(Immediate194);
    if(immediate194mtemp == null){
        %>
<%=immediate194mtemp%>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">specName:</TD>
<TD>
<%
if(immediate194mtemp != null){
java.lang.String typespecName197 = immediate194mtemp.getSpecName();
        String tempResultspecName197 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typespecName197));
%>
        <%=tempResultspecName197%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">schemaURL:</TD>
<TD>
<%
if(immediate194mtemp != null){
java.lang.String typeschemaURL199 = immediate194mtemp.getSchemaURL();
        String tempResultschemaURL199 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeschemaURL199));
%>
        <%=tempResultschemaURL199%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">date:</TD>
<TD>
<%
if(immediate194mtemp != null){
java.util.Calendar typedate201 = immediate194mtemp.getDate();
        java.text.DateFormat dateFormatdate201 = java.text.DateFormat.getDateInstance();
        java.util.Date datedate201 = typedate201.getTime();
        String tempResultdate201 = org.eclipse.jst.ws.util.JspUtils.markup(dateFormatdate201.format(datedate201));
%>
        <%=tempResultdate201%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">aLEID:</TD>
<TD>
<%
if(immediate194mtemp != null){
java.lang.String typeaLEID203 = immediate194mtemp.getALEID();
        String tempResultaLEID203 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeaLEID203));
%>
        <%=tempResultaLEID203%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">eCSpec:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">logicalReaders:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
java.lang.String[] typelogicalReaders207 = tebece0.getLogicalReaders();
        java.util.List listlogicalReaders207= java.util.Arrays.asList(typelogicalReaders207);
        String templogicalReaders207 = listlogicalReaders207.toString();
%>
        <%=templogicalReaders207%>
        <%
        }}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">boundarySpec:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">repeatPeriod:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_value:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECTime tebece2=tebece1.getRepeatPeriod();
if(tebece2 != null){
%>
<%=tebece2.get_value()%><%
}}}}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">startTrigger:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_value:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECTrigger tebece2=tebece1.getStartTrigger();
if(tebece2 != null){
java.lang.String type_value217 = tebece2.get_value();
        String tempResult_value217 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(type_value217));
%>
        <%=tempResult_value217%>
        <%
        }}}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">stopTrigger:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_value:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECTrigger tebece2=tebece1.getStopTrigger();
if(tebece2 != null){
java.lang.String type_value221 = tebece2.get_value();
        String tempResult_value221 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(type_value221));
%>
        <%=tempResult_value221%>
        <%
        }}}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.apache.axis.message.MessageElement[] type_any223 = tebece1.get_any();
        java.util.List list_any223= java.util.Arrays.asList(type_any223);
        String temp_any223 = list_any223.toString();
%>
        <%=temp_any223%>
        <%
        }}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">duration:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_value:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECTime tebece2=tebece1.getDuration();
if(tebece2 != null){
%>
<%=tebece2.get_value()%><%
}}}}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">extension:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpecExtension tebece2=tebece1.getExtension();
if(tebece2 != null){
org.apache.axis.message.MessageElement[] type_any231 = tebece2.get_any();
        java.util.List list_any231= java.util.Arrays.asList(type_any231);
        String temp_any231 = list_any231.toString();
%>
        <%=temp_any231%>
        <%
        }}}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">stableSetInterval:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="-1" ALIGN="LEFT">_value:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec tebece1=tebece0.getBoundarySpec();
if(tebece1 != null){
org.accada.ale.xsd.ale.epcglobal.ECTime tebece2=tebece1.getStableSetInterval();
if(tebece2 != null){
%>
<%=tebece2.get_value()%><%
}}}}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">includeSpecInReports:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
%>
<%=tebece0.isIncludeSpecInReports()%><%
}}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">reportSpecs:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECReportSpec[] typereportSpecs239 = tebece0.getReportSpecs();
        java.util.List listreportSpecs239= java.util.Arrays.asList(typereportSpecs239);
        String tempreportSpecs239 = listreportSpecs239.toString();
%>
        <%=tempreportSpecs239%>
        <%
        }}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
org.apache.axis.message.MessageElement[] type_any241 = tebece0.get_any();
        java.util.List list_any241= java.util.Arrays.asList(type_any241);
        String temp_any241 = list_any241.toString();
%>
        <%=temp_any241%>
        <%
        }}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">extension:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="0" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECSpec tebece0=immediate194mtemp.getECSpec();
if(tebece0 != null){
org.accada.ale.xsd.ale.epcglobal.ECSpecExtension tebece1=tebece0.getExtension();
if(tebece1 != null){
org.apache.axis.message.MessageElement[] type_any245 = tebece1.get_any();
        java.util.List list_any245= java.util.Arrays.asList(type_any245);
        String temp_any245 = list_any245.toString();
%>
        <%=temp_any245%>
        <%
        }}}
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">reports:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECReport[] typereports247 = immediate194mtemp.getReports();
        java.util.List listreports247= java.util.Arrays.asList(typereports247);
        String tempreports247 = listreports247.toString();
%>
        <%=tempreports247%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">totalMilliseconds:</TD>
<TD>
<%
if(immediate194mtemp != null){
%>
<%=immediate194mtemp.getTotalMilliseconds()%><%
}
%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.apache.axis.message.MessageElement[] type_any251 = immediate194mtemp.get_any();
        java.util.List list_any251= java.util.Arrays.asList(type_any251);
        String temp_any251 = list_any251.toString();
%>
        <%=temp_any251%>
        <%
        }
        %>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">extension:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD WIDTH="5%"></TD>
<TD COLSPAN="1" ALIGN="LEFT">_any:</TD>
<TD>
<%
if(immediate194mtemp != null){
org.accada.ale.xsd.ale.epcglobal.ECReportsExtension tebece0=immediate194mtemp.getExtension();
if(tebece0 != null){
org.apache.axis.message.MessageElement[] type_any255 = tebece0.get_any();
        java.util.List list_any255= java.util.Arrays.asList(type_any255);
        String temp_any255 = list_any255.toString();
%>
        <%=temp_any255%>
        <%
        }}
        %>
</TD>
</TABLE>
<%
}
break;
case 289:
        gotMethod = true;
        String specName_48id=  request.getParameter("specName294");
        java.lang.String specName_48idTemp  = specName_48id;
%>
        <jsp:useBean id="_11wsdl1ale1epcglobal1GetSubscribers_47id" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.GetSubscribers" />
        <%
                        _11wsdl1ale1epcglobal1GetSubscribers_47id.setSpecName(specName_48idTemp);
                        java.lang.String[] getSubscribers289mtemp = sampleALEServicePortTypeProxyid.getSubscribers(_11wsdl1ale1epcglobal1GetSubscribers_47id);
                if(getSubscribers289mtemp == null){
        %>
<%=getSubscribers289mtemp%>
<%
        }else{
        java.util.List listreturnp290= java.util.Arrays.asList(getSubscribers289mtemp);
        String tempreturnp290 = listreturnp290.toString();
%>
        <%=tempreturnp290%>
        <%
                }
                break;
                case 296:
                        gotMethod = true;
        %>
        <jsp:useBean id="_11wsdl1ale1epcglobal1EmptyParms_49id" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.EmptyParms" />
        <%
                java.lang.String getStandardVersion296mtemp = sampleALEServicePortTypeProxyid.getStandardVersion(_11wsdl1ale1epcglobal1EmptyParms_49id);
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
                case 301:
                        gotMethod = true;
        %>
        <jsp:useBean id="_11wsdl1ale1epcglobal1EmptyParms_50id" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.EmptyParms" />
        <%
        java.lang.String getVendorVersion301mtemp = sampleALEServicePortTypeProxyid.getVendorVersion(_11wsdl1ale1epcglobal1EmptyParms_50id);
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




// sawielan
// defineReader(String readerName, LRSpec spec)
case 305:
	gotMethod = true;
	String readerName = request.getParameter("readerName");
	specFilePath = request.getParameter("specFilePath");
	%>
	<jsp:useBean id="dR1" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.DefineReader" />
	<%
	dR1.setName(readerName);
	dR1.setSpec(org.accada.ale.util.DeserializerUtil.deserializeLRSpec(specFilePath));
	org.accada.ale.wsdl.ale.epcglobal.DefineReaderResult defineReaderRes  = sampleALEServicePortTypeProxyid.defineReader(dR1);
	%>
	<%=defineReaderRes%>
	<%
break;

// undefineReader(String readerName)
case 306:
	gotMethod = true;
	%>
	<jsp:useBean id="undefineReader1" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.UndefineReader" />
	<%
	undefineReader1.setName(request.getParameter("readerName"));
	org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult undefineReaderRes = sampleALEServicePortTypeProxyid.undefineReader(undefineReader1);
	%>
	<%=undefineReaderRes%>
	<%
	break;

// updateReader(String readerName, LRSpec spec)
case 307:
	gotMethod = true;
	readerName = request.getParameter("readerName");
	specFilePath = request.getParameter("specFilePath");
	%>
	<jsp:useBean id="updateReader1" class="org.accada.ale.wsdl.ale.epcglobal.UpdateReader" />
	<%
	updateReader1.setName(readerName);
	updateReader1.setSpec(org.accada.ale.util.DeserializerUtil.deserializeLRSpec(specFilePath));
	org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult updateReaderRes = sampleALEServicePortTypeProxyid.updateReader(updateReader1);
	%>
	<%=updateReaderRes%>
	<%
	break;

// getLogicalReaderNames
case 308:
	gotMethod = true;
	%>
	<jsp:useBean id="gLRN1" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.EmptyParms" />
	<%
	java.lang.String[] readersArray = sampleALEServicePortTypeProxyid.getLogicalReaderNames(gLRN1);
	if (readersArray == null) {
	%>
	<%=readersArray%>
	<%
	} else {
		java.util.List readersList = java.util.Arrays.asList(readersArray);
		String readers = readersList.toString();
		%>
		<%=readers%>
		<%
	}
		
	break;

// getLRSpec(String readerName);
case 309:
	gotMethod = true;
	%>
	<jsp:useBean id="getLRSpec1" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.GetLRSpec" />
	<%
	getLRSpec1.setName(request.getParameter("readerName"));
	org.accada.ale.xsd.ale.epcglobal.LRSpec lrSpecGetLRSpec = sampleALEServicePortTypeProxyid.getLRSpec(getLRSpec1);
	if (lrSpecGetLRSpec == null) {
		%>
		<%=lrSpecGetLRSpec%>
		<%
	} else {
		boolean getLRSpecIsComposite = lrSpecGetLRSpec.getIsComposite();
		org.accada.ale.xsd.ale.epcglobal.LRProperty[] getLRSpecProperties = lrSpecGetLRSpec.getProperties().getProperty();
		java.lang.String[] getLRSpecReaders = lrSpecGetLRSpec.getReaders().getLogicalReader();
		java.lang.String getLRSpecReadersString = null;
		if (getLRSpecReaders != null) {
			getLRSpecReadersString = java.util.Arrays.asList(getLRSpecReaders).toString();
		}
		org.accada.ale.xsd.ale.epcglobal.LRSpecExtension getLRSpecExtension = lrSpecGetLRSpec.getExtension();
		%>
		<table border="0">
		<tr><th style="text-align:left;"><%=request.getParameter("readerName")%></th>
			<th style="text-align:left;">&nbsp;</th></tr>
		<tr><td style="text-align:left;">isComposite:</td>
			<td style="text-align:left;"><%=getLRSpecIsComposite%></td></tr>
		<tr><td style="text-align:left;">LogicalReaders:</td>
			<td style="text-align:left;"><%=getLRSpecReadersString%></td></tr>
		<tr><td style="text-align:left; vertical-align:top;">LRProperties:</td>
			<td style="text-align:left;">
			<table><tr><th style="border-right:1px solid black; text-align:left;">name</th>
				<th style="text-align:left;">value</th></tr>
			<%
			if (getLRSpecProperties != null) {
				for (org.accada.ale.xsd.ale.epcglobal.LRProperty prop : getLRSpecProperties) {
					%>
					<td style="border-right:1px solid black; text-align:left;"><%=prop.getName()%></td>
					<td style="text-align:left;"><%=prop.getValue()%></td></tr>
					<%
				}
			} else {
				%>
				<td style="border-right:1px solid black; text-align:left;"><%=getLRSpecProperties%></td><td>&nbsp;</td>
				<%
			}
				%>
			</table>
			</td></tr>
		<%
			if (lrSpecGetLRSpec != null && lrSpecGetLRSpec.getExtension() != null) {
				%>
				<tr><td style="text-align:left">ReaderType:</td>
					<td><%=lrSpecGetLRSpec.getExtension().getReaderType()%></td></tr>
				<%
			}
			%>
		</table>
			<%
	}
	break;

// addReaders(String readerName, String[] readers);
case 310:
	gotMethod = true;
	%>	
	<jsp:useBean id="addReaders1" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.AddReaders" />
	<%
	addReaders1.setName(request.getParameter("readerName"));
	addReaders1.setReaders(org.accada.ale.util.DeserializerUtil.deserializeLRLogicalReaders(request.getParameter("filePath")));
	org.accada.ale.wsdl.ale.epcglobal.AddReadersResult addReaderRes = sampleALEServicePortTypeProxyid.addReaders(addReaders1);
	%>
	<%=addReaderRes%>
	<%
	break;
	
	
// setReaders(String readerName, String[] readers);
case 311:
	gotMethod = true;
	%>
	<jsp:useBean id="setReaders1" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.SetReaders" />
	<%
	setReaders1.setName(request.getParameter("readerName"));
	setReaders1.setReaders(org.accada.ale.util.DeserializerUtil.deserializeLRLogicalReaders(request.getParameter("filePath")));
	org.accada.ale.wsdl.ale.epcglobal.SetReadersResult setReaderRes = sampleALEServicePortTypeProxyid.setReaders(setReaders1);
	%>
	<%=setReaderRes%>
	<%	break;
	
	
// removeReaders(String readerName, String[] readers);
case 312:
	gotMethod = true;
	%>
	<jsp:useBean id="removeReaders1" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.RemoveReaders" />
	<%
	removeReaders1.setName(request.getParameter("readerName"));
	removeReaders1.setReaders(org.accada.ale.util.DeserializerUtil.deserializeLRLogicalReaders(request.getParameter("filePath")));
	org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult removeReaderRes = sampleALEServicePortTypeProxyid.removeReaders(removeReaders1);
	%>
	<%=removeReaderRes%>
	<%
	break;
	
	
// setProperties(String readerName, LRProperty[] readers);
case 313:
	gotMethod = true;
	%>
	<jsp:useBean id="setProperties1" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.SetProperties" />
	<%
	setProperties1.setName(request.getParameter("readerName"));
	setProperties1.setProperties(org.accada.ale.util.DeserializerUtil.deserializeLRProperties(request.getParameter("filePath")) );
	org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult setPropRes  = sampleALEServicePortTypeProxyid.setProperties(setProperties1);
	%>
	<%=setPropRes%>
	<%	
	break;
	
// getPropertyValue(String readerName, String propertyName)
case 314:
	gotMethod = true;
	%>
	<jsp:useBean id="gPropertyValue1" scope="session" class="org.accada.ale.wsdl.ale.epcglobal.GetPropertyValue" />
	<%
	gPropertyValue1.setName(request.getParameter("readerName"));
	gPropertyValue1.setPropertyName(request.getParameter("propertyName"));
	java.lang.String propertyValue = sampleALEServicePortTypeProxyid.getPropertyValue(gPropertyValue1);
	%>
	<%=propertyValue%>
	<%
	break;
}

} catch (Exception e) { 
%>
exception: <%= e %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>