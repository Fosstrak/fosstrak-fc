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

package org.fosstrak.ale.util;

/**
 * backwards compatibility to provide ECReportSetEnum values.
 * @author sawielan
 *
 */
public class ECReportSetEnum {
	/** all the current tags. */
	public static final String CURRENT = "CURRENT";
	
	/** all the new tags. */
	public static final String ADDITIONS = "ADDITIONS";
	
	/** all the tags that left the previous cycle. */
	public static final String DELETIONS = "DELETIONS";
}
