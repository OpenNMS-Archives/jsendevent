/*
 * This file is part of the OpenNMS(R) Application.
 *
 * OpenNMS(R) is Copyright (C) 2009 The OpenNMS Group, Inc.  All rights reserved.
 * OpenNMS(R) is a derivative work, containing both original code, included code and modified
 * code that was published under the GNU General Public License. Copyrights for modified
 * and included code are below.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Modifications:
 *
 * Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * For more information contact: 
 *      OpenNMS Licensing       <license@opennms.org>
 *      http://www.opennms.org/
 *      http://www.opennms.com/
 *
 */

package org.opennms.jsendevent.utils;

import org.opennms.jsendevent.JSendEventConstants;
import org.opennms.jsendevent.exceptions.ParameterException;

public class OnmsSeverity
{
  /** Undefined */
  private final int UNDEF = 0;

  /** Indeterminate */
  private final int INDETERMINATE = 1;

  /** Cleared */
  private final int CLEARED = 2;

  /** Normal */
  private final int NORMAL = 3;

  /** Warning */
  private final int WARNING = 4;

  /** Minor */
  private final int MINOR = 5;

  /** Major */
  private final int MAJOR = 6;

  /** Critical */
  private final int CRITICAL = 7;

  /**
   * Get the OpenNMS severity
   * 
   * @param s
   *          Severity as integer
   * @return Severity as text
   * @throws ParameterException
   *           Severity is not an integer
   */
  public String resolvSeverity (String s) throws ParameterException
  {
    String severity = String.valueOf (UNDEF);
    try
    {
      // Try to parse to an integer
      Integer sev_int = Integer.parseInt (s);
      
      // Check if severity is between 0 and 7
      if (!(sev_int >= UNDEF && sev_int <= CRITICAL))
      {
        throw new ParameterException ("Severity " + s
            + " is not valid. Should between 0 and 7.");
      }
      
      // Severity is valid set OpenNMS severities
      switch (sev_int)
      {
        case UNDEF:
          severity = JSendEventConstants.SEVERITY_UNDEF;
          break;
        case INDETERMINATE:
          severity = JSendEventConstants.SEVERITY_INDETERMINATE;
          break;
        case CLEARED:
          severity = JSendEventConstants.SEVERITY_CLEARED;
          break;
        case NORMAL:
          severity = JSendEventConstants.SEVERITY_NORMAL;
          break;
        case WARNING:
          severity = JSendEventConstants.SEVERITY_WARNING;
          break;
        case MINOR:
          severity = JSendEventConstants.SEVERITY_MINOR;
          break;
        case MAJOR:
          severity = JSendEventConstants.SEVERITY_MAJOR;
          break;
        case CRITICAL:
          severity = JSendEventConstants.SEVERITY_CRITICAL;
          break;
        default:
          severity = JSendEventConstants.SEVERITY_UNDEF;
          break;
      }
    } catch (RuntimeException e)
    {
      throw new ParameterException ("Severity " + s + " is not an integer.");
    }
    return severity;
  }
}
