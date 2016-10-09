/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.utils;

import org.junit.Test;

import com.gameserver.utils.TimeUtils;

import static org.junit.Assert.*;

/**
 *
 * @author Attilio Caravelli
 */
public class TimeUtilsTest {
  
  public TimeUtilsTest() {
  }
  
  /**
   * Test of minutesToNanoSeconds method, of class TimeUtils.
   */
  @Test
  public void testMinutesToNanoSeconds() {
    
    int minutes = 10;
    long expResult = 600000000000L;
    long result = TimeUtils.minutesToNanoSeconds(minutes);
    System.out.println("minutesToNanoSeconds");
    assertEquals("10 minutes are 60.000.000.000 nanoSeconds", expResult, result);
  }

  /**
   * Test of nanoSecondsToMinutes method, of class TimeUtils.
   */
  @Test
  public void testNanoSecondsToMinutes() {
    System.out.println("nanoSecondsToMinutes");
    long nanoTime = 600000000000L;
    long expResult = 10L;
    long result = TimeUtils.nanoSecondsToMinutes(nanoTime);
    assertEquals(expResult, result);
  }
  
  /**
   * Test of LESS or Zero minutesToNanoSeconds method, of class TimeUtils.
   */
  @Test
  public void testMinutesToNanoSeconds_LESS_Or_ZERO() 
  {  
    int minutes = 0;
    long expResult = 0L;
    long result = TimeUtils.minutesToNanoSeconds(minutes);
    System.out.println("minutesToNanoSeconds_LESS_Or_Zero");
    assertEquals("0 minutes are 0 nanoSeconds", expResult, result);
    minutes = -1;
    result = TimeUtils.minutesToNanoSeconds(minutes);
    assertEquals("-1 minutes are 0 nanoSeconds", expResult, result);
  }

  /**
   * Test of LESS or Zero nanoSecondsToMinutes method, of class TimeUtils.
   */
  @Test
  public void testNanoSecondsToMinutes_LESS_Or_ZERO()
  {
    System.out.println("nanoSecondsToMinutes_LESS_Or_Zero");
    long nanoTime = 0L;
    long expResult = 0L;
    long result = TimeUtils.nanoSecondsToMinutes(nanoTime);
    assertEquals("0 Long as parameters return 0",expResult, result);
    nanoTime = -1L;
    result = TimeUtils.nanoSecondsToMinutes(nanoTime);
    assertEquals("-1 Long as parameters return 0",expResult, result);
  }
}
