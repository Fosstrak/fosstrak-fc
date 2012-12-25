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
package org.fosstrak.ale.server.type;

import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;

/**
 * This interface allows to implement different notification channels for the ALE (like File/Socket/Raw-TCP).
 * @author swieland
 */
public interface SubscriberOutputChannel {
	
	/**
	 * notify the given ECReport through the underlying notification technique.
	 * @param reports the report to be delivered to the receiver.
	 * @throws ImplementationException upon delivery errors.
	 * @return true if the notification was sent successfully, exception otherwise.
	 */
	boolean notify(ECReports reports) throws ImplementationException;
}
