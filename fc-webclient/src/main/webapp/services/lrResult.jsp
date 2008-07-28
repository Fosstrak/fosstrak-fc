<jsp:useBean id="lrfactory" scope="session"
	class="org.apache.cxf.jaxws.JaxWsProxyFactoryBean" />
<%
java.lang.String lrmethod = request.getParameter("lrmethod");
int lrmethodID = 0;
if (lrmethod == null) { 
	lrmethodID = -1;
}

org.fosstrak.ale.wsdl.alelr.epcglobal.ALELRServicePortType lrservice = null;
if (request.getParameter("lrendpoint") != null && request.getParameter("lrendpoint").length() > 0) {
	lrfactory.setServiceClass(org.fosstrak.ale.wsdl.alelr.epcglobal.ALELRServicePortType.class);
	lrfactory.setAddress(request.getParameter("lrendpoint"));
	lrservice = (org.fosstrak.ale.wsdl.alelr.epcglobal.ALELRServicePortType) lrfactory.create();

} else if (lrfactory.getAddress() != null) {
	lrservice = (org.fosstrak.ale.wsdl.alelr.epcglobal.ALELRServicePortType) lrfactory.create();
} else {
	lrmethodID = -1;
}


boolean lrgotMethod = false;
if (lrmethodID != -1) {
	lrmethodID = Integer.parseInt(lrmethod);
}

