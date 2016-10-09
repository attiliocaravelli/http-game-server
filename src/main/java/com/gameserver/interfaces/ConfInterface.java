/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.interfaces;

/**
 *
 * Default configuration
 */
public interface ConfInterface {
  /**
   * Get default Port 8080
   * @return Port
   */
  public int getPort();
  /**
   * Get default time session (10 Minutes)
   * @return Time in minutes
   */
  public int getTimeOfSession();
}
