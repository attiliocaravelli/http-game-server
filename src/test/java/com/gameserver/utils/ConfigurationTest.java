/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.utils;

import org.junit.Test;

import com.gameserver.utils.Configuration;

import static org.junit.Assert.*;

/**
 *
 * @author Attilio Caravelli
 */
public class ConfigurationTest {
  
  public ConfigurationTest() {
  }
  
  /**
   * Test of getTimeOfSession method, of class Configuration.
   */
  @Test
  public void testGetTimeOfSession() {
    System.out.println("getTimeOfSession");
    int expResult = 10;
    int result = Configuration.getTimeOfSession();
    assertEquals(expResult, result);
   }
}
