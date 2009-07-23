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

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import org.opennms.jsendevent.JSendEventConstants;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * 
 * @author indigo@open-factory.org
 */
public class OnmsEventSender
{
  /** IP-Address */
  private Inet4Address m_inet4;

  /** Socket for connection to OpenNMS eventd */
  private Socket m_socket;

  /**
   * Set connection to OpenNMS eventd
   * 
   * @param ip
   *          IP V4 Address to OpenNMS server
   * @param port
   *          TCP Port for eventd
   * @throws UnknownHostException
   *           IP-Address wrong or unknown
   * @throws IOException
   *           Can«t create connection to OpenNMS server
   * @throws RuntimeException
   *           TCP-Port is not correct
   */
  public void setConnection (String ip, String port)
      throws UnknownHostException, IOException, RuntimeException
  {
    int tcp_port = JSendEventConstants.DEFAULT_EVENT_PORT;
    try
    {
      tcp_port = Integer.parseInt (port);
    } catch (RuntimeException e)
    {
      throw new RuntimeException ("TCP-Port: " + port
          + " is not correct. Error: " + e.getMessage ());
    }
    this.m_inet4 = (Inet4Address) Inet4Address.getByName (ip);
    this.m_socket = new Socket (this.m_inet4, tcp_port);
  }

  /**
   * Send an OpenNMS event
   * 
   * @param event
   *          Event to send
   */
  public void sendEvent (OnmsEventXml event)
  {
    DOMImplementation implementation;
    try
    {
      implementation = DOMImplementationRegistry.newInstance ()
          .getDOMImplementation ("XML 3.0");
      DOMImplementationLS feature = (DOMImplementationLS) implementation
          .getFeature ("LS", "3.0");
      
      LSSerializer serializer = feature.createLSSerializer ();
      LSOutput output = feature.createLSOutput ();

      output.setByteStream (this.m_socket.getOutputStream ());
      serializer.write (event.getEventDoc (), output);
    } catch (ClassCastException e)
    {
      System.err.println ("ClassCastException: " + e.getMessage ());
    } catch (ClassNotFoundException e)
    {
      System.err.println ("ClassCastException: " + e.getMessage ());
    } catch (InstantiationException e)
    {
      System.err.println ("InstantiationException: " + e.getMessage ());
    } catch (IllegalAccessException e)
    {
      System.err.println ("IllegalAccessException: " + e.getMessage ());
    } catch (UnknownHostException ex)
    {
      System.err.println ("FAILED\nUnknown host. Error: " + ex.getMessage ());
      System.exit (JSendEventConstants.EXIT_FAILED);
    } catch (IOException ex)
    {
      System.err
          .println ("FAILED\nI/O Exception while creating socket. Error: "
              + ex.getMessage ());
      System.exit (JSendEventConstants.EXIT_FAILED);
    } finally
    {
      try
      {
        this.m_socket.close ();
      } catch (IOException ex)
      {
        System.err
            .println ("FAILED\nI/O Exception when closing socket. Error: "
                + ex.getMessage ());
        System.exit (JSendEventConstants.EXIT_FAILED);
      }
    }
  }
}
