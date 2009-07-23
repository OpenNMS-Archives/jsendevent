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

package org.opennms.jsendevent;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map.Entry;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.opennms.jsendevent.exceptions.ParameterException;
import org.opennms.jsendevent.utils.ArgumentMap;
import org.opennms.jsendevent.utils.HTMLDateLayout;
import org.opennms.jsendevent.utils.OnmsEventSender;
import org.opennms.jsendevent.utils.OnmsEventXml;

/**
 * Main class to start
 * 
 * @author indigo@open-factory.org
 */
public class Controller
{
  /** Logging */
  private static Logger log = Logger.getRootLogger ();

  /** Log file */
  private String m_logfile;

  /**
   * Default constructor
   */
  public Controller ()
  {
    HTMLLayout layout = new HTMLDateLayout ();
    this.m_logfile = JSendEventConstants.DEFAULT_LOGFILE;
    try
    {
      DailyRollingFileAppender fileAppender = new DailyRollingFileAppender (
          layout, this.m_logfile, "'.'dd-MM-yyyy_HH-mm");
      log.setLevel (Level.INFO);
      log.addAppender (fileAppender);

    } catch (IOException e)
    {
      System.out.println ("Error initializing logfile " + this.m_logfile);
      System.exit (JSendEventConstants.EXIT_FAILED);
    }
  }

  /**
   * Main method to start
   * 
   * @param args
   *          Arguments from command line
   */
  public static void main (String[] args)
  {
    if (args.length == JSendEventConstants.NO_ARGS)
    {
      System.out.println (JSendEventConstants.HELP_TEXT);
      System.exit (JSendEventConstants.EXIT_OK);
    } else
    {
      try
      {
        Controller controller = new Controller ();
        ArgumentMap argumentMap = new ArgumentMap (args);        

        // If "-v" is given set to debug mode
        if (argumentMap.containsKey (JSendEventConstants.PARM_VERBOSE))
        {
          log.setLevel (Level.DEBUG);
          log.debug ("Debug mode enabled");
        }

        log.debug ("Controller initialized");
        OnmsEventSender onmsEventSender = new OnmsEventSender ();
        log.debug ("Event sender initialized");
        OnmsEventXml onmsEventXml = null;

        onmsEventXml = controller.initialize (argumentMap);

        onmsEventSender.setConnection (argumentMap
            .get (JSendEventConstants.PARM_HOST), argumentMap
            .get (JSendEventConstants.PARM_PORT_EVENTD));

        onmsEventSender.sendEvent (onmsEventXml);
        log.info ("Sending event "
            + argumentMap.get (JSendEventConstants.PARM_UEI) + " for "
            + argumentMap.get (JSendEventConstants.PARM_INTERFACE)
            + " successful.");
      } catch (ParameterException e)
      {
        log.error ("Parameter error.\nError: " + e.getMessage ());
        System.err.println ("Parameter error.\nError: " + e.getMessage ());
        System.exit (JSendEventConstants.EXIT_FAILED);
      } catch (UnknownHostException e)
      {
        log.error ("Error sending event.\nError: " + e.getMessage ());
        System.err.println ("Parameter sending event.\nError: " + e.getMessage ());
        System.exit (JSendEventConstants.EXIT_FAILED);
      } catch (IOException e)
      {
        System.err
            .println ("Error writing logfile.\nError: " + e.getMessage ());
        System.exit (JSendEventConstants.EXIT_FAILED);
      }
      log.debug ("Exit code: " + JSendEventConstants.EXIT_OK);
      System.exit (JSendEventConstants.EXIT_OK);
    }
  }

