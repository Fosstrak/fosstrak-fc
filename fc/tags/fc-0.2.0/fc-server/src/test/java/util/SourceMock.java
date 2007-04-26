/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Accada (www.accada.org).
 *
 * Accada is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Accada is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Accada; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package util;

import java.util.Hashtable;

import org.accada.reader.rp.proxy.DataSelector;
import org.accada.reader.rp.proxy.RPProxyException;
import org.accada.reader.rp.proxy.ReadPoint;
import org.accada.reader.rp.proxy.ReadReport;
import org.accada.reader.rp.proxy.Source;
import org.accada.reader.rp.proxy.TagFieldValue;
import org.accada.reader.rp.proxy.TagSelector;
import org.accada.reader.rp.proxy.Trigger;

public class SourceMock implements Source {

	private final String name;

	public SourceMock(String name) {
		this.name = name;
	}
	
	public String getName() throws RPProxyException {
		return name;
	}

	public boolean isFixed() throws RPProxyException {
		return false;
	}

	public void setFixed(boolean isFixed) throws RPProxyException {
	}

	public void addReadPoints(ReadPoint[] readPointList) throws RPProxyException {
	}

	public void removeReadPoints(ReadPoint[] readPointList) throws RPProxyException {
	}

	public void removeAllReadPoints() throws RPProxyException {
	}

	public ReadPoint getReadPoint(String name) throws RPProxyException {
		return null;
	}

	public ReadPoint[] getAllReadPoints() throws RPProxyException {
		return null;
	}

	public void addReadTriggers(Trigger[] triggerList) throws RPProxyException {
	}

	public void removeReadTriggers(Trigger[] triggerList) throws RPProxyException {
	}

	public void removeAllReadTriggers() throws RPProxyException {
	}

	public Trigger getReadTrigger(String name) throws RPProxyException {
		return null;
	}

	public Trigger[] getAllReadTriggers() throws RPProxyException {
		return null;
	}

	public void addTagSelectors(TagSelector[] selectorList) throws RPProxyException {
	}

	public void removeTagSelectors(TagSelector[] tagSelectorList) throws RPProxyException {
	}

	public void removeAllTagSelectors() throws RPProxyException {
	}

	public TagSelector getTagSelector(String name) throws RPProxyException {
		return null;
	}

	public TagSelector[] getAllTagSelectors() throws RPProxyException {
		return null;
	}

	public int getGlimpsedTimeout() throws RPProxyException {
		return 0;
	}

	public void setGlimpsedTimeout(int timeout) throws RPProxyException {
	}

	public int getObservedThreshold() throws RPProxyException {
		return 0;
	}

	public void setObservedThreshold(int threshold) throws RPProxyException {
	}

	public int getObservedTimeout() throws RPProxyException {
		return 0;
	}

	public void setObservedTimeout(int timeout) throws RPProxyException {
	}

	public int getLostTimeout() throws RPProxyException {
		return 0;
	}

	public void setLostTimeout(int timeout) throws RPProxyException {
	}

	public ReadReport rawReadIDs(DataSelector dataselector) throws RPProxyException {
		return null;
	}

	public ReadReport readIDs(DataSelector dataselector) throws RPProxyException {
		return null;
	}

	public ReadReport read(DataSelector dataSelector, Hashtable passwords, TagSelector[] selectors) throws RPProxyException {
		return null;
	}

	public void writeID(String newID, String[] passwords, TagSelector[] tagSelectorList) throws RPProxyException {
	}

	public void write(TagFieldValue[] tagFieldValueList, String[] passwords, TagSelector[] tagSelectorList) throws RPProxyException {
	}

	public void kill(String[] passwords, TagSelector[] tagSelectorList) throws RPProxyException {
	}

	public int getReadCyclesPerTrigger() throws RPProxyException {
		return 0;
	}

	public void setReadCyclesPerTrigger(int cycles) throws RPProxyException {
	}

	public int getMaxReadDutyCycles() throws RPProxyException {
		return 0;
	}

	public void setMaxReadDutyCycles(int cycles) throws RPProxyException {
	}

	public int getReadTimeout() throws RPProxyException {
		return 0;
	}

	public void setReadTimeout(int timeout) throws RPProxyException {
	}

	public int getSession() throws RPProxyException {
		return 0;
	}

	public void setSession(int session) throws RPProxyException {
	}

	public int getMaxNumberSupported() throws RPProxyException {
		return 0;
		
	}
	
}