/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.utils;

import org.junit.Test;

import com.gameserver.utils.ThreadingUtils;

import static org.junit.Assert.*;

/**
 *
 * @author Attilio Caravelli
 */
public class ThreadingUtilsTest {
  
  public ThreadingUtilsTest() {
  }

  /**
   * Test of estimateMaxThread method, of class ThreadingUtils.
   * 4 Core max 80 threads 
   */
  @Test
  public void testEstimateMaxThread() {
    System.out.println("estimateMaxThread");
    int expResult = 80;
    int result = ThreadingUtils.estimateMaxThread();
    assertEquals(expResult, result);
  }
  
}
