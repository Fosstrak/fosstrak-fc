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
package org.fosstrak.ale.server.readers.impl.type;

import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderFactory;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * little helper class allowing us to detach the Logical Reader Manager from static methods.
 * @author swieland
 *
 */
@Service("readerProvider")
public class ReaderProvider {
	
	@Autowired
	private LogicalReaderManager logicalReaderManager;

	/**
	 * factory method to create a new reader. this can be a CompositeReader or a BaseReader.
	 * @param name name of the reader
	 * @param spec the specificationFile for a Reader
	 * @return a logical reader
	 * @throws ImplementationException when the LogicalReader could not be built by reflection
	 */
	public LogicalReader createReader(String name, LRSpec spec)  throws ImplementationException {
		return LogicalReaderFactory.createReader(name, spec, logicalReaderManager);
	}
}
