/*
 * Copyright (c) 2006, ETH Zurich
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the ETH Zurich nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.accada.ale.util;

import java.io.ByteArrayInputStream;
import java.net.URL;

import org.accada.ale.util.DeserializerUtil;
import org.accada.ale.xsd.ale.epcglobal.ECReport;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.apache.log4j.PropertyConfigurator;

import util.ECElementsUtils;

import junit.framework.TestCase;

public class DeserializerUtilTest extends TestCase {

	private static final String EC_SPEC = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns1:ECSpec includeSpecInReports=\"true\" xmlns:ns1=\"urn:epcglobal:ale:xsd:1\"><logicalReaders soapenc:arrayType=\"xsd:string[2]\" xsi:type=\"soapenc:Array\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><logicalReader xsi:type=\"xsd:string\">LogicalReader1</logicalReader><logicalReader xsi:type=\"xsd:string\">LogicalReader2</logicalReader></logicalReaders><boundarySpec xsi:type=\"ns1:ECBoundarySpec\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><startTrigger xsi:type=\"ns1:ECTrigger\" xsi:nil=\"true\"/><repeatPeriod unit=\"MS\" xsi:type=\"ns1:ECTime\">5000</repeatPeriod><stopTrigger xsi:type=\"ns1:ECTrigger\" xsi:nil=\"true\"/><duration unit=\"MS\" xsi:type=\"ns1:ECTime\">3000</duration><stableSetInterval unit=\"MS\" xsi:type=\"ns1:ECTime\">0</stableSetInterval><extension xsi:type=\"ns1:ECBoundarySpecExtension\" xsi:nil=\"true\"/></boundarySpec><reportSpecs soapenc:arrayType=\"ns1:ECReportSpec[1]\" xsi:type=\"soapenc:Array\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><reportSpec reportIfEmpty=\"true\" reportName=\"TestReport\" reportOnlyOnChange=\"false\" xsi:type=\"ns1:ECReportSpec\"><reportSet set=\"CURRENT\" xsi:type=\"ns1:ECReportSetSpec\"/><filterSpec xsi:type=\"ns1:ECFilterSpec\"><includePatterns soapenc:arrayType=\"xsd:string[2]\" xsi:type=\"soapenc:Array\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><includePattern xsi:type=\"xsd:string\">urn:epc:pat:gid-96:1.2.3</includePattern><includePattern xsi:type=\"xsd:string\">urn:epc:pat:gid-96:1.2.*</includePattern></includePatterns><excludePatterns soapenc:arrayType=\"xsd:string[2]\" xsi:type=\"soapenc:Array\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><excludePattern xsi:type=\"xsd:string\">urn:epc:pat:sgtin-64:1.2.3.[1-10]</excludePattern><excludePattern xsi:type=\"xsd:string\">urn:epc:pat:sgtin-64:1.2.*.*</excludePattern></excludePatterns><extension xsi:type=\"ns1:ECFilterSpecExtension\" xsi:nil=\"true\"/></filterSpec><groupSpec soapenc:arrayType=\"xsd:string[2]\" xsi:type=\"soapenc:Array\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><pattern xsi:type=\"xsd:string\">urn:epc:pat:sscc-64:1.[0-99].X</pattern><pattern xsi:type=\"xsd:string\">urn:epc:pat:sscc-64:1.[100-1000].X</pattern></groupSpec><output includeCount=\"true\" includeEPC=\"true\" includeRawDecimal=\"false\" includeRawHex=\"true\" includeTag=\"true\" xsi:type=\"ns1:ECReportOutputSpec\"><extension xsi:type=\"ns1:ECReportOutputSpecExtension\" xsi:nil=\"true\"/></output><extension xsi:type=\"ns1:ECReportSpecExtension\" xsi:nil=\"true\"/></reportSpec></reportSpecs><extension xsi:type=\"ns1:ECSpecExtension\" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/></ns1:ECSpec>";
	private static final String EC_REPORT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns1:ECReport reportName=\"TestReport\" xmlns:ns1=\"urn:epcglobal:ale:xsd:1\"><group groupName=\"TestReportGroupName\" xsi:type=\"ns1:ECReportGroup\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><groupList xsi:type=\"ns1:ECReportGroupList\"><member xsi:type=\"ns1:ECReportGroupListMember\"><epc xsi:type=\"ns2:EPC\" xmlns:ns2=\"urn:epcglobal:xsd:1\">epc1</epc><tag xsi:type=\"ns3:EPC\" xmlns:ns3=\"urn:epcglobal:xsd:1\">tag1</tag><rawHex xsi:type=\"ns4:EPC\" xmlns:ns4=\"urn:epcglobal:xsd:1\">hex1</rawHex><rawDecimal xsi:type=\"ns5:EPC\" xmlns:ns5=\"urn:epcglobal:xsd:1\">dec1</rawDecimal><extension xsi:type=\"ns1:ECReportGroupListMemberExtension\" xsi:nil=\"true\"/></member><member xsi:type=\"ns1:ECReportGroupListMember\"><epc xsi:type=\"ns6:EPC\" xmlns:ns6=\"urn:epcglobal:xsd:1\">epc2</epc><tag xsi:type=\"ns7:EPC\" xmlns:ns7=\"urn:epcglobal:xsd:1\">tag2</tag><rawHex xsi:type=\"ns8:EPC\" xmlns:ns8=\"urn:epcglobal:xsd:1\">hex2</rawHex><rawDecimal xsi:type=\"ns9:EPC\" xmlns:ns9=\"urn:epcglobal:xsd:1\">dec2</rawDecimal><extension xsi:type=\"ns1:ECReportGroupListMemberExtension\" xsi:nil=\"true\"/></member><member xsi:type=\"ns1:ECReportGroupListMember\"><epc xsi:type=\"ns10:EPC\" xmlns:ns10=\"urn:epcglobal:xsd:1\">epc3</epc><tag xsi:type=\"ns11:EPC\" xmlns:ns11=\"urn:epcglobal:xsd:1\">tag3</tag><rawHex xsi:type=\"ns12:EPC\" xmlns:ns12=\"urn:epcglobal:xsd:1\">hex3</rawHex><rawDecimal xsi:type=\"ns13:EPC\" xmlns:ns13=\"urn:epcglobal:xsd:1\">dec3</rawDecimal><extension xsi:type=\"ns1:ECReportGroupListMemberExtension\" xsi:nil=\"true\"/></member><extension xsi:type=\"ns1:ECReportGroupListExtension\" xsi:nil=\"true\"/></groupList><groupCount xsi:type=\"ns1:ECReportGroupCount\"><count xsi:type=\"xsd:int\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">3</count><extension xsi:type=\"ns1:ECReportGroupCountExtension\" xsi:nil=\"true\"/></groupCount><extension xsi:type=\"ns1:ECReportGroupExtension\" xsi:nil=\"true\"/></group><extension xsi:type=\"ns1:ECReportExtension\" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/></ns1:ECReport>";
	private static final String EC_REPORTS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns1:ECReports ALEID=\"TestAleId\" specName=\"TestSpecName\" totalMilliseconds=\"1000\" xmlns:ns1=\"urn:epcglobal:ale:xsd:1\"><reports soapenc:arrayType=\"ns1:ECReport[1]\" xsi:type=\"soapenc:Array\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><report reportName=\"TestReport\" xsi:type=\"ns1:ECReport\"><group groupName=\"TestReportGroupName\" xsi:type=\"ns1:ECReportGroup\"><groupList xsi:type=\"ns1:ECReportGroupList\"><member xsi:type=\"ns1:ECReportGroupListMember\"><epc xsi:type=\"ns2:EPC\" xmlns:ns2=\"urn:epcglobal:xsd:1\">epc1</epc><tag xsi:type=\"ns3:EPC\" xmlns:ns3=\"urn:epcglobal:xsd:1\">tag1</tag><rawHex xsi:type=\"ns4:EPC\" xmlns:ns4=\"urn:epcglobal:xsd:1\">hex1</rawHex><rawDecimal xsi:type=\"ns5:EPC\" xmlns:ns5=\"urn:epcglobal:xsd:1\">dec1</rawDecimal><extension xsi:type=\"ns1:ECReportGroupListMemberExtension\" xsi:nil=\"true\"/></member><member xsi:type=\"ns1:ECReportGroupListMember\"><epc xsi:type=\"ns6:EPC\" xmlns:ns6=\"urn:epcglobal:xsd:1\">epc2</epc><tag xsi:type=\"ns7:EPC\" xmlns:ns7=\"urn:epcglobal:xsd:1\">tag2</tag><rawHex xsi:type=\"ns8:EPC\" xmlns:ns8=\"urn:epcglobal:xsd:1\">hex2</rawHex><rawDecimal xsi:type=\"ns9:EPC\" xmlns:ns9=\"urn:epcglobal:xsd:1\">dec2</rawDecimal><extension xsi:type=\"ns1:ECReportGroupListMemberExtension\" xsi:nil=\"true\"/></member><member xsi:type=\"ns1:ECReportGroupListMember\"><epc xsi:type=\"ns10:EPC\" xmlns:ns10=\"urn:epcglobal:xsd:1\">epc3</epc><tag xsi:type=\"ns11:EPC\" xmlns:ns11=\"urn:epcglobal:xsd:1\">tag3</tag><rawHex xsi:type=\"ns12:EPC\" xmlns:ns12=\"urn:epcglobal:xsd:1\">hex3</rawHex><rawDecimal xsi:type=\"ns13:EPC\" xmlns:ns13=\"urn:epcglobal:xsd:1\">dec3</rawDecimal><extension xsi:type=\"ns1:ECReportGroupListMemberExtension\" xsi:nil=\"true\"/></member><extension xsi:type=\"ns1:ECReportGroupListExtension\" xsi:nil=\"true\"/></groupList><groupCount xsi:type=\"ns1:ECReportGroupCount\"><count xsi:type=\"xsd:int\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">3</count><extension xsi:type=\"ns1:ECReportGroupCountExtension\" xsi:nil=\"true\"/></groupCount><extension xsi:type=\"ns1:ECReportGroupExtension\" xsi:nil=\"true\"/></group><extension xsi:type=\"ns1:ECReportExtension\" xsi:nil=\"true\"/></report></reports><extension xsi:type=\"ns1:ECReportsExtension\" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><ECSpec xsi:type=\"ns1:ECSpec\" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/></ns1:ECReports>";
	
	protected void setUp() throws Exception {

		super.setUp();
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
	}
	
	public void testDeserializeECSpec() throws Exception {
		
		ECSpec ecSpec = DeserializerUtil.deserializeECSpec(new ByteArrayInputStream(EC_SPEC.getBytes()));
		
		ECElementsUtils.assertEquals(ECElementsUtils.createECSpec(), ecSpec);
		
	}
	
	public void testDeserializeECReport() throws Exception {
		
		ECReport ecReport = DeserializerUtil.deserializeECReport(new ByteArrayInputStream(EC_REPORT.getBytes()));
		
		ECElementsUtils.assertEquals(ECElementsUtils.createECReport(), ecReport);
		
	}
	
	public void testDeserializeECReports() throws Exception {
		
		ECReports ecReports = DeserializerUtil.deserializeECReports(new ByteArrayInputStream(EC_REPORTS.getBytes()));

		ECElementsUtils.assertEquals(ECElementsUtils.createECReports(), ecReports);
		
	}
	
}