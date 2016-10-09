/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.exceptions;

/**
 *  Exception throw when there is a malformed request
 * 
 */
public class MalformedRequestException extends Exception {
  
  private static final long serialVersionUID = -5175820194283909018L;
  
  public MalformedRequestException() {
    super();
  }
  
  /**
   * @param message
   */
  public MalformedRequestException(String message) {
    super(message);
  }
  
  /**
   * @param cause
   */
  public MalformedRequestException(Throwable cause) {
    super(cause);
  }
}
