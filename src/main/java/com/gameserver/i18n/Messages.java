/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *  Default constants (message keys)
 *  The method "getBundle" is thread safe as default implementation   
 */
public enum Messages {
  /** 
   * Key for debugging info 
   */
  ALERT_DEBUG, 
  /** 
   * Key of the alert on correct starting of the server
   */
  ALERT_START_SERVER,
  /** 
   * Key of the alert on processing an incoming request 
   */
  ALERT_ON_PROCESSING_REQUEST,
  /** 
   * Key of the alert on cleaning sessions expired 
   */
  ALERT_CLEANING_SESSIONS,
  /** 
   * Key of the alert on sending data 
   */
  ALERT_ON_SENDING_DATA, 
  /** 
   * Key on malformed URL error 
   */
  ERROR_MESSAGE_MALFORMED_URL, 
  /** 
   * Key on sending data error 
   */
  ERROR_ON_SENDING_DATA, 
  /** 
   * Key on session expired or not valid error 
   */
  ERROR_SESSION_EXPIRED;
  
   /** 
    * Resources for the default locale 
    */
  private static final ResourceBundle messages_ =
    ResourceBundle.getBundle("i18n.messages", new Locale("es"));

  /** 
   * @return String locale-dependent message 
   */
  @Override
  public String toString() {
    return messages_.getString(name());
  }
}