/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.interfaces;


/**
 * Interface to log information (Debugging, errors, general info)
 */
public interface LogInterface {
  /**
   * Error on sending data
   * @param message
   */
  public void errorOnSendingData(String message);
  /**
   * Error on Malformed URL
   */
  public void errorMalformedUrl();
  /**
   * Debugging
   * @param message
   */
  public void debugging(String message);
  /**
   * Error on session expired
   */
  public void errorSessionExpired();
}
