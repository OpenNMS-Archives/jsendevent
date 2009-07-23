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

import java.util.HashMap;

import org.opennms.jsendevent.JSendEventConstants;
import org.opennms.jsendevent.exceptions.ParameterException;

/**
 * Class initialize arguments
 * 
 * @author indigo@open-factory.org
 */
public class ArgumentMap extends HashMap<String, String>
{

  /**
   * Generated serial
   */
  private static final long serialVersionUID = -4267341050800437224L;

  /** User defined parameter */
  private HashMap<String, String> m_onmsEventParms;

  /**
   * Constructor to initialize a argument map
   * 
   * @param arg
   *          Arguments from command line
   * @throws ParameterException
   *           Incorrect parameter
   */
  public ArgumentMap (String[] arg) throws ParameterException
  {
    this.m_onmsEventParms = new HashMap<String, String> ();
    super.put (JSendEventConstants.PARM_HOST,
        JSendEventConstants.DEFAULT_OPENNMS_SERVER);
    super.put (JSendEventConstants.PARM_PORT_EVENTD, String
        .valueOf (JSendEventConstants.DEFAULT_EVENT_PORT));
    this.initialize (arg);
    this.checkArguments ();
  }

  /**
   * Add an argument
   * 
   * @param k
   *          Option description
   * @param v
   *          Value
   * @throws ParameterException
   *           Argument already exist
   */
  public void addArgument (String k, String v) throws ParameterException
  {
    super.put (k, v);
  }

  /**
   * Get specific argument
   * 
   * @param k
   *          Argument
   * @return Value from the given argument
   * @throws ParameterException
   *           Argument does not exist
   */
  public String getArgument (String k) throws ParameterException
  {
    if (!super.containsKey (k))
    {
      throw new ParameterException ("Argument " + k + " does not exist.");
    }
    return super.get (k).toString ();
  }

  /**
   * Add OpenNMS user defined event parameter
   * 
   * @param k
   *          Key user defined event parameter
   * @param v
   *          Value user defined event parameter
   * @throws ParameterException
   *           User defined parameter already exist
   */
  public void addOnmsEventParm (String k, String v) throws ParameterException
  {
    if (this.m_onmsEventParms.containsKey (k))
    {
      throw new ParameterException ("User defined paramter " + k
          + " already exist.");
    }
    this.m_onmsEventParms.put (k, v);
  }

  /**
   * Get user defined event parameter
   * 
   * @return User defined event parameter
   */
  public HashMap<String, String> getOnmsEventParms ()
  {
    return this.m_onmsEventParms;
  }

  /**
   * Method check if the minimum required arguments set
   * 
   * @throws ParameterException
   *           Required argument is not set
   */
  public void checkArguments () throws ParameterException
  {
    if (!super.containsKey (JSendEventConstants.PARM_INTERFACE))
    {
      throw new ParameterException ("Required argument "
          + JSendEventConstants.PARM_INTERFACE + " is not set.");
    }

    if (!super.containsKey (JSendEventConstants.PARM_UEI))
    {
      throw new ParameterException ("Required argument "
          + JSendEventConstants.PARM_UEI + " is not set.");
    }
  }

  /**
   * Initialize the arguments
   * 
   * @param arg
   *          All arguments
   * @throws ParameterException
   *           Empty argument
   */
  private void initialize (String[] arg) throws ParameterException
  {
    for (int i = 0; i < arg.length; i++)
    {
      try
      {
        // Add verbose mode without value "-v"
        if (arg[i].equals (JSendEventConstants.PARM_VERBOSE))
        {
          this.addArgument (arg[i], "");
        } else if (arg[i].equals (JSendEventConstants.PARM_ARG))
        {
          // Add parameter -p myparm "my special value"
          super.put (arg[i], null);
          this.m_onmsEventParms.put (arg[i + 1], arg[i + 2]);
        } else
        {
          // Add all other values for example: -i <ip-address>
          this.addArgument (arg[i], arg[++i]);
        }
      } catch (Exception e)
      {
        System.err.println ("Arguments not correct.");
        System.exit (JSendEventConstants.EXIT_FAILED);
      }
    }
  }
}
