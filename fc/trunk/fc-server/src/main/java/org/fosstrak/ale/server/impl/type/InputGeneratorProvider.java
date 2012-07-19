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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.fosstrak.ale.server.readers.rp.InputGenerator;

/**
 * little helper class allowing to detach the ALE from using directly a Set of input generator -> enhance testability.<br/>
 * this helper is implemented as a facade directing all requests to the underlying Hash Set.
 * 
 * @author swieland
 *
 */
public class InputGeneratorProvider implements Set<InputGenerator> {

	/** set of input generators which deliver the tag event inputs. */
	private final Set<InputGenerator> inputGenerators = new HashSet<InputGenerator>();

	@Override
	public boolean add(InputGenerator e) {
		return inputGenerators.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends InputGenerator> c) {
		return inputGenerators.addAll(c);
	}

	@Override
	public void clear() {
		inputGenerators.clear();
	}

	@Override
	public boolean contains(Object o) {
		return inputGenerators.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return inputGenerators.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return inputGenerators.isEmpty();
	}

	@Override
	public Iterator<InputGenerator> iterator() {
		return inputGenerators.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return inputGenerators.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return inputGenerators.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return inputGenerators.retainAll(c);
	}

	@Override
	public int size() {
		return inputGenerators.size();
	}

	@Override
	public Object[] toArray() {
		return inputGenerators.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return inputGenerators.toArray(a);
	}
}
