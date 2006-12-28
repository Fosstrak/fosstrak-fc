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

package util;

import java.util.Hashtable;

import org.accada.reader.proxy.DataSelector;
import org.accada.reader.proxy.RPProxyException;
import org.accada.reader.proxy.ReadPoint;
import org.accada.reader.proxy.ReadReport;
import org.accada.reader.proxy.Source;
import org.accada.reader.proxy.TagFieldValue;
import org.accada.reader.proxy.TagSelector;
import org.accada.reader.proxy.Trigger;

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