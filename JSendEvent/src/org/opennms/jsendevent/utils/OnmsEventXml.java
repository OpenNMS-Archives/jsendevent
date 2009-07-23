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

import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.opennms.jsendevent.JSendEventConstants;
import org.opennms.jsendevent.exceptions.ParameterException;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class which represents the OpenNMS event as XML
 * 
 * @author indigo
 */
public class OnmsEventXml
{
  /** XML document for the event */
  private Document m_eventdoc;

  /** DocumentBuilderFactory */
  private DocumentBuilderFactory m_documentBuilderFactory;

  /** DocumentBuilder */
  private DocumentBuilder m_documentBuilder;

  /** OpenNMS Severity */
  private OnmsSeverity m_severity;

  /** Root element for event */
  private Element m_tag_log;

  /** Events */
  private Element m_tag_events;

  /** Event */
  private Element m_tag_event;

  /** UEI */
  private Element m_tag_uei;

  /** Source */
  private Element m_tag_source;

  /** Node id */
  private Element m_tag_nodeid;

  /** Node host */
  private Element m_tag_host;

  /** Interface */
  private Element m_tag_interface;

  /** Time */
  private Element m_tag_time;

  /** User defined parameters */
  private Element m_tag_parms;

  /**
   * Default constructor initialize current time for the event
   */
  public OnmsEventXml ()
  {
    Date currentTime = Calendar.getInstance ().getTime ();
    DateFormat dformat = DateFormat.getDateTimeInstance (DateFormat.FULL,
        DateFormat.FULL);
    dformat.setTimeZone (TimeZone.getTimeZone ("GMT"));

    this.m_severity = new OnmsSeverity ();

    /* Create XML document */
    this.m_documentBuilderFactory = DocumentBuilderFactory.newInstance ();

    /* this.m_documentBuilderFactory.setValidating (true); */
    /* this.m_documentBuilderFactory.setNamespaceAware (true); */

    /* Try to get a DocumentBuilder */
    try
    {
      this.m_documentBuilder = this.m_documentBuilderFactory
          .newDocumentBuilder ();
    } catch (ParserConfigurationException e)
    {
      System.err.println ("Can«t initialize the XML-Document.\n" + "Error "
          + e.getMessage ());
    }

    this.m_eventdoc = this.m_documentBuilder.newDocument ();

    this.m_tag_log = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_LOG);