try {
switch (lrmethodID) {

//defineReader(String readerName, LRSpec spec)
case 305:
	gotMethod = true;
	java.lang.String readerName = request.getParameter("readerName");
	java.lang.String specFilePath = request.getParameter("specFilePath");
	%>
<jsp:useBean id="dR1" scope="session"
	class="org.fosstrak.ale.wsdl.alelr.epcglobal.Define" />
<%
	dR1.setName(readerName);
	dR1.setSpec(org.fosstrak.ale.util.DeserializerUtil.deserializeLRSpec(specFilePath));
	org.fosstrak.ale.wsdl.alelr.epcglobal.DefineResult defineReaderRes  = lrservice.define(dR1);
	%>
<%=defineReaderRes%>
<%
	break;
	
//undefineReader(String readerName)
case 306:
	gotMethod = true;
	%>
<jsp:useBean id="undefineReader1" scope="session"
	class="org.fosstrak.ale.wsdl.alelr.epcglobal.Undefine" />
<%
	undefineReader1.setName(request.getParameter("readerName"));
	org.fosstrak.ale.wsdl.alelr.epcglobal.UndefineResult undefineReaderRes = lrservice.undefine(undefineReader1);
	%>
<%=undefineReaderRes%>
<%
	break;

//updateReader(String readerName, LRSpec spec)
case 307:
	gotMethod = true;
	readerName = request.getParameter("readerName");
	specFilePath = request.getParameter("specFilePath");
	%>
<jsp:useBean id="updateReader1"
	class="org.fosstrak.ale.wsdl.alelr.epcglobal.Update" />
<%
	updateReader1.setName(readerName);
	updateReader1.setSpec(org.fosstrak.ale.util.DeserializerUtil.deserializeLRSpec(specFilePath));
	org.fosstrak.ale.wsdl.alelr.epcglobal.UpdateResult updateReaderRes = lrservice.update(updateReader1);
	%>
<%=updateReaderRes%>
<%
	break;

//getLogicalReaderNames
case 308:
	gotMethod = true;
	%>
<jsp:useBean id="gLRN1" scope="session"
	class="org.fosstrak.ale.wsdl.alelr.epcglobal.EmptyParms" />
<%
	org.fosstrak.ale.wsdl.alelr.epcglobal.ArrayOfString readersArray = lrservice.getLogicalReaderNames(gLRN1);
	if (readersArray == null) {
	%>
<%=readersArray%>
<%
	} else {
		String readers = readersArray.getString().toString();
		%>
<%=readers%>
<%
	}
		
	break;


//getLRSpec(String readerName);
case 309:
	gotMethod = true;
	%>
<jsp:useBean id="getLRSpec1" scope="session"
	class="org.fosstrak.ale.wsdl.alelr.epcglobal.GetLRSpec" />
<%
	getLRSpec1.setName(request.getParameter("readerName"));
 	org.fosstrak.ale.xsd.ale.epcglobal.LRSpec lrSpecGetLRSpec = lrservice.getLRSpec(getLRSpec1);
	if (lrSpecGetLRSpec == null) {
		%>
<%=lrSpecGetLRSpec%>
<%
	} else {
		boolean getLRSpecIsComposite = lrSpecGetLRSpec.isIsComposite();
		java.util.List<org.fosstrak.ale.xsd.ale.epcglobal.LRProperty> getLRSpecProperties = null;
		if (lrSpecGetLRSpec.getProperties() != null) {
			getLRSpecProperties = lrSpecGetLRSpec.getProperties().getProperty();
		}
		java.util.List<String> getLRSpecReaders = null;
		if (lrSpecGetLRSpec.getReaders() != null) {
				getLRSpecReaders = lrSpecGetLRSpec.getReaders().getReader();
		}
		java.lang.String getLRSpecReadersString = null;
		if (getLRSpecReaders != null) {
			getLRSpecReadersString = java.util.Arrays.asList(getLRSpecReaders).toString();
		}
		org.fosstrak.ale.xsd.ale.epcglobal.LRSpecExtension getLRSpecExtension = lrSpecGetLRSpec.getExtension();
		%>
<table border="0">
	<tr>
		<th style="text-align: left;"><%=request.getParameter("readerName")%></th>
		<th style="text-align: left;">&nbsp;</th>
	</tr>
	<tr>
		<td style="text-align: left;">isComposite:</td>
		<td style="text-align: left;"><%=getLRSpecIsComposite%></td>
	</tr>
	<tr>
		<td style="text-align: left;">LogicalReaders:</td>
		<td style="text-align: left;"><%=getLRSpecReadersString%></td>
	</tr>
	<tr>
		<td style="text-align: left; vertical-align: top;">LRProperties:</td>
		<td style="text-align: left;">
		<table>
			<tr>
				<th style="border-right: 1px solid black; text-align: left;">name</th>
				<th style="text-align: left;">value</th>
			</tr>
			<%
			if (getLRSpecProperties != null) {
				for (org.fosstrak.ale.xsd.ale.epcglobal.LRProperty prop : getLRSpecProperties) {
					%>
			<tr>
				<td style="border-right: 1px solid black; text-align: left;"><%=prop.getName()%></td>
				<td style="text-align: left;"><%=prop.getValue()%></td>
			</tr>
			<%
				}
			} else {
				%>
			<td style="border-right: 1px solid black; text-align: left;"><%=getLRSpecProperties%></td>
			<td>&nbsp;</td>
			<%
			}
				%>
		</table>
		</td>
	</tr>
</table>
<%
	}
	break;

//addReaders(String readerName, String[] readers);
case 310:
	gotMethod = true;
	org.fosstrak.ale.wsdl.alelr.epcglobal.AddReaders addReaders = org.fosstrak.ale.util.DeserializerUtil.deserializeAddReaders(request.getParameter("filePath"));
	addReaders.setName(request.getParameter("readerName"));
	org.fosstrak.ale.wsdl.alelr.epcglobal.AddReadersResult addReadersRes = lrservice.addReaders(addReaders);
	%>
<%=addReadersRes%>
<%	
	break;

//setReaders(String readerName, String[] readers);
case 311:
	gotMethod = true;
	org.fosstrak.ale.wsdl.alelr.epcglobal.SetReaders setReaders = org.fosstrak.ale.util.DeserializerUtil.deserializeSetReaders(request.getParameter("filePath"));
	setReaders.setName(request.getParameter("readerName"));
	org.fosstrak.ale.wsdl.alelr.epcglobal.SetReadersResult setReadersRes = lrservice.setReaders(setReaders);
	%>
<%=setReadersRes%>
<%	
	break;

//removeReaders(String readerName, String[] readers);
case 312:
	gotMethod = true;
	org.fosstrak.ale.wsdl.alelr.epcglobal.RemoveReaders removeReaders = org.fosstrak.ale.util.DeserializerUtil.deserializeRemoveReaders(request.getParameter("filePath"));
	removeReaders.setName(request.getParameter("readerName"));
	org.fosstrak.ale.wsdl.alelr.epcglobal.RemoveReadersResult remReaderRes = lrservice.removeReaders(removeReaders);
	%>
<%=remReaderRes%>
<%	
	break;
	
	// setProperties(String readerName, LRProperty[] readers);
case 313:
	gotMethod = true;
	org.fosstrak.ale.wsdl.alelr.epcglobal.SetProperties setProperties1 = org.fosstrak.ale.util.DeserializerUtil.deserializeSetProperties(request.getParameter("filePath"));
	setProperties1.setName(request.getParameter("readerName"));
	org.fosstrak.ale.wsdl.alelr.epcglobal.SetPropertiesResult setPropRes = lrservice.setProperties(setProperties1);
	%>
<%=setPropRes%>
<%	
	break;
	
//getPropertyValue(String readerName, String propertyName)
case 314:
	gotMethod = true;
	%>
<jsp:useBean id="gPropertyValue1" scope="session"
	class="org.fosstrak.ale.wsdl.alelr.epcglobal.GetPropertyValue" />
<%
	gPropertyValue1.setName(request.getParameter("readerName"));
	gPropertyValue1.setPropertyName(request.getParameter("propertyName"));
	java.lang.String propertyValue = lrservice.getPropertyValue(gPropertyValue1);
	%>
<%=propertyValue%>
<%
	break;

// getStandardVersion
case 315:
	%>
<jsp:useBean id="_11wsdl1ale1epcglobal1EmptyParms_493id" scope="session"
	class="org.fosstrak.ale.wsdl.alelr.epcglobal.EmptyParms" />
<%
	java.lang.String getStandardVersion2963mtemp = lrservice.getStandardVersion(_11wsdl1ale1epcglobal1EmptyParms_493id);
	if(getStandardVersion2963mtemp == null){
	%>
<%=getStandardVersion2963mtemp%>
<%
	}else{
	String tempResultreturnp297s = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getStandardVersion2963mtemp));
	%>
<%=tempResultreturnp297s%>
<%
	}
	break;

	
// getVendorVersion
case 316:
	%>
<jsp:useBean id="_11wsdl1ale1epcglobal1EmptyParms_503id" scope="session"
	class="org.fosstrak.ale.wsdl.alelr.epcglobal.EmptyParms" />
<%
	java.lang.String getVendorVersion3012mtemp = lrservice.getVendorVersion(_11wsdl1ale1epcglobal1EmptyParms_503id);
	if(getVendorVersion3012mtemp == null){
	%>
<%=getVendorVersion3012mtemp %>
<%
	}else{
	String tempResultreturnp3022 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getVendorVersion3012mtemp));
	%>
<%= tempResultreturnp3022 %>
<%
	}
	break;
	
case 318:
	
	java.lang.String fenp = lrfactory.getEndpointName().toString();
	java.lang.String fenpa = lrfactory.getAddress();
	%>
EndPointAddress:
<%=fenpa %><br />
EndpointName:
<%=fenp %>
<%
	break;
}
} catch (Exception e) {
%>
exception:
<%=e%>
<%
return;
}

if (!lrgotMethod) {
%>
<%
}
%>
