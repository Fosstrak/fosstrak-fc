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
package org.fosstrak.ale.server.impl.type;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.server.ReportsGenerator;
import org.fosstrak.ale.server.impl.ReportsGeneratorImpl;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;

/**
 * little helper class allowing to detach the ALE from using directly a HashMap of report generator -> enhance testability.<br/>
 * this helper is implemented as a facade directing all requests to the underlying concurrent hashmap. further 
 * it provides a simple and mockable helper method to create new ReportsGenerators.
 * 
 * @author swieland
 *
 */
public class ReportsGeneratorsProvider implements Map<String, ReportsGenerator> {
	
	/** map of report generators which create the ec reports. */
	private final Map<String, ReportsGenerator> reportGenerators = new ConcurrentHashMap<String, ReportsGenerator>();

	/**
	 * creates a new Reports Generator.
	 * @param specName the name of the specification.
	 * @param spec the specification.
	 * @return the new reports generator.
	 * @throws ImplementationException internal exception in the implementation.
	 * @throws ECSpecValidationException specification is not valid.
	 */
	public ReportsGenerator createNewReportGenerator(String specName, ECSpec spec) throws ECSpecValidationException, ImplementationException {
		// FIXME: this is far from nice...
		return new ReportsGeneratorImpl(specName, spec);
	}
	
	@Override
	public void clear() {
		reportGenerators.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return reportGenerators.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return reportGenerators.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<String, ReportsGenerator>> entrySet() {
		return reportGenerators.entrySet();
	}

	@Override
	public ReportsGenerator get(Object key) {
		return reportGenerators.get(key);
	}

	@Override
	public boolean isEmpty() {
		return reportGenerators.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return reportGenerators.keySet();
	}

	@Override
	public ReportsGenerator put(String key, ReportsGenerator value) {
		return reportGenerators.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends ReportsGenerator> m) {
		reportGenerators.putAll(m);
	}

	@Override
	public ReportsGenerator remove(Object key) {
		return reportGenerators.remove(key);
	}

	@Override
	public int size() {
		return reportGenerators.size();
	}

	@Override
	public Collection<ReportsGenerator> values() {
		return reportGenerators.values();
	}
	
}