  /**
   * Initialize the OpenNMS event with parameter from command line
   * 
   * @param argumentMap
   *          Arguments from command line
   * @return OpenNMS Event in XML
   */
  private OnmsEventXml initialize (ArgumentMap argumentMap)
  {
    OnmsEventXml onmsEventXml = new OnmsEventXml ();
    log.debug ("OpenNMS Event XML initialized");

    if (argumentMap.containsKey (JSendEventConstants.PARM_UEI))
    {
      onmsEventXml.setUei (argumentMap.get (JSendEventConstants.PARM_UEI));
      log.debug ("Parameter: " + JSendEventConstants.PARM_UEI + " set to "
          + argumentMap.get (JSendEventConstants.PARM_UEI));
    }

    if (argumentMap.containsKey (JSendEventConstants.PARM_SERVICE))
    {
      onmsEventXml.setService (argumentMap
          .get (JSendEventConstants.PARM_SERVICE));
      log.debug ("Parameter: " + JSendEventConstants.PARM_SERVICE + " set to "
          + argumentMap.get (JSendEventConstants.PARM_SERVICE));
    }

    if (argumentMap.containsKey (JSendEventConstants.PARM_NODEID))
    {
      onmsEventXml
          .setNodeId (argumentMap.get (JSendEventConstants.PARM_NODEID));
      log.debug ("Parameter: " + JSendEventConstants.PARM_NODEID + " set to "
          + argumentMap.get (JSendEventConstants.PARM_NODEID));
    }

    if (argumentMap.containsKey (JSendEventConstants.PARM_INTERFACE))
    {
      onmsEventXml.setInterface (argumentMap
          .get (JSendEventConstants.PARM_INTERFACE));
      log.debug ("Parameter: " + JSendEventConstants.PARM_INTERFACE
          + " set to " + argumentMap.get (JSendEventConstants.PARM_INTERFACE));
    }

    if (argumentMap.containsKey (JSendEventConstants.PARM_DESCRIPTION))
    {
      onmsEventXml.setDescription (argumentMap
          .get (JSendEventConstants.PARM_DESCRIPTION));
      log
          .debug ("Parameter: " + JSendEventConstants.PARM_DESCRIPTION
              + " set to "
              + argumentMap.get (JSendEventConstants.PARM_DESCRIPTION));
    }

    if (argumentMap.containsKey (JSendEventConstants.PARM_SEVERITY))
    {
      try
      {
        onmsEventXml.setSeverity (argumentMap
            .get (JSendEventConstants.PARM_SEVERITY));
        log.debug ("Parameter: " + JSendEventConstants.PARM_SEVERITY
            + " set to " + argumentMap.get (JSendEventConstants.PARM_SEVERITY));
      } catch (ParameterException e)
      {
        System.err.println (e.getMessage ());
        log.error (JSendEventConstants.PARM_UEI + ": " + e.getMessage ());
        System.exit (JSendEventConstants.EXIT_FAILED);
      } catch (RuntimeException e)
      {
        System.err.println (e.getMessage ());
        log.error (JSendEventConstants.PARM_UEI + ": " + e.getMessage ());
        System.exit (JSendEventConstants.EXIT_FAILED);
      }
    }

    if (argumentMap.containsKey (JSendEventConstants.PARM_OPERINSTRUCT))
    {
      onmsEventXml.setOperinstruct (argumentMap
          .get (JSendEventConstants.PARM_OPERINSTRUCT));
      log.debug ("Parameter: " + JSendEventConstants.PARM_OPERINSTRUCT
          + " set to "
          + argumentMap.get (JSendEventConstants.PARM_OPERINSTRUCT));
    }

    if (argumentMap.containsKey (JSendEventConstants.PARM_ARG))
    {
      for (Entry<String, String> onmsParm : argumentMap.getOnmsEventParms ()
          .entrySet ())
      {
        onmsEventXml.addParm (onmsParm.getKey (), onmsParm.getValue ());
        log.debug ("Parameter: " + onmsParm.getKey () + " set to "
            + onmsParm.getValue ());
      }
    }
    log.debug (onmsEventXml.toString ());
    return onmsEventXml;
  }
}
