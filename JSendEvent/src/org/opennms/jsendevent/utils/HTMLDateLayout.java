package org.opennms.jsendevent.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.spi.LoggingEvent;

/**
 * This HTML Log Formatter is a simple replacement for the standard Log4J
 * HTMLLayout formatter and replaces the default timestamp (milliseconds,
 * relative to the start of the log) with a more readable timestamp (an example
 * of the default format is 2008-11-21-18:35:21.472-0800).
 * */

public class HTMLDateLayout extends org.apache.log4j.HTMLLayout
{
  /**
   * RegEx pattern looks for
   * <tr>
   * <td>nnn...nnn</td> (all whitespace ignored)
   */
  private static final String RX_TIMESTAMP = "\\s*<\\s*tr\\s*>\\s*<\\s*td\\s*>\\s*(\\d*)\\s*<\\s*/td\\s*>";

  /**
   * The timestamp format. The format can be overriden by including the
   * following property in the Log4J configuration file:
   * 
   * log4j.appender.<category>.layout.TimestampFormat
   * 
   * using the same format string as would be specified with SimpleDateFormat.
   */
  private String timestampFormat = "HH:mm:ss dd-MM-yyyy"; // Default format.

  // Example:
  // 2008-11-
  // 21-18:35:
  // 21.472-0800
  /** Initialize date format */
  private SimpleDateFormat sdf = new SimpleDateFormat (timestampFormat);

  /** Default constructor */
  public HTMLDateLayout ()
  {
    super ();
  }

  /**
   * Override HTMLLayout's format() method
   * 
   * @param event
   *          Logging event
   * @return HTML format
   */
  public String format (LoggingEvent event)
  {
    String record = super.format (event); // Get the log record in the default
    // HTMLLayout format.

    Pattern pattern = Pattern.compile (RX_TIMESTAMP); // RegEx to find the
    // default timestamp
    Matcher matcher = pattern.matcher (record);

    if (!matcher.find ()) // If default timestamp cannot be found,
    {
      return record; // Just return the unmodified log record.
    }

    StringBuffer buffer = new StringBuffer (record);

    buffer.replace (matcher.start (1), // Replace the default timestamp with one
        // formatted as desired.
        matcher.end (1), sdf.format (new Date (event.timeStamp)));

    return buffer.toString (); // Return the log record with the desired
    // timestamp format.
  }

  /**
   * Setter for timestamp format. Called if
   * log4j.appender.<category>.layout.TimestampFormat property is specfied
   * 
   * @param format
   *          Date format
   */
  public void setTimestampFormat (String format)
  {
    this.timestampFormat = format;
    this.sdf = new SimpleDateFormat (format); // Use the format specified by the
    // TimestampFormat property
  }

  /**
   * Getter for timestamp format being used.
   * 
   * @return Timestamp format
   */
  public String getTimestampFormat ()
  {
    return this.timestampFormat;
  }
}