    this.m_tag_events = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_EVENTS);

    this.m_tag_event = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_EVENT);

    this.m_tag_host = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_HOST);

    this.m_tag_uei = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_UEI);

    this.m_tag_source = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_SOURCE);
    this.m_tag_source.setTextContent (JSendEventConstants.SOURCE_NAME);

    this.m_tag_nodeid = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_NODEID);
    this.m_tag_interface = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_INTERFACE);

    this.m_tag_time = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_TIME);

    this.m_eventdoc.appendChild (this.m_tag_log);
    this.m_tag_log.appendChild (this.m_tag_events);
    this.m_tag_events.appendChild (this.m_tag_event);
    this.m_tag_event.appendChild (this.m_tag_uei);
    this.m_tag_event.appendChild (this.m_tag_source);
    this.m_tag_event.appendChild (this.m_tag_nodeid);
    this.m_tag_event.appendChild (this.m_tag_time);
    this.m_tag_event.appendChild (this.m_tag_host);
    this.m_tag_event.appendChild (this.m_tag_interface);

    this.setTime (dformat.format (currentTime));
  }

  /**
   * Set the current Unique-Event-Identifier
   * 
   * @param uei
   *          Set current Unique-Event-Identifier
   */
  public void setUei (String uei)
  {
    this.m_tag_uei.setTextContent (uei);
  }

  /**
   * Set the event source
   * 
   * @param source
   *          Event source
   */
  public void setSource (String source)
  {
    this.m_tag_source.setTextContent (source);
  }

  /**
   * Set the event interface
   * 
   * @param iface Event interface
   */
  public void setInterface (String iface)
  {
    this.m_tag_interface.setTextContent (iface);
  }

  /**
   * Set the event node id
   * 
   * @param id
   *          Event node id
   * @throws RuntimeException
   *           Node id is not an integer
   */
  public void setNodeId (String id) throws RuntimeException
  {
    Integer nodeid = Integer.parseInt (id);
    this.m_tag_nodeid.setTextContent (nodeid.toString ());
  }

  /**
   * Set the current time in the OpenNMS event.
   * 
   * @param time
   *          Current time
   */
  public void setTime (String time)
  {
    this.m_tag_time.setTextContent (time);
  }

  /**
   * Set the event nodelabel
   * 
   * @param nodelabel
   *          Event nodelabel
   */
  public void setHost (String nodelabel)
  {
    Element tag_host = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_HOST);
    tag_host.setTextContent (nodelabel);
    this.m_tag_event.appendChild (tag_host);
  }

  /**
   * Set the event service
   * 
   * @param service
   *          Event service
   */
  public void setService (String service)
  {
    Element tag_service = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_SERVICE);
    tag_service.setTextContent (service);
    this.m_tag_event.appendChild (tag_service);
  }

  /**
   * Set the event severity
   * 
   * @param severity
   *          Event severity
   * @throws ParameterException
   *           Severity is not valid
   * @throws RuntimeException
   *           Severity is not an integer
   */
  public void setSeverity (String severity) throws ParameterException,
      RuntimeException
  {
    Element tag_severity = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_SEVERITY);
    tag_severity.setTextContent (this.m_severity.resolvSeverity (severity));
    this.m_tag_event.appendChild (tag_severity);
  }

  /**
   * Set the event description
   * 
   * @param description
   *          Event description
   */
  public void setDescription (String description)
  {
    Element tag_description = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_DESCRIPTION);
    tag_description.setTextContent (description);
    this.m_tag_event.appendChild (tag_description);
  }

  /**
   * Set the event operator instruction
   * 
   * @param operinstruct
   *          Event operator instruction
   */
  public void setOperinstruct (String operinstruct)
  {
    Element tag_operinstruct = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_OPERINSTRUCT);
    tag_operinstruct.setTextContent (operinstruct);
    this.m_tag_event.appendChild (tag_operinstruct);
  }

  /**
   * Get OpenNMS event XML document
   * 
   * @return OpenNMS event XML document
   */
  public Document getEventDoc ()
  {
    return this.m_eventdoc;
  }

  /**
   * Add a user defined event parameter
   * 
   * @param key
   *          Key for event parameter
   * @param value
   *          Value for event parameter
   */
  public void addParm (String key, String value)
  {
    Element tag_parm = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_PARM);
    Element tag_parmName = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_PARMNAME);
    Element tag_value = this.m_eventdoc
        .createElement (JSendEventConstants.EVENT_XMLTAG_VALUE);
    CDATASection cdata_value = this.m_eventdoc
        .createCDATASection (JSendEventConstants.EVENT_XMLTAG_VALUE);
    CDATASection cdata_parmName = this.m_eventdoc.createCDATASection (key);

    tag_value.setAttribute ("type", "string");
    tag_value.setAttribute ("encoding", "text");
    tag_value.appendChild (cdata_value);
    tag_parmName.appendChild (cdata_parmName);
    cdata_value.setData (value);

    tag_parm.appendChild (tag_parmName);
    tag_parm.appendChild (tag_value);

    if (this.m_tag_parms == null)
    {
      this.m_tag_parms = this.m_eventdoc
          .createElement (JSendEventConstants.EVENT_XMLTAG_PARMS);
      this.m_tag_event.appendChild (this.m_tag_parms);
      this.m_tag_parms.appendChild (tag_parm);
    } else
    {
      this.m_tag_parms.appendChild (tag_parm);
    }
  }

  /**
   * Serialize the event.
   * 
   * @return Serialized XML as string
   */
  @Override
  public String toString ()
  {
    StringWriter stringWriter = new StringWriter ();
    StreamResult streamResult = new StreamResult (stringWriter);
    TransformerFactory transformerFactory = TransformerFactory.newInstance ();
    try
    {
      Transformer transformer = transformerFactory.newTransformer ();
      transformer.setOutputProperty (OutputKeys.INDENT, "yes");
      transformer.setOutputProperty (
          "{http://xml.apache.org/xslt}indent-amount", "2");
      transformer.setOutputProperty (OutputKeys.METHOD, "xml");
      transformer.transform (new DOMSource (this.m_eventdoc), streamResult);
    } catch (TransformerException e)
    {
      System.err.println ("Error transforming OpenNMS event.\nError: "
          + e.getMessage ());
      System.exit (JSendEventConstants.EXIT_FAILED);
    }
    return stringWriter.toString ();
  }
}
