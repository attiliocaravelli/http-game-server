/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.exceptions;

/**
 *  Exception throw when there a session is not valid or expired
 */
public class SessionExpiredException extends Exception
{
  
  private static final long serialVersionUID = 4976132469356136301L;
  
  public SessionExpiredException() {
    super();
  }
  
  /**
   * @param message
   */
  public SessionExpiredException(String message) {
    super(message);
  }
  
  /**
   * @param cause
   */
  public SessionExpiredException(Throwable cause) {
    super(cause);
  }
 
}
