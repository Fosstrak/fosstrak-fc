/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Fosstrak (www.fosstrak.org).
 *
 * Fosstrak is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Fosstrak is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Fosstrak; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.fosstrak.ale.server.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroup;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroupListMember;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;
import org.fosstrak.ale.xsd.epcglobal.EPC;
import org.springframework.stereotype.Service;

/**
 * helper to compare ECReports by content.
 * 
 * @author swieland
 *
 */
@Service("ecReportsHelper")
public class ECReportsHelper {

	/**
	 * compares the content of two ECReports and decides on the content whether they equal or not. the 
	 * two reports must follow the same report spec.
	 * @param reportSpec the report spec of the report.
	 * @param newReport the newer report (or report1).
	 * @param oldReport the older report (or report2).
	 * @return true if equal by content, false otherwise.
	 */
	public boolean areReportsEqual(ECReportSpec reportSpec, ECReport newReport, ECReport oldReport) {
		boolean equality = false;
		
		Map<String, ECReportGroup> newGroupByName = new HashMap<String, ECReportGroup> ();
		Map<String, ECReportGroup> oldGroupByName = new HashMap<String, ECReportGroup> ();
		
		// compare the tags...
		if (null != oldReport) {
			List<ECReportGroup> oldGroup = oldReport.getGroup();
			List<ECReportGroup> newGroup = newReport.getGroup();
			if (oldGroup.size() == newGroup.size()) {
				// equal amount of groups, so need to compare the groups...
				for (ECReportGroup g : oldGroup) oldGroupByName.put(g.getGroupName(), g);
				for (ECReportGroup g : newGroup) newGroupByName.put(g.getGroupName(), g);
				
				for (String gName : oldGroupByName.keySet()) {
					ECReportGroup og = oldGroupByName.get(gName);
					ECReportGroup ng = newGroupByName.get(gName);
					
					// now compare the two groups...
					if ((null != og.getGroupList()) && (null != ng.getGroupList())) {
						// need to check								
						boolean useEPC = reportSpec.getOutput().isIncludeEPC();
						boolean useTag = reportSpec.getOutput().isIncludeTag();
						boolean useHex = reportSpec.getOutput().isIncludeRawHex();
						HashSet<String> hs = new HashSet<String>();
						HashSet<String> hs2 = new HashSet<String>();
						for (ECReportGroupListMember oMember : og.getGroupList().getMember()) {
							
							boolean epcAdded = false;
							// compare according the epc field
							if (useEPC) epcAdded = addEPC(hs, oMember.getEpc());
							if (!epcAdded && useTag) epcAdded = addEPC(hs, oMember.getTag());
							if (!epcAdded && useHex) epcAdded = addEPC(hs, oMember.getRawHex());
							if (!epcAdded) epcAdded = addEPC(hs, oMember.getRawDecimal());
						}
						for (ECReportGroupListMember oMember : ng.getGroupList().getMember()) {
							
							boolean epcAdded = false;
							// compare according the epc field
							if (useEPC) epcAdded = addEPC(hs2, oMember.getEpc());
							if (!epcAdded && useTag) epcAdded = addEPC(hs2, oMember.getTag());
							if (!epcAdded && useHex) epcAdded = addEPC(hs2, oMember.getRawHex());
							if (!epcAdded) epcAdded = addEPC(hs2, oMember.getRawDecimal());
						}
						// if intersection is not empty, the sets are not equal
						if (hs.containsAll(hs2) && hs2.containsAll(hs)) {
							// equal
							equality = true;
						} else {
							equality = false;
							break;
						}
						
					} else if ((null == og.getGroupList()) && (null == ng.getGroupList())) {
						// the groups are equal
						equality = true;
					} else {
						// not equal.
						equality = false;
						break;
					}
				}
			}
		}
		return equality;
	}
	
	/**
	 * adds an epc value to the hashset. 
	 * @param set the set where to add.
	 * @param epc the epc to get the value from.
	 * @return true if value could be obtained and added, false otherwise.
	 */
	public boolean addEPC(Set<String> set, EPC epc) {
		if ((null == set) || (null == epc) || (null == epc.getValue())){
			return false;
		}
		set.add(epc.getValue());
		return true;
	}
}
