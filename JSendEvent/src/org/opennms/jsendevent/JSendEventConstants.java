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

/**
 * Constants for JSendEvent
 * 
 * @author indigo@open-factory.org
 */
public class JSendEventConstants
{
  /** Version */
  public static final String JSENDEVENT_VERSION = "1.0";
  
  /** OpenNMS Eventd TCP-Port */
  public static final int DEFAULT_EVENT_PORT = 5817;

  /** Set the default OpenNMS Server with running Eventd */
  public static final String DEFAULT_OPENNMS_SERVER = "localhost";
  
  /** Set the name for source */
  public static final String SOURCE_NAME = "jsendevent";

  /** Default log file */
  public static final String DEFAULT_LOGFILE = "log/jsendevent.html";

  /** No arguments given */
  public static final int NO_ARGS = 0;

  /** Exit OK */
  public static final int EXIT_OK = 0;

  /** Exit failed */
  public static final int EXIT_FAILED = 1;

  /** Short switch for UEI */
  public static final String PARM_UEI = "-u";

  /** Short switch for host */
  public static final String PARM_HOST = "-h";

  /** Short switch for service */
  public static final String PARM_SERVICE = "-s";

  /** Short switch for node id */
  public static final String PARM_NODEID = "-n";

  /** Short switch for interface */
  public static final String PARM_INTERFACE = "-i";

  /** Short switch for description */
  public static final String PARM_DESCRIPTION = "-d";

  /** Short switch for severity */
  public static final String PARM_SEVERITY = "-x";

  /** Short switch for parameter */
  public static final String PARM_ARG = "-p";

  /** Short switch for parameter */
  public static final String PARM_OPERINSTRUCT = "-o";

  /** Short switch for TCP port for eventd */
  public static final String PARM_PORT_EVENTD = "-t";
  
  /** Short switch for logging on/off */
  public static final String PARM_VERBOSE = "-v";

  /** Help text */
  public static final String HELP_TEXT = "JSendEvent - " + JSENDEVENT_VERSION + "\n" 
      + "Usage: java -jar JSendEvent.jar\n"
      + "    -u \t the universal event identifier (UEI)\n"
      + "    -h \t a hostname to send the event to (default: localhost)\n"
      + "    -t \t TCP port from Eventd (default: 5817)\n"
      + "    -s \t service name\n"
      + "    -n \t node identifier (numeric)\n"
      + "    -i \t IP address of the interface\n"
      + "    -d \t a description for the event browser\n"
      + "    -o \t operator instructions\n"
      + "    -v \t be verbose in logfile and set to debug (default: info)\n"
      + "    -x \t the severity of the event (numeric or name)\n"
      + "    \t\t 1 = Indeterminate\n"
      + "    \t\t 2 = Cleared (unimplemented at this time)\n"
      + "    \t\t 3 = Normal\n" + "    \t\t 4 = Warning\n"
      + "    \t\t 5 = Minor\n" + "    \t\t 6 = Major\n"
      + "    \t\t 7 = Critical\n"
      + "    -p\t an event parameter (ie: -p url \"http://www.google.com\"\n\n"
      + "Example: Force discovery of a node:\n"
      + "    java -jar JSendEvent.jar \\\n" + "    \t -i 172.16.1.1 \\\n"
      + "    \t -u uei.opennms.org/internal/discovery/newSuspect\n\n"
      +	"On Windows: You can use JSendEvent.exe instead of java -jar\n";

  /** Event tag <log></log> */
  public static final String EVENT_XMLTAG_LOG = "log";

  /** Event tag <events></events> */
  public static final String EVENT_XMLTAG_EVENTS = "events";

  /** Event tag <event></event> */
  public static final String EVENT_XMLTAG_EVENT = "event";

  /** Event tag <uei></uei> */
  public static final String EVENT_XMLTAG_UEI = "uei";

  /** Event tag <source></source> */
  public static final String EVENT_XMLTAG_SOURCE = "source";

  /** Event tag <nodeid></nodeid> */
  public static final String EVENT_XMLTAG_NODEID = "nodeid";

  /** Event tag <interface></interface> */
  public static final String EVENT_XMLTAG_INTERFACE = "interface";
  
  /** Event tag <time></time> */
  public static final String EVENT_XMLTAG_TIME = "time";

  /** Event tag <service></service> */
  public static final String EVENT_XMLTAG_SERVICE = "service";

  /** Event tag <severity></severity> */
  public static final String EVENT_XMLTAG_SEVERITY = "severity";

  /** Event tag <host></host> */
  public static final String EVENT_XMLTAG_HOST = "host";

  /** Event tag <description></description> */
  public static final String EVENT_XMLTAG_DESCRIPTION = "descr";

  /** Event tag <operinstruct></operinstruct> */
  public static final String EVENT_XMLTAG_OPERINSTRUCT = "operinstruct";

  /** Event tag <parms></parms> */
  public static final String EVENT_XMLTAG_PARMS = "parms";

  /** Event tag <parm></parm> */
  public static final String EVENT_XMLTAG_PARM = "parm";

  /** Event tag <parmName></parmName> */
  public static final String EVENT_XMLTAG_PARMNAME = "parmName";

  /** Event tag <value></value> */
  public static final String EVENT_XMLTAG_VALUE = "value";

  /** Event severity undefined */
  public static final String SEVERITY_UNDEF = "undef";

  /** Event severity indeterminate */
  public static final String SEVERITY_INDETERMINATE = "Indeterminate";

  /** Event severity cleared */
  public static final String SEVERITY_CLEARED = "Cleared";

  /** Event severity normal */
  public static final String SEVERITY_NORMAL = "Normal";

  /** Event severity warning */
  public static final String SEVERITY_WARNING = "Warning";

  /** Event severity minor */
  public static final String SEVERITY_MINOR = "Minor";

  /** Event severity major */
  public static final String SEVERITY_MAJOR = "Major";

  /** Event severity critical */
  public static final String SEVERITY_CRITICAL = "Critical";
}
